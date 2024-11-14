package Test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.pages.CommonMethods;

public class Test1 extends CommonMethods{
	
	@BeforeSuite
	public void generateReport()
	{
		createReport();
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		generateReports("first Test");
		openDriver();
		
	}
	
	@Test
	public void firstTest()

	{
		openSeleniumpage();
	}
	
	@AfterMethod
	public void closeDriver(ITestResult result)
	{
		endOfTest(result, driver);
		if(driver!=null)
		{
			driver.quit();
		}
	}
	
}
