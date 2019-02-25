package com.jmack.Enumerations.ChangeLagerApp;

public enum ChangeLager_RegistrationPage {

    // DESKTOP
    Text_changeLagerRegistrationURL("ChangeLagerRegistration.text.changeLagerRegistrationURL"),
    Text_pageTitle("ChangeLagerRegistration.text.pageTitle"),
    Locator_Tag_head("ChangeLagerRegistration.Locator.Tag.head"),
    Locator_Button_DevPlan("ChangeLagerRegistration.Locator.Button.DevPlan"),
    Locator_Button_DevPlanSelect("ChangeLagerRegistration.Locator.Button.DevPlanSelect"),
    Locator_Button_DevPlanSelected("ChangeLagerRegistration.Locator.Button.DevPlanSelected"),
    Locator_Button_FreePlan("ChangeLagerRegistration.Locator.Button.FreePlan"),
    Locator_Button_FreePlanSelect("ChangeLagerRegistration.Locator.Button.FreePlanSelect"),
    Locator_Button_FreePlanSelected("ChangeLagerRegistration.Locator.Button.FreePlanSelected"),
    Locator_textField_Name("ChangeLagerRegistration.Locator.textField.Name"),
    Locator_textField_Email("ChangeLagerRegistration.Locator.textField.Email"),
    Locator_textField_Password("ChangeLagerRegistration.Locator.textField.Password"),
    Locator_textField_ConfirmPassword("ChangeLagerRegistration.Locator.textField.ConfirmPassword"),
    Locator_textField_CardholdersName("ChangeLagerRegistration.Locator.textField.CardholdersName"),
    Locator_iFrame_CreditCardInformation("ChangeLagerRegistration.Locator.iFrame.CreditCardInformation"),
    Locator_textField_CardNumber("ChangeLagerRegistration.Locator.textField.CardNumber"),
    Locator_textField_CardExpire("ChangeLagerRegistration.Locator.textField.CardExpire"),
    Locator_textField_CardCVC("ChangeLagerRegistration.Locator.textField.CardCVC"),
    Locator_textField_ZIPPostalCode("ChangeLagerRegistration.Locator.textField.ZIPPostalCode"),
    Locator_checkBox_TermsAndService("ChangeLagerRegistration.Locator.checkBox.TermsAndService"),
    Locator_Button_Register("ChangeLagerRegistration.Locator.Button.Register"),
    Locator_text_ErrorMessage("ChangeLagerRegistration.Locator.text.ErrorMessage");

    // MOBILE

    private String str;
    ChangeLager_RegistrationPage(String value) {str = value;}
    @Override
    public String toString() {return str;}
}
