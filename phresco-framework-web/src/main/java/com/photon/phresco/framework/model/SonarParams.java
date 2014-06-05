package com.photon.phresco.framework.model;

public class SonarParams {
	private String sonarUrl;
	private String sonarJdbcUrl;
	private String username;
	private String password;
	private String remoteSonar;
	public SonarParams() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SonarParams(String sonarUrl, String sonarJdbcUrl, String username,
			String password, String remoteSonar) {
		super();
		this.sonarUrl = sonarUrl;
		this.sonarJdbcUrl = sonarJdbcUrl;
		this.username = username;
		this.password = password;
		this.remoteSonar = remoteSonar;
	}
	public String getSonarUrl() {
		return sonarUrl;
	}
	public void setSonarUrl(String sonarUrl) {
		this.sonarUrl = sonarUrl;
	}
	public String getSonarJdbcUrl() {
		return sonarJdbcUrl;
	}
	public void setSonarJdbcUrl(String sonarJdbcUrl) {
		this.sonarJdbcUrl = sonarJdbcUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemoteSonar() {
		return remoteSonar;
	}
	public void setRemoteSonar(String remoteSonar) {
		this.remoteSonar = remoteSonar;
	}
	
	

}
