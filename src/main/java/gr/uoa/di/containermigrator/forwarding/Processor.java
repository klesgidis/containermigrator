package gr.uoa.di.containermigrator.forwarding;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class Processor implements Runnable {
	private final Socket src;
	private final Socket trg;

	public Processor(Socket src, Socket trg) {
		this.src = src;
		this.trg = trg;
	}

	public void run() {
		try {
			new Thread(new Forwarder("Request", src.getInputStream(), trg.getOutputStream())).start();
			new Thread(new Forwarder("Response", trg.getInputStream(), src.getOutputStream())).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
