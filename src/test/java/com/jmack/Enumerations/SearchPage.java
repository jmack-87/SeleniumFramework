package com.jmack.Enumerations;

	public enum SearchPage {
		
		Text_pageTitle("SearchPage.Text.pageTitle"),
		Locator_Tag_head("SearchPage.Locator.Tag.head"),
		Locator_TextField_searchInput("SearchPage.Locator.TextField.searchInput"),
		Locator_Button_searchSubmit("SearchPage.Locator.Button.searchSubmit");

		private String str;
		SearchPage(String value) {str = value;}
		@Override
		public String toString() {return str;}
		
	}
