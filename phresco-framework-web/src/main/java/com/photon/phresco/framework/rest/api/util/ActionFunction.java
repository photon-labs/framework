package com.photon.phresco.framework.rest.api.util;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.LockUtil;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.commons.model.Technology;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.LiquibaseDbDocInfo;
import com.photon.phresco.framework.model.LiquibaseDiffInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackCountInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackTagInfo;
import com.photon.phresco.framework.model.LiquibaseRollbackToDateInfo;
import com.photon.phresco.framework.model.LiquibaseStatusInfo;
import com.photon.phresco.framework.model.LiquibaseTagInfo;
import com.photon.phresco.framework.model.LiquibaseUpdateInfo;
import com.photon.phresco.framework.model.LockDetail;
import com.photon.phresco.framework.model.MinifyInfo;
import com.photon.phresco.framework.model.PerformanceUrls;
import com.photon.phresco.framework.rest.api.RestBase;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Childs.Child;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.MavenCommands.MavenCommand;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.HubConfiguration;
import com.photon.phresco.util.NodeConfig;
import com.photon.phresco.util.NodeConfiguration;
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;


public class ActionFunction extends RestBase implements Constants ,FrameworkConstants,ActionServiceConstant, ResponseCodes {

	private final String PHASE_RUNAGAINST_SOURCE = "run-against-source";
	public static final java.lang.String SERVICE_URL = "phresco.service.url";
	public static final java.lang.String SERVICE_USERNAME = "phresco.service.username";
	public static final java.lang.String SERVICE_PASSWORD = "phresco.service.password";
	public static final java.lang.String SERVICE_API_KEY = "phresco.service.api.key";
	public static final String SUCCESS = "success";

	private static final Logger S_LOGGER= Logger.getLogger(ActionFunction.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	private String customerId="";
	private String selectedFiles="";
	private String phase="";
	private String url="";
	private String username="";
	private String password="";
	private String message="";
	private String isFromCI = "";
	private List<String> minifyFileNames = null;
	private String projectModule = "";
	private String fromPage = "";
	private String pdfName = "";
	private String reportDataType = "";
	private String testBasis = "";
	private String testAction = "";
	boolean connectionAlive = false;
	private ServiceManager serviceManager = null;
	private String appDirName = "";
	private String module = "";
	HttpServletRequest request;

	private LiquibaseRollbackCountInfo liquibaseRollbackCountInfo;
	private LiquibaseRollbackToDateInfo liquibaseRollbackDateInfo;
	private LiquibaseRollbackTagInfo liquibaseRollbackTagInfo;
	private LiquibaseTagInfo liquibaseTagInfo;
	private LiquibaseStatusInfo liquibaseStatusInfo;
	private LiquibaseDbDocInfo liquibaseDbDocInfo;
	
	public void prePopulateModelData(HttpServletRequest request) throws PhrescoException {
		try {
			this.request = request;

			String customerId = request.getParameter(CUSTOMER_ID);
			if (StringUtils.isNotEmpty(customerId) && !"null".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) {
				setCustomerId(customerId);
			} else {
				throw new PhrescoException("No valid Customer Id Passed");
			}
			
			initAppDirName(request);
			initModuleName(request);
			setSelectedFiles(request.getParameter(SELECTED_FILES));
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	public void prePopulateBuildProcessData(HttpServletRequest request) throws PhrescoException {
		try {
			this.request = request;
			

			String customerId = request.getParameter(CUSTOMER_ID);
			if (StringUtils.isNotEmpty(customerId) && !"null".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) {
				setCustomerId(customerId);
			} else {
				throw new PhrescoException("No valid Customer Id Passed");
			}
			
			String url = request.getParameter(PROCESS_URL);
			if (StringUtils.isNotEmpty(url) && !"null".equalsIgnoreCase(request.getParameter(PROCESS_URL))) {
				setUrl(url);
			} else {
				throw new PhrescoException("No valid url Passed");
			}
			
			String username = request.getParameter("username");
			if (StringUtils.isNotEmpty(username) && !"null".equalsIgnoreCase(request.getParameter("username"))) {
				setUsername(username);
			} else {
				throw new PhrescoException("No valid username Passed");
			}
			
			String password = request.getParameter("password");
			if (StringUtils.isNotEmpty(password) && !"null".equalsIgnoreCase(request.getParameter("password"))) {
				setPassword(password);
			} else {
				throw new PhrescoException("No valid password Passed");
			}
			
			String message = request.getParameter("message");
			if (StringUtils.isNotEmpty(message) && !"null".equalsIgnoreCase(request.getParameter("message"))) {
				setMessage(message);
			} else {
				throw new PhrescoException("No valid message Passed");
			}
			
			initAppDirName(request);
			initModuleName(request);
//			setSelectedFiles(request.getParameter(SELECTED_FILES));
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	public void prePopulateBuildModelData(HttpServletRequest request) throws PhrescoException {
		try {
			this.request = request;

			String customerId = request.getParameter(CUSTOMER_ID);
			if (StringUtils.isNotEmpty(customerId) && !"null".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) {
				setCustomerId(customerId);
			} else {
				throw new PhrescoException("No valid Customer Id Passed");
			}

			String userName = request.getParameter(BuildServiceConstants.USERNAME);
			if (StringUtils.isNotEmpty(userName) && !"null".equalsIgnoreCase(userName)) {
				setUsername(userName);
			} else {
				throw new PhrescoException("No User Id passed");
			}
			
			initAppDirName(request);
			initModuleName(request);
			setSelectedFiles(request.getParameter(SELECTED_FILES));
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}

	public void prePopulateMinificationData(HttpServletRequest request) throws PhrescoException {
		try {
			String[] minify_file_names = request.getParameterValues(MINIFY_FILE_NAMES);
			if (!ArrayUtils.isEmpty(minify_file_names)) {
				List<String> minifyFileNames_local= new ArrayList<String>();
				for (String temp : minify_file_names) {
					minifyFileNames_local.add(temp);
				}
				if (minifyFileNames_local.size() != 0) {
					setMinifyFileNames(minifyFileNames_local);
				} else {
					throw new PhrescoException("No valid MINIFYFILENAMES Passed");
				}
			} else {
				throw new PhrescoException("No valid MINIFYFILENAMES Passed");
			}
			
			initAppDirName(request);
			initModuleName(request);
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}

	public void prePopulateFunctionalTestData(HttpServletRequest request) throws PhrescoException {
		try {
			String projectModule = request.getParameter(PROJECT_MODULE);
			if (StringUtils.isNotEmpty(projectModule) && !"null".equalsIgnoreCase(projectModule)) {
				setProjectModule(projectModule);	
			} else {
				//To avoid parser exception in case ProjectModule is not selected.
				setProjectModule("");
			}
			initAppDirName(request);
			initModuleName(request);
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}

	public void prePopulatePrintAsPDFData(HttpServletRequest request) throws PhrescoException {
		try {
			String reportDataType = request.getParameter(REPORT_DATA_TYPE);
			if (StringUtils.isNotEmpty(reportDataType) && !"null".equalsIgnoreCase(reportDataType)) {
				setReportDataType(reportDataType);	
			} else {
				throw new PhrescoException("No valid REPORT_DATA_TYPE param Passed");
			}

			String fromPage = request.getParameter(FROM_PAGE);
			if (StringUtils.isNotEmpty(fromPage) && !"null".equalsIgnoreCase(fromPage)) {
				setFromPage(fromPage);	
			} else {
				throw new PhrescoException("No valid FROM_PAGE param Passed");
			}

			String pdfName = request.getParameter(REQ_PDF_NAME);
			if (StringUtils.isNotEmpty(pdfName) && !"null".equalsIgnoreCase(pdfName)) {
				setPdfName(request.getParameter(REQ_PDF_NAME));	
			}
			
			initAppDirName(request);
			initModuleName(request);
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	public void prePopulateLiquibaseData(HttpServletRequest request) throws PhrescoException {
		try {
			this.request = request;
			initAppDirName(request);
			initModuleName(request);
		} catch (Exception e) {
			throw new PhrescoException(e.getMessage());
		}
	}
	
	private void initAppDirName(HttpServletRequest request) throws PhrescoException {
		String appDirName = request.getParameter(APPDIR);
		if (StringUtils.isNotEmpty(appDirName)) {
			setAppDirName(appDirName);
		} else {
			throw new PhrescoException("No valid App Directory Name Passed");
		}
	}
	
	private void initModuleName(HttpServletRequest request) throws PhrescoException {
		String moduleName = request.getParameter(MODULE_NAME);
		if (StringUtils.isNotEmpty(moduleName)) {
			setModule(moduleName);
		}
	}

	private void printLogs() {
		if (isDebugEnabled) {
			S_LOGGER.debug("CUSTOMER ID received :"+getCustomerId());
			S_LOGGER.debug("APP DIR NAME received :"+getAppDirName());
		}
	}
	
	private void printBuildLogs() {
		if (isDebugEnabled) {
			S_LOGGER.debug("CUSTOMER ID received :"+getCustomerId());
			S_LOGGER.debug("USERNAME  received :"+getUsername());
			S_LOGGER.debug("APP DIR NAME received :"+getAppDirName());
		}
	}

	private ActionResponse generateResponse(BufferedInputStream server_logs, String unique_key) {
		ActionResponse response = new ActionResponse();
//		UUID uniqueKey = UUID.randomUUID();
//		String unique_key = uniqueKey.toString();
		BufferMap.addBufferReader(unique_key, server_logs);

		response.setStatus(STARTED);
		response.setLog(STARTED);
		response.setService_exception("");
		response.setUniquekey(unique_key);

		return response;
	}

	public ActionResponse build(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs = null;
		String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = build(unique_key, displayName, getModule());
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No build logs obtained");
		}
	}


	public ActionResponse deploy(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = deploy(unique_key, displayName, getModule());
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No Deploy logs obtained");
		}
	}

	public ActionResponse processBuild(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = processBuild();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No processBuild logs obtained");
		}
	}
	
	public ActionResponse runUnitTest(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = runUnitTest(unique_key, displayName, getModule());
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No unit test logs obtained");
		}
	}
	
	public ActionResponse runIntegrationTest(HttpServletRequest request) throws PhrescoException {
		this.request = request;
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		String projectCode = request.getParameter("projectCode");;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = runIntegrationTest(unique_key, displayName, projectCode);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No integration test logs obtained");
		}
	}

	public ActionResponse runComponentTest(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = runComponentTest(unique_key, displayName);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No component test logs obtained");
		}
	}

	public ActionResponse codeValidate(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = codeValidate(unique_key, displayName, getModule());
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No code validate logs obtained");
		}
	}

	public ActionResponse runAgainstSource(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = runAgainstSource(unique_key, displayName);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No run against source logs obtained");
		}
	}

	public ActionResponse stopServer(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = stopServer();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No stopServer logs obtained");
		}
	}
	
	public ActionResponse liquibaseDbdoc(LiquibaseDbDocInfo liquibaseDbDocInfo, HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseDbdoc(liquibaseDbDocInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No dbdoc logs obtained");
		}
	}
	
	public ActionResponse liquibaseUpdate(LiquibaseUpdateInfo liquibaseUpdateInfo, HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseUpdate(liquibaseUpdateInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No update logs obtained");
		}
	}
	
	public ActionResponse liquibaseInstall(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseInstall();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No install logs obtained");
		}
	}
	
