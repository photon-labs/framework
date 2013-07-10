package com.photon.helios.Screens.MobLayer;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;

import com.google.common.base.Function;
import com.photon.helios.selenium.util.Constants;
import com.photon.helios.selenium.util.GetCurrentDir;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.AndroidHybridConstants;
import com.photon.helios.uiconstants.AndroidLibraryConstants;
import com.photon.helios.uiconstants.AndroidNativeConstants;
import com.photon.helios.uiconstants.BlackBerryHybridConstants;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.IPhoneHybridConstants;
import com.photon.helios.uiconstants.IPhoneLibraryConstants;
import com.photon.helios.uiconstants.IPhoneNativeConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.WindowsMetroConstants;
import com.photon.helios.uiconstants.WindowsPhoneConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class MobBaseScreen {

	private static WebDriver driver;
	private Log log = LogFactory.getLog("BaseScreen");
	private WebElement element;
	private HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private UserInfoConstants userInfoConst;
	@SuppressWarnings("unused")
	private HeliosframeworkData heliosData;

	public MobBaseScreen() {

	}

	public MobBaseScreen(String selectedBrowser, String applicationURL,
			String applicationContext, phresco_env_config phrscEnv,
			HeliosUiConstants heliosUiConst, UserInfoConstants userInfoConst,
			HeliosframeworkData heliosData) throws ScreenException {

		MobBaseScreen.phrscEnv = phrscEnv;
		this.heliosUiConst = heliosUiConst;
		this.userInfoConst = userInfoConst;
		this.heliosData = heliosData;
		instantiateBrowser(selectedBrowser, applicationURL, applicationContext);

	}

	public void instantiateBrowser(String selectedBrowser,
			String applicationURL, String applicationContext)
	throws ScreenException {

		if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_CHROME)) {
			try {
				log.info("-------------***LAUNCHING GOOGLECHROME***--------------");
				System.setProperty("webdriver.chrome.driver",
				"./chromedriver/chromedriver.exe");
				driver = new ChromeDriver();
				windowResize();
				driver.navigate().to(applicationURL + applicationContext);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_IE)) {
			log.info("---------------***LAUNCHING INTERNET EXPLORE***-----------");
			driver = new InternetExplorerDriver();
			windowResize();
			driver.navigate().to(applicationURL + applicationContext);

		} else if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {
			log.info("-------------***LAUNCHING FIREFOX***--------------");
			driver = new FirefoxDriver();
			windowResize();
			driver.navigate().to(applicationURL + applicationContext);

		}

		else if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_OPERA)) {
			log.info("-------------***LAUNCHING OPERA***--------------");
			System.out.println("******entering window maximize********");
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_SPACE);
				robot.keyPress(KeyEvent.VK_X);
				robot.keyRelease(KeyEvent.VK_X);
			} catch (AWTException e) {

				e.printStackTrace();
			}

		} else {
			throw new ScreenException(
			"------Only FireFox,InternetExplore and Chrome works-----------");
		}

	}

	public static void windowResize() {

		String resolution = phrscEnv.getResolution();
		if (resolution != null) {
			String[] tokens = resolution.split("x");
			String resolutionX = tokens[0];
			String resolutionY = tokens[1];
			int x = Integer.parseInt(resolutionX);
			int y = Integer.parseInt(resolutionY);
			Dimension screenResolution = new Dimension(x, y);
			driver.manage().window().setSize(screenResolution);
		} else {
			driver.manage().window().maximize();
		}
	}

	public void closeBrowser() {
		log.info("-------------***BROWSER CLOSING***--------------");
		if (driver != null) {
			driver.quit();

		}

	}

	public String getChromeLocation() {
		log.info("getChromeLocation:*****CHROME TARGET LOCATION FOUND***");
		String directory = System.getProperty("user.dir");
		String targetDirectory = getChromeFile();
		String location = directory + targetDirectory;
		return location;
	}

	public String getChromeFile() {
		if (System.getProperty("os.name").startsWith(Constants.WINDOWS_OS)) {
			log.info("*******WINDOWS MACHINE FOUND*************");
			return Constants.WINDOWS_DIRECTORY + "/chromedriver.exe";
		} else if (System.getProperty("os.name").startsWith(Constants.LINUX_OS)) {
			log.info("*******LINUX MACHINE FOUND*************");
			return Constants.LINUX_DIRECTORY_64 + "/chromedriver";
		} else if (System.getProperty("os.name").startsWith(Constants.MAC_OS)) {
			log.info("*******MAC MACHINE FOUND*************");
			return Constants.MAC_DIRECTORY + "/chromedriver";
		} else {
			throw new NullPointerException("******PLATFORM NOT FOUND********");
		}

	}

	public WebElement getXpathWebElement(String xpath) throws Exception {
		log.info("Entering:-----getXpathWebElement-------");
		try {

			element = driver.findElement(By.xpath(xpath));

		} catch (Throwable t) {
			log.info("Entering:---------Exception in getXpathWebElement()-----------");
			t.printStackTrace();

		}
		return element;

	}

	public void getIdWebElement(String id) throws ScreenException {
		log.info("Entering:---getIdWebElement-----");
		try {
			element = driver.findElement(By.id(id));

		} catch (Throwable t) {
			log.info("Entering:---------Exception in getIdWebElement()----------");
			t.printStackTrace();

		}

	}

	public void getcssWebElement(String selector) throws ScreenException {
		log.info("Entering:----------getIdWebElement----------");
		try {
			element = driver.findElement(By.cssSelector(selector));

		} catch (Throwable t) {
			log.info("Entering:---------Exception in getIdWebElement()--------");

			t.printStackTrace();

		}

	}

	public void waitForElementPresent(String locator, String methodName)
	throws Exception {
		try {
			By by = null;
			log.info("Entering:--------waitForElementPresent()--------");

			if (locator.startsWith("//")) {
				log.info("Entering:--------Xpath checker--------");
				by = By.xpath(locator);
			} else {
				log.info("Entering:--------Non-Xpath checker----------------");
				by = By.id(locator);
			}

			WebDriverWait wait = new WebDriverWait(driver, 70);
			wait.until(presenceOfElementLocated(by));

		}

		catch (Exception e) {
			log.info("Entering:------presenceOfElementLocated()-----End"
					+ "--(" + locator + ")--");
			File scrFile = ((TakesScreenshot) driver)
			.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,
					new File(GetCurrentDir.getCurrentDirectory() + "/T"
							+ methodName + ".png"));
			Assert.assertNull(scrFile);

		}
	}

	Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
		log.info("Entering:------presenceOfElementLocated()-----Start");
		return new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);

			}

		};

	}

	public void selectText(WebElement element, String TextValue)
	throws ScreenException {
		log.info("Entering:----------get Select Text Webelement----------");
		try {
			Select selObj = new Select(element);
			selObj.selectByVisibleText(TextValue);
		} catch (Throwable t) {
			log.info("Entering:---------Exception in SelectextWebElement()--------");

			t.printStackTrace();

		}

	}

	public void loginPage(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@testLoginPage scenario****");
		try {
			Thread.sleep(5000);
			waitForElementPresent(heliosUiConst.getLoginUserName(), methodName);
			getXpathWebElement(heliosUiConst.getLoginUserName());
			click();
			sendKeys(userInfoConst.getLoginUserName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getLoginPassword(), methodName);
			getXpathWebElement(heliosUiConst.getLoginPassword());
			click();
			sendKeys(userInfoConst.getLoginPassword());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getLoginButton(), methodName);
			getXpathWebElement(heliosUiConst.getLoginButton());
			click();

			Thread.sleep(2000);
			waitForElementPresent(heliosUiConst.getCustomerDropdown(),methodName);
			getXpathWebElement(heliosUiConst.getCustomerDropdown());
			click();

			Thread.sleep(2000);
			waitForElementPresent(heliosUiConst.getCustomerName(), methodName);
			getXpathWebElement(heliosUiConst.getCustomerName());
			click();
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneNativeArchetypeCreate(String methodName,IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(iPhoneNativeConst.getiPhoneNatArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, iPhoneNativeConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, iPhoneNativeConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneNativeArchetypeEditApp(String methodName,	IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeEditApp executing");
		try {
			/*
			 * Thread.sleep(500);
			 * waitForElementPresent(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * (), methodName);
			 * getXpathWebElement(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * ()); click();
			 */

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),	methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneNativeArchetypeUpdateApp(String methodName,
			IPhoneNativeConstants iPhoneNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneHybridArchetypeCreate(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(iPhoneHybridConst.getiPhoneHybArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, iPhoneHybridConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, iPhoneHybridConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneHybridArchetypeEditApp(String methodName,
			IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeEditApp executing");
		try {
			/*
			 * Thread.sleep(500);
			 * waitForElementPresent(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * (), methodName);
			 * getXpathWebElement(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * ()); click();
			 */

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),	methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneHybridArchetypeUpdateApp(String methodName,IPhoneHybridConstants iPhoneHybridConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybridArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneLibraryArchetypeCreate(String methodName,	IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(iPhoneLibraryConst.getiPhoneLibArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, iPhoneLibraryConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, iPhoneLibraryConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneLibraryArchetypeEditApp(String methodName,IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeEditApp executing");
		try {
			/*
			 * Thread.sleep(500);
			 * waitForElementPresent(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * (), methodName);
			 * getXpathWebElement(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * ()); click();
			 */

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),	methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneLibraryArchetypeUpdateApp(String methodName,
			IPhoneLibraryConstants iPhoneLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneLibraryArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidNativeArchetypeCreate(String methodName,	AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(androidNativeConst.getAndroidNatArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(androidNativeConst.getAndroidNatArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(androidNativeConst.getAndroidNatArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, androidNativeConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, androidNativeConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidNativeArchetypeEditApp(String methodName,AndroidNativeConstants  androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(androidNativeConst.getAndroidNatArchetypeEditLink(), methodName);
			getXpathWebElement(androidNativeConst.getAndroidNatArchetypeEditLink()); click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),	methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidNativeArchetypeUpdateApp(String methodName,AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNativeArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidNativeArchetypeBuild(String methodName,
			AndroidNativeConstants androidNativeConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getBuildTab(), methodName);
			getXpathWebElement(heliosUiConst.getBuildTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getGenerateBuild(), methodName);
			getXpathWebElement(heliosUiConst.getGenerateBuild());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildName(), methodName);
			getXpathWebElement(heliosUiConst.getBuildName());
			click();
			clear();
			sendKeys(androidNativeConst.getBuildName());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(heliosUiConst.getChooseEnvironment());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseLogs(), methodName);
			getXpathWebElement(heliosUiConst.getChooseLogs());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildRun(), methodName);
			getXpathWebElement(heliosUiConst.getBuildRun());
			click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidHybridArchetypeCreate(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(androidHybConst.getAndroidHybArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(androidHybConst.getAndroidHybArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(androidHybConst.getAndroidHybArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, androidHybConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, androidHybConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidHybridArchetypeEditApp(String methodName,AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeEditApp executing");
		try {
			
			Thread.sleep(500);
			waitForElementPresent(androidHybConst.getAndroidHybArchetypeEditLink(), methodName);
			getXpathWebElement(androidHybConst.getAndroidHybArchetypeEditLink());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidHybridArchetypeUpdateApp(String methodName,
			AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidHybridArchetypeBuild(String methodName,
			AndroidHybridConstants androidHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getBuildTab(), methodName);
			getXpathWebElement(heliosUiConst.getBuildTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getGenerateBuild(), methodName);
			getXpathWebElement(heliosUiConst.getGenerateBuild());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildName(), methodName);
			getXpathWebElement(heliosUiConst.getBuildName());
			click();
			clear();
			sendKeys(androidHybConst.getBuildName());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(heliosUiConst.getChooseEnvironment());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseLogs(), methodName);
			getXpathWebElement(heliosUiConst.getChooseLogs());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildRun(), methodName);
			getXpathWebElement(heliosUiConst.getBuildRun());
			click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidLibraryArchetypeCreate(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(androidLibraryConst.getAndroidLibArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(androidLibraryConst.getAndroidLibArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(androidLibraryConst.getAndroidLibArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, androidLibraryConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, androidLibraryConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidLibraryArchetypeEditApp(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(androidLibraryConst.getAndroidLibArchetypeEditLink(), methodName);
			getXpathWebElement(androidLibraryConst.getAndroidLibArchetypeEditLink());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),	methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidLibraryArchetypeUpdateApp(String methodName,AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidLibraryArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void androidLibraryArchetypeBuild(String methodName,
			AndroidLibraryConstants androidLibraryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getBuildTab(), methodName);
			getXpathWebElement(heliosUiConst.getBuildTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getGenerateBuild(), methodName);
			getXpathWebElement(heliosUiConst.getGenerateBuild());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildName(), methodName);
			getXpathWebElement(heliosUiConst.getBuildName());
			click();
			clear();
			sendKeys(androidLibraryConst.getBuildName());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(heliosUiConst.getChooseEnvironment());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseLogs(), methodName);
			getXpathWebElement(heliosUiConst.getChooseLogs());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildRun(), methodName);
			getXpathWebElement(heliosUiConst.getBuildRun());
			click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void blackBerryHybridArchetypeCreate(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(blackBerryHybConst.getBlackBerryHybArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, blackBerryHybConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, blackBerryHybConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void blackBerryHybridArchetypeEditApp(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeEditApp executing");
		try {
			/*
			 * Thread.sleep(500);
			 * waitForElementPresent(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * (), methodName);
			 * getXpathWebElement(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * ()); click();
			 */

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void blackBerryHybridArchetypeUpdateApp(String methodName,BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@blackBerryHybridArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void blackBerryHybridArchetypeBuild(String methodName,
			BlackBerryHybridConstants blackBerryHybConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getBuildTab(), methodName);
			getXpathWebElement(heliosUiConst.getBuildTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getGenerateBuild(), methodName);
			getXpathWebElement(heliosUiConst.getGenerateBuild());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildName(), methodName);
			getXpathWebElement(heliosUiConst.getBuildName());
			click();
			clear();
			sendKeys(blackBerryHybConst.getBuildName());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(heliosUiConst.getChooseEnvironment());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseLogs(), methodName);
			getXpathWebElement(heliosUiConst.getChooseLogs());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildRun(), methodName);
			getXpathWebElement(heliosUiConst.getBuildRun());
			click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void windowsMetroArchetypeCreate(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(windowsMetroConst.getWindowsMetroArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, windowsMetroConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, windowsMetroConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsMetroArchetypeEditApp(String methodName,	WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeEditApp executing");
		try {
			/*
			 * Thread.sleep(500);
			 * waitForElementPresent(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * (), methodName);
			 * getXpathWebElement(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * ()); click();
			 */

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),	methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(
					heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsMetroArchetypeUpdateApp(String methodName,WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsMetroArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void windowsMetroArchetypeBuild(String methodName,
			WindowsMetroConstants windowsMetroConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getBuildTab(), methodName);
			getXpathWebElement(heliosUiConst.getBuildTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getGenerateBuild(), methodName);
			getXpathWebElement(heliosUiConst.getGenerateBuild());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildName(), methodName);
			getXpathWebElement(heliosUiConst.getBuildName());
			click();
			clear();
			sendKeys(windowsMetroConst.getBuildName());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(heliosUiConst.getChooseEnvironment());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseLogs(), methodName);
			getXpathWebElement(heliosUiConst.getChooseLogs());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildRun(), methodName);
			getXpathWebElement(heliosUiConst.getBuildRun());
			click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsPhoneArchetypeCreate(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@windowsPhoneArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(windowsMobileConst.getWindowsPhoneArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, windowsMobileConst.getMobileValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, windowsMobileConst.getMobileType());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsPhoneArchetypeEditApp(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeEditApp executing");
		try {
			/*
			 * Thread.sleep(500);
			 * waitForElementPresent(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * (), methodName);
			 * getXpathWebElement(iPhoneNativeConst.getSitecoreArchetypeEditApp
			 * ()); click();
			 */

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebdriverValue(), methodName);
			getXpathWebElement(heliosUiConst.getWebdriverValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(),methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void windowsPhoneArchetypeUpdateApp(String methodName,WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNativeArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void windowsPhoneArchetypeBuild(String methodName,
			WindowsPhoneConstants windowsMobileConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybridArchetypeBuild executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getBuildTab(), methodName);
			getXpathWebElement(heliosUiConst.getBuildTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getGenerateBuild(), methodName);
			getXpathWebElement(heliosUiConst.getGenerateBuild());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildName(), methodName);
			getXpathWebElement(heliosUiConst.getBuildName());
			click();
			clear();
			sendKeys(windowsMobileConst.getBuildName());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseEnvironment(), methodName);
			getXpathWebElement(heliosUiConst.getChooseEnvironment());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseLogs(), methodName);
			getXpathWebElement(heliosUiConst.getChooseLogs());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getBuildRun(), methodName);
			getXpathWebElement(heliosUiConst.getBuildRun());
			click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public boolean isTextPresentBuild(String text) throws InterruptedException,
	ScreenException {

		if (text != null) {
			String build_failure = "ERROR";

			for (int i = 0; i <= 200; i++) {
				if (driver.findElement(By.tagName("body")).getText()
						.contains(text)) {
					break;
				} else {
					if (i == 204) {
						throw new RuntimeException(
						"---- Time out for finding the Text----");
					} else if (driver.findElement(By.tagName("body")).getText()
							.contains(build_failure)) {
						System.out.println("*****Build Failure*****");
						throw new ScreenException("*****Build Failure*****");

					}
					Thread.sleep(500);
				}
			}

		} else {
			throw new RuntimeException("---- Text not existed----");
		}

		return true;

	}

	public boolean successFailureLoop() throws InterruptedException,
	IOException, Exception {
		String foundString = buildStatusVerifier();
		if (foundString.equalsIgnoreCase("BUILD SUCCESS")) {
			return true;
		} else
			return false;
	}

	private String buildStatusVerifier() {
		String BUILD_SUCCESS = "BUILD SUCCESS";
		String BUILD_FAILURE = "BUILD FAILURE";
		// String ERROR = "[ERROR]";
		boolean statusStringFound = false;
		/* long end = 70000; */
		while (!statusStringFound) { /* && end <=70000 */
			if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_SUCCESS)) {
				return BUILD_SUCCESS;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_FAILURE)) {
				return BUILD_FAILURE;
			} /*
			 * else if
			 * (driver.findElement(By.tagName("body")).getText().contains
			 * (ERROR)) { return ERROR; }
			 */
		}
		return null;
	}

	public void click() throws ScreenException {
		log.info("Entering:********click operation start********");
		try {
			element.click();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		log.info("Entering:********click operation end********");

	}

	public void type(String text) throws ScreenException {
		log.info("Entering:********enterText operation start********");
		try {
			clear();
			element.sendKeys(text);

		} catch (Throwable t) {
			t.printStackTrace();
		}
		log.info("Entering:********enterText operation end********");
	}

	public void clear() throws ScreenException {
		log.info("Entering:********clear operation start********");
		try {
			element.clear();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		log.info("Entering:********clear operation end********");

	}

	public void sendKeys(String text) throws ScreenException {
		log.info("Entering:********enterText operation start********");
		try {
			element.sendKeys(text);

		} catch (Throwable t) {
			t.printStackTrace();
		}
		log.info("Entering:********enterText operation end********");
	}

	public void submit() throws ScreenException {
		log.info("Entering:********submit operation start********");
		try {
			element.submit();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		log.info("Entering:********submit operation end********");

	}

	public boolean isTextPresent(String text) {
		if (text != null) {
			boolean value = driver.findElement(By.tagName("body")).getText()
			.contains(text);
			System.out.println("--------TextCheck value---->" + text
					+ "------------Result is-------------" + value);
			AssertJUnit.assertTrue(value);
			return value;
		} else {
			throw new RuntimeException("---- Text not present----");
		}

	}

	public boolean isTextPresent(String text, String methodName) {
		if (text != null) {
			boolean value = driver.findElement(By.tagName("body")).getText()
			.contains(text);
			System.out.println("--------TextCheck value---->" + text
					+ "------------Result is-------------" + value);
			if (!value) {
				takesScreenshot(methodName);
			}
			Assert.assertTrue(value);
			return value;
		} else {
			throw new RuntimeException("---- Text not present----");
		}
	}

	public void takesScreenshot(String methodName) {

		log.info("Entering:::::::::::takesScreenshot");
		File scrFile = ((TakesScreenshot) driver)
		.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile,
					new File(GetCurrentDir.getCurrentDirectory() + "/"
							+ methodName + ".png"));
		} catch (Exception e1) {
			log.info("failureTestMethod" + e1.getMessage());
		}

	}

	public boolean updateSuccessFailureLoop() throws InterruptedException,
	IOException, Exception {
		String foundString = updateStatusVerifier();
		if (foundString.equalsIgnoreCase("Application updated successfully")) {
			return true;
		} else
			return false;
	}

	private String updateStatusVerifier() {
		String UPDATE_SUCCESS = "Application updated successfully";
		boolean statusStringFound = false;
		while (!statusStringFound) {
			if (driver.findElement(By.tagName("body")).getText()
					.contains(UPDATE_SUCCESS)) {
				return UPDATE_SUCCESS;
			}
		}
		return null;
	}

}


