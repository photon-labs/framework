package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhpConstants {	

	private Log log = LogFactory.getLog("PhpConstants");
	
	private String phpArchetypeName = "phpArchetypeName";
	private String phpArchetypeDesc = "phpArchetypeDesc";
	private String phpArchetypeAppCode = "phpArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String phpArchetypeEditLink = "phpArchetypeEditLink";
	private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
    private String buildName = "buildName";
   	
	public PhpConstants()
	{
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadPhpConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("PhpConstants::"+localException.getMessage());
		}
	}


	public String getPhpArchetypeEditLink() {
		return phpArchetypeEditLink;
	}


	public void setPhpArchetypeEditLink(String phpArchetypeEditLink) {
		this.phpArchetypeEditLink = phpArchetypeEditLink;
	}


	public String getPhpArchetypeName() {
		return phpArchetypeName;
	}

	public void setPhpArchetypeName(String phpArchetypeName) {
		this.phpArchetypeName = phpArchetypeName;
	}

	public String getPhpArchetypeDesc() {
		return phpArchetypeDesc;
	}

	public void setPhpArchetypeDesc(String phpArchetypeDesc) {
		this.phpArchetypeDesc = phpArchetypeDesc;
	}

	public String getPhpArchetypeAppCode() {
		return phpArchetypeAppCode;
	}


	public void setPhpArchetypeAppCode(String phpArchetypeAppCode) {
		this.phpArchetypeAppCode = phpArchetypeAppCode;
	}


	public String getTechnologyValue() {
		return technologyValue;
	}


	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
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