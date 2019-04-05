package com.jmack.Enumerations.ChangeLagerApp;

public enum ChangeLager_LoginPage {

    //DESKTOP
    text_URL("ChangeLagerLogin.text.changeLagerLoginURL"),
    text_pageTitle("ChangeLagerLogin.text.pageTitle"),
    Tag_head("ChangeLagerLogin.Locator.Tag.head"),
    textField_Username("ChangeLagerLogin.Locator.textField.Username"),
    textField_Password("ChangeLagerLogin.Locator.textField.Password"),
    checkBox_RememberMe("ChangeLagerLogin.Locator.checkBox.RememberMe"),
    link_ForgotPassword("ChangeLagerLogin.Locator.Link.ForgotPassword"),
    button_SignIn("ChangeLagerLogin.Locator.Button.SignIn"),
    link_Github("ChangeLagerLogin.Locator.Link.Github"),
    link_Bitbuck("ChangeLagerLogin.Locator.Link.Bitbucket"),
    link_Gitlab("ChangeLagerLogin.Locator.Link.Gitlab"),
    link_NoAccountRegister("ChangeLagerLogin.Locator.Link.NoAccountRegister");

    //MOBILE
    private String str;
    ChangeLager_LoginPage(String value) {str = value;}
    @Override
    public String toString() {return str;}




}
