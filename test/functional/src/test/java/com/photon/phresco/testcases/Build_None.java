package com.photon.phresco.testcases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
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
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
import com.photon.phresco.uiconstants.JqueryWidgetConstants;
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
import com.photon.phresco.uiconstants.NodeJSConstantsXml;
import com.photon.phresco.uiconstants.PhpConstantsXml;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.WordPressConstants;
import com.photon.phresco.uiconstants.YuiConstantsXml;
import com.photon.phresco.uiconstants.iPhoneConstantsXml;

import junit.framework.TestCase;

public class Build_None {
	
	private PhrescoUiConstantsXml phrsc;
	private Drupal7ConstantsXml drupal;
	private Drupal6ConstantsXml drupal6;
	private WordPressConstants WordpressConst;
	private MobWidgetConstantsXml mobwidg;
	private JqueryWidgetConstants jquerywidg;
	private iPhoneConstantsXml iPhone;
	private JavaWebServConstantsXml jws;
	private PhpConstantsXml phpconst;
	private SharepointConstantsXml spconst;
	private NodeJSConstantsXml nodejsconst;
	private YuiConstantsXml YuiConst;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
	
	 
	 @Test
	    public void test_Build_Dropal7_None() throws InterruptedException, IOException, Exception{
	    	   		
		 drupal=new Drupal7ConstantsXml();
	    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
	    					+ phrsc.PORT + "/";
	    			browserAppends = "*" + phrsc.BROWSER;
	    			assertNotNull("Browser name should not be null", browserAppends);
	    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
	    			assertNotNull("selenium-port number should not be null",
	    					SELENIUM_PORT);
	    			wel = new WelcomeScreen(phrsc.HOST,
	    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
	    					phrsc.CONTEXT);
	    			assertNotNull(wel);
	    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	    			System.out.println("methodName = " + methodName);
	    			loginObject = new LoginScreen(phrsc);
	    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	    			                   phrescoHome.goToPhrescoHomePage(methodName);
	    		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
	    			//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
	    		    BuildScreen Build=new BuildScreen(phrsc);
	    		    Build.Drupal7NoneBuild(drupal,methodName);
	    			
	    }
	 