	public ActionResponse liquibaseDiff(LiquibaseDiffInfo liquibaseDiffInfo, HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs = null;
		//String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseDiff(liquibaseDiffInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No diff logs obtained");
		}
	}
	public ActionResponse liquibaseStatus(LiquibaseStatusInfo liquibaseStatusInfo,HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs = null;
		//String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseStatus(liquibaseStatusInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No status logs obtained");
		}
	}
	public ActionResponse liquibaseRollbackCount(LiquibaseRollbackCountInfo liquibaseRollbackCountInfo, HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs = null;
		//String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseRollBackCount(liquibaseRollbackCountInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No rollback logs obtained");
		}
	}
	public ActionResponse liquibaseRollbackToDate(LiquibaseRollbackToDateInfo liquibaseRollbackToDateInfo, HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs = null;
		//String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseRollbackToDate(liquibaseRollbackToDateInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No rollback logs obtained");
		}
	}
	public ActionResponse liquibaseRollbackTag(LiquibaseRollbackTagInfo liquibaseRollbackTagInfo, HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs = null;
		//String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseRollbackTag(liquibaseRollbackTagInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No rollback logs obtained");
		}
	}
	public ActionResponse liquibaseTag(LiquibaseTagInfo liquibaseTagInfo, HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs = null;
		//String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = liquibaseTag(liquibaseTagInfo);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No tag logs obtained");
		}
	}
	
	public ActionResponse validateTheme(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = validateTheme();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No validateTheme logs obtained");
		}
	}

	public ActionResponse validateContent(HttpServletRequest request) throws PhrescoException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = validateContent();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No validateContent logs obtained");
		}
	}

	public ActionResponse restartServer(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = restartServer();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No restart server logs obtained");
		}
	}

	public ActionResponse performanceTest(HttpServletRequest request, PerformanceUrls performanceUrls) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		initModuleName(request);
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = performanceTest(performanceUrls, unique_key, displayName);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No performance test logs obtained");
		}
	}

	public ActionResponse loadTest(HttpServletRequest request, PerformanceUrls performanceUrls) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		initModuleName(request);
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = loadTest(performanceUrls, unique_key, displayName);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No loadtest logs obtained");
		}
	}

	public ActionResponse minification(HttpServletRequest request, List<MinifyInfo> files) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		initModuleName(request);
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		String minifyParam = request.getParameter("minifyAll");
		server_logs = minification(files, minifyParam, unique_key, displayName);
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No minification logs obtained");
		}
	}

	public ActionResponse startHub(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = startHub();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No startHub logs obtained");
		}
	}

	public ActionResponse startNode(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = startNode();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No startHub logs obtained");
		}
	}

	public ActionResponse runFunctionalTest(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		String displayName = request.getParameter("displayName");
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = runFunctionalTest(unique_key, displayName, getModule());
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No startHub logs obtained");
		}
	}

	public ActionResponse stopHub(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = stopHub();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No startHub logs obtained");
		}
	}

	public ActionResponse stopNode(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = stopNode();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No startHub logs obtained");
		}
	}

	public ActionResponse showStartedHubLog(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = showStartedHubLog();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No startHub logs obtained");
		}
	}

	public ActionResponse showStartedNodeLog(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = showStartedNodeLog();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No startHub logs obtained");
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
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = generateSiteReport();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No generateSiteReport logs obtained");
		}
	}

	public ActionResponse ciSetup(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = ciSetup();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No setup logs obtained");
		}
	}

	public ActionResponse ciStart(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = ciStart();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No ci startup logs obtained");
		}
	}

	public ActionResponse ciStop(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		BufferedInputStream server_logs=null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		server_logs = ciStop();
		if (server_logs != null) {
			return generateResponse(server_logs, unique_key);
		} else {
			throw new PhrescoException("No ci stop logs obtained");
		}
	}

	public ActionResponse printAsPdf(HttpServletRequest request) throws PhrescoException, IOException {
		printLogs();
		ActionResponse response = new ActionResponse();
		return printAsPdf(response, request);
	}

	public BufferedInputStream build(String uniqueKey, String displayName, String module) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(module);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, module);
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(directory);
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_PACKAGE ,rootModulePath ,module)));
			persistValuesToXml(mojo, PHASE_PACKAGE);
			//To get maven build arguments
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_PACKAGE);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			String workingDirectory = pomFileLocation.getParent();
			getApplicationProcessor(getUsername(),rootModulePath,module).preBuild(applicationInfo);
			appendMultiModuleCommand(module, buildArgCmds); 
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.BUILD, buildArgCmds, workingDirectory);
			
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), REQ_BUILD, displayName, uniqueKey)), true);
		} catch (PhrescoException e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the build process"+e.getMessage());
		}

		return reader;
	}

	public BufferedInputStream deploy(String uniqueKey ,String  displayName, String module) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions.deploy()");
		}
		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(module);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, module);
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_DEPLOY,rootModulePath ,module)));

			persistValuesToXml(mojo, PHASE_DEPLOY);
			//To get maven build arguments
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_DEPLOY);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(module, buildArgCmds); 
			String workingDirectory = pomFileLocation.getParent();
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.DEPLOY, buildArgCmds, workingDirectory);
			
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), REQ_FROM_TAB_DEPLOY, displayName, uniqueKey)), true);
		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Exception occured in the deploy process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the deploy process");
			}
		}

		return reader;
	}

	public BufferedInputStream processBuild() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions.processBuild()");
		}
		BufferedInputStream reader = null;
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_PROCESS_BUILD, rootModulePath, getModule())));
			persistValuesToXml(mojo, PHASE_PROCESS_BUILD);
			
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.PROCESS_BUILD, null, pomFileLocation.getParent());
		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Exception occured in the processBuild process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the processBuild process");
			}
		}

		return reader;
	}
	
	public BufferedInputStream runUnitTest(String uniqueKey, String displayName, String module) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.runUnitTest()");
		}
		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(module);
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, module);
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
			String phrescoUnitInfoFilePath = getPhrescoPluginInfoFilePath(PHASE_UNIT_TEST,rootModulePath, module);
			List<String> buildArgCmds = new ArrayList<String>();
			if (new File(phrescoUnitInfoFilePath).exists()) {
				MojoProcessor mojo = new MojoProcessor(new File(phrescoUnitInfoFilePath));
				persistValuesToXml(mojo, PHASE_UNIT_TEST);
				List<Parameter> parameters = getMojoParameters(mojo, PHASE_UNIT_TEST);
				buildArgCmds = getMavenArgCommands(parameters);
				buildArgCmds.add(HYPHEN_N);
			}
			appendMultiModuleCommand(module, buildArgCmds); 
			
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.UNIT_TEST, buildArgCmds, pomFileLocation.getParent());
			 //To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(appInfo.getId(), UNIT, displayName, uniqueKey)), true);
		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Exception occured in the unit test process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the unit test process");
			}
		}

		return reader;
	}
	
	public BufferedInputStream runIntegrationTest(String uniqueKey, String displayName, String projectCode) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.runIntegrationTest()");
		}
		BufferedInputStream reader = null;
		try {
			String directory = projectCode + "-integrationtest";
			String phrescoIntegrationTestInfoFilePath = Utility.getProjectHome() + directory + File.separatorChar + INTEGRATION_TEST_INFO_FILE;
			List<String> buildArgCmds = new ArrayList<String>();
			if (new File(phrescoIntegrationTestInfoFilePath).exists()) {
				MojoProcessor mojo = new MojoProcessor(new File(phrescoIntegrationTestInfoFilePath));
				persistValuesToXml(mojo, PHASE_INTEGRATION_TEST);
				List<Parameter> parameters = getMojoParameters(mojo, PHASE_INTEGRATION_TEST);
				buildArgCmds = getMavenArgCommands(parameters);
				buildArgCmds.add(HYPHEN_N);
			}
			
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			reader = applicationManager.performAction(null, ActionType.INTEGRATION_TEST, buildArgCmds, Utility.getWorkingDirectoryPath(directory));
			 //To generate the lock for the particular operation
//			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(appInfo.getId(), UNIT, displayName, uniqueKey)), true);
		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Exception occured in the integration test process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the integration test process");
			}
		}

		return reader;
	}

	public BufferedInputStream runComponentTest(String uniqueKey, String displayName) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.runComponentTest:Entry");
		}
		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_COMPONENT_TEST, rootModulePath, getModule())));
			persistValuesToXml(mojo, PHASE_COMPONENT_TEST);
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_COMPONENT_TEST);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.COMPONENT_TEST, buildArgCmds, pomFileLocation.getParent());
			 //To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(appInfo.getId(), COMPONENT, displayName, uniqueKey)), true);
		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Exception occured in the Component test process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the Component test process");
			}
		}

		return reader;
	}

	public BufferedInputStream codeValidate(String uniqueKey, String displayName, String module) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.codeValidate()");
		}
		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(module);
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, module);
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_VALIDATE_CODE, rootModulePath, module)));
			persistValuesToXml(mojo, PHASE_VALIDATE_CODE);

			List<Parameter> parameters = getMojoParameters(mojo, PHASE_VALIDATE_CODE);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(module, buildArgCmds); 
			String workingDirectory = pomFileLocation.getParent();
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.CODE_VALIDATE, buildArgCmds, workingDirectory);
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), REQ_CODE, displayName, uniqueKey)), true);
		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Exception occured in the codeValidate process()" + FrameworkUtil.getStackTraceAsString(e));
				throw new PhrescoException("Exception occured in the codeValidate process");
			}
		}

		return reader;
	}
	
	public BufferedInputStream liquibaseDbdoc(LiquibaseDbDocInfo liquibaseDbDocInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			this.liquibaseDbDocInfo=liquibaseDbDocInfo;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE, rootModulePath,getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();
			persistLiquibaseValuesToXml(mojo, L_DBDOC, dbProperties, null);
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add(ARG_COMMAND+L_DBDOC);
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, buildArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the build process"+e.getMessage());
		}
		return reader;
	}
	
	private void persistLiquibaseValuesToXml(MojoProcessor mojo, String goal, Properties dbProperties, String installFile) throws PhrescoException {
		try {
			List<Parameter> parameters = getMojoParameters(mojo, goal);
			if (CollectionUtils.isNotEmpty(parameters)) {
				for (Parameter parameter : parameters) {
					if (parameter.getKey() != null && dbProperties.getProperty(parameter.getKey()) != null) {
						parameter.setValue(dbProperties.getProperty(parameter.getKey()));
					} else if(parameter.getKey() != null && request.getParameter(parameter.getKey()) != null) {
						parameter.setValue(request.getParameter(parameter.getKey()));
					} else if(parameter.getKey().equalsIgnoreCase(ROLLBACK_COUNT_VALUE) ) {
						parameter.setValue(liquibaseRollbackCountInfo.getRollbackCountValue());
					} else if(parameter.getKey().equalsIgnoreCase(CHANGELOGPATH_ROLLBACK_COUNT_VALUE) ) {
						parameter.setValue(liquibaseRollbackCountInfo.getChangeLogPathForRollbackCount());
					} else if(parameter.getKey().equalsIgnoreCase(ROLLBACK_TO_DATE) ) {
						parameter.setValue(liquibaseRollbackDateInfo.getRollbackDate());
					} else if(parameter.getKey().equalsIgnoreCase(CHANGELOGPATH_ROLLBACK_DATE) ) {
						parameter.setValue(liquibaseRollbackDateInfo.getChangeLogPathForRollbackDate());
					} else if(parameter.getKey().equalsIgnoreCase(ROLLBACK_TAG) ) {
						parameter.setValue(liquibaseRollbackTagInfo.getTag());
					} else if(parameter.getKey().equalsIgnoreCase(CHANGELOGPATH_ROLLBACK_TAG) ) {
						parameter.setValue(liquibaseRollbackTagInfo.getChangeLogPathForRollbackTag());
					} else if(parameter.getKey().equalsIgnoreCase(CHANGELOGPATH_STATUS) ) {
						parameter.setValue(liquibaseStatusInfo.getChangeLogPathForStatus());
					} else if(parameter.getKey().equalsIgnoreCase(VERBOSE) ) {
						parameter.setValue(liquibaseStatusInfo.getVerbose().toString());
					} else if(parameter.getKey().equalsIgnoreCase(TAG) ) {
						parameter.setValue(liquibaseTagInfo.getTag());
					} else if(parameter.getKey().equalsIgnoreCase(CHANGELOGPATH_DBDOC) ) {
						parameter.setValue(liquibaseDbDocInfo.getChangelogFileForDbDoc());
					} else if(parameter.getKey().equalsIgnoreCase(L_INSTALL)) {
						parameter.setValue(installFile);
					}
				}
			}
			mojo.save();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private Configuration getDbConfiguration(String environmentName, String dotPhrescoPath) throws PhrescoException {
		try {
			ConfigManager configManager = null;
//			File baseDir = new File(Utility.getProjectHome() + getAppDirName());
			configManager = new ConfigManagerImpl(new File(dotPhrescoPath + File.separator + Constants.CONFIGURATION_INFO_FILE));
			List<Configuration> configurations = configManager.getConfigurations(environmentName, Constants.SETTINGS_TEMPLATE_DB);
			if (CollectionUtils.isNotEmpty(configurations)) {
				for(Configuration configuration : configurations) {
					if(configuration.getName().equalsIgnoreCase(request.getParameter(CONF_NAME))) {
						return configuration;
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return null;
	}
	
	private Configuration getDbConfigurationForDiff(LiquibaseDiffInfo liquibaseDiffInfo,String environmentName, String dotPhrescoPath) throws PhrescoException {
		try {
			ConfigManager configManager = null;
//			File baseDir = new File(Utility.getProjectHome() + getAppDirName());
			configManager = new ConfigManagerImpl(new File(dotPhrescoPath + File.separator + Constants.CONFIGURATION_INFO_FILE));
			List<Configuration> configurations = configManager.getConfigurations(environmentName, Constants.SETTINGS_TEMPLATE_DB);
			if (CollectionUtils.isNotEmpty(configurations)) {
				for(Configuration configuration : configurations) {
					if(configuration.getName().equalsIgnoreCase(liquibaseDiffInfo.getSrcConfigurationName())) {
						return configuration;
					} else if(configuration.getName().equalsIgnoreCase(liquibaseDiffInfo.getDestConfigurationName())) {
						return configuration;
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return null;
	}
	
	public BufferedInputStream liquibaseUpdate(LiquibaseUpdateInfo liquibaseUpdateInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		Boolean deleteFile = true;
		File file = null;
		Boolean masterFlag = null;
		File master = null;
		File backupMaster = null;
		Boolean updateFlag = null;
		File update = null;
		Boolean installFlag = null;
		File install = null;
		Boolean noNewChangesets = true;
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE, rootModulePath, getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();
			persistLiquibaseValuesToXml(mojo, L_UPDATE, dbProperties, null);
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add(ARG_COMMAND+L_UPDATE);
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			File changesDir = new File(sourceFolderLocation + PATH_TO_LIQUIBASE +CHANGES_FOLDER);
			changesDir.mkdirs();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, YES);
	 
			// for changelog file
			Document changelog = docBuilder.newDocument();
			Element databaseChangeLog = changelog.createElement(DBCHANGELOG);
			changelog.appendChild(databaseChangeLog);
			Attr attr = changelog.createAttribute(XMLNS);
			attr.setValue(DBCHANGELOG_ORG);
			databaseChangeLog.setAttributeNode(attr);
			databaseChangeLog.setAttributeNS(XML_SCHEMA_INSTANCE,
				    SCHEMA_LOCATION, SCHEMA_DEFINITION);
			if(liquibaseUpdateInfo.getSqlStatements() != null && liquibaseUpdateInfo.getRollbackStatements() != null) {
				noNewChangesets = false;
				List<String> sqlStatements = liquibaseUpdateInfo.getSqlStatements();
				List<String> rollbackStatements = liquibaseUpdateInfo.getRollbackStatements();
				int counter = 0;
				for(String sqlStatement : sqlStatements) {
					Element changeset = changelog.createElement(CHANGESET);
					changeset.setAttribute(CHANGESET_ID, String.valueOf(counter));
					changeset.setAttribute(CHANGESET_AUTHOR, liquibaseUpdateInfo.getAuthor());
					databaseChangeLog.appendChild(changeset);
			
					Element sql = changelog.createElement(SQL);
					sql.appendChild(changelog.createTextNode(sqlStatement));
					changeset.appendChild(sql);
			
					Element rollback = changelog.createElement(ROLLBACK);
					rollback.appendChild(changelog.createTextNode(rollbackStatements.get(counter)));
					changeset.appendChild(rollback);
			
					counter++;
				}
				file = new File(changesDir+File.separator+liquibaseUpdateInfo.getFileName()+DOT+XML);
				if (file.exists()) {
					deleteFile = false;
					throw new Exception("file name given for Liquibase Update already exists");					
				}
				file.createNewFile();			
				DOMSource source = new DOMSource(changelog);
				StreamResult result = new StreamResult(file.toURI().getPath());
				transformer.transform(source, result);
			}
			if(!noNewChangesets) {
				
				// for master file
				File versionDir = new File(sourceFolderLocation +PATH_TO_LIQUIBASE+liquibaseUpdateInfo.getDbVersion()); 
				versionDir.mkdirs();
				master = new File(versionDir+MASTER_XML);
				backupMaster = new File(versionDir+BACKUP_MASTER_XML);
				backupMaster.createNewFile();
				if(!master.exists()) {
					masterFlag = false;
					master.createNewFile();
					Document masterXML = docBuilder.newDocument();
					Element databaseChangeLogForMaster = masterXML.createElement(DBCHANGELOG);
					masterXML.appendChild(databaseChangeLogForMaster);
					Attr attrib = masterXML.createAttribute(XMLNS);
					attrib.setValue(DBCHANGELOG_ORG);
					databaseChangeLogForMaster.setAttributeNode(attrib);
					databaseChangeLogForMaster.setAttributeNS(XML_SCHEMA_INSTANCE,
							SCHEMA_LOCATION, SCHEMA_DEFINITION);
					if(file.exists()) {
						Element include = masterXML.createElement(INCLUDE_TAG);
						include.setAttribute(FILE_ATTR, PATH_TO_LIQUIBASE.substring(1, PATH_TO_LIQUIBASE.length())+CHANGES_FOLDER+liquibaseUpdateInfo.getFileName()+DOT+XML);
						databaseChangeLogForMaster.appendChild(include);
					}
					DOMSource sourceMaster = new DOMSource(masterXML);
					StreamResult resultMaster = new StreamResult(master.toURI().getPath());
					transformer.transform(sourceMaster, resultMaster);
				} else {
					masterFlag = true;
					FileUtils.copyFile(master, backupMaster);
					Document masterXML = docBuilder.parse(master);
					Element databaseChangeLogForMaster = masterXML.getDocumentElement();
					if(file.exists()) {
						Element include = masterXML.createElement(INCLUDE_TAG);
						include.setAttribute(FILE_ATTR, PATH_TO_LIQUIBASE.substring(1, PATH_TO_LIQUIBASE.length())+CHANGES_FOLDER+liquibaseUpdateInfo.getFileName()+DOT+XML);
						databaseChangeLogForMaster.appendChild(include);
						
					}
					DOMSource sourceMaster = new DOMSource(masterXML);
					StreamResult resultMaster = new StreamResult(master.toURI().getPath());
					transformer.transform(sourceMaster, resultMaster);
				}
				
				// for update file
				update = new File(sourceFolderLocation +PATH_TO_LIQUIBASE+UPDATE_XML);
				if(!update.exists()) {
					updateFlag = false;
					update.createNewFile();
					Document updateXML = docBuilder.newDocument();
					Element databaseChangeLogForUpdate = updateXML.createElement(DBCHANGELOG);
					updateXML.appendChild(databaseChangeLogForUpdate);
					Attr attrib = updateXML.createAttribute(XMLNS);
					attrib.setValue(DBCHANGELOG_ORG);
					databaseChangeLogForUpdate.setAttributeNode(attrib);
					databaseChangeLogForUpdate.setAttributeNS(XML_SCHEMA_INSTANCE,
							SCHEMA_LOCATION, SCHEMA_DEFINITION);
					Element include = updateXML.createElement(INCLUDE_TAG);
					include.setAttribute(FILE_ATTR, PATH_TO_LIQUIBASE.substring(1, PATH_TO_LIQUIBASE.length())+liquibaseUpdateInfo.getDbVersion()+MASTER_XML);
					databaseChangeLogForUpdate.appendChild(include);
					DOMSource sourceUpdate = new DOMSource(updateXML);
					StreamResult resultUpdate = new StreamResult(update.toURI().getPath());
					transformer.transform(sourceUpdate, resultUpdate);
				}

				//for install file
				install = new File(sourceFolderLocation +PATH_TO_LIQUIBASE+INSTALL_XML);
				if(!install.exists()) {
					installFlag = false;
					install.createNewFile();
					Document installXML = docBuilder.newDocument();
					Element databaseChangeLogForInstall = installXML.createElement(DBCHANGELOG);
					installXML.appendChild(databaseChangeLogForInstall);
					Attr attrib = installXML.createAttribute(XMLNS);
					attrib.setValue(DBCHANGELOG_ORG);
					databaseChangeLogForInstall.setAttributeNode(attrib);
					databaseChangeLogForInstall.setAttributeNS(XML_SCHEMA_INSTANCE,
							SCHEMA_LOCATION, SCHEMA_DEFINITION);
					DOMSource sourceUpdate = new DOMSource(installXML);
					StreamResult resultUpdate = new StreamResult(install.toURI().getPath());
					transformer.transform(sourceUpdate, resultUpdate);
				}
			}
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, buildArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException(flagChecks(file, master, backupMaster, update, install, masterFlag, updateFlag, installFlag, deleteFile, e));
		}
		if(backupMaster != null) {
			backupMaster.delete();
		}
		return reader;
	}
	
	private PhrescoException flagChecks(File file, File master, File backupMaster, File update, File install, Boolean masterFlag, Boolean updateFlag, Boolean installFlag, Boolean deleteFile, Exception e) {
		Boolean renamed = null;
		if(file.getPath() != null && deleteFile) {
			file.delete();
		}
		if (masterFlag != null) {
			if(masterFlag == false) {
				master.delete();
				backupMaster.delete();
			} else {
				master.delete();
				renamed = backupMaster.renameTo(master);
			}
		}
		if (updateFlag != null) {
			if(updateFlag == false) {
				update.delete();
			}
		}
		if(installFlag != null) {
			if(installFlag == false) {
				install.delete();
			}
		}
		if (renamed != null) {
			if(renamed == false) {
				return new PhrescoException("Failed to restore backup. Please rename backupmaster.xml to master.xml . Exception occured in the build process"+e.getMessage());
			}
		}
		return new PhrescoException("Exception occured in the build process"+e.getMessage());
	}
	
	public BufferedInputStream liquibaseInstall() throws PhrescoException {
		BufferedInputStream reader=null;
		BufferedInputStream generated=null;
		String readerRecord = "";
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, rootModulePath, getModule());
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE,rootModulePath, getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			File install = new File(sourceFolderLocation + PATH_TO_LIQUIBASE + INSTALL_XML);
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document installXML = docBuilder.parse(install);
			NodeList includeTags = installXML.getElementsByTagName(INCLUDE_TAG);
			String includeFile = null;
			
			if(includeTags.getLength()!= 0) {
				for (int includeCounter = 0; includeCounter < includeTags.getLength(); includeCounter++) {
					Node includeNode = includeTags.item(includeCounter);		 
					if (includeNode.getNodeType() == Node.ELEMENT_NODE) {
						Element includeElement = (Element) includeNode;
						includeFile = includeElement.getAttribute(FILE_ATTR);
						persistLiquibaseValuesToXml(mojo, L_INSTALL, dbProperties, includeFile);
						List<String> buildArgCmds = new ArrayList<String>();
						buildArgCmds.add(ARG_COMMAND+L_INSTALL);
						reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, buildArgCmds, pomFileLocation.getParent());
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
						String temp = bufferedReader.readLine();
						while(temp != null)
						{
							readerRecord += NEWLINE +" "+ temp + NEWLINE;
							temp = bufferedReader.readLine();
							if(temp.contains(L_UPDATE_SUCCESS)) {
								temp = INSTALL_COMPLETED_FOR+THIS_VERSION;
								readerRecord += NEWLINE +" "+ temp + NEWLINE;
								break;
							}
						}
						reader=null;
					}
				}
			}
			persistLiquibaseValuesToXml(mojo, L_UPDATE, dbProperties, null);
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add(ARG_COMMAND+L_UPDATE);
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, buildArgCmds, pomFileLocation.getParent());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
			String temp = bufferedReader.readLine();
			while(temp != null)
			{
				readerRecord += NEWLINE+" " + temp + NEWLINE;
				temp = bufferedReader.readLine();
				if(temp.contains(L_UPDATE_SUCCESS)) {
					temp = UPDATE_COMPLETED_FOR + THIS_VERSION;
					readerRecord += NEWLINE +" "+temp + NEWLINE;
					break;
				}
			}
			InputStream is = new ByteArrayInputStream(readerRecord.getBytes());
			generated = new BufferedInputStream(is);
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the build process"+e.getMessage());
		}
		return generated;
	}
	
	
	public BufferedInputStream liquibaseDiff(LiquibaseDiffInfo liquibaseDiffInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE, rootModulePath,getModule())));
			Configuration sourceConfiguration = getDbConfigurationForDiff(liquibaseDiffInfo,liquibaseDiffInfo.getSrcEnvironmentName(), dotPhrescoFolderPath);
			Properties sourceDbProperties = sourceConfiguration.getProperties();			
			Configuration referenceConfiguration = getDbConfigurationForDiff(liquibaseDiffInfo,liquibaseDiffInfo.getDestEnvironmentName(), dotPhrescoFolderPath);
			Properties referenceDbProperties = referenceConfiguration.getProperties();
			persistLiquibaseDiffValuesToXml(mojo, L_DIFF, sourceDbProperties, referenceDbProperties);
			List<Parameter> parameters = getMojoParameters(mojo, L_DIFF);
			List<String> liquibaseArgCmds = getMavenArgCommands(parameters);			
			liquibaseArgCmds.add(ARG_COMMAND+L_DIFF);			
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, liquibaseArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the liquibase process "+e.getMessage());
		}
		return reader;
	}

	private void persistLiquibaseDiffValuesToXml(MojoProcessor mojo,
			String goal, Properties sourceDbProperties,
			Properties referenceDbProperties) throws PhrescoException {
		try {
			List<Parameter> parameters = getMojoParameters(mojo, goal);
			if (CollectionUtils.isNotEmpty(parameters)) {
				for (Parameter parameter : parameters) {
					if (parameter.getKey().equalsIgnoreCase(SOURCE_USERNAME)) {
						parameter.setValue(sourceDbProperties.getProperty(REQ_USER_NAME));
					}
					if (parameter.getKey().equalsIgnoreCase(REFERENCE_USERNAME)) {
						parameter.setValue(referenceDbProperties.getProperty(REQ_USER_NAME));
					}
					if (parameter.getKey().equalsIgnoreCase(SOURCE_PASSWORD)) {
						parameter.setValue(sourceDbProperties.getProperty(REQ_PASSWORD));
					}
					if (parameter.getKey().equalsIgnoreCase(REFERENCE_PASSWORD)) {
						parameter.setValue(referenceDbProperties.getProperty(REQ_PASSWORD));
					}
					if (parameter.getKey().equalsIgnoreCase(SOURCE_HOST)) {
						parameter.setValue(sourceDbProperties.getProperty(HOST));
					}
					if (parameter.getKey().equalsIgnoreCase(REFERENCE_HOST)) {
						parameter.setValue(referenceDbProperties.getProperty(HOST));
					}
					if (parameter.getKey().equalsIgnoreCase(SOURCE_PORT)) {
						parameter.setValue(sourceDbProperties.getProperty(PORT));
					}
					if (parameter.getKey().equalsIgnoreCase(REFERENCE_PORT)) {
						parameter.setValue(referenceDbProperties.getProperty(PORT));
					}
					if (parameter.getKey().equalsIgnoreCase(SOURCE_DBNAME)) {
						parameter.setValue(sourceDbProperties.getProperty(DBNAME));
					}
					if (parameter.getKey().equalsIgnoreCase(REFERENCE_DBNAME)) {
						parameter.setValue(referenceDbProperties.getProperty(DBNAME));
					}
					if (parameter.getKey().equalsIgnoreCase(DBTYPE)) {
						parameter.setValue(sourceDbProperties.getProperty(parameter.getKey()));
					}
				}
			}
			mojo.save();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	/*
	 * Liquibase Status
	 */
	public BufferedInputStream liquibaseStatus(LiquibaseStatusInfo liquibaseStatusInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			this.liquibaseStatusInfo=liquibaseStatusInfo;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE, rootModulePath, getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();					
			persistLiquibaseValuesToXml(mojo, L_STATUS, dbProperties, null);
			List<Parameter> parameters = getMojoParameters(mojo, L_STATUS);
			List<String> liquibaseArgCmds = getMavenArgCommands(parameters);			
			liquibaseArgCmds.add(ARG_COMMAND+L_STATUS);			
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
            reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, liquibaseArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the liquibase process "+e.getMessage());
		}
		return reader;
	}

	/*
	 * Liquibase RollBackCount
	 */
	public BufferedInputStream liquibaseRollBackCount(LiquibaseRollbackCountInfo liquibaseRollbackCountInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			this.liquibaseRollbackCountInfo=liquibaseRollbackCountInfo;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE, rootModulePath, getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();			
			persistLiquibaseValuesToXml(mojo, L_ROLLBACK_COUNT, dbProperties,null);		
			List<String> liquibaseArgCmds = new ArrayList<String>();			
			liquibaseArgCmds.add(ARG_COMMAND+L_ROLLBACK_COUNT);			
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, liquibaseArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the liquibase process "+e.getMessage());
		}
		return reader;
	}
	
	/*
	 * Liquibase RollBacktoDate
	 */
	public BufferedInputStream liquibaseRollbackToDate(LiquibaseRollbackToDateInfo liquibaseRollbackToDateInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			this.liquibaseRollbackDateInfo=liquibaseRollbackToDateInfo;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE, rootModulePath, getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();			
			persistLiquibaseValuesToXml(mojo, L_ROLLBACK_TO_DATE, dbProperties, null);
			List<Parameter> parameters = getMojoParameters(mojo, L_ROLLBACK_TO_DATE);
			List<String> liquibaseArgCmds = getMavenArgCommands(parameters);			
			liquibaseArgCmds.add(ARG_COMMAND+L_ROLLBACK_TO_DATE);			
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, liquibaseArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the liquibase process "+e.getMessage());
		}
		return reader;
	}

	/*
	 * Liquibase RollBackTag
	 */
	public BufferedInputStream liquibaseRollbackTag(LiquibaseRollbackTagInfo liquibaseRollbackTagInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			this.liquibaseRollbackTagInfo=liquibaseRollbackTagInfo;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE, rootModulePath, getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();			
			persistLiquibaseValuesToXml(mojo, L_ROLLBACK, dbProperties, null);
			List<Parameter> parameters = getMojoParameters(mojo, L_ROLLBACK);
			List<String> liquibaseArgCmds = getMavenArgCommands(parameters);			
			liquibaseArgCmds.add(ARG_COMMAND+L_ROLLBACK);			
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, liquibaseArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the liquibase process "+e.getMessage());
		}
		return reader;
	}

	/*
	 * Liquibase RollBackTag
	 */
	public BufferedInputStream liquibaseTag(LiquibaseTagInfo liquibaseTagInfo) throws PhrescoException {
		BufferedInputStream reader=null;
		try {
			this.liquibaseTagInfo=liquibaseTagInfo;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(LIQUIBASE,rootModulePath, getModule())));
			Configuration selectedConfiguration = getDbConfiguration(request.getParameter(ENV_NAME), dotPhrescoFolderPath);
			Properties dbProperties = selectedConfiguration.getProperties();			
			persistLiquibaseValuesToXml(mojo, L_TAG, dbProperties, null);		
			List<Parameter> parameters = getMojoParameters(mojo, L_TAG);
			List<String> liquibaseArgCmds = getMavenArgCommands(parameters);			
			liquibaseArgCmds.add(ARG_COMMAND+L_TAG);			
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, ActionType.LIQUIBASE, liquibaseArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error(FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the liquibase process "+e.getMessage());
		}
		return reader;
	}

	public BufferedInputStream runAgainstSource(String uniqueKey, String displayName) throws IOException, PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.javaRunAgainstSource()");
		}
		BufferedInputStream reader = null;
		try {
//			String directory = getAppDirBasedOnMultiModule(getModule());
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");		
			String workingDir = pomFileLocation.getParent();
			//To generate the lock for the particular operation
			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(appInfo.getId(), REQ_START, displayName, uniqueKey)), true);
			
			BufferedReader compileReader = compileSource(workingDir);
			String line = compileReader.readLine();
			while (StringUtils.isNotEmpty(line) && !line.startsWith("[INFO] BUILD FAILURE")) {
				line = compileReader.readLine();
			}

			if (StringUtils.isNotEmpty(line) && line.startsWith("[INFO] BUILD FAILURE")) {
				reader = new BufferedInputStream(new FileInputStream(getLogFilePath(workingDir)));
			} else {
				MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_RUNGAINST_SRC_START, rootModulePath,getModule())));
				persistValuesToXml(mojo, PHASE_RUNGAINST_SRC_START);
				ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
				reader = startServer(false, workingDir, rootModulePath, rootprojectInfo);
			}
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of Build.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the runAgainstSource process");
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Build.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the runAgainstSource process");
		}

		return reader;
	}

	public BufferedInputStream stopServer() throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Build.handleStopServer()");
		}
		BufferedInputStream reader = null;
		boolean success = true;
		ApplicationInfo applicationInfo = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			applicationInfo = projectInfo.getAppInfos().get(0);
			reader = handleStopServer(false);
		} catch (PhrescoException e) {
			success =  false;
			S_LOGGER.error("Entered into catch block of MavenFunctions.stopServer()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the stopServer process");
		} catch (Exception e) {
			success =  false;
			S_LOGGER.error("Entered into catch block of MavenFunctions.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the runAgainstSource process");
		} finally {
			if (success) {
				String uniqueKey = "";
				List<LockDetail> lockDetails = LockUtil.getLockDetails();
				for (LockDetail lockDetail : lockDetails) {
					if("Start".equalsIgnoreCase(lockDetail.getActionType()) && applicationInfo.getId().equals(lockDetail.getAppId())) {
						uniqueKey = lockDetail.getUniqueKey();
					}
				}
				if (StringUtils.isNotEmpty(uniqueKey)) {
					LockUtil.removeLock(uniqueKey);
				}
			}
		}

		return reader;
	}


	public BufferedInputStream restartServer() throws IOException, PhrescoException  {

		BufferedInputStream reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.restartServer()");
		}

		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			String workingDir = pomFileLocation.getParent();
			handleStopServer(true);
			BufferedReader compileReader = compileSource(workingDir);
			String line = compileReader.readLine();
			while (line != null && !line.startsWith("[INFO] BUILD FAILURE")) {
				line = compileReader.readLine();
			}

			if (line != null && line.startsWith("[INFO] BUILD FAILURE")) {
				reader = new BufferedInputStream(new FileInputStream(getLogFilePath(workingDir)));
			} else {
				reader = startServer(true, workingDir, rootModulePath, projectInfo);
			}
		} catch (PhrescoException e) {

		}catch (Exception e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.runAgainstSource()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.restartServer process");
		}

		return reader;
	}


	public BufferedInputStream performanceTest(PerformanceUrls performanceUrls, String uniqueKey, String displayName) throws PhrescoException {

		BufferedInputStream reader=null;

		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_PERFORMANCE_TEST, rootModulePath, getModule())));
			persistValuesToXml(mojo, PHASE_PERFORMANCE_TEST);

			//To get maven build arguments
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_PERFORMANCE_TEST);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			jsonWriter(performanceUrls,  rootModulePath, getModule(), projectInfo);
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.PERFORMANCE_TEST, buildArgCmds, pomFileLocation.getParent());
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), PERFORMACE, displayName, uniqueKey)), true);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}

		return reader;
	}


	public BufferedInputStream loadTest(PerformanceUrls performanceUrls, String uniqueKey, String displayName) throws PhrescoException {

		BufferedInputStream reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.runLoadTest()");
		} 
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_LOAD_TEST, rootModulePath, getModule())));
			persistValuesToXml(mojo, PHASE_LOAD_TEST);
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_LOAD_TEST);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			jsonWriter(performanceUrls, rootModulePath, getModule(), projectInfo);  
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.LOAD_TEST, buildArgCmds, pomFileLocation.getParent());
			 //To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(appInfo.getId(), LOAD, displayName, uniqueKey)), true);
		} catch(PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.LoadTest()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.LoadTest process");
		}
		return reader;
	}

	public BufferedInputStream minification(List<MinifyInfo> files, String minifyParam, String uniqueKey ,String displayName) throws PhrescoException {

		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			
//			String directory = getAppDirBasedOnMultiModule(getModule());
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(directory);
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
//			String pomPath = FrameworkServiceUtil.getAppPom(directory);
			PomProcessor pomProcessor = new PomProcessor(pomFileLocation);
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			List<Element> configList = new ArrayList<Element>();
			createExcludesTagInPom(doc, configList);
			if (Boolean.parseBoolean(minifyParam) && CollectionUtils.isEmpty(files)) { // Only Minify all is selected
				configList.add(createElement(doc, POM_OUTPUTDIR, POM_SOURCE_DIRECTORY));
			} else if (CollectionUtils.isNotEmpty(files)) {
				String dynamicIncludeDir = "";
				if (Boolean.parseBoolean(minifyParam)) {//if Minify all is selected
					dynamicIncludeDir = POM_SOURCE_DIRECTORY;
					configList.add(createElement(doc, POM_OUTPUTDIR, POM_SOURCE_DIRECTORY));
				} else {//if Minify all not is selected
					dynamicIncludeDir = POM_OUTPUT_DIRECTORY;
					configList.add(createElement(doc, POM_OUTPUTDIR, POM_OUTPUT_DIRECTORY));
				}
				createAggregationTagInPom(doc, configList, files, dynamicIncludeDir);
			}
			pomProcessor.addConfiguration(MINIFY_PLUGIN_GROUPID, MINIFY_PLUGIN_ARTFACTID, configList);
			pomProcessor.save();

			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.MINIFY, null,  pomFileLocation.getParent());
			
			 //To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), "minify", displayName, uniqueKey)), true);
			
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.minification()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.minification process");
		}

		return reader;
	}

	public BufferedInputStream startHub() throws PhrescoException {

		BufferedInputStream reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.startHub()");
		}

		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_START_HUB, rootModulePath, getModule())));
			persistValuesToXml(mojo, PHASE_START_HUB);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(directory);
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_START_HUB);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			reader = applicationManager.performAction(projectInfo, ActionType.START_HUB, buildArgCmds, pomFileLocation.getParent());
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.startHub()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.startHub process");
		}

		return reader;
	}


	public BufferedInputStream startNode() throws PhrescoException {

		BufferedInputStream reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.startNode()");
		}

		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_START_NODE, rootModulePath, getModule())));
			persistValuesToXml(mojo, PHASE_START_NODE);
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_START_NODE);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(directory);
			reader = applicationManager.performAction(projectInfo, ActionType.START_NODE, buildArgCmds, pomFileLocation.getParent());
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.startNode()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.startNode process");
		}

		return reader;
	}
	
	public BufferedInputStream validateTheme() throws PhrescoException {
		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
//			StringBuilder workingDirectory = new StringBuilder(FrameworkServiceUtil.getApplicationHome(applicationInfo.getAppDirName()));
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			List<String> buildArgCmds = new ArrayList<String>();
			String pomFileName = pomFileLocation.getName();
			if (!Constants.POM_NAME.equals(pomFileName)) {
				buildArgCmds.add(Constants.HYPHEN_F);
				buildArgCmds.add(pomFileName);
			}
			reader = applicationManager.performAction(projectInfo, ActionType.THEME_VALIDATOR,
					buildArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.validateTheme()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.validateTheme process");
		}
		return reader;
	}
	
	public BufferedInputStream validateContent() throws PhrescoException {
		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
//			StringBuilder workingDirectory = new StringBuilder(FrameworkServiceUtil.getApplicationHome(applicationInfo.getAppDirName()));
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			List<String> buildArgCmds = new ArrayList<String>();
			String pomFileName = pomFileLocation.getName();
			if (!Constants.POM_NAME.equals(pomFileName)) {
				buildArgCmds.add(Constants.HYPHEN_F);
				buildArgCmds.add(pomFileName);
			}
			reader = applicationManager.performAction(projectInfo,
					ActionType.CONTENT_VALIDATOR, buildArgCmds, pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.validateContent()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.validateContent process");
		}
		return reader;
	}

	public BufferedInputStream runFunctionalTest(String uniqueKey, String displayName, String module) throws PhrescoException {

		BufferedInputStream reader = null;

		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.runFunctionalTest()");
		}

		try {
			String rootModulePath = getAppDirBasedOnMultiModule(module);
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, module);
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_FUNCTIONAL_TEST,rootModulePath, module)));
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String seleniumToolType = frameworkUtil.getSeleniumToolType(rootModulePath, module);
			persistValuesToXml(mojo, PHASE_FUNCTIONAL_TEST + HYPHEN + seleniumToolType);
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_FUNCTIONAL_TEST + HYPHEN + seleniumToolType);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(module, buildArgCmds); 
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			reader = applicationManager.performAction(rootprojectInfo, ActionType.FUNCTIONAL_TEST, buildArgCmds, pomFileLocation.getParent());
			 //To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(appInfo.getId(), FUNCTIONAL, displayName, uniqueKey)), true);
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


	public BufferedInputStream stopHub() throws PhrescoException {

		BufferedInputStream reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.stopHub()");
		}

		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
