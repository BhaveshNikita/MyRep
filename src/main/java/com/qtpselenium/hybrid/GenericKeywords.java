package com.qtpselenium.hybrid;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.qtpselenium.hybrid.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenericKeywords {
	
	public WebDriver driver = null;
	public Properties prop;
	ExtentTest test;
	
	
	public GenericKeywords(ExtentTest test){
		this.test=test;
		
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
		 prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		}

		public String openBrowser(String browserName){
		test.log(LogStatus.INFO, "opening Browser " + browserName);
		if(browserName.equals("Mozilla")){
			driver = new FirefoxDriver();
		}else if(browserName.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriverexe"));
			driver= new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
		return Constants.PASS;
		
		}
	
		public String navigate(String urlKey){
			test.log(LogStatus.INFO, "Navigating to " + prop.getProperty(urlKey));
			driver.get(prop.getProperty(urlKey));
			return Constants.PASS;
			
		}
	
		public String click(String locatorKey){
		test.log(LogStatus.INFO, "Clicking on " + locatorKey );
		getElement(locatorKey).click();
		return Constants.PASS;
		}
	
		public String input(String locatorKey, String data){
		test.log(LogStatus.INFO, "Typing in " + locatorKey + " data is " + data);
		getElement(locatorKey).sendKeys(data);
		return Constants.PASS;
		}
	

	
		public String closeBrowser(){
		test.log(LogStatus.INFO, "Closing browser");
		//driver.quit();
		return Constants.PASS;
		}
		
		
	/***************************Validation Function***********************************/
		
		public String verifyText(String locatorKey,String expectedTextKey){
		test.log(LogStatus.INFO, "Verifying text in " + locatorKey + "with expectedtext " + expectedTextKey);
		String actualText =  getElement(locatorKey).getText().trim(); 
		String expectedText = prop.getProperty(expectedTextKey);
		if(actualText.equals(expectedText))
			return Constants.PASS;
		else
			return Constants.FAIL;
		}
		
		public String verifyElementPresent(String locatorKey){
			
			boolean result = isElementPresent(locatorKey);
			if(result)
				return Constants.PASS;
			else
				return Constants.FAIL + "Could not find element " + locatorKey;
			}
		
		public String verifyElementNotPresent(String locatorKey){
			
			boolean result = isElementPresent(locatorKey);
			if(!result)
				return Constants.PASS;
			else
				return Constants.FAIL + "Could find element " + locatorKey;  
			}
		
		public String scrollTo(String xpathKey){
			test.log(LogStatus.INFO, "Scrolling to element " + xpathKey );
			int y = getElement(xpathKey).getLocation().y;
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollTo(0."+(y-200)+")");
			return Constants.PASS;
		}
		
		public String wait(String timeout){
			test.log(LogStatus.INFO, "waiting for  " + timeout );
			try {
				Thread.sleep(Integer.parseInt(timeout));
			} catch (NumberFormatException e  ) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			return Constants.PASS;
			}
		
		
		
	/***************************Utility Function*********************************/
	
		public WebElement getElement(String locatorKey ){
		WebElement e = null;
		try{
		if(locatorKey.endsWith("_id"))
			e= driver.findElement(By.id(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_name"))
			e= driver.findElement(By.name(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_xpath"))
			e= driver.findElement(By.xpath(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_linkText"))
			e= driver.findElement(By.linkText(prop.getProperty(locatorKey)));
		}catch(Exception ex){
			
			test.log(LogStatus.FAIL, "Failure in finding elements -" + locatorKey);
			//fail the test and report the error
			reportFailure("Failure in finding elements -" + locatorKey);
			Assert.fail("Failure in finding elements -" + locatorKey);
		}
		return e;
		}
		
		public boolean isElementPresent(String locatorKey){
			List<WebElement> elementList=null;
			if(locatorKey.endsWith("_id"))
				elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_xpath"))
				elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_linkText"))
				elementList= driver.findElements(By.linkText(prop.getProperty(locatorKey)));
			if(elementList.size()==0)
				return false;		
			else
				return true;
			}
	/***************************Reporting***********************************/
	
	public void reportPass(String msg){
		test.log(LogStatus.INFO, msg);
		}
	
	public void reportFailure(String msg){
		test.log(LogStatus.FAIL, msg);
		takeScreenshot();
		//Assert.fail();
		}
	
	public void takeScreenshot(){
			
		//filename of screenshot
		Date d=new Date();
		String screenshotfile=d.toString().replace(":", "_").replace(" ", "_")+".png";	
		//store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\screenshots\\" + screenshotfile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    //put screenshot file in extent report
	    test.log(LogStatus.INFO, "Screenshot->" + test.addScreenCapture(System.getProperty("user.dir")+"\\screenshots\\" + screenshotfile));
		
	}
	
	
	
	
}
