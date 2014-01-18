package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhrescoUiConstants {

	private Log log = LogFactory.getLog("PhrescoUiConstants");

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
	private String startDate = "startDate";
	private String endDate = "endDate";
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

	private String appTechnologyJ2EE = "appTechnologyJ2EE";
	private String appTechnologyNodeJS = "appTechnologyNodeJS";
	private String appTechnologyPHP = "appTechnologyPHP";
	private String appTechnologyDrupal6 = "appTechnologyDrupal6";
	private String appTechnologyDrupal7 = "appTechnologyDrupal7";
	private String appTechnologyWordPress = "appTechnologyWordPress";
	private String appTechnologyAspDotNet = "appTechnologyAspDotNet";
	private String appTechnologySharePoint = "appTechnologySharePoint";
	private String appTechnologySiteCore = "appTechnologySiteCore";
	private String appTechnologyJSA = "appTechnologyJSA";

	private String webTechnologyHTML5 = "webTechnologyHTML5";
	private String webTechnologyJMW = "webTechnologyJMW";
	private String webTechnologyYMW = "webTechnologyYMW";
	private String webTechnologyMJW = "webTechnologyMJW";
	private String webTechnologyMYW = "webTechnologyMYW";

	private String mobTechnologyWindows = "mobTechnologyWindows";
	private String mobTechnologyWindowsMetro = "mobTechnologyWindowsMetro";
	private String mobTechnologyWindowsPhone = "mobTechnologyWindowsPhone";
	private String mobTechnologyBlackBerry = "mobTechnologyBlackBerry";
	private String mobTechnologyBlackBerryHybrid = "mobTechnologyBlackBerryHybrid";
	private String mobTechnologyAndroid = "mobTechnologyAndroid";
	private String mobTechnologyAndroidLibrary = "mobTechnologyAndroidLibrary";
	private String mobTechnologyAndroidNative = "mobTechnologyAndroidNative";
	private String mobTechnologyAndroidHybrid = "mobTechnologyAndroidHybrid";
	private String mobTechnologyIphone = "mobTechnologyIphone";
	private String mobTechnologyIphoneWorkspace = "mobTechnologyIphoneWorkspace";
	private String mobTechnologyIphoneLibrary = "mobTechnologyIphoneLibrary";
	private String mobTechnologyIphoneNative = "mobTechnologyIphoneNative";
	private String mobTechnologyIphoneHybrid = "mobTechnologyIphoneHybrid";

	/**
	 * Edit Project Page
	 */

	private String projectUpdateButton = "projectUpdateButton";

	/**
	 * AppInfo Page
	 */
	private String appInfoTab = "appInfoTab";
	private String appDesc = "appDesc";

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
	private String appUpdateButton = "appUpdateButton";
	/**
	 * Feature Page
	 */

	private String featureTab = "featureTab";
	private String featureUpdateButton = "featureUpdateButton";

	/**
	 * Code Validation Page
	 */
	private String codeQualityTab = "codeQualityTab";
	private String codeAnalysisTab = "codeAnalysisTab";
	private String codeValidateAgainstDropdown = "codeValidateAgainstDropdown";
	private String codeValidateCs = "codeValidateCs";
	private String codeValidateJs = "codeValidateJs";
	private String codeValidateJava = "codeValidateJava";
	private String codeValidateHtml = "codeValidateHtml";
	private String codeValidateJsp = "codeValidateJsp";
	private String codeValidateSource = "codeValidateSource";
	private String codeTechnologyDropdown = "codeTechnologyDropdown";
	private String codeValidateFunctionalTest = "codeValidateFunctionalTest";
	private String codeValidateBtn = "codeValidateBtn";
	private String codeCloseBtn = "codeCloseBtn";
	private String validateAgainstSrc = "validateAgainstSrc";
	private String skipUnitTestCheckBox = "skipUnitTestCheckBox";

	/**
	 * Configuration Page
	 */
	private String configTab = "configTab";
	private String configButton = "configButton";
	private String addConfig = "addConfig";
	private String configChooseServer = "configChooseServer";
	private String configServer = "configServer";
	private String configServerDesc = "configServerDesc";
	private String configServerProtocol = "configServerProtocol";
	private String configServerHost = "configServerHost";
	private String configServerPort = "configServerPort";
	private String configServerUName = "configServerUName";
	private String configServerPwd = "configServerPwd";
	private String configServerCertificate = "configServerCertificate";
	private String configServerDeployDir = "configServerDeployDir";
	private String configServerContext = "configServerContext";
	private String configChooseDb = "configChooseDb";
	private String configDb = "configDb";
	private String configDbDesc = "configDbDesc";
	private String configDbHost = "configDbHost";
	private String configDbPort = "configDbPort";
	private String configDbUsername = "configDbUsername";
	private String configDbPwd = "configDbPwd";
	private String configDbName = "configDbName";
	private String configUpdateButton = "configUpdateButton";

	/**
	 * Clone Configuration
	 */
	private String cloneEnv = "cloneEnv";
	private String cloneEnvName = "cloneEnvName";
	private String cloneButton = "cloneButton";

	/**
	 * Build Page
	 */
	private String buildTab = "buildTab";
	private String buildGenerate = "buildGenerate";
	private String buildName = "buildName";
	private String chooseEnvironment = "chooseEnvironment";
	private String chooseLogs = "chooseLogs";
	private String buildRun = "buildRun";
	private String buildNumber = "buildNumber";

	/**
	 * Deploy Build
	 */
	private String deployBuild = "deployBuild";
	private String deployButton = "deployButton";

	/**
	 * Runagainst Source
	 */
	private String runAgainstSourceButton = "runAgainstSourceButton";
	private String executeSqlCheckbox = "executeSqlCheckbox";
	private String runAgainstRunButton = "runAgainstRunButton";
	
	/*
	 * Quality Tab
	 */
	
	private String qualityAssuranceButton = "qualityAssuranceButton";
	private String unitButton = "unitButton";
	private String componentButton = "componentButton";
	private String functionalButton = "functionalButton";
	private String manualButton = "manualButton";
	private String performanceButton = "performanceButton";
	private String loadButton = "loadButton";
	

	/**
	 * Unit Test
	 */

	private String unitTestButton = "unitTestButton";
	private String unitTestAgainst = "unitTestAgainst";
	private String unitTestRun = "unitTestRun";

	/*
	 * Component Test
	 */
	private String componentTestButton = "componentTestButton";
	private String componentKillProcessBtn = "componentKillProcessBtn";
	
	/*
	 * Performance Test
	 */
	private String perfjmxType = "perfjmxType";
	private String perftestAgainst = "perftestAgainst";
	private String perfauthManager = "perfauthManager";
	private String perfRampupPeriod = "perfRampupPeriod";
	private String perfHttpName = "perfHttpName";
	private String perfContext = "perfContext";
	private String perfDataType = "perfDataType";
	private String perfEncodingType = "perfEncodingType";
	private String perfRedirect = "perfRedirect";
	private String perfFollowRedirect = "perfFollowRedirect";
	private String perfKeepAlive = "perfKeepAlive";
	private String perHeaderKey = "perHeaderKey";
	private String perfHeaderValue = "perfHeaderValue";
	private String perfHeaderAddButton = "perfHeaderAddButton";
	private String perParameterKey = "perParameterKey";
	private String perfParameterValue = "perfParameterValue";
	private String perfTestButton = "perfTestButton";

	/*
	 * Pdf Report
	 */

	private String pdfReportTypeDropDown = "pdfReportTypeDropDown";
	private String pdfName = "pdfName";
	private String pdfGenerateBtn = "pdfGenerateBtn";
	private String pdfCloseBtn = "pdfCloseBtn";
	private String pdfOverAllReport = "pdfOverAllReport";
	private String pdfDetailReport = "pdfDetailReport";
	
	/*
	 * Delete Projects
	 */
	
	private String projectDeleteIcon = "projectDeleteIcon";
	private String projectDeletePopUpYesBtn = "projectDeletePopUpYesBtn";
	

	public PhrescoUiConstants() {
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
			log.info("Exception in PhrescoUiConstants::"
					+ localException.getMessage());
		}
	}

	/**
	 * @return the functionalTool
	 */
	public String getFunctionalTool() {
		return functionalTool;
	}

	/**
	 * @param functionalTool
	 *            the functionalTool to set
	 */
	public void setFunctionalTool(String functionalTool) {
		this.functionalTool = functionalTool;
	}

	/**
	 * @return the webdriverValue
	 */
	public String getWebdriverValue() {
		return webdriverValue;
	}

	/**
	 * @param webdriverValue
	 *            the webdriverValue to set
	 */
	public void setWebdriverValue(String webdriverValue) {
		this.webdriverValue = webdriverValue;
	}

	/**
	 * @return the gridValue
	 */
	public String getGridValue() {
		return gridValue;
	}

	/**
	 * @param gridValue
	 *            the gridValue to set
	 */
	public void setGridValue(String gridValue) {
		this.gridValue = gridValue;
	}

	/**
	 * @return the jUnitValue
	 */
	public String getjUnitValue() {
		return jUnitValue;
	}

	/**
	 * @param jUnitValue
	 *            the jUnitValue to set
	 */
	public void setjUnitValue(String jUnitValue) {
		this.jUnitValue = jUnitValue;
	}

	/**
	 * @return the phpUnitValue
	 */
	public String getPhpUnitValue() {
		return phpUnitValue;
	}

	/**
	 * @param phpUnitValue
	 *            the phpUnitValue to set
	 */
	public void setPhpUnitValue(String phpUnitValue) {
		this.phpUnitValue = phpUnitValue;
	}

	/**
	 * @return the functionalToolVersion
	 */
	public String getFunctionalToolVersion() {
		return functionalToolVersion;
	}

	/**
	 * @param functionalToolVersion
	 *            the functionalToolVersion to set
	 */
	public void setFunctionalToolVersion(String functionalToolVersion) {
		this.functionalToolVersion = functionalToolVersion;
	}

	/**
	 * @return the functionalToolVersionValue
	 */
	public String getFunctionalToolVersionValue() {
		return functionalToolVersionValue;
	}

	/**
	 * @param functionalToolVersionValue
	 *            the functionalToolVersionValue to set
	 */
	public void setFunctionalToolVersionValue(String functionalToolVersionValue) {
		this.functionalToolVersionValue = functionalToolVersionValue;
	}

	/**
	 * @return the appUpdateButton
	 */
	public String getAppUpdateButton() {
		return appUpdateButton;
	}

	/**
	 * @param appUpdateButton
	 *            the appUpdateButton to set
	 */
	public void setAppUpdateButton(String appUpdateButton) {
		this.appUpdateButton = appUpdateButton;
	}

	/**
	 * @return the featureTab
	 */
	public String getFeatureTab() {
		return featureTab;
	}

	/**
	 * @param featureTab
	 *            the featureTab to set
	 */
	public void setFeatureTab(String featureTab) {
		this.featureTab = featureTab;
	}

	/**
	 * @return the featureUpdateButton
	 */
	public String getFeatureUpdateButton() {
		return featureUpdateButton;
	}

	/**
	 * @param featureUpdateButton
	 *            the featureUpdateButton to set
	 */
	public void setFeatureUpdateButton(String featureUpdateButton) {
		this.featureUpdateButton = featureUpdateButton;
	}

	/**
	 * @return the codeQualityTab
	 */
	public String getCodeQualityTab() {
		return codeQualityTab;
	}

	/**
	 * @param codeQualityTab
	 *            the codeQualityTab to set
	 */
	public void setCodeQualityTab(String codeQualityTab) {
		this.codeQualityTab = codeQualityTab;
	}

	/**
	 * @return the codeAnalysisTab
	 */
	public String getCodeAnalysisTab() {
		return codeAnalysisTab;
	}

	/**
	 * @param codeAnalysisTab
	 *            the codeAnalysisTab to set
	 */
	public void setCodeAnalysisTab(String codeAnalysisTab) {
		this.codeAnalysisTab = codeAnalysisTab;
	}

	/**
	 * @return the codeValidateAgainstDropdown
	 */
	public String getCodeValidateAgainstDropdown() {
		return codeValidateAgainstDropdown;
	}

	/**
	 * @param codeValidateAgainstDropdown
	 *            the codeValidateAgainstDropdown to set
	 */
	public void setCodeValidateAgainstDropdown(
			String codeValidateAgainstDropdown) {
		this.codeValidateAgainstDropdown = codeValidateAgainstDropdown;
	}

	/**
	 * @return the codeValidateCs
	 */
	public String getCodeValidateCs() {
		return codeValidateCs;
	}

	/**
	 * @param codeValidateCs
	 *            the codeValidateCs to set
	 */
	public void setCodeValidateCs(String codeValidateCs) {
		this.codeValidateCs = codeValidateCs;
	}

	/**
	 * @return the codeValidateJs
	 */
	public String getCodeValidateJs() {
		return codeValidateJs;
	}

	/**
	 * @param codeValidateJs
	 *            the codeValidateJs to set
	 */
	public void setCodeValidateJs(String codeValidateJs) {
		this.codeValidateJs = codeValidateJs;
	}

	/**
	 * @return the codeValidateJava
	 */
	public String getCodeValidateJava() {
		return codeValidateJava;
	}

	/**
	 * @param codeValidateJava
	 *            the codeValidateJava to set
	 */
	public void setCodeValidateJava(String codeValidateJava) {
		this.codeValidateJava = codeValidateJava;
	}

	/**
	 * @return the codeValidateHtml
	 */
	public String getCodeValidateHtml() {
		return codeValidateHtml;
	}

	/**
	 * @param codeValidateHtml
	 *            the codeValidateHtml to set
	 */
	public void setCodeValidateHtml(String codeValidateHtml) {
		this.codeValidateHtml = codeValidateHtml;
	}

	/**
	 * @return the codeValidateJsp
	 */
	public String getCodeValidateJsp() {
		return codeValidateJsp;
	}

	/**
	 * @param codeValidateJsp
	 *            the codeValidateJsp to set
	 */
	public void setCodeValidateJsp(String codeValidateJsp) {
		this.codeValidateJsp = codeValidateJsp;
	}

	/**
	 * @return the codeValidateSource
	 */
	public String getCodeValidateSource() {
		return codeValidateSource;
	}

	/**
	 * @param codeValidateSource
	 *            the codeValidateSource to set
	 */
	public void setCodeValidateSource(String codeValidateSource) {
		this.codeValidateSource = codeValidateSource;
	}

	/**
	 * @return the codeTechnologyDropdown
	 */
	public String getCodeTechnologyDropdown() {
		return codeTechnologyDropdown;
	}

	/**
	 * @param codeTechnologyDropdown
	 *            the codeTechnologyDropdown to set
	 */
	public void setCodeTechnologyDropdown(String codeTechnologyDropdown) {
		this.codeTechnologyDropdown = codeTechnologyDropdown;
	}

	/**
	 * @return the codeValidateFunctionalTest
	 */
	public String getCodeValidateFunctionalTest() {
		return codeValidateFunctionalTest;
	}

	/**
	 * @param codeValidateFunctionalTest
	 *            the codeValidateFunctionalTest to set
	 */
	public void setCodeValidateFunctionalTest(String codeValidateFunctionalTest) {
		this.codeValidateFunctionalTest = codeValidateFunctionalTest;
	}

	/**
	 * @return the codeValidateBtn
	 */
	public String getCodeValidateBtn() {
		return codeValidateBtn;
	}

	/**
	 * @param codeValidateBtn
	 *            the codeValidateBtn to set
	 */
	public void setCodeValidateBtn(String codeValidateBtn) {
		this.codeValidateBtn = codeValidateBtn;
	}

	/**
	 * @return the codeCloseBtn
	 */
	public String getCodeCloseBtn() {
		return codeCloseBtn;
	}

	/**
	 * @param codeCloseBtn
	 *            the codeCloseBtn to set
	 */
	public void setCodeCloseBtn(String codeCloseBtn) {
		this.codeCloseBtn = codeCloseBtn;
	}

	/**
	 * @return the validateAgainstSrc
	 */
	public String getValidateAgainstSrc() {
		return validateAgainstSrc;
	}

	/**
	 * @param validateAgainstSrc
	 *            the validateAgainstSrc to set
	 */
	public void setValidateAgainstSrc(String validateAgainstSrc) {
		this.validateAgainstSrc = validateAgainstSrc;
	}

	/**
	 * @return the skipUnitTestCheckBox
	 */
	public String getSkipUnitTestCheckBox() {
		return skipUnitTestCheckBox;
	}

	/**
	 * @param skipUnitTestCheckBox
	 *            the skipUnitTestCheckBox to set
	 */
	public void setSkipUnitTestCheckBox(String skipUnitTestCheckBox) {
		this.skipUnitTestCheckBox = skipUnitTestCheckBox;
	}

	/**
	 * @return the buildTab
	 */
	public String getBuildTab() {
		return buildTab;
	}

	/**
	 * @param buildTab
	 *            the buildTab to set
	 */
	public void setBuildTab(String buildTab) {
		this.buildTab = buildTab;
	}

	/**
	 * @return the buildName
	 */
	public String getBuildName() {
		return buildName;
	}

	/**
	 * @param buildName
	 *            the buildName to set
	 */
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	/**
	 * @return the chooseEnvironment
	 */
	public String getChooseEnvironment() {
		return chooseEnvironment;
	}

	/**
	 * @param chooseEnvironment
	 *            the chooseEnvironment to set
	 */
	public void setChooseEnvironment(String chooseEnvironment) {
		this.chooseEnvironment = chooseEnvironment;
	}

	/**
	 * @return the chooseLogs
	 */
	public String getChooseLogs() {
		return chooseLogs;
	}

	/**
	 * @param chooseLogs
	 *            the chooseLogs to set
	 */
	public void setChooseLogs(String chooseLogs) {
		this.chooseLogs = chooseLogs;
	}

	/**
	 * @return the buildRun
	 */
	public String getBuildRun() {
		return buildRun;
	}

	/**
	 * @param buildRun
	 *            the buildRun to set
	 */
	public void setBuildRun(String buildRun) {
		this.buildRun = buildRun;
	}

	/**
	 * @param loginUserName
	 *            the loginUserName to set
	 */
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	/**
	 * @return the loginUserName
	 */
	public String getLoginUserName() {
		return loginUserName;
	}

	/**
	 * @param loginPassword
	 *            the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * @param loginButton
	 *            the loginButton to set
	 */
	public void setLoginButton(String loginButton) {
		this.loginButton = loginButton;
	}

	/**
	 * @return the loginButton
	 */
	public String getLoginButton() {
		return loginButton;
	}

	/**
	 * @param projectButton
	 *            the projectButton to set
	 */
	public void setProjectButton(String projectButton) {
		this.projectButton = projectButton;
	}

	/**
	 * @return the projectButton
	 */
	public String getProjectButton() {
		return projectButton;
	}

	/**
	 * @param addProjectButton
	 *            the addProjectButton to set
	 */
	public void setAddProjectButton(String addProjectButton) {
		this.addProjectButton = addProjectButton;
	}

	/**
	 * @return the addProjectButton
	 */
	public String getAddProjectButton() {
		return addProjectButton;
	}

	/**
	 * @param customerDropdown
	 *            the customerDropdown to set
	 */
	public void setCustomerDropdown(String customerDropdown) {
		this.customerDropdown = customerDropdown;
	}

	/**
	 * @return the customerDropdown
	 */
	public String getCustomerDropdown() {
		return customerDropdown;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectCode
	 *            the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectVersion
	 *            the projectVersion to set
	 */
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	/**
	 * @return the projectVersion
	 */
	public String getProjectVersion() {
		return projectVersion;
	}

	/**
	 * @param projectDescription
	 *            the projectDescription to set
	 */
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	/**
	 * @return the projectDescription
	 */
	public String getProjectDescription() {
		return projectDescription;
	}

	/**
	 * @param projectBuildMyself
	 *            the projectBuildMyself to set
	 */
	public void setProjectBuildMyself(String projectBuildMyself) {
		this.projectBuildMyself = projectBuildMyself;
	}

	/**
	 * @return the projectBuildMyself
	 */
	public String getProjectBuildMyself() {
		return projectBuildMyself;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param appLayerAppCode
	 *            the appLayerAppCode to set
	 */
	public void setAppLayerAppCode(String appLayerAppCode) {
		this.appLayerAppCode = appLayerAppCode;
	}

	/**
	 * @return the appLayerAppCode
	 */
	public String getAppLayerAppCode() {
		return appLayerAppCode;
	}

	/**
	 * @param appLayerTechnology
	 *            the appLayerTechnology to set
	 */
	public void setAppLayerTechnology(String appLayerTechnology) {
		this.appLayerTechnology = appLayerTechnology;
	}

	/**
	 * @return the appLayerTechnology
	 */
	public String getAppLayerTechnology() {
		return appLayerTechnology;
	}

	/**
	 * @param appLayerTechnologyVersion
	 *            the appLayerTechnologyVersion to set
	 */
	public void setAppLayerTechnologyVersion(String appLayerTechnologyVersion) {
		this.appLayerTechnologyVersion = appLayerTechnologyVersion;
	}

	/**
	 * @return the appLayerTechnologyVersion
	 */
	public String getAppLayerTechnologyVersion() {
		return appLayerTechnologyVersion;
	}

	/**
	 * @param removeAppLayer
	 *            the removeAppLayer to set
	 */
	public void setRemoveAppLayer(String removeAppLayer) {
		this.removeAppLayer = removeAppLayer;
	}

	/**
	 * @return the removeAppLayer
	 */
	public String getRemoveAppLayer() {
		return removeAppLayer;
	}

	/**
	 * @param webLayerAppCode
	 *            the webLayerAppCode to set
	 */
	public void setWebLayerAppCode(String webLayerAppCode) {
		this.webLayerAppCode = webLayerAppCode;
	}

	/**
	 * @return the webLayerAppCode
	 */
	public String getWebLayerAppCode() {
		return webLayerAppCode;
	}

	/**
	 * @param webLayer
	 *            the webLayer to set
	 */
	public void setWebLayer(String webLayer) {
		this.webLayer = webLayer;
	}

	/**
	 * @return the webLayer
	 */
	public String getWebLayer() {
		return webLayer;
	}

	/**
	 * @param webLayerWidget
	 *            the webLayerWidget to set
	 */
	public void setWebLayerWidget(String webLayerWidget) {
		this.webLayerWidget = webLayerWidget;
	}

	/**
	 * @return the webLayerWidget
	 */
	public String getWebLayerWidget() {
		return webLayerWidget;
	}

	/**
	 * @param webLayerWidgetVersion
	 *            the webLayerWidgetVersion to set
	 */
	public void setWebLayerWidgetVersion(String webLayerWidgetVersion) {
		this.webLayerWidgetVersion = webLayerWidgetVersion;
	}

	/**
	 * @return the webLayerWidgetVersion
	 */
	public String getWebLayerWidgetVersion() {
		return webLayerWidgetVersion;
	}

	/**
	 * @param removeWebLayer
	 *            the removeWebLayer to set
	 */
	public void setRemoveWebLayer(String removeWebLayer) {
		this.removeWebLayer = removeWebLayer;
	}

	/**
	 * @return the removeWebLayer
	 */
	public String getRemoveWebLayer() {
		return removeWebLayer;
	}

	/**
	 * @param mobLayerAppCode
	 *            the mobLayerAppCode to set
	 */
	public void setMobLayerAppCode(String mobLayerAppCode) {
		this.mobLayerAppCode = mobLayerAppCode;
	}

	/**
	 * @return the mobLayerAppCode
	 */
	public String getMobLayerAppCode() {
		return mobLayerAppCode;
	}

	/**
	 * @param mobLayer
	 *            the mobLayer to set
	 */
	public void setMobLayer(String mobLayer) {
		this.mobLayer = mobLayer;
	}

	/**
	 * @return the mobLayer
	 */
	public String getMobLayer() {
		return mobLayer;
	}

	/**
	 * @param mobileType
	 *            the mobileType to set
	 */
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	/**
	 * @return the mobileType
	 */
	public String getMobileType() {
		return mobileType;
	}

	/**
	 * @param mobileVersion
	 *            the mobileVersion to set
	 */
	public void setMobileVersion(String mobileVersion) {
		this.mobileVersion = mobileVersion;
	}

	/**
	 * @return the mobileVersion
	 */
	public String getMobileVersion() {
		return mobileVersion;
	}

	/**
	 * @param removeMobileLayer
	 *            the removeMobileLayer to set
	 */
	public void setRemoveMobileLayer(String removeMobileLayer) {
		this.removeMobileLayer = removeMobileLayer;
	}

	/**
	 * @return the removeMobileLayer
	 */
	public String getRemoveMobileLayer() {
		return removeMobileLayer;
	}

	/**
	 * @param projectCreateButton
	 *            the projectCreateButton to set
	 */
	public void setProjectCreateButton(String projectCreateButton) {
		this.projectCreateButton = projectCreateButton;
	}

	/**
	 * @return the projectCreateButton
	 */
	public String getProjectCreateButton() {
		return projectCreateButton;
	}

	/**
	 * @param projectUpdateButton
	 *            the projectUpdateButton to set
	 */
	public void setProjectUpdateButton(String projectUpdateButton) {
		this.projectUpdateButton = projectUpdateButton;
	}

	/**
	 * @return the projectUpdateButton
	 */
	public String getProjectUpdateButton() {
		return projectUpdateButton;
	}

	/**
	 * @param appInfoTab
	 *            the appInfoTab to set
	 */
	public void setAppInfoTab(String appInfoTab) {
		this.appInfoTab = appInfoTab;
	}

	/**
	 * @return the appInfoTab
	 */
	public String getAppInfoTab() {
		return appInfoTab;
	}

	/**
	 * @param appDesc
	 *            the appDesc to set
	 */
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	/**
	 * @return the appDesc
	 */
	public String getAppDesc() {
		return appDesc;
	}

	/**
	 * @param serverDropdown
	 *            the serverDropdown to set
	 */
	public void setServerDropdown(String serverDropdown) {
		this.serverDropdown = serverDropdown;
	}

	/**
	 * @return the serverDropdown
	 */
	public String getServerDropdown() {
		return serverDropdown;
	}

	/**
	 * @param serverVersionDropdown
	 *            the serverVersionDropdown to set
	 */
	public void setServerVersionDropdown(String serverVersionDropdown) {
		this.serverVersionDropdown = serverVersionDropdown;
	}

	/**
	 * @return the serverVersionDropdown
	 */
	public String getServerVersionDropdown() {
		return serverVersionDropdown;
	}

	/**
	 * @param wampServer
	 *            the wampServer to set
	 */
	public void setWampServer(String wampServer) {
		this.wampServer = wampServer;
	}

	/**
	 * @return the wampServer
	 */
	public String getWampServer() {
		return wampServer;
	}

	/**
	 * @param wampServerVersion
	 *            the wampServerVersion to set
	 */
	public void setWampServerVersion(String wampServerVersion) {
		this.wampServerVersion = wampServerVersion;
	}

	/**
	 * @return the wampServerVersion
	 */
	public String getWampServerVersion() {
		return wampServerVersion;
	}

	/**
	 * @param apacheTomcatServer
	 *            the apacheTomcatServer to set
	 */
	public void setApacheTomcatServer(String apacheTomcatServer) {
		this.apacheTomcatServer = apacheTomcatServer;
	}

	/**
	 * @return the apacheTomcatServer
	 */
	public String getApacheTomcatServer() {
		return apacheTomcatServer;
	}

	/**
	 * @param apacheTomcatServerVersion
	 *            the apacheTomcatServerVersion to set
	 */
	public void setApacheTomcatServerVersion(String apacheTomcatServerVersion) {
		this.apacheTomcatServerVersion = apacheTomcatServerVersion;
	}

	/**
	 * @return the apacheTomcatServerVersion
	 */
	public String getApacheTomcatServerVersion() {
		return apacheTomcatServerVersion;
	}

	/**
	 * @param nodeJsServer
	 *            the nodeJsServer to set
	 */
	public void setNodeJsServer(String nodeJsServer) {
		this.nodeJsServer = nodeJsServer;
	}

	/**
	 * @return the nodeJsServer
	 */
	public String getNodeJsServer() {
		return nodeJsServer;
	}

	/**
	 * @param nodeJsServerVersion
	 *            the nodeJsServerVersion to set
	 */
	public void setNodeJsServerVersion(String nodeJsServerVersion) {
		this.nodeJsServerVersion = nodeJsServerVersion;
	}

	/**
	 * @return the nodeJsServerVersion
	 */
	public String getNodeJsServerVersion() {
		return nodeJsServerVersion;
	}

	/**
	 * @param iisServer
	 *            the iisServer to set
	 */
	public void setIisServer(String iisServer) {
		this.iisServer = iisServer;
	}

	/**
	 * @return the iisServer
	 */
	public String getIisServer() {
		return iisServer;
	}

	/**
	 * @param iisServerVersion
	 *            the iisServerVersion to set
	 */
	public void setIisServerVersion(String iisServerVersion) {
		this.iisServerVersion = iisServerVersion;
	}

	/**
	 * @return the iisServerVersion
	 */
	public String getIisServerVersion() {
		return iisServerVersion;
	}

	/**
	 * @param sharepointServer
	 *            the sharepointServer to set
	 */
	public void setSharepointServer(String sharepointServer) {
		this.sharepointServer = sharepointServer;
	}

	/**
	 * @return the sharepointServer
	 */
	public String getSharepointServer() {
		return sharepointServer;
	}

	/**
	 * @param sharepointServerVersion
	 *            the sharepointServerVersion to set
	 */
	public void setSharepointServerVersion(String sharepointServerVersion) {
		this.sharepointServerVersion = sharepointServerVersion;
	}

	/**
	 * @return the sharepointServerVersion
	 */
	public String getSharepointServerVersion() {
		return sharepointServerVersion;
	}

	/**
	 * @param dbDropdown
	 *            the dbDropdown to set
	 */
	public void setDbDropdown(String dbDropdown) {
		this.dbDropdown = dbDropdown;
	}

	/**
	 * @return the dbDropdown
	 */
	public String getDbDropdown() {
		return dbDropdown;
	}

	/**
	 * @param mysqlDb
	 *            the mysqlDb to set
	 */
	public void setMysqlDb(String mysqlDb) {
		this.mysqlDb = mysqlDb;
	}

	/**
	 * @return the mysqlDb
	 */
	public String getMysqlDb() {
		return mysqlDb;
	}

	/**
	 * @param dbVersionDropdown
	 *            the dbVersionDropdown to set
	 */
	public void setDbVersionDropdown(String dbVersionDropdown) {
		this.dbVersionDropdown = dbVersionDropdown;
	}

	/**
	 * @return the dbVersionDropdown
	 */
	public String getDbVersionDropdown() {
		return dbVersionDropdown;
	}

	/**
	 * @param mysqlVersionValue
	 *            the mysqlVersionValue to set
	 */
	public void setMysqlVersionValue(String mysqlVersionValue) {
		this.mysqlVersionValue = mysqlVersionValue;
	}

	/**
	 * @return the mysqlVersionValue
	 */
	public String getMysqlVersionValue() {
		return mysqlVersionValue;
	}

	/**
	 * @param restJsonCheckbox
	 *            the restJsonCheckbox to set
	 */
	public void setRestJsonCheckbox(String restJsonCheckbox) {
		this.restJsonCheckbox = restJsonCheckbox;
	}

	/**
	 * @return the restJsonCheckbox
	 */
	public String getRestJsonCheckbox() {
		return restJsonCheckbox;
	}

	/**
	 * @param restXmlCheckbox
	 *            the restXmlCheckbox to set
	 */
	public void setRestXmlCheckbox(String restXmlCheckbox) {
		this.restXmlCheckbox = restXmlCheckbox;
	}

	/**
	 * @return the restXmlCheckbox
	 */
	public String getRestXmlCheckbox() {
		return restXmlCheckbox;
	}

	/**
	 * @param soap1Checkbox
	 *            the soap1Checkbox to set
	 */
	public void setSoap1Checkbox(String soap1Checkbox) {
		this.soap1Checkbox = soap1Checkbox;
	}

	/**
	 * @return the soap1Checkbox
	 */
	public String getSoap1Checkbox() {
		return soap1Checkbox;
	}

	/**
	 * @param soap2Checkbox
	 *            the soap2Checkbox to set
	 */
	public void setSoap2Checkbox(String soap2Checkbox) {
		this.soap2Checkbox = soap2Checkbox;
	}

	/**
	 * @return the soap2Checkbox
	 */
	public String getSoap2Checkbox() {
		return soap2Checkbox;
	}

	/**
	 * @param jmsCheckbox
	 *            the jmsCheckbox to set
	 */
	public void setJmsCheckbox(String jmsCheckbox) {
		this.jmsCheckbox = jmsCheckbox;
	}

	/**
	 * @return the jmsCheckbox
	 */
	public String getJmsCheckbox() {
		return jmsCheckbox;
	}

	/**
	 * @param functionalFramework
	 *            the functionalFramework to set
	 */
	public void setFunctionalFramework(String functionalFramework) {
		this.functionalFramework = functionalFramework;
	}

	/**
	 * @return the functionalFramework
	 */
	public String getFunctionalFramework() {
		return functionalFramework;
	}

	/**
	 * @param testNGValue
	 *            the testNGValue to set
	 */
	public void setTestNGValue(String testNGValue) {
		this.testNGValue = testNGValue;
	}

	/**
	 * @return the testNGValue
	 */
	public String getTestNGValue() {
		return testNGValue;
	}

	/**
	 * @param cucumberValue
	 *            the cucumberValue to set
	 */
	public void setCucumberValue(String cucumberValue) {
		this.cucumberValue = cucumberValue;
	}

	/**
	 * @return the cucumberValue
	 */
	public String getCucumberValue() {
		return cucumberValue;
	}

	public String getConfigChooseServer() {
		return configChooseServer;
	}

	public String getConfigServer() {
		return configServer;
	}

	public String getConfigServerDesc() {
		return configServerDesc;
	}

	public String getConfigServerProtocol() {
		return configServerProtocol;
	}

	public String getConfigServerHost() {
		return configServerHost;
	}

	public String getConfigServerPort() {
		return configServerPort;
	}

	public String getConfigServerUName() {
		return configServerUName;
	}

	public String getConfigServerCertificate() {
		return configServerCertificate;
	}

	public String getConfigServerDeployDir() {
		return configServerDeployDir;
	}

	public String getConfigServerContext() {
		return configServerContext;
	}

	public String getConfigChooseDb() {
		return configChooseDb;
	}

	public String getConfigDb() {
		return configDb;
	}

	public String getConfigDbDesc() {
		return configDbDesc;
	}

	public String getConfigDbHost() {
		return configDbHost;
	}

	public String getConfigDbPort() {
		return configDbPort;
	}

	public String getConfigDbUsername() {
		return configDbUsername;
	}

	public String getConfigDbPwd() {
		return configDbPwd;
	}

	public String getConfigDbName() {
		return configDbName;
	}

	public String getConfigUpdateButton() {
		return configUpdateButton;
	}

	public String getCloneEnv() {
		return cloneEnv;
	}

	public String getCloneEnvName() {
		return cloneEnvName;
	}

	public String getCloneButton() {
		return cloneButton;
	}

	public String getBuildNumber() {
		return buildNumber;
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

	public void setConfigTab(String configTab) {
		this.configTab = configTab;
	}

	public void setConfigButton(String configButton) {
		this.configButton = configButton;
	}

	public void setAddConfig(String addConfig) {
		this.addConfig = addConfig;
	}

	public void setConfigChooseServer(String configChooseServer) {
		this.configChooseServer = configChooseServer;
	}

	public void setConfigServer(String configServer) {
		this.configServer = configServer;
	}

	public void setConfigServerDesc(String configServerDesc) {
		this.configServerDesc = configServerDesc;
	}

	public void setConfigServerProtocol(String configServerProtocol) {
		this.configServerProtocol = configServerProtocol;
	}

	public void setConfigServerHost(String configServerHost) {
		this.configServerHost = configServerHost;
	}

	public void setConfigServerPort(String configServerPort) {
		this.configServerPort = configServerPort;
	}

	public void setConfigServerUName(String configServerUName) {
		this.configServerUName = configServerUName;
	}

	public String getConfigServerPwd() {
		return configServerPwd;
	}

	public void setConfigServerPwd(String configServerPwd) {
		this.configServerPwd = configServerPwd;
	}

	public void setConfigServerCertificate(String configServerCertificate) {
		this.configServerCertificate = configServerCertificate;
	}

	public void setConfigServerDeployDir(String configServerDeployDir) {
		this.configServerDeployDir = configServerDeployDir;
	}

	public void setConfigServerContext(String configServerContext) {
		this.configServerContext = configServerContext;
	}

	public void setConfigChooseDb(String configChooseDb) {
		this.configChooseDb = configChooseDb;
	}

	public void setConfigDb(String configDb) {
		this.configDb = configDb;
	}

	public void setConfigDbDesc(String configDbDesc) {
		this.configDbDesc = configDbDesc;
	}

	public void setConfigDbHost(String configDbHost) {
		this.configDbHost = configDbHost;
	}

	public void setConfigDbPort(String configDbPort) {
		this.configDbPort = configDbPort;
	}

	public void setConfigDbUsername(String configDbUsername) {
		this.configDbUsername = configDbUsername;
	}

	public void setConfigDbPwd(String configDbPwd) {
		this.configDbPwd = configDbPwd;
	}

	public void setConfigDbName(String configDbName) {
		this.configDbName = configDbName;
	}

	public void setConfigUpdateButton(String configUpdateButton) {
		this.configUpdateButton = configUpdateButton;
	}

	public void setCloneEnv(String cloneEnv) {
		this.cloneEnv = cloneEnv;
	}

	public void setCloneEnvName(String cloneEnvName) {
		this.cloneEnvName = cloneEnvName;
	}

	public void setCloneButton(String cloneButton) {
		this.cloneButton = cloneButton;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getBuildGenerate() {
		return buildGenerate;
	}

	public String getDeployButton() {
		return deployButton;
	}

	public void setBuildGenerate(String buildGenerate) {
		this.buildGenerate = buildGenerate;
	}

	public String getDeployBuild() {
		return deployBuild;
	}

	public void setDeployBuild(String deployBuild) {
		this.deployBuild = deployBuild;
	}

	public void setDeployButton(String deployButton) {
		this.deployButton = deployButton;
	}

	public String getQualityAssuranceButton() {
		return qualityAssuranceButton;
	}

	public String getUnitButton() {
		return unitButton;
	}

	public String getUnitTestButton() {
		return unitTestButton;
	}

	public void setQualityAssuranceButton(String qualityAssuranceButton) {
		this.qualityAssuranceButton = qualityAssuranceButton;
	}

	public void setUnitButton(String unitButton) {
		this.unitButton = unitButton;
	}

	public void setUnitTestButton(String unitTestButton) {
		this.unitTestButton = unitTestButton;
	}

	public String getUnitTestAgainst() {
		return unitTestAgainst;
	}

	public void setUnitTestAgainst(String unitTestAgainst) {
		this.unitTestAgainst = unitTestAgainst;
	}

	public String getUnitTestRun() {
		return unitTestRun;
	}

	public void setUnitTestRun(String unitTestRun) {
		this.unitTestRun = unitTestRun;
	}

	public String getRunAgainstSourceButton() {
		return runAgainstSourceButton;
	}

	public void setRunAgainstSourceButton(String runAgainstSourceButton) {
		this.runAgainstSourceButton = runAgainstSourceButton;
	}

	public String getExecuteSqlCheckbox() {
		return executeSqlCheckbox;
	}

	public void setExecuteSqlCheckbox(String executeSqlCheckbox) {
		this.executeSqlCheckbox = executeSqlCheckbox;
	}

	public String getRunAgainstRunButton() {
		return runAgainstRunButton;
	}

	public void setRunAgainstRunButton(String runAgainstRunButton) {
		this.runAgainstRunButton = runAgainstRunButton;
	}

	/**
	 * @return the pdfReportTypeDropDown
	 */
	public String getPdfReportTypeDropDown() {
		return pdfReportTypeDropDown;
	}

	/**
	 * @param pdfReportTypeDropDown
	 *            the pdfReportTypeDropDown to set
	 */
	public void setPdfReportTypeDropDown(String pdfReportTypeDropDown) {
		this.pdfReportTypeDropDown = pdfReportTypeDropDown;
	}

	/**
	 * @return the pdfName
	 */
	public String getPdfName() {
		return pdfName;
	}

	/**
	 * @param pdfName
	 *            the pdfName to set
	 */
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	/**
	 * @return the pdfGenerateBtn
	 */
	public String getPdfGenerateBtn() {
		return pdfGenerateBtn;
	}

	/**
	 * @param pdfGenerateBtn
	 *            the pdfGenerateBtn to set
	 */
	public void setPdfGenerateBtn(String pdfGenerateBtn) {
		this.pdfGenerateBtn = pdfGenerateBtn;
	}

	/**
	 * @return the pdfCloseBtn
	 */
	public String getPdfCloseBtn() {
		return pdfCloseBtn;
	}

	/**
	 * @param pdfCloseBtn
	 *            the pdfCloseBtn to set
	 */
	public void setPdfCloseBtn(String pdfCloseBtn) {
		this.pdfCloseBtn = pdfCloseBtn;
	}

	/**
	 * @return the pdfOverAllReport
	 */
	public String getPdfOverAllReport() {
		return pdfOverAllReport;
	}

	/**
	 * @param pdfOverAllReport
	 *            the pdfOverAllReport to set
	 */
	public void setPdfOverAllReport(String pdfOverAllReport) {
		this.pdfOverAllReport = pdfOverAllReport;
	}

	/**
	 * @return the pdfDetailReport
	 */
	public String getPdfDetailReport() {
		return pdfDetailReport;
	}

	/**
	 * @param pdfDetailReport
	 *            the pdfDetailReport to set
	 */
	public void setPdfDetailReport(String pdfDetailReport) {
		this.pdfDetailReport = pdfDetailReport;
	}

	/**
	 * @return the appTechnologyJ2EE
	 */
	public String getAppTechnologyJ2EE() {
		return appTechnologyJ2EE;
	}

	/**
	 * @param appTechnologyJ2EE
	 *            the appTechnologyJ2EE to set
	 */
	public void setAppTechnologyJ2EE(String appTechnologyJ2EE) {
		this.appTechnologyJ2EE = appTechnologyJ2EE;
	}

	/**
	 * @return the appTechnologyNodeJS
	 */
	public String getAppTechnologyNodeJS() {
		return appTechnologyNodeJS;
	}

	/**
	 * @param appTechnologyNodeJS
	 *            the appTechnologyNodeJS to set
	 */
	public void setAppTechnologyNodeJS(String appTechnologyNodeJS) {
		this.appTechnologyNodeJS = appTechnologyNodeJS;
	}

	/**
	 * @return the appTechnologyPHP
	 */
	public String getAppTechnologyPHP() {
		return appTechnologyPHP;
	}

	/**
	 * @param appTechnologyPHP
	 *            the appTechnologyPHP to set
	 */
	public void setAppTechnologyPHP(String appTechnologyPHP) {
		this.appTechnologyPHP = appTechnologyPHP;
	}

	/**
	 * @return the appTechnologyDrupal6
	 */
	public String getAppTechnologyDrupal6() {
		return appTechnologyDrupal6;
	}

	/**
	 * @param appTechnologyDrupal6
	 *            the appTechnologyDrupal6 to set
	 */
	public void setAppTechnologyDrupal6(String appTechnologyDrupal6) {
		this.appTechnologyDrupal6 = appTechnologyDrupal6;
	}

	/**
	 * @return the appTechnologyDrupal7
	 */
	public String getAppTechnologyDrupal7() {
		return appTechnologyDrupal7;
	}

	/**
	 * @param appTechnologyDrupal7
	 *            the appTechnologyDrupal7 to set
	 */
	public void setAppTechnologyDrupal7(String appTechnologyDrupal7) {
		this.appTechnologyDrupal7 = appTechnologyDrupal7;
	}

	/**
	 * @return the appTechnologyWordPress
	 */
	public String getAppTechnologyWordPress() {
		return appTechnologyWordPress;
	}

	/**
	 * @param appTechnologyWordPress
	 *            the appTechnologyWordPress to set
	 */
	public void setAppTechnologyWordPress(String appTechnologyWordPress) {
		this.appTechnologyWordPress = appTechnologyWordPress;
	}

	/**
	 * @return the appTechnologyAspDotNet
	 */
	public String getAppTechnologyAspDotNet() {
		return appTechnologyAspDotNet;
	}

	/**
	 * @param appTechnologyAspDotNet
	 *            the appTechnologyAspDotNet to set
	 */
	public void setAppTechnologyAspDotNet(String appTechnologyAspDotNet) {
		this.appTechnologyAspDotNet = appTechnologyAspDotNet;
	}

	/**
	 * @return the appTechnologySharePoint
	 */
	public String getAppTechnologySharePoint() {
		return appTechnologySharePoint;
	}

	/**
	 * @param appTechnologySharePoint
	 *            the appTechnologySharePoint to set
	 */
	public void setAppTechnologySharePoint(String appTechnologySharePoint) {
		this.appTechnologySharePoint = appTechnologySharePoint;
	}

	/**
	 * @return the appTechnologySiteCore
	 */
	public String getAppTechnologySiteCore() {
		return appTechnologySiteCore;
	}

	/**
	 * @param appTechnologySiteCore
	 *            the appTechnologySiteCore to set
	 */
	public void setAppTechnologySiteCore(String appTechnologySiteCore) {
		this.appTechnologySiteCore = appTechnologySiteCore;
	}

	/**
	 * @return the appTechnologyJSA
	 */
	public String getAppTechnologyJSA() {
		return appTechnologyJSA;
	}

	/**
	 * @param appTechnologyJSA
	 *            the appTechnologyJSA to set
	 */
	public void setAppTechnologyJSA(String appTechnologyJSA) {
		this.appTechnologyJSA = appTechnologyJSA;
	}

	/**
	 * @return the webTechnologyHTML5
	 */
	public String getWebTechnologyHTML5() {
		return webTechnologyHTML5;
	}

	/**
	 * @param webTechnologyHTML5
	 *            the webTechnologyHTML5 to set
	 */
	public void setWebTechnologyHTML5(String webTechnologyHTML5) {
		this.webTechnologyHTML5 = webTechnologyHTML5;
	}

	/**
	 * @return the webTechnologyJMW
	 */
	public String getWebTechnologyJMW() {
		return webTechnologyJMW;
	}

	/**
	 * @param webTechnologyJMW
	 *            the webTechnologyJMW to set
	 */
	public void setWebTechnologyJMW(String webTechnologyJMW) {
		this.webTechnologyJMW = webTechnologyJMW;
	}

	/**
	 * @return the webTechnologyYMW
	 */
	public String getWebTechnologyYMW() {
		return webTechnologyYMW;
	}

	/**
	 * @param webTechnologyYMW
	 *            the webTechnologyYMW to set
	 */
	public void setWebTechnologyYMW(String webTechnologyYMW) {
		this.webTechnologyYMW = webTechnologyYMW;
	}

	/**
	 * @return the webTechnologyMJW
	 */
	public String getWebTechnologyMJW() {
		return webTechnologyMJW;
	}

	/**
	 * @param webTechnologyMJW
	 *            the webTechnologyMJW to set
	 */
	public void setWebTechnologyMJW(String webTechnologyMJW) {
		this.webTechnologyMJW = webTechnologyMJW;
	}

	/**
	 * @return the webTechnologyMYW
	 */
	public String getWebTechnologyMYW() {
		return webTechnologyMYW;
	}

	/**
	 * @param webTechnologyMYW
	 *            the webTechnologyMYW to set
	 */
	public void setWebTechnologyMYW(String webTechnologyMYW) {
		this.webTechnologyMYW = webTechnologyMYW;
	}

	/**
	 * @return the mobTechnologyWindows
	 */
	public String getMobTechnologyWindows() {
		return mobTechnologyWindows;
	}

	/**
	 * @param mobTechnologyWindows
	 *            the mobTechnologyWindows to set
	 */
	public void setMobTechnologyWindows(String mobTechnologyWindows) {
		this.mobTechnologyWindows = mobTechnologyWindows;
	}

	/**
	 * @return the mobTechnologyWindowsMetro
	 */
	public String getMobTechnologyWindowsMetro() {
		return mobTechnologyWindowsMetro;
	}

	/**
	 * @param mobTechnologyWindowsMetro
	 *            the mobTechnologyWindowsMetro to set
	 */
	public void setMobTechnologyWindowsMetro(String mobTechnologyWindowsMetro) {
		this.mobTechnologyWindowsMetro = mobTechnologyWindowsMetro;
	}

	/**
	 * @return the mobTechnologyWindowsPhone
	 */
	public String getMobTechnologyWindowsPhone() {
		return mobTechnologyWindowsPhone;
	}

	/**
	 * @param mobTechnologyWindowsPhone
	 *            the mobTechnologyWindowsPhone to set
	 */
	public void setMobTechnologyWindowsPhone(String mobTechnologyWindowsPhone) {
		this.mobTechnologyWindowsPhone = mobTechnologyWindowsPhone;
	}

	/**
	 * @return the mobTechnologyBlackBerry
	 */
	public String getMobTechnologyBlackBerry() {
		return mobTechnologyBlackBerry;
	}

	/**
	 * @param mobTechnologyBlackBerry
	 *            the mobTechnologyBlackBerry to set
	 */
	public void setMobTechnologyBlackBerry(String mobTechnologyBlackBerry) {
		this.mobTechnologyBlackBerry = mobTechnologyBlackBerry;
	}

	/**
	 * @return the mobTechnologyBlackBerryHybrid
	 */
	public String getMobTechnologyBlackBerryHybrid() {
		return mobTechnologyBlackBerryHybrid;
	}

	/**
	 * @param mobTechnologyBlackBerryHybrid
	 *            the mobTechnologyBlackBerryHybrid to set
	 */
	public void setMobTechnologyBlackBerryHybrid(
			String mobTechnologyBlackBerryHybrid) {
		this.mobTechnologyBlackBerryHybrid = mobTechnologyBlackBerryHybrid;
	}

	/**
	 * @return the mobTechnologyAndroid
	 */
	public String getMobTechnologyAndroid() {
		return mobTechnologyAndroid;
	}

	/**
	 * @param mobTechnologyAndroid
	 *            the mobTechnologyAndroid to set
	 */
	public void setMobTechnologyAndroid(String mobTechnologyAndroid) {
		this.mobTechnologyAndroid = mobTechnologyAndroid;
	}

	/**
	 * @return the mobTechnologyAndroidLibrary
	 */
	public String getMobTechnologyAndroidLibrary() {
		return mobTechnologyAndroidLibrary;
	}

	/**
	 * @param mobTechnologyAndroidLibrary
	 *            the mobTechnologyAndroidLibrary to set
	 */
	public void setMobTechnologyAndroidLibrary(
			String mobTechnologyAndroidLibrary) {
		this.mobTechnologyAndroidLibrary = mobTechnologyAndroidLibrary;
	}

	/**
	 * @return the mobTechnologyAndroidNative
	 */
	public String getMobTechnologyAndroidNative() {
		return mobTechnologyAndroidNative;
	}

	/**
	 * @param mobTechnologyAndroidNative
	 *            the mobTechnologyAndroidNative to set
	 */
	public void setMobTechnologyAndroidNative(String mobTechnologyAndroidNative) {
		this.mobTechnologyAndroidNative = mobTechnologyAndroidNative;
	}

	/**
	 * @return the mobTechnologyAndroidHybrid
	 */
	public String getMobTechnologyAndroidHybrid() {
		return mobTechnologyAndroidHybrid;
	}

	/**
	 * @param mobTechnologyAndroidHybrid
	 *            the mobTechnologyAndroidHybrid to set
	 */
	public void setMobTechnologyAndroidHybrid(String mobTechnologyAndroidHybrid) {
		this.mobTechnologyAndroidHybrid = mobTechnologyAndroidHybrid;
	}

	/**
	 * @return the mobTechnologyIphone
	 */
	public String getMobTechnologyIphone() {
		return mobTechnologyIphone;
	}

	/**
	 * @param mobTechnologyIphone
	 *            the mobTechnologyIphone to set
	 */
	public void setMobTechnologyIphone(String mobTechnologyIphone) {
		this.mobTechnologyIphone = mobTechnologyIphone;
	}

	/**
	 * @return the mobTechnologyIphoneWorkspace
	 */
	public String getMobTechnologyIphoneWorkspace() {
		return mobTechnologyIphoneWorkspace;
	}

	/**
	 * @param mobTechnologyIphoneWorkspace
	 *            the mobTechnologyIphoneWorkspace to set
	 */
	public void setMobTechnologyIphoneWorkspace(
			String mobTechnologyIphoneWorkspace) {
		this.mobTechnologyIphoneWorkspace = mobTechnologyIphoneWorkspace;
	}

	/**
	 * @return the mobTechnologyIphoneLibrary
	 */
	public String getMobTechnologyIphoneLibrary() {
		return mobTechnologyIphoneLibrary;
	}

	/**
	 * @param mobTechnologyIphoneLibrary
	 *            the mobTechnologyIphoneLibrary to set
	 */
	public void setMobTechnologyIphoneLibrary(String mobTechnologyIphoneLibrary) {
		this.mobTechnologyIphoneLibrary = mobTechnologyIphoneLibrary;
	}

	/**
	 * @return the mobTechnologyIphoneNative
	 */
	public String getMobTechnologyIphoneNative() {
		return mobTechnologyIphoneNative;
	}

	/**
	 * @param mobTechnologyIphoneNative
	 *            the mobTechnologyIphoneNative to set
	 */
	public void setMobTechnologyIphoneNative(String mobTechnologyIphoneNative) {
		this.mobTechnologyIphoneNative = mobTechnologyIphoneNative;
	}

	/**
	 * @return the mobTechnologyIphoneHybrid
	 */
	public String getMobTechnologyIphoneHybrid() {
		return mobTechnologyIphoneHybrid;
	}

	/**
	 * @param mobTechnologyIphoneHybrid
	 *            the mobTechnologyIphoneHybrid to set
	 */
	public void setMobTechnologyIphoneHybrid(String mobTechnologyIphoneHybrid) {
		this.mobTechnologyIphoneHybrid = mobTechnologyIphoneHybrid;
	}

	/**
	 * @return the componentButton
	 */
	public String getComponentButton() {
		return componentButton;
	}

	/**
	 * @param componentButton
	 *            the componentButton to set
	 */
	public void setComponentButton(String componentButton) {
		this.componentButton = componentButton;
	}

	/**
	 * @return the componentTestButton
	 */
	public String getComponentTestButton() {
		return componentTestButton;
	}

	/**
	 * @param componentTestButton
	 *            the componentTestButton to set
	 */
	public void setComponentTestButton(String componentTestButton) {
		this.componentTestButton = componentTestButton;
	}

	/**
	 * @return the componentKillProcessBtn
	 */
	public String getComponentKillProcessBtn() {
		return componentKillProcessBtn;
	}

	/**
	 * @param componentKillProcessBtn
	 *            the componentKillProcessBtn to set
	 */
	public void setComponentKillProcessBtn(String componentKillProcessBtn) {
		this.componentKillProcessBtn = componentKillProcessBtn;
	}

	/**
	 * @return the functionalButton
	 */
	public String getFunctionalButton() {
		return functionalButton;
	}

	/**
	 * @param functionalButton the functionalButton to set
	 */
	public void setFunctionalButton(String functionalButton) {
		this.functionalButton = functionalButton;
	}

	/**
	 * @return the manualButton
	 */
	public String getManualButton() {
		return manualButton;
	}

	/**
	 * @param manualButton the manualButton to set
	 */
	public void setManualButton(String manualButton) {
		this.manualButton = manualButton;
	}

	/**
	 * @return the performanceButton
	 */
	public String getPerformanceButton() {
		return performanceButton;
	}

	/**
	 * @param performanceButton the performanceButton to set
	 */
	public void setPerformanceButton(String performanceButton) {
		this.performanceButton = performanceButton;
	}

	/**
	 * @return the loadButton
	 */
	public String getLoadButton() {
		return loadButton;
	}

	/**
	 * @param loadButton the loadButton to set
	 */
	public void setLoadButton(String loadButton) {
		this.loadButton = loadButton;
	}

	/**
	 * @return the perfjmxType
	 */
	public String getPerfjmxType() {
		return perfjmxType;
	}

	/**
	 * @param perfjmxType the perfjmxType to set
	 */
	public void setPerfjmxType(String perfjmxType) {
		this.perfjmxType = perfjmxType;
	}

	/**
	 * @return the perftestAgainst
	 */
	public String getPerftestAgainst() {
		return perftestAgainst;
	}

	/**
	 * @param perftestAgainst the perftestAgainst to set
	 */
	public void setPerftestAgainst(String perftestAgainst) {
		this.perftestAgainst = perftestAgainst;
	}

	/**
	 * @return the perfauthManager
	 */
	public String getPerfauthManager() {
		return perfauthManager;
	}

	/**
	 * @param perfauthManager the perfauthManager to set
	 */
	public void setPerfauthManager(String perfauthManager) {
		this.perfauthManager = perfauthManager;
	}

	/**
	 * @return the perfRampupPeriod
	 */
	public String getPerfRampupPeriod() {
		return perfRampupPeriod;
	}

	/**
	 * @param perfRampupPeriod the perfRampupPeriod to set
	 */
	public void setPerfRampupPeriod(String perfRampupPeriod) {
		this.perfRampupPeriod = perfRampupPeriod;
	}

	/**
	 * @return the perfHttpName
	 */
	public String getPerfHttpName() {
		return perfHttpName;
	}

	/**
	 * @param perfHttpName the perfHttpName to set
	 */
	public void setPerfHttpName(String perfHttpName) {
		this.perfHttpName = perfHttpName;
	}

	/**
	 * @return the perfContext
	 */
	public String getPerfContext() {
		return perfContext;
	}

	/**
	 * @param perfContext the perfContext to set
	 */
	public void setPerfContext(String perfContext) {
		this.perfContext = perfContext;
	}

	/**
	 * @return the perfDataType
	 */
	public String getPerfDataType() {
		return perfDataType;
	}

	/**
	 * @param perfDataType the perfDataType to set
	 */
	public void setPerfDataType(String perfDataType) {
		this.perfDataType = perfDataType;
	}

	/**
	 * @return the perfEncodingType
	 */
	public String getPerfEncodingType() {
		return perfEncodingType;
	}

	/**
	 * @param perfEncodingType the perfEncodingType to set
	 */
	public void setPerfEncodingType(String perfEncodingType) {
		this.perfEncodingType = perfEncodingType;
	}

	/**
	 * @return the perfRedirect
	 */
	public String getPerfRedirect() {
		return perfRedirect;
	}

	/**
	 * @param perfRedirect the perfRedirect to set
	 */
	public void setPerfRedirect(String perfRedirect) {
		this.perfRedirect = perfRedirect;
	}

	/**
	 * @return the perfFollowRedirect
	 */
	public String getPerfFollowRedirect() {
		return perfFollowRedirect;
	}

	/**
	 * @param perfFollowRedirect the perfFollowRedirect to set
	 */
	public void setPerfFollowRedirect(String perfFollowRedirect) {
		this.perfFollowRedirect = perfFollowRedirect;
	}

	/**
	 * @return the perfKeepAlive
	 */
	public String getPerfKeepAlive() {
		return perfKeepAlive;
	}

	/**
	 * @param perfKeepAlive the perfKeepAlive to set
	 */
	public void setPerfKeepAlive(String perfKeepAlive) {
		this.perfKeepAlive = perfKeepAlive;
	}

	/**
	 * @return the perHeaderKey
	 */
	public String getPerHeaderKey() {
		return perHeaderKey;
	}

	/**
	 * @param perHeaderKey the perHeaderKey to set
	 */
	public void setPerHeaderKey(String perHeaderKey) {
		this.perHeaderKey = perHeaderKey;
	}

	/**
	 * @return the perfHeaderValue
	 */
	public String getPerfHeaderValue() {
		return perfHeaderValue;
	}

	/**
	 * @param perfHeaderValue the perfHeaderValue to set
	 */
	public void setPerfHeaderValue(String perfHeaderValue) {
		this.perfHeaderValue = perfHeaderValue;
	}

	/**
	 * @return the perfHeaderAddButton
	 */
	public String getPerfHeaderAddButton() {
		return perfHeaderAddButton;
	}

	/**
	 * @param perfHeaderAddButton the perfHeaderAddButton to set
	 */
	public void setPerfHeaderAddButton(String perfHeaderAddButton) {
		this.perfHeaderAddButton = perfHeaderAddButton;
	}

	/**
	 * @return the perParameterKey
	 */
	public String getPerParameterKey() {
		return perParameterKey;
	}

	/**
	 * @param perParameterKey the perParameterKey to set
	 */
	public void setPerParameterKey(String perParameterKey) {
		this.perParameterKey = perParameterKey;
	}

	/**
	 * @return the perfParameterValue
	 */
	public String getPerfParameterValue() {
		return perfParameterValue;
	}

	/**
	 * @param perfParameterValue the perfParameterValue to set
	 */
	public void setPerfParameterValue(String perfParameterValue) {
		this.perfParameterValue = perfParameterValue;
	}

	/**
	 * @return the perfTestButton
	 */
	public String getPerfTestButton() {
		return perfTestButton;
	}

	/**
	 * @param perfTestButton the perfTestButton to set
	 */
	public void setPerfTestButton(String perfTestButton) {
		this.perfTestButton = perfTestButton;
	}

	/**
	 * @return the projectDeleteIcon
	 */
	public String getProjectDeleteIcon() {
		return projectDeleteIcon;
	}

	/**
	 * @param projectDeleteIcon the projectDeleteIcon to set
	 */
	public void setProjectDeleteIcon(String projectDeleteIcon) {
		this.projectDeleteIcon = projectDeleteIcon;
	}

	/**
	 * @return the projectDeletePopUpYesBtn
	 */
	public String getProjectDeletePopUpYesBtn() {
		return projectDeletePopUpYesBtn;
	}

	/**
	 * @param projectDeletePopUpYesBtn the projectDeletePopUpYesBtn to set
	 */
	public void setProjectDeletePopUpYesBtn(String projectDeletePopUpYesBtn) {
		this.projectDeletePopUpYesBtn = projectDeletePopUpYesBtn;
	}
	
	

}
