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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactGroupInfo;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.PropertyTemplate;
import com.photon.phresco.commons.model.SettingsTemplate;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.CronExpressionInfo;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class ConfigurationService.
 */
@Path("/configuration")
public class ConfigurationService extends RestBase implements FrameworkConstants, ServiceConstants {

	/** The Constant S_LOGGER. */
	private static final Logger S_LOGGER = Logger.getLogger(ConfigurationService.class);
	
	/** The is_debug enabled. */
	private static Boolean is_debugEnabled = S_LOGGER.isDebugEnabled();

	/**
	 * Adds the environment.
	 *
	 * @param appDirName the app dir name
	 * @param environments the environments
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEnvironment(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, List<Environment> environments) {
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			String configFileDir = FrameworkServiceUtil.getConfigFileDir(appDirName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			configManager.addEnvironments(environments);
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null,
					"Environments added Successfully", environments);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e,
					"Environments Failed to add", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	/**
	 * List environments.
	 *
	 * @param appDirName the app dir name
	 * @param envName the env name
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listEnvironments(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_ENV_NAME) String envName) {
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			String configFileDir = FrameworkServiceUtil.getConfigFileDir(appDirName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			if (StringUtils.isNotEmpty(envName)) {
				List<Environment> environments = configManager.getEnvironments(Arrays.asList(envName));
				if (CollectionUtils.isNotEmpty(environments)) {
					Environment environment = environments.get(0);
					ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null,
							"Environments Listed", environment);
					return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
				} else {
					ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null,
							"Environments Failed to List for envName : " + envName, null);
					return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header(
							"Access-Control-Allow-Origin", "*").build();
				}
			}
			List<Environment> environments = configManager.getEnvironmentsAlone();
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environments Listed",
					environments);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e,
					"Environments Failed to List", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	/**
	 * Gets the all environments.
	 *
	 * @param appDirName the app dir name
	 * @return the all environments
	 */
	@GET
	@Path("allEnvironments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEnvironments(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			String configFileDir = FrameworkServiceUtil.getConfigFileDir(appDirName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			List<Environment> environments = configManager.getEnvironments();
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environments Listed",
					environments);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e,
					"Environments Failed to List", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}


	/**
	 * Delete environment.
	 *
	 * @param appDirName the app dir name
	 * @param envName the env name
	 * @return the response
	 */
	@DELETE
	@Path("/deleteEnv")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEnv(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_ENV_NAME) String envName) {
		String configFile = FrameworkServiceUtil.getConfigFileDir(appDirName);
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			List<Environment> environments = configManager.getEnvironments(Arrays.asList(envName));
			if (CollectionUtils.isNotEmpty(environments)) {
				Environment environment = environments.get(0);
				if (environment.isDefaultEnv()) {
					ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null,
							"Default Environment can not be deleted", null);
					return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header(
							"Access-Control-Allow-Origin", "*").build();
				}
				configManager.deleteEnvironment(envName);
				ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null,
						"Environment Deleted successfully", null);
				return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e,
					"Environment Failed to Delete", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();
		}
		ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null,
				"Environment Not available to Delete", null);
		return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut)
				.header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Gets the settings template.
	 *
	 * @param appDirName the app dir name
	 * @param techId the tech id
	 * @param userId the user id
	 * @param type the type
	 * @return the settings template
	 */
	@GET
	@Path("/settingsTemplate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSettingsTemplate(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_TECHID) String techId, @QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_TYPE) String type) {
		ResponseInfo<List<SettingsTemplate>> responseData = new ResponseInfo<List<SettingsTemplate>>();
		Map<String, Object> templateMap = new HashMap<String, Object>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			SettingsTemplate settingsTemplate = serviceManager.getConfigTemplateByTechId(techId, type);
			Map<String, List<String>> downloadInfo = getDownloadInfo(serviceManager, appDirName, userId, type);
			templateMap.put("settingsTemplate", settingsTemplate);
			templateMap.put("downloadInfo", downloadInfo);
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, null,
					"confuguration Template Fetched successfully", templateMap);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, e,
					"confuguration Template Not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, e,
					"confuguration Template Not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the config types.
	 *
	 * @param customerId the customer id
	 * @param userId the user id
	 * @param techId the tech id
	 * @return the config types
	 */
	@GET
	@Path("/types")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConfigTypes(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_TECHID) String techId) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<String> settingsTypes = new ArrayList<String>();
			List<SettingsTemplate> settingsTemplates = serviceManager.getConfigTemplates(customerId, techId);
			if (CollectionUtils.isNotEmpty(settingsTemplates)) {
				for (SettingsTemplate settingsTemplate : settingsTemplates) {
					settingsTypes.add(settingsTemplate.getName());
				}
			}
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
					"confuguration Template Fetched successfully", settingsTypes);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e,
					"confuguration Template not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Connection alive check.
	 *
	 * @param url the url
	 * @return the response
	 */
	@GET
	@Path("/connectionAliveCheck")
	@Produces(MediaType.APPLICATION_JSON)
	public Response connectionAliveCheck(@QueryParam(REST_QUERY_URL) String url) {
		if (is_debugEnabled) {
			S_LOGGER.debug("Entering Method  Configurationservice.connectionAliveCheck()");
		}

		if (url == null || ("".equals(url)) == true) {
			return Response.status(Status.BAD_REQUEST).entity(null).header("Access-Control-Allow-Origin", "*").build();
		}

		boolean connection_status = false;
		try {
			String[] results = url.split(",");
			String lprotocol = results[0];
			String lhost = results[1];
			int lport = Integer.parseInt(results[2]);
			boolean tempConnectionAlive = isConnectionAlive(lprotocol, lhost, lport);
			connection_status = tempConnectionAlive == true ? true : false;
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Configurationservice.connectionAliveCheck()"
					+ FrameworkUtil.getStackTraceAsString(e));
			return Response.status(Status.BAD_REQUEST).entity(null).header("Access-Control-Allow-Origin", "*").build();
		}

		return Response.status(Status.OK).entity(connection_status).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Update configuration.
	 *
	 * @param userId the user id
	 * @param customerId the customer id
	 * @param appDirName the app dir name
	 * @param envName the env name
	 * @param configurationlist the configurationlist
	 * @return the response
	 */
	@POST
	@Path("/updateConfig")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateConfiguration(@QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_ENV_NAME) String envName,
			List<Configuration> configurationlist) {
		if (is_debugEnabled) {
			S_LOGGER.debug("Entering Method  Configurationservice.updateConfiguration()");
		}

		String configFile = FrameworkServiceUtil.getConfigFileDir(appDirName);
		ResponseInfo<Configuration> responseData = new ResponseInfo<Configuration>();
		try {
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			List<Configuration> listofconfiguration = configManager.getConfigurations(envName);
			List<String> configuration_names = new ArrayList<String>();
			validateConfiguration(userId, customerId, appDirName, configurationlist);
			for (Configuration configuration_temp : listofconfiguration) {

				configuration_names.add(configuration_temp.getName());
			}
			configManager.deleteConfigurations(envName, configuration_names);
			configManager.createConfiguration(envName, configurationlist);
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, null,
					"Configurations Updated Successfully", "Success");
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			S_LOGGER.error("Entered into Configuration catch block of Configurationservice.updateConfiguration()"
					+ FrameworkUtil.getStackTraceAsString(e));
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e,
					"Configurations failed to be updated for the Environment", "Failure");
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into PhrescoException catch block of Configurationservice.updateConfiguration()"
					+ FrameworkUtil.getStackTraceAsString(e));
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, e.getMessage(), "Failure");
			return Response.status(Status.BAD_REQUEST).entity(finalOuptut).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Configurationservice.updateConfiguration()"
					+ FrameworkUtil.getStackTraceAsString(e));
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e,
					"Configurations failed to be updated for the Environment", "Failure");
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();
		}

	}

	/**
	 * Clone environment.
	 *
	 * @param appDirName the app dir name
	 * @param envName the env name
	 * @param cloneEnvironment the clone environment
	 * @return the response
	 */
	@POST
	@Path("/cloneEnvironment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cloneEnvironment(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_ENV_NAME) String envName, Environment cloneEnvironment) {

		String configFile = FrameworkServiceUtil.getConfigFileDir(appDirName);
		Environment clonedEnvironment = null;
		ResponseInfo<Configuration> responseData = new ResponseInfo<Configuration>();
		try {
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			clonedEnvironment = configManager.cloneEnvironment(envName, cloneEnvironment);
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, null,
					"Clone Environment Done Successfully", clonedEnvironment);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();

		} catch (ConfigurationException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e,
					"Clone Environment Failed", clonedEnvironment);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();

		} catch (PhrescoException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e,
					"Clone Environment Failed", clonedEnvironment);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();

		} catch (Exception e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e,
					"Clone Environment Failed", clonedEnvironment);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin",
					"*").build();
		}
	}

	/**
	 * Cron expression for scheduler.
	 *
	 * @param cronExpInfo the cron exp info
	 * @return the response
	 */
	@POST
	@Path("/cronExpression")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cronValidation(CronExpressionInfo cronExpInfo) {
		ResponseInfo<CronExpressionInfo> responseData = new ResponseInfo<CronExpressionInfo>();
		CronExpressionInfo cronResult = new CronExpressionInfo();
		try {
			String cronBy = cronExpInfo.getCronBy();
			String cronExpression = "";
			Date[] dates = null;
			List<String> datesList = new ArrayList<String>();

			if (REQ_CRON_BY_DAILY.equals(cronBy)) {
				String hours = cronExpInfo.getHours();
				String minutes = cronExpInfo.getMinutes();
				String every = cronExpInfo.getEvery();

				if ("false".equals(every)) {
					if ("*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 * * * * ?";
					} else if ("*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + minutes + " 0 * * ?";
					} else if (!"*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 0 " + hours + " * * ?";
					} else if (!"*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + minutes + " " + hours + " * * ?";
					}
				} else {
					if ("*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 * * * * ?";
					} else if ("*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + "*/" + minutes + " * * * ?"; // 0 replace with *
					} else if (!"*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 0 " + "*/" + hours + " * * ?"; // 0 replace with *
					} else if (!"*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + minutes + " */" + hours + " * * ?"; // 0 replace with *
					}
				}
				dates = testCronExpression(cronExpression);

			} else if (REQ_CRON_BY_WEEKLY.equals(cronBy)) {
				String hours = cronExpInfo.getHours();
				String minutes = cronExpInfo.getMinutes();
				List<String> week = cronExpInfo.getWeek();
				String csv = week.toString().replace("[", "").replace("]", "")
	            .replace(", ", ",");
				hours = ("*".equals(hours)) ? "0" : hours;
				minutes = ("*".equals(minutes)) ? "0" : minutes;
				cronExpression = "0 " + minutes + " " + hours + " ? * " + csv;
				dates = testCronExpression(cronExpression);

			} else if (REQ_CRON_BY_MONTHLY.equals(cronBy)) {
				String hours = cronExpInfo.getHours();
				String minutes = cronExpInfo.getMinutes();
				List<String> month = cronExpInfo.getMonth();
				String csv = month.toString().replace("[", "").replace("]", "")
	            .replace(", ", ",");
				String day = cronExpInfo.getDay();
				hours = ("*".equals(hours)) ? "0" : hours;
				minutes = ("*".equals(minutes)) ? "0" : minutes;
				cronExpression = "0 " + minutes + " " + hours + " " + day + " " + csv + " ?";
				dates = testCronExpression(cronExpression);
			}

			if (dates != null) {
				cronExpression = cronExpression.replace('?', '*');
				cronExpression = cronExpression.substring(2);
				for (int i = 0; i < dates.length; i++) {
					datesList.add(dates[i].toString());
				}
				cronResult.setDates(datesList);
				
			}
			cronResult.setCronExpression(cronExpression);

			ResponseInfo<CronExpressionInfo> finalOutput = responseDataEvaluation(responseData, null,
					"Cron Expression calculated successfully", cronResult);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			ResponseInfo<CronExpressionInfo> finalOutput = responseDataEvaluation(responseData, e, "Cron Expression not Available",
					null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	/**
	 * List environments by project id.
	 *
	 * @param customerId the customer id
	 * @param projectId the project id
	 * @return the response
	 */
	@GET
	@Path("/listEnvironmentsByProjectId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listEnvironmentsByProjectId(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_PROJECTID) String projectId) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		try {
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
			Set<String> environmentSet = new HashSet<String>();
			for (ApplicationInfo appInfo : appInfos) {
				List<Environment> environments = getEnvironments(appInfo);
				for (Environment environment : environments) {
					environmentSet.add(environment.getName());
				}
			}
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null,
					"Environments Listed successfully", environmentSet);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, e, "Environmets not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (ConfigurationException e) {
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, e, "Environmets not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
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
		String configFile = FrameworkServiceUtil.getConfigFileDir(appInfo.getAppDirName());
		ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
		List<Environment> environments = configManager.getEnvironmentsAlone();
		return environments;
	}

	/**
	 * Validate configuration.
	 *
	 * @param userId the user id
	 * @param customerId the customer id
	 * @param appDirName the app dir name
	 * @param configurationlist the configurationlist
	 * @throws PhrescoException the phresco exception
	 */
	private void validateConfiguration(String userId, String customerId, String appDirName,
			List<Configuration> configurationlist) throws PhrescoException {
		ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
		int serverCount = 0;
		int emailCount = 0;
		boolean serverTypeValidation = false;
		boolean isRequired = false;
		String techId = "";
		String dynamicError = "";
		for (int i = 0; i < configurationlist.size(); i++) {
			if (StringUtils.isEmpty(configurationlist.get(i).getName())) {
				throw new PhrescoException("Name is Empty");
			} else {
				String name = configurationlist.get(i).getName();
				for (int j = 0; j < configurationlist.size(); j++) {
					if (i != j) {
						if (name.equals(configurationlist.get(j).getName())) {
							throw new PhrescoException("Name already Exists");
						}
					}
				}
			}
		}

		for (Configuration configuration : configurationlist) {
			SettingsTemplate configTemplateByType = serviceManager.getConfigTemplateByType(customerId, configuration
					.getType());
			if (StringUtils.isEmpty(configuration.getType())) {
				throw new PhrescoException("Configuration Type is Empty");
			}

			if (FrameworkConstants.SERVER.equals(configuration.getType())
					|| FrameworkConstants.EMAIL.equals(configuration.getType())) {
				if (FrameworkConstants.SERVER.equals(configuration.getType())) {
					serverCount++;
				} else {
					String propertyEmail = configuration.getProperties().getProperty(FrameworkConstants.EMAIL_ID);
					if (propertyEmail.isEmpty()) {
						throw new PhrescoException("Email ID is Empty");
					} else {
						emailvalidation(propertyEmail);
					}
					emailCount++;
				}
			}

			if (serverCount > 1) {
				throw new PhrescoException("Server Configuration type Already Exists");
			}

			if (emailCount > 1) {
				throw new PhrescoException("Email Configuration type Already Exists");
			}

			if (!FrameworkConstants.OTHERS.equals(configuration.getType())) {
				List<PropertyTemplate> properties = configTemplateByType.getProperties();
				for (PropertyTemplate propertyTemplate : properties) {
					String propKey = propertyTemplate.getKey();
					String propValue = configuration.getProperties().getProperty(propKey);
					if (FrameworkConstants.REQ_TYPE.equals(propKey)
							&& FrameworkConstants.NODEJS_SERVER.equals(propValue)
							|| FrameworkConstants.NODEJS_MAC_SERVER.equals(propValue)
							|| FrameworkConstants.SHAREPOINT_SERVER.equals(propValue)
							|| FrameworkConstants.IIS_SERVER.equals(propValue)) {
						// If nodeJs and sharepoint server selected , there
						// should not be validation for deploy dir.
						serverTypeValidation = true;
					}

					techId = FrameworkServiceUtil.getApplicationInfo(appDirName).getTechInfo().getId();
					if (techId != null && techId.equals(FrameworkConstants.TECH_SITE_CORE)) {
						if (FrameworkConstants.DEPLOY_DIR.equals(propKey)) {
							isRequired = false;
						}
					}

					if (serverTypeValidation && FrameworkConstants.DEPLOY_DIR.equals(propKey)) {
						isRequired = false;
					}

					// validation for UserName & Password for RemoteDeployment

					if (FrameworkConstants.REMOTE_DEPLOYMENT.equals(propKey)
							&& FrameworkConstants.TRUE.equals(propValue)) {
						if (FrameworkConstants.ADMIN_USERNAME.equals(propKey)
								|| FrameworkConstants.ADMIN_PASSWORD.equals(propKey)) {
							isRequired = true;
						}
						if (FrameworkConstants.DEPLOY_DIR.equals(propKey)) {
							isRequired = false;
						}
					}

					if (isRequired && StringUtils.isEmpty(propValue)) {
						String field = propertyTemplate.getName();
						dynamicError += propKey + Constants.STR_COLON + field + "is missing" + Constants.STR_COMMA;
					}

					if (StringUtils.isNotEmpty(dynamicError)) {
						dynamicError = dynamicError.substring(0, dynamicError.length() - 1);
						throw new PhrescoException(dynamicError);
					}

					// Version Validation for Server and Database configuration
					// types
					if (FrameworkConstants.SERVER.equals(configuration.getType())
							|| FrameworkConstants.DATABASE.equals(configuration.getType())) {
						if (StringUtils.isEmpty(configuration.getProperties().getProperty(FrameworkConstants.VERSION))) {
							throw new PhrescoException("Version is Empty");
						}
					}
					// Site Core installation path check
					if (techId.equals(FrameworkConstants.TECH_SITE_CORE)
							&& FrameworkConstants.SERVER.equals(configuration.getType())
							&& StringUtils.isEmpty(configuration.getProperties().getProperty(
									FrameworkConstants.SETTINGS_TEMP_SITECORE_INST_PATH))) {
						throw new PhrescoException("SiteCore Installation path Location is missing");
					}
				}
			}
		}
	}

	/**
	 * Emailvalidation.
	 *
	 * @param propertyEmail the property email
	 * @throws PhrescoException the phresco exception
	 */
	private void emailvalidation(String propertyEmail) throws PhrescoException {
		Pattern p = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(propertyEmail);
		boolean b = m.matches();
		if (!b) {
			throw new PhrescoException("Email Format mismatch");
		}

	}

	/**
	 * Test cron expression.
	 *
	 * @param expression the expression
	 * @return the date[]
	 * @throws PhrescoException the phresco exception
	 */
	private Date[] testCronExpression(String expression) throws PhrescoException {
		Date[] dates = null;
		try {
			final CronExpression cronExpression = new CronExpression(expression);
			final Date nextValidDate1 = cronExpression.getNextValidTimeAfter(new Date());
			final Date nextValidDate2 = cronExpression.getNextValidTimeAfter(nextValidDate1);
			final Date nextValidDate3 = cronExpression.getNextValidTimeAfter(nextValidDate2);
			final Date nextValidDate4 = cronExpression.getNextValidTimeAfter(nextValidDate3);
			dates = new Date[] { nextValidDate1, nextValidDate2, nextValidDate3, nextValidDate4 };
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return dates;
	}

	/**
	 * Checks if is connection alive.
	 *
	 * @param protocol the protocol
	 * @param host the host
	 * @param port the port
	 * @return true, if is connection alive
	 */
	public boolean isConnectionAlive(String protocol, String host, int port) {
		boolean isAlive = true;
		try {
			URL url = new URL(protocol, host, port, "");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			isAlive = false;
		}

		return isAlive;
	}

	/**
	 * Gets the download info.
	 *
	 * @param serviceManager the service manager
	 * @param appDirName the app dir name
	 * @param userId the user id
	 * @param type the type
	 * @return the download info
	 * @throws PhrescoException the phresco exception
	 */
	private Map<String, List<String>> getDownloadInfo(ServiceManager serviceManager, String appDirName, String userId,
			String type) throws PhrescoException {
		ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
		List<ArtifactGroupInfo> artifactGroupInfos = null;
		Map<String, List<String>> nameMap = new HashMap<String, List<String>>();

		if (Constants.SETTINGS_TEMPLATE_SERVER.equals(type)) {
			artifactGroupInfos = appInfo.getSelectedServers();
		} else if (Constants.SETTINGS_TEMPLATE_DB.equals(type)) {
			artifactGroupInfos = appInfo.getSelectedDatabases();
		}
		if (CollectionUtils.isNotEmpty(artifactGroupInfos)) {
			for (ArtifactGroupInfo artifactGroupInfo : artifactGroupInfos) {
				ArtifactGroup artifactGroup = serviceManager.getArtifactGroupInfo(artifactGroupInfo
						.getArtifactGroupId());
				List<String> artifactInfoIds = artifactGroupInfo.getArtifactInfoIds();
				List<String> verstions = new ArrayList<String>();
				for (String artifactInfoId : artifactInfoIds) {
					ArtifactInfo artifactInfo = serviceManager.getArtifactInfo(artifactInfoId);
					verstions.add(artifactInfo.getVersion());
				}
				nameMap.put(artifactGroup.getName(), verstions);
			}
		}
		return nameMap;
	}
}
