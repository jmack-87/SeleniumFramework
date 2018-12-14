package com.jmack.Base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class TestNGParameterExtractor {

	// TestNG Suite parameters
	private String gridTypeOverride;
	private String platformNameOverride;
	private String platformVersionOverride;
	private String browserNameOverride;
	private String browserVersionOverride;
	private String resolutionOverride;
	private String locationOverride;

	// Mobile
	private String deviceNameOverride;
	private String modelOverride;
	private String appPackageOverride;
	private String appActivityOverride;
	private String bundleIdOverride;

	private String id;
	private String testName;


	@Parameters({
		"gridTypeOverride",
		"platformNameOverride",	"platformVersionOverride",
		"browserNameOverride", "browserVersionOverride",
		"resolutionOverride", "locationOverride",
		"deviceNameOverride", "modelOverride",
		"appPackageOverride", "appActivityOverride",
		"bundleIdOverride", "applicationUnderTest",
		"excelDataFile"
	})
	@BeforeSuite
	public TestNGParameterExtractor(String id, String testName, RuntimeData runtimeData,
			@Optional String gridTypeOverride,
			@Optional String platformNameOverride, @Optional String platformVersionOverride,
			@Optional String browserNameOverride, @Optional String browserVersionOverride,
			@Optional String resolutionOverride, @Optional String locationOverride,
			@Optional String deviceNameOverride, @Optional String modelOverride,
			@Optional String appPackageOverride, @Optional String appActivityOverride,
			@Optional String bundleIdOverride, @Optional String applicationUnderTest,
			@Optional String excelDataFile) {

		this.id = id;
		this.testName = testName;

		this.gridTypeOverride = gridTypeOverride == null ? "" : gridTypeOverride;
		this.platformNameOverride = platformNameOverride == null ? "" : platformNameOverride;
		this.platformVersionOverride = platformVersionOverride == null ? "" : platformVersionOverride;
		this.browserNameOverride = browserNameOverride == null ? "" : browserNameOverride;
		this.browserVersionOverride = browserVersionOverride == null ? "" : browserVersionOverride;
		this.resolutionOverride = resolutionOverride == null ? "" : resolutionOverride;
		this.locationOverride = locationOverride == null ? "" : locationOverride;

		this.deviceNameOverride = deviceNameOverride == null ? "" : deviceNameOverride;
		this.modelOverride = modelOverride == null ? "" : modelOverride;
		this.appPackageOverride = appPackageOverride == null ? "" : appPackageOverride;
		this.appActivityOverride = appActivityOverride == null ? "" : appActivityOverride;
		this.bundleIdOverride = bundleIdOverride == null ? "" : bundleIdOverride;

		overrideJsonExcelData(runtimeData);

	}


	private void overrideJsonExcelData(RuntimeData runtimeData) {

		// PARAMETER OVERRIDES
		System.out.format("[LOG]: <[%s:%s] overriding JSON/Excel data by parameter.>%n", id, testName);

		// if there is an override passed via TestNG suite file, and the override differs from pre-established gridType, override gridType
		if (!("").equals(this.gridTypeOverride) && !runtimeData.gridType.toLowerCase().equals(this.gridTypeOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding gridType \"%s\" with \"%s\">%n", id, testName, runtimeData.gridType, gridTypeOverride);
			runtimeData.gridType = this.gridTypeOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established platformName, override platformName
		if (!("").equals(this.platformNameOverride) && !runtimeData.platformName.toLowerCase().equals(this.platformNameOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformName \"%s\" with \"%s\">%n", id, testName, runtimeData.platformName, platformNameOverride);
			runtimeData.platformName = this.platformNameOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established platformVersion, override platformVersion
		if (!("").equals(this.platformVersionOverride) && !runtimeData.platformVersion.toLowerCase().equals(this.platformVersionOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding platformVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.platformVersion, platformVersionOverride);
			runtimeData.platformVersion = this.platformVersionOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established browser, override browser
		if (!("").equals(this.browserNameOverride) && !runtimeData.browserName.toLowerCase().equals(this.browserNameOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserName \"%s\" with \"%s\">%n", id, testName, runtimeData.browserName, browserNameOverride);
			runtimeData.browserName = browserNameOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established browserVersion, override browserVersion
		if (!("").equals(this.browserVersionOverride) && !runtimeData.browserVersion.toLowerCase().equals(this.browserVersionOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding browserVersion \"%s\" with \"%s\">%n", id, testName, runtimeData.browserVersion, browserVersionOverride);
			runtimeData.browserVersion = browserVersionOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established resolution, override resolution
		if (!("").equals(this.resolutionOverride) && !runtimeData.resolution.toLowerCase().equals(this.resolutionOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding resolution \"%s\" with \"%s\">%n", id, testName, runtimeData.resolution, resolutionOverride);
			runtimeData.resolution = resolutionOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established location, override location
		if (!("").equals(this.locationOverride) && !runtimeData.location.toLowerCase().equals(this.locationOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding location \"%s\" with \"%s\">%n", id, testName, runtimeData.location, locationOverride);
			runtimeData.location = locationOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established deviceName, override deviceName
		if (!("").equals(this.deviceNameOverride) && !runtimeData.deviceName.toLowerCase().equals(this.deviceNameOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding deviceName \"%s\" with \"%s\">%n", id, testName, runtimeData.deviceName, deviceNameOverride);
			runtimeData.deviceName = this.deviceNameOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established model, override model
		if (!("").equals(this.modelOverride) && !runtimeData.model.toLowerCase().equals(this.modelOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding model \"%s\" with \"%s\">%n", id, testName, runtimeData.model, modelOverride);
			runtimeData.model = this.modelOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established appPackage, override appPackage
		if (!("").equals(this.appPackageOverride) && !runtimeData.appPackage.toLowerCase().equals(this.appPackageOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appPackage \"%s\" with \"%s\">%n", id, testName, runtimeData.appPackage, appPackageOverride);
			runtimeData.appPackage = this.appPackageOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established appActivity, override appActivity
		if (!("").equals(this.appActivityOverride) && !runtimeData.appActivity.toLowerCase().equals(this.appActivityOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding appActivity \"%s\" with \"%s\">%n", id, testName, runtimeData.appActivity, appActivityOverride);
			runtimeData.appActivity = this.appActivityOverride;
		}

		// if there is an override passed via TestNG suite file, and the override differs from pre-established bundleId, override bundleId
		if (!("").equals(this.bundleIdOverride) && !runtimeData.bundleId.toLowerCase().equals(this.bundleIdOverride.toLowerCase())) {
			System.out.format("[LOG]: <[%s:%s] overriding bundleId \"%s\" with \"%s\">%n", id, testName, runtimeData.bundleId, bundleIdOverride);
			runtimeData.bundleId = this.bundleIdOverride;
		}

		System.out.format("[LOG]: <[%s:%s] JSON/Excel data overridden by parameter.>%n", id, testName);
		// END PARAMETER OVERRIDES

	}

}
