package gr.uoa.di.containermigrator;

import gr.uoa.di.containermigrator.communication.thread.NodeCmdHandler;
import gr.uoa.di.containermigrator.communication.thread.NodeListener;
import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.migrator.CliDaemon;

/**
 * @author kyriakos
 */
public class Driver {
	private static void usage() {
		System.out.println("Usage: containermigrator <property-file>");
		System.exit(1);
	}

	private static void init(String[] args) {
		if (args.length == 1)
			Global.loadProperties(args[0]);
		else
			usage();
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
