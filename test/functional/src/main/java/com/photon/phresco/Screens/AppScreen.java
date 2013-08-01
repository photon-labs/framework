package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.testng.Assert;
import com.photon.phresco.selenium.util.MagicNumbers;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AspDotNetConstants;
import com.photon.phresco.uiconstants.Drupal6Constants;
import com.photon.phresco.uiconstants.Drupal7Constants;
import com.photon.phresco.uiconstants.J2EEConstants;
import com.photon.phresco.uiconstants.JavaStandaloneConstants;
import com.photon.phresco.uiconstants.NodeJsConstants;
import com.photon.phresco.uiconstants.PhpConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.SharepointConstants;
import com.photon.phresco.uiconstants.SitecoreConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.WordpressConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class AppScreen extends BaseScreen{
	private Log log = LogFactory.getLog("AppScreen");
	private PhrescoUiConstants phrescoUiConst;
	private UserInfoConstants userInfoConst;
	private PhrescoframeworkData phrescoData;


	public AppScreen(String selectedBrowser, String applicationURL,
			String applicationContext, phresco_env_config phrscEnv,
			PhrescoUiConstants phrescoUiConst, UserInfoConstants userInfoConst,
			PhrescoframeworkData phrescoData) throws ScreenException {

		BaseScreen.phrscEnv = phrscEnv;
		this.phrescoUiConst = phrescoUiConst;
		this.userInfoConst = userInfoConst;
		this.phrescoData = phrescoData;
		instantiateBrowser(selectedBrowser, applicationURL, applicationContext);
	}

	public void loginPage(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@loginPage::****** executing ****");
		try {
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getLoginUserName(), methodName);
			getXpathWebElement(phrescoUiConst.getLoginUserName());
			click();
			sendKeys(userInfoConst.getLoginUserName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getLoginPassword(), methodName);
			getXpathWebElement(phrescoUiConst.getLoginPassword());
			click();
			sendKeys(userInfoConst.getLoginPassword());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getLoginButton(), methodName);
			getXpathWebElement(phrescoUiConst.getLoginButton());
			click();

			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCustomerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCustomerDropdown());
			click();

			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCustomerName(), methodName);
			getXpathWebElement(phrescoUiConst.getCustomerName());
			click();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeCreate(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(phpConst.getPhpArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(phpConst.getPhpArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phpConst.getStartDate(), methodName);
			getXpathWebElement(phpConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(phpConst.getPhpArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);			
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyPHP(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyPHP());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(phpConst.getPhpArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeEditProject(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phpConst.getEditProject(), methodName);
			getXpathWebElement(phpConst.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeEditApp(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phpConst.getPhpArchetypeEditApp(), methodName);
			getXpathWebElement(phpConst.getPhpArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServer(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServerVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlDb(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlVersionValue());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getGridValue(), methodName);
			getXpathWebElement(phrescoUiConst.getGridValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeUpdateFeature(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeEditAppDesc(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(phpConst.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpCodeSource(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpCodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpConfigurationServer(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(phpConst.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(phpConst.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(phpConst.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(phpConst.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(phpConst.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(phpConst.getConfigServerContext());
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void phpConfigurationDatabase(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpConfigurationDatabase executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDb());
			click();
			clear();
			sendKeys(phpConst.getConfigDb());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbDesc());
			click();
			clear();
			sendKeys(phpConst.getConfigDbDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbHost());
			click();
			clear();
			sendKeys(phpConst.getConfigDbHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbPort());
			click();
			clear();
			sendKeys(phpConst.getConfigDbPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbUsername(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbUsername());
			click();
			clear();
			sendKeys(phpConst.getConfigDbUsername());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbName(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbName());
			click();
			clear();
			sendKeys(phpConst.getConfigDbName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void phpBuild(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(phpConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpDeploy(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpDeploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void phpUnitTest(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void phpArchetypeOverAllPdfReport(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phpConst.getPhpArchetypePdfReportIcon(), methodName);
			getXpathWebElement(phpConst.getPhpArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(phpConst.getPhpArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void phpArchetypeDetailedPdfReport(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phpConst.getPhpArchetypePdfReportIcon(), methodName);
			getXpathWebElement(phpConst.getPhpArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(phpConst.getPhpArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}



	public void drupal6ArchetypeCreate(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(drupal6Const.getDrupal6ArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(drupal6Const.getDrupal6ArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal6Const.getStartDate(), methodName);
			getXpathWebElement(drupal6Const.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(drupal6Const.getDrupal6ArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyDrupal6(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyDrupal6());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(drupal6Const.getDrupal6ArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void drupal6ArchetypeEditProject(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(drupal6Const.getEditProject(), methodName);
			getXpathWebElement(drupal6Const.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6ArchetypeEditApp(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal6Const.getDrupal6ArchetypeEditApp(), methodName);
			getXpathWebElement(drupal6Const.getDrupal6ArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServer(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServerVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlDb(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlVersionValue());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getGridValue(), methodName);
			getXpathWebElement(phrescoUiConst.getGridValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6ArchetypeUpdateFeature(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6ArchetypeEditAppDesc(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(drupal6Const.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6CodeSource(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6CodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6ConfigurationServer(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(drupal6Const.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(drupal6Const.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(drupal6Const.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(drupal6Const.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(drupal6Const.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(drupal6Const.getConfigServerContext());
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal6ConfigurationDatabase(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ConfigurationDatabase executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDb());
			click();
			clear();
			sendKeys(drupal6Const.getConfigDb());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbDesc());
			click();
			clear();
			sendKeys(drupal6Const.getConfigDbDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbHost());
			click();
			clear();
			sendKeys(drupal6Const.getConfigDbHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbPort());
			click();
			clear();
			sendKeys(drupal6Const.getConfigDbPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbUsername(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbUsername());
			click();
			clear();
			sendKeys(drupal6Const.getConfigDbUsername());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbName(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbName());
			click();
			clear();
			sendKeys(drupal6Const.getConfigDbName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void drupal6Build(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6Build executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(drupal6Const.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6Deploy(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6Deploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal6UnitTest(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6UnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6ArchetypeOverAllPdfReport(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal6Const.getDrupal6ArchetypePdfReportIcon(), methodName);
			getXpathWebElement(drupal6Const.getDrupal6ArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(drupal6Const.getDrupal6ArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal6ArchetypeDetailedPdfReport(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal6Const.getDrupal6ArchetypePdfReportIcon(), methodName);
			getXpathWebElement(drupal6Const.getDrupal6ArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(drupal6Const.getDrupal6ArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}



	public void drupal7ArchetypeCreate(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(drupal7Const.getDrupal7ArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(drupal7Const.getDrupal7ArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal7Const.getStartDate(), methodName);
			getXpathWebElement(drupal7Const.getStartDate());
			click();


			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(drupal7Const.getDrupal7ArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
	        click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyDrupal7(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyDrupal7() );
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(drupal7Const.getDrupal7ArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void drupal7ArchetypeEditProject(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(drupal7Const.getEditProject(), methodName);
			getXpathWebElement(drupal7Const.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal7ArchetypeEditApp(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal7Const.getDrupal7ArchetypeEditApp(), methodName);
			getXpathWebElement(drupal7Const.getDrupal7ArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServer(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServerVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlDb(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlVersionValue());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getGridValue(), methodName);
			getXpathWebElement(phrescoUiConst.getGridValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal7ArchetypeUpdateFeature(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal7ArchetypeEditAppDesc(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(drupal7Const.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal7CodeSource(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7CodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void drupal7ConfigurationServer(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(drupal7Const.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(drupal7Const.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(drupal7Const.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(drupal7Const.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(drupal7Const.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(drupal7Const.getConfigServerContext());
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal7ConfigurationDatabase(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ConfigurationDatabase executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDb());
			click();
			clear();
			sendKeys(drupal7Const.getConfigDb());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbDesc());
			click();
			clear();
			sendKeys(drupal7Const.getConfigDbDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbHost());
			click();
			clear();
			sendKeys(drupal7Const.getConfigDbHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbPort());
			click();
			clear();
			sendKeys(drupal7Const.getConfigDbPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbUsername(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbUsername());
			click();
			clear();
			sendKeys(drupal7Const.getConfigDbUsername());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbName(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbName());
			click();
			clear();
			sendKeys(drupal7Const.getConfigDbName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void drupal7Build(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7Build executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(drupal7Const.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal7Deploy(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7Deploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal7UnitTest(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7UnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal7ArchetypeOverAllPdfReport(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal7Const.getDrupal7ArchetypePdfReportIcon(), methodName);
			getXpathWebElement(drupal7Const.getDrupal7ArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(drupal7Const.getDrupal7ArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal7ArchetypeDetailedPdfReport(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(drupal7Const.getDrupal7ArchetypePdfReportIcon(), methodName);
			getXpathWebElement(drupal7Const.getDrupal7ArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(drupal7Const.getDrupal7ArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void wordpressArchetypeCreate(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(wordpressConst.getWordpressArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(wordpressConst.getWordpressArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(wordpressConst.getStartDate(), methodName);
			getXpathWebElement(wordpressConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(wordpressConst.getWordpressArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyWordPress(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyWordPress() );
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(wordpressConst.getWordpressArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void wordpressArchetypeEditProject(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(wordpressConst.getEditProject(), methodName);
			getXpathWebElement(wordpressConst.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void wordpressArchetypeEditApp(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(wordpressConst.getWordpressArchetypeEditApp(), methodName);
			getXpathWebElement(wordpressConst.getWordpressArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServer(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getWampServerVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlDb(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getMysqlVersionValue());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getGridValue(), methodName);
			getXpathWebElement(phrescoUiConst.getGridValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();
*/
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void wordpressArchetypeUpdateFeature(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void wordpressArchetypeEditAppDesc(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(wordpressConst.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void wordpressCodeSource(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressCodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void wordpressConfigurationServer(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(wordpressConst.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(wordpressConst.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(wordpressConst.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(wordpressConst.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(wordpressConst.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(wordpressConst.getConfigServerContext());
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void wordpressConfigurationDatabase(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressConfigurationDatabase executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseDb());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDb(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDb());
			click();
			clear();
			sendKeys(wordpressConst.getConfigDb());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbDesc());
			click();
			clear();
			sendKeys(wordpressConst.getConfigDbDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbHost());
			click();
			clear();
			sendKeys(wordpressConst.getConfigDbHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbPort());
			click();
			clear();
			sendKeys(wordpressConst.getConfigDbPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbUsername(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbUsername());
			click();
			clear();
			sendKeys(wordpressConst.getConfigDbUsername());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigDbName(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigDbName());
			click();
			clear();
			sendKeys(wordpressConst.getConfigDbName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void wordpressBuild(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(wordpressConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void wordpressDeploy(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressDeploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void wordpressUnitTest(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void wordpressArchetypeOverAllPdfReport(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(wordpressConst.getWordpressArchetypePdfReportIcon(), methodName);
			getXpathWebElement(wordpressConst.getWordpressArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(wordpressConst.getWordpressArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void wordpressArchetypeDetailedPdfReport(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(wordpressConst.getWordpressArchetypePdfReportIcon(), methodName);
			getXpathWebElement(wordpressConst.getWordpressArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(wordpressConst.getWordpressArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EEArchetypeCreate(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(j2EEConst.getJ2EEArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(j2EEConst.getJ2EEArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(j2EEConst.getStartDate(), methodName);
			getXpathWebElement(j2EEConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(j2EEConst.getJ2EEArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyJ2EE(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyJ2EE());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(j2EEConst.getJ2EEArchetypeAppCode(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EEArchetypeEditProject(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(j2EEConst.getEditProject(), methodName);
			getXpathWebElement(j2EEConst.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EEArchetypeEditApp(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(j2EEConst.getJ2EEArchetypeEditApp(), methodName);
			getXpathWebElement(j2EEConst.getJ2EEArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getApacheTomcatServer(), methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getGridValue(), methodName);
			getXpathWebElement(phrescoUiConst.getGridValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EEArchetypeUpdateFeature(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EEArchetypeEditAppDesc(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(j2EEConst.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EECodeSourceJs(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EECodeSourceJs executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJs(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJs());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void j2EECodeSourceJava(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EECodeSourceJava executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJava(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJava());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EECodeSourceHtml(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EECodeSourceHtml executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateHtml(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateHtml());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void j2EECodeSourceJsp(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EECodeSourceJsp executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJsp(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJsp());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EEConfigurationServer(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(j2EEConst.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(j2EEConst.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(j2EEConst.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(j2EEConst.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(j2EEConst.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(j2EEConst.getConfigServerContext());
			
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void j2EEBuild(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(j2EEConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void j2EEDeploy(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEDeploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void j2EEUnitTestAgainstJava(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEUnitTestAgainstJava executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestAgainst(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestAgainst());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJava(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJava());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestRun(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void j2EEUnitTestAgainstJs(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEUnitTestAgainstJs executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestAgainst(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestAgainst());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJs(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJs());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestRun(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void j2EEArchetypeOverAllPdfReport(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(j2EEConst.getJ2EEArchetypePdfReportIcon(), methodName);
			getXpathWebElement(j2EEConst.getJ2EEArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(j2EEConst.getJ2EEArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void j2EEArchetypeDetailedPdfReport(String methodName,J2EEConstants j2EEConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@j2EEArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(j2EEConst.getJ2EEArchetypePdfReportIcon(), methodName);
			getXpathWebElement(j2EEConst.getJ2EEArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(j2EEConst.getJ2EEArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void nodeJsArchetypeCreate(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(nodeJsConst.getNodeJsArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(nodeJsConst.getNodeJsArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(nodeJsConst.getStartDate(), methodName);
			getXpathWebElement(nodeJsConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(nodeJsConst.getNodeJsArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyNodeJS(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyNodeJS());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(nodeJsConst.getNodeJsArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void nodeJsArchetypeEditProject(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(nodeJsConst.getEditProject(), methodName);
			getXpathWebElement(nodeJsConst.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void nodeJsArchetypeEditApp(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(nodeJsConst.getNodeJsArchetypeEditApp(), methodName);
			getXpathWebElement(nodeJsConst.getNodeJsArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getNodeJsServer(), methodName);
			getXpathWebElement(phrescoUiConst.getNodeJsServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getNodeJsServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getNodeJsServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getGridValue(), methodName);
			getXpathWebElement(phrescoUiConst.getGridValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void nodeJsArchetypeUpdateFeature(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void nodeJsArchetypeEditAppDesc(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(nodeJsConst.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void nodeJsCodeSource(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsCodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	

	public void nodeJsConfigurationServer(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigServerContext());
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	
	public void nodeJsUnitTest(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	
	public void runAgainstSource(String methodName , NodeJsConstants nodeJsConst)throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@runAgainstSource executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRunAgainstSourceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getRunAgainstSourceButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getExecuteSqlCheckbox(), methodName);
			getXpathWebElement(phrescoUiConst.getExecuteSqlCheckbox());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRunAgainstRunButton(), methodName);
			getXpathWebElement(phrescoUiConst.getRunAgainstRunButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void nodeJsArchetypeOverAllPdfReport(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(nodeJsConst.getNodeJsArchetypePdfReportIcon(), methodName);
			getXpathWebElement(nodeJsConst.getNodeJsArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(nodeJsConst.getNodeJsArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void nodeJsArchetypeDetailedPdfReport(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(nodeJsConst.getNodeJsArchetypePdfReportIcon(), methodName);
			getXpathWebElement(nodeJsConst.getNodeJsArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(nodeJsConst.getNodeJsArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	

	public void javaStandaloneArchetypeCreate(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(javaStandalone.getJavaSAArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(javaStandalone.getJavaSAArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(javaStandalone.getStartDate(), methodName);
			getXpathWebElement(javaStandalone.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(javaStandalone.getJavaSAArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyJSA(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyJSA());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(javaStandalone.getJavaSAArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public void javaStandaloneArchetypeEditProject(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(javaStandalone.getEditProject(), methodName);
			getXpathWebElement(javaStandalone.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void javaStandaloneArchetypeEditApp(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(javaStandalone.getJavaSAArchetypeEditApp(), methodName);
			getXpathWebElement(javaStandalone.getJavaSAArchetypeEditApp());
			click();

			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void javaStandaloneCodeSource(String methodName,JavaStandaloneConstants standaloneConstants) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneCodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void javaStandaloneArchetypeUpdateFeature(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void javaStandaloneArchetypeEditAppDesc(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(javaStandalone.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void javaStandaloneBuild(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(javaStandalone.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	
	public void javaStandaloneArchetypeOverAllPdfReport(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(javaStandalone.getJavaSAArchetypePdfReportIcon(), methodName);
			getXpathWebElement(javaStandalone.getJavaSAArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(javaStandalone.getJavaSAArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void javaStandaloneArchetypeDetailedPdfReport(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(javaStandalone.getJavaSAArchetypePdfReportIcon(), methodName);
			getXpathWebElement(javaStandalone.getJavaSAArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(javaStandalone.getJavaSAArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}



	public void sitecoreArchetypeCreate(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(sitecoreConst.getSitecoreArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(sitecoreConst.getSitecoreArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sitecoreConst.getStartDate(), methodName);
			getXpathWebElement(sitecoreConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(sitecoreConst.getSitecoreArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologySiteCore(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologySiteCore());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(sitecoreConst.getSitecoreArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sitecoreArchetypeEditProject(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(sitecoreConst.getEditProject(), methodName);
			getXpathWebElement(sitecoreConst.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sitecoreArchetypeEditApp(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sitecoreConst.getSitecoreArchetypeEditApp(), methodName);
			getXpathWebElement(sitecoreConst.getSitecoreArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getIisServer(), methodName);
			getXpathWebElement(phrescoUiConst.getIisServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getIisServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getIisServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getjUnitValue(), methodName);
			getXpathWebElement(phrescoUiConst.getjUnitValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(phrescoUiConst.getWebdriverValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sitecoreArchetypeUpdateFeature(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sitecoreArchetypeEditAppDesc(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(sitecoreConst.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sitecoreCodeSource(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreCodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void sitecoreConfigurationServer(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigServerContext());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void sitecoreBuild(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(sitecoreConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sitecoreDeploy(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreDeploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sitecoreUnitTest(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	
	public void sitecoreArchetypeOverAllPdfReport(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sitecoreConst.getSitecoreArchetypePdfReportIcon(), methodName);
			getXpathWebElement(sitecoreConst.getSitecoreArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(sitecoreConst.getSitecoreArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sitecoreArchetypeDetailedPdfReport(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sitecoreConst.getSitecoreArchetypePdfReportIcon(), methodName);
			getXpathWebElement(sitecoreConst.getSitecoreArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(sitecoreConst.getSitecoreArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void sharePointArchetypeCreate(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(sharePointConst.getSharepointArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(sharePointConst.getSharepointArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sharePointConst.getStartDate(), methodName);
			getXpathWebElement(sharePointConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(sharePointConst.getSharepointArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologySharePoint(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologySharePoint());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(sharePointConst.getSharepointArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointArchetypeEditProject(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(sharePointConst.getEditProject(), methodName);
			getXpathWebElement(sharePointConst.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointArchetypeEditApp(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sharePointConst.getSharepointArchetypeEditApp(), methodName);
			getXpathWebElement(sharePointConst.getSharepointArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSharepointServer(), methodName);
			getXpathWebElement(phrescoUiConst.getSharepointServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSharepointServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getSharepointServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(phrescoUiConst.getWebdriverValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointArchetypeUpdateFeature(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sharePointArchetypeCodeSource(String methodName,
			SharepointConstants sharepointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointCodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointArchetypeEditAppDesc(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(sharePointConst.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointCodeSource(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointCodeSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointCodeFunctionalTest(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointCodeFunctionalTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateFunctionalTest(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateFunctionalTest());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointConfigurationServer(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(sharePointConst.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(sharePointConst.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(sharePointConst.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(sharePointConst.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(sharePointConst.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(sharePointConst.getConfigServerContext());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void sharePointBuild(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(sharePointConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointDeploy(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointDeploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sharePointUnitTest(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sharePointArchetypeOverAllPdfReport(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sharePointConst.getSharepointArchetypePdfReportIcon(), methodName);
			getXpathWebElement(sharePointConst.getSharepointArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(sharePointConst.getSharepointArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sharePointArchetypeDetailedPdfReport(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(sharePointConst.getSharepointArchetypePdfReportIcon(), methodName);
			getXpathWebElement(sharePointConst.getSharepointArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(sharePointConst.getSharepointArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	
	public void aspDotnetArchetypeCreate(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(aspDotnetConst.getAspDotnetArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(aspDotnetConst.getAspDotnetArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(aspDotnetConst.getStartDate(), methodName);
			getXpathWebElement(aspDotnetConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(aspDotnetConst.getAspDotnetArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyAspDotNet(), methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyAspDotNet());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			isTextPresent(aspDotnetConst.getAspDotnetArchetypeName(), methodName);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetArchetypeEditProject(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(aspDotnetConst.getEditProject(), methodName);
			getXpathWebElement(aspDotnetConst.getEditProject());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetArchetypeEditApp(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(aspDotnetConst.getAspDotnetArchetypeEditApp(), methodName);
			getXpathWebElement(aspDotnetConst.getAspDotnetArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getIisServer(), methodName);
			getXpathWebElement(phrescoUiConst.getIisServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getIisServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getIisServerVersion());
			click();

		/*	Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getjUnitValue(), methodName);
			getXpathWebElement(phrescoUiConst.getjUnitValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(phrescoUiConst.getWebdriverValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();
*/
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetArchetypeUpdateFeature(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeUpdateFeature executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			boolean featureUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getFeatureUpdateMsg());
			System.out.println("featureUpdateVerify:::"+featureUpdateVerify);
			Assert.assertTrue(featureUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetArchetypeEditAppDesc(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			clear();
			sendKeys(aspDotnetConst.getAppEditDesc());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetCodeCs(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetCodeCs executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateCs(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateCs());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetCodeJs(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetCodeJs executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJs(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJs());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			/*waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);*/
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void aspDotnetConfigurationServer(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetConfigurationServer executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddConfig(), methodName);
			getXpathWebElement(phrescoUiConst.getAddConfig());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigServer());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDesc());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigServerDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigServerHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigServerPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigServerDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigServerContext());
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void aspDotnetBuild(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(aspDotnetConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseEnvironment());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseLogs(), methodName);
			getXpathWebElement(phrescoUiConst.getChooseLogs());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildRun(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildRun());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetDeploy(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetDeploy executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployBuild(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployBuild());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getDeployButton(), methodName);
			getXpathWebElement(phrescoUiConst.getDeployButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void aspDotnetUnitTest(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void aspDotnetArchetypeOverAllPdfReport(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(aspDotnetConst.getAspDotnetArchetypePdfReportIcon(), methodName);
			getXpathWebElement(aspDotnetConst.getAspDotnetArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(aspDotnetConst.getAspDotnetArchetypeOverAllReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void aspDotnetArchetypeDetailedPdfReport(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(aspDotnetConst.getAspDotnetArchetypePdfReportIcon(), methodName);
			getXpathWebElement(aspDotnetConst.getAspDotnetArchetypePdfReportIcon());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(aspDotnetConst.getAspDotnetArchetypeDetailReportName());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfGenerateBtn());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfCloseBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfCloseBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public boolean successFailureLoop() throws InterruptedException,
	IOException, Exception {
		String foundString = buildStatusVerifier();
		if (foundString.equalsIgnoreCase("BUILD SUCCESS")) {
			return true;
		} else
			return false;
	}

	private String buildStatusVerifier() {
		String BUILD_SUCCESS = "BUILD SUCCESS";
		String BUILD_FAILURE = "BUILD FAILURE";
		String RUN_AGAINST_SOURCE = "server started";
		String RUN_AGAINST_SOURCE_FAILED = "server started";
		boolean statusStringFound = false;
		while (!statusStringFound) {
			if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_SUCCESS)) {
				return BUILD_SUCCESS;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_FAILURE)) {
				return BUILD_FAILURE;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(RUN_AGAINST_SOURCE)) {
				return BUILD_SUCCESS;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(RUN_AGAINST_SOURCE_FAILED)) {
				return BUILD_FAILURE;
			}

		}
		return null;
	}



	public boolean updateSuccessFailureLoop(String methodName,String updateMsg) throws InterruptedException,
	IOException, Exception {
		String foundString = updateStatusVerifier(updateMsg);
		if (foundString.equalsIgnoreCase(updateMsg)) {
			return true;
		} else
			return false;
	}

	private String updateStatusVerifier(String updateMsg) throws InterruptedException {
		String UPDATE_SUCCESS = updateMsg;
		String timeout = "timeout";
		for(int i =0; i<=40;i++)
		{
			if (driver.findElement(By.tagName("body")).getText()
					.contains(UPDATE_SUCCESS)) {
				return UPDATE_SUCCESS;
			}
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
		}
		return timeout;
	}




}
