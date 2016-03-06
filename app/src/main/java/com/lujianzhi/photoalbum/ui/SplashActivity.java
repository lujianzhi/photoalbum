package com.lujianzhi.photoalbum.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.utils.SharedPreferencesUtils;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class SplashActivity extends Activity {

    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent;
            if (SharedPreferencesUtils.getLoginSuccess(getApplicationContext())) {
                intent = new Intent(getApplicationContext(), HomeActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.splash_in, R.anim.splash_out);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.splash_pic);
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void onBackPressed() {
    }
}
