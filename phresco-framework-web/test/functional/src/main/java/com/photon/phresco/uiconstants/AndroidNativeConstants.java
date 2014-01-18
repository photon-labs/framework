package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AndroidNativeConstants {

	private Log log = LogFactory.getLog("AndroidNativeConstants");

	/**
	 * Create Project
	 */

	private String androidNatArchetypeName = "androidNatArchetypeName";
	private String androidNatArchetypeDesc = "androidNatArchetypeDesc";
	private String androidNatArchetypeAppCode = "androidNatArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */

	private String androidNatArchetypeEditApp = "androidNatArchetypeEditApp";

	/**
	 * Edit Project
	 */

	private String androidNatArchetypeProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */

	private String androidNatArchetypeUpdateDesc = "androidNatArchetypeUpdateDesc";

	/**
	 * Generate Build
	 */

	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String androidNatArchetypePdfReportIcon = "androidNatArchetypePdfReportIcon";
	private String androidNatArchetypeOverAllReportName = "androidNatArchetypeOverAllReportName";
	private String androidNatArchetypeDetailReportName = "androidNatArchetypeDetailReportName";

	public AndroidNativeConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadAndroidNativeConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in AndroidNativeConstants::"
					+ localException.getMessage());
		}
	}

	public String getAndroidNatArchetypeName() {
		return androidNatArchetypeName;
	}

	public void setAndroidNatArchetypeName(String androidNatArchetypeName) {
		this.androidNatArchetypeName = androidNatArchetypeName;
	}

	public String getAndroidNatArchetypeDesc() {
		return androidNatArchetypeDesc;
	}

	public void setAndroidNatArchetypeDesc(String androidNatArchetypeDesc) {
		this.androidNatArchetypeDesc = androidNatArchetypeDesc;
	}

	public String getAndroidNatArchetypeAppCode() {
		return androidNatArchetypeAppCode;
	}

	public void setAndroidNatArchetypeAppCode(String androidNatArchetypeAppCode) {
		this.androidNatArchetypeAppCode = androidNatArchetypeAppCode;
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

	public String getAndroidNatArchetypeEditApp() {
		return androidNatArchetypeEditApp;
	}

	public void setAndroidNatArchetypeEditApp(String androidNatArchetypeEditApp) {
		this.androidNatArchetypeEditApp = androidNatArchetypeEditApp;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getAndroidNatArchetypeUpdateDesc() {
		return androidNatArchetypeUpdateDesc;
	}

	public void setAndroidNatArchetypeUpdateDesc(
			String androidNatArchetypeUpdateDesc) {
		this.androidNatArchetypeUpdateDesc = androidNatArchetypeUpdateDesc;
	}

	public String getAndroidNatArchetypeProjectEditIcon() {
		return androidNatArchetypeProjectEditIcon;
	}

	public void setAndroidNatArchetypeProjectEditIcon(
			String androidNatArchetypeProjectEditIcon) {
		this.androidNatArchetypeProjectEditIcon = androidNatArchetypeProjectEditIcon;
	}

	public String getAndroidNatArchetypePdfReportIcon() {
		return androidNatArchetypePdfReportIcon;
	}

	public void setAndroidNatArchetypePdfReportIcon(
			String androidNatArchetypePdfReportIcon) {
		this.androidNatArchetypePdfReportIcon = androidNatArchetypePdfReportIcon;
	}

	public String getAndroidNatArchetypeOverAllReportName() {
		return androidNatArchetypeOverAllReportName;
	}

	public void setAndroidNatArchetypeOverAllReportName(
			String androidNatArchetypeOverAllReportName) {
		this.androidNatArchetypeOverAllReportName = androidNatArchetypeOverAllReportName;
	}

	public String getAndroidNatArchetypeDetailReportName() {
		return androidNatArchetypeDetailReportName;
	}

	public void setAndroidNatArchetypeDetailReportName(
			String androidNatArchetypeDetailReportName) {
		this.androidNatArchetypeDetailReportName = androidNatArchetypeDetailReportName;
	}

}
