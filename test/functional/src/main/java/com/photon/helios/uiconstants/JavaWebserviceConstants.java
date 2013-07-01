package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JavaWebserviceConstants {

	private Log log = LogFactory.getLog("JavaWebserviceConstants");

	private String javaWSArchetypeName = "javaWSArchetypeName";
	private String javaWSArchetypeDesc = "javaWSArchetypeDesc";
	private String javaWSArchetypeAppCode = "javaWSArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String javaWSArchetypeEditApp = "javaWSArchetypeEditApp";
	
	public JavaWebserviceConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadJavaWebServiceConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("JavaWebserviceConstants::"+localException.getMessage());
		}
	}

	public String getJavaWSArchetypeName() {
		return javaWSArchetypeName;
	}

	public void setJavaWSArchetypeName(String javaWSArchetypeName) {
		this.javaWSArchetypeName = javaWSArchetypeName;
	}

	public String getJavaWSArchetypeDesc() {
		return javaWSArchetypeDesc;
	}

	public void setJavaWSArchetypeDesc(String javaWSArchetypeDesc) {
		this.javaWSArchetypeDesc = javaWSArchetypeDesc;
	}

	public String getJavaWSArchetypeAppCode() {
		return javaWSArchetypeAppCode;
	}

	public void setJavaWSArchetypeAppCode(String javaWSArchetypeAppCode) {
		this.javaWSArchetypeAppCode = javaWSArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getJavaWSArchetypeEditApp() {
		return javaWSArchetypeEditApp;
	}

	public void setJavaWSArchetypeEditApp(String javaWSArchetypeEditApp) {
		this.javaWSArchetypeEditApp = javaWSArchetypeEditApp;
	}

	
	
}