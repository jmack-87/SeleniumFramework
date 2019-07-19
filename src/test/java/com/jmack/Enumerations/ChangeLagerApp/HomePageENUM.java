package com.jmack.Enumerations.ChangeLagerApp;

public enum HomePageENUM {

    // DESKTOP
    Text_URL("ChangeLagerHome.text.changeLagerHomeURL"),
    Text_PageTitle("ChangeLagerHome.text.pageTitle"),
    MetaBitLLCHome_Text_PageTitle("MetaBitLLCHome.text.pageTitle"),
    MetaBitLLCHome_Locator_Tag_Head("MetaBitLLCHome.Locator.Tag.head"),
    Locator_Tag_Head("ChangeLagerHome.Locator.Tag.head"),
    Locator_Button_SignUp("ChangeLagerHome.Locator.Button.SignUp"),
    Locator_Button_Login("ChangeLagerHome.Locator.Button.Login"),
    Locator_Button_Register("ChangeLagerHome.Locator.Button.Register"),
    Locator_Button_DownArrow("ChangeLagerHome.Locator.Button.DownArrow"),
    Locator_Button_Single("ChangeLagerHome.Locator.Button.SingleRegister"),
    Locator_Button_MoreThanOne("ChangeLagerHome.Locator.Button.MoreThanOneRegister"),
    Locator_Link_MetaBit ("ChangeLagerHome.Locator.Link.MetaBit");

    // MOBILE

    private String str;
    HomePageENUM(String value) {str = value;}
    @Override
    public String toString() {return str;}
}