package com.photon.phresco.testcases;

import static org.junit.Assert.assertNotNull;

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
import com.photon.phresco.uiconstants.Drupal6ConstantsXml;
import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

import junit.framework.TestCase;

public class Drupal6 extends TestCase{
	
	private PhrescoEnvConstants phrscenv;
	private PhrescoUiConstantsXml phrsc;
	private Drupal6ConstantsXml drupal6;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	 @Test
	    public void testCreateDrupal6None() throws InterruptedException, IOException, Exception{
	    	    		
	    		   drupal6=new Drupal6ConstantsXml();
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
					addappscrn.createProjDRUPAL6(drupal6,methodName);
	    }
	 
	 @Test
	    public void testConfigDrupal6None() throws InterruptedException, IOException, Exception{
	    	    	
	    		   //drupal=new Drupal7ConstantsXml();
	    		   drupal6=new Drupal6ConstantsXml();
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
				    CreateDbsql dbsql = new CreateDbsql(methodName);
					ConfigScreen config = new ConfigScreen();
					config.Drupal6NoneDatabaseConfig(drupal6,methodName);
					config.Drupal6NoneServerConfig(drupal6,methodName);
					
	    }
	 
	 @Test
	    public void testBuildDrupal6None() throws InterruptedException, IOException, Exception{
	    	    		
	    		   //drupal=new Drupal7ConstantsXml();
	    		   drupal6=new Drupal6ConstantsXml();
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
				    BuildScreen Build=new BuildScreen(phrsc);
	    		    Build.Drupal6NoneBuild(drupal6,methodName);
					
					
	    }
	 
	 @Test
	    public void testDeployDrupal6None() throws InterruptedException, IOException, Exception{
	    	    		
	    		   //drupal=new Drupal7ConstantsXml();
	    		   drupal6=new Drupal6ConstantsXml();
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
				    BuildScreen Build=new BuildScreen(phrsc);
	    		    Build.Drupal6Nonedeploy(drupal6,methodName);
					
					
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
