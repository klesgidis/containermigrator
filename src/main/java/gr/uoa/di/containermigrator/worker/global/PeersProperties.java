package gr.uoa.di.containermigrator.worker.global;

import gr.uoa.di.containermigrator.worker.communication.channel.Endpoint;
import gr.uoa.di.containermigrator.worker.communication.channel.EndpointCollection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class PeersProperties implements Preferences{

	private Map<String, Endpoint> peers = new HashMap<>();

	private Endpoint admin;

	private String myHostname;

	public PeersProperties(String propertyFile) {
		ClassLoader classLoader = getClass().getClassLoader();
		String resourcePath = classLoader.getResource(propertyFile).getFile();

		try (InputStream input = new FileInputStream(resourcePath)) {
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			this.myHostname = prop.getProperty("node.me.host");

			String adminHost = prop.getProperty("node.me.admin.host");
			int adminPort = Integer.parseInt(prop.getProperty("node.me.admin.port"));
			int adminListenPort = Integer.parseInt(prop.getProperty("node.me.admin.listenPort"));

			this.admin = new Endpoint(adminHost, adminPort, adminListenPort);

			String[] peers = prop.getProperty("peers").split(",");
			for (String peer : peers) {
				String peerHost = prop.getProperty("node."+peer+".host");
				int peerPort = Integer.parseInt(prop.getProperty("node." + peer + ".port"));
				int peerListenPort = Integer.parseInt(prop.getProperty("node." + peer + ".listenPort"));

				this.peers.put(peer, new Endpoint(
						peerHost, peerPort, peerListenPort
				));
			}
		} catch (IOException|NullPointerException e) {
			e.printStackTrace();
		}
	}

	public Endpoint getAdmin() {
		return admin;
	}

	public Map<String, Endpoint> getPeers() {
		return peers;
	}

	public String getMyHostname() {
		return myHostname;
	}

}
