package com.lujianzhi.photoalbum.net;

import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.net.network.PhotoAlbumNetWork;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.net.parser.PhotoAlbumParser;

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

    public int parserAddAlbum(String jsonStr){
        return photoAlbumParser.parserAddAlbum(jsonStr);
    }

    public void addPhotoAlbumRequest(String name, int type, INetWorkListener netWorkListener) {
        photoAlbumNetWork.addAlbumRequest(name, type, netWorkListener);
    }

    public int parseRegisterStatues(String jsonStr) {
        return photoAlbumParser.parseRegisterStatues(jsonStr);
    }

    public void registerRequest(String userName, String password, INetWorkListener netWorkListener) {
        photoAlbumNetWork.registerRequest(userName, password, netWorkListener);
    }

    public int parserLogin(String jsonStr) {
        return photoAlbumParser.parseLoginStatues(jsonStr);
    }

    public void loginRequest(String userName, String password, INetWorkListener netWorkListener) {
        photoAlbumNetWork.login(userName, password, netWorkListener);
    }

}
