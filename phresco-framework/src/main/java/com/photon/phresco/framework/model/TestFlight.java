package com.photon.phresco.framework.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TestFlight {

	private String tokenPairName;
	private String apiToken;
	private String teamToken;
	
	public String getTokenPairName() {
		return tokenPairName;
	}
	public void setTokenPairName(String tokenPairName) {
		this.tokenPairName = tokenPairName;
	}
	public String getApiToken() {
		return apiToken;
	}
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	public String getTeamToken() {
		return teamToken;
	}
	public void setTeamToken(String teamToken) {
		this.teamToken = teamToken;
	}
	
	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append("tokenPairName", getTokenPairName())
	                .append("apiToken", getApiToken())
	                .append("teamToken", getTeamToken())
	                .toString();
	}
}
