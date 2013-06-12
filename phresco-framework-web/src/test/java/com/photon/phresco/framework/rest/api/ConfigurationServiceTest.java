package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.CronExpressionInfo;
import com.photon.phresco.configuration.Configuration;

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
	
	//@Test
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
	
	//@Test
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
	
	//@Test
	public void listEnvironmentsByProjectId() {
		String customerId = "photon";
		String projectId = "09369f3e-366e-40fa-a983-6d7c753bfcc1";
		Response response = configurationService.listEnvironmentsByProjectId(customerId, projectId);
		System.out.println("Response : " + response.getEntity());
		Assert.assertEquals(200, response.getStatus());
	}
	
	//	@Test
	public void deleteEnvTest() {
	String appDirName = "Pom-Mojo-Test-javawebservice";
	String envName = "Testing";
	Response response = configurationService.deleteEnv(appDirName, envName);
	System.out.println(response.getEntity());
	Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void configurationValidationTest() {
		Properties propertiesServer = new Properties();
		Properties propertiesEmail = new Properties();
		Properties propertiesSiteCore = new Properties();
		propertiesServer.setProperty("context", "CometDTesting");
		propertiesServer.setProperty("admin_username", "");
		propertiesServer.setProperty("deploy_dir", "D:/server for phresco testing/New Folder/");
		propertiesServer.setProperty("additional_context", "");
		propertiesServer.setProperty("port", "8888");
		propertiesServer.setProperty("admin_password", "");
		propertiesServer.setProperty("version", "7.0.x");
		propertiesServer.setProperty("type", "Apache Tomcat");
		propertiesServer.setProperty("remoteDeployment", "false");
		propertiesServer.setProperty("host", "localhost");
		propertiesServer.setProperty("protocol", "http");
		
		propertiesEmail.setProperty("port", "8596");
		propertiesEmail.setProperty("emailid", "test@gmail.com");
		propertiesEmail.setProperty("password", "test");
		propertiesEmail.setProperty("incoming_mail_server", "test");
		propertiesEmail.setProperty("incoming_mail_port", "test");
		propertiesEmail.setProperty("host", "test");
		propertiesEmail.setProperty("username", "test");
		
		propertiesSiteCore.setProperty("siteCoreInstPath", "");
		propertiesSiteCore.setProperty("protocol", "http");
		propertiesSiteCore.setProperty("host", "localhost");
		propertiesSiteCore.setProperty("port", "2468");
		propertiesSiteCore.setProperty("admin_username", "");
		propertiesSiteCore.setProperty("admin_password", "");
		propertiesSiteCore.setProperty("username", "");
		propertiesSiteCore.setProperty("type", "IIS");
		propertiesSiteCore.setProperty("version", "7.5");
		propertiesSiteCore.setProperty("context", "siteCoreServer");
		
		
		
		Configuration configurationServer = new Configuration("Server", "Server", "Production", "Server", propertiesServer);
		Configuration configurationEmail = new Configuration("Email", "Email", "Production", "Email", propertiesEmail);
		Configuration configurationSiteCorePath = new Configuration("Server", "Test", "Production", "Server", propertiesSiteCore);
		Configuration configurationServerDuplicate = new Configuration("Server2", "Server", "Production", "Server", propertiesServer);
		Configuration configurationEmailDuplicate = new Configuration("Email2", "Email", "Production", "Email", propertiesEmail);
		Configuration configurationServerName = new Configuration("Server", "Server", "Production", "Server", propertiesServer);
		List<Configuration> configListServer = new ArrayList<Configuration>();
		List<Configuration> configListEmail = new ArrayList<Configuration>();
		List<Configuration> configListSiteCore = new ArrayList<Configuration>();
		List<Configuration> configListPass = new ArrayList<Configuration>();
		List<Configuration> configListName = new ArrayList<Configuration>();
		configListServer.add(configurationServer);
		configListServer.add(configurationServerDuplicate);
		
		configListEmail.add(configurationEmail);
		configListEmail.add(configurationEmailDuplicate);
		
		configListSiteCore.add(configurationSiteCorePath);
		
		configListPass.add(configurationServer);
		configListPass.add(configurationEmail);
		
		configListName.add(configurationServerName);
		configListName.add(configurationServer);
		
		ConfigurationService configurationService = new ConfigurationService();
		Response responseServer = configurationService.updateConfiguration("admin", "photon", "sample3-HTML5-JQuery-Mobile-Widget", "Production", configListServer);
		
		System.out.println("Response for responseServer : " + responseServer.getEntity());
		Assert.assertEquals(400, responseServer.getStatus());
		
		Response responseEmail = configurationService.updateConfiguration("admin", "photon", "new1-drupal77.8", "Production", configListEmail);
		
		System.out.println("Response for responseEmail : " + responseEmail.getEntity());
		Assert.assertEquals(400, responseEmail.getStatus());
		
		Response responseSiteCore = configurationService.updateConfiguration("admin", "photon", "siteCore1-sitecore3.5", "Production", configListSiteCore);
		
		System.out.println("Response for responseSiteCore : " + responseSiteCore.getEntity());
		Assert.assertEquals(400, responseSiteCore.getStatus());
		
		Response responseName = configurationService.updateConfiguration("admin", "photon", "sample3-HTML5-JQuery-Mobile-Widget", "Production", configListName);
		
		System.out.println("Response for responseSiteCore : " + responseName.getEntity());
		Assert.assertEquals(400, responseName.getStatus());
		
		Response responsePass = configurationService.updateConfiguration("admin", "photon", "new1-drupal77.8", "Production", configListPass);
		
		System.out.println("Response for responsePass : " + responsePass.getEntity());
		Assert.assertEquals(200, responsePass.getStatus());
		
	}	

}
