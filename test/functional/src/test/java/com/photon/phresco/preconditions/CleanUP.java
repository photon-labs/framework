


 /* Author by {phresco} QA Automation Team*/
 
package com.photon.phresco.preconditions;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import com.photon.phresco.Screens.AddApplicationScreen;
import com.photon.phresco.Screens.ApplicationsScreen;
import com.photon.phresco.Screens.LoginScreen;
import com.photon.phresco.Screens.PhrescoWelcomePage;
import com.photon.phresco.Screens.WelcomeScreen;
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

public class CleanUP extends TestCase{
	
	
	private PhrescoUiConstantsXml phrsc;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	private int SELENIUM_PORT;
	String methodName;
	
	
	
    
  @Test
    public void test_Config_Drupal7_None() throws InterruptedException, IOException, Exception{
    	    		
	   
   	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
		//CreateDbsql dbsql = new CreateDbsql(methodName);
				
    }
    
   @Test
    public void test_Config_Drupal6_None() throws InterruptedException, IOException, Exception{
    	    		
    	  
    	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		DeleteDbsql dbsql = new DeleteDbsql(methodName);
    }
   
	
      @Test
    public void test_Config_JWS_None() throws InterruptedException, IOException, Exception{
    	    		
    	  
      	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			DeleteDbsql dbsql = new DeleteDbsql(methodName);
    }
      @Test
    public void test_Config_PHP_None() throws InterruptedException, IOException, Exception{
    	   		
    	  
      	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			DeleteDbsql dbsql = new DeleteDbsql(methodName);
    }
    
      
        
          @Test
    public void test_Config_NodeJS_None() throws InterruptedException, IOException, Exception{
    	    		
        	  
          	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
  			System.out.println("methodName = " + methodName);
  			DeleteDbsql dbsql = new DeleteDbsql(methodName);
    }
    
  
	
     
	   @Test
	    public void test_Config_Wordpress_None() throws InterruptedException, IOException, Exception{
	    	   		
		   
       	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			DeleteDbsql dbsql = new DeleteDbsql(methodName);
	    }
     
    	
    
     //*****************Eshop******************

	  @Test
    public void test_config_drupal7_eshop() throws InterruptedException, IOException, Exception{
    	    		
		  
      	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			DeleteDbsql dbsql = new DeleteDbsql(methodName);
				
    }
  
   
         @Test
    public void test_Config_JWS_Eshop() throws InterruptedException, IOException, Exception{
    	    		
    	  
      	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			DeleteDbsql dbsql = new DeleteDbsql(methodName);
    }
      
      @Test
    public void test_Config_PHP_Blog() throws InterruptedException, IOException, Exception{
    	  
      	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			DeleteDbsql dbsql = new DeleteDbsql(methodName);
    }
    
      
    
          @Test
    public void test_Config_NodeJS_Eshop() throws InterruptedException, IOException, Exception{
    	    
        	methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			DeleteDbsql dbsql = new DeleteDbsql(methodName);
			
    }
    
   
    
	/*@Test
	public void test_Delete_AllProjects() throws InterruptedException,
			IOException, Exception {

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
		//AddApplicationScreen addappscrn = applicationScr
				//.gotoAddApplicationScreen(methodName);
		applicationScr.deleteAll_Projects(methodName);
	}
   */
  
	public void setUp() throws Exception {
		phrsc = new PhrescoUiConstantsXml();
	}

	public void tearDown() {
		//clean();
	}

	private void clean() {
		wel.closeBrowser();
	}

}





