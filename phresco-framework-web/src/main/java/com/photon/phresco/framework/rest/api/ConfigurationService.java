package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.impl.ConfigManagerImpl;
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
	public Response listEnvironments(@QueryParam("appDirName") String appDirName) {
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
	
	@GET
	@Path("/get")
	@Produces (MediaType.APPLICATION_JSON)
	public Response get() {
		File configFile = new File("D:\\phresco_adaption\\framework\\phresco-framework-web\\src\\main\\resources\\phresco-env-config.xml");
		try {
			ConfigManager configManager = PhrescoFrameworkFactory.getConfigManager(configFile);
//			Configuration configuration = configManager.getConfiguration("Production", "Server", "server");
			List<Environment> environments = configManager.getEnvironments();
			System.out.println(environments);
			Gson gson = new Gson();
			for (Environment environment : environments) {
				Type jsonType = new TypeToken<Collection<Configuration>>(){}.getType();
				String envJson = gson.toJson(environment.getConfigurations(), jsonType);
				System.out.println(envJson);
			}
			return Response.ok(environments).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.EXPECTATION_FAILED).entity("").header("Access-Control-Allow-Origin", "*").build();
	}
}

