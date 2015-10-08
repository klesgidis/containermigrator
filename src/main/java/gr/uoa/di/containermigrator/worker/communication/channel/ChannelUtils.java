package gr.uoa.di.containermigrator.worker.communication.channel;

import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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

	public static void multicastMessage(Protocol.Message message) throws Exception {
		// TODO for all
		try (ClientEndpoint cEnd = EndpointCollection.getNodeChannel().getClientEndpoint();
			 Socket sock = cEnd.getSocket();
			 DataOutputStream dOut = new DataOutputStream(sock.getOutputStream())) {

			sendMessage(message, dOut);
		}
	}
}
