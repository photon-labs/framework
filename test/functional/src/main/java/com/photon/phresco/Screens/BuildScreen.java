	package com.photon.phresco.Screens;
	
	import java.io.IOException;
	
	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
	
	import com.photon.phresco.selenium.util.GetCurrentDir;
	import com.photon.phresco.selenium.util.ScreenException;
	import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
	import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
	import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
	import com.photon.phresco.uiconstants.PhpConstantsXml;
	//import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.Drupal6ConstantsXml;
import com.photon.phresco.uiconstants.JavaStandaloneXml;
import com.photon.phresco.uiconstants.JqueryWidgetConstants;
	import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
	import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.WordPressConstants;
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
			
			
			if (isTextPresentBuild("BUILD SUCCESS")) {
				System.out.println("*****OPERATION SUCCEEDED*****");
			} /*else if (isTextPresent("BUILD FAILURE")) {
				log.info("@sucessFailureLoop: failure");
				selenium.captureEntirePageScreenshot(
						GetCurrentDir.getCurrentDirectory() + "\\DeployFailure.png",
						"background=#CCFFDD");
				throw new ScreenException("*****OPERATION FAILED*****");
			}*/
	
		}
		
		public void successFailureLoopRunAganstSource() throws InterruptedException, IOException,
		Exception {
	
			for(int i=0;i<90;i++){
			if(driver.findElement(By.xpath(phrsc.NODEJS_BUILD_STOP_BTN)).isEnabled()){
				System.out.println("-------isEnabled()----------------");
			break;	
			}else{
     		   if(i==89){
    			   throw new RuntimeException("---- Time out for finding the Text----");
    		   }else if(!driver.findElement(By.xpath(phrsc.NODEJS_BUILD_STOP_BTN)).isSelected()){
    			   //System.out.println("-----------isSelected()-----------------");
    			   Thread.sleep(1000);
    		   }
    		  
     		 // Thread.sleep(1000);
    	   }
			
			}

}
	
		/*public void NodeJs_Build(NodeJSConstantsXml nodejsconst,String methodName) throws InterruptedException, IOException,
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
			element= getXpathWebElement(nodejsconst.NODEJS_CONFIG_CHOOSE_SERVER_NAME);
			waitForElementPresent(nodejsconst.NODEJS_CONFIG_CHOOSE_SERVER_NAME,methodName);
			element.click();
			element= getXpathWebElement(nodejsconst.NODEJS_CONFIG_CHOOSE_DB_NAME);
			waitForElementPresent(nodejsconst.NODEJS_CONFIG_CHOOSE_DB_NAME,methodName);
			element.click();
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			waitForElementPresent(nodejsconst.NODEJS_GENERATE_BUILD_DOWNLOAD,methodName);
			successFailureLoop();
	
		}*/
		
		public void NodeJsNoneRunAgainstSrc(NodeJSConstantsXml nodejsconst,String methodName) throws InterruptedException,
		IOException, Exception {
	waitForElementPresent(nodejsconst.NODEJS_PROJECT_CREATION_NONE,methodName);
	element= getXpathWebElement(nodejsconst.NODEJS_PROJECT_CREATION_NONE);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_BTN);
	waitForElementPresent(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_BTN,methodName);
	element.click();
	Thread.sleep(5000);
/*	element= getXpathWebElement(phrsc.DEPLOY_POPUP_EXECUTE_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_EXECUTE_SQL,methodName);
	element.click();
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
	element.click();*/
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
	element.click();
	Thread.sleep(2000);
	waitForElementPresent(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_RUN_BTN,methodName);
	element= getXpathWebElement(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_RUN_BTN);
	element.click();
	
	successFailureLoopRunAganstSource();
	waitForElementPresent(phrsc.NODEJS_BUILD_STOP_BTN,methodName);
	Thread.sleep(3000);

}
	
		public void NodeJsEshopRunAgainstSrc(NodeJSConstantsXml nodejsconst,String methodName) throws InterruptedException,
				IOException, Exception {
			waitForElementPresent(nodejsconst.NODEJS_PROJECT_CREATION_ID,methodName);
	element= getXpathWebElement(nodejsconst.NODEJS_PROJECT_CREATION_ID);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_BTN);
	waitForElementPresent(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_BTN,methodName);
	element.click();
	Thread.sleep(5000);
