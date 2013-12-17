package com.photon.phresco.framework.model;

import java.util.List;

public class GlobalSettings {

	List<RepoDetail> repoDetails;
	List<TestFlight> testFlight;
	
	public List<RepoDetail> getRepoDetails() {
		return repoDetails;
	}
	public void setRepoDetails(List<RepoDetail> repoDetails) {
		this.repoDetails = repoDetails;
	}
	public List<TestFlight> getTestFlight() {
		return testFlight;
	}
	public void setTestFlight(List<TestFlight> testFlight) {
		this.testFlight = testFlight;
	}
}
