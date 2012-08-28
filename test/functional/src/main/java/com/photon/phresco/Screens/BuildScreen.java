	package com.photon.phresco.Screens;
	
	import java.io.IOException;
	
	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
	
	import com.photon.phresco.selenium.util.GetCurrentDir;
	import com.photon.phresco.selenium.util.ScreenException;
	import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
	import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
	import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
	import com.photon.phresco.uiconstants.PhpConstantsXml;
	//import com.photon.phresco.uiconstants.PhrescoUiConstants;
	import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
	import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.iPhoneConstantsXml;
import com.photon.phresco.uiconstants.NodeJSConstantsXml;
import com.photon.phresco.uiconstants.YuiConstantsXml;

	public class BuildScreen extends WebDriverAbstractBaseScreen {
		//private PhrescoUiConstantsXml phrsc;
		private Log log = LogFactory.getLog(getClass());
		PhrescoUiConstantsXml phrsc = new PhrescoUiConstantsXml();
		public WebDriverBaseScreen element;
	
		public BuildScreen(PhrescoUiConstantsXml phrescoConst) {
			this.phrsc = phrescoConst;
		}
	
		public void successFailureLoop() throws InterruptedException, IOException,
				Exception {
			if (isTextPresent("BUILD SUCCESS")) {
				System.out.println("*****OPERATION SUCCEEDED*****");
			} else if (isTextPresent("BUILD FAILURE")) {
				log.info("@sucessFailureLoop: failure");
				selenium.captureEntirePageScreenshot(
						GetCurrentDir.getCurrentDirectory() + "\\DeployFailure.png",
						"background=#CCFFDD");
				throw new ScreenException("*****OPERATION FAILED*****");
			}
	
		}
	
		public void NodeJs_Build(NodeJSConstantsXml nodejsconst,String methodName) throws InterruptedException, IOException,
				Exception {
			waitForElementPresent(nodejsconst.NODEJS_PROJECT_CREATION_ID,methodName);
			element= getXpathWebElement(nodejsconst.NODEJS_PROJECT_CREATION_ID);
			element.click();
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			element.click();
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			element.click();
			/*element= getXpathWebElement(nodejsconst.NODEJS_CONFIG_CHOOSE_SERVER_NAME);
			waitForElementPresent(nodejsconst.NODEJS_CONFIG_CHOOSE_SERVER_NAME,methodName);
			element.click();
			element= getXpathWebElement(nodejsconst.NODEJS_CONFIG_CHOOSE_DB_NAME);
			waitForElementPresent(nodejsconst.NODEJS_CONFIG_CHOOSE_DB_NAME,methodName);
			element.click();*/
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(nodejsconst.NODEJS_GENERATE_BUILD_DOWNLOAD,methodName);
			successFailureLoop();
	
		}
	
		/*public void NodeJs_RunAgainstSrc(NodeJSConstantsXml nodejsconst,String methodName) throws InterruptedException,
				IOException, Exception {
			waitForElementPresent(nodejsconst.NODEJS_PROJECT_CREATION_ID,methodName);
			element= getXpathWebElement(nodejsconst.NODEJS_PROJECT_CREATION_ID);
			element.click();
			
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.NODEJS_BUILD_RUNAGAINSTSRC_BTN);
			click(phrsc.SERVER);
			select(phrsc.SERVER, phrsc.NODEJS_CONFIG_CHOOSE_SERVER_NAME);
			select(phrsc.DATABASE, phrsc.NODEJS_CONFIG_CHOOSE_DB_NAME);
			click(phrsc.NODEJS_BUILD_RUN_BTN);
			waitForElementPresent(phrsc.NODEJS_BUILD_STOP_BTN);
	
		}*/
	
		public void HTML5Widget_Build(YuiConstantsXml YuiConst,String methodName) throws InterruptedException, IOException,
				Exception {
			element=getXpathWebElement(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ);
			waitForElementPresent(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ,methodName);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			//element= getXpathWebElement(phrsc.GENERATEBUILD);
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			element.click();
			/*element= getXpathWebElement(YuiConst.HTML5_WIDGET_CONFIG_SERVER_NAME_CLICK);
			waitForElementPresent(YuiConst.HTML5_WIDGET_CONFIG_SERVER_NAME_CLICK,methodName);
			element.click();
			element= getXpathWebElement(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_NAME_CLICK);
			waitForElementPresent(YuiConst.HTML5_WIDGET_CONFIG_WEBSERVICE_NAME_CLICK,methodName);
			element.click();*/
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(phrsc.DEPLOY,methodName);
			successFailureLoop();
	
		}
	
		public void HTML5Widget_Deploy(YuiConstantsXml YuiConst,String methodName) throws InterruptedException, IOException,
				Exception {
			element=getXpathWebElement(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
	
		}
	
		public void PhpBuild(PhpConstantsXml phpconst,String methodName) throws InterruptedException,
				IOException, Exception {
			element=getXpathWebElement(phpconst.PHPPROJECT);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			/*element= getXpathWebElement(phpconst.PHP_SERVERCONFIG);
			waitForElementPresent(phpconst.PHP_SERVERCONFIG,methodName);
			element.click();
			element= getXpathWebElement(phpconst.PHP_DBCONFIG);
			waitForElementPresent(phpconst.PHP_DBCONFIG,methodName);
			element.click();*/
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(phrsc.DEPLOY,methodName);
			successFailureLoop();

		}
	
		public void SharepointBuild(SharepointConstantsXml spConst,String methodName)
				throws InterruptedException, IOException, Exception {
			element=getXpathWebElement(spConst.CREATEDPROJECT_SHAREPOINT);
			waitForElementPresent(spConst.CREATEDPROJECT_SHAREPOINT,methodName);
		    element.click();
		    element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(phrsc.DEPLOY,methodName);
			successFailureLoop();
	
		}
	
		public void SharepointDeploy(SharepointConstantsXml spConst,String methodName)
				throws Exception {
	        
			element=getXpathWebElement(spConst.CREATEDPROJECT_SHAREPOINT);
			waitForElementPresent(spConst.CREATEDPROJECT_SHAREPOINT,methodName);
		    element.click();
		    element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
			
			
		}
	
		public void JavaWebServiceBuild(JavaWebServConstantsXml jws,String methodName)
				throws InterruptedException, IOException, Exception {
	
			element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE);
			waitForElementPresent(jws.CREATEDPROJECT_JAVAWEBSERVICE,methodName);
		    element.click();
		    element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(phrsc.DEPLOY,methodName);
			successFailureLoop();

	
		}
	
		public void JavaWebServiceDeploy(JavaWebServConstantsXml jws,String methodName)
				throws Exception {
	
			element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE);
			waitForElementPresent(jws.CREATEDPROJECT_JAVAWEBSERVICE,methodName);
		    element.click();
		    element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}
	
		/*public void JavaWebService_RunAgainstSrc(JavaWebServConstantsXml jws)
				throws InterruptedException, IOException, Exception {
			click(jws.CREATEDPROJECT_JAVAWEBSERVICE);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(jws.JAVAWEBSERVICE_BUILD_RUNAGAINSTSRC_BTN);
			click(phrsc.SERVER);
			select(phrsc.SERVER, jws.MYJAVAWERSERVICESERVER);
			select(phrsc.DATABASE, jws.MYJAVAWERSERVICEDB);
			click(jws.JAVAWEBSERVICE_BUILD_RUN_BTN);
			waitForElementPresent(jws.JAVAWEBSERVICE_BUILD_STOP_BTN);
	
		}*/
	
		public void DrupalBuild(Drupal7ConstantsXml drupalConst,String methodName)
				throws InterruptedException, IOException, Exception {
	
			element= getXpathWebElement(drupalConst.DRUPALPROJ);
			waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(phrsc.DEPLOY,methodName);
			successFailureLoop();
		}
	
		public void drupaldeploy(Drupal7ConstantsXml drupalConst,String methodName) throws Exception {
	
			element= getXpathWebElement(drupalConst.DRUPALPROJ);
			waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}
	
		/*public void iphonenativeBuild(iPhoneConstantsXml iPhoneConst)
				throws Exception {
			click(iPhoneConst.CREATEDPROJECT_iPHONENATIVE);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			// click(phrsc.SERVER);
			select(phrsc.WEBSERVICE,
					iPhoneConst.iPHONENATIVE_GENERATE_BUILD_WEBSERVICE);
			click(iPhoneConst.iPHONENATIVE_GENERATE_BUILD_WEBSERVICE_CLICK);
			select(iPhoneConst.GENERATE_BUILD_SDK,
					iPhoneConst.iPHONENATIVE_GENERATE_BUILD_SIMULATOR);
			click(iPhoneConst.iPHONENATIVE_GENERATE_BUILD_SIMULATOR_CLICK);
			// click(phrsc.DATABASE);
			click(phrsc.ENVIRONMENT_BUILD);
			successFailureLoop();
		}
	
		public void iphonenativedeploy(iPhoneConstantsXml iPhoneConst)
				throws Exception {
			click(iPhoneConst.CREATEDPROJECT_iPHONENATIVE);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
			click(phrsc.DEPLOY);
			click(iPhoneConst.iPHONENATIVE_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK);
			successFailureLoop();
	
		}
	*/
		public void deployPHP(PhpConstantsXml phpconst,String methodName) throws Exception {
	
			element=getXpathWebElement(phpconst.PHPPROJECT);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
			
		}
	
		public void MobilewidgetBuild(MobWidgetConstantsXml mobileWidg,String methodName)
				throws Exception {
			
			element=getXpathWebElement(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
			waitForElementPresent(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET,methodName);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(phrsc.DEPLOY,methodName);
			successFailureLoop();
			
		}
	
		public void Mobilewidgetdeploy(MobWidgetConstantsXml mobileWidg,String methodName) throws Exception {
			
			element=getXpathWebElement(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
			waitForElementPresent(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET,methodName);
			element.click();
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}
	
		public void Validation() throws Exception {
	
		}
	
	}
