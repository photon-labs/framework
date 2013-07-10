package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPhoneNativeConstants {

	private Log log = LogFactory.getLog("IPhoneNativeConstants");
	private String iPhoneNatArchetypeName = "iPhoneNatArchetypeName";
	private String iPhoneNatArchetypeDesc = "iPhoneNatArchetypeDesc";
	private String iPhoneNatArchetypeAppCode = "iPhoneNatArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";
	private String buildName = "buildName";


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
			log.info("IPhoneNativeConstants::"+localException.getMessage());
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

	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}


}
