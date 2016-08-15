package com.qtpselenium.hybrid.product;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.hybrid.Keywords;
import com.qtpselenium.hybrid.base.BaseTest;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.DataUtil;
import com.qtpselenium.hybrid.util.ExtentManager;
import com.qtpselenium.hybrid.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FilterMobileTest extends BaseTest {

	
	@BeforeTest
	public void init(){
		testName ="FilterMobileTest";
		xls = new Xls_Reader(Constants.PRODUCTSUITE_XLS);
	}
	
	@Test(dataProvider="getData")
	public void filterMobileTest(Hashtable <String, String> data){
		
		test = rep.startTest(testName);
		test.log(LogStatus.INFO, "Starting FilterMobileTest Test");
		test.log(LogStatus.INFO, data.toString());
		 
		
		app = new Keywords(test);
		test.log(LogStatus.INFO, "Executing keywords");
		if(!DataUtil.isRunnable(testName, xls) || data.get(Constants.RUNMODE_COL).equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test beacuse Runmode is N");
			 throw new SkipException("Skipping the test beacuse Runmode is N");
		}
		app.excuteKeywords(testName, xls , data);
		app.getGenericKeywords().reportPass("Test Passed");	
		app.getGenericKeywords().takeScreenshot();
		
		}
	
	

	
}
