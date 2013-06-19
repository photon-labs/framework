package com.photon.phresco.framework.rest.api.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.configuration.ConfigurationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.rest.api.RestBase;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Childs.Child;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.MavenCommands.MavenCommand;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.plugins.util.MojoUtil;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.HubConfiguration;
import com.photon.phresco.util.NodeConfig;
import com.photon.phresco.util.NodeConfiguration;
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;



public class ActionFunction implements Constants ,FrameworkConstants,ActionServiceConstant {
	

	
	private final String PHASE_RUNAGAINST_SOURCE = "run-against-source";
    public static final java.lang.String SERVICE_URL = "phresco.service.url";
    public static final java.lang.String SERVICE_USERNAME = "phresco.service.username";
    public static final java.lang.String SERVICE_PASSWORD = "phresco.service.password";
    public static final java.lang.String SERVICE_API_KEY = "phresco.service.api.key";
    public static final String SUCCESS = "success";
    
	private static final Logger S_LOGGER= Logger.getLogger(ActionFunction.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
    
	private String appId="";
	private String projectId="";
	private String customerId="";
	private String selectedFiles="";
	private String phase="";
	private String username="";
	private String testAgainst = "";
	private String testName = "";
	private String resultJson = "";
	private String isFromCI = "";
	private List<String> minifyFileNames = null;
	private String minifyAll = "";
	private String projectModule = "";
	private String fromPage = "";
	private String pdfName = "";
	private String reportDataType = "";
	boolean connectionAlive = false;
	private ServiceManager serviceManager = null;
	HttpServletRequest request;
	
	
	public void prePopulateModelData(HttpServletRequest request) throws PhrescoException {
		
		try {
			this.request = request;
			
			if( !("".equalsIgnoreCase(request.getParameter(APP_ID))) && (request.getParameter(APP_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(APP_ID))) ) {
				setAppId(request.getParameter(APP_ID));	
			}
			else {
				throw new PhrescoException("No valid App Id Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(PROJECT_ID))) && (request.getParameter(PROJECT_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(PROJECT_ID))) ) {
				setProjectId(request.getParameter(PROJECT_ID));
			}
			else {
				throw new PhrescoException("No valid Project Id Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) && (request.getParameter(CUSTOMER_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) ) {
				setCustomerId(request.getParameter(CUSTOMER_ID));
			}
			else {
				throw new PhrescoException("No valid Customer Id Passed");
			}
			if( !("".equalsIgnoreCase(request.getParameter(BuildServiceConstants.USERNAME))) && (request.getParameter(BuildServiceConstants.USERNAME) != null) && !("null".equalsIgnoreCase(request.getParameter(BuildServiceConstants.USERNAME))) ) {
				setUsername(request.getParameter(BuildServiceConstants.USERNAME));
			}
			else {
				throw new PhrescoException("No User Id passed");
			}
			
			setSelectedFiles(request.getParameter(SELECTED_FILES));
			
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	public void prePopulatePerformanceTestData(HttpServletRequest request) throws PhrescoException {
		
		try {
			
			if( !("".equalsIgnoreCase(request.getParameter(TEST_AGAINST))) && (request.getParameter(TEST_AGAINST) != null) && !("null".equalsIgnoreCase(request.getParameter(TEST_AGAINST))) ) {
				setTestAgainst(request.getParameter(TEST_AGAINST));	
			}
			else {
				throw new PhrescoException("No valid TEST_AGAINST Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(TEST_NAME))) && (request.getParameter(TEST_NAME) != null) && !("null".equalsIgnoreCase(request.getParameter(TEST_NAME))) ) {
				setTestName(request.getParameter(TEST_NAME));	
			}
			else {
				throw new PhrescoException("No valid TEST_NAME Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(RESULT_JSON))) && (request.getParameter(RESULT_JSON) != null) && !("null".equalsIgnoreCase(request.getParameter(RESULT_JSON))) ) {
				setResultJson(request.getParameter(RESULT_JSON));	
			}
			else {
				throw new PhrescoException("No valid RESULT_JSON Passed");
			}
			//To avoid null pointer exception incase of normal performance test.
			setIsFromCI("");
			
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	
		
	}
	
	
	public void prePopulateMinificationData(HttpServletRequest request) throws PhrescoException {
		
		try {
			
			if( (request.getParameterValues(MINIFY_FILE_NAMES) != null) ) {
				String[] minify_file_names = request.getParameterValues(MINIFY_FILE_NAMES);
				List<String> minifyFileNames_local= new ArrayList<String>();
				for(String temp : minify_file_names) {
					minifyFileNames_local.add(temp);
				}
				if(minifyFileNames_local.size() != 0) {
				setMinifyFileNames(minifyFileNames_local);
				}
				else {
					throw new PhrescoException("No valid MINIFYFILENAMES Passed");
				}
			}
			else {
				throw new PhrescoException("No valid MINIFYFILENAMES Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(MINIFY_ALL))) && (request.getParameter(MINIFY_ALL) != null) && !("null".equalsIgnoreCase(request.getParameter(MINIFY_ALL))) ) {
				setMinifyAll(request.getParameter(MINIFY_ALL));	
			}
			else {
				//To avoid parser exception incase minifyall is not selected.
				setMinifyAll("false");
			}
			
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	public void prePopulateFunctionalTestData(HttpServletRequest request) throws PhrescoException {
		
		try {
			if( !("".equalsIgnoreCase(request.getParameter(PROJECT_MODULE))) && (request.getParameter(PROJECT_MODULE) != null) && !("null".equalsIgnoreCase(request.getParameter(PROJECT_MODULE))) ) {
				setProjectModule(request.getParameter(PROJECT_MODULE));	
			}
			else {
				//To avoid parser exception in case ProjectModule is not selected.
				setProjectModule("");
			}
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	public void prePopulatePrintAsPDFData(HttpServletRequest request) throws PhrescoException {
		
		try {
			if( !("".equalsIgnoreCase(request.getParameter(REPORT_DATA_TYPE))) && (request.getParameter(REPORT_DATA_TYPE) != null) && !("null".equalsIgnoreCase(request.getParameter(REPORT_DATA_TYPE))) ) {
				setReportDataType(request.getParameter(REPORT_DATA_TYPE));	
			}
			else {
				throw new PhrescoException("No valid REPORT_DATA_TYPE param Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(FROM_PAGE))) && (request.getParameter(FROM_PAGE) != null) && !("null".equalsIgnoreCase(request.getParameter(FROM_PAGE))) ) {
				setFromPage(request.getParameter(FROM_PAGE));	
			}
			else {
				throw new PhrescoException("No valid FROM_PAGE param Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(REQ_PDF_NAME))) && (request.getParameter(REQ_PDF_NAME) != null) && !("null".equalsIgnoreCase(request.getParameter(REQ_PDF_NAME))) ) {
				setPdfName(request.getParameter(REQ_PDF_NAME));	
			}
			
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
		private void printLogs()
		{
			if (isDebugEnabled) {
				S_LOGGER.debug("APPP ID received :"+getAppId());
				S_LOGGER.debug("PROJECT ID received :"+getProjectId());
				S_LOGGER.debug("CUSTOMER ID received :"+getCustomerId());
				S_LOGGER.debug("USERNAME  received :"+getUsername());
			}
		}
		
		private ActionResponse generateResponse(BufferedReader server_logs) {
			ActionResponse response = new ActionResponse();
			UUID uniqueKey = UUID.randomUUID();
		    String unique_key = uniqueKey.toString();
		    BufferMap.addBufferReader(unique_key, server_logs);
		     
		    response.setStatus(STARTED);
			response.setLog(STARTED);
			response.setService_exception("");
			response.setUniquekey(unique_key);
			
			return response;
		}
	
	
    	public ActionResponse build(HttpServletRequest request) throws PhrescoException, IOException {
    			
				printLogs();
        		BufferedReader server_logs=null;
        		server_logs = build(getUsername());
        		if(server_logs != null) {
        			return generateResponse(server_logs);
        		}
        		else {
        			throw new PhrescoException("No build logs obatined");
        		}
    	}
    
    
    	public ActionResponse deploy(HttpServletRequest request) throws PhrescoException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = deploy();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No Deploy logs obatined");
    		}
    	}
    	
    	
    	public ActionResponse runUnitTest(HttpServletRequest request) throws PhrescoException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = runUnitTest();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No unit test logs obatined");
    		}
    	}
    	
    	public ActionResponse runComponentTest(HttpServletRequest request) throws PhrescoException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = runComponentTest();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No component test logs obatined");
    		}
    	}
    	
    	public ActionResponse codeValidate(HttpServletRequest request) throws PhrescoException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = codeValidate();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No code validate logs obatined");
    		}
    	}
    	
    	public ActionResponse runAgainstSource(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = runAgainstSource();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No run against source logs obatined");
    		}
    	}
    	
    	public ActionResponse stopServer(HttpServletRequest request) throws PhrescoException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = stopServer();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No stopServer logs obatined");
    		}
    	}
    	
    	public ActionResponse restartServer(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = restartServer();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No restart server logs obatined");
    		}
    	}
    	
    	public ActionResponse performanceTest(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = performanceTest();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No performance test logs obatined");
    		}
    	}
    	
    	public ActionResponse loadTest(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = loadTest();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No loadtest logs obatined");
    		}
    	}
    	
    	public ActionResponse minification(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = minification();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No minification logs obatined");
    		}
    	}
    	
    	public ActionResponse startHub(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = startHub();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No startHub logs obatined");
    		}
    	}
    	
    	public ActionResponse startNode(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = startNode();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No startHub logs obatined");
    		}
    	}
    	
