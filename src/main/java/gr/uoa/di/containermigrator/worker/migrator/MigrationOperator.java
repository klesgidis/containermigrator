package gr.uoa.di.containermigrator.worker.migrator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.core.command.PushImageResultCallback;
import com.google.protobuf.ByteString;
import gr.uoa.di.containermigrator.worker.communication.channel.ChannelUtils;
import gr.uoa.di.containermigrator.worker.communication.channel.ClientEndpoint;
import gr.uoa.di.containermigrator.worker.communication.channel.EndpointCollection;
import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.worker.forwarding.Listener;
import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.Preferences;

import java.io.DataOutputStream;
import java.io.File;
import java.net.InetSocketAddress;

/**
 * @author kyriakos
 */
public class MigrationOperator implements Preferences {
	private final String container;
	private String ipAddress;
	private int port;

	private final int listenPort;
	private String image;
	private final String migrationTag = "migrate";

	private final String imageDir;
	private final String workDir;

	private final String containerBase;
	private final DockerClient dockerClient;

	public MigrationOperator(String container, int listenPort) throws Exception {
		this.dockerClient = Global.getDockerClient();
		this.container = container;
		this.listenPort = listenPort;
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

	private void init() throws Exception {
		System.out.println("Warming up " + this.container + ".");

		// Send initial image to registry
		this.pushToRegistry(this.image);

		// Send message to peers to download the image, to be in consistent state
		this.multicastWarmUp(this.image);

		dockerClient.startContainerCmd(this.container).exec();

		System.out.println("Warm up complete for " + this.container + ".");

		InspectContainerResponse inspection = dockerClient.inspectContainerCmd(this.container).exec();
		this.ipAddress = inspection.getNetworkSettings().getIpAddress();
		this.port = inspection.getConfig().getExposedPorts()[0].getPort();

		new Thread(new Listener(this.listenPort, new InetSocketAddress(this.ipAddress, this.port))).start();
	}

	private void multicastWarmUp(String image) throws Exception {
		Protocol.Message message = Protocol.Message.newBuilder()
				.setType(Protocol.Message.Type.WARM_UP)
				.setWarmUp(
						Protocol.Message.WarmUp.newBuilder()
								.setImage(image)
				)
				.build();
		ChannelUtils.multicastMessage(message);
	}

	public void migrate(String host) throws Exception {
		// Send message to peer to be prepared for the migration
		this.sendPrepForMigration(host);

		// Checkpoint
		this.checkpoint();

		// Commit
		this.commitAndPush();

		// Send memory data
		this.compressAndSendMemoryData(host);
	}

	private void sendPrepForMigration(String host) throws Exception {
		Protocol.Message message = Protocol.Message.newBuilder()
				.setType(Protocol.Message.Type.PREP_FOR_MIGRATION)
				.setPrepForMigration(
						Protocol.Message.PrepForMigration.newBuilder()
								.setImage(image)
								.setTag(this.migrationTag)
								.setOriginalContainer(this.container)
								.setTcpEstablished(true)
				)
				.build();

		try (ClientEndpoint cEnd = Global.getProperties().getPeers().get(host).getClientEndpoint();
			 DataOutputStream dOut = new DataOutputStream(cEnd.getSocket().getOutputStream())) {
			ChannelUtils.sendMessage(message, dOut);
		}
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

	private String commitAndPush() {
		String image = this.commit();

		this.pushToRegistry(image);

		return image;
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

	private void compressAndSendMemoryData(String host) throws Exception {
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
								.setOriginalContainer(this.container)
								.setOriginalIPAddress(this.ipAddress)
								.setOriginalPort(this.port)
								.setData(bytes)
				)
				.build();

		try (ClientEndpoint cEnd = Global.getProperties().getPeers().get(host).getClientEndpoint();
			 DataOutputStream dOut = new DataOutputStream(cEnd.getSocket().getOutputStream());) {
			ChannelUtils.sendMessage(message, dOut);
		}
		System.out.println("OK");
	}

}
