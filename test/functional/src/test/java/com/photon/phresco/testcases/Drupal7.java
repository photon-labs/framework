package com.photon.phresco.testcases;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import com.photon.phresco.Screens.AddApplicationScreen;
import com.photon.phresco.Screens.ApplicationsScreen;
import com.photon.phresco.Screens.BuildScreen;
import com.photon.phresco.Screens.ConfigScreen;
import com.photon.phresco.Screens.LoginScreen;
import com.photon.phresco.Screens.PhrescoWelcomePage;
import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;


public class Drupal7 extends TestCase {
	
	
	private Drupal7ConstantsXml  drupal=new Drupal7ConstantsXml();
	private PhrescoEnvConstants    phrscenv=new PhrescoEnvConstants();
	private PhrescoUiConstantsXml phrsc;
//	private PhrescoEnvConstants phrscenv;
//	private Drupal7ConstantsXml drupal;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	@Test
    public void testCreateDrupal7None() throws InterruptedException, IOException, Exception{
		
    		  
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
				addappscrn.createProjDRUPAL7None(drupal,methodName);
    }
	
	 @Test
	    public void testConfigDrupal7None() throws InterruptedException, IOException, Exception{
	    	    		
	    		 
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
					config.Drupal7NoneDatabaseConfig(drupal,methodName);
					config.Drupal7NoneServerConfig(drupal,methodName);
					
	    }
	 
	 @Test
	    public void testBuildDropal7None() throws InterruptedException, IOException, Exception{
	    	   		
		
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
	    		    phrescoHome.clickOnApplicationsTab(methodName);
	    			//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
	    		    BuildScreen Build=new BuildScreen(phrsc);
	    		    Build.Drupal7NoneBuild(drupal,methodName);
	    			
	    }
	 
	 @Test
	    public void testDeployDrupal7None_() throws InterruptedException, IOException, Exception{
	    	   		
		
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
	    		    Build.drupal7Nonedeploy(drupal,methodName);
	    			
	    }
	
	 @Test
	    
	    public void test_Create_Drupal7_EShop() throws InterruptedException, IOException, Exception{
	    	    		
	    		 
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
					methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
					System.out.println("methodName = " + methodName);
					addappscrn.createProjDRUPAL7(drupal,methodName);
	    }
	 
	 @Test
	    public void test_Config_Drupal7_Eshop() throws InterruptedException, IOException, Exception{
	    	    		
	    		 
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
					config.Drupal7EshopDatabaseConfig(drupal,methodName);
					config.Drupal7EshopServerConfig(drupal,methodName);
					
	    }
	 
	 @Test
	    public void test_Build_Dropal7_Eshop() throws InterruptedException, IOException, Exception{
	    	   		
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
	    		    Build.Drupal7EshopBuild(drupal,methodName);
	    			
	    }
	 
	 @Test
	    public void test_Deploy_Drupal7_Eshop() throws InterruptedException, IOException, Exception{
	  
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
	    		    Build.drupal7Eshopdeploy(drupal,methodName);
	    			
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
