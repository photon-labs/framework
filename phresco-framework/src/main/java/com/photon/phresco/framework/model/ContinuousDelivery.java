package com.photon.phresco.framework.model;

import java.util.List;


public class ContinuousDelivery {

	private String name;
	private String environment;
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

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getEnvironment() {
		return environment;
	}

}


