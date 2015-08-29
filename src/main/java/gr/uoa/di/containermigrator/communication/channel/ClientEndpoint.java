package gr.uoa.di.containermigrator.communication.channel;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kyriakos on 5/11/15.
 */
public class ClientEndpoint implements AutoCloseable {
	public enum SocketType {
		DEFAULT,
		LIVE,
		FUTURE
	}

	private String address;
	private int port;

	private Socket clientSocketExisting = null;
	private Socket clientSocketLive = null;
	private Socket clientSocketFuture = null;

	public ClientEndpoint(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public Socket getSocket() throws IOException {
		return getSocket(SocketType.DEFAULT);
	}
	public Socket getSocket(SocketType type) throws IOException {
		switch (type) {
			case FUTURE:
				if (clientSocketFuture == null)
					clientSocketFuture = new Socket(address, port);
				return clientSocketFuture;
			case LIVE:
				if (clientSocketLive == null)
					clientSocketLive = new Socket(address, port);
				return clientSocketLive;
			case DEFAULT:
				if (clientSocketExisting == null)
					clientSocketExisting = new Socket(address, port);
				return clientSocketExisting;
			default:
				throw new IllegalStateException("Unsupported type " + type.toString());
		}

	}

	@Override
	public void close() throws Exception {
		if (clientSocketExisting != null && clientSocketExisting.isOutputShutdown()) {
			clientSocketExisting.close();
			clientSocketExisting = null;
		}

		if (clientSocketLive != null && clientSocketLive.isOutputShutdown()) {
			clientSocketLive.close();
			clientSocketLive = null;
		}

		if (clientSocketFuture != null && clientSocketFuture.isOutputShutdown()) {
			clientSocketFuture.close();
			clientSocketFuture = null;
		}
	}
}
