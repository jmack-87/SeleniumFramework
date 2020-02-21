package com.ibm.ciclan.Tests.AholdDelhaize.Mobile;


import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ibm.ciclan.Base.TestBase;
import com.ibm.ciclan.Base.CustomAnnotations.RetryOnFailCount;
import com.ibm.ciclan.Enumerations.Generic;
import com.ibm.ciclan.Enumerations.AholdDelhaize.SearchPage;
import com.ibm.ciclan.Enumerations.AholdDelhaize.SearchResults;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Parallelism")
@Feature("iOS Safari")
public class IosSafariFeature extends TestBase {

	/**
	 * Perform a google search. Confirm and click first result. Confirm navigation.
	 * @param testParam optional TestNG value from suite
	 * @throws InterruptedException
	 */
	@Test(testName="Ios Safari Test", description="Run Ios Safari browser in parallel.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Run Ios Safari browser.")
	@Story("Run Ios Safari.")
	@Parameters({"testParam"})
	@RetryOnFailCount(0)
	public void IosSafariTest(@Optional String testParam) throws InterruptedException {

		// launch google.com
		mGeneric.getUrl(Generic.Text_googleURL.toString());

		// confirm google.com is launched
		mGeneric.confirmElementExistence(SearchPage.Locator_Tag_head.toString());
		mGeneric.confirmTitle(SearchPage.Text_pageTitle.toString());

		// set search field
		mGeneric.sendText(SearchPage.Mobile_Locator_TextField_searchInput.toString(), runtimeData.searchString);

		// submit search
		mGeneric.clickElement(SearchPage.Mobile_Locator_Button_searchSubmit.toString());

		// confirm at least one result returns
		mGeneric.confirmElementExistence(SearchResults.Locator_firstResult.toString());

		// confirm result is related to query
		mGeneric.confirmElementExistence(SearchResults.Mobile_CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString);

		// click result
		mGeneric.clickElement(SearchResults.Locator_firstResult.toString());

		// confirm navigation
		mGeneric.confirmElementExistence(SearchResults.Mobile_Locator_Text_aholdSearchConfirmation.toString(), 90);

		// take screenshot
		mGeneric.takeScreenShot("Final confirmation.");

	}


}
