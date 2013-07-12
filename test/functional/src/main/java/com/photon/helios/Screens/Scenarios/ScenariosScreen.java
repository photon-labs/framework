package com.photon.helios.Screens.Scenarios;

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
import org.openqa.selenium.chrome.ChromeDriverService;
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
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.ScenariosConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class ScenariosScreen {

	private static WebDriver driver;
	private ChromeDriverService chromeService;
	private final Log log = LogFactory.getLog("BaseScreen");
	private WebElement element;
	private HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private UserInfoConstants userInfoConst;
	@SuppressWarnings("unused")
	private HeliosframeworkData heliosData;

	public ScenariosScreen() {
	}

	public ScenariosScreen(String selectedBrowser, String applicationURL,
			String applicationContext, phresco_env_config phrscEnv,
			HeliosUiConstants heliosUiConst, UserInfoConstants userInfoConst,
			HeliosframeworkData heliosData) throws ScreenException {
		ScenariosScreen.phrscEnv = phrscEnv;
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
				chromeService = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(getChromeLocation()))
				.usingAnyFreePort().build();
				log.info("-------------***LAUNCHING GOOGLECHROME***--------------");
				driver = new ChromeDriver(chromeService);
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
		} else if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_OPERA)) {
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

			if (chromeService != null) {

			}
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
					new File(GetCurrentDir.getCurrentDirectory() + "\\"
							+ methodName + ".png"));
			Assert.assertNull(scrFile);

		}
	}

	Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
		log.info("Entering:------presenceOfElementLocated()-----Start");
		return new Function<WebDriver, WebElement>() {
			@Override
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
			waitForElementPresent(heliosUiConst.getCustomerDropdown(),
					methodName);
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

	public void androidNative(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidNative executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeAppCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, scenarioConst.getNodeJsValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidNativeMobCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, scenarioConst.getAndroidValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, scenarioConst.getNativeValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneNative(String methodName, ScenariosConstants scenarioConst)
	throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneNative executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeAppCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, scenarioConst.getNodeJsValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneNativeMobCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, scenarioConst.getiPhoneValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, scenarioConst.getNativeValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void androidHybrid(String methodName,
			ScenariosConstants scenarioConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@androidHybrid executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridAppCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, scenarioConst.getNodeJsValue());


			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridWebCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, scenarioConst.getHtml5Value());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, scenarioConst.getYuiMobileWidgetValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAndroidHybridMobCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, scenarioConst.getAndroidValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, scenarioConst.getHybridValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void iPhoneHybrid(String methodName, ScenariosConstants scenarioConst)
	throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@iPhoneHybrid executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridAppCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, scenarioConst.getNodeJsValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridWebCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, scenarioConst.getHtml5Value());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, scenarioConst.getYuiMobileWidgetValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getMobLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getiPhoneHybridMobCode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobLayer(), methodName);
			getXpathWebElement(heliosUiConst.getMobLayer());
			selectText(element, scenarioConst.getiPhoneValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getMobileType(), methodName);
			getXpathWebElement(heliosUiConst.getMobileType());
			selectText(element, scenarioConst.getHybridValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void mobileWidget(String methodName, ScenariosConstants scenarioConst)
	throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@mobileWidget executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetAppcode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, scenarioConst.getJ2EEValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getMobileWidgetWebcode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, scenarioConst.getHtml5Value());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, scenarioConst.getYuiMobileWidgetValue());
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(),methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void jQueryWidget(String methodName, ScenariosConstants scenarioConst)
	throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@jQueryWidget executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetAppcode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(),
					methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, scenarioConst.getJ2EEValue());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getjQueryWidgetWebcode());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, scenarioConst.getHtml5Value());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, scenarioConst.getMultiJqueryWidgetValue());
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(),methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
			click();
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			
			
			Thread.sleep(30000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	
	public void createProjectUseNumericValues(String methodName, ScenariosConstants scenarioConst)
	throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@createProjectUseNumericValues executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getProjectNameNumeric());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getNumericProjectDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getNumericAppCode());		

			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, scenarioConst.getHtml5Value());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, scenarioConst.getYuiMobileWidgetValue());
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
			click();
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void createProjectUseAlphaNumericValues(String methodName, ScenariosConstants scenarioConst)
	throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@createProjectUseNumericValues executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getProjectNameAlphaNumeric());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getAlphaNumericProjectDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getAlphaNumericAppCode());	

					
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, scenarioConst.getHtml5Value());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, scenarioConst.getYuiMobileWidgetValue());
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
			click();
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void createProjectSpecialChar(String methodName, ScenariosConstants scenarioConst)
	throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@createProjectUseNumericValues executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(scenarioConst.getProjectNameSpecialChar());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(scenarioConst.getSpecialCharProjectDesc());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(),
					methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(scenarioConst.getSpecialChar());			
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, scenarioConst.getHtml5Value());

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, scenarioConst.getYuiMobileWidgetValue());
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(),
					methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(),
					methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
			click();
			
			Thread.sleep(1000);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(),
					methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(30000);

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
					if (i == 200) {
						throw new RuntimeException(
						"---- Time out for finding the Text----");
					} else if (driver.findElement(By.tagName("body")).getText()
							.contains(build_failure)) {
						System.out.println("*****Build Failure*****");
						throw new ScreenException("*****Build Failure*****");

					}
					Thread.sleep(1000);
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
					new File(GetCurrentDir.getCurrentDirectory() + "\\"
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
