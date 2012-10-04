
 /* Author by {phresco} QA Automation Team*/
 
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
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.DotNetConstants;
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

public class Create_Config_None extends TestCase{
	
	
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
	private DotNetConstants DotNetConst;
	private AndroidHybridConstants androidHyb;
	String methodName;
	
	
	
    
   @Test
    public void test_Config_Drupal7_None() throws InterruptedException, IOException, Exception{
    	    		
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
			    CreateDbsql dbsql = new CreateDbsql(methodName);
				ConfigScreen config = new ConfigScreen();
				config.Drupal7NoneDatabaseConfig(drupal,methodName);
				config.Drupal7NoneServerConfig(drupal,methodName);
				
    }
    
    @Test
    public void test_Config_Drupal6_None() throws InterruptedException, IOException, Exception{
    	    		
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
			    CreateDbsql dbsql = new CreateDbsql(methodName);
				ConfigScreen config = new ConfigScreen();
				config.Drupal6NoneDatabaseConfig(drupal6,methodName);
				config.Drupal6NoneServerConfig(drupal6,methodName);
				
    }
   @Test
    public void test_Config_MobWidget_None() throws InterruptedException, IOException, Exception{
			    	    		
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
				    
					ConfigScreen config = new ConfigScreen();
				config.MobilewidgetNoneServerConfig(mobwidg,methodName);
				config.HTML5WidgetNoneWebServiceConfig(YuiConst,methodName);
    }
   
   @Test
   public void test_Config_JqueryWidget_None() throws InterruptedException, IOException, Exception{
   	    		
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
			
			ConfigScreen config = new ConfigScreen();
			config.HTML5JqueryWidgetNoneServerConfig(jquerywidg,methodName);
			config.HTML5WidgetNoneWebServiceConfig(YuiConst,methodName);
   }		
   
	
     @Test
    public void test_Config_iPhoneHybrid_None() throws InterruptedException, IOException, Exception{
    	   		
    		iPhone=new iPhoneConstantsXml();
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
			
			ConfigScreen config = new ConfigScreen();
			config.iPhoneHybridNoneServerConfig(iPhone,methodName);
			
   }
     
	 @Test
 	public void test_Config_AndroidHrbrid_None() throws InterruptedException, IOException,
 			Exception {

    	 androidHyb = new AndroidHybridConstants();
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
 		PhrescoWelcomePage phrescoHome = loginObject.testLoginPage(methodName);
 		phrescoHome.goToPhrescoHomePage(methodName);
 		ApplicationsScreen applicationScr = phrescoHome
 				.clickOnApplicationsTab(methodName);
 		// AddApplicationScreen addappscrn =
 		// applicationScr.gotoAddApplicationScreen();
 		
 		ConfigScreen config = new ConfigScreen();
 		config.AndroidHrbridNoneServerConfig(androidHyb,methodName);
 		
 	}
      @Test
    public void test_Config_JWS_None() throws InterruptedException, IOException, Exception{
    	    		
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
			CreateDbsql dbsql = new CreateDbsql(methodName);
			ConfigScreen config = new ConfigScreen();
			config.JavaWebServiceNoneDatabaseConfig(jws,methodName);
			config.JavaWebServiceNoneServerConfig(jws,methodName);
    }
      @Test
    public void test_Config_PHP_None() throws InterruptedException, IOException, Exception{
    	   		
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
    		    CreateDbsql dbsql = new CreateDbsql(methodName);
    			ConfigScreen config = new ConfigScreen();
			config.PhpNoneDatabaseConfig(phpconst,methodName);
			config.PHPNoneServerConfig(phpconst,methodName);
    }
    
      
         @Test
    public void test_Config_SharePoint_None() throws InterruptedException, IOException, Exception{
    	    		
    	      spconst=new SharepointConstantsXml();
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
			
			ConfigScreen config = new ConfigScreen();
			config.SharepointNoneServerConfig(spconst,methodName);
    }
    
          @Test
    public void test_Config_NodeJS_None() throws InterruptedException, IOException, Exception{
    	    		
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
			CreateDbsql dbsql = new CreateDbsql(methodName);
			ConfigScreen config = new ConfigScreen();
			config.NodeJsNoneDatabaseConfig(nodejsconst,methodName);
			config.NodeJsNoneServerConfig(nodejsconst,methodName);
    }
    
    @Test
    public void test_Config_YuiWidget_None() throws InterruptedException, IOException, Exception{
    	    		
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
			
			ConfigScreen config = new ConfigScreen();
			config.HTML5WidgetNoneServerConfig(YuiConst,methodName);
			config.HTML5WidgetNoneWebServiceConfig(YuiConst,methodName);
    }	
    

	@Test
    public void test_Config_DotNet_None() throws InterruptedException, IOException, Exception{
    	    		
		DotNetConst = new DotNetConstants();
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
			
			ConfigScreen config = new ConfigScreen();
			config.DotNetNoneServerConfig(DotNetConst,methodName);
    }
     
	   @Test
	    public void test_Config_Wordpress_None() throws InterruptedException, IOException, Exception{
	    	   		
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
	    		    CreateDbsql dbsql = new CreateDbsql(methodName);
	    			ConfigScreen config = new ConfigScreen();
				config.WordpressNoneDatabaseConfig(WordpressConst,methodName);
				config.WordpressNoneServerConfig(WordpressConst,methodName);
	    }
     
    @Test
    public void test_Config_JqueryMobileWidget_None() throws InterruptedException, IOException, Exception{
    	    		
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
			
			ConfigScreen config = new ConfigScreen();
			config.HTML5JqueryMobileWidgetNoneServerConfig(jquerywidg,methodName);
			config.HTML5WidgetNoneWebServiceConfig(YuiConst,methodName);
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

