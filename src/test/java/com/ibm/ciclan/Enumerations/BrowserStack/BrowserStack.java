package com.ibm.ciclan.Enumerations.BrowserStack;

public enum BrowserStack {

	;

	public enum MobileType {

		TABLET,
		PHONE,
		;

		public static MobileType getMobileType(String type) {
			return MobileType.valueOf(type.toUpperCase());
		}

	}

}
