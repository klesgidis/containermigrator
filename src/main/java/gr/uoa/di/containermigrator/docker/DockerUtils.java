package gr.uoa.di.containermigrator.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import gr.uoa.di.containermigrator.global.Global;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class DockerUtils {
	private static final DockerClient dockerClient = Global.getDockerClient();

	public static Container cloneContainer(Container srcContainer) {
		Container container = null;

		return container;

	}

	public static boolean isRunning(String containerName) {
		Iterable<Container> containers = dockerClient.listContainersCmd().exec();
		for(Container c : containers) {
			if (c.getNames()[0].contains(containerName)) return true;
		}
		return false;
	}

	public static boolean exists(String containerName) {
		Iterable<Container> containers = dockerClient.listContainersCmd()
				.withShowAll(true)
				.exec();
		for(Container c : containers) {
			if (c.getNames()[0].contains(containerName)) return true;
		}
		return false;
	}
}
