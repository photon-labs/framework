package com.photon.phresco.Screens;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.photon.phresco.selenium.util.GetCurrentDir;
import com.photon.phresco.selenium.util.ScreenActionFailedException;
import com.photon.phresco.selenium.util.ScreenException;

public class WebDriverAbstractBaseScreen extends BaseScreen {
	
	public static WebElement element;	
	private By by;
	private Log log = LogFactory.getLog(getClass());
	private static final long TIMEOUT=120;
    
	
	protected WebDriverAbstractBaseScreen(){
		
	}
	protected WebDriverAbstractBaseScreen(String host,int port,String browser,String url,String speed,String context) throws AWTException, IOException, ScreenActionFailedException{
		super(host,port,browser,url,speed,context);
		log.info("Entering:********WebDriverAbstractBaseScreen Constructor********");
	}

	public WebDriverBaseScreen getXpathWebElement(String xpath)throws Exception{
		log.info("Entering:********getXpathWebElement********");
		try{
			
			element=driver.findElement(By.xpath(xpath));
			System.out.println("------------------->"+element);
			
		}catch(Throwable t){
			log.info("Entering:*********Exception in getXpathWebElement()******");
			t.printStackTrace();
			
		}
		return new WebDriverBaseScreen();
	}
	public WebDriverBaseScreen getIdWebElement(String id)throws ScreenException{
		log.info("Entering:****getIdWebElement**********");
		try{
		element=driver.findElement(By.id(id));
		System.out.println("------------------->"+element);
		
		}catch(Throwable t){
			log.info("Entering:*********Exception in getIdWebElement()******");
			t.printStackTrace();
			
		}
		return new WebDriverBaseScreen();
	}
	public WebDriverBaseScreen getcssWebElement(String selector)throws ScreenException{
		log.info("Entering:****getIdWebElement**********");
		try{
		element=driver.findElement(By.cssSelector(selector));
		
		
		
		}catch(Throwable t){
			log.info("Entering:*********Exception in getIdWebElement()******");
			
			t.printStackTrace();
			
		}
		return new WebDriverBaseScreen();
	}
	/**
	 * 
	 * @param by
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getElementValue(By by) throws ScreenException {
		if (by != null) {
			log.info("getFindElement:---Getting the element of-------" + by);
			element = driver.findElement(by);
		} else {
			throw new ScreenException("Value should not be null" + by);
		}
		return new WebDriverBaseScreen();

	}
	/**
	 * 
	 * @param value
	 * @return
	 * @throws ScreenException
	 */
	public By getBySelector(String value) throws ScreenException {

		try {
			log.info("getBySelector():-------Entered---------");
			if (value.startsWith("//")) {
				this.by = By.xpath(value);
				log.info("--------Getting the ### Xpath ### Expression ---------"+this.by);
			} else if (value.startsWith("link")) {
				this.by = By.linkText(value);
				log.info("--------Getting the ### Link ### Expression ---------"+this.by);
			} else if (value.startsWith("css")) {
				this.by = By.cssSelector(value);
				log.info("--------Getting the ### CSS ### Expression ---------"+this.by);
			} else {
				this.by = By.id(value);
				log.info("--------Getting the ### id ### Expression ---------"+this.by);
			}

		} catch (Exception e) {
			log.info("Exception:-------getBySelector()---------" + value);
		}
		
		return this.by;

	}

