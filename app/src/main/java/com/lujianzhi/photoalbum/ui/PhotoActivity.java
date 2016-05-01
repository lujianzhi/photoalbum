package com.lujianzhi.photoalbum.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.ui.viewpager.HackyViewPager;
import com.lujianzhi.photoalbum.view.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianzhi on 2016/1/22.
 */
public class PhotoActivity extends BaseActivity {

    private List<Photo> photoList = new ArrayList<Photo>();
    private int photoPosition;
    private TextView top_title;
    private HackyViewPager hackyViewPager;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_center:
                Toast.makeText(this, "用户中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.comment:
                Toast.makeText(this, "添加对相册的评论", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initTopViews() {
        RelativeLayout top = (RelativeLayout) findViewById(R.id.top);
        ImageView back = (ImageView) top.findViewById(R.id.back);
        top_title = (TextView) top.findViewById(R.id.top_title);
        ImageView user_center = (ImageView) top.findViewById(R.id.user_center);

        back.setVisibility(View.INVISIBLE);
        top_title.setText(photoList.get(photoPosition).getName());
        user_center.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initViews() {
        hackyViewPager = (HackyViewPager) findViewById(R.id.photo_area);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        hackyViewPager.setAdapter(adapter);
        hackyViewPager.setCurrentItem(photoPosition);
//        photoImageView.setImageResource(photoList.get(photoPosition).getPhotoUrl());
    }

    @Override
    protected void initBottomViews() {
        LinearLayout bottom = (LinearLayout) findViewById(R.id.bottom);
        ImageView back = (ImageView) bottom.findViewById(R.id.back);
        ImageView add = (ImageView) bottom.findViewById(R.id.add);
        add.setVisibility(View.GONE);
        ImageView comment = (ImageView) bottom.findViewById(R.id.comment);
        ImageView edit = (ImageView) bottom.findViewById(R.id.edit);
        edit.setVisibility(View.GONE);
        ImageView more = (ImageView) bottom.findViewById(R.id.more);
        back.setOnClickListener(getOnClickListener());
        comment.setOnClickListener(getOnClickListener());
        more.setOnClickListener(getOnClickListener());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected View getJavaCodeView() {
        return null;
    }

    @Override
    protected void initIntentData() {
        Bundle data = getIntent().getBundleExtra("data");
        photoPosition = data.getInt("position");
        photoList = data.getParcelableArrayList("photoList");
    }

    @Override
    protected void initData() {
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        ImagePagerAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((View) object);
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);

            PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.photo);
            Glide.with(PhotoActivity.this).load(photoList.get(position).getPhotoUrl()).into(imageView);
            (view).addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}
