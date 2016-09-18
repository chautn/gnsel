package gnsel.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import gnsel.testbase.SeleniumTest;


@SuppressWarnings("unused")
public class ResourceHelper {
	public static final String config_path = "/config.properties";
	public static Logger logger = Logger.getLogger(SeleniumTest.class);
	
	/**
	 * Loads Properties from a properties file in classpath.
	 */
	public static Properties loadProperties(String path) {
		InputStream in = ClassLoader.class.getResourceAsStream(path);
		Properties properties = new Properties();
		try {
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * Loads Properties from /src/main/resources/config.properties file.
	 */
	public static Properties loadConfig() {
		return loadProperties(config_path);
	}

	/**
	 * Reads the whole text file as a String.
	 */
	public static String readFileToString(String file) {
		try {
			File file_ = new File(ClassLoader.class.getResource(file).toURI());
			return FileUtils.readFileToString(file_);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Generate a unique path for saving screenshot
	 */
	private static String uniquePath(String sub_dir, String suffix, String file_extension) {
		suffix = suffix.replaceAll("[^a-zA-Z0-9_-]", "");
		if (suffix.length() > 255) {
			suffix = suffix.substring(0, 254);
		}
		suffix = (suffix.equals(""))? suffix : "_" + suffix;
		String dir = (System.getProperty("screenshot_dir") != null)? 
				System.getProperty("screenshot_dir") : loadConfig().getProperty("screenshot_dir");
		Date date = new Date();
		Long time = date.getTime();
		String d = new SimpleDateFormat("yyyyMMdd").format(date);
		String m = new SimpleDateFormat("HH_mm").format(date);
		return dir + "/" + d + "/" + sub_dir + "/" + time + "_" + m + suffix + "." + file_extension;
	}
	
	/**
	 * Save screenshot
	 */
	public static void saveScreenshot(File file, String sub_dir, String suffix) {
		String target = uniquePath(sub_dir, suffix, "png");
		try {
			FileUtils.copyFile(file, new File(target));
		} catch (IOException e) {
			logger.debug("cannot save screenshot to " + target);
			e.printStackTrace();
		}
	}
	
}
