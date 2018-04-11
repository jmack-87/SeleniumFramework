package com.jmack.Base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import io.qameta.allure.Attachment;

/**
 * 
 * @author Jerimiah Mack
 *
 */
public class ScreenShot extends Assertion {
	
	private RemoteWebDriver driver;
	private String id = "unknown";
	private String testName = "unknown";
	
	
	/**
	 * Constructor with provisions for instance logging
	 * @param driver RemoteWebDriver
	 * @param id test instance id (timestamp truncated to last three digits)
	 * @param testName
	 */
	public ScreenShot(RemoteWebDriver driver, String id, String testName) {
		
		this.id = id;
		this.testName = testName;
		this.driver = driver;
	}
	
	
	/**
	 * Minimum constructor
	 * @param driver RemoteWebDriver
	 */
	public ScreenShot(RemoteWebDriver driver) {
		
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
		return driver.getScreenshotAs(OutputType.BYTES);
		
	}
	
	
	/**
	 *  Always take a screenshot if Assert fails
	 */
	@Override
	public void onAssertFailure(IAssert<?> assertCommand) {
		
		takeScreenShot("FAIL: "+assertCommand.getMessage());
		
	}
	
	
}
