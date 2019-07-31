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
	 * Confirm Head Tag of ChangeLager Login Page
	 */
	@Step("Validate CL Login Page Head Tag")
	public void confirmHeadTag_CL(){

		//Confirm the Head section of the HTML for the page
		this.generic.confirmElementExistence(LoginPageENUM.Locator_Tag_Head.toString());

	}

	/**
	 * Confirm Page Title of ChangeLager Login Page
	 */
	@Step("Confirm Page Title of ChangeLager Login Page")
	public void confirmPageTitle_CL(){

		//Confirm the Page Title attribute for the page
		this.generic.confirmElementExistence(LoginPageENUM.Text_PageTitle.toString());

	}

	/**
	 * Validate that you have landed on the ChangeLager Login Page
	 */
	@Step("Validate navigation to ChangeLager Login Page")
	public void validateNavigation_CL(){

		//Confirm the Head section of the HTML for the page
		this.confirmHeadTag_CL();

		//Confirm the Page Title attribute for the page
		this.confirmPageTitle_CL();

	}

	/**
	 * Navigate to ChangeLager Login Page,
	 * and confirm that you have landed on the page
	 */
	@Step("Navigate to ChangeLager Login Page, and validate the navigation")
	public void navigateAndValidate_CL(){

		//Open a browser instance and navigate to the ChangeLager Login URL
		this.navigateToLogin_CL();

		//Validate that you have landed on the ChangeLager Login Page
		this.validateNavigation_CL();
	}

	/**
	 * Fill in the 'Username' textbox of the Login section on the ChangeLager login page
	 */
	@Step("Fill in the 'Username' textbox - Login Section")
	public void fillOutUsername_CL(){

		//Click the "Name" textbox, and send it input
		this.generic.sendText(LoginPageENUM.Locator_TextField_Username.toString(), this.runtimeData.username);

	}

	/**
	 * Clear the input from the 'Username' textbox
	 */
	@Step("Clear input from the 'Username' textbox")
	public void clearOutName_CL(){

		//Click the "Name" textbox, clear it, and send it nothing
		this.generic.sendText(LoginPageENUM.Locator_TextField_Username.toString(), "");

	}

	/**
	 * Fill in the 'Password' textbox of the Login section on the ChangeLager Login page
	 */
	@Step("Fill in the 'Password' textbox - Login Section")
	public void fillOutPassword_CL(){

		//Click the "Password" textbox, and send it input
		this.generic.sendText(LoginPageENUM.Locator_TextField_Password.toString(), this.runtimeData.password);

	}

	/**
	 * Clear the input from the 'Password' textbox
	 */
	@Step("Clear input from the 'Password' textbox")
	public void clearOutPassword_CL(){

		//Click the "Password" textbox, clear it, and send it nothing
		this.generic.sendText(LoginPageENUM.Locator_TextField_Password.toString(), "");

	}

	/**
	 * Click the 'Remember Me' checkbox
	 */
	@Step("Click the 'Remember Me' checkbox")
	public void clickRememberMeCheckbox_CL(){

		//Click the checkbox
		this.generic.clickElement(LoginPageENUM.Locator_Checkbox_RememberMe.toString());

	}

	/**
	 * Fill out the 'Login' section of the ChangeLager Login page
	 */
	@Step("Fill out the 'Login' section of the page")
	public void fillOutLoginSection_CL() {

		//Fill in 'Username' textbox
		this.fillOutUsername_CL();

		//Fill in 'Password' textbox
		this.fillOutPassword_CL();

		//Click the 'Remember Me' checkbox
		this.clickRememberMeCheckbox_CL();
	}

	/**
	 * Clear all input from the 'Login' section of the ChangeLager Login page
	 */
	@Step("Clear all input from the 'Login' section of the page")
	public void clearOutLoginSection_CL() {

	}

	/**
	 * Click the 'Forgot Password?' hyperlink
	 */
	@Step("Click the 'Forgot Password' hyperlink")
	public void clickForgotPasswordLink_CL() {

		//Click the link
		this.generic.clickElement(LoginPageENUM.Locator_Link_ForgotPassword.toString());

	}

	/**
	 * Click the 'Sign In' button
	 */
	@Step("Click the 'Sign In' button")
	public void clickSignInButton_CL() {

		//Click the button
		this.generic.clickElement(LoginPageENUM.Locator_Button_SignIn.toString());

	}

	/**
	 * Click the 'Github' link
	 */
	@Step("Click the 'Github' link")
	public void clickGithubLink_CL() {

		//Click the link
		this.generic.clickElement(LoginPageENUM.Locator_Link_Github.toString());

	}


	/**
	 * Click the 'Bitbucket' link
	 */
	@Step("Click the 'Bitbucket' link")
	public void clickBitbucketLink_CL() {

		//Click the link
		this.generic.clickElement(LoginPageENUM.Locator_Link_Bitbucket.toString());

	}

	/**
	 * Click the 'Gitlab' link
	 */
	@Step("Click the 'Gitlab' link")
	public void clickGitlabLink_CL() {

		//Click the link
		this.generic.clickElement(LoginPageENUM.Locator_Link_Gitlab.toString());

	}

	/**
	 * Click the 'No Account? Register!' link
	 */
	@Step("Click the 'No Account? Register!' link")
	public void clickNoAccountRegister_CL() {

		//Click the link
		this.generic.clickElement(LoginPageENUM.Locator_Link_NoAccountRegister.toString());

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
