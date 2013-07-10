package com.photon.helios.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.AppLayer.AppBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.SharepointConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class SharepointTest {

	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppBaseScreen appBaseScreen;
	private static SharepointConstants sharepointConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
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
			appBaseScreen = new AppBaseScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
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
	public void testSharepointArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointArchetypeUpdateApp(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSharepointConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharePointConfigurationCreate(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSharepointBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSharepointBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sharepointBuild(methodName, sharepointConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

