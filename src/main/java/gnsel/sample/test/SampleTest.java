package gnsel.sample.test;

import org.testng.annotations.Test;

import gnsel.sample.page.SamplePage;
import gnsel.testbase.SeleniumTest;

public class SampleTest extends SeleniumTest {
	@Test
	public void test() {
		test1();
	}
	public void test1() {
		driver.navigate().to(config.getProperty("base_url"));
		pause(5000);
		saveScreenshot();
		SamplePage.selfCheck(null, null);
		logger.info("done");
	}
}
