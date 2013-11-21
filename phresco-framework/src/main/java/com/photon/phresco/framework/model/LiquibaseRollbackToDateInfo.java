package com.photon.phresco.framework.model;

public class LiquibaseRollbackToDateInfo {
	private String changeLogPathForRollbackDate;
	private String rollbackDate;
	
	public String getRollbackDate() {
		return rollbackDate;
	}
	public void setRollbackDate(String rollbackDate) {
		this.rollbackDate = rollbackDate;
	}
	public String getChangeLogPathForRollbackDate() {
		return changeLogPathForRollbackDate;
	}
	public void setChangeLogPathForRollbackDate(String changeLogPathForRollbackDate) {
		this.changeLogPathForRollbackDate = changeLogPathForRollbackDate;
	}
}
