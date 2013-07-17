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

import org.codehaus.jettison.json.JSONArray;

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
	BufferedInputStream setup(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException;
	
	/**
	 * start jenkins in phresco
	 * @param projectInfo
	 * @param buildArgCmds
	 * @param workingDirectory
	 * @return
	 * @throws PhrescoException
	 */
	BufferedInputStream start(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException;
	
	/**
	 * stop jenkins in phresco
	 * @param projectInfo
	 * @param buildArgCmds
	 * @param workingDirectory
	 * @return
	 * @throws PhrescoException
	 */
	BufferedInputStream stop(ProjectInfo projectInfo, ActionType action, List<String> buildArgCmds, String workingDirectory) throws PhrescoException;
	
	/**
	 * Returns particular job info
	 * @param appInfo
	 * @param jobName
	 * @return
	 * @throws PhrescoException
	 */
//	CIJob getJob(ApplicationInfo appInfo, String jobName) throws PhrescoException;
	
	/**
	 * Returns the list of user created jobs
	 * @param appInfo
	 * @return
	 * @throws PhrescoException
	 */
//	List<CIJob> getJobs(ApplicationInfo appInfo) throws PhrescoException;
	
	/**
	 * Creates jobs in Jenkins.
	 * @param appInfo
	 * @param job
	 * @throws PhrescoException
	 */
	boolean createJob(ApplicationInfo appInfo, CIJob job) throws PhrescoException;

	/**
	 * Updates jobs in Jenkins.
	 * @param appInfo
	 * @param job
	 * @throws PhrescoException
	 */
	boolean updateJob(ApplicationInfo appInfo, CIJob job) throws PhrescoException;

	/**
	 * triggers build on jobs
	 * @param appInfo
	 * @param jobs
	 * @return
	 * @throws PhrescoException
	 */
		CIJobStatus generateBuild(CIJob ciJob) throws PhrescoException;	
	/**
	 * Delete job. If build is null, job will be deleted
	 * @param appInfo
	 * @param jobs
	 * @return
	 * @throws PhrescoException
	 */
	CIJobStatus deleteJobs(String appDir,List<CIJob> ciJobs, String projectId, String continuousName) throws PhrescoException;
	
	/**
	 * Delete job. If build is null, job will be deleted
	 * @param appInfo
	 * @param builds
	 * @return
	 * @throws PhrescoException
	 */
//	CIJobStatus deleteBuilds(ApplicationInfo appInfo, Map<String, List<String>> builds) throws PhrescoException;

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
//	int getTotalBuilds(ApplicationInfo appInfo) throws PhrescoException;
	
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
	 * Configure confluence configuration on jenkins
	 * @param confluenceUrl
	 * @param confluenceUserName
	 * @param confluencePassword
	 * @return
	 * @throws PhrescoException
	 */
	void saveConfluenceConfiguration(String confluenceUrl, String confluenceUserName, String confluencePassword) throws PhrescoException;
	
	/**
	 * Get Configured email configuration
	 * @param jenkinsPort
	 * @param senderEmailId
	 * @param senderEmailPassword
	 * @return
	 * @throws PhrescoException
	 */
	String getMailConfiguration(String tag) throws PhrescoException;
	
	/**
	 * Get Configured confluence configuration
	 * @return JSONArray
	 * @throws PhrescoException
	 */
	JSONArray getConfluenceConfiguration() throws PhrescoException;
	
	/**
	 * clear confluence configuration nodes
	 * @return 
	 * @throws PhrescoException
	 */
	public void clearConfluenceSitesNodes() throws PhrescoException;
	
	/**
	 * get confluence sites 
	 * @return List<String>
	 * @throws PhrescoException
	 */
	public List<String> getConfluenceSites() throws PhrescoException;
	
	/**
	 * encrypt String 
	 * @return encrypted String
	 * @throws PhrescoException
	 */
	public String encyPassword(String password) throws PhrescoException;
	
	/**
	 * decrypt String 
	 * @return decrypted String
	 * @throws PhrescoException
	 */
	public String decyPassword(String encryptedText) throws PhrescoException;
	
	/**
	 * Checks if the jobTemplate name already used
	 * @param jobTemplateName
	 * @return boolean
	 * @throws PhrescoException
	 */
	boolean isJobTemplateNameExists(String jobTemplateName) throws PhrescoException;
	
	/**
	 * creates job template configuration
	 * @param ciJobTemplates
	 * @param createNewFile
	 * @return boolean
	 * @throws PhrescoException
	 */
	boolean createJobTemplates(List<CIJobTemplate> ciJobTemplates, boolean createNewFile) throws PhrescoException;
	
	/**
	 * Lists all job templates
	 * @return CIJobTemplate
	 * @throws PhrescoException
	 */
	List<CIJobTemplate> getJobTemplates() throws PhrescoException;
	
	/**
	 * Lists job template by appId
	 * @param appId
	 * @return CIJobTemplate
	 * @throws PhrescoException
	 */
	List<CIJobTemplate> getJobTemplatesByAppId(String appId) throws PhrescoException;
	
	/**
	 * Lists job template by projId
	 * @param projId
	 * @return CIJobTemplate
	 * @throws PhrescoException
	 */
	List<CIJobTemplate> getJobTemplatesByProjId(String projId) throws PhrescoException;
	
	/**
	 * Lists job template by job template name
	 * @param jobTemplateName
	 * @return CIJobTemplate
	 * @throws PhrescoException
	 */
	CIJobTemplate getJobTemplateByName(String jobTemplateName) throws PhrescoException;
	
	/**
	 * Update jobTemplate
	 * @param ciJobTemplate, oldName, projId
	 * @return boolean
	 * @throws PhrescoException
	 */
	boolean updateJobTemplate(CIJobTemplate ciJobTemplate, String oldName, String projId) throws PhrescoException;
	
	/**
	 * Deletes list of job templates
	 * @param CIJobTemplate ciJobTemplates
	 * @return boolean
	 * @throws PhrescoException
	 */
	boolean deleteJobTemplates(List<CIJobTemplate> ciJobTemplates) throws PhrescoException;
	
	/**
	 * Deletes job template by name
	 * @param jobTemplateName
	 * @return boolean
	 * @throws PhrescoException
	 */
	boolean deleteJobTemplate(String jobTemplateName, String projId) throws PhrescoException;
	
	public void createJsonJobs(ContinuousDelivery continuousDelivery, List<CIJob> jobs, String projId, String appDir) throws PhrescoException;
	
	public List<ProjectDelivery> getCiJobInfo(String appDir) throws PhrescoException;
	
	public void deleteJsonJobs(String appDir, List<CIJob> selectedJobs, String projectId, String name) throws PhrescoException;
	
	public void clearContinuousDelivery(String continuousDeliveryName, String projId, String appDir) throws PhrescoException;
	
	public CIJob setPreBuildCmds(CIJob job, ApplicationInfo appInfo) throws PhrescoException;
	
	public List<CIJob> getDeleteableJobs(String projectId, ContinuousDelivery continuousDelivery, String appDirName) throws PhrescoException;

	public List<CIJob> getJobs(String continuousName, String projectId, List<ProjectDelivery> ciJobInfo) throws PhrescoException;
	
	public CIJobStatus deleteBuilds(CIJob ciJob,  String buildNumber) throws PhrescoException;
	
	/**
	 * Sets svn credential values without restarting jenkins
	 * @param submitUrl
	 * @param svnUrl
	 * @param username
	 * @param password
	 * @return boolean
	 * @throws PhrescoException
	 */
	public boolean setSVNCredentials(String submitUrl, String svnUrl, String username, String password) throws PhrescoException;
}