package com.jmack.Enumerations;

public enum Generic {
	
	Text_googleURL("Generic.Text.googleURL");

	private String str;
	Generic(String value) {str = value;}
	@Override
	public String toString() {return str;}
	
}
