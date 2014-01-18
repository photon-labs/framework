package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WindowsMetroConstants {

	private Log log = LogFactory.getLog("WindowsMetroConstants");

	/**
	 * Create Project
	 */
	private String windowsMetroArchetypeName = "windowsMetroArchetypeName";
	private String windowsMetroArchetypeDesc = "windowsMetroArchetypeDesc";
	private String windowsMetroArchetypeAppCode = "windowsMetroArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String windowsMetroArchetypeEditAppLink = "windowsMetroArchetypeEditAppLink";

	/**
	 * Edit Project
	 */
	private String windowsMetroProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String windowsMetroArchetypeEditDesc = "windowsMetroArchetypeEditDesc";

	/**
	 * Generate Build
	 */

	private String buildName = "buildName";
	
	/*
	 * Pdf Report
	 */
	private String windowsMetroArchetypePdfReportIcon = "windowsMetroArchetypePdfReportIcon";
	private String windowsMetroArchetypeOverAllReportName = "windowsMetroArchetypeOverAllReportName";
	private String windowsMetroArchetypeDetailReportName = "windowsMetroArchetypeDetailReportName";	

	public WindowsMetroConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadWindowsMetroConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in WindowsMetroConstants::"
					+ localException.getMessage());
		}
	}

	public String getWindowsMetroArchetypeName() {
		return windowsMetroArchetypeName;
	}

	public void setWindowsMetroArchetypeName(String windowsMetroArchetypeName) {
		this.windowsMetroArchetypeName = windowsMetroArchetypeName;
	}

	public String getWindowsMetroArchetypeDesc() {
		return windowsMetroArchetypeDesc;
	}

	public void setWindowsMetroArchetypeDesc(String windowsMetroArchetypeDesc) {
		this.windowsMetroArchetypeDesc = windowsMetroArchetypeDesc;
	}

	public String getWindowsMetroArchetypeAppCode() {
		return windowsMetroArchetypeAppCode;
	}

	public void setWindowsMetroArchetypeAppCode(
			String windowsMetroArchetypeAppCode) {
		this.windowsMetroArchetypeAppCode = windowsMetroArchetypeAppCode;
	}

	public String getMobileValue() {
		return mobileValue;
	}

	public void setMobileValue(String mobileValue) {
		this.mobileValue = mobileValue;
	}

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getWindowsMetroArchetypeEditDesc() {
		return windowsMetroArchetypeEditDesc;
	}

	public void setWindowsMetroArchetypeEditDesc(
			String windowsMetroArchetypeEditDesc) {
		this.windowsMetroArchetypeEditDesc = windowsMetroArchetypeEditDesc;
	}

	public String getWindowsMetroArchetypeEditAppLink() {
		return windowsMetroArchetypeEditAppLink;
	}

	public void setWindowsMetroArchetypeEditAppLink(
			String windowsMetroArchetypeEditAppLink) {
		this.windowsMetroArchetypeEditAppLink = windowsMetroArchetypeEditAppLink;
	}

	public String getWindowsMetroProjectEditIcon() {
		return windowsMetroProjectEditIcon;
	}

	public void setWindowsMetroProjectEditIcon(
			String windowsMetroProjectEditIcon) {
		this.windowsMetroProjectEditIcon = windowsMetroProjectEditIcon;
	}
	
	public String getWindowsMetroArchetypePdfReportIcon() {
		return windowsMetroArchetypePdfReportIcon;
	}

	public void setWindowsMetroArchetypePdfReportIcon(
			String windowsMetroArchetypePdfReportIcon) {
		this.windowsMetroArchetypePdfReportIcon = windowsMetroArchetypePdfReportIcon;
	}

	public String getWindowsMetroArchetypeOverAllReportName() {
		return windowsMetroArchetypeOverAllReportName;
	}

	public void setWindowsMetroArchetypeOverAllReportName(
			String windowsMetroArchetypeOverAllReportName) {
		this.windowsMetroArchetypeOverAllReportName = windowsMetroArchetypeOverAllReportName;
	}

	public String getWindowsMetroArchetypeDetailReportName() {
		return windowsMetroArchetypeDetailReportName;
	}

	public void setWindowsMetroArchetypeDetailReportName(
			String windowsMetroArchetypeDetailReportName) {
		this.windowsMetroArchetypeDetailReportName = windowsMetroArchetypeDetailReportName;
	}


}
