package com.photon.phresco.framework.param.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;

public class WarProjectModuleImplTest {
	WarProjectModuleImpl war = null;

	@Before
	public void setUp() throws Exception {
		war = new WarProjectModuleImpl();;
	}
	
	
	
	@Test
	public void getValues() throws  ConfigurationException, PhrescoException, IOException, ParserConfigurationException, SAXException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applicationInfo", getAppInfo());
		paramMap.put("environmentName", "Production");
		paramMap.put("customerId", "photon");
		PossibleValues possibleValues = war.getValues(paramMap);
		List<Value> values = possibleValues.getValue();
		Assert.assertEquals(0, values.size());
	}

	@After
	public void tearDown() throws Exception {
		if(war != null) {
			war = null;
		}
	}

	private ApplicationInfo getAppInfo() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setId("PHR_Test");
		applicationInfo.setCode("testPhp");
		applicationInfo.setName("testPhp");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("photon");
		applicationInfo.setCustomerIds(customerIds);
		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setId("tech-php");
		techInfo.setName("php-raw");
		techInfo.setVersion("5.4.x");
		techInfo.setTechGroupId("PHP");
		techInfo.setAppTypeId("e1af3f5b-7333-487d-98fa-46305b9dd6ee");
		applicationInfo.setTechInfo(techInfo);
		applicationInfo.setAppDirName("testPhp");
		return applicationInfo;
	}

}
