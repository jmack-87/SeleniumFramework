package com.jmack.Enumerations;

	public enum SearchResults {
		
		CompoundLocator_firstResult("SearchResults.CompoundLocator.firstResult"),
		Locator_Text_ibmSearchConfirmation("SearchResults.Locator.Text.ibmSearchConfirmation"),
		Locator_Text_santanderSearchConfirmation("SearchResults.Locator.Text.santanderSearchConfirmation"),
		Locator_firstResult("SearchResults.Locator.firstResult");

		private String str;
		SearchResults(String value) {str = value;}
		@Override
		public String toString() {return str;}
		
	}
