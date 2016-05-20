package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.adapter.SpacesItemDecoration;
import com.lujianzhi.photoalbum.config.NetWorkConfig;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.view.MyLongPressDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    protected final String TAG = HomeActivity.class.getName();

    protected RecyclerView photoAlbumView;
    protected boolean isFinish;
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isFinish = false;
        }
    };
    protected PhotoAlbumRVAdapter adapter;
    protected boolean isNeedParentResume = true;
    protected boolean isMe;

    protected LinearLayout bottom;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_center:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.back:
                isExit();
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
        Intent intent = new Intent("com.lujianzhi.photoalbum.service.MusicService");
        intent.setPackage(getPackageName());
        startService(intent);
        photoAlbumView = (RecyclerView) findViewById(R.id.photo_album);
        photoAlbumView.setLayoutManager(new GridLayoutManager(this, 2));
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        photoAlbumView.addItemDecoration(decoration);
        adapter = new PhotoAlbumRVAdapter();
        photoAlbumView.setAdapter(adapter);
    }

    @Override
    protected void initBottomViews() {
        bottom = (LinearLayout) findViewById(R.id.bottom);
        ImageView back = (ImageView) bottom.findViewById(R.id.back);
        ImageView comment = (ImageView) bottom.findViewById(R.id.comment);
        back.setOnClickListener(getOnClickListener());
        comment.setVisibility(View.GONE);
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

    @Override
    public void onBackPressed() {
        isExit();
    }

    protected void isExit() {
        if (isFinish) {
            finish();
        } else {
            Toast.makeText(this, R.string.confirm_logout, Toast.LENGTH_SHORT).show();
            isFinish = true;
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    public class PhotoAlbumRVAdapter extends RecyclerView.Adapter<PhotoAlbumRVAdapter.MyViewHolder> {

        public void addData(PhotoAlbum photoAlbum) {
            PhotoAlbumManager.getInstance().getPhotoAlbums().add(photoAlbum);
        }

        public void setData(List<PhotoAlbum> list) {
            PhotoAlbumManager.getInstance().setPhotoAlbums((ArrayList<PhotoAlbum>) list);
        }

        public void clearData() {
            PhotoAlbumManager.getInstance().clearPhotoAlbum();
            adapter.setData(PhotoAlbumManager.getInstance().getPhotoAlbums());
            adapter.notifyDataSetChanged();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_album_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final PhotoAlbum photoAlbum = PhotoAlbumManager.getInstance().getPhotoAlbums().get(position);
            if (!"null".equals(photoAlbum.getCoverUrl())) {
                Glide.with(HomeActivity.this).load(NetWorkConfig.getHttpApiPath() + photoAlbum.getCoverUrl()).into(holder.albumCover);
            } else {
                holder.albumCover.setImageResource(R.drawable.default_photo);
            }
            if (photoAlbum.getType() == 0) {
                holder.albumName.setTextColor(Color.RED);
            } else {
                holder.albumName.setTextColor(Color.BLACK);
            }
            holder.albumName.setText(photoAlbum.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, PhotoAlbumActivity.class);
                    Bundle data = new Bundle();
                    data.putString("albumName", photoAlbum.getName());
                    data.putInt("albumId", photoAlbum.getId());
                    data.putBoolean("isMe", isMe);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MyLongPressDialog dialog = new MyLongPressDialog(HomeActivity.this);
                    dialog.setDeleteVisisble();
                    dialog.setDeleteClickListener(new MyLongPressDialog.IMyClickListener() {
                        @Override
                        public void onClick() {
                            PhotoAlbumManager.getInstance().deleteAlbumRequest(String.valueOf(photoAlbum.getId()), new INetWorkListener() {
                                @Override
                                public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                                    String jsonStr = responseInfo.result.toString();
                                    if (PhotoAlbumManager.getInstance().parserDeleteAlbum(jsonStr) == 1) {
                                        PhotoAlbumManager.getInstance().clearPhotoAlbum();
                                        adapter.setData(PhotoAlbumManager.getInstance().parserAllDeletePhoto(jsonStr));
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {

                                }
                            });
                        }
                    });
                    dialog.show();
                    return false;
                }
            });


        }

        @Override
        public int getItemCount() {
            return PhotoAlbumManager.getInstance().getPhotoAlbums().size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView albumName;
            ImageView albumCover;

            public MyViewHolder(View itemView) {
                super(itemView);
                albumName = (TextView) itemView.findViewById(R.id.name);
                albumCover = (ImageView) itemView.findViewById(R.id.cover);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isNeedParentResume) {
            PhotoAlbumManager.getInstance().getFindAllUserAlbum(new INetWorkListener() {
                @Override
                public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                    String jsonStr = responseInfo.result.toString();
                    PhotoAlbumManager.getInstance().clearPhotoAlbum();
                    adapter.setData(PhotoAlbumManager.getInstance().parserAllAlbum(jsonStr));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(HttpException error, String msg) {

                }
            });
        }

    }
}
