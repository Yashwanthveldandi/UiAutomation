package com.base;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import java.util.logging.Level;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.json.JSONArray;
import org.openqa.selenium.logging.LogEntry;
import org.json.JSONObject;
import org.openqa.selenium.edge.EdgeOptions;
import java.io.FileWriter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel;

public class Utility extends Listeners {

	public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final String NUMERIC_STRING = "0123456789";
	public static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXY";
	public static Properties prop;
	private static String propertyFilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\config.properties";
	public static Workbook workBook = null;
	public static String generatedXlsFilePath = "";
	public static FileOutputStream fileOutputStream = null;
	private static Sheet sheet = null;
	public static final String FILE_EXTN_XLS = ".xls";
	public static final String FILE_EXTN_CSV = ".csv";
	public static HashMap<String, String> map1 = null;
	public static FileInputStream inputStream = null;
	public static JSONArray logArray;

	public static ExtentReportGen EX = new ExtentReportGen();
	static ThreadLocal<WebDriver> dr = new ThreadLocal<>();

	public static int getElementsCount(By element) {
		return getDriver().findElements(element).size();
	}

	public static WebDriver getDriver() {
		return dr.get();
	}

	public static void unload() {
		dr.remove();
	}

	public static ExtentTest test = null;

	public static void generateReports(String testCase) {
		test = extent.createTest(testCase.toString());
	}

	public static void openDriver() {
		String str;
		str = Common.readProperty("browser");

		if (str.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();

				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
				options.setCapability("goog:loggingPrefs", logPrefs);

				options.addArguments(new String[] { "--ignore-certificate-errors" });
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

				if ("true".equalsIgnoreCase(Common.readProperty("headless"))) {
					options.addArguments("--headless");
				}
				WebDriver driver = (WebDriver) new ChromeDriver(options);
				dr.set(driver);
				extent.setSystemInfo("Browser", "Chrome");
				extent.setSystemInfo("Author", "Yashwanth V");
				// Initialize a JSONArray to store log entries
				logArray = new JSONArray();
			
			}

		else if (str.equalsIgnoreCase("edge")) {
				extent.setSystemInfo("Browser", "Edge");
				extent.setSystemInfo("Author", "Yashwanth V");
				System.setProperty("webdriver.edge.driver", "./drivers/msedgedriver.exe");
				EdgeOptions options = new EdgeOptions();
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.PERFORMANCE, Level.ALL); // Enable all levels of performance logs
				options.setCapability(EdgeOptions.LOGGING_PREFS, logPrefs);
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

				if ("true".equalsIgnoreCase(Common.readProperty("headless"))) {
					options.addArguments("--headless=new");
				}
				// Initialize WebDriver with EdgeOptions

				WebDriver driver = (WebDriver) new EdgeDriver(options);
				dr.set(driver);
				// Initialize the log storage
				logArray = new JSONArray();
				
			}
		else
			
