package com.lujianzhi.photoalbum.net;

import android.content.Context;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.https.network.PhotoAlbumNetWork;
import com.lujianzhi.photoalbum.https.parser.PhotoAlbumParser;

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

    public void addPhotoAlbum(Context context, int id, String tableName, boolean isPublic, int type) {
        photoAlbumNetWork.addPhotoAlbum(context, id, tableName, isPublic, type);
    }

    public ArrayList<PhotoAlbum> getPhtotAlbum(Context context) {
        /**
         * 相册假数据
         */
//        for (int i = 0; i < 5; i++) {
//            photoAlbums.add(new PhotoAlbum(i, "生活" + i, true, 1, "", i, "评论:还不错"));
//        }
        return photoAlbumParser.getPhotoAlbum(context);
    }

    public ArrayList<Photo> getPhoto() {
        clearPhoto();
        Photo photo1 = new Photo(1, "第1张照片", "1", "", R.drawable.img_1, "评论:帅气啊");
        Photo photo2 = new Photo(2, "第2张照片", "2", "", R.drawable.img_2, "评论:那是手机么请问");
        Photo photo3 = new Photo(3, "第3张照片", "3", "", R.drawable.img_3, "评论:萝莉诶");
        Photo photo4 = new Photo(4, "第4张照片", "4", "", R.drawable.splash_pic, "评论:这个是什么鬼");
        Photo photo5 = new Photo(5, "第5张照片", "5", "", R.drawable.img_5, "评论:好像很屌的样子");
        Photo photo6 = new Photo(6, "第6张照片", "6", "", R.drawable.img_6, "评论:妹子听什么歌呢");
        Photo photo7 = new Photo(7, "第7张照片", "7", "", R.drawable.img_7, "评论:冷不冷");
        Photo photo8 = new Photo(8, "第8张照片", "8", "", R.drawable.img_8, "评论:万年剪刀手");
        photos.add(photo1);
        photos.add(photo2);
        photos.add(photo3);
        photos.add(photo4);
        photos.add(photo5);
        photos.add(photo6);
        photos.add(photo7);
        photos.add(photo8);
        return photos;
    }

    private void clearPhoto() {
        photos.clear();
    }

}
