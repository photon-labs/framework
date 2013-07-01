package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class WordpressConstants {
	
	private Log log = LogFactory.getLog("WordpressConstants");
	

	private String wordpressArchetypeName = "wordpressArchetypeName";
	private String wordpressArchetypeDesc = "wordpressArchetypeDesc";
	private String wordpressArchetypeAppCode = "wordpressArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String wordpressArchetypeEditApp = "wordpressArchetypeEditApp";
	public WordpressConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadWordPressConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("WordpressConstants::"+localException.getMessage());
		}
	}

	public String getWordpressArchetypeName() {
		return wordpressArchetypeName;
	}

	public void setWordpressArchetypeName(String wordpressArchetypeName) {
		this.wordpressArchetypeName = wordpressArchetypeName;
	}

	public String getWordpressArchetypeDesc() {
		return wordpressArchetypeDesc;
	}

	public void setWordpressArchetypeDesc(String wordpressArchetypeDesc) {
		this.wordpressArchetypeDesc = wordpressArchetypeDesc;
	}

	public String getWordpressArchetypeAppCode() {
		return wordpressArchetypeAppCode;
	}

	public void setWordpressArchetypeAppCode(String wordpressArchetypeAppCode) {
		this.wordpressArchetypeAppCode = wordpressArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getWordpressArchetypeEditApp() {
		return wordpressArchetypeEditApp;
	}

	public void setWordpressArchetypeEditApp(String wordpressArchetypeEditApp) {
		this.wordpressArchetypeEditApp = wordpressArchetypeEditApp;
	}
	
	
	
	}