	public void deleteAllCookies()throws IOException, Exception{
		try{
			log.info("Entering:*********deleteAllCookies()******");
			driver.manage().deleteAllCookies();
		}catch(Exception e){
		e.printStackTrace();
		ScreenCapturer();
		}
	}
	/**
	 * 
	 * @param linkText
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getLinkWebElement(String linkText)
			throws ScreenException {
		log.info("Entering:****getIdWebElement**********");
		try {
			this.by = getBySelector(linkText);
		} catch (Throwable t) {
			log.info("Entering:*********Exception in getLinkWebElement()******");
			t.printStackTrace();
		}
		return getElementValue(this.by);
	}
	/**
	 * 
	 * @param value
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getWebElement(String value)
			throws ScreenException {
		log.info("Entering:****getIdWebElement**********");
		try {
			this.by = getBySelector(value);
			// element = driver.findElement(this.by);
		} catch (Throwable t) {
			log.info("Entering:*********Exception in getIdWebElement()******");
			t.printStackTrace();

		}
		return getElementValue(this.by);
	}
	 /**
	  * 
	  * @param Xpath
	  * @return
	  * @throws ScreenException
	  */
	public By getXpathByValue(String Xpath) throws ScreenException {
		if (Xpath != null) {
			this.by = By.xpath(Xpath);
		} else {
			throw new ScreenException("Enter:---getXpathByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ScreenException
	 */
	public By getIdByValue(String id) throws ScreenException {
		if (id != null) {
			this.by = By.id(id);
			
		} else {
			throw new ScreenException("Enter:---getIdByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param css
	 * @return
	 * @throws ScreenException
	 */
	public By getCSSByValue(String css) throws ScreenException {
		if (css != null) {
			this.by = By.id(css);
			
		} else {
			throw new ScreenException("Enter:---getCSSByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param link
	 * @return
	 * @throws ScreenException
	 */
	public By getLinkByValue(String link) throws ScreenException {
		if (link != null) {
			this.by = By.id(link);

		} else {
			throw new ScreenException("Enter:---getLinkByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param className
	 * @return
	 * @throws ScreenException
	 */
	public By getClassNameByValue(String className) throws ScreenException {
		if (className != null) {
			this.by = By.id(className);

		} else {
			throw new ScreenException("Enter:---getClassNameByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param name
	 * @return
	 * @throws ScreenException
	 */
	public By getNameByValue(String name) throws ScreenException {
		if (name != null) {
			this.by = By.id(name);

		} else {
			throw new ScreenException("Enter:---getClassNameByValue()-----");
		}
		return this.by;
	}
	public By getTagByValue(String name) throws ScreenException {
		if (name != null) {
			this.by = By.tagName(name);

		} else {
			throw new ScreenException("Enter:---getClassNameByValue()-----");
		}
		return this.by;
	}
	
	public void waitForElementPresent(String locator, String methodName) throws IOException, Exception{
		try{
		log.info("Entering:*********waitForElementPresent()******");
	    By by= By.xpath(locator);
		WebDriverWait wait=new WebDriverWait(driver, TIMEOUT);
		
		log.info("Waiting:*************For element" +locator);
		wait.until(presenceOfElementLocated(by));
		}
		catch(Exception e){			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(GetCurrentDir.getCurrentDirectory()+"\\" + methodName + ".png"));
//		    e.printStackTrace();
		    throw new Exception("********Exception*******");
		    
		}
	
	}

	Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) { 
		log.info("Entering:*********presenceOfElementLocated()******Start");
		  return new Function<WebDriver, WebElement>() { 
		    public WebElement apply(WebDriver driver) { 
		    	log.info("Entering:*********presenceOfElementLocated()******End");
		     return driver.findElement(locator);
		     
		    }
		    
		  };
		  
	
		 }
	public void waitForTextPresent(String text) throws IOException, Exception{
		try{
		log.info("Entering:*********waitForElementPresent()******");
	    //By by= By.linkText(locator);
		WebDriverWait wait=new WebDriverWait(driver, TIMEOUT);
		driver.findElement(By.tagName(text)).getText();
		log.info("Waiting:*************One second***********");
		//wait.until(presenceOfElementLocated(by));
		}catch(Exception e){
			e.printStackTrace();
			ScreenCapturer();
			
		}
		
	
	}
	
	
	/*public boolean isTextPresent(String textValue) {
		if (textValue != null) {
			Boolean textCheck = driver.getPageSource().contains(textValue);
			Assert.assertTrue("Text present", textCheck);
		} else {

			throw new RuntimeException("---- Text not existed----");

		}
		return true;
	}*/
	
	public boolean isTextPresentBuild(String text) throws InterruptedException, ScreenException {
		
        if (text!= null){
        	String build_failure="ERROR";
    		/*WebElement element=driver.findElement(By.tagName("body"));
    		Dimension dimension=element.getSize();
    		String textName=element.getText();*/
     //   boolean value=driver.findElement(By.tagName("body")).getText().contains(text);
        	for(int i=0;i<370;i++){
        	   if(driver.findElement(By.tagName("body")).getText().contains(text)){
        		  break; 
        	   }else{
        		   if(i==369){
        			   throw new RuntimeException("---- Time out for finding the Text----");
        		   }else if(driver.findElement(By.tagName("body")).getText().contains(build_failure)){
        			   System.out.println("*****OPERATION FAILED*****");
        			  // Assert.assertTrue(condition);
        			   throw new ScreenException("*****OPERATION FAILED*****");
        			   //break;
        		   }
        		   Thread.sleep(1000);
        	   }
        	}
          
       
        }
        else
        {
            throw new RuntimeException("---- Text not existed----");
        }
       
        return true;
       
    }    
	
	public void successFailureLoop_isButtonEnabled(String buttonName) throws InterruptedException, IOException,
	Exception {

		for(int i=0;i<90;i++){
			System.out.println("--------For Loop---------");
			Thread.sleep(2000);
			WebElement element=driver.findElement(By.xpath(buttonName));
		if(element.isDisplayed()){
			System.out.println("-------isEnabled()----------------");
		break;	
		}else{
 		   if(i==89){
			   throw new RuntimeException("---- Time out for finding the Text----");
		   }else {
			   //System.out.println("-----------isSelected()-----------------");
			   System.out.println("--------Waiting--------------------");
			   Thread.sleep(1000);
		   }
		  
 		 // Thread.sleep(1000);
	   }
		
		}

}
	
	

	/*public boolean isTextPresent(String name) throws ScreenException{
//		String sourceCode=driver.getPageSource();
		System.out.println("-------textvalue"+name);
		WebElement ele=driver.findElement(getTagByValue("body"));
		String name1=ele.getText();
		String[] name2=name1.split(",");
		
		for(String name3:name2){
			name3.trim();			
		//	System.out.println("pagesource code---------->"+name3);
			if(name3.endsWith(name)){
		//		System.out.println("-------------BREAK-------------");
				break;
			}
			
		}
		return true;
	}*/
	public void ScreenCapturer() throws IOException, Exception{
		try{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(scrFile, new File(GetCurrentDir.getCurrentDirectory()+"\\" + super.getClass().getSimpleName()+ ".png"));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Screen was not captured");
		}
		
	}
	
	
/*
	Function<WebDriver, WebElement> presenceOfTextLocated(final By locator) { 
		log.info("Entering:*********presenceOfElementLocated()******Start");
		 driver.findElement(By.tagName("body")).getText()
		     
		    }
		  
	}*/
	}
	
	
	
	
	
	
	

