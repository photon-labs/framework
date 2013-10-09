/**
 * Phresco Framework
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
package com.photon.phresco.framework.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;

public interface ApplicationManager {

	/**
	 * This method updates the Application
	 * @param ProjectInfo
	 * @return Returns the updated ProjectInfo which contains the specific ApplicationInfo
	 * @throws PhrescoException
	 */
	ProjectInfo update(ProjectInfo projectInfo, ServiceManager serviceManager) throws PhrescoException;
	
	/**
	 * This method executes the maven command against the provided ActionType
	 * @param actionType
	 * @return Returns the maven output as Reader object
	 * @throws PhrescoException
	 */
	BufferedInputStream performAction(ProjectInfo projectInfo, ActionType action, List<String> mavenArgCommands, String workingDirectory) throws PhrescoException;
	
	/**
	 * This method configures the maven site report for the application
	 * @param appInfo
	 * @param reportOptions
	 * @throws PhrescoException
	 */
	void configureReport(ApplicationInfo appInfo, List<String> reportOptions) throws PhrescoException;
	
	/**
	 * This method read the buildInfos from the build.info json file.
	 * @param buildInfoFile
	 * @return
	 * @throws PhrescoException
	 */
	List<BuildInfo> getBuildInfos(File buildInfoFile) throws PhrescoException;
	
	/**
	 * @param customerId
	 * @param projectId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	ApplicationInfo getApplicationInfo(String customerId, String projectId, String appId) throws PhrescoException;

	BuildInfo getBuildInfo(int buildNumber, String buildInfoFileDirectory) throws PhrescoException;

	void deleteBuildInfos(String appDirName, int[] buildNumbers) throws PhrescoException;

}
