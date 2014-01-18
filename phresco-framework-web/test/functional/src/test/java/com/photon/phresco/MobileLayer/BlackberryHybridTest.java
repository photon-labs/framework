package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.BlackBerryHybridConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class BlackberryHybridTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static BlackBerryHybridConstants blackBerryConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		blackBerryConst = new BlackBerryHybridConstants();
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
	public void testBlackBerryHybridArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.blackBerryHybridArchetypeCreate(methodName, blackBerryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testBlackBerryHybridArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.blackBerryHybridArchetypeEditProject(methodName, blackBerryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	
	@Test
	public void testBlackBerryHybridArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.blackBerryHybridArchetypeEditApp(methodName, blackBerryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testBlackBerryHybridArchetypeUpdateAppFeatures()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeUpdateAppFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.blackBerryHybridArchetypeUpdateAppFeatures(methodName, blackBerryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testBlackBerryHybridArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.blackBerryHybridArchetypeEditAppDesc(methodName, blackBerryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testBlackBerryHybridArchetypeCodeValidateSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeCodeValidateSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.codeValidateForSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	
	
	@Test
	public void testBlackBerryHybridArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.blackBerryHybridArchetypeBuild(methodName, blackBerryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testBlackBerryHybridArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.blackBerryHybridArchetypeOverAllPdfReport(methodName, blackBerryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testBlackBerryHybridArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.blackBerryHybridArchetypeDetailedPdfReport(methodName, blackBerryConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	

	@AfterTest
	public void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}
