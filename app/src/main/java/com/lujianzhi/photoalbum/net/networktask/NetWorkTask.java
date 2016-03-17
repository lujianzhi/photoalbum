package com.lujianzhi.photoalbum.net.networktask;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.utils.ToastUtils;

/**
 * Created by lujianzhi on 2016/3/16.
 */
public abstract class NetWorkTask {

    private INetWorkListener netWorkListener;

    public void sendHttpRequest() {
        HttpUtils httpUtils = new HttpUtils(5000);
        httpUtils.send(getHttpMethod(), getUrl(), getRequestParams(), getRequestCallBack());
    }

    public abstract HttpRequest.HttpMethod getHttpMethod();

    public abstract String getUrl();

    public abstract RequestParams getRequestParams();

    public void setNetWorkListener(INetWorkListener netWorkListener){
        this.netWorkListener = netWorkListener;
    }

    public <T> RequestCallBack<T> getRequestCallBack() {
        RequestCallBack<T> requestCallBack = new RequestCallBack<T>() {
            @Override
            public void onSuccess(ResponseInfo<T> responseInfo) {
                if (netWorkListener != null) {
                    netWorkListener.onSuccess(responseInfo);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                if (netWorkListener != null) {
                    netWorkListener.onFailure(error, msg);
                }
                ToastUtils.showShortToast(R.string.net_request_failure);
            }
        };
        return requestCallBack;
    }
}
