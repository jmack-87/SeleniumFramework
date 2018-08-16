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

	protected final String proxy = "180.190.255.36:8080";

	protected final String geckoDriver = "C:\\Dev\\SeleniumGrid\\Drivers\\geckodriver.exe";
	protected final String chromeDriver = "C:\\Dev\\SeleniumGrid\\Drivers\\chromedriver.exe";
	protected final String ieDriver = "C:\\Dev\\SeleniumGrid\\Drivers\\IEDriverServer_32.exe";

	protected final String firefoxBinary = "C:\\Dev\\FF\\chrome.exe";
	protected final String firefoxBrowserLogFile = "target\\fflog.txt";

	protected final int defaultTimeOut = 30;
	protected final int defaultPollingRate = 1000;
	protected final String compoundLocatorPlaceholder = "~~~";

	public final String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
	public final String url = "jdbc:db2://vipaddes.mx.sov.pre.corp:5002/DSQG";
	public final String user = "";
	public final String password = "";

	public final String ldapFactory = "com.sun.jndi.ldap.LdapCtxFactory";
	public final String ldapURL = "ldap://ldap.mx.sov.pre.corp:2389";
	public final String ldapPass = "RBKS2010";
	public final String ldapContext = "ou=Usuarios,cn=Particulares,o=BanksphereInternet,o=Sovereign,c=US,o=Grupo Santander";

	public final String operationsPortalUrl = "http://structurals.sov.pre.corp/SVG_Backoffice_ENS/Estatico/ALP_CINTRA_CompIntraPriv_E/Html/portalSOVBackoffice.html?altoAreaCuenta=250";
	public final String operationsPortalUser = "P114203";
	public final String operationsPortalPass = "sov12345";
	public final String operationsPortalPropertiesFile = "operationsPortal.properties";
	public final String operationsPortalPropertiesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + operationsPortalPropertiesFile;
}
