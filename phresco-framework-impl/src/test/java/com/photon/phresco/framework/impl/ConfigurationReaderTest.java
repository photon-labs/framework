package com.photon.phresco.framework.impl;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigurationReaderTest {
	File configFile = null;
	ConfigurationReader configurationReader = null;
	
	@Before
	public void setUp() throws Exception {
		configFile = new File("src/test/resources/phresco-env-config.xml");
		configurationReader = new ConfigurationReader(configFile);
	}

	@After
	public void tearDown() throws Exception {
		if (configFile != null) {
			configFile = null;
		}
		if (configurationReader != null) {
			configurationReader = null;
		}
	}

	@Test
	public void testGetDocument() {
		Document document = configurationReader.getDocument();
		Assert.assertNotNull(document);
	}

	
	@Test
	public void testGetEnvironment() {
		String envName = "Production";
		Element environment = configurationReader.getEnvironment(envName);
		String expectedEnvName = environment.getAttribute("name");
		Assert.assertEquals(envName, expectedEnvName);
	}
}
