package com.jmack.Enumerations;

public enum ChangeLager_RegistrationPage {
    // GENERIC
    Text_pageTitle("ChangeLagerRegistration.text.pageTitle"),
    Locator_Tag_head("ChangeLagerRegistration.Locator.Tag.head"),

    // DESKTOP
    Locator_Button_DevPlanSelect("ChangeLagerRegistration.Locator.Button.DevPlanSelect"),
    Locator_Button_DevPlanSelected("ChangeLagerRegistration.Locator.Button.DevPlanSelected"),
    Locator_Button_FreePlanSelect("ChangeLagerRegistration.Locator.Button.FreePlanSelect"),
    Locator_Button_FreePlanSelected("ChangeLagerRegistration.Locator.Button.FreePlanSelected"),
    Locator_textField_Name("ChangeLagerRegistration.Locator.textField.Name"),
    Locator_textField_Email("ChangeLagerRegistration.Locator.textField.Email"),
    Locator_textField_Password("ChangeLagerRegistration.Locator.textField.Password"),
    Locator_textField_ConfirmPassword("ChangeLagerRegistration.Locator.textField.ConfirmPassword"),
    Locator_checkBox_TermsAndService("ChangeLagerRegistration.Locator.checkBox.TermsAndService"),
    Locator_Button_Register("ChangeLagerRegistration.Locator.Button.Registration");

    // MOBILE

    private String str;
    ChangeLager_RegistrationPage(String value) {str = value;}
    @Override
    public String toString() {return str;}
}
