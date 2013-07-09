package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiYUIWidgetConstants {

	private Log log = LogFactory.getLog("MultiYUIWidgetConstants");

	private String multiYuiArchetypeName = "multiYuiArchetypeName";
    private String multiYuiArchetypeDesc = "multiYuiArchetypeDesc";
    private String multiYuiArchetypeAppCode = "multiYuiArchetypeAppCode";
    private String webLayerValue = "webLayerValue";
    private String widgetValue = "widgetValue";
    private String multiYuiArchetypeEditLink = "multiYuiArchetypeEditLink";
    private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
    private String buildName = "buildname";
    
	
	public MultiYUIWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMultiChannelYUIWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("MultiYUIWidgetConstants::"+localException.getMessage());
		}
	}


	public String getMultiYuiArchetypeName() {
		return multiYuiArchetypeName;
	}


	public void setMultiYuiArchetypeName(String multiYuiArchetypeName) {
		this.multiYuiArchetypeName = multiYuiArchetypeName;
	}


	public String getMultiYuiArchetypeDesc() {
		return multiYuiArchetypeDesc;
	}


	public void setMultiYuiArchetypeDesc(String multiYuiArchetypeDesc) {
		this.multiYuiArchetypeDesc = multiYuiArchetypeDesc;
	}


	public String getMultiYuiArchetypeAppCode() {
		return multiYuiArchetypeAppCode;
	}


	public void setMultiYuiArchetypeAppCode(String multiYuiArchetypeAppCode) {
		this.multiYuiArchetypeAppCode = multiYuiArchetypeAppCode;
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


	public String getMultiYuiArchetypeEditLink() {
		return multiYuiArchetypeEditLink;
	}


	public void setMultiYuiArchetypeEditLink(String multiYuiArchetypeEditLink) {
		this.multiYuiArchetypeEditLink = multiYuiArchetypeEditLink;
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
