package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.util.ActionResponse;

public class CodeValidationServiceTest extends RestBaseTest {

	static String uniqueKey = "";

	@Test
	public void testSonarParameter() throws PhrescoException, IOException {
		ParameterService service = new ParameterService();
		Response response = service.getParameter("JWS-javawebservice", "validate-code", "", userId, customerId);
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void testSonarCodeValidationReportTypes() throws PhrescoException, IOException {
		ParameterService service = new ParameterService();
		MockHttpServletRequest request = new  MockHttpServletRequest();
		request.setServerPort(9000);
		request.setServerName(appDirName);
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Response response = service.getCodeValidationReportTypes("JWS-javawebservice", "validate-code", "", httpServletRequest);
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void triggerCodeValidation() throws PhrescoException, IOException {
		ActionService service = new ActionService();
		MockHttpServletRequest request = new  MockHttpServletRequest();
		request.setParameter("appId", "TestProject");
		request.setParameter("projectId", projectId);
		request.setParameter("customerId", customerId);
		request.setParameter("username", userId);
		request.setParameter("sonar", "src");
		request.setParameter("src", "java");
		request.setParameter("skipTests", "false");
		HttpServletRequest httprequest = (HttpServletRequest)request;
		Response response  = service.codeValidate(httprequest);
		ActionResponse entity = (ActionResponse) response.getEntity();
		uniqueKey = entity.getUniquekey();
		readLog(uniqueKey);
		assertEquals("STARTED", entity.getStatus());
		assertEquals(200, response.getStatus());
	} 

	@Test
	public void testSonarReport() throws PhrescoException, IOException {
		ParameterService service = new ParameterService();
		MockHttpServletRequest request = new  MockHttpServletRequest();
		request.setServerPort(9000);
		request.setServerName(appDirName);
		Response response = service.getIframeReport("217bd500-a109-4c1d-bfa1-02c89652b989", "admin", "JWS-javawebservice", "java", request);
		Assert.assertEquals(200, response.getStatus());
	}


	@Test
	public void codeValidationWithoutAppId() throws PhrescoException, IOException {
		ActionService service = new ActionService();
		MockHttpServletRequest request = new  MockHttpServletRequest();
		request.setParameter("appId", "null");
		HttpServletRequest httprequest = (HttpServletRequest)request;
		Response response  = service.codeValidate(httprequest);
		ActionResponse entity = (ActionResponse) response.getEntity();
		assertEquals("ERROR", entity.getStatus());
	}

	@Test
	public void codeValidationWithoutProjectId() throws PhrescoException, IOException {
		ActionService service = new ActionService();
		MockHttpServletRequest request = new  MockHttpServletRequest();
		request.setParameter("appId", "TestProject");
		request.setParameter("projectId", "null");
		HttpServletRequest httprequest = (HttpServletRequest)request;
		Response response  = service.codeValidate(httprequest);
		ActionResponse entity = (ActionResponse) response.getEntity();
		assertEquals("ERROR", entity.getStatus());
	}

	@Test
	public void codeValidationWithoutCustomerId() throws PhrescoException, IOException {
		try {
			ActionService service = new ActionService();
			MockHttpServletRequest request = new  MockHttpServletRequest();
			request.setParameter("appId", "TestProject");
			request.setParameter("projectId", projectId);
			request.setParameter("customerId", "null");
			HttpServletRequest httprequest = (HttpServletRequest)request;
			Response response  = service.codeValidate(httprequest);
			ActionResponse entity = (ActionResponse) response.getEntity();
			assertEquals("ERROR", entity.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
	}

	@Test
	public void codeValidationWithoutUsername() throws PhrescoException, IOException {
		ActionService service = new ActionService();
		MockHttpServletRequest request = new  MockHttpServletRequest();
		request.setParameter("appId", "TestProject");
		request.setParameter("projectId", projectId);
		request.setParameter("customerId", customerId);
		request.setParameter("username", "null");
		HttpServletRequest httprequest = (HttpServletRequest)request;
		Response response  = service.codeValidate(httprequest);
		ActionResponse entity = (ActionResponse) response.getEntity();
		assertEquals("ERROR", entity.getStatus());
	}


	@Test
	public void codeValidationWithoutParameter() throws PhrescoException, IOException {
		ActionService service = new ActionService();
		MockHttpServletRequest request = new  MockHttpServletRequest();
		HttpServletRequest httprequest = (HttpServletRequest)request;
		Response response  = service.codeValidate(httprequest);
		ActionResponse entity = (ActionResponse) response.getEntity();
		assertEquals("ERROR", entity.getStatus());
	}
}
