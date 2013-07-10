package com.photon.helios.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.MobLayer.MobBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.WindowsMetroConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class WindowsMetroTest {
	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobBaseScreen mobBaseScreen;
	private static WindowsMetroConstants windowsMetroConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		windowsMetroConst = new WindowsMetroConstants();
		try {
			launchingBrowser();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public static void launchingBrowser() throws Exception {

		String applicationURL = phrscEnv.getProtocol() + "://" + phrscEnv.getHost() + ":" + phrscEnv.getPort() + "/";
		selectedBrowser = phrscEnv.getBrowser();
		try {
			mobBaseScreen = new MobBaseScreen(selectedBrowser, applicationURL,phrscEnv.getContext(),phrscEnv,heliosUiConst,userInfoConst,heliosData);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}


	@Test
	public void testWindowsMetroArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWindowsMetroArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.windowsMetroArchetypeCreate(methodName, windowsMetroConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWindowsMetroArchetypeEditApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWindowsMetroArchetypeEditApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.windowsMetroArchetypeEditApp(methodName, windowsMetroConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	
	@Test
	public void testWindowsMetroArchetypeUpdateApp()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWindowsMetroArchetypeUpdateApp()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.windowsMetroArchetypeUpdateApp(methodName, windowsMetroConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testWindowsMetroArchetypeBuild()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testWindowsMetroArchetypeBuild()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.windowsMetroArchetypeBuild(methodName, windowsMetroConst);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	

	@AfterTest
	public void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}





