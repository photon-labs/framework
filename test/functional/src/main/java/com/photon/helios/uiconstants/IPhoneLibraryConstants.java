package com.photon.helios.uiconstants;

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
			log.info("IPhoneLibraryConstants::"+localException.getMessage());
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
	
	

}
