package gnsel.pom;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import org.openqa.selenium.By;

import com.google.gson.Gson;

public class JsonPOM {
	public Locator[] locators;
	public Message[] messages;
	
	public JsonPOM(Locator[] locators, Message[] messages) {
		super();
		this.locators = locators;
		this.messages = messages;
	}
	public static JsonPOM build(String jsonResource) {
		Gson gson = new Gson();
		Reader in = new InputStreamReader(ClassLoader.class.getResourceAsStream(jsonResource));
		return gson.fromJson(in, JsonPOM.class);
	}
	
	public Locator[] getLocators() {
		return locators;
	}
	public void setLocators(Locator[] locators) {
		this.locators = locators;
	}
	public Message[] getMessages() {
		return messages;
	}
	public void setMessages(Message[] messages) {
		this.messages = messages;
	}
	
	public HashMap<String, By> getLocatorMap() {
		HashMap<String, By> map = new HashMap<String, By>();
		int size = locators.length;
		for (int i = 0; i < size; i++) {
			map.put(locators[i].getKey(), locators[i].getBy());
		}
		return map;
	}
	public HashMap<String, String> getMessageMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		int size = messages.length;
		for (int i = 0; i < size; i++) {
			map.put(messages[i].getKey(), messages[i].getMessage());
		}
		return map;
	}

	public static class Locator {
		public String key;
		public String use;
		public String expr;
		public Locator(String key, String use, String expr) {
			super();
			this.key = key;
			this.use = use;
			this.expr = expr;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getUse() {
			return use;
		}
		public void setUse(String use) {
			this.use = use;
		}
		public String getExpr() {
			return expr;
		}
		public void setExpr(String expr) {
			this.expr = expr;
		}
		public By getBy() {
			By by = null;
			switch (use) {
			case "className":
				by = By.className(expr);
				break;
			case "cssSelector":
				by = By.cssSelector(expr);
				break;
			case "id":
				by = By.id(expr);
				break;
			case "linkText":
				by = By.linkText(expr);
				break;
			case "name":
				by = By.name(expr);
				break;
			case "partialLinkText":
				by = By.partialLinkText(expr);
				break;
			case "tagName":
				by = By.tagName(expr);
				break;
			case "xpath": default:
				by = By.xpath(expr);
				break;
			}
			return by;
		}
	}
	public static class Message {
		public String key;
		public String message;
		public Message(String key, String message) {
			super();
			this.key = key;
			this.message = message;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
