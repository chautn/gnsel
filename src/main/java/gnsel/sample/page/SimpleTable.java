package gnsel.sample.page;

import org.openqa.selenium.By;

import gnsel.testbase.SeleniumTest;

public class SimpleTable extends SeleniumTest {
	public By table;
	
	public SimpleTable(By table) {
		this.table = table;
	}
	
	public boolean test() {
		return driver.findElement(table).isDisplayed();
	}
}
