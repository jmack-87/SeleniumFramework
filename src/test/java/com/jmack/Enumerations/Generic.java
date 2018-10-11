package com.jmack.Enumerations;

public enum Generic {
	Text_changeLagerLoginURL("Generic.Text.changeLagerLoginURL"),
	Text_googleURL("Generic.Text.googleURL"),
	Text_nativeContext("Generic.Mobile.Text.Context.NativeApp"),
	Text_webViewContext("Generic.Mobile.Text.Context.WebView");

	private String str;
	Generic(String value) {str = value;}
	@Override
	public String toString() {return str;}
	
}
