package com.photon.phresco.Scenarios;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.ScenariosScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.MultiJQueryWidgetConstants;
import com.photon.phresco.uiconstants.MultiYuiWidgetConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.ScenariosConstants;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.YuiMobileWidgetConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class WebLayerScenariosTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MultiJQueryWidgetConstants multijQueryConstants;
	private static MultiYuiWidgetConstants multiYuiConst;
	private static YuiMobileWidgetConstants yuiMobileConstants;
	private static ScenariosScreen scenariosScreen;
	private static ScenariosConstants scenarioConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception {
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		scenarioConst = new ScenariosConstants();
		
		try {
			launchingBrowser();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void launchingBrowser() throws Exception {

		String applicationURL = phrscEnv.getProtocol() + "://"
		+ phrscEnv.getHost() + ":" + phrscEnv.getPort() + "/";
		selectedBrowser = phrscEnv.getBrowser();
		try {
			scenariosScreen = new ScenariosScreen(selectedBrowser,
					applicationURL, phrscEnv.getContext(), phrscEnv,
					heliosUiConst, userInfoConst, heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}

	@Test
	public void testLogin() throws InterruptedException,
	IOException, Exception {
		try {

			System.out
			.println("---------testLogin()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			scenariosScreen.loginPage(methodName);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testMobileWidgetProjectCreation() throws InterruptedException,
	IOException, Exception {
		try {

			System.out
			.println("---------testMobileWidgetProjectCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			scenariosScreen.yuiMobileWidget(methodName, scenarioConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testJQueryWidgetProjectCreation() throws InterruptedException,
	IOException, Exception {
		try {

			System.out
			.println("---------testJQueryWidgetProjectCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			scenariosScreen.jQueryWidget(methodName, scenarioConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testMultiYuiWidgetArchetypeRunAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testMultiYuiWidgetArchetypeRunAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			scenariosScreen.multiYuiWidgetArchetypeCreate(methodName, multiYuiConst, scenarioConst);
			scenariosScreen.multiYuiWidgetArchetypeEditProject(methodName, scenarioConst);
			scenariosScreen.configurationCreate(methodName, multijQueryConstants, scenarioConst);
			scenariosScreen.runAgainstSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testYuiMobileWidgetArchetypeRunAgainstSource()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testYuiMobileWidgetArchetypeRunAgainstSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			scenariosScreen.yuiMobileWidgetArchetypeCreate(methodName, yuiMobileConstants , scenarioConst);
			scenariosScreen.yuiMobileWidgetArchetypeEditProject(methodName, scenarioConst);
			scenariosScreen.configurationCreate(methodName, multijQueryConstants, scenarioConst);
			scenariosScreen.runAgainstSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@AfterTest
	public void tearDown() {
		scenariosScreen.closeBrowser();
	}

}

