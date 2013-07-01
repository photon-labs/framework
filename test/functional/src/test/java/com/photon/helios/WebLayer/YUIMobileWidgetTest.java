package com.photon.helios.WebLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.Weblayer.WebBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.YUIMobileWidgetConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class YUIMobileWidgetTest {
	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static WebBaseScreen webBaseScreen;
	private static YUIMobileWidgetConstants yuiMobConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		yuiMobConst = new YUIMobileWidgetConstants();
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
	public void testYUIMobileWidgetArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYUIMobileWidgetArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.loginPage(methodName);
			webBaseScreen.yuiMobileWidgetArchetypeCreate(methodName, yuiMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testYUIMobileWidgetArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYUIMobileWidgetArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetArchetypeEditApp(methodName, yuiMobConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testYUIMobileArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYUIMobileArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.yuiMobileWidgetArchetypeUpdateApp(methodName, yuiMobConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@AfterTest
	public  void tearDown() {
		webBaseScreen.closeBrowser();
	}

}

