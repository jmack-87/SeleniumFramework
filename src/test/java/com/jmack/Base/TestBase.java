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
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.jmack.Base.PageObjects.HomePage;
import com.jmack.Base.PageObjects.IFrame;
import com.jmack.Base.PageObjects.LogInPage;
import com.jmack.Base.PageObjects.RegistrationPage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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
	
	// TestNG Suite parameters
	private String gridTypeOverride;
	private String platformNameOverride;
	private String platformVersionOverride;
	private String browserNameOverride;
	private String browserVersionOverride;
	private String resolutionOverride;
	private String locationOverride;
	// Mobile
	private String deviceNameOverride;
	private String modelOverride;
	private String appPackageOverride;
	private String appActivityOverride;
	private String bundleIdOverride;
	
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
	protected IFrame iFrame;
	protected LogInPage logInPage;
	protected RegistrationPage registrationPage;
	
	
	/**
	 * Initialize RemoteWebDriver, gather test data (from JSON) 	
	 * @param testMethod testNG supplied test object
	 * @param browserNameOverride optional TestNG input from test suite
	 */
	@BeforeMethod(description="Extract test data from JSON, create thread-safe WebDriver.")
	@Step("Initialize test.")
	@Parameters({
		"gridTypeOverride",
		"platformNameOverride",	"platformVersionOverride",
		"browserNameOverride", "browserVersionOverride",
		"resolutionOverride", "locationOverride",
		"deviceNameOverride", "modelOverride",
		"appPackageOverride", "appActivityOverride",
		"bundleIdOverride"})
	protected void setUp(Method testMethod,
			// TestNG Suite Parameters
			@Optional String gridTypeOverride,
			@Optional String platformNameOverride, @Optional String platformVersionOverride,
			@Optional String browserNameOverride, @Optional String browserVersionOverride,
			@Optional String resolutionOverride, @Optional String locationOverride,
			@Optional String deviceNameOverride, @Optional String modelOverride,
			@Optional String appPackageOverride, @Optional String appActivityOverride,
			@Optional String bundleIdOverride) {
		
		this.gridTypeOverride = gridTypeOverride == null ? "" : gridTypeOverride;
		this.platformNameOverride = platformNameOverride == null ? "" : platformNameOverride;
		this.platformVersionOverride = platformVersionOverride == null ? "" : platformVersionOverride;
		this.browserNameOverride = browserNameOverride == null ? "" : browserNameOverride;
		this.browserVersionOverride = browserVersionOverride == null ? "" : browserVersionOverride;
		this.resolutionOverride = resolutionOverride == null ? "" : resolutionOverride;
		this.locationOverride = locationOverride == null ? "" : locationOverride;
		
		this.deviceNameOverride = deviceNameOverride == null ? "" : deviceNameOverride;
		this.modelOverride = modelOverride == null ? "" : modelOverride;
		this.appPackageOverride = appPackageOverride == null ? "" : appPackageOverride;
		this.appActivityOverride = appActivityOverride == null ? "" : appActivityOverride;
		this.bundleIdOverride = bundleIdOverride == null ? "" : bundleIdOverride;
		
		testName = testMethod.getName();
		
		// tag the test with the last three digits of timestamp
		id = ts.toString();
		id = id.substring(id.length()-3 );
		
		// initialize/retrieve test data (from JSON)
		try {
			runtimeData.initialize(gc, testName, id);
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		}
		
		// randomize desktop client
		if (runtimeData.browserName.toLowerCase().equals("randomDesktop")) {
			Random r = new Random();
			String[] browsers = {"chrome","firefox","edge","ie"};
			runtimeData.browserName = browsers[r.nextInt(browsers.length)];
		}
		
		//  randomize mobile client
		if (runtimeData.browserName.toLowerCase().equals("randomMobile")) {
			Random r = new Random();
			String[] browsers = {"androidNative","androidChrome","iosNative","iosSafari"};
			runtimeData.browserName = browsers[r.nextInt(browsers.length)];
		}
		
		
		// OVERRIDES
		// if there is an override passed via TestNG suite file, and the override differs from pre-established gridType, override gridType
		if (!("").equals(this.gridTypeOverride) && !runtimeData.gridType.toLowerCase().equals(this.gridTypeOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding gridType \"%s\" with \"%s\">%n", id, testName, runtimeData.gridType, gridTypeOverride);
			runtimeData.gridType = this.gridTypeOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established platformName, override platformName
		if (!("").equals(this.platformNameOverride) && !runtimeData.platformName.toLowerCase().equals(this.platformNameOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformName \"%s\" with \"%s\">%n", id, testName, runtimeData.platformName, platformNameOverride);
			runtimeData.platformName = this.platformNameOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established platformVersion, override platformVersion
		if (!("").equals(this.platformVersionOverride) && !runtimeData.platformVersion.toLowerCase().equals(this.platformVersionOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.platformVersion, platformVersionOverride);
			runtimeData.platformVersion = this.platformVersionOverride;
		}
				
		// if there is an override passed via TestNG suite file, and the override differs from pre-established browser, override browser
		if (!("").equals(this.browserNameOverride) && !runtimeData.browserName.toLowerCase().equals(this.browserNameOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserName \"%s\" with \"%s\">%n", id, testName, runtimeData.browserName, browserNameOverride);
			runtimeData.browserName = browserNameOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established browserVersion, override browserVersion
		if (!("").equals(this.browserVersionOverride) && !runtimeData.browserVersion.toLowerCase().equals(this.browserVersionOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.browserVersion, browserVersionOverride);
			runtimeData.browserVersion = browserVersionOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established resolution, override resolution
		if (!("").equals(this.resolutionOverride) && !runtimeData.resolution.toLowerCase().equals(this.resolutionOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding resolution \"%s\" with \"%s\">%n", id, testName, runtimeData.resolution, resolutionOverride);
			runtimeData.resolution = resolutionOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established location, override location
		if (!("").equals(this.locationOverride) && !runtimeData.location.toLowerCase().equals(this.locationOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding location \"%s\" with \"%s\">%n", id, testName, runtimeData.location, locationOverride);
			runtimeData.location = locationOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established deviceName, override deviceName
		if (!("").equals(this.deviceNameOverride) && !runtimeData.deviceName.toLowerCase().equals(this.deviceNameOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding deviceName \"%s\" with \"%s\">%n", id, testName, runtimeData.deviceName, deviceNameOverride);
			runtimeData.deviceName = this.deviceNameOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established model, override model
		if (!("").equals(this.modelOverride) && !runtimeData.model.toLowerCase().equals(this.modelOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding model \"%s\" with \"%s\">%n", id, testName, runtimeData.model, modelOverride);
			runtimeData.model = this.modelOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established appPackage, override appPackage
		if (!("").equals(this.appPackageOverride) && !runtimeData.appPackage.toLowerCase().equals(this.appPackageOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appPackage \"%s\" with \"%s\">%n", id, testName, runtimeData.appPackage, appPackageOverride);
			runtimeData.appPackage = this.appPackageOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established appActivity, override appActivity
		if (!("").equals(this.appActivityOverride) && !runtimeData.appActivity.toLowerCase().equals(this.appActivityOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appActivity \"%s\" with \"%s\">%n", id, testName, runtimeData.appActivity, appActivityOverride);
			runtimeData.appActivity = this.appActivityOverride;
		}
		
		// if there is an override passed via TestNG suite file, and the override differs from pre-established bundleId, override bundleId
		if (!("").equals(this.bundleIdOverride) && !runtimeData.bundleId.toLowerCase().equals(this.bundleIdOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding bundleId \"%s\" with \"%s\">%n", id, testName, runtimeData.bundleId, bundleIdOverride);
			runtimeData.bundleId = this.bundleIdOverride;
		}
		// END OVERRIDES
		
		
		if (runtimeData.gridType.toLowerCase().equals("local")) {
			switch (runtimeData.browserName.toLowerCase()) {
			//Desktop
			case "desktopchrome":
				options = new ChromeOptions();
				if (runtimeData.headless) {
					((ChromeOptions) options).setHeadless(true);
				}
				break;
			case "desktopfirefox":
				options = new FirefoxOptions();
				if (runtimeData.headless) {
					((FirefoxOptions) options).setHeadless(true);
				}
				break;
			case "desktopedge":
				options = new EdgeOptions();
				break;
			case "desktopie":
				options = new InternetExplorerOptions();
				break;
			case "desktopsafari":
				options = new SafariOptions();
				break;
			//Mobile
			case "androidnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "UIAutomator2");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				caps.setCapability("deviceName", "ignoredButNotEmpty");
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("appPackage", runtimeData.appPackage);
				caps.setCapability("appActivity", runtimeData.appActivity);
				break;
			case "androidchrome":
				caps = DesiredCapabilities.android();
				// optional
				caps.setCapability("automationName", "Appium");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				caps.setCapability("deviceName", "ignoredButNotEmpty");
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setBrowserName("Chrome");
				break;
			case "iosnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "XCUITest");
				caps.setCapability("deviceName", runtimeData.deviceName);
				//required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("bundleId", runtimeData.bundleId);
				break;
			case "iossafari":
				caps = DesiredCapabilities.iphone();
				// optional
				caps.setCapability("automationName", "XCUITest");
				caps.setCapability("deviceName", runtimeData.deviceName);
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setBrowserName("Safari");
				break;
			}
		}
		
		if (runtimeData.gridType.toLowerCase().equals("perfecto")) {
			switch (runtimeData.browserName.toLowerCase()) {
			//Desktop
			case "desktopchrome":
				options = new ChromeOptions();
				((ChromeOptions) options).setCapability(CapabilityType.BROWSER_NAME, "chrome");
				//((ChromeOptions) options).setCapability("platformName", runtimeData.platformName);
				((ChromeOptions) options).setCapability("platform", runtimeData.platform);
				((ChromeOptions) options).setCapability("testName", this.testName);
				((ChromeOptions) options).setCapability("version", runtimeData.browserVersion);
				//((ChromeOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				//((ChromeOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				//((ChromeOptions) options).setCapability("resolution", runtimeData.resolution);
				//((ChromeOptions) options).setCapability("location", runtimeData.location);
				break;
			case "desktopfirefox":
				options = new FirefoxOptions();
				((FirefoxOptions) options).setCapability(CapabilityType.BROWSER_NAME, "Firefox");
				((FirefoxOptions) options).setCapability("platformName", runtimeData.platformName);
				((FirefoxOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				((FirefoxOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				((FirefoxOptions) options).setCapability("resolution", runtimeData.resolution);
				((FirefoxOptions) options).setCapability("location", runtimeData.location);
				break;
			case "desktopedge":
				options = new EdgeOptions();
				((EdgeOptions) options).setCapability(CapabilityType.BROWSER_NAME, "Edge");
				((EdgeOptions) options).setCapability("platformName", runtimeData.platformName);
				((EdgeOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				((EdgeOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				((EdgeOptions) options).setCapability("resolution", runtimeData.resolution);
				((EdgeOptions) options).setCapability("location", runtimeData.location);
				break;
			case "desktopie":
				options = new InternetExplorerOptions();
				((InternetExplorerOptions) options).setCapability(CapabilityType.BROWSER_NAME, "Internet Explorer");
				((InternetExplorerOptions) options).setCapability("platformName", runtimeData.platformName);
				((InternetExplorerOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				((InternetExplorerOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				((InternetExplorerOptions) options).setCapability("resolution", runtimeData.resolution);
				((InternetExplorerOptions) options).setCapability("location", runtimeData.location);
				break;
			case "desktopsafari":
				options = new SafariOptions();
				((SafariOptions) options).setCapability(CapabilityType.BROWSER_NAME, "Safari");
				((SafariOptions) options).setCapability("platformName", runtimeData.platformName);
				((SafariOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				((SafariOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				((SafariOptions) options).setCapability("resolution", runtimeData.resolution);
				((SafariOptions) options).setCapability("location", runtimeData.location);
				((SafariOptions) options).setCapability("openDeviceTimeout", 1);
				break;
			//Mobile
			case "androidnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "Appium");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				caps.setCapability("deviceName", runtimeData.deviceName);
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("appPackage", runtimeData.appPackage);
				caps.setCapability("appActivity", runtimeData.appActivity);
				break;
			case "androidchrome":
				caps = DesiredCapabilities.android();
				// optional
				caps.setCapability("automationName", "Appium");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				caps.setCapability("deviceName", runtimeData.deviceName);
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setBrowserName("Chrome");
				break;
			case "iosnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "XCUITest");
				caps.setCapability("deviceName", runtimeData.deviceName);
				//required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("bundleId", runtimeData.bundleId);
				break;
			case "iossafari":
				caps = DesiredCapabilities.iphone();
				// optional
				caps.setCapability("automationName", "XCUITest");
				caps.setCapability("deviceName", runtimeData.deviceName);
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setBrowserName("Safari");
				break;
			}
		}
		
		//System.out.format("[LOG]: <[%s:%s] using %s; headless: %s;>\n", this.id, this.testName, runtimeData.browserName, runtimeData.headless);
		
		// load properties file (locators definitions)
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
		
		// Desktop
		if (null != options) {
			if (runtimeData.gridType.equals("local")) {
				try {
					driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options));
					if (!(options instanceof SafariOptions)) {
						getDriver().manage().window().maximize();
					}
					ss = new ScreenShot(getDriver(), id, testName);
					generic = new Generic(getDriver(), ss, props, id, testName);
					
					initializePageObjects();
					
				} catch (MalformedURLException m) {
					m.printStackTrace();
				}
			} else if (runtimeData.gridType.equals("perfecto")) {
				
				//Perfecto
				System.out.println("set remote session caps");
				((MutableCapabilities) options).setCapability("securityToken", gc.perfectoSecurityToken);
				
				//SRF
				((MutableCapabilities) options).setCapability("SRF_CLIENT_ID", gc.srfId);
				((MutableCapabilities) options).setCapability("SRF_CLIENT_SECRET", gc.srfPass);
				
				try {
					//Perfecto
					System.out.println("start remote session");
					driver.set(new RemoteWebDriver(new URL(gc.perfectoHost), options));
					
					//SRF
					//driver.set(new RemoteWebDriver(new URL(gc.srfHost), options));
					System.out.println("finished remote session");
					
					if (!(options instanceof SafariOptions)) {
						getDriver().manage().window().maximize();
					}
					ss = new ScreenShot(getDriver(), id, testName);
					generic = new Generic(getDriver(), ss, props, id, testName);
					
					initializePageObjects();
					
				} catch (MalformedURLException m) {
					m.printStackTrace();
				}
			} else {
				// error, no grid identified
			}
		
		// Mobile
		} else if (null != caps) {
			if (runtimeData.gridType.equals("local")) {
				try {
					mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.appiumHost), caps));
					mss = new MobileScreenShot(getMobileDriver(), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(), mss, props, id, testName);
					
					initializeMobilePageObjects();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else if (runtimeData.gridType.equals("perfecto")) {
				
				//Perfecto
				((DesiredCapabilities) caps).setCapability("securityToken", gc.perfectoSecurityToken);
				
				//SRF
				((DesiredCapabilities) caps).setCapability("SRF_CLIENT_ID", gc.srfId);
				((DesiredCapabilities) caps).setCapability("SRF_CLIENT_SECRET", gc.srfPass);
				
				
				try {
					
					//Perfecto
					mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.perfectoHost), caps));
					
					//SRF
					//mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.srfHost), caps));
					
					mss = new MobileScreenShot(getMobileDriver(), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(), mss, props, id, testName);
					
					initializeMobilePageObjects();
					
				} catch (MalformedURLException m) {
					m.printStackTrace();
				}
			} else {
				// error, no grid identified
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
		iFrame = new IFrame(generic, ss, id, testName);
		logInPage = new LogInPage(generic, ss, id, testName);
		registrationPage = new RegistrationPage(generic, ss, id, testName);
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
			driver.remove();
		}
		else if (getMobileDriver() instanceof AppiumDriver<?>) {
			getMobileDriver().quit();
			mDriver.remove();
		}
		System.out.format("[LOG]: <[%s:%s] =====end test=====>%n", id, testName);
	}
	
	
	/**
	 * Returns a thread-safe RemoteWebDriver
	 * @return thread-safe RemoteWebDriver
	 */
	protected RemoteWebDriver getDriver() {
		
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
