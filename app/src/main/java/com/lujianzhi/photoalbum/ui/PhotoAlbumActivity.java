package com.lujianzhi.photoalbum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.adapter.SpacesItemDecoration;
import com.lujianzhi.photoalbum.config.NetWorkConfig;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.GlideLoader;
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.utils.ToastUtils;
import com.lujianzhi.photoalbum.view.MyLongPressDialog;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumActivity extends BaseActivity {
    private static final String TAG = PhotoAlbumActivity.class.getName();

    private RecyclerView photosView;
    private String albumName;
    private int albumId;
    private PhotoRVAdapter adapter;
    private boolean isMe;

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
        photosView = (RecyclerView) findViewById(R.id.photos);
        photosView.setLayoutManager(new GridLayoutManager(this, 4));
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        photosView.addItemDecoration(decoration);
        adapter = new PhotoRVAdapter();
        photosView.setAdapter(adapter);

    }

    @Override
    protected void initBottomViews() {
        LinearLayout bottom = (LinearLayout) findViewById(R.id.bottom);
        ImageView back = (ImageView) bottom.findViewById(R.id.back);
        ImageView add = (ImageView) bottom.findViewById(R.id.add);
        ImageView comment = (ImageView) bottom.findViewById(R.id.comment);
        comment.setVisibility(View.GONE);
        back.setOnClickListener(getOnClickListener());
        add.setOnClickListener(getOnClickListener());
        if (isMe) {
            add.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initIntentData() {
        Bundle data = getIntent().getBundleExtra("data");
        albumName = data.getString("albumName");
        albumId = data.getInt("albumId");
        isMe = data.getBoolean("isMe", false);
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
        /**
         * 初始化图片选择器
         */
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.color11))
                .titleBgColor(getResources().getColor(R.color.color11))
                .titleSubmitTextColor(getResources().getColor(R.color.color5))
                .titleTextColor(getResources().getColor(R.color.color5))
                // 开启多选   （默认为多选）
                .mutiSelect()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(9)
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/PhotoAlbum/Pictures")
                .requestCode(ImageSelector.IMAGE_REQUEST_CODE)
                .build();
        ImageSelector.open(PhotoAlbumActivity.this, imageConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //照片路径
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (final String path : pathList) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Log.i(TAG, path);
                            String[] pics = path.split("/");
                            LogUtils.i(TAG, pics[pics.length - 1]);
                            File file = new File(path);
                            PhotoAlbumManager.getInstance().addPhotoRequest(String.valueOf(albumId), file, pics[pics.length - 1], new INetWorkListener() {
                                @Override
                                public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                                    String resultStr = responseInfo.result.toString();

                                    if (PhotoAlbumManager.getInstance().parserAddPhoto(resultStr) == 1) {
                                        //TODO 上传成功时，只需要返回刚才新添加的照片信息，而不是所有已有照片信息
                                        adapter.addData(PhotoAlbumManager.getInstance().parserSinglePhoto(resultStr));
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        ToastUtils.showShortToast("保存相片失败");
                                    }
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    LogUtils.i(TAG, " photo/upload.do error-msg : " + msg);
                                }
                            });
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add:
                addPhoto();
                break;
            default:
                break;
        }
    }


    public class PhotoRVAdapter extends RecyclerView.Adapter<PhotoRVAdapter.MyViewHolder> {

        public void addData(Photo photo) {
            PhotoAlbumManager.getInstance().getPhotos().add(photo);
        }

        public void setData(List<Photo> photos) {
            PhotoAlbumManager.getInstance().setPhotos((ArrayList<Photo>) photos);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Photo photo = PhotoAlbumManager.getInstance().getPhotos().get(position);
            if (!"null".equals(photo.getPhotoUrl())) {
                Glide.with(PhotoAlbumActivity.this).load(NetWorkConfig.getHttpApiPath() + photo.getPhotoUrl()).into(holder.photoImage);
            } else {
                holder.photoImage.setImageResource(R.drawable.photo);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PhotoAlbumActivity.this, PhotoActivity.class);
                    Bundle data = new Bundle();
                    data.putParcelableArrayList("photoList", PhotoAlbumManager.getInstance().getPhotos());
                    data.putInt("position", holder.getAdapterPosition());
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            });

            if (isMe) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        MyLongPressDialog dialog = new MyLongPressDialog(PhotoAlbumActivity.this);
                        dialog.setDeleteVisisble();
                        dialog.setCoverVisisble();
                        dialog.setSetCoverClickListener(new MyLongPressDialog.IMyClickListener() {
                            @Override
                            public void onClick() {
                                PhotoAlbumManager.getInstance().setCoverRequest(String.valueOf(photo.getPhotoUrl()), albumId, new INetWorkListener() {
                                    @Override
                                    public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                                        String jsonStr = responseInfo.result.toString();
                                        PhotoAlbumManager.getInstance().parseCoverUrl(jsonStr);
                                    }

                                    @Override
                                    public void onFailure(HttpException error, String msg) {

                                    }
                                });
                            }
                        });
                        dialog.setDeleteClickListener(new MyLongPressDialog.IMyClickListener() {
                            @Override
                            public void onClick() {
                                PhotoAlbumManager.getInstance().deletePhotoRequest(String.valueOf(photo.getId()), String.valueOf(photo.getBelongId()), new INetWorkListener() {
                                    @Override
                                    public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                                        String jsonStr = responseInfo.result.toString();
                                        if (PhotoAlbumManager.getInstance().parserDeleteAlbum(jsonStr) == 1) {
                                            PhotoAlbumManager.getInstance().clearPhotoAlbum();
                                            adapter.setData(PhotoAlbumManager.getInstance().parserAllDeletePhoto2(jsonStr));
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

        }

        @Override
        public int getItemCount() {
            return PhotoAlbumManager.getInstance().getPhotos().size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView photoImage;

            public MyViewHolder(View itemView) {
                super(itemView);
                photoImage = (ImageView) itemView.findViewById(R.id.photo);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PhotoAlbumManager.getInstance().getAllPhoto(String.valueOf(albumId), new INetWorkListener() {
            @Override
            public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                String resultStr = responseInfo.result.toString();
                PhotoAlbumManager.getInstance().clearPhoto();
                adapter.setData(PhotoAlbumManager.getInstance().parserAllPhoto(resultStr));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

}
