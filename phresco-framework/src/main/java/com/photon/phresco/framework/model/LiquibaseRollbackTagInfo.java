package com.photon.phresco.framework.model;

public class LiquibaseRollbackTagInfo {
	private String changeLogPathForRollbackTag;
	private String tag;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getChangeLogPathForRollbackTag() {
		return changeLogPathForRollbackTag;
	}
	public void setChangeLogPathForRollbackTag(String changeLogPathForRollbackTag) {
		this.changeLogPathForRollbackTag = changeLogPathForRollbackTag;
	}
}
