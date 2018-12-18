package com.jmack.Base;

import java.util.HashMap;

import org.testng.Assert;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;


/**
 * @author JerimiahMACK
 */
public class ExcelDataExtractor {

	private GlobalConstants gc;
	private String applicationUnderTest;
	private String excelFileName;
	private String filePath;
	private Recordset records;
	private Connection conn;
	private String query;

	private String id;
	private String testName;

	private String key;


	/**
	 * Constructor. Used to simplify the extraction and processing of excel data. Calls internal methods
	 * to initialize connection to excel file, override common testdata repository, and then shut down
	 * connection to excel file.
	 *
	 * @param gc (GlobalConstants) project structural data reference
	 * @param applicationUnderTest (String) name of application. Corresponds to a folder in src/test/resources/excelData/
	 * @param excelFileName (String) name of target excel file
	 * @param runtimeData (JsonDataExtractor) common test data repository
	 * @param id (String) test instance id
	 * @param testName (String) test method name [name should match some row in excel worksheet]
	 */
	public ExcelDataExtractor(GlobalConstants gc, RuntimeData runtimeData, String applicationUnderTest, String excelFileName, String id, String testName) {

		this.gc = gc;
		this.id = id;
		this.testName = testName;
		this.applicationUnderTest = applicationUnderTest;
		this.excelFileName = excelFileName;

		this.getData("test");
		this.overrideTestData(runtimeData, getHashMapData());

		this.getData("system");
		this.overrideSystemData(runtimeData, getHashMapData());

		this.shutDown();

	}


	/**
	 * Load test data from excel, where available
	 *
	 * @param dataLevel (String) worksheet containing data
	 */
	private void getData(String dataLevel) {

		System.setProperty("ROW", "2"); // trim descriptive row
		System.setProperty("COLUMN", "1");

		query = "SELECT * FROM " + dataLevel;

		filePath = this.gc.excelFilesPath + this.applicationUnderTest + "\\" + this.excelFileName;

		Fillo fillo = new Fillo();

		try {
			conn = fillo.getConnection(filePath);
			records = conn.executeQuery(query).where("TestMethodName='" + this.testName + "'");
			Assert.assertTrue(records.getCount() != 0, String.format("No records found. Please ensure TestMethodName '%s' exists in '%s'.", this.testName, excelFileName));
			Assert.assertTrue(records.getCount() == 1, String.format("Multiple records found. Please ensure TestMethodName '%s' is unique.", this.testName));

		} catch (FilloException fe) {
			Assert.fail(fe.getLocalizedMessage());
		}


	}


	/**
	 * Override common test data repository values with values from excel data
	 *
	 * @param runtimeData (RuntimeData) common data container
	 * @param records (RecordSet) fillo excel data extraction results
	 */
	private void overrideTestData(RuntimeData runtimeData, HashMap<String, String> records) {

		System.out.format("[Log]: <[%s:%s] ExcelTestData: {", this.id, this.testName);
		records.forEach((k,v) -> System.out.format("\"%s\":\"%s\";", k, v));
		System.out.format("}>%n");

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "gridType";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.gridType.toLowerCase().equals(records.get(key).toLowerCase())) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.gridType, records.get(key));
				runtimeData.gridType = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "appActivity";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.appActivity.toLowerCase().equals(records.get(key).toLowerCase())) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.appActivity, records.get(key));
				runtimeData.appActivity = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "appPackage";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.appPackage.toLowerCase().equals(records.get(key).toLowerCase())) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.appPackage, records.get(key));
				runtimeData.appPackage = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "browserName";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.browserName.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.browserName, records.get(key));
				runtimeData.browserName = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "bundleId";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.bundleId.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.bundleId, records.get(key));
				runtimeData.bundleId = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "creditCardCVC";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.creditCardCVC.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.creditCardCVC, records.get(key));
				runtimeData.creditCardCVC = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "creditCardExpiration";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.creditCardExpiration.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.creditCardExpiration, records.get(key));
				runtimeData.creditCardExpiration = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "creditCardNumber";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.creditCardNumber.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.creditCardNumber, records.get(key));
				runtimeData.creditCardNumber = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "deviceName";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.deviceName.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.deviceName, records.get(key));
				runtimeData.deviceName = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "headless";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.headless.toString().equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.headless, records.get(key));
				runtimeData.headless = Boolean.parseBoolean(records.get(key));
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "model";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.model.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.model, records.get(key));
				runtimeData.model = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "password";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.password.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.password, records.get(key));
				runtimeData.password = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "platform";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.platform.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.platform, records.get(key));
				runtimeData.platform = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "platformName";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.platformName.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.gridType, records.get(key));
				runtimeData.platformName = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "platformVersion";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.platformVersion.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.platformVersion, records.get(key));
				runtimeData.platformVersion = records.get(key);
			}
		}


		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "resolution";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.resolution.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.resolution, records.get(key));
				runtimeData.resolution = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "searchConfirmationString";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.searchConfirmationString.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.searchConfirmationString, records.get(key));
				runtimeData.searchConfirmationString = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "searchString";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.searchString.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.searchString, records.get(key));
				runtimeData.searchString = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "userEmail";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.userEmail.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.userEmail, records.get(key));
				runtimeData.userEmail = records.get(key);
			}
		}

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "username";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.username.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.username, records.get(key));
				runtimeData.username = records.get(key);
			}
		}

	}


	/**
	 * Override common system data repository values with values from excel data
	 *
	 * @param runtimeData (RuntimeData) common data container
	 * @param records (RecordSet) fillo excel data extraction results
	 */
	private void overrideSystemData(RuntimeData runtimeData, HashMap<String, String> records) {

		// if there is an override passed via excel file, and the override differs from pre-established data, override
		key = "sysOpt";
		if (records.containsKey(key)) {
			if (!("").equals(records.get(key)) && !runtimeData.sysOpt.equals(records.get(key))) {
				System.out.format("[LOG]: <[%s:%s] overriding %s \"%s\" with \"%s\">%n", id, testName, key, runtimeData.sysOpt, records.get(key));
				runtimeData.sysOpt = records.get(key);
			}
		}

	}


	/**
	 * Change fillo recordset to hashmap
	 *
	 * @return (HashMap[String, String]) HashMap equivalent of fillo recordset
	 */
	private HashMap<String, String> getHashMapData() {

		HashMap<String, String> resultsMap = new HashMap<String, String>();

		try {
			if (records.next()) {
				for (String key : records.getFieldNames()) {
					resultsMap.put(key, records.getField(key));
				}
			}
		} catch (FilloException e) {
			return new HashMap<String, String>();
		}
		return resultsMap;

	}


	/**
	 * Close down fillo excel access
	 */
	public void shutDown() {

		records.close();
		conn.close();

	}


}
