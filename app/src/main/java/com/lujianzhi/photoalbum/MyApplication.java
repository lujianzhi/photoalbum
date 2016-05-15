package com.lujianzhi.photoalbum;

import android.app.Application;
import android.content.Context;

import com.lujianzhi.photoalbum.utils.ToastUtils;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class MyApplication extends Application {

    private static Context myApplicationContext = getMyApplicationContext();


    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

    }

    private void initApplication() {

        /**
         * 初始化ToastUtils
         */
        ToastUtils.init(this);
    }

    public static Context getMyApplicationContext() {
        return myApplicationContext;
    }

}
