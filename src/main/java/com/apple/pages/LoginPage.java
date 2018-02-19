package com.apple.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apple.base.TestBase;


public class LoginPage extends TestBase {
	// Page Factory - OR:
	
	@FindBy(xpath = "//*[@id='login-appleId']")
	WebElement appleID;

	@FindBy(xpath = "//*[@id='login-password']")
	WebElement password;
	
	@FindBy(xpath = "//*[@id='sign-in']//span[contains(text(),'Sign In')]")
	WebElement signinButton;

	@FindBy(xpath = "//p[@id='login-errorText']")
	WebElement alertText;

	public LoginPage() {
		PageFactory.initElements(oBaseUtil.getDriver(), this);

	}

	public String getLoginPageTitle() {
		return oBaseUtil.getTitle();
	}

	
	public HomePage login(String un, String pwd) {
		oBaseUtil.waitTillElementIsVisible(appleID, 5);
		appleID.sendKeys(un);
		oBaseUtil.waitTillElementIsVisible(password, 5);
		password.sendKeys(pwd);
		oBaseUtil.waitTillElementIsVisible(signinButton, 5);
		signinButton.click();
		return new HomePage();
	}
	
	public String alertTextPresent(){
		String sAlertText = "Your AppleConnect account or password was entered incorrectly.";
		return oBaseUtil.verifyText(alertText, sAlertText);
	}
	
	

}
