package com.photon.phresco.Screens;

import java.io.IOException;

import org.openqa.jetty.http.SecurityConstraint.Nobody;

import com.photon.phresco.selenium.util.ScreenException;
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
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.WordPressConstants;
import com.photon.phresco.uiconstants.YuiConstantsXml;
import com.photon.phresco.uiconstants.iPhoneConstantsXml;

public class ConfigScreen extends WebDriverAbstractBaseScreen {
	PhrescoUiConstantsXml phrsc = new PhrescoUiConstantsXml();
	public WebDriverBaseScreen element;
	

	
/*public void DrupalServerConfig(Drupal7ConstantsXml drupalConst,String methodName) throws InterruptedException, IOException,
		Exception {

	element= getXpathWebElement(drupalConst.DRUPALPROJ);
	waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_NAME_VALUE);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_DESCRIPTION_VALUE);
	select(phrsc.CONFIGURATIONS_TYPE,
			phrsc.CONFIGURATIONS_TYPE_SERVER_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
	Thread.sleep(3000);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
	element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_HOST_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(drupalConst. CONFIGURATIONS_DRUPAL_SERVER_PORT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_DEPLOYDIR_VALUE);
	//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_CONTEXT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}

*/
public void DrupalDatabaseConfig(Drupal7ConstantsXml drupalConst,String methodName) throws InterruptedException,
		IOException, Exception {
	Thread.sleep(2000);
	waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
	element= getXpathWebElement(drupalConst.DRUPALPROJ);
	
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	  Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	Thread.sleep(1000);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_NAME_VALUE);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_DESCRIPTION_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
	//Thread.sleep(3000);
	element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	element.click();
	Thread.sleep(2000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_HOST_VALUE);
	element.click();
	//Thread.sleep(1000);
	//Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_PORT_VALUE);
	Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_USERNAME_VALUE);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(methodName);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
	Thread.sleep(1000);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}

public void Drupal7NoneServerConfig(Drupal7ConstantsXml drupalConst,String methodName) throws InterruptedException, IOException,
Exception {

	/*element= getXpathWebElement(drupalConst.DRUPALPROJ);
	waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();*/
	Thread.sleep(2000);
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_NAME_VALUE);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_DESCRIPTION_VALUE);
	/*select(phrsc.CONFIGURATIONS_TYPE,
			phrsc.CONFIGURATIONS_TYPE_SERVER_VALUE);*/
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
	Thread.sleep(2000);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
	element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_HOST_VALUE);
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(drupalConst. CONFIGURATIONS_DRUPAL_SERVER_PORT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_DEPLOYDIR_VALUE);
	//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL7_NONE_SERVER_CONTEXT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}



public void Drupal7NoneDatabaseConfig(Drupal7ConstantsXml drupalConst,String methodName) throws InterruptedException,
IOException, Exception {
    
	Thread.sleep(2000);
	waitForElementPresent(drupalConst.DRUPAL7_NONE_PROJ,methodName);
	element= getXpathWebElement(drupalConst.DRUPAL7_NONE_PROJ);
	
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	  Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();
	Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	Thread.sleep(1000);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_NAME_VALUE);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_DESCRIPTION_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
	//Thread.sleep(3000);
	element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	element.click();
	Thread.sleep(4000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_HOST_VALUE);
	element.click();
	//Thread.sleep(1000);
	//Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_PORT_VALUE);
	Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_USERNAME_VALUE);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(methodName);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
	Thread.sleep(1000);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}


public void Drupal6NoneServerConfig(Drupal6ConstantsXml drupal6Const,String methodName) throws InterruptedException, IOException,
Exception {

	Thread.sleep(2000);
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	element.click();
	element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_SERVER_NAME_VALUE);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_SERVER_DESCRIPTION_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
	Thread.sleep(2000);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
	element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_SERVER_HOST_VALUE);
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(drupal6Const. CONFIGURATIONS_DRUPAL6_SERVER_PORT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
	element.click();
	element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_SERVER_DEPLOYDIR_VALUE);
	//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
	element.click();
	element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_SERVER_CONTEXT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}

public void Drupal6NoneDatabaseConfig(Drupal6ConstantsXml drupal6Const,String methodName) throws InterruptedException,
IOException, Exception {

	Thread.sleep(2000);
	waitForElementPresent(drupal6Const.drupal6PROJ,methodName);
element= getXpathWebElement(drupal6Const.drupal6PROJ);

element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.CONFIGURATIONS);
Thread.sleep(3000);
waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
element.click();
Thread.sleep(2000);
waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
element.click();
Thread.sleep(2000);
waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
Thread.sleep(1000);
element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_DB_NAME_VALUE);
element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_DB_DESCRIPTION_VALUE);
element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
element.click();
Thread.sleep(1000);
element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
//Thread.sleep(3000);
element.click();
Thread.sleep(5000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
element.click();
element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_DB_HOST_VALUE);

