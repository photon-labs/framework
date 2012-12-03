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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.util.Utility;
import com.phresco.pom.site.ReportCategories;
import com.phresco.pom.site.Reports;
import com.phresco.pom.util.SiteConfigurator;

public class SiteReport extends FrameworkBaseAction {
	private static final long serialVersionUID = 1L;
	private static final Logger S_LOGGER = Logger.getLogger(SiteReport.class);
	private static Boolean s_debugEnabled = S_LOGGER.isDebugEnabled();
	
	private String SITE_REPORT_PATH = "/do_not_checkin/target/site/index.html";
	
	private List<String> reports = new ArrayList<String>(8);
    
	public String viewSiteReport() {
		if (s_debugEnabled) {
			S_LOGGER.debug("Entering Method  SiteReport.viewSiteReport()");
		}
		
		try {
		    removeSessionAttribute(getAppId() + SESSION_APPINFO);//To remove the appInfo from the session
		    List<Reports> selectedReports = getPomReports(getApplicationInfo());
		    setReqAttribute(REQ_SITE_SLECTD_REPORTS, selectedReports);
		} catch (PhrescoException e) {
			return showErrorPopup(e,  getText(EXCEPTION_REPORT_VIEW_SITE));
		}
		
		return APP_SITE_REPORT_VIEW;
	}

	public String checkForSiteReport() throws UnknownHostException {
		if (s_debugEnabled) {
			S_LOGGER.debug("Entering Method  SiteReport.checkForSiteReport()");
		}
		
		try {
			String appDirName =getApplicationInfo().getAppDirName();
			Properties sysProps = System.getProperties();
	        S_LOGGER.debug( "Phresco FileServer Value of " + PHRESCO_FILE_SERVER_PORT_NO + " is " + sysProps.getProperty(PHRESCO_FILE_SERVER_PORT_NO) );
	        String phrescoFileServerNumber = sysProps.getProperty(PHRESCO_FILE_SERVER_PORT_NO);
        	StringBuilder siteReportPath = new StringBuilder(Utility.getProjectHome());
        	siteReportPath.append(appDirName);
        	siteReportPath.append(SITE_REPORT_PATH);
            File indexPath = new File(siteReportPath.toString());
            if (indexPath.isFile() && StringUtils.isNotEmpty(phrescoFileServerNumber)) {
         		StringBuilder sb = new StringBuilder();
            	sb.append(HTTP_PROTOCOL);
            	sb.append(PROTOCOL_POSTFIX);
            	InetAddress thisIp = InetAddress.getLocalHost();
            	sb.append(thisIp.getHostAddress());
            	sb.append(COLON);
            	sb.append(phrescoFileServerNumber);
            	sb.append(FORWARD_SLASH);
            	sb.append(appDirName);
            	sb.append(SITE_REPORT_PATH);
            	setReqAttribute(REQ_SITE_REPORT_PATH, sb.toString());
         	} else {
         		setReqAttribute(REQ_ERROR, false);
         	}
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of SiteReport.checkForSiteReport()" + FrameworkUtil.getStackTraceAsString(e));
			return showErrorPopup(e,  getText(EXCEPTION_REPORT_VIEW_SITE));
		}
		
		return APP_SITE_REPORT_VIEW;
	}
	
	public String generateSiteReport() {
		if (s_debugEnabled) {
			S_LOGGER.debug("Entering Method  SiteReport.generateReport()");
		}
		
		try {
			ActionType actionType = null;
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			ProjectInfo projectInfo = projectManager.getProject(getProjectId(), getCustomerId());
			actionType = ActionType.SITE_REPORT;
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ApplicationInfo applicationInfo = getApplicationInfo();
			String appDirectoryPath = getAppDirectoryPath(applicationInfo);
			BufferedReader reader = applicationManager.performAction(projectInfo, actionType, null, appDirectoryPath);
			setSessionAttribute(getAppId() + REQ_SITE_REPORT, reader);
			setReqAttribute(REQ_APP_ID, getAppId());
			setReqAttribute(REQ_ACTION_TYPE, REQ_SITE_REPORT);
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of SiteReport.generateSiteReport()"
					+ FrameworkUtil.getStackTraceAsString(e));
			return showErrorPopup(e,  getText(EXCEPTION_REPORT_GENERATE_SITE_REPORT));
		}
		
