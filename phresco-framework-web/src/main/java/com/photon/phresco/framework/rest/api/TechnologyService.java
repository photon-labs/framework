package com.photon.phresco.framework.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.photon.phresco.commons.model.ApplicationType;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.TechnologyGroup;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ClientHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/technology")
public class TechnologyService {
	@GET
	@Path("/apptypes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listApptypes(@QueryParam("userId") String userId, @QueryParam("customerId") String customerId) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			ServiceManager serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			List<ApplicationType> applicationTypes = serviceManager.getApplicationTypes(customerId);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null, "Apptypes listed successfully", applicationTypes);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e, "appTypes not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/customerinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listApptypeTechInfo(@QueryParam("customerName") String customerName) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			Client client = ClientHelper.createClient();
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
	        WebResource resource = client.resource(configuration.getServerPath() + "/admin/customers");
	        resource = resource.queryParam("customerName", customerName);
	        resource.accept(MediaType.APPLICATION_JSON);
	        ClientResponse response = resource.get(ClientResponse.class);
	        GenericType<Customer> genericType = new GenericType<Customer>() {};
	        Customer customer = response.getEntity(genericType);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null, "customer returned successfully", customer);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			
		} catch (Exception e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e, "CustomerInfo not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/techgroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listtechnologyGroup(@QueryParam("customerId") String customerId,
			@QueryParam("appTypeId") String appTypeId, @QueryParam("userId") String userId) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			ServiceManager serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null,	"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin","*").build();
			}
			List<TechnologyGroup> technologyGroups = serviceManager.getTechnologyGroups(customerId, appTypeId);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, null,
					"technologyGroups of customer returned successfully", technologyGroups);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvaluation(responseData, e,"technologyGroups of customer not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

}
