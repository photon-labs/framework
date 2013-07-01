package com.photon.helios.WebLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.Weblayer.WebBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.JQueryMobileWidgetConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class JQueryMobileWidgetTest {
	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static WebBaseScreen webBaseScreen;
	private static JQueryMobileWidgetConstants jQueryMobConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
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
			webBaseScreen = new WebBaseScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}
	

	@Test
	public void testJQueryMobileWidgetArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobileWidgetArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.jQueryMobWidgetArchetypeCreate(methodName, jQueryMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJQueryMobileWidgetArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobileWidgetArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeEditApp(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJQueryMobileArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJQueryMobileArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.jQueryMobWidgetArchetypeUpdateApp(methodName, jQueryMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@AfterTest
	public  void tearDown() {
		webBaseScreen.closeBrowser();
	}

}

