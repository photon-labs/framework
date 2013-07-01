package com.photon.helios.Screens.Weblayer;

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
import com.photon.helios.uiconstants.JQueryMobileWidgetConstants;
import com.photon.helios.uiconstants.MultiJQueryWidgetConstants;
import com.photon.helios.uiconstants.MultiYUIWidgetConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.YUIMobileWidgetConstants;
import com.photon.helios.uiconstants.phresco_env_config;



public class WebBaseScreen {
	private static WebDriver driver;
	private ChromeDriverService chromeService;
	private Log log = LogFactory.getLog("BaseScreen");
	private WebElement element;
	private HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private UserInfoConstants userInfoConst;
	@SuppressWarnings("unused")
	private HeliosframeworkData heliosData;

	public WebBaseScreen() {

	}

	public WebBaseScreen(String selectedBrowser, String applicationURL,	String applicationContext, phresco_env_config phrscEnv,	HeliosUiConstants heliosUiConst, UserInfoConstants userInfoConst,HeliosframeworkData heliosData) throws ScreenException {

		WebBaseScreen.phrscEnv = phrscEnv;
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
				System.setProperty("webdriver.chrome.driver","./chromedriver/chromedriver.exe");
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
			By by=null;
			log.info("Entering:--------waitForElementPresent()--------");

			if(locator.startsWith("//")){
				log.info("Entering:--------Xpath checker--------");
				by = By.xpath(locator);	
			}else{
				log.info("Entering:--------Non-Xpath checker----------------");
				by=By.id(locator);
			}

			WebDriverWait wait = new WebDriverWait(driver, 20);			
			wait.until(presenceOfElementLocated(by));

		}

		catch (Exception e) {
			log.info("Entering:------presenceOfElementLocated()-----End"+"--("+ locator +")--");
			File scrFile = ((TakesScreenshot) driver)
			.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,
					new File(GetCurrentDir.getCurrentDirectory() + "/"
							+ methodName + ".png"));
			/*throw new RuntimeException("waitForElementPresent"
					+ super.getClass().getSimpleName() + " failed", e);*/
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
	public void selectText(WebElement element,String TextValue) throws ScreenException {
		log.info("Entering:----------get Select Text Webelement----------");
		try {
			Select selObj=new Select(element);
			selObj.selectByVisibleText(TextValue);
		} catch (Throwable t) {
			log.info("Entering:---------Exception in SelectextWebElement()--------");

			t.printStackTrace();

		}

	}

