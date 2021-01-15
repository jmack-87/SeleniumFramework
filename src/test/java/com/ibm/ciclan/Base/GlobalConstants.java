package com.ibm.ciclan.Base;


public class GlobalConstants {

	protected final String jsonFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\jsonData\\";
	protected final String propertiesFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\propertiesData\\";
	protected final String excelFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\excelData\\";

	protected final String locatorSeparator = "@@@";
	protected final String compoundLocatorPlaceholder = "~~~";

	protected final String jsonTestsDataArray = "Tests";
	protected final String jsonSystemOptionsDataArray = "System Options";

	protected final String perfectoSecurityToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3ek85T2dhclp1X3ZuQVJuakl0QWQ2SWtEeE41aHR2ZU85OHRibW9XbzVnIn0.eyJqdGkiOiI1ODQxNDM1MC0wMzA5LTRhNTQtOTllNi0xMmQ4YTQ4NGRkNDAiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTY5Njg5NjU0LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2libWdicy1wZXJmZWN0b21vYmlsZS1jb20iLCJhdWQiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsInN1YiI6IjVlYjZlYmMyLTJiNzgtNGFmNC1hNjJiLTkwZTBhODEwYTRhYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMzQ2NjlmNjctYTU5OC00NmQwLTg2YjAtMTE2ODg1YWY4OGE4IiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiMjJkYTZlMzAtNGY2MC00OGMzLTg4NGEtNDBkMWQxODI1NDBmIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fX0.X68s_UqkHtAqDQfO-XhaZ1oa4s3rXrT9hzyoEgalDk4Ed14fkSPLcyAlHf0P9DUNAmo6kVev8yXhKUX9U4ReR8xtC6RHddJmYlNIWvMq3KePnQKLdZuiKUyXSiaUfWgDB3yYQ3MiPc2XlhYi-6r8RP5R0VbDcsdA_nnRmtFrm4niMiwijpxfaZ9wQWdmvtVXnclxqN4fha5Ly01kCYlEkfvtun_kY6Z_MloeCosZGgwtBMome1PXjUL7g6TZ0A3EFv6Nso6BzfJgLdyNu6KRxvC5_OlNzWIHdMv1hdd_qnjIxRkj5DuaskFaf-EiBACmBn2_NqLrRsM-wiPiF61nFQ";

	protected final String perfectoHost = "https://ibmgbs.perfectomobile.com/nexperience/perfectomobile/wd/hub";
	protected final String browserStackHost = "https://<userName>:<passkey>@hub-cloud.browserstack.com/wd/hub";
	protected final String browserStackSeVer = "3.141.59";
	protected final String browserStackIeDriverVer = "3.141.59";
	protected final String browserStackApVer = "1.17.0";
	protected final boolean browserStackLocal = false;
	protected final boolean browserStackRealMobile = true;

	//protected final String headSpinHost = "https://dev-us-pao-0.headspin.io:9096/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub"; // US-chrome
	//protected final String headSpinHost = "https://gb-lhr.headspin.io:9092/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub"; // GB-firefox
	protected final String headSpinHost = "https://dev-us-pao-0.headspin.io:9095/v0/286d8226ec894f798c3394d33d3af4ab/wd/hub"; // US-safari

	protected final boolean headSpinCapture = true;

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
