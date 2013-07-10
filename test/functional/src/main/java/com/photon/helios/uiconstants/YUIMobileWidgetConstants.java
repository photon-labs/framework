package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YUIMobileWidgetConstants {

	private Log log = LogFactory.getLog("YUIMobileWidgetConstants");

	private String YUIMobileArchetypeName = "YUIMobileArchetypeName";
	private String YUIMobileArchetypeDesc = "YUIMobileArchetypeDesc";
	private String YUIMobileArchetypeAppCode = "YUIMobileArchetypeAppCode";
	private String webLayerValue = "webLayerValue";
	private String widgetValue = "widgetValue";
	private String YUIMobileArchetypeEditLink = "YUIMobileArchetypeEditLink";
	private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
    private String buildName = "buildname";



	public YUIMobileWidgetConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadYUIMobileWidgetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("YUIMobileWidgetConstants::"+localException.getMessage());
		}
	}

	public String getYUIMobileArchetypeEditLink() {
		return YUIMobileArchetypeEditLink;
	}


	public void setYUIMobileArchetypeEditLink(String yUIMobileArchetypeEditLink) {
		YUIMobileArchetypeEditLink = yUIMobileArchetypeEditLink;
	}

	public String getYUIMobileArchetypeName() {
		return YUIMobileArchetypeName;
	}

	public void setYUIMobileArchetypeName(String yUIMobileArchetypeName) {
		YUIMobileArchetypeName = yUIMobileArchetypeName;
	}

	public String getYUIMobileArchetypeDesc() {
		return YUIMobileArchetypeDesc;
	}

	public void setYUIMobileArchetypeDesc(String yUIMobileArchetypeDesc) {
		YUIMobileArchetypeDesc = yUIMobileArchetypeDesc;
	}

	public String getYUIMobileArchetypeAppCode() {
		return YUIMobileArchetypeAppCode;
	}

	public void setYUIMobileArchetypeAppCode(String yUIMobileArchetypeAppCode) {
		YUIMobileArchetypeAppCode = yUIMobileArchetypeAppCode;
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
