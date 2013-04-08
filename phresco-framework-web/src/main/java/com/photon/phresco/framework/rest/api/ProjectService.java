package com.photon.phresco.framework.rest.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;

@Path("/project")
public class ProjectService extends RestBase {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam("customer") String customer) throws PhrescoException {
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			List<ProjectInfo> projects = projectManager.discover(customer);
			Gson gson = new Gson();
			String json = gson.toJson(projects);
			json = "{"+"\"row\""+":"+json + "}";
			return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createProject(ProjectInfo projectinfo) throws PhrescoException {
		try {
			ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().create(projectinfo,
					getServiceManager());
			// updateLatestProject();
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

}
