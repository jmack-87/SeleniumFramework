package com.jmack.Base.PageObjects;

import com.jmack.Base.Generic;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;
import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_RegistrationPage;
import io.qameta.allure.Step;

public class RegistrationPage extends TestBase{

    private Generic generic;
    private ScreenShot ss;
    private String id = "unknown";
    private String testName = "unknown";

    /**
     * Minimum constructor
     * @param generic
     * @param ss
     */
    public RegistrationPage(Generic generic, ScreenShot ss) {

        this.generic = generic;
        this.ss = ss;

    }

    /**
     * Constructor provisioned for instance logging
     * @param generic
     * @param ss
     * @param id
     * @param testName
     */
    public RegistrationPage(Generic generic, ScreenShot ss, String id, String testName) {

        this.generic = generic;
        this.ss = ss;
        this.id = id;
        this.testName = testName;

    }

    /**
     * Toggle from 'Dev' to 'Free' subscription type, and validate
     */
    @Step("Switch from 'Dev' to 'Free' subscription type")
    public void toggleDevToFree(){

        //Click the "Free Plan" button on the registration
        generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_FreePlan.toString());

        //Confirm the existence of both of plan options
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelect.toString());
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelected.toString());

        //Take screenshot to verify successful transition from Dev to Free Plan
        generic.takeScreenShot("of 'Free Plan' being selected");
    }

    /**
     * Toggle from 'Free' to 'Dev' subscription type, and validate
     */
    @Step("Switch from 'Free' to 'Dev' subscription type")
    public void toggleFreeToDev(){

        //Click the "Dev Plan" button
        generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_DevPlan.toString());

        //Confirm the "Dev Plan" option is selected; confirm the "Free Plan" option is not selected
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelected.toString());
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelect.toString());

        //Take screenshot to verify successful transition from Free to Dev Plan
        generic.takeScreenShot("of 'Dev Plan' being selected");
    }

    /**
     * Fill out the 'Profile' section of the Registration Form
     */
    @Step("Fill out the 'Profile' section of the form")
    public void fillOutProfileSection(){

        //Click the "Name" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_Name.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Name.toString(), "@U$T1N D3V G0D");

        //Click the "Email" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_Email.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Email.toString(),"l33th4x0r@nyancat.com");

        //Click the "Password" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_Password.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Password.toString(), runtimeData.password);

        //Click the "Confirm Password" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_ConfirmPassword.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_ConfirmPassword.toString(), runtimeData.password);
    }

    /**
     * Fill out the 'Credit Card' section of the Registration Form
     */
    @Step("Fill out the 'Credit Card' section of the form")
    public void fillOutCreditCardSection(){

        //Click the "Cardholder's Name" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString(), "Stinky Stankerton");

        //Switch to the iFrame containing the textboxes for credit card information
        iFrame.switchToIframe(ChangeLager_RegistrationPage.Locator_iFrame_CreditCardInformation.toString());

        //Click the "Credit or debit card number" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardNumber.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardNumber.toString(), runtimeData.creditCardNumber);

        //Click the "Credit or debit card expiration date" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardExpire.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardExpire.toString(), runtimeData.creditCardExpiration);

        //Click the "Credit or debit card CVC/CVV" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardCVC.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardCVC.toString(), runtimeData.creditCardCVC);

        //Step out of the iframe, and back into the default context of the webpage
        iFrame.switchToDefault();

        //Click the "ZIP / Postal Code" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString(), "11111");

        //Click the "Terms and Service" checkbox
        generic.clickElement(ChangeLager_RegistrationPage.Locator_checkBox_TermsAndService.toString());
    }

    /**
     *  Do something with RegistrationPage
     *  @param somethingToPass String (used only to change Allure pass/fail iconography from circle to arrow)
     */
    @Step("Doing something with RegistrationPage")
    public void stuff(String somethingToPass) {

        System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", id, testName, somethingToPass);

        ss.assertTrue(true, "Some message if fail."); // example of an assertion with screenshot on fail
        //ss.takeScreenShot("Some description"); // example of taking screenshot, on demand
        //generic.clickElement("Some.Property.Key"); // example of using generic methods

    }
}
