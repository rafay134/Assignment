package Assign;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import Objects.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.allure.annotations.Title;

@Listeners({ TestListener.class })
public class Zameen_Assignment {

	// Objects
	Logger logger = Logger.getLogger("Zameen_Assignment");
	TestListener tlistener=new TestListener();
	HashMap<String, Integer> guests = new HashMap<String, Integer>();
	Utility utility;
	AirBnb aobj;
	WebDriver driver = null;
	ArrayList<String> credentials = new ArrayList<String> ();

	// Variables
	String URL = "", startDate = "", endDate = "", windowHandle = "";

	@Test(priority = 1)
	public void preRec() throws ParserConfigurationException, SAXException, IOException {

		WebDriverManager.chromedriver().setup();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		//		chromePrefs.put("download.default_directory", downloadPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);	
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(ChromeOptions.CAPABILITY, options);
		options.setExperimentalOption("useAutomationExtension", false);
		//		options.addArguments("--headless");
		driver= new ChromeDriver(options);
		PropertyConfigurator.configure("Log4j.properties");

		utility = new Utility();
		aobj = new AirBnb (driver);

		startDate = utility.AddDate(7);
		endDate = utility.AddDate(14);

		guests.put("children", 1);
		guests.put("adults", 2);

		try {
			credentials = utility.getUsernamePassword();
			URL = credentials.get(0);
		}
		catch (Exception exc23) {}

		driver.get(URL);
		driver.manage().window().maximize();
		logger.info("URL opened - " + URL);

	}

	// Test Scenarios
	@Test (priority=2, description = "Assignment")
	public void VerifyResultsMatchCriteria() throws InterruptedException, IOException, ParseException {

		logger.info(" -- Verifying Test scenario for search results -- ");

		windowHandle = driver.getWindowHandle();

		// Select location
		String location = "Rome, Italy";
		aobj.locationField.sendKeys(location);
		aobj.locationField.sendKeys(Keys.ENTER);
		logger.info("Location entered - " + location);

		String newStartDate = utility.changeDateFormat(startDate, "EEEE, MMMM d, yyyy");
		String newEndDate = utility.changeDateFormat(endDate, "EEEE, MMMM d, yyyy");

		logger.info(newStartDate);
		logger.info(newEndDate);

		String stDate2 = utility.changeDateFormat(startDate, "MMM d");
		String endDate2 = utility.changeDateFormat(endDate, "MMM d");
		String finalDateFilter = "";

		if (stDate2.substring(0, 3).equals(endDate2.substring(0, 3))) {
			finalDateFilter = stDate2.trim() + " – " + endDate2.split(" ")[1].trim();
		}
		else {
			finalDateFilter = stDate2.trim() + " – " + endDate2;
		}

		finalDateFilter = finalDateFilter.trim();
		logger.info(finalDateFilter);

		// Select Date		
		try {
			utility.Pause(driver, aobj.getDateToSelect(newStartDate), "Click", 5);
		}
		catch (Exception exc1) {}

		aobj.getDateToSelect(newStartDate).click();
		logger.info("Start Date selected - " + newStartDate);

		aobj.getDateToSelect(newEndDate).click();
		logger.info("End Date selected - " + newEndDate);

		// Add Guests
		aobj.guestsButton.click();
		logger.info("Guests button clicked");

		Iterator<String> iterator = guests.keySet().iterator();
		int countOfGuests = 0;

		for (int i=0; i<guests.size(); i++) {
			String val = iterator.next();
			aobj.addGuests(guests.get(val), val);
			logger.info(" -- "+guests.get(val)+" "+val+" added -- ");
			countOfGuests = countOfGuests + guests.get(val);
		}

		// Search 
		aobj.searchButton.click();
		logger.info("Search button clicked");

		// Verify Applied filters
		try {
			utility.Pause(driver, aobj.getDatesForFilter, "Click", 10);
		}
		catch (Exception ecx) {}

		String getDateFilter = aobj.getDatesForFilter.getText().trim();
		String getGuestFilter = aobj.getGuestsCount.getText().trim();

		logger.info(getDateFilter);
		logger.info(getGuestFilter);

		getGuestFilter = getGuestFilter.split(" ")[0].trim();

		Assert.assertTrue(getGuestFilter.equals(Integer.toString(countOfGuests)), "Guest count not matched");
		Assert.assertTrue(finalDateFilter.compareTo(getDateFilter) == 0, "Date filter not matched");

		// Verify prop for guests
		utility.checkPageIsReady(driver);

		try {
			utility.Pause(driver, aobj.map, "Click", 10);
		}
		catch (Exception e344) {}

		try {
			utility.Pause(driver, aobj.getPropertiesOfSearch("guests").get(0), "Click", 20);
		}
		catch (Exception e344) {}

		String getGuestProp = aobj.getPropertiesOfSearch("guests").get(0).getText();
		logger.info(getGuestProp);
		int getGuestPropCount = Integer.parseInt(getGuestProp.split("guests")[0].trim());

		Assert.assertTrue(getGuestPropCount>=countOfGuests, "Properties not verified");

		logger.info(" -- Test Scenario for search results verified -- ");
	}

