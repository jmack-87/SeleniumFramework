package com.jmack.Tests.Example.Desktop;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jmack.Base.TestBase;
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
@Feature("Edge")
public class EdgeFeature extends TestBase {

	/**
	 * Perform a google search. Confirm and click first result. Confirm navigation.
	 * @param testParam optional TestNG value from suite
	 * @throws InterruptedException
	 */
	@Test(testName="Edge Test",description="Run Edge browser in parallel.")
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description: Run Edge browser in parallel.")
	@Story("Run Chrome, Firefox, Edge, InternetExplorer in parallel.")
	@Parameters({"testParam"})
	public void EdgeTest(@Optional String testParam) throws InterruptedException {

		generic.getUrl(Generic.Text_googleURL.toString());

		generic.confirmElementExistence(SearchPage.Locator_Tag_head.toString());
		generic.confirmTitle(SearchPage.Text_pageTitle.toString());

		generic.sendText(SearchPage.Locator_TextField_searchInput.toString(), runtimeData.searchString);
		generic.clickElement(SearchPage.Locator_Button_searchSubmit.toString());

		generic.confirmElementExistence(SearchResults.Locator_firstResult.toString());
		generic.confirmElementExistence(SearchResults.CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString);
		generic.clickElement(SearchResults.Locator_firstResult.toString());

		generic.waitForPageLoaded(30);

		generic.confirmElementExistence(SearchResults.Mobile_Locator_Text_aholdSearchConfirmation.toString());

//		homePagePO.stuff("something passed");
		generic.takeScreenShot("Final confirmation.");

	}

}