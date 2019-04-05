package com.jmack.Enumerations.ChangeLagerApp;

public enum ChangeLager_RegistrationPage {

    // DESKTOP
    text_URL("ChangeLagerRegistration.text.changeLagerRegistrationURL"),
    text_pageTitle("ChangeLagerRegistration.Locator.text.pageTitle"),
    Tag_head("ChangeLagerRegistration.Locator.Tag.head"),
    button_DevPlan("ChangeLagerRegistration.Locator.Button.DevPlan"),
    button_DevPlanSelect("ChangeLagerRegistration.Locator.Button.DevPlanSelect"),
    button_DevPlanSelected("ChangeLagerRegistration.Locator.Button.DevPlanSelected"),
    button_FreePlan("ChangeLagerRegistration.Locator.Button.FreePlan"),
    button_FreePlanSelect("ChangeLagerRegistration.Locator.Button.FreePlanSelect"),
    button_FreePlanSelected("ChangeLagerRegistration.Locator.Button.FreePlanSelected"),
    textField_Name("ChangeLagerRegistration.Locator.textField.Name"),
    textField_Email("ChangeLagerRegistration.Locator.textField.Email"),
    textField_Password("ChangeLagerRegistration.Locator.textField.Password"),
    textField_ConfirmPassword("ChangeLagerRegistration.Locator.textField.ConfirmPassword"),
    link_HaveAnAccountLogin("ChangeLagerRegistration.Locator.Link.HaveAnAccountLogin"),
    textField_CardholdersName("ChangeLagerRegistration.Locator.textField.CardholdersName"),
    iFrame_CreditCardInformation("ChangeLagerRegistration.Locator.iFrame.CreditCardInformation"),
    textField_CardNumber("ChangeLagerRegistration.Locator.textField.CardNumber"),
    textField_CardExpire("ChangeLagerRegistration.Locator.textField.CardExpire"),
    textField_CardCVC("ChangeLagerRegistration.Locator.textField.CardCVC"),
    textField_ZIPPostalCode("ChangeLagerRegistration.Locator.textField.ZIPPostalCode"),
    Locator_checkBox_TermsAndService("ChangeLagerRegistration.Locator.checkBox.TermsAndService"),
    button_Register("ChangeLagerRegistration.Locator.Button.Register"),
    text_ErrorMessage("ChangeLagerRegistration.Locator.text.ErrorMessage"),
    text_BlankNameErrorMessage("ChangeLagerRegistration.Locator.text.BlankNameFieldErrorMessage"),
    text_BlankEmailErrorMessage("ChangeLagerRegistration.Locator.text.BlankEmailFieldErrorMessage"),
    text_BlankPasswordErrorMessage("ChangeLagerRegistration.Locator.text.BlankPasswordFieldErrorMessage"),
    text_NonCheckedTermsOfServiceErrorMessage("ChangeLagerRegistration.Locator.text.NonCheckedTermsOfServiceErrorMessage"),
    link_TermsOfUse("ChangeLagerRegistration.Locator.Link.TermsOfService");

    // MOBILE

    private String str;
    ChangeLager_RegistrationPage(String value) {str = value;}
    @Override
    public String toString() {return str;}
}
