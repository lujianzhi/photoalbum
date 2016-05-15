package com.lujianzhi.photoalbum.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lujianzhi on 2016/1/26.
 */
public class MyUtil {

    public static int px2dp(Context context, int px) {
        return (int) (px * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int dp2px(Context context, int dp) {
        return (int) (dp / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int getWindowWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getWindowHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static String getFormatDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(new Date(Long.parseLong(dateStr)));
    }

}
