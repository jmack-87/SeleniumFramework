package com.jmack.Enumerations;

public enum ChangeLager_LoginPage {

    // GENERIC
    Text_pageTitle("ChangeLagerLogin.text.pageTitle"),
    Locator_Tag_head("ChangeLagerLogin.Locator.Tag.head"),

    // DESKTOP
    Locator_Button_SignUp("ChangeLagerLogin.Locator.Button.SignUp"),
    Locator_Button_Login("ChangeLagerLogin.Locator.Button.Login"),
    Locator_Button_Register("ChangeLagerLogin.Locator.Button.Register");

    // MOBILE

    private String str;
    ChangeLager_LoginPage(String value) {str = value;}
    @Override
    public String toString() {return str;}
}
