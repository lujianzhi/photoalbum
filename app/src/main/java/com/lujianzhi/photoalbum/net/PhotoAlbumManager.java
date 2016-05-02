package com.lujianzhi.photoalbum.net;

import com.lujianzhi.photoalbum.entity.Comment;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.net.network.PhotoAlbumNetWork;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.net.parser.PhotoAlbumParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumManager {

    private volatile static PhotoAlbumManager instance;
    private PhotoAlbumParser photoAlbumParser;
    private PhotoAlbumNetWork photoAlbumNetWork;

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

    public int parserDeleteComment(String jsonStr) {
        return photoAlbumParser.parserDeleteComment(jsonStr);
    }

    public void deleteCommentRequest(int commentId, INetWorkListener netWorkListener) {
        photoAlbumNetWork.deleteCommentRequest(commentId, netWorkListener);
    }

    public ArrayList<PhotoAlbum> getPhotoAlbums() {
        return photoAlbumParser.getPhotoAlbums();
    }

    public void setPhotoAlbums(ArrayList<PhotoAlbum> photoAlbums) {
        photoAlbumParser.setPhotoAlbums(photoAlbums);
    }

    public ArrayList<Photo> getPhotos() {
        return photoAlbumParser.getPhotos();
    }

    public void setPhotos(ArrayList<Photo> photos) {
        photoAlbumParser.setPhotos(photos);
    }

    public void clearPhoto() {
        photoAlbumParser.clearPhoto();
    }

    public void clearPhotoAlbum() {
        photoAlbumParser.clearPhotoAlbum();
    }

    public List<Comment> parserAllComment(String jsonStr) {
        return photoAlbumParser.parserAllComment(jsonStr);
    }

    public int parserComment(String jsonStr) {
        return photoAlbumParser.parserComment(jsonStr);
    }

    public void commitCommentRequest(String commitContent, int photoId, INetWorkListener netWorkListener) {
        photoAlbumNetWork.commitCommentRequest(commitContent, photoId, netWorkListener);
    }

    public int parseCoverUrl(String jsonStr) {
        return photoAlbumParser.parserCoverUrl(jsonStr);
    }

    public void setCoverRequest(String photoUrl, int albumId, INetWorkListener netWorkListener) {
        photoAlbumNetWork.setCoverRequest(photoUrl, albumId, netWorkListener);
    }

    public Photo parserSinglePhoto(String jsonStr) {
        return photoAlbumParser.parserSinglePhoto(jsonStr);
    }

    public List<Photo> parserAllPhoto(String jsonStr) {
        return photoAlbumParser.parserAllPhoto(jsonStr);
    }

    public void getAllPhoto(String belongId, INetWorkListener netWorkListener) {
        photoAlbumNetWork.getAllPhoto(belongId, netWorkListener);
    }

    public int parserAddPhoto(String jsonStr) {
        return photoAlbumParser.parserAddPhoto(jsonStr);
    }

    public void addPhotoRequest(String belongId, File file, String photoName, INetWorkListener netWorkListener) {
        photoAlbumNetWork.addPhotoRequest(belongId, file, photoName, netWorkListener);
    }

    public List<PhotoAlbum> parserAllDeletePhoto(String jsonStr) {
        return photoAlbumParser.parserAllDeletePhoto(jsonStr);
    }

    public int parserDeleteAlbum(String jsonStr) {
        return photoAlbumParser.parserDeleteAlbum(jsonStr);
    }

    public void deleteAlbumRequest(String id, INetWorkListener netWorkListener) {
        photoAlbumNetWork.deleteAlbumRequest(id, netWorkListener);
    }

    public PhotoAlbum parserSingleAlbum(String jsonStr) {
        return photoAlbumParser.parserSingleAlbum(jsonStr);
    }

    public List<PhotoAlbum> parserAllAlbum(String jsonStr) {
        return photoAlbumParser.parserAllAlbum(jsonStr);
    }

    public void getAlbumsRequest(INetWorkListener netWorkListener) {
        photoAlbumNetWork.getAlbumsRequest(netWorkListener);
    }

    public int parserAddAlbum(String jsonStr) {
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
