package gr.uoa.di.containermigrator.worker.migrator;

import gr.uoa.di.containermigrator.worker.docker.DockerUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class CliDaemon implements Runnable {

	private void usage() {
		System.out.println("Command in not specified correctly. Type \"help\" for more information.");
	}

	public void run() {
		System.out.println("Type help for more information");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			while ((line = in.readLine()) != null && line.length() != 0) {
				String [] args = line.split(" ");
				switch (args[0]) {
					case "start": {
						if (args.length < 2) usage();

						final String containerName = args[1];
						if (!DockerUtils.exists(containerName)) {
							usage();
							break;
						}

						//Migrations.getMigrations().putIfAbsent(containerName, new MigrationOperator(containerName));
						break;
					}
					case "migrate": {
						if (args.length < 2) usage();

						final String containerName = args[1];
						if (!DockerUtils.exists(containerName)) {
							usage();
							break;
						}

						MigrationOperator m = Migrations.getMigrations().get(containerName);
						if (m == null) {
							System.out.println("You have to start a container" +
									" from this command line and then migrate.");
							break;
						}
						//m.migrate();
						break;
					}
					case "help": {
						StringBuilder sb = new StringBuilder("");
						sb.append("-- start <container-name>")
								.append(":\t\tStarts an already existing container ")
								.append("and  returns the binding INetAddress. \n");
						sb.append("-- help:\t\t\t\t\t\t")
								.append("Returns all the available commands.");
						System.out.println(sb.toString());
						break;
					}
					default:
						usage();
				}
			}
		} catch (Exception e) {
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
