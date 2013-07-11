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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.commons.ApplicationsUtil;
import com.photon.phresco.framework.model.CIBuild;
import com.photon.phresco.framework.model.CIJob;
import com.photon.phresco.framework.model.CIJobStatus;
import com.photon.phresco.framework.model.ContinuousDelivery;
import com.photon.phresco.framework.model.ProjectDelivery;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class CIService.
 */
@Path("/ci")
public class CIService extends RestBase implements FrameworkConstants, ServiceConstants,Constants {

	/**
	 * Gets the jobs.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the jobs
	 * @throws PhrescoException the phresco exception
	 */
//	@GET
//	@Path("/jobs")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getJobs(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
//			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
//			throws PhrescoException {
//		ResponseInfo<List<CIJob>> responseData = new ResponseInfo<List<CIJob>>();
//		try {
//			List<CIJob> jobs = null;
//			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
//			jobs = ciManager.getJobs(applicationInfo);
//			ResponseInfo<List<CIJob>> finalOutput = responseDataEvaluation(responseData, null, "Jobs returned successfully",
//					jobs);
//			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		} catch (PhrescoException e) {
//			ResponseInfo<List<CIJob>> finalOutput = responseDataEvaluation(responseData, e, "No jobs to return",
//					null);
//			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		}
//	}

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
	@GET
	@Path("/builds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBuilds(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir,
			@QueryParam(REST_QUERY_NAME) String name) throws PhrescoException {
		ResponseInfo<List<CIBuild>> responseData = new ResponseInfo<List<CIBuild>>();
		List<CIBuild> builds = null;
		CIJob job = null;
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir);
			for (ProjectDelivery projectDelivery : ciJobInfo) {
				if(projectDelivery.getId().equals(projectId)) {
					List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
					for (ContinuousDelivery continuousDelivery : continuousDeliveries) {
						List<CIJob> ciJobs = continuousDelivery.getJobs();
						for (CIJob ciJob : ciJobs) {
							if(ciJob.getJobName().equals(name)) {
								job = ciJob;
							}
						}
					}
				}
			}
			builds = ciManager.getBuilds(job);
			ResponseInfo<List<CIBuild>> finalOutput = responseDataEvaluation(responseData, null, "Builds returned successfully",
					builds);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<List<CIBuild>> finalOutput = responseDataEvaluation(responseData, e, "No Builds to return",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
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
	public Response createJob(ContinuousDelivery continuousDelivery, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir)
	throws PhrescoException {
		ResponseInfo<ContinuousDelivery> responseData = new ResponseInfo<ContinuousDelivery>();
		try {
			boolean createJob = false;
			List<CIJob> ciJobs = new ArrayList<CIJob>(); 
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJob> jobs = continuousDelivery.getJobs();
			ApplicationInfo applicationInfo = null;
			for(CIJob job : jobs) {	
				if(StringUtils.isNotEmpty(job.getAppDirName())) {
					applicationInfo = FrameworkServiceUtil.getApplicationInfo(job.getAppDirName());
				}
				CIJob jobWithCmds = ciManager.setPreBuildCmds(job,  applicationInfo);
				createJob = ciManager.createJob(applicationInfo, jobWithCmds);
				if (createJob) {
					ciJobs.add(job);
				}
			}
			ciManager.createJsonJobs(continuousDelivery, ciJobs, projectId, appDir);
			
			ResponseInfo<ContinuousDelivery> finalOutput = responseDataEvaluation(responseData, null, "Job created successfully", continuousDelivery);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<ContinuousDelivery> finalOutput = responseDataEvaluation(responseData, e, "Job creation Failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		}
	}
	
//	@POST
//	@Path("/create")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response createCloneJob(@QueryParam(REST_QUERY_CLONE_NAME) String cloneName, @QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName,
//			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_ENV_NAME) String envName, @QueryParam(REST_QUERY_APPID) String appDir)
//			throws PhrescoException {
//		ResponseInfo<ContinuousDelivery> responseData = new ResponseInfo<ContinuousDelivery>();
//		try {
//			boolean createJob = false;
//			System.out.println("Create Job method ======>");
////			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
////			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
//			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			List<CIJob> jobs = continuousDelivery.getJobs();
//			for(CIJob job : jobs) {	
//				ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(job.getAppDirName());
//				CIJob jobWithCmds = ciManager.setPreBuildCmds(job,  applicationInfo);
//				createJob = ciManager.createJob(applicationInfo, jobWithCmds);
//				System.out.println("createJob ===> "+createJob);
//				if (createJob) {
//					ciManager.writeJsonJobs(continuousDelivery, jobWithCmds, projectId);
//				}
//			}
//					
//			 ResponseInfo<ContinuousDelivery> finalOutput = responseDataEvaluation(responseData, null, "Job created successfully", continuousDelivery);
//			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
//		} catch (PhrescoException e) {
//			ResponseInfo<ContinuousDelivery> finalOutput = responseDataEvaluation(responseData, e, "Job creation Failed",
//					null);
//			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		}
//	}
	
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
	public Response updateJob(ContinuousDelivery continuousDelivery, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId,  @QueryParam(REST_QUERY_APPDIR_NAME) String appDir)
			throws PhrescoException {
		ResponseInfo<ContinuousDelivery> responseData = new ResponseInfo<ContinuousDelivery>();
		boolean updateJob = false;
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			
			//Lists objects for current updated jobs, previous jobs, diff jobs to be deleted, list to add for updating jsonFile respectively
			List<CIJob> jobs = continuousDelivery.getJobs();
			List<CIJob> oldJobs = ciManager.getDeleteableJobs(projectId, continuousDelivery, appDir);
			List<CIJob> removeFromList = removeFromList(oldJobs, jobs);
			List<CIJob> ciJobs = new ArrayList<CIJob>(); 
			
			ciManager.clearContinuousDelivery(continuousDelivery.getName(), projectId, appDir);
			for(CIJob job : jobs) {	
				ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(job.getAppDirName());
				updateJob = ciManager.updateJob(applicationInfo, job);		
				if (updateJob) {
					ciJobs.add(job);
				}
			}
			ciManager.createJsonJobs(continuousDelivery, ciJobs, projectId, appDir);
			ciManager.deleteJobs(appDir, removeFromList, projectId, continuousDelivery.getName());
			
			ResponseInfo<ContinuousDelivery> finalOutput = responseDataEvaluation(responseData, null, "Job updated successfully", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, "Job updation Failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	/**
	 * @param customerId
	 * @param projectId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/editContinuousView")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editContinuousView(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_NAME) String continuousName, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir)
			throws PhrescoException {
		ResponseInfo<ContinuousDelivery> responseData = new ResponseInfo<ContinuousDelivery>();
		ContinuousDelivery matchingContinuous = null;
		boolean exist = false;
		CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir);
		if(CollectionUtils.isNotEmpty(ciJobInfo)) {
			for(ProjectDelivery projectDelivery : ciJobInfo) {
				if (projectDelivery.getId().equals(projectId)) {
					List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
					if(CollectionUtils.isNotEmpty(continuousDeliveries)) {
						for(ContinuousDelivery continuousDelivery : continuousDeliveries) {
							if (continuousDelivery.getName().equalsIgnoreCase(continuousName)) {
								matchingContinuous = continuousDelivery;
								exist = true;
							}
						}
					}
				}
			}
		}	
		ResponseInfo<ContinuousDelivery> finalOutput;
		if(exist) {
			finalOutput= responseDataEvaluation(responseData, null, "Continuous Delivery Fetched successfully",	matchingContinuous);
		} else {
			finalOutput = responseDataEvaluation(responseData, null, "Could not find Delivery named "+continuousName,	matchingContinuous);
		}
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * @param customerId
	 * @param projectId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContinuousDeliveryJob(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir)
			throws PhrescoException {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ProjectDelivery projectContinuousDelivery = new ProjectDelivery();
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir);
			if(CollectionUtils.isNotEmpty(ciJobInfo)) {
				for (ProjectDelivery projectDelivery : ciJobInfo) {
					if(projectDelivery.getId().equals(projectId)) {
						List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
						List<ContinuousDelivery> contDeliveryList = new ArrayList<ContinuousDelivery>();
						for (ContinuousDelivery continuousDelivery : continuousDeliveries) {
							
							List<CIJob> ciJobs = continuousDelivery.getJobs();
							String downstreamApplication = "";
							ContinuousDelivery contDelivery = new ContinuousDelivery();
							List<CIJob> jobs = new ArrayList<CIJob>();
							for (CIJob ciJob : ciJobs) {
								if(StringUtils.isEmpty(ciJob.getUpstreamApplication())) {
									jobs.add(ciJob);
									downstreamApplication = ciJob.getDownstreamApplication();
									if(ciJobs.size() == 1) {
										contDelivery.setJobs(jobs);
									}
								}
							}
							if(StringUtils.isNotEmpty(downstreamApplication)) {
								int flag = 0;
								for(int i=0;i<ciJobs.size();i++){
									for (CIJob ciJob : ciJobs) {
										if(ciJob.getJobName().equals(downstreamApplication)) {
											jobs.add(ciJob);
											if(StringUtils.isEmpty(ciJob.getDownstreamApplication())) {
												flag = 1;
												contDelivery.setJobs(jobs);
												downstreamApplication = "";
												break;
											} else {
												downstreamApplication = ciJob.getDownstreamApplication();
											}
											if(flag == 1) {
												break;
											}
										}
										if(flag == 1) {
											break;
										}
									}
								}
							} 
							contDelivery.setName(continuousDelivery.getName());
							contDeliveryList.add(contDelivery);
							projectContinuousDelivery.setContinuousDeliveries(contDeliveryList);
						}
						projectContinuousDelivery.setId(projectId);
					}
				}
			}
			
			ResponseInfo<ProjectDelivery> finalOutput = responseDataEvaluation(responseData, null,
					"Continuous Delivery List Successfully", projectContinuousDelivery);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, "Job creation Failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
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
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appDir) throws PhrescoException {
		ResponseInfo<CIJobStatus> responseData = new ResponseInfo<CIJobStatus>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir);
			CIJobStatus ciJobStatus = null;
			List<CIJob> jobs = ciManager.getJobs(continuousName, projectId, ciJobInfo);
			ciJobStatus = ciManager.deleteJobs(appDir, jobs, projectId, continuousName);
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, null, "Job deleted successfully",
					ciJobStatus);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, e, "Job deletion Failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
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
	public Response deleteBuilds(@QueryParam(REST_QUERY_BUILD_NUMBER) String buildNumber, @QueryParam(REST_QUERY_NAME) String jobName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appDir)
			throws PhrescoException {
		ResponseInfo<CIJobStatus> responseData = new ResponseInfo<CIJobStatus>();
		
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJobStatus deleteBuilds = null;
			ProjectDelivery projectContinuousDelivery = new ProjectDelivery();
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir);
			if(CollectionUtils.isNotEmpty(ciJobInfo)) {
				for (ProjectDelivery projectDelivery : ciJobInfo) {
					if(projectDelivery.getId().equals(projectId)) {
						List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
						List<ContinuousDelivery> contDeliveryList = new ArrayList<ContinuousDelivery>();
						for (ContinuousDelivery continuousDelivery : continuousDeliveries) {
							List<CIJob> ciJobs = continuousDelivery.getJobs();
							for (CIJob ciJob : ciJobs) {
								if(ciJob.getJobName().equals(jobName)) {
									deleteBuilds = ciManager.deleteBuilds(ciJob, buildNumber);
								}
							}
						}
					}
				}
			}
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, null, "Build deleted successfully",
					deleteBuilds);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, e, "Build deletion Failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
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
	public Response getEmailConfiguration(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			String smtpAuthUsername = ciManager.getMailConfiguration(SMTP_AUTH_USERNAME);
			String smtpAuthPassword = ciManager.getMailConfiguration(SMTP_AUTH_PASSWORD);
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Returned mail confiuration Failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
//		return SUCCESS;
		return null;
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
	public Response saveEmailConfiguration(@QueryParam(REST_QUERY_SENDER_MAIL_ID) String senderEmailId,
			@QueryParam(REST_QUERY_SENDER_MAIL_PWD) String senderEmailPassword, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
			throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
			String jenkinsPort = getPortNo(Utility.getJenkinsHome());
			ciManager.saveMailConfiguration(jenkinsPort, senderEmailId, senderEmailPassword);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "Mail Configuration saved successfully",
					null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Save mail Configuration failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
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
//	@POST
//	@Path("/downstream")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response downStreamCheck(String[] selectedJobs, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
//			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
//			throws PhrescoException {
//		ResponseInfo responseData = new ResponseInfo();
//		try {
//			boolean isDownStreamAvailable = false;
//			boolean streamCheck = false;
//			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
//			// get all the jobs
//			List<CIJob> existJobs = ciManager.getJobs(applicationInfo);
//			// get the name of all the jobs
//			List<String> allJobNames = getJobNames(existJobs);
//			List<String> asList = Arrays.asList(selectedJobs);
//			for (String job : asList) {
//				CIJob existJob = ciManager.getJob(applicationInfo, job);
//				String existDownStream = existJob.getDownstreamApplication();
//				boolean contains = allJobNames.contains(existDownStream);
//				if (StringUtils.isNotEmpty(existDownStream) && contains) {
//					streamCheck = true;
//				}
//				if (streamCheck) {
//					isDownStreamAvailable = true;
//				}
//			}
//			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "Down stream checked successfully",
//					null);
//			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		} catch (PhrescoException e) {
//			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Down stream check failed",
//					null);
//			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		}
////		return SUCCESS;
//	}

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
	public Response build(@QueryParam(REST_QUERY_NAME) String name,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir) throws PhrescoException {
		ResponseInfo<CIJobStatus> responseData = new ResponseInfo<CIJobStatus>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJobStatus buildJobs = null;
			CIJob job = null;
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir);
			for (ProjectDelivery projectDelivery : ciJobInfo) {
				if(projectDelivery.getId().equals(projectId)) {
					List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
					for (ContinuousDelivery continuousDelivery : continuousDeliveries) {
						List<CIJob> ciJobs = continuousDelivery.getJobs();
						for (CIJob ciJob : ciJobs) {
							if(ciJob.getJobName().equals(name)) {
								job = ciJob;
								buildJobs = ciManager.generateBuild(job);
							}
						}
					}
				}
			}
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, null, "Build successfully",
					buildJobs);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, e, "Build failed",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
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
//	@POST
//	@Path("/downloadBuild")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response CIBuildDownload(@QueryParam(REST_QUERY_BUILD_DOWNLOAD_URL) String buildDownloadUrl,
//			@QueryParam(REST_QUERY_DOWNLOAD_JOB_NAME) String downloadJobName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
//			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
//			throws PhrescoException {
//		ResponseInfo responseData = new ResponseInfo();
//		InputStream fileInputStream = null;
//		String fileName = "";
//		String contentType = "";
//		String fileType = "";
//		try {
//			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
//
//			CIJob existJob = ciManager.getJob(applicationInfo, downloadJobName);
//			// Get it from web path
//			buildDownloadUrl = buildDownloadUrl.replace(" ", "%20");
//			URL url = new URL(buildDownloadUrl);
//			fileInputStream = url.openStream();
//			fileName = existJob.getJobName();
//			contentType = "application/octet-stream";
//			if (BUILD.equals(existJob.getOperation())) {
//				fileType = Constants.ZIP;
//			} else if (PDF_REPORT.equals(existJob.getOperation())) {
//				fileType = "pdf";
//			}
//			return Response.status(Status.OK).entity(fileInputStream).header("Access-Control-Allow-Origin", "*")
//			.build();
//		} catch (Exception e) {
//			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "Build Download failed",
//					null);
//			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		}
//	}

	/**
	 * Number of jobs is in progress.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param appId the app id
	 * @return the int
	 * @throws PhrescoException the phresco exception
	 */
//	@POST
//	@Path("/progress")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response numberOfJobsIsInProgress(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
//			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPID) String appId)
//			throws PhrescoException {
//		ResponseInfo<Integer> responseData = new ResponseInfo<Integer>();
//		int numberOfJobsInProgress = 0;
//		try {
//			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(customerId, projectId, appId);
//			List<CIJob> jobs = ciManager.getJobs(applicationInfo);
//			if (CollectionUtils.isNotEmpty(jobs)) {
//				for (CIJob ciJob : jobs) {
//					boolean jobCreatingBuild = ciManager.isJobCreatingBuild(ciJob);
//					if (jobCreatingBuild) {
//						numberOfJobsInProgress++;
//					}
//				}
//			}
//			ResponseInfo<Integer> finalOutput = responseDataEvaluation(responseData, null, "Progress Jobs  returned successfully",
//					numberOfJobsInProgress);
//			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		} catch (PhrescoException e) {
//			ResponseInfo<Integer> finalOutput = responseDataEvaluation(responseData, e, "Progress Jobs  return failed",
//					null);
//			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
//					.build();
//		}
//	}

	/**
	 * Local jenkins local alive.
	 *
	 * @return the string
	 */
	@POST
	@Path("/isAlive")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response localJenkinsLocalAlive() {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
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
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Progress Jobs  returned successfully",
					localJenkinsAlive);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			localJenkinsAlive = CODE_404;
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Progress Jobs  returned successfully",
					localJenkinsAlive);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Progress Jobs  returned successfully",
				localJenkinsAlive);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
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
			names.add(job.getJobName());
		}
		return names;
	}
	
	private List<CIJob> removeFromList(List<CIJob> returningList, List<CIJob> negateList) {
		 for (Iterator<CIJob> itr = returningList.iterator();itr.hasNext();) {  
	            CIJob next = itr.next(); 
	            for (CIJob job : negateList) {
		            if(StringUtils.isNotEmpty(job.getJobName()) && (job.getJobName()).equals(next.getJobName())) {
			                itr.remove();  
			            }  
		            }
	        }  
		return returningList;
	}

}
