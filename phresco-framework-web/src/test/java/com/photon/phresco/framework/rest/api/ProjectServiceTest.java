package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.commons.model.ProjectInfo;

public class ProjectServiceTest {
	ProjectService projectService = new ProjectService();
	
	@Test
	public void editProject() {
		String customerId = "photon";
		String projectId = "cb3acb9f-5e9a-406c-94a8-8f911bbc743d";
		Response editProject = projectService.editProject(projectId, customerId);
		ResponseInfo<ProjectInfo> entity = (ResponseInfo<ProjectInfo>) editProject.getEntity();
		Assert.assertEquals(1, entity.getData().getNoOfApps());
	}
}
