package com.rest.server.protocol;

import java.util.HashMap;
import java.util.Map;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author fb421
 *
 */

@ApiModel
public class VideoGameDataPackage {
	
	@ApiModelProperty(required=true,position=1)
	private String command;
	
	@ApiModelProperty(required=true,position=2)
	private Map<String, Object> parameters = new HashMap<String, Object>();

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}


	@Override
	public String toString() {
		return "VideoGameDataPackage [command=" + command + ", parameters=" + parameters + "]";
	}
	
}
