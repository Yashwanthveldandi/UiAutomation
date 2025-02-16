package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import com.aventstack.extentreports.Status;

public class ReadConfig extends Utility{
	
	static Properties prop;
    static String propertyFilePath= System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties";
    
    public static int getElementsCount(By element) {
		return getDriver().findElements(element).size();
		
	}
	
	public ReadConfig()
	{
		File src = new File(propertyFilePath);
		try
		{
			FileInputStream fis=new FileInputStream(src);
			prop=new Properties();
			prop.load(fis);
		}
		catch(Exception e)
		{
			System.out.println("Exception is "+e.getMessage());
		}
	}
	public String getApplicationURL()
	{
		String url=prop.getProperty("baseurl");
		return url;
		
	}
	public String getBrowser()
	{
		String browser=prop.getProperty("browser");
		return browser;
		
	}
	public String getUserName()
	{
		String username=prop.getProperty("uName");
		return username;
		
	}
	public String getPassword()
	{
		String password=prop.getProperty("Password");
		return password;
	}
	public String getExcelPath()
	{
		String excelPath=System.getProperty("user.dir") + "\\Datatable\\TestData.xlsx";
		return excelPath;
	}
	
			
			//needed
			public void putSuite_1Passvalue(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("suite1_passcount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update suite1 passcount : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update suite1 passcount :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getSuite_1Passvalue()
			{
				String getPassValue_1=prop.getProperty("suite1_passcount");
				return getPassValue_1;
			}
			public void putSuite_1Failvalue(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("suite1_failcount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update suite1 failcount: " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update suite1 failcount :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getSuite_1Failvalue()
			{
				String getFailValue_1=prop.getProperty("suite1_failcount");
				return getFailValue_1;
			}
			public void putSuite_1Totalvalue(String totalCount) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("suite1_totalcount", totalCount);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update suite1 totalcount : " + totalCount + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update suite1 totalcount :" + totalCount + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getSuite_1Totalvalue()
			{
				String getTotalValue_1=prop.getProperty("suite1_totalcount");
				return getTotalValue_1;
			}
			public void putSuite_1_starttime(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("suite1_startTime", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update suite1 start time : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update suite1 start time  :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getSuite_1_starttime()
			{
				String getStartTime_1=prop.getProperty("suite1_startTime");
				return getStartTime_1;
			}
			public void putSuite_2Passvalue(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("suite2_passcount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update suite2 passcount : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update suite2 passcount :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getSuite_2Passvalue()
			{
				String getPassCount_2=prop.getProperty("suite2_passcount");
				return getPassCount_2;
			}
			public void putSuite_2Failvalue(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("suite2_failcount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update suite2 failcount : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update suite2 failcount :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getSuite_2Failvalue()
			{
				String getFailCount_2=prop.getProperty("suite2_failcount");
				return getFailCount_2;
			}
			public void putSuite_2Totalvalue(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("suite2_totalcount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update suite2 totalcount : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update suite2 totalcount  :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getSuite_2Totalvalue()
			{
				String getTotalCount_2=prop.getProperty("suite2_totalcount");
				return getTotalCount_2;
			}
			
			public void putTotal_PassedVal(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("totalPassedCount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update total Passed Count  of all suites : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update  total Passed Count of all suites :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String getTotal_PassedVal()
			{
				String getpassedVal=prop.getProperty("totalPassedCount");
				return getpassedVal;
			}
			public void puttotal_FailedCount(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("totalFailedCount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update failedcount  of all suites : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update failedcount of all suites :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String gettotal_FailedCount()
			{
				String getTotal_FailedCount=prop.getProperty("totalFailedCount");
				return getTotal_FailedCount;
			}
			public void put_totalCount(String Value) 
			{
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("totalCount", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update total Count of all suites: " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update total Count of all suites :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
			}
			public String get_totalCount()
			{
				String gettotalcount=prop.getProperty("totalCount");
				return gettotalcount;
			}

			public void putSuite_3EndTime(String Value) {
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("EndTime", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update End Time : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update End Time :" + Value + " is not stored in property file",false, getDriver(), true);
				}
				
				
			}
			public String get_EndTime()
			{
				String getendtime=prop.getProperty("EndTime");
				return getendtime;
			}
			
			public void putSuite_1EndTime(String Value) {
				try
				{
				FileOutputStream globalFileOut = new FileOutputStream(propertyFilePath);
				prop.setProperty("EndTime", Value);
				prop.store(globalFileOut, null);
				globalFileOut.close();
				logReport(Status.PASS, "Update End Time : " + Value + " is stored successfully in property file",false, getDriver(), true);
				}catch(Exception e)
				{
					logReport(Status.FAIL, "Update End Time :" + Value + " is not stored in property file",false, getDriver(), true);
				}
			}
			
			public String getPIDExcelPath()
			{
				String excelPath=System.getProperty("user.dir") + "\\StorePIDsTueandThu.xlsx";
				return excelPath;
			}


}
