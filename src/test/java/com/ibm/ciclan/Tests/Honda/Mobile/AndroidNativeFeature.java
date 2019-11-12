package com.ibm.ciclan.Tests.Honda.Mobile;


import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ibm.ciclan.Base.TestBase;
import com.ibm.ciclan.Base.CustomAnnotations.RetryOnFailCount;
import com.ibm.ciclan.Enumerations.Aep.Aep;

import io.appium.java_client.MobileElement;

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
@Feature("Android Native")
public class AndroidNativeFeature extends TestBase {

	MobileElement me = null;

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
	@RetryOnFailCount(0)
	public void AndroidNativeTest(@Optional("n/a") String testParam) {

		// screenshot application launch
		mGeneric.takeScreenShot("Target application launched.");

		// set email field
		mGeneric.sendText(Aep.Mobile_Locator_TextField_User.toString(), runtimeData.userEmail);

		// set password field
		mGeneric.sendText(Aep.Mobile_Locator_TextField_Password.toString(), runtimeData.password);

		// click login button
		mGeneric.clickElement(Aep.Mobile_Locator_Button_Login.toString());

		// confirm invalid credentials banner displays
		mGeneric.confirmElementExistence(Aep.Mobile_Locator_TextContainer_LoginError.toString());

		// take screenshot
		mGeneric.takeScreenShot("Final confirmation.");

	}


}
