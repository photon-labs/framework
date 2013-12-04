package com.photon.phresco.framework.model;

import java.util.List;

public class DeleteProjectInfo {
	private List<String> appDirNames;
	private List<String> dependents;
	private String rootModule;
	private String actionType;

	public List<String> getAppDirNames() {
		return appDirNames;
	}
	public void setAppDirNames(List<String> appDirNames) {
		this.appDirNames = appDirNames;
	}
	public List<String> getDependents() {
		return dependents;
	}
	public void setDependents(List<String> dependents) {
		this.dependents = dependents;
	}
	public String getRootModule() {
		return rootModule;
	}
	public void setRootModule(String rootModule) {
		this.rootModule = rootModule;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	@Override
	public String toString() {
		return "DeleteProjectInfo [appDirNames=" + appDirNames
				+ ", dependents=" + dependents + ", rootModule=" + rootModule
				+ ", actionType=" + actionType + "]";
	}
}
