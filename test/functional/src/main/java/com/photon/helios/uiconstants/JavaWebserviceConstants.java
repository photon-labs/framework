package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JavaWebserviceConstants {

	private Log log = LogFactory.getLog("JavaWebserviceConstants");

	private String javaWSArchetypeName = "javaWSArchetypeName";
	private String javaWSArchetypeDesc = "javaWSArchetypeDesc";
	private String javaWSArchetypeAppCode = "javaWSArchetypeAppCode";
	private String technologyValue = "technologyValue";
	private String javaWSArchetypeEditApp = "javaWSArchetypeEditApp";
	private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
    private String buildName = "buildName";
	
	public JavaWebserviceConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadJavaWebServiceConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("JavaWebserviceConstants::"+localException.getMessage());
		}
	}

	public String getJavaWSArchetypeName() {
		return javaWSArchetypeName;
	}

	public void setJavaWSArchetypeName(String javaWSArchetypeName) {
		this.javaWSArchetypeName = javaWSArchetypeName;
	}

	public String getJavaWSArchetypeDesc() {
		return javaWSArchetypeDesc;
	}

	public void setJavaWSArchetypeDesc(String javaWSArchetypeDesc) {
		this.javaWSArchetypeDesc = javaWSArchetypeDesc;
	}

	public String getJavaWSArchetypeAppCode() {
		return javaWSArchetypeAppCode;
	}

	public void setJavaWSArchetypeAppCode(String javaWSArchetypeAppCode) {
		this.javaWSArchetypeAppCode = javaWSArchetypeAppCode;
	}

	public String getTechnologyValue() {
		return technologyValue;
	}

	public void setTechnologyValue(String technologyValue) {
		this.technologyValue = technologyValue;
	}

	public String getJavaWSArchetypeEditApp() {
		return javaWSArchetypeEditApp;
	}

	public void setJavaWSArchetypeEditApp(String javaWSArchetypeEditApp) {
		this.javaWSArchetypeEditApp = javaWSArchetypeEditApp;
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