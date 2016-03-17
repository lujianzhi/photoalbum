package com.lujianzhi.photoalbum.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.ViewHolder;
import com.lujianzhi.photoalbum.view.SateliteMenu;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumActivity extends BaseActivity {

    private SateliteMenu mSateliteMenu;
    private GridView photosView;
    private List<Photo> photoList = new ArrayList<Photo>();
    private PhotoAlbum photoAlbum;
    private String albumName;
    private int albumId;

    public static final int SYSTEM_GARRLY_REQUEST_CODE = 100;

    @Override
    protected void initTopViews() {
        RelativeLayout top = (RelativeLayout) findViewById(R.id.top);
        ImageView back = (ImageView) top.findViewById(R.id.back);
        TextView top_title = (TextView) top.findViewById(R.id.top_title);
        ImageView user_center = (ImageView) top.findViewById(R.id.user_center);

        back.setVisibility(View.INVISIBLE);
        top_title.setText(albumName);
        user_center.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initViews() {
        photosView = (GridView) findViewById(R.id.photos);
        MyAdapter adapter = new MyAdapter();
        PhotoAlbumManager.getInstance().getPhoto(this, adapter, albumId);
        photosView.setAdapter(adapter);
        photosView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        photosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhotoAlbumActivity.this, PhotoActivity.class);
                Bundle data = new Bundle();
                data.putInt("position", position);
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
    protected void initIntentData() {
        Bundle data = getIntent().getBundleExtra("data");
        albumName = data.getString("albumName");
        albumId = data.getInt("albumId");
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_album;
    }

    @Override
    protected View getJavaCodeView() {
        return null;
    }

    private void addPhoto() {
        Intent systemGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(systemGalleryIntent, PhotoAlbumActivity.SYSTEM_GARRLY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PhotoAlbumActivity.SYSTEM_GARRLY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // TODO 选中图片以后上传图片
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_center:
                Toast.makeText(this, "用户中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.add:
                addPhoto();
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

    public class MyAdapter extends BaseAdapter {

        public void setData(List<Photo> photos) {
            photoList = photos;
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public Photo getItem(int position) {
            return photoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(PhotoAlbumActivity.this).inflate(R.layout.photo_shrunken_item, null);
            }
            Photo photo = photoList.get(position);
            ImageView photoView = ViewHolder.get(convertView, R.id.photo);
            ImageLoader.getInstance().displayImage(photo.getPhotoUrl(), photoView);
            return convertView;
        }
    }

}
