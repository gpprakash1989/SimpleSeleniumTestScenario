package com.apple.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.apple.base.TestBase;
import com.apple.pages.HomePage;
import com.apple.pages.LoginPage;


public class HomePageTest extends TestBase {
	
	public static HomePage oHomePage;
	public static LoginPage oLoginPage;
	
	public HomePageTest()
	{
		super();
	}
	
	
	@BeforeClass
	public void setUp(){
		initialize(browser);
		oHomePage = new HomePage();

	}
	
	@Test(priority=1)
	public void homePageTitleTest(){
		String sActualLoginPageTitle = oHomePage.getHomePageTitle();
		String expectedLoginPageTitle = "Apple";
		oBaseUtil.verifyTitle(sActualLoginPageTitle, expectedLoginPageTitle);
	}
	
	@Test(priority=2)
	public void clickOnBagIconTest(){
		oHomePage.clickOnBagIcon();
	}
	
	@Test(priority=3)
	public void clickOnSignInLinkTest(){
		oHomePage.clickOnSignInLink();
	}
	
	@AfterClass
	public void tearDown(){
		oBaseUtil.closeBrowser();
	}
	
	

}
