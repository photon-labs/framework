package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.photon.phresco.Screens.BaseScreen;
import com.photon.phresco.selenium.util.MagicNumbers;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.MultiJQueryWidgetConstants;
import com.photon.phresco.uiconstants.MultiYuiWidgetConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.ScenariosConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.YuiMobileWidgetConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class ScenariosScreen extends BaseScreen {
	private final Log log = LogFactory.getLog("Scenarios_BaseScreen");
	private PhrescoUiConstants phrescoUiConst;
	private UserInfoConstants userInfoConst;
	private PhrescoframeworkData phrescoData;

	public ScenariosScreen(String selectedBrowser, String applicationURL,
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
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
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

			Thread.sleep(2000);
			waitForElementPresent(phrescoUiConst.getCustomerDropdown(),
					methodName);
			getXpathWebElement(phrescoUiConst.getCustomerDropdown());
			click();

			Thread.sleep(2000);
			waitForElementPresent(phrescoUiConst.getCustomerName(), methodName);
			getXpathWebElement(phrescoUiConst.getCustomerName());
			click();
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidNative(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@androidNative executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyNodeJS(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyNodeJS());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeMobCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroid(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroid());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getMobTechnologyAndroidNative(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroidNative());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneNative(String methodName, ScenariosConstants scenarioConst)
			throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@iPhoneNative executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyNodeJS(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyNodeJS());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeMobCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphone(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphone());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getMobTechnologyIphoneNative(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphoneNative());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidHybrid(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@androidHybrid executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyNodeJS(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyNodeJS());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridWebCode());

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

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridMobCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroid(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroid());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getMobTechnologyAndroidHybrid(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroidHybrid());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneHybrid(String methodName, ScenariosConstants scenarioConst)
			throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@iPhoneHybrid executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyNodeJS(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyNodeJS());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridWebCode());

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

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridMobCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphone(),
					methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphone());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getMobTechnologyIphoneHybrid(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphoneHybrid());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidget(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@mobileWidget executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetAppcode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyJ2EE(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyJ2EE());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetWebcode());

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

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryWidget(String methodName, ScenariosConstants scenarioConst)
			throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@jQueryWidget executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetAppcode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppLayerTechnology());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppTechnologyJ2EE(),
					methodName);
			getXpathWebElement(phrescoUiConst.getAppTechnologyJ2EE());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetWebcode());

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

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();

			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void createProjectUseNumericValues(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@createProjectUseNumericValues executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getProjectNameNumeric());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getNumericProjectDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getNumericAppCode());

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

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void createProjectUseAlphaNumericValues(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@createProjectUseNumericValues executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getProjectNameAlphaNumeric());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getAlphaNumericProjectDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAlphaNumericAppCode());

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

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void createProjectSpecialChar(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@createProjectUseNumericValues executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getProjectNameSpecialChar());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getSpecialCharProjectDesc());

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(phrescoUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getSpecialChar());

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

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeCreate(String methodName,
			MultiYuiWidgetConstants multiYuiConst,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetScenarioCreate executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getMultiYuiWidgetName());

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
			waitForElementPresent(scenarioConst.getMultiYuiEditProj(),
					methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYuiWidgetArchetypeEditProject(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetScenarioEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(scenarioConst.getMultiYuiEditProj(),
					methodName);
			getXpathWebElement(scenarioConst.getMultiYuiEditProj());
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

	public void yuiMobileWidgetArchetypeCreate(String methodName,
			YuiMobileWidgetConstants yuiMobConst,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@yuiMobileWidgetScenarioCreate executing");
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
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getYuiMobileWidgetName());

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
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@multiYuiWidgetScenarioEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(scenarioConst.getYuiMobEditProj(), methodName);
			getXpathWebElement(scenarioConst.getYuiMobEditProj());
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

	public void configurationCreate(String methodName,
			MultiJQueryWidgetConstants multiJQueryConst,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@ScenariosConfigurationCreate executing");
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

			if (methodName == "testMultiYuiWidgetArchetypeRunAgainstSource") {

				Thread.sleep(MagicNumbers.HALF_SECONDS);
				waitForElementPresent(phrescoUiConst.getConfigServerPort(),
						methodName);
				getXpathWebElement(phrescoUiConst.getConfigServerPort());
				click();
				clear();
				sendKeys(scenarioConst.getMultiYuiConfigPort());

			} else if (methodName == "testYuiMobileWidgetArchetypeRunAgainstSource") {

				Thread.sleep(MagicNumbers.HALF_SECONDS);
				waitForElementPresent(phrescoUiConst.getConfigServerPort(),
						methodName);
				getXpathWebElement(phrescoUiConst.getConfigServerPort());
				click();
				clear();
				sendKeys(scenarioConst.getYuiMobConfigPort());
			}

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

	public void runAgainstSource(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@runAgainstSource executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getBuildTab(), methodName);
			getXpathWebElement(phrescoUiConst.getBuildTab());
			click();

			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRunAgainstSourceButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRunAgainstSourceButton());
			click();

			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getRunAgainstRunButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getRunAgainstRunButton());
			click();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void deleteProjects(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@deleteProjects executing");

		boolean deleteProject = getXpathWebElement(
				phrescoUiConst.getProjectDeleteIcon()).isDisplayed();

		while (deleteProject) {

			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDeleteIcon(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDeleteIcon());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDeletePopUpYesBtn(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectDeletePopUpYesBtn());
			click();
		}

	}

	public boolean isTextPresentBuild(String text) throws InterruptedException,
			ScreenException {

		if (text != null) {
			String build_failure = "ERROR";

			for (int i = 0; i <= 200; i++) {
				if (driver.findElement(By.tagName("body")).getText()
						.contains(text)) {
					break;
				} else {
					if (i == 200) {
						throw new RuntimeException(
								"---- Time out for finding the Text----");
					} else if (driver.findElement(By.tagName("body")).getText()
							.contains(build_failure)) {
						System.out.println("*****Build Failure*****");
						throw new ScreenException("*****Build Failure*****");

					}
					Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
				}
			}

		} else {
			throw new RuntimeException("---- Text not existed----");
		}

		return true;

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
		// String ERROR = "[ERROR]";
		boolean statusStringFound = false;
		/* long end = 70000; */
		while (!statusStringFound) { /* && end <=70000 */
			if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_SUCCESS)) {
				return BUILD_SUCCESS;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_FAILURE)) {
				return BUILD_FAILURE;
			} /*
			 * else if
			 * (driver.findElement(By.tagName("body")).getText().contains
			 * (ERROR)) { return ERROR; }
			 */
		}
		return null;
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
