package com.jmack.Base;


public class GlobalConstants {

	protected final String jsonFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\jsonData\\";
	protected final String propertiesFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\propertiesData\\";
	protected final String excelFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\excelData\\";

	protected final String locatorSeparator = "@@@";
	protected final String compoundLocatorPlaceholder = "~~~";

	protected final String jsonTestsDataArray = "Tests";
	protected final String jsonSystemOptionsDataArray = "System Options";

	protected final String perfectoSecurityToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3ek85T2dhclp1X3ZuQVJuakl0QWQ2SWtEeE41aHR2ZU85OHRibW9XbzVnIn0.eyJqdGkiOiJhNzJiNjc4YS05YmEwLTRkN2EtOWIyZi03OTg4YmZhN2RkMTAiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTQ4MTY4MjY0LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2libWdicy1wZXJmZWN0b21vYmlsZS1jb20iLCJhdWQiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsInN1YiI6IjVlYjZlYmMyLTJiNzgtNGFmNC1hNjJiLTkwZTBhODEwYTRhYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjQ2MDM0MTVhLWZjZjgtNGIzZS04ODk4LWVlNjMzZGQzYWY0NiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX19.mqPDUruahFkjif4pYamCRdAtxSHsjk8-GCCvmpl6Vm9MMV2_usDX7MWP0urf5s93Wvr-E1jkIvijR5PfJ3Fgysy2czAr0VF89qRc2bQKptTK7NuQLPROMhqMnN5_OaP6rv2pUSAl7XLmd7-7YYLctNHX_vtEUC1NXCWevjXoixZPCV9qTNnD7CC7Y8n1JiT7NpSc3JRcWBpmA1jK-8wsdr5Inn0z6qFHest9z9tYkgnVl8y3HT0JwLH7xQmb-wqxL9NB0RBTW63R2W-9taGvbxvlU8QAjn5kwh9XWbIxMUH-tPwH7-GZDmQYGHCkHgE5dv-6mWNzeym7OUurYdY2Sw";

	protected final String perfectoHost = "https://ibmgbs.perfectomobile.com/nexperience/perfectomobile/wd/hub";

	protected final String appiumHost = "http://localhost:4723/wd/hub";
	protected final String gridHost = "http://localhost:4444/wd/hub";

	protected final String proxy = "";

	/* for running against host machine browsers (non-grid).
	 * NOTE: 	NOT supported out-of-the-box.
	 * 			Requires re-write/supplement of TestBase "local" configuration to replace grid configurations
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
