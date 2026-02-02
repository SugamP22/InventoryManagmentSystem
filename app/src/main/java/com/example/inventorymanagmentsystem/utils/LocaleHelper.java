package com.example.inventorymanagmentsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import java.util.Locale;

public class LocaleHelper {
    private static final String PREFS_NAME = "LANG_PREF";
    private static final String KEY_LANG = "lang";

    // Wraps the activity context with the saved language
    public static Context wrapContext(Context context) {
        String lang = getSavedLanguage(context);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }


    // Saves the language choice
    public static void saveLanguage(Context context, String lang) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LANG, lang).apply();
    }

    // Gets the current language (defaults to English)
    public static String getSavedLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANG, "en");
    }
}