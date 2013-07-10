package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AndroidNativeConstants {

	private Log log = LogFactory.getLog("AndroidNativeConstants");

	private String androidNatArchetypeName = "androidNatArchetypeName";
	private String androidNatArchetypeDesc = "androidNatArchetypeDesc";
	private String androidNatArchetypeAppCode = "androidNatArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";
	private String buildName = "buildName";
	private String androidNatArchetypeEditLink = "androidNatArchetypeEditLink";

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
			log.info("AndroidNativeConstants::"+localException.getMessage());
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

	public String getAndroidNatArchetypeEditLink() {
		return androidNatArchetypeEditLink;
	}

	public void setAndroidNatArchetypeEditLink(String androidNatArchetypeEditLink) {
		this.androidNatArchetypeEditLink = androidNatArchetypeEditLink;
	}
   
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	
	


}
