import forwarding.StateMonitor;
import global.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author kyriakos
 */
public class CliDaemon implements Runnable {

	public void run() {
		System.out.println("MIGRATION ON: 1, MIGRATION OFF: 0");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while ((s = in.readLine()) != null && s.length() != 0) {
				if (s.equals("0")) {
					StateMonitor.getInstance().migrationState(false);
				} else if (s.equals("1")) {
					StateMonitor.getInstance().migrationState(true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
