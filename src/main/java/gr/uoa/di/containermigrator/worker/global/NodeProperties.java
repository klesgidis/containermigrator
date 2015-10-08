package gr.uoa.di.containermigrator.worker.global;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class NodeProperties implements Preferences{
	private String nodeId = null;

	private String myHostname;

	private String adminHostname = null;
	private int adminPort;
	private int adminListenPort;
	private int dataPort;
	private int dataListenPort;

	private String dockerHost = null;		// The host we talk to
	private int dockerClientPort = -1;	// The port of the node we talk to
	private int dockerServerPort = -1;	// The port we listen for connections

	private String fileHost = null;		// The host we talk to
	private int fileClientPort = -1;	// The port of the node we talk to
	private int fileServerPort = -1;	// The port we listen for connections

	public NodeProperties(String propertyFile) {
		ClassLoader classLoader = getClass().getClassLoader();
		String resourcePath = classLoader.getResource(propertyFile).getFile();

		try (InputStream input = new FileInputStream(resourcePath)) {
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			myHostname = prop.getProperty("node.me.hostname");


			adminHostname = prop.getProperty("admin.hostname");
			adminPort = Integer.parseInt(prop.getProperty("admin.port"));
			adminListenPort = Integer.parseInt(prop.getProperty("node.me.admin.listenPort"));
			dataPort = Integer.parseInt(prop.getProperty("node.me.data.port"));
			dataListenPort = Integer.parseInt(prop.getProperty("node.me.data.listenPort"));

			String[] tokens = prop.getProperty("dockerNode").split("#");
			dockerHost = tokens[0];
			dockerClientPort = Integer.parseInt(tokens[1]);
			dockerServerPort = Integer.parseInt(tokens[2]);

			tokens = prop.getProperty("fileNode").split("#");
			fileHost = tokens[0];
			fileClientPort = Integer.parseInt(tokens[1]);
			fileServerPort = Integer.parseInt(tokens[2]);
		} catch (IOException|NullPointerException e) {
			e.printStackTrace();
		}
	}

	public String getAdminHostname() {
		return adminHostname;
	}

	public int getAdminPort() {
		return adminPort;
	}

	public String getDockerHost() {
		return dockerHost;
	}

	public int getDockerClientPort() {
		return dockerClientPort;
	}

	public int getDockerServerPort() {
		return dockerServerPort;
	}

	public int getAdminListenPort() {
		return adminListenPort;
	}

	public int getDataListenPort() {
		return dataListenPort;
	}

	public int getDataPort() {
		return dataPort;
	}
}
