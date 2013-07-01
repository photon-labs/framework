package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Drupal6Constants {

	private Log log = LogFactory.getLog("Drupal6Constants");

	private String drupal6ArchetypeName = "drupal6ArchetypeName";
	private String drupal6ArchetypeDesc = "drupal6ArchetypeDesc";
	private String drupal6ArchetypeAppCode = "drupal6ArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String drupal6ArchetypeEditApp = "drupal6ArchetypeEditApp";
	public Drupal6Constants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadDrupal6Constants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Drupal6Constants::"+localException.getMessage());
		}
	}
	public String getDrupal6ArchetypeName() {
		return drupal6ArchetypeName;
	}
	public void setDrupal6ArchetypeName(String drupal6ArchetypeName) {
		this.drupal6ArchetypeName = drupal6ArchetypeName;
	}
	public String getDrupal6ArchetypeDesc() {
		return drupal6ArchetypeDesc;
	}
	public void setDrupal6ArchetypeDesc(String drupal6ArchetypeDesc) {
		this.drupal6ArchetypeDesc = drupal6ArchetypeDesc;
	}
	public String getDrupal6ArchetypeAppCode() {
		return drupal6ArchetypeAppCode;
	}
	public void setDrupal6ArchetypeAppCode(String drupal6ArchetypeAppCode) {
		this.drupal6ArchetypeAppCode = drupal6ArchetypeAppCode;
	}
	public String getTechnologyValue() {
		return technologyValue;
	}
	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}
	public String getDrupal6ArchetypeEditApp() {
		return drupal6ArchetypeEditApp;
	}
	public void setDrupal6ArchetypeEditApp(String drupal6ArchetypeEditApp) {
		this.drupal6ArchetypeEditApp = drupal6ArchetypeEditApp;
	}



}
