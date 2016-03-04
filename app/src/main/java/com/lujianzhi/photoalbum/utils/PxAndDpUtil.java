package com.lujianzhi.photoalbum.utils;

import android.content.Context;

/**
 * Created by lujianzhi on 2016/1/26.
 */
public class PxAndDpUtil {

    public static int px2dp(Context context, int px) {
        return (int) (px * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int dp2px(Context context, int dp) {
        return (int) (dp / context.getResources().getDisplayMetrics().density + 0.5f);
    }

}
