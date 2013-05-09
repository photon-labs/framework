package com.photon.phresco.framework.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/pilot")
public class PilotService {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("customerId") String customer, @QueryParam("techId") String techId, 
			@QueryParam("userId") String userId ) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			ServiceManager serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<ApplicationInfo> pilotProjects = serviceManager.getPilotProjects(customer, techId);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null, "Application pilot listed successfully", pilotProjects);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e, "Application pilot list not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
}
