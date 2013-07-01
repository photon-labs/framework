package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Drupal7Constants {

	private Log log = LogFactory.getLog("Drupal7Constants");
	private String drupal7ArchetypeName = "drupal7ArchetypeName";
	private String drupal7ArchetypeDesc = "drupal7ArchetypeDesc";
	private String drupal7ArchetypeAppCode = "drupal7ArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String drupal7ArchetypeEditApp = "drupal7ArchetypeEditApp";
	public Drupal7Constants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadDrupal7Constants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Drupal7Constants::" + localException.getMessage());
		}
	}

	public String getDrupal7ArchetypeName() {
		return drupal7ArchetypeName;
	}

	public void setDrupal7ArchetypeName(String drupal7ArchetypeName) {
		this.drupal7ArchetypeName = drupal7ArchetypeName;
	}

	public String getDrupal7ArchetypeDesc() {
		return drupal7ArchetypeDesc;
	}

	public void setDrupal7ArchetypeDesc(String drupal7ArchetypeDesc) {
		this.drupal7ArchetypeDesc = drupal7ArchetypeDesc;
	}

	public String getDrupal7ArchetypeAppCode() {
		return drupal7ArchetypeAppCode;
	}

	public void setDrupal7ArchetypeAppCode(String drupal7ArchetypeAppCode) {
		this.drupal7ArchetypeAppCode = drupal7ArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getDrupal7ArchetypeEditApp() {
		return drupal7ArchetypeEditApp;
	}

	public void setDrupal7ArchetypeEditApp(String drupal7ArchetypeEditApp) {
		this.drupal7ArchetypeEditApp = drupal7ArchetypeEditApp;
	}


}
