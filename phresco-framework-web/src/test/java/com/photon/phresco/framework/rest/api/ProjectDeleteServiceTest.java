package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.photon.phresco.framework.model.DeleteProjectInfo;

public class ProjectDeleteServiceTest {
	
	ProjectService projectservice = new ProjectService();
	
	@Test
	public void deleteProjectTest() {
		List<String> projectToDel = new ArrayList<String>();
		projectToDel.add("239");
		projectToDel.add("AddGit1-wordpress3.4.2");
		projectToDel.add("TestJquery");
		projectToDel.add("TestGitProject");
		projectToDel.add("TestProject");
		
		DeleteProjectInfo deleteProjectInfo = new DeleteProjectInfo();
		deleteProjectInfo.setAppDirNames(projectToDel);
		deleteProjectInfo.setActionType("project");
		Response deleteproject = projectservice.deleteproject(deleteProjectInfo);
		assertEquals(200 , deleteproject.getStatus());
	}
}
