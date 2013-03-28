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
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
import com.photon.phresco.uiconstants.PhrescoEnvConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;

import junit.framework.TestCase;


public class Android_Hybrid extends TestCase{

	private PhrescoEnvConstants phrscenv;
	private AndroidHybridConstants androidHyb;
	private MobWidgetConstantsXml mobwidg;
	
	private PhrescoUiConstantsXml phrsc;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;

	
	@Test
	public void test_Create_AndroidHybrid_None() throws InterruptedException,
			IOException, Exception {

		androidHyb = new AndroidHybridConstants();
		 phrscenv=new PhrescoEnvConstants();
			String serverURL = phrscenv.PROTOCOL + "://" + phrscenv.HOST + ":"
					+ phrscenv.PORT + "/";
			browserAppends = "*" + phrscenv.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrscenv.PORT);
			assertNotNull("selenium-port number should not be null",
					SELENIUM_PORT);
			wel = new WelcomeScreen(phrscenv.HOST,
					SELENIUM_PORT, browserAppends, serverURL, phrscenv.SPEED,
					phrscenv.CONTEXT);
		assertNotNull(wel);
		methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName = " + methodName);
		loginObject = new LoginScreen(phrsc);
		PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
		                   phrescoHome.goToPhrescoHomePage(methodName);
	    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
		AddApplicationScreen addappscrn = applicationScr
				.gotoAddApplicationScreen(methodName);
		addappscrn.createProjAndroidHybridNone(androidHyb,methodName);
	}
	
	 @Test
	 	public void test_Config_AndroidHrbrid_None() throws InterruptedException, IOException,
	 			Exception {

	    	 androidHyb = new AndroidHybridConstants();
	    	 phrscenv=new PhrescoEnvConstants();
				String serverURL = phrscenv.PROTOCOL + "://" + phrscenv.HOST + ":"
						+ phrscenv.PORT + "/";
				browserAppends = "*" + phrscenv.BROWSER;
				assertNotNull("Browser name should not be null", browserAppends);
				SELENIUM_PORT = Integer.parseInt(phrscenv.PORT);
				assertNotNull("selenium-port number should not be null",
						SELENIUM_PORT);
				wel = new WelcomeScreen(phrscenv.HOST,
						SELENIUM_PORT, browserAppends, serverURL, phrscenv.SPEED,
						phrscenv.CONTEXT);
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
		public void test_Create_AndroidHybrid_EShop() throws InterruptedException,
				IOException, Exception {
	
			androidHyb = new AndroidHybridConstants();
			 phrscenv=new PhrescoEnvConstants();
				String serverURL = phrscenv.PROTOCOL + "://" + phrscenv.HOST + ":"
						+ phrscenv.PORT + "/";
				browserAppends = "*" + phrscenv.BROWSER;
				assertNotNull("Browser name should not be null", browserAppends);
				SELENIUM_PORT = Integer.parseInt(phrscenv.PORT);
				assertNotNull("selenium-port number should not be null",
						SELENIUM_PORT);
				wel = new WelcomeScreen(phrscenv.HOST,
						SELENIUM_PORT, browserAppends, serverURL, phrscenv.SPEED,
						phrscenv.CONTEXT);
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
			addappscrn.createProjAndroidHybridEshop(androidHyb,methodName);
		}
	 
	 @Test
	 	public void test_Config_AndroidHrbrid_Eshop() throws InterruptedException, IOException,
	 			Exception {

	    	 androidHyb = new AndroidHybridConstants();
	    	 mobwidg = new MobWidgetConstantsXml();
	    	 phrscenv=new PhrescoEnvConstants();
				String serverURL = phrscenv.PROTOCOL + "://" + phrscenv.HOST + ":"
						+ phrscenv.PORT + "/";
				browserAppends = "*" + phrscenv.BROWSER;
				assertNotNull("Browser name should not be null", browserAppends);
				SELENIUM_PORT = Integer.parseInt(phrscenv.PORT);
				assertNotNull("selenium-port number should not be null",
						SELENIUM_PORT);
				wel = new WelcomeScreen(phrscenv.HOST,
						SELENIUM_PORT, browserAppends, serverURL, phrscenv.SPEED,
						phrscenv.CONTEXT);
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
	 		config.AndroidHrbridEshopServerConfig(androidHyb,mobwidg,methodName);
	 		
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
