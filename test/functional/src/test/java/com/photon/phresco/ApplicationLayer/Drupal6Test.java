package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.Drupal6Constants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class Drupal6Test {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static Drupal6Constants drupal6Const;
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
		drupal6Const = new Drupal6Constants();
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
	public void testDrupal6ArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.drupal6ArchetypeCreate(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeEditProject(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testDrupal6ArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeEditApp(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeUpdateFeature(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testDrupal6ArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeEditAppDesc(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeCodeAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeCodeAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6CodeSource(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ConfigurationServer(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeConfigurationDatabase()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeConfigurationDatabase()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			createdb.CreateDatabase(drupal6Const.getConfigDbName());
			appBaseScreen.drupal6ConfigurationDatabase(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testDrupal6ArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal6ArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6Build(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal6ArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6Deploy(methodName, drupal6Const);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal6ArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6UnitTest(methodName, drupal6Const);
			
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal6ArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeOverAllPdfReport(methodName, drupal6Const);
			
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testDrupal6ArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testDrupal6ArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.drupal6ArchetypeDetailedPdfReport(methodName, drupal6Const);
			
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

