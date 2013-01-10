package com.photon.phresco.framework.model;


public class DbContextUrls {
	private String name;
	private String queryType;
	private String query;
	
	public DbContextUrls() {
	}

	public DbContextUrls(String name, String queryType, String query) {
		super();
		this.name = name;
		this.queryType = queryType;
		this.query = query;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
