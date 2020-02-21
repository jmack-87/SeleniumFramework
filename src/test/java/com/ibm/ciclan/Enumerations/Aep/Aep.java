package com.ibm.ciclan.Enumerations.Aep;

public enum Aep {

	Mobile_Locator_TextField_User("Aep.Mobile.Locator.TextField.User"),
	Mobile_Locator_TextField_Password("Aep.Mobile.Locator.TextField.Password"),
	Mobile_Locator_Button_Login("Aep.Mobile.Locator.Button.Login"),
	Mobile_Locator_TextContainer_LoginError("Aep.Mobile.Locator.TextContainer.LoginError"),
	;

	private String str;

	Aep(String value) {
		str = value;
	}

	@Override
	public String toString() {
		return str;
	}


}
