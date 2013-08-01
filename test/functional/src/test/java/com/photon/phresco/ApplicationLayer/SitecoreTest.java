package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.SitecoreConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class SitecoreTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static SitecoreConstants sitecoreConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
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
			appBaseScreen = new AppScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
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
	public void testSitecoreArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreArchetypeEditProject(methodName, sitecoreConst);

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
	public void testSitecoreArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreArchetypeUpdateFeature(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testSitecoreArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreArchetypeEditAppDesc(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSitecoreArchetypeCodeAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeCodeAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreCodeSource(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSitecoreArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreConfigurationServer(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testSitecoreArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSitecoreArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreBuild(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSitecoreArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreDeploy(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSitecoreArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreUnitTest(methodName, sitecoreConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSitecoreArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreArchetypeOverAllPdfReport(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testSitecoreArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testSitecoreArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.sitecoreArchetypeDetailedPdfReport(methodName, sitecoreConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

