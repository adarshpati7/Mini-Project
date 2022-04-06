package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Base {
	protected static Properties prop = new Properties(); // Creating Properties object
	protected static Actions a;												
	protected static WebDriverWait wait;
	protected static ExtentReports extent;
	protected static Logger log;
	protected static String baseUrl;
	protected static String browserName;
	protected static WebDriver driver;

	public static void initialSetup() throws IOException {
		FileInputStream file = new FileInputStream("src\\resources\\data.properties");
		
		prop.load(file);		//Loading the Properties file
		
		baseUrl = prop.getProperty("url"); // Retrieving base url from Properties file
											
		browserName = prop.getProperty("browser"); // Retrieving browser name from Properties file
													

		// Creating driver object based on browser name
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver","C:\\Users\\889991\\workspace\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver","C:\\Users\\889991\\workspace\\geckodriver-v0.29.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.get(baseUrl); // Navigating to the page url
		
		driver.manage().window().maximize(); // Maximizing the browser window
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); // Setting implicit wait for 15 seconds 
		
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);//Setting the amount of time to wait for a page load to complete before throwing an error		
		
		wait=new WebDriverWait(driver,10);	//Setting the explicit wait time for 10 seconds
		
		a=new Actions(driver);							//Creating Actions Class object
	}

	public static ExtentReports extentConfigure() {
		String path = System.getProperty("user.dir") + "\\src\\extentReports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path); // Providing the path where the extent report would be generated
																	  
																		
		reporter.config().setDocumentTitle("Test Results"); // Setting the page title of the extent report HTML file
															
		reporter.config().setReportName("Web Automation Results"); // Setting the extent report HTML document name
																	

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Arpita Neogi");		// Setting the tester name of the extent report HTML file

		return extent;
	}

	public static Logger getLogger(String className) 
	{
		log = LogManager.getLogger(className);			//Getter method for logger object for respective classes
		return log;
	}
	
	public static WebDriverWait getWebDriverWait() 			//Getter method for WebDriverWait class object
	{		
		return wait;
	}
	
	public static Actions getActions() 					//Getter method for Actions class object
	{
		return a;
	}

	public boolean isAlertPresent() 					//If any alert is present then dismisses it
	{ 
	    try 
	    { 
	        driver.switchTo().alert().dismiss(); 
	        return true; 
	    }   
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }  
	}  

	public void takeScreenShot()						//Method to take screenshot
	{
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>(){										//Creating Custom Expected Condition for the page to load
			public Boolean apply(WebDriver driver){
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		wait.until(pageLoadCondition);												//Waiting for the page to load
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);		//Taking a screenshot 
		try
		{
			String fileName = "screenshot"+getBrowserName()+".png";
			FileHandler.copy(src,new File("C:\\Users\\889991\\workspace\\889991_OnlineMobileSearch_Arpita\\screenshot\\"+fileName));   //Copying the screenshot to the destination directory
		} catch (IOException e) 
		{
			e.printStackTrace();					//Printing the error log if any
		}
	}
	public static Properties getProp() {		 //Getter method for properties object
		return prop;
	}

	public static String getBaseUrl() {			//Getter method for base url String
		return baseUrl;
	}

	public static String getBrowserName() {		//Getter method for browser name String
		return browserName;
	}

	public static void closeBrowser() {			//Method to close the driver
		driver.quit();
	}

}
