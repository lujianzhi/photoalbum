package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.net.UserManager;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.MyRegistDialog;
import com.lujianzhi.photoalbum.utils.SharedPreferencesUtils;

import cn.bmob.v3.listener.SaveListener;


/**
 * Created by lujianzhi on 2016/1/28.
 */
public class LoginActivity extends BaseActivity {

    private EditText phoneNumber;
    private EditText userPassword;
    private TextView login;
    private TextView register;
    private User user;
    public static boolean loginState;

    @Override
    protected void initTopViews() {

    }

    @Override
    protected void initViews() {
        phoneNumber = (EditText) findViewById(R.id.phone_number);
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
        user = (User) UserManager.getInstance().getUser(this);
        user.setMobilePhoneNumber(phoneNumber.getText().toString());
        user.setUsername(phoneNumber.getText().toString());
        user.setPassword(userPassword.getText().toString());
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                LoginActivity.loginState = true;
                SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                LoginActivity.loginState = false;
                SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
            }
        });
    }

    private void regist() {
        showDialog();
    }

    private void showDialog() {
        MyRegistDialog dialog = new MyRegistDialog(this, R.style.emptyDialog);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                regist();
                break;
            default:
                break;
        }
    }
}
