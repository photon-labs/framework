package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.WordpressConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class WordpressTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static WordpressConstants wordpressConst;
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
		wordpressConst = new WordpressConstants();
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
	public void testWordpressArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.wordpressArchetypeCreate(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeEditProject(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testWordpressArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeEditApp(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeUpdateFeature(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testWordpressArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeEditAppDesc(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeCodeAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeCodeAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressCodeSource(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressConfigurationServer(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeConfigurationDatabase()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeConfigurationDatabase()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			createdb.CreateDatabase(wordpressConst.getConfigDbName());
			appBaseScreen.wordpressConfigurationDatabase(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWordpressArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWordpressArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressBuild(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWordpressArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressDeploy(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWordpressArchetypeUnitTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressUnitTest(methodName, wordpressConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWordpressArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeOverAllPdfReport(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWordpressArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWordpressArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.wordpressArchetypeDetailedPdfReport(methodName, wordpressConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}

}

