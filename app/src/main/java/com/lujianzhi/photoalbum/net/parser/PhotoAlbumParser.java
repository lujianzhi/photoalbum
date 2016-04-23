package com.lujianzhi.photoalbum.net.parser;

import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.entity.PhotoAlbum;
import com.lujianzhi.photoalbum.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumParser extends BaseParser {

    private ArrayList<PhotoAlbum> photoAlbums = new ArrayList<PhotoAlbum>();
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public int parserAddAlbum(String jsonStr){
        int code = parseCode(jsonStr);
        ToastUtils.showShortToast(parseMessage(jsonStr));
        return code;
    }

    public int parseRegisterStatues(String jsonStr) {
        int code = parseCode(jsonStr);
        ToastUtils.showShortToast(parseMessage(jsonStr));
        return code;
    }

    public int parseLoginStatues(String jsonStr) {
        int code = parseCode(jsonStr);
        ToastUtils.showShortToast(parseMessage(jsonStr));
        return code;
    }

}
