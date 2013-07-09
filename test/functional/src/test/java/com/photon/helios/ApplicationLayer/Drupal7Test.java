package com.photon.helios.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.AppLayer.AppBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.Drupal7Constants;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class Drupal7Test {

	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppBaseScreen appBaseScreen;
	private static Drupal7Constants drupal7Const;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		drupal7Const = new Drupal7Constants();
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
	public void testDrupal7ArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.drupal7ArchetypeCreate(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ArchetypeEditApp(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ArchetypeUpdateApp(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ConfigurationCreate()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ConfigurationCreate(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testdrupal7Build()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testdrupal7Build()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7Build(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

