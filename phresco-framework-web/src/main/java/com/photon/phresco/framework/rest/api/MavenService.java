package com.photon.phresco.framework.rest.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.core.search.indexing.InternalSearchDocument;

import com.google.gson.JsonObject;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.rest.api.util.BufferMap;
import com.photon.phresco.framework.rest.api.util.MavenFunctions;
import com.photon.phresco.framework.rest.api.util.MavenResponse;
import com.photon.phresco.framework.rest.api.util.MavenServiceConstants;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path ("/maven")
public class MavenService implements MavenServiceConstants {
	
	private static final Logger S_LOGGER= Logger.getLogger(MavenService.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	@POST
	@Path("/build")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response build(@Context HttpServletRequest request) throws PhrescoException  {
		MavenFunctions lMavenFunctions = new MavenFunctions();
		MavenResponse response = lMavenFunctions.processRequest(request,BUILDPROJECT);
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	@POST
	@Path("/deploy")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response deploy(@Context HttpServletRequest request) throws PhrescoException  {
		MavenFunctions lMavenFunctions = new MavenFunctions();
		MavenResponse response = lMavenFunctions.processRequest(request,DEPLOYPROJECT);
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	@POST
	@Path("/runUnitTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runUnitTest(@Context HttpServletRequest request) throws PhrescoException  {
		MavenFunctions lMavenFunctions = new MavenFunctions();
		MavenResponse response = lMavenFunctions.processRequest(request,UNIT_TEST);
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	@POST
	@Path("/codeValidate")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response codeValidate(@Context HttpServletRequest request) throws PhrescoException  {
		MavenFunctions lMavenFunctions = new MavenFunctions();
		MavenResponse response = lMavenFunctions.processRequest(request,CODE_VALIDATE);
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	@POST
	@Path("/runAgainstSource")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runAgainstSource(@Context HttpServletRequest request) throws PhrescoException  {
		MavenFunctions lMavenFunctions = new MavenFunctions();
		MavenResponse response = lMavenFunctions.processRequest(request,RUN_AGAINST_SOURCE);
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	@POST
	@Path("/stopServer")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response startServer(@Context HttpServletRequest request) throws PhrescoException  {
		MavenFunctions lMavenFunctions = new MavenFunctions();
		MavenResponse response = lMavenFunctions.processRequest(request,STOP_SERVER);
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Path("/restartServer")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response restartServer(@Context HttpServletRequest request) throws PhrescoException  {
		MavenFunctions lMavenFunctions = new MavenFunctions();
		MavenResponse response = lMavenFunctions.processRequest(request,RESTART_SERVER);
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@GET
	@Path("/readlog")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response read(@Context HttpServletRequest request) throws PhrescoException  {
		
		MavenResponse response = new MavenResponse();		
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
	

}
