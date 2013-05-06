package com.photon.phresco.framework.rest.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.configuration.ConfigurationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/project")
public class ProjectService implements FrameworkConstants {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("customerId") String customer) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			List<ProjectInfo> projects = projectManager.discover(customer);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Project List Successfully", projects);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Project List failed", null);
        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProject(ProjectInfo projectinfo, @QueryParam("userId") String userId) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			ServiceManager serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().create(projectinfo,
					serviceManager);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Project created Successfully", projectInfo);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Project creation failed", null);
        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProject(@QueryParam("projectId") String projectId, @QueryParam("customerId") String customerId ) {
		ProjectInfo projectInfo = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			projectInfo = projectManager.getProject(projectId, customerId);	
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Project edit Successfully", projectInfo);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Project edit failed", null);
        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProject(@QueryParam("oldAppDirName") String oldAppDirName , ProjectInfo projectinfo, @QueryParam("userId") String userId) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			ServiceManager serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(userId);
			if(serviceManager == null) {
				ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "UnAuthorized User", null);
	        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().update(projectinfo, serviceManager, oldAppDirName);
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Project update Successfully", projectInfo);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Project update failed", null);
        	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/editApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editApplication(@QueryParam("appDirName") String appDirName) {
		File projectInfoFile = new File(Utility.getProjectHome() + appDirName + 
				File.separator + ".phresco" + File.separator + "project.info");
		BufferedReader reader = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			reader = new BufferedReader(new FileReader(projectInfoFile));
			ProjectInfo projectInfo = (ProjectInfo) new Gson().fromJson(reader, ProjectInfo.class);
			List<ApplicationInfo> appInfos = projectInfo.getAppInfos();
			for (ApplicationInfo applicationInfo : appInfos) {
				if(applicationInfo.getAppDirName().equals(appDirName)) {
					ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Application edit Successfully", projectInfo);
					return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
				}
			}
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Application edit Successfully", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		return null;
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteproject(List<String> appDirnames) {
		BufferedReader reader = null;
		ResponseInfo responseData = new ResponseInfo();
    	try {
	    	ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
	    	if (CollectionUtils.isNotEmpty(appDirnames)) {
	    	    for (String appDirName : appDirnames) {
	    	        StringBuilder sb = new StringBuilder(Utility.getProjectHome())
	    	        .append(appDirName)
	    	        .append(File.separator)
	    	        .append(FOLDER_DOT_PHRESCO)
	    	        .append(File.separator)
	    	        .append(RUNAGNSRC_INFO_FILE);
	    	        File file = new File(sb.toString());
	    	        if (file.exists()) {
	    	            Gson gson = new Gson();
	    	            reader = new BufferedReader(new FileReader(file));
	    	            ConfigurationInfo configInfo = gson.fromJson(reader, ConfigurationInfo.class);
	    	            int port = Integer.parseInt(configInfo.getServerPort());
	    	            boolean connectionAlive = Utility.isConnectionAlive(HTTP_PROTOCOL, LOCALHOST, port);
	    	            if(connectionAlive) {
	    	            	ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Application unable to delete", null);
	    	            	return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	    	            }
	    	        }
	    	    }
	    	}
	    	projectManager.delete(appDirnames);
    	}catch (PhrescoException e) {
    		ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e, "Application unable to delete", null);
    		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, e,"Application unable to delete", null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo finalOutput = ServiceManagerMap.responseDataEvalution(responseData, null, "Application delete Successfully", null);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
}