/*	element= getXpathWebElement(phrsc.DEPLOY_POPUP_EXECUTE_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_EXECUTE_SQL,methodName);
	element.click();
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
	element.click();*/
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
	element.click();
	Thread.sleep(2000);
	waitForElementPresent(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_RUN_BTN,methodName);
	element= getXpathWebElement(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_RUN_BTN);
	element.click();
	
	successFailureLoopRunAganstSource();
	waitForElementPresent(phrsc.NODEJS_BUILD_STOP_BTN,methodName);
	Thread.sleep(3000);

	
		}
	
		public void HTML5WidgetNoneBuild(YuiConstantsXml YuiConst,String methodName) throws InterruptedException, IOException,
				Exception {
			element=getXpathWebElement(YuiConst.HTML5_WIDGET_NONE_PROJECT_CREATED);
			waitForElementPresent(YuiConst.HTML5_WIDGET_NONE_PROJECT_CREATED,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			
			successFailureLoop();
			Thread.sleep(2000);
			//waitForElementPresent(phrsc.DEPLOY,methodName);
			
			
	
		}
		
		
public void  HTML5WidgetNonedeploy(YuiConstantsXml YuiConst,String methodName) throws Exception {
			
			element= getXpathWebElement(YuiConst.HTML5_WIDGET_NONE_PROJECT_CREATED);
			waitForElementPresent(YuiConst.HTML5_WIDGET_NONE_PROJECT_CREATED,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			Thread.sleep(2000);
			successFailureLoop();
			
		}

public void HTML5WidgetEshopBuild(YuiConstantsXml YuiConst,String methodName) throws InterruptedException, IOException,
Exception {
element=getXpathWebElement(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ);
waitForElementPresent(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ,methodName);
element.click();
Thread.sleep(5000);
element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.GENERATEBUILD);
waitForElementPresent(phrsc.GENERATEBUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
element.click();
Thread.sleep(2000);
element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
element.click();

successFailureLoop();
Thread.sleep(2000);
//waitForElementPresent(phrsc.DEPLOY,methodName);



}

public void  HTML5WidgetEshopdeploy(YuiConstantsXml YuiConst,String methodName) throws Exception {
	
	element= getXpathWebElement(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ);
	waitForElementPresent(YuiConst.HTML5_WIDGET_PROJECT_CREATED_PROJ,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.DEPLOY);
	waitForElementPresent(phrsc.DEPLOY,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
	waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
	element.click();
	successFailureLoop();
	
}
		
		
	
		/*public void HTML5Widget_Eshop_Deploy(YuiConstantsXml YuiConst,String methodName) throws InterruptedException, IOException,
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
			
	
		}*/
	
		public void PhpNoneBuild(PhpConstantsXml phpconst,String methodName) throws InterruptedException,
				IOException, Exception {
			element=getXpathWebElement(phpconst.PHP_NONE_PROJECT);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			
			successFailureLoop();
			Thread.sleep(2000);
			//waitForElementPresent(phrsc.DEPLOY,methodName);
		}
		
public void  PhpNonedeploy(PhpConstantsXml phpconst,String methodName) throws Exception {
			
			element= getXpathWebElement(phpconst.PHP_NONE_PROJECT);
			waitForElementPresent(phpconst.PHP_NONE_PROJECT,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			/*element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
			element.click();*/
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}

public void PhpBlogBuild(PhpConstantsXml phpconst,String methodName) throws InterruptedException,
IOException, Exception {
element=getXpathWebElement(phpconst.PHPPROJECT);
element.click();
Thread.sleep(5000);
element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.GENERATEBUILD);
waitForElementPresent(phrsc.GENERATEBUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
element.click();
Thread.sleep(2000);
element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
element.click();

successFailureLoop();
Thread.sleep(2000);
//waitForElementPresent(phrsc.DEPLOY,methodName);
}

public void  PhpBlogdeploy(PhpConstantsXml phpconst,String methodName) throws Exception {
	
	element= getXpathWebElement(phpconst.PHPPROJECT);
	waitForElementPresent(phpconst.PHPPROJECT,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.DEPLOY);
	waitForElementPresent(phrsc.DEPLOY,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	/*element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
	element.click();*/
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
	element.click();
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
	waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
	element.click();
	successFailureLoop();
	
}

		
		
		public void WordPressBuild(WordPressConstants wordpressConst,String methodName) throws InterruptedException,
		IOException, Exception {
	element=getXpathWebElement(wordpressConst.CREATEDPROJECT_WORDPRESS);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);
}
		
public void  WordPressdeploy(WordPressConstants wordpressConst,String methodName) throws Exception {
			
			element= getXpathWebElement(wordpressConst.CREATEDPROJECT_WORDPRESS);
			waitForElementPresent(wordpressConst.CREATEDPROJECT_WORDPRESS,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
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
	
		
		
		public void JavaWebServiceBuildNone(JavaWebServConstantsXml jws,String methodName)
		throws InterruptedException, IOException, Exception {

	element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE_NONE);
	waitForElementPresent(jws.CREATEDPROJECT_JAVAWEBSERVICE_NONE,methodName);
    element.click();
    Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);


}
		
		public void JavaWebServiceDeployNone(JavaWebServConstantsXml jws,String methodName)
		throws Exception {

	element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE_NONE);
	waitForElementPresent(jws.CREATEDPROJECT_JAVAWEBSERVICE_NONE,methodName);
    element.click();
    Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.DEPLOY);
	waitForElementPresent(phrsc.DEPLOY,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	/*element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
	element.click();*/
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
	waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
	element.click();
	successFailureLoop();
	
}
		public void JavaWebServiceBuildEshop(JavaWebServConstantsXml jws,String methodName)
		throws InterruptedException, IOException, Exception {

	element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE);
	waitForElementPresent(jws.CREATEDPROJECT_JAVAWEBSERVICE,methodName);
    element.click();
    Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);


}
		
		public void JavaWebServiceDeployEshop(JavaWebServConstantsXml jws,String methodName)
		throws Exception {

	element=getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE);
	waitForElementPresent(jws.CREATEDPROJECT_JAVAWEBSERVICE,methodName);
    element.click();
    Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(4000);
	element= getXpathWebElement(phrsc.DEPLOY);
	waitForElementPresent(phrsc.DEPLOY,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	/*element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
	element.click();*/
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
	waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
	waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
	element.click();
	successFailureLoop();
	
}
		
		
		
		public void JavaWebServiceNoneRunAgainstSrc(JavaWebServConstantsXml jws,String methodName) throws InterruptedException,
		IOException, Exception {
	waitForElementPresent(jws.CREATEDPROJECT_JAVAWEBSERVICE_NONE,methodName);
	element= getXpathWebElement(jws.CREATEDPROJECT_JAVAWEBSERVICE_NONE);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	waitForElementPresent(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_BTN,methodName);
	element.click();
	waitForElementPresent(phrsc.NODEJS_BUILD_RUN_AGAINST_SRC_RUN_BTN,methodName);
	element.click();
	waitForElementPresent(phrsc.NODEJS_BUILD_STOP_BTN,methodName);

}
	
		/*public void JavaWebServiceDeployEshop(JavaWebServConstantsXml jws,String methodName)
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
			
		}*/
	
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
	
		public void Drupal7NoneBuild(Drupal7ConstantsXml drupalConst,String methodName)
				throws InterruptedException, IOException, Exception {
	
			element= getXpathWebElement(drupalConst.DRUPAL7_NONE_PROJ);
			waitForElementPresent(drupalConst.DRUPAL7_NONE_PROJ,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			
			successFailureLoop();
			Thread.sleep(2000);
			//waitForElementPresent(phrsc.DEPLOY,methodName);

		}
	
		public void drupal7Nonedeploy(Drupal7ConstantsXml drupalConst,String methodName) throws Exception {
	
			element= getXpathWebElement(drupalConst.DRUPAL7_NONE_PROJ);
			waitForElementPresent(drupalConst.DRUPAL7_NONE_PROJ,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(1000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}
		
		public void Drupal7EshopBuild(Drupal7ConstantsXml drupalConst,String methodName)
		throws InterruptedException, IOException, Exception {

	element= getXpathWebElement(drupalConst.DRUPALPROJ);
	waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);

}
		
		public void drupal7Eshopdeploy(Drupal7ConstantsXml drupalConst,String methodName) throws Exception {
			
			element= getXpathWebElement(drupalConst.DRUPALPROJ);
			waitForElementPresent(drupalConst.DRUPALPROJ,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(1000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
			element.click();
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}

		
		public void Drupal6NoneBuild(Drupal6ConstantsXml drupal6Const,String methodName)
		throws InterruptedException, IOException, Exception {

	element= getXpathWebElement(drupal6Const.drupal6PROJ);
	waitForElementPresent(drupal6Const.drupal6PROJ,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);

}
		
		public void Drupal6Nonedeploy(Drupal6ConstantsXml drupal6Const,String methodName) throws Exception {
			
			element= getXpathWebElement(drupal6Const.drupal6PROJ);
			waitForElementPresent(drupal6Const.drupal6PROJ,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			/*element= getXpathWebElement(phrsc.DEPLOY_POPUP_SELECT_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_SELECT_SQL,methodName);
			element.click();*/
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_MOVE_SQL);
			waitForElementPresent(phrsc.DEPLOY_POPUP_MOVE_SQL,methodName);
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
		/*public void deployPHP(PhpConstantsXml phpconst,String methodName) throws Exception {
	
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
	*/
		public void MobilewidgetNoneBuild(MobWidgetConstantsXml mobileWidg,String methodName)
				throws Exception {
			
			element=getXpathWebElement(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGETNONE);
			waitForElementPresent(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGETNONE,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.GENERATEBUILD);
			waitForElementPresent(phrsc.GENERATEBUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
			element.click();
			
			successFailureLoop();
			Thread.sleep(2000);
			//waitForElementPresent(phrsc.DEPLOY,methodName);

			
		}
		
		
		
public void MobilewidgetNonedeploy(MobWidgetConstantsXml mobileWidg,String methodName) throws Exception {
			
			element= getXpathWebElement(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGETNONE);
			waitForElementPresent(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGETNONE,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}

public void MobilewidgetEshopBuild(MobWidgetConstantsXml mobileWidg,String methodName)
throws Exception {

element=getXpathWebElement(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
waitForElementPresent(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET,methodName);
element.click();
Thread.sleep(5000);
element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.GENERATEBUILD);
waitForElementPresent(phrsc.GENERATEBUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
element.click();
Thread.sleep(2000);
element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
element.click();

successFailureLoop();
Thread.sleep(2000);
//waitForElementPresent(phrsc.DEPLOY,methodName);


}

public void MobilewidgetEshopdeploy(MobWidgetConstantsXml mobileWidg,String methodName) throws Exception {
	
	element= getXpathWebElement(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
	waitForElementPresent(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.DEPLOY);
	waitForElementPresent(phrsc.DEPLOY,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
	waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
	element.click();
	successFailureLoop();
	
}

		
		public void JqueryWidgetNoneBuild(JqueryWidgetConstants jquerywidg,String methodName)
		throws Exception {
	
	element=getXpathWebElement(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ);
	waitForElementPresent(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);

	
}
		
public void  JqueryWidgetNonedeploy(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
			
			element= getXpathWebElement(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ);
			waitForElementPresent(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}

public void JqueryWidgetEshopBuild(JqueryWidgetConstants jquerywidg,String methodName)
throws Exception {

element=getXpathWebElement(jquerywidg.JQUERY_WIDGET_ESHOP_CREATED_PROJECT_NAME);
waitForElementPresent(jquerywidg.JQUERY_WIDGET_ESHOP_CREATED_PROJECT_NAME,methodName);
element.click();
Thread.sleep(5000);
element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.GENERATEBUILD);
waitForElementPresent(phrsc.GENERATEBUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
element.click();
element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
element.click();

successFailureLoop();
Thread.sleep(2000);
//waitForElementPresent(phrsc.DEPLOY,methodName);


}

public void  JqueryWidgetEshopdeploy(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
	
	element= getXpathWebElement(jquerywidg.JQUERY_WIDGET_ESHOP_CREATED_PROJECT_NAME);
	waitForElementPresent(jquerywidg.JQUERY_WIDGET_ESHOP_CREATED_PROJECT_NAME,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.DEPLOY);
	waitForElementPresent(phrsc.DEPLOY,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
	waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
	element.click();
	successFailureLoop();
	
}

		
		public void JqueryMobileWidgetNoneBuild(JqueryWidgetConstants jquerywidg,String methodName)
		throws Exception {
	
	element=getXpathWebElement(jquerywidg.JQUERY_MOBILE_WIDGET_NONE_PROJECT_CREATED_PROJ);
	waitForElementPresent(jquerywidg.JQUERY_MOBILE_WIDGET_NONE_PROJECT_CREATED_PROJ,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);

	
}

public void  JqueryMobileWidgetNonedeploy(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
			
			element= getXpathWebElement(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ);
			waitForElementPresent(jquerywidg.JQUERY_WIDGET_NONE_PROJECT_CREATED_PROJ,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
			element.click();
			Thread.sleep(3000);
			element= getXpathWebElement(phrsc.DEPLOY);
			waitForElementPresent(phrsc.DEPLOY,methodName);
			element.click();
			Thread.sleep(5000);
			element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
			waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
			element.click();
			Thread.sleep(2000);
			element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
			waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
			element.click();
			successFailureLoop();
			
		}
		
public void JqueryMobileWidgetEshopBuild(JqueryWidgetConstants jquerywidg,String methodName)
throws Exception {

element=getXpathWebElement(jquerywidg.JQUERY_MOBILE_WIDGET_ESHOP_PROJECT_CREATED_PROJ);
waitForElementPresent(jquerywidg.JQUERY_MOBILE_WIDGET_ESHOP_PROJECT_CREATED_PROJ,methodName);
element.click();
Thread.sleep(5000);
element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.GENERATEBUILD);
waitForElementPresent(phrsc.GENERATEBUILD,methodName);
element.click();
Thread.sleep(3000);
element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
element.click();
Thread.sleep(2000);
element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
element.click();

successFailureLoop();
Thread.sleep(2000);
//waitForElementPresent(phrsc.DEPLOY,methodName);


}

public void  JqueryMobileWidgetEshopdeploy(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
	
	element= getXpathWebElement(jquerywidg.JQUERY_MOBILE_WIDGET_ESHOP_PROJECT_CREATED_PROJ);
	waitForElementPresent(jquerywidg.JQUERY_MOBILE_WIDGET_ESHOP_PROJECT_CREATED_PROJ,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.DEPLOY);
	waitForElementPresent(phrsc.DEPLOY,methodName);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	Thread.sleep(2000);
	element= getXpathWebElement(phrsc.DEPLOY_POPUP_DEPLOY);
	waitForElementPresent(phrsc.DEPLOY_POPUP_DEPLOY,methodName);
	element.click();
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
		
		public void javastandaloneBuild(JavaStandaloneXml javastandalone,String methodName) throws InterruptedException,
		IOException, Exception {
	element=getXpathWebElement(javastandalone.CREATEDPROJECT_JAVASTANDALONE);
	element.click();
	Thread.sleep(5000);
	element= getXpathWebElement(phrsc.EDITAPPLICATION_BUILD);
	waitForElementPresent(phrsc.EDITAPPLICATION_BUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.GENERATEBUILD);
	waitForElementPresent(phrsc.GENERATEBUILD,methodName);
	element.click();
	Thread.sleep(3000);
	element= getXpathWebElement(phrsc.BUILD_SHOWERROR);
	waitForElementPresent(phrsc.BUILD_SHOWERROR,methodName);
	element.click();
	element= getXpathWebElement(phrsc.ENVIRONMENT_BUILD);
	waitForElementPresent(phrsc.ENVIRONMENT_BUILD,methodName);
	element.click();
	
	successFailureLoop();
	Thread.sleep(2000);
	//waitForElementPresent(phrsc.DEPLOY,methodName);
}
	
	
	
	}
