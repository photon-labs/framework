package com.photon.phresco.framework.param.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;

public class TechnologyVersionImplTest {

	TechnologyVersionImpl techVersion = new TechnologyVersionImpl();
	ApplicationInfo appInfo;
	public static ServiceManager serviceManager = null;
	String customerId = "photon";
	
	@Before
	public void setUp() throws PhrescoException {
		//String serviceURL = "http://172.16.25.196:3030/service/rest/api";
//		String serviceURL = "http://172.16.25.44:8081/service/rest/api";
		String serviceURL = "http://172.16.17.117:7070/service-3/rest/api";
		String userName = "phresco";
        String password = "Phresco@123";
		ServiceContext context = new ServiceContext();
        context.put("phresco.service.url", serviceURL);
        context.put("phresco.service.username", userName);
        context.put("phresco.service.password", password);	
		serviceManager = ServiceClientFactory.getServiceManager(context);
	}
	
	@After
	public void tearDown() throws Exception {
		if (serviceManager != null) {
			serviceManager = null;
		}
	}
	
	@Test
	public void getValuesTest() throws PhrescoException, IOException, ParserConfigurationException, SAXException, ConfigurationException {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("applicationInfo", getApplicationInfo());
		paramsMap.put("serviceManager", serviceManager);
		PossibleValues values = techVersion.getValues(paramsMap);
		List<Value> value = values.getValue();
		Assert.assertEquals(5, value.size());
	}
	
	private static ApplicationInfo getApplicationInfo() {
		ApplicationInfo info = new ApplicationInfo();
		info.setAppDirName("TestProject");
		info.setCode("TestProject");
		info.setId("TestProject");
		info.setCustomerIds(Collections.singletonList("photon"));
		info.setEmailSupported(false);
		info.setPhoneEnabled(false);
		info.setTabletEnabled(false);
		info.setDescription("Simple java web service Project");
		info.setHelpText("Help");
		info.setName("TestProject");
		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setId("tech-php");
		info.setTechInfo(techInfo);
		info.setPilot(false);
		info.setUsed(false);
		info.setDisplayName("TestProject");
		info.setSelectedJSLibs(Collections.singletonList("99aa3901-a088-4142-8158-000f1e80f1bf"));
		info.setVersion("1.0");
		return info;
	}
	
}
