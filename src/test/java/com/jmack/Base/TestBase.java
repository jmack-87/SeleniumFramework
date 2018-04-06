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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.qameta.allure.Step;

public class TestBase {
	
	protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	protected Generic generic;
	protected DataExtractor runtimeData = new DataExtractor();
	protected static GlobalConstants gc = new GlobalConstants();
	
	private Capabilities options = null;
	private Properties testReference = new Properties();
	private InputStream testReferenceFile = null;
	
	private Date date = new Date();
	private Long ts = date.getTime();
	private String id;
	private String testName;
	
	/**
	 * Initialize RemoteWebDriver, gather test data (from JSON) 	
	 * @param testMethod testNG supplied test object
	 */
	@BeforeMethod(description="Extract test data from JSON, create thread-safe WebDriver.")
	@Step("Initialize test.")
	protected void setUp(Method testMethod) {
		this.testName = testMethod.getName();
		
		this.id = ts.toString();
		this.id = this.id.substring(id.length()-3 );
		
		try {
			runtimeData.initialize(gc, this.testName, this.id);
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		}
		
		if (runtimeData.browser.toLowerCase().equals("random")) {
			Random r = new Random();
			String[] browsers = {"chrome","firefox","edge","ie"};
			runtimeData.browser = browsers[r.nextInt(browsers.length)];
		}
		
		switch (runtimeData.browser.toLowerCase()) {
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
			break;
		case "ie":
			options = new InternetExplorerOptions();
			break;
		}
		
		//System.out.format("[LOG]: <[%s:%s] using %s; headless: %s;>\n", this.id, this.testName, runtimeData.browser, runtimeData.headless);
		
		try {
			testReferenceFile = new FileInputStream(gc.testReferenceFilePath);
			testReference.load(testReferenceFile);
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
		
		try {
			driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options));
			getDriver().manage().window().maximize();
			generic = new Generic(getDriver(), gc, testReference, testMethod.getName(), id);
		} catch (MalformedURLException m) {
			m.printStackTrace();
		}

	}
	
	/** 
	 *  Quit RemoteWebDiver (dismiss/close browser). Destroy thread-safe driver.
	 */
	@AfterMethod(description="Quit and destroy thread-safe driver.")
	@Step("Quit browser.")
	protected void tearDown() {
		
		getDriver().quit();
		driver.remove();
		
	}
	
	
	private RemoteWebDriver getDriver() {
		
		return driver.get();
		
	}
	
	
}
