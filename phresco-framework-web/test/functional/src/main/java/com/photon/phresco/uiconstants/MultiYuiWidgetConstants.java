package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiYuiWidgetConstants {

	private Log log = LogFactory.getLog("MultiYUIWidgetConstants");

	/*
	 * Create Project
	 */

	private String multiYuiArchetypeName = "multiYuiArchetypeName";
	private String multiYuiArchetypeDesc = "multiYuiArchetypeDesc";
	private String multiYuiArchetypeAppCode = "multiYuiArchetypeAppCode";
	private String startDate = "startDate";
	private String endDate = "endDate";
	private String webLayerValue = "webLayerValue";
	private String widgetValue = "widgetValue";

	/**
	 * Edit Project
	 */
	private String editProject = "editProject";

	/**
	 * Edit App Desc
	 */
	private String appUpdateDesc = "appUpdateDesc";

	/**
	 * Edit Application
	 */
	private String multiYuiArchetypeEditApp = "multiYuiArchetypeEditApp";

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

	private String multiYuiArchetypePdfReportIcon = "multiYuiArchetypePdfReportIcon";
	private String multiYuiArchetypeOverAllReportName = "multiYuiArchetypeOverAllReportName";
	private String multiYuiArchetypeDetailReportName = "multiYuiArchetypeDetailReportName";

	public MultiYuiWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMultiChannelYUIWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in MultiYUIWidgetConstants::"
					+ localException.getMessage());
		}
	}

	public String getMultiYuiArchetypeName() {
		return multiYuiArchetypeName;
	}

	public void setMultiYuiArchetypeName(String multiYuiArchetypeName) {
		this.multiYuiArchetypeName = multiYuiArchetypeName;
	}

	public String getMultiYuiArchetypeDesc() {
		return multiYuiArchetypeDesc;
	}

	public void setMultiYuiArchetypeDesc(String multiYuiArchetypeDesc) {
		this.multiYuiArchetypeDesc = multiYuiArchetypeDesc;
	}

	public String getMultiYuiArchetypeAppCode() {
		return multiYuiArchetypeAppCode;
	}

	public void setMultiYuiArchetypeAppCode(String multiYuiArchetypeAppCode) {
		this.multiYuiArchetypeAppCode = multiYuiArchetypeAppCode;
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

	public String getMultiYuiArchetypeEditApp() {
		return multiYuiArchetypeEditApp;
	}

	public void setMultiYuiArchetypeEditApp(String multiYuiArchetypeEditApp) {
		this.multiYuiArchetypeEditApp = multiYuiArchetypeEditApp;
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

	public String getMultiYuiArchetypePdfReportIcon() {
		return multiYuiArchetypePdfReportIcon;
	}

	public void setMultiYuiArchetypePdfReportIcon(
			String multiYuiArchetypePdfReportIcon) {
		this.multiYuiArchetypePdfReportIcon = multiYuiArchetypePdfReportIcon;
	}

	public String getMultiYuiArchetypeOverAllReportName() {
		return multiYuiArchetypeOverAllReportName;
	}

	public void setMultiYuiArchetypeOverAllReportName(
			String multiYuiArchetypeOverAllReportName) {
		this.multiYuiArchetypeOverAllReportName = multiYuiArchetypeOverAllReportName;
	}

	public String getMultiYuiArchetypeDetailReportName() {
		return multiYuiArchetypeDetailReportName;
	}

	public void setMultiYuiArchetypeDetailReportName(
			String multiYuiArchetypeDetailReportName) {
		this.multiYuiArchetypeDetailReportName = multiYuiArchetypeDetailReportName;
	}

}
