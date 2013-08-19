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
	 * Creates jobs in Jenkins.
	 * @param appInfo
	 * @param job
	 * @throws PhrescoException
	 */
	boolean createJob(CIJob job) throws PhrescoException;

	/**
	 * Updates jobs in Jenkins.
	 * @param appInfo
	 * @param job
	 * @throws PhrescoException
	 */
	boolean updateJob(CIJob job) throws PhrescoException;

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
	 * @param jenkinsPort
	 * @param senderEmailId
	 * @param senderEmailPassword
	 * @throws PhrescoException
	 */
	void saveMailConfiguration(String jenkinsPort, String senderEmailId, String senderEmailPassword) throws PhrescoException;

	/**
	 * @param confluenceUrl
	 * @param confluenceUserName
	 * @param confluencePassword
	 * @throws PhrescoException
	 */
	void saveConfluenceConfiguration(String confluenceUrl, String confluenceUserName, String confluencePassword) throws PhrescoException;

	/**
	 * @param tag
	 * @return
	 * @throws PhrescoException
	 */
	String getMailConfiguration(String tag) throws PhrescoException;

	/**
	 * @return
	 * @throws PhrescoException
	 */
	JSONArray getConfluenceConfiguration() throws PhrescoException;

	/**
	 * @throws PhrescoException
	 */
	public void clearConfluenceSitesNodes() throws PhrescoException;

	/**
	 * @return
	 * @throws PhrescoException
	 */
	public List<String> getConfluenceSites() throws PhrescoException;

	/**
	 * @param password
	 * @return
	 * @throws PhrescoException
	 */
	public String encyPassword(String password) throws PhrescoException;

	/**
	 * @param encryptedText
	 * @return
	 * @throws PhrescoException
	 */
	public String decyPassword(String encryptedText) throws PhrescoException;

	/**
	 * @param jobTemplateName
	 * @return
	 * @throws PhrescoException
	 */
	boolean isJobTemplateNameExists(String jobTemplateName) throws PhrescoException;

	/**
	 * @param ciJobTemplates
	 * @param createNewFile
	 * @return
	 * @throws PhrescoException
	 */
	boolean createJobTemplates(List<CIJobTemplate> ciJobTemplates, boolean createNewFile) throws PhrescoException;

	/**
	 * @return
	 * @throws PhrescoException
	 */
	List<CIJobTemplate> getJobTemplates() throws PhrescoException;

	/**
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	List<CIJobTemplate> getJobTemplatesByAppId(String appId) throws PhrescoException;

	/**
	 * @param projId
	 * @return
	 * @throws PhrescoException
	 */
	List<CIJobTemplate> getJobTemplatesByProjId(String projId) throws PhrescoException;

	/**
	 * @param jobTemplateName
	 * @return
	 * @throws PhrescoException
	 */
	CIJobTemplate getJobTemplateByName(String jobTemplateName) throws PhrescoException;

	/**
	 * @param ciJobTemplate
	 * @param oldName
	 * @param projId
	 * @return
	 * @throws PhrescoException
	 */
	boolean updateJobTemplate(CIJobTemplate ciJobTemplate, String oldName, String projId) throws PhrescoException;

	/**
	 * @param ciJobTemplates
	 * @return
	 * @throws PhrescoException
	 */
	boolean deleteJobTemplates(List<CIJobTemplate> ciJobTemplates) throws PhrescoException;

	/**
	 * @param jobTemplateName
	 * @param projId
	 * @return
	 * @throws PhrescoException
	 */
	boolean deleteJobTemplate(String jobTemplateName, String projId) throws PhrescoException;

	/**
	 * @param continuousDelivery
	 * @param jobs
	 * @param projId
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	public boolean createJsonJobs(ContinuousDelivery continuousDelivery, List<CIJob> jobs, String projId, String appDir) throws PhrescoException;

	/**
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	public List<ProjectDelivery> getCiJobInfo(String appDir) throws PhrescoException;

	/**
	 * @param appDir
	 * @param selectedJobs
	 * @param projectId
	 * @param name
	 * @throws PhrescoException
	 */
	public void deleteJsonJobs(String appDir, List<CIJob> selectedJobs, String projectId, String name) throws PhrescoException;

	/**
	 * @param continuousDeliveryName
	 * @param projId
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	public boolean clearContinuousDelivery(String continuousDeliveryName, String projId, String appDir) throws PhrescoException;

	/**
	 * @param projectId
	 * @param continuousDelivery
	 * @param appDirName
	 * @return
	 * @throws PhrescoException
	 */
	public List<CIJob> getOldJobs(String projectId, ContinuousDelivery continuousDelivery, String appDirName) throws PhrescoException;

	/**
	 * @param ciJob
	 * @param buildNumber
	 * @return
	 * @throws PhrescoException
	 */
	public CIJobStatus deleteBuilds(CIJob ciJob,  String buildNumber) throws PhrescoException;

	/**
	 * @param submitUrl
	 * @param svnUrl
	 * @param username
	 * @param password
	 * @return
	 * @throws PhrescoException
	 */
	public boolean setSVNCredentials(String submitUrl, String svnUrl, String username, String password) throws PhrescoException;

	/**
	 * @param ciJob
	 * @return
	 * @throws PhrescoException
	 */
	public String getJobStatus(CIJob ciJob) throws PhrescoException;

	/**
	 * @param jobName
	 * @param projectId
	 * @param projectDeliveries
	 * @param continuousName
	 * @return
	 */
	public CIJob getJob(String jobName, String projectId, List<ProjectDelivery> projectDeliveries, String continuousName);
	
	/**
	 * @param jenkinsUrl
	 * @param submitUrl
	 * @param confluenceObj
	 * @param emailAddress
	 * @param emailPassword
	 * @return
	 * @throws PhrescoException
	 */
	public boolean setGlobalConfiguration(String jenkinsUrl, String submitUrl, org.json.JSONArray confluenceObj, String emailAddress, String emailPassword) throws PhrescoException;
}