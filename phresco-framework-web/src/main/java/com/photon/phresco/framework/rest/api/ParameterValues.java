package com.photon.phresco.framework.rest.api;

import com.photon.phresco.plugins.util.MojoProcessor;

public class ParameterValues {

	private MojoProcessor mojoProcessor;
	private String goal;
	private String userId;
	private String customerId;
	private String buildNumber;
	private String module;
	private String rootModule;
	private String projectCode;
	
	public MojoProcessor getMojoProcessor() {
		return mojoProcessor;
	}
	public void setMojoProcessor(MojoProcessor mojoProcessor) {
		this.mojoProcessor = mojoProcessor;
	}
	
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getBuildNumber() {
		return buildNumber;
	}
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	public String getRootModule() {
		return rootModule;
	}
	public void setRootModule(String rootModule) {
		this.rootModule = rootModule;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
