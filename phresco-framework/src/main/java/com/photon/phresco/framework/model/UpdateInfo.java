package com.photon.phresco.framework.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String currentVersion;
	private String message;
	private String latestVersion;
	private boolean updateAvaillable;
	
	public UpdateInfo() {
		super();
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	public boolean isUpdateAvaillable() {
		return updateAvaillable;
	}

	public void setUpdateAvaillable(boolean updateAvaillable) {
		this.updateAvaillable = updateAvaillable;
	}	
	
	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("currentVersion", getCurrentVersion())
	                .append("message", getMessage())
	                .append("latestVersion", getLatestVersion())
	                .append("updateAvaillable", isUpdateAvaillable())
	                .toString();
	}

}
