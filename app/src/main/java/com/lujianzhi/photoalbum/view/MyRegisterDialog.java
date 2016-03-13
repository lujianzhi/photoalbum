package com.lujianzhi.photoalbum.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.net.UserManager;

import cn.bmob.v3.BmobUser;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class MyRegisterDialog extends Dialog {

    private Context context;
    private View popView;
    private TextView negative;
    private TextView positive;
    private TextView phoneNumber;
    private TextView nickName;
    private TextView password;
    private String phoneNumberStr;
    private String nickNameStr;
    private String passwordStr;
    private BmobUser user;

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
        phoneNumber = (TextView) popView.findViewById(R.id.account);
        nickName = (TextView) popView.findViewById(R.id.nickname);
        password = (TextView) popView.findViewById(R.id.password);
    }

    private void getRegisterData() {
        phoneNumberStr = phoneNumber.getText().toString();
        passwordStr = password.getText().toString();
        nickNameStr = nickName.getText().toString();

        user = UserManager.getInstance().getUser(context);
        user.setMobilePhoneNumber(phoneNumberStr);
        user.setUsername(phoneNumberStr);
        ((User) user).setNickName(nickNameStr);
        user.setPassword(passwordStr);
    }

    private void initClickListener() {

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterData();
                if (TextUtils.isEmpty(phoneNumberStr) || TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(context, R.string.register_fail, Toast.LENGTH_SHORT).show();
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
