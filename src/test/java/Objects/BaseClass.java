package Objects;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners({ TestListener.class })
public class BaseClass {

	public WebDriver driver = null;
	Logger logger = Logger.getLogger("Zameen_Assignment");
	public HashMap<String, Integer> guests = new HashMap<String, Integer>();
	public Utility utility;
	public AirBnb aobj;
	public ArrayList<String> credentials = new ArrayList<String> ();

	// Variables
	public String URL = "", startDate = "", endDate = "", windowHandle = "";

	@BeforeSuite
	public void BaseMethod() {

		WebDriverManager.chromedriver().setup();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);	
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(ChromeOptions.CAPABILITY, options);
		options.setExperimentalOption("useAutomationExtension", false);
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

}












