package gr.uoa.di.containermigrator.communication.channel;


import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.global.NodeProperties;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class EndpointCollection {
	private final static NodeProperties props = Global.getProperties();
	private static Endpoint fileChannel = null;		// Files send and receive
	private static Endpoint nodeChannel = null;		// Docker Commands for container's creation and initialization

	public static Endpoint getFileChannel() {
		if (fileChannel == null) fileChannel = new Endpoint(props.getFileHost(),
				props.getFileClientPort(), props.getFileServerPort());
		return fileChannel;
	}

	public static Endpoint getNodeChannel() {
		if (nodeChannel == null) nodeChannel = new Endpoint(props.getDockerHost(),
				props.getDockerClientPort(), props.getDockerServerPort());
		return nodeChannel;
	}
}
