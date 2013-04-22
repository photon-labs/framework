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
public class ProjectService extends LoginService implements FrameworkConstants {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("customerId") String customer) throws PhrescoException {
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			List<ProjectInfo> projects = projectManager.discover(customer);
			return Response.ok(projects).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProject(ProjectInfo projectinfo, @QueryParam("userId") String userId) throws PhrescoException {
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().create(projectinfo,
					serviceManager);
			return Response.status(Status.OK).entity(projectInfo).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	@GET
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProject(@QueryParam("appDirName") String appDirName) throws PhrescoException {
		File projectInfoFile = new File(Utility.getProjectHome() + appDirName + 
				File.separator + ".phresco" + File.separator + "project.info");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(projectInfoFile));
			ProjectInfo projectInfo = (ProjectInfo) new Gson().fromJson(reader, ProjectInfo.class);
			return Response.status(Status.OK).entity(projectInfo).header("Access-Control-Allow-Origin", "*").build();
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProject(@QueryParam("oldAppDirName") String oldAppDirName , ProjectInfo projectinfo, @QueryParam("userId") String userId) throws PhrescoException {
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().update(projectinfo, serviceManager, oldAppDirName);
			return Response.status(Status.OK).entity(projectInfo).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	@GET
	@Path("/editApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editApplication(@QueryParam("appDirName") String appDirName) throws PhrescoException {
		File projectInfoFile = new File(Utility.getProjectHome() + appDirName + 
				File.separator + ".phresco" + File.separator + "project.info");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(projectInfoFile));
			ProjectInfo projectInfo = (ProjectInfo) new Gson().fromJson(reader, ProjectInfo.class);
			List<ApplicationInfo> appInfos = projectInfo.getAppInfos();
			for (ApplicationInfo applicationInfo : appInfos) {
				if(applicationInfo.getAppDirName().equals(appDirName)) {
					return Response.status(Status.OK).entity(applicationInfo).header("Access-Control-Allow-Origin", "*").build();
				}
			}
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
		return Response.status(Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteproject(List<ApplicationInfo> appInfos) throws PhrescoException {
		BufferedReader reader = null;
    	try {
	    	ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
	    	if (CollectionUtils.isNotEmpty(appInfos)) {
	    	    for (ApplicationInfo appInfo : appInfos) {
	    	        StringBuilder sb = new StringBuilder(Utility.getProjectHome())
	    	        .append(appInfo.getAppDirName())
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
	    	            	return Response.status(Status.BAD_REQUEST).entity("Unable To Delete").header("Access-Control-Allow-Origin", "*").build();
	    	            }
	    	        }
	    	    }
	    	}
	    	projectManager.delete(appInfos);
    	}catch (PhrescoException e) {
    		throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		}
		return Response.status(Status.OK).entity(appInfos).header("Access-Control-Allow-Origin", "*").build();
	}
}
