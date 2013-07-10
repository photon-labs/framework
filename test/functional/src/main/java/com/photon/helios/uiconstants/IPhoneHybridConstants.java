package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPhoneHybridConstants {

	private Log log = LogFactory.getLog("IPhoneHybridConstants");
	
	
	private String iPhoneHybArchetypeName = "iPhoneHybArchetypeName";
	private String iPhoneHybArchetypeDesc = "iPhoneHybArchetypeDesc";
	private String iPhoneHybArchetypeAppCode = "iPhoneHybArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String buildName = "buildName";
	private String mobileType = "mobileType";
	

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
			log.info("IPhoneHybridConstants::"+localException.getMessage());
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
	
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	
	}
