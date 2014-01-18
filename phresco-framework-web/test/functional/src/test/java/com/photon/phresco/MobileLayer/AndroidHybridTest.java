package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class AndroidHybridTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static AndroidHybridConstants androidHybridConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		androidHybridConst = new AndroidHybridConstants();
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
	public void testAndroidHybridArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.androidHybridArchetypeCreate(methodName, androidHybridConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidHybridArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidHybridArchetypeEditProject(methodName, androidHybridConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidHybridArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidHybridArchetypeEditApp(methodName, androidHybridConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidHybridArchetypeUpdateAppFeatures()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeUpdateAppFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidHybridArchetypeUpdateAppFeatures(methodName, androidHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidHybridArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidHybridArchetypeEditAppDesc(methodName, androidHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testAndroidHybridArchetypeCodeValidateSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeCodeValidateSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.codeValidateForSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}  
	
	@Test
	public void testAndroidHybridArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidHybridArchetypeBuild(methodName, androidHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidHybridArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidHybridArchetypeOverAllPdfReport(methodName, androidHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testAndroidHybridArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testAndroidHybridArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.androidHybridArchetypeDetailedPdfReport(methodName, androidHybridConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@AfterTest
	public  void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}





