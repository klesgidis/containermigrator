package gr.uoa.di.containermigrator.worker.communication.thread;

import gr.uoa.di.containermigrator.worker.communication.channel.ChannelUtils;
import gr.uoa.di.containermigrator.worker.communication.channel.EndpointCollection;
import gr.uoa.di.containermigrator.worker.communication.protocol.Protocol;
import gr.uoa.di.containermigrator.worker.global.Global;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class NodeListener implements Runnable {
	private String host;

	public NodeListener(String host) {
		this.host = host;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Listening for node connections...");
				Socket socket = Global.getProperties().getPeers().get(host)
						.getServerEndpoint().getSocket().accept();

				new Thread(new NodeMessageHandler(socket)).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
