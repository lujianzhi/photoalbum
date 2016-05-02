package com.lujianzhi.photoalbum.config;

/**
 * Created by lujianzhi on 2016/1/21.
 */
public class NetWorkConfig {
    /**
     * 协议名
     */
    public static final String policyName = "http://";

    /**
     * 电脑ip
     */
    public static final String ip = "192.168.1.110/";

    /**
     * 中间路径
     */
    public static final String path = "NetPhoto";

    public static String getHttpApiPath() {
        return policyName + ip + path;
    }

}
