package com.photon.phresco.WebLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.WebScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.YuiMobileWidgetConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class YuiMobileWidgetTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static WebScreen webBaseScreen;
	private static YuiMobileWidgetConstants yuiMobConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		yuiMobConst = new YuiMobileWidgetConstants();
		try {
			launchingBrowser();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void launchingBrowser() throws Exception {

		String applicationURL = phrscEnv.getProtocol() + "://" + phrscEnv.getHost() + ":" + phrscEnv.getPort() + "/";
		selectedBrowser = phrscEnv.getBrowser();
		try {
			webBaseScreen = new WebScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}


	@Test
	public void testYuiMobileWidgetArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.yuiMobileWidgetArchetypeCreate(methodName, yuiMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testYuiMobileWidgetArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetArchetypeEditProject(methodName, yuiMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testYuiMobileWidgetArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetArchetypeEditApp(methodName, yuiMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testYuiMobileWidgetArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetArchetypeUpdateApp(methodName, yuiMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testYuiMobileWidgetArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetArchetypeEditAppDesc(methodName, yuiMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testYuiMobileWidgetArchetypeConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeConfigurationCreate()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetConfigurationCreate(methodName, yuiMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testYuiMobileWidgetArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testYuiMobileWidgetArchetypeGenerateBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeGenerateBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetGenerateBuild(methodName, yuiMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testYuiMobileWidgetCodeValidateJs()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testYuiMobileWidgetCodeValidateJs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJs(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testYuiMobileWidgetCodeValidateJava()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testYuiMobileWidgetCodeValidateJava()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJava(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testYuiMobileWidgetCodeValidateJsp()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testYuiMobileWidgetCodeValidateJsp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJsp(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testYuiMobileWidgetCodeValidateHtml()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testYuiMobileWidgetCodeValidateHtml()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateHtml(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testYuiMobileWidgetCodeValidateFunctional()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testYuiMobileWidgetCodeValidateFunctional()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testYuiMobileWidgetArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.yuiMobileWidgetArchetypeOverAllPdfReport(methodName, yuiMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testYuiMobileWidgetArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.yuiMobileWidgetArchetypeDetailedPdfReport(methodName, yuiMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@AfterTest
	public  void tearDown() {
		webBaseScreen.closeBrowser();
	}

}

