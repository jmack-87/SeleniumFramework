package com.jmack.Base;


public class GlobalConstants {

	protected String testDataFileName = "TestParametersJSON.json";
	protected String testReferenceFileName = "testReference.properties";
	
	protected String testDataFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\"+testDataFileName;
	protected String testReferenceFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\"+testReferenceFileName;
	
	protected String locatorSeparator = "@@@";
	protected String compoundLocatorPlacehold = "~~~";
	
	protected String perfectoSecurityToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3ek85T2dhclp1X3ZuQVJuakl0QWQ2SWtEeE41aHR2ZU85OHRibW9XbzVnIn0.eyJqdGkiOiI3YWQ2MWU3ZC1mOGUyLTQzOTYtODEzOS1kODg4NDRkY2M5NmYiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTM1NTY3MTI4LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2libWdicy1wZXJmZWN0b21vYmlsZS1jb20iLCJhdWQiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsInN1YiI6IjVlYjZlYmMyLTJiNzgtNGFmNC1hNjJiLTkwZTBhODEwYTRhYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6Ijk1Njk3ZTU5LTE0YjUtNDNiMC1iNWQ2LTAxN2MyZjc1YjgyNSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX19.RG1zulwpU9tOoWqAHJ65hWYIHlZg8YymxFmm3hnicd66WxRBJ4GZaDf905TUGUSOFrRR5tV56uu6gitjyxYqZn5DrxuqoM8J2HF2klcbon41xCf5bcUXVlaQU63y274F6rX7dJ8Yz-LtEaZhzFFInzBRJojHvbKCuEXC2CKj6N5n2fGHHcVU7RjJ_JkT7ZOyZqiq21kDAzNgY-6jZRTuo0rUsG8dztyQd-vsgJ1CRqT_sVSsDl_UaUwsZj-wIqueOunkgfrdGgas_e92gYtF57mVgVPCRSDMRu_dPE-AehDTcGTi1PTffJ04QY6stSZWnSIVAn8n7sw0X_Mao07Wag";
	protected String perfectoHost = "https://ibmgbs.perfectomobile.com/nexperience/perfectomobile/wd/hub";
	
	// #com.santander.Base.TestBase: SRF login config
    protected final String srfHost = "https://ftaas.saas.hpe.com:443/wd/hub";
    protected final String srfId = "t251228371_oauth2-r7u5yVsEGH7811WXXuE4@hpe.com";
    protected final String srfPass = "TPSePEiBhxt2l3NUTJtP";

}
