package com.lanbo.daza.model;

import java.io.Serializable;

/**
 *
 */
public class NewsEntity implements Serializable {


    private String link_no_host; // 跳转链接
    private String imgUrl; // 图片链接
    private String news_des; // 新闻简介

    public NewsEntity() {
    }

    public NewsEntity(String link_no_host, String imgUrl, String news_des) {
        this.link_no_host = link_no_host;
        this.imgUrl = imgUrl;
        this.news_des = news_des;
    }

    public String getLink_no_host() {
        return link_no_host;
    }

    public void setLink_no_host(String link_no_host) {
        this.link_no_host = link_no_host;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNews_des() {
        return news_des;
    }

    public void setNews_des(String news_des) {
        this.news_des = news_des;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "link_no_host='" + link_no_host + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", news_des='" + news_des + '\'' +
                '}';
    }
}