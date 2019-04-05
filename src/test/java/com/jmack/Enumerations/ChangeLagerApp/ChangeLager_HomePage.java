package com.jmack.Enumerations.ChangeLagerApp;

public enum ChangeLager_HomePage {

       // DESKTOP
    text_URL("ChangeLagerHome.text.changeLagerHomeURL"),
    locator_pageTitle("ChangeLagerHome.text.pageTitle"),
    metaBitLLCHome_pageTitle("MetaBitLLCHome.text.pageTitle"),
    Tag_head_MetaBitLLCHome("MetaBitLLCHome.Locator.Tag.head"),
    Tag_head("ChangeLagerHome.Locator.Tag.head"),
    button_SignUp("ChangeLagerHome.Locator.Button.SignUp"),
    button_Login("ChangeLagerHome.Locator.Button.Login"),
    button_Register("ChangeLagerHome.Locator.Button.Register"),
    button_AcceptAllCookies("ChangeLagerHome.Locator.Button.AcceptAllCookies"),
    button_DownArrow("ChangeLagerHome.Locator.Button.DownArrow"),
    button_Single("ChangeLagerHome.Locator.Button.SingleRegister"),
    button_MoreThanOne("ChangeLagerHome.Locator.Button.MoreThanOneRegister"),
    link_MetaBit ("ChangeLagerHome.Locator.Link.MetaBit");

    // MOBILE

    private String str;
    ChangeLager_HomePage(String value) {str = value;}
    @Override
    public String toString() {return str;}
}