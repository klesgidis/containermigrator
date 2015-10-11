package gr.uoa.di.containermigrator.worker;

import gr.uoa.di.containermigrator.worker.communication.channel.Endpoint;
import gr.uoa.di.containermigrator.worker.communication.thread.AdminListener;
import gr.uoa.di.containermigrator.worker.communication.thread.NodeListener;
import gr.uoa.di.containermigrator.worker.docker.DockerUtils;
import gr.uoa.di.containermigrator.worker.global.Global;
import gr.uoa.di.containermigrator.worker.global.Preferences;

import java.util.Map;

/**
 * @author kyriakos
 */
public class Driver implements Preferences{

	private static void usage() {
		System.out.println("Usage: containermigrator <property-file>");
		System.exit(1);
	}

	private static void init(String[] args) {
		if (args.length == 1)
			Global.loadProperties(args[0]);
		else
			usage();

		// Start registry
		if (!DockerUtils.isRunning(REGISTRY_CONTAINER)) {
			//docker run -d -p 5000:5000 --name registry registry:2
			Global.getDockerClient()
					.startContainerCmd(REGISTRY_CONTAINER)
					.exec();
		}
	}

	public static void main(String[] args) throws Exception {
		init(args);

		for (Map.Entry<String, Endpoint> peer : Global.getProperties().getPeers().entrySet())
			new Thread(new NodeListener(peer.getKey())).start();

		new Thread(new AdminListener()).start();
	}

}
