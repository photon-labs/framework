/**
 * Phresco Framework
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
package com.photon.phresco.framework.api;

import java.io.File;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.framework.model.RepoInfo;
import com.photon.phresco.service.client.api.ServiceManager;

public interface SCMManager {

	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @throws Exception 
	 
	 */
	void importProject(ApplicationInfo applicationInfo, RepoInfo repodetail, String displayName, String uniqueKey) throws Exception ;
	
	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @throws Exception 
	
	 */
	public boolean updateProject(RepoDetail repodetail, File workingDir) throws Exception ;
	
	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @param dir
	 * @param commit message
	 * @throws Exception 
	 
	 */
	public boolean addToRepo(RepoInfo repoInfo, ApplicationInfo appInfo) throws Exception ;
	
	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @param dir
	 * @param commit message
	 * @throws Exception 
	 
	 */
	public boolean commitToRepo(RepoDetail repodetail, File dir) throws Exception;
	
	/**
	 * @param applicationInfo
	 * @param repoInfo
	 * @throws Exception
	 */
	public void importTest(ApplicationInfo applicationInfo, RepoInfo repoInfo) throws Exception;
	
	/**
	 * @param applicationInfo
	 * @param repoInfo
	 * @throws Exception
	 */
	public void importPhresco(ApplicationInfo applicationInfo, RepoInfo repoInfo) throws Exception;
	
	/**
	 * @param repoInfo
	 * @param appInfo
	 * @param projInfo
	 * @param serviceManager
	 * @throws PhrescoException
	 */
	public void updatePoms(RepoInfo repoInfo, ProjectInfo projInfo, ServiceManager serviceManager) throws PhrescoException;
}
