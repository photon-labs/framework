package com.photon.phresco.Screens;

import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

public class ApplicationsScreen extends WebDriverAbstractBaseScreen {
	private PhrescoUiConstantsXml phrescoConst;
	public WebDriverBaseScreen element;
	

	public ApplicationsScreen(PhrescoUiConstantsXml phrescoConst) {
		this.phrescoConst = phrescoConst;
	}

	public AddApplicationScreen gotoAddApplicationScreen(String methodName)
			throws Exception {
		element=getXpathWebElement(phrescoConst.ADD_APPLICATION_BUTTON);
		waitForElementPresent(phrescoConst.ADD_APPLICATION_BUTTON,methodName);
		element.click();
		waitForElementPresent(phrescoConst.APPINFO_NAME,methodName);
		//successFailureLoop_isButtonEnabled(phrescoConst.APPINFO_NEXT);
		return new AddApplicationScreen(phrescoConst);
	}
	
	

	public void gotoImportFromSVN() throws ScreenException {

	}
	
	
	public void deleteAll_Projects(String methodName) throws Exception {
	      waitForElementPresent(phrescoConst.ADD_APPLICATION_BUTTON,methodName);
	      waitForElementPresent(phrescoConst.APPINFO_SELECT_ALL_PROJECTS,methodName);
	      element=getXpathWebElement(phrescoConst.APPINFO_SELECT_ALL_PROJECTS);
    		element.click();
		
		}
	
	
	
	
}
