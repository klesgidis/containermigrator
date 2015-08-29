package gr.uoa.di.containermigrator.communication.thread;

import gr.uoa.di.containermigrator.communication.channel.*;
import gr.uoa.di.containermigrator.communication.protocol.Command;

import java.io.*;
import java.net.Socket;

/**
 * @author klesgidis
 */
public class NodeListener extends Thread {

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Listening for node connections...");
				Socket socket = EndpointCollection.getNodeChannel().getServerEndpoint().getSocket().accept();

				try(InputStream in = socket.getInputStream(); DataInputStream dIn = new DataInputStream(in)) {
					Command.ReadyToRestore command = ChannelUtils.recvReadyToRestore(dIn);

					new Thread(new NodeCmdHandler(command)).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
