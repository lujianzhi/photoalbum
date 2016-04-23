package com.lujianzhi.photoalbum.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.net.UserManager;
import com.lujianzhi.photoalbum.utils.ToastUtils;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class MyRegisterDialog extends Dialog {

    private Context context;
    private View popView;
    private TextView negative;
    private TextView positive;
    private TextView userName;
    private TextView password;
    private String userNameStr;
    private String passwordStr;
    private User user;

    public MyRegisterDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        popView = View.inflate(context, R.layout.dialog_regist, null);
        setContentView(popView);
        initView();
        initClickListener();
    }

    private void initView() {
        positive = (TextView) popView.findViewById(R.id.regist);
        negative = (TextView) popView.findViewById(R.id.cancel);
        userName = (TextView) popView.findViewById(R.id.username);
        password = (TextView) popView.findViewById(R.id.password);
    }

    private void getRegisterData() {
        userNameStr = userName.getText().toString();
        passwordStr = password.getText().toString();

        user = UserManager.getInstance().getUser();
        user.setUserName(userNameStr);
        user.setPassword(passwordStr);
    }

    private void initClickListener() {

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterData();
                if (TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(passwordStr)) {
                    ToastUtils.showShortToast(R.string.register_fail);
                } else {
                    UserManager.getInstance().registerUser(context, user, MyRegisterDialog.this);
                }
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}
