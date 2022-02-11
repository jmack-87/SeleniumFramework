package com.ibm.ciclan.Base;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import com.ibm.ciclan.Base.PageObjects.Honda_SearchPO;
import com.ibm.ciclan.Base.PageObjects.HomePagePO;
import com.ibm.ciclan.Base.PageObjects.IFramePO;
import com.ibm.ciclan.Base.PageObjects.LoginPagePO;
import com.ibm.ciclan.Base.PageObjects.RegistrationPagePO;
import com.ibm.ciclan.Enumerations.BrowserStack.BrowserStack;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import io.qameta.allure.Step;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
	private String mobileType;

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
	protected Honda_SearchPO honda_searchPO;
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
		"appActivity", "bundleId", "mobileType"})
	protected void setUp(Method testMethod,
			@Optional String applicationUnderTest,	@Optional String excelDataFile,
			@Optional String jsonDataFile,			@Optional String propertiesFile,
			@Optional String gridType,				@Optional String platformName,
			@Optional String platformVersion,		@Optional String browserName,
			@Optional String browserVersion,		@Optional String resolution,
			@Optional String location,				@Optional String deviceName,
			@Optional String model,					@Optional String appPackage,
			@Optional String appActivity,			@Optional String bundleId,
			@Optional String mobileType) {

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

		this.mobileType = mobileType == null ? "" : mobileType;


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
		System.out.format("[DEBUG]: <[%s:%s] parameter data 'mobileType': '%s'%n", id, testName, mobileType);
		*/

		// START TESTNG PARAMETER OVERRIDES
		// if there is a gridType passed via TestNG suite file, and the gridType differs from pre-established gridType,  gridType
		if (!("").equals(this.gridType) && !runtimeData.gridType.toLowerCase().equals(this.gridType.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding gridType \"%s\" with \"%s\">%n", id, testName, runtimeData.gridType, this.gridType);
			runtimeData.gridType = this.gridType;
		}

		// if there is a platformName passed via TestNG suite file, and the platformName differs from pre-established platformName,  platformName
		if (!("").equals(this.platformName) && !runtimeData.platformName.toLowerCase().equals(this.platformName.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformName \"%s\" with \"%s\">%n", id, testName, runtimeData.platformName, this.platformName);
			runtimeData.platformName = this.platformName;
		}

		// if there is a platformVersion passed via TestNG suite file, and the platformVersion differs from pre-established platformVersion,  platformVersion
		if (!("").equals(this.platformVersion) && !runtimeData.platformVersion.toLowerCase().equals(this.platformVersion.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.platformVersion, platformVersion);
			runtimeData.platformVersion = this.platformVersion;
		}

		// if there is a browserName passed via TestNG suite file, and the browserName differs from pre-established browser,  browser
		if (!("").equals(this.browserName) && !runtimeData.browserName.toLowerCase().equals(this.browserName.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserName \"%s\" with \"%s\">%n", id, testName, runtimeData.browserName, browserName);
			runtimeData.browserName = browserName;
		}

		// if there is a browserVersion passed via TestNG suite file, and the browserVersion differs from pre-established browserVersion,  browserVersion
		if (!("").equals(this.browserVersion) && !runtimeData.browserVersion.toLowerCase().equals(this.browserVersion.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.browserVersion, browserVersion);
			runtimeData.browserVersion = browserVersion;
		}

		// if there is a resolution passed via TestNG suite file, and the resolution differs from pre-established resolution,  resolution
		if (!("").equals(this.resolution) && !runtimeData.resolution.toLowerCase().equals(this.resolution.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding resolution \"%s\" with \"%s\">%n", id, testName, runtimeData.resolution, resolution);
			runtimeData.resolution = resolution;
		}

		// if there is a location passed via TestNG suite file, and the location differs from pre-established location,  location
		if (!("").equals(this.location) && !runtimeData.location.toLowerCase().equals(this.location.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding location \"%s\" with \"%s\">%n", id, testName, runtimeData.location, location);
			runtimeData.location = location;
		}

		// if there is a deviceName passed via TestNG suite file, and the deviceName differs from pre-established deviceName,  deviceName
		if (!("").equals(this.deviceName) && !runtimeData.deviceName.toLowerCase().equals(this.deviceName.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding deviceName \"%s\" with \"%s\">%n", id, testName, runtimeData.deviceName, deviceName);
			runtimeData.deviceName = this.deviceName;
		}

		// if there is a model passed via TestNG suite file, and the model differs from pre-established model,  model
		if (!("").equals(this.model) && !runtimeData.model.toLowerCase().equals(this.model.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding model \"%s\" with \"%s\">%n", id, testName, runtimeData.model, model);
			runtimeData.model = this.model;
		}

		// if there is an appPackage passed via TestNG suite file, and the appPackage differs from pre-established appPackage,  appPackage
		if (!("").equals(this.appPackage) && !runtimeData.appPackage.toLowerCase().equals(this.appPackage.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appPackage \"%s\" with \"%s\">%n", id, testName, runtimeData.appPackage, appPackage);
			runtimeData.appPackage = this.appPackage;
		}

		// if there is an appActivity passed via TestNG suite file, and the appActivity differs from pre-established appActivity,  appActivity
		if (!("").equals(this.appActivity) && !runtimeData.appActivity.toLowerCase().equals(this.appActivity.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appActivity \"%s\" with \"%s\">%n", id, testName, runtimeData.appActivity, appActivity);
			runtimeData.appActivity = this.appActivity;
		}

		// if there is a bundleId passed via TestNG suite file, and the bundleId differs from pre-established bundleId,  bundleId
		if (!("").equals(this.bundleId) && !runtimeData.bundleId.toLowerCase().equals(this.bundleId.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding bundleId \"%s\" with \"%s\">%n", id, testName, runtimeData.bundleId, bundleId);
			runtimeData.bundleId = this.bundleId;
		}

		// if there is a mobileType passed via TestNG suite file, and the mobileType differs from pre-established bundleId,  bundleId
		if (!("").equals(this.mobileType) && !runtimeData.bundleId.toLowerCase().equals(this.mobileType.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding mobileType \"%s\" with \"%s\">%n", id, testName, runtimeData.mobileType, mobileType);
			runtimeData.mobileType = this.mobileType;
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
				caps.setCapability("appium:automationName", "uiautomator2");
				//caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				//caps.setCapability("skipUnlock", true);
				caps.setCapability("appium:deviceName", runtimeData.deviceName);
				// required
				caps.setCapability("platformName", runtimeData.platformName);
				caps.setCapability("appium:platformVersion", runtimeData.platformVersion);
				caps.setCapability("appium:appPackage", runtimeData.appPackage);
				caps.setCapability("appium:appActivity", runtimeData.appActivity);
				break;
			case "androidchrome":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("appium:automationName", "uiautomator2");
				//caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				//caps.setCapability("skipUnlock", true);
				caps.setCapability("appium:deviceName", runtimeData.deviceName);
				// required
				caps.setCapability("appium:platformName", runtimeData.platformName);
				caps.setCapability("appium:platformVersion", runtimeData.platformVersion);
				caps.setBrowserName("chrome");
				break;
			case "iosnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("appium:automationName", "XCUITest");
				caps.setCapability("appium:deviceName", runtimeData.deviceName);
				//required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("appium:platformVersion", runtimeData.platformVersion);
				caps.setCapability("bundleId", runtimeData.bundleId);
				break;
			case "iossafari":
				//caps = DesiredCapabilities.iphone();
				caps = new DesiredCapabilities();
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
				caps.setCapability("real_mobile", "true");
				caps.setBrowserName("Chrome");
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("os_version", runtimeData.platformVersion);
				caps.setCapability("appPackage", runtimeData.appPackage);
				caps.setCapability("appActivity", runtimeData.appActivity);
				break;
			case "androidchrome":
				//caps = DesiredCapabilities.android();
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "appium");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				caps.setCapability("deviceName", runtimeData.deviceName);
				// required
				//caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				//caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("os_version", "10.0");
				caps.setCapability("real_mobile", "true");
				caps.setCapability("browserstack.appium_version", "1.17.0");
				caps.setCapability("browserstack.local", "false");
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
				caps.setCapability("os_version", runtimeData.platformVersion);
				caps.setCapability("bundleId", runtimeData.bundleId);
				break;
			case "iossafari":
				//caps = DesiredCapabilities.iphone();
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "XCUITest");
				caps.setCapability("deviceName", runtimeData.deviceName);
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("os_version", runtimeData.platformVersion);
				caps.setBrowserName("Safari");
				break;
			}
		}

		// setup clients for use with headspin cloud
		if (runtimeData.gridType.toLowerCase().equals("headspin")) {

			System.out.format("[LOG]: <[%s:%s] headspin grid detected>%n", id, testName);

			switch (runtimeData.browserName.toLowerCase()) {
			//Desktop
			case "desktopchrome":

				System.out.format("[LOG]: <[%s:%s] setting chrome options>%n", id, testName);

				options = new ChromeOptions();
				((ChromeOptions) options).setCapability(CapabilityType.BROWSER_NAME, "chrome");
				//((ChromeOptions) options).setCapability("platformName", runtimeData.platformName);
				//((ChromeOptions) options).setCapability("platform", runtimeData.platform);
				((ChromeOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				((ChromeOptions) options).setCapability("version", runtimeData.browserVersion);
				((ChromeOptions) options).setCapability("headspin:InitialScreenSize", "{\"width\": \"1920\", \"height\": \"1080\"}");
				((ChromeOptions) options).setCapability("headspin:capture", true);
				((ChromeOptions) options).setCapability("headspin:testName", "Chrome 80");
				//((ChromeOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				//((ChromeOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				//((ChromeOptions) options).setCapability("resolution", runtimeData.resolution);
				//((ChromeOptions) options).setCapability("location", runtimeData.location);
				((ChromeOptions) options).setCapability("headspinHost","https://dev-us-pao-0.headspin.io:9096/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub");
				break;
			case "desktopfirefox":
				options = new FirefoxOptions();
				((FirefoxOptions) options).setCapability(CapabilityType.BROWSER_NAME, "firefox");
				//((FirefoxOptions) options).setCapability("platformName", runtimeData.platformName);
				//((FirefoxOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				//((FirefoxOptions) options).setCapability("resolution", runtimeData.resolution);
				//((FirefoxOptions) options).setCapability("location", runtimeData.location);
				((FirefoxOptions) options).setCapability("headspin:InitialScreenSize", "{\"width\": \"1920\", \"height\": \"1080\"}");
				((FirefoxOptions) options).setCapability("headspin:capture", true);
				((FirefoxOptions) options).setCapability("headspin:testName", "Firefox 70");
				// palo alto
//				((FirefoxOptions) options).setCapability("headspinHost","https://dev-us-pao-0.headspin.io:9096/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub");
//				((FirefoxOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
//				((FirefoxOptions) options).setCapability("version", runtimeData.browserVersion);
				// gb
				((FirefoxOptions) options).setCapability("headspinHost","https://gb-lhr.headspin.io:9092/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub");
				((FirefoxOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				((FirefoxOptions) options).setCapability("version", runtimeData.browserVersion);
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
				((SafariOptions) options).setCapability(CapabilityType.BROWSER_NAME, "safari");
				//((SafariOptions) options).setCapability("platformName", runtimeData.platformName);
				//((SafariOptions) options).setCapability("platformVersion", runtimeData.platformVersion);
				((SafariOptions) options).setCapability(CapabilityType.BROWSER_VERSION, runtimeData.browserVersion);
				((SafariOptions) options).setCapability("version", runtimeData.browserVersion);
				((SafariOptions) options).setCapability("headspin:InitialScreenSize", "{\"width\": \"1920\", \"height\": \"1080\"}");
				((SafariOptions) options).setCapability("headspin:capture", true);
				((SafariOptions) options).setCapability("headspin:testName", "Safari 13");
				//((SafariOptions) options).setCapability("resolution", runtimeData.resolution);
				//((SafariOptions) options).setCapability("location", runtimeData.location);
				//((SafariOptions) options).setCapability("openDeviceTimeout", 1);
				((SafariOptions) options).setCapability("headspinHost","https://dev-us-pao-0.headspin.io:9095/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub");
				break;

			//Mobile
			case "androidnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "Appium");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				caps.setCapability("deviceName", runtimeData.deviceName);
				caps.setCapability("real_mobile", "true");
				caps.setBrowserName("Chrome");
				// required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("os_version", runtimeData.platformVersion);
				caps.setCapability("appPackage", runtimeData.appPackage);
				caps.setCapability("appActivity", runtimeData.appActivity);
				break;
			case "androidchrome":
				//caps = DesiredCapabilities.android();
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "appium");
				caps.setCapability("takesScreenshot", true);
				caps.setCapability("skipUnlock", true);
				caps.setCapability("deviceName", "Pixel 4");
				caps.setCapability("udid", "9B051FFAZ0060X");
				caps.setCapability("autoAcceptAlerts", true);
				caps.setCapability("platformName", "Android");
				caps.setBrowserName("Chrome");
				caps.setCapability("headspin:capture", true);
				caps.setCapability("headspin:testName", "Android 10.0 Chrome");
				caps.setCapability("lockUrl", "https://286d8226ec894f798c3394d33d3af4ab@api-dev.headspin.io/v0/adb/9B051FFAZ0060X/lock");
				caps.setCapability("unlockUrl","https://286d8226ec894f798c3394d33d3af4ab@api-dev.headspin.io/v0/adb/9B051FFAZ0060X/unlock");
				caps.setCapability("headspinHost","https://dev-us-pao-0.headspin.io:7049/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub");
				break;
			case "iosnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "XCUITest");
				caps.setCapability("deviceName", runtimeData.deviceName);
				//required
				caps.setCapability(CapabilityType.PLATFORM_NAME, runtimeData.platformName);
				caps.setCapability("platformVersion", runtimeData.platformVersion);
				caps.setCapability("os_version", runtimeData.platformVersion);
				caps.setCapability("bundleId", runtimeData.bundleId);
				break;
			case "iossafari":
				//caps = DesiredCapabilities.iphone();
				caps = new DesiredCapabilities();
				caps.setCapability("automationName", "XCUITest");
				caps.setCapability("platformName", "iOS");
				caps.setBrowserName("Safari");
				caps.setCapability("headspin:capture", true);
				caps.setCapability("headspin:testName", "iOS 13 Safari");

				// palo alto
//				caps.setCapability("platformVersion", "13.5");
//				caps.setCapability("udid", "2e8173b26ea8849a0b923d05b8eae5b188beb961");
//				caps.setCapability("deviceName", "iPhone 8 Plus");
//				caps.setCapability("lockUrl", "https://286d8226ec894f798c3394d33d3af4ab@api-dev.headspin.io/v0/idevice/2e8173b26ea8849a0b923d05b8eae5b188beb961/lock");
//				caps.setCapability("unlockUrl","https://286d8226ec894f798c3394d33d3af4ab@api-dev.headspin.io/v0/idevice/2e8173b26ea8849a0b923d05b8eae5b188beb961/unlock");
//				caps.setCapability("headspinHost", "https://dev-us-pao-1.headspin.io:7017/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub");
				// gb
				caps.setCapability("platformVersion", "13.5");
				caps.setCapability("udid", "00008020-000374DC3A45002E");
				caps.setCapability("deviceName", "iPhone XS");
				caps.setCapability("lockUrl", "https://286d8226ec894f798c3394d33d3af4ab@api-dev.headspin.io/v0/idevice/00008020-000374DC3A45002E/lock");
				caps.setCapability("unlockUrl","https://286d8226ec894f798c3394d33d3af4ab@api-dev.headspin.io/v0/idevice/00008020-000374DC3A45002E/unlock");
				caps.setCapability("headspinHost", "https://dev-gb-lhr-0.headspin.io:7029/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub");
				break;
			}
		}

		// setup clients for use with browserstack cloud
		if (runtimeData.gridType.toLowerCase().equals("browserstack")) {

			System.out.format("[LOG]: <[%s:%s] browserstack grid detected>%n", id, testName);

			switch (runtimeData.browserName.toLowerCase()) {

			//Desktop
			case "desktopchrome":
				options = new ChromeOptions();
				((ChromeOptions) options).setCapability("os", runtimeData.platform);
				((ChromeOptions) options).setCapability("os_version", runtimeData.platformVersion);
				((ChromeOptions) options).setCapability(CapabilityType.BROWSER_NAME, "chrome");
				((ChromeOptions) options).setCapability(CapabilityType.BROWSER_VERSION, BrowserStackIntegration.selectDesktop_latestBrowserVersionPerOsOsVersionBrowser(runtimeData.platform, runtimeData.platformVersion, "chrome"));
				((ChromeOptions) options).setCapability("name", id+":"+testName);
				((ChromeOptions) options).setCapability("browserstack.local", gc.browserStackLocal);
				((ChromeOptions) options).setCapability("browserstack.selenium_version", gc.browserStackSeVer);
				break;

			case "desktopfirefox":
				options = new FirefoxOptions();
				((FirefoxOptions) options).setCapability("os", runtimeData.platform);
				((FirefoxOptions) options).setCapability("os_version", runtimeData.platformVersion);
				((FirefoxOptions) options).setCapability(CapabilityType.BROWSER_NAME, "firefox");
				((FirefoxOptions) options).setCapability(CapabilityType.BROWSER_VERSION, BrowserStackIntegration.selectDesktop_latestBrowserVersionPerOsOsVersionBrowser(runtimeData.platform, runtimeData.platformVersion, "firefox"));
				((FirefoxOptions) options).setCapability("name", id+":"+testName);
				((FirefoxOptions) options).setCapability("browserstack.local", gc.browserStackLocal);
				((FirefoxOptions) options).setCapability("browserstack.selenium_version", gc.browserStackSeVer);
				break;

			case "desktopedge":
				options = new EdgeOptions();
				((EdgeOptions) options).setCapability("os", runtimeData.platform);
				((EdgeOptions) options).setCapability("os_version", runtimeData.platformVersion);
				((EdgeOptions) options).setCapability(CapabilityType.BROWSER_NAME, "edge");
				((EdgeOptions) options).setCapability(CapabilityType.BROWSER_VERSION, BrowserStackIntegration.selectDesktop_latestBrowserVersionPerOsOsVersionBrowser(runtimeData.platform, runtimeData.platformVersion, "edge"));
				((EdgeOptions) options).setCapability("name", id+":"+testName);
				((EdgeOptions) options).setCapability("browserstack.local", gc.browserStackLocal);
				((EdgeOptions) options).setCapability("browserstack.selenium_version", gc.browserStackSeVer);

				break;

			case "desktopie":
				options = new InternetExplorerOptions();
				((InternetExplorerOptions) options).setCapability("os", runtimeData.platform);
				((InternetExplorerOptions) options).setCapability("os_version", runtimeData.platformVersion);
				((InternetExplorerOptions) options).setCapability(CapabilityType.BROWSER_NAME, "ie");
				((InternetExplorerOptions) options).setCapability(CapabilityType.BROWSER_VERSION, BrowserStackIntegration.selectDesktop_latestBrowserVersionPerOsOsVersionBrowser(runtimeData.platform, runtimeData.platformVersion, "ie"));
				((InternetExplorerOptions) options).setCapability("name", id+":"+testName);
				((InternetExplorerOptions) options).setCapability("browserstack.local", gc.browserStackLocal);
				((InternetExplorerOptions) options).setCapability("browserstack.selenium_version", gc.browserStackSeVer);
				((InternetExplorerOptions) options).setCapability("browserstack.ie_driver", gc.browserStackIeDriverVer);
				break;

			case "desktopsafari":
				options = new SafariOptions();
				((SafariOptions) options).setCapability("os", runtimeData.platform);
				((SafariOptions) options).setCapability("os_version", runtimeData.platformVersion);
				((SafariOptions) options).setCapability(CapabilityType.BROWSER_NAME, "safari");
				((SafariOptions) options).setCapability(CapabilityType.BROWSER_VERSION, BrowserStackIntegration.selectDesktop_latestBrowserVersionPerOsOsVersionBrowser(runtimeData.platform, runtimeData.platformVersion, "safari"));
				((SafariOptions) options).setCapability("name", id+":"+testName);
				((SafariOptions) options).setCapability("browserstack.local", gc.browserStackLocal);
				((SafariOptions) options).setCapability("browserstack.selenium_version", gc.browserStackSeVer);
				break;

			//Mobile
			case "androidnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "Appium");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				// required
				//caps.setCapability("os", runtimeData.platform);
				caps.setCapability("os_version", BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform, BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				caps.setCapability("device", BrowserStackIntegration.selectMobile_devicePerOsOsVersion(runtimeData.platform, BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform), BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				//caps.setCapability("appPackage", runtimeData.appPackage);
				caps.setCapability("app", runtimeData.appPackage);
				//caps.setCapability("appActivity", runtimeData.appActivity);
				// browserstack specific
				caps.setCapability("name", id+":"+testName);
				caps.setCapability("real_mobile", gc.browserStackRealMobile);
				caps.setCapability("browserstack.local", gc.browserStackLocal);
				caps.setCapability("browserstack.appium_version", gc.browserStackApVer);
				caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
				break;

			case "androidchrome":
				//caps = DesiredCapabilities.android();
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "Appium");
				caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				caps.setCapability("skipUnlock", true);
				// required
				//caps.setCapability("os", runtimeData.platform);
				caps.setCapability("os_version", BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform, BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				caps.setCapability("device", BrowserStackIntegration.selectMobile_devicePerOsOsVersion(runtimeData.platform, BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform), BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				caps.setBrowserName("Chrome");
				// browserstack specific
				caps.setCapability("name", id+":"+testName);
				caps.setCapability("real_mobile", gc.browserStackRealMobile);
				caps.setCapability("browserstack.local", gc.browserStackLocal);
				caps.setCapability("browserstack.appium_version", gc.browserStackApVer);
				caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
				break;

			case "iosnative":
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "XCUITest");
				//required
				//caps.setCapability("os", runtimeData.platform);
				caps.setCapability("os_version", BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform, BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				caps.setCapability("device", BrowserStackIntegration.selectMobile_devicePerOsOsVersion(runtimeData.platform, BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform), BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				//caps.setCapability("bundleId", runtimeData.bundleId);
				caps.setCapability("app", runtimeData.bundleId);
				// browserstack specific
				caps.setCapability("name", id+":"+testName);
				caps.setCapability("real_mobile", gc.browserStackRealMobile);
				caps.setCapability("browserstack.local", gc.browserStackLocal);
				caps.setCapability("browserstack.appium_version", gc.browserStackApVer);
				caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
				break;

			case "iossafari":
				//caps = DesiredCapabilities.iphone();
				caps = new DesiredCapabilities();
				// optional
				caps.setCapability("automationName", "XCUITest");
				// required
				//caps.setCapability("os", runtimeData.platform);
				caps.setCapability("os_version", BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform, BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				caps.setCapability("device", BrowserStackIntegration.selectMobile_devicePerOsOsVersion(runtimeData.platform, BrowserStackIntegration.selectMobile_latestOs(runtimeData.platform), BrowserStack.MobileType.getMobileType(runtimeData.mobileType)));
				caps.setBrowserName("Safari");
				// browserstack specific
				caps.setCapability("name", id+":"+testName);
				caps.setCapability("real_mobile", gc.browserStackRealMobile);
				caps.setCapability("browserstack.local", gc.browserStackLocal);
				caps.setCapability("browserstack.appium_version", gc.browserStackApVer);
				caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
				//caps.setCapability("nativeWebTap", true);
				break;

			}
		}

		if (null != options) {
			System.out.format("[LOG]: <[%s:%s] %s>%n", id, testName, options.toString());
		}

		if (null != caps) {
			System.out.format("[LOG]: <[%s:%s] %s>%n", id, testName, caps.toString());
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
					wde.printStackTrace();
					Assert.fail("Unable to start WebDriver: "+wde.getLocalizedMessage());
				}

			// using Perfecto grid
			} else if (runtimeData.gridType.toLowerCase().equals("perfecto")) {

				//Perfecto
				System.out.println("set perfecto-specific remote session caps");
				((MutableCapabilities) options).setCapability("securityToken", gc.perfectoSecurityToken);

				//SRF (Storm runner functional specific caps)
				//((MutableCapabilities) options).setCapability("SRF_CLIENT_ID", gc.srfId);
				//((MutableCapabilities) options).setCapability("SRF_CLIENT_SECRET", gc.srfPass);

				try {
					//Perfecto
					System.out.println("start perfecto remote session");
					driver.set(new RemoteWebDriver(new URL(gc.perfectoHost), options));

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

			// using browserstack grid
			} else if (runtimeData.gridType.toLowerCase().equals("browserstack")) {

				try {
					//BrowserStack
					System.out.println("start browserstack remote session");
					driver.set(new RemoteWebDriver(new URL(gc.browserStackHost), options));

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
					wde.printStackTrace();
					Assert.fail("Unable to start WebDriver");
				}

			} else if (runtimeData.gridType.toLowerCase().equals("headspin")) {

				try {
					// HeadSpin
					System.out.println("start headspin desktop session");

					String host = options.getCapability("headspinHost").toString();
					System.out.format("[LOG]: <[%s:%s] hostUrl: %s>%n", id, testName, host);

					//driver.set(new RemoteWebDriver(new URL(gc.headSpinHost), options));
					driver.set(new RemoteWebDriver(new URL(host), options));

					if (!(options instanceof SafariOptions)) {
						getDriver().manage().window().maximize();
					}

					ss = new ScreenShot(getDriver(), id, testName);
					generic = new Generic(getDriver(), ss, props, id, testName);

					System.out.format("[LOG]: <[%s:%s] %s>%n", id, testName, getDriver().getCapabilities());
					System.out.format("[LOG]: <[%s:%s] initializing page objects>%n", id, testName);
					initializePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
					Assert.fail("Unable to start WebDriver");
				} catch (WebDriverException wde ) {
					wde.printStackTrace();
					Assert.fail("Unable to start WebDriver");
				}

			} else {
				// error, no grid identified
			}

		// initialize mobile client WebDriver (Appium)
		} else if (null != caps) {

			System.out.format("[LOG]: <[%s:%s] %s client detected on %s grid>%n", id, testName, runtimeData.browserName, runtimeData.gridType);

			if (runtimeData.browserName.equalsIgnoreCase("androidchrome")) {
				options = new ChromeOptions();
				((ChromeOptions) options).setExperimentalOption("w3c", false);
				caps.merge(options);
			}

			// using local appium server
			if (runtimeData.gridType.toLowerCase().equals("local")) {

				if (runtimeData.platformName.toLowerCase().equals("android")) {
					try {
						mDriver.set(new AndroidDriver<MobileElement>(new URL(gc.appiumHost), caps));
						mss = new MobileScreenShot(getMobileDriver(AndroidDriver.class), id, testName);
						mGeneric = new MobileGeneric(getMobileDriver(AndroidDriver.class), mss, props, id, testName);

						System.out.format("[LOG]: <[%s:%s] initializing Andoird mobile page objects>%n", id, testName);
						initializeMobilePageObjects();

					} catch (MalformedURLException mfu) {
						mfu.printStackTrace();
					}
					catch (WebDriverException wde ) {
						wde.printStackTrace();
						Assert.fail("Unable to start WebDriver");
					}
				} else {
					try {
						mDriver.set(new IOSDriver<MobileElement>(new URL(gc.appiumHost), caps));
						mss = new MobileScreenShot(getMobileDriver(IOSDriver.class), id, testName);
						mGeneric = new MobileGeneric(getMobileDriver(IOSDriver.class), mss, props, id, testName);

						System.out.format("[LOG]: <[%s:%s] initializing iOS mobile page objects>%n", id, testName);
						initializeMobilePageObjects();

					} catch (MalformedURLException mfu) {
						mfu.printStackTrace();
					}
					catch (WebDriverException wde ) {
						wde.printStackTrace();
						Assert.fail("Unable to start WebDriver");
					}
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

					mss = new MobileScreenShot(getMobileDriver(AppiumDriver.class), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(AppiumDriver.class), mss, props, id, testName);

					System.out.format("[LOG]: <[%s:%s] initializing mobile page objects>%n", id, testName);
					initializeMobilePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
				}
				catch (WebDriverException wde ) {
					wde.printStackTrace();
					Assert.fail("Unable to start WebDriver");
				}

			// using browserstack grid
			} else if (runtimeData.gridType.toLowerCase().equals("browserstack")) {

				try {

					//Browserstack
					mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.browserStackHost), caps));

					mss = new MobileScreenShot(getMobileDriver(AppiumDriver.class), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(AppiumDriver.class), mss, props, id, testName);

					System.out.format("[LOG]: <[%s:%s] initializing mobile page objects>%n", id, testName);
					initializeMobilePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
				}
				catch (WebDriverException wde ) {
					wde.printStackTrace();
					Assert.fail("Unable to start WebDriver");
				}

			// using headspin grid
			} else if (runtimeData.gridType.toLowerCase().equals("headspin")) {

				try {

					// HeadSpin
					System.out.println("start headspin mobile session");

					// lock session
					//lockHeadSpinSession((String) caps.getCapability("lockUrl"), (String) caps.getCapability("udid"));

					//HeadSpin
					//mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.headSpinHost), caps));
					String host = caps.getCapability("headspinHost").toString();
					System.out.format("[LOG]: <[%s:%s] hostUrl: %s>%n", id, testName, host);
					mDriver.set(new AppiumDriver<MobileElement>(new URL(host), caps));

					mss = new MobileScreenShot(getMobileDriver(AppiumDriver.class), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(AppiumDriver.class), mss, props, id, testName);

					System.out.format("[LOG]: <[%s:%s] initializing mobile page objects>%n", id, testName);
					initializeMobilePageObjects();

				} catch (MalformedURLException mfu) {
					mfu.printStackTrace();
				}
				catch (WebDriverException wde ) {
					wde.printStackTrace();
					Assert.fail("Unable to start WebDriver");
				}

			} else {
				// error, no grid identified
			}

		}

		System.out.format("[LOG]: <[%s:%s] =====Start test=====>%n", id, testName);
	}


	/**
	 *  Add page objects here
	 */
	@Step("Initialize Page Objects.")
	private void initializePageObjects() {

		honda_searchPO = new Honda_SearchPO(generic, ss, id, testName);
		homePagePO = new HomePagePO(generic, ss, id, testName);
		iFrame = new IFramePO(generic, ss, id, testName);
		logInPage = new LoginPagePO(generic, ss, runtimeData, id, testName);
		registrationPagePO = new RegistrationPagePO(generic, ss, runtimeData, id, testName, iFrame);

		System.out.format("[LOG]: <[%s:%s] page objects loaded>%n", id, testName);

	}


	/**
	 *  Add mobile page objects here
	 */
	@Step("Initialize Mobile Page Objects.")
	private void initializeMobilePageObjects() {

		//homePagePO = new HomePagePO(mGeneric, ss, id, testName);

		System.out.format("[LOG]: <[%s:%s] mobile page objects loaded>%n", id, testName);

	}


	/**
	 *  Quit RemoteWebDiver (dismiss/close browser). Destroy thread-safe driver.
	 */
	@AfterMethod(description="Quit and destroy thread-safe driver.")
	@Step("Quit browser.")
	protected void tearDown() {


		if (getDriver() instanceof RemoteWebDriver) {

			// post PASSED to headspin perf API
			if (runtimeData.gridType.toLowerCase().equals("headspin")) {

				// get desktop session id
				String sessionId = getDriver().getSessionId().toString();

				// call perf API
				setHeadSpinPerfStatus(sessionId, "Passed");

			}

			// kill driver
			getDriver().quit();
			driver.remove();

		} else if (getMobileDriver(AppiumDriver.class) instanceof AppiumDriver<?>) {

			// unlock headspin mobile session
			if (runtimeData.gridType.toLowerCase().equals("headspin")) {

				// get desktop session id
				String sessionId = getMobileDriver(AppiumDriver.class).getSessionId().toString();

				// call perf API
				setHeadSpinPerfStatus(sessionId, "Passed");

				// unlock mobile session
				// unlockHeadSpinSession((String) caps.getCapability("unlockUrl"), (String) caps.getCapability("udid"));

			}

			// kill driver
			getMobileDriver(AppiumDriver.class).quit();
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
	protected <T extends WebDriver> T getMobileDriver(Class<T> type) {
		return type.cast(mDriver.get());
	}


	// lock a device
	private void lockHeadSpinSession(String lockUrl, String udid) {

		System.out.format("Locking %s on %s...", udid, lockUrl);

		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(null, new byte[0]);

		Request request = new Request.Builder()
				.url(lockUrl)
				.method("POST", body)
				// Bearer access token can be obtained from Headspin Settings - top right corner of UI or you can also take from Appium URL
				.addHeader("Authorization", "Bearer 286d8226ec894f798c3394d33d3af4ab")
				.addHeader("cache-control", "no-cache")
				.build();

		try {

			Response response = client.newCall(request).execute();
			System.out.format(" %s - %s%n", response.code(), response.message());
			String responseBody = response.body().string();
			System.out.format("%s%n", responseBody);

		} catch (IOException e) {
			System.out.format("Failed to locked.%n", udid);
			e.printStackTrace();
		}

		System.out.format("Locked.%n");

	}


	// lock a device
	private void unlockHeadSpinSession(String unlockUrl, String udid) {


		System.out.format("Unlocking %s on %s...", udid, unlockUrl);

		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(null, new byte[0]);

		Request request = new Request.Builder()
				.url(unlockUrl)
				.method("POST", body)
				// Bearer access token can be obtained from Headspin Settings - top right corner of UI or you can also take from Appium URL
				.addHeader("Authorization", "Bearer 286d8226ec894f798c3394d33d3af4ab")
				.addHeader("cache-control", "no-cache")
				.build();

		try {

			Response response = client.newCall(request).execute();
			System.out.format(" %s - %s%n", response.code(), response.message());
			String responseBody = response.body().string();
			System.out.format("%s%n", responseBody);

		} catch (IOException e) {
			System.out.format("Failed to unlocked.%n", udid);
			e.printStackTrace();
		}

		System.out.format("Unlocked.%n");

	}


	// set headspin status
	private void setHeadSpinPerfStatus(String sessionId, String status) {

		System.out.format("Setting session '%s' status to: '%s'...", sessionId, status);

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");

		RequestBody body = RequestBody.create(mediaType, ""
				+ "{"
				+ "\"session_id\": \"" + sessionId + "\","
				+ "\"status\": \"" + status + "\","
				+ " \"data\": ["
//					+ "{\"key\": \"kpi1_ms\", \"value\": \"12412\"},"
//					+ "{\"key\": \"kpi2_ms\", \"value\": \"13455\"}"
				+ "]}"
			);

		Request request = new Request.Builder()
			.url("https://286d8226ec894f798c3394d33d3af4ab@api-dev.headspin.io/v0/perftests/upload")
			.post(body)
			.addHeader("Content-Type", "application/json")
			.addHeader("Authorization", "Bearer 286d8226ec894f798c3394d33d3af4ab")
			.addHeader("cache-control", "no-cache")
			.build();

		try {

			Response response = client.newCall(request).execute();
			System.out.format(" %s - %s%n", response.code(), response.message());
			String responseBody = response.body().string();
			System.out.format("%s%n", responseBody);

		} catch (IOException e) {
			System.out.format("Failed to set session '%s' status to: '%s'.%n", sessionId, status);
			e.printStackTrace();
		}


	}


}
