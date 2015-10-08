package gr.uoa.di.containermigrator.worker.communication.channel;


import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.NodeProperties;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class EndpointCollection {
	private final static NodeProperties props = Global.getProperties();
	//private static Endpoint fileChannel = null;		// Files send and receive
	private static Endpoint nodeChannel = null;		// Docker Commands for container's creation and initialization
	private static Endpoint adminChannel = null;		// Docker Commands for container's creation and initialization

	public static Endpoint getNodeChannel() {
		if (nodeChannel == null) nodeChannel = new Endpoint(props.getDockerHost(),
				props.getDockerClientPort(), props.getDockerServerPort());
		return nodeChannel;
	}

	public static Endpoint getAdminChannel() {
		if (adminChannel == null) adminChannel = new Endpoint(props.getAdminHostname(),
				props.getAdminPort(), props.getAdminListenPort());
		return adminChannel;
	}
}
