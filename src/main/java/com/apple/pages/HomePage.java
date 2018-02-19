package com.apple.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apple.base.TestBase;

public class HomePage extends TestBase {
	// Page Factory - OR:

	@FindBy(xpath = "//ul[@class='ac-gn-list']//li[@id='ac-gn-bag']/a")
	WebElement bagIcon;

	@FindBy(xpath = "//*[@id='ac-gn-bagview-content']/nav/ul/li[5]/a[contains(text(),'Sign in')]")
	WebElement signinLink;

	public HomePage() {
		PageFactory.initElements(oBaseUtil.getDriver(), this);

	}

	public String getHomePageTitle() {
		
		return oBaseUtil.getTitle();
	}
	
	public void clickOnBagIcon(){
		oBaseUtil.waitTillElementIsVisible(bagIcon, 5);
		bagIcon.click();
	}
	
	public LoginPage clickOnSignInLink(){
		oBaseUtil.waitTillElementIsVisible(signinLink, 5);
		signinLink.click();
		return new LoginPage();
		
	}


}
