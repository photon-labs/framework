package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.IPhoneNativeConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class IPhoneNativeTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static IPhoneNativeConstants iPhoneNativeConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		iPhoneNativeConst = new IPhoneNativeConstants();
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
			mobBaseScreen = new MobScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}


	@Test
	public void testIPhoneNativeArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.iPhoneNativeArchetypeCreate(methodName, iPhoneNativeConst);	
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneNativeArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneNativeArchetypeEditProject(methodName, iPhoneNativeConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneNativeArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneNativeArchetypeEditApp(methodName, iPhoneNativeConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneNativeArchetypeUpdateFeatures()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneNativeArchetypeUpdateFeatures(methodName, iPhoneNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneNativeArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeUpdateAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneNativeArchetypeEditAppDesc(methodName, iPhoneNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneNativeArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneNativeArchetypeUnitTest(methodName, iPhoneNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	
	@Test
	public void testIPhoneNativeArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneNativeArchetypeOverAllPdfReport(methodName, iPhoneNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneNativeArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneNativeArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneNativeArchetypeDetailedPdfReport(methodName, iPhoneNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@AfterTest
	public  void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}


