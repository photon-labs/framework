package com.photon.phresco.framework.rest.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.rest.api.util.BufferMap;
import com.photon.phresco.framework.rest.api.util.ActionFunction;
import com.photon.phresco.framework.rest.api.util.ActionResponse;
import com.photon.phresco.framework.rest.api.util.ActionServiceConstant;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path ("/app")
public class ActionService implements ActionServiceConstant {
	
	private static final Logger S_LOGGER= Logger.getLogger(ActionService.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	@POST
	@Path("/build")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response build(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.build(request);
		} catch (Exception e) {

			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	@POST
	@Path("/deploy")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response deploy(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.deploy(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	
		
	}
	
	
	@POST
	@Path("/runUnitTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runUnitTest(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runUnitTest(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/runComponentTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runComponentTest(@Context HttpServletRequest request) throws PhrescoException  {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runComponentTest(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/codeValidate")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response codeValidate(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.codeValidate(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	@POST
	@Path("/runAgainstSource")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runAgainstSource(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runAgainstSource(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	@POST
	@Path("/stopServer")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response startServer(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.stopServer(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	
		
	}
	
	@POST
	@Path("/restartServer")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response restartServer(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.restartServer(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/performanceTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response performanceTest(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			actionFunction.prePopulatePerformanceTestData(request);
			response = actionFunction.performanceTest(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/loadTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response loadTest(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.loadTest(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/minification")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response minification(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			actionFunction.prePopulateMinificationData(request);
			response = actionFunction.minification(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Path("/startHub")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response startHub(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.startHub(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Path("/startNode")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response startNode(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.startNode(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	@POST
	@Path("/runFunctionalTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runFunctionalTest(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runFunctionalTest(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/stopHub")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response stopHub(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.stopHub(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/stopNode")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response stopNode(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.stopNode(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/checkForHub")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response checkForHub(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.checkForHub(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	
		
	}
	

	@POST
	@Path("/checkForNode")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response checkForNode(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.checkForNode(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	@POST
	@Path("/showStartedHubLog")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response showStartedHubLog(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.showStartedHubLog(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/showStartedNodeLog")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response showStartedNodeLog(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.showStartedNodeLog(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/generateReport")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response generateReport(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.generateSiteReport(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/ciSetup")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response ciSetup(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.ciSetup(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/ciStart")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response ciStart(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.ciStart(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/ciStop")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response ciStop(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.ciStop(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	@POST
	@Path("/printAsPdf")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response printAsPdf(@Context HttpServletRequest request) throws PhrescoException  {ActionFunction actionFunction = new ActionFunction();
	 
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			actionFunction.prePopulatePrintAsPDFData(request);
			response = actionFunction.printAsPdf(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	@GET
	@Path("/readlog")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response read(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionResponse response = new ActionResponse();		
		String status="";
		String uniquekey = request.getParameter(UNIQUE_KEY);
		String log="";
		
		if (isDebugEnabled) {
			S_LOGGER.debug("UNIQUE_KEY received :"+UNIQUE_KEY);
		}
		
		
		try {
			
			log = BufferMap.readBufferReader(uniquekey);
			
			if(log == null){
				
				if (isDebugEnabled) {
					S_LOGGER.debug("Log has finished and hence removing the bufferreader from the map");
				}
				BufferMap.removeBufferReader(uniquekey);
				status=COMPLETED;
			}
			else{
				
				status=INPROGRESS;
			}
		
		} catch (IOException e) {
			status=ERROR;
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
		} catch (Exception e) {
			status=ERROR;
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
		}
		
		response.setStatus(status);
		response.setLog(log);
		response.setUniquekey(uniquekey);
		
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@GET
	@Path("/removereader")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response removeLog(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionResponse response = new ActionResponse();		
		String status="";
		String uniquekey = request.getParameter(UNIQUE_KEY);
		String log="";
		
		if (isDebugEnabled) {
			S_LOGGER.debug("UNIQUE_KEY received :"+UNIQUE_KEY);
		}
		
		
		try {
			
			if( (BufferMap.getBufferReader(uniquekey))  != null ) {
				
				if (isDebugEnabled) {
					S_LOGGER.debug("Buffer reader is present and hence clearing it");
				}
				
				BufferMap.removeBufferReader(uniquekey);
				status = COMPLETED;
				
			}
			else {
				status=ERROR;
				throw new PhrescoException("No Buffer reader found");
			}
			
		
		} catch (Exception e) {
			status=ERROR;
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
		}
		
		response.setStatus(status);
		response.setLog(log);
		response.setUniquekey(uniquekey);
		
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	

}
