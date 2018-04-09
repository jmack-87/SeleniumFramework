package com.jmack.Base.PageObjects;

import com.jmack.Base.ScreenShot;

import io.qameta.allure.Step;

public class HomePage {

	private String testName = "unknown";
	private String id = "unknown";
	private ScreenShot ss;
	
	public HomePage(ScreenShot ss, String id, String testName) {
		this.ss = ss;
		this.id = id;
		this.testName = testName;
	}
	
	public HomePage(ScreenShot ss) {
		this.ss = ss;
	}
	
	/** 
	 *  Do something with HomePage
	 */
	@Step("Doing something with HomePage")
	public void stuff(String somethingToPass) {
		System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on HomePage>%n", this.id, this.testName, somethingToPass);
		
		ss.assertTrue(true,"Some message if fail.");
	}
}
