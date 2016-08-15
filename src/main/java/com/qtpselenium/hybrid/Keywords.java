package com.qtpselenium.hybrid;



import java.util.Hashtable;

import org.testng.Assert;

import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords {
	
	ExtentTest  test;
	AppKeywords app;
	
	public Keywords(ExtentTest  test){
		this.test = test;
	}
	
	
	public void excuteKeywords(String testName, Xls_Reader xls, Hashtable<String, String> testData){
	
	
		String testUnderExecution= testName;

		app = new AppKeywords(test);
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		for(int rNum=2; rNum<=rows; rNum++){
			String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
			if(tcid.equals(testUnderExecution)){
			String  keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
			String object = xls.getCellData(Constants.KEYWORDS_SHEET,Constants.OBJECT_COL, rNum);
			String  key = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum);
			String data = testData.get(key);
			test.log(LogStatus.INFO, tcid +"---" + keyword +"---"+object +"---"+data);
			String result ="";
			if(keyword.equals("openBrowser"))
				result =app.openBrowser(data);
			else if (keyword.equals("navigate"))
				result =app.navigate("url");
			else if (keyword.equals("click"))
				result =app.click(object);
			else if (keyword.equals("input"))
				result =app.input(object,data);
			else if (keyword.equals("closeBrowser"))
				result =app.closeBrowser();
			else if (keyword.equals("verifyLoginDetails"))
				result =app.verifyLoginDetails(testData);
			else if (keyword.equals("verifyElementPresent"))
				result =app.verifyElementPresent(object);
			else if (keyword.equals("ebayLogin"))
				result =app.ebayLogin(testData);
			//else if (keyword.equals("verifyebayLogin"));
				//result =app.verifyebayLogin(testData.get("ExpetcedResult"));
			else if (keyword.equals("defaultLogin"))
				result =app.defaultLogin();
			else if (keyword.equals("wait"))
				result = app.wait(key);
			else if (keyword.equals("scrollTo"))
				result = app.scrollTo(object);
			else if (keyword.equals("ToClickOnlink"))
				result = app.ToClickOnlink(object);
			else if (keyword.equals("actionOnElement"))
				result = app.actionOnElement(object);
			else if (keyword.equals("filterMobileAndValidation"))
				result = app.filterMobileAndValidation(testData);
			
			if(!result.equals(Constants.PASS)){
				app.reportFailure(result);
				Assert.fail(result); 
			}  
			
			
			}
		}
		
		
	}
	
	public AppKeywords getGenericKeywords(){
		return app;
	}

}
