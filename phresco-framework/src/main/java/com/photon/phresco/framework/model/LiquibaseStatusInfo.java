package com.photon.phresco.framework.model;


public class LiquibaseStatusInfo {
	
	private String changeLogPathForStatus;
	private Boolean verbose;		
	
	public Boolean getVerbose() {
		return verbose;
	}
	public void setVerbose(Boolean verbose) {
		this.verbose = verbose;
	}
	public String getChangeLogPathForStatus() {
		return changeLogPathForStatus;
	}
	public void setChangeLogPathForStatus(String changeLogPathForStatus) {
		this.changeLogPathForStatus = changeLogPathForStatus;
	}

}
