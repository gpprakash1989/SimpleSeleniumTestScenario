package com.apple.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.apple.base.TestBase;
import com.apple.pages.HomePage;
import com.apple.pages.LoginPage;
import com.apple.util.DataProviderUtil;


public class LoginPageTest extends TestBase {
	
	public static HomePage oHomePage;
	public static LoginPage oLoginPage;
	public static String sSheetName = "Credentials";
	
	public LoginPageTest()
	{
		super();
	}
	
	
	@BeforeMethod
	public void setUp(){
		initialize(browser);
		oLoginPage = new LoginPage();
		oHomePage = new HomePage();
		oHomePage.clickOnBagIcon();
		oHomePage.clickOnSignInLink();
	}
	
	@Test(priority=1)
	public void loginPageTitleTest(){
		String sActualLoginPageTitle = oLoginPage.getLoginPageTitle();
		String expectedLoginPageTitle = "Sign in - Apple";
		oBaseUtil.verifyTitle(sActualLoginPageTitle, expectedLoginPageTitle);
	}
	
	@Test(priority=2,dataProvider="UserCredentials")
	public void loginTest(String AppleID, String Password){
		oLoginPage.login(AppleID,Password);
	}
	
	@DataProvider
	public Object[][] UserCredentials(){
		
		Object data[][] = DataProviderUtil.getSheetData(testDataFile,sSheetName);
		return data;
	}
	
	
	
	
	@AfterMethod
	public void tearDown(){
		oBaseUtil.closeBrowser();
	}
	
	

}
