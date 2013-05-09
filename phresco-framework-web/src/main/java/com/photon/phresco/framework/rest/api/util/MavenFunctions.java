package com.photon.phresco.framework.rest.api.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
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
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.impl.ProjectManagerImpl;
import com.photon.phresco.framework.rest.api.ServiceManagerMap;
import com.photon.phresco.framework.rest.api.util.MavenServiceConstants;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Childs.Child;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.MavenCommands.MavenCommand;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.plugins.util.MojoUtil;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;



public class MavenFunctions implements Constants ,FrameworkConstants,MavenServiceConstants {
	

	
	private final String PHASE_RUNAGAINST_SOURCE = "run-against-source";
    public static final java.lang.String SERVICE_URL = "phresco.service.url";
    public static final java.lang.String SERVICE_USERNAME = "phresco.service.username";
    public static final java.lang.String SERVICE_PASSWORD = "phresco.service.password";
    public static final java.lang.String SERVICE_API_KEY = "phresco.service.api.key";
    public static final String SUCCESS = "success";
    
	private static final Logger S_LOGGER= Logger.getLogger(MavenFunctions.class);
	private static boolean isInfoEnabled = S_LOGGER.isInfoEnabled();
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
    
	String appId="";
	String projectId="";
	String customerId="";
	String selectedFiles="";
	String phase="";
	String username="";
	
	private String testAgainst = "";
	private String testName = "";
	private String resultJson = "";
	private String isFromCI = "";
	
	private List<String> minifyFileNames = null;
	


	private String minifyAll = "";
	
	
	/*String uniquekey="";*/
	
	HttpServletRequest request;
	
	private ServiceManager serviceManager = null;
	
	
	/*public String getUniquekey() {
		return uniquekey;
	}
	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}*/
	
