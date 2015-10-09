package gr.uoa.di.containermigrator.worker.global;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class Global implements Preferences {
	private static DockerClient client = null;
	public static DockerClient getDockerClient() {
		if (client == null) client = DockerClientBuilder.getInstance(DOCKER_SOCKET_URI).build();
		return client;
	}

	private static PeersProperties properties = null;
	public static void loadProperties(String propertyFile) {
		properties = new PeersProperties(propertyFile);
	}
	public static PeersProperties getProperties() {
		if (properties == null) throw new NullPointerException("Node properties are not initialized.");
		return properties;
	}
}
