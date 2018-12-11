package com.jmack.Base.PageObjects;

import com.jmack.Base.Generic;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;

import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_HomePage;
import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_RegistrationPage;
import io.qameta.allure.Step;


/**
 *
 * @author Jerimiah Mack
 *
 */
public class HomePagePO extends TestBase {

	private ScreenShot ss;
	private String id = "unknown";
	private String testName = "unknown";


	/**
	 * Minimum constructor
	 * @param generic
	 * @param ss
	 */
	public HomePagePO(Generic generic, ScreenShot ss) {

		this.generic = super.generic;
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
	public HomePagePO(Generic generic, ScreenShot ss, String id, String testName) {

		this.generic = generic;
		this.ss = ss;
		this.id = id;
		this.testName = testName;


	}


	/**
	 * Navigate to ChangeLager Home Page and Confirm
	 */
	@Step("Validate ChangeLager HomePage URL")
	public void navigateAndValidate(){

//        System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", id, testName);

		//Open a new browser instance, and navigate to the ChangeLager URL
		this.generic.getUrl(ChangeLager_HomePage.Text_changeLagerHomeURL.toString());

		//Confirm the Head section of the HTML for the page, as well as the title of the page
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Tag_head.toString());
		this.generic.confirmTitle(ChangeLager_HomePage.Text_pageTitle.toString());

	}

	/**
	 * From the ChangeLager Home Page, navigate to the Registration Page via the "Register"
	 * Button, and confirm
	 */
	@Step("Navigate to ChangeLager Registration Page from Home Page using the 'Register' button")
	public void navigateToRegistrationViaRegisterButton(){

//        System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on LogInPage>%n", id, testName);

		//Click the "Register" button on the login page
		this.generic.clickElement(ChangeLager_HomePage.Locator_Button_Register.toString());

		//Confirm the Head section of the HTML for the page, as well as the title of the page
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Tag_head.toString());
		this.generic.confirmTitle(ChangeLager_RegistrationPage.Text_pageTitle.toString());
	}

	/**
	 * From the ChangeLager Home Page, navigate to the Registration Page via the "Sign up for Free"
	 * Button, and confirm
	 */
	@Step("Navigate to ChangeLager Registration Page from Log-In Page using the 'Sign up for free' button")
	public void navigateToRegistrationViaSignUpButton(){

		//Click the "Sign up for free" button on the login page
		this.generic.clickElement(ChangeLager_HomePage.Locator_Button_SignUp.toString());

		//Confirm the Head section of the HTML for the page, as well as the title of the page
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Tag_head.toString());
		this.generic.confirmElementExistence(ChangeLager_RegistrationPage.Text_pageTitle.toString());
	}

	/**
	 * On the ChangeLager Home Page, validate all buttons exist
	 */
	@Step("Validate all Buttons on the ChangeLager Home Page exist")
	public void validateAllButtonsExist(){

		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_Login.toString());
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_Register.toString());
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_SignUp.toString());
	}

	/**
	 *  Do something with HomePage
	 *  @param somethingToPass String (used only to change Allure pass/fail iconography from circle to arrow)
	 */
	@Step("Doing something with HomePage")
	public void stuff(String somethingToPass) {

		System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on HomePage>%n", this.id, this.testName, somethingToPass);

		this.ss.assertTrue(true, "Some message if fail."); // example of an assertion with screenshot on fail
		//ss.takeScreenShot("Some description"); // example of taking screenshot, on demand
		//generic.clickElement("Some.Property.Key"); // example of using generic methods

	}


}
