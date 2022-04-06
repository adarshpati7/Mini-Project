package testCases;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;

import junit.framework.Assert;
import pageObjectRepository.HomePage;
import pageObjectRepository.SearchLandingPage;
import resources.Base;

public class SearchMobilePhonesTest extends Base {
	private HomePage home;
	private SearchLandingPage searchLandingPage;

	public void searchNewlyArrivedPhones() 
	{
		try 
		{
			extent.createTest("searchNewlyArrivedPhones");				//Creating the test name in extent report					
			Assert.assertEquals(getBaseUrl(), driver.getCurrentUrl());	//Validating whether the user is navigated to the correct url
			log.info("User is navigated to home page of website.");

			home = new HomePage(driver);								//Creating a home page object to retrieve all the objects of home page
			isAlertPresent();
			searchLandingPage = home.search("mobile smartphones under 30000");				//Typing in the search text box of home page
			log.info("Searching for mobile smartphones under 30000");

			String searchedString = searchLandingPage.searchedString();		//Retrieving the searched string from the landing page
			
			String noOfPages = searchedString.split(" ")[0];					//Extracting the 'number of pages' (eg,"1-16") from the searched string
			String noOfItems = searchedString.split(" ")[2] + " " + searchedString.split(" ")[3];		//Extracting the 'number of items' (eg,90,000) from the searched string
			
			Assert.assertEquals(true, noOfPages.matches("^[1][-][0-9]+$"));		//Validating the 'number of pages'
			Assert.assertEquals(true, noOfItems.matches("^over\\s[0-9]+[,][0-9]+$"));		//Validating the 'number of items'
			log.info("Search string validated.");

			wait.until(ExpectedConditions.elementToBeClickable(searchLandingPage.sortBySpanElement()));			  //Waiting for the Sort By Span Element to be clickable
			searchLandingPage.sortBy("Newest Arrivals");
			
			Assert.assertEquals("Newest Arrivals", searchLandingPage.getSelectedOptionText());		//Validating whether "Newest Arrivals" is selected in the 'Sort By' list or not
			takeScreenShot();
			
			log.info("Newly Arrived option got selected correctly.");

			extent.flush();					// Erasing any previous data in the extent report and creating a new report
		} catch (Exception e) 
		{
			e.printStackTrace();			//Printing the error log if any
		}
	}

	public static void main(String[] args) throws IOException 
	{
		log=getLogger(SearchMobilePhonesTest.class.getName());			//Creating logger object based on class name
		
		initialSetup();
		log.info("Driver is created.");
		
		extent = extentConfigure();										//Configuration for extent report creation
					
		new SearchMobilePhonesTest().searchNewlyArrivedPhones();		//Running the test case
		
		closeBrowser();													//Closing the browser
		log.info("Browser is closed.");
	}
}
