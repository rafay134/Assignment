package Objects;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Utility {

	public static WebDriverWait wait;
	Logger logger = Logger.getLogger("Utility");

	public void checkPageIsReady(WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");

		}
		// This loop will rotate for 25 times to check If page Is ready after every 1
		// second.
		// You can replace your value with 25 If you wants to Increase or decrease wait
		// time.
		else {

			for (int i = 0; i < 25; i++) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				// To check page ready state.
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					System.out.println("Page Is loaded.");
					break;
				}
			}
		}

	}

	// This function will wait for element according to time(seconds) and condition
	// (Visibility,Click and Frame)
	public static void Pause(WebDriver driver, WebElement element, String KeyWord, int timeInSeconds) {

		wait = new WebDriverWait(driver, timeInSeconds); // time to pause in seconds
		boolean result = false;
		int attempts = 0;
		while (attempts < 4) {

			try {

				if (KeyWord.contains("Visibility")) {
					wait.until(ExpectedConditions.visibilityOf(element)); // waiting for the element to be visible

				} else if (KeyWord.contains("Click")) {
					wait.until(ExpectedConditions.elementToBeClickable(element)); // waiting for the element to be
					// Clickable
				} else if (KeyWord.contains("Frame")) {
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));// waiting for the frame to
					// be available and switch
					// to it
				} else if (KeyWord.contains("Invisibility")) {
					wait.until(ExpectedConditions.invisibilityOf(element));
				} else if (KeyWord.contains("Alert")) {
					wait.until(ExpectedConditions.alertIsPresent());
				}

				result = true;
				break;
			} catch (StaleElementReferenceException e) {

			}
			attempts++;
		}

	}

	// This function will scroll the element
	public static void scrollTo(WebDriver driver, WebElement element) {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	// This function will capture screenshot
	// for capturing screen shot
	public static void captureScreenshot(WebDriver driver, String screenshotName) {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;

			File source = ts.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(source, new File("./Screenshots/" + screenshotName + ".png"));

			System.out.println("Screenshot taken");
		} catch (Exception e) {

			System.out.println("Exception while taking screenshot " + e.getMessage());
		}
	}

	// This function is returning date after adding specified number of days to
	// current date
	public String AddDate(int number) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, number);

		Date currentDatePlusOne = c.getTime();
		String setDate = dateFormat.format(currentDatePlusOne).toString();
		return setDate;

	}

	// This function will hover webdriver on to the specified element.
	public static void movetoHover(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();

	}

	public String changeDateFormat (String date, String format) throws ParseException {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = df.parse(date);

		SimpleDateFormat newFormat = new SimpleDateFormat(format);

		String finalString = newFormat.format(date1);
		return finalString;
	}

	// This function will return Credentials in ArrayList from XML file.
	// It will be used to get url, username and password from xml file
	public ArrayList<String> getUsernamePassword() throws ParserConfigurationException, SAXException, IOException {
		
		// XML Chunk Starts Here
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		String filePath = System.getProperty("user.dir") + "\\Credentials\\Credentials.xml";

		Document document = builder.parse(new File(filePath));
		org.w3c.dom.Element rootElement = document.getDocumentElement();
		String URL = rootElement.getElementsByTagName("URL").item(0).getTextContent();
		String Username = rootElement.getElementsByTagName("Username").item(0).getTextContent();
		String Password = rootElement.getElementsByTagName("Password").item(0).getTextContent();
		String DBConnectionString = rootElement.getElementsByTagName("DBConnectionString").item(0).getTextContent();
		// XML Chunk Ends Here

		ArrayList<String> credentials = new ArrayList<String>();
		credentials.add(URL);
		credentials.add(Username);
		credentials.add(Password);
		credentials.add(DBConnectionString);
		return credentials;
	}

	// This function will handle window change
	public void switchtowindowhandler(WebDriver driver) {

		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}

	}
}