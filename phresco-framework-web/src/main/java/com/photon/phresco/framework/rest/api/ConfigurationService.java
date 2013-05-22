package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.SettingsTemplate;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/configuration")
public class ConfigurationService extends RestBase {

	
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
			configManager.deleteEnvironment(envName);
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, null, "Environment Deleted successfully", null);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<Environment> finalOuptut = responseDataEvaluation(responseData, e, "Environment Failed to Delete", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
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
	public Response getSettingsTemplate(@QueryParam("customerId") String customerId, @QueryParam("userId") String userId, @QueryParam("type") String type) {
		ResponseInfo<List<SettingsTemplate>> responseData = new ResponseInfo<List<SettingsTemplate>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			SettingsTemplate settingsTemplate = serviceManager.getConfigTemplateByType(customerId, type);
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, null, "confuguration Template Fetched successfully", settingsTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<SettingsTemplate>> finalOutput = responseDataEvaluation(responseData, e, "confuguration Template not Fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
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

