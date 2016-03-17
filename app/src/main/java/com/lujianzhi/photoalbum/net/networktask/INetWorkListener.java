package com.lujianzhi.photoalbum.net.networktask;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

/**
 * Created by lujianzhi on 2016/3/16.
 */
public interface INetWorkListener {
    <T> void onSuccess(ResponseInfo<T> responseInfo);

    void onFailure(HttpException error, String msg);
}
