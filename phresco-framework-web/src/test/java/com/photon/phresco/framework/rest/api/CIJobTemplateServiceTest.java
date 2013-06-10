package com.photon.phresco.framework.rest.api;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

public class CIJobTemplateServiceTest {
	
	@Test
	public void getJobTemplatesByEnvironemnt() {
		CIJobTemplateService ciJobTemplateService = new CIJobTemplateService();
		String customerId = "photon";
		String projectId = "09369f3e-366e-40fa-a983-6d7c753bfcc1";
		String envName = "Production";
		Response response = ciJobTemplateService.getJobTemplatesByEnvironemnt(customerId, projectId, envName);
		System.out.println("Response : " + response.getEntity());
		Assert.assertEquals(200, response.getStatus());
	}
}
