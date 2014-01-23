package com.photon.phresco.framework.model;

public class TestStepFailure {
	
	private String failureType;
	private String description;
	private boolean hasFailureImg;
	private String screenshotPath;
	
	public String getFailureType() {
		return failureType;
	}
	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isHasFailureImg() {
		return hasFailureImg;
	}
	public void setHasFailureImg(boolean hasFailureImg) {
		this.hasFailureImg = hasFailureImg;
	}
	public String getScreenshotPath() {
		return screenshotPath;
	}
	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}
}
