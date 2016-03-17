package com.lujianzhi.photoalbum.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Context context;

    public static void init(Context mContext) {
        context = mContext;

    }

    public static void showShortToast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_LONG).show();
    }


}
