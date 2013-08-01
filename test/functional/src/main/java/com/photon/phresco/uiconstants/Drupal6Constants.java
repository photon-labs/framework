package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Drupal6Constants {

	private Log log = LogFactory.getLog("Drupal6Constants");

	/**
	 * Create Project
	 */
	private String drupal6ArchetypeName = "drupal6ArchetypeName";
	private String drupal6ArchetypeDesc = "drupal6ArchetypeDesc";
	private String drupal6ArchetypeAppCode = "drupal6ArchetypeAppCode";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";
	private String technologyValue = "technologyValue";

	/**
	 * Edit Application
	 */
	private String drupal6ArchetypeEditApp = "drupal6ArchetypeEditApp";

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

	private String drupal6ArchetypePdfReportIcon = "drupal6ArchetypePdfReportIcon";
	private String drupal6ArchetypeOverAllReportName = "drupal6ArchetypeOverAllReportName";
	private String drupal6ArchetypeDetailReportName = "drupal6ArchetypeDetailReportName";

	public Drupal6Constants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadDrupal6Constants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in Drupal6Constants::"
					+ localException.getMessage());
		}
	}

	public String getDrupal6ArchetypeName() {
		return drupal6ArchetypeName;
	}

	public String getDrupal6ArchetypeDesc() {
		return drupal6ArchetypeDesc;
	}

	public String getDrupal6ArchetypeAppCode() {
		return drupal6ArchetypeAppCode;
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

	public String getTechnologyValue() {
		return technologyValue;
	}

	public String getDrupal6ArchetypeEditApp() {
		return drupal6ArchetypeEditApp;
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

	public void setDrupal6ArchetypeName(String drupal6ArchetypeName) {
		this.drupal6ArchetypeName = drupal6ArchetypeName;
	}

	public void setDrupal6ArchetypeDesc(String drupal6ArchetypeDesc) {
		this.drupal6ArchetypeDesc = drupal6ArchetypeDesc;
	}

	public void setDrupal6ArchetypeAppCode(String drupal6ArchetypeAppCode) {
		this.drupal6ArchetypeAppCode = drupal6ArchetypeAppCode;
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

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public void setDrupal6ArchetypeEditApp(String drupal6ArchetypeEditApp) {
		this.drupal6ArchetypeEditApp = drupal6ArchetypeEditApp;
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

	public String getDrupal6ArchetypePdfReportIcon() {
		return drupal6ArchetypePdfReportIcon;
	}

	public void setDrupal6ArchetypePdfReportIcon(
			String drupal6ArchetypePdfReportIcon) {
		this.drupal6ArchetypePdfReportIcon = drupal6ArchetypePdfReportIcon;
	}

	public String getDrupal6ArchetypeOverAllReportName() {
		return drupal6ArchetypeOverAllReportName;
	}

	public void setDrupal6ArchetypeOverAllReportName(
			String drupal6ArchetypeOverAllReportName) {
		this.drupal6ArchetypeOverAllReportName = drupal6ArchetypeOverAllReportName;
	}

	public String getDrupal6ArchetypeDetailReportName() {
		return drupal6ArchetypeDetailReportName;
	}

	public void setDrupal6ArchetypeDetailReportName(
			String drupal6ArchetypeDetailReportName) {
		this.drupal6ArchetypeDetailReportName = drupal6ArchetypeDetailReportName;
	}

}
