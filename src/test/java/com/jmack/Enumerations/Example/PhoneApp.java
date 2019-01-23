package com.jmack.Enumerations.Example;

public enum PhoneApp {
	
	Mobile_Button_KeyPad("FavoritesScreen.Mobile.Locator.Button.DialPad"),
	Mobile_Button_0("Dailer.Mobile.Locator.Button.0"),
	Mobile_Button_1("Dailer.Mobile.Locator.Button.1"),
	Mobile_Button_2("Dailer.Mobile.Locator.Button.2"),
	Mobile_Button_3("Dailer.Mobile.Locator.Button.3"),
	Mobile_Button_4("Dailer.Mobile.Locator.Button.4"),
	Mobile_Button_5("Dailer.Mobile.Locator.Button.5"),
	Mobile_Button_6("Dailer.Mobile.Locator.Button.6"),
	Mobile_Button_7("Dailer.Mobile.Locator.Button.7"),
	Mobile_Button_8("Dailer.Mobile.Locator.Button.8"),
	Mobile_Button_9("Dailer.Mobile.Locator.Button.9"),
	Mobile_Button_Star("Dailer.Mobile.Locator.Button.Star"),
	Mobile_Button_Pound("Dailer.Mobile.Locator.Button.Pound"),
	Mobile_Button_Call("Dailer.Mobile.Locator.Button.Call");

	private String str;
	PhoneApp(String value) {str = value;}
	@Override
	public String toString() {return str;}
	
}
