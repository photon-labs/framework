/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.framework.actions.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.Constants;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.Profile;
import com.phresco.pom.util.PomProcessor;

public class Code extends DynamicParameterAction implements Constants {
	private static final long serialVersionUID = 8217209827121703596L;
    private static final Logger S_LOGGER = Logger.getLogger(Code.class);
    private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
    
	private String skipTest = null;
    private String codeTechnology = null;
    private String report = null;
    private String validateAgainst = null;
	private String target = null;
	
	public String code() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Code.code()");
		}
		String serverUrl = "";
		try {
        	setReqAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        	ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
        	ApplicationInfo applicationInfo = applicationManager.getApplicationInfo(getCustomerId(), getProjectId(), getAppId());
        	setReqAttribute(REQ_APP_INFO, applicationInfo);
        	
        	MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(applicationInfo)));
			Parameter parameter = mojo.getParameter(PHASE_VALIDATE_CODE, "sonar");
			setReqAttribute(REQ_PARAMETER, parameter);
			
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			serverUrl = frameworkUtil.getSonarURL();
			URL sonarURL = new URL(serverUrl);
			HttpURLConnection connection = null;
    	    try {
    	    	connection = (HttpURLConnection) sonarURL.openConnection();
    	    	int responseCode = connection.getResponseCode();
    	    	if (responseCode != 200) {
    	    		setReqAttribute(REQ_ERROR, getText(SONAR_NOT_STARTED));
                }
    	    } catch(Exception e) {
    	    	setReqAttribute(REQ_ERROR, getText(SONAR_NOT_STARTED));
    	    }
    	    
			// to get the Profile Id's from the POM
			StringBuilder builder = new StringBuilder(getApplicationHome());
			builder.append(File.separator);
			builder.append(POM_XML);
			File pomPath = new File(builder.toString());
			PomProcessor pomProcessor = new PomProcessor(pomPath);
			String validateReportUrl = pomProcessor.getProperty(PHRESCO_CODE_VALIDATE_REPORT);
			
			if (StringUtils.isNotEmpty(validateReportUrl)) {
				setReqAttribute(CHECK_IPHONE, validateReportUrl);
			}
			
			Model model = pomProcessor.getModel();
			Profiles modelProfiles = model.getProfiles();
			
			List<String> sonarTechReports = new ArrayList<String>();
			List<Profile> profiles = modelProfiles.getProfile();
			for (Profile profile : profiles) {
				if (profile.getProperties() != null) {
					List<Element> any = profile.getProperties().getAny();
					int size = any.size();
					for (int i = 0; i < size; ++i) {
						boolean tagExist = 	any.get(i).getTagName().equals(SONAR_LANGUAGE);
						if (tagExist){
							sonarTechReports.add(profile.getId());
						}
					}
				}
			}
			
			setReqAttribute(REQ_SONAR_TECH_REPORTS, sonarTechReports);
    	} catch (Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered into catch block of Code.code()"+ FrameworkUtil.getStackTraceAsString(e));
    		}
    		new LogErrorReport(e, "Code code()");
        }
		return APP_CODE;
	}
	
	public String check() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Code.check()");
		}
		StringBuilder sb = new StringBuilder();
    	try {
	        Properties sysProps = System.getProperties();
	        if (debugEnabled) {
	        	S_LOGGER.debug( "Phresco FileServer Value of " + PHRESCO_FILE_SERVER_PORT_NO + " is " + sysProps.getProperty(PHRESCO_FILE_SERVER_PORT_NO) );
			}
	        String phrescoFileServerNumber = sysProps.getProperty(PHRESCO_FILE_SERVER_PORT_NO);
	        
            FrameworkConfiguration frameworkConfig = PhrescoFrameworkFactory.getFrameworkConfig();
            ApplicationInfo applicationInfo = getApplicationInfo();
            
            StringBuilder builder = new StringBuilder(getApplicationHome());
            builder.append(File.separatorChar);
        	builder.append(POM_XML);
        	File pomPath = new File(builder.toString());
            PomProcessor processor = new PomProcessor(pomPath);
            String validateAgainst = getReqParameter(REQ_VALIDATE_AGAINST); //getHttpRequest().getParameter("validateAgainst");
            String validateReportUrl = processor.getProperty(PHRESCO_CODE_VALIDATE_REPORT);
            //Check whether iphone Technology or not
			if (StringUtils.isNotEmpty(validateReportUrl)) {
            	StringBuilder codeValidatePath = new StringBuilder(getApplicationHome());
            	codeValidatePath.append(validateReportUrl);
            	codeValidatePath.append(validateAgainst);
            	codeValidatePath.append(File.separatorChar);
            	
            	codeValidatePath.append(INDEX_HTML);
                File indexPath = new File(codeValidatePath.toString());
                S_LOGGER.debug("indexPath ..... " + indexPath);
             	if (indexPath.isFile() && StringUtils.isNotEmpty(phrescoFileServerNumber)) {
                	sb.append(HTTP_PROTOCOL);
                	sb.append(PROTOCOL_POSTFIX);
                	InetAddress thisIp =InetAddress.getLocalHost();
                	sb.append(thisIp.getHostAddress());
                	sb.append(COLON);
                	sb.append(phrescoFileServerNumber);
                	sb.append(FORWARD_SLASH);
                	sb.append(codeValidatePath.toString().replace(File.separator, FORWARD_SLASH));
             	} else {
             		setReqAttribute(REQ_ERROR, getText(FAILURE_CODE_REVIEW));
             	}
             	setReqAttribute(CHECK_IPHONE, validateReportUrl);
        	} else {
	        	String serverUrl = "";
	    	    if (StringUtils.isNotEmpty(frameworkConfig.getSonarUrl())) {
	    	    	serverUrl = frameworkConfig.getSonarUrl();
	    	    } else {
	    	    	serverUrl = getHttpRequest().getRequestURL().toString();
	    	    	StringBuilder tobeRemoved = new StringBuilder();
	    	    	tobeRemoved.append(getHttpRequest().getContextPath());
	    	    	tobeRemoved.append(getHttpRequest().getServletPath());
	
	    	    	Pattern pattern = Pattern.compile(tobeRemoved.toString());
	    	    	Matcher matcher = pattern.matcher(serverUrl);
	    	    	serverUrl = matcher.replaceAll("");
	    	    }
				StringBuilder reportPath = new StringBuilder(getApplicationHome());

				if (StringUtils.isNotEmpty(validateAgainst) && FUNCTIONALTEST.equals(validateAgainst)) {
					FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
					reportPath.append(frameworkUtil.getFunctionalTestDir(applicationInfo));
                }
				reportPath.append(File.separatorChar);
				reportPath.append(POM_XML);
				File file = new File(reportPath.toString());
				processor = new PomProcessor(file);
				String groupId = processor.getModel().getGroupId();
	        	String artifactId = processor.getModel().getArtifactId();
	        	sb.append(serverUrl);
	        	sb.append(frameworkConfig.getSonarReportPath());
	        	sb.append(groupId);
	        	sb.append(COLON);
	        	sb.append(artifactId);
	        	
	        	if (StringUtils.isNotEmpty(validateAgainst) && !REQ_SRC.equals(validateAgainst)) {
	        		sb.append(COLON);
	        		sb.append(validateAgainst);
	        	}
	    		try {
					URL sonarURL = new URL(sb.toString());
					HttpURLConnection connection = (HttpURLConnection) sonarURL.openConnection();
					int responseCode = connection.getResponseCode();
					if (debugEnabled) {
						S_LOGGER.debug("Url to access API ====> " + sb.toString());
	    				S_LOGGER.debug("Response code value " + responseCode);
		    		}
					if (responseCode != 200) {
						setReqAttribute(REQ_ERROR, getText(FAILURE_CODE_REVIEW));
					    return APP_CODE;
		            }
				} catch (Exception e) {
					if (debugEnabled) {
						S_LOGGER.error("Entered into catch block of Code.check()"+ FrameworkUtil.getStackTraceAsString(e));
		    		}
					new LogErrorReport(e, "Code review");
					setReqAttribute(REQ_ERROR, getText(FAILURE_CODE_REVIEW));
					return APP_CODE;
				}
        	}
    	} catch (Exception e) {
    		if (debugEnabled) {
    			S_LOGGER.error("Entered into catch block of Code.check()"+ FrameworkUtil.getStackTraceAsString(e));
    		}
    	}
    	setReqAttribute(REQ_SONAR_PATH, sb.toString());
        return APP_CODE;
    }
	
	public String showCodeValidatePopup() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Code.showCodeValidatePopup()");
		}
		try {
			ApplicationInfo applicationInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(applicationInfo)));
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_VALIDATE_CODE);
			setReqAttribute(REQ_DYNAMIC_PARAMETERS, parameters);
		} catch (PhrescoException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Code.showCodeValidatePopup()" + FrameworkUtil.getStackTraceAsString(e));
			}
		}
		return SHOW_CODE_VALIDATE_POPUP;
	}
	
	public String codeValidate() throws IOException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Code.codeValidate()");
		}
		try {
			ProjectInfo projectInfo = getProjectInfo();
			ApplicationInfo applicationInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(applicationInfo)));
			persistValuesToXml(mojo, PHASE_VALIDATE_CODE);
			
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_VALIDATE_CODE);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			
			BufferedReader reader = applicationManager.performAction(projectInfo, ActionType.CODE_VALIDATE, buildArgCmds, workingDirectory);
			setSessionAttribute(getAppId() + REQ_CODE, reader);
			setReqAttribute(REQ_APP_ID, getAppId());
			setReqAttribute(REQ_ACTION_TYPE, REQ_CODE);
		} catch (PhrescoException e) {
			e.printStackTrace();
		}
		
		return APP_ENVIRONMENT_READER;
	}
	
	public String getCodeTechnology() {
		return codeTechnology;
	}

	public void setCodeTechnology(String codeTechnology) {
		this.codeTechnology = codeTechnology;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getSkipTest() {
		return skipTest;
	}

	public void setSkipTest(String skipTest) {
		this.skipTest = skipTest;
	}
	
	public String getValidateAgainst() {
		return validateAgainst;
	}

	public void setValidateAgainst(String validateAgainst) {
		this.validateAgainst = validateAgainst;
	}
}