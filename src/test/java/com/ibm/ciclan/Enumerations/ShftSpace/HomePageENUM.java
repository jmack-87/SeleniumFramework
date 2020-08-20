package com.ibm.ciclan.Enumerations.ShftSpace;

public enum HomePageENUM {

	// DESKTOP
	Text_URL("ShftSpaceHome.text.url"),
	Text_PageTitle("ShftSpaceHome.text.pageTitle"),
	Locator_Button_Register("ShftSpaceHome.Locator.Button.Register"),
	Locator_Button_Login("ShftSpaceHome.Locator.Button.Login"),
	Locator_Button_FreeForUpTo5MembersRegister("ShftSpaceHome.Locator.Button.FreeForUpTo5MembersRegister"),
	Locator_Button_RegisterSmallTeam("ShftSpaceHome.Locator.Button.RegisterSmallTeam"),
	Locator_Button_RegisterStartup("ShftSpaceHome.Locator.Button.RegisterStartup"),
	Locator_Button_RegisterAgency("ShftSpaceHome.Locator.Button.RegisterAgency"),
	Locator_Button_ContactUsEnterprise("ShftSpaceHome.Locator.Button.ContactUsEnterprise");

	// MOBILE

	private String str;
    HomePageENUM(String value) {str = value;}
    @Override
    public String toString() {return str;}

}
