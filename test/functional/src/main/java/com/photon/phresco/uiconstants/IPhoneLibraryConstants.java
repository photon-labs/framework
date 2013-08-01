package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPhoneLibraryConstants {

	private Log log = LogFactory.getLog("IPhoneLibraryConstants");

	private String iPhoneLibArchetypeName = "iPhoneLibArchetypeName";
	private String iPhoneLibArchetypeDesc = "iPhoneLibArchetypeDesc";
	private String iPhoneLibArchetypeAppCode = "iPhoneLibArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String iphoneLibArchetypeAppEditApp = "iphoneLibArchetypeAppEditApp";

	/**
	 * Edit Project
	 */
	private String iPhoneLibArchetypeProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String iPhoneLibArchetypeUpdateDesc = "iPhoneLibArchetypeUpdateDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String iPhoneLibArchetypePdfReportIcon = "iPhoneLibArchetypePdfReportIcon";
	private String iPhoneLibArchetypeOverAllReportName = "iPhoneLibArchetypeOverAllReportName";
	private String iPhoneLibArchetypeDetailReportName = "iPhoneLibArchetypeDetailReportName";

	public IPhoneLibraryConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadiPhoneLibraryConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in IPhoneLibraryConstants::"
					+ localException.getMessage());
		}
	}

	public String getiPhoneLibArchetypeName() {
		return iPhoneLibArchetypeName;
	}

	public void setiPhoneLibArchetypeName(String iPhoneLibArchetypeName) {
		this.iPhoneLibArchetypeName = iPhoneLibArchetypeName;
	}

	public String getiPhoneLibArchetypeDesc() {
		return iPhoneLibArchetypeDesc;
	}

	public void setiPhoneLibArchetypeDesc(String iPhoneLibArchetypeDesc) {
		this.iPhoneLibArchetypeDesc = iPhoneLibArchetypeDesc;
	}

	public String getiPhoneLibArchetypeAppCode() {
		return iPhoneLibArchetypeAppCode;
	}

	public void setiPhoneLibArchetypeAppCode(String iPhoneLibArchetypeAppCode) {
		this.iPhoneLibArchetypeAppCode = iPhoneLibArchetypeAppCode;
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

	public String getiPhoneLibArchetypeUpdateDesc() {
		return iPhoneLibArchetypeUpdateDesc;
	}

	public void setiPhoneLibArchetypeUpdateDesc(
			String iPhoneLibArchetypeUpdateDesc) {
		this.iPhoneLibArchetypeUpdateDesc = iPhoneLibArchetypeUpdateDesc;
	}

	public String getIphoneLibArchetypeAppEditApp() {
		return iphoneLibArchetypeAppEditApp;
	}

	public void setIphoneLibArchetypeAppEditApp(
			String iphoneLibArchetypeAppEditApp) {
		this.iphoneLibArchetypeAppEditApp = iphoneLibArchetypeAppEditApp;
	}

	public String getiPhoneLibArchetypeProjectEditIcon() {
		return iPhoneLibArchetypeProjectEditIcon;
	}

	public void setiPhoneLibArchetypeProjectEditIcon(
			String iPhoneLibArchetypeProjectEditIcon) {
		this.iPhoneLibArchetypeProjectEditIcon = iPhoneLibArchetypeProjectEditIcon;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getiPhoneLibArchetypePdfReportIcon() {
		return iPhoneLibArchetypePdfReportIcon;
	}

	public void setiPhoneLibArchetypePdfReportIcon(
			String iPhoneLibArchetypePdfReportIcon) {
		this.iPhoneLibArchetypePdfReportIcon = iPhoneLibArchetypePdfReportIcon;
	}

	public String getiPhoneLibArchetypeOverAllReportName() {
		return iPhoneLibArchetypeOverAllReportName;
	}

	public void setiPhoneLibArchetypeOverAllReportName(
			String iPhoneLibArchetypeOverAllReportName) {
		this.iPhoneLibArchetypeOverAllReportName = iPhoneLibArchetypeOverAllReportName;
	}

	public String getiPhoneLibArchetypeDetailReportName() {
		return iPhoneLibArchetypeDetailReportName;
	}

	public void setiPhoneLibArchetypeDetailReportName(
			String iPhoneLibArchetypeDetailReportName) {
		this.iPhoneLibArchetypeDetailReportName = iPhoneLibArchetypeDetailReportName;
	}

}
