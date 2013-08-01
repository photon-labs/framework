package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiJQueryWidgetConstants {

	private Log log = LogFactory.getLog("MultiJQueryWidgetConstants");

	/*
	 * Create Project
	 */

	private String multiJQueryArchetypeName = "multiJQueryArchetypeName";
	private String multiJQueryArchetypeDesc = "multiJQueryArchetypeDesc";
	private String multiJQueryArchetypeAppCode = "multiJQueryArchetypeAppCode";
	private String startDate = "startDate";
	private String endDate = "endDate";
	private String webLayerValue = "webLayerValue";
	private String widgetValue = "widgetValue";

	/**
	 * Edit Application
	 */
	private String multiJQueryArchetypeEditApp = "multiJQueryArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String editProject = "editProject";

	/**
	 * Edit App Desc
	 */
	private String appUpdateDesc = "appUpdateDesc";

	/**
	 * Configuration
	 */
	private String configName = "configName";
	private String configHost = "configHost";
	private String configPort = "configPort";
	private String configDeployDir = "configDeployDir";
	private String configContext = "configContext";

	/**
	 * Generate Build
	 */
	private String buildName = "buildname";

	/*
	 * Pdf Report
	 */

	private String multiJQueryArchetypePdfReportIcon = "multiJQueryArchetypePdfReportIcon";
	private String multiJQueryArchetypeOverAllReportName = "multiJQueryArchetypeOverAllReportName";
	private String multiJQueryArchetypeDetailReportName = "multiJQueryArchetypeDetailReportName";

	public MultiJQueryWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMultiChannelJQueryWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in MultiJQueryWidgetConstants::"
					+ localException.getMessage());
		}
	}

	public String getMultiJQueryArchetypeName() {
		return multiJQueryArchetypeName;
	}

	public void setMultiJQueryArchetypeName(String multiJQueryArchetypeName) {
		this.multiJQueryArchetypeName = multiJQueryArchetypeName;
	}

	public String getMultiJQueryArchetypeDesc() {
		return multiJQueryArchetypeDesc;
	}

	public void setMultiJQueryArchetypeDesc(String multiJQueryArchetypeDesc) {
		this.multiJQueryArchetypeDesc = multiJQueryArchetypeDesc;
	}

	public String getMultiJQueryArchetypeAppCode() {
		return multiJQueryArchetypeAppCode;
	}

	public void setMultiJQueryArchetypeAppCode(
			String multiJQueryArchetypeAppCode) {
		this.multiJQueryArchetypeAppCode = multiJQueryArchetypeAppCode;
	}

	public String getWebLayerValue() {
		return webLayerValue;
	}

	public void setWebLayerValue(String webLayerValue) {
		this.webLayerValue = webLayerValue;
	}

	public String getWidgetValue() {
		return widgetValue;
	}

	public void setWidgetValue(String widgetValue) {
		this.widgetValue = widgetValue;
	}

	public String getMultiJQueryArchetypeEditApp() {
		return multiJQueryArchetypeEditApp;
	}

	public void setMultiJQueryArchetypeEditApp(
			String multiJQueryArchetypeEditApp) {
		this.multiJQueryArchetypeEditApp = multiJQueryArchetypeEditApp;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigDeployDir() {
		return configDeployDir;
	}

	public void setConfigDeployDir(String configDeployDir) {
		this.configDeployDir = configDeployDir;
	}

	public String getConfigContext() {
		return configContext;
	}

	public void setConfigContext(String configContext) {
		this.configContext = configContext;
	}

	public String getConfigPort() {
		return configPort;
	}

	public void setConfigPort(String configPort) {
		this.configPort = configPort;
	}

	public String getConfigHost() {
		return configHost;
	}

	public void setConfigHost(String configHost) {
		this.configHost = configHost;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getEditProject() {
		return editProject;
	}

	public String getAppUpdateDesc() {
		return appUpdateDesc;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setEditProject(String editProject) {
		this.editProject = editProject;
	}

	public void setAppUpdateDesc(String appUpdateDesc) {
		this.appUpdateDesc = appUpdateDesc;
	}

	public String getMultiJQueryArchetypePdfReportIcon() {
		return multiJQueryArchetypePdfReportIcon;
	}

	public void setMultiJQueryArchetypePdfReportIcon(
			String multiJQueryArchetypePdfReportIcon) {
		this.multiJQueryArchetypePdfReportIcon = multiJQueryArchetypePdfReportIcon;
	}

	public String getMultiJQueryArchetypeOverAllReportName() {
		return multiJQueryArchetypeOverAllReportName;
	}

	public void setMultiJQueryArchetypeOverAllReportName(
			String multiJQueryArchetypeOverAllReportName) {
		this.multiJQueryArchetypeOverAllReportName = multiJQueryArchetypeOverAllReportName;
	}

	public String getMultiJQueryArchetypeDetailReportName() {
		return multiJQueryArchetypeDetailReportName;
	}

	public void setMultiJQueryArchetypeDetailReportName(
			String multiJQueryArchetypeDetailReportName) {
		this.multiJQueryArchetypeDetailReportName = multiJQueryArchetypeDetailReportName;
	}

}
