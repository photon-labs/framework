package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WindowsPhoneConstants {

	private Log log = LogFactory.getLog("WindowsPhoneConstants");

	private String windowsPhoneArchetypeName = "windowsPhoneArchetypeName";
	private String windowsPhoneArchetypeDesc = "windowsPhoneArchetypeDesc";
	private String windowsPhoneArchetypeAppCode = "windowsPhoneArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";


	public WindowsPhoneConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadWindowsPhoneConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("WindowsPhoneConstants::"+localException.getMessage());
		}
	}


	public String getWindowsPhoneArchetypeName() {
		return windowsPhoneArchetypeName;
	}


	public void setWindowsPhoneArchetypeName(String windowsPhoneArchetypeName) {
		this.windowsPhoneArchetypeName = windowsPhoneArchetypeName;
	}


	public String getWindowsPhoneArchetypeDesc() {
		return windowsPhoneArchetypeDesc;
	}


	public void setWindowsPhoneArchetypeDesc(String windowsPhoneArchetypeDesc) {
		this.windowsPhoneArchetypeDesc = windowsPhoneArchetypeDesc;
	}


	public String getWindowsPhoneArchetypeAppCode() {
		return windowsPhoneArchetypeAppCode;
	}


	public void setWindowsPhoneArchetypeAppCode(String windowsPhoneArchetypeAppCode) {
		this.windowsPhoneArchetypeAppCode = windowsPhoneArchetypeAppCode;
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

