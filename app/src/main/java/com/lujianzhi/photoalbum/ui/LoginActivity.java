package com.lujianzhi.photoalbum.ui;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.config.NetWorkConfig;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.UserManager;
import com.lujianzhi.photoalbum.net.networktask.MyCookieStore;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.utils.SharedPreferencesUtils;
import com.lujianzhi.photoalbum.utils.ToastUtils;
import com.lujianzhi.photoalbum.view.MyRegisterDialog;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;


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

//        PhotoAlbumManager.getInstance().loginRequest(user.getUserName(), user.getPassword(), new INetWorkListener() {
//            @Override
//            public <T> void onSuccess(ResponseInfo<T> responseInfo) {
//                String respondStr = responseInfo.result.toString();
//                LogUtils.i(TAG, respondStr);
//
//                if (PhotoAlbumManager.getInstance().parserLogin(respondStr) == 1) {
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    LoginActivity.loginState = true;
//                    SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException error, String msg) {
//                LoginActivity.loginState = false;
//                SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
//                ToastUtils.showShortToast(R.string.net_request_failure);
//            }
//        });

        if ("".equals(user.getUserName()) || "".equals(user.getPassword())) {
            ToastUtils.showShortToast("填写完整信息");
        } else {
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("正在登陆");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            final HttpUtils httpUtils = new HttpUtils(10000);
            RequestParams params = new RequestParams();
            params.addBodyParameter("userName", user.getUserName());
            params.addBodyParameter("password", user.getPassword());
            httpUtils.send(HttpRequest.HttpMethod.POST, NetWorkConfig.getHttpApiPath() + "/login/login.do", params, new RequestCallBack<Object>() {
                @Override
                public void onSuccess(ResponseInfo<Object> responseInfo) {
                    String respondStr = responseInfo.result.toString();
                    LogUtils.i(TAG, respondStr);

                    DefaultHttpClient dh = (DefaultHttpClient) httpUtils.getHttpClient();
                    MyCookieStore.cookieStore = dh.getCookieStore();
                    CookieStore cs = dh.getCookieStore();
                    List<Cookie> cookies = cs.getCookies();
                    String aa = null;
                    for (int i = 0; i < cookies.size(); i++) {
                        if ("JSESSIONID".equals(cookies.get(i).getName())) {
                            aa = cookies.get(i).getValue();
                            break;
                        }
                    }
                    Log.i("lawson", "比较sessionid" + aa);

                    dialog.dismiss();

                    if (PhotoAlbumManager.getInstance().parserLogin(respondStr) == 1) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        //TODO 每次都为false
                        LoginActivity.loginState = false;
                        SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
                        finish();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    LoginActivity.loginState = false;
                    SharedPreferencesUtils.recordLoginState(LoginActivity.this, LoginActivity.loginState);
                    ToastUtils.showShortToast(R.string.net_request_failure);
                    dialog.dismiss();
                }
            });
        }

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