	public HttpServletRequest getHttpRequest(){
		
		return request;
		
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
	
	public void prePopulateModelData(HttpServletRequest request) throws PhrescoException {
		
		
		try {
			
			this.request = request;
			
			
			/*if( !("".equalsIgnoreCase(request.getParameter(UNIQUE_KEY))) && (request.getParameter(UNIQUE_KEY) != null) && !("null".equalsIgnoreCase(request.getParameter(UNIQUE_KEY))) )
			{
				setUniquekey(request.getParameter(UNIQUE_KEY));	
			}
			else
			{
				throw new PhrescoException("No valid Unique Key is passed");
			}*/
			
			if( !("".equalsIgnoreCase(request.getParameter(APP_ID))) && (request.getParameter(APP_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(APP_ID))) )
			{
				setAppId(request.getParameter(APP_ID));	
			}
			else
			{
				throw new PhrescoException("No valid App Id Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(PROJECT_ID))) && (request.getParameter(PROJECT_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(PROJECT_ID))) )
			{
				setProjectId(request.getParameter(PROJECT_ID));
			}
			else
			{
				throw new PhrescoException("No valid Project Id Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) && (request.getParameter(CUSTOMER_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) )
			{
				setCustomerId(request.getParameter(CUSTOMER_ID));
			}
			else
			{
				throw new PhrescoException("No valid Customer Id Passed");
			}
			if( !("".equalsIgnoreCase(request.getParameter(USERNAME))) && (request.getParameter(USERNAME) != null) && !("null".equalsIgnoreCase(request.getParameter(USERNAME))) )
			{
				setUsername(request.getParameter(USERNAME));
			}
			else
			{
				throw new PhrescoException("No User Id passed");
			}
			
			setSelectedFiles(request.getParameter(SELECTED_FILES));
			
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	public void prePopulatePerformanceTestData(HttpServletRequest request) throws PhrescoException {
		
		
		try {
			
			
			if( !("".equalsIgnoreCase(request.getParameter(TEST_AGAINST))) && (request.getParameter(TEST_AGAINST) != null) && !("null".equalsIgnoreCase(request.getParameter(TEST_AGAINST))) )
			{
				setTestAgainst(request.getParameter(TEST_AGAINST));	
			}
			else
			{
				throw new PhrescoException("No valid TEST_AGAINST Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(TEST_NAME))) && (request.getParameter(TEST_NAME) != null) && !("null".equalsIgnoreCase(request.getParameter(TEST_NAME))) )
			{
				setTestName(request.getParameter(TEST_NAME));	
			}
			else
			{
				throw new PhrescoException("No valid TEST_NAME Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(RESULT_JSON))) && (request.getParameter(RESULT_JSON) != null) && !("null".equalsIgnoreCase(request.getParameter(RESULT_JSON))) )
			{
				setResultJson(request.getParameter(RESULT_JSON));	
			}
			else
			{
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
			
			if( (request.getParameterValues(MINIFY_FILE_NAMES) != null) )
			{
				String[] minify_file_names = request.getParameterValues(MINIFY_FILE_NAMES);
				List<String> minifyFileNames_local= new ArrayList<String>();
				for(String temp : minify_file_names)
				{
					minifyFileNames_local.add(temp);
				}
				if(minifyFileNames_local.size() != 0) {
				setMinifyFileNames(minifyFileNames_local);
				}
				else {
					throw new PhrescoException("No valid MINIFYFILENAMES Passed");
				}
			}
			else
			{
				throw new PhrescoException("No valid MINIFYFILENAMES Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(MINIFY_ALL))) && (request.getParameter(MINIFY_ALL) != null) && !("null".equalsIgnoreCase(request.getParameter(MINIFY_ALL))) )
			{
				setMinifyAll(request.getParameter(MINIFY_ALL));	
			}
			else
			{
				//To avoid parser exception incase minifyall is not selected.
				setMinifyAll("false");
			}
			
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	
		
	
		
	}
	
	
    public MavenResponse processRequest(HttpServletRequest request,String Command){

		boolean continue_status = true;
		MavenResponse response = new MavenResponse();
		
			try {
			
					try	{
			
						prePopulateModelData(request);
					
					}catch (PhrescoException e) {
			
						S_LOGGER.error(e.getMessage());
						continue_status = false;
						
						response.setStatus(ERROR);
						response.setLog("");
						response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
						response.setUniquekey("");
					}
					
					
					if(continue_status)
					{
							
						if (isDebugEnabled) {
							S_LOGGER.debug("APPP ID received :"+getAppId());
							S_LOGGER.debug("PROJECT ID received :"+getProjectId());
							S_LOGGER.debug("CUSTOMER ID received :"+getCustomerId());
							S_LOGGER.debug("USERNAME  received :"+getUsername());
						}
						
		            	try {
		            		
		            		BufferedReader server_logs=null;
		            		
		            		if(Command.equalsIgnoreCase(MavenServiceConstants.BUILDPROJECT)){
		            			
		            			server_logs = build(getUsername());
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.DEPLOYPROJECT)){
		            			
		            			server_logs = deploy();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.UNIT_TEST)){
		            			
		            			server_logs = runUnitTest();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.CODE_VALIDATE)){
		            			
		            			server_logs = codeValidate();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.RUN_AGAINST_SOURCE)){
		            			
		            			server_logs = runAgainstSource();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.STOP_SERVER)){
		            			
		            			server_logs = stopServer();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.RESTART_SERVER)){
		            			
		            			server_logs = restartServer();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.PERFORMANCE_TEST)){
		            			
		            			prePopulatePerformanceTestData(request);
		            			server_logs = performanceTest();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.LOAD_TEST)){
		            			
		            			server_logs = loadTest();
		            		}
		            		else if(Command.equalsIgnoreCase(MavenServiceConstants.MINIFY)){
		            			
		            			prePopulateMinificationData(request);
		            			server_logs = minification();
		            		}
		            		
		            		if(server_logs != null){
		            			
		            			 UUID uniqueKey = UUID.randomUUID();
							     String unique_key = uniqueKey.toString();
							     BufferMap.addBufferReader(unique_key, server_logs);
							     
							     	response.setStatus(STARTED);
									response.setLog(STARTED);
									response.setService_exception("");
									response.setUniquekey(unique_key);
		            		}
		            		else{
		            			
		            			throw new PhrescoException("No build logs obatined");
		            		}
		            		
						     
						     
						     /*String line="";
				                while((line=server_logs.readLine())!=null) {
				                	
				                	System.out.println(line);
				                }*/
						} catch (PhrescoException e) {
							
							response.setStatus(ERROR);
							response.setLog("");
							response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
							response.setUniquekey("");
							S_LOGGER.error(e.getMessage());
						} catch (Exception e) {
							
							response.setStatus(ERROR);
							response.setLog("");
							response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
							response.setUniquekey("");
							S_LOGGER.error(e.getMessage());
						}
						            	
					}
				            
	        
			}catch(Exception e){
				
				response.setStatus(ERROR);
				response.setLog("");
				response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
				response.setUniquekey("");
				S_LOGGER.error(e.getMessage());
		}
			
			return response;
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
			e.printStackTrace();
			throw new PhrescoException("Exception occured in the build process");
		}
		 catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException("Exception occured in the build process");
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
		this.serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(username);
		return serviceManager;
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

