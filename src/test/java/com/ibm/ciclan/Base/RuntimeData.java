package com.ibm.ciclan.Base;

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


	/**
	 * Check that mimimum WebDriver data requirements are met some
	 * combination of JSON, Excel, Parameter data. Does not check
	 * for test data requirements.
	 *
	 * @return (boolean) minimum data requirements met
	 */
	public boolean minimumDataCheck(String id, String testName) {

		// gridType must not be empty
		if (null == gridType || gridType.equals("")) {
			System.out.format("[LOG]: <[%s:%s] mandatory data 'gridtype' not set>%n", id, testName);
			return false;
		}
		// browserName must not be empty
		if (null == browserName || browserName.equals("")) {
			System.out.format("[LOG]: <[%s:%s] mandatory data 'browserName' not set>%n", id, testName);
			return false;
		}
		// minimum data for appium (local and perfecto) grid
		if (browserName.toLowerCase().contains("android") || browserName.toLowerCase().contains("ios")) {
			// platformName must not be empty
			if (null == platformName || platformName.toLowerCase().equals("")) {
				System.out.format("[LOG]: <[%s:%s] mandatory data 'platformName' not set>%n", id, testName);
				return false;
			}
			// platformVersion must not be empty
			if (null == platformVersion || platformVersion.toLowerCase().equals("")) {
				System.out.format("[LOG]: <[%s:%s] mandatory data 'platformVersion' not set>%n", id, testName);
				return false;
			}
			// iOS native must include bundleId
			if (browserName.toLowerCase().equals("iosNative")) {
				// bundleId must not be empty
				if (null == bundleId || bundleId.toLowerCase().equals("")) {
					System.out.format("[LOG]: <[%s:%s] mandatory data 'bundleId' not set>%n", id, testName);
					return false;
				}
			}
			// android native must include appPackage and appActivity
			if (browserName.toLowerCase().equals("androidNative")) {

				// appPackage must not be empty
				if (null == appPackage || appPackage.toLowerCase().equals("")) {
					System.out.format("[LOG]: <[%s:%s] mandatory data 'appPackage' not set>%n", id, testName);
					return false;
				}
				// appActivity must not be empty
				if (null == appActivity || appActivity.toLowerCase().equals("")) {
					System.out.format("[LOG]: <[%s:%s] mandatory data 'appActivity' not set>%n", id, testName);
					return false;
				}
			}
		}
		// minimum data for perfecto desktop grid
		if (gridType.toLowerCase().equals("perfecto") && browserName.toLowerCase().contains("desktop")) {
			// platformName must not be empty
			if (null == platformName || platformName.toLowerCase().equals("")) {
				System.out.format("[LOG]: <[%s:%s] mandatory data 'platformName' not set>%n", id, testName);
				return false;
			}
			// platformVersion must not be empty
			if (null == platformVersion || platformVersion.toLowerCase().equals("")) {
				System.out.format("[LOG]: <[%s:%s] mandatory data 'platformVersion' not set>%n", id, testName);
				return false;
			}
			// mac requires location
			if (platformName.toLowerCase().equals("mac") && (null == location || location.toLowerCase().equals(""))) {
				System.out.format("[LOG]: <[%s:%s] mandatory data 'location' not set>%n", id, testName);
				return false;
			}
		}
		// if no checks failed
		System.out.format("[LOG]: <[%s:%s] minimum data requirement met>%n", id, testName);
		return true;
	}

}
