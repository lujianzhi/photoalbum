package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.User;
import com.lujianzhi.photoalbum.net.UserManager;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.SharedPreferencesUtils;
import com.lujianzhi.photoalbum.view.MyEditDialog;
import com.lujianzhi.photoalbum.view.RoundImageView;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class UserCenterActivity extends BaseActivity {

    private TextView nickName;
    private TextView phoneNumber;
    private RoundImageView userPortrait;
    private User user;

    @Override
    protected void initTopViews() {
        RelativeLayout top = (RelativeLayout) findViewById(R.id.top);
        TextView top_title = (TextView) top.findViewById(R.id.top_title);
        top_title.setText(getResources().getText(R.string.user_center));
        ImageView back = (ImageView) top.findViewById(R.id.back);
        back.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initViews() {
        nickName = (TextView) findViewById(R.id.nick_name);
        phoneNumber = (TextView) findViewById(R.id.userName);
        userPortrait = (RoundImageView) findViewById(R.id.user_portrait);

        user = UserManager.getInstance().getUser();
        nickName.setText(user.getUserName());

        if ("null".equals(user.getUserUrl()) || "".equals(user.getUserUrl()) || user.getUserUrl() == null) {
            userPortrait.setImageResource(R.drawable.photo);
        } else {
            Glide.with(UserCenterActivity.this).load(user.getUserUrl()).into(userPortrait);
        }

        LinearLayout edit_area = (LinearLayout) findViewById(R.id.edit_area);
        LinearLayout logout_area = (LinearLayout) findViewById(R.id.logout_area);
        LinearLayout personal_album_area = (LinearLayout) findViewById(R.id.personal_album_area);

        logout_area.setOnClickListener(getOnClickListener());
        edit_area.setOnClickListener(getOnClickListener());
        personal_album_area.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initBottomViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    protected View getJavaCodeView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.personal_album_area:
                intoPersonalAlbum();
                break;
            case R.id.edit_area:
                showEditDialog();
                break;
            case R.id.logout_area:
                //TODO 退出登录时需要将session清除
                SharedPreferencesUtils.recordLoginState(this, false);
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void intoPersonalAlbum() {
        startActivity(new Intent(this, PersonalAlbumActivity.class));
    }

    private void showEditDialog() {
        MyEditDialog myEditDialog = new MyEditDialog(this);
        myEditDialog.setComfirmListener(new MyEditDialog.IMyClickListener() {
            @Override
            public void onClick() {
                nickName.setText(user.getUserName());
            }
        });
        myEditDialog.show();
    }
}
