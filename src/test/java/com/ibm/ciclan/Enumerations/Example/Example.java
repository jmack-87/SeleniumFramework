package com.ibm.ciclan.Enumerations.Example;

public enum Example {

	Mobile_Locator_TextField_User("Example.Mobile.Locator.TextField.User"),
	Mobile_Locator_TextField_Password("Example.Mobile.Locator.TextField.Password"),
	Mobile_Locator_Button_Login("Example.Mobile.Locator.Button.Login"),
	Mobile_Locator_TextContainer_LoginError("Example.Mobile.Locator.TextContainer.LoginError"),
	;


	private String str;

	Example(String value) {
		str = value;
	}

	@Override
	public String toString() {
		return str;
	}


}
