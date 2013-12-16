package com.photon.phresco.framework.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestFlight {

	private String tokenPairName;
	private String apiToken;
	private String teamToken;
	private String notes;
	private String notify;
	private String distributionLists;
	private String filePath;
	
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
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	public String getDistributionLists() {
		return distributionLists;
	}
	public void setDistributionLists(String distributionLists) {
		this.distributionLists = distributionLists;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
