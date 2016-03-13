package com.lujianzhi.photoalbum.net;

import android.content.Context;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.view.MyRegisterDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class UserManager {

    private BmobUser user;
    private static UserManager userManager;

    private UserManager() {
        user = new User();
    }

    public static UserManager getInstance() {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null) {
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    public BmobUser getUser(Context context) {
        /**
         * 在扩展了用户类的情况下获取当前登录用户，可以使用如下的示例代码
         * MyUser userInfo = BmobUser.getCurrentUser(context,MyUser.class);
         */
        User user = BmobUser.getCurrentUser(context, User.class);
        if (user != null) {
            return user;
        } else {
            return new User();
        }
    }

    /**
     * 注册用户
     *
     * @param context
     * @param user
     */
    public void registerUser(final Context context, BmobUser user, final MyRegisterDialog dialog) {
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, R.string.register_success, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, R.string.register_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
