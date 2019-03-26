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
        homePage.navigateAndValidate_CL();

        //Validate all buttons exist
        homePage.validateAllButtonsExist_CL();

        //DEPRECATED AS OF 02/04/2019; functionality removed from site
        //Validate the "Accept All Cookies" button exists
        //homePage.validateAcceptAllCookies_CL();

        //DEPRECATED AS OF 02/04/2019; functionality removed from site
        //Click the "Accept All Cookies" button
        //homePage.clickAcceptAllCookies_CL();

        //Click the down arrow
        homePage.clickDownArrow_CL();

        //Click the 'Single' registration button
        homePage.clickSingleRegistration_CL();

        //Navigate back to the ChangeLager website, and confirm
        homePage.navigateAndValidate_CL();

        //Click the 'More Than One' registration button
        homePage.clickMoreThanOneRegistration_CL();

        //Navigate back to the ChangeLager website, and confirm
        homePage.navigateAndValidate_CL();

        //Click the down arrow
        homePage.clickDownArrow_CL();

        //Click the 'MetaBit, LLC' hyperlink at the bottom of the ChangeLager Home Page
        homePage.clickMetaBitLink_CL();

        //Validate the page transition
        homePage.validateNavigation_MBLLC();

        //Navigate back to the ChangeLager website, and confirm
        homePage.navigateAndValidate_CL();

        //Click the "Register" button, and confirm page change
        homePage.clickRegisterButton_CL();

        //Toggle between the different plans to ensure they function appropriately
        registrationPage.toggleDevToFree();
        registrationPage.toggleFreeToDev();

        //Fill out the 'Profile' section of the Registration Form
        registrationPage.fillOutProfileSection();

        //Fill out the 'Credit Card' section of the Registration Form
        registrationPage.fillOutCreditCardSection();

    }


}
