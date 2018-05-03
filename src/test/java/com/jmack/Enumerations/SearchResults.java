package com.jmack.Enumerations;

	public enum SearchResults {
		
		// DESKTOP
		Locator_firstResult("SearchResults.Locator.firstResult"),
		CompoundLocator_firstResult("SearchResults.CompoundLocator.firstResult"),
		Locator_Text_ibmSearchConfirmation("SearchResults.Locator.Text.ibmSearchConfirmation"),
		Locator_Text_santanderSearchConfirmation("SearchResults.Locator.Text.santanderSearchConfirmation"),
		
		// MOBILE
		Mobile_Locator_firstResult("SearchResults.Mobile.Locator.firstResult"),
		Mobile_CompoundLocator_firstResult("SearchResults.Mobile.CompoundLocator.firstResult");
		
		private String str;
		SearchResults(String value) {str = value;}
		@Override
		public String toString() {return str;}
		
	}
