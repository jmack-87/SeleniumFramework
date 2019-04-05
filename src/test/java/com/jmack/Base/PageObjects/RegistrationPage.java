package com.jmack.Base.PageObjects;


import com.jmack.Base.RuntimeData;
import com.jmack.Base.Generic;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;

import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_RegistrationPage;

import io.qameta.allure.Step;


public class RegistrationPage extends TestBase {

	private Generic generic;
	private ScreenShot ss;
	private RuntimeData runtimeData;
	private String id = "unknown";
	private String testName = "unknown";
	private IFrame iFrame;


	/**
	 * Minimum constructor
	 *
	 * @param generic
	 * @param ss
	 */
	public RegistrationPage(Generic generic, ScreenShot ss) {

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
	public RegistrationPage(Generic generic, ScreenShot ss, RuntimeData runtimeData, String id, String testName, IFrame iFrame) {

		this.generic = generic;
		this.ss = ss;
		this.runtimeData = runtimeData;
		this.iFrame = iFrame;
		this.id = id;
		this.testName = testName;

	}

	/**
	 * Navigate to ChangeLager Registration Page
	 */
	@Step("Open ChangeLager Registration Page")
	public void navigateToRegistration_CL(){

		//Open a browser instance and navigate to the ChangeLager Registration URL
		this.generic.getUrl(ChangeLager_RegistrationPage.text_URL.toString());

	}

	/**
	 * Confirm Head Tag of ChangeLager Registration Page
	 */
	@Step("Validate CL Registration Page Head Tag")
	public void confirmHeadTag_CL(){

		//Confirm the Head section of the HTML for the page
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Tag_head.toString());

	}

