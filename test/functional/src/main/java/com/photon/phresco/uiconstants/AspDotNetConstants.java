package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AspDotNetConstants {

	private Log log = LogFactory.getLog("AspDotNetConstants");

	/**
	 * Create Project
	 */
	private String aspDotnetArchetypeName = "aspDotnetArchetypeName";
	private String aspDotnetArchetypeDesc = "aspDotnetArchetypeDesc";
	private String aspDotnetArchetypeAppCode = "aspDotnetArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";

	/**
	 * Edit Application
	 */
	private String aspDotnetArchetypeEditApp = "aspDotnetArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String editProject = "editProject";

	/**
	 * Edit Application Desc
	 */
	private String appEditDesc = "appEditDesc";

	/**
	 * Configuration Server
	 */
	private String configServer = "configServer";
	private String configServerDesc = "configServerDesc";
	private String configServerProtocol = "configServerProtocol";
	private String configServerHost = "configServerHost";
	private String configServerPort = "configServerPort";
	private String configServerUName = "configServerUName";
	private String configServerPwd = "configServerPwd";
	private String configServerCertificate = "configServerCertificate";
	private String configServerDeployDir = "configServerDeployDir";
	private String configServerContext = "configServerContext";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */

	private String aspDotnetArchetypePdfReportIcon = "aspDotnetArchetypePdfReportIcon";
	private String aspDotnetArchetypeOverAllReportName = "aspDotnetArchetypeOverAllReportName";
	private String aspDotnetArchetypeDetailReportName = "aspDotnetArchetypeDetailReportName";

	public AspDotNetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadAspDotNetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in AspDotNetConstants::"
					+ localException.getMessage());
		}
	}

	public String getAspDotnetArchetypeName() {
		return aspDotnetArchetypeName;
	}

	public void setAspDotnetArchetypeName(String aspDotnetArchetypeName) {
		this.aspDotnetArchetypeName = aspDotnetArchetypeName;
	}

	public String getAspDotnetArchetypeDesc() {
		return aspDotnetArchetypeDesc;
	}

	public void setAspDotnetArchetypeDesc(String aspDotnetArchetypeDesc) {
		this.aspDotnetArchetypeDesc = aspDotnetArchetypeDesc;
	}

	public String getAspDotnetArchetypeAppCode() {
		return aspDotnetArchetypeAppCode;
	}

	public void setAspDotnetArchetypeAppCode(String aspDotnetArchetypeAppCode) {
		this.aspDotnetArchetypeAppCode = aspDotnetArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getVersionValue() {
		return versionValue;
	}

	public void setVersionValue(String versionValue) {
		this.versionValue = versionValue;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAspDotnetArchetypeEditApp() {
		return aspDotnetArchetypeEditApp;
	}

	public void setAspDotnetArchetypeEditApp(String aspDotnetArchetypeEditApp) {
		this.aspDotnetArchetypeEditApp = aspDotnetArchetypeEditApp;
	}

	public String getEditProject() {
		return editProject;
	}

	public void setEditProject(String editProject) {
		this.editProject = editProject;
	}

	public String getAppEditDesc() {
		return appEditDesc;
	}

	public void setAppEditDesc(String appEditDesc) {
		this.appEditDesc = appEditDesc;
	}

	public String getConfigServer() {
		return configServer;
	}

	public void setConfigServer(String configServer) {
		this.configServer = configServer;
	}

	public String getConfigServerDesc() {
		return configServerDesc;
	}

	public void setConfigServerDesc(String configServerDesc) {
		this.configServerDesc = configServerDesc;
	}

	public String getConfigServerProtocol() {
		return configServerProtocol;
	}

	public void setConfigServerProtocol(String configServerProtocol) {
		this.configServerProtocol = configServerProtocol;
	}

	public String getConfigServerHost() {
		return configServerHost;
	}

	public void setConfigServerHost(String configServerHost) {
		this.configServerHost = configServerHost;
	}

	public String getConfigServerPort() {
		return configServerPort;
	}

	public void setConfigServerPort(String configServerPort) {
		this.configServerPort = configServerPort;
	}

	public String getConfigServerUName() {
		return configServerUName;
	}

	public void setConfigServerUName(String configServerUName) {
		this.configServerUName = configServerUName;
	}

	public String getConfigServerPwd() {
		return configServerPwd;
	}

	public void setConfigServerPwd(String configServerPwd) {
		this.configServerPwd = configServerPwd;
	}

	public String getConfigServerCertificate() {
		return configServerCertificate;
	}

	public void setConfigServerCertificate(String configServerCertificate) {
		this.configServerCertificate = configServerCertificate;
	}

	public String getConfigServerDeployDir() {
		return configServerDeployDir;
	}

	public void setConfigServerDeployDir(String configServerDeployDir) {
		this.configServerDeployDir = configServerDeployDir;
	}

	public String getConfigServerContext() {
		return configServerContext;
	}

	public void setConfigServerContext(String configServerContext) {
		this.configServerContext = configServerContext;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getAspDotnetArchetypePdfReportIcon() {
		return aspDotnetArchetypePdfReportIcon;
	}

	public void setAspDotnetArchetypePdfReportIcon(
			String aspDotnetArchetypePdfReportIcon) {
		this.aspDotnetArchetypePdfReportIcon = aspDotnetArchetypePdfReportIcon;
	}

	public String getAspDotnetArchetypeOverAllReportName() {
		return aspDotnetArchetypeOverAllReportName;
	}

	public void setAspDotnetArchetypeOverAllReportName(
			String aspDotnetArchetypeOverAllReportName) {
		this.aspDotnetArchetypeOverAllReportName = aspDotnetArchetypeOverAllReportName;
	}

	public String getAspDotnetArchetypeDetailReportName() {
		return aspDotnetArchetypeDetailReportName;
	}

	public void setAspDotnetArchetypeDetailReportName(
			String aspDotnetArchetypeDetailReportName) {
		this.aspDotnetArchetypeDetailReportName = aspDotnetArchetypeDetailReportName;
	}

}