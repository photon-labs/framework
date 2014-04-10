/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.TestFlightResponse;
import com.photon.phresco.framework.model.TestFlight;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
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
	public Response uploadToTestFlight(@QueryParam(ServiceConstants.REST_QUERY_MODULE_NAME) String moduleName, 
			@QueryParam(ServiceConstants.REST_QUERY_APPDIR_NAME) String appDirName, 
			@QueryParam(ServiceConstants.REST_QUERY_BUILD_NUMBER) String buildNumber,
			@QueryParam(ServiceConstants.REST_QUERY_FILE_EXTENSION) String fileExtn,
			TestFlight testFlight) {

		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		String status;
		String errorCode;
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource resource = client.resource("https://testflightapp.com/api/builds.json");
			String appDirPath = Utility.getProjectHome() + appDirName;
			ProjectInfo projectInfo = Utility.getProjectInfo(appDirPath, moduleName);
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			
			if (StringUtils.isEmpty(buildNumber)) {
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, RESPONSE_STATUS_FAILURE, PHRU010002);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			String buildFileName = applicationInfo.getName();
			String buildPath = getBuildName(appDirPath, moduleName, buildNumber).getDeployLocation();
			buildPath = buildPath.substring(0, buildPath.lastIndexOf(File.separator)) +  File.separator + buildFileName + FrameworkConstants.DOT + fileExtn;
			File fileToUpload = new File(buildPath);
			FormDataMultiPart multiPart = new FormDataMultiPart();
			multiPart.field(API_TOKEN_TF, testFlight.getApiToken());
			multiPart.field(TEAM_TOKEN_TF, testFlight.getTeamToken());
			multiPart.field(NOTES, testFlight.getNotes());
			multiPart.field(NOTIFY, testFlight.getNotify());
			multiPart.field(DISTRIBUTION_LISTS, testFlight.getDistributionLists());
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
	
	public static BuildInfo getBuildName(String rootModulePath, String subModuleName, String buildNo) throws PhrescoException {
		try {
			Gson gson = new Gson();
			File buildInfoPath = new File(FrameworkServiceUtil.getBuildInfosFilePath(rootModulePath, subModuleName));
			BufferedReader reader = new BufferedReader(new FileReader(buildInfoPath));
			Type type = new TypeToken<List<BuildInfo>>() {}  .getType();
			List<BuildInfo> buildInfos = (List<BuildInfo>)gson.fromJson(reader, type);
			BuildInfo info = null;
			int buildNum = Integer.parseInt(buildNo);
			for (BuildInfo buildInfo : buildInfos) {
				if (buildInfo.getBuildNo() == buildNum) {
					info = buildInfo;
					break;
				}
			}
			return info;
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}
}