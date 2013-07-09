package com.photon.helios.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HeliosUiConstants {

	private Log log = LogFactory.getLog("HeliosUiConstants");

	/**
	 * Login Page
	 */

	private String loginUserName = "loginUserName";
	private String loginPassword = "loginPassword";
	private String loginButton = "loginButton";

	/**
	 * Menu Page
	 */
	private String projectButton = "projectButton";
	private String addProjectButton = "addProjectButton";
	private String customerDropdown = "customerDropdown";
	private String customerName = "customerName";
	
	/**
	 * Create Project Page
	 */

	private String projectName = "projectName";
	private String projectCode = "projectCode";
	private String projectVersion = "projectVersion";
	private String projectDescription = "projectDescription";
	private String projectBuildMyself = "projectBuildMyself";
	private String appLayerAppCode = "appLayerAppCode";
	private String appLayerTechnology = "appLayerTechnology";
	private String appLayerTechnologyVersion = "appLayerTechnologyVersion";
	private String removeAppLayer = "removeAppLayer";
	private String webLayerAppCode = "webLayerAppCode";
	private String webLayer = "webLayer";
	private String webLayerWidget = "webLayerWidget";
	private String webLayerWidgetVersion = "webLayerWidgetVersion";
	private String removeWebLayer = "removeWebLayer";
	private String mobLayerAppCode = "mobLayerAppCode";
	private String mobLayer = "mobLayer";
	private String mobileType = "mobileType";
	private String mobileVersion = "mobileVersion";
	private String removeMobileLayer = "removeMobileLayer";
	private String projectCreateButton = "projectCreateButton";

	/**
	 * AppInfo Page
	 */
	private String appInfoTab = "appInfoTab";
	private String serverDropdown = "serverDropdown";
	private String serverVersionDropdown = "serverVersionDropdown";
	private String wampServer = "wampServer";
	private String wampServerVersion = "wampServerVersion";
	private String apacheTomcatServer = "apacheTomcatServer";
	private String apacheTomcatServerVersion = "apacheTomcatServerVersion";
	private String nodeJsServer = "nodeJsServer";
	private String nodeJsServerVersion = "nodeJsServerVersion";	
	private String iisServer = "iisServer";
	private String iisServerVersion = "iisServerVersion";
	private String sharepointServer = "sharepointServer";
	private String sharepointServerVersion = "sharepointServerVersion";	
	private String dbDropdown = "dbDropdown";
	private String mysqlDb = "mysqlDb";	
	private String dbVersionDropdown = "dbVersionDropdown";
	private String mysqlVersionValue = "mysqlVersionValue";	
	private String restJsonCheckbox = "restJsonCheckbox";
	private String restXmlCheckbox = "restXmlCheckbox";
	private String soap1Checkbox = "soap1.0Checkbox";
	private String soap2Checkbox = "soap2.0Checkbox";
	private String jmsCheckbox = "jmsCheckbox";
	private String functionalFramework = "functionalFramework";
	private String testNGValue = "testNGValue";
	private String functionalTool = "functionalTool";
	private String cucumberValue = "cucumberValue";
	private String webdriverValue = "webdriverValue";
	private String gridValue = "gridValue";
	private String jUnitValue = "jUnitValue";
	private String phpUnitValue = "phpUnitValue";
	private String functionalToolVersion = "functionalToolVersion";
	private String functionalToolVersionValue = "functionalToolVersionValue";
	
	/**
	 * Feature Page
	 */
	
	private String featureTab = "featureTab";
	
	/**
	 * Configuration Page
	 */

	private String configTab = "configTab";
	private String configButton = "configButton";
	private String addConfig = "addConfig";
	private String chooseServer = "chooseServer";
	private String configName = "configName";
    private String configHost = "configHost";
    private String configPort = "configPort";
    private String configDeployDir = "configDeployDir";
    private String configContext = "configContext";
	private String configUpdate = "configUpdate";
		
	/**
	 * Build Page
	 */
	
	private String buildTab = "buildtab";
	private String generateBuild = "generatebuild";
    private String buildName = "buildname";
    private String chooseEnvironment = "chooseenvironment";
    private String chooseLogs = "chooselogs";
	private String buildRun = "buildrun";
	
	public HeliosUiConstants() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadHeliosUiConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("HeliosUiConstants::" + localException.getMessage());
		}
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(String loginButton) {
		this.loginButton = loginButton;
	}

	public String getProjectButton() {
		return projectButton;
	}

	public void setProjectButton(String projectButton) {
		this.projectButton = projectButton;
	}

	public String getAddProjectButton() {
		return addProjectButton;
	}

	public void setAddProjectButton(String addProjectButton) {
		this.addProjectButton = addProjectButton;
	}
	
	public String getCustomerDropdown() {
		return customerDropdown;
	}

	public void setCustomerDropdown(String customerDropdown) {
		this.customerDropdown = customerDropdown;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectBuildMyself() {
		return projectBuildMyself;
	}

	public void setProjectBuildMyself(String projectBuildMyself) {
		this.projectBuildMyself = projectBuildMyself;
	}

	public String getAppLayerAppCode() {
		return appLayerAppCode;
	}

	public void setAppLayerAppCode(String appLayerAppCode) {
		this.appLayerAppCode = appLayerAppCode;
	}

	public String getAppLayerTechnology() {
		return appLayerTechnology;
	}

	public void setAppLayerTechnology(String appLayerTechnology) {
		this.appLayerTechnology = appLayerTechnology;
	}

	public String getAppLayerTechnologyVersion() {
		return appLayerTechnologyVersion;
	}

	public void setAppLayerTechnologyVersion(String appLayerTechnologyVersion) {
		this.appLayerTechnologyVersion = appLayerTechnologyVersion;
	}

	public String getRemoveAppLayer() {
		return removeAppLayer;
	}

	public void setRemoveAppLayer(String removeAppLayer) {
		this.removeAppLayer = removeAppLayer;
	}

	public String getWebLayerAppCode() {
		return webLayerAppCode;
	}

	public void setWebLayerAppCode(String webLayerAppCode) {
		this.webLayerAppCode = webLayerAppCode;
	}

	public String getWebLayer() {
		return webLayer;
	}

	public void setWebLayer(String webLayer) {
		this.webLayer = webLayer;
	}

	public String getWebLayerWidget() {
		return webLayerWidget;
	}

	public void setWebLayerWidget(String webLayerWidget) {
		this.webLayerWidget = webLayerWidget;
	}

	public String getWebLayerWidgetVersion() {
		return webLayerWidgetVersion;
	}

	public void setWebLayerWidgetVersion(String webLayerWidgetVersion) {
		this.webLayerWidgetVersion = webLayerWidgetVersion;
	}

	public String getRemoveWebLayer() {
		return removeWebLayer;
	}

	public void setRemoveWebLayer(String removeWebLayer) {
		this.removeWebLayer = removeWebLayer;
	}

	public String getMobLayerAppCode() {
		return mobLayerAppCode;
	}

	public void setMobLayerAppCode(String mobLayerAppCode) {
		this.mobLayerAppCode = mobLayerAppCode;
	}

	public String getMobLayer() {
		return mobLayer;
	}

	public void setMobLayer(String mobLayer) {
		this.mobLayer = mobLayer;
	}

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getMobileVersion() {
		return mobileVersion;
	}

	public void setMobileVersion(String mobileVersion) {
		this.mobileVersion = mobileVersion;
	}

	public String getRemoveMobileLayer() {
		return removeMobileLayer;
	}

	public void setRemoveMobileLayer(String removeMobileLayer) {
		this.removeMobileLayer = removeMobileLayer;
	}

	public String getProjectCreateButton() {
		return projectCreateButton;
	}

	public void setProjectCreateButton(String projectCreateButton) {
		this.projectCreateButton = projectCreateButton;
	}

	public String getAppInfoTab() {
		return appInfoTab;
	}

	public String getServerDropdown() {
		return serverDropdown;
	}

	public String getWampServer() {
		return wampServer;
	}

	public void setAppInfoTab(String appInfoTab) {
		this.appInfoTab = appInfoTab;
	}

	public void setServerDropdown(String serverDropdown) {
		this.serverDropdown = serverDropdown;
	}

	public void setWampServer(String wampServer) {
		this.wampServer = wampServer;
	}

	public String getServerVersionDropdown() {
		return serverVersionDropdown;
	}


	public String getDbDropdown() {
		return dbDropdown;
	}

	public String getMysqlDb() {
		return mysqlDb;
	}

	public String getDbVersionDropdown() {
		return dbVersionDropdown;
	}

	public void setServerVersionDropdown(String serverVersionDropdown) {
		this.serverVersionDropdown = serverVersionDropdown;
	}


	public void setDbDropdown(String dbDropdown) {
		this.dbDropdown = dbDropdown;
	}

	public void setMysqlServer(String mysqlDb) {
		this.mysqlDb = mysqlDb;
	}

	public void setDbVersionDropdown(String dbVersionDropdown) {
		this.dbVersionDropdown = dbVersionDropdown;
	}


	public String getRestJsonCheckbox() {
		return restJsonCheckbox;
	}

	public String getRestXmlCheckbox() {
		return restXmlCheckbox;
	}

	public String getSoap1Checkbox() {
		return soap1Checkbox;
	}

	public String getSoap2Checkbox() {
		return soap2Checkbox;
	}

	public String getJmsCheckbox() {
		return jmsCheckbox;
	}

	public String getFunctionalFramework() {
		return functionalFramework;
	}

	public String getTestNGValue() {
		return testNGValue;
	}

	public String getFunctionalTool() {
		return functionalTool;
	}

	public String getCucumberValue() {
		return cucumberValue;
	}

	public void setMysqlDb(String mysqlDb) {
		this.mysqlDb = mysqlDb;
	}

	public void setRestJsonCheckbox(String restJsonCheckbox) {
		this.restJsonCheckbox = restJsonCheckbox;
	}

	public void setRestXmlCheckbox(String restXmlCheckbox) {
		this.restXmlCheckbox = restXmlCheckbox;
	}

	public void setSoap1Checkbox(String soap1Checkbox) {
		this.soap1Checkbox = soap1Checkbox;
	}

	public void setSoap2Checkbox(String soap2Checkbox) {
		this.soap2Checkbox = soap2Checkbox;
	}

	public void setJmsCheckbox(String jmsCheckbox) {
		this.jmsCheckbox = jmsCheckbox;
	}

	public void setFunctionalFramework(String functionalFramework) {
		this.functionalFramework = functionalFramework;
	}

	public void setTestNGValue(String testNGValue) {
		this.testNGValue = testNGValue;
	}

	public void setFunctionalTool(String functionalTool) {
		this.functionalTool = functionalTool;
	}

	public void setCucumberValue(String cucumberValue) {
		this.cucumberValue = cucumberValue;
	}

	public String getWebdriverValue() {
		return webdriverValue;
	}

	public String getGridValue() {
		return gridValue;
	}

	public String getjUnitValue() {
		return jUnitValue;
	}

	public String getPhpUnitValue() {
		return phpUnitValue;
	}

	public void setWebdriverValue(String webdriverValue) {
		this.webdriverValue = webdriverValue;
	}

	public void setGridValue(String gridValue) {
		this.gridValue = gridValue;
	}

	public void setjUnitValue(String jUnitValue) {
		this.jUnitValue = jUnitValue;
	}

	public void setPhpUnitValue(String phpUnitValue) {
		this.phpUnitValue = phpUnitValue;
	}

	public String getFunctionalToolVersion() {
		return functionalToolVersion;
	}

	public String getFunctionalToolVersionValue() {
		return functionalToolVersionValue;
	}

	public void setFunctionalToolVersion(String functionalToolVersion) {
		this.functionalToolVersion = functionalToolVersion;
	}

	public void setFunctionalToolVersionValue(String functionalToolVersionValue) {
		this.functionalToolVersionValue = functionalToolVersionValue;
	}

	public String getNodeJsServer() {
		return nodeJsServer;
	}

	public void setNodeJsServer(String nodeJsServer) {
		this.nodeJsServer = nodeJsServer;
	}

	public String getApacheTomcatServer() {
		return apacheTomcatServer;
	}

	public void setApacheTomcatServer(String apacheTomcatServer) {
		this.apacheTomcatServer = apacheTomcatServer;
	}

	public String getWampServerVersion() {
		return wampServerVersion;
	}

	public String getApacheTomcatServerVersion() {
		return apacheTomcatServerVersion;
	}

	public String getNodeJsServerVersion() {
		return nodeJsServerVersion;
	}

	public String getMysqlVersionValue() {
		return mysqlVersionValue;
	}

	public void setWampServerVersion(String wampServerVersion) {
		this.wampServerVersion = wampServerVersion;
	}

	public void setApacheTomcatServerVersion(String apacheTomcatServerVersion) {
		this.apacheTomcatServerVersion = apacheTomcatServerVersion;
	}

	public void setNodeJsServerVersion(String nodeJsServerVersion) {
		this.nodeJsServerVersion = nodeJsServerVersion;
	}

	public void setMysqlVersionValue(String mysqlVersionValue) {
		this.mysqlVersionValue = mysqlVersionValue;
	}

	public String getIisServer() {
		return iisServer;
	}

	public String getIisServerVersion() {
		return iisServerVersion;
	}

	public String getSharepointServer() {
		return sharepointServer;
	}

	public String getSharepointServerVersion() {
		return sharepointServerVersion;
	}

	public void setIisServer(String iisServer) {
		this.iisServer = iisServer;
	}

	public void setIisServerVersion(String iisServerVersion) {
		this.iisServerVersion = iisServerVersion;
	}

	public void setSharepointServer(String sharepointServer) {
		this.sharepointServer = sharepointServer;
	}

	public void setSharepointServerVersion(String sharepointServerVersion) {
		this.sharepointServerVersion = sharepointServerVersion;
	}

	public String getFeatureTab() {
		return featureTab;
	}

	public void setFeatureTab(String featureTab) {
		this.featureTab = featureTab;
	}

	public String getConfigTab() {
		return configTab;
	}
	
	public String getConfigButton() {
		return configButton;
	}

	public String getAddConfig() {
		return addConfig;
	}
	
	public String getChooseServer() {
		return chooseServer;
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
	
	public String getConfigUpdate() {
		return configUpdate;
	}
	
	public String getBuildName() {
		return buildName;
	}
	
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	
	public String getGenerateBuild() {
		return generateBuild;
	}
	
	public String getBuildRun() {
		return buildRun;
	}
	
	public String getChooseEnvironment() {
		return chooseEnvironment;
	}
	
	public String getChooseLogs() {
		return chooseLogs;
	}
	
	public String getBuildTab() {
		return buildTab;
	}
}
