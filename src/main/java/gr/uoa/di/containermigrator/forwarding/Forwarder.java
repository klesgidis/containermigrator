package gr.uoa.di.containermigrator.forwarding;

import gr.uoa.di.containermigrator.global.Global;
import gr.uoa.di.containermigrator.global.Preferences;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * @author kyriakos
 */
public class Forwarder implements Runnable, Closeable, Preferences {
	private String name;
	private InputStream in;
	private OutputStream out;

	public Forwarder(String name, InputStream in, OutputStream out) {
		this.name = name;
		this.in = in;
		this.out = out;
	}

	public void run() {
		byte[] buf = new byte[BUF_SIZE];
		int count;

		try {
			while ((count = in.read(buf)) != -1) {
				StateMonitor.getInstance().checkState();
//				System.out.println(Thread.currentThread().getName() + "-" + this.name
//						+ ": " + buf.toString());
				out.write(buf, 0, count);
			}
		} catch (SocketException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {

			}
			try {
				out.close();
			} catch (IOException e) {

			}
			System.out.println(Thread.currentThread().getName() + "-" + this.name + ": Finished");
		}

	}

	public void close() throws IOException {

	}
}
