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

	private String id;
	private String testName;
	private GlobalConstants gc;
	private RuntimeData runtimeData;
	private String applicationUnderTest;
	private String jsonFileName;


	public JsonDataExtractor(GlobalConstants gc, RuntimeData runtimeData, String applicationUnderTest, String jsonFileName, String id, String testName) {

		this.id = id;
		this.testName = testName;
		this.gc = gc;
		this.runtimeData = runtimeData;
		this.applicationUnderTest = applicationUnderTest;
		this.jsonFileName = jsonFileName;

		initialize();

	}


	/**
	 * Extract and set test and system level data from reference JSON
	 */
	@Step("Extract dynamic test data from JSON.")
	protected void initialize() {

		String path = this.gc.jsonFilesPath + this.applicationUnderTest + "\\" + this.jsonFileName;

		Object[] data = null;
		try {
			//data = getTestData(this.gc.testDataFilePath);
			data = getTestData(path);
		} catch (FileNotFoundException fnfe) {
			Assert.fail(fnfe.getLocalizedMessage());
		}

		if (null != data[0]) {

			JsonArray testDataArray = (JsonArray) data[0]; // All test definitions, wrapped in Array

			Iterator<JsonElement> tda = testDataArray.iterator();
			while (tda.hasNext()) { // there should only be one (1) array element, containing all test-arrays
				JsonObject tob = (JsonObject) tda.next();
				//System.out.format("[DEBUG]: <[%s:%s] TestsData: %s>%n", this.id, this.testName, tob);

				if (tob.has(this.testName)) {
					setTestData(tob.getAsJsonObject(this.testName));
				} else {
					System.out.format("[LOG]: <[%s:%s] Skipping, JSON test data not found.>%n", this.id, this.testName);
					//return;
				}

			}
		} else {
			System.out.format("[LOG]: <[%s:%s] Skipping, JSON test data array not found.>%n", this.id, this.testName);
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
			System.out.format("[LOG]: <[%s:%s] Skipping, JSON system data array not found.>%n", this.id, this.testName);
		}

	}


	/**
	 * Set test-level data
	 *
	 * @param testData (JsonObject) json array containing test level json elements
	 */
	@Step("Set Test data.")
	private void setTestData(JsonObject testData) {

		System.out.format("[LOG]: <[%s:%s] JsonTestData: %s>%n", this.id, this.testName, testData);

		this.runtimeData.gridType = testData.get("gridType") == null ? "local" : testData.get("gridType").getAsString();

		this.runtimeData.platformName = testData.get("platformName") == null ? "" : testData.get("platformName").getAsString();
		this.runtimeData.platformVersion = testData.get("platformVersion") == null ? "" : testData.get("platformVersion").getAsString();
		this.runtimeData.browserName = testData.get("browserName") == null ? "" : testData.get("browserName").getAsString();
		this.runtimeData.browserVersion = testData.get("browserVersion") == null ? "" : testData.get("browserVersion").getAsString();
		this.runtimeData.resolution = testData.get("resolution") == null ? "" : testData.get("resolution").getAsString();
		this.runtimeData.location = testData.get("location") == null ? "" : testData.get("location").getAsString();
		this.runtimeData.platform = testData.get("platform") == null ? "" : testData.get("platform").getAsString();

		this.runtimeData.headless = testData.get("headless") == null ? false : testData.get("headless").getAsBoolean();

		// mobile native
		this.runtimeData.deviceName = testData.get("deviceName") == null ? "" : testData.get("deviceName").getAsString();
		this.runtimeData.model = testData.get("model") == null ? "" : testData.get("model").getAsString();
		this.runtimeData.appPackage = testData.get("appPackage") == null ? "" : testData.get("appPackage").getAsString();
		this.runtimeData.appActivity = testData.get("appActivity") == null ? "" : testData.get("appActivity").getAsString();
		this.runtimeData.bundleId = testData.get("bundleId") == null ? "" : testData.get("bundleId").getAsString();

		// for sample scripts, not required: searchString
		this.runtimeData.searchString = testData.get("searchString") == null ? "IBM Perfecto" : testData.get("searchString").getAsString();
		// for sample scripts, not required: searchConfirmationString
		this.runtimeData.searchConfirmationString = testData.get("searchConfirmationString") == null ? "IBM" : testData.get("searchConfirmationString").getAsString();

		this.runtimeData.username = testData.get("username") == null ? "" : testData.get("username").getAsString();
		this.runtimeData.password = testData.get("password") == null ? "" : testData.get("password").getAsString();
		this.runtimeData.creditCardNumber = testData.get("creditCardNumber") == null ? "" : testData.get("creditCardNumber").getAsString();
		this.runtimeData.creditCardExpiration = testData.get("creditCardExpiration") == null ? "" : testData.get("creditCardExpiration").getAsString();
		this.runtimeData.creditCardCVC = testData.get("creditCardCVC") == null ? "" : testData.get("creditCardCVC").getAsString();
		this.runtimeData.userEmail = testData.get("userEmail") == null ? "" : testData.get("userEmail").getAsString();

	}


	/**
	 * Set system-level options
	 *
	 * @param sysOpts (JsonObject) json array containing system level json elements
	 */
	@Step("Set System data.")
	private void setSystemOptions(JsonObject sysData) {

		System.out.format("[LOG]: <[%s:%s] JsonSystemData: %s>%n", this.id, this.testName, sysData);

		this.runtimeData.sysOpt = sysData.get("Opt1") == null ? null : sysData.get("Opt1").getAsString(); //example of syntax

	}


	/**
	 * Given a file path, retrieve test data json array and system options json array
	 *
	 * @param jsonDataFilePath (String) path to target data containing file
	 *
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
