package com.photon.phresco.framework.rest.api;

import java.io.File;
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

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.model.CIJobTemplate;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

import fr.opensagres.xdocreport.utils.StringUtils;

@Path("/jobTemplates")
public class CIJobTemplateService extends RestBase {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		ResponseInfo<List<CIJobTemplate>> responseData = new ResponseInfo<List<CIJobTemplate>>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJobTemplate> jobTemplates = null;
			jobTemplates = ciManager.getJobTemplates();
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, null, "Job Templates listed successfully", jobTemplates);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, e, "Job Templates failed to list", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@PathParam("name") String name, @QueryParam("appId") String appId, @QueryParam("projId") String projId, @QueryParam("customerId") String customerId, @QueryParam("userId") String userId) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			CIJobTemplate jobTemplate = null;
			if (StringUtils.isNotEmpty(name)) {
				jobTemplate = ciManager.getJobTemplateByName(name);
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template retrived successfully", jobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates failed to retrive", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(CIJobTemplate ciJobTemplate) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			List<CIJobTemplate> jobTemplates = Arrays.asList(ciJobTemplate);
			boolean createJobTemplates = ciManager.createJobTemplates(jobTemplates, false);
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template added successfully", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates failed to add", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(CIJobTemplate ciJobTemplate) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			boolean updateJobTemplate = ciManager.updateJobTemplate(ciJobTemplate);
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template updated successfully", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates updation failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("name") String name) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIJobTemplate ciJobTemplate = null;
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			boolean deleteJobTemplate = ciManager.deleteJobTemplate(name);
			if (deleteJobTemplate) {
				ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template deleted successfully", ciJobTemplate);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Templates deletion failed", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates deletion failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/getJobTemplatesByEnvironment")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getJobTemplatesByEnvironemnt(@QueryParam("customerId") String customerId, @QueryParam("projectId") String projectId,
			@QueryParam("envName") String envName) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			List<ApplicationInfo> appInfos = FrameworkServiceUtil.getAppInfos(customerId, projectId);
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			Map<JSONObject, List<CIJobTemplate>> jobTemplateMap = new HashMap<JSONObject, List<CIJobTemplate>>();
			for (ApplicationInfo appInfo : appInfos) {
				String appName = appInfo.getName();
				List<Environment> environments = getEnvironments(appInfo);
				for (Environment environment : environments) {
					if (envName.equals(environment.getName())) {
						List<CIJobTemplate> jobTemplates = ciManager.getJobTemplatesByAppId(appName);
						if (CollectionUtils.isNotEmpty(jobTemplates)) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("appName", appInfo.getName());
							jsonObject.put("appDirName", appInfo.getAppDirName());
							
							jobTemplateMap.put(jsonObject, jobTemplates);
//							jobTemplateMap.put(appName, jobTemplates);
						}
					}
				}
			}
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Templates Fetched Successfully", jobTemplateMap);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates Failed to Fetch", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (ConfigurationException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates Failed to Fetch", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (JSONException e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates Failed to Fetch", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	private List<Environment> getEnvironments(ApplicationInfo appInfo)
	throws ConfigurationException {
		String configFile = FrameworkServiceUtil.getConfigFileDir(appInfo.getAppDirName());
		ConfigManager configManager = new ConfigManagerImpl(new File(configFile));
		List<Environment> environments = configManager.getEnvironmentsAlone();
		return environments;
	}
}
