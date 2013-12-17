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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.framework.commons.TestFlightResponse;
import com.photon.phresco.framework.model.TestFlight;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

/**
 * The Class UtilService.
 */
@Path("/uploadBuild")
public class UploadBuildService extends RestBase implements FrameworkConstants, ServiceConstants, ResponseCodes {

	/**
	 * @param log
	 * @return
	 */
	@POST
	@Path("/uploadToTestFlight")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response uploadToTestFlight(TestFlight testFlight) {

		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		String status;
		String errorCode;
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource resource = client.resource("https://testflightapp.com/api/builds.json");
			File fileToUpload = new File(testFlight.getFilePath());
			FormDataMultiPart multiPart = new FormDataMultiPart();
			multiPart.field("api_token", testFlight.getApiToken());
			multiPart.field("team_token", testFlight.getTeamToken());
			multiPart.field("notes", testFlight.getNotes());
			multiPart.field("notify", testFlight.getNotify());
			multiPart.field("distribution_lists", testFlight.getDistributionLists());
			multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload, MediaType.MULTIPART_FORM_DATA_TYPE));
			ClientResponse clientResp = resource.type(
					MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class,
							multiPart);
			if (clientResp.getClientResponseStatus().getStatusCode() != 200) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRU010001;
				ResponseInfo<User> finalOutput = responseDataEvaluation(responseData, null,null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
			GenericType<TestFlightResponse> genericType = new GenericType<TestFlightResponse>() {};
			client.destroy();
			status = RESPONSE_STATUS_SUCCESS;
			errorCode = PHRU000001;
			ResponseInfo<User> finalOutput = responseDataEvaluation(responseData, null,
					clientResp.getEntity(genericType), status, errorCode);

			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();	

		} catch(Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRU010001;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();			
		}
	}
}