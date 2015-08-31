package gr.uoa.di.containermigrator.communication.channel;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kyriakos on 5/11/15.
 */
public class ClientEndpoint implements AutoCloseable {
	private String address;
	private int port;

	private Socket socket = null;

	public ClientEndpoint(String address, int port) {
		this.address = address;
		this.port = port;
	}


	public Socket getSocket() throws IOException {
		if (socket == null)
			socket = new Socket(address, port);
		return socket;
	}

	@Override
	public void close() throws Exception {
		if (socket != null) {
			socket.close();
			socket = null;
		}
	}
}
