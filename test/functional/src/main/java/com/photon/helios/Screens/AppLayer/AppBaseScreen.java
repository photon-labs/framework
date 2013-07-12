package com.photon.helios.Screens.AppLayer;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;
import com.photon.helios.selenium.util.Constants;
import com.photon.helios.selenium.util.GetCurrentDir;
import com.photon.helios.selenium.util.ScreenException;
import com.photon.helios.uiconstants.AspDotNetConstants;
import com.photon.helios.uiconstants.Drupal6Constants;
import com.photon.helios.uiconstants.Drupal7Constants;
import com.photon.helios.uiconstants.HeliosUiConstants;
import com.photon.helios.uiconstants.HeliosframeworkData;
import com.photon.helios.uiconstants.JavaStandaloneConstants;
import com.photon.helios.uiconstants.JavaWebserviceConstants;
import com.photon.helios.uiconstants.NodeJsConstants;
import com.photon.helios.uiconstants.PhpConstants;
import com.photon.helios.uiconstants.SharepointConstants;
import com.photon.helios.uiconstants.SitecoreConstants;
import com.photon.helios.uiconstants.UserInfoConstants;
import com.photon.helios.uiconstants.WordpressConstants;
import com.photon.helios.uiconstants.phresco_env_config;

public class AppBaseScreen {

	private static WebDriver driver;
	private Log log = LogFactory.getLog("BaseScreen");
	private WebElement element;
	private HeliosUiConstants heliosUiConst;
	private static phresco_env_config phrscEnv;
	private UserInfoConstants userInfoConst;
	@SuppressWarnings("unused")
	private HeliosframeworkData heliosData;

	public AppBaseScreen() {

	}



	public AppBaseScreen(String selectedBrowser, String applicationURL,	String applicationContext, phresco_env_config phrscEnv,	HeliosUiConstants heliosUiConst, UserInfoConstants userInfoConst,HeliosframeworkData heliosData) throws ScreenException {

		AppBaseScreen.phrscEnv = phrscEnv;
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
				System.setProperty("webdriver.chrome.driver", "./chromedriver/chromedriver.exe");
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
			//			wait.until(ExpectedConditions.elementToBeClickable(by));

		}

