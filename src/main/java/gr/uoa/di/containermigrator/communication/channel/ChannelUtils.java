package gr.uoa.di.containermigrator.communication.channel;

import gr.uoa.di.containermigrator.communication.protocol.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class ChannelUtils {

	public static void sendMessage(Command.Message message, DataOutputStream dOut) throws IOException {
		message.writeDelimitedTo(dOut);
	}
	public static Command.Message recvMessage(DataInputStream dIn) throws IOException {
		return Command.Message.parseDelimitedFrom(dIn);
	}

	public static void multicastMessage(Command.Message message) throws Exception {
		// TODO for all
		try (ClientEndpoint cEnd = EndpointCollection.getNodeChannel().getClientEndpoint();
			 DataOutputStream dOut = new DataOutputStream(cEnd.getSocket().getOutputStream());) {

			sendMessage(message, dOut);
		}
	}

}
