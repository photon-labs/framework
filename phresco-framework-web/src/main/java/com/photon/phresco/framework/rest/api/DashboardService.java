package com.photon.phresco.framework.rest.api;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.Dashboard;
import com.photon.phresco.commons.model.DashboardInfo;
import com.photon.phresco.commons.model.Dashboards;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.commons.model.Widget;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.logger.SplunkLog4JFactory;
import com.photon.phresco.util.DashboardSearchInfo;
import com.photon.phresco.util.ServiceConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.wink.json4j.OrderedJSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse.Status;

@Path(ServiceConstants.REST_API_DASHBOARD)
public class DashboardService extends RestBase implements ServiceConstants, FrameworkConstants, ResponseCodes {
	private static final Logger LOGGER = Logger.getLogger(DashboardService.class);
	
	String status;
	String errorCode;
	String successCode;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response configureDashboardInfo(DashboardInfo dashboardInfo) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			String dashboardid = PhrescoFrameworkFactory.getProjectManager().configureDashboardConfig(dashboardInfo);

			if (dashboardid != null) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000001;
				ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null,
						dashboardid, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010001;
				ResponseInfo<Dashboard> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010001;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DASHBOARD_ID)
	public Response getDashboardInfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, @QueryParam(REST_QUERY_APPDIR_NAME) String appdirname, @PathParam(REST_QUERY_DASHBOARD_ID) String dashboardid ) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			Dashboard dashboard = PhrescoFrameworkFactory.getProjectManager().getDashboardConfig(projectid, appdirname, dashboardid);
			if (dashboard!=null) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000002;
				ResponseInfo<Dashboard> finalOutput = responseDataEvaluation(responseData, null,
						dashboard, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010002;
				ResponseInfo<HashMap<String, String>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010002;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();}
	}	

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDashboardInfo(DashboardInfo dashboardInfo) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			if (PhrescoFrameworkFactory.getProjectManager().updateDashboardConfig(dashboardInfo)) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000003;
				ResponseInfo<Dashboard> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010003;
				ResponseInfo<Dashboard> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010003;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();}
	}	

	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteDashboardInfo(DashboardInfo dashboardInfo) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			if (PhrescoFrameworkFactory.getProjectManager().deleteDashboardConfig(dashboardInfo)) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000011;
				ResponseInfo<Dashboard> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010011;
				ResponseInfo<Dashboard> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010011;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();}
	}	

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllDashboardInfo(@QueryParam(REST_QUERY_PROJECTID) String projectid) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			HashMap<String, Dashboards> appDashboards = PhrescoFrameworkFactory.getProjectManager().listAllDashboardConfig(projectid);
			if (appDashboards!=null && !appDashboards.isEmpty()) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000008;
				ResponseInfo<HashMap<String, Dashboards>> finalOutput = responseDataEvaluation(responseData, null,
						appDashboards, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010008;
				ResponseInfo<HashMap<String, Widget>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010008;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET)
	public Response addDashboardWidgetInfo(DashboardInfo dashboardInfo) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			String widgetId = PhrescoFrameworkFactory.getProjectManager().addDashboardWidgetConfig(dashboardInfo);
			if (widgetId != null) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000004;
				ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null,
						widgetId, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010004;
				ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010004;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}


	}	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET_ID)
	public Response getDashboardWidgetInfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appdirname, 
			@QueryParam(REST_QUERY_DASHBOARD_ID) String dashboardid, @PathParam(REST_QUERY_WIDGET_ID) String widgetid) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			Widget widget = PhrescoFrameworkFactory.getProjectManager().getDashboardWidgetConfig(projectid, appdirname, dashboardid, widgetid);
			if (widget !=null) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000005;
				ResponseInfo<Widget> finalOutput = responseDataEvaluation(responseData, null,
						widget, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010005;
				ResponseInfo<Widget> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010005;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}
	}	

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET)
	public Response updateDashboardWidgetInfo(DashboardInfo dashboardInfo) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			if (PhrescoFrameworkFactory.getProjectManager().updateDashboardWidgetConfig(dashboardInfo)) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000006;
				ResponseInfo<HashMap<String, Widget>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010006;
				ResponseInfo<HashMap<String, Widget>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010006;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET)
	public Response deleteDashboardWidgetInfo(DashboardInfo dashboardInfo) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			if (PhrescoFrameworkFactory.getProjectManager().deleteDashboardWidgetConfig(dashboardInfo)) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000012;
				ResponseInfo<HashMap<String, Widget>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010012;
				ResponseInfo<HashMap<String, Widget>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010012;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET)
	public Response listDashboardWidgetInfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, @QueryParam(REST_QUERY_APPDIR_NAME) String appdirname) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			Dashboards dashboards = PhrescoFrameworkFactory.getProjectManager().listDashboardWidgetConfig(projectid, appdirname);
			if (dashboards!=null) {
				status = RESPONSE_STATUS_SUCCESS;
				errorCode = PHRD000007;
				ResponseInfo<Dashboards> finalOutput = responseDataEvaluation(responseData, null,
						dashboards, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010007;
				ResponseInfo<HashMap<String, Widget>> finalOutput = responseDataEvaluation(responseData, null,
						null, status, errorCode);

				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}
		}catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD010007;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}

	}

	@POST
	@Path(REST_API_WIDGET_SEARCH)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String searchData(DashboardSearchInfo dashboardSearchInfo) throws JSONException {
		try {
			OrderedJSONObject reponsedata = PhrescoFrameworkFactory.getProjectManager().getdata(dashboardSearchInfo);
			System.out.println("Reponse data obatined is "+reponsedata);
			LOGGER.info("Reponse data obatined is "+reponsedata);
			if (reponsedata != null) {

				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHRD000009;
				OrderedJSONObject finalOutput = new OrderedJSONObject();
				finalOutput.put("data",reponsedata);
				finalOutput.put("exception","null");
				finalOutput.put("responseCode",successCode);
				finalOutput.put("status",status);
				return finalOutput.toString();
			}
			else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD010009;
				JSONObject finalOutput = new JSONObject();
				finalOutput.put("data","null");
				finalOutput.put("exception","null");
				finalOutput.put("responseCode",errorCode);
				finalOutput.put("status",status);
				return finalOutput.toString();	
			}
		}catch (Exception e) {

			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHRD010010;
			JSONObject finalOutput = new JSONObject();
			finalOutput.put("data","null");
			finalOutput.put("exception",e);
			finalOutput.put("responseCode",errorCode);
			finalOutput.put("status",status);
			return finalOutput.toString();		
		}
	}
}