	/**
	 * Confirm Page Title of ChangeLager Registration Page
	 */
	@Step("Confirm Page Title of ChangeLager Registration Page")
	public void confirmPageTitle_CL(){

		//Confirm the Page Title attribute for the page
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.text_pageTitle.toString());

	}

	/**
	 * Validate that you have landed on the ChangeLager Registration Page
	 */
	@Step("Validate navigation to ChangeLager Registration Page")
	public void validateNavigation_CL(){

		//Confirm the Head section of the HTML for the page
		this.confirmHeadTag_CL();

		//Confirm the Page Title attribute for the page
		this.confirmPageTitle_CL();

	}

	/**
	 * Navigate to ChangeLager Registration Page,
	 * and confirm that you have landed on the page
	 */
	@Step("Navigate to ChangeLager Registration Page, and validate the navigation")
	public void navigateAndValidate_CL(){

		//Open a browser instance and navigate to the ChangeLager Registration URL
		this.navigateToRegistration_CL();

		//Validate that you have landed on the ChangeLager Registration Page
		this.validateNavigation_CL();
	}


	/**
	 * Toggle from 'Dev' to 'Free' subscription type, and validate
	 */
	@Step("Switch from 'Dev' to 'Free' subscription type")
	public void toggleDevToFree_CL() {

		// Click the "Free Plan" button on the registration
		this.generic.clickElement(ChangeLager_RegistrationPage.button_FreePlan.toString());

		// Confirm the existence of both of plan options
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.button_DevPlanSelect.toString());
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.button_FreePlanSelected.toString());

		// Take screenshot to verify successful transition from Dev to Free Plan
		this.generic.takeScreenShot("of 'Free Plan' being selected");

	}


	/**
	 * Toggle from 'Free' to 'Dev' subscription type, and validate
	 */
	@Step("Switch from 'Free' to 'Dev' subscription type")
	public void toggleFreeToDev_CL() {

		// Click the "Dev Plan" button
		this.generic.clickElement(ChangeLager_RegistrationPage.button_DevPlan.toString());

		// Confirm the "Dev Plan" option is selected; confirm the "Free Plan" option is
		// not selected
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.button_DevPlanSelected.toString());
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.button_FreePlanSelect.toString());

		// Take screenshot to verify successful transition from Free to Dev Plan
		this.generic.takeScreenShot("of 'Dev Plan' being selected");

	}

	/**
	 * Fill in the 'Name' text field of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Name' text field - Profile Section")
	public void fillOutName_CL(){

		// Click the "Name" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.textField_Name.toString(), this.runtimeData.username);
	}

	/**
	 * Fill in the 'Email' text field of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Email' text field - Profile Section")
	public void fillOutEmail_CL(){

		// Click the "Email" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.textField_Email.toString(), this.runtimeData.userEmail);

	}

	/**
	 * Fill in the 'Password' text field of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Password' text field - Profile Section")
	public void fillOutPassword_CL(){

		// Click the "Password" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.textField_Password.toString(), this.runtimeData.password);

	}

	/**
	 * Fill in the 'Confirm Password' text field of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Confirm Password' text field - Profile Section")
	public void fillOutConfirmPassword_CL(){

		// Click the "Confirm Password" textbox, and send it input
		this.generic.sendText(ChangeLager_RegistrationPage.textField_ConfirmPassword.toString(), this.runtimeData.password);

	}

	/**
	 * Fill out the 'Profile' section of the Registration Form
	 */
	@Step("Fill out the 'Profile' section of the form")
	public void fillOutProfileSection_CL() {

		// Click the "Name" textbox, and send it input
		this.fillOutName_CL();

		// Click the "Email" textbox, and send it input
		this.fillOutEmail_CL();

		// Click the "Password" textbox, and send it input
		this.fillOutPassword_CL();

		// Click the "Confirm Password" textbox, and send it input
		this.fillOutConfirmPassword_CL();
	}

    /**
     * Fill in the "Carholder's Name" textbox on the form
     */
    @Step("Fill in 'Cardholder's Name' textbox")
    public void fillInCardholderName_CL(){

        // Click the "Cardholder's Name" textbox, and send it input
        this.generic.sendText(ChangeLager_RegistrationPage.textField_CardholdersName.toString(), "Stinky Stankerton");

    }

    /**
     * Fill in the "Credit or debit card number" textbox on the form
     */
    @Step("Fill in 'Credit or debit card number' textbox")
    public void fillInCreditOrDebit_CL(){

        // Click the "Credit or debit card number" textbox, and send it input
        this.generic.sendText(ChangeLager_RegistrationPage.textField_CardNumber.toString(),	this.runtimeData.creditCardNumber);

    }

    /**
     * Fill in the "Credit or debit expiration date" textbox on the form
     */
    @Step("Fill in the 'Credit or debit expiration date' textbox")
    public void fillInCreditOrDebitExpDate_CL(){

        // Click the "Credit or debit card expiration date" textbox, and send it input
        this.generic.sendText(ChangeLager_RegistrationPage.textField_CardExpire.toString(), this.runtimeData.creditCardExpiration);

    }

    /**
     * Fill in the "Credit or debit CVC/CVV" textbox on the form
     */
    @Step("Fill in the 'Credit or debit CVC/CVV' textbox")
    public void fillInCreditOrDebitCVC_CVV_CL(){

        // Click the "Credit or debit card CVC/CVV" textbox, and send it input
        this.generic.sendText(ChangeLager_RegistrationPage.textField_CardCVC.toString(), this.runtimeData.creditCardCVC);

    }

    /**
     * Fill in the "ZIP / Postal Code" textbox on the form
     */
    @Step("Fill in the 'ZIP / Postal Code' textbox")
    public void fillInZIP_CL(){

        // Click the "ZIP / Postal Code" textbox, and send it input
        this.generic.sendText(ChangeLager_RegistrationPage.textField_ZIPPostalCode.toString(), "11111");

    }


    /**
	 * Fill out the 'Credit Card' section of the Registration Form
	 */
	@Step("Fill out the 'Credit Card' section of the form")
	public void fillOutCreditCardSection_CL() {

        //Fill in Cardholder's Name
        this.fillInCardholderName_CL();

		// Switch to the iFrame containing the textboxes for credit card information
		this.iFrame.switchToIframe(ChangeLager_RegistrationPage.iFrame_CreditCardInformation.toString());

        //Fill in Credit Or Debit
        this.fillInCreditOrDebit_CL();

        //Fill in Credit or Debit Expiration Date
        this.fillInCreditOrDebitExpDate_CL();

        //Fill in Credit or Debit CVC
        this.fillInCreditOrDebitCVC_CVV_CL();

        // Step out of the iframe, and back into the default context of the webpage
		this.iFrame.switchToDefault();

		//Fill in Zip Code
        this.fillInZIP_CL();

		// Click the "Terms and Service" checkbox
		this.generic.clickElement(ChangeLager_RegistrationPage.Locator_checkBox_TermsAndService.toString());
	}

	/**
	 * Click the "Register" button on the ChangeLager Registration Page
	 */
	@Step("Click the 'Register' button")
	public void clickRegisterButton_CL(){

		//Click the 'Register' button
		this.generic.clickElement(ChangeLager_RegistrationPage.button_Register.toString());

	}

	/**
	 * Validate the error message for the "Name" field is displayed
	 */
	@Step("Validate the error message for the 'Name' field is displayed")
	public void validateNameErrorMessage_CL(){

		//Validate the error message text for the "Name" field is displayed
		//when the field is blank
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.text_BlankNameErrorMessage.toString());

	}

	/**
	 * Validate the error message for the "Email" field is displayed
	 */
	@Step("Validate the error message for the 'Email' field is displayed")
	public void validateEmailErrorMessage_CL(){

		//Validate the error message text for the "Email" field is displayed
		//when the field is blank
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.text_BlankEmailErrorMessage.toString());

	}

	/**
	 * Validate the error message for the "Password" field is displayed
	 */
	@Step("Validate the error message for the 'Password' field is displayed")
	public void validatePasswordErrorMessage_CL(){

		//Validate the error message text for the "Password" field is displayed
		//when the field is blank
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.text_BlankPasswordErrorMessage.toString());

	}

	/**
	 * Validate the error message for the "I Accept The Terms Of Service" checkbox is displayed
	 */
	@Step("Validate the error message for the 'I Accept The Terms Of Service' checkbox is displayed")
	public void validateTermsOfServiceErrorMessage_CL(){

		//Validate the error message text for the "I Accept The Terms Of Service" checkbox
		//when the checkbox is not checked
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.text_NonCheckedTermsOfServiceErrorMessage.toString());

	}

	/**
	 * Click the "Terms Of Use" hyperlink
	 */
	@Step("Click the 'Terms Of Use' hyperlink")
	public void clickTermsOfUseHyperlink_CL(){

		//Click the "Terms Of Use" hyperlink
		this.generic.clickElement(ChangeLager_RegistrationPage.link_TermsOfUse.toString());

	}

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
