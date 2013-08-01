package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WindowsPhoneConstants {

	private Log log = LogFactory.getLog("WindowsPhoneConstants");

	/**
	 * Create Project
	 */

	private String windowsPhoneArchetypeName = "windowsPhoneArchetypeName";
	private String windowsPhoneArchetypeDesc = "windowsPhoneArchetypeDesc";
	private String windowsPhoneArchetypeAppCode = "windowsPhoneArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String windowsPhoneArchetypeEditAppLink = "windowsPhoneArchetypeEditAppLink";

	/**
	 * Edit Project
	 */
	private String windowsPhoneProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String windowsPhoneArchetypeEditDesc = "windowsPhoneArchetypeEditDesc";

	/**
	 * Generate Build
	 */

	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String windowsPhoneArchetypePdfReportIcon = "windowsPhoneArchetypePdfReportIcon";
	private String windowsPhoneArchetypeOverAllReportName = "windowsPhoneArchetypeOverAllReportName";
	private String windowsPhoneArchetypeDetailReportName = "windowsPhoneArchetypeDetailReportName";

	public WindowsPhoneConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadWindowsPhoneConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in WindowsPhoneConstants::"
					+ localException.getMessage());
		}
	}

	public String getWindowsPhoneArchetypeName() {
		return windowsPhoneArchetypeName;
	}

	public void setWindowsPhoneArchetypeName(String windowsPhoneArchetypeName) {
		this.windowsPhoneArchetypeName = windowsPhoneArchetypeName;
	}

	public String getWindowsPhoneArchetypeDesc() {
		return windowsPhoneArchetypeDesc;
	}

	public void setWindowsPhoneArchetypeDesc(String windowsPhoneArchetypeDesc) {
		this.windowsPhoneArchetypeDesc = windowsPhoneArchetypeDesc;
	}

	public String getWindowsPhoneArchetypeAppCode() {
		return windowsPhoneArchetypeAppCode;
	}

	public void setWindowsPhoneArchetypeAppCode(
			String windowsPhoneArchetypeAppCode) {
		this.windowsPhoneArchetypeAppCode = windowsPhoneArchetypeAppCode;
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

	public String getWindowsPhoneArchetypeEditDesc() {
		return windowsPhoneArchetypeEditDesc;
	}

	public void setWindowsPhoneArchetypeEditDesc(
			String windowsPhoneArchetypeEditDesc) {
		this.windowsPhoneArchetypeEditDesc = windowsPhoneArchetypeEditDesc;
	}

	public String getWindowsPhoneArchetypeEditAppLink() {
		return windowsPhoneArchetypeEditAppLink;
	}

	public void setWindowsPhoneArchetypeEditAppLink(
			String windowsPhoneArchetypeEditAppLink) {
		this.windowsPhoneArchetypeEditAppLink = windowsPhoneArchetypeEditAppLink;
	}

	public String getWindowsPhoneProjectEditIcon() {
		return windowsPhoneProjectEditIcon;
	}

	public void setWindowsPhoneProjectEditIcon(
			String windowsPhoneProjectEditIcon) {
		this.windowsPhoneProjectEditIcon = windowsPhoneProjectEditIcon;
	}

	public String getWindowsPhoneArchetypePdfReportIcon() {
		return windowsPhoneArchetypePdfReportIcon;
	}

	public void setWindowsPhoneArchetypePdfReportIcon(
			String windowsPhoneArchetypePdfReportIcon) {
		this.windowsPhoneArchetypePdfReportIcon = windowsPhoneArchetypePdfReportIcon;
	}

	public String getWindowsPhoneArchetypeOverAllReportName() {
		return windowsPhoneArchetypeOverAllReportName;
	}

	public void setWindowsPhoneArchetypeOverAllReportName(
			String windowsPhoneArchetypeOverAllReportName) {
		this.windowsPhoneArchetypeOverAllReportName = windowsPhoneArchetypeOverAllReportName;
	}

	public String getWindowsPhoneArchetypeDetailReportName() {
		return windowsPhoneArchetypeDetailReportName;
	}

	public void setWindowsPhoneArchetypeDetailReportName(
			String windowsPhoneArchetypeDetailReportName) {
		this.windowsPhoneArchetypeDetailReportName = windowsPhoneArchetypeDetailReportName;
	}

}
