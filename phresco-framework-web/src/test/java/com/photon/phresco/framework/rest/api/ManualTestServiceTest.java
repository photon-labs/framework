package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.photon.phresco.commons.model.TestCase;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.TestSuite;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ManualTestServiceTest extends RestBaseTest {
	
	private final String testSuiteName = "manualtesting";
	
	@Test
	public void testGetManualTemplate() throws PhrescoException {
		ManualTestService service = new ManualTestService();
		Response manualTemplate = service.getManualTemplate("xls");
		InputStream entity = (InputStream) manualTemplate.getEntity();
		Assert.assertNotNull(entity);
		try {
			Assert.assertEquals(entity.read(), 208);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUploadManualTemplate() throws PhrescoException {
		try {
			PomProcessor pomProcessor = FrameworkUtil.getInstance().getPomProcessor(appDirName);
	    	String manualTestReportPath = pomProcessor.getProperty("phresco.manualTest.testcase.path");
	    	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(appDirName);
			builder.append(File.separator);
			builder.append(manualTestReportPath);
			InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("helios_manul_test_template.xls");
			File file = new File(builder.toString() + "/helios_manul_test_template.xls");
			FileUtils.copyInputStreamToFile(resourceStream, file);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	@Test
	public void testGetTestSuites() throws PhrescoException {
		ManualTestService service = new ManualTestService();
		Response testSuites = service.getTestSuites(appDirName);
		ResponseInfo<List<TestSuite>> response = (ResponseInfo<List<TestSuite>>) testSuites.getEntity();
		
		Assert.assertEquals(200, testSuites.getStatus());
		Assert.assertEquals("Testsuites returned Successfully", response.getMessage());
	}

	@Test
	public void testGetTestCase() throws PhrescoException {
		ManualTestService service = new ManualTestService();
		Response testCase = service.getTestCase(appDirName, "Login");
		ResponseInfo<List<com.photon.phresco.commons.model.TestCase>> response = (ResponseInfo<List<TestCase>>) testCase.getEntity();
		List<TestCase> data = response.getData();
		
		Assert.assertEquals(200, testCase.getStatus());
		Assert.assertEquals("Testcases returned Successfully", response.getMessage());
		Assert.assertNotNull(data);
	}

	@Test
	public void testCreateTestSuite() throws PhrescoException {
		ManualTestService service = new ManualTestService();
		Response response = service.createTestSuite(testSuiteName, appDirName);
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("TestSuite Added Successfully", (String) response.getEntity());
	}

	@Test
	public void testCreateTestCase() throws PhrescoException {
		ManualTestService service = new ManualTestService();
		Response response = service.createTestCase(createTestCase() , testSuiteName, appDirName);
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("TestCase Added Successfully", (String) response.getEntity());
	}

	@Test
	public void testUpdateTestCase() throws PhrescoException {
		ManualTestService service = new ManualTestService();
		TestCase testCase = createTestCase();
		testCase.setDescription("Manual Testing Description Updated");
		testCase.setExpectedResult("Manual Test Expected Result Updated");
		testCase.setFeatureId("Manual Test Feature ID Updated");
		Response response = service.updateTestCase(testCase, testSuiteName, appDirName);
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("TestCase Updated Successfully", (String) response.getEntity());
	}
	
	private TestCase createTestCase() {
		TestCase testCase = new TestCase();
		testCase.setBugComment("Manual Testing Comment");
		testCase.setDescription("Manual Testing Description");
		testCase.setExpectedResult("Manual Test Expected Result");
		testCase.setFeatureId("Manual Test Feature ID");
		testCase.setRequirementId("Manual Test Req Id");
		testCase.setStatus("Success");
		testCase.setSteps("Manual Test Steps");
		return testCase;
	}

}
