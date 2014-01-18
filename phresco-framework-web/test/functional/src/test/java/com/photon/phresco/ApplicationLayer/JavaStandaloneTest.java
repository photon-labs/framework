package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.JavaStandaloneConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class JavaStandaloneTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static JavaStandaloneConstants javaSAConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		javaSAConst = new JavaStandaloneConstants();
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
	public void testJavaStandaloneArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.javaStandaloneArchetypeCreate(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJavaStandaloneArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneArchetypeEditProject(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testJavaStandaloneArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneArchetypeEditApp(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	@Test
	public void testJavaStandaloneCodeSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneCodeSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneCodeSource(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test        
	public void testJavaStandaloneArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJavaStandaloneArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneArchetypeUpdateFeature(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJavaStandaloneArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneArchetypeEditAppDesc(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	@Test
	public void testJavaStandaloneBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneBuild(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testJavaStandaloneArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneArchetypeOverAllPdfReport(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testJavaStandaloneArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaStandaloneArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaStandaloneArchetypeDetailedPdfReport(methodName, javaSAConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

