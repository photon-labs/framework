package com.photon.phresco.framework.rest.api;

import java.io.IOException;
import java.util.ArrayList;
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
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactElement;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.DownloadInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/features")
public class FeatureService extends RestBase implements ServiceConstants {
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("customerId") String customer, @QueryParam("techId") String techId,
			@QueryParam("type") String type, @QueryParam("userId") String userId ) {
		ResponseInfo<List<ArtifactGroup>> responseData = new ResponseInfo<List<ArtifactGroup>>();
		try {
			ServiceManager serviceManager = ServiceManagerMap.getServiceManager(userId);
			if(serviceManager == null) {
				ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<ArtifactGroup> features = serviceManager.getFeatures(customer, techId, type);
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null, "Application Features listed successfully", features);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, e, "Application Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, e, "Application Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/desc")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDescription(@QueryParam("customerId") String customer, 
			@QueryParam(DB_COLUMN_ARTIFACT_GROUP_ID) String artifactGroupId, @QueryParam("userId") String userId) {
		ResponseInfo<ArtifactElement> responseData = new ResponseInfo<ArtifactElement>();
		try {
			ServiceManager serviceManager = ServiceManagerMap.getServiceManager(userId);
			if(serviceManager == null) {
				ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			ArtifactElement artifactElement = serviceManager.getArtifactDescription(artifactGroupId);
			ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, null, 
					"Application Features listed successfully", artifactElement.getDescription());
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, e, "Application Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo<ArtifactElement> finalOutput = responseDataEvaluation(responseData, e, "Application Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/dependencyFeature")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependencyFeature(@QueryParam("userId") String userId, ArtifactInfo artifactInfo) throws PhrescoException {
		List<String> dependencyIds = artifactInfo.getDependencyIds();
		ResponseInfo<List<ArtifactGroup>> responseData = new ResponseInfo<List<ArtifactGroup>>();
		try {
			List<ArtifactGroup> atArtifactGroups = new ArrayList<ArtifactGroup>();
			ServiceManager serviceManager = ServiceManagerMap.getServiceManager(userId);
			if(serviceManager == null) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			for (String dependencyId : dependencyIds) {
				ArtifactInfo artifact = serviceManager.getArtifactInfo(dependencyId);
				String artifactGroupId = artifact.getArtifactGroupId();
				ArtifactGroup artifactGroupInfo = serviceManager.getArtifactGroupInfo(artifactGroupId);
				atArtifactGroups.add(artifactGroupInfo);
			}
			ResponseInfo<List<ArtifactGroup>> finalOutput = responseDataEvaluation(responseData, null, " Dependency Features listed successfully", atArtifactGroups);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo<ArtifactGroup> finalOutput = responseDataEvaluation(responseData, e, "Dependency Features not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
}