		{
			Assert.fail("No Browser is defined");	
		}
		
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
	}

	// Method to capture logs, create timestamped file name, and write to JSON file
	public static void captureLogs() {
		List<LogEntry> entries = getDriver().manage().logs().get(LogType.PERFORMANCE).getAll();
		System.out.println(entries.size() + " " + LogType.PERFORMANCE + " log entries found");

		// Add each log entry to the logArray
		for (LogEntry entry : entries) {
			String logMessage = entry.getMessage();
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // Get the current
																								// timestamp

			// Create a JSON object for each log entry
			JSONObject logObject = new JSONObject();
			logObject.put("timestamp", timestamp);
			logObject.put("message", logMessage);

			// Add the log entry to the JSONArray
			logArray.put(logObject);
		}

		// Create a timestamped file name (e.g., 2024-12-09_15-45-12_browser_logs.json)
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()); // Current timestamp
		String fileName = timestamp + "_browser_logs_with_timestamp.json"; // Use timestamp in file name

		String logsFolder = "./logs";
		// Ensure the logs folder exists, create it if it doesn't
		File folder = new File(logsFolder);
		if (!folder.exists()) {
			folder.mkdir(); // Create the logs folder if it doesn't exist
		}
		// Define the complete path to the file inside the logs folder
		File logFile = new File(logsFolder, fileName);

		// Write the log entries to a JSON file
		try (FileWriter file = new FileWriter(logFile)) {
			file.write(logArray.toString(4)); // Pretty print with an indentation level of 4
			System.out.println("Logs written to file: " + fileName); // Confirmation message
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void resetProgress() {
		Common.writeProperty("./src/main/resources/progress.properties", "iterationComplete", "0");
		Common.writeProperty("./src/main/resources/progress.properties", "iterationAll", "0");
		Common.writeProperty("./src/main/resources/progress.properties", "overallComplete", "0");
		Common.writeProperty("./src/main/resources/progress.properties", "overallAll", "0");
		Common.writeProperty("./src/main/resources/progress.properties", "pass", "0");
		Common.writeProperty("./src/main/resources/progress.properties", "fail", "0");
		Common.writeProperty("./src/main/resources/progress.properties", "skip", "0");
		Common.writeProperty("./src/main/resources/progress.properties", "reportOpen", "false");
	}

	public static void navigateURL(String url) {
		getDriver().get(url);
		logReport(Status.PASS, "Navigated to the URL [" + url + "]", true, getDriver(), true);
	}

	@BeforeSuite
	public static void beforeSuite() {
		resetProgress();
	}

	static String takeScreenshot(WebDriver driver) {
		String screenShotName = String.valueOf(getCurrentDateTime().replace("/", "_").replace(":", "_").replace("[", "")
				.replace("] ", "").replace(" ", "_")) + UUID.randomUUID().toString();
		String path = String.valueOf(EX.reportPath) + "/screenshots/" + screenShotName + ".jpeg";
		String retPath = "./screenshots/" + screenShotName + ".jpeg";
		try {
			if (Common.readProperty("browser").equals("phantomjs"))
				((JavascriptExecutor) driver).executeScript(
						"(function() {var style = document.createElement('style'), text = document.createTextNode('body { background: #fff }'); style.setAttribute('type', 'text/css'); style.appendChild(text); document.head.insertBefore(style, document.head.firstChild);})();",
						new Object[0]);
			File f = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileHandler.copy(f, new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Can not take screenshot: " + e);
		}
		return retPath;
	}

	protected static void addOneForProgress(String property) {
		Common.writeProperty("./src/main/resources/progress.properties", property, Integer.toString(
				1 + Integer.parseInt(Common.readProperty("./src/main/resources/progress.properties", property))));
	}

	public static void endOfTest(ITestResult result, WebDriver driver) {
		if (result.getStatus() == 2) {
			addOneForProgress("fail");
			System.out.println(String.valueOf(getCurrentDateTime()) + "Test Failed: " + result.getThrowable());
			try {
				extenttest.get().fail("Test Failed: " + result.getThrowable(),
						MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot(driver)).build());
			} catch (IOException e) {
				extenttest.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
				e.printStackTrace();
			}
		} else if (result.getStatus() == 3) {
			addOneForProgress("skip");
			System.out.println(String.valueOf(getCurrentDateTime()) + "Test Skipped: " + result.getThrowable());
			extenttest.get().log(Status.SKIP, "Test Case skipped");
		} else {
			addOneForProgress("pass");
			System.out.println(String.valueOf(getCurrentDateTime()) + "Test Completed");
			extenttest.get().log(Status.PASS, "Test Case Completed");
		}
		addOneForProgress("iterationComplete");
	}

	// Existing Methods
	public static void jsClick(By element) {
		WebElement e = getDriver().findElement(element);
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", new Object[] { e });
	}

	public static void jsClick(String element) {
		WebElement e = getDriver().findElement(By.xpath(element));
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", new Object[] { e });
	}

	public static void sleep(int seconds) throws InterruptedException {
		Thread.sleep(seconds);
	}

	public static void passReport(String message) {
		logReport(Status.PASS, message, true, getDriver(), true);
	}

	public static void failReport(String message) {
		logReport(Status.FAIL, message, true, getDriver(), true);
	}

	public static void scrollTillElement(String element) throws InterruptedException {
		try {
			WebElement fr = getDriver().findElement(By.xpath(element));
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView();", new Object[] { fr });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scrollTillElement(WebElement element) throws InterruptedException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView();", new Object[] { element });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scrollTillElement(By element) throws InterruptedException {
		try {
			WebElement fr = getDriver().findElement(element);
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView();", new Object[] { fr });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void SelectByVisibletext(By xpath, String text) throws InterruptedException {
		waitforLoadingIconToDissappear();
		scrollTillElement(xpath);
		Select sel = new Select(getDriver().findElement(xpath));
		sel.selectByVisibleText(text);
		Reporter.log(String.valueOf(text) + " selected");
	}

	public static void SelectByValue(By xpath, String value) throws InterruptedException {
		waitforLoadingIconToDissappear();
		scrollTillElement(xpath);
		Select sel = new Select(getDriver().findElement(xpath));
		sel.selectByValue(value);
		Reporter.log(String.valueOf(value) + " selected");
	}

	public static void doubleClick(By xpath) {
		Actions actions = new Actions(getDriver());
		WebElement element = getDriver().findElement(xpath);
		actions.doubleClick(element).build().perform();
		Reporter.log("Double click success");
	}

	public static void rightClick(By xpath) {
		Actions actions = new Actions(getDriver());
		WebElement element = getDriver().findElement(xpath);
		actions.contextClick(element).build().perform();
		Reporter.log("Double click success");
	}

	public static void clear(By xpath) throws InterruptedException {
		getDriver().findElement(xpath).clear();
		sleep(1000);
	}

	public static void clear(String Xpath) throws InterruptedException {
		getDriver().findElement(By.xpath(Xpath)).clear();
		sleep(1000);
	}

	public static String getText(By xpath) {
		String value = getDriver().findElement(xpath).getText();
		return value;
	}

	public static String getText(String Xpath) {
		String value = getDriver().findElement(By.xpath(Xpath)).getText();
		return value;
	}

	public static String getAttribute(By xpath) {
		String value = getDriver().findElement(xpath).getAttribute("value");
		return value;
	}

	public static void isDisplayed(By xPath, String value) {
		if (getDriver().findElement(xPath).isDisplayed()) {
			passReport(String.valueOf(value) + " is displayed");
		} else {
			failReport(xPath + " is not displayed");
		}
	}

	public static void isEnabled(By xPath, String value) {
		if (getDriver().findElement(xPath).isEnabled()) {
			passReport(String.valueOf(value) + " is Enabled");
		} else {
			failReport(xPath + " is not Enabled");
		}
	}

	public static void isSelected(By xPath, String value) {
		if (getDriver().findElement(xPath).isSelected()) {
			passReport(String.valueOf(value) + " is Selected ");
		} else {
			failReport(xPath + " is not Selected");
		}
	}

	public static boolean isEnabled(By element) {
		return getDriver().findElement(element).isEnabled();
	}

	public static boolean isDisplayed(By element) {
		return getDriver().findElement(element).isDisplayed();
	}

	public static boolean isSelected(By element) {
		return getDriver().findElement(element).isSelected();
	}

	public static String getProperty(String path, String Property) throws IOException {
		FileReader reader = new FileReader(path);
		Properties p = new Properties();
		p.load(reader);
		String value = p.getProperty(Property);
		return value;
	}

	public static void waitforLoadingIconToDissappear() throws InterruptedException {
		List<WebElement> loadElement = getDriver().findElements(By.xpath("//*[@role='progressbar']"));
		int count = 0;
		while (loadElement.size() != 0 && count < 20) {
			Thread.sleep(1000L);
			count++;
		}
		System.out.println("waiting for load element to dissapper");
	}

	public static void sendKeys(By xpath, String value) {
		getDriver().findElement(xpath).sendKeys(new CharSequence[] { value });
		Reporter.log(String.valueOf(value) + " has entered successfully");
	}

	public static void sendKeys(String Xpath, String value) {
		getDriver().findElement(By.xpath(Xpath)).sendKeys(new CharSequence[] { value });
		Reporter.log(String.valueOf(value) + " has entered successfully");
	}

	public static void waitUntilElementLoad(WebElement element, int waitSeconds) {
		Calendar waitTime = Calendar.getInstance();
		waitTime.add(13, waitSeconds);
		while (true) {
			Calendar nowTime = Calendar.getInstance();
			if (!waitTime.after(nowTime))
				break;
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				if (element.isDisplayed())
					break;
			} catch (Exception exception) {
			}
		}
	}

	public static void click(By element) {
		try {
			getDriver().findElement(element).click();
		} catch (ElementNotInteractableException e) {
			scrollToElement(element);
			getDriver().findElement(element).click();
			logReport(Status.PASS, element + " : failed to click element", true, getDriver(), true);
		}
	}

	public static void click(String element) {
		try {
			getDriver().findElement(By.xpath(element)).click();
		} catch (ElementNotInteractableException e) {
			scrollToElement(element);
			getDriver().findElement(By.xpath(element)).click();
			logReport(Status.PASS, String.valueOf(element) + " : failed to click element", true, getDriver(), true);
		}
	}

	public static void scrollToElement(By element) {
		try {
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
					new Object[] { getDriver().findElement(element) });
		} catch (Exception e) {
			logReport(Status.PASS, element + " : failed to Sroll till element", true, getDriver(), true);
		}
	}

	public static void scrollToElement(String element) {
		try {
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
					new Object[] { getDriver().findElement(By.xpath(element)) });
		} catch (Exception e) {
			logReport(Status.PASS, String.valueOf(element) + " : failed to Sroll till element", true, getDriver(),
					true);
		}
	}

	public static void log(String log) {
		Reporter.log(log);
	}

	public static void waitUntilElementLoad(By element, int waitSeconds) {
		Calendar waitTime = Calendar.getInstance();
		waitTime.add(13, waitSeconds);
		while (true) {
			Calendar nowTime = Calendar.getInstance();
			if (!waitTime.after(nowTime))
				break;
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				if (getDriver().findElement(element).isDisplayed())
					break;
			} catch (Exception exception) {
			}
		}
	}
	
	public static void waitingforElementLoad(By xpath,int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
		wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
	}

	public static void CloseBrowser() {
		Assert.fail();
		getDriver().quit();
	}

	public static void hardPause(int seconds) {
		try {
			Thread.sleep((seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean isFileLocked(File file) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             FileChannel channel = raf.getChannel()) {

           
            FileLock lock = channel.tryLock();
            if (lock == null) {
               
                return true;
            }

            lock.release();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public static void waitUntilFileIsUnlocked(File file) {
        while (isFileLocked(file)) {
            try {
               
                System.out.println("File is locked, waiting...");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("File is now unlocked.");
    }


	public synchronized static void updateSerivceID(String serviceID, int rownumber)  {
		try {
			
		File file = new File("./resources/serviceIDdetails.xlsx");
		waitUntilFileIsUnlocked(file);
		
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);

		Sheet sheet = workbook.getSheetAt(0);

		Row row = sheet.getRow(rownumber);
		if (row == null) {
			row = sheet.createRow(rownumber);
		}

		
		Cell cell = row.getCell(0);

		
		if (cell == null) {
			cell = row.createCell(0); 
		}

		
		cell.setCellValue(serviceID);

		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);

		fos.close();
		fis.close();

		
		workbook.close();

		System.out.println(serviceID + " updated successfully.");
		}catch (Exception e) {
			
		}
	}

	public synchronized static String getServiceID(int rowNumber) {
		String ServiceID = "";
		try {
		
		File file = new File("./resources/serviceIDdetails.xlsx");

		
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);

		Row row = sheet.getRow(rowNumber);

		Cell cell = row.getCell(0); 

		if (cell != null) {
			ServiceID = cell.getStringCellValue(); 
		} else {
			ServiceID = "Cell is empty or does not exist.";
		}

		
		fis.close();
		workbook.close();
		
		}catch (Exception e) {
			
		}
		return ServiceID;
		
	}
	
	
	public synchronized static void updateEstimate(String estimate, int rownumber)  {
		try {
		
		File file = new File("./resources/estimatedetais.xlsx");

		
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);

		Sheet sheet = workbook.getSheetAt(0);

		Row row = sheet.getRow(rownumber);

		if (row == null) {
			row = sheet.createRow(rownumber); 
		}

		Cell cell = row.getCell(0);

		if (cell == null) {
			cell = row.createCell(0); 
		}
		
		cell.setCellValue(estimate);
		
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		fis.close();
		workbook.close();

		System.out.println(estimate + " updated successfully.");
		}catch (Exception e) {
			
		}
	}
	
	public synchronized static String getEstimate(int rowNumber) {
		String Estimate = "";
		try {
		
		File file = new File("./resources/estimatedetais.xlsx");

		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);
		
		Sheet sheet = workbook.getSheetAt(0);
		
		Row row = sheet.getRow(rowNumber);

		Cell cell = row.getCell(0); 

		if (cell != null) {
			Estimate = cell.getStringCellValue(); 
		} else {
			Estimate = "Cell is empty or does not exist.";
		}

		
		fis.close();
		workbook.close();
		
		}catch (Exception e) {
			
		}
		return Estimate;
		
	}
	
	

	public synchronized static void updatePID(String PID, int serviceIDindex, int PIDindex) {
		try {

		
		File file = new File("./resources/serviceIDdetails.xlsx");

		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis); 
		Sheet sheet = workbook.getSheetAt(0);

		Row row = sheet.getRow(serviceIDindex); 
		if (row == null) {
			row = sheet.createRow(1); 
		}

		Cell cell = row.getCell(PIDindex);
		if (cell == null) {
			cell = row.createCell(PIDindex);
		}
		
		cell.setCellValue(PID);
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		fis.close();
		workbook.close();

		System.out.println(PID + " updated successfully.");
		}catch (Exception e) {
			
		}
	}

	public synchronized static String getPID(int serviceIDindex, int PIDindex) {
		String PID = "";

		try {
		
		File file = new File("./resources/serviceIDdetails.xlsx");

		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis); 
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(serviceIDindex); 
		Cell cell = row.getCell(PIDindex); 
		if (cell != null) {
			PID = cell.getStringCellValue();
		} else {
			PID = "Cell is empty or does not exist.";
		}

		fis.close();
		workbook.close();

		}catch (Exception e) {
			
		}
		return PID;
	}
	
	
}