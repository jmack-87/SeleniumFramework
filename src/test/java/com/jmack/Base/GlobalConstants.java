package com.jmack.Base;


public class GlobalConstants {

	protected final String testDataFileName = "TestParametersJSON.json";
	protected final String testReferenceFileName = "testReference.properties";

	protected final String testDataFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + testDataFileName; // runtime test data
	protected final String testReferenceFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + testReferenceFileName; // locators/strings

	protected final String locatorSeparator = "@@@";

	protected final String perfectoSecurityToken = "";
	protected final String perfectoHost = "https://ibmgbs.perfectomobile.com/nexperience/perfectomobile/wd/hub";

	protected final String appiumHost = "http://localhost:4723/wd/hub";
	protected final String gridHost = "http://localhost:4444/wd/hub";

	protected final String proxy = "";

	//for running local instances
	protected final String geckoDriver = "";
	protected final String chromeDriver = "";
	protected final String ieDriver = "";

	protected final String firefoxBinary = "";
	protected final String firefoxBrowserLogFile = "target\\fflog.txt";

	protected final int defaultTimeOut = 30;
	protected final int defaultPollingRate = 1000;
	protected final String compoundLocatorPlaceholder = "~~~";

	public final String jdbcClassName = "";
	public final String url = "";
	public final String user = "";
	public final String password = "";

	public final String ldapFactory = "";
	public final String ldapURL = "";
	public final String ldapPass = "";
	public final String ldapContext = "";
}
