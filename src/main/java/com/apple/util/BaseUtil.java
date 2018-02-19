package com.apple.util;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseUtil extends ConfigProperties {

	public static WebDriver oDriver;
	private long lngPageLoadTimeOut;
	private long lngElementDetectionTimeOut;
	String firstChildWindow;
	String frame;
	Actions myActions;
	Action seriesOfActions;

	public BaseUtil() {
		lngPageLoadTimeOut = 60L;
		lngElementDetectionTimeOut = 30L;
	}

	public void setPageLoadTimeOut(long lngPageLoadTimeOut) {
		this.lngPageLoadTimeOut = lngPageLoadTimeOut;
	}

	public void setElementDetectionTimeOut(long lngElementDetectionTimeOut) {
		this.lngElementDetectionTimeOut = lngElementDetectionTimeOut;
	}

	public void openBrowser(String sBrowserType, String sUrl) {
		try {
			oDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
					getBrowserCapabilities(sBrowserType));

			if (sUrl.isEmpty()) {

				sUrl = "about:blank";
			}

			oDriver.manage().window().maximize();
			oDriver.manage().deleteAllCookies();

			oDriver.manage().timeouts().pageLoadTimeout(lngPageLoadTimeOut, TimeUnit.SECONDS);

			oDriver.manage().timeouts().implicitlyWait(lngElementDetectionTimeOut, TimeUnit.SECONDS);

			oDriver.get(sUrl);

			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static DesiredCapabilities getBrowserCapabilities(String browserType) {
		DesiredCapabilities capability;
		switch (getBrowserTypeIndexed(browserType)) {
		case 1:
			System.out.println("Opening firefox driver");
			capability = DesiredCapabilities.firefox();
			capability.setPlatform(Platform.WINDOWS);
			return capability;
		case 3:
			System.out.println("Opening chrome driver");
			capability = DesiredCapabilities.chrome();
			capability.setPlatform(Platform.WINDOWS);
			return capability;
		case 2:
			System.out.println("Opening IE driver");
			capability = DesiredCapabilities.internetExplorer();
			capability.setPlatform(Platform.WINDOWS);
			capability.setCapability("ignoreZoomSetting", true);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			return capability;
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			return DesiredCapabilities.firefox();
		}
	}

	// ------------------------------------------------------------------

	public void closeBrowser() {
		try {
			if (oDriver != null) {
				oDriver.quit();
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// ------------------------------------------------------------------

	public WebDriver getDriver() {
		return oDriver;
	}

	// ------------------------------------------------------------------

	public String getTitle() {
		return oDriver.getTitle();

	}

	// ------------------------------------------------------------------
	private static int getBrowserTypeIndexed(String sBrowserType) {
		sBrowserType = sBrowserType.toLowerCase().trim();

		if (sBrowserType.isEmpty()) {
			return -1;
		}

		if (sBrowserType.equals("ff") || sBrowserType.equals("firefox") || sBrowserType.equals("mozilla")
				|| sBrowserType.equals("mozilla firefox")) {
			return 1;
		}

		if (sBrowserType.equals("ie") || sBrowserType.equals("explorer") || sBrowserType.equals("internet explorer")) {
			return 2;
		}

		if (sBrowserType.equals("chrome") || sBrowserType.equals("google") || sBrowserType.equals("google chrome")) {
			return 3;
		}

		return -1;
	}

	// ---------------------------------------------------------------------

	public void getUrl(String url) {
		oDriver.get(url);
	}

	// ---------------------------------------------------------------------

	public boolean waitTillElementIsVisible(WebElement element, long timeoutSeconds) {
		try {

			WebDriverWait oWait = new WebDriverWait(oDriver, timeoutSeconds);

			oWait.until(ExpectedConditions.visibilityOf(element));
			return true;

		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

	// ---------------------------------------------------------------------

	public void waitTillElementIsClickable(WebElement element, long timeoutSeconds) {
		try {

			WebDriverWait oWait = new WebDriverWait(oDriver, timeoutSeconds);

			oWait.until(ExpectedConditions.elementToBeClickable(element));

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// -------------------------------------------------------------------

	public void waitForSeconds(long seconds) {
		try {

			Thread.sleep(seconds * 1000L);
		} catch (Throwable t) {

			t.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------

	public String savePageSnapshot() {
		try {
			
			String sImagePath =System.getProperty("user.dir") +"/LoginTestScreenShots/" + getDateTimeStamp() + ".png";
					
			TakesScreenshot oCamera;
			File oTmpFile, oImageFile;
			oImageFile = new File(sImagePath);

			try {
				Files.deleteIfExists(Paths.get(sImagePath));
			} catch (NoSuchFileException e) {
				System.out.println("No such file/directory exists");
			}

			oCamera = (TakesScreenshot) oDriver;
			oTmpFile = (File) oCamera.getScreenshotAs(OutputType.FILE);
			oCamera.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(oTmpFile, oImageFile);

			return "File got saved";

		} catch (Throwable t) {
			t.printStackTrace();
			return "File already exists";
		}
	}
	
	public String savePageSnapshot(String sImagePath) {
		try {
			
					
			TakesScreenshot oCamera;
			File oTmpFile, oImageFile;
			oImageFile = new File(sImagePath);

			try {
				Files.deleteIfExists(Paths.get(sImagePath));
			} catch (NoSuchFileException e) {
				System.out.println("No such file/directory exists");
			}

			oCamera = (TakesScreenshot) oDriver;
			oTmpFile = (File) oCamera.getScreenshotAs(OutputType.FILE);
			oCamera.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(oTmpFile, oImageFile);

			return "File got saved";

		} catch (Throwable t) {
			t.printStackTrace();
			return "File already exists";
		}
	}

	// ---------------------------------------------------------------------

	public void setText(WebElement element, String sText) {
		try {

			element.sendKeys(sText);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------

	public String getText(WebElement element) {
		try {
			return element.getText();

		} catch (Throwable t) {
			t.printStackTrace();
			return "No such text found";
		}
	}

	// ---------------------------------------

	public String verifyText(WebElement element, String sValue) {
		String text = element.getText().toString();
		if (text.equalsIgnoreCase(sValue)) {
			return "Text Verified";
		} else {
			System.out.println("Text got: " + text);
			return "Verification Failed";
		}
	}

	// ---------------------------------------

	public void verifyTitle(String actual, String expected) {

		Assert.assertEquals(actual, expected, "Verification Failed");

	}

	// ---------------------------------------

	public void switchToWindow(int x) {
		try {
			firstChildWindow = oDriver.getWindowHandles().toArray()[x].toString();
			oDriver.switchTo().window(firstChildWindow);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// -----------------------------------------

	public void switchToFrame(String frameID) {
		try {
			oDriver.switchTo().frame(frameID);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// -----------------------------------------

	public void selectParentWindow() {

		oDriver.switchTo().window(oDriver.getWindowHandles().toArray()[0].toString());

	}

	// -----------------------------------------

	public void selectItemInListBox(WebElement element, String sItemVisibleText) {
		try {

			Select oListBox;

			oListBox = new Select(element);

			oListBox.selectByVisibleText(sItemVisibleText);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// -----------------------------------------

	public String mouseHover(WebElement element) {
		try {
			myActions = new Actions(oDriver);
			seriesOfActions = myActions.moveToElement(element).build();
			seriesOfActions.perform();
			return "Action performed successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}

	}

	// -----------------------------------------

	public String mouseHoverAndClick(WebElement element) {
		try {
			WebElement we = oDriver.findElement(By.xpath("//li[@id='wp-admin-bar-logout']/a[1]"));
			myActions = new Actions(oDriver);
			seriesOfActions = myActions.moveToElement(element).moveToElement(we).click(we).build();
			seriesOfActions.perform();
			return "Action performed successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}

	}

	// -----------------------------------------

	public void scrollToTop() {
		JavascriptExecutor js = (JavascriptExecutor) oDriver;
		// if the element is on top.
		js.executeScript("scroll(250, 0)");
	}

	// -----------------------------------------

	public void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) oDriver;
		// if the element is on bottom.
		js.executeScript("scroll(0,250)");
	}

	// -----------------------------------------

	public void scrollToElement(WebElement element) {
		JavascriptExecutor jse;
		jse = (JavascriptExecutor) oDriver;
		int xPoint = element.getLocation().x;
		int yPoint = element.getLocation().y;
		String command = String.format("window.scrollTo(%d, %d)", xPoint, yPoint);
		jse.executeScript(command);

	}

	// -----------------------------------------

	public void pressTab(String sValue) {
		seriesOfActions = myActions.keyDown(Keys.TAB).keyUp(Keys.TAB).sendKeys(sValue).build();
		seriesOfActions.perform();

	}

	// -----------------------------------------

	public void pressEnter() {
		seriesOfActions = myActions.keyDown(Keys.ENTER).keyUp(Keys.ENTER).build();
		seriesOfActions.perform();

	}

	// -----------------------------------------

	public void selectItemByValueInListBox(WebElement element, String sItemValue) {
		try {
			Select oListBox;

			oListBox = new Select(element);

			oListBox.selectByValue(sItemValue);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static String getDateTimeStamp() {

		Date oDate;
		String[] sDatePart;
		String sDateStamp;

		oDate = new Date();
		System.out.println(oDate.toString());
		// Mon Sep 07 17:28:42 IST 2015

		sDatePart = oDate.toString().split(" ");

		sDateStamp = sDatePart[5] + "_" + sDatePart[1] + "_" + sDatePart[2] + "_" + sDatePart[3];

		sDateStamp = sDateStamp.replace(":", "_");
		System.out.println(sDateStamp);
		return sDateStamp;
	}

	// -----------------------------------------

	public void closeCurrentBrowser() {
		oDriver.close();
	}
}
