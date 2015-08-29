package gr.uoa.di.containermigrator.communication.channel;

/**
 * Created by kyriakos on 5/11/15.
 */
public class Endpoint {
	private ClientEndpoint clientEndpoint = null;
	private ServerEndpoint serverEndpoint = null;

	public Endpoint(String clientAddress, int clientPort, int serverPort) {
		if (clientAddress != null && clientPort != -1)
			clientEndpoint = new ClientEndpoint(clientAddress, clientPort);
		serverEndpoint = new ServerEndpoint(serverPort);
	}

	public ClientEndpoint getClientEndpoint() {
		if (clientEndpoint == null) throw new IllegalStateException("There is no client endpoint");
		return clientEndpoint;
	}

	public ServerEndpoint getServerEndpoint() {
		return serverEndpoint;
	}

}
