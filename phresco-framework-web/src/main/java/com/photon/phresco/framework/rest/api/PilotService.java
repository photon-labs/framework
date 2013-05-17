package com.photon.phresco.framework.rest.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroupInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/pilot")
public class PilotService extends RestBase {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("customerId") String customer, @QueryParam("techId") String techId, 
			@QueryParam("userId") String userId ) {
		ResponseInfo<List<ApplicationInfo>> responseData = new ResponseInfo<List<ApplicationInfo>>();
		try {
			ServiceManager serviceManager = ServiceManagerMap.getServiceManager(userId);
			if(serviceManager == null) {
				ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<ApplicationInfo> pilotProjects = serviceManager.getPilotProjects(customer, techId);
			ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, null, "Application pilot listed successfully", pilotProjects);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, e, "Application pilot list not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, e, "Application pilot list not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/dependentPilot")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependentPilot(@QueryParam("userId") String userId, @QueryParam("pilotId") String pilotId, @QueryParam("type") String type) throws PhrescoException {
		ResponseInfo<List<ArtifactGroupInfo>> responseData = new ResponseInfo<List<ArtifactGroupInfo>>();
		try {
			ServiceManager serviceManager = ServiceManagerMap.getServiceManager(userId);
			ApplicationInfo appInfo = serviceManager.getPilotFromId(pilotId);
			if(FrameworkConstants.REQ_SERVERS.equals(type)) {
				List<ArtifactGroupInfo> selectedServers = appInfo.getSelectedServers();
				ResponseInfo<List<ArtifactGroupInfo>> finalOutput = responseDataEvaluation(responseData, null, "Application pilot listed successfully", selectedServers);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if(FrameworkConstants.REQ_DATABASES.equals(type)) {
				List<ArtifactGroupInfo> selectedDatabases = appInfo.getSelectedDatabases();
				ResponseInfo<List<ArtifactGroupInfo>> finalOutput = responseDataEvaluation(responseData, null, "Application pilot listed successfully", selectedDatabases);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else if(FrameworkConstants.REQ_WEBSERVICES.equals(type)) {
				List<String> selectedWebservices = appInfo.getSelectedWebservices();
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "Application pilot listed successfully", selectedWebservices);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (IOException e) {
			ResponseInfo<List<ArtifactGroupInfo>> finalOutput = responseDataEvaluation(responseData, e, "Application pilot list not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo<List<ArtifactGroupInfo>> finalOutput = responseDataEvaluation(responseData, null, "Application pilot list not fetched", null);
		return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
}
