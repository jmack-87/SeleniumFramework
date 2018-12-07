package com.jmack.Tests.Desktop.ChromeFeatures.ChangeLager;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jmack.Base.TestBase;
import com.jmack.Base.CustomAnnotations.RetryOnFailCount;
import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_LoginPage;
import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_RegistrationPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Parallelism")
@Feature("Chrome")
public class ChangeLager_E2E extends TestBase {

    /**
     * Perform E2E test for all relevant ChangeLager Pages
     * @param testParam optional TestNG value from suite
     */
    @Test(testName="ChangeLager E2E Test", description="Perform general E2E test, in parallel, across multiple threads")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: Perform general E2E test, in parallel, across multiple threads")
    @Story("Run Chrome, Firefox, Edge, InternetExplorer in parallel.")
    @Parameters({"testParam"})
    @RetryOnFailCount(0)
    public void ChangeLager_E2E(@Optional String testParam) {

        logInPage.navigateAndValidate();

        //Take screenshot to verify successful page navigation
        generic.takeScreenShot("of successful navigation to 'Registration' page");

        //Click the "Free Plan" button on the registration
        generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_FreePlan.toString());

        //Confirm the existence of both of plan options
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelect.toString());
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelected.toString());

        //Take screenshot to verify successful transition from Dev to Free Plan
        generic.takeScreenShot("of 'Free Plan' being selected");

        //Click the "Dev Plan" button
        generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_DevPlan.toString());

        //Confirm the "Dev Plan" option is selected; confirm the "Free Plan" option is not selected
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelected.toString());
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelect.toString());

        //Take screenshot to verify successful transition from Free to Dev Plan
        generic.takeScreenShot("of 'Dev Plan' being selected");

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

        //Click the "Cardholder's Name" textbox, and send it input
        generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString());
        generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString(), "Stinky Stankerton");

        //Scroll the page downward
        generic.scrollDown();

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

        //Take Screenshot to verify the form is filled in with the appropriate details
        generic.takeScreenShot("of form after being filled with input");

    }

}
