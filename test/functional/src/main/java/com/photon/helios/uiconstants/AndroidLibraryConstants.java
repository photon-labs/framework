package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AndroidLibraryConstants {

	private Log log = LogFactory.getLog("AndroidLibraryConstants");
	private String androidLibArchetypeName = "androidLibArchetypeName";
	private String androidLibArchetypeDesc = "androidLibArchetypeDesc";
	private String androidLibArchetypeAppCode = "androidLibArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";
	private String androidLibArchetypeEditLink = "androidLibArchetypeEditLink";

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
			log.info("AndroidLibraryConstants::"+localException.getMessage());
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

	public String getAndroidLibArchetypeEditLink() {
		return androidLibArchetypeEditLink;
	}

	public void setAndroidLibArchetypeEditLink(String androidLibArchetypeEditLink) {
		this.androidLibArchetypeEditLink = androidLibArchetypeEditLink;
	}
	
	

}


