package com.jmack.Base;


public class GlobalConstants {

	protected final String testDataFileName = "TestParametersJSON.json";
	protected final String testReferenceFileName = "testReference.properties";

	protected final String testDataFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\" + testDataFileName;
	protected final String testReferenceFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\" + testReferenceFileName;

	protected final String locatorSeparator = "@@@";
	protected final String compoundLocatorPlaceholder = "~~~";

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

	protected final String jdbcClassName = "";
	protected final String url = "";
	protected final String user = "";
	protected final String password = "";

	protected final String ldapFactory = "";
	protected final String ldapURL = "";
	protected final String ldapPass = "";
	protected final String ldapContext = "";

	// #com.santander.Base.TestBase: SRF login config
    protected final String srfHost = "https://ftaas.saas.hpe.com:443/wd/hub";
    protected final String srfId = "";
    protected final String srfPass = "";

}
