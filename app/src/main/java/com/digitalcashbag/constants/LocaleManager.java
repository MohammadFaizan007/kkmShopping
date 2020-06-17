package com.digitalcashbag.constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by surya on 30/4/18.
 */

public class LocaleManager {
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_HINDI = "hi";
    private static final String LANGUAGE_KEY = "language_key";

    public static Context setLocale(Context c) {
        return updateResources(c, getLanguage(c));
    }

    public static Context setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        return updateResources(c, language);
    }

    public static String getLanguage(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH);
    }

    @SuppressLint("ApplySharedPref")
    private static void persistLanguage(Context c, String language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }

//    public static Context onAttach(Context context) {
//        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
//        return setLocale(context, lang);
//    }
//
//    public static Context onAttach(Context context, String defaultLanguage) {
//        String lang = getPersistedData(context, defaultLanguage);
//        return setLocale(context, lang);
//    }
//
//    public static String getLanguage(Context context) {
//        return getPersistedData(context, Locale.getDefault().getLanguage());
//    }
//
//    public static Context setLocale(Context context, String language) {
//        persist(context, language);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return updateResources(context, language);
//        }
//
//        return updateResourcesLegacy(context, language);
//    }
//
//    private static String getPersistedData(Context context, String defaultLanguage) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return preferences.getString(LANGUAGE_KEY, defaultLanguage);
//    }
//
//    private static void persist(Context context, String language) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        editor.putString(LANGUAGE_KEY, language);
//        editor.apply();
//    }
//
//    @TargetApi(Build.VERSION_CODES.N)
//    private static Context updateResources(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//
//        Configuration configuration = context.getResources().getConfiguration();
//        configuration.setLocale(locale);
//
//        return context.createConfigurationContext(configuration);
//    }
//
//    @SuppressWarnings("deprecation")
//    private static Context updateResourcesLegacy(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//
//        Resources resources = context.getResources();
//
//        Configuration configuration = resources.getConfiguration();
//        configuration.locale = locale;
//
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//
//        return context;
//    }
////    public static Context setLocale(Context c) {
////        return updateResources(c, getLanguage(c));
////    }
////
////    public static Context setNewLocale(Context mContext, String language) {
////        persistLanguage(mContext, language);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////            return updateResources(mContext, language);
////        }
////
////        return updateResourcesLegacy(mContext, language);
////        // return updateResources(c, language);
////    }
////
////    public static String getLanguage(Context c) {
////        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
////        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH);
////    }
////
////    @SuppressLint("ApplySharedPref")
////    private static void persistLanguage(Context c, String language) {
////        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
////        // use commit() instead of apply(), because sometimes we kill the application process immediately
////        // which will prevent apply() to finish
////        prefs.edit().putString(LANGUAGE_KEY, language).commit();
////    }
////
////    //    private static Context updateResources(Context context, String language) {
//////        Locale locale = new Locale(language);
//////        Locale.setDefault(locale);
//////
//////        Resources res = context.getResources();
//////        Configuration config = new Configuration(res.getConfiguration());
//////        if (Build.VERSION.SDK_INT >= 17) {
//////            config.setLocale(locale);
//////            context = context.createConfigurationContext(config);
//////        } else {
//////            config.locale = locale;
//////            res.updateConfiguration(config, res.getDisplayMetrics());
//////        }
//////        return context;
//////    }
//////
//////    public static Locale getLocale(Resources res) {
//////        Configuration config = res.getConfiguration();
//////        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
//////    }
////    @TargetApi(Build.VERSION_CODES.N)
////    private static Context updateResources(Context context, String language) {
////        Locale locale = new Locale(language);
////        Locale.setDefault(locale);
////
////        Configuration configuration = context.getResources().getConfiguration();
////        configuration.setLocale(locale);
////
////        return context.createConfigurationContext(configuration);
////    }
////
////    @SuppressWarnings("deprecation")
////    private static Context updateResourcesLegacy(Context context, String language) {
////        Locale locale = new Locale(language);
////        Locale.setDefault(locale);
////
////        Resources resources = context.getResources();
////
////        Configuration configuration = resources.getConfiguration();
////        configuration.locale = locale;
////
////        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
////
////        return context;
////    }

}
