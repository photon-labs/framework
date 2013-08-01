package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPhoneWorkspaceConstants {

	private Log log = LogFactory.getLog("IPhoneWorkspaceConstants");

	private String iPhoneWorkspaceArchetypeName = "iPhoneWorkspaceArchetypeName";
	private String iPhoneWorkspaceArchetypeDesc = "iPhoneWorkspaceArchetypeDesc";
	private String iPhoneWorkspaceArchetypeAppCode = "iPhoneWorkspaceArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String iphoneWorkspaceArchetypeEditApp = "iphoneWorkspaceArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String iPhoneWorkspaceArchetypeProjectEditIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String iPhoneWorkspaceArchetypeUpdateDesc = "iPhoneWorkspaceArchetypeUpdateDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String iPhoneWorkspaceArchetypePdfReportIcon = "iPhoneWorkspaceArchetypePdfReportIcon";
	private String iPhoneWorkspaceArchetypeOverAllReportName = "iPhoneWorkspaceArchetypeOverAllReportName";
	private String iPhoneWorkspaceArchetypeDetailReportName = "iPhoneWorkspaceArchetypeDetailReportName";

	public IPhoneWorkspaceConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadiPhoneWorkspaceConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in IPhoneWorkspaceConstants::"
					+ localException.getMessage());
		}
	}

	public String getiPhoneWorkspaceArchetypeName() {
		return iPhoneWorkspaceArchetypeName;
	}

	public void setiPhoneWorkspaceArchetypeName(
			String iPhoneWorkspaceArchetypeName) {
		this.iPhoneWorkspaceArchetypeName = iPhoneWorkspaceArchetypeName;
	}

	public String getiPhoneWorkspaceArchetypeDesc() {
		return iPhoneWorkspaceArchetypeDesc;
	}

	public void setiPhoneWorkspaceArchetypeDesc(
			String iPhoneWorkspaceArchetypeDesc) {
		this.iPhoneWorkspaceArchetypeDesc = iPhoneWorkspaceArchetypeDesc;
	}

	public String getiPhoneWorkspaceArchetypeAppCode() {
		return iPhoneWorkspaceArchetypeAppCode;
	}

	public void setiPhoneWorkspaceArchetypeAppCode(
			String iPhoneWorkspaceArchetypeAppCode) {
		this.iPhoneWorkspaceArchetypeAppCode = iPhoneWorkspaceArchetypeAppCode;
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

	public String getIphoneWorkspaceArchetypeEditApp() {
		return iphoneWorkspaceArchetypeEditApp;
	}

	public void setIphoneWorkspaceArchetypeEditApp(
			String iphoneWorkspaceArchetypeEditApp) {
		this.iphoneWorkspaceArchetypeEditApp = iphoneWorkspaceArchetypeEditApp;
	}

	public String getiPhoneWorkspaceArchetypeProjectEditIcon() {
		return iPhoneWorkspaceArchetypeProjectEditIcon;
	}

	public void setiPhoneWorkspaceArchetypeProjectEditIcon(
			String iPhoneWorkspaceArchetypeProjectEditIcon) {
		this.iPhoneWorkspaceArchetypeProjectEditIcon = iPhoneWorkspaceArchetypeProjectEditIcon;
	}

	public String getiPhoneWorkspaceArchetypeUpdateDesc() {
		return iPhoneWorkspaceArchetypeUpdateDesc;
	}

	public void setiPhoneWorkspaceArchetypeUpdateDesc(
			String iPhoneWorkspaceArchetypeUpdateDesc) {
		this.iPhoneWorkspaceArchetypeUpdateDesc = iPhoneWorkspaceArchetypeUpdateDesc;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getiPhoneWorkspaceArchetypePdfReportIcon() {
		return iPhoneWorkspaceArchetypePdfReportIcon;
	}

	public void setiPhoneWorkspaceArchetypePdfReportIcon(
			String iPhoneWorkspaceArchetypePdfReportIcon) {
		this.iPhoneWorkspaceArchetypePdfReportIcon = iPhoneWorkspaceArchetypePdfReportIcon;
	}

	public String getiPhoneWorkspaceArchetypeOverAllReportName() {
		return iPhoneWorkspaceArchetypeOverAllReportName;
	}

	public void setiPhoneWorkspaceArchetypeOverAllReportName(
			String iPhoneWorkspaceArchetypeOverAllReportName) {
		this.iPhoneWorkspaceArchetypeOverAllReportName = iPhoneWorkspaceArchetypeOverAllReportName;
	}

	public String getiPhoneWorkspaceArchetypeDetailReportName() {
		return iPhoneWorkspaceArchetypeDetailReportName;
	}

	public void setiPhoneWorkspaceArchetypeDetailReportName(
			String iPhoneWorkspaceArchetypeDetailReportName) {
		this.iPhoneWorkspaceArchetypeDetailReportName = iPhoneWorkspaceArchetypeDetailReportName;
	}

}
