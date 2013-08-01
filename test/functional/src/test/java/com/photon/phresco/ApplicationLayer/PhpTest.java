package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhpConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class PhpTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static PhpConstants phpConst;
	private String methodName;
	private static String selectedBrowser;
	private static CreateDbsql createdb;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		phpConst = new PhpConstants();
		createdb = new CreateDbsql();
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
	public void testPhpArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.phpArchetypeCreate(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testPhpArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpArchetypeEditProject(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testPhpArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpArchetypeEditApp(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testPhpArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpArchetypeUpdateFeature(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testPhpArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpArchetypeEditAppDesc(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testPhpArchetypeCodeAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeCodeAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpCodeSource(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testPhpArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testPhpArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpConfigurationServer(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testPhpArchetypeConfigurationDatabase()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeConfigurationDatabase()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			createdb.CreateDatabase(phpConst.getConfigDbName());
			appBaseScreen.phpConfigurationDatabase(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testPhpArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testPhpArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpBuild(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testPhpArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpDeploy(methodName, phpConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testPhpArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpUnitTest(methodName, phpConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testPhpArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpArchetypeOverAllPdfReport(methodName, phpConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testPhpArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testPhpArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.phpArchetypeDetailedPdfReport(methodName, phpConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

