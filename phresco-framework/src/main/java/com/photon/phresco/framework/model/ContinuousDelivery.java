package com.photon.phresco.framework.model;

import java.util.List;


public class ContinuousDelivery {

	private String name;
	private String envName;
	private List<CIJob> jobs;

	public ContinuousDelivery() {
		super();
	}

	public ContinuousDelivery(String name, List<CIJob> jobs) {
		super();
		this.setName(name);
		this.setJobs(jobs);	       
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setJobs(List<CIJob> jobs) {
		this.jobs = jobs;
	}

	public List<CIJob> getJobs() {
		return jobs;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getEnvName() {
		return envName;
	}

}


