package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SitecoreConstants {

	private Log log = LogFactory.getLog("SitecoreConstants");
	private String sitecoreArchetypeName = "sitecoreArchetypeName";
	private String sitecoreArchetypeDesc = "sitecoreArchetypeDesc";
	private String sitecoreArchetypeAppCode = "sitecoreArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String sitecoreArchetypeEditApp = "sitecoreArchetypeEditApp";
	private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
    private String buildName = "buildName";
	
	public SitecoreConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadSiteCoreConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("SitecoreConstants::"+localException.getMessage());
		}
	}

	public String getSitecoreArchetypeName() {
		return sitecoreArchetypeName;
	}

	public void setSitecoreArchetypeName(String sitecoreArchetypeName) {
		this.sitecoreArchetypeName = sitecoreArchetypeName;
	}

	public String getSitecoreArchetypeDesc() {
		return sitecoreArchetypeDesc;
	}

	public void setSitecoreArchetypeDesc(String sitecoreArchetypeDesc) {
		this.sitecoreArchetypeDesc = sitecoreArchetypeDesc;
	}

	public String getSitecoreArchetypeAppCode() {
		return sitecoreArchetypeAppCode;
	}

	public void setSitecoreArchetypeAppCode(String sitecoreArchetypeAppCode) {
		this.sitecoreArchetypeAppCode = sitecoreArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getSitecoreArchetypeEditApp() {
		return sitecoreArchetypeEditApp;
	}

	public void setSitecoreArchetypeEditApp(String sitecoreArchetypeEditApp) {
		this.sitecoreArchetypeEditApp = sitecoreArchetypeEditApp;
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
