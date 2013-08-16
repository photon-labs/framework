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

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.PerformanceUrls;
import com.photon.phresco.framework.rest.api.util.ActionFunction;
import com.photon.phresco.framework.rest.api.util.ActionResponse;
import com.photon.phresco.framework.rest.api.util.ActionServiceConstant;
import com.photon.phresco.framework.rest.api.util.BufferMap;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class ActionService.
 */
@Path ("/app")
public class ActionService implements ActionServiceConstant, FrameworkConstants, ResponseCodes {
	
	/** The Constant S_LOGGER. */
	private static final Logger S_LOGGER= Logger.getLogger(ActionService.class);
	
	/** The is debug enabled. */
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	/**
	 * Builds the application.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/build")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response build(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateBuildModelData(request);
			response = actionFunction.build(request);
			if(response.getStatus() != RESPONSE_STATUS_FAILURE) {
				response.setResponseCode(PHR700004);
			}
		} catch (Exception e) {

			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR710008);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	/**
	 * Deploy.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/deploy")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response deploy(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.deploy(request);
			if(response.getStatus() != RESPONSE_STATUS_FAILURE) {
				response.setResponseCode(PHR700005);
			}
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR710009);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	
		
	}
	
	/**
	 * @param request
	 * @return
	 * @throws PhrescoException
	 */
	@POST
	@Path("/processBuild")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response processBuild(@Context HttpServletRequest request) throws PhrescoException  {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateBuildProcessData(request);
			response = actionFunction.processBuild(request);
			response.setResponseCode(PHR700007);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR710015);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	
		
	}
	
	/**
	 * Run unit test.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/runUnitTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runUnitTest(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runUnitTest(request);
			response.setResponseCode(PHRQ100002);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ110002);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Run component test.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/runComponentTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runComponentTest(@Context HttpServletRequest request) throws PhrescoException  {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runComponentTest(request);
			response.setResponseCode(PHRQ200001);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ210001);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Code validate.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/codeValidate")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response codeValidate(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.codeValidate(request);
			response.setResponseCode(PHR500002);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR510004);
			response.setStatus(RESPONSE_STATUS_ERROR);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	/**
	 * Run against source.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/runAgainstSource")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runAgainstSource(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runAgainstSource(request);
			if(response.getStatus() != RESPONSE_STATUS_FAILURE) {
				response.setResponseCode(PHR700006);
			}
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR710010);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	/**
	 * Start server.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/stopServer")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response stopServer(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.stopServer(request);
			response.setResponseCode(PHR700008);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR710016);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	
		
	}
	
	/**
	 * Restart server.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/restartServer")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response restartServer(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.restartServer(request);
			response.setResponseCode(PHR700009);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR710017);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Performance test.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/performanceTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response performanceTest(@Context HttpServletRequest request, PerformanceUrls performanceUrls) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.performanceTest(request, performanceUrls);
			response.setResponseCode(PHRQ500005);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ510006);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Performance test.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/ciPerformanceTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response ciPerformanceTest(@Context HttpServletRequest request, PerformanceUrls performanceUrls) throws PhrescoException  {
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			//actionFunction.prePopulateModelData(request);
			String jsonWriterForCi = actionFunction.jsonWriterForCi(request, performanceUrls);
			response.setStatus(jsonWriterForCi);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Load test.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/loadTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response loadTest(@Context HttpServletRequest request, PerformanceUrls performanceUrls) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.loadTest(request, performanceUrls);
			response.setResponseCode(PHRQ600003);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ610003);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Minification.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
	
	/**
	 * Start hub.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/startHub")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response startHub(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.startHub(request);
			response.setResponseCode(PHRQ300002);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310002);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	/**
	 * Start node.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/startNode")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response startNode(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.startNode(request);
			response.setResponseCode(PHRQ300003);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310003);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	/**
	 * Run functional test.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/runFunctionalTest")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response runFunctionalTest(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.runFunctionalTest(request);
			response.setResponseCode(PHRQ300004);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310004);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Stop hub.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/stopHub")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response stopHub(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.stopHub(request);
			response.setResponseCode(PHRQ300005);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310005);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Stop node.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/stopNode")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response stopNode(@Context HttpServletRequest request) throws PhrescoException  {
		
		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.stopNode(request);
			response.setResponseCode(PHRQ300006);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310006);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Check for hub.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310007);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	
		
	}
	

	/**
	 * Check for node.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310008);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	/**
	 * Show started hub log.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/showStartedHubLog")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response showStartedHubLog(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		response.setResponseCode(PHRQ300009);
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.showStartedHubLog(request);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310009);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Show started node log.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/showStartedNodeLog")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response showStartedNodeLog(@Context HttpServletRequest request) throws PhrescoException  {

		ActionFunction actionFunction = new ActionFunction();
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulateModelData(request);
			response = actionFunction.showStartedNodeLog(request);
			response.setResponseCode(PHRQ300010);
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHRQ310010);
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Generate report.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
	
	/**
	 * Ci setup.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
	
	/**
	 * Ci start.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
	
	/**
	 * Ci stop.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
	
	
	/**
	 * Prints the as pdf.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/printAsPdf")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response printAsPdf(@Context HttpServletRequest request) throws PhrescoException  {
		ActionFunction actionFunction = new ActionFunction();
		boolean isReportAvailable = false;
		ActionResponse response = new ActionResponse();
		try	{
			actionFunction.prePopulatePrintAsPDFData(request);
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String appDirName = request.getParameter(APPDIR);
			String fromPage = request.getParameter(FROM_PAGE);
			ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			
			// is sonar report available
			if ((FrameworkConstants.ALL).equals(fromPage)) {
			isReportAvailable = actionFunction.isSonarReportAvailable(frameworkUtil, appInfo, request);
			}

			// is test report available
			if (!isReportAvailable) {
				isReportAvailable = actionFunction.isTestReportAvailable(frameworkUtil, appInfo, fromPage);
			}
//			boolean testReportAvailable = actionFunction.isTestReportAvailable(frameworkUtil, appInfo, fromPage);
			if (isReportAvailable) {
				response = actionFunction.printAsPdf(request);
			} else {
				response.setService_exception("Atleast one Test Report should be available Or Sonar report should be available");
				response.setResponseCode(PHR210020);
				response.setStatus(RESPONSE_STATUS_FAILURE);
			}
			
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setLog("");
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			response.setResponseCode(PHR210021);
			
		}
		return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	
	/**
	 * Read.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
				
				status=ActionServiceConstant.INPROGRESS;
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
	
	/**
	 * Removes the log.
	 *
	 * @param request the request
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
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
