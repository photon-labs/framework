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
import javax.xml.transform.dom.DOMSource;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.AddCertificateInfo;
import com.photon.phresco.framework.model.CronExpressionInfo;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.util.Utility;

public class ConfigurationServiceTest extends LoginServiceTest {
	
	ConfigurationService configurationService = new ConfigurationService();
	
	@Test
	public void getAllEnvironmentsTest() {
		Response response = configurationService.getAllEnvironments(appDirName,"true","");
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
		Configuration prodWebService = new Configuration();
		prodConfigServer.setName("serverconfig");
		prodConfigServer.setType("Server");
		Properties propProd = new Properties();
		propProd.setProperty("protocol", "http");
		propProd.setProperty("host", "localhost");
		propProd.setProperty("port", "8654");
		propProd.setProperty("admin_username", "");
		propProd.setProperty("admin_password", "");
		propProd.setProperty("remoteDeployment", "false");
		propProd.setProperty("certificate", "");
		propProd.setProperty("type", "Apache Tomcat");
		propProd.setProperty("version", "6.0.x");
		propProd.setProperty("deploy_dir", "C:\\wamp");
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
		
		prodWebService.setName("ws");
		prodWebService.setType("WebService");
		Properties propProdWs= new Properties();
		propProdWs.setProperty("host", "localhost");
		propProdWs.setProperty("port", "3306");
		propProdWs.setProperty("username", "root");
		propProdWs.setProperty("password", "");
		propProdWs.setProperty("context", "webservice");
		propProdWs.setProperty("protocol", "http");
		prodWebService.setProperties(propProdWs);
		
		configList.add(prodConfigServer);
		configList.add(prodConfigDb);
		configList.add(prodWebService);
		
		env.setConfigurations(configList);
		Environment environment = new Environment();
		environment.setName("Testing");
		environment.setDefaultEnv(false);
		
		Configuration testConfigServer = new Configuration();
		Configuration testConfigdb = new Configuration();
		Configuration testWebService = new Configuration();
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
		testServer.setProperty("deploy_dir", "c:\\wamp\\www");
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
		
		
		testWebService.setName("ws");
		testWebService.setType("WebService");
		Properties testProdWs= new Properties();
		testProdWs.setProperty("host", "localhost");
		testProdWs.setProperty("port", "3306");
		testProdWs.setProperty("username", "root");
		testProdWs.setProperty("password", "");
		testProdWs.setProperty("context", "webservice");
		testProdWs.setProperty("protocol", "http");
		testWebService.setProperties(testProdWs);
		configList1.add(testConfigServer);
		configList1.add(testConfigdb);
		configList1.add(testWebService);
		environment.setConfigurations(configList1);
		
		Environment devEnv = new Environment();
		devEnv.setName("dev");
		devEnv.setDefaultEnv(false);
		devEnv.setDesc("dev for validation");
		environments.add(env);
		environments.add(devEnv);
		environments.add(environment);
		Response response = configurationService.addEnvironment(appDirName, environments);
		ResponseInfo<List<Environment>> environmentList = (ResponseInfo<List<Environment>>) configurationService.getAllEnvironments(appDirName,"true", "").getEntity();
		List<Environment> data = environmentList.getData();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(3, data.size());
		Response responseFail = configurationService.addEnvironment("", environments);
		Assert.assertEquals(417, responseFail.getStatus());
	}
	
