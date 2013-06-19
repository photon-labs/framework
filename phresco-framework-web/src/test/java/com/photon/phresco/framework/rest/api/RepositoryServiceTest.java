/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.rest.api;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
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
	
	@Test
	public void  addProjectToRepo() {
		String appDirName = "sampletest-Java WebService";
		String userId = "santhosh_ja";
		String projectId = "cb3acb9f-5e9a-406c-94a8-8f911bbc743d";
		String appId = "773bc891-c5c6-422b-a0c5-bfad3d0bd86c";
		RepoDetail repodetail = new RepoDetail();
		repodetail.setUserName("admin");
		repodetail.setPassword("manage");
		repodetail.setType("svn");
		repodetail.setCommitMessage("[artf672433]testcommit");
		repodetail.setRepoUrl("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/");
		
		Response addProjectToRepo = repositoryservice.addProjectToRepo(appDirName, repodetail, userId, projectId, appId);
		Assert.assertEquals(200,addProjectToRepo.getStatus());
	}
	
	@Test
	public void  commitProjectToRepo() {
		String appDirName = "sampletest-Java WebService";
		RepoDetail repodetail = new RepoDetail();
		repodetail.setUserName("admin");
		repodetail.setPassword("manage");
		repodetail.setType("svn");
		repodetail.setCommitMessage("[artf672433]testcommit");
		repodetail.setRepoUrl("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/sampletest-Java WebService");
		
		repodetail.setCommitableFiles(Arrays.asList("C:\\Documents and Settings\\saravanan_va\\workspace\\projects\\sampletest-Java WebService\\pom.xml","C:\\Documents and Settings\\saravanan_va\\workspace\\projects\\sampletest-Java WebService\\docs\\README.txt"));
		Response commitImportedProject = repositoryservice.commitImportedProject(repodetail, appDirName);
		Assert.assertEquals(200,commitImportedProject.getStatus());
	}
	
	@Test
	public void  updateProjectToRepo() {
		String appDirName = "sampletest-Java WebService";
		RepoDetail repodetail = new RepoDetail();
		repodetail.setUserName("admin");
		repodetail.setPassword("manage");
		repodetail.setRevision("head");
		repodetail.setType("svn");
		repodetail.setRepoUrl("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/sampletest-Java WebService");
		Response updateImportedApplicaion = repositoryservice.updateImportedApplicaion(appDirName, repodetail);
		Assert.assertEquals(200,updateImportedApplicaion.getStatus());
	}
	
	@Test
	public void  importProjectToRepo() {
		RepoDetail repodetail = new RepoDetail();
		repodetail.setUserName("admin");
		repodetail.setPassword("manage");
		repodetail.setRevision("head");
		repodetail.setType("svn");
		repodetail.setRepoUrl("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/test502/");
		Response importApplication = repositoryservice.importApplication(repodetail);
		Assert.assertEquals(200,importApplication.getStatus());
	}
	

	@Test
	public void  fetchLogMessages() throws PhrescoException {
		RepoDetail repodetail = new RepoDetail();
		repodetail.setUserName("admin");
		repodetail.setPassword("manage");
		repodetail.setRevision("head");
		repodetail.setType("svn");
		repodetail.setRepoUrl("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/");
		Response fetchLogMessages = repositoryservice.fetchLogMessages(repodetail);
		ResponseInfo<List<String>> responseInfo = (ResponseInfo<List<String>>) fetchLogMessages.getEntity();
		Assert.assertEquals(5,responseInfo.getData().size());
	}
}
