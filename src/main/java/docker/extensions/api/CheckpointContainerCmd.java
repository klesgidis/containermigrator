package docker.extensions.api;

import com.github.dockerjava.api.NotFoundException;
import com.github.dockerjava.api.command.DockerCmdSyncExec;
import com.github.dockerjava.api.command.SyncDockerCmd;

/**
 * @author kyriakos
 */
public interface CheckpointContainerCmd extends SyncDockerCmd<CheckpointContainerResponse> {
	String getContainerId();

	CheckpointContainerCmd withContainerId(String containerId);

	/**
	 * @throws NotFoundException
	 *             No such container
	 */
	@Override
	CheckpointContainerResponse exec() throws NotFoundException;

	interface Exec extends DockerCmdSyncExec<CheckpointContainerCmd, CheckpointContainerResponse> {
	}
}