	@Test
	public void addNonEnvConfigTest() {
		List<Configuration> configList = new ArrayList<Configuration>();
		Configuration prodConfigServer = new Configuration();
		prodConfigServer.setName("serverconfig");
		prodConfigServer.setType("Server");
		Properties propProd = new Properties();
		propProd.setProperty("protocol", "http");
		propProd.setProperty("host", "localhost");
		propProd.setProperty("port", "8654");
		propProd.setProperty("admin_username", "");
		propProd.setProperty("admin_password", "");
		propProd.setProperty("remoteDeployment", "false");
		propProd.setProperty("certificate", "");
		propProd.setProperty("type", "Apache Tomcat");
		propProd.setProperty("version", "6.0.x");
		propProd.setProperty("deploy_dir", "C:\\wamp");
		propProd.setProperty("context", "serverprtod");
		prodConfigServer.setProperties(propProd);
		configList.add(prodConfigServer);
		Response response = configurationService.updateConfiguration(userId, customerId, appDirName, "", configList, "false", "");
		Assert.assertEquals(200, response.getStatus());
		
		
		List<Configuration> configList1 = new ArrayList<Configuration>();
		Configuration prodConfigServer1 = new Configuration();
		prodConfigServer1.setName("serverconfig");
		prodConfigServer1.setType("Server");
		Properties propPro1 = new Properties();
		propPro1.setProperty("protocol", "http");
		propPro1.setProperty("host", "localhost");
		propPro1.setProperty("port", "8654");
		propPro1.setProperty("admin_username", "");
		propPro1.setProperty("admin_password", "");
		propPro1.setProperty("remoteDeployment", "false");
		propPro1.setProperty("certificate", "");
		propPro1.setProperty("type", "Apache Tomcat");
		propPro1.setProperty("version", "6.0.x");
		propPro1.setProperty("deploy_dir", "C:\\wamp");
		propPro1.setProperty("context", "serverprtod");
		prodConfigServer1.setProperties(propPro1);
		configList1.add(prodConfigServer1);
		
		Response responseUp = configurationService.updateConfiguration(userId, customerId, appDirName, "", configList1, "false", "serverconfig");
		Assert.assertEquals(200, responseUp.getStatus());
		
		List<Configuration> configList2 = new ArrayList<Configuration>();
		Configuration prodConfigServer2 = new Configuration();
		prodConfigServer2.setName("Configs");
		prodConfigServer2.setType("Server");
		Properties propProd2 = new Properties();
		propProd2.setProperty("protocol", "http");
		propProd2.setProperty("host", "localhost");
		propProd2.setProperty("port", "5555");
		propProd2.setProperty("admin_username", "rrr");
		propProd2.setProperty("admin_password", "sss");
		propProd2.setProperty("remoteDeployment", "false");
		propProd2.setProperty("certificate", "");
		propProd2.setProperty("type", "Apache Tomcat");
		propProd2.setProperty("version", "7.0.x");
		propProd2.setProperty("deploy_dir", "C:\\wamp");
		propProd2.setProperty("context", "serverprtod");
		prodConfigServer2.setProperties(propProd2);
		configList2.add(prodConfigServer2);
		Response responseNonEnv = configurationService.updateConfiguration(userId, customerId, appDirName, "", configList2, "false", "");
		Assert.assertEquals(200, responseNonEnv.getStatus());
		
		
	}
	
	@Test
	public void listEnvironmentTest() {
		Response response = configurationService.listEnvironments(appDirName, "Production", "","");
		ResponseInfo<Environment> responseInfo = (ResponseInfo<Environment>) response.getEntity();
		Environment environment = responseInfo.getData();
		Assert.assertEquals("Production", environment.getName());
		
		Response responseNon = configurationService.listEnvironments(appDirName, "", "false","serverconfig");
		Assert.assertEquals(200, responseNon.getStatus());
		
		Response responseNonEnvList = configurationService.getAllEnvironments(appDirName, "false", "Server");
		Assert.assertEquals(200, responseNonEnvList.getStatus());
		
	}
	
	@Test
	public void listEnvironmentTestFail() {
		Response response = configurationService.listEnvironments(appDirName, "","","");
		Assert.assertEquals(200, response.getStatus());
		Response responseFail = configurationService.listEnvironments("", "Production","","");
		Assert.assertEquals(417, responseFail.getStatus());
		
	}
	
	@Test
	public void deleteEnvTestFail() {
		String envName = "Production";
		Response response = configurationService.deleteEnv(appDirName, envName);
		ResponseInfo<Environment> responseInfo = (ResponseInfo<Environment>) response.getEntity();
		Assert.assertEquals("Default Environment can not be deleted", responseInfo.getMessage());
//		Response responseFail = configurationService.deleteEnv(appDirName, "");
//		Assert.assertEquals(417, responseFail.getStatus());
	}
	
	@Test
	public void deleteEnvTest() {
		String envName = "Testing";
		Response response = configurationService.deleteEnv(appDirName, envName);
		Assert.assertEquals(200, response.getStatus());
		
		Response responsedel = configurationService.deleteConfiguraion(appDirName, "Configs");
		Assert.assertEquals(200, responsedel.getStatus());
		
		
	}
	
