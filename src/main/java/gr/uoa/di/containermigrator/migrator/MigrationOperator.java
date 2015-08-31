package gr.uoa.di.containermigrator.migrator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.CriuOptions;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import com.google.protobuf.ByteString;
import gr.uoa.di.containermigrator.communication.channel.ChannelUtils;
import gr.uoa.di.containermigrator.communication.channel.ClientEndpoint;
import gr.uoa.di.containermigrator.communication.channel.EndpointCollection;
import gr.uoa.di.containermigrator.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.global.Preferences;

import java.io.DataOutputStream;
import java.io.File;

/**
 * @author kyriakos
 */
public class MigrationOperator implements Preferences {
	private final String container;
	private final String image;
	private final String migrationTag = "migrate";

	private final String imageDir;
	private final String workDir;

	private final String containerBase;
	private final DockerClient dockerClient;

	public MigrationOperator(String container) throws Exception {
		this.dockerClient = Global.getDockerClient();
		this.container = container;
		this.containerBase = IMAGE_BASE + container;
		this.imageDir = this.containerBase + "/mem/";
		this.workDir = this.containerBase + "/logs/";

		InspectContainerResponse inspection = dockerClient.inspectContainerCmd(this.container).exec();
		this.image = inspection.getConfig().getImage();

		// Initialize folders
		new File(this.imageDir).mkdirs();
		new File(this.workDir).mkdirs();

		this.init();
	}

	public void init() throws Exception {
		System.out.println("Warming up " + this.container + ".");

		// Send initial image to registry
		this.pushToRegistry(this.image);

		// Send message to peers to download the image, to be in consistent state
		this.multicastForPull(this.image);

		dockerClient.startContainerCmd(this.container).exec();

		System.out.println("Warm up complete for " + this.container + ".");
	}

	private void multicastForPull(String image) throws Exception {
		Protocol.Message message = Protocol.Message.newBuilder()
				.setType(Protocol.Message.Type.PULL)
				.setPull(
						Protocol.Message.Pull.newBuilder()
								.setImage(image)
				)
				.build();
		ChannelUtils.multicastMessage(message);
	}

	public void migrate() throws Exception {
		// Checkpoint
		this.checkpoint();

		// Commit
		String image = this.commitAndPush();

		// TODO send memory files
		this.compressAndSendMemoryData();


		Protocol.Message message = Protocol.Message.newBuilder()
				.setType(Protocol.Message.Type.RESTORE)
				.setRestore(
						Protocol.Message.Restore.newBuilder()
								.setImage(image)
								.setTag(this.migrationTag)
								.setTcpEstablished(true)
				)
				.build();

		try (ClientEndpoint cEnd = EndpointCollection.getNodeChannel().getClientEndpoint();
			 DataOutputStream dOut = new DataOutputStream(cEnd.getSocket().getOutputStream());) {
			ChannelUtils.sendMessage(message, dOut);
		}

		// Pull
//		this.pull(taggedImage);
//
//		// Clone container
//		String trgContainer = this.createClone(taggedImage);
//
//		// Restore
//		this.restore(trgContainer);
	}

	private void compressAndSendMemoryData() throws Exception {
		final String output = this.containerBase + "/memory.zip";
		System.out.println("Compressing to " + output);
		Zip zip = new Zip(this.imageDir, output);
		zip.generateFileList();
		zip.zipIt();
		System.out.println("OK");

		System.out.println("Sending data");
		ByteString bytes = MigrationUtils.FileInputStreamToByteString(new File(output));
		Protocol.Message message = Protocol.Message.newBuilder()
				.setType(Protocol.Message.Type.MEMORY_DATA)
				.setMemoryData(Protocol.Message.MemoryData.newBuilder()
								.setData(bytes)
				)
				.build();

		try (ClientEndpoint cEnd = EndpointCollection.getNodeChannel().getClientEndpoint();
			 DataOutputStream dOut = new DataOutputStream(cEnd.getSocket().getOutputStream());) {
			ChannelUtils.sendMessage(message, dOut);
		}
		System.out.println("OK");
	}

	private void checkpoint() {
		System.out.println("Checkpointing container " + this.container);
		this.dockerClient.checkpointContainerCmd(this.container)
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
		// TODO You have to handle containers with the same image (Put Random num on commit name)
		System.out.println("Committing container " + this.container);
		this.dockerClient.commitCmd(this.container)
				.withRepository(this.image)
				.withTag(this.migrationTag)
				.exec();
		System.out.println("OK");
		return  this.image;
	}

	private String pushToRegistry(String image) {
		final String taggedImage = REGISTRY_URI + image;
		System.out.println("Tagging image " + image);
		this.dockerClient.tagImageCmd(image, taggedImage, this.migrationTag)
				.withForce()
				.exec();
		System.out.println("OK");
		System.out.println("Pushing image " + REGISTRY_URI + image);
		this.dockerClient.pushImageCmd(taggedImage)
				.withTag(this.migrationTag)
				.exec(new PushImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");

		return taggedImage;
	}

	private String commitAndPush() {
		String image = this.commit();

		this.pushToRegistry(image);

		return image;
	}

	private void pull(String taggedImage) {
		System.out.println("Pulling " + taggedImage);
		this.dockerClient.pullImageCmd(taggedImage)
				.exec(new PullImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");
	}

	private String createClone(String image) {
		final String newContainer = this.container + "-restored";
		System.out.println("Creating container " + newContainer + " from " + image);
		dockerClient.createContainerCmd(image)
				.withName(newContainer)
				.exec();
		System.out.println("OK");
		return newContainer;
	}
}
