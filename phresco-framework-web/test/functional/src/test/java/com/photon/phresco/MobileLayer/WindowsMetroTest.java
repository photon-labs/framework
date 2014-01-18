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
import com.photon.phresco.uiconstants.WindowsMetroConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class WindowsMetroTest {
	private static UserInfoConstants userInfoConst;
	private static PhrescoframeworkData heliosData;
	private static PhrescoUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobScreen mobBaseScreen;
	private static WindowsMetroConstants windowsMetroConst;
	private String methodName;
	private static String selectedBrowser;

	@BeforeTest
	public void setUp() throws Exception {
		phrscEnv = new phresco_env_config();
		heliosUiConst = new PhrescoUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new PhrescoframeworkData();
		windowsMetroConst = new WindowsMetroConstants();
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
	public void testWindowsMetroArchetypeCreation()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.windowsMetroArchetypeCreate(methodName,
					windowsMetroConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	
	@Test
	public void testWindowsMetroArchetypeEditProject() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeEditProject()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsMetroArchetypeEditProject(methodName,
					windowsMetroConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsMetroArchetypeEditApp() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsMetroArchetypeEditApp(methodName,
					windowsMetroConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsMetroArchetypeUpdateAppFeatures()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeUpdateAppFeatures()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsMetroArchetypeUpdateAppFeatures(methodName,
					windowsMetroConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsMetroArchetypeCodeValidateSource()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeCodeValidateSource()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.codeValidateForSource(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsMetroArchetypeEditAppDesc()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeEditAppDesc()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsMetroArchetypeEditAppDesc(methodName,
					windowsMetroConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testWindowsMetroArchetypeBuild() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsMetroArchetypeBuild(methodName,
					windowsMetroConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsMetroArchetypeOverAllPdfReport() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeOverAllPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsMetroArchetypeUnitTest(methodName, windowsMetroConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsMetroArchetypeDetailedPdfReport() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("---------testWindowsMetroArchetypeDetailedPdfReport()-------------");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			mobBaseScreen.windowsMetroArchetypeUnitTest(methodName, windowsMetroConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@AfterTest
	public void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}
