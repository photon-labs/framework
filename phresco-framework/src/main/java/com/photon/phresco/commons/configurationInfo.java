package com.photon.phresco.commons;

public class configurationInfo {
	
	private String environment;

	private String serverPort;
	
	private String context;
	
	private String moduleName;

	public configurationInfo() {
		super();
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironmentName(String environment) {
		this.environment = environment;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String toString() {
		return "configurationInfo [environmentName=" + environment
				+ ", serverPort=" + serverPort + ", context=" + context
				+ ", moduleName=" + moduleName + "]";
	}
}
