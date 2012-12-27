package com.photon.phresco.framework.model;


public class DbContextUrls {
	private String dbPerName;
	private String queryType;
	private String query;
	
	public DbContextUrls() {
	}

	public DbContextUrls(String dbPerName, String queryType, String query) {
		super();
		this.dbPerName = dbPerName;
		this.queryType = queryType;
		this.query = query;
	}

	public String getDbPerName() {
		return dbPerName;
	}

	public void setDbPerName(String dbPerName) {
		this.dbPerName = dbPerName;
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

}
