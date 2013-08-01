package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SitecoreConstants {

	private Log log = LogFactory.getLog("SitecoreConstants");

	/**
	 * Create Project
	 */
	private String sitecoreArchetypeName = "sitecoreArchetypeName";
	private String sitecoreArchetypeDesc = "sitecoreArchetypeDesc";
	private String sitecoreArchetypeAppCode = "sitecoreArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";

	/**
	 * Edit Application
	 */
	private String sitecoreArchetypeEditApp = "sitecoreArchetypeEditApp";

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
	private String sitecoreArchetypePdfReportIcon = "sitecoreArchetypePdfReportIcon";
	private String sitecoreArchetypeOverAllReportName = "sitecoreArchetypeOverAllReportName";
	private String sitecoreArchetypeDetailReportName = "sitecoreArchetypeDetailReportName";

	public SitecoreConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadSiteCoreConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in SitecoreConstants::"
					+ localException.getMessage());
		}
	}

	public String getSitecoreArchetypeName() {
		return sitecoreArchetypeName;
	}

	public void setSitecoreArchetypeName(String sitecoreArchetypeName) {
		this.sitecoreArchetypeName = sitecoreArchetypeName;
	}

	public String getSitecoreArchetypeDesc() {
		return sitecoreArchetypeDesc;
	}

	public void setSitecoreArchetypeDesc(String sitecoreArchetypeDesc) {
		this.sitecoreArchetypeDesc = sitecoreArchetypeDesc;
	}

	public String getSitecoreArchetypeAppCode() {
		return sitecoreArchetypeAppCode;
	}

	public void setSitecoreArchetypeAppCode(String sitecoreArchetypeAppCode) {
		this.sitecoreArchetypeAppCode = sitecoreArchetypeAppCode;
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

	public String getSitecoreArchetypeEditApp() {
		return sitecoreArchetypeEditApp;
	}

	public void setSitecoreArchetypeEditApp(String sitecoreArchetypeEditApp) {
		this.sitecoreArchetypeEditApp = sitecoreArchetypeEditApp;
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

	public String getSitecoreArchetypePdfReportIcon() {
		return sitecoreArchetypePdfReportIcon;
	}

	public void setSitecoreArchetypePdfReportIcon(
			String sitecoreArchetypePdfReportIcon) {
		this.sitecoreArchetypePdfReportIcon = sitecoreArchetypePdfReportIcon;
	}

	public String getSitecoreArchetypeOverAllReportName() {
		return sitecoreArchetypeOverAllReportName;
	}

	public void setSitecoreArchetypeOverAllReportName(
			String sitecoreArchetypeOverAllReportName) {
		this.sitecoreArchetypeOverAllReportName = sitecoreArchetypeOverAllReportName;
	}

	public String getSitecoreArchetypeDetailReportName() {
		return sitecoreArchetypeDetailReportName;
	}

	public void setSitecoreArchetypeDetailReportName(
			String sitecoreArchetypeDetailReportName) {
		this.sitecoreArchetypeDetailReportName = sitecoreArchetypeDetailReportName;
	}

}
