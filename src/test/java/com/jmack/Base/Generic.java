package com.jmack.Base;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
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
import org.openqa.selenium.support.ui.Select;

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
	private String value;
	private WebElement we = null;
	private List<WebElement> weArray = null;
	private boolean imageWidthNotZero;

	protected String id = "unknown";
	protected String testName = "unknown";
	protected JavascriptExecutor jse;

	private ScreenShot ss;
	// private GlobalConstants gc;


	/**
	 * Minimum constructor for generic step-operations and confirmation.
	 *
	 * @param driver (WebDriver) thread-safe WebDriver
	 * @param ss (ScreenShot) Screenshot instance
	 * @param props (Properties) properties file instance
	 */
	public Generic(RemoteWebDriver driver, ScreenShot ss, Properties props) {

		this.ss = ss;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<RemoteWebDriver>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(gc.defaultTimeOut));
		this.jse = (JavascriptExecutor) this.driver;

	}


	/**
	 * Minimum constructor for generic step-operations and confirmation.
	 *
	 * @param driver (WebDriver) thread-safe WebDriver
	 * @param ss (ScreenShot) Screenshot instance
	 * @param props (Properties) properties file instance
	 */
	public Generic(AppiumDriver<?> driver, ScreenShot ss, Properties props) {

		this.ss = ss;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<RemoteWebDriver>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(gc.defaultTimeOut));
		this.jse = (JavascriptExecutor) this.driver;
		((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);

	}


	/**
	 * Constructor for generic step-operations and confirmation, provisioned for
	 * instance logging
	 *
	 * @param driver (WebDriver) thread-safe WebDriver
	 * @param ss (ScreenShot) Screenshot instance
	 * @param props (Properties) properties file instance
	 * @param id (String) test instance id
	 * @param testName (String) test-method name
	 */
	public Generic(RemoteWebDriver driver, ScreenShot ss, Properties props, String id, String testName) {

		this.ss = ss;
		this.id = id;
		this.testName = testName;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<RemoteWebDriver>(this.driver).ignoring(NoSuchElementException.class)

				.withTimeout(Duration.ofSeconds(gc.defaultTimeOut));
		this.jse = (JavascriptExecutor) this.driver;

	}


	/**
	 * Attempt to locate element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey) {

		byType = processLocator(propertyKey);

		try {
			we = this.wait
					.until(new Function<RemoteWebDriver, WebElement>() {
						public WebElement apply(RemoteWebDriver drv) {
							return drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>%n");
			return null;
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to locate element within <macWait> time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey, int maxWait) {

		byType = processLocator(propertyKey, maxWait);

		double start = System.currentTimeMillis();
		try {
			we = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, WebElement>() {
						public WebElement apply(RemoteWebDriver drv) {
							return drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to locate element within <maxWait> time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey, int maxWait, int polling) {

		byType = processLocator(propertyKey, maxWait);

		double start = System.currentTimeMillis();
		try {
			we = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.pollingEvery(Duration.ofMillis(polling))
					.until(new Function<RemoteWebDriver, WebElement>() {
						public WebElement apply(RemoteWebDriver drv) {
							return drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to locate compound element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey, String replacement) {

		byType = processLocator(propertyKey, replacement);

		try {
			we = this.wait
					.until(new Function<RemoteWebDriver, WebElement>() {
						public WebElement apply(RemoteWebDriver drv) {
							return drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>%n");
			return null;
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to locate compound element within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey, String replacement, int maxWait) {

		byType = processLocator(propertyKey, replacement, maxWait);

		double start = System.currentTimeMillis();
		try {
			we = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, WebElement>() {
						public WebElement apply(RemoteWebDriver drv) {
							return drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to locate one or more element(s) within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (List<WebElement>|null)
	 */
	private List<WebElement> waitForElements(String propertyKey) {

		byType = processLocator(propertyKey);

		try {
			weArray = this.wait
					.until(new Function<RemoteWebDriver, List<WebElement>>() {
						public List<WebElement> apply(RemoteWebDriver drv) {
							return drv.findElements(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>%n");
			return null;
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}


	/**
	 * Attempt to locate one or more element(s) within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (List<WebElement>|null)
	 */
	private List<WebElement> waitForElements(String propertyKey, int maxWait) {

		byType = processLocator(propertyKey, maxWait);

		double start = System.currentTimeMillis();
		try {
			weArray = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, List<WebElement>>() {
						public List<WebElement> apply(RemoteWebDriver drv) {
							return drv.findElements(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}


	/**
	 * Attempt to locate one or more compound element(s) within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (List<WebElement>|null)
	 */
	// @Step("Wait for element.")
	private List<WebElement> waitForElements(String propertyKey, String replacement) {

		byType = processLocator(propertyKey, replacement);

		try {
			weArray = this.wait
					.until(new Function<RemoteWebDriver, List<WebElement>>() {
						public List<WebElement> apply(RemoteWebDriver drv) {
							return drv.findElements(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>%n");
			return null;
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}


	/**
	 * Attempt to locate one or more compound element(s) within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (List<WebElement>|null)
	 */
	// @Step("Wait for element.")
	private List<WebElement> waitForElements(String propertyKey, String replacement, int maxWait) {

		byType = processLocator(propertyKey, replacement, maxWait);

		double start = System.currentTimeMillis();
		try {
			weArray = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, List<WebElement>>() {
						public List<WebElement> apply(RemoteWebDriver drv) {
							return drv.findElements(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected> ");
			return null;
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}


	/**
	 * Attempt to NOT locate element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (null|WebElement)
	 */
	private WebElement waitForElementNotFound(String propertyKey) {

		byType = processLocator(propertyKey);


		double start = System.currentTimeMillis();
		try {
			this.wait
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							return !((we = drv.findElement(byType)) instanceof WebElement);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == we) {
				return null;
			} else {
				return we;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to NOT locate element within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (null|WebElement)
	 */
	private WebElement waitForElementNotFound(String propertyKey, int maxWait) {

		byType = processLocator(propertyKey, maxWait);

		double start = System.currentTimeMillis();
		try {
			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							we = null;
							return !((we = drv.findElement(byType)) instanceof WebElement);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == we) {
				//System.out.format(" <null element>");
				return null;
			} else {
				return we;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to NOT locate compound element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (null|WebElement)
	 */
	private WebElement waitForElementNotFound(String propertyKey, String replacement) {

		byType = processLocator(propertyKey, replacement);

		double start = System.currentTimeMillis();
		try {
			this.wait
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							return !((we = drv.findElement(byType)) instanceof WebElement);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == we) {
				return null;
			} else {
				return we;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to NOT locate compound element within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (null|WebElement)
	 */
	private WebElement waitForElementNotFound(String propertyKey, String replacement, int maxWait) {

		byType = processLocator(propertyKey, replacement, maxWait);

		double start = System.currentTimeMillis();
		try {
			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							return !((we = drv.findElement(byType)) instanceof WebElement);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == we) {
				return null;
			} else {
				return we;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return we;

	}


	/**
	 * Attempt to NOT locate one or more element(s) within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (null|WebElement)
	 */
	private List<WebElement> waitForElementsNotFound(String propertyKey) {

		byType = processLocator(propertyKey);

		double start = System.currentTimeMillis();
		try {
			this.wait
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							return (weArray = drv.findElements(byType)).size() == 0;
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == weArray) {
				return null;
			} else {
				return weArray;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}


	/**
	 * Attempt to NOT locate one or more element(s) within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (null|WebElement)
	 */
	private List<WebElement> waitForElementsNotFound(String propertyKey, int maxWait) {

		byType = processLocator(propertyKey, maxWait);

		double start = System.currentTimeMillis();
		try {
			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							return (weArray = drv.findElements(byType)).size() == 0;
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == weArray) {
				return null;
			} else {
				return weArray;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}


	/**
	 * Attempt to NOT locate one or more compound element(s) within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (null|WebElement)
	 */
	private List<WebElement> waitForElementsNotFound(String propertyKey, String replacement) {

		byType = processLocator(propertyKey, replacement);

		double start = System.currentTimeMillis();
		try {
			this.wait
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							return (weArray = drv.findElements(byType)).size() == 0;
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == weArray) {
				return null;
			} else {
				return weArray;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}


	/**
	 * Attempt to NOT locate one or more compound element(s) within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (null|WebElement)
	 */
	private List<WebElement> waitForElementsNotFound(String propertyKey, String replacement, int maxWait) {

		byType = processLocator(propertyKey, replacement, maxWait);

		double start = System.currentTimeMillis();
		try {
			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<RemoteWebDriver, Boolean>() {
						public Boolean apply(RemoteWebDriver drv) {
							return (weArray = drv.findElements(byType)).size() == 0;
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == weArray) {
				return null;
			} else {
				return weArray;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return weArray;

	}

	/**
	 * Retrieve and process property value as locator
	 *
	 * @param propertyKey (String) properties file key defining string
	 * @return (By)
	 */
	private By processLocator(String propertyKey) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName, command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = command[0];
		type = command[1];

		System.out.format("[LOG]: <[%s:%s] waiting for locator: %s; type: %s;>%n", this.id, this.testName, locator, type);

		byType = null;
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

		return byType;

	}


	/**
	 * Retrieve and process property value as locator with wait
	 *
	 * @param propertyKey (String) properties file key defining string
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (By)
	 */
	private By processLocator(String propertyKey, int maxWait) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName, command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = command[0];
		type = command[1];

		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for absence of locator: %s; type: %s;>", this.id, this.testName, maxWait, locator, type);

		byType = null;
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

		return byType;

	}


	/**
	 * Retrieve and process property value as compound locator
	 *
	 * @param propertyKey (String) properties file key defining string
	 * @param replacement (String) value to replace placeholder
	 * @return (By)
	 */
	private By processLocator(String propertyKey, String replacement) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName, command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = buildDynamicLocator(command[0], replacement);
		type = command[1];

		System.out.format("[LOG]: <[%s:%s] waiting for locator: %s; type: %s;>%n", this.id, this.testName, locator, type);

		byType = null;
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


		return byType;

	}


	/**
	 * Retrieve and process property value as compound locator with wait
	 *
	 * @param propertyKey (String) properties file key defining string
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (By)
	 */
	private By processLocator(String propertyKey, String replacement, int maxWait) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName,
		// command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = buildDynamicLocator(command[0], replacement);
		type = command[1];

		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for locator: %s; type: %s;>", this.id, this.testName, maxWait, locator, type);

		byType = null;
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

		return byType;

	}


	/**
	 * Asserts page title equals target value
	 *
	 * @param propertyKey (String) properties file key defining string
	 */
	@Step("Confirm page title.")
	public void confirmTitle(String propertyKey) {

		ss.assertTrue(driver.getTitle().toLowerCase().equals(getPropertyValue(propertyKey)));

	}

	/**
	 * Scrolls down by 250 pixels
	 *
	 */
	@Step("Scroll down on the window.")
	public void scrollDown() {

		JavascriptExecutor js = driver;
		js.executeScript("window.scrollBy(0,250)");

	}


	/**
	 * Scroll to an element, direction independent
	 *
	 * @param propertyKey (String) properties file key defining string
	 */
	public void smartScroll(String propertyKey) {

		this.we = confirmElementExistence(propertyKey);
		int currentY = 0;
		int currentX = 0;
		int afterY = 10000; // just some large number to start
		int afterX = 10000; // just some large number to start

		// move to element until moving no longer changes position
		do {
			currentY = this.we.getRect().y;
			currentX = this.we.getRect().x;
			jse.executeScript("arguments[0].scrollIntoView(true);", this.we);
			afterY = this.we.getRect().y;
			afterX = this.we.getRect().x;
		} while ((Math.abs(currentY - afterY) != 0) && (Math.abs(currentX - afterX) != 0));

	}


	/**
	 * Scrolls up
	 * @Param pixelX (int)
     * @Param pixelY (int)
	 */
	@Step("Scroll up on the window.")
	public void scrollUp(int pixelX, int pixelY) {

		jse.executeScript("window.scrollBy(argumets[0], -1*arguments[1])", pixelX, pixelY);  // aug[0], arg[1]

	}

	/**
	 * Scrolls down
     * @Param pixelX (int)
     * @Param pixelY (int)
	 */
	@Step("Scroll down on the window.")
	public void scrollDown(int pixelX, int pixelY) {

		jse.executeScript("window.scrollBy(argumets[0], arguments[1])", pixelX, pixelY);

	}

	/**
	 * Scrolls up by 250 pixels
	 *
	 */
	@Step("Scroll up on the window.")
	public void scrollUp() {

		jse.executeScript("window.scrollBy(0,-250)");

	}


	/**
	 * Navigates browser to target URL
	 *
	 * @param propertyKey (String) properties file key defining string
	 */
	@Step("Navigate to URL.")
	public void getUrl(String propertyKey) {

		driver.get(getPropertyValue(propertyKey));
		waitForPageLoaded();
		ss.takeScreenShot("After navigation to URL.");

	}


	/**
	 * Navigates to target URL (runtimeData)
	 *
	 * @param runtimeValue (String) value from runtimeData/GlobalConstants
	 */
	@Step("Navigate to URL.")
	public void getUrl(String[] runtimeValue) {

		driver.get(runtimeValue[0]);
		waitForPageLoaded();
		ss.takeScreenShot("After navigation to URL.");

	}


	/**
	 * Retrieves locator definition and type from WebElement property definition
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (String[]) containing locator definition and locator type
	 */
	private String[] locatorElementAndMethod(String propertyKey) {

		//System.out.format("[DEBUG]: <[%s:%s] key: %s>%n", this.id, this.testName, propertyKey);
		command = null;
		String propertyValue = null;

		//System.out.format("[DEBUG]: <[%s:%s] key found?: %b>%n", this.id, this.testName, this.props.containsKey(propertyKey));
		ss.assertTrue(props.containsKey(propertyKey), "Property key not found");
		propertyValue = this.props.getProperty(propertyKey);
		//System.out.format("[DEBUG]: <[%s:%s] key value: %s>%n", this.id, this.testName, propertyValue);
		command = propertyValue.split(gc.locatorSeparator);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName, command.length);


		return command;

	}


	/**
	 * Return String value of non-WebElement property definition
	 *
	 * @param propertyKey (String) properties file key defining string
	 * @return (String) value of property
	 */
	public String getPropertyValue(String propertyKey) {

		// System.out.format("[DEBUG]: <[%s:%s] key: %s>%n", this.id, this.testName, propertyKey);
		command = null;
		String propertyValue = null;

		ss.assertTrue(props.containsKey(propertyKey), "Property key not found");
		// System.out.format("[DEBUG]: <[%s:%s] key found?: %b>%n", this.id, this.testName, props.containsKey(propertyKey));

		propertyValue = props.getProperty(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] key value: %s>%n", this.id, this.testName, propertyValue);


		return propertyValue;

	}


	/**
	 * Inserts string into target element
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param input (String) text to enter into target element
	 */
	@Step("Input text.")
	public void sendText(String propertyKey, String input) {

		we = confirmElementExistence(propertyKey);
		we.clear();
		we.sendKeys(input);
		ss.takeScreenShot("After input");

	}


	/**
	 * Inserts string into target element
	 *
	 * @param we (WebElement) selenium text field
	 * @param input (String) text to enter into target element
	 */
	@Step("Input text.")
	public void sendText(WebElement we, String input) {

		we.clear();
		we.sendKeys(input);
		ss.takeScreenShot("After input");

	}


	/**
	 * Manually trigger a screenshot
	 *
	 * @param description (String) describing screenshot to be taken
	 */
	@Step("Take Screenshot: {0}.")
	public void takeScreenShot(String description) {

		ss.takeScreenShot(description);

	}


	/**
	 * After confirming element existence, clicks element.
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 */
	@Step("Click element.")
	public void clickElement(String propertyKey) {

		we = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] clicking element with property key: \"%s\">%n", this.id, this.testName, propertyKey);
		we.click();
		waitForPageLoaded();

	}


	/**
	 * After confirming element existence, clicks element.
	 *
	 * @param wel (WebElement) selenium web element
	 */
	@Step("Click element.")
	public void clickElement(WebElement wel) {

		System.out.format("[LOG]: <[%s:%s] clicking element>%n", this.id, this.testName);
		wel.click();
		waitForPageLoaded();

	}


	/**
	 * After confirming element existence, clicks element.
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 */
	@Step("Click element.")
	public void clickElement(String propertyKey, int timeOut) {

		we = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] clicking element with property key: \"%s\">%n", this.id, this.testName, propertyKey);
		we.click();
		waitForPageCompletelyLoaded(timeOut);

	}


	/**
	 * After confirming dynamic element existence, clicks element.
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) compound locator replacement
	 */
	@Step("Click dynamic element.")
	public void clickElement(String propertyKey, String replacement) {

		we = confirmElementExistence(propertyKey, replacement);
		System.out.format("[LOG]: <[%s:%s] clicking element with property key: \"%s\">%n", this.id, this.testName, propertyKey);
		we.click();
		waitForPageLoaded();

	}


	/**
	 * Asserts element exists, includes custom error message
	 *
	 * @param propertyKeyCustomError  (String[]) properties file key defining element locator, custom error message
	 * @return (WebElement)
	 */
	@Step("Confirm element exists.")
	public WebElement confirmElementExistence(String[] propertyKeyCustomError) {

		ss.assertTrue((we = waitForElement(propertyKeyCustomError[0])) instanceof WebElement, propertyKeyCustomError[1]);
		return we;

	}


	/**
	 * Asserts element can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (WebElement)
	 */
	@Step("Confirm element exists.")
	public WebElement confirmElementExistence(String propertyKey) {

		ss.assertTrue((we = waitForElement(propertyKey)) instanceof WebElement, "Element could not be located.");
		return we;

	}


	/**
	 * Asserts element can be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement)
	 */
	@Step("Confirm element exists within {maxWait} seconds.")
	public WebElement confirmElementExistence(String propertyKey, int maxWait) {

		ss.assertTrue((we = waitForElement(propertyKey, maxWait)) instanceof WebElement, "Element could not be located.");
		return we;

	}


	/**
	 * Asserts element can be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement)
	 */
	@Step("Confirm element exists within {maxWait} seconds.")
	public WebElement confirmElementExistence(String propertyKey, int maxWait, int polling) {

		ss.assertTrue((we = waitForElement(propertyKey, maxWait, polling)) instanceof WebElement, "Element could not be located.");
		return we;

	}


	/**
	 * Asserts compound element can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (WebElement)
	 */
	@Step("Confirm dynamic element exists.")
	public WebElement confirmElementExistence(String propertyKey, String replacement) {

		ss.assertTrue((we = waitForElement(propertyKey, replacement)) instanceof WebElement, "Element could not be located.");
		return we;

	}


	/**
	 * Asserts compound element can be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement)
	 */
	@Step("Confirm dynamic element exists within {maxWait} seconds.")
	public WebElement confirmElementExistence(String propertyKey, String replacement, int maxWait) {

		ss.assertTrue((we = waitForElement(propertyKey, replacement, maxWait)) instanceof WebElement, "Element could not be located.");
		return we;

	}


	/**
	 * Asserts one or more elements can be found within default time constraint, includes custom error message
	 *
	 * @param propertyKeyCustomError (String[]) properties file key defining element locator, custom error messsage
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist.")
	public List<WebElement> confirmElementsExistence(String[] propertyKeyCustomError) {

		ss.assertTrue((weArray = waitForElements(propertyKeyCustomError[0])).size() > 0, propertyKeyCustomError[1]);
		return weArray;

	}


	/**
	 * Asserts one or more element(s) can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist.")
	public List<WebElement> confirmElementsExistence(String propertyKey) {

		ss.assertTrue((weArray = waitForElements(propertyKey)).size() > 0, "One or more Elements could not be located.");
		return weArray;

	}


	/**
	 * Asserts one or more element(s) can be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist within {maxWait} seconds.")
	public List<WebElement> confirmElementsExistence(String propertyKey, int maxWait) {

		ss.assertTrue((weArray = waitForElements(propertyKey, maxWait)).size() > 0, "One or more Elements could not be located.");
		return weArray;

	}


	/**
	 * Asserts compound element(s) can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (List<WebElement>)
	 */
	@Step("Confirm dynamic element(s) exist.")
	public List<WebElement> confirmElementsExistence(String propertyKey, String replacement) {

		ss.assertTrue((weArray = waitForElements(propertyKey, replacement)).size() > 0, "One or more Elements could not be located.");
		return weArray;

	}


	/**
	 * Asserts one or more compound element(s) can be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (List<WebElement>)
	 */
	@Step("Confirm dynamic element(s) exist within {maxWait} seconds.")
	public List<WebElement> confirmElementsExistence(String propertyKey, String replacement, int maxWait) {

		ss.assertTrue((weArray = waitForElements(propertyKey, replacement, maxWait)).size() > 0, "One or more Elements could not be located.");
		return weArray;

	}


	/**
	 * Asserts element cannot be found within default time constraint
	 *
	 * @param propertyKeyCustomError (String[]) properties file key defining element locator, custom error message
	 */
	@Step("Confirm element does not exist.")
	public void confirmElementNonExistence(String[] propertyKeyCustomError) {

		ss.assertFalse(waitForElementNotFound(propertyKeyCustomError[0]) instanceof WebElement, propertyKeyCustomError[1]);

	}


	/**
	 * Asserts element cannot be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator


	 */
	@Step("Confirm element does not exist.")
	public void confirmElementNonExistence(String propertyKey) {

		ss.assertFalse(waitForElementNotFound(propertyKey) instanceof WebElement, "Persistent element located unexpectedly.");

	}


	/**
	 * Asserts element cannot be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 */
	@Step("Confirm element does not exist within {maxWait} seconds.")
	public void confirmElementNonExistence(String propertyKey, int maxWait) {

		ss.assertFalse(waitForElementNotFound(propertyKey, maxWait) instanceof WebElement, "Persistent element located unexpectedly.");

	}


	/**
	 * Asserts compound element cannot be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 */
	@Step("Confirm dynamic element does not exist.")
	public void confirmElementNonExistence(String propertyKey, String replacement) {

		ss.assertFalse(waitForElementNotFound(propertyKey, replacement) instanceof WebElement, "Persistent element located unexpectedly.");

	}


	/**
	 * Asserts compound element cannot be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 */
	@Step("Confirm dynamic element does not exist within {maxWait} seconds.")
	public void confirmElementNonExistence(String propertyKey, String replacement, int maxWait) {

		ss.assertFalse(waitForElementNotFound(propertyKey, replacement, maxWait) instanceof WebElement, "Persistent element located unexpectedly.");

	}


	/**
	 * Asserts element cannot be found within default time constraint, including custom error message
	 *
	 * @param propertyKeyCustomError (String[]) properties file key defining element locator, custom error message
	 */
	@Step("Confirm element(s) do not exist.")
	public void confirmElementsNonExistence(String[] propertyKeyCustomError) {

		ss.assertFalse(waitForElementsNotFound(propertyKeyCustomError[0]) instanceof List<?>, propertyKeyCustomError[1]);

	}


	/**
	 * Asserts one or more element(s) cannot be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 */
	@Step("Confirm element(s) do not exist.")
	public void confirmElementsNonExistence(String propertyKey) {

		ss.assertFalse(waitForElementsNotFound(propertyKey) instanceof List<?>, "Persistent element(s) located unexpectedly.");

	}


	/**
	 * Asserts one or more element(s) cannot be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 */
	@Step("Confirm element(s) do not exist within {maxWait} seconds.")
	public void confirmElementsNonExistence(String propertyKey, int maxWait) {

		ss.assertFalse(waitForElementsNotFound(propertyKey, maxWait) instanceof List<?>, "Persistent element(s) located unexpectedly.");

	}


	/**
	 * Asserts one or more compound element(s) cannot be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 */
	@Step("Confirm dynamic element(S) do not exist.")
	public void confirmElementsNonExistence(String propertyKey, String replacement) {

		ss.assertFalse(waitForElementsNotFound(propertyKey, replacement) instanceof List<?>, "Persistent element(s) located unexpectedly.");

	}


	/**
	 * Asserts one or more compound element(s) cannot be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @param maxWait (int) maximum time in seconds to wait for element
	 */
	@Step("Confirm dynamic element(s) do not exist within {maxWait} seconds.")
	public void confirmElementsNonExistence(String propertyKey, String replacement, int maxWait) {

		ss.assertFalse(waitForElementsNotFound(propertyKey, replacement, maxWait) instanceof List<?>, "Persistent element(s) located unexpectedly.");

	}


	/**
	 * Given a generic locator (includes placeholder string), replace placeholder
	 * string with desired string
	 *
	 * @param locator (String) properties file key defining element locator
	 * @param replacement (String) to replace placeholder
	 * @return (String) locator as rebuilt
	 */
	// @Step("Build dynamic locator.")
	private String buildDynamicLocator(String locator, String replacement) {

		return locator.replace(gc.compoundLocatorPlaceholder, replacement);

	}


	/**
	 * Waits up to 30s for the browser JavaScript engine to report standby.
	 *
	 */
	@Step("Wait for page to load.")
	public void waitForPageLoaded() {
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for page to load.>%n", this.id, this.testName, gc.defaultTimeOut);
		this.wait.until(new Function<RemoteWebDriver, Boolean>() {
			public Boolean apply(RemoteWebDriver drv) {
				return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete") ||
						((JavascriptExecutor) drv).executeScript("return document.readyState").equals("interactive"));
			}
		});

	}


	/**
	 * Waits up to timeOutInSeconds for the browser JavaScript engine to report
	 * standby.
	 *
	 * @param timeOut (int) maximum seconds to wait
	 */
	@Step("Wait for page to load.")
	public void waitForPageLoaded(int timeOut) {
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for page to load.>%n", this.id, this.testName, timeOut);
		FluentWait<RemoteWebDriver> wait = new FluentWait<RemoteWebDriver>(driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(timeOut));

		wait.until(new Function<RemoteWebDriver, Boolean>() {
			public Boolean apply(RemoteWebDriver drv) {
				return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete") ||
						((JavascriptExecutor) drv).executeScript("return document.readyState").equals("interactive"));
			}
		});
		wait = null;

	}


	/**
	 * Waits up to timeOutInSeconds for the browser JavaScript engine to report
	 * standby.
	 *
	 * @param timeOut (int) maximum seconds to wait
	 */
	@Step("Wait for page to completely load.")
	public void waitForPageCompletelyLoaded(int timeOut) {
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for page to completely load.>%n", this.id, this.testName, timeOut);
		FluentWait<RemoteWebDriver> wait = new FluentWait<RemoteWebDriver>(driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(timeOut));

		wait.until(new Function<RemoteWebDriver, Boolean>() {
			public Boolean apply(RemoteWebDriver drv) {
				return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete"));
			}
		});
		wait = null;

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target value (property key).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) option to set properties key
	 */
	@Step("Set dropdown value.")
	public void setDropdownByStaticValue(String propertyKey, String valueKey) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		String value = getPropertyValue(propertyKey);
		List<WebElement> options = sel.getOptions();
		boolean valueExists = false;

		for (WebElement we: options) {
			if (we.getAttribute("value").toLowerCase().equals(value.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", this.id, this.testName, value);
			sel.selectByValue(value);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").toLowerCase().equals(value.toLowerCase()));
			ss.takeScreenShot("Dropdown set by value.");
		} else {
			// force a descriptive fail if value not found
			ss.assertTrue(false, "Could not find value among options.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target value (runtimeData).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) runtimeData value
	 */
	@Step("Set dropdown by dynamic value.")
	public void setDropdownByDynamicValue(String propertyKey, String runtimeValue) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		List<WebElement> options = sel.getOptions();
		boolean valueExists = false;

		for (WebElement we: options) {
			if (we.getAttribute("value").toLowerCase().equals(runtimeValue.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", this.id, this.testName, runtimeValue);
			sel.selectByValue(runtimeValue);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").toLowerCase().equals(runtimeValue.toLowerCase()));
			ss.takeScreenShot("Dropdown set by value.");
		} else {
			// force a descriptive fail if value not found
			ss.assertTrue(false, "Could not find value among options.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target index (properties).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) value properties key
	 */
	@Step("Set dropdown by static index.")
	public void setDropdownByStaticIndex(String propertyKey, String valueKey) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		int index = Integer.parseInt(getPropertyValue(valueKey));

		if (index < sel.getOptions().size()) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d>%n", this.id, this.testName, index);
			String value = sel.getOptions().get(index).getAttribute("value");
			sel.selectByIndex(index);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").equals(value));
			ss.takeScreenShot("Dropdown set by index.");
		} else {
			// force a descriptive fail if index is not appropriate
			ss.assertTrue(false, "Index beyond allowable bounds.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target index (runtimeData).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeIndex (int) target option index
	 */
	@Step("Set dropdown by dynamic index.")
	public void setDropdownByDynamicIndex(String propertyKey, String runtimeIndex) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		int index = Integer.parseInt(runtimeIndex);

		if ( index < sel.getOptions().size()) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d>%n", this.id, this.testName, index);
			String value = sel.getOptions().get(index).getAttribute("value");
			sel.selectByIndex(index);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").equals(value));
			ss.takeScreenShot("Dropdown set by index.");
		} else {
			// force a descriptive fail if index is not appropriate
			ss.assertTrue(false, "Index beyond allowable bounds.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target index (int).
	 * Once set, confirms selection.
	 * <strong></strong>
	 *
	 * @param propertyKey (String) locator properties key
	 * @param index (int) target option index
	 * @deprecated NOT PREFERRED due to hard-coding dependency. use {@link #setDropdownByDynamicIndex(String, String)} where possible
	 */
	@Step("Set dropdown by dynamic[hard] index.")
	@Deprecated
	public void setDropdownByDynamicIndex(String propertyKey, int index) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));

		if ( index < sel.getOptions().size()) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d>%n", this.id, this.testName, index);
			String value = sel.getOptions().get(index).getAttribute("value");
			sel.selectByIndex(index);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").equals(value), "Expected value not found.");
			ss.takeScreenShot("Dropdown set by index[hard].");
		} else {
			// force a descriptive fail if index is not appropriate
			ss.assertTrue(false, "Index beyond allowable bounds.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target value (testReference).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) value properties key
	 */
	@Step("Set dropdown static visible text value.")
	public void setDropdownByStaticVisibleText(String propertyKey, String valueKey) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		String visibleText = getPropertyValue(valueKey);
		List<WebElement> options = sel.getOptions();
		boolean valueExists = false;

		for (WebElement we: options) {
			if (we.getText().toLowerCase().equals(visibleText.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", id, testName, visibleText);
			sel.selectByVisibleText(visibleText);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().equals(visibleText.toLowerCase()));
			ss.takeScreenShot("Dropdown set by visible text.");
		} else {
			// force a descriptive fail if no match
			ss.assertTrue(false, "Could not find exact visible text match.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target value (runtimeData).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) runtimeData value
	 */
	@Step("Set dropdown by dynamic visible text value.")
	public void setDropdownByDynamicVisibleText(String propertyKey, String runtimeValue) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		List<WebElement> options = sel.getOptions();
		boolean valueExists = false;

		for (WebElement we: options) {
			if (we.getText().toLowerCase().equals(runtimeValue.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", id, testName, runtimeValue);
			sel.selectByVisibleText(runtimeValue);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().equals(runtimeValue.toLowerCase()));
			ss.takeScreenShot("Dropdown set by visible text.");
		} else {
			// force a descriptive fail if no match
			ss.assertTrue(false, "Could not find exact visible text match.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target index,
	 * as determined by p[artial text match (properties).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) value properties key
	 */
	@Step("Set dropdown by static partial visible text value.")
	public void setDropdownByStaticPartialVisibleText(String propertyKey, String valueKey) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		List<WebElement> options = sel.getOptions();
		String value = getPropertyValue(valueKey);
		//System.out.format("[DEBUG]: <[%s:%s] comparing against value: \"%s\">%n", id, testName, value);
		int index = getIndexByPartialText(value, options);
		//System.out.format("[DEBUG]: <[%s:%s] index of match: %d>%n", id, testName, index);

		if (index >= 0) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d, by partial text: \"%s\">%n", id, testName, index, value);
			sel.selectByIndex(index);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().contains(value.toLowerCase()));
			ss.takeScreenShot("Dropdown set by partial text.");
		} else {
			// force a descriptive fail if no match
			ss.assertTrue(false, "Could not find partial visible text match.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target index,
	 * as determined by partial text match (runtimeData).
	 * Once set, confirms selection.
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) runtimeData value
	 */
	@Step("Set dropdown by dynamic partial visible text value.")
	public void setDropdownByDynamicPartialVisibleText(String propertyKey, String runtimeValue) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		List<WebElement> options = sel.getOptions();
		//System.out.format("[DEBUG]: <[%s:%s] comparing against value: \"%s\">%n", id, testName, runtimeValue);
		int index = getIndexByPartialText(runtimeValue, options);
		//System.out.format("[DEBUG]: <[%s:%s] index of match: %d>%n", id, testName, index);

		if (index >= 0) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d, by partial text: \"%s\">%n", id, testName, index, runtimeValue);
			sel.selectByIndex(index);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().contains(runtimeValue.toLowerCase()));
			ss.takeScreenShot("Dropdown set by partial text.");
		} else {
			// force a descriptive fail if no match
			ss.assertTrue(false, "Could not find partial visible text match.");
		}

	}


	/**
	 * Helper method for {@link #setDropdownByStaticPartialVisibleText(String, String)}
	 * and {@link #setDropdownByDynamicPartialVisibleText(String, String)}.
	 *
	 * Compares list element text to a known string in order to determine index of
	 * matching element.
	 *
	 * @param match (String) text to compare against select option text
	 * @param options (List<WebElement>) list of select options as web elements
	 * @return (int) index of match, or -1
	 */
	private int getIndexByPartialText(String match, List<WebElement> options) {

		for (int i = 0; i < options.size(); i++) {
			/*if (null != options.get(i)) {
				System.out.format("[DEBUG]: <[%s:%s] value extracted at index=%d: \"%s\">%n", id, testName, i, options.get(i).getText());
			}*/
			if (null != options.get(i) && options.get(i).getText().contains(match)) {
				return i;
			}
		}
		return -1;

	}


	/**
	 * Confirm the selection state of a target dropdown menu widget (runtimeData)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) comparison string
	 */
	public void confirmDynamicDropdownValue(String propertyKey, String runtimeValue) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		ss.assertTrue(sel.getFirstSelectedOption().getText().toLowerCase().equals(runtimeValue.toLowerCase()));

	}


	/**
	 * Confirm the selection state of a target dropdown menu widget (properties)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) comparison string
	 */
	public void confirmStaticDropdownValue(String propertyKey, String valueKey) {

		waitForPageLoaded();
		String value = getPropertyValue(valueKey);
		Select sel = new Select(confirmElementExistence(propertyKey));
		ss.assertTrue(sel.getFirstSelectedOption().getText().toLowerCase().equals(value.toLowerCase()));

	}


	/**
	 * Compares a given string (property key) against the text contents of
	 * a given web element (property key)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) comparison string properties key
	 */
	@Step("Confirm text value.")
	public void confirmStaticTextValue(String propertyKey, String valueKey) {

		value = getPropertyValue(valueKey);
		we = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, we.getText().replace("\n", ""), value);
		ss.assertTrue(we.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");

	}


	/**
	 * Compares a given string (runtimeData/caller derived) against the text contents of
	 * a given web element (property key)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param value (String) comparison string
	 */
	@Step("Confirm text value.")
	public void confirmDynamicTextValue(String propertyKey, String value) {

		we = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, we.getText().replace("\n", ""), value);
		ss.assertTrue(we.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");

	}


	/**
	 * Compares a given string (runtimeData/caller derived) against the text contents of
	 * a given web element (WebElement)
	 *
	 * @param we (WebElement) selenium element wrapping text
	 * @param value (String) comparison string
	 */
	@Step("Confirm text value.")
	public void confirmDynamicTextValue(WebElement we, String value) {

		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, we.getText().replace("\n", ""), value);
		ss.assertTrue(we.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");

	}


	/**
	 * Compares a given string (runtimeData) against the text contents of a given web element
	 * (property key)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public void confirmExactDynamicTextValue(String propertyKey, String runtimeValue) {

		we = confirmElementExistence(propertyKey);
		ss.assertTrue(we.getText().equals(runtimeValue));
		ss.takeScreenShot("Confirm text presence.");

	}


	/**
	 * Compares a given string (runtimeData) against the text contents of a given web element
	 * (WebElement)
	 *
	 * @param we (WebElement) selenium element wrapping text
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public void confirmExactDynamicTextValue(WebElement we, String runtimeValue) {

		//System.out.format("[DEBUG]: <[%s:%s] comparing strings [exp]\"%s\":[act]\"%s\">%n", this.id, this.testName, runtimeValue.trim(), we.getText().trim());
		ss.assertTrue(we.getText().trim().equals(runtimeValue.trim()));
		ss.takeScreenShot("Confirm text presence.");

	}


	/**
	 * Compares a given string (runtimeData) against the input text of a given input field
	 * (runtimeData)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public void confirmExactDynamicInputText(String propertyKey, String runtimeValue) {

		we = confirmElementExistence(propertyKey);
		String value = (String) this.jse.executeScript("return arguments[0].value", we);
		//String value = (String) this.jse.executeScript("return $(arguments[0]).val()", loc); // has an issue with unrecognized expression that I do not understand
		//System.out.format("[DEBUG]: <[%s:%s] value returned from javascriptexecutor: \"%s\">%n", value);
		ss.assertTrue(value.equals(runtimeValue));
		ss.takeScreenShot("Confirm text presence.");

	}


	/**
	 * Compares a given string (runtimeData) against the input text of a given input field
	 * (runtimeData)
	 *
	 * @param we (WebElement) selenium element wrapping text
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public void confirmExactDynamicInputText(WebElement we, String runtimeValue) {

		String value = (String) this.jse.executeScript("return arguments[0].value", we);
		//String value = (String) this.jse.executeScript("return $(arguments[0]).val()", loc); // has an issue with unrecognized expression that I do not understand
		//System.out.format("[DEBUG]: <[%s:%s] value returned from javascriptexecutor: \"%s\">%n", value);
		ss.assertTrue(value.equals(runtimeValue));
		ss.takeScreenShot("Confirm text presence.");

	}


	/**
	 * Given a target window title, cycle through open windows until title matches, else return false
	 *
	 * @param valueKey (String) window title for comparison
	 * @return (boolean) true if window found
	 */
	@Step("Switch to window by title.")
	public void switchToWindowByTitle(String valueKey) {

		String title;
		String currentHandle = this.driver.getWindowHandle();
		//System.out.format("[DEBUG]: <[%s:%s] current window handle: \"%s\">%n", this.id, this.testName, currentHandle);
		Set<String> handles = this.driver.getWindowHandles();

		for (String handle: handles) {
			System.out.format("[LOG]: <[%s:%s] checking window handle: \"%s\" for title match.>%n", this.id, this.testName, handle);
			if(!handle.equals(currentHandle)) {
				this.driver.switchTo().window(handle);
				waitForPageLoaded(120);
				title = this.driver.getTitle();
				//System.out.format("[DEBUG]: <[%s:%s] comparing window title to reference: \"%s:%s\">%n", this.id, this.testName, title, getPropertyValue(valueKey));
				//System.out.format("[DEBUG]: <[%s:%s] strings match?: %b>%n", this.id, this.testName, title.toLowerCase().equals(getPropertyValue(valueKey).toLowerCase().trim()));
				if (title.replace("\u2013","-").toLowerCase().equals(getPropertyValue(valueKey).toLowerCase())) {
					ss.assertTrue(true);
					return;
				}
			}
		}
		ss.assertTrue(false, "Could not find matching window title.");

	}


	/**
	 * Check for broken image.
	 */
	@Step("Check for broken image.")
	public void checkForBrokenImage(String propertyKey) {

		System.out.format("[LOG]: <[%s:%s] checking for broken image.>%n", this.id, testName);
		we = confirmElementExistence(propertyKey);
		imageWidthNotZero = false;
		imageWidthNotZero = (boolean) ((JavascriptExecutor) this.driver).executeScript("return (arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0)", we);
		ss.assertTrue(imageWidthNotZero, "Image is broken");

	}


	/**
	 * Populates text list from multi-element locator, compares against known text list (CSV, property key)
	 */
	@Step("Confirm multi-element text.")
	public void confirmMultiElementText(String propertyKey, String valueKey) {

		List<String> foundList = new ArrayList<String>();
		List<String> expectedList = new ArrayList<String>();
		List<WebElement> weArray = new ArrayList<WebElement>();
		String[] values = null;

		weArray = confirmElementsExistence(propertyKey);
		values = getPropertyValue(valueKey).split(",");
		expectedList = Arrays.asList(values);

		for (WebElement we: weArray) {
			foundList.add(we.getText());
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (String s: expectedList) {
			sb.append(s+",");
		}
		sb.append("}:{");
		for (String s: foundList) {
			sb.append(s+",");
		}
		sb.append("}");

		System.out.format("[LOG]: <[%s:%s] comparing string lists: [%s]>%n", this.id, this.testName, sb.toString());
		ss.assertTrue(expectedList.equals(foundList), "Lists differ.");
		ss.takeScreenShot("Lists match.");

	}


	/**
	 * Force fail a test
	 */
	@Step("Force a FAIL.")
	public void failTest() {

		ss.assertTrue(false, "This assertion intended to FAIL.");

	}

}

