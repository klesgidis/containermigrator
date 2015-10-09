package gr.uoa.di.containermigrator.worker.communication.channel;


import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.PeersProperties;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class EndpointCollection {
	private final static PeersProperties props = Global.getProperties();

	private Endpoint dataChannel = null;	// Communication with admin to receive data from clients
	private Endpoint adminChannel = null;	// Communication with admin for commands

	public EndpointCollection(String adminHost, int adminPort, int adminListenPort,
							  int dataPort, int dataListenPort) {

		this.dataChannel = new Endpoint(adminHost, dataPort, dataListenPort);
		this.adminChannel = new Endpoint(adminHost, adminPort, adminListenPort);
	}

	public Endpoint getDataChannel() {
		return dataChannel;
	}

	public Endpoint getAdminChannel() {
		return adminChannel;
	}
}
