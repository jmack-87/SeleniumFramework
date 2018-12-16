package com.jmack.Base;

public class RuntimeData {

	// Data available to TestBase/Generic/PageObjects
	protected String sysOpt = "";
	protected String gridType = "";
	protected String platformName = "";
	protected String platformVersion = "";
	protected String browserName = "";
	protected String browserVersion = "";
	protected String resolution = "";
	protected String location = "";
	protected String platform = "";
	protected Boolean headless = false;
	protected String deviceName = "";
	protected String model = "";
	protected String appPackage = "";
	protected String appActivity = "";
	protected String bundleId = "";

	// Data available to scripts
	public String searchString = "";
	public String searchConfirmationString = "";
	public String username = "";
	public String password = "";
	public String creditCardNumber = "";
	public String creditCardExpiration = "";
	public String creditCardCVC = "";
	public String userEmail = "";

}
