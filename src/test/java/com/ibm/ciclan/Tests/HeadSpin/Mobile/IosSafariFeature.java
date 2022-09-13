package com.ibm.ciclan.Tests.HeadSpin.Mobile;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ibm.ciclan.Base.TestBase;
import com.ibm.ciclan.Base.CustomAnnotations.RetryOnFailCount;
import com.ibm.ciclan.Enumerations.HeadSpin.HeadSpin;

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

		// launch url
		mGeneric.getUrl(HeadSpin.Generic_Text_SauceDemoUrl.toString());

		// try to clear cache
		mGeneric.returnDriver().manage().deleteAllCookies();

		// confirm page title
		//mGeneric.confirmTitle(HeadSpin.Login_Text_pageTitle.toString());

		// enter username
		mGeneric.sendText(HeadSpin.Login_Locator_TextField_UserName.toString(), runtimeData.username);

		// enter password
		mGeneric.sendText(HeadSpin.Login_Locator_TextField_Password.toString(), runtimeData.password);

		// click submit
		mGeneric.clickElement(HeadSpin.Login_Locator_Button_Login.toString());

		// confirm page transition
		mGeneric.confirmElementExistence(HeadSpin.Inventory_Locator_DropDown_Sort.toString());

		// get array of products
		List<WebElement> weArray = mGeneric.confirmElementsExistence(HeadSpin.Inventory_MultiLocator_Container_InventoryItems.toString());

		// get first item
		WebElement firstItem = weArray.get(0);

		// get description
		WebElement descriptionContainer = mGeneric.confirmElementExistence(firstItem, HeadSpin.Inventory_RelativeLocator_TextContainer_Description.toString());
		String description = descriptionContainer.getText();

		// get price
		WebElement priceContainer = mGeneric.confirmElementExistence(firstItem, HeadSpin.Inventory_RelativeLocator_TextContainer_Price.toString());
		String price = (priceContainer.getText()).replace("$", "");

		// get add to cart button
		WebElement addToCart = mGeneric.confirmElementExistence(firstItem, HeadSpin.Inventory_RelativeLocator_Button_AddToCart.toString());

		// add to cart
		mGeneric.clickElement(addToCart);

		// confirm cart is now +1 item
		mGeneric.confirmStaticTextValue(HeadSpin.Header_Locator_TextContainer_CartItemCounter.toString(), HeadSpin.Header_Text_CartCount.toString());

		// go to cart
		mGeneric.clickElement(HeadSpin.Header_Locator_Link_Cart.toString());

		// confirm navigation
		mGeneric.confirmElementExistence(HeadSpin.Cart_Locator_Button_Checkout.toString());

		// get cart line items
		weArray = mGeneric.confirmElementsExistence(HeadSpin.Cart_MultiLocator_Container_LineItems.toString());

		// get first line item
		firstItem = weArray.get(0);

		// confirm line item quantity
		mGeneric.confirmStaticTextValue(HeadSpin.Cart_RelativeLocator_TextField_Quantity.toString(), HeadSpin.Cart_Text_ItemCount.toString());

		// confirm line item description
		mGeneric.confirmDynamicTextValue(HeadSpin.Cart_RelativeLocator_TextContainer_Description.toString(), description);

		// confirm line item price
		mGeneric.confirmDynamicTextValue(HeadSpin.Cart_RelativeLocator_TextContainer_Price.toString(), price);

	}


}
