package com.ibm.ciclan.Enumerations.ShftSpace;

public enum LoginPageENUM {

	//DESKTOP

	Text_URL("ShftSpaceLogin.text.url"),
	Text_PageTitle("ShftSpaceLogin.text.pageTitle"),
	Locator_Button_LoginPage("ShftSpaceLogin.Locator.Button.LoginPage"),
	Locator_Button_Register("ShftSpaceLogin.Locator.Button.Register"),
	Locator_TextField_EmailInput("ShftSpaceLogin.Locator.textField.emailInput"),
	Locator_TextField_PasswordInput("ShftSpaceLogin.Locator.textField.passwordInput"),
	Locator_CheckBox_RememberMeNotChecked("ShftSpaceLogin.Locator.checkBox.RememberMeNotChecked"),
	Locator_CheckBox_RememberMeChecked("ShftSpaceLogin.Locator.checkBox.RememberMeChecked"),
	Locator_Button_LoginSubmit("ShftSpaceLogin.Locator.Button.LoginSubmit"),
	Locator_Link_ForgotYourPassword("ShftSpaceLogin.Locator.Link.ForgotYourPassword");

	// MOBILE

	private String str;
	LoginPageENUM(String value) {str = value;}
	@Override
	public String toString() {return str;}

}
