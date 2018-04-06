package com.jmack.Base.PageObjects;

import io.qameta.allure.Step;

public class HomePage {

	private String testName;
	private String id;
	
	public HomePage(String id, String testName) {
		this.testName = testName;
		this.id = id;
	}
	
	/** 
	 *  Do something with HomePage
	 */
	@Step("Doing something with HomePage")
	public void stuff(String somethingToPass) {
		System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on HomePage>%n", this.id, this.testName, somethingToPass);
	}
}
