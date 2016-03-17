package com.lujianzhi.photoalbum.config;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class NetWorkConfig {
    /**
     * 协议名
     */
    public static final String policyName = "Http://";

    /**
     * 电脑ip
     */
    public static final String ip = "";

    /**
     * 中间路径
     */
    public static final String path = "";

    public static String getHttpApiPath(){
        return policyName+ip+path;
    }

}
