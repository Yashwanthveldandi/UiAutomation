package com.pages;

import org.openqa.selenium.By;

import com.base.Core;

public class CommonMethods extends Core {
	
	private static By downloads = By.xpath("//*[contains(text(),'Downloads')]");
	
	//opening downloads page of selenium.dev
	public static void openSeleniumpage()
	{
		driver.get("https://www.selenium.dev/");
		waitUntilElementLoad(downloads,10);
		click(downloads);
		passReport("downloads page clicked");
	}

}
