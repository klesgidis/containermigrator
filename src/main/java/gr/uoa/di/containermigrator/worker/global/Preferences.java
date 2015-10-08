package gr.uoa.di.containermigrator.worker.global;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public interface Preferences {

	String DOCKER_SOCKET_URI = "unix:///var/run/docker.sock";

	int BUF_SIZE = 1024;

	int LISTENING_PORT = 1234;

	String IMAGE_BASE = "/tmp/containermigrator/";

	String REGISTRY_CONTAINER = "registry";

	String REGISTRY_URI = "127.0.0.1:5000/";
}
