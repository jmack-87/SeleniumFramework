package com.ibm.ciclan.Enumerations.AholdDelhaize;

public enum AholdDelhaize {

	Mobile_Locator_TextField_Email("AholdDelhaize.Mobile.Locator.TextField.Email"),
	Mobile_Locator_TextField_Password("AholdDelhaize.Mobile.Locator.TextField.Password"),
	Mobile_Locator_Button_Login("AholdDelhaize.Mobile.Locator.Button.Login"),
	Mobile_Locator_TextContainer_LoginError("AholdDelhaize.Mobile.Locator.TextContainer.LoginError"),
	;

	private String str;

	AholdDelhaize(String value) {
		str = value;
	}

	@Override
	public String toString() {
		return str;
	}


}