		catch (Exception e) {
			log.info("Entering:------presenceOfElementLocated()-----End"
					+ "--(" + locator + ")--");
			File scrFile = ((TakesScreenshot) driver)
			.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,
					new File(GetCurrentDir.getCurrentDirectory() + "/"
							+ methodName + ".png"));
			Assert.assertNull(scrFile);

		}
	}

	Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
		log.info("Entering presenceOfElementLocated");
		return new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					Thread.sleep(500);
					log.info("Waiting for Element to present");
				} catch (InterruptedException e) {
					log.info("Exception in presenceOfElementLocated"
							+ e.getMessage());
				}
				return driver.findElement(locator);
			}
		};
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
		log.info("@loginPage::****** executing ****");
		try {
			Thread.sleep(2000);
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

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeCreate(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(phpConst.getPhpArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(phpConst.getPhpArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(phpConst.getPhpArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, phpConst.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(phpConst.getPhpArchetypeName(), methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeEditApp(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(phpConst.getPhpArchetypeEditLink(), methodName);
			getXpathWebElement(phpConst.getPhpArchetypeEditLink());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServer(), methodName);
			getXpathWebElement(heliosUiConst.getWampServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getWampServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlDb(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlDb());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlVersionValue());
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
			waitForElementPresent(heliosUiConst.getCucumberValue(), methodName);
			getXpathWebElement(heliosUiConst.getCucumberValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(30000);
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void phpArchetypeUpdateApp(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpArchetypeUpdateApp executing");
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
	
	public void phpConfigurationCreate(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(phpConst.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(phpConst.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(phpConst.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(phpConst.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(phpConst.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void phpBuild(String methodName,PhpConstants phpConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@phpBuild executing");
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
			sendKeys(phpConst.getBuildName());
			
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


	public void drupal6ArchetypeCreate(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(drupal6Const.getDrupal6ArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(drupal6Const.getDrupal6ArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(drupal6Const.getDrupal6ArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, drupal6Const.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(drupal6Const.getDrupal6ArchetypeName(), methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void drupal6ArchetypeEditApp(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(drupal6Const.getDrupal6ArchetypeEditApp(), methodName);
			getXpathWebElement(drupal6Const.getDrupal6ArchetypeEditApp());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServer(), methodName);
			getXpathWebElement(heliosUiConst.getWampServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getWampServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlDb(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlDb());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlVersionValue());
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
			waitForElementPresent(heliosUiConst.getCucumberValue(), methodName);
			getXpathWebElement(heliosUiConst.getCucumberValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			Thread.sleep(20000);
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal6ArchetypeUpdateApp(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ArchetypeUpdateApp executing");
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
	
	public void drupal6ConfigurationCreate(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6ConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(drupal6Const.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(drupal6Const.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(drupal6Const.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(drupal6Const.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(drupal6Const.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal6Build(String methodName,Drupal6Constants drupal6Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal6Build executing");
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
			sendKeys(drupal6Const.getBuildName());
			
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

	public void drupal7ArchetypeCreate(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(drupal7Const.getDrupal7ArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(drupal7Const.getDrupal7ArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(drupal7Const.getDrupal7ArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, drupal7Const.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(drupal7Const.getDrupal7ArchetypeName(), methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal7ArchetypeEditApp(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(drupal7Const.getDrupal7ArchetypeEditApp(), methodName);
			getXpathWebElement(drupal7Const.getDrupal7ArchetypeEditApp());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServer(), methodName);
			getXpathWebElement(heliosUiConst.getWampServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getWampServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlDb(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlDb());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlVersionValue());
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
			waitForElementPresent(heliosUiConst.getCucumberValue(), methodName);
			getXpathWebElement(heliosUiConst.getCucumberValue());
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
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void drupal7ArchetypeUpdateApp(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ArchetypeUpdateApp executing");
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
	
	public void drupal7ConfigurationCreate(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7ConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(drupal7Const.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(drupal7Const.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(drupal7Const.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(drupal7Const.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(drupal7Const.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void drupal7Build(String methodName,Drupal7Constants drupal7Const) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@drupal7Build executing");
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
			sendKeys(drupal7Const.getBuildName());
			
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

	public void wordpressArchetypeCreate(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(wordpressConst.getWordpressArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(wordpressConst.getWordpressArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(wordpressConst.getWordpressArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, wordpressConst.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(wordpressConst.getWordpressArchetypeEditApp(), methodName);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


	public void wordpressArchetypeEditApp(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(wordpressConst.getWordpressArchetypeEditApp(), methodName);
			getXpathWebElement(wordpressConst.getWordpressArchetypeEditApp());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServer(), methodName);
			getXpathWebElement(heliosUiConst.getWampServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getWampServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getWampServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlDb(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlDb());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlVersionValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalFramework(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalFramework());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getTestNGValue(), methodName);
			getXpathWebElement(heliosUiConst.getTestNGValue());
			click();

			Thread.sleep(500);;
			waitForElementPresent(heliosUiConst.getFunctionalTool(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalTool());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getCucumberValue(), methodName);
			getXpathWebElement(heliosUiConst.getCucumberValue());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersion(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void wordpressArchetypeUpdateApp(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressArchetypeUpdateApp executing");
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
	
	public void wordpressConfigurationCreate(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(wordpressConst.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(wordpressConst.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(wordpressConst.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(wordpressConst.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(wordpressConst.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void wordpressBuild(String methodName,WordpressConstants wordpressConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@wordpressBuild executing");
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
			sendKeys(wordpressConst.getBuildName());
			
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
	
	

	public void javaWSArchetypeCreate(String methodName,JavaWebserviceConstants javaWSConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaWSArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(javaWSConst.getJavaWSArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(javaWSConst.getJavaWSArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(javaWSConst.getJavaWSArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, javaWSConst.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(javaWSConst.getJavaWSArchetypeName(), methodName);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void javaWSArchetypeEditApp(String methodName,JavaWebserviceConstants javaWSConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaWSArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(javaWSConst.getJavaWSArchetypeEditApp(), methodName);
			getXpathWebElement(javaWSConst.getJavaWSArchetypeEditApp());
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
			waitForElementPresent(heliosUiConst.getDbDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlDb(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlDb());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlVersionValue());
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
			waitForElementPresent(heliosUiConst.getCucumberValue(), methodName);
			getXpathWebElement(heliosUiConst.getCucumberValue());
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
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void javaWSArchetypeUpdateApp(String methodName,JavaWebserviceConstants javaWSConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaWSArchetypeUpdateApp executing");
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
	
	public void javaWSConfigurationCreate(String methodName,JavaWebserviceConstants javaWSConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaWSConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(javaWSConst.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(javaWSConst.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(javaWSConst.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(javaWSConst.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(javaWSConst.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void javaWSBuild(String methodName,JavaWebserviceConstants javaWSConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaWSBuild executing");
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
			sendKeys(javaWSConst.getBuildName());
			
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

	public void nodeJsArchetypeCreate(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(nodeJsConst.getNodeJsArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(nodeJsConst.getNodeJsArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(nodeJsConst.getNodeJsArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, nodeJsConst.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(nodeJsConst.getNodeJsArchetypeName(), methodName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void nodeJsArchetypeEditApp(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(nodeJsConst.getNodeJsEditApp(), methodName);
			getXpathWebElement(nodeJsConst.getNodeJsEditApp());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getNodeJsServer(), methodName);
			getXpathWebElement(heliosUiConst.getNodeJsServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getNodeJsServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getNodeJsServerVersion());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlDb(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlDb());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getDbVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getDbVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getMysqlVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getMysqlVersionValue());
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
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void nodeJsArchetypeUpdateApp(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeUpdateApp executing");
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
	
	public void nodeJsConfigurationCreate(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(nodeJsConst.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void nodejsWSBuild(String methodName,NodeJsConstants nodeJsConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodejsWSBuild executing");
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
			sendKeys(nodeJsConst.getBuildName());
			
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

	public void javaStandaloneArchetypeCreate(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@nodeJsArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(javaStandalone.getJavaSAArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(javaStandalone.getJavaSAArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(javaStandalone.getJavaSAArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, javaStandalone.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(javaStandalone.getJavaSAArchetypeName(), methodName);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void javaStandaloneArchetypeEditApp(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(javaStandalone.getJavaSAArchetypeEditApp(), methodName);
			getXpathWebElement(javaStandalone.getJavaSAArchetypeEditApp());
			click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void javaStandaloneBuild(String methodName,JavaStandaloneConstants javaStandalone) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@javaStandaloneBuild executing");
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
			sendKeys(javaStandalone.getBuildName());
			
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


	public void sitecoreArchetypeCreate(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(sitecoreConst.getSitecoreArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(sitecoreConst.getSitecoreArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(sitecoreConst.getSitecoreArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, sitecoreConst.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(sitecoreConst.getSitecoreArchetypeName(), methodName);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sitecoreArchetypeEditApp(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(sitecoreConst.getSitecoreArchetypeEditApp(), methodName);
			getXpathWebElement(sitecoreConst.getSitecoreArchetypeEditApp());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getIisServer(), methodName);
			getXpathWebElement(heliosUiConst.getIisServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getIisServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getIisServerVersion());
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
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sitecoreArchetypeUpdateApp(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreArchetypeUpdateApp executing");
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
	
	public void sitecoreConfigurationCreate(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(sitecoreConst.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sitecoreBuild(String methodName,SitecoreConstants sitecoreConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sitecoreBuild executing");
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
			sendKeys(sitecoreConst.getBuildName());
			
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


	public void sharePointArchetypeCreate(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(sharePointConst.getSharepointArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(sharePointConst.getSharepointArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(sharePointConst.getSharepointArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, sharePointConst.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveMobileLayer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectCreateButton(), methodName);
			getXpathWebElement(heliosUiConst.getProjectCreateButton());
			click();
			Thread.sleep(10000);
			isTextPresent(sharePointConst.getSharepointArchetypeName(), methodName);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void sharePointArchetypeEditApp(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(sharePointConst.getSharepointArchetypeEditApp(), methodName);
			getXpathWebElement(sharePointConst.getSharepointArchetypeEditApp());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getSharepointServer(), methodName);
			getXpathWebElement(heliosUiConst.getSharepointServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getSharepointServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getSharepointServerVersion());
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
			
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void sharePointArchetypeUpdateApp(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointArchetypeUpdateApp executing");
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
	
	public void sharePointConfigurationCreate(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharePointConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(sharePointConst.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(sharePointConst.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(sharePointConst.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(sharePointConst.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(sharePointConst.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void sharepointBuild(String methodName,SharepointConstants sharePointConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@sharepointBuild executing");
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
			sendKeys(sharePointConst.getBuildName());
			
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

	public void aspDotnetArchetypeCreate(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeCreate executing");
		try {
			Thread.sleep(500);
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
			sendKeys(aspDotnetConst.getAspDotnetArchetypeName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getProjectDescription(), methodName);
			getXpathWebElement(heliosUiConst.getProjectDescription());
			click();
			clear();
			sendKeys(aspDotnetConst.getAspDotnetArchetypeDesc());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerAppCode(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerAppCode());
			click();
			clear();
			sendKeys(aspDotnetConst.getAspDotnetArchetypeAppCode());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAppLayerTechnology(), methodName);
			getXpathWebElement(heliosUiConst.getAppLayerTechnology());
			selectText(element, aspDotnetConst.getTechnologyValue());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getRemoveWebLayer(), methodName);
			getXpathWebElement(heliosUiConst.getRemoveWebLayer());
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
			isTextPresent(aspDotnetConst.getAspDotnetArchetypeName(), methodName);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetArchetypeEditApp(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeEditApp executing");
		try {
			Thread.sleep(500);
			waitForElementPresent(aspDotnetConst.getAspDotnetArchetypeEditApp(), methodName);
			getXpathWebElement(aspDotnetConst.getAspDotnetArchetypeEditApp());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getIisServer(), methodName);
			getXpathWebElement(heliosUiConst.getIisServer());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getServerVersionDropdown(), methodName);
			getXpathWebElement(heliosUiConst.getServerVersionDropdown());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getIisServerVersion(), methodName);
			getXpathWebElement(heliosUiConst.getIisServerVersion());
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
			waitForElementPresent(heliosUiConst.getFunctionalToolVersionValue(), methodName);
			getXpathWebElement(heliosUiConst.getFunctionalToolVersionValue());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);
			
			


		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void aspDotnetArchetypeUpdateApp(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetArchetypeUpdateApp executing");
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

	public void aspDotnetConfigurationCreate(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetConfigurationCreate executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(heliosUiConst.getConfigTab(), methodName);
			getXpathWebElement(heliosUiConst.getConfigTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigButton(), methodName);
			getXpathWebElement(heliosUiConst.getConfigButton());
			click();

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getAddConfig(), methodName);
			getXpathWebElement(heliosUiConst.getAddConfig());
			click();
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getChooseServer(), methodName);
			getXpathWebElement(heliosUiConst.getChooseServer());
			click();
			

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigName(), methodName);
			getXpathWebElement(heliosUiConst.getConfigName());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigName());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigHost(), methodName);
			getXpathWebElement(heliosUiConst.getConfigHost());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigHost());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigPort(), methodName);
			getXpathWebElement(heliosUiConst.getConfigPort());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigPort());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigDeployDir(), methodName);
			getXpathWebElement(heliosUiConst.getConfigDeployDir());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigDeployDir());

			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigContext(), methodName);
			getXpathWebElement(heliosUiConst.getConfigContext());
			click();
			clear();
			sendKeys(aspDotnetConst.getConfigContext());
			
			Thread.sleep(500);
			waitForElementPresent(heliosUiConst.getConfigUpdate(), methodName);
			getXpathWebElement(heliosUiConst.getConfigUpdate());
			click();
			Thread.sleep(20000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void aspDotnetBuild(String methodName,AspDotNetConstants aspDotnetConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@aspDotnetBuild executing");
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
			sendKeys(aspDotnetConst.getBuildName());
			
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

	public void ButtonEnabled(String Xpath) throws InterruptedException,
	IOException, Exception {
		Thread.sleep(500);
		try {
			if (driver.findElement(By.xpath(Xpath)).isEnabled()) {
				System.out
				.println("-------New_ReleaseAvailable----------------");
			} else if (driver.findElement(By.xpath(Xpath)).isSelected()) {
				{
					System.out
					.println("------- New_Release_Available----------------");
				}
			} else {
				System.out.println("----------No_Release_Available----------");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void MouseOverEvents(String Xpath) throws NullPointerException,
	IOException {
		log.info("@Entering:---------------MouseOverEvents()------------------------");
		try {

			element = driver.findElement(By.xpath(Xpath));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();

		} catch (Throwable t) {
			log.info("--------Exception in MouseOverEvents-------------- ");
		}
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
		String RUN_AGAINST_SOURCE = "server started";
		String RUN_AGAINST_SOURCE_FAILED = "server started";
		boolean statusStringFound = false;
		while (!statusStringFound) {
			if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_SUCCESS)) {
				return BUILD_SUCCESS;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(BUILD_FAILURE)) {
				return BUILD_FAILURE;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(RUN_AGAINST_SOURCE)) {
				return BUILD_SUCCESS;
			} else if (driver.findElement(By.tagName("body")).getText()
					.contains(RUN_AGAINST_SOURCE_FAILED)) {
				return BUILD_FAILURE;
			}

		}
		return null;
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

	public boolean configServerSuccessFailureLoop()
	throws InterruptedException, IOException, Exception {
		String foundString = serverStatusVerifier();
		if (foundString
				.equalsIgnoreCase("Configuration Server created successfully")) {
			return true;
		} else
			return false;
	}

	private String serverStatusVerifier() {
		String CONFIG_SERVER = "Configuration Server created successfully";
		boolean statusStringFound = false;
		while (!statusStringFound) {
			if (driver.findElement(By.tagName("body")).getText()
					.contains(CONFIG_SERVER)) {
				return CONFIG_SERVER;
			}
		}
		return null;
	}

	public boolean configDbSuccessFailureLoop() throws InterruptedException,
	IOException, Exception {
		String foundString = dbStatusVerifier();
		if (foundString
				.equalsIgnoreCase("Configuration Database created successfully")) {
			return true;
		} else
			return false;
	}

	private String dbStatusVerifier() {
		String CONFIG_DB = "Configuration Database created successfully";
		boolean statusStringFound = false;
		while (!statusStringFound) {
			if (driver.findElement(By.tagName("body")).getText()
					.contains(CONFIG_DB)) {
				return CONFIG_DB;
			}
		}
		return null;
	}

	public boolean successFailureLoop2() throws InterruptedException,
	IOException, Exception {
		String foundString = buildStatusVerifier2();
		if (foundString.equalsIgnoreCase("Selected Features")) {
			return true;
		} else
			return false;
	}

	private String buildStatusVerifier2() {
		String SELECT_FEATURE = "Selected Features";
		// String BUILD_FAILURE = "BUILD FAILURE";
		// String ERROR = "[ERROR]";
		boolean statusStringFound = false;
		while (!statusStringFound) {
			if (driver.findElement(By.tagName("body")).getText()
					.contains(SELECT_FEATURE)) {
				return SELECT_FEATURE;
			}
		}
		return null;
	}

	/*public void appLayerBuildTab(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@appLayerBuildTab::****** executing ****");
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
			boolean buildTestResult = successFailureLoop();
			System.out.println("buildTestResult:::::::" + buildTestResult);
			Assert.assertTrue(buildTestResult);
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void appLayerDeploy(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@appLayerDeploy::******executing ****");
		try {
			Thread.sleep(2000);
			waitForElementPresent(phrsc.getDeployBtn(), methodName);
			getXpathWebElement(phrsc.getDeployBtn());
			click();

			Thread.sleep(5000);
			waitForElementPresent(phrsc.getSelectSQL(), methodName);
			getXpathWebElement(phrsc.getSelectSQL());
			click();

			Thread.sleep(2000);
			waitForElementPresent(phrsc.getDeployOK(), methodName);
			getXpathWebElement(phrsc.getDeployOK());
			click();
			Thread.sleep(20000);
			boolean deployTestResult = successFailureLoop();
			System.out.println("deployTestResult:::::" + deployTestResult);
			Assert.assertTrue(deployTestResult);
			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void appLayerDeploy1(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@appLayerDeploy1::******executing ****");
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
			System.out.println("deployTestResult::::::" + deployTestResult);
			Assert.assertTrue(deployTestResult);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void appLayerRunAgainstSource(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@appLayerRunAgainstSource::******executing Deploy****");
		try {
			Thread.sleep(2000);
			waitForElementPresent(phrsc.getBuildTab(), methodName);
			getXpathWebElement(phrsc.getBuildTab());
			click();

			Thread.sleep(500);
			waitForElementPresent(phrsc.getRunAgainstSrcBtn(), methodName);
			getXpathWebElement(phrsc.getRunAgainstSrcBtn());
			click();

			Thread.sleep(8000);
			waitForElementPresent(phrsc.getSelectSQL(), methodName);
			getXpathWebElement(phrsc.getSelectSQL());
			click();

			Thread.sleep(5000);
			waitForElementPresent(phrsc.getRunAgainstSrcRUNBtn(), methodName);
			getXpathWebElement(phrsc.getRunAgainstSrcRUNBtn());
			click();
			Thread.sleep(5000);
			boolean runAgainstTestResult = successFailureLoop();
			System.out.println("runAgainstTestResult::::::"
					+ runAgainstTestResult);
			Assert.assertTrue(runAgainstTestResult);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void javaStandaloneProjectHelloWorldBuildTab(String methodName,
			JavaStandaloneConstants JvaStdAloneConst) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@javaStandaloneProjectHelloWorldBuildTab::****** executing ****");
		try {
			Thread.sleep(5000);
			waitForElementPresent(phrsc.getMenuTab(), methodName);
			getXpathWebElement(phrsc.getMenuTab());
			click();
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}*/

}
