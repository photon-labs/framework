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

import java.io.*;
import java.util.*;

import com.photon.phresco.commons.model.*;
import com.photon.phresco.exception.*;
import com.photon.phresco.framework.model.*;


/**
 * Interface for communicating with Jenkins (CI)
 */
public interface CIManager {
	
	/**
	 * setup jenkins in phresco
	 * @param projectInfo
	 * @param buildArgCmds
	 * @param workingDirectory
	 * @return
	 * @throws PhrescoException
	 */
	BufferedReader setup(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException;
	
	/**
	 * start jenkins in phresco
	 * @param projectInfo
	 * @param buildArgCmds
	 * @param workingDirectory
	 * @return
	 * @throws PhrescoException
	 */
	BufferedReader start(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException;
	
	/**
	 * stop jenkins in phresco
	 * @param projectInfo
	 * @param buildArgCmds
	 * @param workingDirectory
	 * @return
	 * @throws PhrescoException
	 */
	BufferedReader stop(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException;
	
	/**
	 * Returns particular job info
	 * @param appInfo
	 * @param jobName
	 * @return
	 * @throws PhrescoException
	 */
	CIJob getJob(ApplicationInfo appInfo, String jobName) throws PhrescoException;
	
	/**
	 * Returns the list of user created jobs
	 * @param appInfo
	 * @return
	 * @throws PhrescoException
	 */
	List<CIJob> getJobs(ApplicationInfo appInfo) throws PhrescoException;
	
	/**
	 * Creates jobs in Jenkins.
	 * @param appInfo
	 * @param job
	 * @throws PhrescoException
	 */
	void createJob(ApplicationInfo appInfo, CIJob job) throws PhrescoException;

	/**
	 * Updates jobs in Jenkins.
	 * @param appInfo
	 * @param job
	 * @throws PhrescoException
	 */
	void updateJob(ApplicationInfo appInfo, CIJob job) throws PhrescoException;

	/**
	 * triggers build on jobs
	 * @param appInfo
	 * @param jobs
	 * @return
	 * @throws PhrescoException
	 */
	CIJobStatus buildJobs(ApplicationInfo appInfo, List<String> jobs) throws PhrescoException;
	
	/**
	 * Delete job. If build is null, job will be deleted
	 * @param appInfo
	 * @param jobs
	 * @return
	 * @throws PhrescoException
	 */
	CIJobStatus deleteJobs(ApplicationInfo appInfo, List<String> jobs) throws PhrescoException;
	
	/**
	 * Delete job. If build is null, job will be deleted
	 * @param appInfo
	 * @param builds
	 * @return
	 * @throws PhrescoException
	 */
	CIJobStatus deleteBuilds(ApplicationInfo appInfo, Map<String, List<String>> builds) throws PhrescoException;

	/**
	 * Gets builds for a job from the jenkins.
	 * @param job
	 * @return
	 * @throws PhrescoException
	 */
	List<CIBuild> getBuilds(CIJob job) throws PhrescoException;

	/**
	 * Checks whether job is in progress
	 * @param ciJob
	 * @return
	 * @throws PhrescoException
	 */
	boolean isJobCreatingBuild(CIJob ciJob) throws PhrescoException;

	/**
	 * Get total number of builds
	 * @param appInfo
	 * @return
	 * @throws PhrescoException
	 */
	int getTotalBuilds(ApplicationInfo appInfo) throws PhrescoException;
	
	/**
	 * Configure email configuration on jenkins
	 * @param jenkinsPort
	 * @param senderEmailId
	 * @param senderEmailPassword
	 * @return
	 * @throws PhrescoException
	 */
	void saveMailConfiguration(String jenkinsPort, String senderEmailId, String senderEmailPassword) throws PhrescoException;
	
	/**
	 * Get Configured email configuration
	 * @param jenkinsPort
	 * @param senderEmailId
	 * @param senderEmailPassword
	 * @return
	 * @throws PhrescoException
	 */
	String getMailConfiguration(String tag) throws PhrescoException;
}