package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AspDotNetConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class AspDotnetTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static AspDotNetConstants aspDotnetConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		aspDotnetConst = new AspDotNetConstants();
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
			appBaseScreen = new AppScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}

	
	@Test
	public void testAspDotnetArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.aspDotnetArchetypeCreate(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testAspDotnetArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetArchetypeEditProject(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testAspDotnetArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetArchetypeEditApp(methodName, aspDotnetConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAspDotnetArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetArchetypeUpdateFeature(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testAspDotnetArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetArchetypeEditAppDesc(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAspDotnetArchetypeCodeAgainstCs()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeCodeAgainstCs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetCodeCs(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAspDotnetArchetypeCodeAgainstJs()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeCodeAgainstJs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetCodeJs(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAspDotnetArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetConfigurationServer(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	
	@Test
	public void testAspDotnetArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAspDotnetArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetBuild(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAspDotnetArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetDeploy(methodName, aspDotnetConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAspDotnetArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetUnitTest(methodName, aspDotnetConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAspDotnetArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetArchetypeOverAllPdfReport(methodName, aspDotnetConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAspDotnetArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAspDotnetArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.aspDotnetArchetypeDetailedPdfReport(methodName, aspDotnetConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

