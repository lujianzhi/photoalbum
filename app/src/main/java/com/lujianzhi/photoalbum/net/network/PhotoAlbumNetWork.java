package com.lujianzhi.photoalbum.net.network;

import android.content.Context;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lujianzhi.photoalbum.config.NetWorkConfig;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.net.networktask.NetWorkTask;

import java.io.File;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbumNetWork {

    public void getAllPhoto(final String belongId, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/photo/findAll.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams requestParams = new RequestParams();
                requestParams.addBodyParameter("belongId", belongId);
                return requestParams;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void addPhotoRequest(final String belongId, final File file, final String photoName, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/photo/upload.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams requestParams = new RequestParams();
                requestParams.addHeader("enctype", "multipart/form-data");
                //TODO 多照片上传
                requestParams.addBodyParameter("belongId", belongId);
                requestParams.addBodyParameter("file", file);
                requestParams.addBodyParameter("name", photoName);
                return requestParams;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void deleteAlbumRequest(final String id, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/album/delete.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("id", id);
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void getAlbumsRequest(INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/album/findAll.do";
            }

            @Override
            public RequestParams getRequestParams() {
                return null;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void addAlbumRequest(final String name, final int type, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/album/add.do";
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
                return NetWorkConfig.getHttpApiPath() + "/login/login.do";
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
                return NetWorkConfig.getHttpApiPath() + "/login/register.do";
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

    private String getText(Context context, int strId) {
        return context.getResources().getText(strId).toString();
    }

}
