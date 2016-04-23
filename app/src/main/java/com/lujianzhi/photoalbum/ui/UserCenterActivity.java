package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.SharedPreferencesUtils;
import com.lujianzhi.photoalbum.view.RoundImageView;

import cn.bmob.v3.BmobUser;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class UserCenterActivity extends BaseActivity {

    private TextView nickName;
    private TextView phoneNumber;
    private RoundImageView userPortrait;
    private BmobUser user;

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

//        user = UserManager.getInstance().getUser();
//        nickName.setText(((User) user).getUserName());
        phoneNumber.setText(String.format(getResources().getString(R.string.phone_number), user.getMobilePhoneNumber().toString()));
//        ImageLoader.getInstance().displayImage(((User) user).getUserPortrait(), userPortrait);

        LinearLayout edit_area = (LinearLayout) findViewById(R.id.edit_area);
        LinearLayout logout_area = (LinearLayout) findViewById(R.id.logout_area);

        logout_area.setOnClickListener(getOnClickListener());
        edit_area.setOnClickListener(getOnClickListener());
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
            case R.id.edit_area:
                Toast.makeText(this, "编辑信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_area:
                BmobUser.logOut(this);
                SharedPreferencesUtils.recordLoginState(this,false);
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
