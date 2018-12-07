package com.jmack.Base.PageObjects;

import com.jmack.Base.Generic;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;
import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_LoginPage;
import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_RegistrationPage;
import io.qameta.allure.Step;

public class LogInPage extends TestBase{

    private Generic generic;
    private ScreenShot ss;
    private String id = "unknown";
    private String testName = "unknown";

    /**
     * Minimum constructor
     * @param generic
     * @param ss
     */
    public LogInPage(Generic generic, ScreenShot ss) {

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
    public LogInPage(Generic generic, ScreenShot ss, String id, String testName) {

        this.generic = generic;
        this.ss = ss;
        this.id = id;
        this.testName = testName;

    }

    /**
     * Navigate to ChangeLager Log-In Page and Confirm
     */
    @Step("Validate ChangeLager LogInPage URL")
    public void navigateAndValidate(){

//        System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", id, testName);

        //Open a new browser instance, and navigate to the ChangeLager URL
        generic.getUrl(ChangeLager_LoginPage.Text_changeLagerLoginURL.toString());

        //Confirm the Head section of the HTML for the page, as well as the title of the page
        generic.confirmElementExistence(ChangeLager_LoginPage.Locator_Tag_head.toString());
        generic.confirmTitle(ChangeLager_LoginPage.Text_pageTitle.toString());
    }

    /**
     * From the ChangeLager Log-In Page, navigate to the Registration Page via the "Register"
     * Button, and confirm
     */
    @Step("Navigate to ChangeLager Registration Page from Log-In Page using the 'Register' button")
    public void navigateToRegistrationViaRegisterButton(){

//        System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", id, testName);

        //Click the "Register" button on the login page
        generic.clickElement(ChangeLager_LoginPage.Locator_Button_Register.toString());

        //Confirm the Head section of the HTML for the page, as well as the title of the page
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Tag_head.toString());
        generic.confirmTitle(ChangeLager_RegistrationPage.Text_pageTitle.toString());
    }

    /**
     * From the ChangeLager Log-In Page, navigate to the Registration Page via the "Sign up for Free"
     * Button, and confirm
     */
    @Step("Navigate to ChangeLager Registration Page from Log-In Page using the 'Sign up for free' button")
    public void navigateToRegistrationViaSignUpButton(){

        //Click the "Sign up for free" button on the login page
        generic.clickElement(ChangeLager_LoginPage.Locator_Button_SignUp.toString());

        //Confirm the Head section of the HTML for the page, as well as the title of the page
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Tag_head.toString());
        generic.confirmElementExistence(ChangeLager_RegistrationPage.Text_pageTitle.toString());
    }

    /**
     * On the ChangeLager Log-In, validate all buttons exist
     */
    @Step("Validate all Buttons on the ChangeLager Log-In Page exist")
    public void validateAllButtonsExist(){

        generic.confirmElementExistence(ChangeLager_LoginPage.Locator_Button_Login.toString());
        generic.confirmElementExistence(ChangeLager_LoginPage.Locator_Button_Register.toString());
        generic.confirmElementExistence(ChangeLager_LoginPage.Locator_Button_SignUp.toString());
    }

    /**
     *  Do something with LogInPage
     *  @param somethingToPass String (used only to change Allure pass/fail iconography from circle to arrow)
     */
    @Step("Doing something with LogInPage")
    public void stuff(String somethingToPass) {

        System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", id, testName, somethingToPass);

        ss.assertTrue(true, "Some message if fail."); // example of an assertion with screenshot on fail
        //ss.takeScreenShot("Some description"); // example of taking screenshot, on demand
        //generic.clickElement("Some.Property.Key"); // example of using generic methods

    }
}
