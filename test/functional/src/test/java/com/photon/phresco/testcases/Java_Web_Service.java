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

import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.JavaWebServConstantsXml;


import junit.framework.TestCase;


public class Java_Web_Service extends TestCase{
	
	private JavaWebServConstantsXml jws;
	
	private PhrescoEnvConstants phrscenv=new PhrescoEnvConstants();
	private PhrescoUiConstantsXml phrsc;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	 @Test
	    public void testCreateJWSNone() throws InterruptedException, IOException, Exception{
	    	    		
	    		jws=new JavaWebServConstantsXml();
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
				addappscrn.createProjJavaWebServiceNone(jws,methodName);
	    }
	 
	 @Test
	    public void testConfigJWSNone() throws InterruptedException, IOException, Exception{
	    	    		
	    		jws=new JavaWebServConstantsXml();
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
				config.JavaWebServiceNoneDatabaseConfig(jws,methodName);
				config.JavaWebServiceNoneServerConfig(jws,methodName);
	    }
	 
	 @Test
	    public void testBuildJWSNone() throws InterruptedException, IOException, Exception{
	    	    		
	    		jws=new JavaWebServConstantsXml();
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
 		    Build.JavaWebServiceBuildNone(jws,methodName);
	 }
	 
	 @Test
	 public void testDeployJWSNone() throws InterruptedException, IOException, Exception{
	 	    		
	 		jws=new JavaWebServConstantsXml();
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
			    Build.JavaWebServiceDeployNone(jws,methodName);
	 }
	 
	 @Test
	    public void testCreateJWSEShop() throws InterruptedException, IOException, Exception{
	    	    		
	    		jws=new JavaWebServConstantsXml();
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
				addappscrn.createProjJavaWebServiceEshop(jws,methodName);
	    }
	 
	 @Test
	    public void testConfigJWSEshop() throws InterruptedException, IOException, Exception{
	    	    		
	    		jws=new JavaWebServConstantsXml();
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
				config.JavaWebServiceEshopDatabaseConfig(jws,methodName);
				config.JavaWebServiceEshopServerConfig(jws,methodName);
	    }
	 
	 @Test
	    public void testBuildJWSEshop() throws InterruptedException, IOException, Exception{
	    	    		
	    		jws=new JavaWebServConstantsXml();
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
		    Build.JavaWebServiceBuildEshop(jws,methodName);
	 }
	 
	 @Test
	 public void testDeployJWSEshop() throws InterruptedException, IOException, Exception{
	 	    		
	 		jws=new JavaWebServConstantsXml();
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
			    Build.JavaWebServiceDeployEshop(jws,methodName);
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