//			String workingDirectory = getAppDirectoryPath(appInfo);
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			reader = applicationManager.performAction(projectInfo, ActionType.STOP_HUB, buildArgCmds, pomFileLocation.getParent());
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.stopHub()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Entered into catch block of MavenFunctions.stopHub()");
			//return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_STOP_HUB));
		}

		return reader;
	}


	public BufferedInputStream stopNode() throws PhrescoException {

		BufferedInputStream reader = null;

		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.stopNode()");
		}

		try {
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
//			ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
//			String workingDirectory = getAppDirectoryPath(appInfo);
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			reader = applicationManager.performAction(projectInfo, ActionType.STOP_NODE, buildArgCmds, pomFileLocation.getParent());
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
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			HubConfiguration hubConfig = getHubConfig(rootModulePath, getModule());
			if (hubConfig != null) {
				String host = hubConfig.getHost();
				int port = hubConfig.getPort();
				setConnectionAlive(Utility.isConnectionAlive(HTTP_PROTOCOL, host, port));
				response.setStatus(RESPONSE_STATUS_SUCCESS);
				response.setResponseCode(PHRQ300007);
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
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			File testFolderLocation = Utility.getTestFolderLocation(projectInfo, rootModulePath, getModule());
			String functionalTestDir = FrameworkServiceUtil.getFunctionalTestDir(rootModulePath, getModule());
			
//			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
//			String functionalTestDir = frameworkUtil.getFunctionalTestDir(FrameworkServiceUtil.getApplicationInfo(getAppDirName()));
			StringBuilder sb = new StringBuilder(testFolderLocation.getPath());
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
				response.setStatus(RESPONSE_STATUS_SUCCESS);
				response.setResponseCode(PHRQ300008);
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


	public BufferedInputStream showStartedHubLog() throws PhrescoException {
		BufferedInputStream reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.showStartedHubLog()");
		}

		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			HubConfiguration hubConfig = getHubConfig(rootModulePath, getModule());
			String host = hubConfig.getHost();
			int port = hubConfig.getPort();
			String str = "Hub is already running in " + host + ":" + port;
			InputStream is = new ByteArrayInputStream(str.getBytes());
			reader = new BufferedInputStream(is);
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.showStartedHubLog()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Entered into catch block of MavenFunctions.stopNode()"+ FrameworkUtil.getStackTraceAsString(e));
			//return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_HUB_LOG));
		}

		return reader;
	}


	public BufferedInputStream showStartedNodeLog() throws PhrescoException {
		BufferedInputStream reader = null;

		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.showStartedNodeLog()");
		}

		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			NodeConfiguration nodeConfig = getNodeConfig(rootModulePath, getModule());
			NodeConfig configuration = nodeConfig.getConfiguration();
			String host = configuration.getHost();
			int port = configuration.getPort();
			String str = "Node is already running in " + host + COLON + port;
			InputStream is = new ByteArrayInputStream(str.getBytes());
			reader = new BufferedInputStream(is);

		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.showStartedNodeLog()"+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Entered into catch block of MavenFunctions.showStartedNodeLog()"+ FrameworkUtil.getStackTraceAsString(e));
			//return showErrorPopup(e, getText(EXCEPTION_QUALITY_FUNCTIONAL_NODE_LOG));
		}

		return reader;
	}

	public BufferedInputStream generateSiteReport() throws PhrescoException {
		BufferedInputStream reader = null;
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions.generateReport()");
		}

		try {
			ActionType actionType = null;
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(getAppDirName());
			actionType = ActionType.SITE_REPORT;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			String appDirectoryPath = getAppDirectoryPath(applicationInfo);
			reader = applicationManager.performAction(projectInfo, actionType, null, appDirectoryPath);
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions.generateSiteReport()"
					+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("exception occured in the generate site report");
		}
		return reader;
	}


	public BufferedInputStream ciSetup() throws PhrescoException {
		BufferedInputStream reader = null;

		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions CI setup()");
		}
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ProjectInfo projectInfo = getProjectInfo();
			if (isDebugEnabled) {
				S_LOGGER.debug("Jenkins Home " + Utility.getJenkinsHome().toString());
			}

			List<String> buildArgCmds = new ArrayList<String>(1);
			buildArgCmds.add(SKIP_TESTS);
			String workingDirectory = Utility.getJenkinsHome();
			reader = ciManager.setup(null, ActionType.INSTALL, buildArgCmds , workingDirectory);

		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of MavenFunctions CI setup()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("exception occured in the CI Setup ");
		}
		return reader;
	}


	public BufferedInputStream ciStart() throws PhrescoException {
		BufferedInputStream reader = null;

		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions .startJenkins()");
		}
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ProjectInfo projectInfo = getProjectInfo();
			if (isDebugEnabled) {
				S_LOGGER.debug("Jenkins Home " + Utility.getJenkinsHome().toString());
			}

			List<String> buildArgCmds = null;
			String workingDirectory = Utility.getJenkinsHome();

			reader = ciManager.start(null, ActionType.START, buildArgCmds , workingDirectory);

		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of CI.startJenkins()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("exception occured in the CI Start up ");
		}
		return reader;
	}

	public BufferedInputStream ciStop() throws PhrescoException {

		BufferedInputStream reader=null;		
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  MavenFunctions .stopJenkins()");
		}
		try {
			CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
//			ProjectInfo projectInfo = getProjectInfo();
			if (isDebugEnabled) {
				S_LOGGER.debug("Jenkins Home " + Utility.getJenkinsHome().toString());
			}

			List<String> buildArgCmds = null;
			String workingDirectory = Utility.getJenkinsHome();
			reader = ciManager.stop(null, ActionType.STOP, buildArgCmds , workingDirectory);

		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of CI.stopJenkins()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("exception occured in the CI stop functionality");
		}
		return reader;
	}


	public ActionResponse printAsPdf (ActionResponse response, HttpServletRequest request) throws PhrescoException {
		S_LOGGER.debug("Entering Method Quality.printAsPdf()");
		try {
//			String appDirName = request.getParameter(REQ_APP_DIR_NAME);
			String userId = request.getParameter(REQ_USER_ID);
			String fromPage = request.getParameter(REQ_FROM_PAGE);
			String pdfName = request.getParameter(REQ_PDF_NAME);
			String reportDataType = request.getParameter(REPORT_DATA_TYPE);
			
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			File getpomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, getModule());
			String sonarURL = FrameworkServiceUtil.getSonarURL(request);
			String customerId = projectInfo.getCustomerIds().get(0);

			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			String techId = applicationInfo.getTechInfo().getId();
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				response.setStatus(ActionServiceConstant.SUCCESS);
				response.setLog(ActionServiceConstant.SUCCESS);
				response.setService_exception("Unauthorized User ");
				return response;
			} 
			Technology technology = serviceManager.getTechnology(techId);
			
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, getModule());
			StringBuilder sb = new StringBuilder(dotPhrescoFolderPath);
			sb.append(File.separator);
			sb.append(PHRESCO_HYPEN);
			sb.append(PHASE_PDF_REPORT);
			sb.append(INFO_XML);
			
			MojoProcessor mojo = new MojoProcessor(new File(sb.toString()));
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_PDF_REPORT);

			if (CollectionUtils.isNotEmpty(parameters)) {
				for (Parameter parameter : parameters) {
					String key = parameter.getKey();
					if (REQ_REPORT_TYPE.equals(key)) {
						parameter.setValue(reportDataType);
					} else if (REQ_TEST_TYPE.equals(key)) {
						if (StringUtils.isEmpty(fromPage)) {
							setFromPage(FROMPAGE_ALL);
						}
						parameter.setValue(fromPage);
					} else if (REQ_SONAR_URL.equals(key)) {
	            		parameter.setValue(sonarURL);
	            	} else if (LOGO.equals(key)) {
	            		parameter.setValue(getLogoImageString(userId, customerId));
	            	} else if (THEME.equals(key)) {
	            		parameter.setValue(getThemeColorJson(userId, customerId));
	            	} else if (REQ_REPORT_NAME.equals(key)) {
	            		parameter.setValue(pdfName);
	            	} else if (TECHNOLOGY_NAME.equals(key)) {
	            		parameter.setValue(technology.getName());
	            	}
				}
			}
			mojo.save();

			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(module, buildArgCmds); 
