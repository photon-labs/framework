package com.photon.phresco.framework.commons;

import java.util.List;

import com.photon.phresco.framework.model.TestSuiteResult;

public class SureFireReport {
	//custom report
	private List<AllTestSuite> allTestSuites;
	//detail report
	private List<TestSuiteResult> testSuites;
	public List<AllTestSuite> getAllTestSuites() {
		return allTestSuites;
	}
	public void setAllTestSuites(List<AllTestSuite> allTestSuites) {
		this.allTestSuites = allTestSuites;
	}
	public List<TestSuiteResult> getTestSuites() {
		return testSuites;
	}
	public void setTestSuites(List<TestSuiteResult> testSuites) {
		this.testSuites = testSuites;
	}
}
