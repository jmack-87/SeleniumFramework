package com.ibm.ciclan.Enumerations.Aep;

public enum SearchPage {

	// GENERIC
	Text_pageTitle("SearchPage.Text.pageTitle"), Locator_Tag_head("SearchPage.Locator.Tag.head"),

	// DESKTOP
	Locator_TextField_searchInput("SearchPage.Locator.TextField.searchInput"),
	Locator_Button_searchSubmit("SearchPage.Locator.Button.searchSubmit"),

	// MOBILE
	Mobile_Locator_TextField_searchInput("SearchPage.Mobile.Locator.TextField.searchInput"),
	Mobile_Locator_Button_searchSubmit("SearchPage.Mobile.Locator.Button.searchSubmit"),
	;

	private String str;

	SearchPage(String value) {
		str = value;
	}

	@Override
	public String toString() {
		return str;
	}


}
