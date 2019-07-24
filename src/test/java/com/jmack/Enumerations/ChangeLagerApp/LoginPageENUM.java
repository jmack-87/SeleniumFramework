package com.jmack.Enumerations.ChangeLagerApp;

public enum LoginPageENUM {

    //DESKTOP
    Text_URL("ChangeLagerLogin.text.changeLagerLoginURL"),
    Text_PageTitle("ChangeLagerLogin.text.pageTitle"),
    Locator_Tag_Head("ChangeLagerLogin.Locator.Tag.head"),
    Locator_TextField_Username("ChangeLagerLogin.Locator.textField.Username"),
    Locator_TextField_Password("ChangeLagerLogin.Locator.textField.Password"),
    Locator_Checkbox_RememberMe("ChangeLagerLogin.Locator.checkBox.RememberMe"),
    Locator_Link_ForgotPassword("ChangeLagerLogin.Locator.Link.ForgotPassword"),
    Locator_Button_SignIn("ChangeLagerLogin.Locator.Button.SignIn"),
    Locator_Link_Github("ChangeLagerLogin.Locator.Link.Github"),
    Locator_Link_Bitbucket("ChangeLagerLogin.Locator.Link.Bitbucket"),
    Locator_Link_Gitlab("ChangeLagerLogin.Locator.Link.Gitlab"),
    Locator_Link_NoAccountRegister("ChangeLagerLogin.Locator.Link.NoAccountRegister");

    //MOBILE
    private String str;
    LoginPageENUM(String value) {str = value;}
    @Override
    public String toString() {return str;}




}
