package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AndroidNativeConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class AndroidNativeTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static AndroidNativeConstants androidNativeConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		androidNativeConst = new AndroidNativeConstants();
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
	public void testAndroidNativeArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.androidNativeArchetypeCreate(methodName, androidNativeConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidNativeArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeEditProject(methodName, androidNativeConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidNativeArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeEditApp(methodName, androidNativeConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidNativeArchetypeUpdateAppFeatures()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeUpdateAppFeatures(methodName, androidNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidNativeArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeEditAppDesc(methodName, androidNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidNativeArchetypeCodeValidateSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeCodeValidateSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.codeValidateForSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidNativeArchetypeCodeValidateFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeCodeValidateFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidNativeArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeBuild(methodName, androidNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidNativeArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeUnitTest(methodName, androidNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidNativeArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeOverAllPdfReport(methodName, androidNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidNativeArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidNativeArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidNativeArchetypeDetailedPdfReport(methodName, androidNativeConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	

	@AfterTest
	public  void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}


