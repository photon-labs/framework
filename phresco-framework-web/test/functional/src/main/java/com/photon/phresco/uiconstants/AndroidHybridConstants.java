package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AndroidHybridConstants {

	/**
	 * Create Project
	 */
	private Log log = LogFactory.getLog("AndroidHybridConstants");
	private String androidHybArchetypeName = "androidHybArchetypeName";
	private String androidHybArchetypeDesc = "androidHybArchetypeDesc";
	private String androidHybArchetypeAppCode = "androidHybArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String androidHybArchetypeEditApp = "androidHybArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String androidHybArchetypeProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String androidHybArchetypeUpdateDesc = "androidHybArchetypeUpdateDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String androidHybArchetypePdfReportIcon = "androidHybArchetypePdfReportIcon";
	private String androidHybArchetypeOverAllReportName = "androidHybArchetypeOverAllReportName";
	private String androidHybArchetypeDetailReportName = "androidHybArchetypeDetailReportName";

	public AndroidHybridConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadAndroidHybridConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in AndroidHybridConstants::"
					+ localException.getMessage());
		}
	}

	public String getAndroidHybArchetypeName() {
		return androidHybArchetypeName;
	}

	public void setAndroidHybArchetypeName(String androidHybArchetypeName) {
		this.androidHybArchetypeName = androidHybArchetypeName;
	}

	public String getAndroidHybArchetypeDesc() {
		return androidHybArchetypeDesc;
	}

	public void setAndroidHybArchetypeDesc(String androidHybArchetypeDesc) {
		this.androidHybArchetypeDesc = androidHybArchetypeDesc;
	}

	public String getAndroidHybArchetypeAppCode() {
		return androidHybArchetypeAppCode;
	}

	public void setAndroidHybArchetypeAppCode(String androidHybArchetypeAppCode) {
		this.androidHybArchetypeAppCode = androidHybArchetypeAppCode;
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

	public String getAndroidHybArchetypeEditApp() {
		return androidHybArchetypeEditApp;
	}

	public void setAndroidHybArchetypeEditApp(String androidHybArchetypeEditApp) {
		this.androidHybArchetypeEditApp = androidHybArchetypeEditApp;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getAndroidHybArchetypeUpdateDesc() {
		return androidHybArchetypeUpdateDesc;
	}

	public void setAndroidHybArchetypeUpdateDesc(
			String androidHybArchetypeUpdateDesc) {
		this.androidHybArchetypeUpdateDesc = androidHybArchetypeUpdateDesc;
	}

	public String getAndroidHybArchetypeProjectEditIcon() {
		return androidHybArchetypeProjectEditIcon;
	}

	public void setAndroidHybArchetypeProjectEditIcon(
			String androidHybArchetypeProjectEditIcon) {
		this.androidHybArchetypeProjectEditIcon = androidHybArchetypeProjectEditIcon;
	}

	public String getAndroidHybArchetypePdfReportIcon() {
		return androidHybArchetypePdfReportIcon;
	}

	public void setAndroidHybArchetypePdfReportIcon(
			String androidHybArchetypePdfReportIcon) {
		this.androidHybArchetypePdfReportIcon = androidHybArchetypePdfReportIcon;
	}

	public String getAndroidHybArchetypeOverAllReportName() {
		return androidHybArchetypeOverAllReportName;
	}

	public void setAndroidHybArchetypeOverAllReportName(
			String androidHybArchetypeOverAllReportName) {
		this.androidHybArchetypeOverAllReportName = androidHybArchetypeOverAllReportName;
	}

	public String getAndroidHybArchetypeDetailReportName() {
		return androidHybArchetypeDetailReportName;
	}

	public void setAndroidHybArchetypeDetailReportName(
			String androidHybArchetypeDetailReportName) {
		this.androidHybArchetypeDetailReportName = androidHybArchetypeDetailReportName;
	}

}
