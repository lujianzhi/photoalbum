package com.lujianzhi.photoalbum.net;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.view.MyRegisterDialog;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class UserManager {

    private String TAG = UserManager.class.getName();

    private User user;
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

    public User getUser() {
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
    public void registerUser(final Context context, User user, final MyRegisterDialog dialog) {
        PhotoAlbumManager.getInstance().registerRequest(user.getUserName(), user.getPassword(), new INetWorkListener() {
            @Override
            public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                String responseStr = responseInfo.result.toString();
                LogUtils.i(TAG, responseStr);

                if (PhotoAlbumManager.getInstance().parseRegisterStatues(responseStr) == 1) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                LogUtils.i(TAG, " /login/register.do : " + error.getMessage());
            }
        });
    }

}
