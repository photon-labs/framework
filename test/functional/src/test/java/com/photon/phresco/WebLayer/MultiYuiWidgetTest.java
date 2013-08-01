package com.photon.phresco.WebLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.WebScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.MultiYuiWidgetConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class MultiYuiWidgetTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static WebScreen webBaseScreen;
	private static MultiYuiWidgetConstants multiYuiConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		multiYuiConst = new MultiYuiWidgetConstants();
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
	public void testMultiYuiWidgetArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.multiYuiWidgetArchetypeCreate(methodName, multiYuiConst);
		
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testMultiYuiWidgetArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiYuiWidgetArchetypeEditProject(methodName, multiYuiConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testMultiYuiWidgetArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiYuiWidgetArchetypeEditApp(methodName, multiYuiConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testMultiYuiWidgetArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiYuiWidgetArchetypeUpdateApp(methodName, multiYuiConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testMultiYuiWidgetArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiYuiWidgetArchetypeEditAppDesc(methodName, multiYuiConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testMultiYuiWidgetArchetypeConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeConfigurationCreate()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiYuiWidgetConfigurationCreate(methodName, multiYuiConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testMultiYuiWidgetArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testMultiYuiWidgetArchetypeGenerateBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeGenerateBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiYuiWidgetGenerateBuild(methodName, multiYuiConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testMultiYuiWidgetCodeValidateJs() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testMultiYuiWidgetCodeValidateJs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJs(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testMultiYuiWidgetCodeValidateJava()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiYuiWidgetCodeValidateJava()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJava(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testMultiYuiWidgetCodeValidateJsp()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiYuiWidgetCodeValidateJsp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJsp(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testMultiYuiWidgetCodeValidateHtml()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiYuiWidgetCodeValidateHtml()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateHtml(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testMultiYuiWidgetCodeValidateFunctional()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiYuiWidgetCodeValidateFunctional()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testMultiYuiWidgetArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.multiYuiWidgetArchetypeOverAllPdfReport(methodName, multiYuiConst);
		
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testMultiYuiWidgetArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.multiYuiWidgetArchetypeDetailedPdfReport(methodName, multiYuiConst);
		
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@AfterTest
	public  void tearDown() {
		webBaseScreen.closeBrowser();
	}

}

