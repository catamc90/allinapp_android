package com.allin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.allin.response.LoginInfo;
import com.allin.response.UserInfo;

/**
 * Created by harry on 10/2/17.
 */

public class PreferenceManager {

    public static final String APP_DEFAULTS = "AllInApp";

    public static final String LANGUAGE = "language";

    public static SharedPreferences getSharedPref(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(
                APP_DEFAULTS, Context.MODE_PRIVATE);

        return preferences;

    }

    public static void saveString(Context context, String key, String value) {
        Log.e("SAVING_" + key, "VALUE : " + value);
        SharedPreferences.Editor editor = getSharedPref(context).edit();
        editor.putString(key, "" + value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        return getSharedPref(context).getString(key, null);
    }

    public static void saveInt(Context context, String key,
                               int value) {
        Log.e("SAVING_" + key, "VALUE : " + value);
        SharedPreferences.Editor editor = getSharedPref(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        return getSharedPref(context).getInt(key, 0);
    }

    public static void saveBoolean(Context context, String key,
                                   boolean value) {
        Log.e("SAVING_" + key, "VALUE : " + value);
        SharedPreferences.Editor editor = getSharedPref(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getSharedPref(context).getBoolean(key, false);
    }

    public static void saveLong(Context context, String key, long value) {
        Log.e("SAVING_" + key, "VALUE : " + value);
        SharedPreferences.Editor editor = getSharedPref(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key) {
        return getSharedPref(context).getLong(key, 0);
    }

    public static void saveLanguage(Context context, String key, String value) {
        Log.e("SAVING_" + key, "VALUE : " + value);
        SharedPreferences.Editor editor = getSharedPref(context).edit();
        editor.putString(LANGUAGE, value);
        editor.commit();
    }

    public static String getLanguage(Context context) {
        return getSharedPref(context).getString(LANGUAGE, "fr");
    }

    public static void clearAll(Context context) {
        getSharedPref(context).edit().clear().commit();
    }

    public static void clearKey(Context context, String key) {
        Log.e("Utils", "REMOVING : " + key);
        getSharedPref(context).edit().remove(key).commit();
    }

    public static void saveLoginData(Context context, LoginInfo loginInfo) {
        saveString(context, Constants.PrefsKey.PREFS_ACCESS_TOKEN, loginInfo.accessToken);
        saveString(context, Constants.PrefsKey.PREFS_USER_NAME, loginInfo.username);
        saveString(context, Constants.PrefsKey.PREFS_REAL_NAME, loginInfo.realname);
        saveBoolean(context, Constants.PrefsKey.PREFS_IS_MANAGER, loginInfo.isManager);
        saveBoolean(context, Constants.PrefsKey.PREFS_HAS_ADMIN, loginInfo.hasAdmin);
    }

    public static void saveUserData(Context context, UserInfo userInfo) {
        saveString(context, Constants.PrefsKey.PREFS_USER_NAME, userInfo.username);
        saveString(context, Constants.PrefsKey.PREFS_DISPLAY_NAME, userInfo.displayName);
        saveString(context, Constants.PrefsKey.PREFS_AVATAR, userInfo.avatar);
        saveString(context, Constants.PrefsKey.PREFS_EMAIL, userInfo.email);
        saveString(context, Constants.PrefsKey.PREFS_CREATION_TIME, userInfo.createTime);
        saveString(context, Constants.PrefsKey.PREFS_UPDATE_TIME, userInfo.updateTime);

        saveInt(context, Constants.PrefsKey.PREFS_USER_ID, userInfo.userId);
        saveInt(context, Constants.PrefsKey.PREFS_COMPANY_ID, userInfo.companyId);
        saveInt(context, Constants.PrefsKey.PREFS_ASSET_COUNT, userInfo.assetCount);
        saveInt(context, Constants.PrefsKey.PREFS_ROLE, userInfo.role);
        saveInt(context, Constants.PrefsKey.PREFS_ACTIVE, userInfo.active);
        saveInt(context, Constants.PrefsKey.PREFS_ASSET_GOAL, userInfo.assetGoal);
        saveInt(context, Constants.PrefsKey.PREFS_SCORE, userInfo.score);

        saveBoolean(context, Constants.PrefsKey.PREFS_IS_MANAGER, userInfo.isManager);
    }

    public static String getAccessToken(Context context) {
        return PreferenceManager.getString(context, Constants.PrefsKey.PREFS_ACCESS_TOKEN);
    }


//    public static void saveUserInfo(Context context, UserInfo userInfo) {
//
//        saveStringToUserDefaults(context, Constant.USER_ID, userInfo.userId);
//        saveStringToUserDefaults(context, Constant.NAME, userInfo.name);
//        saveStringToUserDefaults(context, Constant.EMAIL, userInfo.email);
//        saveStringToUserDefaults(context, Constant.PHONE, userInfo.phone);
//        saveStringToUserDefaults(context, Constant.ZIPCODE, userInfo.zipcode);
//
//    }

}