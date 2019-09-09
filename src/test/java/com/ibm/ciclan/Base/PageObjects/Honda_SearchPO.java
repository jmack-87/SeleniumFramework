package com.ibm.ciclan.Base.PageObjects;


import com.ibm.ciclan.Base.Generic;
import com.ibm.ciclan.Base.ScreenShot;
import com.ibm.ciclan.Base.TestBase;

import io.qameta.allure.Step;


/**
 *
 * @author Jerimiah Mack
 *
 */
public class Honda_SearchPO extends TestBase {

	private ScreenShot ss;
	private String id = "unknown";
	private String testName = "unknown";


	/**
	 * Minimum constructor
	 *
	 * @param generic
	 * @param ss
	 */
	public Honda_SearchPO(Generic generic, ScreenShot ss) {

		this.generic = super.generic;
		this.ss = ss;

	}


	/**
	 * Constructor provisioned for instance logging
	 *
	 * @param generic
	 * @param ss
	 * @param id
	 * @param testName
	 */
	public Honda_SearchPO(Generic generic, ScreenShot ss, String id, String testName) {

		this.generic = generic;
		this.ss = ss;
		this.id = id;
		this.testName = testName;

	}


	/**
	 * Do something with Honda_searchPO
	 *
	 * @param somethingToPass String (used only to change Allure pass/fail iconography from circle to arrow)
	 */
	@Step("Doing something with Honda_SearchPO")
	public void stuff(String somethingToPass) {

		System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on Honda_SearchPO>%n", this.id, this.testName, somethingToPass);

		this.ss.assertTrue(true, "Some message if fail.");

	}


}
