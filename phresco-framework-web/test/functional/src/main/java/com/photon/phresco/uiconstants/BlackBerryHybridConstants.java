package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BlackBerryHybridConstants {

	private Log log = LogFactory.getLog("BlackBerryHybridConstants");

	/**
	 * Create Project
	 */

	private String blackBerryHybArchetypeName = "blackBerryHybArchetypeName";
	private String blackBerryHybArchetypeDesc = "blackBerryHybArchetypeDesc";
	private String blackBerryHybArchetypeAppCode = "blackBerryHybArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";

	/**
	 * Edit Application
	 */
	private String blackBerryHybArchetypeEditAppLink = "blackBerryHybArchetypeEditApp";

	/**
	 * Edit Project
	 */
	private String blackBerryHybArchetypeEditProjectIcon = "editProject";

	/**
	 * Edit App Desc
	 */
	private String updateDesc = "updateDesc";

	/**
	 * Generate Build
	 */
	private String buildName = "buildName";

	/*
	 * Pdf Report
	 */
	private String blackBerryHybArchetypePdfReportIcon = "blackBerryHybArchetypePdfReportIcon";
	private String blackBerryHybArchetypeOverAllReportName = "blackBerryHybArchetypeOverAllReportName";
	private String blackBerryHybArchetypeDetailReportName = "blackBerryHybArchetypeDetailReportName";

	public BlackBerryHybridConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadBlackBerryHybridConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in BlackBerryHybridConstants::"
					+ localException.getMessage());
		}
	}

	public String getBlackBerryHybArchetypeName() {
		return blackBerryHybArchetypeName;
	}

	public void setBlackBerryHybArchetypeName(String blackBerryHybArchetypeName) {
		this.blackBerryHybArchetypeName = blackBerryHybArchetypeName;
	}

	public String getBlackBerryHybArchetypeDesc() {
		return blackBerryHybArchetypeDesc;
	}

	public void setBlackBerryHybArchetypeDesc(String blackBerryHybArchetypeDesc) {
		this.blackBerryHybArchetypeDesc = blackBerryHybArchetypeDesc;
	}

	public String getBlackBerryHybArchetypeAppCode() {
		return blackBerryHybArchetypeAppCode;
	}

	public void setBlackBerryHybArchetypeAppCode(
			String blackBerryHybArchetypeAppCode) {
		this.blackBerryHybArchetypeAppCode = blackBerryHybArchetypeAppCode;
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

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public String getBlackBerryHybArchetypeEditAppLink() {
		return blackBerryHybArchetypeEditAppLink;
	}

	public void setBlackBerryHybArchetypeEditAppLink(
			String blackBerryHybArchetypeEditAppLink) {
		this.blackBerryHybArchetypeEditAppLink = blackBerryHybArchetypeEditAppLink;
	}

	public String getBlackBerryHybArchetypeEditProjectIcon() {
		return blackBerryHybArchetypeEditProjectIcon;
	}

	public void setBlackBerryHybArchetypeEditProjectIcon(
			String blackBerryHybArchetypeEditProjectIcon) {
		this.blackBerryHybArchetypeEditProjectIcon = blackBerryHybArchetypeEditProjectIcon;
	}

	public String getBlackBerryHybArchetypePdfReportIcon() {
		return blackBerryHybArchetypePdfReportIcon;
	}

	public void setBlackBerryHybArchetypePdfReportIcon(
			String blackBerryHybArchetypePdfReportIcon) {
		this.blackBerryHybArchetypePdfReportIcon = blackBerryHybArchetypePdfReportIcon;
	}

	public String getBlackBerryHybArchetypeOverAllReportName() {
		return blackBerryHybArchetypeOverAllReportName;
	}

	public void setBlackBerryHybArchetypeOverAllReportName(
			String blackBerryHybArchetypeOverAllReportName) {
		this.blackBerryHybArchetypeOverAllReportName = blackBerryHybArchetypeOverAllReportName;
	}

	public String getBlackBerryHybArchetypeDetailReportName() {
		return blackBerryHybArchetypeDetailReportName;
	}

	public void setBlackBerryHybArchetypeDetailReportName(
			String blackBerryHybArchetypeDetailReportName) {
		this.blackBerryHybArchetypeDetailReportName = blackBerryHybArchetypeDetailReportName;
	}

}
