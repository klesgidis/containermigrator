package gr.uoa.di.containermigrator.migrator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.CriuOptions;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
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
		// Start registry
		if (!DockerUtils.isRunning(REGISTRY_CONTAINER)) {
			if (!DockerUtils.exists(REGISTRY_CONTAINER))
				throw new Exception("Container " + REGISTRY_CONTAINER + " doesn't exist.");

			//docker run -d -p 5000:5000 --name registry registry:2
			this.dockerClient.startContainerCmd(REGISTRY_CONTAINER).exec();
		}

		// TODO Send initial Image to registry to prepare for a quick migration
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

	private String commitAndPush() {
		final String newImageName = "tomcat-test-commit";
		System.out.println("Committing container " + this.srcContainer);
		this.dockerClient.commitCmd(this.srcContainer)
				.withRepository(newImageName)
				.exec();
		System.out.println("OK");

		final String taggedImage = REGISTRY_URI + newImageName;
		System.out.println("Tagging image " + newImageName);
		this.dockerClient.tagImageCmd(newImageName, taggedImage, "")
				.withForce()
				.exec();
		System.out.println("OK");
		System.out.println("Pushing image " + REGISTRY_URI + newImageName);
		this.dockerClient.pushImageCmd(taggedImage)
				.exec(new PushImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");

		return taggedImage;
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
