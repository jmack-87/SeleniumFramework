package com.jmack.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.jmack.Base.PageObjects.HomePage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.Step;

/**
 * 
 * @author Jerimiah Mack
 *
 */
public class TestBase {
	
	private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<AppiumDriver<?>> mDriver = new ThreadLocal<>();
	protected Properties props = new Properties();

	private Capabilities options = null;
	private DesiredCapabilities caps = null;
	private InputStream testReferenceFile = null;
	private Date date = new Date();
	private Long ts = date.getTime();
	private String browserOverride = "";
	
	private String id;
	private String testName;
	
	// Helpers
	protected Generic generic;
	protected MobileGeneric mGeneric;
	protected ScreenShot ss = null;
	protected MobileScreenShot mss = null;
	protected DataExtractor runtimeData = new DataExtractor();
	protected static GlobalConstants gc = new GlobalConstants();
	
	// Page Objects
	protected HomePage homePage;
	
	
	/**
	 * Initialize RemoteWebDriver, gather test data (from JSON) 	
	 * @param testMethod testNG supplied test object
	 * @param browserOverride optional TestNG input from test suite
	 */
	@BeforeMethod(description="Extract test data from JSON, create thread-safe WebDriver.")
	@Step("Initialize test.")
	@Parameters({"browserOverride"})
	protected void setUp(Method testMethod, @Optional String browserOverride) {
		this.browserOverride = browserOverride;
		testName = testMethod.getName();
		
		// tag the test with the last three digits of timestamp
		id = ts.toString();
		id = id.substring(id.length()-3 );
		
		try {
			runtimeData.initialize(gc, testName, id);
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		}
		
		// 
		if (runtimeData.browser.toLowerCase().equals("randomDesktop")) {
			Random r = new Random();
			String[] browsers = {"chrome","firefox","edge","ie"};
			runtimeData.browser = browsers[r.nextInt(browsers.length)];
		}
		
		//  
		if (runtimeData.browser.toLowerCase().equals("randomMobile")) {
			Random r = new Random();
			String[] browsers = {"androidNative","androidChrome","iosNative","iosSafari"};
			runtimeData.browser = browsers[r.nextInt(browsers.length)];
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from preestablished browser, override browser
		if (!browserOverride.equals("") && !runtimeData.browser.toLowerCase().equals(this.browserOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browser \"%s\" with \"%s\">%n", id, testName, runtimeData.browser, browserOverride);
			runtimeData.browser = browserOverride;
			
		}
		
		switch (runtimeData.browser.toLowerCase()) {
		//Desktop
		case "chrome":
			options = new ChromeOptions();
			if (runtimeData.headless) {
				((ChromeOptions) options).setHeadless(true);
			}
			break;
		case "firefox":
			options = new FirefoxOptions();
			if (runtimeData.headless) {
				((FirefoxOptions) options).setHeadless(true);
			}
			break;
		case "edge":
			options = new EdgeOptions();
			((EdgeOptions) options).setCapability("", "");
			break;
		case "ie":
			options = new InternetExplorerOptions();
			break;
		//Mobile
		case "androidnative":
			caps = DesiredCapabilities.android();
			caps.setCapability("automationName", "UIAutomator2");
			caps.setCapability("platformVersion", runtimeData.platformVersion);
			caps.setCapability("appPackage", runtimeData.aut);
			caps.setCapability("takesScreenshot", "true");
			break;
		case "androidchrome":
			caps = DesiredCapabilities.android();
			caps.setCapability("automationName", "UIAutomator2");
			caps.setCapability("platformVersion", runtimeData.platformVersion);
			caps.setCapability("skipUnlock", true);
			caps.setCapability("deviceName", "ignored");
			caps.setCapability("takesScreenshot", "true");
			caps.setBrowserName("Chrome");
			break;
		case "iosnative":
			caps = DesiredCapabilities.iphone();
			caps.setCapability("automationName", "XCUITest");
			caps.setCapability("platformVersion", runtimeData.platformVersion);
			caps.setCapability("bundleId", runtimeData.aut);
			break;
		case "iossafari":
			caps = DesiredCapabilities.iphone();
			caps.setCapability("automationName", "XCUITest");
			caps.setCapability("platformVersion", runtimeData.platformVersion);
			caps.setBrowserName("Safari");
			break;
		}
		
		//System.out.format("[LOG]: <[%s:%s] using %s; headless: %s;>\n", this.id, this.testName, runtimeData.browser, runtimeData.headless);
		
		try {
			testReferenceFile = new FileInputStream(gc.testReferenceFilePath);
			props.load(testReferenceFile);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (testReferenceFile != null) {
				try {
					testReferenceFile.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}
		
		if (null != options) {
			
			if (runtimeData.gridType.equals("local")) {
				try {
					driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options));
					getDriver().manage().window().maximize();
					ss = new ScreenShot(getDriver(), id, testName);
					generic = new Generic(getDriver(), ss, props, id, testName);
					
					initializePageObjects();
					
				} catch (MalformedURLException m) {
					m.printStackTrace();
				}
			} else if (runtimeData.gridType.equals("perfecto")) {
				((DesiredCapabilities) options).setCapability("securityToken", gc.perfectoSecurityToken);
				try {
					driver.set(new RemoteWebDriver(new URL(gc.perfectoHost), options));
					getDriver().manage().window().maximize();
					ss = new ScreenShot(getDriver(), id, testName);
					generic = new Generic(getDriver(), ss, props, id, testName);
					
					initializePageObjects();
					
				} catch (MalformedURLException m) {
					m.printStackTrace();
				}
			} else {
				// error, no grid identified
			}
			
		} else if (null != caps) {
			try {
				//System.out.format("[DEBUG]: <setting up appium driver for %s>%n", runtimeData.browser);
				mDriver.set(new AppiumDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), caps));
				mss = new MobileScreenShot(getMobileDriver(), id, testName);
				mGeneric = new MobileGeneric(getMobileDriver(), mss, props, id, testName);
				
				initializeMobilePageObjects();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			// throw config error?
		}
		
		System.out.format("[LOG]: <[%s:%s] =====Start test=====>%n", id, testName);
	}
	
	
	/** 
	 *  Add page objects here
	 */
	@Step("Initialize Page Objects.")
	private void initializePageObjects() {
		
		homePage = new HomePage(generic, ss, id, testName);
	}
	
	
	/** 
	 *  Add mobile page objects here
	 */
	@Step("Initialize Mobile Page Objects.")
	private void initializeMobilePageObjects() {
		
		//homePage = new HomePage(mGeneric, ss, id, testName);
	}
	
	
	/** 
	 *  Quit RemoteWebDiver (dismiss/close browser). Destroy thread-safe driver.
	 */
	@AfterMethod(description="Quit and destroy thread-safe driver.")
	@Step("Quit browser.")
	protected void tearDown() {
		
		if (getDriver() instanceof RemoteWebDriver) {
			getDriver().quit();
		}
		else if (getMobileDriver() instanceof AppiumDriver<?>) {
			getMobileDriver().quit();
		}
		driver.remove();
		System.out.format("[LOG]: <[%s:%s] =====end test=====>%n", id, testName);
	}
	
	
	/**
	 * Returns a thread-safe RemoteWebDriver
	 * @return thread-safe RemoteWebDriver
	 */
	private RemoteWebDriver getDriver() {
		
		return driver.get();
	}
	
	
	/**
	 * Returns a thread-safe AppiumDriver
	 * @return thread-safe AppiumDriver
	 */
	private AppiumDriver<?> getMobileDriver() {
		
		return mDriver.get();
	}
	
	
}
