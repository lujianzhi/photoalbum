package com.lujianzhi.photoalbum.net.network;

import android.content.Context;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lujianzhi.photoalbum.config.NetWorkConfig;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.net.networktask.NetWorkTask;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumNetWork {

    public void getAlbumsRequest(INetWorkListener netWorkListener){

    }

    public void addAlbumRequest(final String name, final int type, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "album/add.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("name", name);
                params.addBodyParameter("type", String.valueOf(type));
                return params;
            }
        };
        netWorkTask.sendHttpRequest();
        netWorkTask.setNetWorkListener(netWorkListener);
    }

    public void login(final String userName, final String password, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "login/login.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("userName", userName);
                params.addBodyParameter("password", password);
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void registerRequest(final String userName, final String password, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "login/register.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("userName", userName);
                params.addBodyParameter("password", password);
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void addPhotoAlbum(INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/";
            }

            @Override
            public RequestParams getRequestParams() {
                // 需要的参数
                return null;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }


    private String getText(Context context, int strId) {
        return context.getResources().getText(strId).toString();
    }

}
