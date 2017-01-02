package gnsel.testbase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import gnsel.utils.ResourceHelper;

public class SeleniumTest {
	@SuppressWarnings("rawtypes")
	private static Class clazz = SeleniumTest.class;
	public static Logger logger = Logger.getLogger(SeleniumTest.class);
	public static WebDriver driver;
	public static String browser;
	public static Properties config;
	
	@BeforeSuite
	public void beforeSuite() throws IOException {
		config = ResourceHelper.loadConfig();
	}
	@BeforeMethod
	public void init() {
		browser = (System.getProperty("browser") != null)? System.getProperty("browser") : config.getProperty("browser");
		switch (browser.toLowerCase()) {
			case "ie":
				if (System.getProperty("webdriver.ie.driver") == null) {
					System.setProperty("webdriver.ie.driver", config.getProperty("webdriver.ie.driver"));
				}
				DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
				cap.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
				driver = new InternetExplorerDriver(cap);
				browser = "ie";
				break;
			case "chrome":
				if (System.getProperty("webdriver.chrome.driver") == null) {
					System.setProperty("webdriver.chrome.driver", config.getProperty("webdriver.chrome.driver"));
				}
				driver = new ChromeDriver();
				browser = "chrome";
				break;
			case "firefox": //firefox 47+
				if (System.getProperty("webdriver.gecko.driver") == null) {
					System.setProperty("webdriver.gecko.driver", config.getProperty("webdriver.gecko.driver"));
				}
				driver = new MarionetteDriver();
				browser = "firefox";
				break;
			case "safari": //only Safari 10+
				driver = new SafariDriver();
				browser = "safari";
				break;
			default: //firefox 45 or older
				driver = new FirefoxDriver();
				browser = "firefox-";
				break;
		}
		int implicit_timeout = (config.getProperty("implicit_timeout") != null)? 
				Integer.parseInt(config.getProperty("implicit_timeout")) : 5;
		driver.manage().timeouts().implicitlyWait(implicit_timeout, TimeUnit.SECONDS);
		pause(5000);
		driver.manage().window().maximize();
	}
	@AfterMethod
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	/**
	 * We just don't want to have to add 'throws InterruptedException' everywhere.
	 */
	public static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			//eat the exception
		}
	}
	
	public static void saveScreenshot(String sub_dir, String suffix) {
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		ResourceHelper.saveScreenshot(file, sub_dir, suffix);
	}
	public static void saveScreenshot() {
		saveScreenshot(clazz.getName(), "");
	}
	

	public static List<String> getTextOptions(Select select) {
		List<String> result = new ArrayList<String>();
		List<WebElement> options = select.getOptions();
		for (WebElement e : options) {
			result.add(e.getText());
		}
		return result;
	}
	
	/**
	 * Tests the options of a select box against a given set.
	 */
	public static boolean testSelectOptions(Select select, Set<String> unorder_set) {
		List<String> options = getTextOptions(select);
		if (options.size() != unorder_set.size()) {
			return false;
		}
		for (String s : unorder_set) {
			if (!(options.contains(s))) {
				return false;
			}
		}
		return true;
	}
	public static boolean testSelectOptionsContain(Select select, Set<String> unorder_set) {
		List<String> options = getTextOptions(select);
		for (String s : unorder_set) {
			if (!(options.contains(s))) {
				return false;
			}
		}
		return true;
	}
	
	public static void check(WebElement checkbox) {
		if (!(checkbox.isSelected())) {
			checkbox.click();
		}
	}
	public static void uncheck(WebElement checkbox) {
		if (checkbox.isSelected()) {
			checkbox.click();
		}
	}
}
