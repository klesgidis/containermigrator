package gr.uoa.di.containermigrator.migrator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.CriuOptions;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import gr.uoa.di.containermigrator.communication.channel.ChannelUtils;
import gr.uoa.di.containermigrator.communication.protocol.Command;
import gr.uoa.di.containermigrator.docker.DockerUtils;
import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.global.Preferences;

import java.io.File;

/**
 * @author kyriakos
 */
public class Migrator implements Preferences {
	private final String srcContainer;
	private final String imageDir;
	private final String workDir;
	private final DockerClient dockerClient;

	public Migrator(String srcContainer) throws Exception {
		this.srcContainer = srcContainer;
		this.imageDir = IMAGE_BASE + srcContainer + "/mem/";
		this.workDir = IMAGE_BASE + srcContainer + "/logs/";
		this.dockerClient = Global.getDockerClient();

		// Initialize folders
		new File(this.imageDir).mkdirs();
		new File(this.workDir).mkdirs();

		this.init();
	}

	public void init() throws Exception {
		System.out.println("Warming up " + this.srcContainer + ".");

		InspectContainerResponse inspection = dockerClient.inspectContainerCmd(this.srcContainer)
				.exec();

		// Send initial image to registry
		final String image = inspection.getConfig().getImage();
		this.pushToRegistry(image);

		// Send message to peers to download the image, to be in consistent state
		Command.Message message = Command.Message.newBuilder()
				.setType(Command.Message.Type.PULL)
				.setPull(
						Command.Pull.newBuilder()
								.setImage(image)
								.setRegistry(REGISTRY_URI)
				)
				.build();
		ChannelUtils.multicastMessage(message);

		System.out.println("Warm up complete for " + this.srcContainer + ".");
	}

	public void migrate() {
		// Checkpoint
		this.checkpoint();

		// Commit
		String taggedImage = this.commitAndPush();

		// Pull
		this.pull(taggedImage);

		// Clone container
		String trgContainer = this.createClone(taggedImage);

		// Restore
		this.restore(trgContainer);
	}

	private void checkpoint() {
		System.out.println("Checkpointing container " + this.srcContainer);
		this.dockerClient.checkpointContainerCmd(this.srcContainer)
				.withImagesDirectory(this.imageDir)
				.withWorkDirectory(this.workDir)
				.withTcpEstablished(true)
				.exec();
		System.out.println("OK");
	}

	private void restore(String trgContainer) {
		System.out.println("Restoring to container " + trgContainer);
		this.dockerClient.restoreContainerCmd(trgContainer)
				.withCriuOptions(new CriuOptions(this.imageDir, this.workDir, true))
				.withForce(true)
				.exec();
		System.out.println("OK");
	}

	private String commit() {
		final String newImageName = "tomcat-test-commit";
		System.out.println("Committing container " + this.srcContainer);
		this.dockerClient.commitCmd(this.srcContainer)
				.withRepository(newImageName)
				.exec();
		System.out.println("OK");
		return  newImageName;
	}

	private String pushToRegistry(String image) {
		final String taggedImage = REGISTRY_URI + image;
		System.out.println("Tagging image " + image);
		this.dockerClient.tagImageCmd(image, taggedImage, "")
				.withForce()
				.exec();
		System.out.println("OK");
		System.out.println("Pushing image " + REGISTRY_URI + image);
		this.dockerClient.pushImageCmd(taggedImage)
				.exec(new PushImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");

		return taggedImage;
	}

	private String commitAndPush() {
		String image = this.commit();

		return this.pushToRegistry(image);
	}

	private void pull(String taggedImage) {
		System.out.println("Pulling " + taggedImage);
		this.dockerClient.pullImageCmd(taggedImage)
				.exec(new PullImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");
	}

	private String createClone(String image) {
		final String newContainer = this.srcContainer + "-restored";
		System.out.println("Creating container " + newContainer + " from " + image);
		dockerClient.createContainerCmd(image)
				.withName(newContainer)
				.exec();
		System.out.println("OK");
		return newContainer;
	}
}
