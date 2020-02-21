package com.ibm.ciclan.Enumerations.Google;

public enum Google {

	// GENERIC
	Text_pageTitle("SearchPage.Text.pageTitle"),
	Locator_Tag_head("SearchPage.Locator.Tag.head"),

	// DESKTOP
	Locator_TextField_searchInput("SearchPage.Locator.TextField.searchInput"),
	Locator_Button_searchSubmit("SearchPage.Locator.Button.searchSubmit"),

	// MOBILE
	Mobile_Locator_TextField_searchInput("SearchPage.Mobile.Locator.TextField.searchInput"),
	Mobile_Locator_Button_searchSubmit("SearchPage.Mobile.Locator.Button.searchSubmit"),

	// DESKTOP
	Locator_firstResult("SearchResults.Locator.firstResult"),
	Locator_secondResult("SearchResults.Locator.secondResult"),
	CompoundLocator_firstResult("SearchResults.CompoundLocator.firstResult"),
	CompoundLocator_secondResult("SearchResults.CompoundLocator.secondResult"),
	Locator_Text_ibmSearchConfirmation("SearchResults.Locator.Text.ibmSearchConfirmation"),
	Locator_Text_santanderSearchConfirmation("SearchResults.Locator.Text.santanderSearchConfirmation"),

	// MOBILE
	Mobile_Locator_firstResult("SearchResults.Mobile.Locator.firstResult"),
	Mobile_CompoundLocator_firstResult("SearchResults.Mobile.CompoundLocator.firstResult"),
	Mobile_Locator_Text_aholdSearchConfirmation("SearchResults.Mobile.Locator.Text.aholdSearchConfirmation"),
	Mobile_Locator_Text_hondaSearchConfirmation("SearchResults.Mobile.Locator.Text.hondaSearchConfirmation"),
	Mobile_Locator_Text_aepSearchConfirmation("SearchResults.Mobile.Locator.Text.aepSearchConfirmation"),
	Mobile_Locator_Link_careCentrixSearchConfirmation("SearchResults.Mobile.Locator.Link.careCentrixSearchConfirmation"),
	Mobile_Locator_Link_firstResult("SearchResults.Mobile.Locator.Link.firstResult"),

	Mobile_Locator_TextField_User("Google.Mobile.Locator.TextField.User"),
	Mobile_Locator_Button_UserName_Next("Google.Mobile.Locator.Button.UserName.Next"),
	Mobile_Locator_Text_UserName("Google.Mobile.Locator.Text.Email"),
	Mobile_Locator_TextField_Password("Google.Mobile.Locator.TextField.Password"),
	Mobile_Locator_Button_Password_Next("Google.Mobile.Locator.Button.Password.Next"),
	Mobile_Locator_TextContainer_LoginError("Google.Mobile.Locator.TextContainer.LoginError"),
	;


	private String str;
	Google(String value) {str = value;}

	@Override
	public String toString() {return str;}


}
