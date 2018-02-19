package com.apple.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Reporter;


public class ConfigProperties {
	

	public static String application = "";
	public static String appleURL = "";
	public static String browser = "";
	
	public static String chromeDriverLocation = "";
	public static String ieDriverLocation = "";
	public static String geckoDriverLocation = "";
	public static String testDataFile = "";
	
	/**
	 * This method loads properties from configuration file
	 * 
	 * @return configProperties Class Object
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	static {

		Properties propertiesObj = new Properties();
		String path = System.getProperty("user.dir")+"\\src\\main\\java\\com\\apple\\config\\config.properties";
		System.out.println("Properties file path ..." + path);

		try {
			propertiesObj.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Reporter.log("FileNotFoundException found : " + e);
		} catch (IOException e) {
			e.printStackTrace();
			Reporter.log("IOException found : " + e);
		}
		chromeDriverLocation = propertiesObj.getProperty("chromeDriverLocation");
		ieDriverLocation = propertiesObj.getProperty("ieDriverLocation");
		geckoDriverLocation = propertiesObj.getProperty("geckoDriverLocation");
		appleURL = propertiesObj.getProperty("appleURL");
		browser = propertiesObj.getProperty("browser");
		application = propertiesObj.getProperty("application");
		testDataFile = propertiesObj.getProperty(application + ".testDataFile");
	}
}


	

