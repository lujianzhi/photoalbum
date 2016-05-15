package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.utils.LogUtils;

/**
 * Created by Lawson on 2016/5/15.
 */
public class PersonalAlbumActivity extends HomeActivity {

    @Override
    protected void initTopViews() {
        RelativeLayout top = (RelativeLayout) findViewById(R.id.top);
        ImageView back = (ImageView) top.findViewById(R.id.back);
        TextView top_title = (TextView) top.findViewById(R.id.top_title);
        ImageView user_center = (ImageView) top.findViewById(R.id.user_center);

        user_center.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        top_title.setText(R.string.personal_album);
    }

    @Override
    protected void initViews() {
        super.initViews();
        isNeedParentResume = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_center:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.back:
                isExit();
                break;
            case R.id.add:
                showAddPhotoAlbum();
                break;
            default:
                break;
        }
    }

    @Override
    protected void isExit() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PhotoAlbumManager.getInstance().getUserAlbumsRequest(new INetWorkListener() {
            @Override
            public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                String jsonStr = responseInfo.result.toString();
                LogUtils.i(TAG, " album/findUserAll.do : " + jsonStr);
                PhotoAlbumManager.getInstance().clearPhotoAlbum();
                adapter.setData(PhotoAlbumManager.getInstance().parserAllAlbum(jsonStr));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                adapter.clearData();
            }
        });
    }
}
