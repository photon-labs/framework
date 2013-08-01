package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.NodeJsConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class NodeJsTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static NodeJsConstants nodeJsConst;
	private String methodName;
	private static String selectedBrowser;
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		nodeJsConst = new NodeJsConstants();
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
	public void testNodeJsArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.nodeJsArchetypeCreate(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testNodeJsArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsArchetypeEditProject(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 

	@Test
	public void testNodeJsArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsArchetypeEditApp(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testNodeJsArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsArchetypeUpdateFeature(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testNodeJsArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsArchetypeEditAppDesc(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testNodeJsArchetypeCodeAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeCodeAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsCodeSource(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testNodeJsArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testNodeJsArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsConfigurationServer(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testNodeJsArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testNodeJsArchetyperunAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.runAgainstSource(methodName, nodeJsConst);
		} catch (Exception t) {
			t.printStackTrace();
		}
	}
	
	@Test
	public void testNodeJsArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsArchetypeOverAllPdfReport(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testNodeJsArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testNodeJsArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.nodeJsArchetypeDetailedPdfReport(methodName, nodeJsConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}




}

