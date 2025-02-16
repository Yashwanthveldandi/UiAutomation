package Test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.Utility;

public class Test1 extends Utility{
		
	@BeforeMethod
	public void beforeMethod()
	{
		openDriver();
		
	}
	
	@Test(description="Test1")
	public void firstTest()

	{
		getDriver().get("https://www.google.com/");
	}
	
	@AfterMethod
	public void closeDriver(ITestResult result)
	{
		endOfTest(result, getDriver());//need to comment it while running only single test
		if(getDriver()!=null)
		{
			getDriver().quit();
			unload();
		}
	}
	
}
