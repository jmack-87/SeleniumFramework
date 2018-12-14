package com.jmack.Base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import org.testng.Assert;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.qameta.allure.Step;

public class JsonDataExtractor {

	/*	Data available to Generic
	 */
	protected String sysOpt = "";
	protected String gridType = "";
	public String platformName = ""; //
	protected String platformVersion = ""; //
	protected String browserName = "";
	protected String browserVersion = ""; //perfecto
	protected String resolution = ""; //perfecto
	protected String location = ""; //perfecto
	public String platform = ""; //

	protected Boolean headless = false;

	protected String deviceName = "";
	protected String model = "";
	protected String appPackage = "";
	protected String appActivity = "";
	protected String bundleId = "";

	/*	Data available to scripts
	 */
	public String searchString = "";
	public String searchConfirmationString = "";
	public String username = "";
	public String password = "";
	public String creditCardNumber = "";
	public String creditCardExpiration = "";
	public String creditCardCVC = "";
	public String userEmail = "";

	private String id;
	private String testName;

	private GlobalConstants gc;

	public JsonDataExtractor(String id, String testName, GlobalConstants gc) {

		this.id = id;
		this.testName = testName;
		this.gc = gc;
		initialize();

	}



	/**
	 * Extract and set test and system level data from reference JSON
	 *
	 * @param gc GlobalConstants instance
	 * @param testName test class name String
	 *
	 * @throws FileNotFoundException in case gc.testDateFilePath invalid
	 */
	@Step("Extract dynamic test data from JSON.")
	protected void initialize() {

		Object[] data = null;
		try {
			data = getTestData(this.gc.testDataFilePath);
		} catch (FileNotFoundException fnfe) {
			Assert.fail(fnfe.getLocalizedMessage());
		}

		if (null != data[0]) {

			JsonArray testDataArray = (JsonArray) data[0]; // All test definitions, wrapped in Array

			Iterator<JsonElement> tda = testDataArray.iterator();
			while (tda.hasNext()) { // there should only be one (1) array element, containing all test-arrays
				JsonObject tob = (JsonObject) tda.next();
				//System.out.format("[DEBUG]: <[%s:%s] TestsData: %s>%n", this.id, this.testName, tob);
				setTestData(tob.getAsJsonObject(this.testName));
			}
		} else {
			System.out.format("[LOG]: <[%s:%s] Skipping JSON test data extraction. JSON test data not found.>%n", this.id, this.testName);
		}

		if (null != data[1]) {

			JsonArray systemDataArray = (JsonArray) data [1]; // All System Options definitions, wrapped in Array

			Iterator<JsonElement> sda = systemDataArray.iterator();
			while (sda.hasNext()) { // there should only be one (1) array element, containing all key:value pairs
				JsonObject sob = (JsonObject) sda.next();
				//System.out.format("[DEBUG]: <[%s:%s] SystemData: %s>%n", this.id, this.testName, sob);
				setSystemOptions(sob);
			}
		} else {
			System.out.format("[LOG]: <[%s:%s] Skipping JSON system data extraction. JSON system data not found.>%n", this.id, this.testName);
		}

	}


	/**
	 * Set test-level data
	 *
	 * @param testData json array containing test level json elements
	 */
	@Step("Set Test data.")
	private void setTestData(JsonObject testData) {

		System.out.format("[LOG]: <[%s:%s] JsonData: %s>%n", this.id, this.testName, testData);

		this.gridType = testData.get("gridType") == null ? "local" : testData.get("gridType").getAsString();

		this.platformName = testData.get("platformName") == null ? "" : testData.get("platformName").getAsString(); // perfecto
		this.platformVersion = testData.get("platformVersion") == null ? "" : testData.get("platformVersion").getAsString(); // perfecto
		this.browserName = testData.get("browserName") == null ? "" : testData.get("browserName").getAsString();
		this.browserVersion = testData.get("browserVersion") == null ? "" : testData.get("browserVersion").getAsString(); // perfecto
		this.resolution = testData.get("resolution") == null ? "" : testData.get("resolution").getAsString(); // perfecto
		this.location = testData.get("location") == null ? "" : testData.get("location").getAsString(); // perfecto
		this.platform = testData.get("platform") == null ? "" : testData.get("platform").getAsString(); // perfecto

		this.headless = testData.get("headless") == null ? false : testData.get("headless").getAsBoolean(); // local

		// mobile native
		this.deviceName = testData.get("deviceName") == null ? "" : testData.get("deviceName").getAsString();
		this.model = testData.get("model") == null ? "" : testData.get("model").getAsString();
		this.appPackage = testData.get("appPackage") == null ? "" : testData.get("appPackage").getAsString();
		this.appActivity = testData.get("appActivity") == null ? "" : testData.get("appActivity").getAsString();
		this.bundleId = testData.get("bundleId") == null ? "" : testData.get("bundleId").getAsString();

		// for sample scripts, not required: searchString
		this.searchString = testData.get("searchString") == null ? "IBM Perfecto" : testData.get("searchString").getAsString();
		// for sample scripts, not required: searchConfirmationString
		this.searchConfirmationString = testData.get("searchConfirmationString") == null ? "IBM" : testData.get("searchConfirmationString").getAsString();

		this.username = testData.get("username") == null ? "" : testData.get("username").getAsString();
		this.password = testData.get("password") == null ? "" : testData.get("password").getAsString();
		this.creditCardNumber = testData.get("creditCardNumber") == null ? "" : testData.get("creditCardNumber").getAsString();
		this.creditCardExpiration = testData.get("creditCardExpiration") == null ? "" : testData.get("creditCardExpiration").getAsString();
		this.creditCardCVC = testData.get("creditCardCVC") == null ? "" : testData.get("creditCardCVC").getAsString();
		this.userEmail = testData.get("userEmail") == null ? "" : testData.get("userEmail").getAsString();

		System.out.format("[LOG]: <[%s:%s] Test data loaded.>%n", this.id, this.testName, testData);

	}


	/**
	 * Set system-level options
	 *
	 * @param sysOpts json array containing system level json elements
	 */
	@Step("Set System data.")
	private void setSystemOptions(JsonObject sysOpts) {

		System.out.format("[LOG]: <[%s:%s] SystemData: %s>%n", this.id, this.testName, sysOpts);

		sysOpt = sysOpts.get("Opt1") == null ? null : sysOpts.get("Opt1").getAsString(); //example of syntax

	}


	/**
	 * Given a file path, retrieve test data json array and system options json array
	 *
	 * @param jsonDataFilePath
	 * @return Object[] containing two JSON arrays {testData, systemData}
	 *
	 * @throws FileNotFoundException
	 */
	private Object[] getTestData(String jsonDataFilePath) throws FileNotFoundException {

		FileReader jsonDataFileReader = new FileReader(jsonDataFilePath);
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonData = jsonParser.parse(jsonDataFileReader).getAsJsonObject();

		//force the fail for now
		JsonArray testsArray = jsonData.getAsJsonArray(this.gc.jsonTestsDataArray);

		//JsonArray testsArray = jsonData.getAsJsonArray("Tests");
		JsonArray systemArray = jsonData.getAsJsonArray(this.gc.jsonSystemOptionsDataArray);

		return new Object[] {testsArray, systemArray};

	}


}
