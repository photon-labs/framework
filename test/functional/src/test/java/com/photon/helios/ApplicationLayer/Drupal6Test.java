package com.photon.helios.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.AppLayer.AppBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.Drupal6Constants;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class Drupal6Test {

	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppBaseScreen appBaseScreen;
	private static Drupal6Constants drupal6Const;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		drupal6Const = new Drupal6Constants();
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
	public void testDrupal6ArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.drupal6ArchetypeCreate(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeEditApp(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeUpdateApp(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ConfigurationCreate(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testdrupal6Build()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testdrupal6Build()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6Build(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

