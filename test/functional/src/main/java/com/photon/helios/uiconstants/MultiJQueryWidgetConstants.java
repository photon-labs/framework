package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiJQueryWidgetConstants {

	private Log log = LogFactory.getLog("MultiJQueryWidgetConstants");

	private String multiJQueryArchetypeName = "multiJQueryArchetypeName";
    private String multiJQueryArchetypeDesc = "multiJQueryArchetypeDesc";
    private String multiJQueryArchetypeAppCode = "multiJQueryArchetypeAppCode";
    private String webLayerValue = "webLayerValue";
    private String widgetValue = "widgetValue";
    private String multiJQueryArchetypeEditLink = "multiJQueryArchetypeEditLink";
    private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
    private String buildName = "buildname";
    
	public MultiJQueryWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMultiChannelJQueryWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("MultiJQueryWidgetConstants::"+localException.getMessage());
		}
	}


	public String getMultiJQueryArchetypeName() {
		return multiJQueryArchetypeName;
	}


	public void setMultiJQueryArchetypeName(String multiJQueryArchetypeName) {
		this.multiJQueryArchetypeName = multiJQueryArchetypeName;
	}


	public String getMultiJQueryArchetypeDesc() {
		return multiJQueryArchetypeDesc;
	}


	public void setMultiJQueryArchetypeDesc(String multiJQueryArchetypeDesc) {
		this.multiJQueryArchetypeDesc = multiJQueryArchetypeDesc;
	}


	public String getMultiJQueryArchetypeAppCode() {
		return multiJQueryArchetypeAppCode;
	}


	public void setMultiJQueryArchetypeAppCode(String multiJQueryArchetypeAppCode) {
		this.multiJQueryArchetypeAppCode = multiJQueryArchetypeAppCode;
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


	public String getMultiJQueryArchetypeEditLink() {
		return multiJQueryArchetypeEditLink;
	}


	public void setMultiJQueryArchetypeEditLink(String multiJQueryArchetypeEditLink) {
		this.multiJQueryArchetypeEditLink = multiJQueryArchetypeEditLink;
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
