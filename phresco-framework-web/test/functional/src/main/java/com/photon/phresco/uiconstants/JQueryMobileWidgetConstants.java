package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JQueryMobileWidgetConstants {

	private Log log = LogFactory.getLog("JQueryMobileWidgetConstants");

	/*
	 * Create Project
	 */

	private String jQueryMobArchetypeName = "jQueryMobArchetypeName";
	private String jQueryMobArchetypeDesc = "jQueryMobArchetypeDesc";
	private String startDate = "startDate";
	private String endDate = "endDate";
	private String jQueryMobArchetypeAppCode = "jQueryMobArchetypeAppCode";
	private String webLayerValue = "webLayerValue";
	private String widgetValue = "widgetValue";
	private String versionValue = "versionValue";

	/**
	 * Edit Project
	 */
	private String editProject = "editProject";

	/**
	 * Edit Application
	 */
	private String jQueryMobArchetypeEditApp = "jQueryMobArchetypeEditApp";

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

	private String jQueryMobArchetypePdfReportIcon = "jQueryMobArchetypePdfReportIcon";
	private String jQueryMobArchetypeOverAllReportName = "jQueryMobArchetypeOverAllReportName";
	private String jQueryMobArchetypeDetailReportName = "jQueryMobArchetypeDetailReportName";

	public JQueryMobileWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadJqueryWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in JQueryMobileWidgetConstants::"
					+ localException.getMessage());
		}
	}

	public String getjQueryMobArchetypeName() {
		return jQueryMobArchetypeName;
	}

	public void setjQueryMobArchetypeName(String jQueryMobArchetypeName) {
		this.jQueryMobArchetypeName = jQueryMobArchetypeName;
	}

	public String getjQueryMobArchetypeDesc() {
		return jQueryMobArchetypeDesc;
	}

	public void setjQueryMobArchetypeDesc(String jQueryMobArchetypeDesc) {
		this.jQueryMobArchetypeDesc = jQueryMobArchetypeDesc;
	}

	public String getjQueryMobArchetypeAppCode() {
		return jQueryMobArchetypeAppCode;
	}

	public void setjQueryMobArchetypeAppCode(String jQueryMobArchetypeAppCode) {
		this.jQueryMobArchetypeAppCode = jQueryMobArchetypeAppCode;
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

	public String getjQueryMobArchetypeEditApp() {
		return jQueryMobArchetypeEditApp;
	}

	public void setjQueryMobArchetypeEditApp(String jQueryMobArchetypeEditApp) {
		this.jQueryMobArchetypeEditApp = jQueryMobArchetypeEditApp;
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

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getVersionValue() {
		return versionValue;
	}

	public void setVersionValue(String versionValue) {
		this.versionValue = versionValue;
	}

	public String getEditProject() {
		return editProject;
	}

	public void setEditProject(String editProject) {
		this.editProject = editProject;
	}

	public String getAppUpdateDesc() {
		return appUpdateDesc;
	}

	public void setAppUpdateDesc(String appUpdateDesc) {
		this.appUpdateDesc = appUpdateDesc;
	}

	public String getjQueryMobArchetypePdfReportIcon() {
		return jQueryMobArchetypePdfReportIcon;
	}

	public void setjQueryMobArchetypePdfReportIcon(
			String jQueryMobArchetypePdfReportIcon) {
		this.jQueryMobArchetypePdfReportIcon = jQueryMobArchetypePdfReportIcon;
	}

	public String getjQueryMobArchetypeOverAllReportName() {
		return jQueryMobArchetypeOverAllReportName;
	}

	public void setjQueryMobArchetypeOverAllReportName(
			String jQueryMobArchetypeOverAllReportName) {
		this.jQueryMobArchetypeOverAllReportName = jQueryMobArchetypeOverAllReportName;
	}

	public String getjQueryMobArchetypeDetailReportName() {
		return jQueryMobArchetypeDetailReportName;
	}

	public void setjQueryMobArchetypeDetailReportName(
			String jQueryMobArchetypeDetailReportName) {
		this.jQueryMobArchetypeDetailReportName = jQueryMobArchetypeDetailReportName;
	}

}