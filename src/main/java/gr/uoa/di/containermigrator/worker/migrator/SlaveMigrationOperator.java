package gr.uoa.di.containermigrator.worker.migrator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.CriuOptions;
import com.github.dockerjava.core.command.PullImageResultCallback;
import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class SlaveMigrationOperator implements Preferences {
	private final String container;

	private final String image;
	private final String tag;

	private final DockerClient dockerClient;

	private final String containerBase;

	private final String imageDir;
	private final String workDir;

	public SlaveMigrationOperator(String container, String image, String tag) {
		this.container = container;
		this.image = image;
		this.tag = tag;
		this.dockerClient = Global.getDockerClient();

		this.containerBase = IMAGE_BASE + container;
		this.imageDir = this.containerBase + "/mem/";
		this.workDir = this.containerBase + "/logs/";

		new File(this.imageDir).mkdirs();
		new File(this.workDir).mkdirs();
	}

	public void prepareMemoryData(byte[] bytes) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(this.containerBase + "/memory.zip")) {
			fos.write(bytes);
		}

		UnZip unZip = new UnZip(this.containerBase + "/memory.zip", this.imageDir);
		unZip.unZipIt();
	}

	public void pull() {
		final String taggedImage = REGISTRY_URI + image;
		System.out.println("Pulling " + taggedImage + ":" + this.tag);
		this.dockerClient.pullImageCmd(taggedImage)
				.withTag(this.tag)
				.exec(new PullImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");
	}

	public void createClone() {
		final String taggedImage = this.image + ":" + this.tag;
		System.out.println("Creating new container " + this.container + ".");
		this.dockerClient.createContainerCmd(taggedImage)
				.withName(container)
				.exec();
		System.out.println("OK");
	}

	public void restore() {
		System.out.println("Restoring to container " + this.container);
		this.dockerClient.restoreContainerCmd(this.container)
				.withCriuOptions(new CriuOptions(this.imageDir, this.workDir, true))
				.withForce(true)
				.exec();
		System.out.println("OK");
	}

}
