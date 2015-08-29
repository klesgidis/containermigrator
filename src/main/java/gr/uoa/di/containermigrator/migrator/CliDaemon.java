package gr.uoa.di.containermigrator.migrator;

import gr.uoa.di.containermigrator.communication.protocol.Command;
import gr.uoa.di.containermigrator.forwarding.StateMonitor;
import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.communication.channel.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author kyriakos
 */
public class CliDaemon implements Runnable {

	public void run() {
		System.out.println(Global.getProperties().getNodeId() + " - MIGRATION ON: 1, MIGRATION OFF: 0");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String s;
		try {
			while ((s = in.readLine()) != null && s.length() != 0) {
				if (s.equals("1")) {

					try (ClientEndpoint cEnd = EndpointCollection.getNodeChannel().getClientEndpoint();
						 DataOutputStream dOut = new DataOutputStream(cEnd.getSocket().getOutputStream());) {
						Command.ReadyToRestore cmd = Command.ReadyToRestore.newBuilder()
								.setImage("TestImage")
								.setTcpEstablished(true)
								.build();

						ChannelUtils.sendReadyToRestore(cmd, dOut);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

//		String s;
//		try {
//			while ((s = in.readLine()) != null && s.length() != 0) {
//				if (s.equals("0")) {
//					StateMonitor.getInstance().migrationState(false);
//				} else if (s.equals("1")) {
//					StateMonitor.getInstance().migrationState(true);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
