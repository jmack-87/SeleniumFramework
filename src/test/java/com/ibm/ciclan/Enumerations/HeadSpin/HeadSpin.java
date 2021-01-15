package com.ibm.ciclan.Enumerations.HeadSpin;

public enum HeadSpin {

	// GENERIC
	Generic_Text_SauceDemoUrl("Generic.Text.sauceDemoUrl"),

	// DESKTOP
		// Module: Login
		Login_Text_pageTitle("Login.Text.PageTitle"),
		Login_Locator_TextField_UserName("Login.Locator.TextField.UserName"),
		Login_Locator_TextField_Password("Login.Locator.TextField.Password"),
		Login_Locator_Button_Login("Login.Locator.Button.Login"),
		Login_Locator_Button_DismissError("Login.Locator.Button.DismissError"),
		Login_Locator_TextContainer_ErrorText("Login.Locator.TextContainer.ErrorText"),
		Login_Text_InvalidCredentialsError("Login.Text.InvalidCredentialsError"),
		Login_Text_LockedOutUser("Login.Text.LockedOutError"),
		Login_Text_UserNameRequired("Login.Text.UserNameRequired"),
		Login_Text_PasswordRequired("Login.Text.PasswordRequired"),

		// Module: Header
		Header_Locator_Button_Menu("Header.Locator.Button.Menu"),
		Header_Locator_Link_Cart("Header.Locator.Link.Cart"),
		Header_Locator_TextContainer_CartItemCounter("Header.Locator.TextContainer.CartItemCounter"),
		Header_Text_CartCount("Header.Text.CartCount"),

		// Module: Inventory
		Inventory_Locator_TextContainer_ProductLabel("Inventory.Locator.TextContainer.ProductLabel"),
		Inventory_Text_ProductLabel("Inventory.Text.ProductLabel"),
		Inventory_Locator_DropDown_Sort("Inventory.Locator.DropDown.Sort"),
		Inventory_MultiLocator_Container_InventoryItems("Inventory.MultiLocator.Container.InventoryItems"),
		Inventory_RelativeLocator_Link_InventoryItemLink("Inventory.RelativeLocator.Link.InventoryItemLink"),
		Inventory_RelativeLocator_TextContainer_Description("Inventory.RelativeLocator.TextContainer.Description"),
		Inventory_RelativeLocator_TextContainer_Price("Inventory.RelativeLocator.TextContainer.Price"),
		Inventory_RelativeLocator_Button_AddToCart("Inventory.RelativeLocator.Button.AddToCart"),

		// Module: Cart
		Cart_Locator_TextContainer_SubHeader("Cart.Locator.TextContainer.SubHeader"),
		Cart_Text_SubHeader("Cart.Text.SubHeader"),
		Cart_Locator_Button_ContinueShopping("Cart.Locator.Button.ContinueShopping"),
		Cart_Locator_Button_Checkout("Cart.Locator.Button.Checkout"),
		Cart_MultiLocator_Container_LineItems("Cart.MultiLocator.Container.LineItems"),
		Cart_RelativeLocator_TextField_Quantity("Cart.RelativeLocator.TextField.Quantity"),
		Cart_RelativeLocator_Link_InventoryItemLink("Cart.RelativeLocator.Link.InventoryItemLink"),
		Cart_RelativeLocator_TextContainer_Description("Cart.RelativeLocator.TextContainer.Description"),
		Cart_RelativeLocator_TextContainer_Price("Cart.RelativeLocator.TextContainer.Price"),
		Cart_RelativeLocator_Button_Remove("Cart.RelativeLocator.Button.Remove"),
		Cart_Text_ItemCount("Cart.Text.ItemCount"),

		// Module: Checkout, Your Info
		YourInfo_Locator_TextContainer_SubHeader("YourInfo.Locator.TextContainer.SubHeader"),
		YourInfo_Text_SubHeader("YourInfo.Text.SubHeader"),
		YourInfo_Locator_TextField_FirstName(""),
		YourInfo_Locator_TextField_LastName(""),
		YourInfo_Locator_TextField_ZipCode(""),
		YourInfo_Locator_Button_Cancel(""),
		YourInfo_Locator_Button_Continue(""),
		YourInfo_Locator_Button_DismissError(""),
		YourInfo_Locator_TextContainer_ErrorText(""),
		YourInfo_Text_FirstNameRequired(""),
		YourInfo_Text_LastNameRequired(""),
		YourInfo_Text_PostalCodeRequired(""),

		// Module: Checkout, Overview
		Overview_Locator_TextContainer_SubHeader("Overview.Locator.TextContainer.SubHeader"),
		Overview_Text_SubHeader("Overview.Text.SubHeader"),
		Overview_MultiLocator_Container_LineItems(""),
		Overview_RealtiveLocator_TextContainer_Quantity(""),
		Overview_RealtiveLocator_Link_InventoryItemLink(""),
		Overview_RealtiveLocator_TextContainer_Description(""),
		Overview_RealtiveLocator_TextContainer_Price(""),
		Overview_Locator_TextContainer_ItemTotal(""),
		Overview_Locator_TextContainer_Tax(""),
		Overview_Locator_TextContainer_Total(""),
		Overview_Locator_Button_Cancel(""),
		Overview_Locator_Button_Finish(""),

		// Module: Finish
		Finish_Locator_TextContainer_SubHeader(""),
		Finish_Text_SubHeader(""),
		Finish_Locator_TextContainer_ThankYou(""),
		Finish_Text_ThankYou(""),

	// MOBILE
		// Module: ???

	;

	private String str;

	HeadSpin(String value) {
		str = value;
	}

	@Override
	public String toString() {
		return str;
	}


}
