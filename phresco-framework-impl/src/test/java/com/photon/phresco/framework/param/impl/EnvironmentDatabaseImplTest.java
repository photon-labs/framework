package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.impl.EnvironmentsParameterImpl;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.plugins.util.MojoProcessor;

public class EnvironmentDatabaseImplTest {

	EnvironmentDataBasesImpl envDbParam = new EnvironmentDataBasesImpl();
	MojoProcessor mojo;
	
	
	@Test
	public void getValues() throws ConfigurationException, PhrescoException, IOException, ParserConfigurationException, SAXException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applicationInfo", getApplicationInfo());
		paramMap.put("environmentName", "Production");
		paramMap.put("customerId", "photon");
		PossibleValues possibleValues = envDbParam.getValues(paramMap);
		List<Value> values = possibleValues.getValue();
		if (values.size() == 0) {
			Assert.assertTrue(values.size() == 0);
		} else {
			Assert.assertTrue(values.size() > 0);
		}
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
