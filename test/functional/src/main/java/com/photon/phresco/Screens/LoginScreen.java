/**
 * Phresco Framework Root
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;

import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;

public class LoginScreen extends WebDriverAbstractBaseScreen {
	private PhrescoUiConstantsXml phrsc;
	private Log log = LogFactory.getLog(getClass());
	public WebDriverBaseScreen element;

	public LoginScreen(PhrescoUiConstantsXml phrescoConstant)
			throws InterruptedException, IOException, Exception {
		log.info("@LoginScreen::*****constructor******");
		this.phrsc = phrescoConstant;
	}

	public PhrescoWelcomePage testLoginPage(String methodName) throws Exception {
		try {
			log.info("@testLoginPage::******executing loginpage scenario****");
			Thread.sleep(2000);
			element=getXpathWebElement(phrsc.USER_NAME_XPATH);
			waitForElementPresent(phrsc.USER_NAME_XPATH,methodName);
			element.type(phrsc.USER_ID);
			
			element=getXpathWebElement(phrsc.PASSWORD_XPATH);
			waitForElementPresent(phrsc.PASSWORD_XPATH,methodName);
			element.type(phrsc.PASSWORD);
			
			element=getXpathWebElement(phrsc.REMEMBER_ME_CHECK);
			waitForElementPresent(phrsc.REMEMBER_ME_CHECK,methodName);
			element.click();

			element=getXpathWebElement(phrsc.LOGIN_BUTTON);
			waitForElementPresent(phrsc.LOGIN_BUTTON,methodName);
			element.click();
			
			waitForTextPresent(phrsc.WELCOME_TO_PHRESCO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PhrescoWelcomePage(phrsc);
		
	}
	
	
	

}
	

	

