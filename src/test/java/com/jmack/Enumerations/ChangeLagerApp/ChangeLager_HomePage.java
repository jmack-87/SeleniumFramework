package com.jmack.Enumerations.ChangeLagerApp;

public enum ChangeLager_HomePage {

       // DESKTOP
    Text_changeLagerHome_URL("ChangeLagerHome.text.changeLagerHomeURL"),
    Text_changeLagerHome_pageTitle("ChangeLagerHome.text.pageTitle"),
    Text_MetaBitLLCHome_pageTitle("MetaBitLLCHome.text.pageTitle"),
    Locator_Tag_head_MetaBitLLCHome("MetaBitLLCHome.Locator.Tag.head"),
    Locator_Tag_head_changeLagerHome("ChangeLagerHome.Locator.Tag.head"),
    Locator_Button_SignUp("ChangeLagerHome.Locator.Button.SignUp"),
    Locator_Button_Login("ChangeLagerHome.Locator.Button.Login"),
    Locator_Button_Register("ChangeLagerHome.Locator.Button.Register"),
    Locator_Button_AcceptAllCookies("ChangeLagerHome.Locator.Button.AcceptAllCookies"),
    Locator_Button_DownArrow("ChangeLagerHome.Locator.Button.DownArrow"),
    Locator_Button_Single("ChangeLagerHome.Locator.Button.SingleRegister"),
    Locator_Button_MoreThanOne("ChangeLagerHome.Locator.Button.MoreThanOneRegister"),
    Locator_Link_MetaBit("ChangeLagerHome.Locator.Link.MetaBit");

    // MOBILE

    private String str;
    ChangeLager_HomePage(String value) {str = value;}
    @Override
    public String toString() {return str;}
}