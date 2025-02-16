package com.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public  class Listeners  implements ITestListener  {

	public static ExtentReportGen extentReportGen = new ExtentReportGen();
	
	    int skipCount=0;
	    static int failedCount = 0;
	    static int passedCount = 0;
	    static String endtDateTime = null;
	    static String startDateTime = null;

static ExtentReports extent=ExtentReportGen.extentReportGeneration(); 

protected static ThreadLocal<ExtentTest> extenttest=new ThreadLocal<ExtentTest>();
ExtentTest Test;

	@Override
	public void onTestStart(ITestResult results) 
	{
		Test=extent.createTest(results.getMethod().getDescription());
		extenttest.set(Test);
		extenttest.get().log(Status.INFO,"Test case Started ");
		
//		String testName = results.getMethod().getMethodName();
//		Test=extent.createTest(testName);
//		//Test=extent.createTest(results.getMethod().getMethodName());
//		extenttest.set(Test);
//		extenttest.get().log(Status.INFO,"Test case Started : " + testName);
	}
	
	@BeforeSuite
    public void getStarttime()
    {
    	startDateTime=getCurrentDateTimeforStartEnd();
    }

	@Override
	public void onTestFailure(ITestResult results)
	{
		extenttest.get().fail(results.getThrowable());
		if(results.getStatus()==ITestResult.FAILURE) 
		{
			failedCount=failedCount+1;
			System.out.println("Passed Count : "+passedCount);
			System.out.println("Failed Count : "+failedCount);
			System.out.println("Skip Count : "+skipCount);
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult results)
	{
		if(results.getStatus()==ITestResult.SKIP) 
		{
			skipCount=skipCount+1;
			System.out.println("Passed Count : "+passedCount);
			System.out.println("Failed Count : "+failedCount);
			System.out.println("Skip Count : "+skipCount);
		}
	}
	
	@Override
	public void onTestSuccess(ITestResult results)
	{
		extenttest.get().log(Status.PASS, "Test case Completed SuccessfullY");
		if(results.getStatus()==ITestResult.SUCCESS) 
		{
			passedCount=passedCount+1;
			System.out.println("Passed Count : "+passedCount);
			System.out.println("Failed Count : "+failedCount);
			System.out.println("Skip Count : "+skipCount);
		}
	}
	
	@Override
	public void onFinish(ITestContext results)
	{
	extent.flush();
	endtDateTime=getCurrentDateTimeforStartEnd();
	try 
	{
		Thread.sleep(5000);
		htmlReader();
	    Thread.sleep(5000);
	}
	catch (InterruptedException e) 
	{
		e.printStackTrace();
	}
	try 
	{
		//EWData.DbUpdate();
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
		
	}


	@Override
	public void onStart(ITestContext arg0)
	{
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0)
	{
		
	}
	
	public static String getCurrentDateTime()
	{
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	    LocalDateTime now = LocalDateTime.now();
	    return "[" + dtf.format(now) + "] ";
	}
	
	public static String getCurrentDateTimeforStartEnd()
	{
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss a");
		 LocalDateTime now = LocalDateTime.now();
		 return dtf.format(now) ;
	}
	
	 public static void logReport(Status status, String message, boolean takeScreenshot, WebDriver driver, boolean displayConsole) {
		    if (takeScreenshot) {
		      try {
		    	  extenttest.get().log(status, message, 
		            MediaEntityBuilder.createScreenCaptureFromPath(Utility.takeScreenshot(driver)).build());
		      } catch (IOException e) {
		    	  extenttest.get().log(status, "Unable to capture screenshot. Logging Provided information: " + message);
		      } 
	    } else {
		    	extenttest.get().log(status, message);
		    } 
		    if (displayConsole) {
		      message = message.replace(
		          "<span onclick=\"$(this).next().toggle();\">Show/Hide Stacktrace</span><p style=\"display: none;\">", 
		          "");
		      System.out.println(String.valueOf(getCurrentDateTime()) + message);
		    } 
	 }
	 
	 public static void htmlReader()
	 {
		String passedCountS=Integer.toString(passedCount);
		String failedCountS=Integer.toString(failedCount);
		 try {
		      PrintWriter writer = new PrintWriter(System.getProperty("user.dir")+"\\reports\\TestResults.txt" , "UTF-8");
		      writer.println("PassedCount " + passedCountS);
		      writer.println("FailedCount " + failedCountS);
		      writer.println("Start " + startDateTime);
		      writer.println("End " + endtDateTime);
		      writer.close();
		    } 
		 catch (IOException e) 
		 {
	          e.printStackTrace();
	     }
	 }
	 

}