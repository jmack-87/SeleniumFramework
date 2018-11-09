package com.jmack.Tests.Desktop;


import javafx.scene.control.TextFormatter;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jmack.Base.TestBase;
import com.jmack.Base.CustomAnnotations.RetryOnFailCount;
import com.jmack.Enumerations.Generic;
import com.jmack.Enumerations.ChangeLager_LoginPage;
import com.jmack.Enumerations.ChangeLager_RegistrationPage;
//import com.jmack.Enumerations.SearchPage;
//import com.jmack.Enumerations.SearchResults;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Parallelism")
@Feature("Chrome")
public class ChromeFeature extends TestBase {

	/**
	 * Perform a google search. Confirm and click first result. Confirm navigation.
	 * @param testParam optional TestNG value from suite
	 * @throws InterruptedException
	 */
	@Test(testName="Chrome Test", description="Run Chrome browser in parallel.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Run Chrome browser in parallel.")
	@Story("Run Chrome, Firefox, Edge, InternetExplorer in parallel.")
	@Parameters({"testParam"})
	@RetryOnFailCount(0)
	public void ChromeTest(@Optional String testParam) throws InterruptedException {

	    //Open a new browser instance, and navigate to the ChangeLager URL
		generic.getUrl(Generic.Text_changeLagerLoginURL.toString());

		//Confirm the Head section of the HTML for the page, as well as the title of the page
	    generic.confirmElementExistence(ChangeLager_LoginPage.Locator_Tag_head.toString());
	    generic.confirmTitle(ChangeLager_LoginPage.Text_pageTitle.toString());

	    //Click the "Register" button on the login page
	    generic.clickElement(ChangeLager_LoginPage.Locator_Button_Register.toString());

	    //Click the "Free Plan" button on the registration
		generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_FreePlan.toString());

		//Confirm the existence of both of plan options
		generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelect.toString());
		generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelected.toString());

		//Click the "Dev Plan" button
	    generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_DevPlan.toString());

	    //Confirm the "Dev Plan" option is selected; confirm the "Free Plan" option is not selected
	    generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelected.toString());
	    generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelect.toString());

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
		generic.switchToIframe(ChangeLager_RegistrationPage.Locator_iFrame_CreditCardInformation.toString());

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
		generic.switchToDefault();

	    //Click the "ZIP / Postal Code" textbox, and send it input
	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString(), "11111");

	    //Click the "Terms and Service" checkbox
	    generic.clickElement(ChangeLager_RegistrationPage.Locator_checkBox_TermsAndService.toString());

//		generic.getUrl(Generic.Text_googleURL.toString());
//
//      generic.confirmElementExistence(SearchPage.Locator_Tag_head.toString());
//		generic.confirmTitle(SearchPage.Text_pageTitle.toString());
//
//		generic.sendText(SearchPage.Locator_TextField_searchInput.toString(), runtimeData.searchString);
//		generic.clickElement(SearchPage.Locator_Button_searchSubmit.toString());
//
//		generic.confirmElementExistence(SearchResults.Locator_firstResult.toString());
//		generic.confirmElementExistence(SearchResults.CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString);
//		generic.clickElement(SearchResults.Locator_firstResult.toString());
//
//		generic.waitForPageLoaded(30);
//
//		generic.confirmElementExistence(SearchResults.Locator_Text_ibmSearchConfirmation.toString());
//
//		homePage.stuff("something passed");
		
	}
	
}
