package com.jmack.Enumerations.ChangeLagerApp;

public enum RegistrationPageENUM {

    // DESKTOP
    Text_URL("ChangeLagerRegistration.text.changeLagerRegistrationURL"),
    Locator_Text_PageTitle("ChangeLagerRegistration.Locator.text.pageTitle"),
    Locator_Tag_Head("ChangeLagerRegistration.Locator.Tag.head"),
    Locator_Button_DevPlan("ChangeLagerRegistration.Locator.Button.DevPlan"),
    Locator_Button_DevPlanSelect("ChangeLagerRegistration.Locator.Button.DevPlanSelect"),
    Locator_Button_DevPlanSelected("ChangeLagerRegistration.Locator.Button.DevPlanSelected"),
    Locator_Button_FreePlan("ChangeLagerRegistration.Locator.Button.FreePlan"),
    Locator_Button_FreePlanSelect("ChangeLagerRegistration.Locator.Button.FreePlanSelect"),
    Locator_Button_FreePlanSelected("ChangeLagerRegistration.Locator.Button.FreePlanSelected"),
    Locator_TextField_Name("ChangeLagerRegistration.Locator.textField.Name"),
    Locator_TextField_Email("ChangeLagerRegistration.Locator.textField.Email"),
    Locator_TextField_Password("ChangeLagerRegistration.Locator.textField.Password"),
    Locator_TextField_ConfirmPassword("ChangeLagerRegistration.Locator.textField.ConfirmPassword"),
    Locator_Link_HaveAnAccountLogin("ChangeLagerRegistration.Locator.Link.HaveAnAccountLogin"),
    Locator_TextField_CardholdersName("ChangeLagerRegistration.Locator.textField.CardholdersName"),
    Locator_iFrame_CreditCardInformation("ChangeLagerRegistration.Locator.iFrame.CreditCardInformation"),
    Locator_TextField_CardNumber("ChangeLagerRegistration.Locator.textField.CardNumber"),
    Locator_TextField_CardExpire("ChangeLagerRegistration.Locator.textField.CardExpire"),
    Locator_TextField_CardCVC("ChangeLagerRegistration.Locator.textField.CardCVC"),
    Locator_TextField_ZIPPostalCode("ChangeLagerRegistration.Locator.textField.ZIPPostalCode"),
    Locator_CheckBox_TermsAndService("ChangeLagerRegistration.Locator.checkBox.TermsAndService"),
    Locator_Button_Register("ChangeLagerRegistration.Locator.Button.Register"),
    Locator_Text_ErrorMessage("ChangeLagerRegistration.Locator.text.ErrorMessage"),
    Locator_Text_BlankNameErrorMessage("ChangeLagerRegistration.Locator.text.BlankNameFieldErrorMessage"),
    Locator_Text_BlankEmailErrorMessage("ChangeLagerRegistration.Locator.text.BlankEmailFieldErrorMessage"),
    Locator_Text_BlankPasswordErrorMessage("ChangeLagerRegistration.Locator.text.BlankPasswordFieldErrorMessage"),
    Locator_Text_NonCheckedTermsOfServiceErrorMessage("ChangeLagerRegistration.Locator.text.NonCheckedTermsOfServiceErrorMessage"),
    Locator_Link_TermsOfUse("ChangeLagerRegistration.Locator.Link.TermsOfService");

    // MOBILE

    private String str;
    RegistrationPageENUM(String value) {str = value;}
    @Override
    public String toString() {return str;}
}
