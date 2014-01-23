package com.photon.phresco.framework.model;

public class TestStep {
	
	private String name;
	private String action;
	private String file;
	private String time;
	private TestStepFailure testStepFailure;
	private TestStepError testStepError;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public TestStepFailure getTestStepFailure() {
		return testStepFailure;
	}
	public void setTestStepFailure(TestStepFailure testStepFailure) {
		this.testStepFailure = testStepFailure;
	}
	public TestStepError getTestStepError() {
		return testStepError;
	}
	public void setTestStepError(TestStepError testStepError) {
		this.testStepError = testStepError;
	}
}
