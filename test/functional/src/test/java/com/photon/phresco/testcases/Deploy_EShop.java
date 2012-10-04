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


public class Deploy_EShop {
	
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
    public void test_Deploy_Drupal7_None_() throws InterruptedException, IOException, Exception{
    	   		
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
    		    Build.drupal7Eshopdeploy(drupal,methodName);
    			
    }
 
	 @Test
	    public void test_Deploy_MobWidget_None() throws InterruptedException, IOException, Exception{
				    	    		
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
		    		    Build.MobilewidgetEshopdeploy(mobwidg,methodName);
	    }
	 
	 @Test
	   public void test_Deploy_JqueryWidget_None_Proj() throws InterruptedException, IOException, Exception{
	   	    		
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
			    Build.JqueryWidgetEshopdeploy(jquerywidg,methodName);
	   }
	 
	  @Test
	 public void test_Deploy_JWS_None() throws InterruptedException, IOException, Exception{
	 	    		
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
			    Build.JavaWebServiceDeployEshop(jws,methodName);
	 }

	  @Test
	    public void test_Deploy_PHP_None() throws InterruptedException, IOException, Exception{
	    	   		
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
	    		    Build.PhpEshopdeploy(phpconst,methodName);
	    			
	    }
	 
	 
	@Test
	 public void test_Deploy_NodeJS() throws InterruptedException, IOException, Exception{
	 	    		
				nodejsconst = new NodeJSConstantsXml();
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
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	         phrescoHome.goToPhrescoHomePage(methodName);
				ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
				 BuildScreen Build=new BuildScreen(phrsc);
	 		    Build.NodeJsEshopRunAgainstSrc(nodejsconst,methodName);
	 }
	 
		 @Test
	    public void test_Deploy_YuiWidget_None_Proj() throws InterruptedException, IOException, Exception{
	    	    		
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
			    Build.HTML5WidgetEshopdeploy(YuiConst,methodName);
	    }	
	 
		 
		 @Test
		    public void test_Deploy_JqueryMobileWidget_Proj() throws InterruptedException, IOException, Exception{
		    	    		
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
				    Build.JqueryMobileWidgetEshopdeploy(jquerywidg,methodName);
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
