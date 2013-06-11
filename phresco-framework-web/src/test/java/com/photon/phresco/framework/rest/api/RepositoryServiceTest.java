package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.photon.phresco.framework.model.RepoDetail;

import junit.framework.Assert;

public class RepositoryServiceTest  {
	
	RepositoryService repositoryservice = new RepositoryService();


	@Test
	public void  getpopupvaluesForCommit() {
		String appDirName = "wordpress-WordPress";
		String action = "commit";
		String userId = "admin";
		Response fetchPopUpValues = repositoryservice.fetchPopUpValues(appDirName, action, userId);
		ResponseInfo<RepoDetail> responseInfo = (ResponseInfo<RepoDetail>) fetchPopUpValues.getEntity();
		RepoDetail repodetail = (RepoDetail) responseInfo.getData();
		Assert.assertEquals("Type Of Project", "svn", repodetail.getType());
	}
	
	@Test
	public void  getpopupvaluesForCommitableFiles() {
		String appDirName = "wordpress-WordPress";
		String action = "commit";
		String userId = "admin";
		Response fetchPopUpValues = repositoryservice.fetchPopUpValues(appDirName, action, userId);
		ResponseInfo<RepoDetail> responseInfo = (ResponseInfo<RepoDetail>) fetchPopUpValues.getEntity();
		RepoDetail repodetail = (RepoDetail) responseInfo.getData();
		Assert.assertEquals("List of Files changed", 4, repodetail.getRepoInfoFile().size());
	}
	
	@Test
	public void  getpopupvaluesForUpdate() {
		String appDirName = "wordpress-WordPress";
		String action = "commit";
		String userId = "admin";
		Response fetchPopUpValues = repositoryservice.fetchPopUpValues(appDirName, action, userId);
		ResponseInfo<RepoDetail> responseInfo = (ResponseInfo<RepoDetail>) fetchPopUpValues.getEntity();
		RepoDetail repodetail = (RepoDetail) responseInfo.getData();
		Assert.assertEquals("Repository url of the project", "https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/wordpress-WordPress", repodetail.getRepoUrl());
	}
}