	 @Test
	    public void test_Build_Drupal6_None() throws InterruptedException, IOException, Exception{
	    	    		
	    		   //drupal=new Drupal7ConstantsXml();
	    		   drupal6=new Drupal6ConstantsXml();
	    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
	    					+ phrsc.PORT + "/";
	    			browserAppends = "*" + phrsc.BROWSER;
	    			assertNotNull("Browser name should not be null", browserAppends);
	    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
	    			assertNotNull("selenium-port number should not be null",
	    					SELENIUM_PORT);
	    			wel = new WelcomeScreen(phrsc.HOST,
	    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
	    					phrsc.CONTEXT);
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
	    public void test_Build_MobWidget_None() throws InterruptedException, IOException, Exception{
				    	    		
	                mobwidg=new MobWidgetConstantsXml();
	                YuiConst=new YuiConstantsXml();
		    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
		    					+ phrsc.PORT + "/";
		    			browserAppends = "*" + phrsc.BROWSER;
		    			assertNotNull("Browser name should not be null", browserAppends);
		    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
		    			assertNotNull("selenium-port number should not be null",
		    					SELENIUM_PORT);
		    			wel = new WelcomeScreen(phrsc.HOST,
		    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
		    					phrsc.CONTEXT);
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
	   public void test_Build_JqueryWidget_None() throws InterruptedException, IOException, Exception{
	   	    		
	   	  jquerywidg=new JqueryWidgetConstants();
	   	  YuiConst = new YuiConstantsXml();
				
				String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
						+ phrsc.PORT + "/";
				browserAppends = "*" + phrsc.BROWSER;
				assertNotNull("Browser name should not be null", browserAppends);
				SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
				assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
				wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
						serverURL, phrsc.SPEED, phrsc.CONTEXT);
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
	    public void test_Build_JWS_None() throws InterruptedException, IOException, Exception{
	    	    		
	    		jws=new JavaWebServConstantsXml();
	    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
	    					+ phrsc.PORT + "/";
	    			browserAppends = "*" + phrsc.BROWSER;
	    			assertNotNull("Browser name should not be null", browserAppends);
	    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
	    			assertNotNull("selenium-port number should not be null",
	    					SELENIUM_PORT);
	    			wel = new WelcomeScreen(phrsc.HOST,
	    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
	    					phrsc.CONTEXT);
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
	    public void test_Build_PHP_None() throws InterruptedException, IOException, Exception{
	    	   		
	    	     phpconst=new PhpConstantsXml();
	    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
	    					+ phrsc.PORT + "/";
	    			browserAppends = "*" + phrsc.BROWSER;
	    			assertNotNull("Browser name should not be null", browserAppends);
	    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
	    			assertNotNull("selenium-port number should not be null",
	    					SELENIUM_PORT);
	    			wel = new WelcomeScreen(phrsc.HOST,
	    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
	    					phrsc.CONTEXT);
	    			assertNotNull(wel);
	    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	    			System.out.println("methodName = " + methodName);
	    			loginObject = new LoginScreen(phrsc);
	    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	    			                   phrescoHome.goToPhrescoHomePage(methodName);
	    		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
	    			//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
	    		   
	    		    BuildScreen Build=new BuildScreen(phrsc);
	    		    Build.PhpNoneBuild(phpconst,methodName);
	    			
	    }
	 
	 @Test
	    public void test_Build_YuiWidget_None() throws InterruptedException, IOException, Exception{
	    	    		
	    	YuiConst = new YuiConstantsXml();
				
				String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
						+ phrsc.PORT + "/";
				browserAppends = "*" + phrsc.BROWSER;
				assertNotNull("Browser name should not be null", browserAppends);
				SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
				assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
				wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
						serverURL, phrsc.SPEED, phrsc.CONTEXT);
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
	    public void test_Build_Config_Wordpress_None() throws InterruptedException, IOException, Exception{
	    	   		
	    	 WordpressConst = new WordPressConstants();
	    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
	    					+ phrsc.PORT + "/";
	    			browserAppends = "*" + phrsc.BROWSER;
	    			assertNotNull("Browser name should not be null", browserAppends);
	    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
	    			assertNotNull("selenium-port number should not be null",
	    					SELENIUM_PORT);
	    			wel = new WelcomeScreen(phrsc.HOST,
	    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
	    					phrsc.CONTEXT);
	    			assertNotNull(wel);
	    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	    			System.out.println("methodName = " + methodName);
	    			loginObject = new LoginScreen(phrsc);
	    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	    			                   phrescoHome.goToPhrescoHomePage(methodName);
	    		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
	    			//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
	    		    BuildScreen Build=new BuildScreen(phrsc);
	    		    Build.WordPressBuild(WordpressConst,methodName);
	    			
	    }
	  
	  @Test
	    public void test_Build_JqueryMobileWidget_None() throws InterruptedException, IOException, Exception{
	    	    		
	    	  jquerywidg=new JqueryWidgetConstants();
	    	  YuiConst = new YuiConstantsXml();
				
				String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
						+ phrsc.PORT + "/";
				browserAppends = "*" + phrsc.BROWSER;
				assertNotNull("Browser name should not be null", browserAppends);
				SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
				assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
				wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
						serverURL, phrsc.SPEED, phrsc.CONTEXT);
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
    		    Build.JqueryMobileWidgetNoneBuild(jquerywidg,methodName);
	    }		
	    
	  
	    

	   
	 
	 @Before
	 public void setUp() throws Exception {
			phrsc = new PhrescoUiConstantsXml();
		}
  @After
		public void tearDown() {
			clean();
		}

		private void clean() {
			wel.closeBrowser();
		}
	    

	    

}
