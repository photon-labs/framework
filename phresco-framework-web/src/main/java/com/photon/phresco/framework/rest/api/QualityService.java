package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FileListFilter;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.QualityUtil;
import com.photon.phresco.framework.model.TestSuite;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/quality")
public class QualityService extends RestBase implements ServiceConstants, FrameworkConstants {
	
	private static Map<String, Map<String, NodeList>> testSuiteMap = Collections.synchronizedMap(new HashMap<String, Map<String, NodeList>>(8));
	private boolean updateCache;
	
	@GET
	@Path("/unit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unit(@QueryParam("appDirName") String appDirName, @QueryParam("userId") String userId) {
		ResponseInfo<Map> responseData = new ResponseInfo<Map>();
		try {
			List<String> unitReportOptions = getUnitReportOptions(appDirName);
			List<String> projectModules = FrameworkServiceUtil.getProjectModules(appDirName);
			Map<String, List<String>> unitTestOptionsMap = new HashMap<String, List<String>>();
			unitTestOptionsMap.put("reportOptions", unitReportOptions);
			unitTestOptionsMap.put("projectModules", projectModules);
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "Parameter returned successfully", unitTestOptionsMap);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, "Unable to get unit test report options", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
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
	
	@GET
	@Path("/testsuites")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestSuites(@QueryParam("appDirName") String appDirName, @QueryParam("testType") String testType,
			@QueryParam("techReport") String techReport, @QueryParam("moduleName") String moduleName) throws PhrescoException {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
        // TO kill the Process
        String baseDir = Utility.getProjectHome()+ appDirName;
        Utility.killProcess(baseDir, testType);
        
        String testResultPath = "";
        String testSuitePath = "";
        List<String> resultTestSuiteNames = null;
		try {
			testResultPath = getTestResultPath(appDirName, moduleName, testType, techReport);
			testSuitePath = getTestSuitePath(appDirName, testType, techReport);
			resultTestSuiteNames = getTestSuiteNames(appDirName, testType, moduleName, techReport, testResultPath, testSuitePath);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} 
		ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "Test Suites listed successfully", resultTestSuiteNames);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}
	
	private String getTestResultPath(String appDirName, String moduleName, String testType, String techReport) throws PhrescoException {
		String testResultPath = "";
		if(testType.equals("unit")) {
			testResultPath = getUnitTestResultPath(appDirName, moduleName, techReport);
		} else if(testType.equals("functional")) {
			getFunctionalTestResultPath(appDirName, moduleName);
		} else if(testType.equals("performance")) {
			
		} else if(testType.equals("load")) {
			
		}
		return testResultPath;	
	}
	
	private String getTestSuitePath(String appDirName, String testType, String techReport) throws PhrescoException {
		String testSuitePath = "";
		if (testType.equals("unit")) {
			if (StringUtils.isNotEmpty(techReport)) {
				testSuitePath = getUnitTestSuitePath(appDirName, techReport);
			} else {
				testSuitePath = getUnitTestSuitePath(appDirName);
			}
		} else if (testType.equals("functional")) {
			testSuitePath = getFunctionalTestSuitePath(appDirName);
		} else if (testType.equals("performance")) {

		} else if (testType.equals("load")) {

		}
		return testSuitePath;
	}
	
	private String getUnitTestResultPath(String appDirName, String moduleName, String techReport) throws PhrescoException {
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
        } catch (PhrescoPomException e) {
            throw new PhrescoException(e);
        }
        
        return sb.toString();
    }
	
	private List<String> getTestSuiteNames(String appDirName, String testType, 
			String moduleName, String techReport, String testResultPath, String testSuitePath) throws PhrescoException {
		String testSuitesMapKey = appDirName + testType +  moduleName + techReport;
		Map<String, NodeList> testResultNameMap = testSuiteMap.get(testSuitesMapKey);
		List<String> resultTestSuiteNames = null;
		if (MapUtils.isEmpty(testResultNameMap) || updateCache) {
			File[] resultFiles = getTestResultFiles(testResultPath);
			if (!ArrayUtils.isEmpty(resultFiles)) {
				QualityUtil.sortResultFile(resultFiles);
				updateCache(appDirName, testType, moduleName, techReport, resultFiles, testSuitePath);
			}
			testResultNameMap = testSuiteMap.get(testSuitesMapKey);
		}
		if (testResultNameMap != null) {
			resultTestSuiteNames = new ArrayList<String>(
					testResultNameMap.keySet());
		}
		return resultTestSuiteNames;
}
	
	private String getUnitTestReportDir(String appDirName, String option) throws PhrescoException {
        try {
        	PomProcessor pomProcessor = FrameworkUtil.getInstance().getPomProcessor(appDirName);
			return pomProcessor.
				getProperty(Constants.POM_PROP_KEY_UNITTEST_RPT_DIR_START + option + Constants.POM_PROP_KEY_UNITTEST_RPT_DIR_END);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
	
	public String getUnitTestReportDir(String appDirName) throws PhrescoException {
        try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.POM_PROP_KEY_UNITTEST_RPT_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
	
	public String getUnitTestSuitePath(String appDirName, String option) throws PhrescoException {
        try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.POM_PROP_KEY_UNITTEST_TESTSUITE_XPATH_START + 
					option + Constants.POM_PROP_KEY_UNITTEST_TESTSUITE_XPATH_END);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
	
	public String getUnitTestSuitePath(String appDirName) throws PhrescoException {
        try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.POM_PROP_KEY_UNITTEST_TESTSUITE_XPATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
	
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
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
	}
	
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
	
	private List<TestSuite> getTestSuite(NodeList nodelist) throws PhrescoException {
    	List<TestSuite> allTestSuites = new ArrayList<TestSuite>(2);
		TestSuite tstSuite = null;
		for (int i = 0; i < nodelist.getLength(); i++) {
		    tstSuite =  new TestSuite();
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
	
	private File[] getTestResultFiles(String path) {
		File testDir = new File(path);
        if (testDir.isDirectory()) {
            FilenameFilter filter = new FileListFilter("", "xml");
            return testDir.listFiles(filter);
        }
        return null;
	}
	
	private String getFunctionalTestSuitePath(String appDirName) throws PhrescoException {
        try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.POM_PROP_KEY_FUNCTEST_TESTSUITE_XPATH);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
	
	private String getFunctionalTestReportDir(String appDirName) throws PhrescoPomException, PhrescoException {
        try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.POM_PROP_KEY_FUNCTEST_RPT_DIR);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
    }
	
	private String getUnitTestReportOptions(String appDirName) throws PhrescoException, PhrescoPomException {
		return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.PHRESCO_UNIT_TEST);
	}
}