package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.UserManager;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.utils.SharedPreferencesUtils;
import com.lujianzhi.photoalbum.view.MyRegisterDialog;


/**
 * Created by lujianzhi on 2016/1/28.
 */
public class LoginActivity extends BaseActivity {

    private String TAG = LoginActivity.class.getName();

    private EditText userName;
    private EditText userPassword;
    private TextView login;
    private TextView register;
    private User user;
    public static boolean loginState;

    public PhotoAlbumManager photoAlbumManager;

    @Override
    protected void initTopViews() {

    }

    @Override
    protected void initViews() {
        photoAlbumManager = PhotoAlbumManager.getInstance();

        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.user_password);
        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);

        login.setOnClickListener(getOnClickListener());
        register.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initBottomViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected View getJavaCodeView() {
        return null;
    }

    public void login() {
        user = UserManager.getInstance().getUser();
        user.setPassword(userPassword.getText().toString());
        user.setUserName(userName.getText().toString());
        photoAlbumManager.loginRequest(userName.getText().toString(), userPassword.getText().toString(), new INetWorkListener() {
            @Override
            public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                String respondStr = responseInfo.result.toString();
                LogUtils.i(TAG, respondStr);

                if (PhotoAlbumManager.getInstance().parserLogin(respondStr) == 1) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    LoginActivity.loginState = true;
                    SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
                    finish();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                LoginActivity.loginState = false;
                SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
            }
        });
    }

    private void register() {
        showDialog();
    }

    private void showDialog() {
        MyRegisterDialog dialog = new MyRegisterDialog(this, R.style.emptyDialog);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
            default:
                break;
        }
    }
}
