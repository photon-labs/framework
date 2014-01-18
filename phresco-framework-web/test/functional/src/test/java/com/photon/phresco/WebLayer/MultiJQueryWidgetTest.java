package com.photon.phresco.WebLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.WebScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.MultiJQueryWidgetConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class MultiJQueryWidgetTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static WebScreen webBaseScreen;
	private static MultiJQueryWidgetConstants multiJQueryConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		multiJQueryConst = new MultiJQueryWidgetConstants();
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
	public void testMultiJQueryWidgetArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiJQueryWidgetArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.multiJQueryWidgetArchetypeCreate(methodName, multiJQueryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testMultiJQueryWidgetArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {
			
			System.out.println("---------testMultiJQueryWidgetArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetArchetypeEditProject(methodName, multiJQueryConst);
		
		} catch (Exception t) {
			t.printStackTrace();
		}
	} 

	@Test
	public void testMultiJQueryWidgetArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {
			
			System.out.println("---------testMultiJQueryWidgetArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetArchetypeEditApp(methodName, multiJQueryConst);
		
		} catch (Exception t) {
			t.printStackTrace();
		}
	} 

	@Test
	public void testMultiJQueryWidgetArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {
		
			System.out.println("---------testMultiJQueryWidgetArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetArchetypeUpdateApp(methodName, multiJQueryConst);
		
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testMultiJQueryWidgetArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiJQueryWidgetArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetArchetypeEditAppDesc(methodName, multiJQueryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testMultiJQueryWidgetArchetypeConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {
			
			System.out.println("---------testMultiJQueryWidgetArchetypeConfigurationCreate()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetConfigurationCreate(methodName, multiJQueryConst);
		
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testMultiJQueryWidgetArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiJQueryWidgetArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	public void testMultiJQueryWidgetArchetypeGenerateBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiJQueryWidgetArchetypeGenerateBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetGenerateBuild(methodName, multiJQueryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	@Test
	public void testMultiJQueryWidgetCodeValidateJs()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiJQueryWidgetCodeValidateJs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJs(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testMultiJQueryWidgetCodeValidateJava()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiJQueryWidgetCodeValidateJava()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJava(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testMultiJQueryWidgetCodeValidateJsp()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiJQueryWidgetCodeValidateJsp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateJsp(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testMultiJQueryWidgetCodeValidateHtml()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiJQueryWidgetCodeValidateHtml()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateHtml(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testMultiJQueryWidgetCodeValidateFunctional()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testMultiJQueryWidgetCodeValidateFunctional()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			webBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testMultiJQueryWidgetArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {
			
			System.out.println("---------testMultiJQueryWidgetArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetArchetypeOverAllPdfReport(methodName, multiJQueryConst);
		
		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testMultiJQueryWidgetArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {
			
			System.out.println("---------testMultiJQueryWidgetArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetArchetypeDetailedPdfReport(methodName, multiJQueryConst);
		
		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testMultiJQueryWidgetArchetypeComponentTest()
	throws InterruptedException, IOException, Exception {
		try {
			
			System.out.println("---------testMultiJQueryWidgetArchetypeComponentTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.componentTest(methodName);
		
		} catch (Exception t) {
			t.printStackTrace();
		}
	} 

	@AfterTest
	public  void tearDown() {
		webBaseScreen.closeBrowser();
	}

}

