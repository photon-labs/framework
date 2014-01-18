package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.Drupal7Constants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class Drupal7Test {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static Drupal7Constants drupal7Const;
	private String methodName;
	private static String selectedBrowser;
	private static CreateDbsql createdb;
	
	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		drupal7Const = new Drupal7Constants();
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
	public void testDrupal7ArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ArchetypeEditProject(methodName, drupal7Const);

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
	public void testDrupal7ArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ArchetypeUpdateFeature(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testDrupal7ArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ArchetypeEditAppDesc(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ArchetypeCodeAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeCodeAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7CodeSource(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ConfigurationServer(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ArchetypeConfigurationDatabase()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeConfigurationDatabase()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			createdb.CreateDatabase(drupal7Const.getConfigDbName());
			appBaseScreen.drupal7ConfigurationDatabase(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal7ArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal7ArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7Build(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal7ArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7Deploy(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal7ArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7UnitTest(methodName, drupal7Const);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	
	@Test
	public void testDrupal7ArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ArchetypeOverAllPdfReport(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	
	@Test
	public void testDrupal7ArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal7ArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal7ArchetypeDetailedPdfReport(methodName, drupal7Const);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

