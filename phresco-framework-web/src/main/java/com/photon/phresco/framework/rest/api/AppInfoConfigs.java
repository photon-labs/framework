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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.commons.model.DownloadInfo;
import com.photon.phresco.commons.model.WebService;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class AppInfoConfigs.
 */
@Path("/appConfig")
public class AppInfoConfigs extends RestBase implements ServiceConstants {

	/**
	 * Gets the download infos.
	 *
	 * @param customerId the customer id
	 * @param techId the tech id
	 * @param type the type
	 * @param platform the platform
	 * @param userId the user id
	 * @return the download infos
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDownloadInfos(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_TECHID) String techId, @QueryParam(REST_QUERY_TYPE) String type,
			@QueryParam(REST_QUERY_PLATFORM) String platform, @QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<List<DownloadInfo>> responseData = new ResponseInfo<List<DownloadInfo>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<DownloadInfo>> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<DownloadInfo> downloadInfos = serviceManager.getDownloads(customerId, techId, type, platform);
			if (CollectionUtils.isNotEmpty(downloadInfos)) {
				Collections.sort(downloadInfos, sortByNameInAlphaOrder());
			}
			ResponseInfo<List<DownloadInfo>> finalOutput = responseDataEvaluation(responseData, null,
					" Configuration listed successfully", downloadInfos);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<DownloadInfo>> finalOutput = responseDataEvaluation(responseData, e,
					"Configuration not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the web services.
	 *
	 * @param userId the user id
	 * @return the web services
	 */
	@GET
	@Path("/webservices")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWebServices(@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<List<WebService>> responseData = new ResponseInfo<List<WebService>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<WebService>> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<WebService> webServices = serviceManager.getWebServices();
			ResponseInfo<List<WebService>> finalOutput = responseDataEvaluation(responseData, null,
					" Configuration listed successfully", webServices);
			return Response.status(ClientResponse.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
					"*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<WebService>> finalOutput = responseDataEvaluation(responseData, e,
					"Webservice configuration not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Sort by name in alphabetic order.
	 *
	 * @return the comparator
	 */
	public Comparator sortByNameInAlphaOrder() {
		return new Comparator() {
			public int compare(Object firstObject, Object secondObject) {
				DownloadInfo projectInfo1 = (DownloadInfo) firstObject;
				DownloadInfo projectInfo2 = (DownloadInfo) secondObject;
				return projectInfo1.getName().compareToIgnoreCase(projectInfo2.getName());
			}
		};
	}
}
