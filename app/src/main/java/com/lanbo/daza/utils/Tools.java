package com.lanbo.daza.utils;

/**
 * Created by wumeng051 on 2017/7/17.
 * 工具类
 */

public class Tools {

    /**
     * 给链接加上host
     * @param sourceUrl 原链接
     * @param host host地址
     * @return url地址
     */
    public static String fixUrl(String sourceUrl, String host) {
        if(!sourceUrl.startsWith("http")){
            sourceUrl = host + sourceUrl;
        }
        return sourceUrl;
    }
}
