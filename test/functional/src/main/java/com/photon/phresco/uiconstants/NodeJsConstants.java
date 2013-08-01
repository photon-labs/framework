package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NodeJsConstants {

	private Log log = LogFactory.getLog("NodeJsConstants");

	/**
	 * Create Project
	 */
	private String nodeJsArchetypeName = "nodeJsArchetypeName";
	private String nodeJsArchetypeDesc = "nodeJsArchetypeDesc";
	private String nodeJsArchetypeAppCode = "nodeJsArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";

	/**
	 * Edit Application
	 */
	private String nodeJsArchetypeEditApp = "nodeJsArchetypeEditApp";

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

	private String nodeJsArchetypePdfReportIcon = "nodeJsArchetypePdfReportIcon";
	private String nodeJsArchetypeOverAllReportName = "nodeJsArchetypeOverAllReportName";
	private String nodeJsArchetypeDetailReportName = "nodeJsArchetypeDetailReportName";

	public NodeJsConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadNodeJsWebServiceConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in NodeJsConstants::"
					+ localException.getMessage());
		}
	}

	public String getNodeJsArchetypeName() {
		return nodeJsArchetypeName;
	}

	public void setNodeJsArchetypeName(String nodeJsArchetypeName) {
		this.nodeJsArchetypeName = nodeJsArchetypeName;
	}

	public String getNodeJsArchetypeDesc() {
		return nodeJsArchetypeDesc;
	}

	public void setNodeJsArchetypeDesc(String nodeJsArchetypeDesc) {
		this.nodeJsArchetypeDesc = nodeJsArchetypeDesc;
	}

	public String getNodeJsArchetypeAppCode() {
		return nodeJsArchetypeAppCode;
	}

	public void setNodeJsArchetypeAppCode(String nodeJsArchetypeAppCode) {
		this.nodeJsArchetypeAppCode = nodeJsArchetypeAppCode;
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

	public String getNodeJsArchetypeEditApp() {
		return nodeJsArchetypeEditApp;
	}

	public void setNodeJsArchetypeEditApp(String nodeJsArchetypeEditApp) {
		this.nodeJsArchetypeEditApp = nodeJsArchetypeEditApp;
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

	public String getNodeJsArchetypePdfReportIcon() {
		return nodeJsArchetypePdfReportIcon;
	}

	public void setNodeJsArchetypePdfReportIcon(
			String nodeJsArchetypePdfReportIcon) {
		this.nodeJsArchetypePdfReportIcon = nodeJsArchetypePdfReportIcon;
	}

	public String getNodeJsArchetypeOverAllReportName() {
		return nodeJsArchetypeOverAllReportName;
	}

	public void setNodeJsArchetypeOverAllReportName(
			String nodeJsArchetypeOverAllReportName) {
		this.nodeJsArchetypeOverAllReportName = nodeJsArchetypeOverAllReportName;
	}

	public String getNodeJsArchetypeDetailReportName() {
		return nodeJsArchetypeDetailReportName;
	}

	public void setNodeJsArchetypeDetailReportName(
			String nodeJsArchetypeDetailReportName) {
		this.nodeJsArchetypeDetailReportName = nodeJsArchetypeDetailReportName;
	}

}