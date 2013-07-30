package com.photon.phresco.framework.model;

import java.util.List;

public class PerformanceUrls {
	private List<ContextUrls> contextUrls;
	private List<DbContextUrls> dbContextUrls;
	
	public List<ContextUrls> getContextUrls() {
		return contextUrls;
	}
	
	public void setContextUrls(List<ContextUrls> contextUrls) {
		this.contextUrls = contextUrls;
	}
	
	public List<DbContextUrls> getDbContextUrls() {
		return dbContextUrls;
	}
	
	public void setDbContextUrls(List<DbContextUrls> dbContextUrls) {
		this.dbContextUrls = dbContextUrls;
	}
}
