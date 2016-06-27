package com.lujianzhi.photoalbum.net.parser;

import com.lujianzhi.photoalbum.entity.Comment;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.utils.MyUtil;
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

    public ArrayList<PhotoAlbum> getPhotoAlbums() {
        return photoAlbums;
    }

    public void setPhotoAlbums(ArrayList<PhotoAlbum> photoAlbums) {
        this.photoAlbums = photoAlbums;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public void clearPhoto() {
        photos.clear();
    }

    public void clearPhotoAlbum() {
        photoAlbums.clear();
    }

    public int parserEditInfo(String jsonStr) {
        ToastUtils.showShortToast(parseMessage(jsonStr));
        return parseCode(jsonStr);
    }

    public List<Photo> parserAllDeletePhoto2(String jsonStr) {
        photos.clear();
        String json = parseMessage(jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Photo photo = new Photo();
                JSONObject photoObj = jsonArray.getJSONObject(i);
                if (photoObj.has("comments") && !photoObj.isNull("comments")) {
                    JSONArray commentArray = photoObj.getJSONArray("comments");
                    ArrayList<Comment> comments = new ArrayList<>();
                    for (int j = 0; j < commentArray.length(); j++) {
                        JSONObject commentObj = commentArray.getJSONObject(j);
                        if (commentObj != null) {
                            Comment comment = new Comment();
                            comment.setContent(commentObj.getString("content"));
                            comment.setDate(MyUtil.getFormatDate(commentObj.getString("date")));
                            comment.setId(commentObj.getInt("id"));
                            comment.setName(commentObj.getString("userName"));
                            comment.setPhotoId(commentObj.getInt("photoId"));
                            comment.setUserId(commentObj.getInt("userId"));
                            comments.add(comment);
                        }
                    }
                    photo.setComment(comments);
                }
                photo.setId(Integer.valueOf(photoObj.getString("id")));
                photo.setBelongId(Integer.valueOf(photoObj.getString("belongId")));
                photo.setName(photoObj.getString("name"));
                photo.setPhotoUrl(photoObj.getString("photoUrl"));
                photo.setVote(photoObj.isNull("voteValue") ? 0.0 : photoObj.getDouble("voteValue"));
                photos.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public float parserVotePoint(String jsonStr) {
        return Float.valueOf(parseMessage(jsonStr));
    }

    public int parserVote(String jsonStr) {
        return parseCode(jsonStr);
    }

    public int parserComment(String jsonStr) {
        return parseCode(jsonStr);
    }

    public int parserCoverUrl(String jsonStr) {
        parseMessage(jsonStr);
        return parseCode(jsonStr);
    }

    public List<Comment> parserAllComment(String jsonStr) {
        List<Comment> comments = new ArrayList<>();
        String json = parseMessage(jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Comment comment = new Comment();
                JSONObject commentObj = jsonArray.getJSONObject(i);
                comment.setName(commentObj.getString("userName"));
                comment.setUserId(commentObj.getInt("userId"));
                comment.setPhotoId(commentObj.getInt("photoId"));
                comment.setContent(commentObj.getString("content"));
                comment.setDate(MyUtil.getFormatDate(commentObj.getString("date")));
                comment.setId(commentObj.getInt("id"));
                comments.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public List<PhotoAlbum> parserAllDeletePhoto(String jsonStr) {
        String json = parseMessage(jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                PhotoAlbum photoAlbum = new PhotoAlbum();
                JSONObject photoObj = jsonArray.getJSONObject(i);
                photoAlbum.setId(Integer.valueOf(photoObj.getString("id")));
                photoAlbum.setName(photoObj.getString("name"));
                photoAlbum.setType(Integer.valueOf(photoObj.getString("type")));
                photoAlbum.setCoverUrl(photoObj.getString("coverUrl"));
                photoAlbums.add(photoAlbum);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoAlbums;
    }

    public int parserDeleteAlbum(String jsonStr) {
        return parseCode(jsonStr);
    }

    public Photo parserSinglePhoto(String jsonStr) {
        String json = parseMessage(jsonStr);
        Photo photo = new Photo();
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject photoObj = jsonArray.getJSONObject(0);
            if (photoObj.has("comments") && !photoObj.isNull("comments")) {
                JSONArray commentArray = photoObj.getJSONArray("comments");
                ArrayList<Comment> comments = new ArrayList<>();
                for (int i = 0; i < commentArray.length(); i++) {
                    JSONObject commentObj = commentArray.getJSONObject(i);
                    if (commentObj != null) {
                        Comment comment = new Comment();
                        comment.setContent(commentObj.getString("content"));
                        comment.setDate(MyUtil.getFormatDate(commentObj.getString("date")));
                        comment.setId(commentObj.getInt("id"));
                        comment.setName(commentObj.getString("userName"));
                        comment.setPhotoId(commentObj.getInt("photoId"));
                        comment.setUserId(commentObj.getInt("userId"));
                        comments.add(comment);
                    }
                }
                photo.setComment(comments);
            }
            photo.setPhotoUrl(photoObj.getString("photoUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photo;
    }

    public List<Photo> parserAllPhoto(String jsonStr) {
        photos.clear();
        String json = parseMessage(jsonStr);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Photo photo = new Photo();
                JSONObject photoObj = jsonArray.getJSONObject(i);
                if (photoObj.has("comments") && !photoObj.isNull("comments")) {
                    JSONArray commentArray = photoObj.getJSONArray("comments");
                    ArrayList<Comment> comments = new ArrayList<>();
                    for (int j = 0; j < commentArray.length(); j++) {
                        JSONObject commentObj = commentArray.getJSONObject(j);
                        if (commentObj != null) {
                            Comment comment = new Comment();
                            comment.setContent(commentObj.getString("content"));
                            comment.setDate(MyUtil.getFormatDate(commentObj.getString("date")));
                            comment.setId(commentObj.getInt("id"));
                            comment.setName(commentObj.getString("userName"));
                            comment.setPhotoId(commentObj.getInt("photoId"));
                            comment.setUserId(commentObj.getInt("userId"));
                            comments.add(comment);
                        }
                    }
                    photo.setComment(comments);
                }
                photo.setId(Integer.valueOf(photoObj.getString("id")));
                photo.setBelongId(Integer.valueOf(photoObj.getString("belongId")));
                photo.setName(photoObj.getString("name"));
                photo.setPhotoUrl(photoObj.getString("photoUrl"));
                photo.setVote(photoObj.isNull("voteValue") ? 0.0 : photoObj.getDouble("voteValue"));
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

    public PhotoAlbum parserSingleAlbum(String jsonStr) {
        String json = parseMessage(jsonStr);
        PhotoAlbum photoAlbum = new PhotoAlbum();
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject photoAlbumObj = jsonArray.getJSONObject(0);
            photoAlbum.setCoverUrl(photoAlbumObj.getString("coverUrl"));
            photoAlbum.setId(photoAlbumObj.getInt("id"));
            photoAlbum.setName(photoAlbumObj.getString("name"));
            photoAlbum.setType(photoAlbumObj.getInt("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoAlbum;
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
