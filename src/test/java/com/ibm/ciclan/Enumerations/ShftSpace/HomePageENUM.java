package com.ibm.ciclan.Enumerations.ShftSpace;

public enum HomePageENUM {

	// DESKTOP
	Text_URL("ShftSpaceHome.text.url"),
	Text_PageTitle(),
	Locator_Button_Register("ShftSpaceHome.Locator.Button.Register"),

	// MOBILE

	private String str;
    HomePageENUM(String value) {str = value;}
    @Override
    public String toString() {return str;}

}