//			String workingDirectory = getAppDirectoryPath(applicationInfo);
			ProjectInfo rootprojectInfo = Utility.getProjectInfo(rootModulePath, "");
			BufferedInputStream reader = applicationManager.performAction(rootprojectInfo, ActionType.PDF_REPORT, buildArgCmds, getpomFileLocation.getParent());
			
			int available = reader.available();
			while (available != 0) {
				byte[] buf = new byte[available];
                int read = reader.read(buf);
                if (read == -1 ||  buf[available-1] == -1) {
                	break;
                } else {
                	System.out.println("Console : " + new String(buf));
                }
                available = reader.available();
			}
			
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of ActionFunction.printAsPdf()"+ e);
			response.setStatus(ERROR);
			response.setLog(ERROR);
			response.setService_exception(FrameworkUtil.getStackTraceAsString(e));
			response.setUniquekey("");
			throw new PhrescoException("exception occured in the Print As PDF functionality");
		}
		response.setStatus(RESPONSE_STATUS_SUCCESS);
		response.setLog(ActionServiceConstant.SUCCESS);
		response.setResponseCode(PHR200016);
		return response;
	}

	protected String getThemeColorJson(String username, String customerId) throws PhrescoException {
		String themeJsonStr = "";
		try {
			Customer customer = getServiceManager(username).getCustomer(customerId);
			if (customer != null) {
				Map<String, String> frameworkTheme = customer.getFrameworkTheme();
				if (frameworkTheme != null) {
					Gson gson = new Gson();
					themeJsonStr = gson.toJson(frameworkTheme);
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return themeJsonStr;
	}

	protected String getLogoImageString(String username, String customerId) throws PhrescoException {
		String encodeImg = "";
		try {
			InputStream fileInputStream = null;
			fileInputStream = getServiceManager(username).getIcon(customerId);
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

	private NodeConfiguration getNodeConfig(String rootModulePath, String subModule) throws PhrescoException {
		BufferedReader reader = null;
		NodeConfiguration nodeConfig = null;
		try {
//			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
//			String functionalTestDir = frameworkUtil.getFunctionalTestDir(FrameworkServiceUtil.getApplicationInfo(getAppDirName()));
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModule);
			File testFolderLocation = Utility.getTestFolderLocation(projectInfo, rootModulePath, subModule);
			String functionalTestDir = FrameworkServiceUtil.getFunctionalTestDir(rootModulePath, subModule);
			StringBuilder sb = new StringBuilder(testFolderLocation.getPath());
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

	private HubConfiguration getHubConfig(String rootModulePath, String subModule) throws PhrescoException {
		BufferedReader reader = null;
		HubConfiguration hubConfig = null;
		try {
//			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModule);
			File testFolderLocation = Utility.getTestFolderLocation(projectInfo, rootModulePath, subModule);
			String functionalTestDir = FrameworkServiceUtil.getFunctionalTestDir(rootModulePath, subModule);
//			String functionalTestDir = frameworkUtil.getFunctionalTestDir(FrameworkServiceUtil.getApplicationInfo(getAppDirName()));
			StringBuilder sb = new StringBuilder(testFolderLocation.getPath());
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
	private void createAggregationTagInPom(Document doc, List<Element> configList, List<MinifyInfo> files,
			String dynamicIncludeDir) throws PhrescoException {
		Element aggregationsElement = doc.createElement(POM_AGGREGATIONS);
		String applnFolderName = StringUtils.isNotEmpty(getModule()) ? getModule() : getAppDirName();
		for (MinifyInfo file : files) {
			String newFileName = ""; 
			String extension = "";
			String csvJsFile = file.getCsvFileName();
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
			String location = file.getOpFileLoc();
			String[] splitted = location.split(applnFolderName);
			String minificationDir = splitted[1];
			appendChildElement(doc, agrigationElement, POM_OUTPUT, MINIFY_OUTPUT_DIRECTORY + minificationDir.replace("\\", "/") + file.getCompressName() + DOT_MIN_DOT + file.getFileType());
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
	
	public String jsonWriterForCi(@Context HttpServletRequest request ,PerformanceUrls performanceUrls) throws PhrescoException {
		FileWriter fw = null;
		String property = "";
		String module = request.getParameter("moduleName");
		String appDirName = request.getParameter("appDirName");
//		if (StringUtils.isNotEmpty(module)) {
//			appDirName = appDirName + File.separator + module;
//		}
		String rootModulePath = "";
		String subModuleName = "";
		if (StringUtils.isNotEmpty(module)) {
			rootModulePath = Utility.getProjectHome() + appDirName;
			subModuleName = module;
		} else {
			rootModulePath = Utility.getProjectHome() + appDirName;
		}
		File pomFile = Utility.getpomFileLocation(rootModulePath, module);
		ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, subModuleName);
		File testFolderLocation = Utility.getTestFolderLocation(projectInfo, rootModulePath, module);
		try {
			if(REQ_PARAMETERS.equalsIgnoreCase(request.getParameter("testBasis")) && StringUtils.isNotEmpty("testAgainst")
					|| (StringUtils.isEmpty(request.getParameter("testBasis")) && StringUtils.isNotEmpty("testAgainst"))) {
				if (LOAD.equals(request.getParameter("testAction"))) {
					property = POM_PROP_KEY_LOADTEST_DIR;
				} else {
					property = POM_PROP_KEY_PERFORMANCETEST_DIR;					
				}
//				ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
//				File pomFile = getPOMFile(applicationInfo, appDirName);
				PomProcessor processor = new PomProcessor(pomFile);					
		        String performTestDir = processor.getProperty(property);	        
				FileOutputStream fop;
				boolean success = false;
				StringBuilder filepath = new StringBuilder(testFolderLocation.getPath())
				.append(performTestDir)
				.append(File.separator)
				.append(request.getParameter("testAgainst"))
				.append(File.separator)
				.append(Constants.FOLDER_JSON);
				
				success = new File(filepath.toString()).mkdirs();
				filepath.append(File.separator)
				.append(request.getParameter("testName"))
				.append(DOT_JSON);
				File file = new File(filepath.toString());
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				Gson gson = new Gson();
				String string = gson.toJson(performanceUrls).toString();
				byte[] contentInBytes = string.getBytes();				 
				fop.write(contentInBytes);
				fop.flush();
				fop.close();				
									
				StringBuilder infofilepath = new StringBuilder(testFolderLocation.getPath())
				.append(performTestDir)
				.append(File.separator)
				.append(request.getParameter("testAgainst"))
				.append(File.separator)
				.append(Constants.FOLDER_JSON)				
				.append(File.separator)
				.append("ci")
				.append(".info");					
				file = new File(infofilepath.toString());	
				if (!file.exists()) {
					file.createNewFile();
					fw = new FileWriter(file);
					fw.write(request.getParameter("testName")+DOT_JSON);
				} else {
					fw = new FileWriter(infofilepath.toString(),true);
					StringBuilder jsonFileName = new StringBuilder()
					.append(",")
					.append(request.getParameter("testName"));
					fw.write(jsonFileName.toString()+DOT_JSON);						
				}
			}	
		} catch (Exception e) {	
			throw new PhrescoException(e);
		} finally {
			Utility.closeStream(fw);
		}
		return SUCCESS;
	}
	
	public String jsonWriter(PerformanceUrls performanceUrls, String rootModulePath, String module, ProjectInfo projectInfo) throws PhrescoException {
		FileWriter fw = null;
		String property = "";
		try {
			if(REQ_PARAMETERS.equalsIgnoreCase(request.getParameter("testBasis")) && StringUtils.isNotEmpty("testAgainst")
					|| (StringUtils.isEmpty(request.getParameter("testBasis")) && StringUtils.isNotEmpty("testAgainst"))) {
				if (LOAD.equals(request.getParameter("testAction"))) {
					property = POM_PROP_KEY_LOADTEST_DIR;
				} else {
					property = POM_PROP_KEY_PERFORMANCETEST_DIR;					
				}
				
//				ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
				File pomFile = Utility.getpomFileLocation(rootModulePath, module);
				File testFolderLocation = Utility.getTestFolderLocation(projectInfo, rootModulePath, module);
				PomProcessor processor = new PomProcessor(pomFile);					
		        String performTestDir = processor.getProperty(property);	        
				FileOutputStream fop;
				StringBuilder filepath = new StringBuilder(testFolderLocation.getPath())
				.append(performTestDir)
				.append(File.separator)
				.append(request.getParameter("testAgainst"))
				.append(File.separator)
				.append(Constants.FOLDER_JSON);			
				if (new File(filepath.toString()).exists()) {
					filepath.append(File.separator)
					.append(request.getParameter("testName"))
					.append(DOT_JSON);				
					File file = new File(filepath.toString());
					fop = new FileOutputStream(file);
					if (!file.exists()) {
						file.createNewFile();
					}
					Gson gson = new Gson();
					String string = gson.toJson(performanceUrls).toString();
					byte[] contentInBytes = string.getBytes();				 
					fop.write(contentInBytes);
					fop.flush();
					fop.close();				
				}
			}
		} catch (Exception e) {	
			throw new PhrescoException(e);
		} finally {
			Utility.closeStream(fw);
		}
		return SUCCESS;
	}

/*	private File getPOMFile(ApplicationInfo appInfo, String appDirName) throws PhrescoException {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(appDirName);
		String pomFile = Utility.getPhrescoPomFromWorkingDirectory(appInfo, new File(builder.toString()));
		builder.append(File.separatorChar).append(pomFile);
		return new File(builder.toString());
	}*/


	private BufferedInputStream handleStopServer(boolean readData) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Build.handleStopServer()");
		}

		BufferedInputStream reader = null;
		try {
			String rootModulePath = getAppDirBasedOnMultiModule(getModule());
			File pomFileLocation = Utility.getpomFileLocation(rootModulePath, "");
			ProjectInfo projectInfo = Utility.getProjectInfo(rootModulePath, "");
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			String workingDirectory = Utility.getWorkingDirectoryPath(getAppDirName());
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			reader = applicationManager.performAction(projectInfo, ActionType.STOPSERVER, buildArgCmds, pomFileLocation.getParent());
			
			if (readData) {
				int available = reader.available();
				while (available != 0) {
					byte[] buf = new byte[available];
	                int read = reader.read(buf);
	                if (read == -1 ||  buf[available-1] == -1) {
	                	break;
	                } else {
	                	// 
	                }
	                available = reader.available();
				}
			}
			
			deleteLogFile(pomFileLocation.getParent());
		} catch (Exception e) {
			S_LOGGER.error("Entered into catch block of Build.handleStopServer()"
					+ FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException("Exception occured in the MavenFunctions.handleStopServer process");
		}

		return reader;
	}


	private BufferedReader compileSource(String workingDir) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method Build.compileSource()");
		}

		BufferedReader reader = null;
		try {
			Commandline cl = new Commandline("mvn clean compile");
//			String projectDir = Utility.getProjectHome() + directory;
			cl.setWorkingDirectory(workingDir);
			Process process = cl.execute();
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			writeLog(reader, workingDir);
			reader = new BufferedReader(new FileReader(getLogFilePath(workingDir)));
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

	private void writeLog(BufferedReader in, String directory) throws PhrescoException {
		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			String logFolderPath = getLogFolderPath(directory);
			File logfolder = new File(logFolderPath);
			if (!logfolder.exists()) {
				logfolder.mkdirs();
			}
			fstream = new FileWriter(getLogFilePath(directory));
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


	private BufferedInputStream startServer(boolean isRunAgainst, String workingDir, String rooModulePath, ProjectInfo projectInfo) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method MavenFunctions.startServer()");
		}
		BufferedInputStream reader = null;
		Parameter parameter = null;
		MojoProcessor mojo = null;
		try {
			// TODO: delete the server.log and create empty server.log file
			deleteLogFile(workingDir);
			if (isRunAgainst) {
				mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_RUNGAINST_SRC_START, rooModulePath, getModule())));
				parameter = mojo.getParameter(PHASE_RUNGAINST_SRC_START, "executeSql");
				String execValue = parameter.getValue();
				if (execValue.equals("true")) {
					parameter.setValue("false");
					mojo.save();
				}
			}
			// TODO: delete the server.log and create empty server.log file
			deleteLogFile(workingDir);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
//			ProjectInfo projectInfo = FrameworkServiceUtil.getProjectInfo(directory);
//			String workingDirectory = Utility.getWorkingDirectoryPath(getAppDirName());
			List<String> buildArgCmds = new ArrayList<String>();
			buildArgCmds.add(HYPHEN_N);
			appendMultiModuleCommand(getModule(), buildArgCmds);
			reader = applicationManager.performAction(projectInfo, ActionType.RUNAGAINSTSOURCE, buildArgCmds, workingDir);

		} catch (PhrescoException e) {
			if (isDebugEnabled) {
				S_LOGGER.error("Entered into catch block of Build.startServer()" + FrameworkUtil.getStackTraceAsString(e));
			}
			throw new PhrescoException(e);
		}
		return reader;
	}

	public void deleteLogFile(String workingDir) throws PhrescoException {
		try {
			File logFile = new File(getLogFilePath(workingDir));
			File infoFile = new File(getLogFolderPath(workingDir) + File.separator + RUN_AGS_LOG_FILE);
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

	private String getLogFilePath(String workingDir) throws PhrescoException {
		StringBuilder builder = new StringBuilder(getLogFolderPath(workingDir));
		builder.append(File.separator);
		builder.append(LOG_FILE);
		return builder.toString();
	}

	private String getLogFolderPath(String directory) throws PhrescoException {
		StringBuilder builder = new StringBuilder(directory);
		builder.append(File.separator);
		builder.append(DO_NOT_CHECKIN_DIR);
		builder.append(File.separator);
		builder.append(LOG_DIR);
		return builder.toString();
	}


	private List<com.photon.phresco.configuration.Configuration> getConfiguration(String directory, String environmentName,
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
			configManager = new ConfigManagerImpl(new File(getAppConfigPath(directory)));
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

	public String getAppConfigPath(String directory) throws PhrescoException {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(directory);
		builder.append(File.separator);
		builder.append(FOLDER_DOT_PHRESCO);
		builder.append(File.separator);
		builder.append(CONFIGURATION_INFO_FILE_NAME);
		return builder.toString();
	}


	/*private String readRunAgainstInfo(String directory) throws PhrescoException {
		String env = null;
		BufferedReader reader = null;
		try {
			ConfigurationInfo info = new ConfigurationInfo();
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append(directory);
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
*/



	public String getPhrescoPluginInfoFilePath(String goal, String rootModulePath, String module) throws PhrescoException {
		String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, module);
		StringBuilder sb = new StringBuilder(dotPhrescoFolderPath);
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
		String directory = "";
		if (StringUtils.isNotEmpty(getModule())) {
			directory = getAppDirName() + File.separator + getModule();
		} else {
			directory = getAppDirName();
		}
		builder.append(directory);
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
	
	protected ApplicationProcessor getApplicationProcessor(String username, String rootModulePath, String module) throws PhrescoException {
		ApplicationProcessor applicationProcessor = null;
		try {
			//doLogin();
			Customer customer = getServiceManager(username).getCustomer(getCustomerId());
			RepoInfo repoInfo = customer.getRepoInfo();
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, module);
			StringBuilder sb = new StringBuilder(dotPhrescoFolderPath)
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
			throw new PhrescoException(e);
		}catch (Exception e) {
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
	
	private void appendMultiModuleCommand(String module, List<String> buildArgCmds) {
		if (StringUtils.isNotEmpty(module)) {
			buildArgCmds.add(DMODULE_NAME + module);
		}
	}
	
	private String getAppDirBasedOnMultiModule(String module) {
		String rootModulePath = "";
		if (StringUtils.isNotEmpty(module)) {
			rootModulePath = Utility.getProjectHome() + getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + getAppDirName();
		}
		return rootModulePath;
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
	public String getIsFromCI() {
		return isFromCI;
	}
	public void setIsFromCI(String isFromCI) {
		this.isFromCI = isFromCI;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public void setTestAction(String testAction) {
		this.testAction = testAction;
	}

	public String getTestAction() {
		return testAction;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTestBasis() {
		return testBasis;
	}

	public void setTestBasis(String testBasis) {
		this.testBasis = testBasis;
	}

	public String getAppDirName() {
		return appDirName;
	}

	public void setAppDirName(String appDirName) {
		this.appDirName = appDirName;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModule() {
		return module;
	}

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
	/**
	 * The Class XmlNameFileFilter.
	 */
	public class XmlNameFileFilter implements FilenameFilter {
		
		/** The filter_. */
		private String filter_;

		/**
		 * Instantiates a new xml name file filter.
		 *
		 * @param filter the filter
		 */
		public XmlNameFileFilter(String filter) {
			filter_ = filter;
		}
		
		public boolean accept(File dir, String name) {
			return name.endsWith(filter_);
		}
	}




}

