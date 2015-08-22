import forwarding.Listener;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author kyriakos
 */
public class Driver {
	public static void main(String[] args) throws IOException {
//		InspectContainerResponse response = Global.getDockerClient()
//				.inspectContainerCmd("registry").exec();
//		System.out.println(response.getId());
		new Thread(new Listener(new InetSocketAddress("172.17.0.1", 8080))).start();

		new Thread(new CliDaemon()).start();
	}
}
