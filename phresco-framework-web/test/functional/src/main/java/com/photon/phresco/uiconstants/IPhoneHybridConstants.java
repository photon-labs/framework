package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPhoneHybridConstants {

	private Log log = LogFactory.getLog("IPhoneHybridConstants");

	private String iPhoneHybArchetypeName = "iPhoneHybArchetypeName";
	private String iPhoneHybArchetypeDesc = "iPhoneHybArchetypeDesc";
	private String iPhoneHybArchetypeAppCode = "iPhoneHybArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String iphoneHybSrchetypeAppEditApp = "iphoneHybSrchetypeAppEditApp";

	/**
	 * Edit Project
	 */
	private String iPhoneHybArchetypeProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String iPhoneHybArchetypeUpdateDesc = "iPhoneHybArchetypeUpdateDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String iPhoneHybArchetypePdfReportIcon = "iPhoneHybArchetypePdfReportIcon";
	private String iPhoneHybArchetypeOverAllReportName = "iPhoneHybArchetypeOverAllReportName";
	private String iPhoneHybArchetypeDetailReportName = "iPhoneHybArchetypeDetailReportName";

	public IPhoneHybridConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadiPhoneHybridConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in IPhoneHybridConstants::"
					+ localException.getMessage());
		}
	}

	public String getiPhoneHybArchetypeName() {
		return iPhoneHybArchetypeName;
	}

	public void setiPhoneHybArchetypeName(String iPhoneHybArchetypeName) {
		this.iPhoneHybArchetypeName = iPhoneHybArchetypeName;
	}

	public String getiPhoneHybArchetypeDesc() {
		return iPhoneHybArchetypeDesc;
	}

	public void setiPhoneHybArchetypeDesc(String iPhoneHybArchetypeDesc) {
		this.iPhoneHybArchetypeDesc = iPhoneHybArchetypeDesc;
	}

	public String getiPhoneHybArchetypeAppCode() {
		return iPhoneHybArchetypeAppCode;
	}

	public void setiPhoneHybArchetypeAppCode(String iPhoneHybArchetypeAppCode) {
		this.iPhoneHybArchetypeAppCode = iPhoneHybArchetypeAppCode;
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

	public String getiPhoneHybArchetypeUpdateDesc() {
		return iPhoneHybArchetypeUpdateDesc;
	}

	public void setiPhoneHybArchetypeUpdateDesc(
			String iPhoneHybArchetypeUpdateDesc) {
		this.iPhoneHybArchetypeUpdateDesc = iPhoneHybArchetypeUpdateDesc;
	}

	public String getIphoneHybSrchetypeAppEditApp() {
		return iphoneHybSrchetypeAppEditApp;
	}

	public void setIphoneHybSrchetypeAppEditApp(
			String iphoneHybSrchetypeAppEditApp) {
		this.iphoneHybSrchetypeAppEditApp = iphoneHybSrchetypeAppEditApp;
	}

	public String getiPhoneHybArchetypeProjectEditIcon() {
		return iPhoneHybArchetypeProjectEditIcon;
	}

	public void setiPhoneHybArchetypeProjectEditIcon(
			String iPhoneHybArchetypeProjectEditIcon) {
		this.iPhoneHybArchetypeProjectEditIcon = iPhoneHybArchetypeProjectEditIcon;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getiPhoneHybArchetypePdfReportIcon() {
		return iPhoneHybArchetypePdfReportIcon;
	}

	public void setiPhoneHybArchetypePdfReportIcon(
			String iPhoneHybArchetypePdfReportIcon) {
		this.iPhoneHybArchetypePdfReportIcon = iPhoneHybArchetypePdfReportIcon;
	}

	public String getiPhoneHybArchetypeOverAllReportName() {
		return iPhoneHybArchetypeOverAllReportName;
	}

	public void setiPhoneHybArchetypeOverAllReportName(
			String iPhoneHybArchetypeOverAllReportName) {
		this.iPhoneHybArchetypeOverAllReportName = iPhoneHybArchetypeOverAllReportName;
	}

	public String getiPhoneHybArchetypeDetailReportName() {
		return iPhoneHybArchetypeDetailReportName;
	}

	public void setiPhoneHybArchetypeDetailReportName(
			String iPhoneHybArchetypeDetailReportName) {
		this.iPhoneHybArchetypeDetailReportName = iPhoneHybArchetypeDetailReportName;
	}

}
