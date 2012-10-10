package com.photon.phresco.testcases;

import java.io.IOException;
import org.junit.Test;
import com.photon.phresco.Screens.AddApplicationScreen;
import com.photon.phresco.Screens.ApplicationsScreen;
import com.photon.phresco.Screens.BuildScreen;
import com.photon.phresco.Screens.ConfigScreen;
import com.photon.phresco.Screens.LoginScreen;
import com.photon.phresco.Screens.PhrescoWelcomePage;
import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.uiconstants.JavaStandaloneXml;
import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.WordPressConstants;

import junit.framework.TestCase;

public class JavaStandalone extends TestCase{
	
	private JavaStandaloneXml javastandalone;
	
	private PhrescoEnvConstants phrscenv=new PhrescoEnvConstants();

	private PhrescoUiConstantsXml phrsc;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;

	
	@Test  
     public void test_Create_Javastandalone_None() throws InterruptedException, IOException, Exception{
		
		 javastandalone = new JavaStandaloneXml();
			
		 String serverURL = phrscenv.PROTOCOL + "://" + phrscenv.HOST + ":"
			+ phrscenv.PORT + "/";
	browserAppends = "*" + phrscenv.BROWSER;
	assertNotNull("Browser name should not be null", browserAppends);
	SELENIUM_PORT = Integer.parseInt(phrscenv.PORT);
	assertNotNull("selenium-port number should not be null",
			SELENIUM_PORT);
	wel = new WelcomeScreen(phrscenv.HOST,
			SELENIUM_PORT, browserAppends, serverURL, phrscenv.SPEED,
			phrscenv.CONTEXT);
			assertNotNull(wel);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProj_JavastandaloneNone(javastandalone,methodName);
  }
	  @Test
	    public void test_Build_Javastandalone_None() throws InterruptedException, IOException, Exception{
	    	   		
		  javastandalone = new JavaStandaloneXml();
		  String serverURL = phrscenv.PROTOCOL + "://" + phrscenv.HOST + ":"
			+ phrscenv.PORT + "/";
	browserAppends = "*" + phrscenv.BROWSER;
	assertNotNull("Browser name should not be null", browserAppends);
	SELENIUM_PORT = Integer.parseInt(phrscenv.PORT);
	assertNotNull("selenium-port number should not be null",
			SELENIUM_PORT);
	wel = new WelcomeScreen(phrscenv.HOST,
			SELENIUM_PORT, browserAppends, serverURL, phrscenv.SPEED,
			phrscenv.CONTEXT);
	    			assertNotNull(wel);
	    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	    			System.out.println("methodName = " + methodName);
	    			loginObject = new LoginScreen(phrsc);
	    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	    			                   phrescoHome.goToPhrescoHomePage(methodName);
	    		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
	    			//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
	    		    BuildScreen Build=new BuildScreen(phrsc);
	    		    Build.javastandaloneBuild(javastandalone,methodName);
	    			
	    }
	 
	  public void setUp() throws Exception {
			phrsc = new PhrescoUiConstantsXml();
		}

		public void tearDown() {
			clean();
		}

		private void clean() {
			wel.closeBrowser();
		}

	 
}