	// Test Scenarios
	@Test (priority=3, description = "Assignment")
	public void VerifyResultsAndDetailsMatchExtraCriteria() throws InterruptedException, IOException {

		logger.info(" -- Verifying Extra Filters Scenario -- ");

		// Add filters
		aobj.moreFilters.click();
		logger.info("More filters clicked");

		int bedCount = 5;
		aobj.increaseBedCount(bedCount);
		logger.info("Bed count increased to - " + bedCount);

		String facility = "Pool";
		aobj.selectFacility(facility);

		// Check stays
		aobj.showStaysButton.click();
		logger.info("Stays button clicked");

		try {
			utility.Pause(driver, aobj.map, "Click", 10);
		}
		catch (Exception e344) {}

		utility.Pause(driver, aobj.filterApplied, "Click", 10);
		
		try {
			utility.Pause(driver, aobj.getPropertiesOfSearch("bedroom").get(0), "Click", 10);
		}
		catch (Exception exc2) {}

		String getBedroomProp = aobj.getPropertiesOfSearch("bedroom").get(0).getText();
		logger.info(getBedroomProp);
		int getBedroomPropCount = Integer.parseInt(getBedroomProp.split("bedroom")[0].trim());

		Assert.assertTrue(getBedroomPropCount>=bedCount, "Properties not verified");

		// Select a property
		aobj.getSearchResults.get(0).click();
		logger.info("First property selected");

		utility.checkPageIsReady(driver);		

		utility.switchtowindowhandler(driver);

		utility.Pause(driver, aobj.imageForProperty, "Visibility", 10);

		// Wait for the image to load
		for (int i=0; i<10; i++) {
			logger.info(aobj.imageForProperty.getAttribute("src"));
			if (aobj.imageForProperty.getAttribute("src").contains("http")) {
				logger.info("Image loaded");
				break;
			}
		}

		try {
			utility.Pause(driver, aobj.showAllAmenitiesButton, "Click", 10);
		}
		catch (Exception exc234) {}

		aobj.showAllAmenitiesButton.click();
		logger.info("Show all amenities clicked");

		try {
			utility.Pause(driver, aobj.amenitiesPopupHeading, "Visibility", 10);
		}
		catch (Exception exc234) {}

		Assert.assertTrue(aobj.getAmenityFromPopup(facility).isDisplayed(), "Amenity not present");
		logger.info("Amenity is displayed");

		logger.info(" -- Test Scenario for Extra Filters verified -- ");
	}

	// Test Scenarios
	@Test (priority=4, description = "Assignment")
	public void VerifyPropOnMap() throws InterruptedException, IOException {

		String text = driver.getTitle();
		logger.info(text);

		if (!text.contains("Stays")) {
			driver.switchTo().window(windowHandle);
		}

		logger.info(" -- Verifying Scenario for property on Map -- ");

		String getPropertyName = "";

		// Apply the same filter as on first test
		if (aobj.filterApplied.isDisplayed()) {

			aobj.moreFilters.click();
			logger.info("More filters clicked");

			int bedCount = 5;
			aobj.decreaseBedCount(bedCount);
			logger.info("Bed count decreased by - " + bedCount);

			String facility = "Pool";
			aobj.selectFacility(facility);
			logger.info("Facility unselected");

			// Check stays
			aobj.showStaysButton.click();
			logger.info("Stays button clicked");

		}

		try {
			utility.Pause(driver, aobj.map, "Click", 10);
		}
		catch (Exception e344) {}

		utility.Pause(driver, aobj.filterUnApplied, "Click", 10);

		getPropertyName = aobj.searchList.get(0).getAttribute("aria-label");
		logger.info(getPropertyName);

		String getHref = aobj.searchList.get(0).getAttribute("href");
		logger.info(getHref);

		String perNightCost = aobj.perNightSearchResults.get(0).getText();
		perNightCost = perNightCost.split("per")[0].trim();
		
		try {
			utility.Pause(driver, aobj.getPropertyOnMap(getPropertyName), "Click", 10);
		}
		catch (Exception exc2323) {}

		String getColor = aobj.getPropertyOnMap(getPropertyName).getCssValue("background-color");
		logger.info(getColor);

		utility.movetoHover(driver, aobj.getSearchResults.get(0));
		logger.info("Hovered over the first search result");

		String getColor2 = aobj.getPropertyOnMap(getPropertyName).getCssValue("background-color");
		logger.info(getColor2);

		if (!getColor.equals(getColor2)) {
			logger.info("Color changed");
		}
		else {
			Assert.fail("Property color not changed on Map");
		}

		aobj.getPropertyOnMap(getPropertyName).click();
		logger.info("Property clicked on Map");

		try {
			Thread.sleep(500);
			utility.Pause(driver, aobj.getPerNightCostOnMap(getPropertyName), "Click", 10);
		}
		catch (Exception exc223) {}

		String costOnMap = aobj.getPerNightCostOnMap(getPropertyName).getText().trim();
		costOnMap = costOnMap.split("per")[0].trim();

		logger.info(perNightCost + " -- " + costOnMap);

		Assert.assertTrue(perNightCost.equals(costOnMap), "Costs details not matched");
		logger.info("Cost details and name matched");

		logger.info(" -- Test Scenario for property on Map verified -- ");

	}

	@AfterMethod
	public void onTestFaliure(ITestResult iTestResult){

		if(ITestResult.FAILURE==iTestResult.getStatus()){
			utility.captureScreenshot(driver,iTestResult.getName());
			if(driver instanceof WebDriver){
				System.out.println("Screenshot Captured for test case:"+tlistener.getTestMethodName(iTestResult));
				tlistener.saveScreenshotPNG(driver);
			}
		}
	}

	//It will execute after every test Suite       
	@Title("Closing Browser")
	@AfterSuite
	public void ClosingBrowser() {

		logger.info("driver quit");
		driver.quit();
	}

}
