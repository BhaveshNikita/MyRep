package com.qtpselenium.hybrid.util;

import java.util.Hashtable;



public class DataUtil {

	public static Object[][] getData(Xls_Reader xls, String testName){
			
			String sheetName= "Data";
		
			//System.out.println(xls.getCellData(sheetName, 0, 1));
			int testStartRow = 1;
			while(!xls.getCellData(sheetName, 0, testStartRow).equals(testName)){
				testStartRow++;
				}
				System.out.println("Test strats from -" + testStartRow);
				int colStartRowNum = testStartRow + 1;
				int dataStartRowNum = testStartRow + 2;
				
				
				//calculate total rows in testcase
				int rows = 0;
				while(!xls.getCellData(sheetName, 0, dataStartRowNum+rows).equals(""))
					rows++;
				System.out.println("Total rows in testcase -" + rows);
				
				//calculate total cols in testcase
				int cols =0;
				while(!xls.getCellData(sheetName, cols, colStartRowNum).equals(""))
					cols++;
				
				System.out.println("Total cols in testcase -" + cols);
				
				//read data from testcase
				Object[][] data = new Object[rows][1];
				Hashtable<String, String> table = null;
				int dataRow = 0;
				for(int rNum = dataStartRowNum; rNum<dataStartRowNum+rows; rNum++ ){
					table = new Hashtable<String,String>();
					for(int cNum = 0; cNum<cols; cNum++){
					String key = xls.getCellData(sheetName, cNum, colStartRowNum); //testcases column 
					String value = xls.getCellData(sheetName, cNum, rNum); //testcase all rows
					table.put(key, value);
					}
					data[dataRow][0] =table;
					dataRow++;
				}
			return data;	
		
		}
		
		public static boolean isRunnable(String testName, Xls_Reader xls ){
	
		int rows=xls.getRowCount(Constants.TESTCASES_SHEET);
		for(int r = 2; r<=rows; r++){
		 String tName = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, r);
		 if(tName.equals(testName)){
			 String runmode= xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, r);
			 if(runmode.equals("Y"))
			 return true;
		 else
			 return false;
			 }

		}
		return false;
}
	

}
