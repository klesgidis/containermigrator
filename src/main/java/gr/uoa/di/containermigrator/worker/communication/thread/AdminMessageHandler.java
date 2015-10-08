package gr.uoa.di.containermigrator.worker.communication.thread;

import com.github.dockerjava.api.DockerClient;
import gr.uoa.di.containermigrator.worker.communication.channel.ChannelUtils;
import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.worker.docker.DockerUtils;
import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.Preferences;
import gr.uoa.di.containermigrator.worker.migrator.MigrationOperator;
import gr.uoa.di.containermigrator.worker.migrator.Migrations;
import sun.org.mozilla.javascript.ast.CatchClause;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class AdminMessageHandler implements Runnable, Preferences {
	private final Socket socket;
	private final DockerClient dockerClient;

	public AdminMessageHandler(Socket socket) {
		this.dockerClient = Global.getDockerClient();
		this.socket = socket;
	}

	@Override
	public void run() {
		//System.out.println(this.message.toString());
		try(InputStream in = socket.getInputStream(); DataInputStream dIn = new DataInputStream(in);
			DataOutputStream dOut = new DataOutputStream(this.socket.getOutputStream())) {

			Protocol.AdminMessage message = ChannelUtils.recvAdminMessage(dIn);

			switch (message.getType()) {
				case START:
					handleStart(dOut, message);
					break;
				case MIGRATE:
					handleMigrate(dOut, message);
					break;
				case LIST:
					System.out.println("List");
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleMigrate(DataOutputStream dOut, Protocol.AdminMessage message) {

	}

	private void handleStart(DataOutputStream dOut, Protocol.AdminMessage message) throws Exception {
		Protocol.AdminResponse.Builder responseBuilder = Protocol.AdminResponse.newBuilder();

		final String containerName = message.getStart().getContainer();
		if (!DockerUtils.exists(containerName)) {
			ChannelUtils.sendAdminResponse(
					responseBuilder.setType(Protocol.AdminResponse.Type.ERROR)
							.setPayload("Container " + containerName + " doesn't exist.")
							.build(), dOut);
			return;
		}

		Migrations.getMigrations().putIfAbsent(containerName, new MigrationOperator(containerName));

		ChannelUtils.sendAdminResponse(
			responseBuilder.setType(Protocol.AdminResponse.Type.OK)
					.build(), dOut);
	}
}
