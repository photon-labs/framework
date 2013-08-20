package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.ManualTestResult;
import com.photon.phresco.framework.model.TestSuite;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path(ServiceConstants.REST_API_MANUAL)
public class ManualTestService extends RestBase implements ServiceConstants, FrameworkConstants, ResponseCodes {
	
	@GET
	@Path(REST_API_MANUALTEMPLATE)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getManualTemplate(@QueryParam(REST_QUERY_FILETYPE) String fileType) throws PhrescoException {
		ResponseInfo info = new ResponseInfo();
        try {
        	InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("helios_manul_test_template." + fileType);
        	if(resourceStream == null) {
        		return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*").entity(resourceStream).build();
        	}
        	
        	return Response.status(Status.OK).entity(resourceStream).header("Content-Disposition", "attachment; filename=helios_manul_test_template." + fileType).build();
        } catch (Exception e) {
        	ResponseInfo finalOutput = responseDataEvaluation(info, e,
					null, RESPONSE_STATUS_ERROR, PHRQ410001);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	@POST
	@Path(REST_API_UPLOADTEMPLATE)
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadManualTemplate(@Context HttpServletRequest request, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) 
		throws PhrescoException {
		ResponseInfo response = new ResponseInfo();
        try {
        	String uploadedFileName = request.getHeader(FrameworkConstants.X_FILE_NAME);
        	String fileName = URLDecoder.decode(uploadedFileName, "UTF-8");
        	InputStream inputStream = request.getInputStream();
        	PomProcessor pomProcessor = FrameworkUtil.getInstance().getPomProcessor(appDirName);
        	String manualTestReportPath = pomProcessor.getProperty("phresco.manualTest.report.dir");
        	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    		builder.append(appDirName);
    		builder.append(File.separator);
    		builder.append(manualTestReportPath);
    		File file = new File(builder.toString() + File.separator + fileName);
    		FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (Exception e) {
        	ResponseInfo finalOutput = responseDataEvaluation(response, e,
					null, RESPONSE_STATUS_ERROR, PHRQ410002);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
        ResponseInfo responseData = responseDataEvaluation(response, null, null, RESPONSE_STATUS_SUCCESS, PHRQ400001);
		return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*").entity(responseData).build();
	}
	
	@GET
	@Path(REST_API_TESTSUITES)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestSuites(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) throws PhrescoException {
		ResponseInfo<List<TestSuite>> responseData = new ResponseInfo<List<TestSuite>>();
		List<TestSuite> readManualTestSuiteFile = new ArrayList<TestSuite>();
		ManualTestResult createManualTestResult = null;
		ResponseInfo<List<TestSuite>> finalOutput = null;
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String manualTestDir = getManualTestReportDir(appDirName);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(manualTestDir);
			File file = new File(sb.toString());
			if (! new File(sb.toString()).exists()) {
				finalOutput = responseDataEvaluation(responseData, null, createManualTestResult, RESPONSE_STATUS_FAILURE, PHRQ410003);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			readManualTestSuiteFile = frameworkUtil.readManualTestSuiteFile(sb.toString());
			createManualTestResult = createManualTestResult(readManualTestSuiteFile);
			finalOutput = responseDataEvaluation(responseData, null, createManualTestResult, RESPONSE_STATUS_SUCCESS, PHRQ400002);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHRQ410004);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} 
	}
	
	@GET
	@Path(REST_API_TESTCASES)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestCase(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, 
			@QueryParam("testSuiteName") String testsuitename) throws PhrescoException {
		ResponseInfo<List<com.photon.phresco.commons.model.TestCase>> responseData = new 
			ResponseInfo<List<com.photon.phresco.commons.model.TestCase>>();
		List<com.photon.phresco.commons.model.TestCase> readTestCase = null;
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String manualTestDir = getManualTestReportDir(appDirName);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(manualTestDir);
			readTestCase = frameworkUtil.readManualTestCaseFile(sb.toString(), testsuitename, null);
			ResponseInfo<List<com.photon.phresco.commons.model.TestCase>> finalOutput = 
				responseDataEvaluation(responseData, null, readTestCase, RESPONSE_STATUS_SUCCESS, PHRQ400003);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<List<com.photon.phresco.commons.model.TestCase>> finalOutput = 
				responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHRQ410005);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
	@Path(REST_API_TESTSUITES)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response createTestSuite(@QueryParam("testSuiteName") String testSuiteName, 
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String path = getManualTestReportDir(appDirName);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(path);
			String cellValue[] = {"", "", testSuiteName, "", "", "", "", "", "", "", "", "", ""};
			frameworkUtil.addNew(sb.toString(), testSuiteName, cellValue);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, RESPONSE_STATUS_SUCCESS, PHRQ400004);
			return Response.status(Status.OK).entity(finalOutput).build();
		} catch(PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHRQ410006);
			return Response.status(Status.OK).entity(finalOutput).build();
		}
	}
	
