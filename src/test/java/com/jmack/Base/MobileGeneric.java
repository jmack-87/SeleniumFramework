package com.jmack.Base;

import java.time.Duration;
import java.util.Properties;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

/**
 * 
 * @author Jerimiah Mack
 *
 */
public class MobileGeneric extends TestBase {

	private AppiumDriver<?> driver;
	private FluentWait<AppiumDriver<?>> wait;
	private Properties props;
	private By byType = null;
	private String[] command;
	private String errorCondition = "";
	private MobileElement me = null;
	
	protected String id = "unknown";
	protected String testName = "unknown";
	
	private MobileScreenShot ss;


	/**
	 * Minimum constructor for generic step-operations and confirmation.
	 * @param driver thread-safe WebDriver
	 * @param gc GlobalConstants instance
	 * @param props properties file instance
	 */
	public MobileGeneric(AppiumDriver<?> driver, MobileScreenShot ss, Properties props) {
		
		this.ss = ss;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<AppiumDriver<?>>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofMillis(1000));
		
	}
	
	/**
	 * Constructor for generic step-operations and confirmation, provisioned for instance logging
	 * @param driver thread-safe WebDriver
	 * @param gc GlobalConstants instance
	 * @param props properties file instance
	 */
	public MobileGeneric(AppiumDriver<?> driver, MobileScreenShot ss, Properties props, String id, String testName) {
		
		this.ss = ss;
		this.id = id;
		this.testName = testName;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<AppiumDriver<?>>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofMillis(1000));	
	}


	/**
	 * Acquires locator and type from locatorElementAndMethod(). Asserts locator has type (is locator vs string).
	 * FluentWait for element, by locator+type.
	 * @param propertyKey properties file key defining element locator
	 * @return WebElement or null
	 */
	//@Step("Wait for element.")
	public MobileElement waitForElement(String propertyKey) {

		String locator;
		String type;
		
		command = locatorElementAndMethod(propertyKey);
		//System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", id, testName, command.length);
		
		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", id, testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);
		
		locator = command[0];
		type = command[1];
		
		System.out.format("[LOG]: <[%s:%s] waiting for locator: %s; type: %s;>%n", id, testName, locator, type);
		
		switch (type) {
			case "xpath":
				byType = MobileBy.xpath(locator);
				break;
			case "css":
				byType = MobileBy.cssSelector(locator);
				break;
			case "id":
				byType = MobileBy.id(locator);
				break;
			case "accessibilityId":
				byType = MobileBy.AccessibilityId(locator);
				break;
			case "tagname":
				byType = MobileBy.tagName(locator);
				break;
			default:
				byType = MobileBy.tagName(locator);
				break;
		}
		
		try {
			me = this.wait.until(new Function<AppiumDriver<?>, MobileElement>(){
				public MobileElement apply(AppiumDriver<?> drv) {
					return (MobileElement) drv.findElement(byType);
				}
			});
		} catch (TimeoutException to) {
			return null;
		}
		
		//System.out.format("[DEBUG]: <[%s:%s] found %s>%n", id, testName, me);
		return me;
		
	}
	
	
	/**
	 * Acquires locator and type from locatorElementAndMethod(). Asserts locator has type (is locator vs string).
	 * Builds dynamic locator. FluentWait for element, by locator+type.
	 * @param propertyKey properties file key defining element locator
	 * @return WebElement or null
	 */
	//@Step("Wait for element.")
	public MobileElement waitForElement(String propertyKey, String replacement) {

		String locator;
		String type;
		
		command = locatorElementAndMethod(propertyKey);
		//System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", id, testName, command.length);
		
		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", id, testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = buildDynamicLocator(command[0], replacement);
		type = command[1];
		
		System.out.format("[LOG]: <[%s:%s] waiting for locator: %s; type: %s;>%n", id, testName, locator, type);
		
		switch (type) {
			case "xpath":
				byType = MobileBy.xpath(locator);
				break;
			case "css":
				byType = MobileBy.cssSelector(locator);
				break;
			case "id":
				byType = MobileBy.id(locator);
				break;
			case "accessibilityId":
				byType = MobileBy.AccessibilityId(locator);
				break;
			case "tagname":
				byType = MobileBy.tagName(locator);
				break;
			default:
				byType = MobileBy.tagName(locator);
				break;
		}
		
		try {
			me = this.wait.until(new Function<AppiumDriver<?>, MobileElement>(){
				public MobileElement apply(AppiumDriver<?> drv) {
					return (MobileElement) drv.findElement(byType);
				}
			});
		} catch (TimeoutException to) {
			return null;
		}
		
		//System.out.format("[DEBUG]: <[%s:%s] found %s>%n", id, testName, me);
		return me;
		
	}
	
	
	/**
	 * Asserts page title equals target value
	 * @param propertyKey properties file key defining string
	 */
	@Step("Confirm page title.")
	public void confirmTitle(String propertyKey) {
		
		ss.assertTrue(driver.getTitle().toLowerCase().equals(getPropertyValue(propertyKey)));
	}
	
	
	/**
	 * Navigates browser to target URL
	 * @param propertyKey properties file key defining string
	 */
	@Step("Navigate to URL.")
	public void getUrl(String propertyKey) {
		
		driver.get(getPropertyValue(propertyKey));
	}

	
	/**
	 * Retrieves locator definition and type from WebElement property definition
	 * @param propertyKey properties file key defining element locator
	 * @return String[] containing locator definition and locator type
	 */
	private String[] locatorElementAndMethod(String propertyKey) {
		
		//System.out.format("[DEBUG]: <[%s:%s] key: %s>%n", id, testName, propertyKey);
		command = null;
		String propertyValue = null;
		
		//System.out.format("[DEBUG]: <[%s:%s] key found?: %b>%n", id, testName, props.containsKey(propertyKey));
		if (props.containsKey(propertyKey)) {
			propertyValue = props.getProperty(propertyKey);
			//System.out.format("[DEBUG]: <[%s:%s] key value: %s>%n", id, testName, propertyValue);
			command = propertyValue.split(gc.locatorSeparator);
			//System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", id, testName, command.length);
		}
		
		return command;
	}
	
	
	/**
	 * Return String value of non-WebElement property definition
	 * @param propertyKey properties file key defining string
	 * @return String value of property
	 */
	private String getPropertyValue(String propertyKey) {
		
		//System.out.format("[DEBUG]: <[%s:%s] key: %s>%n", id, testName, propertyKey);
		command = null;
		String propertyValue = null;
		
		//System.out.format("[DEBUG]: <[%s:%s] key found?: %b>%n", id, testName, props.containsKey(propertyKey));
		if (props.containsKey(propertyKey)) {
			propertyValue = props.getProperty(propertyKey);
			//System.out.format("[DEBUG]: <[%s:%s] key value: %s>%n", id, testName, propertyValue);
		}
		
		return propertyValue;
	}

	
	/**
	 * Inserts string into target element
	 * @param propertyKey properties file key defining element locator
	 * @param input text to enter into target element
	 */
	@Step("Input text.")
	public void sendText(String propertyKey, String input) {
		
		me = confirmElementExistence(propertyKey);
		me.clear();
		me.sendKeys(input);
		ss.takeScreenShot("After input");
	}

	
	/**
	 * Manually trigger a screenshot
	 * @param description String describing screenshot to be taken
	 */
	@Step("Take Screenshot: {0}")
	public void takeScreenShot(String description) {
		
		ss.takeScreenShot(description);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * After confirming element existence, clicks element. 
	 * @param propertyKey properties file key defining element locator
	 */
	@Step("Click element.")
	public void clickElement(String propertyKey) {
		
		me = confirmElementExistence(propertyKey);
		me.click();
	}

	
	/**
	 * Performs a FluentWait for element described by property propertyKey. Asserts wait returns element.
	 * @param propertyKey properties file key defining element locator
	 */
	@Step("Confirm element exists.")
	public MobileElement confirmElementExistence(String propertyKey) {
		
		ss.assertTrue((me = waitForElement(propertyKey)) != null);
		return me;
	}

	
	/**
	 * Performs a FluentWait for element described by dynamic property propertyKey+replacement. Asserts wait returns element.
	 * @param propertyKey properties file key defining element locator
	 * @param replacement String to replace placeholder
	 * @return WebElement
	 */
	@Step("Confirm dynamic element exists.")
	public MobileElement confirmElementExistence(String propertyKey, String replacement) {
		
		ss.assertTrue((me = waitForElement(propertyKey, replacement)) != null);
		return me;
	}
	
	
	/**
	 * Given a generic locator (includes placeholder string), replace placeholder string with desired string
	 * @param locator properties file key defining element locator
	 * @param replacement String to replace placeholder
	 * @return String locator as rebuilt
	 */
	//@Step("Build dynamic locator.")
	private String buildDynamicLocator(String locator, String replacement) {
		
		return locator.replace(gc.compoundLocatorPlacehold, replacement);
	}
	
	
	/**
	 * Waits up to 30s for the browser JavaScript engine to report standby.
	 * 
	 */
	@Step("Wait for page to completely load.")
	public void waitForPageLoaded() {
		
		this.wait.until(new Function<RemoteWebDriver, Boolean>(){
			public Boolean apply(RemoteWebDriver drv) {
				return ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete");
			}
		});
	}
	
	
	/**
	 * Waits up to timeOutInSeconds for the browser JavaScript engine to report standby.
	 * @param timeOutInSeconds maximum seconds to wait
	 */
	@Step("Wait for page to completely load.")
	public void waitForPageLoaded(int timeOut) {
		
		FluentWait<RemoteWebDriver> wait = new FluentWait<RemoteWebDriver>(driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(timeOut));
		
		wait.until(new Function<RemoteWebDriver, Boolean>(){
			public Boolean apply(RemoteWebDriver drv) {
				return ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete");
			}
		});
		wait = null;
	}
	
	
	/**
	 *  Force fail a test
	 */
	@Step("Force a FAIL.")
	public void failTest() {
		
		ss.assertTrue(false, "This assertion intended to FAIL.");
	}
		
		
}
