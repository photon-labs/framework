package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AspDotNetConstants {

	private Log log = LogFactory.getLog("AspDotNetConstants");
	private String aspDotnetArchetypeName = "aspDotnetArchetypeName";
	private String aspDotnetArchetypeDesc = "aspDotnetArchetypeDesc";
	private String aspDotnetArchetypeAppCode = "aspDotnetArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String aspDotnetArchetypeEditApp = "aspDotnetArchetypeEditApp";
	public AspDotNetConstants()
	{
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadAspDotNetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("AspDotNetConstantsXml::"+localException.getMessage());
		}
	}

	public String getAspDotnetArchetypeName() {
		return aspDotnetArchetypeName;
	}

	public void setAspDotnetArchetypeName(String aspDotnetArchetypeName) {
		this.aspDotnetArchetypeName = aspDotnetArchetypeName;
	}

	public String getAspDotnetArchetypeDesc() {
		return aspDotnetArchetypeDesc;
	}

	public void setAspDotnetArchetypeDesc(String aspDotnetArchetypeDesc) {
		this.aspDotnetArchetypeDesc = aspDotnetArchetypeDesc;
	}

	public String getAspDotnetArchetypeAppCode() {
		return aspDotnetArchetypeAppCode;
	}

	public void setAspDotnetArchetypeAppCode(String aspDotnetArchetypeAppCode) {
		this.aspDotnetArchetypeAppCode = aspDotnetArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getAspDotnetArchetypeEditApp() {
		return aspDotnetArchetypeEditApp;
	}

	public void setAspDotnetArchetypeEditApp(String aspDotnetArchetypeEditApp) {
		this.aspDotnetArchetypeEditApp = aspDotnetArchetypeEditApp;
	}
	
	
	
}