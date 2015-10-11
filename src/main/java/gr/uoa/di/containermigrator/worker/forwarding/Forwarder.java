package gr.uoa.di.containermigrator.worker.forwarding;

import gr.uoa.di.containermigrator.worker.global.Preferences;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
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
				out.write(buf, 0, count);
			}
		} catch (SocketException e) {

		} catch (IOException e) {
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
		}

	}

	public void close() throws IOException {

	}
}
