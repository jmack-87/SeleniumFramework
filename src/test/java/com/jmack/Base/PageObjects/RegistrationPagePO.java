package com.jmack.Base.PageObjects;


import com.jmack.Base.DataExtractor;
import com.jmack.Base.Generic;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;

import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_RegistrationPage;

import io.qameta.allure.Step;


public class RegistrationPagePO extends TestBase {

	private Generic generic;
	private ScreenShot ss;
	private DataExtractor runtimeData;
	private String id = "unknown";
	private String testName = "unknown";
	private IFramePO iFrame;


	/**
	 * Minimum constructor
	 *
	 * @param generic
	 * @param ss
	 */
	public RegistrationPagePO(Generic generic, ScreenShot ss) {

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
	public RegistrationPagePO(Generic generic, ScreenShot ss, DataExtractor runtimeData, String id, String testName, IFramePO iFrame) {

		this.generic = generic;
		this.ss = ss;
		this.runtimeData = runtimeData;
		this.iFrame = iFrame;
		this.id = id;
		this.testName = testName;
	}


	/**
	 * Toggle from 'Dev' to 'Free' subscription type, and validate
	 */
	@Step("Switch from 'Dev' to 'Free' subscription type")
	public void toggleDevToFree() {

		// Click the "Free Plan" button on the registration
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_FreePlan.toString());

		// Confirm the existence of both of plan options
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelect.toString());
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelected.toString());

		// Take screenshot to verify successful transition from Dev to Free Plan
		this.generic.takeScreenShot("of 'Free Plan' being selected");

	}


	/**
	 * Toggle from 'Free' to 'Dev' subscription type, and validate
	 */
	@Step("Switch from 'Free' to 'Dev' subscription type")
	public void toggleFreeToDev() {

		// Click the "Dev Plan" button
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_DevPlan.toString());

		// Confirm the "Dev Plan" option is selected; confirm the "Free Plan" option is
		// not selected
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelected.toString());
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelect.toString());

		// Take screenshot to verify successful transition from Free to Dev Plan
		this.generic.takeScreenShot("of 'Dev Plan' being selected");

	}


	/**
	 * Fill out the 'Profile' section of the Registration Form
	 */
	@Step("Fill out the 'Profile' section of the form")
	public void fillOutProfileSection() {

		// Click the "Name" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Name.toString(), "@U$T1N D3V G0D");

		// Click the "Email" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Email.toString(), "l33th4x0r@nyancat.com");

		// Click the "Password" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Password.toString(), this.runtimeData.password);

		// Click the "Confirm Password" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_ConfirmPassword.toString(), this.runtimeData.password);

	}


	/**
	 * Fill out the 'Credit Card' section of the Registration Form
	 */
	@Step("Fill out the 'Credit Card' section of the form")
	public void fillOutCreditCardSection() {

		// Click the "Cardholder's Name" textbox, and send it input
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString());
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString(), "Stinky Stankerton");

		// Switch to the iFrame containing the textboxes for credit card information
		this.iFrame.switchToIframe(ChangeLager_RegistrationPage.Locator_iFrame_CreditCardInformation.toString());

		// Click the "Credit or debit card number" textbox, and send it input
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardNumber.toString());
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardNumber.toString(),	this.runtimeData.creditCardNumber);

		// Click the "Credit or debit card expiration date" textbox, and send it input
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardExpire.toString());
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardExpire.toString(), this.runtimeData.creditCardExpiration);

		// Click the "Credit or debit card CVC/CVV" textbox, and send it input
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardCVC.toString());
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardCVC.toString(), this.runtimeData.creditCardCVC);

		// Step out of the iframe, and back into the default context of the webpage
		this.iFrame.switchToDefault();

		// Click the "ZIP / Postal Code" textbox, and send it input
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString());
		this.generic.sendText(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString(), "11111");

		// Click the "Terms and Service" checkbox
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_checkBox_TermsAndService.toString());
	}

	/**
	 * Navigate
	 */


	/**
	 * Do something with RegistrationPage
	 *
	 * @param somethingToPass String (used only to change Allure pass/fail
	 *                        iconography from circle to arrow)
	 */
	@Step("Doing something with RegistrationPage")
	public void stuff(String somethingToPass) {

		System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", this.id, this.testName, somethingToPass);

		this.ss.assertTrue(true, "Some message if fail."); // example of an assertion with screenshot on fail
		// ss.takeScreenShot("Some description"); // example of taking screenshot, on
		// demand
		// generic.clickElement("Some.Property.Key"); // example of using generic
		// methods

	}


}
