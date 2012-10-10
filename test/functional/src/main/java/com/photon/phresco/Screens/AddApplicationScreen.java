package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.AndroidNativeConstants;
import com.photon.phresco.uiconstants.DotNetConstants;
import com.photon.phresco.uiconstants.Drupal6ConstantsXml;
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.JavaStandaloneXml;
import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
import com.photon.phresco.uiconstants.JqueryWidgetConstants;
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
import com.photon.phresco.uiconstants.NodeJSConstantsXml;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.PhpConstantsXml;
import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.WordPressConstants;
import com.photon.phresco.uiconstants.YuiConstantsXml;
import com.photon.phresco.uiconstants.iPhoneConstantsXml;

public class AddApplicationScreen extends WebDriverAbstractBaseScreen {
	private PhrescoUiConstantsXml phrsc;
	private Log log = LogFactory.getLog(getClass());
	public WebDriverBaseScreen element;

	public AddApplicationScreen(PhrescoUiConstantsXml phrescoConst) throws Exception {
		this.phrsc = phrescoConst;
		
	}
	


	public void createProjDRUPAL7(Drupal7ConstantsXml drupalConst,String methodName) throws Exception {
		
		element=getXpathWebElement(phrsc.APPINFO_NAME);
		waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		element.click();
		element.type(drupalConst.APPINFO_DRUPAL_NAME_VALUE);
		
		element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		element.type(drupalConst.APPINFO_DRUPAL_DESCRIPTION_VALUE);
		
		element=getXpathWebElement(phrsc.TECH_WEBAPP);
		waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
		element.click();
		
		element=getXpathWebElement(phrsc.TECHNOLOGY);
		waitForElementPresent(phrsc.TECHNOLOGY,methodName);
		element.click();
		
		element=getXpathWebElement(drupalConst.DRUPAL7);
		waitForElementPresent(drupalConst.DRUPAL7,methodName);
		element.type(drupalConst.DRUPAL7);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.PILOTPROJ);
		waitForElementPresent(phrsc.PILOTPROJ,methodName);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.ESHOP,methodName);
		element=getXpathWebElement(phrsc.ESHOP);
		element.type(phrsc.ESHOP);
		element.click();
		Thread.sleep(2000);
		
		waitForElementPresent(phrsc.ADDSERVER,methodName);
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		
	}

	   
		public void createProjHTML5MobileWidget(MobWidgetConstantsXml mobwidg,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			element.click();
			element.type(mobwidg.APPINFO_HTML5MobileWidget_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			element.type(mobwidg.APPINFO_HTML5MobileWidget_DESCRIPTION_VALUE);
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			element.click();
			element=getXpathWebElement(mobwidg.HTML5MobileWidget);
			element.type(mobwidg.HTML5MobileWidget);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			Thread.sleep(1000);
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.type(phrsc.ESHOP);
			element.click();
			Thread.sleep(2000);
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}
		
		public void createProjHTML5WidgEshop(YuiConstantsXml YuiConst, String methodName) throws Exception {

			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(YuiConst.HTML5_WIDGET_PROJECT_NAME);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(YuiConst.HTML5_WIDGET_PROJECT_DESCRIPTION);
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET);
			waitForElementPresent(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET,methodName);
			element.type(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			Thread.sleep(1000);
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.type(phrsc.ESHOP);
			element.click();
			//Thread.sleep(2000);
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			

		}
		
		public void createProjJqueryWidget(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(jquerywidg.JQUERY_WIDGET_ESHOP_PROJET_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(jquerywidg.JQUERY_WIDGET_PROJECT_DESCRIPTION);
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			waitForElementPresent(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET,methodName);
			element=getXpathWebElement(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET);
			element.type(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			Thread.sleep(1000);
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.type(phrsc.ESHOP);
			element.click();
			Thread.sleep(2000);
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		}
	  public void createProjiPhoneNativeEshop(iPhoneConstantsXml iPhone,String methodName) throws Exception {
		  element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(iPhone.APPINFO_iPHONENATIVE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(iPhone.APPINFO_iPHONENATIVE_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.type(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(iPhone.MOBILEAPP_iPHONENATIVE_CLICK);
			waitForElementPresent(iPhone.MOBILEAPP_iPHONENATIVE_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
            waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			Thread.sleep(1000);
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.type(phrsc.ESHOP);
			element.click();
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		
	}
	  
 public void createProjiPhoneHybridEshop(iPhoneConstantsXml iPhone,String methodName) throws Exception {
        
	 element=getXpathWebElement(phrsc.APPINFO_NAME);
		element.click();
		element.type(iPhone.APPINFO_iPHONEHYBRID_NAME_VALUE);
		element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		element.type(iPhone.APPINFO_iPHONEHYBRID_DESCRIPTION_VALUE);
		waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
		element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
		element.click();
		element=getXpathWebElement(phrsc.TECHNOLOGY);
		waitForElementPresent(phrsc.TECHNOLOGY,methodName);
		element.click();
		element=getXpathWebElement(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK);
		waitForElementPresent(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK,methodName);
		element.type(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK);
		element.click();
		element=getXpathWebElement(phrsc.PILOTPROJ);
		waitForElementPresent(phrsc.PILOTPROJ,methodName);
		element.click();
		Thread.sleep(1000);
		waitForElementPresent(phrsc.ESHOP,methodName);
		element=getXpathWebElement(phrsc.ESHOP);
		element.type(phrsc.ESHOP);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.ADDSERVER,methodName);
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);    
        
    }
 
 public void createProjAndroidNativeEShop(AndroidNativeConstants androidNat,String methodName) throws Exception {
	    

	    element=getXpathWebElement(phrsc.APPINFO_NAME);
	    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		element.click();
		element.type(androidNat.APPINFO_ANDROIDNATIVE_NAME_VALUE);
		element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		element.type(androidNat.APPINFO_ANDROIDNATIVE_DESCRIPTION_VALUE);
		waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
		element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
		element.click();
		element=getXpathWebElement(phrsc.TECHNOLOGY);
		waitForElementPresent(phrsc.TECHNOLOGY,methodName);
		element.click();
		element=getXpathWebElement(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK);
		waitForElementPresent(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK,methodName);
		element.type(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK);
		element.click();
		element=getXpathWebElement(phrsc.PILOTPROJ);
		waitForElementPresent(phrsc.PILOTPROJ,methodName);
		element.click();
		Thread.sleep(1000);
		waitForElementPresent(phrsc.ESHOP,methodName);
		element=getXpathWebElement(phrsc.ESHOP);
		element.type(phrsc.ESHOP);
		element.click();
		//Thread.sleep(2000);
		waitForElementPresent(phrsc.ADDSERVER,methodName);
		
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

	}
 public void createProjAndroidHybridEshop(AndroidHybridConstants androidHyb,String methodName) throws Exception {
	    
	 element=getXpathWebElement(phrsc.APPINFO_NAME);
	    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		element.click();
		element.type(androidHyb.APPINFO_ANDROIDHYBRID_NAME_VALUE);
		element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		element.type(androidHyb.APPINFO_ANDROIDHYBRID_DESCRIPTION_VALUE);
		waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
		element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
		element.click();
		element=getXpathWebElement(phrsc.TECHNOLOGY);
		waitForElementPresent(phrsc.TECHNOLOGY,methodName);
		element.click();
		element=getXpathWebElement(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK);
		waitForElementPresent(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK,methodName);
		element.type(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK);
		element.click();
		element=getXpathWebElement(phrsc.PILOTPROJ);
		waitForElementPresent(phrsc.PILOTPROJ,methodName);
		element.click();
		Thread.sleep(1000);
		waitForElementPresent(phrsc.ESHOP,methodName);
		element=getXpathWebElement(phrsc.ESHOP);
		element.type(phrsc.ESHOP);
		element.click();
		//Thread.sleep(2000);

		waitForElementPresent(phrsc.ADDSERVER,methodName);
		//Thread.sleep(2000);
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

	}
 
 
 	public void createProjJavaWebServiceEshop(JavaWebServConstantsXml jws,String methodName) throws Exception {
 		element=getXpathWebElement(phrsc.APPINFO_NAME);
	    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
	    element.click();
	    element.type(jws.APPINFO_JAVAWEBSERVICE_NAME_VALUE);
	    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
	    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
	    element.type(jws.APPINFO_JAVAWEBSERVICE_DESCRIPTION_VALUE);
	    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
	    waitForElementPresent(phrsc.TECH_WEBSERVICE,methodName);
	    element.click();
	    element=getXpathWebElement(phrsc.TECHNOLOGY);
		waitForElementPresent(phrsc.TECHNOLOGY,methodName);
		element.click();
		element=getXpathWebElement(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK);
		waitForElementPresent(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK,methodName);
		element.type(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK);
		element.click();
		element=getXpathWebElement(phrsc.PILOTPROJ);
		waitForElementPresent(phrsc.PILOTPROJ,methodName);
		element.click();
		Thread.sleep(1000);
		waitForElementPresent(phrsc.ESHOP,methodName);
		element=getXpathWebElement(phrsc.ESHOP);
		element.type(phrsc.ESHOP);
		element.click();
		Thread.sleep(2000);

		waitForElementPresent(phrsc.ADDSERVER,methodName);
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
	
}
	public void createProjPHPBlog(PhpConstantsXml phpconst,String methodName) throws Exception {
		element=getXpathWebElement(phrsc.APPINFO_NAME);
		waitForElementPresent(phrsc.APPINFO_NAME,methodName);
	    element.click();
	    element.type(phpconst.APPINFO_PHP_NAME_VALUE);
	    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
	    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
	    element.type(phpconst.APPINFO_PHP_DESCRIPTION_VALUE);
	    element=getXpathWebElement(phrsc.TECH_WEBAPP);
	    waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
		element.click();
		element=getXpathWebElement(phrsc.TECHNOLOGY);
		waitForElementPresent(phrsc.TECHNOLOGY,methodName);
		element.click();
		element=getXpathWebElement(phpconst.PHP_CLICK);
		 element.type(phpconst.PHP_CLICK);
		element.click();
		element=getXpathWebElement(phrsc.PILOTPROJ);
		element.click();
		Thread.sleep(1000);
		waitForElementPresent(phpconst.APPINFO_PHP_PHPBLOG,methodName);
		element=getXpathWebElement(phpconst.APPINFO_PHP_PHPBLOG);
		element.type(phrsc.ESHOP);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.ADDSERVER,methodName);
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

	}
	   
	public void createProjSharepointRescMngmt(SharepointConstantsXml spconst,String methodName) throws Exception {
		element=getXpathWebElement(phrsc.APPINFO_NAME);
	    element.click();
	    element.type(spconst.APPINFO_SHAREPOINT_NAME_VALUE);
	    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
	    element.type(spconst.APPINFO_SHAREPOINT_DESCRIPTION_VALUE);
	    element=getXpathWebElement(phrsc.TECH_WEBAPP);
		element.click();
		element=getXpathWebElement(phrsc.TECHNOLOGY);
		element.click();
		element=getXpathWebElement(spconst.SHAREPOINT);
		 element.type(spconst.SHAREPOINT);
		element.click();
		element=getXpathWebElement(phrsc.PILOTPROJ);
		element.click();
		Thread.sleep(1000);
		waitForElementPresent(spconst.SHAREPOINT_RESOURCEMNGMT,methodName);
		element=getXpathWebElement(spconst.SHAREPOINT_RESOURCEMNGMT);
		element.type(phrsc.ESHOP);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.ADDSERVER,methodName);
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
	}


	public void createProjNodeJSEshop(NodeJSConstantsXml nodejsconst,String methodName) throws Exception {
		
		element=getXpathWebElement(phrsc.APPINFO_NAME);
	    element.click();
	    element.type(nodejsconst.NODEJS_PROJET_NAME);
	    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
	    element.type(nodejsconst.NODEJS_PROJECT_DESCRIPTION);
	    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
	    element.click();
	    element=getXpathWebElement(phrsc.TECHNOLOGY);
		waitForElementPresent(phrsc.TECHNOLOGY,methodName);
		element.click();
		element=getXpathWebElement(nodejsconst.NODEJS_SELECT_TECHNOLOGY);
		element.type(nodejsconst.NODEJS_SELECT_TECHNOLOGY);
		element.click();
		element=getXpathWebElement(phrsc.PILOTPROJ);
		element.click();
		Thread.sleep(1000);
		waitForElementPresent(phrsc.ESHOP,methodName);
		element=getXpathWebElement(phrsc.ESHOP);
		element.type(phrsc.ESHOP);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.ADDSERVER,methodName);
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.APPINFO_NEXT);
		waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
		element.click();
		Thread.sleep(5000);
		waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
		element=getXpathWebElement(phrsc.APPINFO_FINISH);
		element.click();
		
		waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		
	}



 /***********************************NONE_PROJECTS*********************************************/

public void createProjDRUPAL7None(Drupal7ConstantsXml drupalConst,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(drupalConst.APPINFO_DRUPALNONE_NAME_VALUE);
			
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(drupalConst.APPINFO_DRUPAL_DESCRIPTION_VALUE);
			
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			
			element=getXpathWebElement(drupalConst.DRUPAL7);
			waitForElementPresent(drupalConst.DRUPAL7,methodName);
			element.type(drupalConst.DRUPAL7);
			element.click();
			//element.click();
						
			//element.type(drupalConst.DRUPAL7);
			
			/*element=getXpathWebElement(drupalConst.DRUPAL7_CLICK);
			waitForElementPresent(drupalConst.DRUPAL7_CLICK,methodName);
			element.click();
			*/
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.SELECT_SERVER,methodName);
			element=getXpathWebElement(phrsc.SELECT_SERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_APACHE,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_APACHE);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_APACHE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_APACHE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_ADD);
			element.click();
			Thread.sleep(3000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}
			public void createProjDRUPAL6(Drupal6ConstantsXml drupal6Const,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(drupal6Const.APPINFO_DRUPAL6_NAME_VALUE);
				
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(drupal6Const.APPINFO_DRUPAL6_DESCRIPTION_VALUE);
				
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				
				element=getXpathWebElement(drupal6Const.DRUPAL6);
				waitForElementPresent(drupal6Const.DRUPAL6,methodName);
				element.type(drupal6Const.DRUPAL6);
				element.click();
				//element.click();
				
				/*element=getXpathWebElement(phrsc.PILOTPROJ);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();
				*/
				Thread.sleep(2000);
				waitForElementPresent(phrsc.ADDSERVER,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER);
				element.click();
				waitForElementPresent(phrsc.SELECT_SERVER,methodName);
				element=getXpathWebElement(phrsc.SELECT_SERVER);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_APACHE,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_APACHE);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_APACHE_VERSION,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_APACHE_VERSION);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_ADD);
				element.click();
				waitForElementPresent(phrsc.ADDDATABASE,methodName);
				element=getXpathWebElement(phrsc.ADDDATABASE);
				element.click();
				waitForElementPresent(phrsc.ADDDATABASE_VERSION,methodName);
				element=getXpathWebElement(phrsc.ADDDATABASE_VERSION);
				element.click();
				waitForElementPresent(phrsc.ADDDATABASE_ADD,methodName);
				element=getXpathWebElement(phrsc.ADDDATABASE_ADD);
				element.click();
				
				Thread.sleep(5000);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				element.click();
				Thread.sleep(5000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				element.click();
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				
			}
		   
			public void createProjHTML5MobileWidgetNone(MobWidgetConstantsXml mobwidg,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				element.click();
				element.type(mobwidg.APPINFO_MOBILEWIDGET_NONE_NAME_VALUE);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				element.type(mobwidg.APPINFO_HTML5MobileWidget_DESCRIPTION_VALUE);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				element.click();
				element=getXpathWebElement(mobwidg.HTML5MobileWidget);
				element.type(mobwidg.HTML5MobileWidget);
				element.click();
				//element.click();
				/*element=getXpathWebElement(phrsc.PILOTPROJ);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();*/
				Thread.sleep(2000);
				waitForElementPresent(phrsc.ADDSERVER,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_TOMCAT_VERSION,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_TOMCAT_VERSION);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_ADD);
				element.click();
				waitForElementPresent(phrsc.SELECT_WEBSERVICE,methodName);
				element=getXpathWebElement(phrsc.SELECT_WEBSERVICE);
				element.click();
				
				
				Thread.sleep(5000);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				element.click();
				Thread.sleep(5000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				//Thread.sleep(10000);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				//waitForElementPresent(mobwidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
				
			}
			
			public void createProjHTML5WidgNone(YuiConstantsXml YuiConst,String methodName) throws Exception {

				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(YuiConst.HTML5_WIDGET_PROJECT_NONE_NAME);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(YuiConst.HTML5_WIDGET_PROJECT_DESCRIPTION);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				element=getXpathWebElement(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET);
				waitForElementPresent(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET,methodName);
				element.type(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET);
				element.click();
				//element.click();
				/*element=getXpathWebElement(phrsc.PILOTPROJ);
				waitForElementPresent(phrsc.PILOTPROJ,methodName);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();
				*/
				Thread.sleep(2000);

				waitForElementPresent(phrsc.ADDSERVER,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_TOMCAT_VERSION,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_TOMCAT_VERSION);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_ADD);
				element.click();
				waitForElementPresent(phrsc.SELECT_WEBSERVICE,methodName);
				element=getXpathWebElement(phrsc.SELECT_WEBSERVICE);
				element.click();
				
				Thread.sleep(5000);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				element.click();
				Thread.sleep(5000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				

			}
			
			public void createProjJqueryWidgetNone(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(jquerywidg.JQUERY_WIDGET_NONEPROJ_NAME);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(jquerywidg.JQUERY_WIDGET_PROJECT_DESCRIPTION);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				waitForElementPresent(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET,methodName);
				element=getXpathWebElement(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET);
				element.type(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET);
				element.click();
				//element.click();
				/*element=getXpathWebElement(phrsc.PILOTPROJ);
				waitForElementPresent(phrsc.PILOTPROJ,methodName);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();*/
				Thread.sleep(2000);
				

				waitForElementPresent(phrsc.ADDSERVER,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_TOMCAT_VERSION,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_TOMCAT_VERSION);
				element.click();
				waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
				element=getXpathWebElement(phrsc.ADDSERVER_ADD);
				element.click();
				waitForElementPresent(phrsc.SELECT_WEBSERVICE,methodName);
				element=getXpathWebElement(phrsc.SELECT_WEBSERVICE);
				element.click();
	
				Thread.sleep(5000);
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element.click();
				Thread.sleep(5000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			}
		  public void createProjiPhoneNativeNone(iPhoneConstantsXml iPhone,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(iPhone.APPINFO_iPHONENATIVE_NONEPROJ_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(iPhone.APPINFO_iPHONENATIVE_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.type(phrsc.TECH_MOBILEAPP);
			element.click();
			//element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(iPhone.MOBILEAPP_iPHONENATIVE_CLICK);
			waitForElementPresent(iPhone.MOBILEAPP_iPHONENATIVE_CLICK,methodName);
			element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
            waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}
		  
		  
		  
	 public void createProjiPhoneHybridNone(iPhoneConstantsXml iPhone,String methodName) throws Exception {
	        
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
			element.click();
			element.type(iPhone.APPINFO_iPHONEHYBRID_NONE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			element.type(iPhone.APPINFO_iPHONEHYBRID_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK);
			waitForElementPresent(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK,methodName);
			element.type(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_TOMCAT_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_TOMCAT_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);    
	        
	    }
	 
	 public void createProjAndroidNativeNone(AndroidNativeConstants androidNat,String methodName) throws Exception {
		    
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(androidNat.APPINFO_ANDROIDNATIVE_NONE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(androidNat.APPINFO_ANDROIDNATIVE_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK);
			waitForElementPresent(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK,methodName);
			element.type(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
	 public void createProjAndroidHybridNone(AndroidHybridConstants androidHyb,String methodName) throws Exception {
		    
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(androidHyb.APPINFO_ANDROIDHYBRID_NONE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(androidHyb.APPINFO_ANDROIDHYBRID_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK);
			waitForElementPresent(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK,methodName);
			element.type(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);

			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_TOMCAT_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_TOMCAT_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
	 
	 
	 	public void createProjJavaWebServiceNone(JavaWebServConstantsXml jws,String methodName) throws Exception {
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(jws.APPINFO_JAVAWEBSERVICE_NONE_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(jws.APPINFO_JAVAWEBSERVICE_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
		    waitForElementPresent(phrsc.TECH_WEBSERVICE,methodName);
		    element.click();
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK);
			waitForElementPresent(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK,methodName);
			element.type(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			

			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_TOMCAT_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_TOMCAT_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_ADD);
			element.click();
			
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		
	}
		public void createProjPHPNone(PhpConstantsXml phpconst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(phpconst.APPINFO_PHP_NONEPROJ_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(phpconst.APPINFO_PHP_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBAPP);
		    waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(phpconst.PHP_CLICK);
			 element.type(phpconst.PHP_CLICK);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.SELECT_SERVER,methodName);
			element=getXpathWebElement(phrsc.SELECT_SERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_APACHE,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_APACHE);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_APACHE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_APACHE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			
			waitForElementPresent(phrsc.ADDDATABASE,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_ADD);
			element.click();
			
		
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
		   
		public void createProjSharepointNone(SharepointConstantsXml spconst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(spconst.APPINFO_SHAREPOINT_NONE_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(spconst.APPINFO_SHAREPOINT_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			element.click();
			element=getXpathWebElement(spconst.SHAREPOINT);
			 element.type(spconst.SHAREPOINT);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.SELECT_SERVER,methodName);
			element=getXpathWebElement(phrsc.SELECT_SERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_SHAREPOINT,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_SHAREPOINT);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_SHAREPOINT_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_SHAREPOINT_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		}


		public void createProjNodeJSNone(NodeJSConstantsXml nodejsconst,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(nodejsconst.NODEJS_PROJET_NONE_NAME);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(nodejsconst.NODEJS_PROJECT_DESCRIPTION);
		    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
		    element.click();
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(nodejsconst.NODEJS_SELECT_TECHNOLOGY);
			element.type(nodejsconst.NODEJS_SELECT_TECHNOLOGY);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.SELECT_SERVER,methodName);
			element=getXpathWebElement(phrsc.SELECT_SERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_NODEJS,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_NODEJS);
			element.click();
			
			waitForElementPresent(phrsc.ADDSERVER_NODEJS_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_NODEJS_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_ADD);
			element.click();
			
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}


		public void createProjDotNetNone(DotNetConstants dotNetConst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(dotNetConst.APPINFO_DOTNET_NONEPROJ_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(dotNetConst.APPINFO_DOTNET_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(dotNetConst.WEBAPP_DOTNET_CLICK);
			waitForElementPresent(dotNetConst.WEBAPP_DOTNET_CLICK,methodName);
			element.type(dotNetConst.WEBAPP_DOTNET_CLICK);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.SELECT_SERVER,methodName);
			element=getXpathWebElement(phrsc.SELECT_SERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_IIS,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_IIS);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_IIS_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_IIS_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}


		public void createProjWordPressNone(WordPressConstants wordpressConst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(wordpressConst.APPINFO_WORDPRESS_NONEPROJ_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(wordpressConst.APPINFO_WORDPRESS_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(wordpressConst.WEBAPP_WORDPRESS_CLICK);
			waitForElementPresent(wordpressConst.WEBAPP_WORDPRESS_CLICK,methodName);
			 element.type(wordpressConst.WEBAPP_WORDPRESS_CLICK);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.SELECT_SERVER,methodName);
			element=getXpathWebElement(phrsc.SELECT_SERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_APACHE,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_APACHE);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_APACHE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_APACHE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			
			waitForElementPresent(phrsc.ADDDATABASE,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDDATABASE_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDDATABASE_ADD);
			element.click();
			
			
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}
		
		public void createProjHTML5JqueryMobileWidgetNone(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
			
			Thread.sleep(2000);
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(jquerywidg.JQUERY_MOBILE_WIDGET_NONEPROJ_NAME);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(jquerywidg.JQUERY_MOBILE_WIDGET_PROJECT_DESCRIPTION);
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			waitForElementPresent(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_MOBILE_WIDGET,methodName);
			element=getXpathWebElement(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_MOBILE_WIDGET);
			element.type(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_MOBILE_WIDGET);
			element.click();
			//element.click();
			/*element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();*/
			Thread.sleep(2000);
			

			waitForElementPresent(phrsc.ADDSERVER,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_TOMCAT_VERSION,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_TOMCAT_VERSION);
			element.click();
			waitForElementPresent(phrsc.ADDSERVER_ADD,methodName);
			element=getXpathWebElement(phrsc.ADDSERVER_ADD);
			element.click();
			waitForElementPresent(phrsc.SELECT_WEBSERVICE,methodName);
			element=getXpathWebElement(phrsc.SELECT_WEBSERVICE);
			element.click();
			
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		}

		
public void createProjHTML5JqueryMobileWidget(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
			
	        Thread.sleep(2000);
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(jquerywidg.JQUERY_MOBILE_WIDGET_ESHOP_PROJ_NAME);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(jquerywidg.JQUERY_MOBILE_WIDGET_PROJECT_DESCRIPTION);
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			waitForElementPresent(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_MOBILE_WIDGET,methodName);
			element=getXpathWebElement(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_MOBILE_WIDGET);
			element.type(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_MOBILE_WIDGET);
			element.click();
			//element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.type(phrsc.ESHOP);
			element.click();
			Thread.sleep(2000);
			
			waitForElementPresent(phrsc.ADDSERVER,methodName);
			Thread.sleep(5000);
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			Thread.sleep(5000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		}

public void createProj_JavastandaloneNone(JavaStandaloneXml javastandalone,String methodName) throws Exception {
    
    element=getXpathWebElement(phrsc.APPINFO_NAME);
    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
	element.click();
	element.type(javastandalone.APPINFO_JAVASTANDALONE_NONEPROJ_NAME_VALUE);
	element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
	waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
	element.type(javastandalone.APPINFO_JAVASTANDALONE_DESCRIPTION_VALUE);
	/*waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
	element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
	element.click();*/
	element=getXpathWebElement(phrsc.TECHNOLOGY);
	waitForElementPresent(phrsc.TECHNOLOGY,methodName);
	element.click();
	Thread.sleep(2000);
	element=getXpathWebElement(javastandalone.WEBAPP_JAVASTANDALONE_CLICK);
	waitForElementPresent(javastandalone.WEBAPP_JAVASTANDALONE_CLICK,methodName);
	element.type(javastandalone.WEBAPP_JAVASTANDALONE_CLICK);
	element.click();
	//element.click();
	/*element=getXpathWebElement(phrsc.PILOTPROJ);
	waitForElementPresent(phrsc.PILOTPROJ,methodName);
	element.click();
	waitForElementPresent(phrsc.NONE,methodName);
	element=getXpathWebElement(phrsc.NONE);
	element.click();*/
	Thread.sleep(2000);
	element=getXpathWebElement(phrsc.APPINFO_NEXT);
	waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
	element.click();
	waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
	element=getXpathWebElement(phrsc.APPINFO_FINISH);
	element.click();
	waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

}

		
    	public ConfigScreen ConfigScreen() throws Exception {
    		element=getXpathWebElement(phrsc.APPLICATIONS_TAB);
    		element.click();
			//waitForElementPresent(phrsc.ADD_APPLICATION_BUTTON);
			return new ConfigScreen();
		}
		
		

	

		

		}

