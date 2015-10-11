package gr.uoa.di.containermigrator.worker.communication.channel;

import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.worker.global.Global;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class ChannelUtils {

	public static void sendAdminMessage(Protocol.AdminMessage message, DataOutputStream dOut) throws IOException {
		message.writeDelimitedTo(dOut);
	}
	public static Protocol.AdminMessage recvAdminMessage(DataInputStream dIn) throws IOException {
		return Protocol.AdminMessage.parseDelimitedFrom(dIn);
	}

	public static void sendMessage(Protocol.Message message, DataOutputStream dOut) throws IOException {
		message.writeDelimitedTo(dOut);
	}
	public static Protocol.Message recvMessage(DataInputStream dIn) throws IOException {
		return Protocol.Message.parseDelimitedFrom(dIn);
	}

	public static void sendAdminResponse(Protocol.AdminResponse response, DataOutputStream dOut) throws IOException {
		response.writeDelimitedTo(dOut);
	}
	public static Protocol.AdminResponse recvAdminResponse(DataInputStream dIn) throws IOException {
		return Protocol.AdminResponse.parseDelimitedFrom(dIn);
	}

	public static void sendResponse(Protocol.Response response, DataOutputStream dOut) throws IOException {
		response.writeDelimitedTo(dOut);
	}
	public static Protocol.Response recvResponse(DataInputStream dIn) throws IOException {
		return Protocol.Response.parseDelimitedFrom(dIn);
	}

	public static void multicastMessage(Protocol.Message message) throws Exception {
		for (Map.Entry<String, Endpoint> entry : Global.getProperties().getPeers().entrySet()) {
			try (ClientEndpoint cEnd = Global.getProperties().getPeers().get(entry.getKey()).getClientEndpoint();
				 Socket sock = cEnd.getSocket();
				 DataOutputStream dOut = new DataOutputStream(sock.getOutputStream())) {

				sendMessage(message, dOut);
			}
		}
	}

	public static int fetchAvailablePort() {
		int port = -1;
		try (ServerSocket ss = new ServerSocket(0)) {
			port = ss.getLocalPort();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return port;
	}
}
