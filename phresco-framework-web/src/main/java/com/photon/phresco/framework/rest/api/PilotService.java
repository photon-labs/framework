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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroupInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class PilotService.
 */
@Path("/pilot")
public class PilotService extends RestBase implements ServiceConstants, FrameworkConstants, ResponseCodes {

	/**
	 * List the pilot projects.
	 *
	 * @param customerId the customer id
	 * @param techId the tech id
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_TECHID) String techId, @QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<List<ApplicationInfo>> responseData = new ResponseInfo<List<ApplicationInfo>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<ApplicationInfo> pilotProjects = serviceManager.getPilotProjects(customerId, techId);
			if(CollectionUtils.isEmpty(pilotProjects)){
				ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, null,
						"Application pilot listed successfully", null);
				return Response.status(Status.NO_CONTENT).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, null,
					"Application pilot listed successfully", pilotProjects);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ApplicationInfo>> finalOutput = responseDataEvaluation(responseData, e,
					"Application pilot list not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the dependent pilot.
	 *
	 * @param userId the user id
	 * @param pilotId the pilot id
	 * @param type the type
	 * @return the dependent pilot
	 * @throws PhrescoException the phresco exception
	 */
	@GET
	@Path("/dependentPilot")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependentPilot(@QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_PILOT_ID) String pilotId, @QueryParam(REST_QUERY_TYPE) String type)
			throws PhrescoException {
		ResponseInfo<List<ArtifactGroupInfo>> responseData = new ResponseInfo<List<ArtifactGroupInfo>>();
		ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
		ApplicationInfo appInfo = serviceManager.getPilotFromId(pilotId);
		if (FrameworkConstants.REQ_SERVERS.equals(type)) {
			List<ArtifactGroupInfo> selectedServers = appInfo.getSelectedServers();
			ResponseInfo<List<ArtifactGroupInfo>> finalOutput = responseDataEvaluation(responseData, null,
					"Application pilot listed successfully", selectedServers);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} else if (FrameworkConstants.REQ_DATABASES.equals(type)) {
			List<ArtifactGroupInfo> selectedDatabases = appInfo.getSelectedDatabases();
			ResponseInfo<List<ArtifactGroupInfo>> finalOutput = responseDataEvaluation(responseData, null,
					"Application pilot listed successfully", selectedDatabases);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} else if (FrameworkConstants.REQ_WEBSERVICES.equals(type)) {
			List<String> selectedWebservices = appInfo.getSelectedWebservices();
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
					"Application pilot listed successfully", selectedWebservices);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo<List<ArtifactGroupInfo>> finalOutput = responseDataEvaluation(responseData, null,
				"Application pilot list not fetched", null);
		return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	/**
	 * Pre built projects.
	 *
	 * @param userId the user id
	 * @param customerId the customer id
	 * @return the response
	 */
	@GET
	@Path("/prebuilt")
	@Produces(MediaType.APPLICATION_JSON)
	public Response preBuiltProjects(@QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<List<ProjectInfo>> responseData = new ResponseInfo<List<ProjectInfo>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_FAILURE, PHR210003);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<ProjectInfo> preBuilts = serviceManager.getPrebuiltProjects(customerId);
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null,
					preBuilts, RESPONSE_STATUS_SUCCESS, PHR200025);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR210046);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
}
