package com.photon.phresco.framework.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.VersionInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.UpgradeManager;
import com.photon.phresco.framework.model.UpdateInfo;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/upgrade")
public class UpgradeService extends RestBase implements FrameworkConstants, ServiceConstants, ResponseCodes {

	@GET
	@Path("/upgradeAvailable")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateExistsCheck(@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<UpdateInfo> responseData = new ResponseInfo<UpdateInfo>();
		try {
			UpdateInfo updateInfo = new UpdateInfo();
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null, null,
						RESPONSE_STATUS_FAILURE, PHR910001);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, "*").build();
			}
			UpgradeManager updateManager = PhrescoFrameworkFactory.getUpdateManager();
			String currentVersion = updateManager.getCurrentVersion();
			updateInfo.setCurrentVersion(currentVersion);
			VersionInfo versionInfo = updateManager.checkForUpdate(serviceManager, currentVersion);
			updateInfo.setMessage(versionInfo.getMessage());
			updateInfo.setLatestVersion(versionInfo.getFrameworkVersion());
			updateInfo.setUpdateAvaillable(versionInfo.isUpdateAvailable());
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null, updateInfo,
					RESPONSE_STATUS_SUCCESS, PHR900001);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, e, null,
					RESPONSE_STATUS_ERROR, PHR910002);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doUpdate(@QueryParam("newVersion") String newVersion, @QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<ProjectInfo> finalOutput = responseDataEvaluation(responseData, null, null,
						RESPONSE_STATUS_FAILURE, PHR910003);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, "*").build();
			}
			UpgradeManager updateManager = PhrescoFrameworkFactory.getUpdateManager();
			updateManager.doUpdate(serviceManager, newVersion, ServiceConstants.DEFAULT_CUSTOMER_NAME);
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null, null,
					RESPONSE_STATUS_SUCCESS, PHR900002);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ProjectInfo>> finalOutput = responseDataEvaluation(responseData, null, null,
					RESPONSE_STATUS_ERROR, PHR910004);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL_HEADER)
					.build();
		}
	}
}
