package com.ibm.ciclan.Tests.Example.Mobile;


import org.openqa.selenium.WebElement;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ibm.ciclan.Base.TestBase;
import com.ibm.ciclan.Base.CustomAnnotations.RetryOnFailCount;
import com.ibm.ciclan.Enumerations.Example.PhoneApp;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Parallelism")
@Feature("Android Native")
public class AndroidNativeFeature extends TestBase {

	WebElement me = null;

	/**
	 * Perform a google search. Confirm and click first result. Confirm navigation.
	 * @param testParam optional TestNG value from suite
	 * @throws InterruptedException
	 */
	@Test(testName="Android Native App Test", description="Run Android Native app in parallel.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Run Android Native App.")
	@Story("Run Android Native App.")
	@Parameters({"testParam"})
	@RetryOnFailCount(2)
	public void AndroidNativeTest(@Optional("n/a") String testParam) {

		// confirm dialer button on phone default screen
		me = mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_KeyPad.toString());

		// click dialer button
		mGeneric.clickWithConfirmation(me, PhoneApp.Mobile_Button_1.toString());

		mGeneric.takeScreenShot("Dialer pad displayed.");

		// confirm dialpad buttons
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_1.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_2.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_3.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_4.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_5.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_6.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_7.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_8.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_9.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_0.toString());

		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_Star.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_Pound.toString());
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_Call.toString());

	}


}
