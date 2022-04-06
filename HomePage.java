package pageObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import resources.Base;

public class HomePage extends Base
{
	@FindBy(id="twotabsearchtextbox")							//Locating search text box WebElement
	WebElement searchTextBox;
	
	@FindBy(id="nav-search-submit-button")						//Locating for search button WebElement
	WebElement searchButton;
	
	public HomePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);					//To initialize every WebElement variable
	}
	
	public SearchLandingPage search(String searchText)
	{
		searchTextBox.sendKeys(searchText);
		searchButton.click();									//Method to search by searchText
		
		return new SearchLandingPage(driver);					//Returning the search landing page object after searching for the product
	}
}
