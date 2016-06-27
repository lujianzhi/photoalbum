package com.lujianzhi.photoalbum.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    /**
     * 初始化Activity
     */
    private void init() {
        initIntentData();
        initData();
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        int layoutId = getLayoutId();
        View contentView = getJavaCodeView();
        if (layoutId != 0) {
            setContentView(layoutId);
        } else if (contentView != null) {
            setContentView(contentView);
        } else {
            throw new RuntimeException("orz...忘了设置layout了...");
        }
        initTopViews();
        initViews();
        initBottomViews();
    }

    /**
     * 初始化顶部View
     */
    protected abstract void initTopViews();

    /**
     * 初始化View
     */
    protected abstract void initViews();

    /**
     * 初始化底部View
     */
    protected abstract void initBottomViews();

    /**
     * 设置布局文件id
     */
    protected abstract int getLayoutId();

    /**
     * java代码写布局时使用此方法
     *
     * @return java实现的代码
     */
    protected abstract View getJavaCodeView();


    /**
     * 初始化Intent传过来的数据
     */
    protected void initIntentData() {
    }

    /**
     * 获取数据
     */
    protected void initData() {
    }

    /**
     * 事件监听器
     *
     * @return
     */
    protected View.OnClickListener getOnClickListener() {
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent("com.lujianzhi.photoalbum.service.MusicService");
        intent.setPackage(getPackageName());
        stopService(intent);
    }
}
