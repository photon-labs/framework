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

import java.io.File;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.applications.DynamicParameterAction;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.commons.ApplicationsUtil;
import com.photon.phresco.framework.model.CIBuild;
import com.photon.phresco.framework.model.CIJob;
import com.photon.phresco.framework.model.CIJobStatus;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;

/**
 * The Class CIService.
 */
@Path("/ci")
public class CIService extends DynamicParameterAction implements FrameworkConstants, ServiceConstants {

	/**
	 * Gets the jobs.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the jobs
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/jobs")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CIJob> getJobs(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			List<CIJob> jobs = null;
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			jobs = ciManager.getJobs(applicationInfo);
			return jobs;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the builds.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @param name the name
	 * @return the builds
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/builds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CIBuild> getBuilds(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId,
			@QueryParam(REST_QUERY_NAME) String name) throws PhrescoException {
		try {
			List<CIBuild> builds = null;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJob job = ciManager.getJob(applicationInfo, name);
			builds = ciManager.getBuilds(job);
			return builds;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Creates the job.
	 *
	 * @param job the job
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the string
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createJob(CIJob job, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ciManager.createJob(applicationInfo, job);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return SUCCESS;
	}

	/**
	 * Update job.
	 *
	 * @param job the job
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the string
	 * @throws PhrescoException the phresco exception
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJob(CIJob job, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			ciManager.updateJob(applicationInfo, job);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return SUCCESS;
	}

	/**
	 * Delete jobs.
	 *
	 * @param jobNames the job names
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the cI job status
	 * @throws PhrescoException the phresco exception
	 */
	@DELETE
	@Path("/deletejobs")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CIJobStatus deleteJobs(String[] jobNames, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			return ciManager.deleteJobs(applicationInfo, Arrays.asList(jobNames));
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Delete builds.
	 *
	 * @param selectedBuilds the selected builds
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the cI job status
	 * @throws PhrescoException the phresco exception
	 */
	@DELETE
	@Path("/deletebuilds")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CIJobStatus deleteBuilds(String[] selectedBuilds, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		Map<String, List<String>> buildsTobeDeleted = new HashMap<String, List<String>>();
		for (String build : selectedBuilds) {
			String[] split = build.split(",");
			List<String> buildNumbers = new ArrayList<String>();
			if (buildsTobeDeleted.containsKey(split[0])) {
				List<String> listBuildNos = buildsTobeDeleted.get(split[0]);
				listBuildNos.add(split[1]);
				buildsTobeDeleted.put(split[0], listBuildNos);
			} else {
				buildNumbers.add(split[1]);
				buildsTobeDeleted.put(split[0], buildNumbers);
			}

		}
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			return ciManager.deleteBuilds(applicationInfo, buildsTobeDeleted);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the email configuration.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the email configuration
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/mail")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getEmailConfiguration(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			String smtpAuthUsername = ciManager.getMailConfiguration(SMTP_AUTH_USERNAME);
			String smtpAuthPassword = ciManager.getMailConfiguration(SMTP_AUTH_PASSWORD);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return SUCCESS;
	}

	/**
	 * Save email configuration.
	 *
	 * @param senderEmailId the sender email id
	 * @param senderEmailPassword the sender email password
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the string
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/savemail")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveEmailConfiguration(@QueryParam(REST_QUERY_SENDER_MAIL_ID) String senderEmailId,
			@QueryParam(REST_QUERY_SENDER_MAIL_PWD) String senderEmailPassword, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			String jenkinsPort = getPortNo(Utility.getJenkinsHome());
			ciManager.saveMailConfiguration(jenkinsPort, senderEmailId, senderEmailPassword);
			return SUCCESS;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Down stream check.
	 *
	 * @param selectedJobs the selected jobs
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the string
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/downstream")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String downStreamCheck(String[] selectedJobs, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			boolean isDownStreamAvailable = false;
			boolean streamCheck = false;
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			// get all the jobs
			List<CIJob> existJobs = ciManager.getJobs(applicationInfo);
			// get the name of all the jobs
			List<String> allJobNames = getJobNames(existJobs);
			List<String> asList = Arrays.asList(selectedJobs);
			for (String job : asList) {
				CIJob existJob = ciManager.getJob(applicationInfo, job);
				String existDownStream = existJob.getDownStreamProject();
				boolean contains = allJobNames.contains(existDownStream);
				if (StringUtils.isNotEmpty(existDownStream) && contains) {
					streamCheck = true;
				}
				if (streamCheck) {
					isDownStreamAvailable = true;
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return SUCCESS;
	}

	/**
	 * Builds the.
	 *
	 * @param selectedJobs the selected jobs
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the cI job status
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/build")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CIJobStatus Build(String[] selectedJobs, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);

			return ciManager.buildJobs(applicationInfo, Arrays.asList(selectedJobs));
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * CI build download.
	 *
	 * @param buildDownloadUrl the build download url
	 * @param downloadJobName the download job name
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the input stream
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/downloadBuild")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public InputStream CIBuildDownload(@QueryParam(REST_QUERY_BUILD_DOWNLOAD_URL) String buildDownloadUrl,
			@QueryParam(REST_QUERY_DOWNLOAD_JOB_NAME) String downloadJobName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		InputStream fileInputStream = null;
		String fileName = "";
		String contentType = "";
		String fileType = "";
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);

			CIJob existJob = ciManager.getJob(applicationInfo, downloadJobName);
			// Get it from web path
			buildDownloadUrl = buildDownloadUrl.replace(" ", "%20");
			URL url = new URL(buildDownloadUrl);
			fileInputStream = url.openStream();
			fileName = existJob.getName();
			contentType = "application/octet-stream";
			if (BUILD.equals(existJob.getOperation())) {
				fileType = ZIP;
			} else if (PDF_REPORT.equals(existJob.getOperation())) {
				fileType = "pdf";
			}
			return fileInputStream;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Number of jobs is in progress.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the int
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/progress")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int numberOfJobsIsInProgress(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		int numberOfJobsInProgress = 0;
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			List<CIJob> jobs = ciManager.getJobs(applicationInfo);
			if (CollectionUtils.isNotEmpty(jobs)) {
				for (CIJob ciJob : jobs) {
					boolean jobCreatingBuild = ciManager.isJobCreatingBuild(ciJob);
					if (jobCreatingBuild) {
						numberOfJobsInProgress++;
					}
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return numberOfJobsInProgress;
	}

	/**
	 * Local jenkins local alive.
	 *
	 * @return the string
	 */
	@POST
	@Path("/isAlive")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String localJenkinsLocalAlive() {
		String localJenkinsAlive = "";
		try {
			URL url = new URL(HTTP_PROTOCOL + PROTOCOL_POSTFIX + LOCALHOST + FrameworkConstants.COLON
					+ Integer.parseInt(getPortNo(Utility.getJenkinsHome())) + FrameworkConstants.FORWARD_SLASH + CI);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int code = httpConnection.getResponseCode();
			localJenkinsAlive = code + "";
		} catch (ConnectException e) {
			localJenkinsAlive = CODE_404;
		} catch (Exception e) {
			localJenkinsAlive = CODE_404;
		}
		return localJenkinsAlive;
	}

	/**
	 * Gets the port no.
	 *
	 * @param path the path
	 * @return the port no
	 * @throws PhrescoException the phresco exception
	 */
	public static String getPortNo(String path) throws PhrescoException {
		String portNo = "";
		try {
			Document document = ApplicationsUtil.getDocument(new File(path + File.separator + POM_FILE));
			String portNoNode = CI_TOMCAT_HTTP_PORT;
			NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(document, portNoNode);
			portNo = nodelist.item(0).getTextContent();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return portNo;
	}

	/**
	 * Gets the job names.
	 *
	 * @param existJobs the exist jobs
	 * @return the job names
	 */
	private List<String> getJobNames(List<CIJob> existJobs) {
		List<String> names = new ArrayList<String>();
		for (CIJob job : existJobs) {
			names.add(job.getName());
		}
		return names;
	}

}
