package com.jmack.Base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import io.qameta.allure.Attachment;

public class ScreenShot extends Assertion {
	
	private String id = "unknown";
	private String testName = "unknown";
	private RemoteWebDriver driver;

	
	public ScreenShot(RemoteWebDriver driver, String testName, String id) {
		this.id = id;
		this.testName = testName;
		this.driver = driver;
	}
	
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
		System.out.format("[LOG]: <[%s:%s] taking screenshot: \"%s\">%n", this.id, this.testName, description);
		return this.driver.getScreenshotAs(OutputType.BYTES);
		
	}
	
	/**
	 *  Always take a screenshot if Assert fails
	 */
	@Override
	public void onAssertFailure(IAssert<?> assertCommand) {
		
		takeScreenShot("FAIL: "+assertCommand.getMessage());
		
	}
	
}
