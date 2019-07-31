package com.jmack.Enumerations.ChangeLagerApp;

public enum ResetPasswordPageENUM {

	//DESKTOP
	Text_URL("ChangeLagerResetPassword.text.resetPasswordURL"),
	Text_PageTitle("ChangeLagerResetPassword.text.pageTitle"),
	Locator_Tag_Head("ChangeLagerResetPassword.Locator.Tag.head"),
	Locator_TextField_Email("ChangeLagerResetPassword.Locator.textField.Email"),
	Locator_Button_SendPasswordResetLink("ChangeLagerResetPassword.Locator.Button.SendPasswordResetLink"),
	Locator_Link_Login("ChangeLagerResetPassword.Locator.Link.Login"),
	Text_BlankEmailFieldErrorMessageVisible("ChangeLagerResetPassword.Locator.text.BlankEmailFieldErrorMessageVisible");

	//MOBILE
    private String str;
    ResetPasswordPageENUM(String value) {str = value;}
    @Override
    public String toString() {return str;}
}
