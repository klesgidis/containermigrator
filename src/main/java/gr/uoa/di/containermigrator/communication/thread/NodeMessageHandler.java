package gr.uoa.di.containermigrator.communication.thread;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.CriuOptions;
import com.github.dockerjava.core.command.PullImageResultCallback;
import gr.uoa.di.containermigrator.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.global.Preferences;
import gr.uoa.di.containermigrator.migrator.UnZip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author kyriakos
 */
public class NodeMessageHandler implements Runnable, Preferences {
	private final Protocol.Message message;
	private final DockerClient dockerClient;

	public NodeMessageHandler(Protocol.Message message) {
		this.message = message;
		this.dockerClient = Global.getDockerClient();
	}

	@Override
	public void run() {
		//System.out.println(this.message.toString());
		switch (this.message.getType()) {
			case RESTORE:
				this.handleRestore();
				break;
			case PULL:
				this.handlePull(this.message.getPull().getImage(), "");
				break;
			case MEMORY_DATA:
				try {
					this.handleMemoryData();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	private void handleRestore() {
		final String taggedImage = this.message.getRestore().getImage() + ":" +
				this.message.getRestore().getTag();
		handlePull(this.message.getRestore().getImage(), this.message.getRestore().getTag());

		final String trgContainer = "tomcat2";
		System.out.println("Creating new container " + trgContainer + ".");
		this.dockerClient.createContainerCmd(taggedImage)
				.withName(trgContainer)
				.exec();
		System.out.println("OK");

		System.out.println("Restoring to container " + trgContainer);

		final String imageDir = IMAGE_BASE + "tomcat1" + "/mem/";
		final String workDir = IMAGE_BASE + "tomcat1" + "/logs/";

		this.dockerClient.restoreContainerCmd(trgContainer)
				.withCriuOptions(new CriuOptions(imageDir, workDir, this.message.getRestore().getTcpEstablished()))
				.withForce(true)
				.exec();
		System.out.println("OK");
	}

	private void handlePull(String image, String tag) {
		final String taggedImage = REGISTRY_URI + image;
		System.out.println("Pulling " + taggedImage);
		this.dockerClient.pullImageCmd(taggedImage)
				.withTag(tag)
				.exec(new PullImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");
	}

	private void handleMemoryData() throws IOException {
		final String containerName = "tomcat2";
		final String imageDir = IMAGE_BASE + containerName;
		new File(imageDir).mkdirs();

		try (FileOutputStream fos = new FileOutputStream(imageDir + "/memory.zip")) {
			fos.write(this.message.getMemoryData().getData().toByteArray());
		}

		UnZip unZip = new UnZip(imageDir + "/memory.zip", imageDir + "/mem/");
		unZip.unZipIt();
	}
}
