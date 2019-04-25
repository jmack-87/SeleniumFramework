package com.jmack.Base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;

/**
 *
 * @author Jerimiah Mack
 *
 */
public class MobileScreenShot extends Assertion {

	private AppiumDriver<?> driver;
	private String id = "unknown";
	private String testName = "unknown";


	/**
	 * Constructor with provisions for instance logging
	 * @param driver RemoteWebDriver
	 * @param id test instance id (timestamp truncated to last three digits)
	 * @param testName
	 */
	public MobileScreenShot(AppiumDriver<?> driver, String id, String testName) {

		this.id = id;
		this.testName = testName;
		this.driver = driver;
	}


	/**
	 * Minimum constructor
	 * @param driver RemoteWebDriver
	 */
	public MobileScreenShot(AppiumDriver<?> driver) {

		this.driver = driver;
	}


	/**
	 * Take a screenshot
	 * @param description String description of screenshot supplied to Allure framework
	 * @return
	 */
	@Attachment(value="{description}", type="image/png")
	public byte[] takeScreenShot(String description) {

		System.out.format("[LOG]: <[%s:%s] taking screenshot: \"%s\">%n", id, testName, description);
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}


	/**
	 *  Always take a screenshot if Assert fails
	 */
	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError er) {

		takeScreenShot("FAIL: "+er.getLocalizedMessage());
	}


}
