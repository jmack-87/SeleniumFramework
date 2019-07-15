package com.jmack.Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import com.jmack.Base.PageObjects.HomePagePO;
import com.jmack.Base.PageObjects.IFramePO;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.jmack.Base.PageObjects.LoginPagePO;
import com.jmack.Base.PageObjects.RegistrationPagePO;

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
	private Properties props = new Properties();

	private Capabilities options = null;
	private DesiredCapabilities caps = null;
	private InputStream testReferenceFile = null;
	private Date date = new Date();
	private Long ts = date.getTime();

	//Excel-based data
	private String applicationUnderTest = null;
	private String excelDataFile = null;
	private String jsonDataFile = null;
	private String propertiesFile = null;

	// TestNG Suite parameters
	private String gridType;
	private String platformName;
	private String platformVersion;
	private String browserName;
	private String browserVersion;
	private String resolution;
	private String location;

	// Mobile
	private String deviceName;
	private String model;
	private String appPackage;
	private String appActivity;
	private String bundleId;

	private String id;
	private String testName;

	// Helpers
	protected Generic generic;
	protected MobileGeneric mGeneric;
	private ScreenShot ss = null;
	private MobileScreenShot mss = null;
	protected JsonDataExtractor jsonDataExtractor;
	protected GlobalConstants gc = new GlobalConstants();
	protected RuntimeData runtimeData = new RuntimeData();
	protected ExcelDataExtractor excelDataExtractor;

	// Page Objects
	protected HomePagePO homePagePO;
	protected IFramePO iFrame;
	protected LoginPagePO logInPage;
	protected RegistrationPagePO registrationPagePO;


	/**
	 * Initialize RemoteWebDriver, gather test data (from JSON, excel, TestNG suite Parameters)
	 *
	 * @param testMethod testNG supplied test object
	 * @param browserNameOverride optional TestNG input from test suite
	 */
	@BeforeMethod(description="Extract test data from JSON, create thread-safe WebDriver.")
	@Step("Initialize test.")
	@Parameters({
		"applicationUnderTest", "excelDataFile",
		"jsonDataFile", "propertiesFile",
		"gridType", "platformName",
		"platformVersion", "browserName",
		"browserVersion", "resolution",
		"location", "deviceName",
		"model", "appPackage",
		"appActivity", "bundleId"})
	protected void setUp(Method testMethod,
			@Optional String applicationUnderTest,	@Optional String excelDataFile,
			@Optional String jsonDataFile,			@Optional String propertiesFile,
			@Optional String gridType,				@Optional String platformName,
			@Optional String platformVersion,		@Optional String browserName,
			@Optional String browserVersion,		@Optional String resolution,
			@Optional String location,				@Optional String deviceName,
			@Optional String model,					@Optional String appPackage,
			@Optional String appActivity,			@Optional String bundleId) {

		this.applicationUnderTest = applicationUnderTest == null ? null : applicationUnderTest;
		this.excelDataFile = excelDataFile == null ? null : excelDataFile;
		this.jsonDataFile = jsonDataFile == null ? null : jsonDataFile;
		this.propertiesFile = propertiesFile == null ? null : propertiesFile;

		this.gridType = gridType == null ? "" : gridType;
		this.platformName = platformName == null ? "" : platformName;
		this.platformVersion = platformVersion == null ? "" : platformVersion;
		this.browserName = browserName == null ? "" : browserName;
		this.browserVersion = browserVersion == null ? "" : browserVersion;
		this.resolution = resolution == null ? "" : resolution;
		this.location = location == null ? "" : location;

		this.deviceName = deviceName == null ? "" : deviceName;
		this.model = model == null ? "" : model;
		this.appPackage = appPackage == null ? "" : appPackage;
		this.appActivity = appActivity == null ? "" : appActivity;
		this.bundleId = bundleId == null ? "" : bundleId;


		testName = testMethod.getName();

		// tag the test with the last three digits of timestamp
		id = ts.toString();
		id = id.substring(id.length() - 3);

		// START JSON DATA EXTRACTION
		if (!(null == this.jsonDataFile || null == this.applicationUnderTest)) {
			jsonDataExtractor = new JsonDataExtractor(this.gc, this.runtimeData, this.applicationUnderTest, this.jsonDataFile, this.id, this.testName);
		}
		// START JSON DATA EXTRACTION

		// START EXCEL OVERRIDES
		if (!(null == this.excelDataFile || null == this.applicationUnderTest)) {
			excelDataExtractor = new ExcelDataExtractor(this.gc, this.runtimeData, this.applicationUnderTest, this.excelDataFile, this.id, this.testName);
		}
		// END EXCEL OVERRIDES

		/* DEBUG Parameters
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'applicationUnderTest': '%s'%n", id, testName, applicationUnderTest);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'excelDataFile': '%s'%n", id, testName, excelDataFile);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'jsonDataFile': '%s'%n", id, testName, jsonDataFile);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'propertiesFile': '%s'%n", id, testName, propertiesFile);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'gridType': '%s'%n", id, testName, gridType);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'platformName': '%s'%n", id, testName, platformName);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'platformVersion': '%s'%n", id, testName, platformVersion);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'browserName': '%s'%n", id, testName, browserName);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'browserVersion': '%s'%n", id, testName, browserVersion);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'resolution': '%s'%n", id, testName, resolution);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'location': '%s'%n", id, testName, location);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'deviceName': '%s'%n", id, testName, deviceName);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'model': '%s'%n", id, testName, model);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'appPackage': '%s'%n", id, testName, appPackage);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'appActivity': '%s'%n", id, testName, appActivity);
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'bundleId': '%s'%n", id, testName, bundleId);
		*/

		// START TESTNG PARAMETER OVERRIDES
		// if there is an  passed via TestNG suite file, and the  differs from pre-established gridType,  gridType
		if (!("").equals(this.gridType) && !runtimeData.gridType.toLowerCase().equals(this.gridType.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding gridType \"%s\" with \"%s\">%n", id, testName, runtimeData.gridType, this.gridType);
			runtimeData.gridType = this.gridType;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established platformName,  platformName
		if (!("").equals(this.platformName) && !runtimeData.platformName.toLowerCase().equals(this.platformName.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformName \"%s\" with \"%s\">%n", id, testName, runtimeData.platformName, this.platformName);
			runtimeData.platformName = this.platformName;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established platformVersion,  platformVersion
		if (!("").equals(this.platformVersion) && !runtimeData.platformVersion.toLowerCase().equals(this.platformVersion.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.platformVersion, platformVersion);
			runtimeData.platformVersion = this.platformVersion;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established browser,  browser
		if (!("").equals(this.browserName) && !runtimeData.browserName.toLowerCase().equals(this.browserName.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserName \"%s\" with \"%s\">%n", id, testName, runtimeData.browserName, browserName);
			runtimeData.browserName = browserName;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established browserVersion,  browserVersion
		if (!("").equals(this.browserVersion) && !runtimeData.browserVersion.toLowerCase().equals(this.browserVersion.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.browserVersion, browserVersion);
			runtimeData.browserVersion = browserVersion;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established resolution,  resolution
		if (!("").equals(this.resolution) && !runtimeData.resolution.toLowerCase().equals(this.resolution.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding resolution \"%s\" with \"%s\">%n", id, testName, runtimeData.resolution, resolution);
			runtimeData.resolution = resolution;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established location,  location
		if (!("").equals(this.location) && !runtimeData.location.toLowerCase().equals(this.location.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding location \"%s\" with \"%s\">%n", id, testName, runtimeData.location, location);
			runtimeData.location = location;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established deviceName,  deviceName
		if (!("").equals(this.deviceName) && !runtimeData.deviceName.toLowerCase().equals(this.deviceName.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding deviceName \"%s\" with \"%s\">%n", id, testName, runtimeData.deviceName, deviceName);
			runtimeData.deviceName = this.deviceName;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established model,  model
		if (!("").equals(this.model) && !runtimeData.model.toLowerCase().equals(this.model.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding model \"%s\" with \"%s\">%n", id, testName, runtimeData.model, model);
			runtimeData.model = this.model;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established appPackage,  appPackage
		if (!("").equals(this.appPackage) && !runtimeData.appPackage.toLowerCase().equals(this.appPackage.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appPackage \"%s\" with \"%s\">%n", id, testName, runtimeData.appPackage, appPackage);
			runtimeData.appPackage = this.appPackage;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established appActivity,  appActivity
		if (!("").equals(this.appActivity) && !runtimeData.appActivity.toLowerCase().equals(this.appActivity.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appActivity \"%s\" with \"%s\">%n", id, testName, runtimeData.appActivity, appActivity);
			runtimeData.appActivity = this.appActivity;
		}

		// if there is an  passed via TestNG suite file, and the  differs from pre-established bundleId,  bundleId
		if (!("").equals(this.bundleId) && !runtimeData.bundleId.toLowerCase().equals(this.bundleId.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding bundleId \"%s\" with \"%s\">%n", id, testName, runtimeData.bundleId, bundleId);
			runtimeData.bundleId = this.bundleId;
		}
		// END TESTNG PARAMETER OVERRIDES


		// randomize desktop client
		if (runtimeData.browserName.toLowerCase().equals("randomDesktop")) {
			Random r = new Random(System.currentTimeMillis());
			String[] browsers = {"chrome","firefox","edge","ie"};
			runtimeData.browserName = browsers[r.nextInt(browsers.length)];
		}

		//  randomize mobile client
		if (runtimeData.browserName.toLowerCase().equals("randomMobile")) {
			Random r = new Random(System.currentTimeMillis());
			String[] browsers = {"androidNative","androidChrome","iosNative","iosSafari"};
			runtimeData.browserName = browsers[r.nextInt(browsers.length)];
		}


		// make sure minimum data requirements have been met
		Assert.assertTrue(runtimeData.minimumDataCheck(this.id, this.testName), "Minimum data requirements were not met. Check JSON/Excel/Parameter for minimum data.");


		// Set up clients for use with a locally hosted selenium grid
		if (runtimeData.gridType.toLowerCase().equals("local")) {

			System.out.format("[LOG]: <[%s:%s] local grid detected>%n", id, testName);

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
				caps.setCapability("automationName", "UIAutomator2");
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

		// setup clients for use with Perfecto cloud
		if (runtimeData.gridType.toLowerCase().equals("perfecto")) {

			System.out.format("[LOG]: <[%s:%s] perfecto grid detected>%n", id, testName);

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


		// load properties file (locators definitions)
		try {

			String path = gc.propertiesFilesPath + this.applicationUnderTest + "\\" + this.propertiesFile;

			testReferenceFile = new FileInputStream(path);
			props.load(testReferenceFile);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (testReferenceFile != null) {
				try {
					testReferenceFile.close();
					System.out.format("[LOG]: <[%s:%s] properties loaded>%n", id, testName);
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}

		// initialize desktop client WebDriver
		if (null != options) {

			System.out.format("[LOG]: <[%s:%s] %s client detected on %s grid>%n", id, testName, runtimeData.browserName, runtimeData.gridType);

			// using local grid
			if (runtimeData.gridType.toLowerCase().equals("local")) {
				try {
					driver.set(new RemoteWebDriver(new URL(gc.gridHost), options));
					if (!(options instanceof SafariOptions)) {
						getDriver().manage().window().maximize();
					}

					System.out.format("[LOG]: <[%s:%s] launching screenshot helper>%n", id, testName);
					ss = new ScreenShot(getDriver(), id, testName);

					System.out.format("[LOG]: <[%s:%s] launching generic helper>%n", id, testName);
					generic = new Generic(getDriver(), ss, props, id, testName);

					System.out.format("[LOG]: <[%s:%s] initializing page objects>%n", id, testName);
					initializePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
				}
				catch (WebDriverException wde ) {
					Assert.fail("Unable to start WebDriver");
				}

			// using Perfecto grid
			} else if (runtimeData.gridType.toLowerCase().equals("perfecto")) {

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

					System.out.format("[LOG]: <[%s:%s] initializing page objects>%n", id, testName);
					initializePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
				}
				catch (WebDriverException wde ) {
					Assert.fail("Unable to start WebDriver");
				}
			} else {
				// error, no grid identified
			}

		// initialize mobile client WebDriver (Appium)
		} else if (null != caps) {

			System.out.format("[LOG]: <[%s:%s] %s client detected on %s grid>%n", id, testName, runtimeData.browserName, runtimeData.gridType);

			// using local appium server
			if (runtimeData.gridType.toLowerCase().equals("local")) {
				try {
					mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.appiumHost), caps));
					mss = new MobileScreenShot(getMobileDriver(), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(), mss, props, id, testName);

					System.out.format("[LOG]: <[%s:%s] initializing mobile page objects>%n", id, testName);
					initializeMobilePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
				}
				catch (WebDriverException wde ) {
					Assert.fail("Unable to start WebDriver");
				}

			// using perfecto
			} else if (runtimeData.gridType.toLowerCase().equals("perfecto")) {

				//Perfecto
				((DesiredCapabilities) caps).setCapability("securityToken", gc.perfectoSecurityToken);

				/* StormRunnerFunctional
				((DesiredCapabilities) caps).setCapability("SRF_CLIENT_ID", gc.srfId);
				((DesiredCapabilities) caps).setCapability("SRF_CLIENT_SECRET", gc.srfPass);*/

				try {

					//Perfecto
					mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.perfectoHost), caps));

					/* StormRunnerFunctional
					mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.srfHost), caps));*/

					mss = new MobileScreenShot(getMobileDriver(), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(), mss, props, id, testName);

					System.out.format("[LOG]: <[%s:%s] initializing mobile page objects>%n", id, testName);
					initializeMobilePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
				}
				catch (WebDriverException wde ) {
					Assert.fail("Unable to start WebDriver");
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

		homePagePO = 			new HomePagePO(generic, ss, id, testName);
		iFrame = 				new IFramePO(generic, ss, id, testName);
		logInPage = 			new LoginPagePO(generic, ss, runtimeData, id, testName);
		registrationPagePO = 	new RegistrationPagePO(generic, ss, runtimeData, id, testName, iFrame); // must be after IFramePO

		System.out.format("[LOG]: <[%s:%s] page objects loaded>%n", id, testName);

	}


	/**
	 *  Add mobile page objects here
	 */
	@Step("Initialize Mobile Page Objects.")
	private void initializeMobilePageObjects() {

		//homePagePO = new HomePagePO(mGeneric, ss, id, testName);
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
