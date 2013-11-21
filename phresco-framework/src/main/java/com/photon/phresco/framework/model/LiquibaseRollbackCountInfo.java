package com.photon.phresco.framework.model;

public class LiquibaseRollbackCountInfo {
	private String changeLogPathForRollbackCount;
	private String rollbackCountValue;
	
	public String getRollbackCountValue() {
		return rollbackCountValue;
	}
	public void setRollbackCountValue(String rollbackCountValue) {
		this.rollbackCountValue = rollbackCountValue;
	}
	public String getChangeLogPathForRollbackCount() {
		return changeLogPathForRollbackCount;
	}
	public void setChangeLogPathForRollbackCount(
			String changeLogPathForRollbackCount) {
		this.changeLogPathForRollbackCount = changeLogPathForRollbackCount;
	}
	
}
