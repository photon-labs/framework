package com.photon.phresco.Screens;

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
import com.photon.phresco.selenium.util.Constants;
import com.photon.phresco.selenium.util.GetCurrentDir;
import com.photon.phresco.selenium.util.MagicNumbers;
import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.PhrescoUiConstants;
import com.photon.phresco.uiconstants.PhrescoframeworkData;
import com.photon.phresco.uiconstants.ScenariosConstants;
import com.photon.phresco.uiconstants.phresco_env_config;

public class BaseScreen {

	protected static WebDriver driver;
	private Log log = LogFactory.getLog("BaseScreen");
	private WebElement element;
	protected static phresco_env_config phrscEnv;
	private PhrescoUiConstants phrescoUiConst;
	private PhrescoframeworkData phrescoData;

	
	public BaseScreen()
	{
		phrescoUiConst = new PhrescoUiConstants();
		phrescoData = new PhrescoframeworkData();
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
			Thread.sleep(MagicNumbers.HALF_SECONDS);
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

			WebDriverWait wait = new WebDriverWait(driver, MagicNumbers.THIRTY_SEC);
			wait.until(presenceOfElementLocated(by));

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
					Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
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
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			Select selObj = new Select(element);
			selObj.selectByVisibleText(TextValue);
		} catch (Throwable t) {
			log.info("Entering:---------Exception in SelectextWebElement()--------");
			t.printStackTrace();
		}
	}

	public void click() throws ScreenException {
		log.info("Entering:********click operation start********");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			element.isDisplayed();
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
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			element.clear();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		log.info("Entering:********clear operation end********");

	}

	public void sendKeys(String text) throws ScreenException {
		log.info("Entering:********enterText operation start********");
		try {
			Thread.sleep(MagicNumbers.HALF_SECONDS);
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

	public void toolTipVerifyText(String Xpath,ScenariosConstants scenarioConst) throws Exception
	{   
		String toolTipText = null;
		log.info("@Entering:---------------ToolTipVerification()------------------------");
		try
		{
			element=driver.findElement(By.xpath(Xpath));
			toolTipText = element.getAttribute("title");
			System.out.println("toolTipText::::::"+toolTipText);
			/*toolTipVerifyText=toolTipText;
			System.out.println("toolTipVerifyText:::"+toolTipVerifyText);
			boolean flag = toolTipVerifyText.equalsIgnoreCase(verifyText);	
			System.out.println("Flag:::::::"+flag);
			Assert.assertTrue(flag);*/
		}
		catch(Throwable t)
		{
			log.info("--------Exception in ToolTipVerification-------------- ");
		}
	} 
	
	
	public void cloneEnvironment(String methodName)throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
		}
		log.info("@cloneEnvironment executing");
		try {
			Thread.sleep(3000);
			waitForElementPresent(phrescoUiConst.getConfigTab(), methodName);
			getXpathWebElement(phrescoUiConst.getConfigTab());
			click();
			
			Thread.sleep(3000);
			waitForElementPresent(phrescoUiConst.getCloneEnv(), methodName);
			getXpathWebElement(phrescoUiConst.getCloneEnv());
			click();
			
			Thread.sleep(3000);
			waitForElementPresent(phrescoUiConst.getCloneEnvName(), methodName);
			getXpathWebElement(phrescoUiConst.getCloneEnvName());
			click();
			sendKeys(phrescoData.getCloneEnvName());
			
			Thread.sleep(3000);
			waitForElementPresent(phrescoUiConst.getCloneButton(), methodName);
			getXpathWebElement(phrescoUiConst.getCloneButton());
			click();
				
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	
	public void codeValidateFunctionalTest(String methodName) throws Exception {
		if (StringUtils.isEmpty(methodName)) {
			methodName = Thread.currentThread().getStackTrace()[1]
			                                                    .getMethodName();
		}
		log.info("@codeValidateFunctionalTest executing");
		try {
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeQualityTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeQualityTab());
			click();
			
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeAnalysisTab(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeAnalysisTab());
			click();
			
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateAgainstDropdown(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateAgainstDropdown());
			click();
			
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateFunctionalTest(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateFunctionalTest());
			click();
									
			Thread.sleep(MagicNumbers.HALF_SECONDS);
			waitForElementPresent(phrescoUiConst.getCodeValidateBtn(), methodName);
			getXpathWebElement(phrescoUiConst.getCodeValidateBtn());
			click();
			Thread.sleep(MagicNumbers.TWENTY_THOUSAND_SECONDS);
			
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}


}
