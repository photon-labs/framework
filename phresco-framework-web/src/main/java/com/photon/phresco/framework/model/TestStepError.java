package com.photon.phresco.framework.model;

public class TestStepError {

	private String errorType;
	private String description;
	private boolean hasErrorImg;
	private String screenshotPath;
	
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isHasErrorImg() {
		return hasErrorImg;
	}
	public void setHasErrorImg(boolean hasErrorImg) {
		this.hasErrorImg = hasErrorImg;
	}
	public String getScreenshotPath() {
		return screenshotPath;
	}
	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}
}
