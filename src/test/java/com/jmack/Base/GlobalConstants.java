package com.jmack.Base;


public class GlobalConstants {

	protected String testDataFileName = "TestParametersJSON.json";
	protected String testReferenceFileName = "testReference.properties";
	
	protected String testDataFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\"+testDataFileName;
	protected String testReferenceFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\"+testReferenceFileName;
	
	protected String locatorSeparator = "@@@";
	protected String compoundLocatorPlacehold = "~~~";

}
