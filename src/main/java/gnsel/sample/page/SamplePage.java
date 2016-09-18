package gnsel.sample.page;

import java.util.HashMap;

import org.openqa.selenium.By;

import gnsel.pom.JsonPOM;
import gnsel.pom.StateInterface;
import gnsel.testbase.SeleniumTest;

public class SamplePage extends SeleniumTest {
	@SuppressWarnings("rawtypes")
	private static Class clazz = SamplePage.class;
	
	private static JsonPOM pom = JsonPOM.build("/pom/sample/" + clazz.getSimpleName() + ".json");
	public static HashMap<String, By> locators = pom.getLocatorMap();
	public static HashMap<String, String> messages = pom.getMessageMap();
	public static enum State implements StateInterface {
		STATIC,
		VALIDATING_SMTH
	}
	
	public static SimpleTable table = new SimpleTable(locators.get("table"));

	@SuppressWarnings("unchecked")
	public static boolean selfCheck(StateInterface state, Object data) {
		saveScreenshot(clazz.getName(), (state == null)? "" : state.toString());
		boolean test = true;
		State s = (state == null)? State.STATIC : (State)state;
		switch (s) {
		case STATIC:
			test = test && testStatic();
			break;
		case VALIDATING_SMTH:
			test = test && testSmth((HashMap<String, String>)data);
			break;
		default:
			break;
		}
		return test;
	}
	
	/*************************** TEST METHODS *******************************/
	/************************************************************************/
	public static boolean testStatic() {
		return false;
	}
	public static boolean testSmth(HashMap<String, String> data) {
		return false;
	}
	
	/*************************** READ DATA METHODS **************************/
	/************************************************************************/
	
	/*************************** FILL METHODS *******************************/
	/************************************************************************/
	
	/*************************** UTILITIES **********************************/
	/************************************************************************/
}
