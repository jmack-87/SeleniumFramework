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
	@RetryOnFailCount(2)
	public void ChromeTest(@Optional String testParam) throws InterruptedException {

	    generic.getUrl(Generic.Text_changeLagerLoginURL.toString());

	    generic.confirmElementExistence(ChangeLager_LoginPage.Locator_Tag_head.toString());
	    generic.confirmTitle(ChangeLager_LoginPage.Text_pageTitle.toString());

	    generic.clickElement(ChangeLager_LoginPage.Locator_Button_Register.toString());

		generic.waitForPageCompletelyLoaded(10);

		generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_FreePlan.toString());

		generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelect.toString());
		generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelected.toString());

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_Button_DevPlan.toString());

	    generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_DevPlanSelected.toString());
	    generic.confirmElementExistence(ChangeLager_RegistrationPage.Locator_Button_FreePlanSelect.toString());

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_Name.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Name.toString(), "@U$T1N D3V G0D");

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_Email.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Email.toString(),"l33th4x0r@nyancat.com");

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_Password.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_Password.toString(), runtimeData.password);

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_ConfirmPassword.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_ConfirmPassword.toString(), runtimeData.password);

	    generic.scrollDown();

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardholdersName.toString(), "Stinky Stankerton");

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardNumber.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardNumber.toString(), runtimeData.creditCardNumber);

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardExpire.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardExpire.toString(), runtimeData.creditCardExpiration);

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_CardCVC.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_CardCVC.toString(), runtimeData.creditCardCVC);

	    generic.clickElement(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString());
	    generic.sendText(ChangeLager_RegistrationPage.Locator_textField_ZIPPostalCode.toString(), "11111");

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
