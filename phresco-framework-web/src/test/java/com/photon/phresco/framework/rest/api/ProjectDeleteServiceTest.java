package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

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
		Response deleteproject = projectservice.deleteproject(projectToDel, "project");
		assertEquals(200 , deleteproject.getStatus());
	}
}
