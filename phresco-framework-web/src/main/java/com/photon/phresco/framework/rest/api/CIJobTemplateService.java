package com.photon.phresco.framework.rest.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.api.client.ClientResponse.Status;

import com.photon.phresco.framework.model.CIJobTemplate;

@Path("/jobTemplate")
public class CIJobTemplateService extends RestBase {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("name") String name, @QueryParam("appId") String appId, @QueryParam("projId") String projId, @QueryParam("customerId") String customerId, @QueryParam("userId") String userId) {
		ResponseInfo<List<CIJobTemplate>> responseData = new ResponseInfo<List<CIJobTemplate>>();
		try {
			List<CIJobTemplate> ciJobTemplates = null;
			// Get all job templates
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, null, "Job Templates listed successfully", ciJobTemplates);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<List<CIJobTemplate>> finalOutput = responseDataEvaluation(responseData, e, "Job Templates failed to list", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@PathParam("name") int name) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIJobTemplate ciJobTemplate = null;
			// get job template by name
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template retrived successfully", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates failed to retrive", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(CIJobTemplate ciJobTemplate) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			// add job template
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template added successfully", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates failed to add", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(CIJobTemplate ciJobTemplate) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			// update job template
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template updated successfully", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates updation failed", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("name") String name) {
		ResponseInfo<CIJobTemplate> responseData = new ResponseInfo<CIJobTemplate>();
		try {
			CIJobTemplate ciJobTemplate = null;
			// delete job template
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, null, "Job Template deleted successfully", ciJobTemplate);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<CIJobTemplate> finalOutput = responseDataEvaluation(responseData, e, "Job Templates deletion failes", null);
			return Response.status(Status.EXPECTATION_FAILED).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
}
