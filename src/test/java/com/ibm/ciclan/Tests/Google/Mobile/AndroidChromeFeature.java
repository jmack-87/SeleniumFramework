package com.ibm.ciclan.Tests.Google.Mobile;


import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ibm.ciclan.Base.TestBase;
import com.ibm.ciclan.Base.CustomAnnotations.RetryOnFailCount;
import com.ibm.ciclan.Enumerations.Generic;
import com.ibm.ciclan.Enumerations.Google.SearchPage;
import com.ibm.ciclan.Enumerations.Google.SearchResults;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


/**
 *
 * @author JerimiahMACK
 *
 */
@Epic("Parallelism")
@Feature("Android Chrome")
public class AndroidChromeFeature extends TestBase {


	/**
	 * Perform a google search. Confirm and click first result. Confirm navigation.
	 * @param testParam optional TestNG value from suite
	 * @throws InterruptedException
	 */
	@Test(testName="Android Chrome Test", description="Run Android Chrome browser in parallel.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Run Android Chrome browser.")
	@Story("Run Android Chrome.")
	@Parameters({"testParam"})
	@RetryOnFailCount(0)
	public void AndroidChromeTest(@Optional String testParam) {

		// launch google.com
		mGeneric.getUrl(Generic.Text_googleURL.toString());

		// confirm google.com is launched
		mGeneric.confirmElementExistence(SearchPage.Locator_Tag_head.toString());
		mGeneric.confirmTitle(SearchPage.Text_pageTitle.toString());

		// set search field
		mGeneric.sendText(SearchPage.Mobile_Locator_TextField_searchInput.toString(), runtimeData.searchString);

		// submit search
		//mGeneric.clickElement(SearchPage.Mobile_Locator_Button_searchSubmit.toString());
		mGeneric.androidPressKey();

		// confirm at least one result returns
		mGeneric.confirmElementExistence(SearchResults.Locator_firstResult.toString());

		// take screenshot
		mGeneric.takeScreenShot("Search results.");

		// confirm result is related to query
		mGeneric.confirmElementExistence(SearchResults.Mobile_CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString);

		// scroll to element
		mGeneric.scrollToElement(SearchResults.Mobile_CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString, 4);

		// click result
		mGeneric.clickElement(SearchResults.Mobile_CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString);

		// confirm navigation
		mGeneric.confirmElementExistence(SearchResults.Mobile_Locator_Text_googleSearchConfirmation.toString());

		// take screenshot
		mGeneric.takeScreenShot("Final confirmation.");

	}


}
