package com.lujianzhi.photoalbum.net;

import android.content.Context;

import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.net.network.PhotoAlbumNetWork;
import com.lujianzhi.photoalbum.net.parser.PhotoAlbumParser;
import com.lujianzhi.photoalbum.ui.HomeActivity;
import com.lujianzhi.photoalbum.ui.PhotoAlbumActivity;

import java.util.ArrayList;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumManager {

    private volatile static PhotoAlbumManager instance;
    private PhotoAlbumParser photoAlbumParser;
    private PhotoAlbumNetWork photoAlbumNetWork;
    private ArrayList<PhotoAlbum> photoAlbums = new ArrayList<PhotoAlbum>();
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    private PhotoAlbumManager() {
        photoAlbumNetWork = new PhotoAlbumNetWork();
        photoAlbumParser = new PhotoAlbumParser();
    }

    public static PhotoAlbumManager getInstance() {
        if (instance == null) {
            synchronized (PhotoAlbumManager.class) {
                if (instance == null) {
                    instance = new PhotoAlbumManager();
                }
            }
        }
        return instance;
    }

    public void addPhotoAlbum(Context context, int id, String tableName, int type) {
        photoAlbumNetWork.addPhotoAlbum(context, id, tableName, type);
    }

    public void getPhotoAlbum(Context context,HomeActivity.PhotoAlbumAdapter adapter) {
        photoAlbumParser.getPhotoAlbum(context,adapter);
    }

    public void getPhoto(Context context,PhotoAlbumActivity.MyAdapter adapter,int albumId) {
        clearPhoto();
        photoAlbumParser.getPhoto(context, adapter, albumId);
    }

    private void clearPhoto() {
        photos.clear();
    }

}
