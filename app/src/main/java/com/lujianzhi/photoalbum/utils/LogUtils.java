package com.lujianzhi.photoalbum.utils;

import android.util.Log;

public class LogUtils {

    private static boolean isDebug = true;
    private static final String TAG = "lawson";

    public static void i(String logMessage) {
        if (isDebug) {
            Log.i(TAG, logMessage);
        }

    }

    public static void i(String TAG,String logMessage){
        if (isDebug) {
            Log.i(TAG, logMessage);
        }
    }

}
