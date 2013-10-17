package com.photon.phresco.framework.rest.api;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.Dashboard;
import com.photon.phresco.commons.model.Dashboards;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.Widget;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.util.DashboardSearchInfo;
import com.photon.phresco.util.ServiceConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse.Status;

@Path(ServiceConstants.REST_API_DASHBOARD)
public class DashboardService extends RestBase implements ServiceConstants, FrameworkConstants, ResponseCodes {
	String status;
	String errorCode;
	String successCode;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DASHBOARD_CONFIGURE)
	public Response configureDashboardConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, 
			@QueryParam(REST_QUERY_APPID) String appid, 
			@QueryParam(REST_QUERY_APPCODE) String appcode, 
			@QueryParam(REST_QUERY_APPNAME) String appname, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appdirname,
			@QueryParam(REST_QUERY_DASHBOARD_NAME) String dashboardname, 
			@QueryParam(REST_QUERY_DATATYPE) String datatype , 
			@QueryParam(REST_QUERY_USER_NAME) String username, 
			@QueryParam(REST_QUERY_PASSWORD) String password, 
			@QueryParam(REST_QUERY_URL) String url) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			String dashboardid = PhrescoFrameworkFactory.getProjectManager().configureDashboardConfig(projectid, appid, appcode, appname, appdirname, dashboardname, datatype, username, password, url);
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DASHBOARD_GET)
	public Response getDashboardConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, @QueryParam(REST_QUERY_APPDIR_NAME) String appdirname, @QueryParam(REST_QUERY_DASHBOARD_ID) String dashboardid ) {
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}
	}	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DASHBOARD_UPDATE)
	public Response updateDashboardConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appdirname, 
			@QueryParam(REST_QUERY_DASHBOARD_ID) String dashboardid, 
			@QueryParam(REST_QUERY_DASHBOARD_NAME) String dashboardname, 
			@QueryParam(REST_QUERY_DATATYPE) String datatype , 
			@QueryParam(REST_QUERY_USER_NAME) String username, @QueryParam(REST_QUERY_PASSWORD) String password, 
			@QueryParam(REST_QUERY_URL) String url) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			if (PhrescoFrameworkFactory.getProjectManager().updateDashboardConfig(projectid, appdirname, dashboardid, dashboardname, datatype, username, password, url)) {
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}
	}	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DASHBOARD_LIST)
	public Response listAllDashboardConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid) {
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET_ADD)
	public Response addDashboardWidgetConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appdirname, 
			@QueryParam(REST_QUERY_DASHBOARD_ID) String dashboardid, 
			@QueryParam(REST_QUERY_WIDGET_NAME) String name,  
			@QueryParam(REST_QUERY_WIDGET_QUERY) String query , 
			@QueryParam(REST_QUERY_WIDGET_AUTOREFRESH) String autorefresh,
			@QueryParam(REST_QUERY_WIDGET_START_TIME) String starttime,
			@QueryParam(REST_QUERY_WIDGET_END_TIME) String endtime) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			Date starttime1 = getDate(starttime);
			Date endtime1 = getDate(endtime);
			String widgetId = PhrescoFrameworkFactory.getProjectManager().addDashboardWidgetConfig(projectid, appdirname, dashboardid, name, query, autorefresh, starttime1, endtime1);
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}


	}	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET_GET)
	public Response getDashboardWidgetConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appdirname, 
			@QueryParam(REST_QUERY_DASHBOARD_ID) String dashboardid, @QueryParam(REST_QUERY_WIDGET_ID) String widgetid) {
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}
	}	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET_UPDATE)
	public Response updateDashboardWidgetConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appdirname, 
			@QueryParam(REST_QUERY_DASHBOARD_ID) String dashboardid, 
			@QueryParam(REST_QUERY_WIDGET_ID) String widgetid, 
			@QueryParam(REST_QUERY_WIDGET_NAME) String name,  
			@QueryParam(REST_QUERY_WIDGET_QUERY) String query , 
			@QueryParam(REST_QUERY_WIDGET_AUTOREFRESH) String autorefresh,
			@QueryParam(REST_QUERY_WIDGET_START_TIME) String starttime,
			@QueryParam(REST_QUERY_WIDGET_END_TIME) String endtime) {
		ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
		try {
			Date starttime1 = getDate(starttime);
			Date endtime1 = getDate(endtime);
			if (PhrescoFrameworkFactory.getProjectManager().updateDashboardWidgetConfig(projectid, appdirname, dashboardid,widgetid, name, query, autorefresh, starttime1, endtime1)) {
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_WIDGET_LISTALL)
	public Response listDashboardWidgetConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid, @QueryParam(REST_QUERY_APPDIR_NAME) String appdirname) {
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
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
			.build();
		}

	}

	@POST
	@Path(REST_API_WIDGET_SEARCH)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String searchData(DashboardSearchInfo dashboardSearchInfo) throws JSONException {
		try {
			JSONObject reponsedata = PhrescoFrameworkFactory.getProjectManager().getsplunkdata(dashboardSearchInfo);
			System.out.println("Reponse data obatined is "+reponsedata);

			if (reponsedata != null) {

				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHRD010006;
				JSONObject finalOutput = new JSONObject();
				finalOutput.put("data",reponsedata);
				finalOutput.put("exception","null");
				finalOutput.put("responseCode",successCode);
				finalOutput.put("status",status);
				return finalOutput.toString();
			}
			else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD000006;
				JSONObject finalOutput = new JSONObject();
				finalOutput.put("data",reponsedata);
				finalOutput.put("exception","null");
				finalOutput.put("responseCode",errorCode);
				finalOutput.put("status",status);
				return finalOutput.toString();	
			}
		}catch (Exception e) {

			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRD000007;
			JSONObject finalOutput = new JSONObject();
			finalOutput.put("data","null");
			finalOutput.put("exception",e);
			finalOutput.put("responseCode",errorCode);
			finalOutput.put("status",status);
			return finalOutput.toString();		
		}
	}

	public Date getDate(String dateStr) throws PhrescoException {
		if(dateStr != null) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a"); 
			Date date;
			try {
				date = df.parse(dateStr);
				return date;
			} catch (ParseException e) {
				throw new PhrescoException();
			}
		}
		return null;

	}
}