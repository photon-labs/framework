package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.photon.phresco.exception.PhrescoException;

public class ConfigurationServiceTest extends RestBaseTest {
	
	@Before
	public void initialize() throws PhrescoException {
		new RestBaseTest();
	}
	
	@Test
	public void  getSettingsTemplateTest() {
		ConfigurationService configurationService = new ConfigurationService();
		String appDirName = "bar-WordPress";
		String customerId = getCustomer().get(0);
		String userId = "admin";
		String type = "Server";
		Response response = configurationService.getSettingsTemplate(appDirName, customerId, userId, type);
		System.out.println("Response : " + response.getEntity());
		Assert.assertEquals(200, response.getStatus());
	}
}