	public  void loginPage(String methodName) throws Exception {
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
			waitForElementPresent(heliosUiConst.getCustomerDropdown(), methodName);
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


	public void multiYUIWidgetArchetypeCreate(String methodName,MultiYUIWidgetConstants multiYUIConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@multiYUIWidgetArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(multiYUIConst.getMultiYuiArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(multiYUIConst.getMultiYuiArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(multiYUIConst.getMultiYuiArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, multiYUIConst.getWebLayerValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, multiYUIConst.getWidgetValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiYUIWidgetArchetypeEditApp(String methodName,MultiYUIWidgetConstants multiYUIConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@multiYUIWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(multiYUIConst.getMultiYuiArchetypeEditLink(), methodName);
			getXpathWebElement(multiYUIConst.getMultiYuiArchetypeEditLink());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServer(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(), methodName);
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
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(), methodName);
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
	
	public void multiYUIWidgetArchetypeUpdateApp(String methodName,MultiYUIWidgetConstants multiYUIConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@multiYUIWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void multiJQueryWidgetArchetypeCreate(String methodName,MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(multiJQueryConst.getMultiJQueryArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(multiJQueryConst.getMultiJQueryArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(multiJQueryConst.getMultiJQueryArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, multiJQueryConst.getWebLayerValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, multiJQueryConst.getWidgetValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
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

	
	public void multiJQueryWidgetArchetypeEditApp(String methodName,MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(multiJQueryConst.getMultiJQueryArchetypeEditLink(), methodName);
			getXpathWebElement(multiJQueryConst.getMultiJQueryArchetypeEditLink());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServer(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(), methodName);
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
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(), methodName);
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
	
	
	public void multiJQueryWidgetArchetypeUpdateApp(String methodName,MultiJQueryWidgetConstants multiJQueryConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@multiJQueryWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void yuiMobileWidgetArchetypeCreate(String methodName,YUIMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(yuiMobConst.getYUIMobileArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(yuiMobConst.getYUIMobileArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(yuiMobConst.getYUIMobileArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, yuiMobConst.getWebLayerValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, yuiMobConst.getWidgetValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
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
	
	public void yuiMobileWidgetArchetypeEditApp(String methodName,YUIMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(yuiMobConst.getYUIMobileArchetypeEditLink(), methodName);
			getXpathWebElement(yuiMobConst.getYUIMobileArchetypeEditLink());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServer(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(), methodName);
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
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(), methodName);
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
	
	public void yuiMobileWidgetArchetypeUpdateApp(String methodName,YUIMobileWidgetConstants yuiMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@yuiMobileWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void jQueryMobWidgetArchetypeCreate(String methodName,JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddProjectButton(), methodName);
			getXpathWebElement(heliosUiConst.getAddProjectButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectName(), methodName);
			getXpathWebElement(heliosUiConst.getProjectName());
			click();
			clear();
			sendKeys(jQueryMobConst.getjQueryMobArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(jQueryMobConst.getjQueryMobArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerAppCode());
			click();
			clear();
			sendKeys(jQueryMobConst.getjQueryMobArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayer());
			selectText(element, jQueryMobConst.getWebLayerValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWebLayerWidget(), methodName);
			getXpathWebElement(heliosUiConst.getWebLayerWidget());
			selectText(element, jQueryMobConst.getWidgetValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveAppLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveAppLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveMobileLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
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

	public void jQueryMobWidgetArchetypeEditApp(String methodName,JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(jQueryMobConst.getjQueryMobArchetypeEditLink(), methodName);
			getXpathWebElement(jQueryMobConst.getjQueryMobArchetypeEditLink());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppInfoTab(), methodName);
			getXpathWebElement(heliosUiConst.getAppInfoTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServer(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getApacheTomcatServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getApacheTomcatServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(), methodName);
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
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(), methodName);
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
	
	public void jQueryMobWidgetArchetypeUpdateApp(String methodName,JQueryMobileWidgetConstants jQueryMobConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@jQueryMobWidgetArchetypeUpdateApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFeatureTab(), methodName);
			getXpathWebElement(heliosUiConst.getFeatureTab());
			click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
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
	public void type(String text)throws ScreenException{
		log.info("Entering:********enterText operation start********");
		try{
			element.clear();
			element.sendKeys(text);

		}catch(Throwable t){
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
	public boolean isTextPresent(String text)
	{
		if(text!=null)
		{
			boolean value=driver.findElement(By.tagName("body")).getText().contains(text);
			System.out.println("--------TextCheck value---->"+text+"------------Result is-------------"+value); 
			AssertJUnit.assertTrue(value);
			return value;
		}
		else
		{
			throw new RuntimeException("---- Text not present----");
		}

	}
	/*public void webLayerBuildTab(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@webLayerBuildTab::****** executing ****");
		try {
			Thread.sleep(5000);
			waitForElementPresent(phrsc.getBuildTab(), methodName);
			getXpathWebElement(phrsc.getBuildTab());
			click();

			Thread.sleep(2000);
			waitForElementPresent(phrsc.getGenerateBuild(), methodName);
			getXpathWebElement(phrsc.getGenerateBuild());
			click();

			Thread.sleep(5000);
			waitForElementPresent(phrsc.getBuildNameXpath(), methodName);
			getXpathWebElement(phrsc.getBuildNameXpath());
			click();
			sendKeys(phrsc.getBuildName());

			Thread.sleep(2000);
			waitForElementPresent(phrsc.getBuildNumberXpath(), methodName);
			getXpathWebElement(phrsc.getBuildNumberXpath());
			sendKeys(phrsc.getBuildNumber());

			Thread.sleep(2000);
			waitForElementPresent(phrsc.getBuildBtn(), methodName);
			getXpathWebElement(phrsc.getBuildBtn());
			click();
			Thread.sleep(2000);
			boolean buildTestResult=successFailureLoop();
			System.out.println("buildTestResult:::::::"+buildTestResult);
			Assert.assertTrue(buildTestResult);
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void webLayerDeploy(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@webLayerDeploy::******executing ****");
		try {
			Thread.sleep(2000);
			waitForElementPresent(phrsc.getDeployBtn(), methodName);
			getXpathWebElement(phrsc.getDeployBtn());
			click();

			Thread.sleep(2000);
			waitForElementPresent(phrsc.getDeployOK(), methodName);
			getXpathWebElement(phrsc.getDeployOK());
			click();
			Thread.sleep(20000);
			boolean deployTestResult = successFailureLoop();
			System.out.println("deployTestResult:::::"+deployTestResult);
			Assert.assertTrue(deployTestResult);
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	 */
	public boolean isTextPresent(String text,String methodName) {
		if (text != null) {
			boolean value = driver.findElement(By.tagName("body")).getText()
			.contains(text);
			System.out.println("--------TextCheck value---->" + text
					+ "------------Result is-------------" + value);
			if(!value)
			{
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

	public boolean updateSuccessFailureLoop() throws InterruptedException, IOException, Exception {
		String foundString = updateStatusVerifier();
		if(foundString.equalsIgnoreCase("Application updated successfully"))
		{
			return true;
		}
		else
			return false;			
	}

	private String updateStatusVerifier() {
		String UPDATE_SUCCESS = "Application updated successfully";
		boolean statusStringFound = false;		
		while (!statusStringFound) { 
			if (driver.findElement(By.tagName("body")).getText().contains(UPDATE_SUCCESS)) {
				return UPDATE_SUCCESS;
			} 
		}
		return null;
	}

}
