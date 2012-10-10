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
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.YuiConstantsXml;

import junit.framework.TestCase;


public class YUI_Mobile_Widget extends TestCase{
	
	private PhrescoEnvConstants phrscenv;
	private PhrescoUiConstantsXml phrsc;
	private MobWidgetConstantsXml mobwidg;
	private YuiConstantsXml YuiConst;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	
	 @Test
	    public void test_Create_MobWidget_None() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
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
					addappscrn.createProjHTML5MobileWidgetNone(mobwidg,methodName);
	    }
	 
	 @Test
	    public void test_Config_MobWidget_None() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
	                YuiConst=new YuiConstantsXml();
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
					config.MobilewidgetNoneServerConfig(mobwidg,methodName);
					config.HTML5WidgetNoneWebServiceConfig(YuiConst,methodName);
	    }
	 
	 @Test
	    public void test_Build_MobWidget_None() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
	                YuiConst=new YuiConstantsXml();
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
		    		    Build.MobilewidgetNoneBuild(mobwidg,methodName);
	    }
	
	 @Test
	    public void test_Deploy_MobWidget_None() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
	                YuiConst=new YuiConstantsXml();
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
		    		    Build.MobilewidgetNonedeploy(mobwidg,methodName);
	    }
	 
	 @Test
	    public void test_Create_MobWidget_EShop() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
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
					addappscrn.createProjHTML5MobileWidget(mobwidg,methodName);
	    }
	 
	 @Test
	    public void test_Config_MobWidget_Eshop() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
	                YuiConst=new YuiConstantsXml();
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
					config.MobilewidgetEshopServerConfig(mobwidg,methodName);
					config.HTML5WidgetEshopWebServiceConfig(YuiConst,methodName);
	    }
	 
	 @Test
	    public void test_Build_MobWidget_Eshop() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
	                YuiConst=new YuiConstantsXml();
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
		    		    Build.MobilewidgetEshopBuild(mobwidg,methodName);
	    }
	 
	 @Test
	    public void test_Deploy_MobWidget_Eshop() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
	                YuiConst=new YuiConstantsXml();
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
		    		    Build.MobilewidgetEshopdeploy(mobwidg,methodName);
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
