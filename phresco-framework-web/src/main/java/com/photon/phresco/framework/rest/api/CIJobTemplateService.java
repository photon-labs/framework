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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.ContinuousDelivery;
import com.photon.phresco.commons.model.ProjectDelivery;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.commons.model.CIJobTemplate;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class CIJobTemplateService.
 */
@Path("/jobTemplates")
public class CIJobTemplateService extends RestBase implements ServiceConstants {
	
	/**
	 * List the jobTemplates.
	 *
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<List<CIJobTemplate>> responseData = new ResponseInfo<List<CIJobTemplate>>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJobTemplate> jobTemplates = new ArrayList<CIJobTemplate>();
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
			jobTemplates = ciManager.getJobTemplatesByProjId(projectId, appInfos);
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, null, "Job Templates listed successfully", jobTemplates);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, e,
					"Job Templates failed to list", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	/**
	 * List the jobTemplates by name.
	 *
	 * @param name the name
	 * @param appId the app id
	 * @param projectId the project id
	 * @param customerId the customer id
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@PathParam(REST_QUERY_NAME) String name, @QueryParam(REST_QUERY_APPID) String appId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJobTemplate> jobTemplates = null;
			if (StringUtils.isNotEmpty(name)) {
				List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
				jobTemplates = ciManager.getJobTemplatesByProjId(projectId, appInfos);
			}
			if (CollectionUtils.isNotEmpty(jobTemplates)) {
				for (CIJobTemplate jobTemplate : jobTemplates) {
					if (name.equalsIgnoreCase(jobTemplate.getName())) {
						ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template retrived successfully", jobTemplate);
						return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
					} 
				}
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Cannot Find jobTemplate", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e,
					"Job Templates failed to retrive", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	@GET
	@Path("/validate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateName(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId, @QueryParam(REST_QUERY_NAME) String name, @QueryParam(REST_QUERY_OLDNAME) String oldName) {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		try {
			boolean status = true;
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJobTemplate> jobTemplates = null;
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
			jobTemplates = ciManager.getJobTemplatesByProjId(projectId, appInfos);
			if (CollectionUtils.isNotEmpty(jobTemplates)) {
				for (CIJobTemplate jobTemplate : jobTemplates) {
					if (StringUtils.isNotEmpty(name) && name.equalsIgnoreCase(jobTemplate.getName()) && (!jobTemplate.getName().equalsIgnoreCase(oldName))) {
						status = false;
					}
				}
			}
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, "", status);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, "", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	/**
	 * Adds the job template.
	 *
	 * @param ciJobTemplate the ci job template
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(CIJobTemplate ciJobTemplate, @QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJobTemplate> jobTemplates = Arrays.asList(ciJobTemplate);
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
			ciManager.createJobTemplates(jobTemplates, false, appInfos);
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null,
					"Job Template added successfully", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e,
					"Job Templates failed to add", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	/**
	 * Update the existing jobtemplate.
	 *
	 * @param ciJobTemplate the ci job template
	 * @return the response
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(CIJobTemplate ciJobTemplate, @QueryParam(REST_QUERY_OLDNAME) String oldName, @QueryParam(REST_QUERY_PROJECTID) String projId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projId);
			boolean updateJobTemplate = ciManager.updateJobTemplate(ciJobTemplate, oldName, projId, appInfos);
			if (!updateJobTemplate) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template updation failed", updateJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} 			
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template updated successfully", updateJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e,
					"Job Templates updation failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	/**
	 * Delete the job template.
	 *
	 * @param name the name
	 * @return the response
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam(REST_QUERY_NAME) String name,  @QueryParam(REST_QUERY_PROJECTID) String projId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIJobTemplate ciJobTemplate = null;
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projId);
			List<CIJobTemplate> jobTemplates = ciManager.getJobTemplatesByProjId(projId, appInfos);
			boolean validate = validate(name, jobTemplates, projId);
			if(validate) {
				boolean deleteJobTemplate = ciManager.deleteJobTemplate(name, projId, appInfos);
				if (deleteJobTemplate) {
					ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null,
							"Job Template deleted successfully", ciJobTemplate);
					return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
							.build();
				}
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null,
						"Job Templates deletion failed", ciJobTemplate);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null,
						"Job is created using "+ name +" Template", validate);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e,
					"Job Templates deletion failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	private boolean validate(String name, List<CIJobTemplate> jobTemplates, String projId) throws PhrescoException {
		CIJobTemplate jobTemplate = new CIJobTemplate();
		for (CIJobTemplate ciJobTemplate2 : jobTemplates) {
			if(ciJobTemplate2.getName().equals(name)) {
				jobTemplate = ciJobTemplate2;
			}
		}
		CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		List<ProjectDelivery> projectDeliveries = ciManager.getCiJobInfo(null);
		if (CollectionUtils.isNotEmpty(projectDeliveries)) {
			ProjectDelivery projectDelivery = Utility.getProjectDelivery(projId, projectDeliveries);
			if (projectDelivery != null) {
				List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
				if (CollectionUtils.isNotEmpty(continuousDeliveries)) {
					for (ContinuousDelivery continuousDelivery : continuousDeliveries) {
						List<CIJob> jobs = continuousDelivery.getJobs();
						for (CIJob ciJob : jobs) {
							if(ciJob.getTemplateName().equals(name)) {
								return false;
							}
						}
					}
				}
			}
		}
		
		List<String> appIds = jobTemplate.getAppIds();
		for (String appId : appIds) {
			List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appId);
			if (CollectionUtils.isNotEmpty(ciJobInfo)) {
				ProjectDelivery appDelivery = Utility.getProjectDelivery(projId, ciJobInfo);
				if (appDelivery != null) {
					List<ContinuousDelivery> continuousDelivery = appDelivery.getContinuousDeliveries();
					for (ContinuousDelivery cd : continuousDelivery) {
						List<CIJob> jobs = cd.getJobs();
						for (CIJob ciJob : jobs) {
							if(ciJob.getTemplateName().equals(name)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Gets the job templates by environemnt.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @param envName the env name
	 * @return the job templates by environemnt
	 */
	@GET
	@Path("/getJobTemplatesByEnvironment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobTemplatesByEnvironemnt(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_EVN_NAME) String envName, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDir) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		Map<JSONObject, List<CIJobTemplate>> jobTemplateMap = new HashMap<JSONObject, List<CIJobTemplate>>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			if(StringUtils.isNotEmpty(appDir)) {
				ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDir);
				getJobTemplateByAppDir(envName, jobTemplateMap, ciManager, applicationInfo);
			} else {
				List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
				for (ApplicationInfo appInfo : appInfos) {
					getJobTemplateByAppDir(envName, jobTemplateMap, ciManager, appInfo);
				}
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null,
					"Job Templates Fetched Successfully", jobTemplateMap);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e,
					"Job Templates Failed to Fetch", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates Failed to Fetch", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (JSONException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates Failed to Fetch", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	private void getJobTemplateByAppDir(String envName,
			Map<JSONObject, List<CIJobTemplate>> jobTemplateMap,
			CIManager ciManager, ApplicationInfo applicationInfo)
			throws ConfigurationException, PhrescoException, JSONException {
		List<Environment> environments = getEnvironments(applicationInfo);
		for (Environment environment : environments) {
			if (envName.equals(environment.getName())) {
				List<CIJobTemplate> jobTemplates = ciManager.getJobTemplatesByAppId(applicationInfo.getName());
				if (CollectionUtils.isNotEmpty(jobTemplates)) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("appName", applicationInfo.getName());
					jsonObject.put("appDirName", applicationInfo.getAppDirName());
					jobTemplateMap.put(jsonObject, jobTemplates);
				}
			}
		}
	}

	/**
	 * Gets the environments.
	 *
	 * @param appInfo the app info
	 * @return the environments
	 * @throws ConfigurationException the configuration exception
	 */
	private List<Environment> getEnvironments(ApplicationInfo appInfo) throws ConfigurationException {
		List<Environment> environments = new ArrayList<Environment>();
		String configFile = FrameworkServiceUtil.getConfigFileDir(appInfo.getAppDirName());
		ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
		environments = configManager.getEnvironmentsAlone();
		return environments;
	}
}
