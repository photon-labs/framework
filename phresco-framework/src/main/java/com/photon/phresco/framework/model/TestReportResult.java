package com.photon.phresco.framework.model;

import java.util.Map;

public class TestReportResult {
	
	private Map<String, String> testReports;
	private int totalTestResults;
	private int totalTestSuccess;
	private int totalTestFailure;
	private int totalTestError;
	
	public void setTestReports(Map<String, String> testReports) {
		this.testReports = testReports;
	}
	
	public Map<String, String> getTestReports() {
		return testReports;
	}
	
	public void setTotalTestResults(int totalTestResults) {
		this.totalTestResults = totalTestResults;
	}
	
	public int getTotalTestResults() {
		return totalTestResults;
	}
	
	public void setTotalTestSuccess(int totalTestSuccess) {
		this.totalTestSuccess = totalTestSuccess;
	}
	
	public int getTotalTestSuccess() {
		return totalTestSuccess;
	}
	
	public void setTotalTestFailure(int totalTestFailure) {
		this.totalTestFailure = totalTestFailure;
	}
	
	public int getTotalTestFailure() {
		return totalTestFailure;
	}
	
	public void setTotalTestError(int totalTestError) {
		this.totalTestError = totalTestError;
	}
	
	public int getTotalTestError() {
		return totalTestError;
	}
}
