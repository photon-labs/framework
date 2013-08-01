package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPhoneNativeConstants {

	private Log log = LogFactory.getLog("IPhoneNativeConstants");

	/*
	 * Create Project
	 */
	private String iPhoneNatArchetypeName = "iPhoneNatArchetypeName";
	private String iPhoneNatArchetypeDesc = "iPhoneNatArchetypeDesc";
	private String iPhoneNatArchetypeAppCode = "iPhoneNatArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String iPhoneNatArchetypeEditAppApp = "iPhoneNatArchetypeEditAppApp";

	/**
	 * Edit Project
	 */
	private String iPhoneNativeArchetypeProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String iPhoneNatArchetypeUpdateDesc = "iPhoneNatArchetypeUpdateDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String iPhoneNatArchetypePdfReportIcon = "iPhoneNatArchetypePdfReportIcon";
	private String iPhoneNatArchetypeOverAllReportName = "iPhoneNatArchetypeOverAllReportName";
	private String iPhoneNatArchetypeDetailReportName = "iPhoneNatArchetypeDetailReportName";	


	public IPhoneNativeConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadiPhoneNativeConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in IPhoneNativeConstants::"
					+ localException.getMessage());
		}
	}

	public String getiPhoneNatArchetypeName() {
		return iPhoneNatArchetypeName;
	}

	public void setiPhoneNatArchetypeName(String iPhoneNatArchetypeName) {
		this.iPhoneNatArchetypeName = iPhoneNatArchetypeName;
	}

	public String getiPhoneNatArchetypeDesc() {
		return iPhoneNatArchetypeDesc;
	}

	public void setiPhoneNatArchetypeDesc(String iPhoneNatArchetypeDesc) {
		this.iPhoneNatArchetypeDesc = iPhoneNatArchetypeDesc;
	}

	public String getiPhoneNatArchetypeAppCode() {
		return iPhoneNatArchetypeAppCode;
	}

	public void setiPhoneNatArchetypeAppCode(String iPhoneNatArchetypeAppCode) {
		this.iPhoneNatArchetypeAppCode = iPhoneNatArchetypeAppCode;
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

	public String getiPhoneNatArchetypeEditAppApp() {
		return iPhoneNatArchetypeEditAppApp;
	}

	public void setiPhoneNatArchetypeEditAppApp(
			String iPhoneNatArchetypeEditAppApp) {
		this.iPhoneNatArchetypeEditAppApp = iPhoneNatArchetypeEditAppApp;
	}

	public String getiPhoneNatArchetypeUpdateDesc() {
		return iPhoneNatArchetypeUpdateDesc;
	}

	public void setiPhoneNatArchetypeUpdateDesc(
			String iPhoneNatArchetypeUpdateDesc) {
		this.iPhoneNatArchetypeUpdateDesc = iPhoneNatArchetypeUpdateDesc;
	}

	public String getiPhoneNativeArchetypeProjectEditIcon() {
		return iPhoneNativeArchetypeProjectEditIcon;
	}

	public void setiPhoneNativeArchetypeProjectEditIcon(
			String iPhoneNativeArchetypeProjectEditIcon) {
		this.iPhoneNativeArchetypeProjectEditIcon = iPhoneNativeArchetypeProjectEditIcon;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getiPhoneNatArchetypePdfReportIcon() {
		return iPhoneNatArchetypePdfReportIcon;
	}

	public void setiPhoneNatArchetypePdfReportIcon(
			String iPhoneNatArchetypePdfReportIcon) {
		this.iPhoneNatArchetypePdfReportIcon = iPhoneNatArchetypePdfReportIcon;
	}
	
	public String getiPhoneNatArchetypeOverAllReportName() {
		return iPhoneNatArchetypeOverAllReportName;
	}

	public void setiPhoneNatArchetypeOverAllReportName(
			String iPhoneNatArchetypeOverAllReportName) {
		this.iPhoneNatArchetypeOverAllReportName = iPhoneNatArchetypeOverAllReportName;
	}
	

	public String getiPhoneNatArchetypeDetailReportName() {
		return iPhoneNatArchetypeDetailReportName;
	}

	public void setiPhoneNatArchetypeDetailReportName(
			String iPhoneNatArchetypeDetailReportName) {
		this.iPhoneNatArchetypeDetailReportName = iPhoneNatArchetypeDetailReportName;
	}

}
