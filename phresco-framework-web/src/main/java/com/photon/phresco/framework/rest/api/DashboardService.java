package com.photon.phresco.framework.rest.api;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.DashboardConfigInfo;
import com.photon.phresco.commons.model.DashboardWidgetConfigInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.util.DashboardSearchInfo;
import com.photon.phresco.util.ServiceConstants;
import java.util.List;
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
	@Path(REST_API_DASHBOARD_CONFIGURE)
		public Response getDashboardConfigureinfo(@QueryParam(REST_QUERY_PROJECTID) String projectid) {
			ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
			try {
				DashboardConfigInfo dashboardconfiginfo = null;
				dashboardconfiginfo = PhrescoFrameworkFactory.getProjectManager().getDashboardInfo(projectid);
				if (dashboardconfiginfo != null) {
					status = RESPONSE_STATUS_SUCCESS;
					errorCode = PHRD010001;
					ResponseInfo<DashboardConfigInfo> finalOutput = responseDataEvaluation(responseData, null,
							dashboardconfiginfo, status, errorCode);

					return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();
				}
				else {
					status = RESPONSE_STATUS_FAILURE;
					errorCode = PHRD000001;
					ResponseInfo<DashboardConfigInfo> finalOutput = responseDataEvaluation(responseData, null,
							dashboardconfiginfo, status, errorCode);

					return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();	
				}
				
		}catch (Exception e) {
			return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_DASHBOARD_UPDATE)
		public Response updateDashboardConfigureinfo(DashboardConfigInfo dashboardConfigInfo) {
			ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
			try {
				if (dashboardConfigInfo != null) {
					if (PhrescoFrameworkFactory.getProjectManager().getDashboardInfo(dashboardConfigInfo.getProjectid()) != null) {

						PhrescoFrameworkFactory.getProjectManager().updateDashboardInfo(dashboardConfigInfo);
					} else {
						PhrescoFrameworkFactory.getProjectManager().addDashboardInfo(dashboardConfigInfo);
					}
					status = RESPONSE_STATUS_SUCCESS;
					errorCode = PHRD010002;
					ResponseInfo<DashboardConfigInfo> finalOutput = responseDataEvaluation(responseData, null,
							null, status, errorCode);
					
					return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();	
				} 

				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHRD000002;
				ResponseInfo<DashboardConfigInfo> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_FAILURE, PHRD010002);
				
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
				.build();	
			}catch (Exception e) {
			
				return Response.status(Status.OK).entity(responseData).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
					.build();
		}
	}	
	
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path(REST_API_WIDGET_ADD)
			public Response addDashboardWidgetConfigureInfo(DashboardWidgetConfigInfo dashboardWidgetConfigInfo, @QueryParam(REST_QUERY_PROJECTID) String projectId) {
				ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
				try {
					DashboardConfigInfo dashboardconfiginfo = null;
					dashboardconfiginfo = PhrescoFrameworkFactory.getProjectManager().getDashboardInfo(projectId);
					if (dashboardconfiginfo != null) {

						DashboardWidgetConfigInfo widget = PhrescoFrameworkFactory.getProjectManager().addDashboardWidgetInfo(dashboardWidgetConfigInfo, projectId);
						status = RESPONSE_STATUS_SUCCESS;
						errorCode = PHRD010003;
						ResponseInfo<DashboardWidgetConfigInfo> finalOutput = responseDataEvaluation(responseData, null,
								widget, status, errorCode);
						
						return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
						.build();
					}
					else {
						status = RESPONSE_STATUS_FAILURE;
						errorCode = PHRD000003;
						ResponseInfo<DashboardWidgetConfigInfo> finalOutput = responseDataEvaluation(responseData, null,
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
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path(REST_API_WIDGET_CONFIGURE)
			public Response configureDashboardWidgetConfigureInfo(DashboardWidgetConfigInfo dashboardWidgetConfigInfo , @QueryParam(REST_QUERY_PROJECTID) String projectId) {
				ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
				try {
					if (PhrescoFrameworkFactory.getProjectManager().configureDashboardWidgetInfo(dashboardWidgetConfigInfo , projectId)) {
						status = RESPONSE_STATUS_SUCCESS;
						errorCode = PHRD010004;
						ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null,
								null, status, errorCode);
						
						return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
						.build();
					}
					else {
						status = RESPONSE_STATUS_FAILURE;
						errorCode = PHRD000004;
						ResponseInfo<String> finalOutput = responseDataEvaluation(responseData, null,
								null , status, errorCode);
						
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
			public Response listAllDashboardWidgetConfigureInfo( @QueryParam(REST_QUERY_PROJECTID) String projectId) {
				ResponseInfo<ProjectInfo> responseData = new ResponseInfo<ProjectInfo>();
				try {
					List<DashboardWidgetConfigInfo> allWidgetsList = PhrescoFrameworkFactory.getProjectManager().listAllDashboardWidgetInfo(projectId);
					if (allWidgetsList != null) {

						status = RESPONSE_STATUS_SUCCESS;
						errorCode = PHRD010005;
						ResponseInfo<List<DashboardWidgetConfigInfo>> finalOutput = responseDataEvaluation(responseData, null,
								allWidgetsList, status, errorCode);

						return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
						.build();
					}
					else {
						status = RESPONSE_STATUS_FAILURE;
						errorCode = PHRD000005;
						ResponseInfo<DashboardConfigInfo> finalOutput = responseDataEvaluation(responseData, null,
								null, "No dashboard widgets found!!", "");

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
}