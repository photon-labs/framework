package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AndroidLibraryConstants {

	private Log log = LogFactory.getLog("AndroidLibraryConstants");

	/**
	 * Create Project
	 */

	private String androidLibArchetypeName = "androidLibArchetypeName";
	private String androidLibArchetypeDesc = "androidLibArchetypeDesc";
	private String androidLibArchetypeAppCode = "androidLibArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String androidLibArchetypeEditApp = "androidLibArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String androidLibArchetypeProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String androidLibArchetypeUpdateDesc = "androidLibArchetypeUpdateDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String androidLibArchetypePdfReportIcon = "androidLibArchetypePdfReportIcon";
	private String androidLibArchetypeOverAllReportName = "androidLibArchetypeOverAllReportName";
	private String androidLibArchetypeDetailReportName = "androidLibArchetypeDetailReportName";

	public AndroidLibraryConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadAndroidLibraryConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in AndroidLibraryConstants::"
					+ localException.getMessage());
		}
	}

	public String getAndroidLibArchetypeName() {
		return androidLibArchetypeName;
	}

	public void setAndroidLibArchetypeName(String androidLibArchetypeName) {
		this.androidLibArchetypeName = androidLibArchetypeName;
	}

	public String getAndroidLibArchetypeDesc() {
		return androidLibArchetypeDesc;
	}

	public void setAndroidLibArchetypeDesc(String androidLibArchetypeDesc) {
		this.androidLibArchetypeDesc = androidLibArchetypeDesc;
	}

	public String getAndroidLibArchetypeAppCode() {
		return androidLibArchetypeAppCode;
	}

	public void setAndroidLibArchetypeAppCode(String androidLibArchetypeAppCode) {
		this.androidLibArchetypeAppCode = androidLibArchetypeAppCode;
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

	public String getAndroidLibArchetypeEditApp() {
		return androidLibArchetypeEditApp;
	}

	public void setAndroidLibArchetypeEditApp(String androidLibArchetypeEditApp) {
		this.androidLibArchetypeEditApp = androidLibArchetypeEditApp;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getAndroidLibArchetypeUpdateDesc() {
		return androidLibArchetypeUpdateDesc;
	}

	public void setAndroidLibArchetypeUpdateDesc(
			String androidLibArchetypeUpdateDesc) {
		this.androidLibArchetypeUpdateDesc = androidLibArchetypeUpdateDesc;
	}

	public String getAndroidLibArchetypeProjectEditIcon() {
		return androidLibArchetypeProjectEditIcon;
	}

	public void setAndroidLibArchetypeProjectEditIcon(
			String androidLibArchetypeProjectEditIcon) {
		this.androidLibArchetypeProjectEditIcon = androidLibArchetypeProjectEditIcon;
	}

	public String getAndroidLibArchetypePdfReportIcon() {
		return androidLibArchetypePdfReportIcon;
	}

	public void setAndroidLibArchetypePdfReportIcon(
			String androidLibArchetypePdfReportIcon) {
		this.androidLibArchetypePdfReportIcon = androidLibArchetypePdfReportIcon;
	}

	public String getAndroidLibArchetypeOverAllReportName() {
		return androidLibArchetypeOverAllReportName;
	}

	public void setAndroidLibArchetypeOverAllReportName(
			String androidLibArchetypeOverAllReportName) {
		this.androidLibArchetypeOverAllReportName = androidLibArchetypeOverAllReportName;
	}

	public String getAndroidLibArchetypeDetailReportName() {
		return androidLibArchetypeDetailReportName;
	}

	public void setAndroidLibArchetypeDetailReportName(
			String androidLibArchetypeDetailReportName) {
		this.androidLibArchetypeDetailReportName = androidLibArchetypeDetailReportName;
	}

}
