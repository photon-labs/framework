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
import com.photon.phresco.uiconstants.YuiConstantsXml;

import junit.framework.TestCase;

public class Multichannel_YUI_Widget extends TestCase {
	
	private YuiConstantsXml YuiConst;
	
	private PhrescoEnvConstants phrscenv;
	private PhrescoUiConstantsXml phrsc;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	 @Test
	    public void test_Create_YuiWidget_None() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				addappscrn.createProjHTML5WidgNone(YuiConst,methodName);
	    }	
	 
	 
	    @Test
	    public void test_Config_YuiWidget_None() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				loginObject = new LoginScreen(phrsc);
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	            phrescoHome.goToPhrescoHomePage(methodName);
				ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
				
				ConfigScreen config = new ConfigScreen();
				config.HTML5WidgetNoneServerConfig(YuiConst,methodName);
				config.HTML5WidgetNoneWebServiceConfig(YuiConst,methodName);
	    }	
	    
	    @Test
	    public void test_Build_YuiWidget_None() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				loginObject = new LoginScreen(phrsc);
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	            phrescoHome.goToPhrescoHomePage(methodName);
				ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
				BuildScreen Build=new BuildScreen(phrsc);
    		    Build.HTML5WidgetNoneBuild(YuiConst,methodName);
	    }	
	    
	    @Test
	    public void test_Deploy_YuiWidget_None() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				loginObject = new LoginScreen(phrsc);
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	            phrescoHome.goToPhrescoHomePage(methodName);
				ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
				BuildScreen Build=new BuildScreen(phrsc);
			    Build.HTML5WidgetNonedeploy(YuiConst,methodName);
	    }	
	    
	    @Test
	    public void test_Create_YuiWidget_EShop() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				addappscrn.createProjHTML5WidgEshop(YuiConst,methodName);
	    }
	    
	    @Test
	    public void test_Config_YuiWidget_Eshop() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				loginObject = new LoginScreen(phrsc);
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	            phrescoHome.goToPhrescoHomePage(methodName);
				ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
				
				ConfigScreen config = new ConfigScreen();
				config.HTML5WidgetEshopServerConfig(YuiConst,methodName);
				config.HTML5WidgetEshopWebServiceConfig(YuiConst,methodName);
	    }	
	    
	    @Test
	    public void test_Build_YuiWidget_Eshop() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				loginObject = new LoginScreen(phrsc);
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	            phrescoHome.goToPhrescoHomePage(methodName);
				ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
				BuildScreen Build=new BuildScreen(phrsc);
 		    Build.HTML5WidgetEshopBuild(YuiConst,methodName);
	    }	
	 
	    @Test
	    public void test_Deploy_YuiWidget_Eshop() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
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
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				loginObject = new LoginScreen(phrsc);
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	            phrescoHome.goToPhrescoHomePage(methodName);
				ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
				BuildScreen Build=new BuildScreen(phrsc);
			    Build.HTML5WidgetEshopdeploy(YuiConst,methodName);
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
