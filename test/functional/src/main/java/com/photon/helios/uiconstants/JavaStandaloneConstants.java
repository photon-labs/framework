package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JavaStandaloneConstants {

	private Log log = LogFactory.getLog("JavaStandaloneConstants");

	private String javaSAArchetypeName = "javaSAArchetypeName";
	private String javaSAArchetypeDesc = "javaSAArchetypeDesc";
	private String javaSAArchetypeAppCode = "javaSAArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String javaSAArchetypeEditApp = "javaSAArchetypeEditApp";

	public JavaStandaloneConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadJavaStandaloneConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("JavaStandaloneConstants::"+localException.getMessage());
		}
	}
	public String getJavaSAArchetypeName() {
		return javaSAArchetypeName;
	}
	public void setJavaSAArchetypeName(String javaSAArchetypeName) {
		this.javaSAArchetypeName = javaSAArchetypeName;
	}
	public String getJavaSAArchetypeDesc() {
		return javaSAArchetypeDesc;
	}
	public void setJavaSAArchetypeDesc(String javaSAArchetypeDesc) {
		this.javaSAArchetypeDesc = javaSAArchetypeDesc;
	}
	public String getJavaSAArchetypeAppCode() {
		return javaSAArchetypeAppCode;
	}
	public void setJavaSAArchetypeAppCode(String javaSAArchetypeAppCode) {
		this.javaSAArchetypeAppCode = javaSAArchetypeAppCode;
	}
	public String getTechnologyValue() {
		return technologyValue;
	}
	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}
	public String getJavaSAArchetypeEditApp() {
		return javaSAArchetypeEditApp;
	}
	public void setJavaSAArchetypeEditApp(String javaSAArchetypeEditApp) {
		this.javaSAArchetypeEditApp = javaSAArchetypeEditApp;
	}



}
