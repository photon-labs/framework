package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JQueryMobileWidgetConstants {

	private Log log = LogFactory.getLog("JQueryMobileWidgetConstants");
	
     private String jQueryMobArchetypeName = "jQueryMobArchetypeName";
     private String jQueryMobArchetypeDesc = "jQueryMobArchetypeDesc";
     private String jQueryMobArchetypeAppCode = "jQueryMobArchetypeAppCode";
     private String webLayerValue = "webLayerValue";
     private String widgetValue = "widgetValue";
     private String jQueryMobArchetypeEditLink = "jQueryMobArchetypeEditLink";
     private String configName = "configName";
     private String configHost = "configHost";
     private String configPort = "configPort";
     private String configDeployDir = "configDeployDir";
     private String configContext = "configContext";
     private String buildName = "buildname";
     
	public JQueryMobileWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadJqueryWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("JQueryMobileWidgetConstants::"+localException.getMessage());
		}
	}

	public String getjQueryMobArchetypeName() {
		return jQueryMobArchetypeName;
	}

	public void setjQueryMobArchetypeName(String jQueryMobArchetypeName) {
		this.jQueryMobArchetypeName = jQueryMobArchetypeName;
	}

	public String getjQueryMobArchetypeDesc() {
		return jQueryMobArchetypeDesc;
	}

	public void setjQueryMobArchetypeDesc(String jQueryMobArchetypeDesc) {
		this.jQueryMobArchetypeDesc = jQueryMobArchetypeDesc;
	}

	public String getjQueryMobArchetypeAppCode() {
		return jQueryMobArchetypeAppCode;
	}

	public void setjQueryMobArchetypeAppCode(String jQueryMobArchetypeAppCode) {
		this.jQueryMobArchetypeAppCode = jQueryMobArchetypeAppCode;
	}

	public String getWebLayerValue() {
		return webLayerValue;
	}

	public void setWebLayerValue(String webLayerValue) {
		this.webLayerValue = webLayerValue;
	}

	public String getWidgetValue() {
		return widgetValue;
	}

	public void setWidgetValue(String widgetValue) {
		this.widgetValue = widgetValue;
	}

	public String getjQueryMobArchetypeEditLink() {
		return jQueryMobArchetypeEditLink;
	}

	public void setjQueryMobArchetypeEditLink(String jQueryMobArchetypeEditLink) {
		this.jQueryMobArchetypeEditLink = jQueryMobArchetypeEditLink;
	}
	
	public String getConfigName() {
		return configName;
	}
	
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	public String getConfigDeployDir() {
		return configDeployDir;
	}
	
	public void setConfigDeployDir(String configDeployDir) {
		this.configDeployDir = configDeployDir;
	}
	
	public String getConfigContext() {
		return configContext;
	}
	
	public void setConfigContext(String configContext) {
		this.configContext = configContext;
	}
	
	public String getConfigPort() {
		return configPort;
	}
	
	public void setConfigPort(String configPort) {
		this.configPort = configPort;
	}
	
	public String getConfigHost() {
		return configHost;
	}
	
	public void setConfigHost(String configHost) {
		this.configHost = configHost;
	}
	
	public String getBuildName() {
		return buildName;
	}
	
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	
	
	}