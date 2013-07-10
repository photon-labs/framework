package com.photon.helios.Scenarios;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.Scenarios.ScenariosScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.ScenariosConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class ScenariosTest {
	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static ScenariosScreen scenariosScreen;
	private static ScenariosConstants scenarioConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception {
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
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
	public void testAndroidNativeProjectCreation() throws InterruptedException,
	IOException, Exception {
		try {

			System.out
			.println("---------testAndroidNativeProjectCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			scenariosScreen.androidNative(methodName, scenarioConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testiPhoneNativeProjectCreation() throws InterruptedException,
	IOException, Exception {
		try {

			System.out
			.println("---------testiPhoneNativeProjectCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			scenariosScreen.iPhoneNative(methodName, scenarioConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}


	@Test
	public void testAndroidHybridProjectCreation() throws InterruptedException,
	IOException, Exception {
		try {

			System.out
			.println("---------testAndroidHybridProjectCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			scenariosScreen.androidHybrid(methodName, scenarioConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testiPhoneHybridProjectCreation() throws InterruptedException,
	IOException, Exception {
		try {

			System.out
			.println("---------testiPhoneHybridProjectCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
			scenariosScreen.iPhoneHybrid(methodName, scenarioConst);
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
			scenariosScreen.mobileWidget(methodName, scenarioConst);
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
	@AfterTest
	public void tearDown() {
		scenariosScreen.closeBrowser();
	}

}
