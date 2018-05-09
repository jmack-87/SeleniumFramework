package com.jmack.Base;

import java.time.Duration;
import java.util.Properties;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

/**
 * 
 * @author Jerimiah Mack
 *
 */
public class Generic extends TestBase {

	private RemoteWebDriver driver;
	private FluentWait<RemoteWebDriver> wait;
	private Properties props;
	private By byType = null;
	private String[] command;
	private String errorCondition = "";
	private WebElement we = null;
	
	protected String id = "unknown";
	protected String testName = "unknown";
	
	private ScreenShot ss;
	//private GlobalConstants gc;

	
	/**
	 * Minimum constructor for generic step-operations and confirmation.
	 * @param driver thread-safe WebDriver
	 * @param gc GlobalConstants instance
	 * @param props properties file instance
	 */
	public Generic(RemoteWebDriver driver, ScreenShot ss, Properties props) {
		
		this.ss = ss;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<RemoteWebDriver>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(30));
		
	}
	
	/**
	 * Minimum constructor for generic step-operations and confirmation.
	 * @param driver thread-safe WebDriver
	 * @param gc GlobalConstants instance
	 * @param props properties file instance
	 */
	public Generic(AppiumDriver<?> driver, ScreenShot ss, Properties props) {
		
		this.ss = ss;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<RemoteWebDriver>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(30));
		
		((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		
	}
	
	/**
	 * Constructor for generic step-operations and confirmation, provisioned for instance logging
	 * @param driver thread-safe WebDriver
	 * @param gc GlobalConstants instance
	 * @param props properties file instance
	 */
	public Generic(RemoteWebDriver driver, ScreenShot ss, Properties props, String id, String testName) {
		
		this.ss = ss;
		this.id = id;
		this.testName = testName;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<RemoteWebDriver>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(30));
		
	}


	/**
	 * Acquires locator and type from locatorElementAndMethod(). Asserts locator has type (is locator vs string).
	 * FluentWait for element, by locator+type.
	 * @param propertyKey properties file key defining element locator
	 * @return WebElement or null
	 */
	@Step("Wait for element.")
	public WebElement waitForElement(String propertyKey) {

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
				byType = By.xpath(locator);
				break;
			case "css":
				byType = By.cssSelector(locator);
				break;
			case "id":
				byType = By.id(locator);
				break;
			case "tagname":
				byType = By.tagName(locator);
				break;
			default:
				byType = By.tagName(locator);
				break;
		}
		
		try {
			we = this.wait.until(new Function<RemoteWebDriver, WebElement>(){
					public WebElement apply(RemoteWebDriver drv) {
						return drv.findElement(byType);
					}
			});
		} catch (TimeoutException to) {
			return null;
		}
		
		//System.out.format("[DEBUG]: <[%s:%s] found %s>%n", id, testName, me);
		return we;
		
	}
	
	
	/**
	 * Acquires locator and type from locatorElementAndMethod(). Asserts locator has type (is locator vs string).
	 * Builds dynamic locator. FluentWait for element, by locator+type.
	 * @param propertyKey properties file key defining element locator
	 * @return WebElement or null
	 */
	//@Step("Wait for element.")
	public WebElement waitForElement(String propertyKey, String replacement) {

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
				byType = By.xpath(locator);
				break;
			case "css":
				byType = By.cssSelector(locator);
				break;
			case "id":
				byType = By.id(locator);
				break;
			case "tagname":
				byType = By.tagName(locator);
				break;
			default:
				byType = By.tagName(locator);
				break;
		}
		
		try {
			we = this.wait.until(new Function<RemoteWebDriver, WebElement>(){
					public WebElement apply(RemoteWebDriver drv) {
						return drv.findElement(byType);
					}
			});
		} catch (TimeoutException to) {
			return null;
		}
		
		//System.out.format("[DEBUG]: <[%s:%s] found %s>%n", id, testName, me);
		return we;
		
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
		
		we = confirmElementExistence(propertyKey);
		we.clear();
		we.sendKeys(input);
		ss.takeScreenShot("After input");
		
	}

	
	/**
	 * Manually trigger a screenshot
	 * @param description String describing screenshot to be taken
	 */
	@Step("Take Screenshot: {0}")
	public void takeScreenShot(String description) {
		
		ss.takeScreenShot(description);
	}
	
	
	/**
	 * After confirming element existence, clicks element. 
	 * @param propertyKey properties file key defining element locator
	 */
	@Step("Click element.")
	public void clickElement(String propertyKey) {
		
		we = confirmElementExistence(propertyKey);
		we.click();
		
	}

	
	/**
	 * Performs a FluentWait for element described by property propertyKey. Asserts wait returns element.
	 * @param propertyKey properties file key defining element locator
	 */
	@Step("Confirm element exists.")
	public WebElement confirmElementExistence(String propertyKey) {
		
		ss.assertTrue((we = waitForElement(propertyKey)) != null);
		return we;
		
	}

	
	/**
	 * Performs a FluentWait for element described by dynamic property propertyKey+replacement. Asserts wait returns element.
	 * @param propertyKey properties file key defining element locator
	 * @param replacement String to replace placeholder
	 * @return WebElement
	 */
	@Step("Confirm dynamic element exists.")
	public WebElement confirmElementExistence(String propertyKey, String replacement) {
		
		ss.assertTrue((we = waitForElement(propertyKey, replacement)) != null);
		return we;
		
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
