package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WindowsMetroConstants {

	private Log log = LogFactory.getLog("WindowsMetroConstants");

	private String windowsMetroArchetypeName = "windowsMetroArchetypeName";
	private String windowsMetroArchetypeDesc = "windowsMetroArchetypeDesc";
	private String windowsMetroArchetypeAppCode = "windowsMetroArchetypeAppCode";
	private String mobileValue = "mobileValue";
	private String mobileType = "mobileType";


	public WindowsMetroConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadWindowsMetroConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("WindowsMetroConstants::"+localException.getMessage());
		}
	}


	public String getWindowsMetroArchetypeName() {
		return windowsMetroArchetypeName;
	}


	public void setWindowsMetroArchetypeName(String windowsMetroArchetypeName) {
		this.windowsMetroArchetypeName = windowsMetroArchetypeName;
	}


	public String getWindowsMetroArchetypeDesc() {
		return windowsMetroArchetypeDesc;
	}


	public void setWindowsMetroArchetypeDesc(String windowsMetroArchetypeDesc) {
		this.windowsMetroArchetypeDesc = windowsMetroArchetypeDesc;
	}


	public String getWindowsMetroArchetypeAppCode() {
		return windowsMetroArchetypeAppCode;
	}


	public void setWindowsMetroArchetypeAppCode(String windowsMetroArchetypeAppCode) {
		this.windowsMetroArchetypeAppCode = windowsMetroArchetypeAppCode;
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

