package com.lujianzhi.photoalbum.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.lujianzhi.photoalbum.adapter.SpacesItemDecoration;
import com.lujianzhi.photoalbum.config.NetWorkConfig;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.utils.ToastUtils;
import com.lujianzhi.photoalbum.view.MyConfirmDialog;
import com.lujianzhi.photoalbum.view.MyLongPressDialog;

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
        photosView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
        back.setOnClickListener(getOnClickListener());
        add.setOnClickListener(getOnClickListener());
        comment.setOnClickListener(getOnClickListener());
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
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PhotoAlbumActivity.SYSTEM_GARRLY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            MyConfirmDialog dialog = new MyConfirmDialog(PhotoAlbumActivity.this);
            dialog.setPositiveClickListener(new MyConfirmDialog.IMyClickListener() {
                @Override
                public void onClick() {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        String[] pics = picturePath.split("/");
                        File file = new File(picturePath);

                        PhotoAlbumManager.getInstance().addPhotoRequest(String.valueOf(albumId), file, pics[pics.length - 1], new INetWorkListener() {
                            @Override
                            public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                                String resultStr = responseInfo.result.toString();
                                LogUtils.i(TAG, " photo/upload.do : " + resultStr);

                                if (PhotoAlbumManager.getInstance().parserAddPhoto(resultStr) == 1) {
                                    ToastUtils.showShortToast("保存相片成功");
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
                        cursor.close();
                    }

                }
            });
            dialog.show();
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

        public void clearData() {
            PhotoAlbumManager.getInstance().clearPhoto();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
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
                    data.putInt("position", position);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MyLongPressDialog dialog = new MyLongPressDialog(PhotoAlbumActivity.this);
                    dialog.setConfirmText(R.string.photoAlbum_set_cover);
                    dialog.setPositiveClickListener(new MyLongPressDialog.IMyClickListener() {
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
                    dialog.show();
                    return false;
                }
            });

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
                LogUtils.i(TAG, " photo/findAll.do : " + resultStr);
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
