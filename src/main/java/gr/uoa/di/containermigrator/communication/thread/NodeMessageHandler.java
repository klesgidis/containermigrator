package gr.uoa.di.containermigrator.communication.thread;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.PullImageResultCallback;
import gr.uoa.di.containermigrator.communication.protocol.Command;
import gr.uoa.di.containermigrator.global.Global;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author kyriakos
 */
public class NodeMessageHandler implements Runnable {
	private final Command.Message message;
	private final DockerClient dockerClient;

	public NodeMessageHandler(Command.Message message) {
		this.message = message;
		this.dockerClient = Global.getDockerClient();
	}

	@Override
	public void run() {
		//System.out.println(this.message.toString());
		switch (this.message.getType()) {
			case RESTORE:
				handleRestore();
				break;
			case PULL:
				handlePull();
				break;
		}
	}

	private void handleRestore() {
		throw new NotImplementedException();
	}

	private void handlePull() {
		final String taggedImage = this.message.getPull().getRegistry() + this.message.getPull().getImage();
		System.out.println("Pulling " + taggedImage);
		this.dockerClient.pullImageCmd(taggedImage)
				.exec(new PullImageResultCallback())
				.awaitSuccess();
		System.out.println("OK");
	}
}
