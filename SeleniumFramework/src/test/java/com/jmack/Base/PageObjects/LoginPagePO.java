package com.jmack.Base.PageObjects;

import com.jmack.Base.Generic;
import com.jmack.Base.RuntimeData;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;
import com.jmack.Enumerations.ChangeLagerApp.LoginPageENUM;
import io.qameta.allure.Step;

public class LoginPagePO extends TestBase{

    private Generic generic;
    private ScreenShot ss;
    private String id = "unknown";
    private String testName = "unknown";

    /**
     * Minimum constructor
     * @param generic
     * @param ss
     */
    public LoginPagePO(Generic generic, ScreenShot ss) {

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
    public LoginPagePO(Generic generic, ScreenShot ss, RuntimeData runtimeData, String id, String testName) {

        this.generic = generic;
        this.runtimeData = runtimeData;
        this.ss = ss;
        this.id = id;
        this.testName = testName;

    }

    /**
     * Navigate to the ChangeLager Login Page
     */
    @Step("Navigate to the ChangeLager Login Page")
    public void navigateToLogin_CL(){

        //Navigate to the ChangeLager Login Page
        logInPage.generic.getUrl(LoginPageENUM.Text_URL.toString());
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
