package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.view.MyAddAlbumDialog;
import com.lujianzhi.photoalbum.view.MyLongPressDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private final String TAG = HomeActivity.class.getName();

    private List<PhotoAlbum> photoAlbumsList = new ArrayList<PhotoAlbum>();
    private RecyclerView photoAlbumView;
    private boolean isFinish;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isFinish = false;
        }
    };
    private PhotoAlbumRVAdapter adapter;

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
        photoAlbumView = (RecyclerView) findViewById(R.id.photo_album);
        photoAlbumView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        photoAlbumView.addItemDecoration(decoration);
        adapter = new PhotoAlbumRVAdapter();
        PhotoAlbumManager.getInstance().getAlbumsRequest(new INetWorkListener() {
            @Override
            public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                String jsonStr = responseInfo.result.toString();
                LogUtils.i(TAG, " album/findAll.do : " + jsonStr);

                adapter.setData(PhotoAlbumManager.getInstance().parserAllAlbum(jsonStr));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
        photoAlbumView.setAdapter(adapter);
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
        comment.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
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

    private void showAddPhotoAlbum() {
        final MyAddAlbumDialog dialog = new MyAddAlbumDialog(this);
        dialog.setPositiveClickListener(new MyAddAlbumDialog.IMyClickListener() {
            @Override
            public void onClick() {
                PhotoAlbumManager.getInstance().addPhotoAlbumRequest(dialog.getAddAlbumName(), dialog.getAddAlbumType(), new INetWorkListener() {
                    @Override
                    public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                        String responseStr = responseInfo.result.toString();
                        LogUtils.i(TAG, " album/add.do : " + responseStr);

                        if (PhotoAlbumManager.getInstance().parserAddAlbum(responseStr) == 1) {
                            //TODO 添加相册返回时的处理应该于添加相片一样
                            adapter.addData(PhotoAlbumManager.getInstance().parserAllAlbum(responseStr).get(0));
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
    }

    @Override
    public void onBackPressed() {
        isExit();
    }

    private void isExit() {
        if (isFinish) {
            finish();
        } else {
            Toast.makeText(this, R.string.confirm_logout, Toast.LENGTH_SHORT).show();
            isFinish = true;
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    public class PhotoAlbumRVAdapter extends RecyclerView.Adapter<PhotoAlbumRVAdapter.MyViewHolder> {
        public void setData(List<PhotoAlbum> list) {
            photoAlbumsList = list;
        }

        public void addData(PhotoAlbum photoAlbum) {
            photoAlbumsList.add(photoAlbum);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_album_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final PhotoAlbum photoAlbum = photoAlbumsList.get(position);
            if (!"null".equals(photoAlbum.getCoverUrl())) {
                Glide.with(HomeActivity.this).load(photoAlbum.getCoverUrl()).into(holder.albumCover);
//                Glide.with(HomeActivity.this).load(NetWorkConfig.getHttpApiPath() + photoAlbum.getCoverUrl()).into(holder.albumCover);
            } else {
                holder.albumCover.setImageResource(R.drawable.photo);
            }
            if (photoAlbum.getType() == 1) {
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
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MyLongPressDialog dialog = new MyLongPressDialog(HomeActivity.this);
                    dialog.setPositiveClickListener(new MyLongPressDialog.IMyClickListener() {
                        @Override
                        public void onClick() {
                            PhotoAlbumManager.getInstance().deleteAlbumRequest(String.valueOf(photoAlbum.getId()), new INetWorkListener() {
                                @Override
                                public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                                    String jsonStr = responseInfo.result.toString();
                                    if (1 == PhotoAlbumManager.getInstance().parserDeleteAlbum(jsonStr)) {
                                        //TODO 删除后返回所有的相册信息
//                                        adapter.setData();
//                                        adapter.notifyDataSetChanged();
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
            return photoAlbumsList.size();
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

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }

}
