package com.photon.phresco.framework.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private RepoDetail srcRepoDetail;
	private RepoDetail phrescoRepoDetail;
	private RepoDetail testRepoDetail;
	private boolean splitPhresco;
	private boolean splitTest;

	public RepoDetail getSrcRepoDetail() {
		return srcRepoDetail;
	}
	
	public void setSrcRepoDetail(RepoDetail srcRepoDetail) {
		this.srcRepoDetail = srcRepoDetail;
	}
	
	public RepoDetail getPhrescoRepoDetail() {
		return phrescoRepoDetail;
	}
	
	public void setPhrescoRepoDetail(RepoDetail phrescoRepoDetail) {
		this.phrescoRepoDetail = phrescoRepoDetail;
	}
	
	public RepoDetail getTestRepoDetail() {
		return testRepoDetail;
	}
	
	public void setTestRepoDetail(RepoDetail testRepoDetail) {
		this.testRepoDetail = testRepoDetail;
	}
	
	public boolean isSplitPhresco() {
		return splitPhresco;
	}
	
	public void setSplitPhresco(boolean splitPhresco) {
		this.splitPhresco = splitPhresco;
	}
	
	public boolean isSplitTest() {
		return splitTest;
	}
	
	public void setSplitTest(boolean splitTest) {
		this.splitTest = splitTest;
	}

	public String toString() {
		return new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE)
		.append("srcRepoDetail", getSrcRepoDetail())
		.append("phrescoRepoDetail", getPhrescoRepoDetail())
		.append("testRepoDetail", getTestRepoDetail())
		.append("splitPhresco", isSplitPhresco())
		.append("splitTest", isSplitTest())
		.toString();
	}
}