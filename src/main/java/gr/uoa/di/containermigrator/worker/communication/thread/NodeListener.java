package gr.uoa.di.containermigrator.worker.communication.thread;

import gr.uoa.di.containermigrator.worker.communication.channel.ChannelUtils;
import gr.uoa.di.containermigrator.worker.communication.channel.EndpointCollection;
import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class NodeListener implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Listening for node connections...");
				Socket socket = EndpointCollection.getNodeChannel().getServerEndpoint().getSocket().accept();

				try(InputStream in = socket.getInputStream(); DataInputStream dIn = new DataInputStream(in)) {
					Protocol.Message message = ChannelUtils.recvMessage(dIn);

					new Thread(new NodeMessageHandler(message)).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
