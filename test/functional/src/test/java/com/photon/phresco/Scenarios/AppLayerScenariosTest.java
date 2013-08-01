package com.photon.phresco.Scenarios;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.ScenariosScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class AppLayerScenariosTest {

	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static ScenariosScreen scenariosScreen;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception {
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
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
	
	@AfterTest
	public void tearDown() {
		scenariosScreen.closeBrowser();
	}

}