    	public ActionResponse runFunctionalTest(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = runFunctionalTest();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No startHub logs obatined");
    		}
    	}
    	
    	public ActionResponse stopHub(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = stopHub();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No startHub logs obatined");
    		}
    	}
    	
    	public ActionResponse stopNode(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = stopNode();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No startHub logs obatined");
    		}
    	}
    	
    	public ActionResponse showStartedHubLog(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = showStartedHubLog();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No startHub logs obatined");
    		}
    	}
    	
    	public ActionResponse showStartedNodeLog(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = showStartedNodeLog();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No startHub logs obatined");
    		}
    	}
    	
    	public ActionResponse checkForHub(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		ActionResponse response = new ActionResponse();
    		return checkForHub(response);
    	}
    	
    	public ActionResponse checkForNode(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		ActionResponse response = new ActionResponse();
    		return checkForNode(response);
    	}
    	
    	public ActionResponse generateSiteReport(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = generateSiteReport();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No generateSiteReport logs obatined");
    		}
    	}
    	
    	
    	public ActionResponse ciSetup(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = ciSetup();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No setup logs obatined");
    		}
    	}
    	
    	public ActionResponse ciStart(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = ciStart();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No ci startup logs obatined");
    		}
    	}
    	
    	public ActionResponse ciStop(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		BufferedReader server_logs=null;
    		server_logs = ciStop();
    		if(server_logs != null) {
    			return generateResponse(server_logs);
    		}
    		else {
    			throw new PhrescoException("No ci stop logs obatined");
    		}
    	}
    	
    	public ActionResponse printAsPdf(HttpServletRequest request) throws PhrescoException, IOException {
    		
    		printLogs();
    		ActionResponse response = new ActionResponse();
    		return printAsPdf(getUsername(),response);
    	}
    
    	public BufferedReader build(String username) throws PhrescoException, IOException {

		BufferedReader reader=null;
		try {
			
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = getProjectInfo();
			ApplicationInfo applicationInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_PACKAGE)));
			persistValuesToXml(mojo, PHASE_PACKAGE);
			//To get maven build arguments
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_PACKAGE);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			getApplicationProcessor(username).preBuild(getApplicationInfo());
			reader = applicationManager.performAction(projectInfo, ActionType.BUILD, buildArgCmds, workingDirectory);
			
		} catch (PhrescoException e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the build process"+e.getMessage());
		}
		 catch (Exception e) {
			 S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the build process"+e.getMessage());
			}

		return reader;
	}
	
	
	public BufferedReader deploy() throws PhrescoException {
		    	
	    	BufferedReader reader=null;
	    	
			if (isDebugEnabled) {
				S_LOGGER.debug("Entering Method  MavenFunctions.deploy()");
			}
			try {
				ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
				ProjectInfo projectInfo = getProjectInfo();
				ApplicationInfo applicationInfo = getApplicationInfo();
				MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_DEPLOY)));
				
				persistValuesToXml(mojo, PHASE_DEPLOY);
				//To get maven build arguments
				List<Parameter> parameters = getMojoParameters(mojo, PHASE_DEPLOY);
				List<String> buildArgCmds = getMavenArgCommands(parameters);
				buildArgCmds.add(HYPHEN_N);
				String workingDirectory = getAppDirectoryPath(applicationInfo);
				reader = applicationManager.performAction(projectInfo, ActionType.DEPLOY, buildArgCmds, workingDirectory);
				
			} catch (PhrescoException e) {
				if (isDebugEnabled) {
					S_LOGGER.error("Exception occured in the deploy process()" + FrameworkUtil.getStackTraceAsString(e));
					throw new PhrescoException("Exception occured in the deploy process");
				}
			}
	
			return reader;
	}
	
	
	public BufferedReader runUnitTest() throws PhrescoException {
		
		BufferedReader reader=null;
		
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.runUnitTest()");
        }
        
        try {
            ApplicationInfo appInfo = getApplicationInfo();
            StringBuilder workingDirectory = new StringBuilder(getAppDirectoryPath(appInfo));
            MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_UNIT_TEST)));
            persistValuesToXml(mojo, PHASE_UNIT_TEST);
            List<Parameter> parameters = getMojoParameters(mojo, PHASE_UNIT_TEST);
            List<String> buildArgCmds = getMavenArgCommands(parameters);
            buildArgCmds.add(HYPHEN_N);
            ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
            reader = applicationManager.performAction(getProjectInfo(), ActionType.UNIT_TEST, buildArgCmds, workingDirectory.toString());
        } catch (PhrescoException e) {
            if (isDebugEnabled) {
            	S_LOGGER.error("Exception occured in the unit test process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the unit test process");
            }
        }
        
        return reader;
    }
	
	public BufferedReader runComponentTest() throws PhrescoException {
		
		BufferedReader reader=null;
		
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.runComponentTest:Entry");
        }
        
        try {
            ApplicationInfo appInfo = getApplicationInfo();
            StringBuilder workingDirectory = new StringBuilder(getAppDirectoryPath(appInfo));
            MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_COMPONENT_TEST)));
            persistValuesToXml(mojo, PHASE_COMPONENT_TEST);
            List<Parameter> parameters = getMojoParameters(mojo, PHASE_COMPONENT_TEST);
            List<String> buildArgCmds = getMavenArgCommands(parameters);
            buildArgCmds.add(HYPHEN_N);
            ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
            reader = applicationManager.performAction(getProjectInfo(), ActionType.COMPONENT_TEST, buildArgCmds, workingDirectory.toString());
        } catch (PhrescoException e) {
            if (isDebugEnabled) {
            	S_LOGGER.error("Exception occured in the Component test process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the Component test process");
            }
        }
        
        return reader;
    }
	
	
	public BufferedReader codeValidate() throws PhrescoException {
		
		BufferedReader reader=null;
		
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.codeValidate()");
		}
		
		try {
			ProjectInfo projectInfo = getProjectInfo();
			ApplicationInfo applicationInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_VALIDATE_CODE)));
			persistValuesToXml(mojo, PHASE_VALIDATE_CODE);
			
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_VALIDATE_CODE);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			
			reader = applicationManager.performAction(projectInfo, ActionType.CODE_VALIDATE, buildArgCmds, workingDirectory);
		} catch (PhrescoException e) {
    		if (isDebugEnabled) {
            	S_LOGGER.error("Exception occured in the codeValidate process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the codeValidate process");
    		}
		}
		
		return reader;
	}
	
	public BufferedReader runAgainstSource() throws IOException, PhrescoException {
		
		BufferedReader reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.javaRunAgainstSource()");
		}
		
		try {
			BufferedReader compileReader = compileSource();
			String line = compileReader.readLine();
			while (StringUtils.isNotEmpty(line) && !line.startsWith("[INFO] BUILD FAILURE")) {
				line = compileReader.readLine();
			}
			
			if (StringUtils.isNotEmpty(line) && line.startsWith("[INFO] BUILD FAILURE")) {
				reader = new BufferedReader(new FileReader(getLogFilePath()));
			} else {
				MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_RUNGAINST_SRC_START)));
				persistValuesToXml(mojo, PHASE_RUNGAINST_SRC_START);
				com.photon.phresco.plugins.model.Mojos.Mojo.Configuration config = mojo.getConfiguration(PHASE_RUNGAINST_SRC_START);
				Map<String, String> configs = MojoUtil.getAllValues(config);
				String environmentName = configs.get(ENVIRONMENT_NAME);
				reader = startServer(environmentName);
			}
		} catch (PhrescoException e) {
				S_LOGGER.error("Entered into catch block of Build.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the runAgainstSource process");
		}catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Build.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the runAgainstSource process");
		}

		return reader;

	}
	
	
	public BufferedReader stopServer() throws PhrescoException {
		
		BufferedReader reader =null;
		
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Build.handleStopServer()");
		}
		
		try {
			reader = handleStopServer(false);
		} catch (PhrescoException e) {
				S_LOGGER.error("Entered into catch block of MavenFunctions.stopServer()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the stopServer process");
		}catch (Exception e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the runAgainstSource process");
		}

		return reader;
	}
	
	
	public BufferedReader restartServer() throws IOException, PhrescoException  {
		
		BufferedReader reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.restartServer()");
		}
		
		try {
			handleStopServer(true);
			BufferedReader compileReader = compileSource();
			String line = compileReader.readLine();
			while (line != null && !line.startsWith("[INFO] BUILD FAILURE")) {
				line = compileReader.readLine();
			}
			
			if (line != null && line.startsWith("[INFO] BUILD FAILURE")) {
				reader = new BufferedReader(new FileReader(getLogFilePath()));
			} else {
				reader = startServer(null);
			}
		} catch (PhrescoException e) {
			e.printStackTrace();
		}catch (Exception e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.restartServer process");
		}

		return reader;
	}
	
	
	public BufferedReader performanceTest() throws PhrescoException {
		
		BufferedReader reader=null;
		
    	try {
    		FileOutputStream fop = null;
    		ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
    		ProjectInfo projectInfo = getProjectInfo();
    		ApplicationInfo applicationInfo = getApplicationInfo();
    		MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_PERFORMANCE_TEST)));
    		persistValuesToXml(mojo, PHASE_PERFORMANCE_TEST);

    		//To get maven build arguments
    		List<Parameter> parameters = getMojoParameters(mojo, PHASE_PERFORMANCE_TEST);
    		List<String> buildArgCmds = getMavenArgCommands(parameters);
    		buildArgCmds.add(HYPHEN_N);
    		String workingDirectory = getAppDirectoryPath(applicationInfo);  
    		
    		//
            performanceJsonWriter();    			
    		reader = applicationManager.performAction(projectInfo, ActionType.PERFORMANCE_TEST, buildArgCmds, workingDirectory);
    		
    	} catch (PhrescoException e) {
    		throw new PhrescoException(e);
    	}

    	return reader;
    }
	
	
	public BufferedReader loadTest() throws PhrescoException {
		
		BufferedReader reader = null;
    	if (isDebugEnabled) {
	        S_LOGGER.debug("Entering Method MavenFunctions.runLoadTest()");
	    } 
    	try {
    		ApplicationInfo appInfo = getApplicationInfo();
	        StringBuilder workingDirectory = new StringBuilder(getAppDirectoryPath(appInfo));
	        MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_LOAD_TEST)));
            persistValuesToXml(mojo, PHASE_LOAD_TEST);
            List<Parameter> parameters = getMojoParameters(mojo, PHASE_LOAD_TEST);
            List<String> buildArgCmds = getMavenArgCommands(parameters);
            buildArgCmds.add(HYPHEN_N);
            ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
            reader = applicationManager.performAction(getProjectInfo(), ActionType.LOAD_TEST, buildArgCmds, workingDirectory.toString());
    	} catch(PhrescoException e) {
	    		S_LOGGER.error("Entered into catch block of MavenFunctions.LoadTest()"+ FrameworkUtil.getStackTraceAsString(e));
	    		throw new PhrescoException("Exception occured in the MavenFunctions.LoadTest process");
    	}
    	return reader;
    }
	
	public BufferedReader minification() throws PhrescoException {
		
		BufferedReader reader = null;
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = getApplicationInfo();
			ProjectInfo projectInfo = getProjectInfo();
			String pomPath = Utility.getProjectHome() + File.separator + applicationInfo.getAppDirName() + File.separator + POM_FILE;
			PomProcessor pomProcessor = new PomProcessor(new File(pomPath));
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			List<Element> configList = new ArrayList<Element>();
			List<String> files = getMinifyFileNames();
			createExcludesTagInPom(doc, configList);
			if (Boolean.parseBoolean(getMinifyAll()) && CollectionUtils.isEmpty(files)) { // Only Minify all is selected
				configList.add(createElement(doc, POM_OUTPUTDIR, POM_SOURCE_DIRECTORY));
			} else if (CollectionUtils.isNotEmpty(files)) {
				String dynamicIncludeDir = "";
				if (Boolean.parseBoolean(getMinifyAll())) {//if Minify all is selected
					dynamicIncludeDir = POM_SOURCE_DIRECTORY;
					configList.add(createElement(doc, POM_OUTPUTDIR, POM_SOURCE_DIRECTORY));
				} else {//if Minify all not is selected
					dynamicIncludeDir = POM_OUTPUT_DIRECTORY;
					configList.add(createElement(doc, POM_OUTPUTDIR, POM_OUTPUT_DIRECTORY));
				}
				createAggregationTagInPom(applicationInfo, doc, configList, files, dynamicIncludeDir);
			}
			pomProcessor.addConfiguration(MINIFY_PLUGIN_GROUPID, MINIFY_PLUGIN_ARTFACTID, configList);
			pomProcessor.save();
			
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			
			reader = applicationManager.performAction(projectInfo, ActionType.MINIFY, null, workingDirectory);
		} catch (Exception e) {
				S_LOGGER.error("Entered into catch block of MavenFunctions.minification()"+ FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the MavenFunctions.minification process");
		}
		
		return reader;
	}
	
	public BufferedReader startHub() throws PhrescoException {
		
		BufferedReader reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.startHub()");
		}

		try {
			ApplicationInfo appInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_START_HUB)));
			persistValuesToXml(mojo, PHASE_START_HUB);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = getProjectInfo();
			String workingDirectory = getAppDirectoryPath(appInfo);
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_START_HUB);
            List<String> buildArgCmds = getMavenArgCommands(parameters);
            buildArgCmds.add(HYPHEN_N);
			reader = applicationManager.performAction(projectInfo, ActionType.START_HUB, buildArgCmds, workingDirectory);
		} catch (PhrescoException e) {
	    		S_LOGGER.error("Entered into catch block of MavenFunctions.startHub()"+ FrameworkUtil.getStackTraceAsString(e));
	    		throw new PhrescoException("Exception occured in the MavenFunctions.startHub process");
		}

		return reader;
	}
	
	
	public BufferedReader startNode() throws PhrescoException {
		
		BufferedReader reader = null;
		if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.startNode()");
        }
		
        try {
        	ApplicationInfo appInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_START_NODE)));
			persistValuesToXml(mojo, PHASE_START_NODE);
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_START_NODE);
            List<String> buildArgCmds = getMavenArgCommands(parameters);
            buildArgCmds.add(HYPHEN_N);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = getProjectInfo();
			String workingDirectory = getAppDirectoryPath(appInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.START_NODE, buildArgCmds, workingDirectory);
        } catch (PhrescoException e) {
	    		S_LOGGER.error("Entered into catch block of MavenFunctions.startNode()"+ FrameworkUtil.getStackTraceAsString(e));
	    		throw new PhrescoException("Exception occured in the MavenFunctions.startNode process");
        }
        
        return reader;
    }
	
	
	public BufferedReader runFunctionalTest() throws PhrescoException {
		
		BufferedReader reader = null;
		
	    if (isDebugEnabled) {
	        S_LOGGER.debug("Entering Method MavenFunctions.runFunctionalTest()");
	    }
	    
	    try {
	        ApplicationInfo appInfo = getApplicationInfo();
	        StringBuilder workingDirectory = new StringBuilder(getAppDirectoryPath(appInfo));
	        if (StringUtils.isNotEmpty(getProjectModule())) {
	            workingDirectory.append(File.separator);
	            workingDirectory.append(getProjectModule());
	        }
	        MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_FUNCTIONAL_TEST)));
	        FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
            String seleniumToolType = frameworkUtil.getSeleniumToolType(appInfo);
	        persistValuesToXml(mojo, PHASE_FUNCTIONAL_TEST + HYPHEN + seleniumToolType);
	        List<Parameter> parameters = getMojoParameters(mojo, PHASE_FUNCTIONAL_TEST + HYPHEN + seleniumToolType);
            List<String> buildArgCmds = getMavenArgCommands(parameters);
            buildArgCmds.add(HYPHEN_N);
	        ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
	        reader = applicationManager.performAction(getProjectInfo(), ActionType.FUNCTIONAL_TEST, buildArgCmds, workingDirectory.toString());
	        
	    } catch (PhrescoException e) {
	    	
	    		S_LOGGER.error("Entered into catch block of Quality.runFunctionalTest()"+ FrameworkUtil.getStackTraceAsString(e));
	    		throw new PhrescoException("Exception occured in the MavenFunctions.startNode process");
	    		
	        //return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_RUN));
	    } catch (PhrescoPomException e) {
	    	
                S_LOGGER.error("Entered into catch block of Quality.runFunctionalTest()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Exception occured in the MavenFunctions.startNode process");
            //return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_QUALITY_FUNCTIONAL_RUN));
        }

	    return reader;
	}
	
	
	public BufferedReader stopHub() throws PhrescoException {
		
		BufferedReader reader = null;
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.stopHub()");
        }

        try {
            ApplicationInfo appInfo = getApplicationInfo();
            ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
            ProjectInfo projectInfo = getProjectInfo();
            String workingDirectory = getAppDirectoryPath(appInfo);
            List<String> buildArgCmds = new ArrayList<String>();
            buildArgCmds.add(HYPHEN_N);
            reader = applicationManager.performAction(projectInfo, ActionType.STOP_HUB, buildArgCmds, workingDirectory);
        } catch (PhrescoException e) {
                S_LOGGER.error("Entered into catch block of MavenFunctions.stopHub()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.stopHub()");
            //return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_STOP_HUB));
        }

        return reader;
    }
	
	
	public BufferedReader stopNode() throws PhrescoException {
		
		BufferedReader reader = null;
		
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.stopNode()");
        }

        try {
            ApplicationInfo appInfo = getApplicationInfo();
            ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
            ProjectInfo projectInfo = getProjectInfo();
            String workingDirectory = getAppDirectoryPath(appInfo);
            List<String> buildArgCmds = new ArrayList<String>();
            buildArgCmds.add(HYPHEN_N);
            reader = applicationManager.performAction(projectInfo, ActionType.STOP_NODE, buildArgCmds, workingDirectory);
        } catch (PhrescoException e) {
                S_LOGGER.error("Entered into catch block of MavenFunctions.stopNode()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.stopNode()");
            //return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_STOP_NODE));
        }

        return reader;
    }
	
	
	public ActionResponse checkForHub(ActionResponse response) throws PhrescoException {
	    if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.checkForHub()");
        }
	    
	    try {
	        HubConfiguration hubConfig = getHubConfig();
            if (hubConfig != null) {
                String host = hubConfig.getHost();
                int port = hubConfig.getPort();
                setConnectionAlive(Utility.isConnectionAlive(HTTP_PROTOCOL, host, port));
                response.setStatus(ActionServiceConstant.SUCCESS);
				response.setService_exception("");
				response.setConnectionAlive(isConnectionAlive());
            }
        } catch (Exception e) {
        		response.setStatus(ERROR);
        		response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
        		response.setConnectionAlive(false);
                S_LOGGER.error("Entered into catch block of MavenFunctions.checkForHub()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.checkForHub()");
        }
	    
	    return response;
	}
	
	
	public ActionResponse checkForNode(ActionResponse response) throws PhrescoException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.checkForNode()");
        }
        
        BufferedReader reader = null;
        try {
            FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
            String functionalTestDir = frameworkUtil.getFunctionalTestDir(getApplicationInfo());
            StringBuilder sb = new StringBuilder(getApplicationHome());
            sb.append(functionalTestDir)
			.append(File.separator)
			.append(Constants.NODE_CONFIG_JSON);
            File hubConfigFile = new File(sb.toString());
            Gson gson = new Gson();
            reader = new BufferedReader(new FileReader(hubConfigFile));
            NodeConfiguration nodeConfiguration = gson.fromJson(reader, NodeConfiguration.class);
            if (nodeConfiguration != null) {
                String host = nodeConfiguration.getConfiguration().getHost();
                int port = nodeConfiguration.getConfiguration().getPort();
                setConnectionAlive(Utility.isConnectionAlive(HTTP_PROTOCOL, host, port));
                response.setStatus(ActionServiceConstant.SUCCESS);
				response.setService_exception("");
				response.setConnectionAlive(isConnectionAlive());
            }
        } catch (PhrescoException e) {
        		response.setStatus(ERROR);
        		response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
        		response.setConnectionAlive(false);
                S_LOGGER.error("Entered into catch block of MavenFunctions.checkForNode()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.checkForNode()");
            //return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_NODE_CONNECTION));
            
        } catch (PhrescoPomException e) {
        		response.setStatus(ERROR);
        		response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
        		response.setConnectionAlive(false);
                S_LOGGER.error("Entered into catch block of MavenFunctions.checkForNode()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.checkForNode()");
            //return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_QUALITY_FUNCTIONAL_NODE_CONNECTION));
            
        } catch (FileNotFoundException e) {
        		response.setStatus(ERROR);
        		response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
        		response.setConnectionAlive(false);
                S_LOGGER.error("Entered into catch block of MavenFunctions.checkForNode()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.checkForNode()");
            //return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_QUALITY_FUNCTIONAL_NODE_CONNECTION));
            
        } finally {
            Utility.closeStream(reader);
        }
        
        return response;
    }
	
	
	public BufferedReader showStartedHubLog() throws PhrescoException {
		BufferedReader reader = null;
	    if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.showStartedHubLog()");
        }
	    
	    try {
	        HubConfiguration hubConfig = getHubConfig();
	        String host = hubConfig.getHost();
	        int port = hubConfig.getPort();
	        String str = "Hub is already running in " + host + ":" + port;
	        InputStream is = new ByteArrayInputStream(str.getBytes());
	        reader = new BufferedReader(new InputStreamReader(is));
        } catch (PhrescoException e) {
                S_LOGGER.error("Entered into catch block of MavenFunctions.showStartedHubLog()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.stopNode()"+ FrameworkUtil.getStackTraceAsString(e));
                //return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_HUB_LOG));
        }
	    
	    return reader;
	}
	
	
	public BufferedReader showStartedNodeLog() throws PhrescoException {
		BufferedReader reader = null;
		
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method MavenFunctions.showStartedNodeLog()");
        }
        
        try {
            NodeConfiguration nodeConfig = getNodeConfig();
            NodeConfig configuration = nodeConfig.getConfiguration();
            String host = configuration.getHost();
            int port = configuration.getPort();
            String str = "Node is already running in " + host + COLON + port;
            InputStream is = new ByteArrayInputStream(str.getBytes());
            reader = new BufferedReader(new InputStreamReader(is));
            
        } catch (PhrescoException e) {
                S_LOGGER.error("Entered into catch block of MavenFunctions.showStartedNodeLog()"+ FrameworkUtil.getStackTraceAsString(e));
                throw new PhrescoException("Entered into catch block of MavenFunctions.showStartedNodeLog()"+ FrameworkUtil.getStackTraceAsString(e));
            //return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_NODE_LOG));
        }
        
        return reader;
    }
	
	public BufferedReader generateSiteReport() throws PhrescoException {
		BufferedReader reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions.generateReport()");
		}
		
		try {
			ActionType actionType = null;
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			ProjectInfo projectInfo = projectManager.getProject(getProjectId(), getCustomerId());
			actionType = ActionType.SITE_REPORT;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = getApplicationInfo();
			String appDirectoryPath = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, actionType, null, appDirectoryPath);
		} catch (PhrescoException e) {
				S_LOGGER.error("Entered into catch block of MavenFunctions.generateSiteReport()"
					+ FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("exception occured in the generate site report");
		}
		return reader;
	}
	
	
	public BufferedReader ciSetup() throws PhrescoException {
		BufferedReader reader = null;
		
		if (isDebugEnabled) {
		S_LOGGER.debug("Entering Method  MavenFunctions CI setup()");
		}
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ProjectInfo projectInfo = getProjectInfo();
			if (isDebugEnabled) {
				S_LOGGER.debug("Jenkins Home " + Utility.getJenkinsHome().toString());
			}

			List<String> buildArgCmds = new ArrayList<String>(1);
			buildArgCmds.add(SKIP_TESTS);
			String workingDirectory = Utility.getJenkinsHome();
			reader = ciManager.setup(projectInfo, ActionType.INSTALL, buildArgCmds , workingDirectory);
			
		} catch (PhrescoException e) {
				S_LOGGER.error("Entered into catch block of MavenFunctions CI setup()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("exception occured in the CI Setup ");
		}
		return reader;
	}
	
	
	public BufferedReader ciStart() throws PhrescoException {
		BufferedReader reader = null;
		
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions .startJenkins()");
		}
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ProjectInfo projectInfo = getProjectInfo();
			if (isDebugEnabled) {
				S_LOGGER.debug("Jenkins Home " + Utility.getJenkinsHome().toString());
			}
			
			List<String> buildArgCmds = null;
			String workingDirectory = Utility.getJenkinsHome();

			reader = ciManager.start(projectInfo, ActionType.START, buildArgCmds , workingDirectory);
			
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of CI.startJenkins()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("exception occured in the CI Start up ");
		}
		return reader;
	}
	
	public BufferedReader ciStop() throws PhrescoException {
		
		BufferedReader reader=null;		
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions .stopJenkins()");
		}
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			ProjectInfo projectInfo = getProjectInfo();
			if (isDebugEnabled) {
				S_LOGGER.debug("Jenkins Home " + Utility.getJenkinsHome().toString());
			}
			
			List<String> buildArgCmds = null;
			String workingDirectory = Utility.getJenkinsHome();
			reader = ciManager.stop(projectInfo, ActionType.STOP, buildArgCmds , workingDirectory);
			
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of CI.stopJenkins()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("exception occured in the CI stop functionality");
		}
		return reader;
	}
	
	
	public ActionResponse printAsPdf (String username,ActionResponse response) throws PhrescoException {
        S_LOGGER.debug("Entering Method Quality.printAsPdf()");
        try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = getProjectInfo();
			
			ApplicationInfo applicationInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_PDF_REPORT)));
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_PDF_REPORT);
			//String sonarUrl = (String) getReqAttribute(REQ_SONAR_URL);
	        if (CollectionUtils.isNotEmpty(parameters)) {
	            for (Parameter parameter : parameters) {
	            	String key = parameter.getKey();
	            	if (REQ_REPORT_TYPE.equals(key)) {
	            		parameter.setValue(reportDataType);
	            	} else if (REQ_TEST_TYPE.equals(key)) {
	            		if (StringUtils.isEmpty(fromPage)) {
	            			setFromPage(FROMPAGE_ALL);
	            		}
	            		parameter.setValue(getFromPage());
	            	} /*else if (REQ_SONAR_URL.equals(key)) {
	            		parameter.setValue(sonarUrl);
	            	}*/ else if ("logo".equals(key)) {
	            		parameter.setValue(getLogoImageString(username));
	            	} else if ("theme".equals(key)) {
	            		parameter.setValue(getThemeColorJson(username));
	            	} else if (REQ_REPORT_NAME.equals(key)) {
	            		parameter.setValue(pdfName);
	            	}
	            }
	        }
	        mojo.save();
	        
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			BufferedReader reader = applicationManager.performAction(projectInfo, ActionType.PDF_REPORT, buildArgCmds, workingDirectory);
			String line;
			line = reader.readLine();
			while (line != null) {
				line = reader.readLine();
				System.out.println("Restart Start Console : " + line);
			}
        } catch (Exception e) {
        	S_LOGGER.error("Entered into catch block of ActionFunction.printAsPdf()"+ e);
        	response.setStatus(ERROR);
            response.setLog(ERROR);
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
        	throw new PhrescoException("exception occured in the Print As PDF functionality");
        }
        response.setStatus(ActionServiceConstant.SUCCESS);
        response.setLog(ActionServiceConstant.SUCCESS);
        return response;
    }
	
	 protected String getThemeColorJson(String username) throws PhrescoException {
	    	String themeJsonStr = "";
	    	try {
	    		//User user = (User) getSessionAttribute(SESSION_USER_INFO);
	    		User user = (User)getServiceManager(username).getUserInfo();
	    		List<Customer> customers = user.getCustomers();
	    		for (Customer customer : customers) {
					if (customer.getId().equals(getCustomerId())) {
						Map<String, String> frameworkTheme = customer.getFrameworkTheme();
						if (frameworkTheme != null) {
							Gson gson = new Gson();
							themeJsonStr = gson.toJson(frameworkTheme);
						}
					}
				}
			} catch (Exception e) {
				throw new PhrescoException(e);
			}
	    	return themeJsonStr;
	    }
	
    protected String getLogoImageString(String username) throws PhrescoException {
    	String encodeImg = "";
    	try {
        	InputStream fileInputStream = null;
    		//fileInputStream = getServiceManager().getIcon(getCustomerId());
        	fileInputStream = getServiceManager(username).getIcon(getCustomerId());
    		if (fileInputStream != null) {
        		byte[] imgByte = null;
        		imgByte = IOUtils.toByteArray(fileInputStream);
        	    byte[] encodedImage = Base64.encodeBase64(imgByte);
                encodeImg = new String(encodedImage);
    		}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    	return encodeImg;
    }
	
	private NodeConfiguration getNodeConfig() throws PhrescoException {
        BufferedReader reader = null;
        NodeConfiguration nodeConfig = null;
        try {
            FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
            String functionalTestDir = frameworkUtil.getFunctionalTestDir(getApplicationInfo());
            StringBuilder sb = new StringBuilder(getApplicationHome());
            sb.append(functionalTestDir)
            .append(File.separator)
            .append(Constants.NODE_CONFIG_JSON);
            File hubConfigFile = new File(sb.toString());
            Gson gson = new Gson();
            reader = new BufferedReader(new FileReader(hubConfigFile));
            nodeConfig = gson.fromJson(reader, NodeConfiguration.class);
        } catch (PhrescoException e) {
            throw new PhrescoException(e);
        } catch (PhrescoPomException e) {
            throw new PhrescoException(e);
        } catch (FileNotFoundException e) {
            throw new PhrescoException(e);
        } finally {
            Utility.closeReader(reader);
        }

        return nodeConfig;
    }
	
	private HubConfiguration getHubConfig() throws PhrescoException {
	    BufferedReader reader = null;
	    HubConfiguration hubConfig = null;
	    try {
	        FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
	        String functionalTestDir = frameworkUtil.getFunctionalTestDir(getApplicationInfo());
	        StringBuilder sb = new StringBuilder(getApplicationHome());
	        sb.append(functionalTestDir)
	        .append(File.separator)
	        .append(Constants.HUB_CONFIG_JSON);
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
	 * To create Aggregations tag in Pom.xml with minification details
	 * @param applicationInfo
	 * @param doc
	 * @param configList
	 * @param files
	 * @param dynamicIncludeDir
	 */
	private void createAggregationTagInPom(ApplicationInfo applicationInfo, Document doc, List<Element> configList, List<String> files,
			String dynamicIncludeDir) throws PhrescoException {
		Element aggregationsElement = doc.createElement(POM_AGGREGATIONS);
		for (String file : files) {
			String newFileName = ""; 
			String extension = "";
			String csvJsFile = getHttpRequest().getParameter(file);
			List<String> chosenFiles = Arrays.asList(csvJsFile.split(CSV_PATTERN));
			Element agrigationElement = appendChildElement(doc, aggregationsElement, POM_AGGREGATION, null);
			appendChildElement(doc, agrigationElement, POM_INPUTDIR, dynamicIncludeDir);
			Element includesElement = doc.createElement(POM_INCLUDES);
			for (String chosenFile : chosenFiles) {
				int lastDot = chosenFile.lastIndexOf(DOT);
				newFileName = chosenFile.substring(0, lastDot);
				extension = chosenFile.substring(lastDot + 1, chosenFile.length());
				appendChildElement(doc, includesElement, POM_INCLUDE, "**/" + newFileName + HYPEN_MIN_DOT + extension);
				agrigationElement.appendChild(includesElement);
			}
			String location = getHttpRequest().getParameter(file + MINIFY_FILE_LOCATION);
			String[] splitted = location.split(applicationInfo.getAppDirName());
			String minificationDir = splitted[1];
			appendChildElement(doc, agrigationElement, POM_OUTPUT, MINIFY_OUTPUT_DIRECTORY + minificationDir + file + DOT_MIN_DOT + extension);
		}
		configList.add(aggregationsElement);
	}
	
	private void createExcludesTagInPom(Document doc, List<Element> configList) throws PhrescoException {
		configList.add(createElement(doc, POM_SOURCEDIR, POM_SOURCE_DIRECTORY));
		configList.add(createElement(doc, POM_FORCE, POM_VALUE_TRUE));
		configList.add(createElement(doc, POM_JS_WARN, POM_VALUE_FALSE));
		configList.add(createElement(doc, POM_LINE_BREAK, POM_LINE_MAX_COL_COUNT));
		
		Element excludesElement = doc.createElement(POM_EXCLUDES);
		appendChildElement(doc, excludesElement, POM_EXCLUDE, POM_EXCLUDE_JS);
		appendChildElement(doc, excludesElement, POM_EXCLUDE, POM_EXCLUDE_MIN_JS);
		appendChildElement(doc, excludesElement, POM_EXCLUDE, POM_EXCLUDE_MINIFIED_JS);
		appendChildElement(doc, excludesElement, POM_EXCLUDE, POM_EXCLUDE_MIN_CSS);
		appendChildElement(doc, excludesElement, POM_EXCLUDE, POM_EXCLUDE_MINIFIED_CSS);
		configList.add(excludesElement);
	}
	
	private Element createElement(Document doc, String elementName, String textContent) throws PhrescoException {
		Element element = doc.createElement(elementName);
		if (StringUtils.isNotEmpty(textContent)) {
			element.setTextContent(textContent);
		}
		return element;
	}
	
	private Element appendChildElement(Document doc, Element parent, String elementName, String textContent) throws PhrescoException {
		Element childElement = createElement(doc, elementName, textContent);
		parent.appendChild(childElement);
		return childElement;
	}
	
	public String performanceJsonWriter() throws PhrescoException {
		
		FileWriter fw = null;
		
		try {
			if(StringUtils.isNotEmpty(getTestAgainst())) {
				ApplicationInfo applicationInfo = getApplicationInfo();
				PomProcessor processor = new PomProcessor(getPOMFile(applicationInfo.getAppDirName()));					
		        String performTestDir = processor.getProperty(POM_PROP_KEY_PERFORMANCETEST_DIR);	        
				FileOutputStream fop;
				boolean success = false;
				if(getIsFromCI().equalsIgnoreCase("true")) {				
					StringBuilder filepath = new StringBuilder(Utility.getProjectHome())
					.append(applicationInfo.getAppDirName())
					.append(performTestDir)
					.append(File.separator)
					.append(getTestAgainst())
					.append(File.separator)
					.append(Constants.FOLDER_JSON);
//					.append(File.separator)
//					.append("CITemp");					
					success = new File(filepath.toString()).mkdirs();
					
					filepath.append(File.separator)
					.append(getTestName())
					.append(DOT_JSON);
					File file = new File(filepath.toString());
					fop = new FileOutputStream(file);
					if (!file.exists()) {
						file.createNewFile();
					}
					byte[] contentInBytes = getResultJson().getBytes();				 
					fop.write(contentInBytes);
					fop.flush();
					fop.close();				
										
					StringBuilder infofilepath = new StringBuilder(Utility.getProjectHome())
					.append(applicationInfo.getAppDirName())
					.append(performTestDir)
					.append(File.separator)
					.append(getTestAgainst())
					.append(File.separator)
					.append(Constants.FOLDER_JSON)				
//					.append(File.separator)
//					.append("CITemp")
					.append(File.separator)
					.append("ci")
					.append(".info");					
					file = new File(infofilepath.toString());					
					if (!file.exists()) {
						file.createNewFile();
						fw = new FileWriter(file);
						fw.write(getTestName()+DOT_JSON);
					} else {
						fw = new FileWriter(infofilepath.toString(),true);
						StringBuilder jsonFileName = new StringBuilder()
						.append(",")
						.append(getTestName());
						fw.write(jsonFileName.toString()+DOT_JSON);						
					}
					
				} else {					
					StringBuilder filepath = new StringBuilder(Utility.getProjectHome())
					.append(applicationInfo.getAppDirName())
					.append(performTestDir)
					.append(File.separator)
					.append(getTestAgainst())
					.append(File.separator)
					.append(Constants.FOLDER_JSON);			
					if (new File(filepath.toString()).exists()) {
						filepath.append(File.separator)
						.append(getTestName())
						.append(DOT_JSON);				
						File file = new File(filepath.toString());
						fop = new FileOutputStream(file);
						if (!file.exists()) {
							file.createNewFile();
						}
						byte[] contentInBytes = getResultJson().getBytes();				 
						fop.write(contentInBytes);
						fop.flush();
						fop.close();				
					}
				}				
			}			
		} catch (Exception e) {			
			throw new PhrescoException(e);
		} finally {
			Utility.closeStream(fw);
		}
		return SUCCESS;
	}
	
    private File getPOMFile(String appDirName) throws PhrescoException {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome())
        .append(appDirName)
        .append(File.separatorChar)
        .append(POM_NAME);
        return new File(builder.toString());
    }
	
	
	private BufferedReader handleStopServer(boolean readData) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Build.handleStopServer()");
		}
		
		BufferedReader reader = null;
		try {
			ApplicationInfo applicationInfo = getApplicationInfo();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(getProjectInfo(), ActionType.STOPSERVER, null, workingDirectory);
			if (readData) {
				while (StringUtils.isNotEmpty(reader.readLine())) {}
			}
			deleteLogFile();
		} catch (Exception e) {
				S_LOGGER.error("Entered into catch block of Build.handleStopServer()"
						+ FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the MavenFunctions.handleStopServer process");
		}
		
		return reader;
	}
	
	
	private BufferedReader compileSource() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Build.compileSource()");
		}
		
		BufferedReader reader = null;
		try {
			Commandline cl = new Commandline("mvn clean compile");
			String projectDir = Utility.getProjectHome() + getApplicationInfo().getAppDirName();
			cl.setWorkingDirectory(projectDir);
			Process process = cl.execute();
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			writeLog(reader);
			reader = new BufferedReader(new FileReader(getLogFilePath()));
		} catch (FileNotFoundException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.compileSource()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			throw new PhrescoException(e);
		} catch (CommandLineException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.compileSource()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			throw new PhrescoException(e);
		}
		
		return reader;
	}
	
	private void writeLog(BufferedReader in) throws PhrescoException {
		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			String logFolderPath = getLogFolderPath();
			File logfolder = new File(logFolderPath);
			if (!logfolder.exists()) {
				logfolder.mkdirs();
			}
			fstream = new FileWriter(getLogFilePath());
			out = new BufferedWriter(fstream);
			String line = "";
			while ((line = in.readLine()) != null) {
				out.write(line + "\n");
				out.flush();
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeWriter(out);
			try {
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	
	private BufferedReader startServer(String environmentName) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.startServer()");
		}
		String serverHost = "";
		String serverProtocol = "";
		int serverPort = 0;
		BufferedReader reader = null;
		try {
			ApplicationInfo applicationInfo = getApplicationInfo();
			if(StringUtils.isEmpty(environmentName)) {
				environmentName = readRunAgainstInfo();
			}
			List<com.photon.phresco.configuration.Configuration> configurations = getConfiguration(environmentName, Constants.SETTINGS_TEMPLATE_SERVER);
			if (CollectionUtils.isNotEmpty(configurations)) {
				for (com.photon.phresco.configuration.Configuration serverConfiguration : configurations) {
					serverHost = serverConfiguration.getProperties().getProperty(Constants.SERVER_HOST);
					serverProtocol = serverConfiguration.getProperties().getProperty(Constants.SERVER_PROTOCOL);
					serverPort = Integer.parseInt(serverConfiguration.getProperties()
							.getProperty(Constants.SERVER_PORT));
				}
			}
			// TODO: delete the server.log and create empty server.log file
			deleteLogFile();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = getProjectInfo();
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.RUNAGAINSTSOURCE, null, workingDirectory);
			boolean connectionStatus = Utility.isConnectionAlive(serverProtocol, serverHost, serverPort);

		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.startServer()" + FrameworkUtil.getStackTraceAsString(e));
			}
			throw new PhrescoException(e);
		}
		return reader;
	}
	
	public void deleteLogFile() throws PhrescoException {
		try {
			File logFile = new File(getLogFilePath());
			File infoFile = new File(getLogFolderPath() + File.separator + RUN_AGS_LOG_FILE);
			if (logFile.isFile() && logFile.exists()) {
				logFile.delete();
			} 
			if(infoFile.isFile() && infoFile.exists()) {
				infoFile.delete();
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	private String getLogFilePath() throws PhrescoException {
		StringBuilder builder = new StringBuilder(getLogFolderPath());
		builder.append(File.separator);
		builder.append(LOG_FILE);
		return builder.toString();
	}
	
	private String getLogFolderPath() throws PhrescoException {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(getApplicationInfo().getAppDirName());
		builder.append(File.separator);
		builder.append(DO_NOT_CHECKIN_DIR);
		builder.append(File.separator);
		builder.append(LOG_DIR);
		return builder.toString();
	}
	
	
	private List<com.photon.phresco.configuration.Configuration> getConfiguration(String environmentName,
			String type) throws PhrescoException {
		ConfigManager configManager = null;
		try {
			File settingsFile = new File(getGlobalSettingsPath());
			if (settingsFile.exists()) {
				configManager = new ConfigManagerImpl(settingsFile);
				List<com.photon.phresco.configuration.Configuration> settingsconfig = configManager.getConfigurations(
						environmentName, type);
				if (CollectionUtils.isNotEmpty(settingsconfig)) {
					return settingsconfig;
				}
			}
			configManager = new ConfigManagerImpl(new File(getAppConfigPath()));
			List<com.photon.phresco.configuration.Configuration> configurations = configManager.getConfigurations(
					environmentName, type);
			if (CollectionUtils.isNotEmpty(configurations)) {
				return configurations;
			}

		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		}
		return null;
	}
	
	public String getGlobalSettingsPath() throws PhrescoException {
    	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    	builder.append(getCustomerId());
		builder.append("-");
		builder.append(SETTINGS_INFO_FILE_NAME);
		return builder.toString();
    }
	
	public String getAppConfigPath() throws PhrescoException {
    	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    	builder.append(getApplicationInfo().getAppDirName());
    	builder.append(File.separator);
    	builder.append(FOLDER_DOT_PHRESCO);
    	builder.append(File.separator);
    	builder.append(CONFIGURATION_INFO_FILE_NAME);
    	return builder.toString();
    }
	
	
	private String readRunAgainstInfo() throws PhrescoException {
		String env = null;
		BufferedReader reader = null;
		try {
			ConfigurationInfo info = new ConfigurationInfo();
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(getApplicationInfo().getAppDirName());
			builder.append(File.separator);
			builder.append(FOLDER_DOT_PHRESCO);
			builder.append(File.separator);
			builder.append(RUN_AGS_ENV_FILE);
			File infoFile = new File(builder.toString());
			if(infoFile.exists()) {
				reader = new BufferedReader(new FileReader(builder.toString()));
				Gson gson = new Gson();
				info = gson.fromJson(reader, ConfigurationInfo.class);
				env = info.getEnvironment();
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeReader(reader);
		}

		return env;
	}


	
	protected ProjectInfo getProjectInfo() throws PhrescoException {
		ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
		ProjectInfo projectInfo = projectManager.getProject(getProjectId(), getCustomerId(), getAppId());
		return projectInfo;
	}
	
	 public ApplicationInfo getApplicationInfo() throws PhrescoException {
	        ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
	        return applicationManager.getApplicationInfo(getCustomerId(), getProjectId(), getAppId());
	    }
	 
	 public String getPhrescoPluginInfoFilePath(String goal) throws PhrescoException {
			StringBuilder sb = new StringBuilder(getApplicationHome());
			sb.append(File.separator);
			sb.append(FOLDER_DOT_PHRESCO);
			sb.append(File.separator);
			sb.append(PHRESCO_HYPEN);
			// when phase is CI, it have to take ci info file for update dependency
			if (PHASE_CI.equals(getPhase())) {
				sb.append(getPhase());
			} else if (StringUtils.isNotEmpty(goal) && goal.contains(FUNCTIONAL)) {
				sb.append(PHASE_FUNCTIONAL_TEST);
			} else if (PHASE_RUNGAINST_SRC_START.equals(goal)|| PHASE_RUNGAINST_SRC_STOP.equals(goal) ) {
				sb.append(PHASE_RUNAGAINST_SOURCE);
			} else {
				sb.append(goal);
			}
			sb.append(INFO_XML);

			return sb.toString();
		}
	 
	 public String getApplicationHome() throws PhrescoException {
	        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
	        builder.append(getApplicationInfo().getAppDirName());
	        return builder.toString();
	    }
	 
	 
	 String[] getReqParameterValues (String name) throws PhrescoException {
		 
		 return request.getParameterValues(name);
	 }
	 
	 String getReqParameter(String name) throws PhrescoException {
		 
		 return request.getParameter(name);
		 
	 }
	 
	 
	 protected void persistValuesToXml(MojoProcessor mojo, String goal) throws PhrescoException {
		    try {
		        List<Parameter> parameters = getMojoParameters(mojo, goal);
		        if (CollectionUtils.isNotEmpty(parameters)) {
		            for (Parameter parameter : parameters) {
		                StringBuilder csParamVal = new StringBuilder();
		                if (Boolean.parseBoolean(parameter.getMultiple())) {
		                	if (getReqParameterValues(parameter.getKey()) == null) {
		                		parameter.setValue("");
		                	} else {
		                		String[] parameterValues = getReqParameterValues(parameter.getKey());
			                    for (String parameterValue : parameterValues) {
			                        csParamVal.append(parameterValue);
			                        csParamVal.append(",");
			                    }
			                    String csvVal = csParamVal.toString();
			                    parameter.setValue(csvVal.toString().substring(0, csvVal.lastIndexOf(",")));
		                	}
		                } else if (TYPE_BOOLEAN.equalsIgnoreCase(parameter.getType())) {
		                    if (getReqParameter(parameter.getKey()) != null) {
		                        parameter.setValue(getReqParameter(parameter.getKey()));
		                    } else {
		                        parameter.setValue(Boolean.FALSE.toString());
		                    }
		                } else if (parameter.getType().equalsIgnoreCase(TYPE_MAP)) {
		                    List<Child> childs = parameter.getChilds().getChild();
		                    String[] keys = getReqParameterValues(childs.get(0).getKey());
		                    String[] values = getReqParameterValues(childs.get(1).getKey());
		                    Properties properties = new Properties();
		                    for (int i = 0; i < keys.length; i++) {
		                        properties.put(keys[i], values[i]);
		                    }
		                    StringWriter writer = new StringWriter();
		                    properties.store(writer, "");
		                    String value = writer.getBuffer().toString();
		                    parameter.setValue(value);
		                } else if (parameter.getType().equalsIgnoreCase(TYPE_PACKAGE_FILE_BROWSE)) {
		                	parameter.setValue(getSelectedFiles());
		                } else if(parameter.getType().equalsIgnoreCase(TYPE_PASSWORD)) {
		                	byte[] encodedPwd = Base64.encodeBase64(getReqParameter(parameter.getKey()).getBytes());
		                	String encodedString = new String(encodedPwd);
		                	parameter.setValue(encodedString);
		                } else {
		                    parameter.setValue(StringUtils.isNotEmpty(getReqParameter(parameter.getKey())) ? (String)getReqParameter(parameter.getKey()) : "");
		                }
		            }
		        }
		        mojo.save();
		        
		    } catch (IOException e) {
		        throw new PhrescoException(e);
		    }  catch (Exception e) {
		        throw new PhrescoException(e);
		    }
		    
		}

	 
	 protected List<Parameter> getMojoParameters(MojoProcessor mojo, String goal) throws PhrescoException {
			com.photon.phresco.plugins.model.Mojos.Mojo.Configuration mojoConfiguration = mojo.getConfiguration(goal);
			if (mojoConfiguration != null) {
			    return mojoConfiguration.getParameters().getParameter();
			}
			
			return null;
		}
	 
	 protected List<String> getMavenArgCommands(List<Parameter> parameters) throws PhrescoException {
			List<String> buildArgCmds = new ArrayList<String>();	
			if(CollectionUtils.isEmpty(parameters)) {
				return buildArgCmds;
			}
			for (Parameter parameter : parameters) {
				if (parameter.getPluginParameter()!= null && PLUGIN_PARAMETER_FRAMEWORK.equalsIgnoreCase(parameter.getPluginParameter())) {
					List<MavenCommand> mavenCommand = parameter.getMavenCommands().getMavenCommand();
					for (MavenCommand mavenCmd : mavenCommand) {
						if (StringUtils.isNotEmpty(parameter.getValue()) && parameter.getValue().equalsIgnoreCase(mavenCmd.getKey())) {
							buildArgCmds.add(mavenCmd.getValue());
						}
					}
				}
			}
			return buildArgCmds;
		}
	 
	 protected String getAppDirectoryPath(ApplicationInfo applicationInfo) throws PhrescoException {
	    	return Utility.getProjectHome() + applicationInfo.getAppDirName();
	    }
	 
	 protected ApplicationProcessor getApplicationProcessor(String username) throws PhrescoException {
	        ApplicationProcessor applicationProcessor = null;
	        try {
	        	//doLogin();
	            Customer customer = getServiceManager(username).getCustomer(getCustomerId());
	            RepoInfo repoInfo = customer.getRepoInfo();
	            StringBuilder sb = new StringBuilder(getApplicationHome())
	            .append(File.separator)
	            .append(Constants.DOT_PHRESCO_FOLDER)
	            .append(File.separator)
	            .append(Constants.APPLICATION_HANDLER_INFO_FILE);
	            MojoProcessor mojoProcessor = new MojoProcessor(new File(sb.toString()));
	            ApplicationHandler applicationHandler = mojoProcessor.getApplicationHandler();
	            if (applicationHandler != null) {
	                List<ArtifactGroup> plugins = setArtifactGroup(applicationHandler);
	                PhrescoDynamicLoader dynamicLoader = new PhrescoDynamicLoader(repoInfo, plugins);
	                applicationProcessor = dynamicLoader.getApplicationProcessor(applicationHandler.getClazz());
	            }
	        } catch (PhrescoException e) {
	        	e.printStackTrace();
	            throw new PhrescoException(e);
	        }catch (Exception e) {
	        	e.printStackTrace();
		        throw new PhrescoException(e);
		    }

	        return applicationProcessor;
	    }
	 
	 protected List<ArtifactGroup> setArtifactGroup(ApplicationHandler applicationHandler) throws PhrescoException {
	        List<ArtifactGroup> plugins = new ArrayList<ArtifactGroup>();
	        ArtifactGroup artifactGroup = new ArtifactGroup();
	        artifactGroup.setGroupId(applicationHandler.getGroupId());
	        artifactGroup.setArtifactId(applicationHandler.getArtifactId());
	        List<ArtifactInfo> artifactInfos = new ArrayList<ArtifactInfo>();
	        ArtifactInfo artifactInfo = new ArtifactInfo();
	        artifactInfo.setVersion(applicationHandler.getVersion());
	        artifactInfos.add(artifactInfo);
	        artifactGroup.setVersions(artifactInfos);
	        plugins.add(artifactGroup);
	        return plugins;
	    }
	 
	 
	 protected ServiceManager getServiceManager(String username) throws PhrescoException {
		this.serviceManager = RestBase.CONTEXT_MANAGER_MAP.get(username);
		return serviceManager;
		}
	 
	 
	 public HttpServletRequest getHttpRequest(){
			return request;
		}
		public boolean isConnectionAlive() {
			return connectionAlive;
		}
		public void setConnectionAlive(boolean connectionAlive) {
			this.connectionAlive = connectionAlive;
		}
		public String getProjectModule() {
			return projectModule;
		}
		public void setProjectModule(String projectModule) {
			this.projectModule = projectModule;
		}
		public List<String> getMinifyFileNames() {
			return minifyFileNames;
		}
		public void setMinifyFileNames(List<String> minifyFileNames) {
			this.minifyFileNames = minifyFileNames;
		}
		public String getMinifyAll() {
			return minifyAll;
		}
		public void setMinifyAll(String minifyAll) {
			this.minifyAll = minifyAll;
		}
		public String getIsFromCI() {
			return isFromCI;
		}
		public void setIsFromCI(String isFromCI) {
			this.isFromCI = isFromCI;
		}
		public String getResultJson() {
			return resultJson;
		}
		public void setResultJson(String resultJson) {
			this.resultJson = resultJson;
		}
		public String getTestAgainst() {
			return testAgainst;
		}
		public void setTestAgainst(String testAgainst) {
			this.testAgainst = testAgainst;
		}
		public String getTestName() {
			return testName;
		}
		public void setTestName(String testName) {
			this.testName = testName;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPhase() {
			return phase;
		}
		public void setPhase(String phase) {
			this.phase = phase;
		}
		public String getAppId() {
			return appId;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		public String getProjectId() {
			return projectId;
		}
		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}
		public String getSelectedFiles() {
			return selectedFiles;
		}
		public void setSelectedFiles(String selectedFiles) {
			this.selectedFiles = selectedFiles;
		}
		public String getFromPage() {
			return fromPage;
		}

		public void setFromPage(String fromPage) {
			this.fromPage = fromPage;
		}

		public String getReportDataType() {
			return reportDataType;
		}

		public void setReportDataType(String reportDataType) {
			this.reportDataType = reportDataType;
		}

		public String getPdfName() {
			return pdfName;
		}

		public void setPdfName(String pdfName) {
			this.pdfName = pdfName;
		}
	 
	 //------------ Will be replaced by reusing the login service coding .
	 
	 
	 //private static ServiceManager serviceManager = null;
	    
	   /* protected ServiceManager getServiceManager(String username) {
			return serviceManager;
		}
	    
	    
	    
	    protected User doLogin() {
	    	
	        try {
	        	Credentials credentials = new Credentials("demouser", "phresco");
	        	
	            String userName = credentials.getUsername();
	            String password = credentials.getPassword();
	            ServiceContext context = new ServiceContext();
	            FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
	            context.put(SERVICE_URL, configuration.getServerPath());
	            context.put(SERVICE_USERNAME, userName);
	            context.put(SERVICE_PASSWORD, password);
	            context.put(SERVICE_API_KEY, configuration.apiKey());
	        
	            serviceManager = ServiceClientFactory.getServiceManager(context);
	        } catch (PhrescoWebServiceException ex) {
	            throw new PhrescoWebServiceException(ex.getResponse());
	        } catch (PhrescoException e) {
	        	throw new PhrescoWebServiceException(e);
			}
	        return serviceManager.getUserInfo();
	    }*/
	    
	    
	    

}

