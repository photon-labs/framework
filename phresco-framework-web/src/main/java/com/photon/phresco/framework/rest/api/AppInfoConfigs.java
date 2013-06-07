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
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/appConfig")
public class AppInfoConfigs extends RestBase {
	
	@GET
	@Path("/list")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getDownloadInfos(@QueryParam("customerId") String customerId, @QueryParam("tech-id") String techId,
			@QueryParam("type") String type, @QueryParam("platform") String platform, @QueryParam("userId") String userId) {
		ResponseInfo<List<DownloadInfo>> responseData = new ResponseInfo<List<DownloadInfo>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<DownloadInfo>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<DownloadInfo> downloadInfos = serviceManager.getDownloads(customerId, techId, type, platform);
			 if (CollectionUtils.isNotEmpty(downloadInfos)) {
	            	Collections.sort(downloadInfos, sortByNameInAlphaOrder());
	            }
			ResponseInfo<List<DownloadInfo>> finalOutput = responseDataEvaluation(responseData, null, " Configuration listed successfully", downloadInfos);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<DownloadInfo>> finalOutput = responseDataEvaluation(responseData, e, "Configuration not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/webservices")
	@Produces (MediaType.APPLICATION_JSON)
	public Response getWebServices(@QueryParam("userId") String userId) {
		ResponseInfo<List<WebService>> responseData = new ResponseInfo<List<WebService>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo<List<WebService>> finalOutput = responseDataEvaluation(responseData, null, "UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<WebService> webServices = serviceManager.getWebServices();
			ResponseInfo<List<WebService>> finalOutput = responseDataEvaluation(responseData, null, " Configuration listed successfully", webServices);
			return Response.status(ClientResponse.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<WebService>> finalOutput = responseDataEvaluation(responseData, e, "Webservice configuration not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
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
