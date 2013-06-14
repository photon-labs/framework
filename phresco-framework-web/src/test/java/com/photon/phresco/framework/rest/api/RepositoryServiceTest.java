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
