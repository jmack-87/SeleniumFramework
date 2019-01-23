package com.jmack.Tests.Example.Desktop;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jmack.Base.TestBase;
import com.jmack.Base.CustomAnnotations.RetryOnFailCount;
import com.jmack.Enumerations.Generic;
import com.jmack.Enumerations.Example.SearchPage;
import com.jmack.Enumerations.Example.SearchResults;

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

		generic.getUrl(Generic.Text_googleURL.toString());

        generic.confirmElementExistence(SearchPage.Locator_Tag_head.toString());
		generic.confirmTitle(SearchPage.Text_pageTitle.toString());

		generic.sendText(SearchPage.Locator_TextField_searchInput.toString(), runtimeData.searchString);
		generic.clickElement(SearchPage.Locator_Button_searchSubmit.toString());

		generic.confirmElementExistence(SearchResults.Locator_firstResult.toString());
		generic.confirmElementExistence(SearchResults.CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString);
		generic.clickElement(SearchResults.Locator_firstResult.toString());

		generic.waitForPageLoaded(30);

		generic.confirmElementExistence(SearchResults.Locator_Text_ibmSearchConfirmation.toString());

		homePage.stuff("something passed");
		
	}
	
}
