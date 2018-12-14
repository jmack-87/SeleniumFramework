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

	private String id;
	private String testName;


	public ExcelDataExtractor(String applicationUnderTest, String excelFileName, JsonDataExtractor runtimeData, String id, String testName) {

		this.id = id;
		this.testName = testName;

		this.initialize(applicationUnderTest, excelFileName);
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
	private void initialize(String appUnderTest, String excelFileName) {

		System.setProperty("ROW", "2"); // trim descriptive row
		System.setProperty("COLUMN", "1");

		query = "SELECT * FROM test";

		this.appUnderTest = appUnderTest;
		this.excelFileName = excelFileName;

		filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\spreadsheetData\\" + this.appUnderTest + "\\" + this.excelFileName;

		Fillo fillo = new Fillo();

		try {
			conn = fillo.getConnection(filePath);
			records = conn.executeQuery(query).where("TestMethodName='" + this.testName + "'");
			Assert.assertTrue(records.getCount() != 0, String.format("No records found. Please ensure TestMethodName '%s' exists in '%s'.", this.testName, excelFileName));
			Assert.assertTrue(records.getCount() == 1, String.format("Multiple records found. Please ensure TestMethodName '%s' is unique.", this.testName));

//			while(records.next()) {
//				List<String> keys = records.getFieldNames();
//				for (String key: keys) {
//					System.out.format("%s:'%s'%n", key, records.getField(key));
//				}
//			}

		} catch (FilloException fe) {
			Assert.fail(fe.getLocalizedMessage());
		}


	}


	private void overrideJSONdata(JsonDataExtractor runtimeData, HashMap<String, String> records) {

		System.out.format("[Log]: <[%s:%s] Excel Data: {", this.id, this.testName);
		records.forEach((k,v) -> System.out.format("\"%s\":\"%s\";", k, v));
		System.out.format("}>%n");


		if (records.containsKey("gridType") && records.get("gridType") != "") {
			System.out.format("[DEBUG]: <[%s:%s] Overriding gridType: %s -> %s>%n", this.id, this.testName, runtimeData.gridType, records.get("gridType"));
			runtimeData.gridType = records.get("gridType") == "" ? runtimeData.gridType : records.get("gridType");
		}

		if (records.containsKey("appActivity") && records.get("appactivity") != "") {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.appActivity, records.get("appActivity"));
			runtimeData.appActivity = records.get("appActivity") == "" ? runtimeData.appActivity : records.get("appActivity");
		}

		if (records.containsKey("appPackage")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.appPackage, records.get("appPackage"));
			runtimeData.appPackage = records.get("appPackage") == "" ? runtimeData.appPackage : records.get("appPackage");
		}

		if (records.containsKey("browserName")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.browserName, records.get("browserName"));
			runtimeData.browserName = records.get("browserName") == "" ? runtimeData.browserName : records.get("browserName");
		}

		if (records.containsKey("bundleId")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.bundleId, records.get("bundleId"));
			runtimeData.bundleId = records.get("bundleId") == "" ? runtimeData.bundleId : records.get("bundleId");
		}

		if (records.containsKey("creditCardCVC")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.creditCardCVC, records.get("creditCardCVC"));
			runtimeData.creditCardCVC = records.get("creditCardCVC") == "" ? runtimeData.creditCardCVC : records.get("creditCardCVC");
		}

		if (records.containsKey("creditCardExpiration")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.creditCardExpiration, records.get("creditCardExpiration"));
			runtimeData.creditCardExpiration = records.get("creditCardExpiration") == "" ? runtimeData.creditCardExpiration : records.get("creditCardExpiration");
		}

		if (records.containsKey("creditCardNumber")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.creditCardNumber, records.get("creditCardNumber"));
			runtimeData.creditCardNumber = records.get("creditCardNumber") == "" ? runtimeData.creditCardNumber : records.get("creditCardNumber");
		}

		if (records.containsKey("deviceName")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.deviceName, records.get("deviceName"));
			runtimeData.deviceName = records.get("deviceName") == "" ? runtimeData.deviceName : records.get("deviceName");
		}

		if (records.containsKey("appheadlessActivity")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.gridType, records.get("gridType"));
			runtimeData.gridType = records.get("gridType") == "" ? runtimeData.gridType : records.get("gridType");
		}

		if (records.containsKey("headless")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.headless, records.get("headless"));
			runtimeData.headless = records.get("headless") == "" ? runtimeData.headless : Boolean.parseBoolean(records.get("headless"));
		}

		if (records.containsKey("model")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.location, records.get("location"));
			runtimeData.location = records.get("location") == "" ? runtimeData.location : records.get("location");
		}

		if (records.containsKey("model")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.model, records.get("model"));
			runtimeData.model = records.get("model") == "" ? runtimeData.model : records.get("model");
		}

		if (records.containsKey("password")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.password, records.get("password"));
			runtimeData.password = records.get("password") == "" ? runtimeData.password : records.get("password");
		}

		if (records.containsKey("platform")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.platform, records.get("platform"));
			runtimeData.platform = records.get("platform") == "" ? runtimeData.platform : records.get("platform");
		}

		if (records.containsKey("platformName")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.platformName, records.get("platformName"));
			runtimeData.platformName = records.get("platformName") == "" ? runtimeData.platformName : records.get("platformName");
		}

		if (records.containsKey("platformVersion")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.platformVersion, records.get("platformVersion"));
			runtimeData.platformVersion = records.get("platformVersion") == "" ? runtimeData.platformVersion : records.get("platformVersion");
		}

		if (records.containsKey("resolution")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.resolution, records.get("resolution"));
			runtimeData.resolution = records.get("resolution") == "" ? runtimeData.resolution : records.get("resolution");
		}

		if (records.containsKey("searchConfirmationString")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.searchConfirmationString, records.get("searchConfirmationString"));
			runtimeData.searchConfirmationString = records.get("searchConfirmationString") == ""? runtimeData.searchConfirmationString : records.get("searchConfirmationString");
		}

		if (records.containsKey("searchString")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.searchString, records.get("searchString"));
			runtimeData.searchString = records.get("searchString") == "" ? runtimeData.searchString : records.get("searchString");
		}

		if (records.containsKey("userEmail")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.userEmail, records.get("userEmail"));
			runtimeData.userEmail = records.get("userEmail") == "" ? runtimeData.userEmail : records.get("userEmail");
		}

		if (records.containsKey("username")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding %s -> %s>%n", this.id, this.testName, runtimeData.username, records.get("username"));
			runtimeData.username = records.get("username") == "" ? runtimeData.username : records.get("username");
		}

		if (records.containsKey("sysOpt")) {
			System.out.format("[DEBUG]: <[%s:%s] Overriding sysOpt: %s -> %s>%n", this.id, this.testName, runtimeData.sysOpt, records.get("sysOpt"));
			runtimeData.sysOpt = records.get("sysOpt") == "" ? runtimeData.sysOpt : records.get("sysOpt");
		}

	}


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


	public void shutDown() {

		records.close();
		conn.close();

	}


}
