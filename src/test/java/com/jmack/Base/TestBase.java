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

import com.jmack.Base.PageObjects.HomePagePO;
import com.jmack.Base.PageObjects.IFramePO;
import com.jmack.Base.PageObjects.LogInPagePO;
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

	private String id;
	private String testName;

	// Helpers
	protected Generic generic;
	protected MobileGeneric mGeneric;
	private ScreenShot ss = null;
	private MobileScreenShot mss = null;
	protected JsonDataExtractor jsonDataExtractor;
	protected TestNGParameterExtractor testNGParameterExtractor;
	protected static GlobalConstants gc = new GlobalConstants();
	protected static RuntimeData runtimeData = new RuntimeData();
	protected ExcelDataExtractor excelDataExtractor;

	// Page Objects
	protected HomePagePO homePage;
	protected IFramePO iFrame;
	protected LogInPagePO logInPage;
	protected RegistrationPagePO registrationPage;


	/**
	 * Initialize RemoteWebDriver, gather test data (from JSON)
	 * @param testMethod testNG supplied test object
	 * @param browserNameOverride optional TestNG input from test suite
	 */
	@BeforeMethod(description="Extract test data from JSON, create thread-safe WebDriver.")
	@Step("Initialize test.")
	@Parameters({"applicationUnderTest", "excelDataFile"})
	protected void setUp(Method testMethod, @Optional String applicationUnderTest, @Optional String excelDataFile) {

		this.applicationUnderTest = applicationUnderTest == null ? null : applicationUnderTest;
		this.excelDataFile = excelDataFile == null ? null : excelDataFile;

		testName = testMethod.getName();

		// tag the test with the last three digits of timestamp
		id = ts.toString();
		id = id.substring(id.length() - 3);

		// START JSON DATA EXTRACTION
		jsonDataExtractor = new JsonDataExtractor(id, testName, gc);
		// START JSON DATA EXTRACTION

		// START EXCEL OVERRIDES
		if (!(null == this.excelDataFile || null == this.applicationUnderTest)) {
			excelDataExtractor = new ExcelDataExtractor(this.applicationUnderTest, this.excelDataFile, this.jsonDataExtractor, this.id, this.testName);
		}
		// END EXCEL OVERRIDES


		// START TESTNG PARAMETER OVERRIDES
		testNGParameterExtractor = new TestNGParameterExtractor(id, testName, runtimeData);
		// END TESTNG PARAMETER OVERRIDES


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
					System.out.format("[LOG]: <[%s:%s] properties loaded.>%n", id, testName);
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}

		// Desktop
		if (null != options) {

			System.out.format("[LOG]: <[%s:%s] %s browser detected on %s grid>%n", id, testName, runtimeData.browserName, runtimeData.gridType);

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

				} catch (MalformedURLException m) {
					m.printStackTrace();
				}

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

					initializePageObjects();

				} catch (MalformedURLException m) {
					m.printStackTrace();
				}
			} else {
				// error, no grid identified
			}

		// Mobile
		} else if (null != caps) {
			if (runtimeData.gridType.toLowerCase().equals("local")) {
				try {
					mDriver.set(new AppiumDriver<MobileElement>(new URL(gc.appiumHost), caps));
					mss = new MobileScreenShot(getMobileDriver(), id, testName);
					mGeneric = new MobileGeneric(getMobileDriver(), mss, props, id, testName);

					initializeMobilePageObjects();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (runtimeData.gridType.toLowerCase().equals("perfecto")) {

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

		homePage = 			new HomePagePO				(generic, ss, id, testName);
		iFrame = 			new IFramePO				(generic, ss, id, testName);
		logInPage = 		new LogInPagePO				(generic, ss, id, testName);
		registrationPage = 	new RegistrationPagePO		(generic, ss, runtimeData, id, testName, iFrame); // must be after IFramePO

		System.out.format("[LOG]: <[%s:%s] page objects loaded>%n", id, testName);

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
