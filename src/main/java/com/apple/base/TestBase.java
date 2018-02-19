package com.apple.base;

/*
 * 
 * @author = Nandkishor Periwal
 * 
 */


import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.apple.util.BaseUtil;
import com.apple.util.ConfigProperties;
import com.apple.util.WebEventListener;

public class TestBase extends ConfigProperties{
	public static BaseUtil oBaseUtil;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener oWebEventListener;

	public TestBase() {
		oBaseUtil = new BaseUtil();
	}
	
	public static void initialize(String browserName) {
		oBaseUtil.openBrowser(browserName, appleURL);
		BaseUtil.oDriver = oBaseUtil.getDriver();
		e_driver = new EventFiringWebDriver(oBaseUtil.getDriver());
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		oWebEventListener = new WebEventListener();
		e_driver.register(oWebEventListener);
		BaseUtil.oDriver = e_driver;
		
	}
	
	
	

}
