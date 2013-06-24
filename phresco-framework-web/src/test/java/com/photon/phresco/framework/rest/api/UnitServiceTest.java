package com.photon.phresco.framework.rest.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.util.ActionResponse;

public class UnitServiceTest extends RestBaseTest {

	QualityService service = new QualityService();

	@Test
	public void testUnitTestReportOption() throws PhrescoException, IOException {
		Response response = service.unit(appDirName, userId);
		int status = response.getStatus();
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void trigerTest() throws PhrescoException {
		ActionService actionService = new ActionService();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("testAgainst", "java");
		request.setParameter("customerId", customerId);
		request.setParameter("projectId", projectId);
		request.setParameter("appId", appCode);
		request.setParameter("username", userId);
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response runUnitTest = actionService.runUnitTest(httpServletRequest);
		ActionResponse entity = (ActionResponse) runUnitTest.getEntity();
		String uniqueKey = entity.getUniquekey();
		readLog(uniqueKey);
	}

	@Test
	public void testGetTestSuites() throws PhrescoException, IOException {
		Response response = service.getTestSuites(appDirName, "unit", "java", "");
		int status = response.getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	public void testgetTestReports() throws PhrescoException, IOException {
		Response response = service.getTestReports(appDirName, "unit", "java", "", "com.photon.phresco.service.TestCase");
		int status = response.getStatus();
		Assert.assertEquals(200, status);
	}
}
