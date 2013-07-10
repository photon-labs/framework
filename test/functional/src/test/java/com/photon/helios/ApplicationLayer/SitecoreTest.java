package com.photon.helios.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.AppLayer.AppBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.SitecoreConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class SitecoreTest {

	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppBaseScreen appBaseScreen;
	private static SitecoreConstants sitecoreConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		sitecoreConst = new SitecoreConstants();
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
	public void testSitecoreArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.sitecoreArchetypeCreate(methodName, sitecoreConst);
			
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSitecoreArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreArchetypeEditApp(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSitecoreArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreArchetypeUpdateApp(methodName, sitecoreConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSitecoreConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreConfigurationCreate()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreConfigurationCreate(methodName, sitecoreConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSitecoreBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreBuild(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

