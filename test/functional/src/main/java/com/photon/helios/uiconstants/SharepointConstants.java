package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SharepointConstants {

	private Log log = LogFactory.getLog("SharepointConstants");
	
	private String sharepointArchetypeName = "sharepointArchetypeName";
	private String sharepointArchetypeDesc = "sharepointArchetypeDesc";
	private String sharepointArchetypeAppCode = "sharepointArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String sharepointArchetypeEditApp = "sharepointArchetypeEditApp";
	
	public SharepointConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadSharePointConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("SharepointConstants::" + localException.getMessage());
		}
	}

	public String getSharepointArchetypeName() {
		return sharepointArchetypeName;
	}

	public void setSharepointArchetypeName(String sharepointArchetypeName) {
		this.sharepointArchetypeName = sharepointArchetypeName;
	}

	public String getSharepointArchetypeDesc() {
		return sharepointArchetypeDesc;
	}

	public void setSharepointArchetypeDesc(String sharepointArchetypeDesc) {
		this.sharepointArchetypeDesc = sharepointArchetypeDesc;
	}

	public String getSharepointArchetypeAppCode() {
		return sharepointArchetypeAppCode;
	}

	public void setSharepointArchetypeAppCode(String sharepointArchetypeAppCode) {
		this.sharepointArchetypeAppCode = sharepointArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getSharepointArchetypeEditApp() {
		return sharepointArchetypeEditApp;
	}

	public void setSharepointArchetypeEditApp(String sharepointArchetypeEditApp) {
		this.sharepointArchetypeEditApp = sharepointArchetypeEditApp;
	}

	
	
}