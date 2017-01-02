package demo;

import org.testng.annotations.Test;

import gnsel.testbase.SeleniumTest;

public class DemoTest extends SeleniumTest {
	// A method to test WebDriver setup
	@Test
	public void test() {
		driver.navigate().to(config.getProperty("base_url"));
		pause(5000);
		saveScreenshot();
	}
}
