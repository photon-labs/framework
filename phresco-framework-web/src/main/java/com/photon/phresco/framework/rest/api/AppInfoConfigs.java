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
import com.sun.jersey.api.client.ClientResponse;

@Path("/appConfig")
public class AppInfoConfigs extends RestBase {
	
	@GET
	@Path("/downloadInfos")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getDownloadInfos(@QueryParam("customerId") String customerId, @QueryParam("tech-id") String techId,
			@QueryParam("type") String type, @QueryParam("platform") String platform) throws PhrescoException {
		List<DownloadInfo> downloadInfos = getServiceManager().getDownloads(customerId, techId, type, platform);
		return Response.status(ClientResponse.Status.OK).entity(downloadInfos).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/webservices")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getWebServices() throws PhrescoException {
		List<WebService> webServices = getServiceManager().getWebServices();
		return Response.status(ClientResponse.Status.OK).entity(webServices).header("Access-Control-Allow-Origin", "*").build();
	}
}
