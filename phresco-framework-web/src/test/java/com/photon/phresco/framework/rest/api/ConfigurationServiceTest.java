/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.CronExpressionInfo;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.impl.ConfigManagerImpl;

public class ConfigurationServiceTest extends LoginServiceTest {
	
	ConfigurationService configurationService = new ConfigurationService();
	
	@Test
	public void getAllEnvironmentsTest() {
		Response response = configurationService.getAllEnvironments(appDirName);
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void addEnvironmentTest() {
		List<Environment> environments = new ArrayList<Environment>();
		Environment env = new Environment();
		env.setName("Production");
		env.setDefaultEnv(true);
		Environment environment = new Environment();
		environment.setName("Testing");
		environment.setDefaultEnv(false);
		environments.add(env);
		environments.add(environment);
		Response response = configurationService.addEnvironment(appDirName, environments);
		ResponseInfo<List<Environment>> environmentList = (ResponseInfo<List<Environment>>) configurationService.getAllEnvironments(appDirName).getEntity();
		List<Environment> data = environmentList.getData();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(2, data.size());
	}
	
	@Test
	public void listEnvironmentTest() {
		Response response = configurationService.listEnvironments(appDirName, "Production");
		ResponseInfo<Environment> responseInfo = (ResponseInfo<Environment>) response.getEntity();
		Environment environment = responseInfo.getData();
		Assert.assertEquals("Production", environment.getName());
	}
	
	@Test
	public void deleteEnvTestFail() {
		String envName = "Production";
		Response response = configurationService.deleteEnv(appDirName, envName);
		ResponseInfo<Environment> responseInfo = (ResponseInfo<Environment>) response.getEntity();
		Assert.assertEquals("Default Environment can not be deleted", responseInfo.getMessage());
	}
	
	@Test
	public void deleteEnvTest() {
		String envName = "Testing";
		Response response = configurationService.deleteEnv(appDirName, envName);
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void  getSettingsTemplateTest() {
		String type = "Server";
		Response response = configurationService.getSettingsTemplate(appDirName, techId, userId, type);
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testCronExpressionSuccess() throws PhrescoException {
		CronExpressionInfo cron = new CronExpressionInfo();
		cron.setCronBy("Weekly");
		cron.setEvery("false");
		cron.setHours("5");
		cron.setMinutes("23");
		cron.setWeek(Arrays.asList("3"));
		cron.setMonth(Arrays.asList("9"));
		cron.setDay("8");
		
		Response cronValidation = configurationService.cronValidation(cron);
		ResponseInfo<CronExpressionInfo> entity = (ResponseInfo<CronExpressionInfo>) cronValidation.getEntity();
		assertEquals("23 5 * * 3" , entity.getData().getCronExpression());
	}
	
	@Test
	public void testCronExpressionFailure() throws PhrescoException {
		CronExpressionInfo cron = new CronExpressionInfo();
		cron.setCronBy("Weekly");
		cron.setEvery("false");
		cron.setHours("5");
		cron.setMinutes("24");
		cron.setWeek(Arrays.asList("3"));
		cron.setMonth(Arrays.asList("9"));
		cron.setDay("8");
		
		Response cronValidation = configurationService.cronValidation(cron);
		ResponseInfo<CronExpressionInfo> entity = (ResponseInfo<CronExpressionInfo>) cronValidation.getEntity();
		Assert.assertNotSame("CronExpression  not Matching", "23 5 * * 3" , entity.getData().getCronExpression());
	}
	
	@Test
	public void testDate() throws PhrescoException {
		CronExpressionInfo cron = new CronExpressionInfo();
		cron.setCronBy("Monthly");
		cron.setEvery("false");
		cron.setHours("2");
		cron.setMinutes("12");
		cron.setDay("5");
		cron.setMonth(Arrays.asList("2,3"));
		cron.setDay("8");
		
		Response cronValidation = configurationService.cronValidation(cron);
		ResponseInfo<CronExpressionInfo> entity = (ResponseInfo<CronExpressionInfo>) cronValidation.getEntity();
		Assert.assertEquals(4, entity.getData().getDates().size());
	}
	
	@Test
	public void listEnvironmentsByProjectId() {
		Response response = configurationService.listEnvironmentsByProjectId(customerId, projectId);
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void getConfigTypeTest() {
		Response response = configurationService.getConfigTypes(customerId, userId, techId);
		ResponseInfo<List<String>> responseInfo = (ResponseInfo<List<String>>) response.getEntity();
		List<String> data = responseInfo.getData();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotSame(null, data);
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
		configListSiteCore.add(configurationServer);
		
		configListPass.add(configurationServer);
		configListPass.add(configurationEmail);
		
		configListName.add(configurationServerName);
		configListName.add(configurationServer);
		
		Response responseServer = configurationService.updateConfiguration(userId, customerId, appDirName, "Production", configListServer);
		
		System.out.println("Response for responseServer : " + responseServer.getEntity());
		Assert.assertEquals(400, responseServer.getStatus());
		
		Response responseEmail = configurationService.updateConfiguration(userId, customerId, appDirName, "Production", configListEmail);
		
		System.out.println("Response for responseEmail : " + responseEmail.getEntity());
		Assert.assertEquals(400, responseEmail.getStatus());
		
		Response responseSiteCore = configurationService.updateConfiguration(userId, customerId, appDirName, "Production", configListSiteCore);
		
		System.out.println("Response for responseSiteCore : " + responseSiteCore.getEntity());
		Assert.assertEquals(400, responseSiteCore.getStatus());
		
		Response responseName = configurationService.updateConfiguration(userId, customerId, appDirName, "Production", configListName);
		
		System.out.println("Response for responseSiteCore : " + responseName.getEntity());
		Assert.assertEquals(400, responseName.getStatus());
		
		Response responsePass = configurationService.updateConfiguration(userId, customerId, appDirName, "Production", configListPass);
		
		System.out.println("Response for responsePass : " + responsePass.getEntity());
		Assert.assertEquals(200, responsePass.getStatus());
		
	}
	
	@Test
	public void connectionAliveTest() throws PhrescoException {
		String connectionUrl = getConnectionUrl("Production", "Server", "Server");
		Response response = configurationService.connectionAliveCheck(connectionUrl);
		Assert.assertEquals(false, response.getEntity());
	}
	
	@Test
	public void cloneEnvironmentTest() {
		Environment cloneEnvironment = new Environment();
		cloneEnvironment.setName("clone");
		cloneEnvironment.setDefaultEnv(false);
		Response response = configurationService.cloneEnvironment(appDirName, "Production", cloneEnvironment);
		Assert.assertEquals(200, response.getStatus());
	}
	
	private String getConnectionUrl(String envName, String type, String configName) throws PhrescoException {
		String configFileDir = FrameworkServiceUtil.getConfigFileDir(appDirName);
		try {
			ConfigManager configManager = new ConfigManagerImpl(new File(configFileDir));
			Configuration configuration = configManager.getConfiguration(envName, type, configName);
			Properties properties = configuration.getProperties();
			String protocol = properties.getProperty("protocol");
			String host = properties.getProperty("host");
			String port = properties.getProperty("port");
			return protocol + "," +host + "," + port;
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		}
	}
}
