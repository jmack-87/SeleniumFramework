package com.jmack.Tests;

import org.testng.annotations.Test;

import com.jmack.Base.TestBase;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Parallelism")
@Feature("Edge")
public class EdgeFeature extends TestBase {

	
	@Test(testName="Edge Test",description="Run Edge browser in parallel.")
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description: Run Edge browser in parallel.")
	@Story("Run Chrome, Firefox, Edge, InternetExplorer in parallel.")
	public void EdgeTest() throws InterruptedException {
			
		generic.getUrl("googleURL");
		
		generic.confirmElementExistence("SearchPage.Locator.Tag.head");
		generic.confirmTitle("SearchPage.Text.pageTitle");
		
		generic.sendText("SearchPage.Locator.TextField.searchInput", runtimeData.searchString);
		generic.clickElement("SearchPage.Locator.Button.searchSubmit");
		
		generic.confirmElementExistence("SearchResults.Locator.FirstResult");
		generic.confirmElementExistence("SearchResults.CompoundLocator.FirstResult", runtimeData.searchConfirmationString);
		generic.clickElement("SearchResults.Locator.FirstResult");
		generic.waitForPageLoaded(30);
		
		generic.confirmElementExistence("SearchResults.Locator.Text.ibmSearchConfirmation");
		
		homePage.stuff("something passed");
		
	}
	
}
