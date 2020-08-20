package com.ibm.ciclan.Enumerations.ShftSpace;

public enum RegistrationPageENUM {

	//DESKTOP

	Text_URL("ShftSpaceRegistration.text.url"),
	Text_PageTitle("ShftSpaceRegistration.text.pageTitle"),
	Locator_Button_LoginPage("ShftSpaceRegistration.Locator.Button.LoginPage"),
	Locator_Button_Register("ShftSpaceRegistration.Locator.Button.Register"),
	Locator_TextField_TeamName("ShftSpaceRegistration.Locator.textField.teamName"),
	Locator_TextField_Name("ShftSpaceRegistration.Locator.textField.name"),
	Locator_TextField_EmailAddress("ShftSpaceRegistration.Locator.textField.emailAddress"),
	Locator_TextField_Password("ShftSpaceRegistration.Locator.textField.password"),
	Locator_TextField_ConfirmPassword("ShftSpaceRegistration.Locator.textField.confirmPassword"),
	Locator_CheckBox_TermsOfServiceNotChecked("ShftSpaceRegistration.Locator.checkBox.TermsOfServiceNotChecked"),
	Locator_CheckBox_TermsOfServiceChecked("ShftSpaceRegistration.Locator.checkBox.TermsOfServiceChecked");

	// MOBILE

	private String str;
	RegistrationPageENUM(String value) {str = value;}
	@Override
	public String toString() {return str;}

}
