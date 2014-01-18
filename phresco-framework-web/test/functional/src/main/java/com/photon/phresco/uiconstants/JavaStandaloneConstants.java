package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JavaStandaloneConstants {

	private Log log = LogFactory.getLog("JavaStandaloneConstants");

	/**
	 * Create Project
	 */
	private String javaSAArchetypeName = "javaSAArchetypeName";
	private String javaSAArchetypeDesc = "javaSAArchetypeDesc";
	private String javaSAArchetypeAppCode = "javaSAArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String versionValue = "versionValue";
	private String startDate = "startDate";
	private String endDate = "endDate";

	/**
	 * Edit Application
	 */
	private String javaSAArchetypeEditApp = "javaSAArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String editProject = "editProject";

	/**
	 * Edit Application Desc
	 */
	private String appEditDesc = "appEditDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */

	private String javaSAArchetypePdfReportIcon = "javaSAArchetypePdfReportIcon";
	private String javaSAArchetypeOverAllReportName = "javaSAArchetypeOverAllReportName";
	private String javaSAArchetypeDetailReportName = "javaSAArchetypeDetailReportName";

	public JavaStandaloneConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadJavaStandaloneConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in JavaStandaloneConstants::"
					+ localException.getMessage());
		}
	}

	public String getJavaSAArchetypeName() {
		return javaSAArchetypeName;
	}

	public void setJavaSAArchetypeName(String javaSAArchetypeName) {
		this.javaSAArchetypeName = javaSAArchetypeName;
	}

	public String getJavaSAArchetypeDesc() {
		return javaSAArchetypeDesc;
	}

	public void setJavaSAArchetypeDesc(String javaSAArchetypeDesc) {
		this.javaSAArchetypeDesc = javaSAArchetypeDesc;
	}

	public String getJavaSAArchetypeAppCode() {
		return javaSAArchetypeAppCode;
	}

	public void setJavaSAArchetypeAppCode(String javaSAArchetypeAppCode) {
		this.javaSAArchetypeAppCode = javaSAArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getJavaSAArchetypeEditApp() {
		return javaSAArchetypeEditApp;
	}

	public void setJavaSAArchetypeEditApp(String javaSAArchetypeEditApp) {
		this.javaSAArchetypeEditApp = javaSAArchetypeEditApp;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
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

	public String getEditProject() {
		return editProject;
	}

	public String getAppEditDesc() {
		return appEditDesc;
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

	public void setEditProject(String editProject) {
		this.editProject = editProject;
	}

	public void setAppEditDesc(String appEditDesc) {
		this.appEditDesc = appEditDesc;
	}

	public String getJavaSAArchetypePdfReportIcon() {
		return javaSAArchetypePdfReportIcon;
	}

	public void setJavaSAArchetypePdfReportIcon(
			String javaSAArchetypePdfReportIcon) {
		this.javaSAArchetypePdfReportIcon = javaSAArchetypePdfReportIcon;
	}

	public String getJavaSAArchetypeOverAllReportName() {
		return javaSAArchetypeOverAllReportName;
	}

	public void setJavaSAArchetypeOverAllReportName(
			String javaSAArchetypeOverAllReportName) {
		this.javaSAArchetypeOverAllReportName = javaSAArchetypeOverAllReportName;
	}

	public String getJavaSAArchetypeDetailReportName() {
		return javaSAArchetypeDetailReportName;
	}

	public void setJavaSAArchetypeDetailReportName(
			String javaSAArchetypeDetailReportName) {
		this.javaSAArchetypeDetailReportName = javaSAArchetypeDetailReportName;
	}

}
