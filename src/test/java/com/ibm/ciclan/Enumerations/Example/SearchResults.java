package com.ibm.ciclan.Enumerations.Example;

public enum SearchResults {

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
	;

	private String str;

	SearchResults(String value) {
		str = value;
	}

	@Override
	public String toString() {
		return str;
	}


}
