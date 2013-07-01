package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhpConstants {	

	private Log log = LogFactory.getLog("PhpConstants");
	
	private String phpArchetypeName = "phpArchetypeName";
	private String phpArchetypeDesc = "phpArchetypeDesc";
	private String phpArchetypeAppCode = "phpArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String phpArchetypeEditLink = "phpArchetypeEditLink";
   	
	public PhpConstants()
	{
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadPhpConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("PhpConstants::"+localException.getMessage());
		}
	}


	public String getPhpArchetypeEditLink() {
		return phpArchetypeEditLink;
	}


	public void setPhpArchetypeEditLink(String phpArchetypeEditLink) {
		this.phpArchetypeEditLink = phpArchetypeEditLink;
	}


	public String getPhpArchetypeName() {
		return phpArchetypeName;
	}

	public void setPhpArchetypeName(String phpArchetypeName) {
		this.phpArchetypeName = phpArchetypeName;
	}

	public String getPhpArchetypeDesc() {
		return phpArchetypeDesc;
	}

	public void setPhpArchetypeDesc(String phpArchetypeDesc) {
		this.phpArchetypeDesc = phpArchetypeDesc;
	}

	public String getPhpArchetypeAppCode() {
		return phpArchetypeAppCode;
	}


	public void setPhpArchetypeAppCode(String phpArchetypeAppCode) {
		this.phpArchetypeAppCode = phpArchetypeAppCode;
	}


	public String getTechnologyValue() {
		return technologyValue;
	}


	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

}