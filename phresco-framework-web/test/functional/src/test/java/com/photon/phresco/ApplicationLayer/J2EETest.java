package com.photon.phresco.ApplicationLayer;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.AppScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.J2EEConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class J2EETest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData phrescoData;
	private static PhrescoUiConstants phrescoUiConst;
	private static phresco_env_config phrscEnv;
	private static AppScreen appBaseScreen;
	private static J2EEConstants j2EEConst;
	private String methodName;
	private static String selectedBrowser;
	

	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		phrescoUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		phrescoData = new PhrescoframeworkData();
		j2EEConst = new J2EEConstants();
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
			appBaseScreen = new AppScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,phrescoUiConst,userInfoConst,phrescoData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}


	@Test
	public void testJ2EEArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.loginPage(methodName);
			appBaseScreen.j2EEArchetypeCreate(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJ2EEArchetypeEditProject()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEArchetypeEditProject(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testJ2EEArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEArchetypeEditApp(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJ2EEArchetypeUpdateFeature()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeUpdateFeature()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEArchetypeUpdateFeature(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 

	@Test
	public void testJ2EEArchetypeEditAppDesc()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEArchetypeEditAppDesc(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJ2EEArchetypeCodeAgainstSourceJs()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeCodeAgainstSourceJs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EECodeSourceJs(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJ2EEArchetypeCodeAgainstSourceJava()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeCodeAgainstSourceJava()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EECodeSourceJava(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJ2EEArchetypeCodeAgainstSourceJsp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeCodeAgainstSourceJsp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EECodeSourceJsp(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJ2EEArchetypeCodeAgainstSourceHtml()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeCodeAgainstSourceHtml()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EECodeSourceHtml(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJ2EEArchetypeCodeAgainstFunctionalTest()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeCodeAgainstFunctionalTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.codeValidateFunctionalTest(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testJ2EEArchetypeConfigurationServer()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeConfigurationServer()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEConfigurationServer(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	
	@Test
	public void testJ2EEArchetypeCloneEnvironment()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeCloneEnvironment()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.cloneEnvironment(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testJ2EEArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEBuild(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testJ2EEArchetypeDeploy()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeDeploy()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEDeploy(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testJ2EEArchetypeUnitTestAgainstJava()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeUnitTestAgainstJava()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEUnitTestAgainstJava(methodName, j2EEConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testJ2EEArchetypeUnitTestAgainstJs()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeUnitTestAgainstJs()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEUnitTestAgainstJs(methodName, j2EEConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testJ2EEArchetypeOverAllPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEArchetypeOverAllPdfReport(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 
	
	@Test
	public void testJ2EEArchetypeDetailedPdfReport()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testJ2EEArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			appBaseScreen.j2EEArchetypeDetailedPdfReport(methodName, j2EEConst);

		} catch (Exception t) {
			t.printStackTrace();
		}
	} 

	@AfterTest
	public  void tearDown() {
		appBaseScreen.closeBrowser();
	}



}

