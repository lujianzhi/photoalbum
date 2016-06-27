package com.lujianzhi.photoalbum.net.networktask;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.utils.LogUtils;
import com.lujianzhi.photoalbum.utils.ToastUtils;

/**
 * Created by lujianzhi on 2016/3/16.
 */
public abstract class NetWorkTask {

    private INetWorkListener netWorkListener;
    private HttpUtils httpUtils;
//    private PreferencesCookieStore cookieStore;

    public void sendHttpRequest() {
        httpUtils = new HttpUtils(10000);
        httpUtils.configCookieStore(MyCookieStore.cookieStore);
//        cookieStore = new PreferencesCookieStore(MyApplication.getMyApplicationContext());
//        httpUtils.configCookieStore(cookieStore);
        httpUtils.send(getHttpMethod(), getUrl(), getRequestParams(), getRequestCallBack());
    }

    public abstract HttpRequest.HttpMethod getHttpMethod();

    public abstract String getUrl();

    public abstract RequestParams getRequestParams();

    public void setNetWorkListener(INetWorkListener netWorkListener) {
        this.netWorkListener = netWorkListener;
    }

    public <T> RequestCallBack<T> getRequestCallBack() {
        RequestCallBack<T> requestCallBack = new RequestCallBack<T>() {
            @Override
            public void onSuccess(ResponseInfo<T> responseInfo) {

                if (netWorkListener != null) {
                    LogUtils.i("网络请求成功 : " + responseInfo.result.toString());
                    netWorkListener.onSuccess(responseInfo);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                if (netWorkListener != null) {
                    netWorkListener.onFailure(error, msg);
                }
                LogUtils.i("网络请求失败 : " + msg);
                ToastUtils.showShortToast(R.string.net_request_failure);
            }
        };
        return requestCallBack;
    }
}
