package com.lujianzhi.photoalbum.net.network;

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

    public void editInfoRequest(final String oldPassword, final String password, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/user/updatePassword.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("oldPassword", oldPassword);
                params.addBodyParameter("newPassword", password);
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void deletePhotoRequest(final String id, final String belongId, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/photo/delete.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("id", id);
                params.addBodyParameter("belongId", belongId);
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void voteRequest(final String photoId, final String belongId, final String vote, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/vote/vote.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("photoId", photoId);
                params.addBodyParameter("voteValue", vote);
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void deleteCommentRequest(final int commentId, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/comment/delete.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("id", String.valueOf(commentId));
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void commitCommentRequest(final String commentContent, final int photoId, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/comment/add.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("content", commentContent);
                params.addBodyParameter("photoId", String.valueOf(photoId));
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

    public void setCoverRequest(final String photoUrl, final int albumId, INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/album/setCover.do";
            }

            @Override
            public RequestParams getRequestParams() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("url", photoUrl);
                params.addBodyParameter("id", String.valueOf(albumId));
                return params;
            }
        };
        netWorkTask.setNetWorkListener(netWorkListener);
        netWorkTask.sendHttpRequest();
    }

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

    public void getFindAllUserAlbum(INetWorkListener netWorkListener) {
        NetWorkTask netWorkTask = new NetWorkTask() {
            @Override
            public HttpRequest.HttpMethod getHttpMethod() {
                return HttpRequest.HttpMethod.POST;
            }

            @Override
            public String getUrl() {
                return NetWorkConfig.getHttpApiPath() + "/album/findAllUserAlbum.do";
            }

            @Override
            public RequestParams getRequestParams() {
                return null;
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

}
