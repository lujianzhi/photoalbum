package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.https.PhotoAlbumManager;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private List<PhotoAlbum> photoAlbumsList = new ArrayList<PhotoAlbum>();
    private GridView photoAlbumView;
    private boolean isFinish;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isFinish = false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_center:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.back:
                if (isFinish) {
                    finish();
                } else {
                    Toast.makeText(this, R.string.confirm_logout, Toast.LENGTH_SHORT).show();
                    isFinish = true;
                    handler.sendEmptyMessageDelayed(0, 2000);
                }
                break;
            case R.id.add:
                PhotoAlbumManager.getInstance().addPhotoAlbum(this, photoAlbumsList.size() + 1, "第一个相册", true, 1);
                break;
            case R.id.comment:
                Toast.makeText(this, "添加对相册的评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.more:
                Toast.makeText(this, "弹出更多菜单", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initTopViews() {
        RelativeLayout top = (RelativeLayout) findViewById(R.id.top);
        ImageView back = (ImageView) top.findViewById(R.id.back);
        TextView top_title = (TextView) top.findViewById(R.id.top_title);
        ImageView user_center = (ImageView) top.findViewById(R.id.user_center);

        user_center.setVisibility(View.VISIBLE);
        back.setVisibility(View.INVISIBLE);
        top_title.setText(R.string.app_name);
        user_center.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initViews() {
        photoAlbumView = (GridView) findViewById(R.id.photo_album);
        PhotoAlbumAdapter adapter = new PhotoAlbumAdapter();
        adapter.setData(PhotoAlbumManager.getInstance().getPhtotAlbum(this));
        photoAlbumView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        photoAlbumView.setAdapter(adapter);
        photoAlbumView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoAlbum photoAlbum = (PhotoAlbum) parent.getItemAtPosition(position);
                Intent intent = new Intent(HomeActivity.this, PhotoAlbumActivity.class);
                Bundle data = new Bundle();
                data.putString("albumName", photoAlbum.getName());
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initBottomViews() {
        LinearLayout bottom = (LinearLayout) findViewById(R.id.bottom);
        ImageView back = (ImageView) bottom.findViewById(R.id.back);
        ImageView add = (ImageView) bottom.findViewById(R.id.add);
        ImageView comment = (ImageView) bottom.findViewById(R.id.comment);
        ImageView more = (ImageView) bottom.findViewById(R.id.more);
        back.setOnClickListener(getOnClickListener());
        add.setOnClickListener(getOnClickListener());
        comment.setOnClickListener(getOnClickListener());
        more.setOnClickListener(getOnClickListener());
    }

    @Override
    protected View getJavaCodeView() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
    }

    private class PhotoAlbumAdapter extends BaseAdapter {

        public void setData(List<PhotoAlbum> list) {
            photoAlbumsList = list;
        }

        @Override
        public int getCount() {
            return photoAlbumsList.size();
        }

        @Override
        public Object getItem(int position) {
            return photoAlbumsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.photo_album_item, null);
            }
            PhotoAlbum photoAlbum = photoAlbumsList.get(position);
            TextView albumName = ViewHolder.get(convertView, R.id.name);
            ImageView albumCover = ViewHolder.get(convertView, R.id.cover);
            if (!TextUtils.isEmpty(photoAlbum.getCoverUrl())) {
                ImageLoader.getInstance().displayImage(photoAlbum.getCoverUrl(),albumCover);
            }
            albumName.setText(photoAlbum.getName());
            return convertView;
        }
    }
}
