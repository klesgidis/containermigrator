package gr.uoa.di.containermigrator;

import gr.uoa.di.containermigrator.communication.thread.NodeListener;
import gr.uoa.di.containermigrator.docker.DockerUtils;
import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.global.Preferences;
import gr.uoa.di.containermigrator.migrator.CliDaemon;

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

		new Thread(new NodeListener()).start();

		//Migrator m = new Migrator("tomcat1");

		//m.migrate();


//		new Thread(new Listener(new InetSocketAddress("172.17.0.1", 8080))).start();
//
		new Thread(new CliDaemon()).start();
	}
}
