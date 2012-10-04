/*
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.testcases;

import java.io.IOException;
import org.junit.Test;
import com.photon.phresco.Screens.AddApplicationScreen;
import com.photon.phresco.Screens.ApplicationsScreen;
import com.photon.phresco.Screens.LoginScreen;
import com.photon.phresco.Screens.PhrescoWelcomePage;
import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.AndroidNativeConstants;
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

public class Create_ProjectEshop extends TestCase{
	
	
	private PhrescoUiConstantsXml phrsc;
	private Drupal7ConstantsXml drupal;
	private MobWidgetConstantsXml mobwidg;
	private JqueryWidgetConstants jquerywidg;
	private iPhoneConstantsXml iPhone;
	private AndroidNativeConstants androidNat;
	private AndroidHybridConstants androidHyb;
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
    
    public void testCreate_Drupal7_EShop_Proj() throws InterruptedException, IOException, Exception{
    	    		
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
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				addappscrn.createProjDRUPAL7(drupal,methodName);
    }
  @Test
    public void testCreate_MobWidget_EShop_Proj() throws InterruptedException, IOException, Exception{
			    	    		
                mobwidg=new MobWidgetConstantsXml();
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
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				addappscrn.createProjHTML5MobileWidget(mobwidg,methodName);
    }
  @Test
   public void testCreate_JqueryWidget_EShop_Proj() throws InterruptedException, IOException, Exception{
			    	    		
	               jquerywidg=new JqueryWidgetConstants();
               
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
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
				methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
				addappscrn.createProjJqueryWidget(jquerywidg,methodName);
   }
  @Test
    public void testCreate_iPhoneNative_EShop_Proj() throws InterruptedException, IOException, Exception{
    	    		
			iPhone = new iPhoneConstantsXml();
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
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjiPhoneNative(iPhone,methodName);
    }
    @Test
    public void testCreate_iPhoneHybd_EShop_Proj() throws InterruptedException, IOException, Exception{
    	   		
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
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjiPhoneHybrid(iPhone,methodName);
    }
		@Test
		public void testCreate_AndroidNative_EShop_Proj() throws InterruptedException,
				IOException, Exception {
	
			androidNat = new AndroidNativeConstants();
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
			AddApplicationScreen addappscrn = applicationScr
					.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjAndroidNative(androidNat,methodName);
		}
	
		@Test
		public void testCreate_AndroidHybrid_EShop_Proj() throws InterruptedException,
				IOException, Exception {
	
			androidHyb = new AndroidHybridConstants();
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
			AddApplicationScreen addappscrn = applicationScr
					.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjAndroidHybrid(androidHyb,methodName);
		}
    @Test
    public void testCreate_JWS_EShop_Proj() throws InterruptedException, IOException, Exception{
    	    		
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
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjJavaWebService(jws,methodName);
    }
    @Test
    public void testCreate_PHP_EShop_Proj() throws InterruptedException, IOException, Exception{
    	   		
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
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjPHP(phpconst,methodName);
    }
    @Test
    public void testCreate_Share_EShop_Proj() throws InterruptedException, IOException, Exception{
    	    		
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
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjSharepoint(spconst,methodName);
    }
    @Test
    public void testCreate_NodeJS_EShop_Proj() throws InterruptedException, IOException, Exception{
    	    		
			nodejsconst = new NodeJSConstantsXml();
			//CreateDbsql db = new CreateDbsql();
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
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjNodeJS(nodejsconst,methodName);
    }
    
    @Test
    public void testCreate_YuiWidget_EShop_Proj() throws InterruptedException, IOException, Exception{
    	    		
    	YuiConst = new YuiConstantsXml();
			
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
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			addappscrn.createProjHTML5Widg(YuiConst,methodName);
    }
  
    
    @Test
    public void testCreate_JqueryMobWidget_EShop_Proj() throws InterruptedException, IOException, Exception{
			    	    		
    	 jquerywidg=new JqueryWidgetConstants();
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
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
				addappscrn.createProjHTML5JqueryMobileWidget(jquerywidg,methodName);
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

