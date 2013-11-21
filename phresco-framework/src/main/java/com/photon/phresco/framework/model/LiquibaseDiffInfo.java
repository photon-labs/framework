package com.photon.phresco.framework.model;

public class LiquibaseDiffInfo {
	private String srcConfigurationName;
	private String destConfigurationName;
	private String srcEnvironmentName;
	private String destEnvironmentName;
	
	public String getSrcConfigurationName() {
		return srcConfigurationName;
	}
	public void setSrcConfigurationName(String srcConfigurationName) {
		this.srcConfigurationName = srcConfigurationName;
	}
	public String getDestConfigurationName() {
		return destConfigurationName;
	}
	public void setDestConfigurationName(String destConfigurationName) {
		this.destConfigurationName = destConfigurationName;
	}
	public String getSrcEnvironmentName() {
		return srcEnvironmentName;
	}
	public void setSrcEnvironmentName(String srcEnvironmentName) {
		this.srcEnvironmentName = srcEnvironmentName;
	}
	public String getDestEnvironmentName() {
		return destEnvironmentName;
	}
	public void setDestEnvironmentName(String destEnvironmentName) {
		this.destEnvironmentName = destEnvironmentName;
	}

}
