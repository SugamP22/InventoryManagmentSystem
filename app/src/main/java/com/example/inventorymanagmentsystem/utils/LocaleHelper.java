package com.example.inventorymanagmentsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * I use this to support English and Spanish. I save the chosen language in SharedPreferences and
 * wrap the context so all activities get the correct strings from values/ or values-es-rES/.
 */
public class LocaleHelper {
    private static final String PREFS_NAME = "LANG_PREF";
    private static final String KEY_LANG = "lang";

    /** I wrap the context with the saved locale so getString() and resources use the right language. */
    public static Context wrapContext(Context context) {
        String lang = getSavedLanguage(context);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }

    public static void saveLanguage(Context context, String lang) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LANG, lang).apply();
    }

    /** I default to "en" if nothing was saved yet. */
    public static String getSavedLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANG, "en");
    }
}