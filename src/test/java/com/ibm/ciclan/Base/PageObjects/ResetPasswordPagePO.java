package com.ibm.ciclan.Base.PageObjects;

import com.ibm.ciclan.Base.RuntimeData;
import com.ibm.ciclan.Base.Generic;
import com.ibm.ciclan.Base.ScreenShot;
import com.ibm.ciclan.Base.TestBase;

import com.ibm.ciclan.Enumerations.ChangeLager.ResetPasswordPageENUM;

import io.qameta.allure.Step;

public class ResetPasswordPagePO extends TestBase{

	private Generic generic;
	private ScreenShot ss;
	private RuntimeData runtimeData;
	private String id = "unknown";
	private String testName = "unknown";
	private IFramePO iFrame;

	/**
	 * Minimum constructor
	 *
	 * @param generic
	 * @param ss
	 */
	public ResetPasswordPagePO(Generic generic, ScreenShot ss) {

		this.generic = generic;
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
	public ResetPasswordPagePO(Generic generic, ScreenShot ss, RuntimeData runtimeData, String id, String testName, IFramePO iFrame) {

		this.generic = generic;
		this.ss = ss;
		this.runtimeData = runtimeData;
		this.iFrame = iFrame;
		this.id = id;
		this.testName = testName;

	}

	/**
	 * Navigate to the ChangeLager Reset Password Page
	 */
	@Step("Open ChangeLager Reset Password Page")
	public void navigateToResetPassword_CL() {

		//Open a browser instance and navigate to the ChangeLager Reset Password URL
		this.generic.getUrl(ResetPasswordPageENUM.Text_URL.toString());

	}

	/**
	 * Confirm Head Tag of ChangeLager Reset Password Page
	 */
	@Step("Validate CL Reset Password Page Head Tag")
	public void confirmHeadTag_CL() {

		//Confirm the Head section of the HTML for the page
		this.generic.confirmElementExistence(ResetPasswordPageENUM.Locator_Tag_Head.toString());

	}

	/**
	 * Confirm Page Title of ChangeLager Reset Password Page
	 */
	@Step("Confirm Page Title of ChangeLager Reset Password Page")
	public void confirmPageTitle_CL() {

		//Confirm the Page Title attribute for the page
		this.generic.confirmElementExistence(ResetPasswordPageENUM.Text_PageTitle.toString());

	}

	/**
	 * Validate that you have landed on the ChangeLager Reset Password Page
	 */
	@Step("Validate navigation to ChangeLager Reset Password Page")
	public void validateNavigation_CL() {

		//Confirm the Head section of the HTML for the page
		this.confirmHeadTag_CL();

		//Confirm the Page title attribute for the page
		this.confirmPageTitle_CL();

	}

	/**
	 * Navigate to ChangeLager Reset Password Page,
	 * and confirm that you have landed on the page
	 */
	@Step("Navigate to ChangeLager Reset Password Page, and validate the navigation")
	public void navigateAndValidate_CL() {

		//Open a browser instance and navigate to the ChangeLager Reset Password URL
		this.navigateToResetPassword_CL();

		//Validate that you have landed on the ChangeLager Reset Password Page
		this.validateNavigation_CL();

	}

	/**
	 * Fill in the "Username" textbox on the
	 * ChangeLager Reset Password page
	 */
	@Step("Fill in the 'Username' textbox")
	public void fillOutUsername_CL() {

		//Click the "Username" textbox, and send it input
		this.generic.sendText(ResetPasswordPageENUM.Locator_TextField_Email.toString(), this.runtimeData.username);

	}

	/**
	 * Clear the input from the "Username" textbox on the
	 * ChangeLager Reset Password page
	 */

}
