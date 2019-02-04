package com.jmack.Base.PageObjects;

import com.jmack.Base.Generic;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;

import com.jmack.Enumerations.ChangeLagerApp.ChangeLager_HomePage;

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
	 *
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
	 * Navigate to ChangeLager Home Page
	 */
	@Step("Open ChangeLager Webpage")
	public void navigateTo_CL() {

		// Open a new browser instance and navigate to the ChangeLager URL
		this.generic.getUrl(ChangeLager_HomePage.Text_changeLagerHomeURL.toString());

	}


	/**
	 * Confirm Head Tag of ChangeLager Home Page
	 */
	@Step("Validate CL Home Page Head Tag")
	public void confirmHeadTag_CL() {

		// Confirm the Head section of the HTML for the page
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Tag_head.toString());

	}


	/**
	 * Navigate to ChangeLager Home Page and Confirm
	 */
	@Step("Validate ChangeLager HomePage URL")
	public void navigateAndValidate_CL() {

		// Open a new browser instance, and navigate to the ChangeLager URL
		this.navigateTo_CL();

		// Confirm the Head section of the HTML for the page
		this.confirmHeadTag_CL();

		// Confirm the Title of the page
		this.generic.confirmTitle(ChangeLager_HomePage.Text_pageTitle.toString());

	}


	/**
	 * Validate the 'Login' button on the ChangeLager Home Page
	 */
	@Step("Validate 'Login' button on the ChangeLager Home page")
	public void validateLoginButton_CL(){

		//Validate the button exists
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_Login.toString());
	}

	/**
	 * Validate the 'Register' button on the ChangeLager Home Page
	 */
	@Step("Validate 'Register' button on the ChangeLager Home page")
	public void validateRegisterButton_CL(){

		//Validate the button exists
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_Register.toString());

	}

	/**
	 * Validate the 'Sign up for free' button on the ChangeLager Home Page
	 */
	@Step("Validate 'Sign up for free' button on the ChangeLager Home page")
	public void validateSignUpButton_CL(){

		//Validate the button exists
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_SignUp.toString());

	}

	/**
	 * Validate the down arrow button on the ChangeLager Home Page
	 */
	@Step("Validate the down arrow button on the ChangeLager Home Page")
	public void validateDownArrowButton_CL(){

		//Validate the button exists
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_DownArrow.toString());

	}


	/**
	 * On the ChangeLager Home Page, validate all buttons exist
	 */
	@Step("Validate all Buttons on the ChangeLager Home Page exist")
	public void validateAllButtonsExist_CL(){

		validateLoginButton_CL();
		validateRegisterButton_CL();
		validateSignUpButton_CL();
		validateDownArrowButton_CL();

	}

	/**
	 * On the ChangeLager Home Page, validate the existence of the "Accept All Cookies" button
	 */
	@Step("Validate the 'Accept All Cookies' button on the ChangeLager Home Page exists")
	public void validateAcceptAllCookies_CL(){

		//validate the button exists
		this.generic.confirmElementExistence(ChangeLager_HomePage.Locator_Button_AcceptAllCookies.toString());
	}

	/**
	 * Click the 'Register' button
	 */
	@Step("Click the 'Register' button")
	public void clickRegisterButton_CL() {

		// Click the "Register" button on the home page
		this.generic.clickElement(ChangeLager_HomePage.Locator_Button_Register.toString());

	}


	/**
	 * Click the 'SignUp' button
	 */
	@Step("Click the 'Sign Up' button")
	public void clickSignUpButton_CL() {

		// Click the "Sign up for free" button on the home page
		this.generic.clickElement(ChangeLager_HomePage.Locator_Button_SignUp.toString());

	}


	/**
	 * Click the 'Login' button
	 */
	@Step("Click the 'Login' button")
	public void clickLoginButton_CL() {

		// Click the "Login" button on the home page
		this.generic.clickElement(ChangeLager_HomePage.Locator_Button_Login.toString());

	}

	/**
	 * Click the 'Accept All Cookies' button
	 */
	@Step("Click the 'Accept All Cookies' button")
	public void clickAcceptAllCookies_CL(){

		//Click the "Accept All Cookies" button on the home page
		this.generic.clickElement(ChangeLager_HomePage.Locator_Button_AcceptAllCookies.toString());
	}

	/**
	 * Click the down arrow button
	 */
	@Step("Click the down arrow button")
	public void clickDownArrow_CL(){

		//Click the down arrow button on the home page
		this.generic.clickElement(ChangeLager_HomePage.Locator_Button_DownArrow.toString());

	}


	/**
	 * Do something with HomePage
	 *
	 * @param somethingToPass String (used only to change Allure pass/fail iconography from circle to arrow)
	 */
	@Step("Doing something with HomePage")
	public void stuff(String somethingToPass) {

		System.out.format("[LOG]: <[%s:%s] testing: \"%s\" on HomePage>%n", this.id, this.testName, somethingToPass);

		this.ss.assertTrue(true, "Some message if fail.");

	}


}
