package com.lujianzhi.photoalbum.net.parser;

import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumParser extends BaseParser {

    private ArrayList<PhotoAlbum> photoAlbums = new ArrayList<PhotoAlbum>();
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public void clearPhoto() {
        photos.clear();
    }

    public int parserDeleteAlbum(String jsonStr) {
        return parseCode(jsonStr);
    }

    public List<Photo> parserAllPhoto(String jsonStr) {
        String json = parseMessage(jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Photo photo = new Photo();
                JSONObject photoObj = jsonArray.getJSONObject(i);
                //TODO 设置photo的评论和url
//                photo.setComment();
                photo.setPhotoUrl(photoObj.getString("photoUrl"));
                photos.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public int parserAddPhoto(String jsonStr) {
        return parseCode(jsonStr);
    }

    public List<PhotoAlbum> parserAllAlbum(String jsonStr) {
        photoAlbums.clear();
        String json = parseMessage(jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                PhotoAlbum photoAlbum = new PhotoAlbum();
                JSONObject photoAlbumObj = jsonArray.getJSONObject(i);
                photoAlbum.setCoverUrl(photoAlbumObj.getString("coverUrl"));
                photoAlbum.setId(photoAlbumObj.getInt("id"));
                photoAlbum.setName(photoAlbumObj.getString("name"));
                photoAlbum.setType(photoAlbumObj.getInt("type"));
                photoAlbums.add(photoAlbum);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoAlbums;
    }

    public int parserAddAlbum(String jsonStr) {
        ToastUtils.showShortToast(parseMessage(jsonStr));
        return parseCode(jsonStr);
    }

    public int parseRegisterStatues(String jsonStr) {
        ToastUtils.showShortToast(parseMessage(jsonStr));
        return parseCode(jsonStr);
    }

    public int parseLoginStatues(String jsonStr) {
        ToastUtils.showShortToast(parseMessage(jsonStr));
        return parseCode(jsonStr);
    }

}
