/**
 * Framework Web Archive
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
package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.CIBuild;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.CIJobStatus;
import com.photon.phresco.commons.model.ContinuousDelivery;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.ModuleInfo;
import com.photon.phresco.commons.model.ProjectDelivery;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.Technology;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.impl.CIManagerImpl;
import com.photon.phresco.framework.impl.util.FrameworkUtil;
import com.photon.phresco.framework.model.GlobalSettings;
import com.photon.phresco.framework.model.Keychain;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.framework.model.TestFlight;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class CIService.
 */

@Path("/ci")
public class CIService extends RestBase implements FrameworkConstants, ServiceConstants, Constants, ResponseCodes {

	private ServiceManager serviceManager = null;
	
	/**
	 * @param projectId
	 * @param appDir
	 * @param jobName
	 * @param continuousName
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/builds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBuilds(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir,
			@QueryParam(REST_QUERY_NAME) String jobName, @QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName, 
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule) throws PhrescoException {
		ResponseInfo<List<CIBuild>> responseData = new ResponseInfo<List<CIBuild>>();
		List<CIBuild> builds = null;
		CIJob job = null;
		String module = "";
		String splitPath = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			List<CIJob> ciJobs = Utility.getJobs(continuousName, projectId, ciJobInfo);
			for (CIJob ciJob : ciJobs) {
				if(ciJob.getJobName().equals(jobName)) {
					job = ciJob;
				}
			}
			builds = ciManager.getBuilds(job);
			ResponseInfo<List<CIBuild>> finalOutput = responseDataEvaluation(responseData, null, builds, RESPONSE_STATUS_SUCCESS, PHR800001);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<List<CIBuild>> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810001);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		} catch (PhrescoPomException e) {
			ResponseInfo<List<CIBuild>> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810001);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		}
	}

	/**
	 * @param continuousDelivery
	 * @param customerId
	 * @param projectId
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createJob(@Context HttpServletRequest request, ContinuousDelivery continuousDelivery, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir, 
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule)
	throws PhrescoException, PhrescoPomException {
		ResponseInfo<ContinuousDelivery> responseData = new ResponseInfo<ContinuousDelivery>();
		ResponseInfo<ContinuousDelivery> finalOutput;
		String module = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				String splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			boolean coreCreateJob = coreCreateJob(continuousDelivery, projectId, appDir, userId, customerId, request);
			if (coreCreateJob) {
				finalOutput = responseDataEvaluation(responseData, null, continuousDelivery, RESPONSE_STATUS_SUCCESS, PHR800002);
			} else {
				finalOutput = responseDataEvaluation(responseData, null, continuousDelivery, RESPONSE_STATUS_FAILURE, PHR810003);
			}
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810003);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810003);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}
	
	@GET
	@Path("/validation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response jobNameValidataion(@QueryParam(REST_QUERY_NAME) String jobName, @QueryParam("flag") String flag, @QueryParam(REST_QUERY_USERID) String userId)
					throws PhrescoException, JSONException, ClientProtocolException, IOException {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		boolean hasTrue = true;
		String name = null;
		try {
			if(!flag.equalsIgnoreCase("update")) {
				JSONArray jobs = new JSONArray("["+jobName+"]");
				String jenkinsUrl = FrameworkUtil.getJenkinsUrl() + "/api/json";
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpContext httpContext = new BasicHttpContext();
				HttpGet httpget = new HttpGet(jenkinsUrl);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
				ciManager.getAuthenticatedHttpClient(httpClient, httpContext);
				String execute = httpClient.execute(httpget, responseHandler);
				
				JSONArray jsonArray = new JSONArray("["+execute.toString()+"]");
			    JSONObject jsonObject = jsonArray.getJSONObject(0);
			    JSONArray jobJsonArray = jsonObject.getJSONArray("jobs");
			    for (int k = 0; k<jobs.length(); k++) {
				    for (int j = 0; j<jobJsonArray.length(); j++) {
				    	 JSONObject jobJsonObject = jobJsonArray.getJSONObject(j);
				    	 if(jobs.get(k).toString().equals(jobJsonObject.getString("name"))) {
				    		 hasTrue = false;
				    		 name = jobs.get(k).toString();
				    		 break;
				    	 }
				    }
				}
			}
		    if(hasTrue) {
				ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, hasTrue, RESPONSE_STATUS_SUCCESS, PHR800003);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		    } else {
		    	ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, name, RESPONSE_STATUS_FAILURE, PHR810031);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		    }
		} catch (PhrescoException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810031);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}

	/**
	 * @param cloneName
	 * @param envName
	 * @param continuousName
	 * @param customerId
	 * @param projectId
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	@POST
	@Path("/clone")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createClone(@Context HttpServletRequest request, @QueryParam(REST_QUERY_CLONE_NAME) String cloneName, @QueryParam(REST_QUERY_ENV_NAME) String envName,
			@QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir, 
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule)
	throws PhrescoException {
		ResponseInfo<ContinuousDelivery> responseData = new ResponseInfo<ContinuousDelivery>();
		ResponseInfo<ContinuousDelivery> finalOutput;
		String module = "";
		String splitPath = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			String trimmedName = continuousName.trim();
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ContinuousDelivery continuousDelivery = new ContinuousDelivery();

			List<CIJob> ciJobs = new ArrayList<CIJob>(); 
			List<CIJob> jobs = new ArrayList<CIJob>();

			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			ContinuousDelivery specificContinuousDelivery = Utility.getContinuousDelivery(projectId, trimmedName, ciJobInfo);
			if(specificContinuousDelivery.getName().equals(trimmedName)) {
				continuousDelivery.setName(cloneName);
				continuousDelivery.setEnvName(envName);
				jobs = specificContinuousDelivery.getJobs();
			}

			for(CIJob cijob: jobs) {
				cijob.setJobName(cijob.getJobName()+"-"+envName);
				cijob.setEnvironmentName(envName);
				if(StringUtils.isNotEmpty(cijob.getDownstreamApplication())) {
					cijob.setDownstreamApplication(cijob.getDownstreamApplication()+"-"+envName);
				}
				if(StringUtils.isNotEmpty(cijob.getUpstreamApplication())) {
					cijob.setUsedClonnedWorkspace(cijob.getUpstreamApplication()+"-"+envName);
					cijob.setUpstreamApplication(cijob.getUpstreamApplication()+"-"+envName);
				}

				ciJobs.add(cijob);
			}
			continuousDelivery.setJobs(ciJobs);
			if (appDir.contains("\\")) {
				String[] split = appDir.split("\\\\");
				appDir = split[0];
			}
			boolean coreCreateJob = coreCreateJob(continuousDelivery, projectId, appDir, userId, customerId, request);

			if (coreCreateJob) {
				if (StringUtils.isEmpty(appDir)) {
					copyGlobalInfoFile(customerId, projectId);
				}
				finalOutput = responseDataEvaluation(responseData, null, continuousDelivery, RESPONSE_STATUS_SUCCESS, PHR800002);
			} else {
				finalOutput = responseDataEvaluation(responseData, null, null, RESPONSE_STATUS_FAILURE, PHR810003);
			}
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810003);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810003);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		}
	}
	
	/**
	 * @param continuousDelivery
	 * @param customerId
	 * @param projectId
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateJob(@Context HttpServletRequest request, ContinuousDelivery continuousDelivery, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir, 
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_OLDNAME)String oldname, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule)
	throws PhrescoException, ClientProtocolException, IOException, JSONException {
		String thisName = continuousDelivery.getName();
		if (StringUtils.isNotEmpty(oldname) && (!thisName.equalsIgnoreCase(oldname))) {
			thisName = oldname;
		}
		ResponseInfo<CIJobStatus> responseData = new ResponseInfo<CIJobStatus>();
		boolean updateJob = false;
		CIJobStatus status = null;
		String module = "";
		String splitPath = "";
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			List<CIJob> tempCiJobs = new ArrayList<CIJob>(); 
			List<CIJob> tempJobs = new ArrayList<CIJob>();
			List<CIJob> newJobs = continuousDelivery.getJobs();
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<CIJob> oldJobs = ciManager.getOldJobs(projectId, thisName, appDir, globalInfo, READ);
			tempCiJobs.addAll(oldJobs);

			for (CIJob ciJob : newJobs) {
				boolean exists = false;
				for (CIJob oldCiJob : oldJobs) {
					if(oldCiJob.getJobName().equals(ciJob.getJobName())) {
						boolean equals = ciJob.equals(oldCiJob);
						//tempCiJobs.add(ciJob);
						ApplicationInfo applicationInfo = new ApplicationInfo();
						String ciName = continuousDelivery.getName();
						if(!equals || (!ciName.equalsIgnoreCase(oldname))) {
							exists = true;
							if(StringUtils.isNotEmpty(ciJob.getAppDirName())) {
								splitPath = Utility.splitPathConstruction(ciJob.getAppDirName());
								if(StringUtils.isNotEmpty(ciJob.getModule())) {
									splitPath = splitPath + File.separator + ciJob.getModule();
								}
								applicationInfo = FrameworkServiceUtil.getApplicationInfo(splitPath);
							}
							CIJob job = setPreBuildCmds(ciJob,  applicationInfo, appDir, projectId, continuousDelivery.getName(), userId, customerId, request);
							updateJob = ciManager.updateJob(job);
							//						if(updateJob) {
							tempJobs.add(ciJob);
							//						}
						} else {
							exists = true;
							tempJobs.add(ciJob);
						}
					} 
				}
				if (!exists) {
					ApplicationInfo applicationInfo = new ApplicationInfo();
					if(StringUtils.isNotEmpty(ciJob.getAppDirName())) {
						splitPath = Utility.splitPathConstruction(ciJob.getAppDirName());
						if(StringUtils.isNotEmpty(ciJob.getModule())) {
							splitPath = splitPath + File.separator + ciJob.getModule();
						}
						applicationInfo = FrameworkServiceUtil.getApplicationInfo(splitPath);
					}
					CIJob jobWithCmds = setPreBuildCmds(ciJob,  applicationInfo, appDir, projectId, continuousDelivery.getName(), userId, customerId, request);
					boolean validateJob = validateJob(jobWithCmds.getJobName());
					if(validateJob){
						boolean createJob = ciManager.createJob(jobWithCmds);
						if(createJob) {
							tempJobs.add(ciJob);
						}
						exists = false;
					} else {
						ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, jobWithCmds.getJobName(), RESPONSE_STATUS_FAILURE, PHR810031);
						return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
					}
					
				} 
			}
			boolean clearContinuousDelivery = ciManager.clearContinuousDelivery(thisName, projectId, appDir, "", WRITE);
			if(clearContinuousDelivery) {
				continuousDelivery.setJobs(tempJobs);
			}

			boolean createJsonJobs = ciManager.createJsonJobs(continuousDelivery, tempJobs, projectId, appDir, "", WRITE);
			if (createJsonJobs) {
				for(CIJob job:tempJobs) {
					for (CIJob ciJob2 : tempCiJobs) {
						if(ciJob2.getJobName().equals(job.getJobName())) {
							oldJobs.remove(ciJob2);
						}
					}
				}
				status = ciManager.deleteJobs(appDir, oldJobs, projectId, thisName);
			}
			if (StringUtils.isEmpty(appDir)) {
				copyGlobalInfoFile(customerId, projectId);
			}
			ResponseInfo<CIJobStatus> finalOutput;
			if (createJsonJobs) {
				finalOutput = responseDataEvaluation(responseData, null, continuousDelivery, RESPONSE_STATUS_SUCCESS, PHR800020);
			} else {
				finalOutput = responseDataEvaluation(responseData, null, null, RESPONSE_STATUS_FAILURE, PHR810005);
			}
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810005);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810005);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}
	
	private boolean validateJob(String jobName) throws PhrescoException, ClientProtocolException, IOException, JSONException {
		boolean hasTrue = true;
		String jenkinsUrl = FrameworkUtil.getJenkinsUrl()+ "/api/json";
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpGet httpget = new HttpGet(jenkinsUrl);
		CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		ciManager.getAuthenticatedHttpClient(httpClient, httpContext);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String execute = httpClient.execute(httpget, responseHandler);
		
		JSONArray jsonArray = new JSONArray("["+execute.toString()+"]");
	    JSONObject jsonObject = jsonArray.getJSONObject(0);
	    JSONArray jobJsonArray = jsonObject.getJSONArray("jobs");
	    for (int j = 0; j<jobJsonArray.length(); j++) {
	    	 JSONObject jsonObject1 = jobJsonArray.getJSONObject(j);
	    	 if(jobName.equals(jsonObject1.getString("name"))) {
	    		 hasTrue = false;
	    		 break;
	    	 }
	    }
	    return hasTrue;
		
	}

	/**
	 * @param projectId
	 * @param continuousName
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/editContinuousView")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editContinuousView(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_NAME) String continuousName, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDir, @QueryParam(REST_QUERY_CUSTOMERID) String customerId, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule)
	throws PhrescoException {
		ResponseInfo<ContinuousDelivery> responseData = new ResponseInfo<ContinuousDelivery>();
		ResponseInfo<ContinuousDelivery> finalOutput;
		ContinuousDelivery matchingContinuous = null;
		boolean exist = false;
		String module = "";
		String splitPath = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			ContinuousDelivery continuousDelivery = Utility.getContinuousDelivery(projectId, continuousName.trim(), ciJobInfo);
			if (continuousDelivery.getName().equalsIgnoreCase(continuousName)) {
				matchingContinuous = continuousDelivery;
				exist = true;
			}

			if(exist) {
				finalOutput= responseDataEvaluation(responseData, null, matchingContinuous, RESPONSE_STATUS_SUCCESS, PHR800004);
			} else {
				finalOutput = responseDataEvaluation(responseData, null, matchingContinuous, RESPONSE_STATUS_FAILURE, PHR810006);
			}
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, null, matchingContinuous, RESPONSE_STATUS_FAILURE, PHR810006);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		}
	}
	
	/**
	 * @param projectId
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContinuousDeliveryJob(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule, @QueryParam(REST_QUERY_CUSTOMERID) String customerId)
	throws PhrescoException {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		String module = "";
		String splitPath = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ProjectDelivery projectContinuousDelivery = new ProjectDelivery();
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			if(CollectionUtils.isNotEmpty(ciJobInfo)) {
				ProjectDelivery projectDelivery = Utility.getProjectDelivery(projectId, ciJobInfo);
				if (projectDelivery != null) {
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
						contDelivery.setEnvName(continuousDelivery.getEnvName());
						contDeliveryList.add(contDelivery);
						projectContinuousDelivery.setContinuousDeliveries(contDeliveryList);
					}
				}
				projectContinuousDelivery.setId(projectId);
			}

			ResponseInfo<ProjectDelivery> finalOutput = responseDataEvaluation(responseData, null, projectContinuousDelivery, RESPONSE_STATUS_SUCCESS, PHR800005);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810007);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810007);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}

	/**
	 * @param continuousName
	 * @param customerId
	 * @param projectId
	 * @param appDir
	 * @return
	 * @throws PhrescoException
	 */
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir) throws PhrescoException {
		ResponseInfo<CIJobStatus> responseData = new ResponseInfo<CIJobStatus>();
		String module = "";
		String splitPath = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			CIJobStatus ciJobStatus = null;
			List<CIJob> jobs = Utility.getJobs(continuousName, projectId, ciJobInfo);
			ciJobStatus = ciManager.deleteJobs(appDir, jobs, projectId, continuousName);
			boolean clearContinuousDelivery = ciManager.clearContinuousDelivery(continuousName, projectId, appDir, "", WRITE);
			if (clearContinuousDelivery && StringUtils.isEmpty(appDir)) {
				copyGlobalInfoFile(customerId, projectId);
			}
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, null, ciJobStatus, RESPONSE_STATUS_SUCCESS, PHR800006);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810009);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810009);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}

	/**
	 * @param buildNumber
	 * @param jobName
	 * @param customerId
	 * @param projectId
	 * @param appDir
	 * @param continuousName
	 * @return
	 * @throws PhrescoException
	 */
	@DELETE
	@Path("/deletebuilds")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteBuilds(@QueryParam(REST_QUERY_BUILD_NUMBER) String buildNumber, @QueryParam(REST_QUERY_NAME) String jobName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule, @QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName)
	throws PhrescoException {
		ResponseInfo<CIJobStatus> responseData = new ResponseInfo<CIJobStatus>();
		ResponseInfo<CIJobStatus> finalOutput ;
		String module = "";
		String splitPath = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJobStatus deleteBuilds = null;
			
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}	
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			CIJob specificJob = ciManager.getJob(jobName, projectId, ciJobInfo, continuousName);
			if (specificJob != null) {
				deleteBuilds = ciManager.deleteBuilds(specificJob, buildNumber);
				finalOutput = responseDataEvaluation(responseData, null, deleteBuilds, RESPONSE_STATUS_SUCCESS, PHR800007);
			} else {
				finalOutput = responseDataEvaluation(responseData, null, deleteBuilds, RESPONSE_STATUS_FAILURE, PHR810011);
			}

			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810011);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810011);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}
	
	/**
	 * @param name
	 * @param projectId
	 * @param appDir
	 * @param continuousName
	 * @return
	 * @throws PhrescoException
	 */
	@POST
	@Path("/build")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response build(@QueryParam(REST_QUERY_NAME) String name, @QueryParam(REST_QUERY_PROJECTID) String projectId, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDir, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule, 
			@QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId) throws PhrescoException {
		ResponseInfo<CIJobStatus> responseData = new ResponseInfo<CIJobStatus>();
		String module = "";
		String splitPath = "";
		try {
			//appLevel
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJobStatus buildJobs = null;
			ResponseInfo<CIJobStatus> finalOutput; 
			
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			//proj Level
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			CIJob specificJob = ciManager.getJob(name, projectId, ciJobInfo, continuousName);
			if (specificJob != null) {
				buildJobs = ciManager.generateBuild(specificJob);
				finalOutput = responseDataEvaluation(responseData, null, buildJobs, RESPONSE_STATUS_SUCCESS, PHR800008);
			} else {
				finalOutput = responseDataEvaluation(responseData, null, buildJobs, RESPONSE_STATUS_FAILURE, PHR810012);
			}

			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810013);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			ResponseInfo<CIJobStatus> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810013);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
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
	@Path("/mail")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getEmailConfiguration()
	throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		List<RepoDetail> confluenceDetail = new ArrayList<RepoDetail>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			String[] cred = new String[2];
			cred[0] = ciManager.getMailConfiguration(SMTP_AUTH_USERNAME);
			String mailConfiguration = ciManager.getMailConfiguration(SMTP_AUTH_PASSWORD);
			if(StringUtils.isNotEmpty(mailConfiguration)) {
				cred[1] = ciManager.decyPassword(mailConfiguration);
			} else {
				cred[1] = "";
			}
			
			finalOutput = responseDataEvaluation(responseData, null, cred, RESPONSE_STATUS_SUCCESS, PHR800009);
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810014);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
	/**
	 * @param customerId
	 * @param projectId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/tfs")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTfsSystemConfig()
	throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			String tfsConfig = ciManager.getTfsConfiguration();
			finalOutput = responseDataEvaluation(responseData, null, tfsConfig, RESPONSE_STATUS_SUCCESS, PHR810044);
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810045);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
	/**
	 * @return
	 * @throws PhrescoException
	 * @throws UnknownHostException
	 */
	@GET
	@Path("/jenkinsUrl")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getJenkinsUrl()throws PhrescoException, UnknownHostException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		try {
			RepoDetail jenkinsDetails = new RepoDetail();
			jenkinsDetails.setRepoUrl(FrameworkUtil.getJenkinsUrl());
			jenkinsDetails.setUserName(FrameworkUtil.getJenkinsUsername());
			jenkinsDetails.setPassword(FrameworkUtil.getJenkinsPassword());
			finalOutput = responseDataEvaluation(responseData, null, jenkinsDetails, RESPONSE_STATUS_SUCCESS, PHR800024);
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810037);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
				.build();
	}
	
	/**
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/confluence")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getConfluenceConfiguration()	throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		List<RepoDetail> confluenceDetail = new ArrayList<RepoDetail>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			org.codehaus.jettison.json.JSONArray confluenceConfiguration = ciManager.getConfluenceConfiguration();
			if (confluenceConfiguration != null) {
				for (int i = 0; i < confluenceConfiguration.length(); i++) {
					RepoDetail repo = new RepoDetail();
					org.codehaus.jettison.json.JSONObject JSONobject = (org.codehaus.jettison.json.JSONObject) confluenceConfiguration.get(i);
					repo.setRepoUrl(JSONobject.getString(CONFLUENCE_SITE_URL));
					repo.setUserName(JSONobject.getString(CONFLUENCE_USERNAME));
					repo.setPassword(JSONobject.getString(FrameworkConstants.PASSWORD));
					confluenceDetail.add(repo);
				}
			}
			finalOutput = responseDataEvaluation(responseData, null, confluenceDetail, RESPONSE_STATUS_SUCCESS, PHR800021);
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810034);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (org.codehaus.jettison.json.JSONException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810034);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
	/**
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/testFlight")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTestFlightConfiguration()	throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		List<TestFlight> testFlightDetail = new ArrayList<TestFlight>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			org.codehaus.jettison.json.JSONArray testFlightConfiguration = ciManager.getTestFlightConfiguration();
			if (testFlightConfiguration != null) {
				for (int i = 0; i < testFlightConfiguration.length(); i++) {
					TestFlight config = new TestFlight();
					org.codehaus.jettison.json.JSONObject JSONobject = (org.codehaus.jettison.json.JSONObject) testFlightConfiguration.get(i);
					config.setTokenPairName(JSONobject.getString(TESTFLIGHT_TOKEN_NAME));
					config.setApiToken(JSONobject.getString(API_TOKEN));
					config.setTeamToken(JSONobject.getString(TEAM_TOKEN));
					testFlightDetail.add(config);
				}
			}
			finalOutput = responseDataEvaluation(responseData, null, testFlightDetail, RESPONSE_STATUS_SUCCESS, PHR800026);
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810043);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (org.codehaus.jettison.json.JSONException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810043);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
	/**
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/keyChains")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getKeyChains()	throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		List<Keychain> keyChains = new ArrayList<Keychain>();
		try {
			String defaultKeychain = "";
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			org.codehaus.jettison.json.JSONArray keyChainsConfiguration = ciManager.getKeyChains();
			if (keyChainsConfiguration != null) {
//				for (int i = 0; i < keyChainsConfiguration.length(); i++) {
//					org.codehaus.jettison.json.JSONObject JSONobject = (org.codehaus.jettison.json.JSONObject) keyChainsConfiguration.get(i);
//					defaultKeychain = JSONobject.getString("defaultKeychain");
//				}

				for (int i = 0; i < keyChainsConfiguration.length(); i++) {
					Keychain keys = new Keychain();
					org.codehaus.jettison.json.JSONObject JSONobject = (org.codehaus.jettison.json.JSONObject) keyChainsConfiguration.get(i);
					String name = JSONobject.getString("keychainName");
					keys.setKeychainName(name);
					keys.setKeychainPath(JSONobject.getString("keychainPath"));
					keys.setKeychainPassword(JSONobject.getString("keychainPassword"));
					if ((StringUtils.isNotEmpty(defaultKeychain)) && (name.equalsIgnoreCase(defaultKeychain))) {
						keys.setDefaultKeychain(true);
					}
					keyChains.add(keys);
				}
			}
			finalOutput = responseDataEvaluation(responseData, null, keyChains, RESPONSE_STATUS_SUCCESS, PHR800027);
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810048);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (org.codehaus.jettison.json.JSONException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810048);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
	/**
	 * @return
	 * @throws PhrescoException
	 */
	@POST
	@Path("/global")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setGlobalConfiguration(GlobalSettings globalInfo, @QueryParam(REST_QUERY_EMAIL_ADDRESS) String emailAddress, @QueryParam(REST_QUERY_EMAIL_PASSWORD) String emailPassword , @QueryParam(REST_QUERY_URL) String url, @QueryParam(REST_QUERY_USER_NAME) String username , @QueryParam(REST_QUERY_PASSWORD) String password, @QueryParam(REST_QUERY_TFS_URL) String tfsUrl) throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		boolean setGlobalConfiguration = false;
		try {
			File pomFile = new File(FrameworkUtil.getJenkinsPOMFilePath());
			PomProcessor pom = new PomProcessor(pomFile);
			if (StringUtils.isNotEmpty(url)) {
				pom.setProperty(URL, url);
				pom.setProperty(USER_NAME, username);
				pom.setProperty(FrameworkConstants.PASSWORD, password);
			} else {
				pom.removeProperty(URL);
				pom.removeProperty(USER_NAME);
				pom.removeProperty(FrameworkConstants.PASSWORD);
			}
			pom.save();
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			String jenkinsUrl = FrameworkUtil.getLocaJenkinsUrl();
			//To enable remote global configuration changing
//			String jenkinsUrl = FrameworkUtil.getJenkinsUrl();
			String submitUrl = jenkinsUrl + FrameworkConstants.FORWARD_SLASH + CONFIG_SUBMIT;
			org.json.JSONArray JSONarray = new org.json.JSONArray();
			List<RepoDetail> repoDetails = globalInfo.getRepoDetails();
			if(CollectionUtils.isNotEmpty(repoDetails)) {
				for (RepoDetail repodetail : repoDetails) {
					org.json.JSONObject confluenceObj = new org.json.JSONObject();
					confluenceObj.put(CONFLUENCE_SITE_URL, repodetail.getRepoUrl());
					confluenceObj.put(CONFLUENCE_USERNAME, repodetail.getUserName());
					confluenceObj.put(FrameworkConstants.PASSWORD, repodetail.getPassword());
					JSONarray.put(confluenceObj);
				}
			}
			org.json.JSONArray testFlightJSONarray = new org.json.JSONArray();
			List<TestFlight> testFlightConfigs = globalInfo.getTestFlight();
			if(CollectionUtils.isNotEmpty(testFlightConfigs)) {
				for (TestFlight testFlight : testFlightConfigs) {
					org.json.JSONObject testFlightObj = new org.json.JSONObject();
					testFlightObj.put(TESTFLIGHT_TOKEN_NAME, testFlight.getTokenPairName());
					testFlightObj.put(API_TOKEN, testFlight.getApiToken());
					testFlightObj.put(TEAM_TOKEN, testFlight.getTeamToken());
					testFlightJSONarray.put(testFlightObj);
				}
			}
			
			org.json.JSONArray keyChainJSONarray = new org.json.JSONArray();
			List<Keychain> keyChainConfigs = globalInfo.getKeychains();
			if(CollectionUtils.isNotEmpty(keyChainConfigs)) {
				for (Keychain keyChain : keyChainConfigs) {
					org.json.JSONObject keyChainObj = new org.json.JSONObject();
					keyChainObj.put("keychainName", keyChain.getKeychainName());
					keyChainObj.put("keychainPath", keyChain.getKeychainPath());
					keyChainObj.put("keychainPassword", keyChain.getKeychainPassword());
					keyChainObj.put("inSearchPath", true);
					keyChainJSONarray.put(keyChainObj);
				}
			}
			
			setGlobalConfiguration = ciManager.setGlobalConfiguration(jenkinsUrl, submitUrl, JSONarray, emailAddress, emailPassword, testFlightJSONarray, keyChainJSONarray,tfsUrl);
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, setGlobalConfiguration, RESPONSE_STATUS_ERROR, PHR810034);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (org.json.JSONException e) {
			finalOutput = responseDataEvaluation(responseData, e, setGlobalConfiguration, RESPONSE_STATUS_ERROR, PHR810034);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, null, setGlobalConfiguration, RESPONSE_STATUS_SUCCESS, PHR800022);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
		finalOutput = responseDataEvaluation(responseData, null, setGlobalConfiguration, RESPONSE_STATUS_SUCCESS, PHR800022);
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
	/**
	 * @param buildDownloadUrl
	 * @param downloadJobName
	 * @param customerId
	 * @param projectId
	 * @param appDir
	 * @param continuousName
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/downloadBuild")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response CIBuildDownload(@QueryParam(REST_QUERY_BUILD_DOWNLOAD_URL) String buildDownloadUrl,
			@QueryParam(REST_QUERY_DOWNLOAD_JOB_NAME) String downloadJobName, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir, 
			@QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule)
			throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		ResponseInfo finalOutput = null;
		String module = "";
		String splitPath = "";
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if(StringUtils.isNotEmpty(rootModule)) {
				module = appDir;
				appDir = rootModule;
			} 
			
			if (StringUtils.isNotEmpty(appDir)) {
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				appDir = splitPath;
				ProjectInfo projectInfo = Utility.getProjectInfo(Utility.getProjectHome() + appDir, "");
				if (projectInfo != null) {
					projectId = projectInfo.getId();
				}
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			CIJob job = ciManager.getJob(downloadJobName, projectId, ciJobInfo, continuousName);
			// Get it from web path
			String jenkinsUrl = FrameworkUtil.getJenkinsUrl(job) + "/job/";
			buildDownloadUrl = buildDownloadUrl.replace(" ", "%20");
			String url = jenkinsUrl+job.getJobName()+"/ws/"+buildDownloadUrl;
			finalOutput = responseDataEvaluation(responseData, null, url, RESPONSE_STATUS_SUCCESS, PHR800010);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (Exception e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810035);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		}
	}
	
	/**
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/presetup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response preSetup() throws PhrescoException {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		ResponseInfo<String> finalOutput;
		boolean exists = false;
		String status = PHR810047;
		try {
			File warFile = new File(Utility.getJenkinsHome() + File.separator + TARGET_DIR + File.separator + "ci.war");
			if (warFile.exists()) {
				exists = true;
				status = PHR810046;
			} 
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		finalOutput = responseDataEvaluation(responseData, null, exists, RESPONSE_STATUS_SUCCESS, status);
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
	
    @GET
    @Path("/sonarpresetup")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response presonarSetup() throws PhrescoException {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		ResponseInfo<String> finalOutput;
		boolean exists = false;
		String status = PHR810050;
		try {
			File warFile = new File(Utility.getPhrescoHome() + "/workspace/tools/sonar/target/tomcat/webapps/sonar");
			if (warFile.exists()) {
				exists = true;
				status = PHR810049;
			} 
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		finalOutput = responseDataEvaluation(responseData, null, exists, RESPONSE_STATUS_SUCCESS, status);
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}
	
    
	/**
	 * @return
	 */
	@GET
	@Path("/isAlive")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response localJenkinsLocalAlive() {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		String localJenkinsAlive = "";
		ResponseInfo<String> finalOutput;
		String statusCode= "";
		try {
			String jenkinsUrl = FrameworkUtil.getJenkinsUrl();
			URL url = new URL(jenkinsUrl);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int code = httpConnection.getResponseCode();
			localJenkinsAlive = code + "";
			statusCode = PHR800011;
		} catch (ConnectException e) {
			localJenkinsAlive = CODE_404;
			statusCode = PHR810017;
		} catch (Exception e) {
			localJenkinsAlive = CODE_404;
			statusCode = PHR810017;
		}
		finalOutput = responseDataEvaluation(responseData, null, localJenkinsAlive, RESPONSE_STATUS_SUCCESS, statusCode);
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
		.build();
	}

	/**
	 * @param jobName
	 * @param continuousName
	 * @param projectId
	 * @param appDir
	 * @return
	 */
	@GET
	@Path("/jobStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getStatus(@QueryParam(REST_QUERY_NAME) String jobName, @QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir,
			@QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule, @QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		ResponseInfo<Boolean> finalOutput = null;
		String splitPath = "";
		try {
			String module = "";
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJob job = null;
			
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			List<CIJob> ciJobs = Utility.getJobs(continuousName, projectId, ciJobInfo);
			for (CIJob ciJob : ciJobs) {
				if(ciJob.getJobName().equals(jobName)) {
					job = ciJob;
				}
			}
			String jobStatus = ciManager.getJobStatus(job);
			finalOutput = responseDataEvaluation(responseData, null, jobStatus, RESPONSE_STATUS_SUCCESS, PHR800012);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810018);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810018);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}
	
	@GET
	@Path("/lastBuildStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getLastBuildStatus(@QueryParam(REST_QUERY_NAME) String jobName, @QueryParam(REST_QUERY_CONTINOUSNAME) String continuousName,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDir, 
			@QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule, @QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		ResponseInfo<Boolean> finalOutput = null;
		String module = "";
		String splitPath = "";
		try {
			if(projectId == null || projectId.equals("null") || projectId.equals("")) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				} 
				splitPath = Utility.splitPathConstruction(appDir);
				if(StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(splitPath);
				projectId = projectInfo.getId();
			}
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJob job = null;
			
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			String globalInfo = "";
			if (CollectionUtils.isNotEmpty(appInfos)) {
				globalInfo = appInfos.get(0).getAppDirName();
				globalInfo = Utility.splitPathConstruction(globalInfo);
			}
			if (StringUtils.isNotEmpty(appDir)) {
				appDir = splitPath;
			}
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
			List<CIJob> ciJobs = Utility.getJobs(continuousName, projectId, ciJobInfo);
			for (CIJob ciJob : ciJobs) {
				if(ciJob.getJobName().equals(jobName)) {
					job = ciJob;
				}
			}
			CIBuild build = ciManager.getStatusInfo(job);
			finalOutput = responseDataEvaluation(responseData, null, build, RESPONSE_STATUS_SUCCESS, PHR800023);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810036);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch (PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810036);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}
	
	@GET
	@Path("/pipeline")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pipeLineValidation(@QueryParam(REST_QUERY_NAME) String pipelineName, @QueryParam(REST_QUERY_PROJECTID) String projectId, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDir, @QueryParam(REST_QUERY_CUSTOMERID) String customerId, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		ResponseInfo<Boolean> finalOutput = null;
		boolean nameExist = false;
		String module = "";
		String splitPath = "";
		try {
		List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
		String globalInfo = "";
		if (CollectionUtils.isNotEmpty(appInfos)) {
			globalInfo = appInfos.get(0).getAppDirName();
			globalInfo = Utility.splitPathConstruction(globalInfo);
		}
		CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		if(StringUtils.isNotEmpty(rootModule)) {
			module = appDir;
			appDir = rootModule;
		} 
		if (StringUtils.isNotEmpty(appDir)) {
		splitPath = Utility.splitPathConstruction(appDir);
		}
		if(StringUtils.isNotEmpty(rootModule)) {
			splitPath = splitPath + File.separator + module;
		}
		if (StringUtils.isNotEmpty(appDir)) {
			appDir = splitPath;
		}
		List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appDir, globalInfo, READ);
		ProjectDelivery projectDelivery = null;
		if(CollectionUtils.isNotEmpty(ciJobInfo)) {
			projectDelivery = Utility.getProjectDelivery(projectId, ciJobInfo);
		}
		if (projectDelivery != null) {
			List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
			for (ContinuousDelivery continuousDelivery : continuousDeliveries) {
				if (continuousDelivery.getName().equalsIgnoreCase(pipelineName)) {
					nameExist = true;
				}
			}
		}
		finalOutput = responseDataEvaluation(responseData, null, nameExist, RESPONSE_STATUS_SUCCESS, PHR810042);
		return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810042);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		} catch(PhrescoPomException e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810042);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
			.build();
		}
	}
	
	//core method used to create jobs in contDelivery
	private boolean coreCreateJob(ContinuousDelivery continuousDelivery, String  projectId, String appDir, String userId, String customerId, HttpServletRequest request) throws PhrescoException {
		CIManagerImpl ciManager = null;
		boolean createJsonJobs = false;
		String splitPath = "";
		try {
			ciManager = new CIManagerImpl();
			ApplicationInfo applicationInfo = null;
			List<CIJob> ciJobs = new ArrayList<CIJob>(); 
			List<CIJob> jobs = continuousDelivery.getJobs();
			for(CIJob job : jobs) {	
				if(StringUtils.isNotEmpty(job.getAppDirName())) {
					if(StringUtils.isEmpty(appDir)) {
						//proj
						splitPath = Utility.splitPathConstruction(job.getAppDirName());
					} else {
						//app
						//change this
						splitPath = Utility.splitPathConstruction(appDir);
					}
					if (StringUtils.isNotEmpty(job.getModule())) {
						splitPath = splitPath + File.separator + job.getModule();
					}
					applicationInfo = FrameworkServiceUtil.getApplicationInfo(splitPath);
				}
				CIJob jobWithCmds = setPreBuildCmds(job,  applicationInfo, appDir, projectId, continuousDelivery.getName(), userId, customerId, request);
				boolean createJob = ciManager.createJob(job);
				if (createJob) {
					ciJobs.add(job);
				}
			}
			if (CollectionUtils.isNotEmpty(ciJobs)) {
				if(StringUtils.isNotEmpty(appDir)) {
					appDir = splitPath;
				}
				createJsonJobs = ciManager.createJsonJobs(continuousDelivery, ciJobs, projectId, appDir, "", WRITE);
				if (createJsonJobs && StringUtils.isEmpty(appDir)) {
					copyGlobalInfoFile(customerId, projectId);
				}
			}
			
			return createJsonJobs;
		} catch (PhrescoException e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}
	
	private void copyGlobalInfoFile(String customerId, String  projectId) throws PhrescoException {
		try {
			String srcDir = Utility.getProjectHome() + CI_JOB_INFO_NAME;
			List<String> appDirs =  new ArrayList<String>();
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			if (CollectionUtils.isNotEmpty(appInfos)) {
				for (ApplicationInfo appInfo : appInfos) {
					String dirName = Utility.splitPathConstruction(appInfo.getAppDirName());
					if(CollectionUtils.isNotEmpty(appInfo.getModules())) {
						List<ModuleInfo> modules = appInfo.getModules();
						for (ModuleInfo moduleInfo : modules) {
							appDirs.add(dirName + File.separator + moduleInfo.getCode());
						}
					}
					appDirs.add(dirName);	
				}
			}
			for (String dirName : appDirs) {
				String destDir = Utility.getProjectHome() + File.separator + dirName + FrameworkConstants.PHRESCO + File.separator + "global-"+CI_JOB_INFO_NAME;
				FileUtils.copyFile(new File(srcDir), new File(destDir));
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	public CIJob setPreBuildCmds(CIJob job, ApplicationInfo appInfo, String appDir, String id, String name, String userId, String customerId, HttpServletRequest request) throws PhrescoException, FileNotFoundException {
		try {
			List<String> preBuildStepCmds = new ArrayList<String>();

			String operation = job.getOperation();

			String mvncmd = "";

//			String pomFileName = Utility.getPhrescoPomFile(appInfo);
			boolean flag = false;
			if (StringUtils.isNotEmpty(job.getUrl())) {
				flag = true;
			}
			String pomFileName = Utility.constructSubPath(job.getAppDirName(), flag, job.getRepoType());
			job.setPomLocation(pomFileName);
			
			// jenkins configurations
			job.setJenkinsUrl(FrameworkUtil.getJenkinsHost()); // here we are setting the host only
			job.setJenkinsPort(FrameworkUtil.getJenkinsPort());
			job.setJenkinsPath(FrameworkUtil.getJenkinsPath());
			job.setJenkinsProtocol(FrameworkUtil.getJenkinsProtocol());
			
			List<Parameter> parameters = null;

			String integrationType = GLOBAL;
			if(StringUtils.isNotEmpty(appDir)) {
				integrationType = LOCAL;
			}
			Technology technology = null;
			if (StringUtils.isNotEmpty(userId)) {
				ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
				technology = serviceManager.getTechnology(appInfo.getTechInfo().getId());
			}
			String prebuildCmd = "";
			String operationName = "";
			String path = "";
			String splitPath = Utility.splitPathConstruction(job.getAppDirName());
			if(StringUtils.isNotEmpty(job.getModule())) {
				splitPath = splitPath + File.separator +job.getModule();
			} 
			
			File pomFileLocation = Utility.getPomFileLocation(Utility.getProjectHome() + job.getAppDirName(), "");
			PomProcessor pom = new PomProcessor(pomFileLocation);
			String splitDir = pom.getProperty(POM_PROP_KEY_SPLIT_PHRESCO_DIR);
			String srcDir = pom.getProperty(POM_PROP_KEY_SPLIT_SRC_DIR);
			String testDir = pom.getProperty(POM_PROP_KEY_SPLIT_TEST_DIR);
			String localPom = POM_XML;
			if (appInfo != null) {
				localPom = appInfo.getPomFile();
				if (StringUtils.isNotEmpty(appInfo.getPhrescoPomFile())) {
					localPom = appInfo.getPhrescoPomFile();
				}
			}
			if (BUILD.equalsIgnoreCase(operation) || DEVICE_BUILD.equalsIgnoreCase(operation)) {
				if(DEVICE_BUILD.equalsIgnoreCase(operation)) {
					job.setEnablePostBuildStep(true);
				}
				// enable archiving
				job.setEnableArtifactArchiver(true);
				// if the enable build release option is choosed in UI, the file pattenr value will be used
				List<String> modules = FrameworkServiceUtil.getProjectModules(job.getAppDirName());
				String releasePattern = "";
				if (!StringUtils.isNotEmpty(splitDir) && StringUtils.isNotEmpty(testDir) && !POM_XML.equals(localPom)) {
					releasePattern = srcDir + FILE_SEPARATOR;
				} else if ((StringUtils.isNotEmpty(splitDir) || StringUtils.isNotEmpty(srcDir) || StringUtils.isNotEmpty(testDir)) && POM_XML.equals(localPom)) {
					releasePattern = srcDir + FILE_SEPARATOR;
				} else if ((StringUtils.isNotEmpty(splitDir) || StringUtils.isNotEmpty(srcDir) || StringUtils.isNotEmpty(testDir)) && !POM_XML.equals(localPom)) {
					releasePattern = splitDir + FILE_SEPARATOR;
				} 
				
				if (StringUtils.isNotEmpty(job.getModule())) {
					releasePattern =  releasePattern + job.getModule() + FILE_SEPARATOR;
					job.setCollabNetFileReleasePattern(releasePattern + CI_BUILD_EXT);
				} else if (CollectionUtils.isEmpty(modules)) {
					job.setCollabNetFileReleasePattern(releasePattern + CI_BUILD_EXT);
				} else {
					job.setEnableArtifactArchiver(false);
				}
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_PACKAGE, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if(phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_PACKAGE);
				}
				ActionType actionType = ActionType.BUILD;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_PACKAGE;
			} else if (DEPLOY.equals(operation)) {
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_DEPLOY, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_DEPLOY);
				}
				ActionType actionType = ActionType.DEPLOY;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_DEPLOY;
			} else if (PDF_REPORT.equals(operation)) {
				// for pdf report attachment patterns
				// based on test report type, it should be specified
				//TODO validation for TestType
				String attacheMentPattern;
				if(job.getTestType().equalsIgnoreCase("All")) {
					attacheMentPattern = "cumulativeReports";
				} else {
					attacheMentPattern = job.getTestType();
				}
				// enable archiving
				job.setEnableArtifactArchiver(true);
				job.setTechnologyName(technology.getName());
				String attachPattern = "do_not_checkin/archives/" + attacheMentPattern + "/*.pdf";
				job.setAttachmentsPattern(attachPattern); //do_not_checkin/archives/cumulativeReports/*.pdf
				// if the enable build release option is choosed in UI, the file pattenr value will be used
				List<String> modules = FrameworkServiceUtil.getProjectModules(job.getAppDirName());
				String releasePattern = "";
				if (!StringUtils.isNotEmpty(splitDir) && StringUtils.isNotEmpty(testDir) && !POM_XML.equals(localPom)) {
					releasePattern = srcDir + FILE_SEPARATOR;
				} else if ((StringUtils.isNotEmpty(splitDir) || StringUtils.isNotEmpty(srcDir) || StringUtils.isNotEmpty(testDir)) && POM_XML.equals(localPom)) {
					releasePattern = srcDir + FILE_SEPARATOR;
				} else if ((StringUtils.isNotEmpty(splitDir) || StringUtils.isNotEmpty(srcDir) || StringUtils.isNotEmpty(testDir)) && !POM_XML.equals(localPom)) {
					releasePattern = splitDir + FILE_SEPARATOR;
				} 
				
				if (StringUtils.isNotEmpty(job.getModule())) {
					releasePattern = releasePattern + job.getModule() + FILE_SEPARATOR;
					job.setCollabNetFileReleasePattern(releasePattern  + attachPattern);
				} else if (CollectionUtils.isEmpty(modules)) {
					job.setCollabNetFileReleasePattern(releasePattern + attachPattern);
				} else {
					job.setEnableArtifactArchiver(false);
				}
				// here we can set necessary values in request and we can change object value as well...
				// getting sonar url
				
//				FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
				String usingSonarUrl = FrameworkServiceUtil.getSonarURL(request);
				// logo and theme
				//				String logoImageString = getLogoImageString();
				//				String themeColorJson = getThemeColorJson();

				// object change
				job.setSonarUrl(usingSonarUrl);
				job.setLogo(getLogoImageString(userId, customerId));
				job.setTheme(getThemeColorJson(userId, customerId));

				// set values in request
//								sonarUrl = usingSonarUrl;
				//				logo = logoImageString;
				//				theme = themeColorJson;
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_PDF_REPORT, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_PDF_REPORT);
				}
				ActionType actionType = ActionType.PDF_REPORT;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_PDF_REPORT;
			} else if (CODE_VALIDATION.equals(operation)) {
				FrameworkServiceUtil fUtil = new FrameworkServiceUtil();
				StringBuilder sb = new StringBuilder();
				List<String> buildArgCmds = new ArrayList<String>();
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_VALIDATE_CODE, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_VALIDATE_CODE);
				}
				ActionType actionType = ActionType.CODE_VALIDATE;
				mvncmd =  actionType.getActionType().toString();
				List<String> props = fUtil.getProps(buildArgCmds);
				if(CollectionUtils.isNotEmpty(props)) {
				        for(String sonarParams : props){
				            sb.append(Constants.SPACE).append(sonarParams); 
				        }
				        mvncmd = mvncmd + sb.toString();   
				}
				operationName = Constants.PHASE_VALIDATE_CODE;
			} else if (UNIT_TEST.equals(operation)) {
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_UNIT_TEST, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_UNIT_TEST);
				}
				ActionType actionType = ActionType.UNIT_TEST;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_UNIT_TEST;
				if (job.isCoberturaPlugin()) {
					job.setEnablePostBuildStep(true);
					List<String> postBuildStepCmds = new ArrayList<String>();
					postBuildStepCmds.add(MAVEN_SEP_COBER);
					postBuildStepCmds.add("shell#SEP#cd ${WORKSPACE}\n${GCOV_HOME} -r ${WORKSPACE} -x -o coverage.xml");
					if (CollectionUtils.isNotEmpty(postBuildStepCmds)) {
						job.setPostbuildStepCommands(postBuildStepCmds);
					}
				}
			} else if (FUNCTIONAL_TEST.equals(operation)) {
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_FUNCTIONAL_TEST, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					com.photon.phresco.framework.commons.FrameworkUtil frameworkUtil = com.photon.phresco.framework.commons.FrameworkUtil.getInstance();
					String seleniumToolType = "";
					seleniumToolType = frameworkUtil.getSeleniumToolType(Utility.getProjectHome() + job.getAppDirName(), job.getModule());
	
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_FUNCTIONAL_TEST + HYPHEN + seleniumToolType);
				}
				ActionType actionType = ActionType.FUNCTIONAL_TEST;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_FUNCTIONAL_TEST;
			} else if (LOAD_TEST.equals(operation)) {
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_LOAD_TEST, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_LOAD_TEST);
				}
				ActionType actionType = ActionType.LOAD_TEST;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_LOAD_TEST;
			} else if (PERFORMANCE_TEST_CI.equals(operation)) {
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_PERFORMANCE_TEST, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_PERFORMANCE_TEST);
				}
				ActionType actionType = ActionType.PERFORMANCE_TEST;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_PERFORMANCE_TEST;
			} else if (COMPONENT_TEST_CI.equals(operation)) {
				path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, Constants.PHASE_COMPONENT_TEST, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, Constants.PHASE_COMPONENT_TEST);
				}
				ActionType actionType = ActionType.COMPONENT_TEST;
				mvncmd =  actionType.getActionType().toString();
				operationName = Constants.PHASE_COMPONENT_TEST;
			} else if (PHASE_RELEASE.equals(operation)) {
				/*path = FrameworkServiceUtil.getPhrescoPluginInfoFilePath(Constants.PHASE_CI, PHASE_RELEASE, splitPath);
				File phrescoPluginInfoFilePath = new File(path);
				if (phrescoPluginInfoFilePath.exists()) {
					MojoProcessor mojo = new MojoProcessor(phrescoPluginInfoFilePath);
					//To get maven build arguments
					parameters = FrameworkServiceUtil.getMojoParameters(mojo, PHASE_RELEASE);
				}*/
				ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
				Customer customer = serviceManager.getCustomer(customerId);
				com.photon.phresco.commons.model.RepoInfo repoInfo = customer.getRepoInfo();
				ActionType actionType = ActionType.RELEASE;
				mvncmd =  actionType.getActionType().toString();
				mvncmd = mvncmd + Constants.SPACE + HYPHEN_DEV_VERSION + job.getDevelopmentVersion() + Constants.SPACE + HYPHEN_REL_VERSION + 
						job.getReleaseVersion() + Constants.SPACE + HYPHEN_TAG + job.getTagName() + Constants.SPACE + HYPHEN_USERNAME +
						job.getReleaseUsername() + Constants.SPACE + HYPHEN_PASSWORD + job.getReleasePassword() + Constants.SPACE +
						HYPHEN_MESSAGE + "\"" + job.getReleaseMessage() + "\"" + Constants.SPACE + HYPHEN_JOBNAME + job.getJobName()
						+ Constants.SPACE + HYPHEN_APPDIR_NAME + job.getAppDirName() + Constants.SPACE + HYPHEN_REPO_USERNAME + 
						repoInfo.getRepoUserName() + Constants.SPACE + HYPHEN_REPO_PWD + repoInfo.getRepoPassword();
				operationName = PHASE_RELEASE;
				if (Boolean.parseBoolean(job.getSkipTests())) {
					mvncmd = mvncmd + Constants.SPACE + SKIP_TESTS;
				}
			} else if (PHASE_NEXUS_DEPLOY.equals(operation)) {
				ActionType actionType = ActionType.NEXUS_DEPLOY;
				mvncmd =  actionType.getActionType().toString();
				mvncmd = mvncmd + Constants.SPACE + "-Dusername=" + job.getNexusUsername() + Constants.SPACE + "-Dpassword=" + job.getNexusPassword()+ Constants.SPACE + HYPHEN_JOBNAME + job.getJobName()
				+ Constants.SPACE + HYPHEN_APPDIR_NAME + job.getAppDirName(); 
				operationName = PHASE_NEXUS_DEPLOY;
				if (Boolean.parseBoolean(job.getSkipTests())) {
					mvncmd = mvncmd + Constants.SPACE + SKIP_TESTS;
				}
			}
			
			prebuildCmd = CI_PRE_BUILD_STEP + HYPHEN_GOAL + Constants.PHASE_CI + HYPHEN_PHASE + operationName +
			CREATIONTYPE + integrationType + ID + id + CONTINUOUSNAME + STR_DOUBLE_QUOTES + name + STR_DOUBLE_QUOTES; 
			if(!POM_NAME.equals(pomFileName)) {
				prebuildCmd = prebuildCmd + HYPHEN_F_SPACE + pomFileName; 
			}
			// To handle multi module project
			prebuildCmd = prebuildCmd + FrameworkConstants.SPACE + HYPHEN_N; 
			
			if(StringUtils.isNotEmpty(job.getModule())) {
				mvncmd = mvncmd + FrameworkConstants.SPACE  + DMODULE_NAME+job.getModule();
				prebuildCmd = prebuildCmd + FrameworkConstants.SPACE  + DMODULE_NAME+job.getModule();
			}


			List<String> buildArgCmds = FrameworkServiceUtil.getMavenArgCommands(parameters);
			if(!POM_NAME.equals(pomFileName)) {
				buildArgCmds.add(HYPHEN_F);
				buildArgCmds.add(pomFileName);
			}
			if (!CollectionUtils.isEmpty(buildArgCmds)) {
				for (String buildArgCmd : buildArgCmds) {
					mvncmd = mvncmd + FrameworkConstants.SPACE + buildArgCmd;
				}
			}

			// for build job alone existing do_not_checkin need to be cleared
			// For pdf report, it should clear existing pdf reports in do_not_checkin folder
			if (BUILD.equals(operation) || PDF_REPORT.equals(operation)) {
				mvncmd = CI_PROFILE + mvncmd;
			}
			if (!DEVICE_BUILD.equalsIgnoreCase(operation)) {
				preBuildStepCmds.add(prebuildCmd);
			}
			// To handle multi module project
			mvncmd = mvncmd + FrameworkConstants.SPACE + HYPHEN_N;
			job.setMvnCommand(mvncmd);
			// prebuild step enable
			job.setEnablePreBuildStep(true);

			job.setPrebuildStepCommands(preBuildStepCmds);		
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return job;
	}
	
	protected String getLogoImageString(String username, String customerId) throws PhrescoException {
		String encodeImg = "";
		try {
			InputStream fileInputStream = null;
			fileInputStream = getServiceManager(username).getIcon(customerId);
			if (fileInputStream != null) {
				byte[] imgByte = null;
				imgByte = IOUtils.toByteArray(fileInputStream);
				byte[] encodedImage = Base64.encodeBase64(imgByte);
				encodeImg = new String(encodedImage);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return encodeImg;
	}
	
	protected ServiceManager getServiceManager(String username) throws PhrescoException {
		this.serviceManager = RestBase.CONTEXT_MANAGER_MAP.get(username);
		return serviceManager;
	}

	protected String getThemeColorJson(String username, String customerId) throws PhrescoException {
		String themeJsonStr = "";
		try {
			Customer customer = getServiceManager(username).getCustomer(customerId);
			if (customer != null) {
				Map<String, String> frameworkTheme = customer.getFrameworkTheme();
				if (frameworkTheme != null) {
					Gson gson = new Gson();
					themeJsonStr = gson.toJson(frameworkTheme);
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return themeJsonStr;
	}
	
}
