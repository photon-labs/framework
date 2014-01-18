package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Drupal7Constants {

	private Log log = LogFactory.getLog("Drupal7Constants");

	/**
	 * Create Project
	 */
	private String drupal7ArchetypeName = "drupal7ArchetypeName";
	private String drupal7ArchetypeDesc = "drupal7ArchetypeDesc";
	private String drupal7ArchetypeAppCode = "drupal7ArchetypeAppCode";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";
	private String technologyValue = "technologyValue";

	/**
	 * Edit Application
	 */
	private String drupal7ArchetypeEditApp = "drupal7ArchetypeEditApp";

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

	private String drupal7ArchetypePdfReportIcon = "drupal7ArchetypePdfReportIcon";
	private String drupal7ArchetypeOverAllReportName = "drupal7ArchetypeOverAllReportName";
	private String drupal7ArchetypeDetailReportName = "drupal7ArchetypeDetailReportName";

	public Drupal7Constants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadDrupal7Constants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in Drupal7Constants::"
					+ localException.getMessage());
		}
	}

	public String getDrupal7ArchetypeName() {
		return drupal7ArchetypeName;
	}

	public void setDrupal7ArchetypeName(String drupal7ArchetypeName) {
		this.drupal7ArchetypeName = drupal7ArchetypeName;
	}

	public String getDrupal7ArchetypeDesc() {
		return drupal7ArchetypeDesc;
	}

	public void setDrupal7ArchetypeDesc(String drupal7ArchetypeDesc) {
		this.drupal7ArchetypeDesc = drupal7ArchetypeDesc;
	}

	public String getDrupal7ArchetypeAppCode() {
		return drupal7ArchetypeAppCode;
	}

	public void setDrupal7ArchetypeAppCode(String drupal7ArchetypeAppCode) {
		this.drupal7ArchetypeAppCode = drupal7ArchetypeAppCode;
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

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getDrupal7ArchetypeEditApp() {
		return drupal7ArchetypeEditApp;
	}

	public void setDrupal7ArchetypeEditApp(String drupal7ArchetypeEditApp) {
		this.drupal7ArchetypeEditApp = drupal7ArchetypeEditApp;
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

	public String getConfigDb() {
		return configDb;
	}

	public void setConfigDb(String configDb) {
		this.configDb = configDb;
	}

	public String getConfigDbDesc() {
		return configDbDesc;
	}

	public void setConfigDbDesc(String configDbDesc) {
		this.configDbDesc = configDbDesc;
	}

	public String getConfigDbHost() {
		return configDbHost;
	}

	public void setConfigDbHost(String configDbHost) {
		this.configDbHost = configDbHost;
	}

	public String getConfigDbPort() {
		return configDbPort;
	}

	public void setConfigDbPort(String configDbPort) {
		this.configDbPort = configDbPort;
	}

	public String getConfigDbUsername() {
		return configDbUsername;
	}

	public void setConfigDbUsername(String configDbUsername) {
		this.configDbUsername = configDbUsername;
	}

	public String getConfigDbPwd() {
		return configDbPwd;
	}

	public void setConfigDbPwd(String configDbPwd) {
		this.configDbPwd = configDbPwd;
	}

	public String getConfigDbName() {
		return configDbName;
	}

	public void setConfigDbName(String configDbName) {
		this.configDbName = configDbName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getDrupal7ArchetypePdfReportIcon() {
		return drupal7ArchetypePdfReportIcon;
	}

	public void setDrupal7ArchetypePdfReportIcon(
			String drupal7ArchetypePdfReportIcon) {
		this.drupal7ArchetypePdfReportIcon = drupal7ArchetypePdfReportIcon;
	}

	public String getDrupal7ArchetypeOverAllReportName() {
		return drupal7ArchetypeOverAllReportName;
	}

	public void setDrupal7ArchetypeOverAllReportName(
			String drupal7ArchetypeOverAllReportName) {
		this.drupal7ArchetypeOverAllReportName = drupal7ArchetypeOverAllReportName;
	}

	public String getDrupal7ArchetypeDetailReportName() {
		return drupal7ArchetypeDetailReportName;
	}

	public void setDrupal7ArchetypeDetailReportName(
			String drupal7ArchetypeDetailReportName) {
		this.drupal7ArchetypeDetailReportName = drupal7ArchetypeDetailReportName;
	}
}
