package com.esgi.vMail.control;
import java.util.Locale;
import java.util.ResourceBundle;

public class LangManager {
	protected static ResourceBundle bundle;
	protected static Locale locale;

	public static ResourceBundle getBundle() {
		if (bundle == null) {
			locale = Locale.getDefault();
			bundle = ResourceBundle.getBundle("vMail", locale);
		}
		return bundle;
	}

	public static String text(String key) {
		if (LangManager.getBundle().containsKey(key)) {
			return LangManager.getBundle().getString(key);
		}
		return key;
	}
}
