package com.lujianzhi.photoalbum.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianzhi on 2016/1/22.
 */
public class PhotoActivity extends BaseActivity {

    private ImageView photoImageView;
    private FrameLayout comments;
    private LinearLayout photoArea;
    private List<Photo> photoList = new ArrayList<Photo>();
    private int photoPosition;
    private TextView top_title;
    private boolean isShow;
    private float xDown = 0f;
    private float yDown = 0f;
    private float xUp = 0f;
    private float yUp = 0f;

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
            case R.id.edit:
                Toast.makeText(this, "对相册进行编辑", Toast.LENGTH_SHORT).show();
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
        top_title = (TextView) top.findViewById(R.id.top_title);
        ImageView user_center = (ImageView) top.findViewById(R.id.user_center);

        back.setVisibility(View.INVISIBLE);
        top_title.setText(photoList.get(photoPosition).getName());
        user_center.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initViews() {
        photoImageView = (ImageView) findViewById(R.id.photo);
        photoArea = (LinearLayout) findViewById(R.id.photo_area);
        comments = (FrameLayout) findViewById(R.id.comments);
        photoImageView.setImageResource(photoList.get(photoPosition).getResId());
        photoArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    xDown = event.getX();
                    yDown = event.getY();
                }
                if (action == MotionEvent.ACTION_UP) {
                    xUp = event.getX();
                    yUp = event.getY();
                    calculateXY(xDown, xUp, yDown, yUp);
                }
                return true;
            }
        });
    }

    @Override
    protected void initBottomViews() {
        LinearLayout bottom = (LinearLayout) findViewById(R.id.bottom);
        ImageView back = (ImageView) bottom.findViewById(R.id.back);
        ImageView add = (ImageView) bottom.findViewById(R.id.add);
        add.setVisibility(View.GONE);
        ImageView comment = (ImageView) bottom.findViewById(R.id.comment);
        ImageView edit = (ImageView) bottom.findViewById(R.id.edit);
        edit.setVisibility(View.VISIBLE);
        ImageView more = (ImageView) bottom.findViewById(R.id.more);
        back.setOnClickListener(getOnClickListener());
        comment.setOnClickListener(getOnClickListener());
        edit.setOnClickListener(getOnClickListener());
        more.setOnClickListener(getOnClickListener());
    }

    private void calculateXY(float xDown, float xUp, float yDown, float yUp) {
        switchPhotos(xDown, xUp);

        showOrHideComment(xDown, xUp, yDown, yUp);

    }

    private void showOrHideComment(float xDown, float xUp, float yDown, float yUp) {
        if (xDown - xUp < 250 && xDown - xUp > -250) {
            TextView comment = new TextView(this);
            comment.setText(photoList.get(photoPosition).getComment());
            comment.setPadding((int) xDown, (int) yDown, 0, 0);
            comment.setTextColor(Color.BLACK);
            comment.setTextSize(20);
            if (isShow) {
                isShow = false;
                comments.removeAllViews();
                comments.setVisibility(View.GONE);
            } else {
                isShow = true;
                comments.addView(comment);
                comments.setVisibility(View.VISIBLE);
            }
        }
    }

    private void switchPhotos(float xDown, float xUp) {
        Photo photo = new Photo();
        if (xDown - xUp > 250 && photoPosition < photoList.size() - 1) {
            photoPosition++;
            changePhoto(photo, photoPosition);
        } else if (xDown - xUp < -250 && photoPosition > 0) {
            photoPosition--;
            changePhoto(photo, photoPosition);
        }
    }

    private void changePhoto(Photo photo, int position) {
        photo = photoList.get(position);
        photoImageView.setImageResource(photo.getResId());
        top_title.setText(photo.getName());
        photoImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.photo_change));
        comments.removeAllViews();
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
    }

    @Override
    protected void initData() {
        photoList = PhotoAlbumManager.getInstance().getPhoto();
    }
}