	@POST
	@Path(REST_API_TESTCASES)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response createTestCase(com.photon.phresco.commons.model.TestCase testCase, @QueryParam("testSuiteName") String testSuiteName,
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String path = getManualTestReportDir(appDirName);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(path);
			String cellValue[] = {"", testCase.getFeatureId(), "",testCase.getTestCaseId(), testCase.getDescription(), testCase.getSteps(), "", "",
					testCase.getExpectedResult(), testCase.getActualResult(), testCase.getStatus(), "", "", testCase.getBugComment()};
			frameworkUtil.addNewTestCase(sb.toString(), testSuiteName,cellValue, testCase.getStatus());
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, testCase, RESPONSE_STATUS_SUCCESS, PHRQ400005);
			return Response.status(Status.OK).entity(finalOutput).build();
		} catch(PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHRQ410007);
			return Response.status(Status.OK).entity(finalOutput).build();
		}
	}
	
	@PUT
	@Path("/testcases")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response updateTestCase(com.photon.phresco.commons.model.TestCase testCase, @QueryParam("testSuiteName") String testSuiteName,
			@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) throws PhrescoException {
		ResponseInfo responseData = new ResponseInfo();
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String path = getManualTestReportDir(appDirName);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(path);
			frameworkUtil.readManualTestCaseFile(sb.toString(), testSuiteName, testCase);
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, testCase, RESPONSE_STATUS_SUCCESS, PHRQ400006);
			return Response.status(Status.OK).entity(finalOutput).build();
		} catch(PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHRQ410008);
			return Response.status(Status.OK).entity(finalOutput).build();			
		}
	}
	
	private ManualTestResult createManualTestResult(List<TestSuite> readManualTestSuiteFile) {
		ManualTestResult mtr = new ManualTestResult();
		float totalTestSuccess = 0;
		float totalFailure = 0;
		float totalNotApplicable = 0;
		float totalBlocked = 0;
		float totalNotExecuted = 0;
		float total = 0;
		float totalTestCoverage = 0;
		for (TestSuite testSuite : readManualTestSuiteFile) {
			totalTestSuccess =  totalTestSuccess + testSuite.getSuccess();
			totalFailure = totalFailure + testSuite.getFailures();
			totalNotApplicable = totalNotApplicable + testSuite.getNotApplicable();
			totalBlocked = totalBlocked + testSuite.getBlocked();
			totalNotExecuted = totalNotExecuted + testSuite.getNotExecuted();
			total = total + testSuite.getTotal();
			totalTestCoverage = totalTestCoverage + testSuite.getTestCoverage();
		}
		mtr.setTestSuites(readManualTestSuiteFile);
		mtr.setTotalSuccess(totalTestSuccess);
		mtr.setTotalFailure(totalFailure);
		mtr.setTotalNotApplicable(totalNotApplicable);
		mtr.setTotalBlocked(totalBlocked);
		mtr.setTotalNotExecuted(totalNotExecuted);
		mtr.setTotal(total);
		mtr.setTotalTestCoverage(totalTestCoverage);
		return mtr;
	}

	private String getManualTestReportDir(String appDirName) throws PhrescoException {
        try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.POM_PROP_KEY_MANUALTEST_RPT_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
}