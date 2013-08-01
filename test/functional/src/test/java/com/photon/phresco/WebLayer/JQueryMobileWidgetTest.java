package com.photon.phresco.WebLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.WebScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.JQueryMobileWidgetConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class JQueryMobileWidgetTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static WebScreen webBaseScreen;
	private static JQueryMobileWidgetConstants jQueryMobConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		jQueryMobConst = new JQueryMobileWidgetConstants();
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
	public void testJQueryMobWidgetArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.jQueryMobWidgetArchetypeCreate(methodName, jQueryMobConst);
		} catch (Exception t) {
			t.printStackTrace();
		}
	} 

	@Test
	public void testJQueryMobWidgetArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeEditProject(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJQueryMobWidgetArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeEditApp(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJQueryMobWidgetArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeUpdateApp(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJQueryMobWidgetArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeEditAppDesc(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJQueryMobWidgetArchetypeConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeConfigurationCreate()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetConfigurationCreate(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJQueryMobWidgetArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJQueryMobWidgetCodeValidateJs()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testJQueryMobWidgetCodeValidateJs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJs(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testJQueryMobWidgetCodeValidateJava()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testJQueryMobWidgetCodeValidateJava()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJava(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testJQueryMobWidgetCodeValidateJsp()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testJQueryMobWidgetCodeValidateJsp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJsp(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testJQueryMobWidgetCodeValidateHtml()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testJQueryMobWidgetCodeValidateHtml()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateHtml(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testJQueryMobWidgetCodeValidationFunctional()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testJQueryMobWidgetCodeValidateFunctional()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	
	@Test
	public void testJQueryMobWidgetArchetypeGenerateBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeGenerateBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetGenerateBuild(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJQueryMobWidgetArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeDetailedPdfReport(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJQueryMobWidgetArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobWidgetArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeOverAllPdfReport(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@AfterTest
	public  void tearDown() {
		webBaseScreen.closeBrowser();
	}

}

