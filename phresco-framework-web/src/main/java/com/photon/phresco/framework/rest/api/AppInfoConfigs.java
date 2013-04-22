package com.photon.phresco.framework.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.photon.phresco.commons.model.DownloadInfo;
import com.photon.phresco.commons.model.WebService;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.sun.jersey.api.client.ClientResponse;

@Path("/appConfig")
public class AppInfoConfigs extends LoginService {
	
	@GET
	@Path("/list")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getDownloadInfos(@QueryParam("customerId") String customerId, @QueryParam("tech-id") String techId,
			@QueryParam("type") String type, @QueryParam("platform") String platform, @QueryParam("userId") String userId) throws PhrescoException {
		ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
		List<DownloadInfo> downloadInfos = serviceManager.getDownloads(customerId, techId, type, platform);
		return Response.status(ClientResponse.Status.OK).entity(downloadInfos).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/webservices")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getWebServices(@QueryParam("userId") String userId) throws PhrescoException {
		ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
		List<WebService> webServices = serviceManager.getWebServices();
		return Response.status(ClientResponse.Status.OK).entity(webServices).header("Access-Control-Allow-Origin", "*").build();
	}
}
