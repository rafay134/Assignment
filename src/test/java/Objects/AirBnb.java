package Objects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import freemarker.log.Logger;

public class AirBnb {

	WebDriver driver;
	Utility utility;
	Logger logger = Logger.getLogger("AirBnb");
	
	//// Zameen Assignment object ////
	
	@FindBy(xpath = "//*[contains(@id, 'bigsearch-query')]")
	public WebElement locationField;
	
	public WebElement getDateToSelect (String dateToSelect) {
		return driver.findElement(By.xpath("//*[contains(@aria-label, 'Choose "+dateToSelect+"')]"));
	}
	
	@FindBy(xpath = "//*[contains(@data-testid, 'structured-search-input-field-split-dates-0')]")
	public WebElement checkInDate;
	
	@FindBy(xpath = "//*[contains(@data-testid, 'structured-search-input-field-split-dates-1')]")
	public WebElement checkOutDate;
	
	@FindBy(xpath = "//*[contains(@data-testid, 'structured-search-input-field-guests-button')]")
	public WebElement guestsButton;
	
	public void addGuests (int count, String guestType) {
		
		WebElement addButton = driver.findElement(By.xpath("//*[contains(@data-testid, 'stepper-"+guestType+"-increase-button')]"));
		int getCurrentCount = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@data-testid, 'stepper-"+guestType+"-value')]")).getText().trim());
		
		utility.Pause(driver, addButton, "Click", 20);
		
		for (int i=0; i<count-getCurrentCount; i++) {
			addButton.click();
			logger.info("'" + guestType + "' added");
		}
	}
	
	@FindBy(xpath = "//*[contains(@data-testid, 'structured-search-input-search-button')]")
	public WebElement searchButton;
	
	@FindBy(xpath = "(//*[contains(text(), 'Check in / Check out')]//following-sibling::div)[1]")
	public WebElement getDatesForFilter;
	
	@FindBy(xpath = "(//*[contains(text(), 'Guests')]//following-sibling::div)[1]")
	public WebElement getGuestsCount;
	
	public List<WebElement> getPropertiesOfSearch (String prop) {
		return driver.findElements(By.xpath("//*[contains(@itemprop, 'itemListElement')]//*[contains(text(), '"+prop+"')]"));
	}
	
	@FindBy(id = "menuItemButton-dynamicMoreFilters")
	public WebElement moreFilters;
	
	@FindBy(xpath = "//*[contains(@aria-label, 'More filters')]")
	public WebElement moreFiltersPopup;
	
	@FindBy(xpath = "(//*[contains(@id, 'beds-stepper')]//*[contains(@aria-label, 'increase value')])[2]")
	public WebElement increaseBedCount;
	
	@FindBy(xpath = "(//*[contains(@id, 'beds-stepper')]//*[contains(@aria-label, 'decrease value')])[2]")
	public WebElement decreaseBedCount;
		
	@FindBy(xpath = "//*[contains(@aria-label, 'Map')]")
	public WebElement map;
	
	public void increaseBedCount (int countToIncrease) {
		
		utility.Pause(driver, increaseBedCount, "Click", 20);
				
		for (int c=0; c<countToIncrease; c++) {
			increaseBedCount.click();
			logger.info("Bed count increased");
		}
		
	}
	
	public void decreaseBedCount (int countToDecrease) {
		
		utility.Pause(driver, decreaseBedCount, "Click", 20);
		
		for (int c=0; c<countToDecrease; c++) {
			decreaseBedCount.click();
			logger.info("Bed count decreased");
		}
		
	}
	
	public void selectFacility (String facility) {
		utility.retryingFindClick(driver, By.xpath("//*[contains(@id, 'filterItem-facilities-checkbox') and @name = '"+facility+"']"));
		logger.info("Facility '" + facility + "' selected");
	}
	
	@FindBy(xpath = "//*[contains(@data-testid, 'more-filters-modal-submit-button')]")
	public WebElement showStaysButton;
	
	@FindBy(xpath = "//*[contains(@itemprop, 'itemListElement')]")
	public List<WebElement> getSearchResults;
	
	@FindBy(xpath = "//*[@data-section-id = 'NAV_DEFAULT']//*[contains(text(), 'Amenities')]")
	public WebElement amenitiesButton;	
	
	public WebElement getAmenity (String amenity) {
		return driver.findElement(By.xpath("//*[@data-section-id = 'AMENITIES_DEFAULT']//*[contains(text(), '"+amenity+"')]"));
	}
	
	@FindBy(xpath = "//*[@id='menuItemButton-dynamicMoreFilters']//*[contains(text(), '2')]")
	public WebElement filterApplied;
	
	@FindBy(xpath = "//*[@id='menuItemButton-dynamicMoreFilters']//*[text()= 'More filters']")
	public WebElement filterUnApplied;
	
	@FindBy(xpath = "//*[contains(@itemprop, 'itemListElement')]//a")
	public List<WebElement> searchList;
	
	public WebElement getPropertyOnMap (String propName) {
		return driver.findElement(By.xpath("//*[contains(@aria-label, '"+propName+"')]//div//div"));
	}
	
	public WebElement getPerNightCostOnMap (String propName) {
		return driver.findElement(By.xpath("//div[contains(text(), '"+propName+"')]//parent::div//*[contains(text(), 'per night')]"));
	}
	
	@FindBy(xpath = "//*[contains(@itemprop, 'itemListElement')]//a//parent::div//*[contains(text(), 'per night')]")
	public List<WebElement> perNightSearchResults;
	
	@FindBy(id = "FMP-target")
	public WebElement imageForProperty;
	
	@FindBy(xpath = "//*[contains(@data-section-id, 'AMENITIES_DEFAULT')]//a")
	public WebElement showAllAmenitiesButton;
	
	public WebElement getAmenityFromPopup (String amenity) {
		return driver.findElement(By.xpath("//*[contains(@aria-label, 'Amenities')]//*[contains(text(), '"+amenity+"')]"));
	}
	
	@FindBy(xpath = "//*[contains(@aria-label, 'Amenities')]")
	public WebElement amenitiesPopup;
	
	public WebElement getLinkToPropOnMap (String propName) {
		return driver.findElement(By.xpath("//div[contains(text(), '"+propName+"')]//parent::div//preceding-sibling::a"));
	}
	
	@FindBy(xpath = "//*[contains(@aria-label, 'Amenities')]//h2[contains(text(), 'Amenities')]")
	public WebElement amenitiesPopupHeading;
	
	public AirBnb(WebDriver driver) {

		this.driver = driver;

		// This initElements method will create all WebElements

		PageFactory.initElements(driver, this);
		utility = new Utility();

	}
}
