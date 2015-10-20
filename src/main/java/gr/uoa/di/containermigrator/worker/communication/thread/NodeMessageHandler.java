package gr.uoa.di.containermigrator.worker.communication.thread;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.DockerException;
import com.github.dockerjava.api.model.Mount;
import com.github.dockerjava.core.command.PullImageResultCallback;
import gr.uoa.di.containermigrator.worker.communication.channel.ChannelUtils;
import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.Preferences;
import gr.uoa.di.containermigrator.worker.migrator.Migrations;
import gr.uoa.di.containermigrator.worker.migrator.SlaveMigrationOperator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class NodeMessageHandler implements Runnable, Preferences {
	private final Socket socket;
	private final DockerClient dockerClient;

	public NodeMessageHandler(Socket socket) {
		this.socket = socket;
		this.dockerClient = Global.getDockerClient();
	}

	@Override
	public void run() {
		try(InputStream in = socket.getInputStream(); DataInputStream dIn = new DataInputStream(in);
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream())) {

			Protocol.Message message = ChannelUtils.recvMessage(dIn);
			switch (message.getType()) {
				case WARM_UP:
					this.handleWarmUp(message.getWarmUp().getImage());
					break;
				case PREP_FOR_MIGRATION:
					this.handlePrepForMigration(message);
					break;
				case MEMORY_DATA:
					try {
						this.handleMemoryData(message, dOut);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void handleWarmUp(String image) {
		final String taggedImage = REGISTRY_URI + image.replace(":", "_");
		System.out.println("Pulling " + taggedImage);
		this.dockerClient.pullImageCmd(taggedImage)
				.exec(new PullImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");
	}

	private String handlePrepForMigration(Protocol.Message message) {
		final String originalContainer = message.getPrepForMigration().getOriginalContainer();
		final String image = message.getPrepForMigration().getImage();
		final String tag = message.getPrepForMigration().getTag();
		final String newContainerName = "";
		Migrations.getSlaves().putIfAbsent(originalContainer,
				new SlaveMigrationOperator(newContainerName, image, tag));

		return newContainerName;
	}

	private void handleMemoryData(Protocol.Message message, DataOutputStream dOut) throws Exception {
		final String originalContainer = message.getMemoryData().getOriginalContainer();
		final String originalIPAddress = message.getMemoryData().getOriginalIPAddress();
		final int originalPort = message.getMemoryData().getOriginalPort();

		SlaveMigrationOperator s = Migrations.getSlaves()
				.get(originalContainer);

		s.prepareMemoryData(message.getMemoryData().getData().toByteArray());

		s.pull();

		s.createClone();

		if (message.getMemoryData().hasVolumeData()) {
			Mount[] mounts = this.dockerClient.inspectContainerCmd(s.getContainer())
					.exec()
					.getMounts();
			if (mounts.length == 0) throw new Exception("The target container contains no mounts.");

			String extractLocation = mounts[0].getSource();

			s.prepareVolumeData(message.getMemoryData().getVolumeData().toByteArray(), extractLocation);
		}

		int listenPort = s.restore(originalIPAddress, originalPort);
		String container = s.getContainer();

		ChannelUtils.sendResponse(
				Protocol.Response.newBuilder()
						.setType(Protocol.Response.Type.OK)
					.setPayload(container+"#"+listenPort)
					.build(),
				dOut
		);
	}
}
