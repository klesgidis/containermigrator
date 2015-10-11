package gr.uoa.di.containermigrator.worker.communication.thread;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.PullImageResultCallback;
import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.Preferences;
import gr.uoa.di.containermigrator.worker.migrator.Migrations;
import gr.uoa.di.containermigrator.worker.migrator.SlaveMigrationOperator;

import java.io.IOException;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
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
		switch (this.message.getType()) {
			case WARM_UP:
				this.handleWarmUp(this.message.getWarmUp().getImage());
				break;
			case PREP_FOR_MIGRATION:
				this.handlePrepForMigration();
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

	private void handleWarmUp(String image) {
		final String taggedImage = REGISTRY_URI + image;
		System.out.println("Pulling " + taggedImage);
		this.dockerClient.pullImageCmd(taggedImage)
				.exec(new PullImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");
	}

	private String handlePrepForMigration() {
		final String originalContainer = this.message.getPrepForMigration().getOriginalContainer();
		final String image = this.message.getPrepForMigration().getImage();
		final String tag = this.message.getPrepForMigration().getTag();
		final String newContainerName = "tomcat2";
		Migrations.getSlaves().putIfAbsent(originalContainer,
				new SlaveMigrationOperator(newContainerName, image, tag));

		return newContainerName;
	}

	private void handleMemoryData() throws IOException {
		final String originalContainer = this.message.getMemoryData().getOriginalContainer();
		final String originalIPAddress = this.message.getMemoryData().getOriginalIPAddress();
		final int originalPort = this.message.getMemoryData().getOriginalPort();

		SlaveMigrationOperator s = Migrations.getSlaves()
				.get(originalContainer);

		s.prepareMemoryData(this.message.getMemoryData().getData().toByteArray());

		s.pull();

		s.createClone();

		s.restore(originalIPAddress, originalPort);
	}
}
