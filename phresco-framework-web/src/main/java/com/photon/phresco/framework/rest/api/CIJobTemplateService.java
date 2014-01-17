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
import java.io.IOException;
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
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.CIJob;
import com.photon.phresco.commons.model.CIJobTemplate;
import com.photon.phresco.commons.model.ContinuousDelivery;
import com.photon.phresco.commons.model.ModuleInfo;
import com.photon.phresco.commons.model.ProjectDelivery;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.impl.util.FrameworkUtil;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class CIJobTemplateService.
 */
@Path("/jobTemplates")
public class CIJobTemplateService extends RestBase implements FrameworkConstants, ServiceConstants, ResponseCodes {
	
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
			FrameworkUtil.getAppInfos(customerId, projectId);
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			jobTemplates = ciManager.getJobTemplatesByProjId(projectId, appInfos);
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, null,
					jobTemplates, RESPONSE_STATUS_SUCCESS, PHR800013);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (Exception e) {
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR810019);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();
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
				List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
				jobTemplates = ciManager.getJobTemplatesByProjId(projectId, appInfos);
			}
			if (CollectionUtils.isNotEmpty(jobTemplates)) {
				for (CIJobTemplate jobTemplate : jobTemplates) {
					if (name.equalsIgnoreCase(jobTemplate.getName())) {
						ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, jobTemplate, RESPONSE_STATUS_SUCCESS, PHR800014);
						
						return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
					} 
				}
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, null, RESPONSE_STATUS_SUCCESS, PHR810020);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
			
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810021);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();
		}
	}

	@GET
	@Path("/validate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateName(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId, 
			@QueryParam(REST_QUERY_NAME) String name, @QueryParam(REST_QUERY_OLDNAME) String oldName) {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		try {
			boolean validName = validName(projectId, customerId, name, oldName);
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, validName, RESPONSE_STATUS_SUCCESS, PHR800015);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (Exception e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810022);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		}
	}
	
	private boolean validName(String projectId, String customerId, String name, String oldName) throws PhrescoException {
		boolean status = true;
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJobTemplate> jobTemplates = null;
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			jobTemplates = ciManager.getJobTemplatesByProjId(projectId, appInfos);
			if (CollectionUtils.isNotEmpty(jobTemplates)) {
				for (CIJobTemplate jobTemplate : jobTemplates) {
					if (StringUtils.isNotEmpty(name) && name.equalsIgnoreCase(jobTemplate.getName()) && (!jobTemplate.getName().equalsIgnoreCase(oldName))) {
						status = false;
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return status;
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
		boolean errorFound = false;
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<String> appIds = ciJobTemplate.getAppIds();
			List<CIJobTemplate> templates = new ArrayList<CIJobTemplate>();
			List<String> singleMods =  new ArrayList<String>();
			for (String appName : appIds) {
				String[] split = appName.split("#SEP#");
				if (split.length == 2) {
					String appNameSplitted = split[0];
					String appModuleName = split[1];
					CIJobTemplate tempModTemplate = new CIJobTemplate(ciJobTemplate);
					
					String moduleName = tempModTemplate.getName() + HYPHEN + appModuleName;
						tempModTemplate.setName(moduleName);
						tempModTemplate.setAppIds(Arrays.asList(appNameSplitted));
						tempModTemplate.setModule(appModuleName);
						templates.add(tempModTemplate);
				} else if (split.length == 1) {
					singleMods.add(split[0]);
				}
			}
			if (CollectionUtils.isNotEmpty(singleMods)) {
				ciJobTemplate.setAppIds(singleMods);
				templates.add(ciJobTemplate);
			}
				
			for (CIJobTemplate template : templates) {
				boolean validName = validName(projectId, customerId, template.getName(), "");
				if (!validName) {
					errorFound = true;
				}
			}
			
			if (errorFound) {
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_SUCCESS, PHR810041);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,
						ALL_HEADER).build();
			}
			
			for (CIJobTemplate template : templates) {
				List<CIJobTemplate> jobTemplates = Arrays.asList(template);
				List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
				ciManager.createJobTemplates(jobTemplates, false, appInfos);
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, ciJobTemplate, RESPONSE_STATUS_SUCCESS, PHR800016);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR810023);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();
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
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projId);
			
			List<CIJobTemplate> jobTemplates = ciManager.getJobTemplatesByProjId(projId, appInfos);
			boolean validate = validate(ciJobTemplate.getName(), jobTemplates, projId, appInfos, "update", ciJobTemplate, null, oldName);
			if(validate) {
				boolean updateJobTemplate = ciManager.updateJobTemplate(ciJobTemplate, oldName, projId, appInfos);
				if (!updateJobTemplate) {
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, updateJobTemplate, RESPONSE_STATUS_ERROR, PHR810024);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
				} 			
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, updateJobTemplate, RESPONSE_STATUS_SUCCESS, PHR800017);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
			} else {
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, validate, RESPONSE_STATUS_FAILURE, PHR810038);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
			}
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810024);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();
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
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projId);
			List<CIJobTemplate> jobTemplates = ciManager.getJobTemplatesByProjId(projId, appInfos);
			boolean validate = validate(name, jobTemplates, projId, appInfos, "delete", null, null, "");
			if(validate) {
				boolean deleteJobTemplate = ciManager.deleteJobTemplate(name, projId, appInfos);
				if (deleteJobTemplate) {
					ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, ciJobTemplate, RESPONSE_STATUS_SUCCESS, PHR800018);
					return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
							.build();
				}
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, ciJobTemplate, RESPONSE_STATUS_FAILURE, PHR810027);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
			} else {
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, validate, RESPONSE_STATUS_FAILURE, PHR810033);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
			}
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810027);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();
		}
	}


	@GET
	@Path("/validateApp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateJobTemplate(@QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(REST_QUERY_CUSTOMERID) String customerId, 
			@QueryParam(REST_QUERY_NAME) String name, @QueryParam(REST_QUERY_APPNAME) String appName) {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
			List<CIJobTemplate> jobTemplates = ciManager.getJobTemplatesByProjId(projectId, appInfos);
			boolean validate = validate(name, jobTemplates, projectId, appInfos, "appNameValidate", null, appName, "");
			String msg = appName + "#SEP#" + validate;
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, msg, RESPONSE_STATUS_SUCCESS, PHR800015);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (Exception e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810022);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		}
	}

	private boolean validate(String templateName, List<CIJobTemplate> jobTemplates, String projId, List<ApplicationInfo> appInfos, String operation, CIJobTemplate ciJobTemplate, String appName, String oldName) throws PhrescoException {
		String name = templateName;
		if (StringUtils.isNotEmpty(oldName)) {
			name = oldName;
		}
		CIJobTemplate jobTemplatz = new CIJobTemplate();
		
		for (CIJobTemplate jobtemplate : jobTemplates) {
			if(name.equals(jobtemplate.getName())) {
				jobTemplatz = jobtemplate;
			}
		}
		CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		String appDirName = appInfos.get(0).getAppDirName();
		List<ProjectDelivery> projectDeliveries = ciManager.getCiJobInfo(null, appDirName, Constants.READ);
		if (CollectionUtils.isNotEmpty(projectDeliveries)) {
			ProjectDelivery projectDelivery = Utility.getProjectDelivery(projId, projectDeliveries);
			if (projectDelivery != null) {
				List<ContinuousDelivery> continuousDeliveries = projectDelivery.getContinuousDeliveries();
				if (CollectionUtils.isNotEmpty(continuousDeliveries)) {
					for (ContinuousDelivery continuousDelivery : continuousDeliveries) {
						List<CIJob> jobs = continuousDelivery.getJobs();
						for (CIJob ciJob : jobs) {
							if(ciJob.getTemplateName().equals(name) && operation.equalsIgnoreCase("delete")) {
								return false;
							} else if (StringUtils.isNotEmpty(appName) && operation.equalsIgnoreCase("appNameValidate") && ciJob.getTemplateName().equals(name) && ciJob.getAppName().equals(appName)) {
								return false;
							} else if (ciJob.getTemplateName().equals(name) && operation.equalsIgnoreCase("update") && StringUtils.isEmpty(ciJob.getUpstreamApplication()) && !ciJobTemplate.isEnableRepo()) {
								return false;
							}
						}
					}
				}
			}
		}

		List<String> appIds = jobTemplatz.getAppIds();
		for (String appId : appIds) {
			for (ApplicationInfo appInfo : appInfos) {
				if(appInfo.getName().equals(appId)){
					List<ProjectDelivery> ciJobInfo = ciManager.getCiJobInfo(appInfo.getAppDirName(), "", Constants.READ);
					if (CollectionUtils.isNotEmpty(ciJobInfo)) {
						ProjectDelivery appDelivery = Utility.getProjectDelivery(projId, ciJobInfo);
						if (appDelivery != null) {
							List<ContinuousDelivery> continuousDelivery = appDelivery.getContinuousDeliveries();
							for (ContinuousDelivery cd : continuousDelivery) {
								List<CIJob> jobs = cd.getJobs();
								for (CIJob ciJob : jobs) {
									if(ciJob.getTemplateName().equals(name) && operation.equalsIgnoreCase("delete")) {
										return false;
									} else if (StringUtils.isNotEmpty(appName) && operation.equalsIgnoreCase("appNameValidate") && ciJob.getTemplateName().equals(name) && ciJob.getAppName().equals(appName)) {
										return false;
									} else if (ciJob.getTemplateName().equals(name) && operation.equalsIgnoreCase("update") && StringUtils.isEmpty(ciJob.getUpstreamApplication()) && !ciJobTemplate.isEnableRepo()) {
										return false;
									}
								}
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
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDir, @QueryParam(REST_QUERY_ROOT_MODULE_NAME) String rootModule) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		Map<JSONObject, List<CIJobTemplate>> jobTemplateMap = new HashMap<JSONObject, List<CIJobTemplate>>();
		String module = "";
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			if(StringUtils.isNotEmpty(appDir)) {
				if(StringUtils.isNotEmpty(rootModule)) {
					module = appDir;
					appDir = rootModule;
				}
				String splitPath = Utility.splitPathConstruction(appDir);
				if (StringUtils.isNotEmpty(rootModule)) {
					splitPath = splitPath + File.separator + module;
				}
				ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(splitPath);
				getJobTemplateByAppDir(envName, jobTemplateMap, ciManager, applicationInfo, null);
			} else {
				List<ApplicationInfo> appInfos = FrameworkUtil.getAppInfos(customerId, projectId);
				for (ApplicationInfo appInfo : appInfos) {
					if(CollectionUtils.isNotEmpty(appInfo.getModules())) {
						List<ModuleInfo> modules = appInfo.getModules();
						for (ModuleInfo moduleInfo : modules) {
							getJobTemplateByAppDir(envName, jobTemplateMap, ciManager, appInfo, moduleInfo.getCode());
						}
					} else {
						getJobTemplateByAppDir(envName, jobTemplateMap, ciManager, appInfo, null);
					}
					
				}
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, jobTemplateMap, RESPONSE_STATUS_SUCCESS, PHR800019);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810028);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,
					ALL_HEADER).build();
		} catch (ConfigurationException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810028);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (JSONException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810028);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		} catch (PhrescoPomException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR810028);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER).build();
		}
	}
	
	private void getJobTemplateByAppDir(String envName,
			Map<JSONObject, List<CIJobTemplate>> jobTemplateMap,
			CIManager ciManager, ApplicationInfo applicationInfo, String moduleName)
			throws ConfigurationException, PhrescoException, JSONException, PhrescoPomException {
		List<Environment> environments = getEnvironments(applicationInfo, moduleName);
		for (Environment environment : environments) {
			if (envName.equals(environment.getName())) {
				String appDirName = applicationInfo.getAppDirName();
				if(StringUtils.isNotEmpty(applicationInfo.getRootModule())) {
					moduleName = appDirName;
					appDirName = applicationInfo.getRootModule();
				}
				List<CIJobTemplate> jobTemplates = ciManager.getJobTemplatesByAppId(appDirName, applicationInfo.getName(), moduleName);
				if (CollectionUtils.isNotEmpty(jobTemplates)) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("appName", applicationInfo.getName());
					jsonObject.put("appDirName", appDirName);
					if(CollectionUtils.isNotEmpty(applicationInfo.getModules())) {
						jsonObject.put("moduleName", moduleName);
					}
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
	private List<Environment> getEnvironments(ApplicationInfo appInfo, String moduleName) throws ConfigurationException, PhrescoException, PhrescoPomException {
		List<Environment> environments = new ArrayList<Environment>();
		
		if(StringUtils.isNotEmpty(appInfo.getRootModule())) {
			String splitPath = Utility.splitPathConstruction(appInfo.getRootModule());
			String configFile = FrameworkServiceUtil.getConfigFileDir(splitPath, appInfo.getAppDirName());
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			environments = configManager.getEnvironmentsAlone();
		} else {
			String splitPath = Utility.splitPathConstruction(appInfo.getAppDirName());
			String configFile = FrameworkServiceUtil.getConfigFileDir(splitPath, moduleName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			environments = configManager.getEnvironmentsAlone();
		}
		
		return environments;
	}
}
