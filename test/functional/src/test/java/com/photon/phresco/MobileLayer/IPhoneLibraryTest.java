package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.IPhoneLibraryConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class IPhoneLibraryTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static IPhoneLibraryConstants iPhoneLibraryConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		iPhoneLibraryConst = new IPhoneLibraryConstants();
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
	public void testIPhoneLibraryArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneLibraryArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.iPhoneLibraryArchetypeCreate(methodName, iPhoneLibraryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneLibraryArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneLibraryArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneLibraryArchetypeEditProject(methodName, iPhoneLibraryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneLibraryArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneLibraryArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneLibraryArchetypeEditApp(methodName, iPhoneLibraryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneLibraryArchetypeUpdateAppFeatures()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneLibraryArchetypeUpdateAppFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneLibraryArchetypeUpdateAppFeatures(methodName, iPhoneLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneLibraryArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneLibraryArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneLibraryArchetypeEditAppDesc(methodName, iPhoneLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneLibraryArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneLibraryArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneLibraryArchetypeOverAllPdfReport(methodName, iPhoneLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneLibraryArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneLibraryArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneLibraryArchetypeDetailedPdfReport(methodName, iPhoneLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@AfterTest
	public void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}
