package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.IPhoneWorkspaceConstants;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class IPhoneWorkspaceTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static IPhoneWorkspaceConstants iphoneWorkspaceconst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception {
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		iphoneWorkspaceconst = new IPhoneWorkspaceConstants();
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
			mobBaseScreen = new MobScreen(selectedBrowser, applicationURL,
					phrscEnv.getContext(), phrscEnv, heliosUiConst,
					userInfoConst, heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}

	@Test
	public void testIPhoneWorkspaceArchetypeCreation()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testIPhoneWorkspaceArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.iPhoneWorkspaceArchetypeCreate(methodName,
					iphoneWorkspaceconst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testIPhoneWorkspaceArchetypeEditProject()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testIPhoneWorkspaceArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.iPhoneWorkspaceArchetypeEditProject(methodName,
					iphoneWorkspaceconst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testIPhoneWorkspaceArchetypeEditApp()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testIPhoneWorkspaceArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.iPhoneWorkspaceArchetypeEditApp(methodName,
					iphoneWorkspaceconst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testIPhoneWorkspaceArchetypeUpdateAppFeatures()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testIPhoneWorkspaceArchetypeUpdateAppFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.iPhoneWorkspaceArchetypeUpdateFeatures(methodName,
					iphoneWorkspaceconst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testIPhoneWorkspaceArchetypeEditAppDesc()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testIPhoneWorkspaceArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.iPhoneWorkspaceArchetypeEditAppDesc(methodName,
					iphoneWorkspaceconst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testIPhoneWorkspaceArchetypeOverAllPdfReport()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testIPhoneWorkspaceArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.iPhoneWorkspaceArchetypeOverAllPdfReport(methodName,
					iphoneWorkspaceconst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testIPhoneWorkspaceArchetypeDetailedPdfReport()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testIPhoneWorkspaceArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.iPhoneWorkspaceArchetypeDetailedPdfReport(methodName,
					iphoneWorkspaceconst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}
