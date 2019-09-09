package com.ibm.ciclan.Base;


public class GlobalConstants {

	protected final String jsonFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\jsonData\\";
	protected final String propertiesFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\propertiesData\\";
	protected final String excelFilesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\excelData\\";

	protected final String locatorSeparator = "@@@";
	protected final String compoundLocatorPlaceholder = "~~~";

	protected final String jsonTestsDataArray = "Tests";
	protected final String jsonSystemOptionsDataArray = "System Options";

	protected final String perfectoSecurityToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3ek85T2dhclp1X3ZuQVJuakl0QWQ2SWtEeE41aHR2ZU85OHRibW9XbzVnIn0.eyJqdGkiOiI5N2NkYTc4YS03NjNlLTQ5MGUtYjM0OC03NDRlNGYwZGI2MGIiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTU4OTM1NzM3LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2libWdicy1wZXJmZWN0b21vYmlsZS1jb20iLCJhdWQiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsInN1YiI6IjVlYjZlYmMyLTJiNzgtNGFmNC1hNjJiLTkwZTBhODEwYTRhYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiOGZkOGUwZDItNWMwMy00YTZlLTg3Y2YtODk0MjQ0MWM3MmE2IiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiOGIyZTYwYjYtZjhhYi00ODlkLTg5NTktOTUzMjBhOTM0OGY1IiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fX0.Rb6YCh8lTsQlEhAc0KBf3SHXPCwmAQvzrXkESgUyadRvRnifkq-OwcqCOE_zsQ8sNb-xPeorbkw09Wt1jEXE4MMEggW91Nk0x8rNRWIRBgNTsLwInfJ0a-j9AdlIvNKYx6_wea6ylKOjTPfrhod5_iEeVpGG3t_9Fztv5uFJh09KVgM5lp_bkRtR7vCM-xlBOlC-SnXJqaNNJTzop6PZTkRh9x6Vn7KauH2h6bhn9pvV-ssh7LgrazSe1nxKArvH-O-HYmeorojs7r7rEDMdbjD_mvE4vVTmaxYIJlm2iP3q8zokOieCe7nxJ-8MAf-lzTTs9VnUE12KOTKnW9bmSg";

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
