package com.jmack.Base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.qameta.allure.Step;

public class DataExtractor {
	
	/*	Data available to Generic
	 */
	protected String sysOpt;
	protected String browser;
	protected Boolean headless;
	
	/*	Data available to scripts
	 */
	public String searchString;
	public String searchConfirmationString;
	
	private String id;
	private String testName;


	/**
	 * Extract and set test and system level data from reference JSON
	 * @param gc GlobalConstants instance
	 * @param testName test class name String
	 * @throws FileNotFoundException in case gc.testDateFilePath invalid
	 */
	@Step("Extract dynamic test data from JSON.")
	protected void initialize(GlobalConstants gc, String testName, String id) throws FileNotFoundException {
		this.testName = testName;
		this.id = id;
		
		Object[] data = getTestData(gc.testDataFilePath);
		JsonArray testDataArray = (JsonArray) data[0]; // All test definitions, wrapped in Array
		JsonArray systemDataArray = (JsonArray) data [1]; // All System Options definitions, wrapped in Array
		
		//System.out.format("[DEBUG]: <[%s:%s] TestsCount: %d>%n", this.id, this.testName, testDataArray.size());
		//System.out.format("[DEBUG]: <[%s:%s] SystemDataCount: %d>%n", this.id, this.testName, systemDataArray.size());
		
		Iterator<JsonElement> sda = systemDataArray.iterator();
		while (sda.hasNext()) { // there should only be one (1) array element, containing all key:value pairs
			JsonObject sob = (JsonObject) sda.next();
			//System.out.format("[DEBUG]: <[%s:%s] SystemData: %s>%n", this.id, this.testName, sob);
			setSystemOptions(sob);
		}
		
		Iterator<JsonElement> tda = testDataArray.iterator();
		while (tda.hasNext()) { // there should only be one (1) array element, containing all test-arrays 
			JsonObject tob = (JsonObject) tda.next();
			//System.out.format("[DEBUG]: <[%s:%s] TestsData: %s>%n", this.id, this.testName, tob);
			setTestData(tob.getAsJsonObject(testName));
		}
	}
	
	/**
	 * Set test-level data
	 * @param testData json array containing test level json elements
	 */
	@Step("Set Test data.")
	private void setTestData(JsonObject testData) {
		System.out.format("[LOG]: <[%s:%s] RuntimeData: %s>%n", this.id, this.testName, testData);
		browser = testData.get("browser") == null ? "random" : testData.get("browser").getAsString();
		headless = testData.get("headless") == null ? false : testData.get("headless").getAsBoolean();
		searchString = testData.get("searchString") == null ? "IBM Perfecto" : testData.get("searchString").getAsString();
		searchConfirmationString = testData.get("searchConfirmationString") == null ? "IBM" : testData.get("searchConfirmationString").getAsString();
	
	}
	
	/**
	 * Set system-level options
	 * @param sysOpts json array containing system level json elements
	 */
	@Step("Set System data.")
	private void setSystemOptions(JsonObject sysOpts) {
		System.out.format("[LOG]: <[%s:%s] SystemData: %s>%n", this.id, this.testName, sysOpts);
		sysOpt = sysOpts.get("Opt1") == null ? null : sysOpts.get("Opt1").getAsString(); //example of syntax
		
	}
	
	/**
	 * Given a file path, retrieve test data json array and system options json array
	 * @param jsonDataFilePath
	 * @return Object[] containing two JSON arrays {testData, systemData}
	 * @throws FileNotFoundException
	 */
	private Object[] getTestData(String jsonDataFilePath) throws FileNotFoundException {
		FileReader jsonDataFileReader = new FileReader(jsonDataFilePath);
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonData = jsonParser.parse(jsonDataFileReader).getAsJsonObject();
		JsonArray testsArray = jsonData.getAsJsonArray("Tests");
		JsonArray systemArray = jsonData.getAsJsonArray("System Options");

		return new Object[] {testsArray, systemArray};

	}
	

}
