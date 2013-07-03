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
//		Response responseFail = configurationService.getAllEnvironments(null);
//		ResponseInfo<List<Environment>> entity = (ResponseInfo<List<Environment>>)responseFail.getEntity();
//		Assert.assertEquals(400, responseFail.getStatus());
	}
	
	@Test
	public void connectionAliveForEnv() throws PhrescoException {
		String connectionUrl = "";
		Response response = configurationService.connectionAliveCheck(connectionUrl);
		Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void addEnvironmentTest() {
		List<Environment> environments = new ArrayList<Environment>();
		List<Configuration> configList = new ArrayList<Configuration>();
		List<Configuration> configList1 = new ArrayList<Configuration>();
		Environment env = new Environment();
		env.setName("Production");
		env.setDefaultEnv(true);
		
		Configuration prodConfigServer = new Configuration();
		Configuration prodConfigDb = new Configuration();
		prodConfigServer.setName("serverconfig");
		prodConfigServer.setType("Server");
		Properties propProd = new Properties();
		propProd.setProperty("protocol", "http");
		propProd.setProperty("host", "localhost");
		propProd.setProperty("port", "8654");
		propProd.setProperty("admin_username", "");
		propProd.setProperty("admin_password", "");
		propProd.setProperty("remoteDeployment", "");
		propProd.setProperty("certificate", "");
		propProd.setProperty("type", "Apache Tomcat");
		propProd.setProperty("version", "6.0.x");
		propProd.setProperty("deploy_dir", "c:\\wamp\\");
		propProd.setProperty("context", "serverprtod");
		prodConfigServer.setProperties(propProd);
		
		prodConfigDb.setName("db");
		prodConfigDb.setType("Database");
		Properties propProddb = new Properties();
		propProddb.setProperty("host", "localhost");
		propProddb.setProperty("port", "3306");
		propProddb.setProperty("username", "root");
		propProddb.setProperty("password", "");
		propProddb.setProperty("dbname", "testjava");
		propProddb.setProperty("type", "MySQL");
		propProddb.setProperty("version", "5.5.1");
		prodConfigDb.setProperties(propProddb);
		
		configList.add(prodConfigServer);
		configList.add(prodConfigDb);
		env.setConfigurations(configList);
		Environment environment = new Environment();
		environment.setName("Testing");
		environment.setDefaultEnv(false);
		
		Configuration testConfigServer = new Configuration();
		Configuration testConfigdb = new Configuration();
		testConfigServer.setName("testconfig");
		testConfigServer.setType("Server");
		Properties testServer = new Properties();
		testServer.setProperty("protocol", "http");
		testServer.setProperty("host", "localhost");
		testServer.setProperty("port", "6589");
		testServer.setProperty("admin_username", "");
		testServer.setProperty("admin_password", "");
		testServer.setProperty("remoteDeployment", "");
		testServer.setProperty("certificate", "");
		testServer.setProperty("type", "Apache Tomcat");
		testServer.setProperty("version", "6.0.x");
		testServer.setProperty("deploy_dir", "c:\\wamp\\");
		testServer.setProperty("context", "serverprtod");
		testConfigServer.setProperties(testServer);
		
		testConfigdb.setName("testdb");
		testConfigdb.setType("Database");
		Properties propTestdb = new Properties();
		propTestdb.setProperty("host", "localhost");
		propTestdb.setProperty("port", "3306");
		propTestdb.setProperty("username", "root");
		propTestdb.setProperty("password", "");
		propTestdb.setProperty("dbname", "testjava1");
		propTestdb.setProperty("type", "MySQL");
		propTestdb.setProperty("version", "5.5.1");
		testConfigdb.setProperties(propTestdb);
		
		configList1.add(testConfigServer);
		configList1.add(testConfigdb);
		environment.setConfigurations(configList1);
		
		Environment devEnv = new Environment();
		devEnv.setName("dev");
		devEnv.setDefaultEnv(false);
		devEnv.setDesc("dev for validation");
		environments.add(env);
		environments.add(devEnv);
		environments.add(environment);
		Response response = configurationService.addEnvironment(appDirName, environments);
		ResponseInfo<List<Environment>> environmentList = (ResponseInfo<List<Environment>>) configurationService.getAllEnvironments(appDirName).getEntity();
		List<Environment> data = environmentList.getData();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(3, data.size());
		Response responseFail = configurationService.addEnvironment("", environments);
		Assert.assertEquals(417, responseFail.getStatus());
	}
	
	@Test
	public void listEnvironmentTest() {
		Response response = configurationService.listEnvironments(appDirName, "Production");
		ResponseInfo<Environment> responseInfo = (ResponseInfo<Environment>) response.getEntity();
		Environment environment = responseInfo.getData();
		Assert.assertEquals("Production", environment.getName());
	}
	
	@Test
	public void listEnvironmentTestFail() {
		Response response = configurationService.listEnvironments(appDirName, "");
		Assert.assertEquals(200, response.getStatus());
		Response responseFail = configurationService.listEnvironments("", "Production");
		Assert.assertEquals(417, responseFail.getStatus());
	}
	
	@Test
	public void deleteEnvTestFail() {
		String envName = "Production";
		Response response = configurationService.deleteEnv(appDirName, envName);
		ResponseInfo<Environment> responseInfo = (ResponseInfo<Environment>) response.getEntity();
		Assert.assertEquals("Default Environment can not be deleted", responseInfo.getMessage());
		Response responseFail = configurationService.deleteEnv(appDirName, "");
		Assert.assertEquals(417, responseFail.getStatus());
		Response envFail = configurationService.deleteEnv("", envName);
		Assert.assertEquals(417, envFail.getStatus());
	}
	
	@Test
	public void deleteEnvTest() {
		String envName = "Testing";
		Response response = configurationService.deleteEnv(appDirName, envName);
		Assert.assertEquals(200, response.getStatus());
	}
	
//	@Test
	public void  getSettingsTemplateTest() {
		Response templateLoginFail = configurationService.getSettingsTemplate(appDirName, techId, "sample", "Server");
		Assert.assertEquals(400, templateLoginFail.getStatus());
		Response responseDb = configurationService.getSettingsTemplate(appDirName, techId, userId, "Database");
		Assert.assertEquals(200, responseDb.getStatus());
		Response responseServer = configurationService.getSettingsTemplate(appDirName, techId, userId, "Server");
		Assert.assertEquals(200, responseServer.getStatus());
		Response settingsTempFail = configurationService.getSettingsTemplate(appDirName, "", userId, "");
		Assert.assertEquals(400, settingsTempFail.getStatus());
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
		Response responseLoginFail = configurationService.getConfigTypes(customerId, "sample", techId);
		Assert.assertEquals(400, responseLoginFail.getStatus());
		Response response = configurationService.getConfigTypes(customerId, userId, techId);
		Assert.assertEquals(200, response.getStatus());
//		Response responseConfigType = configurationService.getConfigTypes("", userId, "");
//		Assert.assertEquals(400, responseConfigType.getStatus());
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
		
		
		
		Configuration configurationServer = new Configuration("Server", "Serverconfig", "dev", "Server", propertiesServer);
		Configuration configurationEmail = new Configuration("Email", "Emailconfig", "dev", "Email", propertiesEmail);
		Configuration configurationSiteCorePath = new Configuration("Server", "Testconfig", "dev", "Server", propertiesSiteCore);
		Configuration configurationServerDuplicate = new Configuration("Server2", "Serverconfig", "dev", "Server", propertiesServer);
		Configuration configurationEmailDuplicate = new Configuration("Email2", "Emailconfig", "dev", "Email", propertiesEmail);
		Configuration configurationServerName = new Configuration("Server", "Serverconfig", "dev", "Server", propertiesServer);
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
		
		Response responseServer = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListServer);
		Assert.assertEquals(400, responseServer.getStatus());
		
		Response responseEmail = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListEmail);
		Assert.assertEquals(400, responseEmail.getStatus());
		
		Response responseSiteCore = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListSiteCore);
		Assert.assertEquals(400, responseSiteCore.getStatus());
		
		Response responseName = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListName);
		Assert.assertEquals(400, responseName.getStatus());
		
		Response responsePass = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListPass);
		Assert.assertEquals(200, responsePass.getStatus());
		
	}
	
	@Test
	public void connectionAliveTest() throws PhrescoException {
		String connectionUrl = getConnectionUrl("Production", "Server", "serverconfig");
		Response response = configurationService.connectionAliveCheck(connectionUrl);
		Assert.assertEquals(false, response.getEntity());
		String connectionUrlFail = "htp" + "," + "localhost" + "," + "7o9o";
		Response responseFail = configurationService.connectionAliveCheck(connectionUrlFail);
		Assert.assertEquals(400, responseFail.getStatus());
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