Thread.sleep(1000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
element.click();
element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_DB_PORT_VALUE);
Thread.sleep(1000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
Thread.sleep(1000);
element.type(drupal6Const.CONFIGURATIONS_DRUPAL6_DB_USERNAME_VALUE);
element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
Thread.sleep(1000);
element.type(methodName);
element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
Thread.sleep(1000);
element.click();
waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}



public void SharepointNoneServerConfig(SharepointConstantsXml spconst,String methodName) throws InterruptedException,
		IOException, Exception {
		
	Thread.sleep(2000);
	    element=getXpathWebElement(spconst.CREATEDPROJECT_SHAREPOINT_NONE);
	    element.click();
	    Thread.sleep(3000);
	    element=getXpathWebElement(phrsc.CONFIGURATIONS);
	    element.click();
	    Thread.sleep(3000);
	    element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	    waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
		element.click();
		element.type(spconst.SHAREPOINT_SERVER_CONFIGURATION_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(spconst.SHAREPOINT_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(spconst.SHAREPOINT_SETTINGS_HOST_VALUE);
		//Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(spconst.SHAREPOINT_SETTINGS_PORT_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(spconst.SHAREPOINT_SETTINGS_SERVER_DEPLOYDIR_VALUE);
		/*element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_TYPE);
		element.type(spconst.SHAREPOINT_SETTINGS_SERVER_TYPE_VALUE);*/
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(spconst.SHAREPOINT_SETTINGS_SERVER_CONTEXT_VALUE );
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	}

public void DotNetNoneServerConfig(DotNetConstants dotNetConst,String methodName) throws InterruptedException,
IOException, Exception {

element=getXpathWebElement(dotNetConst.CREATEDPROJECT_DOTNET);
element.click();
Thread.sleep(3000);
element=getXpathWebElement(phrsc.CONFIGURATIONS);
element.click();
Thread.sleep(2000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
element.click();
Thread.sleep(2000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
element.click();
element.type(dotNetConst.DOTNET_SERVER_CONFIGURATION_NAME_VALUE);
element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
element.type(dotNetConst.DOTNET_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
element.click();
Thread.sleep(2000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
Thread.sleep(3000);
element.click();
Thread.sleep(1000);
element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
element.click();
Thread.sleep(3000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
element.type(dotNetConst.DOTNET_SERVER_CONFIGURATION_HOST_NAME_VALUE);
Thread.sleep(2000);
element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
element.type(dotNetConst.DOTNET_SERVER_CONFIGURATION_PORT_VALUE);
element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
element.type(dotNetConst.DOTNET_SETTINGS_SERVER_DEPLOYDIR_VALUE);
/*element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_TYPE);
element.type(spconst.SHAREPOINT_SETTINGS_SERVER_TYPE_VALUE);*/
element=getXpathWebElement(dotNetConst.DOTNET_CONFIGURATIONS_SERVER_SITENAME);
element.type(dotNetConst.DOTNET_SERVER_CONFIGURATION_SITENAME_VALUE);
element=getXpathWebElement(dotNetConst.DOTNET_CONFIGURATIONS_SERVER_APPNAME);
element.type(dotNetConst.DOTNET_SERVER_CONFIGURATION_APPNAME_VALUE);
element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
element.click();
waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}





	
/*public void PHPServerConfig(PhpConstantsXml phpconst,String methodName) throws InterruptedException,IOException, Exception {
  
	element= getXpathWebElement(drupalConst.DRUPALPROJ);
	waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	element.click();
	element.type(phpconst.PHP_CONFIGURATION_NAME_SERVER);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	element.type(phpconst.PHP_CONFIGURATION_DESCRIPTION_SERVER);
	select(phrsc.CONFIGURATIONS_TYPE,
			phrsc.CONFIGURATIONS_TYPE_SERVER_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
	Thread.sleep(3000);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
	element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(phpconst.SERVER_HOST_DESCRIPTIONS);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(phpconst. PHP_SERVER_PORT);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
	element.click();
	element.type(phpconst.PHP_CONFIGURATION_SERVER_DEPLOYDIR);
	//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
	element.click();
	element.type(phpconst.PHP_CONFIGURATION_SERVER_CONTEXT);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}


public void PhpDatabaseConfig(PhpConstantsXml phpconst,String methodName) throws InterruptedException,IOException, Exception {
	
	element= getXpathWebElement(phpconst.PHPPROJECT);
	waitForElementPresent(phpconst.PHPPROJECT,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	Thread.sleep(1000);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	Thread.sleep(1000);
	element.type(phpconst.PHP_CONFIGURATION_DB_NAME);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
	element.type(phpconst.PHP_CONFIGURATION_DB_DESCRIPTION);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
	//Thread.sleep(3000);
	element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	element.click();
	Thread.sleep(2000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(phpconst.SERVER_HOST_DESCRIPTIONS);
	element.click();
	//Thread.sleep(1000);
	//Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(phpconst.CONFIGURATION_PHP_PORT);
	Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(phpconst.PHP_SETTINGS_DB_USERNAME);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(methodName);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
	Thread.sleep(1000);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}*/

public void PHPNoneServerConfig(PhpConstantsXml phpconst,String methodName) throws InterruptedException,IOException, Exception {
	  
	/*element= getXpathWebElement(drupalConst.DRUPALPROJ);
	waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();*/
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	element.click();
	element.type(phpconst.PHP_CONFIGURATION_NAME_SERVER);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	element.type(phpconst.PHP_CONFIGURATION_DESCRIPTION_SERVER);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
	Thread.sleep(3000);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
	element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(phpconst.SERVER_HOST_DESCRIPTIONS);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(phpconst. PHP_SERVER_PORT);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
	element.click();
	element.type(phpconst.PHP_CONFIGURATION_SERVER_DEPLOYDIR);
	//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
	element.click();
	element.type(phpconst.PHP_NONE_CONFIGURATION_SERVER_CONTEXT);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}


public void PhpNoneDatabaseConfig(PhpConstantsXml phpconst,String methodName) throws InterruptedException,IOException, Exception {
	
	element= getXpathWebElement(phpconst.PHP_NONE_PROJECT);
	waitForElementPresent(phpconst.PHP_NONE_PROJECT,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	  Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();
	Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	Thread.sleep(1000);
	element.type(phpconst.PHP_CONFIGURATION_DB_NAME);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
	element.type(phpconst.PHP_CONFIGURATION_DB_DESCRIPTION);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
	Thread.sleep(3000);
	element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	element.click();
	Thread.sleep(3000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(phpconst.SERVER_HOST_DESCRIPTIONS);
	element.click();
	//Thread.sleep(1000);
	Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(phpconst.CONFIGURATION_PHP_PORT);
	Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(phpconst.PHP_SETTINGS_DB_USERNAME);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(methodName);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
	Thread.sleep(1000);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}

public void WordpressNoneServerConfig(WordPressConstants wordpressConst,String methodName) throws InterruptedException,IOException, Exception {
	  
	/*element= getXpathWebElement(drupalConst.DRUPALPROJ);
	waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();*/
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	element.click();
	element.type(wordpressConst.WORDPRESS_SERVER_CONFIGURATION_NAME_VALUE);
	element.click();
	element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	element.type(wordpressConst.WORDPRESS_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
	Thread.sleep(3000);
	element.click();
	Thread.sleep(1000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
	element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element.click();
	element.type(wordpressConst.WORDPRESS_SERVER_CONFIGURATION_HOST_NAME_VALUE);
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(wordpressConst. WORDPRESS_SERVER_CONFIGURATION_PORT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
	element.click();
	element.type(wordpressConst.CONFIGURATIONS_WORDPRESS_SERVER_DEPLOYDIR_VALUE);
	//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
	element.click();
	element.type(wordpressConst.WORDPRESS_SERVER_CONFIGURATION_CONTEXT_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}


public void WordpressNoneDatabaseConfig(WordPressConstants wordpressConst,String methodName) throws InterruptedException,IOException, Exception {
	
	element= getXpathWebElement(wordpressConst.CREATEDPROJECT_WORDPRESS);
	waitForElementPresent(wordpressConst.CREATEDPROJECT_WORDPRESS,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS);
	  Thread.sleep(3000);
	waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
	element.click();
	Thread.sleep(2000);
	waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
	Thread.sleep(1000);
	element.type(wordpressConst.CONFIGURATIONS_WORDPRESS_DB_NAME_VALUE);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
	waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
	element.type(wordpressConst.CONFIGURATIONS_WORDPRESS_DB_DESCRIPTION_VALUE);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
	Thread.sleep(5000);
	element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
	element.click();
	Thread.sleep(4000);
	waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	
	element.click();
	element.type(wordpressConst.CONFIGURATIONS_WORDPRESS_DB_HOST_VALUE);
	element.click();
	//Thread.sleep(1000);
	Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
	element.click();
	element.type(wordpressConst.CONFIGURATIONS_WORDPRESS_DB_PORT_VALUE);
	Thread.sleep(1000);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(wordpressConst.CONFIGURATIONS_WORDPRESS_DB_USERNAME_VALUE);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
	waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
	Thread.sleep(1000);
	element.type(methodName);
	element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
	waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
	Thread.sleep(1000);
	element.click();
	waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
}




public void JavaWebServiceNoneServerConfig(JavaWebServConstantsXml jws,String methodName) throws InterruptedException,
			IOException, Exception {
        
	    /*element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE);
	    element.click();
	    element=getXpathWebElement(phrsc.CONFIGURATIONS);
	    element.click();*/
	    element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(jws.JAVAWEBSERVICE_SERVER_CONFIGURATION_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jws.JAVAWEBSERVICE_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_VALUE);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(3000);
		/*element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();*/
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(2000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_HOST_VALUE);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_PORT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(jws.CONFIGURATIONS_JWS_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(jws. CONFIGURATIONS_JWS_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_SERVER_DEPLOYDIR_VALUE);
		/*element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_TYPE);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_SERVER_TYPE_VALUE);*/
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(jws.JAVAWEBSERVICE_NONE_SETTINGS_SERVER_CONTEXT_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	}

	public void JavaWebServiceNoneDatabaseConfig(JavaWebServConstantsXml jws,String methodName) throws InterruptedException,
			IOException, Exception {

		element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE_NONE);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(jws.JAVAWEBSERVICE_DB_CONFIGURATION_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jws.JAVAWEBSERVICE_DB_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		Thread.sleep(1000);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.click();
		element.type(jws.JAVAWEBSERVICE_SETTINGS_DB_HOST_VALUE);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_DB_PORT_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_TYPE_DB_USERNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
	}
	
public void NodeJsNoneServerConfig(NodeJSConstantsXml nodejsconst,String methodName) throws InterruptedException,
	IOException, Exception {

		/*element=getXpathWebElement(nodejsconst.NODEJS_PROJECT_CREATION_ID);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();*/
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(nodejsconst. NODEJS_CONFIG_SERVER_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(nodejsconst.NODEJS_CONFIG_SERVER_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(nodejsconst.NODEJS_CONFIG_SERVER_HOST);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(nodejsconst. NODEJS_NONE_CONFIG_SERVER_PORT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(nodejsconst.CONFIGURATIONS_NODEJS_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(nodejsconst. CONFIGURATIONS_NODEJS_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(nodejsconst.NODEJS_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(nodejsconst.NODEJS_NONE_CONFIG_SERVER_CONTEXT_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		}

	public void NodeJsNoneDatabaseConfig(NodeJSConstantsXml nodejsconst,String methodName) throws InterruptedException,
	IOException, Exception {

		element=getXpathWebElement(nodejsconst.NODEJS_PROJECT_CREATION_NONE);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(nodejsconst.NODEJS_CONFIG_DB_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(nodejsconst.NODEJS_CONFIG_DB_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
		//Thread.sleep(3000);
		element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		element.click();
		Thread.sleep(5000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		Thread.sleep(4000);
		element.type(nodejsconst.NODEJS_CONFIG_DB_HOST);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(nodejsconst.NODEJS_CONFIG_DB_PORT);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
		element.type(nodejsconst.NODEJS_CONFIG_DB_USERNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		}
	
	public void HTML5WidgetNoneServerConfig(YuiConstantsXml YuiConst,String methodName) throws InterruptedException,
	IOException, Exception {

		element=getXpathWebElement(YuiConst.HTML5_WIDGET_NONE_PROJECT_CREATED);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(YuiConst. HTML5_WIDGET_CONFIG_SERVER_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(YuiConst.HTML5_WIDGET_PROJECT_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(4000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_SERVER_HOST);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_SERVER_PORT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(YuiConst.CONFIGURATIONS_HTML5WIDGET_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(YuiConst. CONFIGURATIONS_HTML5WIDGET_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(YuiConst.HTML5_WIDGET_NONE_CONFIG_SERVER_CONTEXT_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		}
	
	public void HTML5JqueryWidgetNoneServerConfig(JqueryWidgetConstants jquerywidg,String methodName) throws InterruptedException,
	IOException, Exception {

		waitForElementPresent(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ,methodName);
		element=getXpathWebElement(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_HOST);
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_PORT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(jquerywidg.CONFIGURATIONS_HTML5JQUERY_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(jquerywidg. CONFIGURATIONS_HTML5JQUERY_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(jquerywidg.HTML5JQUERY_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(jquerywidg.JQUERY_WIDGET_NONE_CONFIG_SERVER_CONTEXTNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		}
	
	public void HTML5JqueryMobileWidgetNoneServerConfig(JqueryWidgetConstants jquerywidg,String methodName) throws InterruptedException,
	IOException, Exception {

		Thread.sleep(2000);
		element=getXpathWebElement(jquerywidg.JQUERY_MOBILE_WIDGET_NONE_PROJECT_CREATED_PROJ);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_HOST);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_PORT);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(jquerywidg.CONFIGURATIONS_HTML5JQUERY_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(jquerywidg. CONFIGURATIONS_HTML5JQUERY_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(jquerywidg.HTML5JQUERY_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(jquerywidg.HTML5JQUERYMOBILE_None_CONFIG_SERVER_CONTEXT_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		}
	
	
	public void iPhoneHybridNoneServerConfig(iPhoneConstantsXml iPhoneConst,String methodName) throws InterruptedException,
	IOException, Exception {
		
		 MobWidgetConstantsXml mobwidg = new MobWidgetConstantsXml();

		element=getXpathWebElement(iPhoneConst.CREATEDPROJECT_iPHONEHYBRID_NONE);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_NAME_VALUE);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	    element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_HOST_VALUE);
		Thread.sleep(2000);
		
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
	    element.click();
	    element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PORT_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_USERNAME_VALUE);
		
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
	    element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PASSWORD_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_DEPLOYDIR_VALUE);
		//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_ESHOP_SERVER_CONTEXT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
		
		}





	public void HTML5WidgetNoneWebServiceConfig(YuiConstantsXml YuiConst,String methodName) throws InterruptedException,
	IOException, Exception {
		/*Thread.sleep(2000);
		element=getXpathWebElement(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();*/
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.type(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.click();
		Thread.sleep(4000);
		//element=getXpathWebElement(phrsc.HTML5_WIDGET_CONFIG_TYPE_CLICK);
		//element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_HOST);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_PORT);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_CONTEXTNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);

	}





	public void MobilewidgetNoneServerConfig(MobWidgetConstantsXml mobwidg,String methodName)throws InterruptedException,
	IOException, Exception {
		
		element= getXpathWebElement(mobwidg.CREATEDPROJECT_HTML5MOBILEWIDGETNONE);
		waitForElementPresent(mobwidg.CREATEDPROJECT_HTML5MOBILEWIDGETNONE,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		Thread.sleep(1000);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_NAME_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_HOST_VALUE);
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PORT_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PASSWORD_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_DEPLOYDIR_VALUE);
		//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_NONE_SERVER_CONTEXT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void AndroidHrbridNoneServerConfig(AndroidHybridConstants androidHyb,String methodName)throws InterruptedException,
	IOException, Exception {
		
		 MobWidgetConstantsXml mobwidg = new MobWidgetConstantsXml();
		
		element= getXpathWebElement(androidHyb.CREATEDPROJECT_ANDROIDHYBRID_NONE);
		waitForElementPresent(androidHyb.CREATEDPROJECT_ANDROIDHYBRID_NONE,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_NAME_VALUE);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
	    element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_HOST_VALUE);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PORT_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PASSWORD_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_DEPLOYDIR_VALUE);
		//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_ESHOP_SERVER_CONTEXT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void HTML5JqueryMobileWidgetEshopServerConfig(
			JqueryWidgetConstants jquerywidg, String methodName) throws Exception {
		Thread.sleep(2000);
		element=getXpathWebElement(jquerywidg.JQUERY_MOBILE_WIDGET_ESHOP_PROJECT_CREATED_PROJ);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_HOST);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_PORT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(jquerywidg.CONFIGURATIONS_HTML5JQUERY_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(jquerywidg. CONFIGURATIONS_HTML5JQUERY_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(jquerywidg.HTML5JQUERY_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(jquerywidg.HTML5JQUERYMOBILE_Eshop_CONFIG_SERVER_CONTEXT_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void HTML5WidgetEshopWebServiceConfig(YuiConstantsXml YuiConst,
			String methodName) throws IOException, Exception {
		
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		Thread.sleep(2000);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.type(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.click();
		Thread.sleep(4000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_HOST);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_PORT);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_CONTEXTNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void HTML5WidgetEshopServerConfig(YuiConstantsXml YuiConst,
			String methodName) throws Exception {
		element=getXpathWebElement(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(YuiConst. HTML5_WIDGET_CONFIG_SERVER_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(YuiConst.HTML5_WIDGET_PROJECT_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_SERVER_HOST);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_SERVER_PORT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(YuiConst.CONFIGURATIONS_HTML5WIDGET_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(YuiConst. CONFIGURATIONS_HTML5WIDGET_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(YuiConst.HTML5_WIDGET_ESHOP_CONFIG_SERVER_CONTEXT_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void NodeJsEshopServerConfig(NodeJSConstantsXml nodejsconst,
			String methodName) throws Exception {
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(nodejsconst. NODEJS_CONFIG_SERVER_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(nodejsconst.NODEJS_CONFIG_SERVER_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(4000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(nodejsconst.NODEJS_CONFIG_SERVER_HOST);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(nodejsconst.NODEJS_ESHOP_CONFIG_SERVER_PORT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(nodejsconst.CONFIGURATIONS_NODEJS_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(nodejsconst. CONFIGURATIONS_NODEJS_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(nodejsconst.NODEJS_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(nodejsconst.NODEJS_ESHOP_CONFIG_SERVER_CONTEXT_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void NodeJsEshopDatabaseConfig(NodeJSConstantsXml nodejsconst,
			String methodName) throws Exception {
		element=getXpathWebElement(nodejsconst.NODEJS_PROJECT_CREATION_ID);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(nodejsconst.NODEJS_CONFIG_DB_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(nodejsconst.NODEJS_CONFIG_DB_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
		//Thread.sleep(3000);
		element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		element.click();
		Thread.sleep(4000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		//element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		element.type(nodejsconst.NODEJS_CONFIG_DB_HOST);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(nodejsconst.NODEJS_CONFIG_DB_PORT);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
		element.type(nodejsconst.NODEJS_CONFIG_DB_USERNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void SharepointRescMngmtServerConfig(SharepointConstantsXml spconst,
			String methodName) throws Exception, Exception {
		 element=getXpathWebElement(spconst.CREATEDPROJECT_SHAREPOINT );
		    element.click();
		    Thread.sleep(3000);
			element= getXpathWebElement(phrsc.CONFIGURATIONS);
			Thread.sleep(1000);
			waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
			element.click();
			Thread.sleep(3000);
			waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
			element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
			element.click();
			Thread.sleep(2000);
			element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
			waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
			element.click();
			element.type(spconst.SHAREPOINT_SERVER_CONFIGURATION_NAME_VALUE);
			element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
			element.type(spconst.SHAREPOINT_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
			element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
			element.click();
			element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
			waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
			Thread.sleep(3000);
			element.click();
			Thread.sleep(1000);
			element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
			waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
			element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
			element.click();
			Thread.sleep(3000);
			element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
			element.type(spconst.SHAREPOINT_SETTINGS_HOST_VALUE);
			Thread.sleep(2000);
			element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
			element.type(spconst.SHAREPOINT_SETTINGS_PORT_VALUE);
			element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
			element.type(spconst.SHAREPOINT_SETTINGS_SERVER_DEPLOYDIR_VALUE);
			/*element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_TYPE);
			element.type(spconst.SHAREPOINT_SETTINGS_SERVER_TYPE_VALUE);*/
			element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
			element.type(spconst.SHAREPOINT_SETTINGS_SERVER_CONTEXT_VALUE );
			element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
			element.click();
			waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void PHPBlogServerConfig(PhpConstantsXml phpconst, String methodName) throws Exception {
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(phpconst.PHP_CONFIGURATION_NAME_SERVER);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(phpconst.PHP_CONFIGURATION_DESCRIPTION_SERVER);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		element.type(phpconst.SERVER_HOST_DESCRIPTIONS);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(phpconst. PHP_SERVER_PORT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(phpconst.PHP_CONFIGURATION_SERVER_DEPLOYDIR);
		//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(phpconst.PHP_BLOG_CONFIGURATION_SERVER_CONTEXT);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void PhpBlogDatabaseConfig(PhpConstantsXml phpconst,
			String methodName) throws Exception {
		element= getXpathWebElement(phpconst.PHPPROJECT);
		waitForElementPresent(phpconst.PHPPROJECT,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		Thread.sleep(1000);
		element.type(phpconst.PHP_CONFIGURATION_DB_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
		element.type(phpconst.PHP_CONFIGURATION_DB_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
		//Thread.sleep(3000);
		element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		element.click();
		Thread.sleep(3000);
		//element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		element.type(phpconst.SERVER_HOST_DESCRIPTIONS);
		//element.click();
		//Thread.sleep(1000);
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(phpconst.CONFIGURATION_PHP_PORT);
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(phpconst.PHP_SETTINGS_DB_USERNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
		Thread.sleep(1000);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void JavaWebServiceEshopServerConfig(JavaWebServConstantsXml jws,
			String methodName) throws Exception {
		element=getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(jws.JAVAWEBSERVICE_SERVER_CONFIGURATION_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jws.JAVAWEBSERVICE_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_HOST_VALUE);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_PORT_VALUE);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(jws.CONFIGURATIONS_JWS_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(jws. CONFIGURATIONS_JWS_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_SERVER_DEPLOYDIR_VALUE);
		/*element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_TYPE);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_SERVER_TYPE_VALUE);*/
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(jws.JAVAWEBSERVICE_ESHOP_SETTINGS_SERVER_CONTEXT_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void JavaWebServiceEshopDatabaseConfig(JavaWebServConstantsXml jws,
			String methodName) throws Exception {
		element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(jws.JAVAWEBSERVICE_DB_CONFIGURATION_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jws.JAVAWEBSERVICE_DB_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		element.type(jws.JAVAWEBSERVICE_SETTINGS_DB_HOST_VALUE);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_DB_PORT_VALUE);
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
		element.type(jws.JAVAWEBSERVICE_SETTINGS_TYPE_DB_USERNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void AndroidHrbridEshopServerConfig(
			AndroidHybridConstants androidHyb,MobWidgetConstantsXml mobwidg, String methodName) throws Exception {
		element= getXpathWebElement(androidHyb.CREATEDPROJECT_ANDROIDHYBRID);
		waitForElementPresent(androidHyb.CREATEDPROJECT_ANDROIDHYBRID,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_NAME_VALUE);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_HOST_VALUE);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PORT_VALUE);
		Thread.sleep(2000);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PASSWORD_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_DEPLOYDIR_VALUE);
		//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_ESHOP_SERVER_CONTEXT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void iPhoneHybridEshopServerConfig(iPhoneConstantsXml iPhoneConst,MobWidgetConstantsXml mobwidg,
			String methodName) throws Exception {
		element=getXpathWebElement(iPhoneConst.CREATEDPROJECT_iPHONEHYBRID_ESHOP);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_NAME_VALUE);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_HOST_VALUE);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PORT_VALUE);
		Thread.sleep(2000);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PASSWORD_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_DEPLOYDIR_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_ESHOP_SERVER_CONTEXT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void HTML5JqueryWidgetEshopServerConfig(
			JqueryWidgetConstants jquerywidg, String methodName) throws IOException, Exception {
		element=getXpathWebElement(jquerywidg.JQUERY_WIDGET_ESHOP_CREATED_PROJECT_NAME);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(jquerywidg.JQUERY_WIDGET_CONFIG_SERVER_HOST);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(jquerywidg. JQUERY_WIDGET_CONFIG_SERVER_PORT);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(jquerywidg.CONFIGURATIONS_HTML5JQUERY_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(jquerywidg. CONFIGURATIONS_HTML5JQUERY_SERVER_PASSWORD_VALUE);
		
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.type(jquerywidg.HTML5JQUERY_CONFIG_SERVER_DEPLOY_DIR);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.type(jquerywidg. JQUERY_WIDGET_ESHOP_CONFIG_SERVER_CONTEXTNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void MobilewidgetEshopServerConfig(MobWidgetConstantsXml mobwidg,
			String methodName) throws IOException, Exception {
		element= getXpathWebElement(mobwidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
		waitForElementPresent(mobwidg.CREATEDPROJECT_HTML5MOBILEWIDGET,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_NAME_VALUE);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(mobwidg.MOBILEWIDGET_SERVER_CONFIGURATION_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_HOST_VALUE);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PORT_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_USERNAME_CLICK,methodName);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_USERNAME_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_SERVER_PASSWORD_CLICK,methodName);
		element.click();
		element.type(mobwidg. CONFIGURATIONS_MOBILEWIDGET_SERVER_PASSWORD_VALUE);
		
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_SERVER_DEPLOYDIR_VALUE);
		//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(mobwidg.CONFIGURATIONS_MOBILEWIDGET_ESHOP_SERVER_CONTEXT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void Drupal7EshopServerConfig(Drupal7ConstantsXml drupalConst,
			String methodName) throws Exception {
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_NAME_VALUE);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_DESCRIPTION_VALUE);
		/*select(phrsc.CONFIGURATIONS_TYPE,
				phrsc.CONFIGURATIONS_TYPE_SERVER_VALUE);*/
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_SERVER_CLICK,methodName);
		element.click();
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_HOST_VALUE);
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(drupalConst. CONFIGURATIONS_DRUPAL_SERVER_PORT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_DEPLOYDIR);
		element.click();
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_SERVER_DEPLOYDIR_VALUE);
		//type(phrsc. CONFIGURATIONS_SERVER_TYPE_WAMP,phrsc.CONFIGURATIONS_DRUPAL_SERVER_TYPE_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SERVER_CONTEXT);
		element.click();
		element.type(drupalConst.CONFIGURATIONS_DRUPAL7_ESHOP_SERVER_CONTEXT_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void Drupal7EshopDatabaseConfig(Drupal7ConstantsXml drupalConst,
			String methodName) throws IOException, Exception {
		Thread.sleep(1000);
		element= getXpathWebElement(drupalConst.DRUPALPROJ);
		waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS);
		Thread.sleep(1000);
		waitForElementPresent(phrsc.CONFIGURATIONS,methodName);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_NAME,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		Thread.sleep(1000);
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_NAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		waitForElementPresent(phrsc.CONFIGURATIONS_DESCRIPTION,methodName);
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_DESCRIPTION_VALUE);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK,methodName);
		//Thread.sleep(3000);
		element.type(phrsc.CONFIGURATIONS_TYPE_DATABASE_CLICK);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element.click();
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_HOST_VALUE);
		element.click();
		//Thread.sleep(1000);
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element.click();
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_PORT_VALUE);
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_DATABASE_USERNAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(drupalConst.CONFIGURATIONS_DRUPAL_DB_USERNAME_VALUE);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK,methodName);
		Thread.sleep(1000);
		element.type(methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		waitForElementPresent(phrsc.CONFIGURATIONS_SAVE,methodName);
		Thread.sleep(1000);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}

	public void iphonenativeEshopWebServiceConfig(iPhoneConstantsXml iPhoneConst,YuiConstantsXml YuiConst,
			String methodName) throws IOException, Exception {
		
		waitForElementPresent(iPhoneConst.CREATEDPROJECT_iPHONENATIVE_ESHOP,methodName);
		element=getXpathWebElement(iPhoneConst.CREATEDPROJECT_iPHONENATIVE_ESHOP);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		Thread.sleep(2000);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.type(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.click();
		Thread.sleep(2000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_HOST);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_PORT);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_CONTEXTNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}
	
	public void androidNativeEshopWebServiceConfig(AndroidNativeConstants androidNat,YuiConstantsXml YuiConst,
			String methodName) throws IOException, Exception {
		
		waitForElementPresent(androidNat.CREATEDPROJECT_ANDROIDENATIVE_ESHOP,methodName);
		element=getXpathWebElement(androidNat.CREATEDPROJECT_ANDROIDENATIVE_ESHOP);
		element.click();
		Thread.sleep(3000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS);
		element.click();
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_ADD);
		element.click();
		Thread.sleep(2000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_NAME);
		element.click();
		Thread.sleep(1000);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_NAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_DESCRIPTION);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_DESCRIPTION);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE,methodName);
		element.click();
		Thread.sleep(1000);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.type(phrsc.CONFIGURATIONS_TYPE_WEBSERVICE_CLICK);
		element.click();
		Thread.sleep(3000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_PROTOCOL_TYPE,methodName);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(1000);
		element= getXpathWebElement(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		waitForElementPresent(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK,methodName);
		element.type(phrsc.CONFIGURATIONS_TYPE_HTTP_CLICK);
		element.click();
		Thread.sleep(3000);
		waitForElementPresent(phrsc.CONFIGURATIONS_HOST,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_HOST);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_HOST);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_PORT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_PORT);
		element.type(YuiConst. HTML5_WIDGET_CONFIG_WEBSERVICE_PORT);
		Thread.sleep(2000);
		waitForElementPresent(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT,methodName);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_WEBSERVICE_CONTEXT);
		element.type(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_CONTEXTNAME);
		element=getXpathWebElement(phrsc.CONFIGURATIONS_SAVE);
		element.click();
		waitForElementPresent(phrsc.CONFIGURATIONS_ADD,methodName);
		
	}
	
}
