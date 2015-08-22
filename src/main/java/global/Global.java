package global;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kyriakos
 */
public class Global {
	private static DockerClient dockerClient = null;
	public static DockerClient getDockerClient() {
		if (dockerClient == null)
			dockerClient = DockerClientBuilder.getInstance(Preferences.DOCKER_SOCKET_URI).build();
		return dockerClient;
	}
}
