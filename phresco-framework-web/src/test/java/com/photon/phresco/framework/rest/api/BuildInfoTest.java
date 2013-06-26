package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.util.ActionResponse;

public class BuildInfoTest extends RestBaseTest {

	ParameterService parameterservice = new ParameterService();
	ActionService actionservice = new ActionService();
	BuildInfoService buildinfoservice = new BuildInfoService();
	static String uniqueKey = "";
	
	@Test
	public void getBuildParams() {
		String goal = "package";
		String phase = "package";
		
		Response response = parameterservice.getParameter(appDirName, goal, phase, userId, customerId);
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void generateBuild() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("buildName", "sample");
		request.setParameter("buildNumber", "1");
		request.setParameter("environmentName", "Production");
		request.setParameter("logs", "showErrors");
		request.setParameter("skipTest", "true");
		request.setParameter("customerId", customerId);
		request.setParameter("projectId", "TestProject");
		request.setParameter("appId", "TestProject");
		request.setParameter("username", userId);
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response build = actionservice.build(httpServletRequest);
		ActionResponse entity = (ActionResponse) build.getEntity();
		uniqueKey = entity.getUniquekey();
		assertEquals("STARTED", entity.getStatus());
	}
	
	@Test
	public void readBuildLog() throws PhrescoException {
		
		assertEquals(true, readLog());
	}
	
	public Boolean readLog() throws PhrescoException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("uniquekey", uniqueKey);
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response build = actionservice.read(httpServletRequest);
		ActionResponse output = (ActionResponse) build.getEntity();
		System.out.println(output.getLog());
		if (output.getLog() != null) {

			if (output.getLog().contains("BUILD FAILURE")) {
				fail("Error occured ");
			}
			if ("INPROGRESS".equalsIgnoreCase(output.getStatus())) {
				readLog();
				return true;
			} else if ("COMPLETED".equalsIgnoreCase(output.getStatus())) {
				System.out.println("***** Log finished ***********");
				return true;
			} else if ("ERROR".equalsIgnoreCase(output.getStatus())) {
				fail("Error occured while retrieving the logs");
				return false;
			}
		}
		return false;
	}
	
	
	
	@Test
	public void getBuildList() {
		Response parameter = buildinfoservice.list(appDirName);
		ResponseInfo<List<BuildInfo>> entity = (ResponseInfo<List<BuildInfo>>) parameter.getEntity();
		List<BuildInfo> data = entity.getData();
		assertEquals(1, data.size());
	}
	
	@Test
	public void downloadBuild() {
		Response parameter = buildinfoservice.buildInfoZip("TestProject", 1);
		assertEquals(200, parameter.getStatus());
	}
	
	
	@Test
	public void deleteBuild() {
		String[] buildNumbers = {"1"};
		Response parameter = buildinfoservice.deleteBuild(buildNumbers, "TestProject", customerId, "TestProject");
		assertEquals(200, parameter.getStatus());
	}

	//@Test
	public void  checkServerStatus() {
		BuildInfoService buildInfoService = new BuildInfoService();
		Response response = buildInfoService.checkStatus(appDirName);
		Assert.assertEquals(200, response.getStatus());
	}
}
