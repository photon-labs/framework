package com.photon.helios.MobileLayer;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.photon.helios.Screens.MobLayer.MobBaseScreen;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.BlackBerryHybridConstants;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class BlackberryHybridTest {

	private static UserInfoConstants userInfoConst;
	private static HeliosframeworkData heliosData;
	private static HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private static MobBaseScreen mobBaseScreen;
	private static BlackBerryHybridConstants blackBerryConst;
	private String methodName;
	private static String selectedBrowser;
	
	@BeforeTest
	public  void setUp() throws Exception
	{
		phrscEnv = new phresco_env_config();
		heliosUiConst = new HeliosUiConstants();
		userInfoConst = new UserInfoConstants();
		heliosData = new HeliosframeworkData();
		blackBerryConst = new BlackBerryHybridConstants();
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
	public void testBlackBerryHybridArchetypeCreation()
	throws InterruptedException, IOException, Exception {
		try {

			System.out.println("---------testBlackBerryHybridArchetypeCreation()-------------");
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			mobBaseScreen.loginPage(methodName);
			mobBaseScreen.blackBerryHybridArchetypeCreate(methodName, blackBerryConst);
		} catch (Exception t) {
			t.printStackTrace();

		}
	} 
	

	@AfterTest
	public void tearDown() {
		mobBaseScreen.closeBrowser();
	}

}
