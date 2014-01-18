package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YuiMobileWidgetConstants {

	private Log log = LogFactory.getLog("YUIMobileWidgetConstants");

	/*
	 * Create Project
	 */

	private String YUIMobileArchetypeName = "YUIMobileArchetypeName";
	private String YUIMobileArchetypeDesc = "YUIMobileArchetypeDesc";
	private String YUIMobileArchetypeAppCode = "YUIMobileArchetypeAppCode";
	private String startDate = "startDate";
	private String endDate = "endDate";
	private String webLayerValue = "webLayerValue";
	private String widgetValue = "widgetValue";

	/**
	 * Edit Application
	 */

	private String YUIMobileArchetypeEditApp = "YUIMobileArchetypeEditApp";

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

	private String yuiMobileArchetypePdfReportIcon = "yuiMobileArchetypePdfReportIcon";
	private String yuiMobileArchetypeOverAllReportName = "yuiMobileArchetypeOverAllReportName";
	private String yuiMobileArchetypeDetailReportName = "yuiMobileArchetypeDetailReportName";

	public YuiMobileWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadYUIMobileWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in YUIMobileWidgetConstants::"
					+ localException.getMessage());
		}
	}

	public String getYUIMobileArchetypeEditApp() {
		return YUIMobileArchetypeEditApp;
	}

	public void setYUIMobileArchetypeEditApp(String yUIMobileArchetypeEditApp) {
		YUIMobileArchetypeEditApp = yUIMobileArchetypeEditApp;
	}

	public String getYUIMobileArchetypeName() {
		return YUIMobileArchetypeName;
	}

	public void setYUIMobileArchetypeName(String yUIMobileArchetypeName) {
		YUIMobileArchetypeName = yUIMobileArchetypeName;
	}

	public String getYUIMobileArchetypeDesc() {
		return YUIMobileArchetypeDesc;
	}

	public void setYUIMobileArchetypeDesc(String yUIMobileArchetypeDesc) {
		YUIMobileArchetypeDesc = yUIMobileArchetypeDesc;
	}

	public String getYUIMobileArchetypeAppCode() {
		return YUIMobileArchetypeAppCode;
	}

	public void setYUIMobileArchetypeAppCode(String yUIMobileArchetypeAppCode) {
		YUIMobileArchetypeAppCode = yUIMobileArchetypeAppCode;
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

	public String getYuiMobileArchetypePdfReportIcon() {
		return yuiMobileArchetypePdfReportIcon;
	}

	public void setYuiMobileArchetypePdfReportIcon(
			String yuiMobileArchetypePdfReportIcon) {
		this.yuiMobileArchetypePdfReportIcon = yuiMobileArchetypePdfReportIcon;
	}

	public String getYuiMobileArchetypeOverAllReportName() {
		return yuiMobileArchetypeOverAllReportName;
	}

	public void setYuiMobileArchetypeOverAllReportName(
			String yuiMobileArchetypeOverAllReportName) {
		this.yuiMobileArchetypeOverAllReportName = yuiMobileArchetypeOverAllReportName;
	}

	public String getYuiMobileArchetypeDetailReportName() {
		return yuiMobileArchetypeDetailReportName;
	}

	public void setYuiMobileArchetypeDetailReportName(
			String yuiMobileArchetypeDetailReportName) {
		this.yuiMobileArchetypeDetailReportName = yuiMobileArchetypeDetailReportName;
	}

}
