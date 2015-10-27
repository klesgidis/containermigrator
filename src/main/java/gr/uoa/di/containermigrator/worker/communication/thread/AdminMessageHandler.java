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
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class AdminMessageHandler implements Runnable, Preferences {
	private final Socket socket;

	public AdminMessageHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try(InputStream in = this.socket.getInputStream(); DataInputStream dIn = new DataInputStream(in);
			DataOutputStream dOut = new DataOutputStream(this.socket.getOutputStream())) {

			try {
				Protocol.AdminMessage message = ChannelUtils.recvAdminMessage(dIn);

				switch (message.getType()) {
					case START:
						handleStart(dOut, message);
						break;
					case MIGRATE:
						handleMigrate(dOut, message);
						break;
					case PING:
						handlePing(dOut, message);
						break;
				}
			} catch (Exception e) {
				this.sendErrorMessage(dOut, e.getMessage());
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//region Handlers

	private void handlePing(DataOutputStream dOut, Protocol.AdminMessage message) throws Exception {
		this.sendOkMessage(dOut, null);
	}

	private void handleMigrate(DataOutputStream dOut, Protocol.AdminMessage message) throws Exception {
		final String container = message.getMigrate().getContainer();
		final String target = message.getMigrate().getTarget();
		if (!DockerUtils.exists(container)) {
			this.sendErrorMessage(dOut, "Container " + container + " doesn't exist.");
			return;
		}

		MigrationOperator m = Migrations.getMigrations().get(container);
		if (m == null) {
			this.sendErrorMessage(dOut, "Container " + container + " is not running.");
			return;
		}

		String containerAndListenPort = m.migrate(target);

		this.sendOkMessage(dOut, containerAndListenPort);
	}

	private void handleStart(DataOutputStream dOut, Protocol.AdminMessage message) throws Exception {
		final String container = message.getStart().getContainer();
		if (!DockerUtils.exists(container)) {
			this.sendErrorMessage(dOut, "Container " + container + " doesn't exist.");
			return;
		}

		Integer listenPort = ChannelUtils.fetchAvailablePort();
		Migrations.getMigrations().putIfAbsent(container, new MigrationOperator(container, listenPort));

		this.sendOkMessage(dOut, listenPort.toString());
	}

	//endregion

	//region Utilities

	private void sendErrorMessage(DataOutputStream dOut, String payload) throws IOException {
		Protocol.AdminResponse.Builder responseBuilder = Protocol.AdminResponse.newBuilder();
		ChannelUtils.sendAdminResponse(
				responseBuilder.setType(Protocol.AdminResponse.Type.ERROR)
						.setPayload(payload)
						.build(), dOut);
	}

	private void sendOkMessage(DataOutputStream dOut, String payload) throws IOException {
		Protocol.AdminResponse.Builder responseBuilder = Protocol.AdminResponse.newBuilder();

		responseBuilder.setType(Protocol.AdminResponse.Type.OK);
		if (payload != null) responseBuilder.setPayload(payload);

		ChannelUtils.sendAdminResponse(responseBuilder.build(), dOut);
	}

	//endregion
}