	@Test
	public void  getSettingsTemplateTest() {
		Response templateLoginFail = configurationService.getSettingsTemplate(appDirName, techId, "sample", "Server");
		Assert.assertEquals(400, templateLoginFail.getStatus());
		Response responseDb = configurationService.getSettingsTemplate(appDirName, techId, userId, "Database");
		Assert.assertEquals(200, responseDb.getStatus());
		Response responseServer = configurationService.getSettingsTemplate(appDirName, techId, userId, "Email");
		Assert.assertEquals(200, responseServer.getStatus());
//		Response settingsTempFail = configurationService.getSettingsTemplate(appDirName, "", userId, "");
//		Assert.assertEquals(400, settingsTempFail.getStatus());
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
	public void testCronExpressionSuccessOnDaily() throws PhrescoException {
		CronExpressionInfo cron = new CronExpressionInfo();
		cron.setCronBy("Daily");
		cron.setEvery("false");
		cron.setHours("5");
		cron.setMinutes("23");
		cron.setDay("8");
		Response cronValidation = configurationService.cronValidation(cron);
		assertEquals(200 , cronValidation.getStatus());
		
		CronExpressionInfo cron1 = new CronExpressionInfo();
		cron1.setCronBy("Daily");
		cron1.setEvery("true");
		cron1.setHours("5");
		cron1.setMinutes("23");
		cron1.setDay("8");
		Response cronValdaily = configurationService.cronValidation(cron1);
		assertEquals(200 , cronValdaily.getStatus());
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
		
		Response responseServer = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListServer,"","");
		Assert.assertEquals(200, responseServer.getStatus());
		
		Response responseEmail = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListEmail,"","");
		Assert.assertEquals(200, responseEmail.getStatus());
		
		Response responseSiteCore = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListSiteCore,"","");
		Assert.assertEquals(200, responseSiteCore.getStatus());
		
		Response responseName = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListName,"","");
		Assert.assertEquals(200, responseName.getStatus());
		
		Response responsePass = configurationService.updateConfiguration(userId, customerId, appDirName, "dev", configListPass,"","");
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
	
//	@Test
	public void returnCertificate() throws PhrescoException {
		Response authenticateServer = configurationService.authenticateServer("kumar_s", "8443", "TestProject");
		Assert.assertEquals(200, authenticateServer.getStatus());
	}
	
	@Test
	public void addCertificate() throws PhrescoException {
		AddCertificateInfo add = new AddCertificateInfo();
		add.setAppDirName("TestProject");
		add.setHost("kumar_s");
		add.setPort("8443");
		add.setPropValue("CN=kumar_s");
		add.setFromPage("configuration");
		add.setCertificateName("CN=kumar_s");
		add.setEnvironmentName("Production");
		add.setConfigName("Server");
		add.setCustomerId("photon");
//		Response authenticateServer = configurationService.addCertificate(add);
//		Assert.assertEquals(200, authenticateServer.getStatus());
		
		AddCertificateInfo add1 = new AddCertificateInfo();
		add1.setAppDirName("TestProject");
		add1.setHost("kumar_s");
		add1.setPort("8443");
		add1.setPropValue("CN=kumar_s");
		add1.setFromPage("settings");
		add1.setCertificateName("CN=kumar_s");
		add1.setEnvironmentName("Production");
		add1.setConfigName("Server");
		add1.setCustomerId("photon");
//		Response authenticate = configurationService.addCertificate(add1);
//		Assert.assertEquals(200, authenticate.getStatus());
		
		AddCertificateInfo add4 = new AddCertificateInfo();
		add4.setHost("kumar_s");
		add4.setPort("6356");
		add4.setFromPage("configuraiton");
		add4.setPropValue("");
		Response addFailsonValue = configurationService.addCertificate(add4);
		Assert.assertEquals(200, addFailsonValue.getStatus());
		 
		File certFile = FileUtils.toFile(this.getClass().getResource("/Production-Server.crt"));
		AddCertificateInfo add5 = new AddCertificateInfo();
		add5.setAppDirName("TestProject");
		add5.setHost("localhost");
		add5.setPort("8080");
		add5.setPropValue(certFile.getPath());
		add5.setFromPage("configuration");
		add5.setCertificateName(certFile.getPath());
		add5.setEnvironmentName("Production");
		add5.setConfigName("Server");
		add5.setCustomerId("photon");
		Response authenticateServerPath = configurationService.addCertificate(add5);
		Assert.assertEquals(200, authenticateServerPath.getStatus());
		
		AddCertificateInfo add6 = new AddCertificateInfo();
		add6.setAppDirName("TestProject");
		add6.setHost("localhost");
		add6.setPort("8080");
		add6.setPropValue(certFile.getPath());
		add6.setFromPage("settings");
		add6.setCertificateName(certFile.getPath());
		add6.setEnvironmentName("Production");
		add6.setConfigName("Server");
		add6.setCustomerId("photon");
		Response authenticateServersettings = configurationService.addCertificate(add6);
		Assert.assertEquals(200, authenticateServersettings.getStatus());
		
	}
	
	@Test
	public void fileBrowseTest() {
		Response fileStructure = configurationService.returnFileBorwseFolderStructure(Utility.getProjectHome()
				+ appDirName);
		Assert.assertEquals(200, fileStructure.getStatus());
		Response fileEntireStructure = configurationService.returnFileBorwseEntireStructure(appDirName);
		Assert.assertEquals(200, fileEntireStructure.getStatus());
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
