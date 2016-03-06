package com.lujianzhi.photoalbum.net.network;

import android.content.Context;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumNetWork {

    public void addPhotoAlbum(final Context context, int id, String tableName, boolean isPublic, int type) {
        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setId(id);
        photoAlbum.setName(tableName);
        photoAlbum.setIsPublic(isPublic);
        photoAlbum.setType(type);
        photoAlbum.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, getText(context, R.string.add_photo_album_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, getText(context, R.string.add_photo_album_success), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getText(Context context, int strId) {
        return context.getResources().getText(strId).toString();
    }

}
