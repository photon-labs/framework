package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AndroidHybridConstants {

	private Log log = LogFactory.getLog("AndroidHybridConstants");

	private String androidHybArchetypeName = "androidHybArchetypeName";
	private String androidHybArchetypeDesc = "androidHybArchetypeDesc";
	private String androidHybArchetypeAppCode = "androidHybArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";
	private String androidHybArchetypeEditLink = "androidHybArchetypeEditLink"; 

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
			log.info("AndroidHybridConstants::"+localException.getMessage());
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

	public String getAndroidHybArchetypeEditLink() {
		return androidHybArchetypeEditLink;
	}

	public void setAndroidHybArchetypeEditLink(String androidHybArchetypeEditLink) {
		this.androidHybArchetypeEditLink = androidHybArchetypeEditLink;
	}


  


}
