package gr.uoa.di.containermigrator.worker.communication.channel;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class ServerEndpoint implements AutoCloseable {
	private int port;
	private ServerSocket serverSocket = null;

	public ServerEndpoint(int port) {
		this.port = port;
	}

	public ServerSocket getSocket() throws IOException {
		if (serverSocket == null)
			serverSocket = new ServerSocket(port);
		return serverSocket;
	}

	@Override
	public void close() throws Exception {
		if (serverSocket != null)
			serverSocket.close();
		serverSocket = null;
	}
}
