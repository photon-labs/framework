package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BlackBerryHybridConstants {

	private Log log = LogFactory.getLog("BlackBerryHybridConstants");
	
	private String blackBerryHybArchetypeName = "blackBerryHybArchetypeName";
	private String blackBerryHybArchetypeDesc = "blackBerryHybArchetypeDesc";
	private String blackBerryHybArchetypeAppCode = "blackBerryHybArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";
	
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
			log.info("Exception in BlackBerryHybridConstants::"+localException.getMessage());
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

	
	
	}
