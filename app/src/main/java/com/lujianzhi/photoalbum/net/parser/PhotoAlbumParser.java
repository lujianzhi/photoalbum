package com.lujianzhi.photoalbum.net.parser;

import android.content.Context;
import android.widget.Toast;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
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

    public ArrayList<PhotoAlbum> getPhotoAlbum(Context context) {
        findPhotoAlbum(context);
        return photoAlbums;
    }

    /**
     * 如果您使用了setTableName方法来自定义表名，那么在对该表进行数据查询的时候必须使用以下方法。
     * 需要注意的是查询的结果是JSONArray,需要自行解析JSONArray中的数据。
     */
    private void findPhotoAlbum(final Context context) {
        BmobQuery query = new BmobQuery("album");
        query.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    Toast.makeText(context, "查找相册信息成功", Toast.LENGTH_SHORT).show();
                    photoAlbums = parsePhotoAlbum(jsonArray);
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
                photoAlbum.setId(photoAlbumsObj.getInt("id"));
                photoAlbum.setName(photoAlbumsObj.getString("name"));
                photoAlbum.setType(photoAlbumsObj.getInt("type"));
                photoAlbum.setCoverUrl(photoAlbumsObj.getString("coverUrl"));
                list.add(photoAlbum);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
