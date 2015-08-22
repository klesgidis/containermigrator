package global;

/**
 * @author kyriakos
 */
public interface Preferences {
	String DOCKER_SOCKET_URI = "unix:///var/run/docker.sock";

	int BUF_SIZE = 1024;

	int LISTENING_PORT = 1234;
}
