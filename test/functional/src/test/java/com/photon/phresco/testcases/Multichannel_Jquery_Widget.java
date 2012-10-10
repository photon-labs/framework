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
import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.JqueryWidgetConstants;
import com.photon.phresco.uiconstants.YuiConstantsXml;

import junit.framework.TestCase;


public class Multichannel_Jquery_Widget extends TestCase{

	private PhrescoEnvConstants phrscenv=new PhrescoEnvConstants();

	private PhrescoUiConstantsXml phrsc;
	private JqueryWidgetConstants jquerywidg;
	private YuiConstantsXml YuiConst;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	 @Test
	   public void test_Create_JqueryWidget_None() throws InterruptedException, IOException, Exception{
				    	    		
		               jquerywidg=new JqueryWidgetConstants();
	               
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
					addappscrn.createProjJqueryWidgetNone(jquerywidg,methodName);
	   }
	 
	 @Test
	   public void test_Config_JqueryWidget_None() throws InterruptedException, IOException, Exception{
	   	    		
	   	  jquerywidg=new JqueryWidgetConstants();
	   	  YuiConst = new YuiConstantsXml();
				
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
				config.HTML5JqueryWidgetNoneServerConfig(jquerywidg,methodName);
				config.HTML5WidgetNoneWebServiceConfig(YuiConst,methodName);
	   }	
	 
	 @Test
	   public void test_Build_JqueryWidget_None() throws InterruptedException, IOException, Exception{
	   	    		
	   	  jquerywidg=new JqueryWidgetConstants();
	   	  YuiConst = new YuiConstantsXml();
				
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
  		    Build.JqueryWidgetNoneBuild(jquerywidg,methodName);
	   }	
	 
	 @Test
	   public void test_Deploy_JqueryWidget_None() throws InterruptedException, IOException, Exception{
	   	    		
	   	  jquerywidg=new JqueryWidgetConstants();
	   	  YuiConst = new YuiConstantsXml();
				
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
			    Build.JqueryWidgetNonedeploy(jquerywidg,methodName);
	   }
	 
	 @Test
	   public void test_Create_JqueryWidget_EShop() throws InterruptedException, IOException, Exception{
				    	    		
		               jquerywidg=new JqueryWidgetConstants();
	               
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
					addappscrn.createProjJqueryWidget(jquerywidg,methodName);
	   }
	 
	 @Test
	   public void test_Config_JqueryWidget_Eshop() throws InterruptedException, IOException, Exception{
	   	    		
	   	  jquerywidg=new JqueryWidgetConstants();
	   	  YuiConst = new YuiConstantsXml();
				
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
				config.HTML5JqueryWidgetEshopServerConfig(jquerywidg,methodName);
				config.HTML5WidgetEshopWebServiceConfig(YuiConst,methodName);
	   }		
	 
	 @Test
	   public void test_Build_JqueryWidget_Eshop() throws InterruptedException, IOException, Exception{
	   	    		
	   	  jquerywidg=new JqueryWidgetConstants();
	   	  YuiConst = new YuiConstantsXml();
				
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
		    Build.JqueryWidgetEshopBuild(jquerywidg,methodName);
	   }	
	 
	 @Test
	   public void test_Deploy_JqueryWidget_Eshop() throws InterruptedException, IOException, Exception{
	   	    		
	   	  jquerywidg=new JqueryWidgetConstants();
	   	  YuiConst = new YuiConstantsXml();
				
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
			    Build.JqueryWidgetEshopdeploy(jquerywidg,methodName);
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
