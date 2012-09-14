package com.photon.phresco.framework.commons;

import java.util.List;

import com.photon.phresco.framework.model.TestSuiteResult;

public class XmlReport {
	private String fileName;
	private List<TestSuiteResult> testSuites;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<TestSuiteResult> getTestSuites() {
		return testSuites;
	}
	public void setTestSuites(List<TestSuiteResult> testSuites) {
		this.testSuites = testSuites;
	}
}