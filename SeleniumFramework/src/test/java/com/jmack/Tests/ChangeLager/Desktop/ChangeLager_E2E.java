package com.jmack.Tests.ChangeLager.Desktop;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jmack.Base.TestBase;
import com.jmack.Base.CustomAnnotations.RetryOnFailCount;

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
    @Parameters({})
    @RetryOnFailCount(0)
    public void ChangeLager_E2ETest() {

        //Navigate to the ChangeLager website, and confirm
        homePagePO.navigateAndValidate_CL();

        //Validate all buttons exist
        homePagePO.validateAllButtonsExist_CL();

        //Click the down arrow
        homePagePO.clickDownArrow_CL();

        //Click the 'Single' registration button
        homePagePO.clickSingleRegistration_CL();

        //Validate navigation to the ChangeLager Registration Page
        registrationPagePO.validateNavigation_CL();

        //Navigate back to the ChangeLager website, and confirm
        homePagePO.navigateAndValidate_CL();

        //Click the 'More Than One' registration button
        homePagePO.clickMoreThanOneRegistration_CL();

        //Validate navigation to the ChangeLager Registration Page
        registrationPagePO.validateNavigation_CL();

        //Navigate back to the ChangeLager website, and confirm
        homePagePO.navigateAndValidate_CL();

        //Click the down arrow
        homePagePO.clickDownArrow_CL();

        //Click the 'MetaBit, LLC' hyperlink at the bottom of the ChangeLager Home Page
        homePagePO.clickMetaBitLink_CL();

        //Validate the page transition
        homePagePO.validateNavigation_MBLLC();

        //Navigate back to the ChangeLager website, and confirm
        homePagePO.navigateAndValidate_CL();

        //Click the "Register" button, and confirm page change
        homePagePO.clickRegisterButton_CL();

        //Validate navigation to the ChangeLager Registration Page
        registrationPagePO.validateNavigation_CL();

        //Toggle from the "Developer" option to the "Free" option
        registrationPagePO.toggleDevToFree_CL();

        //Click the "Register" button, and confirm page change
        registrationPagePO.clickRegisterButton_CL();

        //Validate the "Name" fields error message is visible
        registrationPagePO.validateNameErrorMessageVisible_CL();

        //Validate the "Email" fields error message is visible
        registrationPagePO.validateEmailErrorMessageVisible_CL();

        //Validate the "Password" fields error message is visible
        registrationPagePO.validatePasswordErrorMessageVisible_CL();

        //Validate the "Terms Of Service" error message is visible
        registrationPagePO.validateTermsOfServiceErrorMessageVisible_CL();

        //Fill in the "Name" field
        registrationPagePO.fillOutName_CL();

        //Click the "Register" button
        registrationPagePO.clickRegisterButton_CL();

        //Validate the "Email" error message is still visible
        registrationPagePO.validateEmailErrorMessageVisible_CL();

        //Validate the "Password" error message is still visible
        registrationPagePO.validatePasswordErrorMessageVisible_CL();

        //Validate the "Terms Of Service" error message is still visible
        registrationPagePO.validateTermsOfServiceErrorMessageVisible_CL();

        //Fill in the "Email" field
        registrationPagePO.fillOutEmail_CL();

        //Click the "Register" button
        registrationPagePO.clickRegisterButton_CL();

        //Validate the "Password" error message is still visible
        registrationPagePO.validatePasswordErrorMessageVisible_CL();

        //Validate the "Terms Of Service" error message is still visible
        registrationPagePO.validateTermsOfServiceErrorMessageVisible_CL();

        //Fill in the "Password" field
        registrationPagePO.fillOutPassword_CL();

        //Click the "Register" button
        registrationPagePO.clickRegisterButton_CL();

        //Validate the "Terms Of Service" error message is still visible
        registrationPagePO.validateTermsOfServiceErrorMessageVisible_CL();

        //Click the "Terms Of Service" checkbox
        registrationPagePO.clickTermsOfServiceCheckbox_CL();

        //Toggle form the "Free" option to the "Developer" option
        registrationPagePO.toggleFreeToDev_CL();

        //Fill out the 'Profile' section of the Registration Form
        registrationPagePO.fillOutProfileSection_CL();

        //Fill out the 'Credit Card' section of the Registration Form
        registrationPagePO.fillOutCreditCardSection_CL();

    }


}
