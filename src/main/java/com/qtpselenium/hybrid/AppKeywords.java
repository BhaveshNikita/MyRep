package com.qtpselenium.hybrid;



import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qtpselenium.hybrid.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AppKeywords extends GenericKeywords {

	public AppKeywords(ExtentTest test) {
		super(test);
		
	}
	
	public String verifyLoginDetails(Hashtable<String, String> testData){
		
		testData.get("Username");
		
		return Constants.PASS;
		
	}
	public String login(String username, String password){
		getElement("LoginLink_xpath").click();
		getElement("email_xpath").sendKeys(username);
		getElement("Password_xpath").sendKeys(password);
		getElement("SignInButton_xpath").click();
		return Constants.PASS;
	}

  	public String ebayLogin(Hashtable<String, String> testData) {
		test.log(LogStatus.INFO, "loging in with "+ testData.get("Username")+ " / "+ testData.get("Password") );
		return login(testData.get("Username"), testData.get("Password"));
		
	}
  	
  	public String actionOnElement(String locatorKey){
		WebElement AllLinks = getElement(locatorKey);
		Actions act = new Actions(driver);
		act.moveToElement(AllLinks).build().perform();
		//click on link 
		
		return Constants.PASS;
	
  	}
  	
 	public String ToClickOnlink(String locatorKey){
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(locatorKey))));
		getElement(locatorKey).click();
		return Constants.PASS;
	
 	}
  	
  	
	
	public String defaultLogin(){
		 return login(prop.getProperty("username"), prop.getProperty("password"));
		
	}

	public String verifyebayLogin(String expectedResults) {
		test.log(LogStatus.INFO, "Validating login");
		boolean result = isElementPresent("Myebay_xpath");
		String actualResults = "";
		if(result)
			actualResults = "Success";
		else
			actualResults = "Failure";
		if(!actualResults.equals(expectedResults)){
			return "Failed - " + "Got actual result is " + actualResults ; 
			}
		return Constants.PASS;
		}

	public String filterMobileAndValidation(Hashtable<String, String> testData){
		String brandName = testData.get("MobileCompany");
		driver.findElement(By.xpath("//span[text()='"+brandName+"']")).click();
		driver.findElement(By.xpath(prop.getProperty("buyitnow_xpath"))).click();
		input("Minprice_xpath", testData.get("MinPrice"));
		wait("5000");
		input("Maxprice_xpath", testData.get("MaxPrice"));
		driver.findElement(By.xpath(prop.getProperty("Submitprice_xpath"))).click();
		
		String SelectedCompany = testData.get("MobileCompany") +" Mobile Phones";
		String ActualSelectedComapny = getElement("mobiletitle_xpath").getText();
		System.out.println(SelectedCompany + "----" + ActualSelectedComapny );
		if(!SelectedCompany.equals(ActualSelectedComapny)){
			 return Constants.FAIL;
		}
		
		return Constants.PASS;
	}




}
