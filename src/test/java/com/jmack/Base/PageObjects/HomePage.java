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
	public void stuff() {
		System.out.format("[LOG]: <[%s:%s] doing something with HomePage>%n", this.id, this.testName);
	}
}
