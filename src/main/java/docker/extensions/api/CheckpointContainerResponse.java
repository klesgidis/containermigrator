package docker.extensions.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kyriakos
 */
public class CheckpointContainerResponse  {
	@JsonProperty("Id")
	private String id;
}
