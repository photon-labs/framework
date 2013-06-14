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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.photon.phresco.commons.FileListFilter;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.QualityUtil;
import com.photon.phresco.framework.model.TestCase;
import com.photon.phresco.framework.model.TestCaseError;
import com.photon.phresco.framework.model.TestCaseFailure;
import com.photon.phresco.framework.model.TestReportResult;
import com.photon.phresco.framework.model.TestSuite;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.HubConfiguration;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class QualityService.
 */
@Path("/quality")
public class QualityService extends RestBase implements ServiceConstants, FrameworkConstants {

	/** The test suite map. */
	private static Map<String, Map<String, NodeList>> testSuiteMap = Collections
			.synchronizedMap(new HashMap<String, Map<String, NodeList>>(8));
	
	/** The set failure test cases. */
	private int setFailureTestCases;
	
	/** The error test cases. */
	private int errorTestCases;
	
	/** The node length. */
	private int nodeLength;
	
	/** The test suite. */
	private String testSuite = "";

	/**
	 * Unit.
	 *
	 * @param appDirName the app dir name
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/unit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unit(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<Map> responseData = new ResponseInfo<Map>();
		try {
			List<String> unitReportOptions = getUnitReportOptions(appDirName);
			List<String> projectModules = FrameworkServiceUtil.getProjectModules(appDirName);
			Map<String, List<String>> unitTestOptionsMap = new HashMap<String, List<String>>();
			unitTestOptionsMap.put("reportOptions", unitReportOptions);
			unitTestOptionsMap.put("projectModules", projectModules);
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
					"Parameter returned successfully", unitTestOptionsMap);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e,
					"Unable to get unit test report options", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the test suites.
	 *
	 * @param appDirName the app dir name
	 * @param testType the test type
	 * @param techReport the tech report
	 * @param moduleName the module name
	 * @return the test suites
	 * @throws PhrescoException the phresco exception
	 */
	@GET
	@Path("/testsuites")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestSuites(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_TEST_TYPE) String testType, @QueryParam(REST_QUERY_TECH_REPORT) String techReport,
			@QueryParam(REST_QUERY_MODULE_NAME) String moduleName) throws PhrescoException {
		ResponseInfo<List<TestSuite>> responseData = new ResponseInfo<List<TestSuite>>();
		// TO kill the Process
		String baseDir = Utility.getProjectHome() + appDirName;
		Utility.killProcess(baseDir, testType);

		String testSuitePath = getTestSuitePath(appDirName, testType, techReport);
		String testCasePath = getTestCasePath(appDirName, testType, techReport);
		List<TestSuite> testSuites = testSuites(appDirName, moduleName, testType, moduleName, techReport,
				testSuitePath, testCasePath, "All");
		if (CollectionUtils.isEmpty(testSuites)) {
			ResponseInfo<Configuration> finalOuptut = responseDataEvaluation(responseData, null,
					"Test Result not available", testSuites);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
		ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
				"Test Suites listed successfully", testSuites);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Test suites.
	 *
	 * @param appDirName the app dir name
	 * @param moduleName the module name
	 * @param testType the test type
	 * @param module the module
	 * @param techReport the tech report
	 * @param testSuitePath the test suite path
	 * @param testCasePath the test case path
	 * @param testSuite the test suite
	 * @return the list
	 * @throws PhrescoException the phresco exception
	 */
	private List<TestSuite> testSuites(String appDirName, String moduleName, String testType, String module,
			String techReport, String testSuitePath, String testCasePath, String testSuite) throws PhrescoException {
		setTestSuite(testSuite);
		List<TestSuite> suites = new ArrayList<TestSuite>();
		try {
			String testSuitesMapKey = appDirName + testType + module + techReport;
			String testResultPath = getTestResultPath(appDirName, moduleName, testType, techReport);
			File[] testResultFiles = getTestResultFiles(testResultPath);
			if (ArrayUtils.isEmpty(testResultFiles)) {
				return null;
			}
			getTestSuiteNames(appDirName, testType, moduleName, techReport, testResultPath, testSuitePath);
			Map<String, NodeList> testResultNameMap = testSuiteMap.get(testSuitesMapKey);
			if (MapUtils.isEmpty(testResultNameMap)) {
				return null;
			}
			// NodeList testSuites = testResultNameMap.get(testSuite);
			Map<String, String> testSuitesResultMap = new HashMap<String, String>();
			float totalTestSuites = 0;
			float successTestSuites = 0;
			float failureTestSuites = 0;
			float errorTestSuites = 0;
			// get all nodelist of testType of a project
			Collection<NodeList> allTestResultNodeLists = testResultNameMap.values();
			for (NodeList allTestResultNodeList : allTestResultNodeLists) {
				if (allTestResultNodeList.getLength() > 0) {
					List<TestSuite> allTestSuites = getTestSuite(allTestResultNodeList);
					if (CollectionUtils.isNotEmpty(allTestSuites)) {
						for (TestSuite tstSuite : allTestSuites) {
							// testsuite values are set before calling
							// getTestCases value
							setTestSuite(tstSuite.getName());
							getTestCases(appDirName, allTestResultNodeList, testSuitePath, testCasePath);
							float tests = 0;
							float failures = 0;
							float errors = 0;
							tests = Float.parseFloat(String.valueOf(getNodeLength()));
							failures = Float.parseFloat(String.valueOf(getSetFailureTestCases()));
							errors = Float.parseFloat(String.valueOf(getErrorTestCases()));
							float success = 0;

							if (failures != 0 && errors == 0) {
								if (failures > tests) {
									success = failures - tests;
								} else {
									success = tests - failures;
								}
							} else if (failures == 0 && errors != 0) {
								if (errors > tests) {
									success = errors - tests;
								} else {
									success = tests - errors;
								}
							} else if (failures != 0 && errors != 0) {
								float failTotal = (failures + errors);
								if (failTotal > tests) {
									success = failTotal - tests;
								} else {
									success = tests - failTotal;
								}
							} else {
								success = tests;
							}

							totalTestSuites = totalTestSuites + tests;
							failureTestSuites = failureTestSuites + failures;
							errorTestSuites = errorTestSuites + errors;
							successTestSuites = successTestSuites + success;
							String rstValues = tests + "," + success + "," + failures + "," + errors;
							testSuitesResultMap.put(tstSuite.getName(), rstValues);

							TestSuite suite = new TestSuite();
							suite.setName(tstSuite.getName());
							suite.setSuccess(success);
							suite.setErrors(errors);
							suite.setTime(tstSuite.getTime());
							suite.setTotal(totalTestSuites);
							suite.setTestCases(tstSuite.getTestCases());
							suites.add(suite);
						}
					}
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return suites;
	}

	/**
	 * Gets the test reports.
	 *
	 * @param appDirName the app dir name
	 * @param testType the test type
	 * @param techReport the tech report
	 * @param moduleName the module name
	 * @param testSuite the test suite
	 * @return the test reports
	 * @throws PhrescoException the phresco exception
	 */
	@GET
	@Path("/testreports")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestReports(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_TEST_TYPE) String testType, @QueryParam(REST_QUERY_TECH_REPORT) String techReport,
			@QueryParam(REST_QUERY_MODULE_NAME) String moduleName, @QueryParam(REST_QUERY_TEST_SUITE) String testSuite)
			throws PhrescoException {
		String testSuitePath = "";
		String testCasePath = "";
		try {
			testSuitePath = getTestSuitePath(appDirName, testType, techReport);
			testCasePath = getTestCasePath(appDirName, testType, techReport);
			return testReport(appDirName, moduleName, testType, moduleName, techReport, testSuitePath, testCasePath,
					testSuite);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the functional test framework.
	 *
	 * @param appDirName the app dir name
	 * @return the functional test framework
	 */
	@GET
	@Path("/functionalFramework")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFunctionalTestFramework(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		ResponseInfo<Map<String, Object>> responseData = new ResponseInfo<Map<String, Object>>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String functionalTestFramework = FrameworkServiceUtil.getFunctionalTestFramework(appDirName);
			map.put("functionalFramework", functionalTestFramework);
			if (SELENIUM_GRID.equalsIgnoreCase(functionalTestFramework)) {
				HubConfiguration hubConfig = getHubConfiguration(appDirName);
				if (hubConfig != null) {
					String host = hubConfig.getHost();
					int port = hubConfig.getPort();
					boolean isConnectionAlive = Utility.isConnectionAlive(HTTP_PROTOCOL, host, port);
					map.put("hubStatus", isConnectionAlive);
				}
			}
			ResponseInfo<Map<String, Object>> finalOutput = responseDataEvaluation(responseData, null,
					"Functional test framework fetched Successfully", map);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<Map<String, Object>> finalOutput = responseDataEvaluation(responseData, e,
					"Failed to get the functional test framework", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the hub configuration.
	 *
	 * @param appDirName the app dir name
	 * @return the hub configuration
	 * @throws PhrescoException the phresco exception
	 */
	private HubConfiguration getHubConfiguration(String appDirName) throws PhrescoException {
		BufferedReader reader = null;
		HubConfiguration hubConfig = null;
		try {
			String functionalTestDir = FrameworkServiceUtil.getFunctionalTestDir(appDirName);
			StringBuilder sb = new StringBuilder(FrameworkServiceUtil.getApplicationHome(appDirName));
			sb.append(functionalTestDir).append(File.separator).append(Constants.HUB_CONFIG_JSON);
			File hubConfigFile = new File(sb.toString());
			Gson gson = new Gson();
			reader = new BufferedReader(new FileReader(hubConfigFile));
			hubConfig = gson.fromJson(reader, HubConfiguration.class);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeReader(reader);
		}

		return hubConfig;
	}

	/**
	 * Gets the test case path.
	 *
	 * @param appDirName the app dir name
	 * @param testType the test type
	 * @param techReport the tech report
	 * @return the test case path
	 * @throws PhrescoException the phresco exception
	 */
	private String getTestCasePath(String appDirName, String testType, String techReport) throws PhrescoException {
		String testCasePath = "";
		if (testType.equals("unit")) {
			if (StringUtils.isNotEmpty(techReport)) {
				testCasePath = getUnitTestCasePath(appDirName, techReport);
			} else {
				testCasePath = getUnitTestCasePath(appDirName);
			}
		} else if (testType.equals("functional")) {
			testCasePath = getFunctionalTestCasePath(appDirName);
		} else if (testType.equals("component")) {
			testCasePath = getComponentTestCasePath(appDirName);
		}
		return testCasePath;
	}

	/**
	 * Test report.
	 *
	 * @param appDirName the app dir name
	 * @param moduleName the module name
	 * @param testType the test type
	 * @param module the module
	 * @param techReport the tech report
	 * @param testSuitePath the test suite path
	 * @param testCasePath the test case path
	 * @param testSuite the test suite
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	private Response testReport(String appDirName, String moduleName, String testType, String module,
			String techReport, String testSuitePath, String testCasePath, String testSuite) throws PhrescoException {
		setTestSuite(testSuite);
		ResponseInfo<TestReportResult> responseDataAll = new ResponseInfo<TestReportResult>();
		ResponseInfo<List<TestCase>> responseData = new ResponseInfo<List<TestCase>>();
		try {
			String testSuitesMapKey = appDirName + testType + module + techReport;
			if (MapUtils.isEmpty(testSuiteMap)) {
				String testResultPath = getTestResultPath(appDirName, moduleName, testType, techReport);
				getTestSuiteNames(appDirName, testType, moduleName, techReport, testResultPath, testSuitePath);
			}
			Map<String, NodeList> testResultNameMap = testSuiteMap.get(testSuitesMapKey);
			NodeList testSuites = testResultNameMap.get(testSuite);
			if (ALL.equals(testSuite)) {
				Map<String, String> testSuitesResultMap = new HashMap<String, String>();
				float totalTestSuites = 0;
				float successTestSuites = 0;
				float failureTestSuites = 0;
				float errorTestSuites = 0;
				// get all nodelist of testType of a project
				Collection<NodeList> allTestResultNodeLists = testResultNameMap.values();
				for (NodeList allTestResultNodeList : allTestResultNodeLists) {
					if (allTestResultNodeList.getLength() > 0) {
						List<TestSuite> allTestSuites = getTestSuite(allTestResultNodeList);
						if (CollectionUtils.isNotEmpty(allTestSuites)) {
							for (TestSuite tstSuite : allTestSuites) {
								// testsuite values are set before calling
								// getTestCases value
								setTestSuite(tstSuite.getName());
								getTestCases(appDirName, allTestResultNodeList, testSuitePath, testCasePath);
								float tests = 0;
								float failures = 0;
								float errors = 0;
								tests = Float.parseFloat(String.valueOf(getNodeLength()));
								failures = Float.parseFloat(String.valueOf(getSetFailureTestCases()));
								errors = Float.parseFloat(String.valueOf(getErrorTestCases()));
								float success = 0;

								if (failures != 0 && errors == 0) {
									if (failures > tests) {
										success = failures - tests;
									} else {
										success = tests - failures;
									}
								} else if (failures == 0 && errors != 0) {
									if (errors > tests) {
										success = errors - tests;
									} else {
										success = tests - errors;
									}
								} else if (failures != 0 && errors != 0) {
									float failTotal = (failures + errors);
									if (failTotal > tests) {
										success = failTotal - tests;
									} else {
										success = tests - failTotal;
									}
								} else {
									success = tests;
								}

								totalTestSuites = totalTestSuites + tests;
								failureTestSuites = failureTestSuites + failures;
								errorTestSuites = errorTestSuites + errors;
								successTestSuites = successTestSuites + success;
								String rstValues = tests + "," + success + "," + failures + "," + errors;
								testSuitesResultMap.put(tstSuite.getName(), rstValues);
							}
						}
					}
				}
				TestReportResult result = new TestReportResult();
				result.setTestReports(testSuitesResultMap);
				createTestReportResult(testSuitesResultMap, result);
				ResponseInfo<TestReportResult> finalOutput = responseDataEvaluation(responseDataAll, null,
						"Test Cases listed successfully", result);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
						.build();
			} else {
				if (testSuites.getLength() > 0) {
					List<TestCase> testCases;
					testCases = getTestCases(appDirName, testSuites, testSuitePath, testCasePath);
					if (CollectionUtils.isEmpty(testCases)) {
						ResponseInfo<List<TestCase>> finalOutput = responseDataEvaluation(responseData, null,
								"TestCase Not Available", testCases);
						return Response.status(Status.OK).entity(finalOutput)
								.header("Access-Control-Allow-Origin", "*").build();
					} else {
						boolean isClassEmpty = false;
						// to check whether class attribute is there or not
						for (TestCase testCase : testCases) {
							if (testCase.getTestClass() == null) {
								isClassEmpty = true;
							}
						}
						// setReqAttribute(IS_CLASS_EMPTY, isClassEmpty);
						ResponseInfo<List<TestCase>> finalOutput = responseDataEvaluation(responseData, null,
								"TestCase Listed Successfully", testCases);
						return Response.status(Status.OK).entity(finalOutput)
								.header("Access-Control-Allow-Origin", "*").build();
					}
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return null;
	}

	/**
	 * Creates the test report result.
	 *
	 * @param testSuitesResultMap the test suites result map
	 * @param result the result
	 */
	private void createTestReportResult(Map<String, String> testSuitesResultMap, TestReportResult result) {
		Set<String> keySet = testSuitesResultMap.keySet();
		int totalValue = keySet.size();
		// All testSuite total colum value calculation
		int totalTstCases = 0;
		int totalSuccessTstCases = 0;
		int totalFailureTstCases = 0;
		int totalErrorTstCases = 0;

		for (String key : keySet) {
			String csvResults = testSuitesResultMap.get(key);
			String[] results = csvResults.split(",");
			float total = Float.parseFloat(results[0]);
			float success = Float.parseFloat(results[1]);
			float failure = Float.parseFloat(results[2]);
			float error = Float.parseFloat(results[3]);
			totalTstCases = totalTstCases + (int) total;
			totalSuccessTstCases = totalSuccessTstCases + (int) success;
			totalFailureTstCases = totalFailureTstCases + (int) failure;
			totalErrorTstCases = totalErrorTstCases + (int) error;
		}
		result.setTotalTestError(totalErrorTstCases);
		result.setTotalTestFailure(totalFailureTstCases);
		result.setTotalTestSuccess(totalSuccessTstCases);
		result.setTotalTestResults(totalValue);
	}

	/**
	 * Gets the test cases.
	 *
	 * @param appDirName the app dir name
	 * @param testSuites the test suites
	 * @param testSuitePath the test suite path
	 * @param testCasePath the test case path
	 * @return the test cases
	 * @throws PhrescoException the phresco exception
	 */
	private List<TestCase> getTestCases(String appDirName, NodeList testSuites, String testSuitePath,
			String testCasePath) throws PhrescoException {
		try {
			StringBuilder sb = new StringBuilder(); // testsuites/testsuite[@name='yyy']/testcase
			sb.append(testSuitePath);
			sb.append(NAME_FILTER_PREFIX);
			sb.append(getTestSuite());
			sb.append(NAME_FILTER_SUFIX);
			sb.append(testCasePath);

			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xpath.evaluate(sb.toString(), testSuites.item(0).getParentNode(),
					XPathConstants.NODESET);
			// For tehnologies like php and drupal duoe to plugin change xml
			// testcase path modified
			if (nodeList.getLength() == 0) {
				StringBuilder sbMulti = new StringBuilder();
				sbMulti.append(testSuitePath);
				sbMulti.append(NAME_FILTER_PREFIX);
				sbMulti.append(getTestSuite());
				sbMulti.append(NAME_FILTER_SUFIX);
				sbMulti.append(XPATH_TESTSUTE_TESTCASE);
				nodeList = (NodeList) xpath.evaluate(sbMulti.toString(), testSuites.item(0).getParentNode(),
						XPathConstants.NODESET);
			}

			// For technology sharepoint
			if (nodeList.getLength() == 0) {
				StringBuilder sbMulti = new StringBuilder(); // testsuites/testsuite[@name='yyy']/testcase
				sbMulti.append(XPATH_MULTIPLE_TESTSUITE);
				sbMulti.append(NAME_FILTER_PREFIX);
				sbMulti.append(getTestSuite());
				sbMulti.append(NAME_FILTER_SUFIX);
				sbMulti.append(testCasePath);
				nodeList = (NodeList) xpath.evaluate(sbMulti.toString(), testSuites.item(0).getParentNode(),
						XPathConstants.NODESET);
			}

			List<TestCase> testCases = new ArrayList<TestCase>();

			StringBuilder screenShotDir = new StringBuilder(Utility.getProjectHome() + appDirName);
			screenShotDir.append(File.separator);
			String sceenShotDir = getSceenShotDir(appDirName);
			if (StringUtils.isEmpty(sceenShotDir)) {
				screenShotDir.append(getFunctionalTestReportDir(appDirName));
				screenShotDir.append(File.separator);
				screenShotDir.append(SCREENSHOT_DIR);
			} else {
				screenShotDir.append(sceenShotDir);
			}
			screenShotDir.append(File.separator);

			int failureTestCases = 0;
			int errorTestCases = 0;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList childNodes = node.getChildNodes();
				NamedNodeMap nameNodeMap = node.getAttributes();
				TestCase testCase = new TestCase();
				for (int k = 0; k < nameNodeMap.getLength(); k++) {
					Node attribute = nameNodeMap.item(k);
					String attributeName = attribute.getNodeName();
					String attributeValue = attribute.getNodeValue();
					if (ATTR_NAME.equals(attributeName)) {
						testCase.setName(attributeValue);
					} else if (ATTR_CLASS.equals(attributeName) || ATTR_CLASSNAME.equals(attributeName)) {
						testCase.setTestClass(attributeValue);
					} else if (ATTR_FILE.equals(attributeName)) {
						testCase.setFile(attributeValue);
					} else if (ATTR_LINE.equals(attributeName)) {
						testCase.setLine(Float.parseFloat(attributeValue));
					} else if (ATTR_ASSERTIONS.equals(attributeName)) {
						testCase.setAssertions(Float.parseFloat(attributeValue));
					} else if (ATTR_TIME.equals(attributeName)) {
						testCase.setTime(attributeValue);
					}
				}

				if (childNodes != null && childNodes.getLength() > 0) {
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node childNode = childNodes.item(j);
						if (ELEMENT_FAILURE.equals(childNode.getNodeName())) {
							failureTestCases++;
							TestCaseFailure failure = getFailure(childNode);
							if (failure != null) {
								File file = new File(screenShotDir.toString() + testCase.getName()
										+ FrameworkConstants.DOT + IMG_PNG_TYPE);
								if (file.exists()) {
									failure.setHasFailureImg(true);
								}
								testCase.setTestCaseFailure(failure);
							}
						}

						if (ELEMENT_ERROR.equals(childNode.getNodeName())) {
							errorTestCases++;
							TestCaseError error = getError(childNode);
							if (error != null) {
								File file = new File(screenShotDir.toString() + testCase.getName()
										+ FrameworkConstants.DOT + IMG_PNG_TYPE);
								if (file.exists()) {
									error.setHasErrorImg(true);
								}
								testCase.setTestCaseError(error);
							}
						}
					}
				}
				testCases.add(testCase);
			}
			setSetFailureTestCases(failureTestCases);
			setErrorTestCases(errorTestCases);
			setNodeLength(nodeList.getLength());
			return testCases;
		} catch (PhrescoException e) {
			throw e;
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the failure.
	 *
	 * @param failureNode the failure node
	 * @return the failure
	 */
	private static TestCaseFailure getFailure(Node failureNode) {
		TestCaseFailure failure = new TestCaseFailure();
		failure.setDescription(failureNode.getTextContent());
		failure.setFailureType(REQ_TITLE_EXCEPTION);
		NamedNodeMap nameNodeMap = failureNode.getAttributes();

		if (nameNodeMap != null && nameNodeMap.getLength() > 0) {
			for (int k = 0; k < nameNodeMap.getLength(); k++) {
				Node attribute = nameNodeMap.item(k);
				String attributeName = attribute.getNodeName();
				String attributeValue = attribute.getNodeValue();

				if (ATTR_TYPE.equals(attributeName)) {
					failure.setFailureType(attributeValue);
				}
			}
		}
		return failure;
	}

	/**
	 * Gets the error.
	 *
	 * @param errorNode the error node
	 * @return the error
	 */
	private static TestCaseError getError(Node errorNode) {
		TestCaseError tcError = new TestCaseError();
		tcError.setDescription(errorNode.getTextContent());
		tcError.setErrorType(REQ_TITLE_ERROR);
		NamedNodeMap nameNodeMap = errorNode.getAttributes();
		if (nameNodeMap != null && nameNodeMap.getLength() > 0) {
			for (int k = 0; k < nameNodeMap.getLength(); k++) {
				Node attribute = nameNodeMap.item(k);
				String attributeName = attribute.getNodeName();
				String attributeValue = attribute.getNodeValue();

				if (ATTR_TYPE.equals(attributeName)) {
					tcError.setErrorType(attributeValue);
				}
			}
		}
		return tcError;
	}

	/**
	 * Gets the unit report options.
	 *
	 * @param appDirName the app dir name
	 * @return the unit report options
	 * @throws PhrescoException the phresco exception
	 */
	private List<String> getUnitReportOptions(String appDirName) throws PhrescoException {
		try {
			String unitTestReportOptions = getUnitTestReportOptions(appDirName);
			if (StringUtils.isNotEmpty(unitTestReportOptions)) {
				return Arrays.asList(unitTestReportOptions.split(","));
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return null;
	}

	/**
	 * Gets the test result path.
	 *
	 * @param appDirName the app dir name
	 * @param moduleName the module name
	 * @param testType the test type
	 * @param techReport the tech report
	 * @return the test result path
	 * @throws PhrescoException the phresco exception
	 */
	private String getTestResultPath(String appDirName, String moduleName, String testType, String techReport)
			throws PhrescoException {
		String testResultPath = "";
		if (testType.equals("unit")) {
			testResultPath = getUnitTestResultPath(appDirName, moduleName, techReport);
		} else if (testType.equals("functional")) {
			testResultPath = getFunctionalTestResultPath(appDirName, moduleName);
		} else if (testType.equals("component")) {
			testResultPath = getComponentTestResultPath(appDirName, moduleName);
		} else if (testType.equals("performance")) {

		} else if (testType.equals("load")) {

		}
		return testResultPath;
	}

	/**
	 * Gets the test suite path.
	 *
	 * @param appDirName the app dir name
	 * @param testType the test type
	 * @param techReport the tech report
	 * @return the test suite path
	 * @throws PhrescoException the phresco exception
	 */
	private String getTestSuitePath(String appDirName, String testType, String techReport) throws PhrescoException {
		String testSuitePath = "";
		if (testType.equals("unit")) {
			if (StringUtils.isNotEmpty(techReport)) {
				testSuitePath = getUnitTestSuitePath(appDirName, techReport);
			} else {
				testSuitePath = getUnitTestSuitePath(appDirName);
			}
		} else if (testType.equals("component")) {
			testSuitePath = getComponentTestSuitePath(appDirName);
		} else if (testType.equals("functional")) {
			testSuitePath = getFunctionalTestSuitePath(appDirName);
		} else if (testType.equals("performance")) {

		} else if (testType.equals("load")) {

		}
		return testSuitePath;
	}

	/**
	 * Gets the unit test result path.
	 *
	 * @param appDirName the app dir name
	 * @param moduleName the module name
	 * @param techReport the tech report
	 * @return the unit test result path
	 * @throws PhrescoException the phresco exception
	 */
	private String getUnitTestResultPath(String appDirName, String moduleName, String techReport)
			throws PhrescoException {
		StringBuilder sb = new StringBuilder(Utility.getProjectHome() + appDirName);
		if (StringUtils.isNotEmpty(moduleName)) {
			sb.append(File.separatorChar);
			sb.append(moduleName);
		}
		// TODO Need to change this
		StringBuilder tempsb = new StringBuilder(sb);
		if (FrameworkConstants.JAVASCRIPT.equals(techReport)) {
			tempsb.append(FrameworkConstants.UNIT_TEST_QUNIT_REPORT_DIR);
			File file = new File(tempsb.toString());
			if (file.isDirectory() && file.list().length > 0) {
				sb.append(FrameworkConstants.UNIT_TEST_QUNIT_REPORT_DIR);
			} else {
				sb.append(FrameworkConstants.UNIT_TEST_JASMINE_REPORT_DIR);
			}
		} else {
			if (StringUtils.isNotEmpty(techReport)) {
				sb.append(getUnitTestReportDir(appDirName, techReport));
			} else {
				sb.append(getUnitTestReportDir(appDirName));
			}
		}
		return sb.toString();
	}

	/**
	 * Gets the functional test result path.
	 *
	 * @param appDirName the app dir name
	 * @param moduleName the module name
	 * @return the functional test result path
	 * @throws PhrescoException the phresco exception
	 */
	private String getFunctionalTestResultPath(String appDirName, String moduleName) throws PhrescoException {

		StringBuilder sb = new StringBuilder();
		try {
			sb.append(Utility.getProjectHome() + appDirName);
			if (StringUtils.isNotEmpty(moduleName)) {
				sb.append(File.separatorChar);
				sb.append(moduleName);
			}
			sb.append(getFunctionalTestReportDir(appDirName));
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}

		return sb.toString();
	}

	/**
	 * Gets the component test result path.
	 *
	 * @param appDirName the app dir name
	 * @param moduleName the module name
	 * @return the component test result path
	 * @throws PhrescoException the phresco exception
	 */
	private String getComponentTestResultPath(String appDirName, String moduleName) throws PhrescoException {

		StringBuilder sb = new StringBuilder();
		try {
			sb.append(Utility.getProjectHome() + appDirName);
			if (StringUtils.isNotEmpty(moduleName)) {
				sb.append(File.separatorChar);
				sb.append(moduleName);
			}
			sb.append(getComponentTestReportDir(appDirName));
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}

		return sb.toString();
	}

	/**
	 * Gets the test suite names.
	 *
	 * @param appDirName the app dir name
	 * @param testType the test type
	 * @param moduleName the module name
	 * @param techReport the tech report
	 * @param testResultPath the test result path
	 * @param testSuitePath the test suite path
	 * @return the test suite names
	 * @throws PhrescoException the phresco exception
	 */
	private List<String> getTestSuiteNames(String appDirName, String testType, String moduleName, String techReport,
			String testResultPath, String testSuitePath) throws PhrescoException {
		String testSuitesMapKey = appDirName + testType + moduleName + techReport;
		Map<String, NodeList> testResultNameMap = testSuiteMap.get(testSuitesMapKey);
		List<String> resultTestSuiteNames = null;
		if (MapUtils.isEmpty(testResultNameMap)) {
			File[] resultFiles = getTestResultFiles(testResultPath);
			if (!ArrayUtils.isEmpty(resultFiles)) {
				QualityUtil.sortResultFile(resultFiles);
				updateCache(appDirName, testType, moduleName, techReport, resultFiles, testSuitePath);
			}
			testResultNameMap = testSuiteMap.get(testSuitesMapKey);
		}
		if (testResultNameMap != null) {
			resultTestSuiteNames = new ArrayList<String>(testResultNameMap.keySet());
		}
		return resultTestSuiteNames;
	}

	/**
	 * Gets the unit test report dir.
	 *
	 * @param appDirName the app dir name
	 * @param option the option
	 * @return the unit test report dir
	 * @throws PhrescoException the phresco exception
	 */
	private String getUnitTestReportDir(String appDirName, String option) throws PhrescoException {
		try {
			PomProcessor pomProcessor = FrameworkUtil.getInstance().getPomProcessor(appDirName);
			return pomProcessor.getProperty(Constants.POM_PROP_KEY_UNITTEST_RPT_DIR_START + option
					+ Constants.POM_PROP_KEY_UNITTEST_RPT_DIR_END);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the unit test report dir.
	 *
	 * @param appDirName the app dir name
	 * @return the unit test report dir
	 * @throws PhrescoException the phresco exception
	 */
	public String getUnitTestReportDir(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_UNITTEST_RPT_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the unit test suite path.
	 *
	 * @param appDirName the app dir name
	 * @param option the option
	 * @return the unit test suite path
	 * @throws PhrescoException the phresco exception
	 */
	public String getUnitTestSuitePath(String appDirName, String option) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_UNITTEST_TESTSUITE_XPATH_START + option
							+ Constants.POM_PROP_KEY_UNITTEST_TESTSUITE_XPATH_END);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the unit test suite path.
	 *
	 * @param appDirName the app dir name
	 * @return the unit test suite path
	 * @throws PhrescoException the phresco exception
	 */
	public String getUnitTestSuitePath(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_UNITTEST_TESTSUITE_XPATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Update cache.
	 *
	 * @param appDirName the app dir name
	 * @param testType the test type
	 * @param moduleName the module name
	 * @param techReport the tech report
	 * @param resultFiles the result files
	 * @param testSuitePath the test suite path
	 * @throws PhrescoException the phresco exception
	 */
	private void updateCache(String appDirName, String testType, String moduleName, String techReport,
			File[] resultFiles, String testSuitePath) throws PhrescoException {
		Map<String, NodeList> mapTestSuites = new HashMap<String, NodeList>(10);
		for (File resultFile : resultFiles) {
			Document doc = getDocument(resultFile);
			NodeList testSuiteNodeList = evaluateTestSuite(doc, testSuitePath);
			if (testSuiteNodeList.getLength() > 0) {
				List<TestSuite> allTestSuites = getTestSuite(testSuiteNodeList);
				for (TestSuite tstSuite : allTestSuites) {
					mapTestSuites.put(tstSuite.getName(), testSuiteNodeList);
				}
			}
		}
		String testSuitesKey = appDirName + testType + moduleName + techReport;

		testSuiteMap.put(testSuitesKey, mapTestSuites);
	}

	/**
	 * Gets the document.
	 *
	 * @param resultFile the result file
	 * @return the document
	 * @throws PhrescoException the phresco exception
	 */
	private Document getDocument(File resultFile) throws PhrescoException {
		InputStream fis = null;
		DocumentBuilder builder = null;
		try {
			fis = new FileInputStream(resultFile);
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(false);
			builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(fis);
			return doc;
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Evaluate test suite.
	 *
	 * @param doc the doc
	 * @param testSuitePath the test suite path
	 * @return the node list
	 * @throws PhrescoException the phresco exception
	 */
	private NodeList evaluateTestSuite(Document doc, String testSuitePath) throws PhrescoException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression xPathExpression;
		NodeList testSuiteNode = null;
		try {
			xPathExpression = xpath.compile(testSuitePath);
			testSuiteNode = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		}
		return testSuiteNode;
	}

	/**
	 * Gets the test suite.
	 *
	 * @param nodelist the nodelist
	 * @return the test suite
	 * @throws PhrescoException the phresco exception
	 */
	private List<TestSuite> getTestSuite(NodeList nodelist) throws PhrescoException {
		List<TestSuite> allTestSuites = new ArrayList<TestSuite>(2);
		TestSuite tstSuite = null;
		for (int i = 0; i < nodelist.getLength(); i++) {
			tstSuite = new TestSuite();
			Node node = nodelist.item(i);
			NamedNodeMap nameNodeMap = node.getAttributes();

			for (int k = 0; k < nameNodeMap.getLength(); k++) {
				Node attribute = nameNodeMap.item(k);
				String attributeName = attribute.getNodeName();
				String attributeValue = attribute.getNodeValue();
				if (FrameworkConstants.ATTR_ASSERTIONS.equals(attributeName)) {
					tstSuite.setAssertions(attributeValue);
				} else if (FrameworkConstants.ATTR_ERRORS.equals(attributeName)) {
					tstSuite.setErrors(Float.parseFloat(attributeValue));
				} else if (FrameworkConstants.ATTR_FAILURES.equals(attributeName)) {
					tstSuite.setFailures(Float.parseFloat(attributeValue));
				} else if (FrameworkConstants.ATTR_FILE.equals(attributeName)) {
					tstSuite.setFile(attributeValue);
				} else if (FrameworkConstants.ATTR_NAME.equals(attributeName)) {
					tstSuite.setName(attributeValue);
				} else if (FrameworkConstants.ATTR_TESTS.equals(attributeName)) {
					tstSuite.setTests(Float.parseFloat(attributeValue));
				} else if (FrameworkConstants.ATTR_TIME.equals(attributeName)) {
					tstSuite.setTime(attributeValue);
				}
			}
			allTestSuites.add(tstSuite);
		}
		return allTestSuites;
	}

	/**
	 * Gets the test result files.
	 *
	 * @param path the path
	 * @return the test result files
	 */
	private File[] getTestResultFiles(String path) {
		File testDir = new File(path);
		if (testDir.isDirectory()) {
			FilenameFilter filter = new FileListFilter("", "xml");
			return testDir.listFiles(filter);
		}
		return null;
	}

	/**
	 * Gets the functional test suite path.
	 *
	 * @param appDirName the app dir name
	 * @return the functional test suite path
	 * @throws PhrescoException the phresco exception
	 */
	private String getFunctionalTestSuitePath(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_FUNCTEST_TESTSUITE_XPATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the component test suite path.
	 *
	 * @param appDirName the app dir name
	 * @return the component test suite path
	 * @throws PhrescoException the phresco exception
	 */
	private String getComponentTestSuitePath(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_COMPONENTTEST_TESTSUITE_XPATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the functional test report dir.
	 *
	 * @param appDirName the app dir name
	 * @return the functional test report dir
	 * @throws PhrescoException the phresco exception
	 */
	private String getFunctionalTestReportDir(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_FUNCTEST_RPT_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the component test report dir.
	 *
	 * @param appDirName the app dir name
	 * @return the component test report dir
	 * @throws PhrescoException the phresco exception
	 */
	private String getComponentTestReportDir(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_COMPONENTTEST_RPT_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the unit test report options.
	 *
	 * @param appDirName the app dir name
	 * @return the unit test report options
	 * @throws PhrescoException the phresco exception
	 */
	private String getUnitTestReportOptions(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.PHRESCO_UNIT_TEST);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the unit test case path.
	 *
	 * @param appDirName the app dir name
	 * @param option the option
	 * @return the unit test case path
	 * @throws PhrescoException the phresco exception
	 */
	private String getUnitTestCasePath(String appDirName, String option) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_UNITTEST_TESTCASE_PATH_START + option
							+ Constants.POM_PROP_KEY_UNITTEST_TESTCASE_PATH_END);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the unit test case path.
	 *
	 * @param appDirName the app dir name
	 * @return the unit test case path
	 * @throws PhrescoException the phresco exception
	 */
	private String getUnitTestCasePath(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_UNITTEST_TESTCASE_PATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the functional test case path.
	 *
	 * @param appDirName the app dir name
	 * @return the functional test case path
	 * @throws PhrescoException the phresco exception
	 */
	private String getFunctionalTestCasePath(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_FUNCTEST_TESTCASE_PATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the component test case path.
	 *
	 * @param appDirName the app dir name
	 * @return the component test case path
	 * @throws PhrescoException the phresco exception
	 */
	private String getComponentTestCasePath(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_COMPONENTTEST_TESTCASE_PATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Gets the sceen shot dir.
	 *
	 * @param appDirName the app dir name
	 * @return the sceen shot dir
	 * @throws PhrescoException the phresco exception
	 */
	private String getSceenShotDir(String appDirName) throws PhrescoException {
		try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(
					Constants.POM_PROP_KEY_SCREENSHOT_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Sets the sets the failure test cases.
	 *
	 * @param setFailureTestCases the new sets the failure test cases
	 */
	public void setSetFailureTestCases(int setFailureTestCases) {
		this.setFailureTestCases = setFailureTestCases;
	}

	/**
	 * Gets the sets the failure test cases.
	 *
	 * @return the sets the failure test cases
	 */
	public int getSetFailureTestCases() {
		return setFailureTestCases;
	}

	/**
	 * Sets the error test cases.
	 *
	 * @param errorTestCases the new error test cases
	 */
	public void setErrorTestCases(int errorTestCases) {
		this.errorTestCases = errorTestCases;
	}

	/**
	 * Gets the error test cases.
	 *
	 * @return the error test cases
	 */
	public int getErrorTestCases() {
		return errorTestCases;
	}

	/**
	 * Sets the node length.
	 *
	 * @param nodeLength the new node length
	 */
	public void setNodeLength(int nodeLength) {
		this.nodeLength = nodeLength;
	}

	/**
	 * Gets the node length.
	 *
	 * @return the node length
	 */
	public int getNodeLength() {
		return nodeLength;
	}

	/**
	 * Gets the test suite.
	 *
	 * @return the test suite
	 */
	public String getTestSuite() {
		return testSuite;
	}

	/**
	 * Sets the test suite.
	 *
	 * @param testSuite the new test suite
	 */
	public void setTestSuite(String testSuite) {
		this.testSuite = testSuite;
	}
}