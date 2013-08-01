package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.testng.Assert;
import com.photon.phresco.selenium.util.MagicNumbers;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.JQueryMobileWidgetConstants;
import com.photon.phresco.uiconstants.MultiJQueryWidgetConstants;
import com.photon.phresco.uiconstants.MultiYuiWidgetConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.YuiMobileWidgetConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class WebScreen extends BaseScreen {
	private Log log = LogFactory.getLog("WebScreen");
	private PhrescoUiConstants phrescoUiConst;
	private UserInfoConstants userInfoConst;
	private PhrescoframeworkData phrescoData;

	public WebScreen(String selectedBrowser, String applicationURL,
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
		log.info("@testLoginPage scenario****");
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
			waitForElementPresent(phrescoUiConst.getCustomerDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCustomerDropdown());
			click();

			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCustomerName(), methodName);
			getXpathWebElement(phrescoUiConst.getCustomerName());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeCreate(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(multiYuiConst.getMultiYuiArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(multiYuiConst.getMultiYuiArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(multiYuiConst.getStartDate(), methodName);
			getXpathWebElement(multiYuiConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(multiYuiConst.getMultiYuiArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyHTML5(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyHTML5());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerWidget(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerWidget());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyMYW(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyMYW());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,
					phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::" + projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(multiYuiConst.getMultiYuiArchetypeEditApp(),
					methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeEditProject(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(multiYuiConst.getEditProject(), methodName);
			getXpathWebElement(multiYuiConst.getEditProject());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeEditApp(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(multiYuiConst.getMultiYuiArchetypeEditApp(),
					methodName);
			getXpathWebElement(multiYuiConst.getMultiYuiArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getApacheTomcatServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebdriverValue(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebdriverValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeUpdateApp(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeEditAppDesc(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetArchetypeEditAppDesc executing");
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
			sendKeys(multiYuiConst.getAppUpdateDesc());
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetConfigurationCreate(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetConfigurationCreate executing");
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
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(multiYuiConst.getConfigName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(multiYuiConst.getConfigHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(multiYuiConst.getConfigPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(multiYuiConst.getConfigDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(multiYuiConst.getConfigContext());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetGenerateBuild(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetGenerateBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(multiYuiConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(),
					methodName);
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
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetDeploy(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetDeploy executing");
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

	public void multiYuiWidgetUnitTest(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeOverAllPdfReport(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetArchetypeOverAllPdfReport executing");
		try {

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					multiYuiConst.getMultiYuiArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(multiYuiConst
					.getMultiYuiArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(multiYuiConst.getMultiYuiArchetypeOverAllReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void multiYuiWidgetArchetypeDetailedPdfReport(String methodName,
			MultiYuiWidgetConstants multiYuiConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					multiYuiConst.getMultiYuiArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(multiYuiConst
					.getMultiYuiArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(multiYuiConst.getMultiYuiArchetypeDetailReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void multiJQueryWidgetArchetypeCreate(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(multiJQueryConst.getMultiJQueryArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(multiJQueryConst.getMultiJQueryArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(multiJQueryConst.getStartDate(), methodName);
			getXpathWebElement(multiJQueryConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(multiJQueryConst.getMultiJQueryArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyHTML5(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyHTML5());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerWidget(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerWidget());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyMJW(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyMJW());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,
					phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::" + projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(
					multiJQueryConst.getMultiJQueryArchetypeEditApp(),
					methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetArchetypeEditProject(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(multiJQueryConst.getEditProject(), methodName);
			getXpathWebElement(multiJQueryConst.getEditProject());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetArchetypeEditApp(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					multiJQueryConst.getMultiJQueryArchetypeEditApp(),
					methodName);
			getXpathWebElement(multiJQueryConst
					.getMultiJQueryArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getApacheTomcatServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebdriverValue(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebdriverValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetArchetypeUpdateApp(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetArchetypeEditAppDesc(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeEditAppDesc executing");
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
			sendKeys(multiJQueryConst.getAppUpdateDesc());
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetConfigurationCreate(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetConfigurationCreate executing");
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
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(multiJQueryConst.getConfigName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(multiJQueryConst.getConfigHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(multiJQueryConst.getConfigPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(multiJQueryConst.getConfigDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(multiJQueryConst.getConfigContext());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetGenerateBuild(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetGenerateBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(multiJQueryConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(),
					methodName);
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
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetDeploy(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetDeploy executing");
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

	public void multiJQueryWidgetUnitTest(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetArchetypeOverAllPdfReport(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeOverAllPdfReport executing");
		try {

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					multiJQueryConst.getMultiJQueryArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(multiJQueryConst
					.getMultiJQueryArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(multiJQueryConst
					.getMultiJQueryArchetypeOverAllReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void multiJQueryWidgetArchetypeDetailedPdfReport(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					multiJQueryConst.getMultiJQueryArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(multiJQueryConst
					.getMultiJQueryArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(multiJQueryConst.getMultiJQueryArchetypeDetailReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void yuiMobileWidgetArchetypeCreate(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(yuiMobConst.getYUIMobileArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(yuiMobConst.getYUIMobileArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(yuiMobConst.getStartDate(), methodName);
			getXpathWebElement(yuiMobConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(yuiMobConst.getYUIMobileArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyHTML5(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyHTML5());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerWidget(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerWidget());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyYMW(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyYMW());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,
					phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::" + projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(yuiMobConst.getYUIMobileArchetypeEditApp(),
					methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetArchetypeEditProject(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(yuiMobConst.getEditProject(), methodName);
			getXpathWebElement(yuiMobConst.getEditProject());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetArchetypeEditApp(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(yuiMobConst.getYUIMobileArchetypeEditApp(),
					methodName);
			getXpathWebElement(yuiMobConst.getYUIMobileArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getApacheTomcatServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebdriverValue(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebdriverValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetArchetypeUpdateApp(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetArchetypeEditAppDesc(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeEditAppDesc executing");
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
			sendKeys(yuiMobConst.getAppUpdateDesc());

			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetConfigurationCreate(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetConfigurationCreate executing");
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
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(yuiMobConst.getConfigName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(yuiMobConst.getConfigHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(yuiMobConst.getConfigPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(yuiMobConst.getConfigDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(yuiMobConst.getConfigContext());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetGenerateBuild(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetGenerateBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(yuiMobConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(),
					methodName);
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
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetDeploy(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetDeploy executing");
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

	public void yuiMobileWidgetUnitTest(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetArchetypeOverAllPdfReport(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeOverAllPdfReport executing");
		try {

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					yuiMobConst.getYuiMobileArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(yuiMobConst.getYuiMobileArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(yuiMobConst.getYuiMobileArchetypeOverAllReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void yuiMobileWidgetArchetypeDetailedPdfReport(String methodName,
			YuiMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					yuiMobConst.getYuiMobileArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(yuiMobConst.getYuiMobileArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(yuiMobConst.getYuiMobileArchetypeDetailReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void jQueryMobWidgetArchetypeCreate(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(jQueryMobConst.getjQueryMobArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(jQueryMobConst.getjQueryMobArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getStartDate(), methodName);
			getXpathWebElement(phrescoUiConst.getStartDate());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(jQueryMobConst.getStartDate(), methodName);
			getXpathWebElement(jQueryMobConst.getStartDate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(jQueryMobConst.getjQueryMobArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyHTML5(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyHTML5());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerWidget(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerWidget());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebTechnologyJMW(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebTechnologyJMW());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,
					phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::" + projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(
					jQueryMobConst.getjQueryMobArchetypeEditApp(), methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetArchetypeEditProject(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(jQueryMobConst.getEditProject(), methodName);
			getXpathWebElement(jQueryMobConst.getEditProject());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetArchetypeEditApp(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					jQueryMobConst.getjQueryMobArchetypeEditApp(), methodName);
			getXpathWebElement(jQueryMobConst.getjQueryMobArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getApacheTomcatServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getServerVersionDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(phrescoUiConst.getApacheTomcatServerVersion());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalFramework());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getTestNGValue(), methodName);
			getXpathWebElement(phrescoUiConst.getTestNGValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalTool(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalTool());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getGridValue(), methodName);
			getXpathWebElement(phrescoUiConst.getGridValue());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),
					methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();*/

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetArchetypeUpdateApp(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetArchetypeEditAppDesc(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeEditAppDesc executing");
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
			sendKeys(jQueryMobConst.getAppUpdateDesc());
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetConfigurationCreate(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetConfigurationCreate executing");
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
			waitForElementPresent(phrescoUiConst.getConfigChooseServer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigChooseServer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServer(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigServer());
			click();
			clear();
			sendKeys(jQueryMobConst.getConfigName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerHost(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerHost());
			click();
			clear();
			sendKeys(jQueryMobConst.getConfigHost());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerPort(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerPort());
			click();
			clear();
			sendKeys(jQueryMobConst.getConfigPort());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerDeployDir(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerDeployDir());
			click();
			clear();
			sendKeys(jQueryMobConst.getConfigDeployDir());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getConfigServerContext(),
					methodName);
			getXpathWebElement(phrescoUiConst.getConfigServerContext());
			click();
			clear();
			sendKeys(jQueryMobConst.getConfigContext());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetGenerateBuild(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetGenerateBuild executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildGenerate(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildGenerate());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildName(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildName());
			click();
			clear();
			sendKeys(jQueryMobConst.getBuildName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getChooseEnvironment(),
					methodName);
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
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetDeploy(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetDeploy executing");
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

	public void jQueryMobWidgetUnitTest(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitTestButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getUnitTestButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryMobWidgetArchetypeOverAllPdfReport(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeOverAllPdfReport executing");
		try {

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					jQueryMobConst.getjQueryMobArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(jQueryMobConst
					.getjQueryMobArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfOverAllReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			sendKeys(jQueryMobConst.getjQueryMobArchetypeOverAllReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void jQueryMobWidgetArchetypeDetailedPdfReport(String methodName,
			JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					jQueryMobConst.getjQueryMobArchetypePdfReportIcon(),
					methodName);
			getXpathWebElement(jQueryMobConst
					.getjQueryMobArchetypePdfReportIcon());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfReportTypeDropDown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getPdfReportTypeDropDown());
			click();
			sendKeys(phrescoUiConst.getPdfDetailReport());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfName(), methodName);
			getXpathWebElement(phrescoUiConst.getPdfName());
			click();
			clear();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			sendKeys(jQueryMobConst.getjQueryMobArchetypeDetailReportName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getPdfGenerateBtn(),
					methodName);
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

	public void codeValidateJs(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@codeValidateJs  executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJs(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJs());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(),
					methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void codeValidateJava(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@codeValidateJava  executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJava(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJava());
			click();
			/*
			 * Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			 * waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(),
			 * methodName);
			 * getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			 * click();
			 */
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void codeValidateJsp(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@codeValidateJsp  executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateJsp(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateJsp());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(),
					methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void codeValidateHtml(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@codeValidateHtml  executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateSource(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateSource());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeTechnologyDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeTechnologyDropdown());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateHtml(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateHtml());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public boolean updateSuccessFailureLoop(String methodName, String updateMsg)
			throws InterruptedException, IOException, Exception {
		String foundString = updateStatusVerifier(updateMsg);
		if (foundString.equalsIgnoreCase(updateMsg)) {
			return true;
		} else
			return false;
	}

	private String updateStatusVerifier(String updateMsg)
			throws InterruptedException {
		String UPDATE_SUCCESS = updateMsg;
		String timeout = "timeout";
		for (int i = 0; i <= 40; i++) {
			if (driver.findElement(By.tagName("body")).getText()
					.contains(UPDATE_SUCCESS)) {
				return UPDATE_SUCCESS;
			}
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
		}
		return timeout;
	}

}
