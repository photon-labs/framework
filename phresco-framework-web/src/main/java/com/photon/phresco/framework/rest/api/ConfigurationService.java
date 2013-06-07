package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
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
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/configuration")
public class ConfigurationService extends RestBase implements FrameworkConstants {
	
	private static final Logger S_LOGGER = Logger.getLogger(ConfigurationService.class);
    private static Boolean is_debugEnabled  = S_LOGGER.isDebugEnabled();

	
	@POST
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response addEnvironment(@QueryParam("appDirName") String appDirName, List<Environment> environments) {
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			String configFileDir = getConfigFileDir(appDirName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			configManager.addEnvironments(environments);
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environments added Successfully", environments);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e, "Environments Failed to add", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response listEnvironments(@QueryParam("appDirName") String appDirName, @QueryParam("envName") String envName) {
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			String configFileDir = getConfigFileDir(appDirName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			if(StringUtils.isNotEmpty(envName)) {
				List<Environment> environments = configManager.getEnvironments(Arrays.asList(envName));
				if(CollectionUtils.isNotEmpty(environments)) {
					Environment environment = environments.get(0);
					ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environments Listed", environment);
					return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
				} else {
					ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environments Failed to List for envName : " + envName, null);
					return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
				}
			}
			List<Environment> environments = configManager.getEnvironmentsAlone();
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environments Listed", environments);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e, "Environments Failed to List", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("allEnvironments")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getAllEnvironments(@QueryParam("appDirName") String appDirName) {
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			String configFileDir = getConfigFileDir(appDirName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			List<Environment> environments = configManager.getEnvironments();
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environments Listed", environments);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e, "Environments Failed to List", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
    @Path ("/saveConfig")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response saveConfiguration(@QueryParam("appDirName") String appDirName, Configuration configuration) {
		ResponseInfo<Configuration> responseData = new ResponseInfo<Configuration>();
		try {
			String configFileDir = getConfigFileDir(appDirName);
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			configManager.createConfiguration(configuration.getEnvName(), configuration);
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, null, "Environments Listed", configuration);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Cofiguration Failed to add", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@DELETE
	@Path ("/deleteEnv")
	@Produces (MediaType.APPLICATION_JSON)
	public Response deleteEnv(@QueryParam("appDirName") String appDirName, @QueryParam("envName") String envName) {
		String configFile = getConfigFileDir(appDirName);
		ResponseInfo<Environment> responseData = new ResponseInfo<Environment>();
		try {
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			List<Environment> environments = configManager.getEnvironments(Arrays.asList(envName));
			if(CollectionUtils.isNotEmpty(environments)) {
				Environment environment = environments.get(0);
				if(environment.isDefaultEnv()) {
					ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Default Environment can not be deleted", null);
					return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
				}
				configManager.deleteEnvironment(envName);
				ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environment Deleted successfully", null);
				return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e, "Environment Failed to Delete", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environment Not available to Delete", null);
		return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@DELETE
	@Path ("/deleteConfig")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response deleteConfig(@QueryParam("appDirName") String appDirName, @QueryParam("envName") String envName, List<String> configurations) {
		String configFile = getConfigFileDir(appDirName);
		ResponseInfo<Configuration> responseData = new ResponseInfo<Configuration>();
		try {
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			configManager.deleteConfigurations(envName, configurations);
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, null, "Configurations Deleted successfully", null);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Configurations Failed to Delete", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path ("/settingsTemplate")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getSettingsTemplate(@QueryParam("appDirName") String appDirName, @QueryParam("customerId") String customerId, @QueryParam("userId") String userId, @QueryParam("type") String type) {
		ResponseInfo<List<SettingsTemplate>> responseData = new ResponseInfo<List<SettingsTemplate>>();
		Map<String, Object> templateMap = new HashMap<String, Object>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			SettingsTemplate settingsTemplate = serviceManager.getConfigTemplateByType(customerId, type);
			Map<String, List<String>> downloadInfo = getDownloadInfo(serviceManager, appDirName, userId, type);
			templateMap.put("settingsTemplate", settingsTemplate);
			templateMap.put("downloadInfo", downloadInfo);
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, null, "confuguration Template Fetched successfully", templateMap);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, e, "confuguration Template Not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, e, "confuguration Template Not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path ("/types")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getConfigTypes(@QueryParam("customerId") String customerId, @QueryParam("userId") String userId, @QueryParam("techId") String techId) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<String> settingsTypes = new ArrayList<String>();
			List<SettingsTemplate> settingsTemplates = serviceManager.getConfigTemplates(customerId, techId);
			if(CollectionUtils.isNotEmpty(settingsTemplates)) {
				for (SettingsTemplate settingsTemplate : settingsTemplates) {
					settingsTypes.add(settingsTemplate.getName());
				}
			}
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "confuguration Template Fetched successfully", settingsTypes);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, "confuguration Template not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path ("/connectionAliveCheck")
	@Produces (MediaType.APPLICATION_JSON)
	public Response connectionAliveCheck(@QueryParam("url") String url) {
		if (is_debugEnabled) {
			S_LOGGER.debug("Entering Method  Configurationservice.connectionAliveCheck()");
		}

		if(url == null || ("".equals(url)) == true ) {
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
			S_LOGGER.error("Entered into catch block of Configurationservice.connectionAliveCheck()" + FrameworkUtil.getStackTraceAsString(e));
			return Response.status(Status.BAD_REQUEST).entity(null).header("Access-Control-Allow-Origin", "*").build();
		}

		return Response.status(Status.OK).entity(connection_status).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
    @Path ("/updateConfig")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response updateConfiguration(@QueryParam("appDirName") String appDirName,@QueryParam("envName") String envName, List<Configuration> configurationlist) {

		String configFile = getConfigFileDir(appDirName);
		ResponseInfo<Configuration> responseData = new ResponseInfo<Configuration>();
		try {
			
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			List<Configuration> listofconfiguration = configManager.getConfigurations(envName);
			List<String> configuration_names = new ArrayList<String>();
			for(Configuration configuration_temp : listofconfiguration) {
				
				configuration_names.add(configuration_temp.getName());
			}
			configManager.deleteConfigurations(envName, configuration_names);
			configManager.createConfiguration(envName, configurationlist);
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, null, "Configurations Updated Successfully", "Success");
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Configurations failed to be updated for the Environment", "Failure");
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Configurations failed to be updated for the Environment", "Failure");
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Configurations failed to be updated for the Environment", "Failure");
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
		
	}
	
	
	@POST
    @Path ("/cloneEnvironment")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response cloneEnvironment(@QueryParam("appDirName") String appDirName,@QueryParam("envName") String envName, Environment cloneEnvironment) {
		
		String configFile = getConfigFileDir(appDirName);
		Environment clonedEnvironment= null;
		ResponseInfo<Configuration> responseData = new ResponseInfo<Configuration>();
		try {
			ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
			clonedEnvironment = configManager.cloneEnvironment(envName, cloneEnvironment );
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, null, "Clone Environment Done Successfully", clonedEnvironment);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
			
		} catch (ConfigurationException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Clone Environment Failed", clonedEnvironment);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
			
		} catch (PhrescoException e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Clone Environment Failed", clonedEnvironment);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
			
		} catch (Exception e) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, e, "Clone Environment Failed", clonedEnvironment);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
		
	}

	@POST
	@Path ("/cronExpression")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response cronValidation(CronExpressionInfo cronExpInfo) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		try {
			String cronBy = cronExpInfo.getCronBy();
			String cronExpression = "";
			Date[] dates = null;

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
						cronExpression = "0 " + minutes + " " + hours
						+ " * * ?";
					}
				} else {
					if ("*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 * * * * ?";
					} else if ("*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + "*/" + minutes + " * * * ?"; // 0 replace with *
					} else if (!"*".equals(hours) && "*".equals(minutes)) {
						cronExpression = "0 0 " + "*/" + hours + " * * ?"; // 0 replace with *
					} else if (!"*".equals(hours) && !"*".equals(minutes)) {
						cronExpression = "0 " + minutes + " */" + hours
						+ " * * ?"; // 0 replace with *
					}
				}
				dates = testCronExpression(cronExpression);

			} else if (REQ_CRON_BY_WEEKLY.equals(cronBy)) {
				String hours = cronExpInfo.getHours();
				String minutes = cronExpInfo.getMinutes();
				String week = cronExpInfo.getWeek();
				hours = ("*".equals(hours)) ? "0" : hours;
				minutes = ("*".equals(minutes)) ? "0" : minutes;
				cronExpression = "0 " + minutes + " " + hours + " ? * " + week;
				dates = testCronExpression(cronExpression);

			} else if (REQ_CRON_BY_MONTHLY.equals(cronBy)) {
				String hours = cronExpInfo.getHours();
				String minutes = cronExpInfo.getMinutes();
				String month = cronExpInfo.getMonth();
				String day = cronExpInfo.getDay();
				hours = ("*".equals(hours)) ? "0" : hours;
				minutes = ("*".equals(minutes)) ? "0" : minutes;
				cronExpression = "0 " + minutes + " " + hours + " " + day + " "
				+ month + " ?";
				dates = testCronExpression(cronExpression);
			}

			if (dates != null) {
				cronExpression = cronExpression.replace('?', '*');
				cronExpression = cronExpression.substring(2);
			}

			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null, "Cron Expression calculated successfully", cronExpression);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, e, "Cron Expression not Available", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	
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
	
	private Map<String, List<String>> getDownloadInfo(ServiceManager serviceManager, String appDirName,
			String userId, String type)	throws PhrescoException {
		ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
		List<ArtifactGroupInfo> artifactGroupInfos = null;
		Map<String, List<String>> nameMap = new HashMap<String, List<String>>();
		
		if(Constants.SETTINGS_TEMPLATE_SERVER.equals(type)) {
			artifactGroupInfos = appInfo.getSelectedServers();
		} else if(Constants.SETTINGS_TEMPLATE_DB.equals(type)) {
			artifactGroupInfos = appInfo.getSelectedDatabases();
		} if(CollectionUtils.isNotEmpty(artifactGroupInfos)) {
			for (ArtifactGroupInfo artifactGroupInfo : artifactGroupInfos) {
				ArtifactGroup artifactGroup = serviceManager.getArtifactGroupInfo(artifactGroupInfo.getArtifactGroupId());
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
	
	private String getConfigFileDir(String appDirName) {
		StringBuilder builder = new StringBuilder();
		builder.append(Utility.getProjectHome())
		.append(appDirName)
		.append(File.separatorChar)
		.append(Constants.DOT_PHRESCO_FOLDER)
		.append(File.separatorChar)
		.append(Constants.CONFIGURATION_INFO_FILE);
		return builder.toString();
	}
}

