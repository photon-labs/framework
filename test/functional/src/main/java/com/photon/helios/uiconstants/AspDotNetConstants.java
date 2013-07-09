package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AspDotNetConstants {

	private Log log = LogFactory.getLog("AspDotNetConstants");
	private String aspDotnetArchetypeName = "aspDotnetArchetypeName";
	private String aspDotnetArchetypeDesc = "aspDotnetArchetypeDesc";
	private String aspDotnetArchetypeAppCode = "aspDotnetArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String aspDotnetArchetypeEditApp = "aspDotnetArchetypeEditApp";
	private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
    private String buildName = "buildName";
    
	public AspDotNetConstants()
	{
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadAspDotNetConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("AspDotNetConstantsXml::"+localException.getMessage());
		}
	}

	public String getAspDotnetArchetypeName() {
		return aspDotnetArchetypeName;
	}

	public void setAspDotnetArchetypeName(String aspDotnetArchetypeName) {
		this.aspDotnetArchetypeName = aspDotnetArchetypeName;
	}

	public String getAspDotnetArchetypeDesc() {
		return aspDotnetArchetypeDesc;
	}

	public void setAspDotnetArchetypeDesc(String aspDotnetArchetypeDesc) {
		this.aspDotnetArchetypeDesc = aspDotnetArchetypeDesc;
	}

	public String getAspDotnetArchetypeAppCode() {
		return aspDotnetArchetypeAppCode;
	}

	public void setAspDotnetArchetypeAppCode(String aspDotnetArchetypeAppCode) {
		this.aspDotnetArchetypeAppCode = aspDotnetArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getAspDotnetArchetypeEditApp() {
		return aspDotnetArchetypeEditApp;
	}

	public void setAspDotnetArchetypeEditApp(String aspDotnetArchetypeEditApp) {
		this.aspDotnetArchetypeEditApp = aspDotnetArchetypeEditApp;
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
	
}