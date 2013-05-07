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
		MavenResponse response = lMavenFunctions.processBuildRequest(request);
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
				BufferMap.readBufferReader(uniquekey);
				status=COMPLETED;
			}
			else{
				
				status=INPROGRESS;
			}
		
		} catch (IOException e) {
			status=ERROR;
			S_LOGGER.error(e.getMessage());
			response.setService_exception(e.getMessage());
		} catch (Exception e) {
			status=ERROR;
			S_LOGGER.error(e.getMessage());
			response.setService_exception(e.getMessage());
		}
		
		response.setStatus(status);
		response.setLog(log);
		response.setService_exception("");
		response.setUniquekey(uniquekey);
		
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	

}
