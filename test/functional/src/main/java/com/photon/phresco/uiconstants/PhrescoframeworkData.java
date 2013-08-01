package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhrescoframeworkData {

	private Log log = LogFactory.getLog("PhrescoframeworkData");

	/**
	 * Project Create Message
	 */
	private String projectCreateMsg = "projectCreateMsg";
	
	/**
	 * App Update Message
	 */
	private String appUpdateMsg = "appUpdateMsg";
	
	/**
	 * Feature Update Message
	 */
	
	private String featureUpdateMsg = "featureUpdateMsg";
	
	/**
	 * Config Update Message
	 */
	private String configUpdateMsg = "configUpdateMsg";
	
	/**
	 * Project Update Message
	 */
	private String projectUpdateMsg = "projectUpdateMsg";
	
	/**
	 * Clone Environment Name
	 */
	private String cloneEnvName = "cloneEnvName";
	
	public PhrescoframeworkData() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadHeliosDataConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in PhrescoframeworkData::" + localException.getMessage());
		}
	}

	public String getAppUpdateMsg() {
		return appUpdateMsg;
	}
	public String getConfigUpdateMsg() {
		return configUpdateMsg;
	}
	public String getProjectUpdateMsg() {
		return projectUpdateMsg;
	}
	public void setAppUpdateMsg(String appUpdateMsg) {
		this.appUpdateMsg = appUpdateMsg;
	}
	public void setConfigUpdateMsg(String configUpdateMsg) {
		this.configUpdateMsg = configUpdateMsg;
	}
	public void setProjectUpdateMsg(String projectUpdateMsg) {
		this.projectUpdateMsg = projectUpdateMsg;
	}
	public String getProjectCreateMsg() {
		return projectCreateMsg;
	}
	public void setProjectCreateMsg(String projectCreateMsg) {
		this.projectCreateMsg = projectCreateMsg;
	}

	/**
	 * @param cloneEnvName the cloneEnvName to set
	 */
	public void setCloneEnvName(String cloneEnvName) {
		this.cloneEnvName = cloneEnvName;
	}

	/**
	 * @return the cloneEnvName
	 */
	public String getCloneEnvName() {
		return cloneEnvName;
	}

	public String getFeatureUpdateMsg() {
		return featureUpdateMsg;
	}
	public void setFeatureUpdateMsg(String featureUpdateMsg) {
		this.featureUpdateMsg = featureUpdateMsg;
	}	
	
	}
