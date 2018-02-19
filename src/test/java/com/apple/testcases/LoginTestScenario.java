package com.apple.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apple.base.TestBase;
import com.apple.pages.HomePage;
import com.apple.pages.LoginPage;
import com.apple.util.DataProviderUtil;

public class LoginTestScenario extends TestBase {
	public static HomePage oHomePage;
	public static LoginPage oLoginPage;
	public static String sSheetName = "Credentials";
	public String browserName;
	
	
	public LoginTestScenario()
	{
		super();
	}
	
	@BeforeClass
	@Parameters({ "browser" })
	public void init(String browserName) {
	  this.browserName = browserName;
	}
	
	
	@Test(priority=1,dataProvider="UserCredentials")
	public void LoginTest(String sUserID, String sPassword){
		initialize(browserName);
		oHomePage = new HomePage();
		
		oBaseUtil.verifyTitle(oHomePage.getHomePageTitle(), "Apple");
		
		oHomePage.clickOnBagIcon();
		
		oLoginPage = oHomePage.clickOnSignInLink();
		
		oBaseUtil.verifyTitle(oLoginPage.getLoginPageTitle(), "Sign in - Apple");
		
		oLoginPage.login(sUserID,sPassword);
		
		System.out.println(oLoginPage.alertTextPresent());
		
		oBaseUtil.savePageSnapshot();
		
		oBaseUtil.closeBrowser();
		
	}
	
	
	@DataProvider
	public Object[][] UserCredentials(){
		
		Object data[][] = DataProviderUtil.getSheetData(testDataFile,sSheetName);
		return data;
	}
	
	

}
