package com.photon.phresco.framework.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String revision;
	private String repoUrl;
	private String userName;
	private String password;
	private String revisionVal;
	private String commitMessage;
	private List<String>commitableFiles;
	private String type;
	
	public RepoDetail() {
		super();
	}

	public RepoDetail(String revision, String repoUrl, String userName, String password, String revisionVal,
			String commitMessage, List<String> commitableFiles, String type) {
		super();
		this.revision = revision;
		this.repoUrl = repoUrl;
		this.userName = userName;
		this.password = password;
		this.revisionVal = revisionVal;
		this.commitMessage = commitMessage;
		this.commitableFiles = commitableFiles;
		this.type = type;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRevisionVal() {
		return revisionVal;
	}

	public void setRevisionVal(String revisionVal) {
		this.revisionVal = revisionVal;
	}

	public String getCommitMessage() {
		return commitMessage;
	}

	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}

	public List<String> getCommitableFiles() {
		return commitableFiles;
	}

	public void setCommitableFiles(List<String> commitableFiles) {
		this.commitableFiles = commitableFiles;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String toString() {
		 return new ToStringBuilder(this,
	                ToStringStyle.DEFAULT_STYLE)
	                .append(super.toString())
	                .append("revision", getRevision())
	                .append("repoUrl", getRepoUrl())
	                .append("userName", getUserName())
	                .append("password", getPassword())
	                .append("revisionVal", getRevisionVal())
	                .append("commitMessage", getCommitMessage())
	                .append("commitableFiles", getCommitableFiles())
	                .append("type", getType())
	                .toString();
	}
}
