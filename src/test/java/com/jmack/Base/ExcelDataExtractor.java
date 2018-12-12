package com.jmack.Base;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;


/**
 * @author JerimiahMACK
 */
public class ExcelDataExtractor {

	private String appUnderTest;
	private String excelFileName;
	private String filePath;
	private Recordset records;
	private Connection conn;
	private String query;


	public ExcelDataExtractor(String applicationUnderTest, String excelFileName, String testName, DataExtractor runtimeData) {

		this.initialize(applicationUnderTest, excelFileName, testName);
		this.overrideJSONdata(runtimeData, this.getHashMapData());
		this.shutDown();

	}


	/**
	 * Load test data from excel, where available
	 *
	 * @param appUnderTest
	 * @param excelFileName
	 * @param testName
	 */
	public void initialize(String appUnderTest, String excelFileName, String testName) {

		System.setProperty("ROW", "2"); // trim descriptive row
		System.setProperty("COLUMN", "1");

		query = "SELECT * FROM test";

		this.appUnderTest = appUnderTest;
		this.excelFileName = excelFileName;

		filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\spreadSheetData\\" + this.appUnderTest + "\\"+this.excelFileName;

		Fillo fillo = new Fillo();

		try {
			conn = fillo.getConnection(filePath);
			records = conn.executeQuery(query).where("TestMethodName='" + testName + "'");
			Assert.assertTrue(records.getCount() != 0, String.format("No records found. Please ensure TestMethodName '%s' exists in '%s'.", testName, excelFileName));
			Assert.assertTrue(records.getCount() == 1, String.format("Multiple records found. Please ensure TestMethodName '%s' is unique.", testName));

			while(records.next()) {
				List<String> keys = records.getFieldNames();
				for (String key: keys) {
					System.out.format("%s:'%s'%n", key, records.getField(key));
				}
			}

		} catch (FilloException fe) {
			Assert.fail("Unable to load excel data.");
		}

	}


	private void overrideJSONdata(DataExtractor runtimeData, HashMap<String, String> records) {

		if (records.containsKey("appActivity")) {
			runtimeData.appActivity = records.get("appActivity") == "" ? runtimeData.appActivity : records.get("appActivity");
		}

		if (records.containsKey("appPackage")) {
			runtimeData.appPackage = records.get("appPackage") == "" ? runtimeData.appPackage : records.get("appPackage");
		}

		if (records.containsKey("browserName")) {
			runtimeData.browserName = records.get("browserName") == "" ? runtimeData.browserName : records.get("browserName");
		}

		if (records.containsKey("bundleId")) {
			runtimeData.bundleId = records.get("bundleId") == "" ? runtimeData.bundleId : records.get("bundleId");
		}

		if (records.containsKey("creditCardCVC")) {
			runtimeData.creditCardCVC = records.get("creditCardCVC") == "" ? runtimeData.creditCardCVC : records.get("creditCardCVC");
		}

		if (records.containsKey("creditCardExpiration")) {
			runtimeData.creditCardExpiration = records.get("creditCardExpiration") == "" ? runtimeData.creditCardExpiration : records.get("creditCardExpiration");
		}

		if (records.containsKey("creditCardNumber")) {
			runtimeData.creditCardNumber = records.get("creditCardNumber") == "" ? runtimeData.creditCardNumber : records.get("creditCardNumber");
		}

		if (records.containsKey("deviceName")) {
			runtimeData.deviceName = records.get("deviceName") == "" ? runtimeData.deviceName : records.get("deviceName");
		}

		if (records.containsKey("appheadlessActivity")) {
			runtimeData.gridType = records.get("gridType") == "" ? runtimeData.gridType : records.get("gridType");
		}

		if (records.containsKey("headless")) {
			runtimeData.headless = records.get("headless") == "" ? runtimeData.headless : Boolean.parseBoolean(records.get("headless"));
		}

		if (records.containsKey("model")) {
			runtimeData.location = records.get("location") == "" ? runtimeData.location : records.get("location");
		}

		if (records.containsKey("model")) {
			runtimeData.model = records.get("model") == "" ? runtimeData.model : records.get("model");
		}

		if (records.containsKey("password")) {
			runtimeData.password = records.get("password") == "" ? runtimeData.password : records.get("password");
		}

		if (records.containsKey("platform")) {
			runtimeData.platform = records.get("platform") == "" ? runtimeData.platform : records.get("platform");
		}

		if (records.containsKey("platformName")) {
			runtimeData.platformName = records.get("platformName") == "" ? runtimeData.platformName : records.get("platformName");
		}

		if (records.containsKey("platformVersion")) {
			runtimeData.platformVersion = records.get("platformVersion") == "" ? runtimeData.platformVersion : records.get("platformVersion");
		}

		if (records.containsKey("resolution")) {
			runtimeData.resolution = records.get("resolution") == "" ? runtimeData.resolution : records.get("resolution");
		}

		if (records.containsKey("searchConfirmationString")) {
			runtimeData.searchConfirmationString = records.get("searchConfirmationString") == ""? runtimeData.searchConfirmationString : records.get("searchConfirmationString");
		}

		if (records.containsKey("searchString")) {
			runtimeData.searchString = records.get("searchString") == "" ? runtimeData.searchString : records.get("searchString");
		}

		if (records.containsKey("userEmail")) {
			runtimeData.userEmail = records.get("userEmail") == "" ? runtimeData.userEmail : records.get("userEmail");
		}

		if (records.containsKey("username")) {
			runtimeData.username = records.get("username") == "" ? runtimeData.username : records.get("username");
		}

		if (records.containsKey("sysOpt")) {
			runtimeData.sysOpt = records.get("sysOpt") == "" ? runtimeData.sysOpt : records.get("sysOpt");
		}

	}


	public HashMap<String, String> getHashMapData() {

		HashMap<String, String> resultsMap = new HashMap<String, String>();

		try {
			for (String key : records.getFieldNames()) {
				resultsMap.put(key, records.getField(key));
			}
		} catch (FilloException e) {
			return new HashMap<String, String>();
		}
		return resultsMap;

	}
	

	public void shutDown() {

		records.close();
		conn.close();

	}


}
