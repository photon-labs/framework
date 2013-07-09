package com.photon.helios.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.AppLayer.AppBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.WordpressConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class WordpressTest {

	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppBaseScreen appBaseScreen;
	private static WordpressConstants wordpressConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		wordpressConst = new WordpressConstants();
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
	public void testWordpressArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {
			System.out.println("---------testWordpressArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.wordpressArchetypeCreate(methodName, wordpressConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeEditApp(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeUpdateApp(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testwordpressConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testwordpressConfigurationCreate()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressConfigurationCreate(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWordpressBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressBuild(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}

}

