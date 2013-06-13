package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

public class ParameterServiceTest extends RestBaseTest {
	
	ParameterService parameterService = new ParameterService();
	
	public ParameterServiceTest() {
		super();
	}
	
	@Test
	public void getDynamicParameter() {
		String appDirName = "sample3-HTML5-JQuery-Mobile-Widget";
		String goal = "package";
		String phase = "ci";
		Response response = parameterService.getParameter(appDirName, goal, phase);
		Assert.assertEquals(200, response.getStatus());
	}
}
