package com.photon.helios.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.AppLayer.AppBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.JavaWebserviceConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class JavaWebserviceTest {

	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppBaseScreen appBaseScreen;
	private static JavaWebserviceConstants javaWSConst;
	private String methodName;
	private static String selectedBrowser;
	

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		javaWSConst = new JavaWebserviceConstants();
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
			appBaseScreen = new AppBaseScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}


	@Test
	public void testJavaWebserviceArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaWebserviceArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.javaWSArchetypeCreate(methodName, javaWSConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJavaWebserviceArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaWebserviceArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaWSArchetypeEditApp(methodName, javaWSConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJavaWebserviceArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaWebserviceArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaWSArchetypeUpdateApp(methodName, javaWSConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJavaWSConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJavaWSConfigurationCreate()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaWSConfigurationCreate(methodName, javaWSConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testjavaWSBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testjavaWSBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.javaWSBuild(methodName, javaWSConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}


	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

