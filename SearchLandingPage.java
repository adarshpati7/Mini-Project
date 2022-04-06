package pageObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import resources.Base;

public class SearchLandingPage extends Base {

	@FindBy(css=".a-section.a-spacing-small.a-spacing-top-small>span:nth-child(1)")		//Locating searched string WebElement
	WebElement searchedString;
	
	@FindBy(id="s-result-sort-select")						//Locating 'Sort By' List WebElement
	WebElement sortByList;
	
	@FindBy(id="a-autoid-0")								//Locating 'Sort By' span WebElement
	WebElement sortBySpanElement;
	
	@FindBy(id="s-result-sort-select_1")					//Locating 'Price Low To High' option WebElement
	WebElement priceLowToHighOption;
	
	@FindBy(id="s-result-sort-select_2")					//Locating 'Price High To Low' option WebElement
	WebElement priceHighToLowOption;
	
	@FindBy(id="s-result-sort-select_3")					//Locating 'Avg Customer Review' option WebElement
	WebElement averageCustomerReviewOption;
	
	@FindBy(id="s-result-sort-select_4")					//Locating 'Newest Arrivals' option WebElement
	WebElement newestArrivalsOption;
	
	public SearchLandingPage(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);					//To initialize every WebElement variable
	}
	
	private WebElement sortByList() 							//Method to return 'Sort By' list web element
	{
		return sortByList;										
	}
	
	private WebElement sortByOptions(String sortByText) 		//Method to the return web element of respective option of 'Sort By' list as per the parameter(text of the option) passed
	{
		switch(sortByText)
		{
		case"Newest Arrivals": 
			return newestArrivalsOption; 	
		
		case"Price High To Low": 									
			return priceHighToLowOption;
		
		case"Price Low To High": 
			return priceLowToHighOption;
		
		case"Average Customer Review": 
			return averageCustomerReviewOption;
		}
		return null;
	}

	public WebElement sortBySpanElement() 					//Method to return 'Sort By' span web element
	{
		return sortBySpanElement;							
	}
		
	public String searchedString() 							//Method to return searched string web element
	{
		return searchedString.getText();								
	}
	
	
	public void sortBy(String sortByText) 										//Method to Sort as per user's choice
	{
		a.moveToElement(sortBySpanElement()).click().build().perform();	
		a.moveToElement(sortByOptions(sortByText)).click().build().perform();
	}
	
	public String getSelectedOptionText()										//Method to get the text of 'Sort By' selected option
	{
		Select dropdown= new Select(sortByList());
		return dropdown.getFirstSelectedOption().getText();
	}
	
	
}
