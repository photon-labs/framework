package com.photon.phresco.framework.model;

import java.util.List;

public class TestFunction {
	private String name;
	private String testClass;
	private List<TestStep> testSteps;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTestClass() {
		return testClass;
	}
	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}
	public void setTestSteps(List<TestStep> testSteps) {
		this.testSteps = testSteps;
	}
	public List<TestStep> getTestSteps() {
		return testSteps;
	}
}
