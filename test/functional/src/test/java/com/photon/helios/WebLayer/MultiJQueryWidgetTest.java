package com.photon.helios.WebLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.Weblayer.WebBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.MultiJQueryWidgetConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class MultiJQueryWidgetTest {
	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static WebBaseScreen webBaseScreen;
	private static MultiJQueryWidgetConstants multiJQueryConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
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
			webBaseScreen = new WebBaseScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
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
	public void testMultiJQueryArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiJQueryArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			webBaseScreen.multiJQueryWidgetArchetypeUpdateApp(methodName, multiJQueryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testMultiJQueryWidgetConfigurationCreate()
			throws InterruptedException, IOException, Exception {
				try {

					System.out.println("---------testMultiJQueryWidgetConfigurationCreate()-------------");
					methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
					webBaseScreen.multiJQueryWidgetConfigurationCreate(methodName, multiJQueryConst);

				} catch (Exception t) {
					t.printStackTrace();

				}
			} 
	
	public void testMultiJQueryWidgetGenerateBuild()
			throws InterruptedException, IOException, Exception {
				try {

					System.out.println("---------testMultiJQueryWidgetGenerateBuild()-------------");
					methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
					webBaseScreen.multiJQueryWidgetGenerateBuild(methodName, multiJQueryConst);

				} catch (Exception t) {
					t.printStackTrace();

				}
			} 
	
	@AfterTest
	public  void tearDown() {
		webBaseScreen.closeBrowser();
	}

}
	
