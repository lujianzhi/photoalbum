package com.lujianzhi.photoalbum.view;

import android.app.Dialog;
import android.content.Context;
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
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.utils.ToastUtils;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class MyEditDialog extends Dialog {
    private final String TAG = MyEditDialog.class.getCanonicalName();

    private Context context;
    private View popView;
    private TextView negative;
    private TextView positive;
    private EditText userName;
    private EditText password;
    private EditText passwordConfirm;
    private String userNameStr;
    private String passwordStr;
    private String passwordConfirmStr;
    private User user;

    private IMyClickListener myClickListener;

    public MyEditDialog(Context context) {
        super(context, R.style.emptyDialog);
        this.context = context;
        popView = View.inflate(context, R.layout.dialog_edit, null);
        setContentView(popView);
        initView();
        initClickListener();
    }

    private void initView() {
        positive = (TextView) popView.findViewById(R.id.regist);
        negative = (TextView) popView.findViewById(R.id.cancel);
        userName = (EditText) popView.findViewById(R.id.username);
        password = (EditText) popView.findViewById(R.id.password);
        passwordConfirm = (EditText) popView.findViewById(R.id.password);
    }

    private void getRegisterData() {
        userNameStr = userName.getText().toString();
        passwordStr = password.getText().toString();
        passwordConfirmStr = passwordConfirm.getText().toString();
    }

    private void initClickListener() {

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterData();
                if (passwordStr.equals(passwordConfirmStr)) {
                    PhotoAlbumManager.getInstance().editInfoRequest(userNameStr, passwordConfirmStr, new INetWorkListener() {
                        @Override
                        public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                            String jsonStr = responseInfo.result.toString();
                            LogUtils.i(TAG, jsonStr);

                            if (PhotoAlbumManager.getInstance().parserEditInfo(jsonStr) == 1) {
                                user = UserManager.getInstance().getUser();
                                user.setPassword(passwordConfirmStr);
                                user.setUserName(userNameStr);
                                ToastUtils.showShortToast(R.string.edit_success);
                            } else {
                                ToastUtils.showShortToast(R.string.edit_failure);
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {

                        }
                    });
                } else {
                    ToastUtils.showShortToast(R.string.edit_tips);
                }
                myClickListener.onClick();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void setComfirmListener(IMyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface IMyClickListener {
        void onClick();
    }
}
