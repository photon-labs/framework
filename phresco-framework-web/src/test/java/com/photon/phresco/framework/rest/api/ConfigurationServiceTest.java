package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.CronExpressionInfo;

public class ConfigurationServiceTest extends RestBaseTest {
	
	ConfigurationService configurationService = new ConfigurationService();
	
	public ConfigurationServiceTest() {
		super();
	}
	
//	@Test
	public void  getSettingsTemplateTest() {
		String appDirName = "bar-WordPress";
		String customerId = getCustomer().get(0);
		String userId = "admin";
		String type = "Server";
		Response response = configurationService.getSettingsTemplate(appDirName, customerId, userId, type);
		System.out.println("Response : " + response.getEntity());
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testCronExpressionSuccess() throws PhrescoException {
		CronExpressionInfo cron = new CronExpressionInfo();
		cron.setCronBy("Weekly");
		cron.setEvery("false");
		cron.setHours("5");
		cron.setMinutes("23");
		cron.setWeek("3");
		cron.setMonth("9");
		cron.setDay("8");
		
		Response cronValidation = configurationService.cronValidation(cron);
		ResponseInfo<String> entity = (ResponseInfo<String>) cronValidation.getEntity();
		assertEquals("23 5 * * 3" , entity.getData());
	}
	
	@Test
	public void testCronExpressionFailure() throws PhrescoException {
		CronExpressionInfo cron = new CronExpressionInfo();
		cron.setCronBy("Weekly");
		cron.setEvery("false");
		cron.setHours("5");
		cron.setMinutes("24");
		cron.setWeek("3");
		cron.setMonth("9");
		cron.setDay("8");
		
		Response cronValidation = configurationService.cronValidation(cron);
		ResponseInfo<String> entity = (ResponseInfo<String>) cronValidation.getEntity();
		Assert.assertNotSame("CronExpression  not Matching", "23 5 * * 3" , entity.getData());
	}
	
//	@Test
	public void deleteEnvTest() {
	String appDirName = "Pom-Mojo-Test-javawebservice";
	String envName = "Testing";
	Response response = configurationService.deleteEnv(appDirName, envName);
	System.out.println(response.getEntity());
	Assert.assertEquals(200, response.getStatus());
	}

}
