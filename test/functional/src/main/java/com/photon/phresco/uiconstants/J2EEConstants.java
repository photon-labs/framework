package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class J2EEConstants {

	private Log log = LogFactory.getLog("JavaWebserviceConstants");

	/**
	 * Create Project
	 */
	private String j2EEArchetypeName = "j2EEArchetypeName";
	private String j2EEArchetypeDesc = "j2EEArchetypeDesc";
	private String j2EEArchetypeAppCode = "j2EEArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";

	/**
	 * Edit Application
	 */
	private String j2EEArchetypeEditApp = "j2EEArchetypeEditApp";

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

	private String j2EEArchetypePdfReportIcon = "j2EEArchetypePdfReportIcon";
	private String j2EEArchetypeOverAllReportName = "j2EEArchetypeOverAllReportName";
	private String j2EEArchetypeDetailReportName = "j2EEArchetypeDetailReportName";

	public J2EEConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadJavaWebServiceConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in JavaWebserviceConstants::"
					+ localException.getMessage());
		}
	}

	public String getJ2EEArchetypeName() {
		return j2EEArchetypeName;
	}

	public void setJ2EEArchetypeName(String j2eeArchetypeName) {
		j2EEArchetypeName = j2eeArchetypeName;
	}

	public String getJ2EEArchetypeDesc() {
		return j2EEArchetypeDesc;
	}

	public void setJ2EEArchetypeDesc(String j2eeArchetypeDesc) {
		j2EEArchetypeDesc = j2eeArchetypeDesc;
	}

	public String getJ2EEArchetypeAppCode() {
		return j2EEArchetypeAppCode;
	}

	public void setJ2EEArchetypeAppCode(String j2eeArchetypeAppCode) {
		j2EEArchetypeAppCode = j2eeArchetypeAppCode;
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

	public String getJ2EEArchetypeEditApp() {
		return j2EEArchetypeEditApp;
	}

	public void setJ2EEArchetypeEditApp(String j2eeArchetypeEditApp) {
		j2EEArchetypeEditApp = j2eeArchetypeEditApp;
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

	public String getJ2EEArchetypePdfReportIcon() {
		return j2EEArchetypePdfReportIcon;
	}

	public void setJ2EEArchetypePdfReportIcon(String j2eeArchetypePdfReportIcon) {
		j2EEArchetypePdfReportIcon = j2eeArchetypePdfReportIcon;
	}

	public String getJ2EEArchetypeOverAllReportName() {
		return j2EEArchetypeOverAllReportName;
	}

	public void setJ2EEArchetypeOverAllReportName(
			String j2eeArchetypeOverAllReportName) {
		j2EEArchetypeOverAllReportName = j2eeArchetypeOverAllReportName;
	}

	public String getJ2EEArchetypeDetailReportName() {
		return j2EEArchetypeDetailReportName;
	}

	public void setJ2EEArchetypeDetailReportName(
			String j2eeArchetypeDetailReportName) {
		j2EEArchetypeDetailReportName = j2eeArchetypeDetailReportName;
	}

}