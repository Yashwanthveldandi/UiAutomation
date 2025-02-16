package com.base;

import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

@SuppressWarnings("deprecation")
public class ExtentReportGen extends Utility{
	//public class ExtentReportGen extends CommonElement{
	
	 static ExtentReports extent;
	 static String reportPath = null;
	 public static ExtentTest test = null;
	 static ExtentHtmlReporter htmlReporter = null;
	 private static  ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	 
	public static ExtentReports extentReportGeneration()
	{
		reportPath = String.valueOf(System.getProperty("user.dir")) + "/reports/ExecutionReport_" +getCurrentDateTime()
	      .replace("/", "_").replace(":", "_").replace("[", "").replace("] ", "").replace(" ", "_");
	    (new File(reportPath)).mkdir();
	    (new File(String.valueOf(reportPath) + "/screenshots")).mkdir();
	    (new File(String.valueOf(reportPath) + "/files")).mkdir();
	    (new File(String.valueOf(reportPath) + "/files/xmls")).mkdir();
	    //String reportFilepath=System.getProperty("user.dir")+"extent.html";
	    ExtentSparkReporter ExtentSpark=new ExtentSparkReporter(String.valueOf(reportPath) + "/extent.html");
	    extent= new ExtentReports();
	    ExtentSpark.config().setTheme(Theme.DARK);
	    ExtentSpark.config().setDocumentTitle("EAD Automation Report");
	    ExtentSpark.config().setEncoding("utf-8");
	    ExtentSpark.config().setReportName("Automation Report");
		extent.attachReporter(ExtentSpark);
		return extent;
	}
	
	public static synchronized ExtentTest createTest(String testName)
	{
		test = extent.createTest(testName);
		extentTest.set(test);
		return test;
	}
	
//	public static void htmlReader() 
//	 {
//		 	String[] failedCount = null;
//		    String[] passedCount = null;
//		    String endtDateTime = null;
//		    String startDateTime = null;
//		    try {
//		      File filePath = new File(String.valueOf(ExtentReportGen.reportPath) + "\\extent.html");
//		      Document document = Jsoup.parse(filePath, "UTF-8");
//		      Elements divs = document.select("div");
//		      for (Element div : divs) 
//		      {
//		        String fail = div.ownText();
//		        if (fail.contains("test(s) failed")) 
//		        {
//		          String failed = div.text();
//		          failedCount = failed.split(" ");
//		          System.out.println("Failed Count:" + failedCount[0]);
//		          break;
//		        } 
//		      } 
//		      Elements spans = document.select("span");
//		      for (Element span : spans) 
//		      {
//		        String pass = span.ownText();
//		        if (pass.contains("test(s) passed")) 
//		        {
//		          String passed = span.text();
//		          passedCount = passed.split(" ");
//		          System.out.println("Passed Count:" + passedCount[0]);
//		          break;
//		        } 
//		      } 
//		      Elements startdivs = document.select("div");
//		      for (Element div : startdivs) 
//		      {
//		        String fail = div.ownText();
//		        if (fail.contains("Start")) 
//		        {
//		          startDateTime = div.text();
//		          System.out.println(startDateTime);
//		          break;
//		        } 
//		      } 
//		      Elements enddivs = document.select("div");
//		      for (Element div : enddivs) 
//		      {
//		        String fail = div.ownText();
//		        if (fail.contains("End")) 
//		        {
//		          endtDateTime = div.text();
//		          System.out.println(endtDateTime);
//		          break;
//		        } 
//		      }
//		      //String reportPath = "./reports";
//		//String passedCountS=Integer.toString(passedCount);
//		//String failedCountS=Integer.toString(failedCount);
//		      PrintWriter writer = new PrintWriter(System.getProperty("user.dir")+"\\reports\\TestResults.txt" , "UTF-8");
//		      //PrintWriter writer = new PrintWriter(String.valueOf(reportPath) + "/TestResults.txt", "UTF-8");
//		      writer.println("PassedCount " + passedCount[0]);
//		      writer.println("FailedCount " + failedCount[0]);
//		      writer.println("Start " + startDateTime);
//		      writer.println("End " + endtDateTime);
//		      writer.close();
//		    } 
//		 catch (IOException e) 
//		 {
//	          e.printStackTrace();
//	     }
//	}
}