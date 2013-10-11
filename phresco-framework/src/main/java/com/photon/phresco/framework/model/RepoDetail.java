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
	private List<RepoFileInfo> repoInfoFile;
	private String type;
	private boolean testCheckOut;
	private String testRepoUrl;
	private String testUserName;
	private String testPassword;
	private String testRevision;
	private String testRevisionVal;
	private String branch;
	private String passPhrase;
	private String stream;
	
	private boolean repoExist;

	public RepoDetail() {
		super();
	}
	
	public RepoDetail(String revision, String repoUrl, String userName, String password, String revisionVal, String commitMessage,
			List<String> commitableFiles, List<RepoFileInfo> repoInfoFile, String type, boolean testCheckOut, String testRepoUrl,
			String testUserName, String testPassword, String testRevision, boolean repoExist,String stream) {
		super();
		this.revision = revision;
		this.repoUrl = repoUrl;
		this.userName = userName;
		this.password = password;
		this.revisionVal = revisionVal;
		this.commitMessage = commitMessage;
		this.commitableFiles = commitableFiles;
		this.repoInfoFile = repoInfoFile;
		this.type = type;
		this.testCheckOut = testCheckOut;
		this.testRepoUrl = testRepoUrl;
		this.testUserName = testUserName;
		this.testPassword = testPassword;
		this.testRevision = testRevision;
		this.repoExist = repoExist;
		this.stream = stream;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<RepoFileInfo> getRepoInfoFile() {
		return repoInfoFile;
	}

	public void setRepoInfoFile(List<RepoFileInfo> repoInfoFile) {
		this.repoInfoFile = repoInfoFile;
	}

	public boolean isRepoExist() {
		return repoExist;
	}

	public void setRepoExist(boolean repoExist) {
		this.repoExist = repoExist;
	}

	public boolean isTestCheckOut() {
		return testCheckOut;
	}

	public void setTestCheckOut(boolean testCheckOut) {
		this.testCheckOut = testCheckOut;
	}

	public String getTestRepoUrl() {
		return testRepoUrl;
	}

	public void setTestRepoUrl(String testRepoUrl) {
		this.testRepoUrl = testRepoUrl;
	}

	public String getTestUserName() {
		return testUserName;
	}

	public void setTestUserName(String testUserName) {
		this.testUserName = testUserName;
	}

	public String getTestPassword() {
		return testPassword;
	}

	public void setTestPassword(String testPassword) {
		this.testPassword = testPassword;
	}

	public String getTestRevision() {
		return testRevision;
	}

	public void setTestRevision(String testRevision) {
		this.testRevision = testRevision;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranch() {
		return branch;
	}

	public void setTestRevisionVal(String testRevisionVal) {
		this.testRevisionVal = testRevisionVal;
	}

	public String getTestRevisionVal() {
		return testRevisionVal;
	}

	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}

	public String getPassPhrase() {
		return passPhrase;
	}
	
	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getStream() {
		return stream;
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
	                .append("repoInfoFile", getRepoInfoFile())
	                .append("repoExist", isRepoExist())
	                .toString();
	}
}
