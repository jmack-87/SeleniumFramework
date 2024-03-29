package com.ibm.ciclan.Base;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;

/**
 *
 * @author Jerimiah Mack
 *
 */
public class MobileGeneric extends TestBase {

	private AppiumDriver driver;
	private FluentWait<AppiumDriver> wait;
	private Properties props;
	private By byType = null;
	private String[] command;
	private String errorCondition = "";
	private String value;
	private WebElement me = null;
	private List<WebElement> meArray = new ArrayList<WebElement>();


	protected String id = "unknown";
	protected String testName = "unknown";
	protected JavascriptExecutor jse;

	private MobileScreenShot ss;


	/**
	 * Minimum constructor for generic step-operations and confirmation.
	 *
	 * @param driver thread-safe WebDriver
	 * @param ss MobileScreenShot instance
	 * @param props properties file instance
	 */
	public MobileGeneric(AppiumDriver driver, MobileScreenShot ss, Properties props) {

		this.ss = ss;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<AppiumDriver>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(gc.defaultTimeOut))
				.pollingEvery(Duration.ofMillis(gc.defaultPollingRate));
		this.jse = (JavascriptExecutor) this.driver;

	}


	/**
	 * Constructor for generic step-operations and confirmation, provisioned for instance logging
	 *
	 * @param driver thread-safe WebDriver
	 * @param ss MobileScreenShot instance
	 * @param props properties file instance
	 */
	public MobileGeneric(AppiumDriver driver, MobileScreenShot ss, Properties props, String id, String testName) {

		this.ss = ss;
		this.id = id;
		this.testName = testName;
		this.driver = driver;
		this.props = props;
		this.wait = new FluentWait<AppiumDriver>(this.driver)
				.ignoring(NoSuchElementException.class)
				.withTimeout(Duration.ofSeconds(gc.defaultTimeOut))
				.pollingEvery(Duration.ofMillis(gc.defaultPollingRate));
		this.jse = (JavascriptExecutor) this.driver;

	}

	/**
	 * Attempt to locate element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, "");
		me = null;

		try {
			me = this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, WebElement>() {
						public WebElement apply(AppiumDriver drv) {
							return (WebElement) drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 * Attempt to locate element within <macWait> time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey, int maxWait) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, maxWait, "");
		me = null;

		try {
			me = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, WebElement>() {
						public WebElement apply(AppiumDriver drv) {
							return (WebElement) drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 * Attempt to locate element within <maxWait> time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey, int maxWait, int polling) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, maxWait, "");
		me = null;

		try {
			me = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.pollingEvery(Duration.ofMillis(polling))
					.until(new Function<AppiumDriver, WebElement>() {
						public WebElement apply(AppiumDriver drv) {
							return (WebElement) drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 * Attempt to locate compound element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(String propertyKey, String replacement) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, "");
		me = null;

		try {
			me = this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, WebElement>() {
						public WebElement apply(AppiumDriver drv) {
							return (WebElement) drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

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

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, maxWait, "");
		me = null;

		try {
			me = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, WebElement>() {
						public WebElement apply(AppiumDriver drv) {
							return (WebElement) drv.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 *
	 * @param w (WebElement) parent element containing target element
	 * @param propertyKey target element (properties file)
	 * @return (WebElement|null)
	 */
	private WebElement waitForElement(WebElement m, String propertyKey) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, "");
		me = null;

		try {
			me = this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, WebElement>() {
						public WebElement apply(AppiumDriver drv) {
							return m.findElement(byType);
						}
					});
		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 * Attempt to locate one or more element(s) within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (List<WebElement>|null)
	 */
	private List<WebElement> waitForElements(String propertyKey) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, "");
		meArray.clear();

		try {

			meArray = this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, List<WebElement>>() {
						public List<WebElement> apply(AppiumDriver drv) {
							return (List<WebElement>) drv.findElements(byType);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

	}


	/**
	 * Attempt to locate one or more element(s) within default time constraint
	 *
	 * @param w (WebElement) reference element
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (List<WebElement>|null)
	 */
	private List<WebElement> waitForElements(WebElement m, String propertyKey) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, "");
		meArray.clear();

		try {

			meArray = this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, List<WebElement>>() {
						public List<WebElement> apply(AppiumDriver drv) {
							return m.findElements(byType);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

	}


	/**
	 * Attempt to locate one or more element(s) within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (List<WebElement>|null)
	 */
	private List<WebElement> waitForElements(String propertyKey, int maxWait) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, maxWait, "");
		meArray.clear();

		try {

			meArray = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, List<WebElement>>() {
						public List<WebElement> apply(AppiumDriver drv) {
							return (List<WebElement>) drv.findElements(byType);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

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

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, "");
		meArray.clear();

		try {

			meArray = this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, List<WebElement>>() {
						public List<WebElement> apply(AppiumDriver drv) {
							return (List<WebElement>) drv.findElements(byType);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

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

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, maxWait, "");
		meArray.clear();

		try {

			meArray = this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, List<WebElement>>() {
						public List<WebElement> apply(AppiumDriver drv) {
							return (List<WebElement>) drv.findElements(byType);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");
			return null;

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

	}


	/**
	 * Attempt to NOT locate element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (null|WebElement)
	 */
	private WebElement waitForElementNotFound(String propertyKey) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, "absence of ");
		meArray.clear();

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return !((me = (WebElement) drv.findElement(byType)) instanceof WebElement);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == me) {
				return null;

			} else {
				return me;
			}
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 * Attempt to NOT locate element within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (null|WebElement)
	 */
	private WebElement waitForElementNotFound(String propertyKey, int maxWait) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, maxWait, "absence of ");
		meArray.clear();

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							me = null;
							return !((me = (WebElement) drv.findElement(byType)) instanceof WebElement);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected> ");
			if (null == me) {

				return null;
			} else {
				return me;
			}

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 * Attempt to NOT locate compound element within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (null|WebElement)
	 */
	private WebElement waitForElementNotFound(String propertyKey, String replacement) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, "absence of ");
		me = null;

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return !((me = (WebElement) drv.findElement(byType)) instanceof WebElement);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == me) {
				return null;
			} else {
				return me;
			}

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

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

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, maxWait, "absence of ");
		me = null;

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return !((me = (WebElement) drv.findElement(byType)) instanceof WebElement);
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == me) {
				return null;
			} else {
				return me;
			}

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return me;

	}


	/**
	 * Attempt to NOT locate one or more element(s) within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (null|WebElement)
	 */
	private List<WebElement> waitForElementsNotFound(String propertyKey) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, "absence of ");
		meArray.clear();

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return (meArray = (List<WebElement>) drv.findElements(byType)).size() == 0;
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == meArray) {
				return null;
			} else {
				return meArray;
			}

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

	}


	/**
	 * Attempt to NOT locate one or more element(s) within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param maxWait (int) maximum time in seconds to wait for element
	 * @return (null|WebElement)
	 */
	private List<WebElement> waitForElementsNotFound(String propertyKey, int maxWait) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, maxWait, "absence of ");
		meArray.clear();

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return (meArray = (List<WebElement>) drv.findElements(byType)).size() == 0;
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == meArray) {
				return null;
			} else {
				return meArray;
			}

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

	}


	/**
	 * Attempt to NOT locate one or more compound element(s) within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param replacement (String) value to replace placeholder
	 * @return (null|WebElement)
	 */
	private List<WebElement> waitForElementsNotFound(String propertyKey, String replacement) {

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, "absence of ");
		meArray.clear();

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return (meArray = (List<WebElement>) drv.findElements(byType)).size() == 0;
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == meArray) {
				return null;
			} else {
				return meArray;
			}

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

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

		double start = System.currentTimeMillis();
		byType = processLocator(propertyKey, replacement, maxWait, "absence of ");
		meArray.clear();

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) maxWait))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							meArray = (List<WebElement>) drv.findElements(byType);
							return meArray.size() == 0;
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: expected>");
			if (null == meArray) {
				return null;
			} else {
				return meArray;
			}

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

		// System.out.format("[DEBUG]: <[%s:%s] found %s>%n", this.id, this.testName, we);
		return meArray;

	}


	/**
	 * Retrieve and process property value as locator
	 *
	 * @param propertyKey (String) properties file key defining string
	 * @return (By)
	 */
	private By processLocator(String propertyKey, String posNeg) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName, command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = command[0];
		type = command[1];

		//System.out.format("[LOG]: <[%s:%s] waiting for %slocator: %s; type: %s;>", this.id, this.testName, posNeg, locator, type);
		System.out.format("[LOG]: <[%s:%s] waiting for %slocator: %s;>", this.id, this.testName, posNeg, propertyKey);

		byType = null;
		switch (type.toLowerCase()) {
			case "xpath":
				byType = AppiumBy.xpath(locator);
				break;
			case "css":
				byType = AppiumBy.cssSelector(locator);
				break;
			case "id":
				byType = AppiumBy.id(locator);
				break;
			case "accessibilityid":
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
				break;
			case "androidselector":
				byType =  (AppiumBy) AppiumBy.androidUIAutomator(locator);
				break;
			case "tag":
				byType =  AppiumBy.tagName(locator);
				break;
			default:
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
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
	private By processLocator(String propertyKey, int maxWait, String posNeg) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName, command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = command[0];
		type = command[1];

		//System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for %slocator: %s; type: %s;>", this.id, this.testName, maxWait, posNeg, locator, type);
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for %slocator: %s;>", this.id, this.testName, maxWait, posNeg, propertyKey);

		byType = null;
		switch (type.toLowerCase()) {
			case "xpath":
				byType = AppiumBy.xpath(locator);
				break;
			case "css":
				byType = AppiumBy.cssSelector(locator);
				break;
			case "id":
				byType = AppiumBy.id(locator);
				break;
			case "accessibilityid":
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
				break;
			case "androidselector":
				byType =  (AppiumBy) AppiumBy.androidUIAutomator(locator);
				break;
			case "tag":
				byType =  AppiumBy.tagName(locator);
				break;
			default:
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
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
	private By processLocator(String propertyKey, String replacement, String posNeg) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName, command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = buildDynamicLocator(command[0], replacement);
		type = command[1];

		//System.out.format("[LOG]: <[%s:%s] waiting for %slocator: %s; type: %s;>", this.id, this.testName, posNeg, locator, type);
		System.out.format("[LOG]: <[%s:%s] waiting for %slocator: %s;>", this.id, this.testName, posNeg, propertyKey);

		byType = null;
		switch (type.toLowerCase()) {
			case "xpath":
				byType = AppiumBy.xpath(locator);
				break;
			case "css":
				byType = AppiumBy.cssSelector(locator);
				break;
			case "id":
				byType = AppiumBy.id(locator);
				break;
			case "accessibilityid":
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
				break;
			case "androidselector":
				byType =  (AppiumBy) AppiumBy.androidUIAutomator(locator);
				break;
			case "tag":
				byType =  AppiumBy.tagName(locator);
				break;
			default:
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
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
	private By processLocator(String propertyKey, String replacement, int maxWait, String posNeg) {

		String locator;
		String type;

		command = locatorElementAndMethod(propertyKey);
		// System.out.format("[DEBUG]: <[%s:%s] command length: %d>%n", this.id, this.testName,
		// command.length);

		errorCondition = String.format("[DEBUG]: <[%s:%s] no such element: %s>%n", this.id, this.testName, command[0]);
		ss.assertTrue(command.length == 2, errorCondition);

		locator = buildDynamicLocator(command[0], replacement);
		type = command[1];

		//System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for %slocator: %s; type: %s;>", this.id, this.testName, maxWait, posNeg, locator, type);
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for %slocator: %s;>", this.id, this.testName, maxWait, posNeg, propertyKey);

		byType = null;
		switch (type.toLowerCase()) {
			case "xpath":
				byType = AppiumBy.xpath(locator);
				break;
			case "css":
				byType = AppiumBy.cssSelector(locator);
				break;
			case "id":
				byType = AppiumBy.id(locator);
				break;
			case "accessibilityid":
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
				break;
			case "androidselector":
				byType =  (AppiumBy) AppiumBy.androidUIAutomator(locator);
				break;
			case "tag":
				byType =  AppiumBy.tagName(locator);
				break;
			default:
				byType = (AppiumBy) AppiumBy.accessibilityId(locator);
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
	 * Scrolls down
	 *
	 */
	@Step("Scroll down on the window.")
	public void scrollDown() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)");

	}


	/**
	 * Scroll to an element, direction independent
	 *
	 * @param propertyKey (String) properties file key defining string
	 */
	public WebElement smartScroll(String propertyKey) {

		this.me = confirmElementExistence(propertyKey);
		int currentY = 0;
		int currentX = 0;
		int afterY = 100000;
		int afterX = 100000;

		System.out.format("[LOG]: <[%s:%s] scrolling to element: %s; ", this.id, this.testName, propertyKey);
		// move to element until moving no longer changes position. in general, the move is instantaneous
		do {
			currentY = (int) (long) this.jse.executeScript("return window.pageYOffset;");
			currentX = (int) (long) this.jse.executeScript("return window.pageXOffset;");
			System.out.format("(%d,%d) -> ", currentX, currentY);

			this.jse.executeScript("return arguments[0].scrollIntoView(true);", this.me);

			afterY = (int) (long) this.jse.executeScript("return window.pageYOffset;");
			afterX = (int) (long) this.jse.executeScript("return window.pageXOffset;");
			System.out.format("(%d,%d)", afterX, afterY);
		} while ((Math.abs(currentY - afterY) != 0) && (Math.abs(currentX - afterX) != 0));

		System.out.format(";>%n" );
		return this.me;

	}


	/**
	 * Scroll to an element, direction independent
	 *
	 * @param w (WebElement)
	 */
	public WebElement smartScroll(WebElement m) {

		int currentY = 0;
		int currentX = 0;
		int afterY = 100000;
		int afterX = 100000;

		System.out.format("[LOG]: <[%s:%s] scrolling to element: ", this.id, this.testName);
		// move to element until moving no longer changes position. in general, the move is instantaneous
		do {
			currentY = (int) (long) this.jse.executeScript("return window.pageYOffset;");
			currentX = (int) (long) this.jse.executeScript("return window.pageXOffset;");
			System.out.format("(%d,%d) -> ", currentX, currentY);

			this.jse.executeScript("return arguments[0].scrollIntoView(true);", m);

			afterY = (int) (long) this.jse.executeScript("return window.pageYOffset;");
			afterX = (int) (long) this.jse.executeScript("return window.pageXOffset;");
			System.out.format("(%d,%d)", afterX, afterY);
		} while ((Math.abs(currentY - afterY) != 0) && (Math.abs(currentX - afterX) != 0));

		System.out.format(";>%n" );
		return m;

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
	 * Scrolls up
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
		ss.assertTrue(props.containsKey(propertyKey), "Property key \""+propertyKey+"\" not found");
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

		ss.assertTrue(props.containsKey(propertyKey), "Property key \""+propertyKey+"\" not found");
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

		me = confirmElementExistence(propertyKey);
		//me.click();
		me.clear();
		me.sendKeys(input);
		ss.takeScreenShot("After input");

	}


	/**
	 * Inserts string into target element
	 *
	 * @param me (WebElement) selenium text field
	 * @param input (String) text to enter into target element
	 */
	@Step("Input text.")
	public void sendText(WebElement me, String input) {

		//me.click();
		me.clear();
		me.sendKeys(input);
		ss.takeScreenShot("After input");

	}


	/**
	 * Inserts string into target element
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param input (String) text to enter into target element
	 * @param takeSS (boolean) trigger to take a screenshot
	 */
	@Step("Input text.")
	public void sendText(String propertyKey, String input, boolean takeSS) {

		me = confirmElementExistence(propertyKey);
		//me.click();
		me.clear();
		me.sendKeys(input);

		if (takeSS) {
			ss.takeScreenShot("After input");
		}

	}


	/**
	 * Inserts string into target element
	 *
	 * @param we (WebElement) selenium text field
	 * @param input (String) text to enter into target element
	 * @param takeSS (boolean) trigger to take a screenshot
	 */
	@Step("Input text.")
	public void sendText(WebElement me, String input, boolean takeSS) {

		me.clear();
		me.sendKeys(input);

		if (takeSS) {
			ss.takeScreenShot("After input");
		}

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

		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] clicking element with property key: \"%s\">%n", this.id, this.testName, propertyKey);
		me.click();

	}


	/**
	 * After confirming element existence, clicks element.
	 *
	 * @param wel (WebElement) selenium web element
	 */
	@Step("Click element.")
	public void clickElement(WebElement me) {

		System.out.format("[LOG]: <[%s:%s] clicking element>%n", this.id, this.testName);
		me.click();
		waitForPageLoaded();

	}


	/**
	 * After confirming element existence, clicks element.
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 */
	@Step("Click element.")
	public void clickElement(String propertyKey, int timeOut) {

		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] clicking element with property key: \"%s\">%n", this.id, this.testName, propertyKey);
		me.click();
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

		me = confirmElementExistence(propertyKey, replacement);
		System.out.format("[LOG]: <[%s:%s] clicking element with property key: \"%s\">%n", this.id, this.testName, propertyKey);
		me.click();
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

		ss.assertTrue((me = waitForElement(propertyKeyCustomError[0])) instanceof WebElement, propertyKeyCustomError[1]);
		return me;

	}


	/**
	 * Asserts element can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (WebElement)
	 */
	@Step("Confirm element exists.")
	public WebElement confirmElementExistence(String propertyKey) {

		ss.assertTrue((me = waitForElement(propertyKey)) instanceof WebElement, "Element could not be located.");
		return me;

	}


	/**
	 * Asserts element can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (WebElement)
	 */
	@Step("Confirm element exists.")
	public WebElement confirmElementExistenceOrNone(String propertyKey, int maxWait) {

		return me = waitForElement(propertyKey, maxWait);

	}

	/**
	 * Asserts element can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (WebElement)
	 */
	@Step("Confirm element exists.")
	public WebElement confirmElementExistenceOrNone(String propertyKey, String replacement, int maxWait) {

		return me = waitForElement(propertyKey, replacement, maxWait);

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

		ss.assertTrue((me = waitForElement(propertyKey, maxWait)) instanceof WebElement, "Element could not be located.");
		return me;

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

		ss.assertTrue((me = waitForElement(propertyKey, maxWait, polling)) instanceof WebElement, "Element could not be located.");
		return me;

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

		ss.assertTrue((me = waitForElement(propertyKey, replacement)) instanceof WebElement, "Element could not be located.");
		return me;

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

		ss.assertTrue((me = waitForElement(propertyKey, replacement, maxWait)) instanceof WebElement, "Element could not be located.");
		return me;

	}


	/**
	 * Asserts element can be found relative to parent element
	 *
	 * @param parentElement (WebElement)
	 * @param propertyKey (String)
	 * @return (WebElement)
	 */
	@Step("Confirm dynamic element exists within 30 seconds.")
	public WebElement confirmElementExistence(WebElement parentElement, String propertyKey) {

		ss.assertTrue((me = waitForElement(parentElement, propertyKey)) instanceof WebElement, "Element could not be located.");
		return me;

	}


	/**
	 * Asserts one or more elements can be found within default time constraint, includes custom error message
	 *
	 * @param propertyKeyCustomError (String[]) properties file key defining element locator, custom error messsage
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist.")
	public List<WebElement> confirmElementsExistence(String[] propertyKeyCustomError) {

		ss.assertTrue(null != (meArray = waitForElements(propertyKeyCustomError[0])), propertyKeyCustomError[1]);
		int size = meArray.size();
		ss.assertTrue(size > 0, propertyKeyCustomError[1]);
		return meArray;

	}


	/**
	 * Asserts one or more element(s) can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist.")
	public List<WebElement> confirmElementsExistence(String propertyKey) {

		ss.assertTrue(null != (meArray = waitForElements(propertyKey)), "One or more Elements could not be located.");
		int size = meArray.size();
		ss.assertTrue(size > 0, "One or more Elements could not be located.");
		return meArray;

	}


	/**
	 * Asserts zero or more element(s) can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist.")
	public List<WebElement> confirmElementsExistenceOrNone(String propertyKey) {

		ss.assertTrue(null != (meArray = waitForElements(propertyKey)), "One or more Elements could not be located.");
		int size = meArray.size();
		ss.assertTrue(size >= 0, "One or more Elements could not be located.");
		return meArray;

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

		ss.assertTrue(null != (meArray = waitForElements(propertyKey, maxWait)), "One or more Elements could not be located.");
		int size = meArray.size();
		ss.assertTrue(size > 0, "One or more Elements could not be located.");
		return meArray;

	}


	/**
	 * Asserts one or more element(s) can be found within time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @param w (WebElement)
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist within {maxWait} seconds.")
	public List<WebElement> confirmElementsExistence(WebElement m, String propertyKey) {

		ss.assertTrue(null != (meArray = waitForElements(m, propertyKey)), "One or more Elements could not be located.");
		int size = meArray.size();
		ss.assertTrue(size > 0, "One or more Elements could not be located.");
		return meArray;

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

		ss.assertTrue(null != (meArray = waitForElements(propertyKey, replacement)), "One or more Elements could not be located.");
		int size = meArray.size();
		ss.assertTrue(size > 0, "One or more Elements could not be located.");
		return meArray;

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

		ss.assertTrue(null != (meArray = waitForElements(propertyKey, replacement, maxWait)), "One or more Elements could not be located.");
		int size = meArray.size();
		ss.assertTrue(size > 0, "One or more Elements could not be located.");
		return meArray;

	}


	/**
	 * Asserts zero or more element(s) can be found within default time constraint
	 *
	 * @param propertyKey (String) properties file key defining element locator
	 * @return (List<WebElement>)
	 */
	@Step("Confirm element(s) exist.")
	public List<WebElement> confirmElementsExistenceOrNone(WebElement m, String propertyKey) {

		ss.assertTrue(null != (meArray = waitForElements(m, propertyKey)), "One or more Elements could not be located.");
		int size = meArray.size();
		ss.assertTrue(size >= 0, "One or more Elements could not be located.");
		return meArray;

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

		return locator.replaceAll(gc.compoundLocatorPlaceholder, replacement);

	}


	/**
	 * Waits up to 30s for the browser JavaScript engine to report standby.
	 *
	 */
	@Step("Wait for page to load.")
	public void waitForPageLoaded() {

		double start = System.currentTimeMillis();
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for page to load.>", this.id, this.testName, gc.defaultTimeOut);

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete") ||
									((JavascriptExecutor) drv).executeScript("return document.readyState").equals("interactive") );
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

	}


	/**
	 * Waits up to timeOut seconds for the browser JavaScript engine to report standby.

	 *
	 * @param timeOut (int) maximum seconds to wait
	 */
	@Step("Wait for page to load.")
	public void waitForPageLoaded(int timeOut) {





		double start = System.currentTimeMillis();
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for page to load.>", this.id, this.testName, timeOut);

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) timeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete") ||
									((JavascriptExecutor) drv).executeScript("return document.readyState").equals("interactive") );
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

	}


	/**
	 * Waits up to timeOut seconds for the browser JavaScript engine to report completed.
	 */
	@Step("Wait for page to completely load.")
	public void waitForPageCompletelyLoaded() {

		double start = System.currentTimeMillis();
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for page to completely load.>", this.id, this.testName, gc.defaultTimeOut);

		try {
			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete") );
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

	}


	/**
	 * Waits up to timeOut seconds for the browser JavaScript engine to report completed.
	 *
	 * @param timeOut (int) maximum seconds to wait
	 */
	@Step("Wait for page to completely load.")
	public void waitForPageCompletelyLoaded(int timeOut) {





		double start = System.currentTimeMillis();
		System.out.format("[LOG]: <[%s:%s] waiting up to %d seconds for page to completely load.>", this.id, this.testName, timeOut);

		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) timeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete") );
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

	}


	/**
	 * Waits up to timeOut seconds for the browser JavaScript engine to report completed.
	 *
	 * @param customMessage (String) custom logging message
	 */
	@Step("Wait for page to completely load.")
	public void waitForPageCompletelyLoaded(String customMessage) {

		double start = System.currentTimeMillis();
		System.out.format("[LOG]: <[%s:%s] %s>", this.id, this.testName, customMessage);

		try {
			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return ( ((JavascriptExecutor) drv).executeScript("return document.readyState").equals("complete") );
						}
					});

		} catch (TimeoutException to) {
			System.out.format(" <timed out: unexpected>");

		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

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
		String value = getPropertyValue(valueKey);
		List<WebElement> options = sel.getOptions();
		boolean valueExists = false;

		for (WebElement w: options) {
			if (w.getAttribute("value").toLowerCase().equals(value.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", this.id, this.testName, value);
			sel.selectByValue(value);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").toLowerCase().equals(value.toLowerCase()), "Unable to confirm selection.");
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

		for (WebElement w: options) {
			if (w.getAttribute("value").toLowerCase().equals(runtimeValue.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", this.id, this.testName, runtimeValue);
			sel.selectByValue(runtimeValue);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").toLowerCase().equals(runtimeValue.toLowerCase()), "Unable to confirm selection.");
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
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").equals(value), "Unable to confirm selection.");
			ss.takeScreenShot("Dropdown set by index.");
		} else {
			// force a descriptive fail if index is not appropriate
			ss.assertTrue(false, "Index beyond allowable bounds.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target index (properties).
	 * Once set, confirms selection.
	 *
	 * @param w (WebElement)
	 * @param valueKey (String)
	 */
	@Step("Set dropdown by static index.")
	public void setDropdownByStaticIndex(WebElement m, String valueKey) {

		waitForPageLoaded();
		Select sel = new Select(m);
		int index = Integer.parseInt(getPropertyValue(valueKey));

		if (index < sel.getOptions().size()) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d>%n", this.id, this.testName, index);
			String value = sel.getOptions().get(index).getAttribute("value");
			sel.selectByIndex(index);
			ss.assertTrue(new Select(m).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(m).getFirstSelectedOption().getAttribute("value").equals(value), "Unable to confirm selection.");
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
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").equals(value), "Unable to confirm selection.");
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
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getAttribute("value").equals(value), "Unable to confirm selection.");
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

		for (WebElement w: options) {
			if (w.getText().toLowerCase().equals(visibleText.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", id, testName, visibleText);
			sel.selectByVisibleText(visibleText);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().equals(visibleText.toLowerCase()), "Unable to confirm selection.");
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

		for (WebElement w: options) {
			if (w.getText().toLowerCase().equals(runtimeValue.toLowerCase())) {
				valueExists = true;
				break;
			}
		}

		if (valueExists) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to: \"%s\">%n", id, testName, runtimeValue);
			sel.selectByVisibleText(runtimeValue);
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().equals(runtimeValue.toLowerCase()), "Unable to confirm selection.");
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
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().contains(value.toLowerCase()), "Unable to confirm selection.");
			ss.takeScreenShot("Dropdown set by partial text.");
		} else {
			// force a descriptive fail if no match
			ss.assertTrue(false, "Could not find partial visible text match.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target index,
	 * as determined by partial text match (properties).
	 * Once set, confirms selection.
	 *
	 * @param w (WebElement) derived element
	 * @param valueKey (string) value properties key
	 */
	@Step("Set dropdown by static partial visible text value.")
	public void setDropdownByStaticPartialVisibleText(WebElement m, String valueKey) {

		waitForPageLoaded();
		Select sel = new Select(m);
		List<WebElement> options = sel.getOptions();
		String value = getPropertyValue(valueKey);
		//System.out.format("[DEBUG]: <[%s:%s] comparing against value: \"%s\">%n", id, testName, value);
		int index = getIndexByPartialText(value, options);
		//System.out.format("[DEBUG]: <[%s:%s] index of match: %d>%n", id, testName, index);

		if (index >= 0) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d, by partial text: \"%s\">%n", id, testName, index, value);
			sel.selectByIndex(index);
			ss.assertTrue(new Select(m).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(m).getFirstSelectedOption().getText().toLowerCase().contains(value.toLowerCase()), "Unable to confirm selection.");
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
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(confirmElementExistence(propertyKey)).getFirstSelectedOption().getText().toLowerCase().contains(runtimeValue.toLowerCase()), "Unable to confirm selection.");
			ss.takeScreenShot("Dropdown set by partial text.");
		} else {
			// force a descriptive fail if no match
			ss.assertTrue(false, "Could not find partial visible text match.");
		}

	}


	/**
	 * Waits for dropdown to be found. Once found, sets dropdown to target containing target value,
	 * as determined by partial text match (runtimeData).
	 * Once set, confirms selection.
	 *
	 * @param w (WebElement)
	 * @param runtimeValue (String)
	 */
	@Step("Set dropdown by dynamic partial visible text value.")
	public void setDropdownByDynamicPartialVisibleText(WebElement m, String runtimeValue) {

		waitForPageLoaded();
		Select sel = new Select(m);
		List<WebElement> options = sel.getOptions();
		//System.out.format("[DEBUG]: <[%s:%s] comparing against value: \"%s\">%n", id, testName, runtimeValue);
		int index = getIndexByPartialText(runtimeValue, options);
		//System.out.format("[DEBUG]: <[%s:%s] index of match: %d>%n", id, testName, index);

		if (index >= 0) {
			System.out.format("[LOG]: <[%s:%s] setting dropdown value to index: %d, by partial text: \"%s\">%n", id, testName, index, runtimeValue);
			sel.selectByIndex(index);
			ss.assertTrue(new Select(m).getAllSelectedOptions().size() == 1, "Multiple selections unexpected.");
			ss.assertTrue(new Select(m).getFirstSelectedOption().getText().toLowerCase().contains(runtimeValue.toLowerCase()), "Unable to confirm selection.");
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
	 * Compares a given string (property key) against the text contents of
	 * a given web element (property key)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) comparison string
	 * @return (WebElement)
	 */
	@Step("Confirm text value.")
	public WebElement confirmStaticTextValue(String propertyKey, String valueKey) {

		value = getPropertyValue(valueKey);
		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, me.getText().replace("\n", ""), value);
		ss.assertTrue(me.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compare a give string (properties) against the contents of a given dynamic web element (properties)
	 *
	 * @param propertyKey (String) compound locator properties key
	 * @param replacementKey (String) replacement text properties key
	 * @param valueKey (String) comparison value text properties key
	 */
	@Step("Confirm text value.")
	public WebElement confirmStaticTextValue(String propertyKey, String replacementKey, String valueKey) {

		me = confirmElementExistence(propertyKey, getPropertyValue(replacementKey));
		ss.assertTrue(me.getText().trim().toLowerCase().contains(getPropertyValue(valueKey).trim().toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compares a given string (property key) against the text contents of
	 * a given web element (property key)
	 *
	 * @param w (WebElement)
	 * @param valueKey (String) comparison string
	 * @return (WebElement)
	 */
	@Step("Confirm text value.")
	public WebElement confirmStaticTextValue(WebElement m, String valueKey) {

		value = getPropertyValue(valueKey);
		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, m.getText().replace("\n", ""), value);
		ss.assertTrue(m.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return m;

	}

	/**
	 * Compares a given string (property key) against the text contents of
	 * a given web element (property key)
	 *
	 * @param w (WebElement)
	 * @param valueKeys (String) comparison string
	 * @return (WebElement)
	 */
	@Step("Confirm text value.")
	public WebElement confirmStaticTextValues(WebElement m, List<String> valueKeys) {

		List<String> values = new ArrayList<String>();

		for (String s: valueKeys) {
			values.add(getPropertyValue(s));
		}

		System.out.format("[LOG]: <[%s:%s] comparing element text matches list: \"%s:%s\">%n", this.id, this.testName, m.getAttribute("innerHTML"), String.join(",", values));
		ss.assertTrue(elementContentInList(m.getAttribute("innerHTML"), values), "Could not confirm a suitable Process Description.");

		ss.takeScreenShot("Confirm text presence.");
		return m;

	}


	/**
	 * Helper method for {@link #confirmStaticTextValues(WebElement, List)}.
	 * Compares singular string against string list contents.
	 *
	 * @param search (String) string to compare against list values
	 * @param reference (List<String>) list of potential matches
	 * @return (boolean) search string found in list?
	 */
	private boolean elementContentInList(String search, List<String> reference) {

		for (String s: reference) {
			if (s.toLowerCase().contains(search.toLowerCase())) {
				return true;
			}
		}
		return false;

	}


	/**
	 * Compares a given string (runtimeData/caller derived) against the text contents of
	 * a given web element (property key)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param value (String) comparison string
	 */
	@Step("Confirm text value.")
	public WebElement confirmDynamicTextValue(String propertyKey, String value) {

		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, me.getText().replace("\n", ""), value);
		ss.assertTrue(me.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compare a give string (runtimeData) against the contents of a given dynamic web element (properties)
	 *
	 * @param propertyKey (String) compound locator properties key
	 * @param replacementKey (String) replacement text properties key
	 * @param value (String) comparison value text
	 */
	@Step("Confirm text value.")
	public WebElement confirmDynamicTextValue(String propertyKey, String replacementKey, String value) {

		me = confirmElementExistence(propertyKey, getPropertyValue(replacementKey));
		ss.assertTrue(me.getText().trim().toLowerCase().contains(value.trim().toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compare a give string (runtimeData) against the contents of a given dynamic web element (runtimeData)
	 *
	 * @param propertyKey (String) compound locator properties key
	 * @param values (String[]) a string array containing the replacement value at position 0 and comparison value at position 1
	 */
	@Step("Confirm text value.")
	public WebElement confirmDynamicTextValue(String propertyKey, String[] values) {

		String replacementValue = values[0];
		String value = values[1];

		me = confirmElementExistence(propertyKey, replacementValue);
		ss.assertTrue(me.getText().trim().toLowerCase().contains(value.trim().toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compares a given string (runtimeData/caller derived) against the text contents of
	 * a given web element (WebElement)
	 *
	 * @param we (WebElement) selenium element wrapping text
	 * @param value (String) comparison string
	 */
	@Step("Confirm text value.")
	public WebElement confirmDynamicTextValue(WebElement me, String value) {

		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, me.getText().replace("\n", ""), value);
		ss.assertTrue(me.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compares a given string (runtimeData/caller derived) against the text contents of
	 * a given web element (property key), expected absence
	 *
	 * @param propertyKey (String) locator properties key
	 * @param value (String) comparison string
	 */
	@Step("Confirm text value.")
	public WebElement confirmDynamicNotTextValue(String propertyKey, String value) {

		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] comparing string contains string: \"%s:%s\">%n", this.id, this.testName, me.getText().replace("\n", ""), value);
		ss.assertTrue(!me.getText().toLowerCase().contains(value.toLowerCase()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compares a given string (runtimeData) against the text contents of a given web element
	 * (property key)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public WebElement confirmExactDynamicTextValue(String propertyKey, String runtimeValue) {

		me = confirmElementExistence(propertyKey);
		ss.assertTrue(me.getText().equals(runtimeValue));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compares a given string (runtimeData) against the text contents of a given web element
	 * (WebElement)
	 *
	 * @param we (WebElement) selenium element wrapping text
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public WebElement confirmExactDynamicTextValue(WebElement me, String runtimeValue) {

		//System.out.format("[DEBUG]: <[%s:%s] comparing strings [exp]\"%s\":[act]\"%s\">%n", this.id, this.testName, runtimeValue.trim(), me.getText().trim());
		ss.assertTrue(me.getText().trim().equals(runtimeValue.trim()));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compares a given string (runtimeData) against the input text of a given input field
	 * (runtimeData)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public WebElement confirmExactDynamicInputText(String propertyKey, String runtimeValue) {

		me = confirmElementExistence(propertyKey);
		String value = (String) this.jse.executeScript("return arguments[0].value", me);
		//String value = (String) this.jse.executeScript("return $(arguments[0]).val()", loc); // has an issue with unrecognized expression that I do not understand
		//System.out.format("[DEBUG]: <[%s:%s] value returned from javascriptexecutor: \"%s\">%n", value);
		ss.assertTrue(value.equals(runtimeValue));
		ss.takeScreenShot("Confirm text presence.");
		return me;

	}


	/**
	 * Compares a given string (runtimeData) against the input text of a given input field
	 * (runtimeData)
	 *
	 * @param we (WebElement) selenium element wrapping text
	 * @param runtimeValue (String) comparison string
	 */
	@Step("Confirm text value.")
	public WebElement confirmExactDynamicInputText(WebElement me, String runtimeValue) {

		String value = (String) this.jse.executeScript("return arguments[0].value", me);
		//String value = (String) this.jse.executeScript("return $(arguments[0]).val()", loc); // has an issue with unrecognized expression that I do not understand
		//System.out.format("[DEBUG]: <[%s:%s] value returned from javascriptexecutor: \"%s\">%n", value);
		ss.assertTrue(value.equals(runtimeValue));
		ss.takeScreenShot("Confirm text presence.");
		return me;

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
	 *  Given one daughter window, switch to daughter window
	 */
	@Step("Switch to daughter window.")
	public String[] switchToDaughterWindow() {

		String currentHandle = this.driver.getWindowHandle();
		Set<String> handles = this.driver.getWindowHandles();
		String hndl = null;

		ss.assertTrue(handles.size() == 2, "More than one daughter window.");

		for (String handle: handles) {
			if (!handle.equals(currentHandle)) {
				this.driver.switchTo().window(handle);
				waitForPageLoaded(30);
				hndl = handle;
			}
		}
		return new String[] {currentHandle, hndl};

	}


	/**
	 * Given a window handle, switch to it
	 */
	@Step("Switch to parent window.")
	public void switchToWindowByHandle(String handle) {

		Set<String> handles = this.driver.getWindowHandles();
		if (handles.contains(handle)) {
			this.driver.switchTo().window(handle);
			waitForPageLoaded(30);
		} else {
			ss.assertTrue(false, "Window handle not found.");
		}

	}


	/**
	 * Check for broken image.
	 */
	@Step("Check for broken image.")
	public void checkForBrokenImage(String propertyKey) {

		//waitForPageCompletelyLoaded();
		me = confirmElementExistence(propertyKey);

		System.out.format("[LOG]: <[%s:%s] checking up to %d seconds for broken image.>", this.id, this.testName, gc.defaultTimeOut);
		double start = System.currentTimeMillis();
		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return (boolean) ((JavascriptExecutor) drv).executeScript("return (arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0)", me);
						}
					});

		} catch (TimeoutException to) {
			ss.assertTrue(false, "Image is broken.");
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

	}


	/**
	 * Check for broken image.
	 */
	@Step("Check for broken image.")
	public void checkForBrokenImage(String propertyKey, int wait) {

		//waitForPageCompletelyLoaded();
		me = confirmElementExistence(propertyKey, wait);

		System.out.format("[LOG]: <[%s:%s] checking up to %d seconds for broken image.>", this.id, this.testName, gc.defaultTimeOut);
		double start = System.currentTimeMillis();
		try {

			this.wait
					.withTimeout(Duration.ofSeconds((long) gc.defaultTimeOut))
					.until(new Function<AppiumDriver, Boolean>() {
						public Boolean apply(AppiumDriver drv) {
							return (boolean) ((JavascriptExecutor) drv).executeScript("return (arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0)", me);
						}
					});

		} catch (TimeoutException to) {
			ss.assertTrue(false, "Image is broken.");
		} finally {
			System.out.format(" <waited %.2f seconds>%n", (System.currentTimeMillis() - start)/1000.0);
		}

	}


	/**
	 * Populates text list from multi-element locator, compares against known text list (CSV, property key)
	 */
	@Step("Confirm multi-element text.")
	public void confirmMultiElementText(String propertyKey, String valueKey) {

		List<String> foundList = new ArrayList<String>();
		List<String> expectedList = new ArrayList<String>();
		List<WebElement> meArray = new ArrayList<WebElement>();
		String[] values = null;

		meArray = confirmElementsExistence(propertyKey);
		values = getPropertyValue(valueKey).split(",");
		expectedList = Arrays.asList(values);

		for (WebElement me: meArray) {
			foundList.add(me.getText());
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
	 * Given a propertyKey resulting in a List of WebElements, and a comma-separated
	 * list of strings, compares per-element text with per-string text
	 *
	 * @param propertyKey (String) multi-locator property key (properties)
	 * @param valueKey (String) multi-text property key (properties)
	 */
	@Step("Confirm multi-element text, element by element.")
	public void confirmMultiElementTextByElement(String propertyKey, String valueKey) {

		List<String> foundList = new ArrayList<String>();
		List<String> expectedList = new ArrayList<String>();
		List<WebElement> meArray = new ArrayList<WebElement>();
		String[] values = null;

		meArray = confirmElementsExistence(propertyKey);
		values = getPropertyValue(valueKey).split(",");
		expectedList = Arrays.asList(values);

		for (WebElement me: meArray) {
			foundList.add(me.getText());
		}

		Iterator<String> found = foundList.listIterator();
		Iterator<String> expected = expectedList.listIterator();
		while (found.hasNext() && expected.hasNext()) {
			String f = found.next();
			String e = expected.next();
			ss.assertTrue(f.contains(e), String.format("Found: %s; Expected: %s", f, e));
		}
		ss.takeScreenShot("Lists match.");

	}


	/**
	 * Close current window
	 */
	@Step("Close window.")
	public void closeWindow() {

		this.driver.close();

	}


	/**
	 * Takes an enum (Android software keyboard key code) and presses
	 *
	 * @param keyEnum
	 */
	@Step("Press keyboard key.")
	public void androidPressKey() {

		AndroidDriver tempDrv = getMobileDriver(AndroidDriver.class);
		tempDrv.pressKey(new KeyEvent(AndroidKey.ENTER));
		tempDrv = null;

	}


	/**
	 * Execute a boolean returning synchronous JavaScript snippet (fully defined)
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @return (boolean) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, boolean return.")
	public boolean jseExecuteBoolean(String scriptKey) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s;>%n", this.id, this.testName, scriptKey);
		return (boolean) this.jse.executeScript(script);

	}


	/**
	 * Execute a boolean returning synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param propertyKey (String) locator (properties)
	 * @return (boolean) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, boolean return.")
	public boolean jseExecuteBoolean(String scriptKey, String propertyKey) {

		String script = getPropertyValue(scriptKey);
		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against element: %s;>%n", this.id, this.testName, scriptKey, propertyKey);
		return (boolean) this.jse.executeScript(script, me);

	}


	/**
	 * Execute a boolean returning synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param we (WebElement) selenium element
	 * @return (boolean) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, boolean return.")
	public boolean jseExecuteBoolean(String scriptKey, WebElement me) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against dynamic element.>%n", this.id, this.testName, scriptKey);
		return (boolean) this.jse.executeScript(script, me);

	}


	/**
	 * Execute a integer returning synchronous JavaScript snippet (fully defined)
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @return (int) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, integer return.")
	public int jseExecuteInteger(String scriptKey) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s;>%n", this.id, this.testName, scriptKey);
		return (int) (long) this.jse.executeScript(script);

	}


	/**
	 * Execute a integer returning synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param propertyKey (String) locator (properties)
	 * @return (int) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, integer return.")
	public int jseExecuteInteger(String scriptKey, String propertyKey) {

		String script = getPropertyValue(scriptKey);
		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against element: %s;>%n", this.id, this.testName, scriptKey, propertyKey);
		return (int) (long) this.jse.executeScript(script, me);

	}


	/**
	 * Execute a integer returning synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param we (WebElement) selenium element
	 * @return (int) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, integer return.")
	public int jseExecuteInteger(String scriptKey, WebElement me) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against dynamic element.>%n", this.id, this.testName, scriptKey);
		return (int) (long) this.jse.executeScript(script, me);

	}


	/**
	 * Execute a String returning synchronous JavaScript snippet (fully defined)
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @return (String) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, string return.")
	public String jseExecuteString(String scriptKey) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s;>%n", this.id, this.testName, scriptKey);
		return (String) this.jse.executeScript(script);

	}


	/**
	 * Execute a String returning synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param propertyKey (String) locator (properties)
	 * @return (String) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, string return.")
	public String jseExecuteString(String scriptKey, String propertyKey) {

		String script = getPropertyValue(scriptKey);
		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against element: %s;>%n", this.id, this.testName, scriptKey, propertyKey);
		return (String) this.jse.executeScript(script, me);

	}


	/**
	 * Execute a String returning synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param we (WebElement) selenium element
	 * @return (String) result of script execution as returned by JSE
	 */
	@Step("Execute JavaScript snippet, string return.")
	public String jseExecuteString(String scriptKey, WebElement me) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against dynamic element.>%n", this.id, this.testName, scriptKey);
		return (String) this.jse.executeScript(script, me);

	}


	/**
	 * Execute a synchronous JavaScript snippet (fully defined)
	 *
	 * @param scriptKey (String) script reference (properties)
	 */
	@Step("Execute JavaScript snippet, no return.")
	public void jseExecuteVoid(String scriptKey) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s;>%n", this.id, this.testName, scriptKey);
		this.jse.executeScript(script);

	}


	/**
	 * Execute a synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param propertyKey (String) locator (properties)
	 */
	@Step("Execute JavaScript snippet, no return.")
	public void jseExecuteVoid(String scriptKey, String propertyKey) {

		String script = getPropertyValue(scriptKey);
		me = confirmElementExistence(propertyKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against element: %s;>%n", this.id, this.testName, scriptKey, propertyKey);
		this.jse.executeScript(script, me);

	}


	/**
	 * Execute a synchronous JavaScript snippet (fully defined),
	 * respective some WebElement
	 *
	 * @param scriptKey (String) script reference (properties)
	 * @param we (WebElement) locator (properties)
	 */
	@Step("Execute JavaScript snippet, no return.")
	public void jseExecuteVoid(String scriptKey, WebElement me) {

		String script = getPropertyValue(scriptKey);
		System.out.format("[LOG]: <[%s:%s] executing script: %s; against dynamic element.>%n", this.id, this.testName, scriptKey);
		this.jse.executeScript(script, me);

	}


	/**
	 * Confirm the selection state of a target dropdown menu widget (runtimeData)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param runtimeValue (String) value from runtimeData/GlobalConstants
	 */
	@Step("Confirm dropdown selected value.")
	public void confirmDynamicDropdownValue(String propertyKey, String runtimeValue) {

		waitForPageLoaded();
		Select sel = new Select(confirmElementExistence(propertyKey));
		ss.assertTrue(sel.getFirstSelectedOption().getText().toLowerCase().equals(runtimeValue.toLowerCase()));

	}


	/**
	 * Confirm the selection state of a target dropdown menu widget (properties)
	 *
	 * @param propertyKey (String) locator properties key
	 * @param valueKey (String) option to set properties key
	 */
	@Step("Confirm dropdown selected value.")
	public void confirmStaticDropdownValue(String propertyKey, String valueKey) {

		waitForPageLoaded();
		String value = getPropertyValue(valueKey);
		Select sel = new Select(confirmElementExistence(propertyKey));
		ss.assertTrue(sel.getFirstSelectedOption().getText().toLowerCase().equals(value.toLowerCase()));

	}

	/**
	 * Grab the current instance of 'driver' to use for methods in 'PageObjects'
	 *
	 */
	@Step("Return the current instance of 'driver'")
	public RemoteWebDriver returnDriver() {

		return this.driver;

	}


	public void clickWithConfirmation(WebElement me2, String propertyKey) {

		me.click();
		confirmElementExistence(propertyKey);
		confirmElementExistence(propertyKey, "");

	}


	public void scrollToElement(String propertyKey, String replacement, int maxSwipes) {

		int i = 0;
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("start", "50%,80%");
		params.put("end", "50%,20%");
		params.put("duration", "2");


		while(!elementInViewport(propertyKey, replacement) && i < maxSwipes) {

			System.out.format("[LOG]: <[%s:%s] performing swipe.>%n", this.id, this.testName);
			this.driver.executeScript("mobile:touch:swipe", params);
			i++;

		}

	}


	public boolean elementInViewport(String propertyKey, String replacement) {

		Dimension windowSize = 	this.driver.manage().window().getSize();

		int windowHeight = 		windowSize.getHeight();
		int windowWidth = 		windowSize.getWidth();

		int elementY = 			100000;
		int elementX = 			100000;
		int elementHeight = 	0;
		int elementWidth = 		0;
		WebElement w = 			null;

		w = confirmElementExistenceOrNone(propertyKey, replacement, 6);

		if (!(w instanceof WebElement)) {
			return false;
		}

		elementY = w.getRect().y;
		elementX = w.getRect().x;
		elementHeight = w.getRect().height;
		elementWidth = w.getRect().width;

		System.out.format("[LOG]: <[%s:%s] windowH: %d; windowW: %d; elementY: %d; elementX: %d;>%n", this.id, this.testName, windowHeight, windowWidth, elementY, elementX);

		if ((elementY <= (windowHeight - elementHeight)) && (elementX <= (windowWidth - elementWidth))) {
			return true;
		}

		return false;
	}


}
