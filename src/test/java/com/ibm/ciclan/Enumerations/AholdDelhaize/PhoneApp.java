package com.ibm.ciclan.Enumerations.AholdDelhaize;

public enum PhoneApp {

	Mobile_Button_KeyPad("FavoritesScreen.Mobile.Locator.Button.DialPad"),
	Mobile_Button_0("Dialer.Mobile.Locator.Button.0"),
	Mobile_Button_1("Dialer.Mobile.Locator.Button.1"),
	Mobile_Button_2("Dialer.Mobile.Locator.Button.2"),
	Mobile_Button_3("Dialer.Mobile.Locator.Button.3"),
	Mobile_Button_4("Dialer.Mobile.Locator.Button.4"),
	Mobile_Button_5("Dialer.Mobile.Locator.Button.5"),
	Mobile_Button_6("Dialer.Mobile.Locator.Button.6"),
	Mobile_Button_7("Dialer.Mobile.Locator.Button.7"),
	Mobile_Button_8("Dialer.Mobile.Locator.Button.8"),
	Mobile_Button_9("Dialer.Mobile.Locator.Button.9"),
	Mobile_Button_Star("Dialer.Mobile.Locator.Button.Star"),
	Mobile_Button_Pound("Dialer.Mobile.Locator.Button.Pound"),
	Mobile_Button_Call("Dialer.Mobile.Locator.Button.Call"),
	;

	private String str;

	PhoneApp(String value) {
		str = value;
	}

	@Override
	public String toString() {
		return str;
	}


}
