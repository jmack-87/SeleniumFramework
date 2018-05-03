package com.jmack.Tests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jmack.Base.TestBase;
import com.jmack.Enumerations.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

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
	public void AndroidChromeTest(@Optional String testParam) throws InterruptedException {
		
		mGeneric.getUrl(Generic.Text_googleURL.toString());
		
		mGeneric.confirmElementExistence(SearchPage.Locator_Tag_head.toString());
		mGeneric.confirmTitle(SearchPage.Text_pageTitle.toString());
		
		mGeneric.sendText(SearchPage.Mobile_Locator_TextField_searchInput.toString(), runtimeData.searchString);
		mGeneric.clickElement(SearchPage.Mobile_Locator_Button_searchSubmit.toString());
		
		mGeneric.confirmElementExistence(SearchResults.Mobile_Locator_firstResult.toString());
		mGeneric.confirmElementExistence(SearchResults.Mobile_CompoundLocator_firstResult.toString(), runtimeData.searchConfirmationString);
		mGeneric.clickElement(SearchResults.Mobile_Locator_firstResult.toString());

		mGeneric.waitForPageLoaded(30);
		
		mGeneric.confirmElementExistence(SearchResults.Locator_Text_ibmSearchConfirmation.toString());
		
		//mGeneric.failTest();
	}

}
