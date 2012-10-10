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

import com.photon.phresco.uiconstants.DotNetConstants;
import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import junit.framework.TestCase;

public class ASP_DotNET extends TestCase {
	
	private DotNetConstants DotNetConst;
	private PhrescoEnvConstants phrscenv;
	private PhrescoUiConstantsXml phrsc;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	 @Test  
	  public void test_Create_DotNet_None() throws InterruptedException, IOException, Exception{
			
		        DotNetConst = new DotNetConstants();
				
		        phrscenv=new PhrescoEnvConstants();
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
				addappscrn.createProjDotNetNone(DotNetConst,methodName);
	    }	
	 

		@Test
	    public void test_Config_DotNet_None() throws InterruptedException, IOException, Exception{
	    	    		
			DotNetConst = new DotNetConstants();
			 phrscenv=new PhrescoEnvConstants();
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
				
				ConfigScreen config = new ConfigScreen();
				config.DotNetNoneServerConfig(DotNetConst,methodName);
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
