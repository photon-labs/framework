package com.photon.phresco.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.phresco.Screens.MobScreen;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.UserInfoConstants;
import com.photon.phresco.uiconstants.WindowsPhoneConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class WindowsPhoneTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static WindowsPhoneConstants windowsPhoneConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception {
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		windowsPhoneConst = new WindowsPhoneConstants();
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
	public void testWindowsPhoneArchetypeCreation()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.windowsPhoneArchetypeCreate(methodName,
					windowsPhoneConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsPhoneArchetypeEditProject() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeEditProject(methodName,
					windowsPhoneConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsPhoneArchetypeEditApp() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeEditApp(methodName,
					windowsPhoneConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsPhoneArchetypeUpdateAppFeatures()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeUpdateAppFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeUpdateAppFeatures(methodName,
					windowsPhoneConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsPhoneArchetypeEditAppDesc()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeEditAppDesc(methodName,
					windowsPhoneConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsPhoneCodeValidateForSource()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneCodeValidateForSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.codeValidateForSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsPhoneArchetypeBuild() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeBuild(methodName,
					windowsPhoneConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsPhoneArchetypeUnitTest() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeUnitTest()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeUnitTest(methodName, windowsPhoneConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsPhoneArchetypeOverAllPdfReport() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeOverAllPdfReport(methodName, windowsPhoneConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsPhoneArchetypeDetailedPdfReport() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsPhoneArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsPhoneArchetypeDetailedPdfReport(methodName, windowsPhoneConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}
