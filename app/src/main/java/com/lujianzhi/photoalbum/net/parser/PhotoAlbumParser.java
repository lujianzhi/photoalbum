package com.lujianzhi.photoalbum.net.parser;

import android.content.Context;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.ui.HomeActivity;
import com.lujianzhi.photoalbum.ui.PhotoAlbumActivity;
import com.lujianzhi.photoalbum.utils.MyException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumParser {

    private ArrayList<PhotoAlbum> photoAlbums = new ArrayList<PhotoAlbum>();
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public void getPhotoAlbum(Context context, HomeActivity.PhotoAlbumAdapter adapter) {
        findPhotoAlbum(context, adapter);
    }

    /**
     * 如果您使用了setTableName方法来自定义表名，那么在对该表进行数据查询的时候必须使用以下方法。
     * 需要注意的是查询的结果是JSONArray,需要自行解析JSONArray中的数据。
     */
    private void findPhotoAlbum(final Context context, final HomeActivity.PhotoAlbumAdapter adapter) {
        BmobQuery query = new BmobQuery(PhotoAlbum.TABLENAME);
        query.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    photoAlbums = parsePhotoAlbum(jsonArray);
                    adapter.setData(photoAlbums);
                    adapter.notifyDataSetChanged();
                } catch (MyException e) {
                    e.showExceptionToast(context, R.string.photoAlbum_parse_error);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "查找相册信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<PhotoAlbum> parsePhotoAlbum(JSONArray jsonArray) throws MyException {
        ArrayList<PhotoAlbum> list = new ArrayList<PhotoAlbum>();
        JSONObject photoAlbumsObj;
        for (int i = 0, length = jsonArray.length(); i < length; i++) {
            try {
                photoAlbumsObj = (JSONObject) jsonArray.get(i);
                PhotoAlbum photoAlbum = new PhotoAlbum();
                if (photoAlbumsObj.has("id")) {
                    photoAlbum.setId(photoAlbumsObj.getInt("id"));
                }
                if (photoAlbumsObj.has("name")) {
                    photoAlbum.setName(photoAlbumsObj.getString("name"));
                }
                if (photoAlbumsObj.has("type")) {
                    photoAlbum.setType(photoAlbumsObj.getInt("type"));
                }
                if (photoAlbumsObj.has("coverUrl")) {
                    photoAlbum.setCoverUrl(photoAlbumsObj.getString("coverUrl"));
                }
                list.add(photoAlbum);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void getPhoto(Context context, PhotoAlbumActivity.MyAdapter adapter, int albumId) {
        findPhoto(context, adapter, albumId);
    }

    private void findPhoto(final Context context, final PhotoAlbumActivity.MyAdapter adapter, final int albumId) {
        BmobQuery query = new BmobQuery(Photo.TABLENAME);
        query.addWhereEndsWith("belongId", String.valueOf(albumId));
        query.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    photos = parsePhoto(jsonArray);
                    adapter.setData(photos);
                    adapter.notifyDataSetChanged();
                } catch (MyException e) {
                    e.showExceptionToast(context, R.string.photoAlbum_parse_error);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "查找相册信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Photo> parsePhoto(JSONArray jsonArray) throws MyException {
        ArrayList<Photo> list = new ArrayList<Photo>();
        JSONObject photoObj;
        for (int i = 0, length = jsonArray.length(); i < length; i++) {
            try {
                photoObj = (JSONObject) jsonArray.get(i);
                Photo photo = new Photo();
                if (photoObj.has("id")) {
                    photo.setId(photoObj.getInt("id"));
                }
                if (photoObj.has("name")) {
                    photo.setName(photoObj.getString("name"));
                }
                if (photoObj.has("photoUrl")) {
                    photo.setPhotoUrl(photoObj.getString("photoUrl"));
                }
                if (photoObj.has("comment")) {
                    photo.setComment(photoObj.getString("comment"));
                }
                list.add(photo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
