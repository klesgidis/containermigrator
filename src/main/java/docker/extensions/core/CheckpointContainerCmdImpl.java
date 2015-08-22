package docker.extensions.core;

import com.github.dockerjava.api.NotFoundException;
import com.github.dockerjava.core.command.AbstrDockerCmd;
import docker.extensions.api.CheckpointContainerCmd;
import docker.extensions.api.CheckpointContainerResponse;

/**
 * @author kyriakos
 */
public class CheckpointContainerCmdImpl extends AbstrDockerCmd<CheckpointContainerCmd, CheckpointContainerResponse> implements
		CheckpointContainerCmd {

	private String containerId;

	public CheckpointContainerCmdImpl(CheckpointContainerCmd.Exec exec, String containerId) {
		super(exec);
		withContainerId(containerId);
	}

	@Override
	public String getContainerId() {
		return containerId;
	}

	@Override
	public CheckpointContainerCmd withContainerId(String containerId) {
		//checkNotNull(containerId, "containerId was not specified");
		//com.google.common.base
		this.containerId = containerId;
		return this;
	}

	@Override
	public String toString() {
		return "inspect " + containerId;
	}

	/**
	 * @throws NotFoundException
	 *             No such container
	 */
	@Override
	public CheckpointContainerResponse exec() throws NotFoundException {
		return super.exec();
	}
}
