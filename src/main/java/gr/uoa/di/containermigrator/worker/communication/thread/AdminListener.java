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
public class AdminListener implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Listening for admin connections...");
				Socket socket = EndpointCollection.getAdminChannel().getServerEndpoint().getSocket().accept();

				new Thread(new AdminMessageHandler(socket)).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
