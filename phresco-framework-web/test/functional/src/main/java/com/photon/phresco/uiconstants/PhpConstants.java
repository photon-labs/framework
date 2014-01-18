package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhpConstants {

	private Log log = LogFactory.getLog("PhpConstants");

	/**
	 * Create Project
	 */
	private String phpArchetypeName = "phpArchetypeName";
	private String phpArchetypeDesc = "phpArchetypeDesc";
	private String phpArchetypeAppCode = "phpArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";

	/**
	 * Edit App
	 */
	private String phpArchetypeEditApp = "phpArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String editProject = "editProject";

	/**
	 * Edit App Desc
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
	 * Configuration Database
	 */
	private String configDb = "configDb";
	private String configDbDesc = "configDbDesc";
	private String configDbHost = "configDbHost";
	private String configDbPort = "configDbPort";
	private String configDbUsername = "configDbUsername";
	private String configDbPwd = "configDbPwd";
	private String configDbName = "configDbName";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String phpArchetypePdfReportIcon = "phpArchetypePdfReportIcon";
	private String phpArchetypeOverAllReportName = "phpArchetypeOverAllReportName";
	private String phpArchetypeDetailReportName = "phpArchetypeDetailReportName";

	public PhpConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadPhpConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in PhpConstants::"
					+ localException.getMessage());
		}
	}

	public String getPhpArchetypeName() {
		return phpArchetypeName;
	}

	public String getPhpArchetypeDesc() {
		return phpArchetypeDesc;
	}

	public String getPhpArchetypeAppCode() {
		return phpArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public String getVersionValue() {
		return versionValue;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getPhpArchetypeEditApp() {
		return phpArchetypeEditApp;
	}

	public String getEditProject() {
		return editProject;
	}

	public String getAppEditDesc() {
		return appEditDesc;
	}

	public String getConfigServer() {
		return configServer;
	}

	public String getConfigServerDesc() {
		return configServerDesc;
	}

	public String getConfigServerProtocol() {
		return configServerProtocol;
	}

	public String getConfigServerHost() {
		return configServerHost;
	}

	public String getConfigServerPort() {
		return configServerPort;
	}

	public String getConfigServerUName() {
		return configServerUName;
	}

	public String getConfigServerPwd() {
		return configServerPwd;
	}

	public String getConfigServerCertificate() {
		return configServerCertificate;
	}

	public String getConfigServerDeployDir() {
		return configServerDeployDir;
	}

	public String getConfigServerContext() {
		return configServerContext;
	}

	public String getConfigDb() {
		return configDb;
	}

	public String getConfigDbDesc() {
		return configDbDesc;
	}

	public String getConfigDbHost() {
		return configDbHost;
	}

	public String getConfigDbPort() {
		return configDbPort;
	}

	public String getConfigDbUsername() {
		return configDbUsername;
	}

	public String getConfigDbPwd() {
		return configDbPwd;
	}

	public String getConfigDbName() {
		return configDbName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setPhpArchetypeName(String phpArchetypeName) {
		this.phpArchetypeName = phpArchetypeName;
	}

	public void setPhpArchetypeDesc(String phpArchetypeDesc) {
		this.phpArchetypeDesc = phpArchetypeDesc;
	}

	public void setPhpArchetypeAppCode(String phpArchetypeAppCode) {
		this.phpArchetypeAppCode = phpArchetypeAppCode;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public void setVersionValue(String versionValue) {
		this.versionValue = versionValue;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setPhpArchetypeEditApp(String phpArchetypeEditApp) {
		this.phpArchetypeEditApp = phpArchetypeEditApp;
	}

	public void setEditProject(String editProject) {
		this.editProject = editProject;
	}

	public void setAppEditDesc(String appEditDesc) {
		this.appEditDesc = appEditDesc;
	}

	public void setConfigServer(String configServer) {
		this.configServer = configServer;
	}

	public void setConfigServerDesc(String configServerDesc) {
		this.configServerDesc = configServerDesc;
	}

	public void setConfigServerProtocol(String configServerProtocol) {
		this.configServerProtocol = configServerProtocol;
	}

	public void setConfigServerHost(String configServerHost) {
		this.configServerHost = configServerHost;
	}

	public void setConfigServerPort(String configServerPort) {
		this.configServerPort = configServerPort;
	}

	public void setConfigServerUName(String configServerUName) {
		this.configServerUName = configServerUName;
	}

	public void setConfigServerPwd(String configServerPwd) {
		this.configServerPwd = configServerPwd;
	}

	public void setConfigServerCertificate(String configServerCertificate) {
		this.configServerCertificate = configServerCertificate;
	}

	public void setConfigServerDeployDir(String configServerDeployDir) {
		this.configServerDeployDir = configServerDeployDir;
	}

	public void setConfigServerContext(String configServerContext) {
		this.configServerContext = configServerContext;
	}

	public void setConfigDb(String configDb) {
		this.configDb = configDb;
	}

	public void setConfigDbDesc(String configDbDesc) {
		this.configDbDesc = configDbDesc;
	}

	public void setConfigDbHost(String configDbHost) {
		this.configDbHost = configDbHost;
	}

	public void setConfigDbPort(String configDbPort) {
		this.configDbPort = configDbPort;
	}

	public void setConfigDbUsername(String configDbUsername) {
		this.configDbUsername = configDbUsername;
	}

	public void setConfigDbPwd(String configDbPwd) {
		this.configDbPwd = configDbPwd;
	}

	public void setConfigDbName(String configDbName) {
		this.configDbName = configDbName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getPhpArchetypePdfReportIcon() {
		return phpArchetypePdfReportIcon;
	}

	public void setPhpArchetypePdfReportIcon(String phpArchetypePdfReportIcon) {
		this.phpArchetypePdfReportIcon = phpArchetypePdfReportIcon;
	}

	public String getPhpArchetypeOverAllReportName() {
		return phpArchetypeOverAllReportName;
	}

	public void setPhpArchetypeOverAllReportName(
			String phpArchetypeOverAllReportName) {
		this.phpArchetypeOverAllReportName = phpArchetypeOverAllReportName;
	}

	public String getPhpArchetypeDetailReportName() {
		return phpArchetypeDetailReportName;
	}

	public void setPhpArchetypeDetailReportName(
			String phpArchetypeDetailReportName) {
		this.phpArchetypeDetailReportName = phpArchetypeDetailReportName;
	}

}