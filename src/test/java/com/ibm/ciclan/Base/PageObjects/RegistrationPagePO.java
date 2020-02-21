package com.ibm.ciclan.Base.PageObjects;


import com.ibm.ciclan.Base.RuntimeData;
import com.ibm.ciclan.Base.Generic;
import com.ibm.ciclan.Base.ScreenShot;
import com.ibm.ciclan.Base.TestBase;

import com.ibm.ciclan.Enumerations.ChangeLager.RegistrationPageENUM;

import io.qameta.allure.Step;


public class RegistrationPagePO extends TestBase {

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
	public RegistrationPagePO(Generic generic, ScreenShot ss, RuntimeData runtimeData, String id, String testName, IFramePO iFrame) {

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
		this.generic.getUrl(RegistrationPageENUM.Text_URL.toString());

	}

	/**
	 * Confirm Head Tag of ChangeLager Registration Page
	 */
	@Step("Validate CL Registration Page Head Tag")
	public void confirmHeadTag_CL(){

		//Confirm the Head section of the HTML for the page
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Tag_Head.toString());

	}

	/**
	 * Confirm Page Title of ChangeLager Registration Page
	 */
	@Step("Confirm Page Title of ChangeLager Registration Page")
	public void confirmPageTitle_CL(){

		//Confirm the Page Title attribute for the page
		this.generic.confirmElementExistence(RegistrationPageENUM.Text_PageTitle.toString());

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
	 * Validate that the 'Free Plan' option on the page is selected
	 */
	@Step("Validate that the 'Free Plan' option on the page is selected")
	public void validateFreePlanSelected(){

		//Confirm that the 'Free Plan' option is selected
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Button_FreePlanSelected.toString());

		//Confirm that the 'Developer Plan' option is not selected
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Button_DevPlanSelect.toString());

	}

	/**
	 * Validate that the 'Developer Plan' option on the page is selected
	 */
	@Step("Validate that the 'Developer Plan' option on the page is selected")
	public void validateDevPlanSelected(){

		//Confirm that the 'Developer Plan' option is selected
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Button_DevPlanSelected.toString());

		//Confirm that the 'Free Plan' option is not selected
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Button_FreePlanSelect.toString());

	}


	/**
	 * Toggle from 'Dev' to 'Free' subscription type, and validate
	 */
	@Step("Switch from 'Dev' to 'Free' subscription type")
	public void toggleDevToFree_CL() {

		//Click the "Free Plan" button on the registration
		this.generic.clickElement(RegistrationPageENUM.Locator_Button_FreePlan.toString());

		//Validate that the 'Free Plan' option is selected, and that the 'Developer Plan'
		//option is not
		this.validateFreePlanSelected();

		//Take screenshot to verify successful transition from Dev to Free Plan
		this.generic.takeScreenShot("of 'Free Plan' being selected");

	}


	/**
	 * Toggle from 'Free' to 'Dev' subscription type, and validate
	 */
	@Step("Switch from 'Free' to 'Dev' subscription type")
	public void toggleFreeToDev_CL() {

		//Click the "Dev Plan" button
		this.generic.clickElement(RegistrationPageENUM.Locator_Button_DevPlan.toString());

		//Confirm the "Dev Plan" option is selected; confirm the "Free Plan" option is
		//not selected
		this.validateDevPlanSelected();

		//Take screenshot to verify successful transition from Free to Dev Plan
		this.generic.takeScreenShot("of 'Dev Plan' being selected");

	}

	/**
	 * Fill in the 'Name' textbox of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Name' textbox - Profile Section")
	public void fillOutName_CL(){

		//Click the "Name" textbox, and send it input
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_Name.toString(), this.runtimeData.username);

	}

	/**
	 * Clear the input from the 'Name' textbox
	 */
	@Step("Clear input from the 'Name' textbox")
	public void clearOutName_CL(){

		//Click the "Name" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_Name.toString(), "");

	}

	/**
	 * Fill in the 'Email' textbox of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Email' textbox - Profile Section")
	public void fillOutEmail_CL(){

		//Click the "Email" textbox, and send it input
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_Email.toString(), this.runtimeData.userEmail);

	}

	/**
	 * Clear the input from the 'Email' textbox
	 */
	@Step("Clear input from the 'Email' textbox")
	public void clearOutEmail_CL(){

		//Click the "Email" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_Email.toString(), "");

	}

	/**
	 * Fill in the 'Password' textbox of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Password' textbox - Profile Section")
	public void fillOutPassword_CL(){

		//Click the "Password" textbox, and send it input
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_Password.toString(), this.runtimeData.password);

	}

	/**
	 * Clear the input from the 'Password' textbox
	 */
	@Step("Clear input from the 'Password' textbox")
	public void clearOutPassword_CL(){

		//Click the "Password" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_Password.toString(), "");

	}

	/**
	 * Fill in the 'Confirm Password' textbox of the profile section on the ChangeLager registration page
	 */
	@Step("Fill in the 'Confirm Password' textbox - Profile Section")
	public void fillOutConfirmPassword_CL(){

		//Click the "Confirm Password" textbox, and send it input
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_ConfirmPassword.toString(), this.runtimeData.password);

	}

	/**
	 * Clear the input from the 'Confirm Password' textbox
	 */
	@Step("Clear input from the 'Confirm Password' textbox")
	public void clearOutConfirmPassword_CL(){

		//Click the "Confirm Password" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_ConfirmPassword.toString(), "");

	}

	/**
	 * Fill out the 'Profile' section of the Registration Form
	 */
	@Step("Fill out the 'Profile' section of the form")
	public void fillOutProfileSection_CL() {

		//Click the "Name" textbox, and send it input
		this.fillOutName_CL();

		//Click the "Email" textbox, and send it input
		this.fillOutEmail_CL();

		//Click the "Password" textbox, and send it input
		this.fillOutPassword_CL();

		//Click the "Confirm Password" textbox, and send it input
		this.fillOutConfirmPassword_CL();

	}

	/**
	 * Clear input from the 'Profile' section of the Registration Form
	 */
	@Step("Clear all input from the 'Profile' section of the Registration Form")
	public void clearOutProfileSection_CL() {

		//Click the "Name" textbox, clear it, and send it nothing
		this.clearOutName_CL();

		//Click the "Email" textbox, clear it, and send it nothing
		this.clearOutEmail_CL();

		//Click the "Password" textbox, clear it, and send it nothing
		this.clearOutPassword_CL();

		//Click the "Confirm Password" textbox, clear it, and send it nothing
		this.clearOutConfirmPassword_CL();
}

    /**
     * Fill in the "Carholder's Name" textbox on the form
     */
    @Step("Fill in 'Cardholder's Name' textbox")
    public void fillInCardholderName_CL(){

        //Click the "Cardholder's Name" textbox, and send it input
        this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardholdersName.toString(), "Stinky Stankerton");

    }

	/**
	 * Clear the input from the "Cardholder's Name" textbox
	 */
	@Step("Clear input from the 'Cardholder's Name' textbox")
	public void clearOutCardholderName_CL(){

		//Click the "Cardholder's Name" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardholdersName.toString(), "");

	}

    /**
     * Fill in the "Credit or debit card number" textbox on the form
     */
    @Step("Fill in 'Credit or debit card number' textbox")
    public void fillInCreditOrDebit_CL(){

        //Click the "Credit or debit card number" textbox, and send it input
        this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardNumber.toString(),	this.runtimeData.creditCardNumber);

    }

	/**
	 * Clear the input from the "Credit or debit card number" textbox
	 */
	@Step("Clear input from the 'Credit or debit card number' textbox")
	public void clearOutCreditOrDebit_CL(){

		//Click the "Credit or debit card number" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardNumber.toString(), "");

	}

	/**
     * Fill in the "Credit or debit expiration date" textbox on the form
     */
    @Step("Fill in the 'Credit or debit expiration date' textbox")
    public void fillInCreditOrDebitExpDate_CL(){

        //Click the "Credit or debit card expiration date" textbox, and send it input
        this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardExpire.toString(), this.runtimeData.creditCardExpiration);

    }

	/**
	 * Clear the input from the "Credit or debit expiration date" textbox
	 */
	@Step("Clear input from the 'Credit or debit expiration date' textbox")
	public void clearOutCreditOrDebitExpDate_CL(){

		//Click the "Credit or debit expiration date" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardExpire.toString(), "");

	}

    /**
     * Fill in the "Credit or debit CVC/CVV" textbox on the form
     */
    @Step("Fill in the 'Credit or debit CVC/CVV' textbox")
    public void fillInCreditOrDebitCVC_CVV_CL(){

        //Click the "Credit or debit card CVC/CVV" textbox, and send it input
        this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardCVC.toString(), this.runtimeData.creditCardCVC);

    }

	/**
	 * Clear the input from the "Credit or debit CVC/CVV" textbox
	 */
	@Step("Clear input from the 'Credit or debit CVC/CVV' textbox")
	public void clearOutCreditOrDebitCVC_CVV_CL(){

		//Click the "Credit or debit CVC/CVV" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_CardCVC.toString(), "");

	}

	/**
     * Fill in the "ZIP / Postal Code" textbox on the form
     */
    @Step("Fill in the 'ZIP / Postal Code' textbox")
    public void fillInZIP_CL(){

        //Click the "ZIP / Postal Code" textbox, and send it input
        this.generic.sendText(RegistrationPageENUM.Locator_TextField_ZIPPostalCode.toString(), "11111");

    }

	/**
	 * Clear the input from the "ZIP / Postal Code" textbox
	 */
	@Step("Clear input from the 'ZIP / Postal Code' textbox")
	public void clearOutZIP_CL(){

		//Click the "ZIP / Postal Code" textbox, clear it, and send it nothing
		this.generic.sendText(RegistrationPageENUM.Locator_TextField_ZIPPostalCode.toString(), "");

	}

	/**
	 * Click the 'I Accept The Terms Of Service' checkbox
	 * whether it is checked or not
	 */
	@Step("Click the 'I Accept The Terms Of Service' checkbox")
	public void clickTermsOfServiceCheckbox_CL(){

		//Click the checkbox
		this.generic.clickElement(RegistrationPageENUM.Locator_CheckBox_TermsAndService.toString());

	}

	/**
	 * Click the 'I Accept The Terms Of Service' checkbox
	 * only if it is not already checked
	 */
	@Step("Click the 'I Accept The Terms Of Service' checkbox if it IS NOT already selected")
	public void clickNonCheckedTermsOfServiceCheckbox_CL() {

		//Check to see if the Checkbox IS NOT already selected

		if (this.generic.confirmElementExistence(RegistrationPageENUM.Locator_CheckBox_TermsAndServiceNotChecked.toString()) != null) {

			//If it IS NOT already selected, click the checkbox

			this.generic.clickElement(RegistrationPageENUM.Locator_CheckBox_TermsAndServiceNotChecked.toString());

			//And take a screenshot to verify the change
			this.generic.takeScreenShot("CHECKBOX CLICKED - CHECKBOX SHOULD BE CHECKED");

		} else {

			//Else, outprint message

			System.out.format(" <Checkbox State: ALREADY SELECTED>");

		}

	}

	/**
	 * Click the 'I Accept The Terms Of Service' checkbox
	 * only if it is already checked
	 */
	@Step("Click the 'I Accept The Terms Of Service' checkbox only if it IS already checked")
	public void clickCheckedTermsOfServiceCheckbox_CL() {

		//Check to see if the Checkbox IS already selected

				if (this.generic.confirmElementExistence(RegistrationPageENUM.Locator_CheckBox_TermsAndServiceChecked.toString()) != null) {

					//If it IS already checked, click the checkbox

					this.generic.clickElement(RegistrationPageENUM.Locator_CheckBox_TermsAndServiceChecked.toString());

					//And take a screenshot to verify the change
					this.generic.takeScreenShot("CHECKBOX CLICKED - CHECKBOX SHOULD NOT BE CHECKED");

				} else {

					//Else, outprint message

					System.out.format(" <Checkbox State: ALREADY NOT SELECTED>");

				}

	}

	/**
	 * Fill out the 'Credit Card' section of the Registration Form
	 */
	@Step("Fill out the 'Credit Card' section of the form")
	public void fillOutCreditCardSection_CL() {

        //Fill in Cardholder's Name
        this.fillInCardholderName_CL();

		//Switch to the iFrame containing the textboxes for credit card information
		this.iFrame.switchToIframe(RegistrationPageENUM.Locator_iFrame_CreditCardInformation.toString());

        //Fill in Credit Or Debit
        this.fillInCreditOrDebit_CL();

        //Fill in Credit or Debit Expiration Date
        this.fillInCreditOrDebitExpDate_CL();

        //Fill in Credit or Debit CVC
        this.fillInCreditOrDebitCVC_CVV_CL();

        //Step out of the iframe, and back into the default context of the webpage
		this.iFrame.switchToDefault();

		//Fill in Zip Code
        this.fillInZIP_CL();

		//Click the "Terms and Service" checkbox if it is not already checked
		this.clickNonCheckedTermsOfServiceCheckbox_CL();

	}

	/**
	 * Clear input from the 'Credit Card' section of the Registration Form
	 */
	@Step("Clear all input from the 'Credit Card' section of the Registration Form")
	public void clearOutCreditCardSection_CL() {

		//Click the "Cardholder's Name" textbox, clear it, and send it nothing
		this.clearOutCardholderName_CL();

		//Switch to the iFrame containing the textboxes for credit card information
		this.iFrame.switchToIframe(RegistrationPageENUM.Locator_iFrame_CreditCardInformation.toString());

		//Click the "Credit or debit card number" textbox, clear it, and send it nothing
		this.clearOutCreditOrDebit_CL();

		//Click the "Credit or debit expiration date" textbox, clear it, and send it nothing
		this.clearOutCreditOrDebitExpDate_CL();

		//Click the "Credit or debit CVC/CVV" textbox, clear it, and send it nothing
		this.clearOutCreditOrDebitCVC_CVV_CL();

		//Step out of the iframe, and back into the default context of the webpage
		this.iFrame.switchToDefault();

		//Click the "ZIP / Postal Code" textbox, clear it, and send it nothing
		this.clearOutZIP_CL();

		//Click the "Terms and Service" checkbox only if it is already checked
		this.clickNonCheckedTermsOfServiceCheckbox_CL();

	}

	/**
	 * Fill out the entire Registration Form
	 */
	@Step("Fill out the entire Registration Form")
	public void fillOutEntireRegistrationForm_CL(){

		//Fill out the "Profile" section of the Registration Form
		this.fillOutProfileSection_CL();

		//Fill out the "Credit Card section" of the Registration Form
		this.fillOutCreditCardSection_CL();

	}

	/**
	 * Clear input from the entire Registration Form
	 */
	@Step("Clear input from the entire Registration Form")
	public void clearOutEntireRegistrationForm_CL(){

		//Clear input from the "Profile" section of the Registration Form
		this.clearOutProfileSection_CL();

		//Clear input from the "Credit Card" section of the Registration Form
		this.clearOutCreditCardSection_CL();

	}

	/**
	 * Click the "Register" button on the ChangeLager Registration Page
	 */
	@Step("Click the 'Register' button")
	public void clickRegisterButton_CL(){

		//Click the 'Register' button
		this.generic.clickElement(RegistrationPageENUM.Locator_Button_Register.toString());

	}

	/**
	 * Validate the error message for the "Name" field is displayed
	 */
	@Step("Validate the error message for the 'Name' field is displayed")
	public void validateNameErrorMessageVisible_CL(){

		//Validate the error message text for the "Name" field is displayed
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Text_BlankNameErrorMessageVisible.toString());

	}

	/**
	 * Validate the error message for the "Email" field is displayed
	 */
	@Step("Validate the error message for the 'Email' field is displayed")
	public void validateEmailErrorMessageVisible_CL(){

		//Validate the error message text for the "Email" field is displayed
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Text_BlankEmailErrorMessageVisible.toString());

	}

	/**
	 * Validate the error message for the "Password" field is displayed
	 */
	@Step("Validate the error message for the 'Password' field is displayed")
	public void validatePasswordErrorMessageVisible_CL(){

		//Validate the error message text for the "Password" field is displayed
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Text_BlankPasswordErrorMessageVisible.toString());

	}

	/**
	 * Validate the error message for the "I Accept The Terms Of Service" checkbox is displayed
	 */
	@Step("Validate the error message for the 'I Accept The Terms Of Service' checkbox is displayed")
	public void validateTermsOfServiceErrorMessageVisible_CL(){

		//Validate the error message text for the "I Accept The Terms Of Service" checkbox
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Text_NonCheckedTermsOfServiceErrorMessageVisible.toString());

	}

	/**
	 * Validate the error message for the two password fields not matching is displayed
	 */
	@Step("Validate the error message for the two password fields not matching is displayed")
	public void validatePasswordsDoNotMatchErrorMessageVisible_CL(){

		//Validate the error message is visible
		this.generic.confirmElementExistence(RegistrationPageENUM.Locator_Text_PasswordsDoNotMatchErrorMessageVisible.toString());

	}

	/**
	 * Click the "Terms Of Use" hyperlink
	 */
	@Step("Click the 'Terms Of Use' hyperlink")
	public void clickTermsOfUseHyperLink_CL(){

		//Click the "Terms Of Use" hyperlink
		this.generic.clickElement(RegistrationPageENUM.Locator_Link_TermsOfUse.toString());

	}

	/**
	 * Click the "Have an Account? Login!" hyperlink
	 */
	@Step("Click the 'Have an Account? Login!' hyperlink")
	public void clickHaveAnAccountLogin(){

		//Click the "Have an Account? Login!" hyperlink
		this.generic.clickElement(RegistrationPageENUM.Locator_Link_HaveAnAccountLogin.toString());

	}

	/**
	 * Do something with RegistrationPagePO
	 *
	 * @param somethingToPass String (used only to change Allure pass/fail
	 *                        iconography from circle to arrow)
	 */
	@Step("Doing something with RegistrationPagePO")
	public void stuff(String somethingToPass) {

		System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", this.id, this.testName, somethingToPass);

		this.ss.assertTrue(true, "Some message if fail."); //example of an assertion with screenshot on fail
		//ss.takeScreenShot("Some description"); //example of taking screenshot, on
		//demand
		//generic.clickElement("Some.Property.Key"); //example of using generic
		//methods

	}


}
