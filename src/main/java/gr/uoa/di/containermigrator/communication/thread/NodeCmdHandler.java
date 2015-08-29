package gr.uoa.di.containermigrator.communication.thread;

import gr.uoa.di.containermigrator.communication.protocol.Command;

/**
 * @author kyriakos
 */
public class NodeCmdHandler implements Runnable {
	private final Command.ReadyToRestore command;

	public NodeCmdHandler(Command.ReadyToRestore command) {
		this.command = command;
	}

	@Override
	public void run() {
		System.out.println(this.command.toString());
	}
}
