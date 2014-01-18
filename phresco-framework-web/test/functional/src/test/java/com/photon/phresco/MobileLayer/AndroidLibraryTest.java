package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AndroidLibraryConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class AndroidLibraryTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static AndroidLibraryConstants androidLibraryConst;
	private String methodName;

	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		androidLibraryConst = new AndroidLibraryConstants();
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
	public void testAndroidLibraryArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.androidLibraryArchetypeCreate(methodName, androidLibraryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidLibraryArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidLibraryArchetypeEditProject(methodName, androidLibraryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidLibraryArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidLibraryArchetypeEditApp(methodName, androidLibraryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidLibraryArchetypeUpdateAppFeatures()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeUpdateAppFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidLibraryArchetypeUpdateAppFeatures(methodName, androidLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidLibraryArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidLibraryArchetypeEditAppDesc(methodName, androidLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	
	@Test
	public void testAndroidLibraryArchetypeCodeValidateSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeCodeValidateSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.codeValidateForSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	
	@Test
	public void testAndroidLibraryArchetypeCodeValidateFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeCodeValidateFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidLibraryArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidLibraryArchetypeBuild(methodName, androidLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidLibraryArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidLibraryArchetypeOverAllPdfReport(methodName, androidLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidLibraryArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidLibraryArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidLibraryArchetypeDetailedPdfReport(methodName, androidLibraryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public  void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}







