package com.jmack.Base;


public class GlobalConstants {

	protected final String jsonFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\jsonData\\";
	protected final String propertiesFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\propertiesData\\";
	protected final String excelFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\excelData\\";

	protected final String locatorSeparator = "@@@";
	protected final String compoundLocatorPlaceholder = "~~~";

	protected final String jsonTestsDataArray = "Tests";
	protected final String jsonSystemOptionsDataArray = "System Options";

	protected final String perfectoSecurityToken = "";
	protected final String perfectoHost = "";

	protected final String appiumHost = "http://localhost:4723/wd/hub";
	protected final String gridHost = "http://localhost:4444/wd/hub";

	protected final String proxy = "";

	/* for running against host machine browsers (non-grid).
	 * NOTE: 	NOT supported out-of-the-box.
	 * 			Requires re-write of TestBase "local" configuration to replace grid configurations
	 */
	protected final String geckoDriver = "";
	protected final String firefoxBinary = "";
	protected final String chromeDriver = "";
	protected final String ieDriver = "";

	protected final String firefoxBrowserLogFile = "target\\fflog.txt";

	protected final int defaultTimeOut = 30;
	protected final int defaultPollingRate = 1000;

	// DB2 support
	protected final String jdbcClassName = "";
	protected final String url = "";
	protected final String user = "";
	protected final String password = "";

	// LDAP support
	protected final String ldapFactory = "";
	protected final String ldapURL = "";
	protected final String ldapPass = "";
	protected final String ldapContext = "";

	// StormRunnerFunctional support
    protected final String srfHost = "";
    protected final String srfId = "";
    protected final String srfPass = "";

}
