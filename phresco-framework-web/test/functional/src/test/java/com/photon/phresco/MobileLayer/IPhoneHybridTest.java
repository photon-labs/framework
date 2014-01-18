package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.IPhoneHybridConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class IPhoneHybridTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static IPhoneHybridConstants iPhoneHybridConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		iPhoneHybridConst = new IPhoneHybridConstants();
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
	public void testIPhoneHybridArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.iPhoneHybridArchetypeCreate(methodName, iPhoneHybridConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneHybridArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneHybridArchetypeEditProject(methodName, iPhoneHybridConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneHybridArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneHybridArchetypeEditApp(methodName, iPhoneHybridConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneHybridArchetypeUpdateFeatures()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeUpdateFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneHybridArchetypeUpdateFeatures(methodName, iPhoneHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneHybridArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeEditDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneHybridArchetypeEditAppDesc(methodName, iPhoneHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneHybridArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneHybridArchetypeUnitTest(methodName, iPhoneHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneHybridArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneHybridArchetypeOverAllPdfReport(methodName, iPhoneHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testIPhoneHybridArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testIPhoneHybridArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.iPhoneHybridArchetypeDetailedPdfReport(methodName, iPhoneHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@AfterTest
	public  void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}





