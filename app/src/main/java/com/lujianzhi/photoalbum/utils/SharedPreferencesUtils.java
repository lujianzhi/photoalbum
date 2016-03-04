package com.lujianzhi.photoalbum.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lujianzhi on 2016/1/28.
 */
public class SharedPreferencesUtils {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    public static final String LOGINSTATE = "LOGINSTATE";

    public static boolean getLoginSuccess(Context context) {
        sp = context.getSharedPreferences(SharedPreferencesUtils.LOGINSTATE, Context.MODE_PRIVATE);
        return sp.getBoolean(SharedPreferencesUtils.LOGINSTATE, false);
    }

    public static void recordLoginState(Context context, boolean loginState) {
        sp = context.getSharedPreferences(SharedPreferencesUtils.LOGINSTATE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean(SharedPreferencesUtils.LOGINSTATE, loginState);
        editor.commit();
    }

}