		return APP_ENVIRONMENT_READER;
	}
	
	public String configure() {
		if (s_debugEnabled) {
			S_LOGGER.debug("Entering Method  SiteReport.configure()");
		}
		
		try {
			String techId = getTechId();
			ApplicationInfo appInfo = getApplicationInfo();
			List<Reports> reports = getServiceManager().getReports(techId);
			setReqAttribute(REQ_SITE_REPORTS, reports);
			List<Reports> selectedReports = getPomReports(appInfo);
			setReqAttribute(REQ_SITE_REPORTS, reports);
			setReqAttribute(REQ_SITE_SLECTD_REPORTS, selectedReports);
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of SiteReport.configure()"
					+ FrameworkUtil.getStackTraceAsString(e));
			return showErrorPopup(e,  getText(EXCEPTION_REPORT_CONFIGURE));
		}
		
		return APP_SITE_REPORT_CONFIGURE;
	}
	
	public String createReportConfig() {
		if (s_debugEnabled) {	
			S_LOGGER.debug("Entering Method  SiteReport.createReportConfig()");
		}
		
		try {
			//To get the selected reports from the UI
			List<String> selectedReports = getReports();
			
			//To get the selected ReportCategories from the UI
			String[] arraySelectedRptCategories = getHttpRequest().getParameterValues(REQ_SITE_SLECTD_REPORTSCATEGORIES);
			List<ReportCategories> selectedReportCategories = new ArrayList<ReportCategories>();
			if (!ArrayUtils.isEmpty(arraySelectedRptCategories)) {
				for (String arraySelectedRptCategory : arraySelectedRptCategories) {
					ReportCategories cat = new ReportCategories();
					cat.setName(arraySelectedRptCategory);
					selectedReportCategories.add(cat);
				}
			}
			
			// To get the list of Reports to be added
			String techId = getTechId();
			List<Reports> allReports = getServiceManager().getReports(techId);
			List<Reports> reportsToBeAdded = new ArrayList<Reports>();
			if (CollectionUtils.isNotEmpty(selectedReports) && CollectionUtils.isNotEmpty(allReports)) {
				for (Reports report : allReports) {
					if (selectedReports.contains(report.getArtifactId())) {
						reportsToBeAdded.add(report);
					}
				}
			}
			updateRptPluginInPOM(getApplicationInfo(), reportsToBeAdded, selectedReportCategories);
			addActionMessage(getText(SUCCESS_SITE_CONFIGURE));
		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of SiteReport.createReportConfig()"
					+ FrameworkUtil.getStackTraceAsString(e));
			return showErrorPopup(e,  getText(EXCEPTION_REPORT_CREATE_REPORT_CONFIG));
		}
		
		return viewSiteReport();
	}
	
	private void updateRptPluginInPOM(ApplicationInfo appInfo, List<Reports> reports, List<ReportCategories> reportCategories) throws PhrescoException {
		try {
			SiteConfigurator configurator = new SiteConfigurator();
			File file = new File(getAppPom());
			configurator.addReportPlugin(reports, reportCategories, file);
		} catch (Exception e) {
			throw new PhrescoException();
		}
	}

	private List<Reports> getPomReports(ApplicationInfo appInfo) throws PhrescoException {
		try {
			SiteConfigurator configurator = new SiteConfigurator();
			File file = new File(getAppPom());
			List<Reports> reports = configurator.getReports(file);
			
			return reports;
		} catch (PhrescoException ex) {
			throw new PhrescoException(ex);
		}
	}

	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
	}
}