package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.SharepointConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class SharepointTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static SharepointConstants sharepointConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		sharepointConst = new SharepointConstants();
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
	public void testSharepointArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.sharePointArchetypeCreate(methodName, sharepointConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testSharepointArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeEditProject(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testSharepointArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeEditApp(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSharepointArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeUpdateFeature(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testSharepointArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeEditAppDesc(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSharepointArchetypeCodeSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeCodeSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeCodeSource(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSharepointArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSharepointArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointConfigurationServer(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	
	@Test
	public void testSharepointArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSharepointArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointBuild(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSharepointArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointDeploy(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSharepointArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointUnitTest(methodName, sharepointConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSharepointArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeOverAllPdfReport(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testSharepointArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeDetailedPdfReport(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

