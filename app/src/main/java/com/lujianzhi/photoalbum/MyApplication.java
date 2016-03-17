package com.lujianzhi.photoalbum;

import android.app.Application;

import com.lujianzhi.photoalbum.utils.ConstantsUtil;
import com.lujianzhi.photoalbum.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

    }

    private void initApplication() {
        /**
         * 初始化Bmob的SDK
         */
        Bmob.initialize(getApplicationContext(), ConstantsUtil.APPLICATION_ID);

        /**
         * 初始化ImageLoader的SDK
         */
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

        /**
         * 初始化ToastUtils
         */
        ToastUtils.init(this);
    }

}
