package com.ibm.ciclan.Tests.Honda.Mobile;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jmack.Base.TestBase;
import com.jmack.Base.CustomAnnotations.RetryOnFailCount;
import com.jmack.Enumerations.Example.PhoneApp;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Parallelism")
@Feature("Ios Native")
public class IosNativeFeature extends TestBase {
	
	/**
	 * Perform a google search. Confirm and click first result. Confirm navigation.
	 * @param testParam optional TestNG value from suite
	 * @throws InterruptedException
	 */
	@Test(testName="Ios Native App Test", description="Run Ios Native app in parallel.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Run Ios Native App.")
	@Story("Run Ios Native App.")
	@Parameters({"testParam"})
	@RetryOnFailCount(0)
	public void IosNativeTest(@Optional String testParam) {
		
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_KeyPad.toString());
		mGeneric.clickElement(PhoneApp.Mobile_Button_KeyPad.toString());
		
		mGeneric.confirmElementExistence(PhoneApp.Mobile_Button_1.toString());
		
		mGeneric.takeScreenShot("Dialer pad displayed.");
		
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

		//mGeneric.failTest();
	}

}
