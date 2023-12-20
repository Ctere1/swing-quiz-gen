package quizGenerator.multipleChoice.Configuration;

import java.util.Locale;
import java.util.prefs.Preferences;

public class ConfigurationManager {
	private static final String USER_LANGUAGE = "en";

	// Get the selected language from the user preferences
	public static Locale getSelectedLanguage() {
		Preferences prefs = Preferences.userNodeForPackage(ConfigurationManager.class);
		String languageTag = prefs.get(USER_LANGUAGE, "");
		return languageTag.isEmpty() ? null : Locale.forLanguageTag(languageTag);
	}

	// Set the selected language in user preferences
	public static void setSelectedLanguage(Locale locale) {
		Preferences prefs = Preferences.userNodeForPackage(ConfigurationManager.class);
		prefs.put(USER_LANGUAGE, locale.toLanguageTag());

	}
}
