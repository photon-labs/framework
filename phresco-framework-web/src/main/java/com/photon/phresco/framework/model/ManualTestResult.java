package com.photon.phresco.framework.model;

import java.util.List;

public class ManualTestResult {
	
	private List<TestSuite> testSuites;
	private float totalSuccess;
	private float totalFailure;
	private float totalNotApplicable;
	private float totalBlocked;
	private float totalNotExecuted;
	private float total;
	private float totalTestCoverage;
	
	public List<TestSuite> getTestSuites() {
		return testSuites;
	}
	
	public void setTestSuites(List<TestSuite> testSuites) {
		this.testSuites = testSuites;
	}
	
	public float getTotalSuccess() {
		return totalSuccess;
	}
	
	public void setTotalSuccess(float totalSuccess) {
		this.totalSuccess = totalSuccess;
	}
	
	public float getTotalFailure() {
		return totalFailure;
	}
	
	public void setTotalFailure(float totalFailure) {
		this.totalFailure = totalFailure;
	}
	
	public float getTotalNotApplicable() {
		return totalNotApplicable;
	}
	
	public void setTotalNotApplicable(float totalNotApplicable) {
		this.totalNotApplicable = totalNotApplicable;
	}
	
	public float getTotalBlocked() {
		return totalBlocked;
	}
	
	public void setTotalBlocked(float totalBlocked) {
		this.totalBlocked = totalBlocked;
	}
	
	public float getTotalNotExecuted() {
		return totalNotExecuted;
	}
	
	public void setTotalNotExecuted(float totalNotExecuted) {
		this.totalNotExecuted = totalNotExecuted;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public float getTotalTestCoverage() {
		return totalTestCoverage;
	}
	
	public void setTotalTestCoverage(float totalTestCoverage) {
		this.totalTestCoverage = totalTestCoverage;
	}
	
}
