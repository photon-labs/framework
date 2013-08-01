package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.photon.phresco.selenium.util.MagicNumbers;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.AndroidLibraryConstants;
import com.photon.phresco.uiconstants.AndroidNativeConstants;
import com.photon.phresco.uiconstants.BlackBerryHybridConstants;
import com.photon.phresco.uiconstants.IPhoneHybridConstants;
import com.photon.phresco.uiconstants.IPhoneLibraryConstants;
import com.photon.phresco.uiconstants.IPhoneNativeConstants;
import com.photon.phresco.uiconstants.IPhoneWorkspaceConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.WindowsMetroConstants;
import com.photon.phresco.uiconstants.WindowsPhoneConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class MobScreen extends BaseScreen {


	private Log log = LogFactory.getLog("MobScreen");
	
	private PhrescoUiConstants phrescoUiConst;
	private UserInfoConstants userInfoConst;
	private PhrescoframeworkData phrescoData;

	
	public MobScreen(String selectedBrowser, String applicationURL,
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
		log.info("@loginPage scenario****");
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

			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCustomerDropdown(),methodName);
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

	public void iPhoneNativeArchetypeCreate(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeAppCode());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphone(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphone());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphoneNative(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphoneNative());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneNativeArchetypeEditProject(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneNativeConst.getiPhoneNativeArchetypeProjectEditIcon(), methodName);
			getXpathWebElement(iPhoneNativeConst.getiPhoneNativeArchetypeProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneNativeArchetypeEditApp(String methodName,	IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeEditApp executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneNativeConst.getiPhoneNatArchetypeEditAppApp(), methodName);
			getXpathWebElement(iPhoneNativeConst.getiPhoneNatArchetypeEditAppApp());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

		/*	Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),	methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneNativeArchetypeEditAppDesc(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeUpdateAppDesc executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneNativeArchetypeUpdateFeatures(String methodName,
			IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeUpdateApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneNativeArchetypeBuild(String methodName,
			IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeBuild executing");
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
			sendKeys(iPhoneNativeConst.getBuildName());
			
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
		} 
		catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void iPhoneNativeDeploy(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeDeploy executing");
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
	
	public void iPhoneNativeArchetypeUnitTest(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneNativeArchetypeOverAllPdfReport(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneNativeConst.getiPhoneNatArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneNativeConst.getiPhoneNatArchetypePdfReportIcon());
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
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeOverAllReportName());
			
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
	
	public void iPhoneNativeArchetypeDetailedPdfReport(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneNativeConst.getiPhoneNatArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneNativeConst.getiPhoneNatArchetypePdfReportIcon());
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
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeDetailReportName());
			
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

	public void iPhoneHybridArchetypeCreate(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphone(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphone());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphoneHybrid(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphoneHybrid());
			click();			

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneHybridArchetypeEditProject(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneHybridConst.getiPhoneHybArchetypeProjectEditIcon(), methodName);
			getXpathWebElement(iPhoneHybridConst.getiPhoneHybArchetypeProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneHybridArchetypeEditApp(String methodName,
			IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeEditApp executing");
		try {

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneHybridConst.getIphoneHybSrchetypeAppEditApp(), methodName);
			getXpathWebElement(iPhoneHybridConst.getIphoneHybSrchetypeAppEditApp());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),	methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneHybridArchetypeUpdateFeatures(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeUpdateApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneHybridArchetypeEditAppDesc(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneHybridArchetypeBuild(String methodName,
			IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeBuild executing");
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
			sendKeys(iPhoneHybridConst.getBuildName());
			
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
		} 
		catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void iPhoneHybridDeploy(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridDeploy executing");
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
	
	public void iPhoneHybridArchetypeUnitTest(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneHybridArchetypeOverAllPdfReport(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeOverAllPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneHybridConst.getiPhoneHybArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneHybridConst.getiPhoneHybArchetypePdfReportIcon());
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
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeOverAllReportName());
			
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
	
	public void iPhoneHybridArchetypeDetailedPdfReport(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneHybridConst.getiPhoneHybArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneHybridConst.getiPhoneHybArchetypePdfReportIcon());
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
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeDetailReportName());
			
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

	public void iPhoneLibraryArchetypeCreate(String methodName,	IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphone(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphone());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphoneLibrary(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphoneLibrary());
			click();			

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneLibraryArchetypeEditProject(String methodName,IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneLibraryConst.getiPhoneLibArchetypeProjectEditIcon(), methodName);
			getXpathWebElement(iPhoneLibraryConst.getiPhoneLibArchetypeProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneLibraryArchetypeEditApp(String methodName,IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeEditApp executing");
		try {

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneLibraryConst.getIphoneLibArchetypeAppEditApp(), methodName);
			getXpathWebElement(iPhoneLibraryConst.getIphoneLibArchetypeAppEditApp());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),	methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneLibraryArchetypeUpdateAppFeatures(String methodName,
			IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeUpdateAppFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneLibraryArchetypeEditAppDesc(String methodName,
			IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneLibraryArchetypeBuild(String methodName,
			IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeBuild executing");
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
			sendKeys(iPhoneLibraryConst.getBuildName());
			
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
	
	public void iPhoneLibraryArchetypeOverAllPdfReport(String methodName,IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneLibraryConst.getiPhoneLibArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneLibraryConst.getiPhoneLibArchetypePdfReportIcon());
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
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeOverAllReportName());
			
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
	
	public void iPhoneLibraryArchetypeDetailedPdfReport(String methodName,IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneLibraryConst.getiPhoneLibArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneLibraryConst.getiPhoneLibArchetypePdfReportIcon());
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
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeDetailReportName());
			
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
	
	
	public void iPhoneWorkspaceArchetypeCreate(String methodName,IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeAppCode());
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphone(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphone());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyIphoneWorkspace(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyIphoneWorkspace());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneWorkspaceArchetypeEditProject(String methodName,IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeProjectEditIcon(), methodName);
			getXpathWebElement(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneWorkspaceArchetypeEditApp(String methodName,IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeEditApp executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneWorkspaceConst.getIphoneWorkspaceArchetypeEditApp(), methodName);
			getXpathWebElement(iPhoneWorkspaceConst.getIphoneWorkspaceArchetypeEditApp());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

		/*	Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),	methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneWorkspaceArchetypeEditAppDesc(String methodName,IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeEditAppDesc executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneWorkspaceArchetypeUpdateFeatures(String methodName,
			IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeUpdateFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void iPhoneWorkspaceArchetypeBuild(String methodName,
			IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeBuild executing");
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
			sendKeys(iPhoneWorkspaceConst.getBuildName());
			
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
		} 
		catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void iPhoneWorkspaceDeploy(String methodName,IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceDeploy executing");
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
		
	
	public void iPhoneWorkspaceArchetypeOverAllPdfReport(String methodName,IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypePdfReportIcon());
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
			sendKeys(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeOverAllReportName());
			
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
	
	public void iPhoneWorkspaceArchetypeDetailedPdfReport(String methodName,IPhoneWorkspaceConstants iPhoneWorkspaceConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneWorkspaceArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypePdfReportIcon(), methodName);
			getXpathWebElement(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypePdfReportIcon());
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
			sendKeys(iPhoneWorkspaceConst.getiPhoneWorkspaceArchetypeDetailReportName());
			
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


	public void androidNativeArchetypeCreate(String methodName,	AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(androidNativeConst.getAndroidNatArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(androidNativeConst.getAndroidNatArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(androidNativeConst.getAndroidNatArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroid(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroid());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroidNative(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroidNative());
			click();		
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidNativeArchetypeEditProject(String methodName,AndroidNativeConstants  androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidNativeConst.getAndroidNatArchetypeProjectEditIcon(), methodName);
			getXpathWebElement(androidNativeConst.getAndroidNatArchetypeProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidNativeArchetypeEditApp(String methodName,AndroidNativeConstants  androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidNativeConst.getAndroidNatArchetypeEditApp(), methodName);
			getXpathWebElement(androidNativeConst.getAndroidNatArchetypeEditApp()); click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),	methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidNativeArchetypeEditAppDesc(String methodName,AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(androidNativeConst.getAndroidNatArchetypeUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidNativeArchetypeUpdateAppFeatures(String methodName,AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeUpdateAppFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	
	
	public void androidNativeArchetypeBuild(String methodName,
			AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeBuild executing");
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
			sendKeys(androidNativeConst.getBuildName());
			
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
	
	
	public void androidNativeDeploy(String methodName,AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeDeploy executing");
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
	
	public void androidNativeArchetypeUnitTest(String methodName,AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidNativeArchetypeOverAllPdfReport(String methodName,AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidNativeConst.getAndroidNatArchetypePdfReportIcon(), methodName);
			getXpathWebElement(androidNativeConst.getAndroidNatArchetypePdfReportIcon());
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
			sendKeys(androidNativeConst.getAndroidNatArchetypeOverAllReportName());
			
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
	
	public void androidNativeArchetypeDetailedPdfReport(String methodName,AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidNativeConst.getAndroidNatArchetypePdfReportIcon(), methodName);
			getXpathWebElement(androidNativeConst.getAndroidNatArchetypePdfReportIcon());
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
			sendKeys(androidNativeConst.getAndroidNatArchetypeDetailReportName());
			
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


	public void androidHybridArchetypeCreate(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(androidHybConst.getAndroidHybArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(androidHybConst.getAndroidHybArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(androidHybConst.getAndroidHybArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroid(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroid());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroidHybrid(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroidHybrid());
			click();				

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidHybridArchetypeEditProject(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidHybConst.getAndroidHybArchetypeProjectEditIcon(), methodName);
			getXpathWebElement(androidHybConst.getAndroidHybArchetypeProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	

	public void androidHybridArchetypeEditApp(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeEditApp executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidHybConst.getAndroidHybArchetypeEditApp(), methodName);
			getXpathWebElement(androidHybConst.getAndroidHybArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			
			
			

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidHybridArchetypeUpdateAppFeatures(String methodName,
			AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeUpdateAppFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidHybridArchetypeEditAppDesc(String methodName,
			AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeUpdateEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(androidHybConst.getAndroidHybArchetypeUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	
	
	
	public void androidHybridArchetypeBuild(String methodName,
			AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
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
			sendKeys(androidHybConst.getBuildName());
			
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
	
	
	public void androidHybridDeploy(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridDeploy executing");
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
	
	public void androidHybridArchetypeOverAllPdfReport(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidHybConst.getAndroidHybArchetypePdfReportIcon(), methodName);
			getXpathWebElement(androidHybConst.getAndroidHybArchetypePdfReportIcon());
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
			sendKeys(androidHybConst.getAndroidHybArchetypeOverAllReportName());
			
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
	
	public void androidHybridArchetypeDetailedPdfReport(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidHybConst.getAndroidHybArchetypePdfReportIcon(), methodName);
			getXpathWebElement(androidHybConst.getAndroidHybArchetypePdfReportIcon());
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
			sendKeys(androidHybConst.getAndroidHybArchetypeDetailReportName());
			
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

	

	public void androidLibraryArchetypeCreate(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(androidLibraryConst.getAndroidLibArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(androidLibraryConst.getAndroidLibArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(androidLibraryConst.getAndroidLibArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroid(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroid());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyAndroidLibrary(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyAndroidLibrary());
			click();	

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidLibraryArchetypeEditProject(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidLibraryConst.getAndroidLibArchetypeProjectEditIcon(), methodName);
			getXpathWebElement(androidLibraryConst.getAndroidLibArchetypeProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidLibraryArchetypeEditApp(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeEditApp executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidLibraryConst.getAndroidLibArchetypeEditApp(), methodName);
			getXpathWebElement(androidLibraryConst.getAndroidLibArchetypeEditApp());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),	methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidLibraryArchetypeUpdateAppFeatures(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeUpdateAppFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidLibraryArchetypeEditAppDesc(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(androidLibraryConst.getAndroidLibArchetypeUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
		
	
	public void androidLibraryArchetypeBuild(String methodName,
			AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
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
			sendKeys(androidLibraryConst.getBuildName());
			
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
	
	public void androidLibraryArchetypeOverAllPdfReport(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidLibraryConst.getAndroidLibArchetypePdfReportIcon(), methodName);
			getXpathWebElement(androidLibraryConst.getAndroidLibArchetypePdfReportIcon());
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
			sendKeys(androidLibraryConst.getAndroidLibArchetypeOverAllReportName());
			
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
	
	public void androidLibraryArchetypeDetailedPdfReport(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(androidLibraryConst.getAndroidLibArchetypePdfReportIcon(), methodName);
			getXpathWebElement(androidLibraryConst.getAndroidLibArchetypePdfReportIcon());
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
			sendKeys(androidLibraryConst.getAndroidLibArchetypeDetailReportName());
			
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

	

	public void blackBerryHybridArchetypeCreate(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyBlackBerry(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyBlackBerry());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyBlackBerryHybrid(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyBlackBerryHybrid());
			click();				

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void blackBerryHybridArchetypeEditProject(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(blackBerryHybConst.getBlackBerryHybArchetypeEditProjectIcon(), methodName);
			getXpathWebElement(blackBerryHybConst.getBlackBerryHybArchetypeEditProjectIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void blackBerryHybridArchetypeEditApp(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeEditApp executing");
		try {

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(blackBerryHybConst.getBlackBerryHybArchetypeEditAppLink(), methodName);
			getXpathWebElement(blackBerryHybConst.getBlackBerryHybArchetypeEditAppLink());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void blackBerryHybridArchetypeUpdateAppFeatures(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeUpdateAppFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void blackBerryHybridArchetypeEditAppDesc(String methodName,
			BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeEditAppDesc executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(blackBerryHybConst.getUpdateDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	

	public void blackBerryHybridArchetypeBuild(String methodName,
			BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeBuild executing");
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
			sendKeys(blackBerryHybConst.getBuildName());
			
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
	
	public void blackBerryHybridDeploy(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridDeploy executing");
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
	
	public void blackBerryHybridArchetypeOverAllPdfReport(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeOverAllPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(blackBerryHybConst.getBlackBerryHybArchetypePdfReportIcon(), methodName);
			getXpathWebElement(blackBerryHybConst.getBlackBerryHybArchetypePdfReportIcon());
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
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeOverAllReportName());
			
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
	
	public void blackBerryHybridArchetypeDetailedPdfReport(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(blackBerryHybConst.getBlackBerryHybArchetypePdfReportIcon(), methodName);
			getXpathWebElement(blackBerryHybConst.getBlackBerryHybArchetypePdfReportIcon());
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
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeDetailReportName());
			
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
	
	public void windowsMetroArchetypeCreate(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyWindows(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyWindows());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyWindowsMetro(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyWindowsMetro());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(windowsMetroConst.getWindowsMetroArchetypeEditAppLink(), methodName);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void windowsMetroArchetypeEditProject(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(windowsMetroConst.getWindowsMetroProjectEditIcon(), methodName);
			getXpathWebElement(windowsMetroConst.getWindowsMetroProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsMetroArchetypeEditApp(String methodName,	WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeEditApp executing");
		try {
			
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(windowsMetroConst.getWindowsMetroArchetypeEditAppLink(), methodName);
			getXpathWebElement(windowsMetroConst.getWindowsMetroArchetypeEditAppLink());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

		/*	Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),	methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(
					phrescoUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(phrescoUiConst.getFunctionalToolVersionValue());
			click();
*/			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsMetroArchetypeUpdateAppFeatures(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeUpdateAppFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	
	public void windowsMetroArchetypeEditAppDesc(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeEditAppDesc executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeEditDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	
	public void windowsMetroArchetypeBuild(String methodName,
			WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeBuild executing");
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
			sendKeys(windowsMetroConst.getBuildName());
			
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
	
	public void windowsMetroDeploy(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroDeploy executing");
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
	
	public void windowsMetroArchetypeOverAllPdfReport(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(windowsMetroConst.getWindowsMetroArchetypePdfReportIcon(), methodName);
			getXpathWebElement(windowsMetroConst.getWindowsMetroArchetypePdfReportIcon());
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
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeOverAllReportName());
			
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
	
	public void windowsMetroArchetypeDetailedPdfReport(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(windowsMetroConst.getWindowsMetroArchetypePdfReportIcon(), methodName);
			getXpathWebElement(windowsMetroConst.getWindowsMetroArchetypePdfReportIcon());
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
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeDetailReportName());
			
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
	
	
	public void windowsMetroArchetypeUnitTest(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsPhoneArchetypeCreate(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeCreate executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(phrescoUiConst.getAddProjectButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectName(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectName());
			click();
			clear();
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeName());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectDescription(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeDesc());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(phrescoUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeAppCode());

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobLayer(), methodName);
			getXpathWebElement(phrescoUiConst.getMobLayer());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyWindows(),methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyWindows());
			click();		

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobileType(), methodName);
			getXpathWebElement(phrescoUiConst.getMobileType());
			click();
								
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getMobTechnologyWindowsPhone(), methodName);
			getXpathWebElement(phrescoUiConst.getMobTechnologyWindowsPhone());
			click();

			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(phrescoUiConst.getProjectCreateButton());
			click();
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			waitForElementPresent(windowsMobileConst.getWindowsPhoneArchetypeEditAppLink(), methodName);
			boolean projectCreateVerify = updateSuccessFailureLoop(methodName,phrescoData.getProjectCreateMsg());
			System.out.println("projectCreateVerify:::"+projectCreateVerify);
			Assert.assertTrue(projectCreateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void windowsPhoneArchetypeEditProject(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeEditProject executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(windowsMobileConst.getWindowsPhoneProjectEditIcon(), methodName);
			getXpathWebElement(windowsMobileConst.getWindowsPhoneProjectEditIcon());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean projectUpdateVerify = updateSuccessFailureLoop(methodName, phrescoData.getProjectUpdateMsg());
			System.out.println("projectUpdateVerify:::"+projectUpdateVerify);
			Assert.assertTrue(projectUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsPhoneArchetypeEditApp(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeEditApp executing");
		try {
			
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(windowsMobileConst.getWindowsPhoneArchetypeEditAppLink(), methodName);
			getXpathWebElement(windowsMobileConst.getWindowsPhoneArchetypeEditAppLink());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();

			/*Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFunctionalFramework(),methodName);
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
			waitForElementPresent(phrescoUiConst.getFunctionalToolVersion(),methodName);
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
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
		
	
	public void windowsPhoneArchetypeEditAppDesc(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeEditAppDesc executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(phrescoUiConst.getAppInfoTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppDesc(), methodName);
			getXpathWebElement(phrescoUiConst.getAppDesc());
			click();
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeEditDesc());

			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getAppUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getAppUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			boolean appUpdateVerify = updateSuccessFailureLoop(methodName,phrescoData.getAppUpdateMsg());
			System.out.println("appUpdateVerify:::"+appUpdateVerify);
			Assert.assertTrue(appUpdateVerify);
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsPhoneArchetypeUpdateAppFeatures(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeUpdateAppFeatures executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureTab(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureTab());
			click();			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getFeatureUpdateButton(), methodName);
			getXpathWebElement(phrescoUiConst.getFeatureUpdateButton());
			click();
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void windowsPhoneArchetypeBuild(String methodName,
			WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
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
			sendKeys(windowsMobileConst.getBuildName());
			
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
	
	public void windowsPhoneDeploy(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneDeploy executing");
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
	
	public void windowsPhoneArchetypeUnitTest(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeUnitTest executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getQualityAssuranceButton(), methodName);
			getXpathWebElement(phrescoUiConst.getQualityAssuranceButton());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getUnitButton(), methodName);
			getXpathWebElement(phrescoUiConst.getUnitButton());
			click();
			
			Thread.sleep(MagicNumbers.TEN_THOUSAND_SECONDS);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void windowsPhoneArchetypeOverAllPdfReport(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeOverAllPdfReport executing");
		try {
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getProjectButton(), methodName);
			getXpathWebElement(phrescoUiConst.getProjectButton());
			click();
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(windowsMobileConst.getWindowsPhoneArchetypePdfReportIcon(), methodName);
			getXpathWebElement(windowsMobileConst.getWindowsPhoneArchetypePdfReportIcon());
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
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeOverAllReportName());
			
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
	
	public void windowsPhoneArchetypeDetailedPdfReport(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeDetailedPdfReport executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(windowsMobileConst.getWindowsPhoneArchetypePdfReportIcon(), methodName);
			getXpathWebElement(windowsMobileConst.getWindowsPhoneArchetypePdfReportIcon());
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
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeDetailReportName());
			
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
	
	public void codeValidateForSource(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@codeValidateForSource executing");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			
			if(methodName=="testAndroidHybridArchetypeCodeValidateSource" || methodName=="testAndroidNativeArchetypeCodeValidateSource"
				||methodName=="testBlackBerryHybridArchetypeCodeValidateSource"){
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getSkipUnitTestCheckBox(), methodName);
			getXpathWebElement(phrescoUiConst.getSkipUnitTestCheckBox());
			click();
				
			}
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
			
			
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


