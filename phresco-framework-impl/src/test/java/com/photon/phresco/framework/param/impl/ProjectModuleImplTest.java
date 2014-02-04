package com.photon.phresco.framework.param.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;

public class ProjectModuleImplTest {

	ProjectModuleImpl projectModule = new ProjectModuleImpl();
	
	@Test
	public void getValues() throws ConfigurationException, PhrescoException, IOException, ParserConfigurationException, SAXException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applicationInfo", getApplicationInfo());
		PossibleValues possibleValues = projectModule.getValues(paramMap);
		Assert.assertEquals(null, possibleValues);
	}
	
	
	private static ApplicationInfo getApplicationInfo() {
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
		applicationInfo.setPomFile("pom.xml");
		return applicationInfo;
	}
}
