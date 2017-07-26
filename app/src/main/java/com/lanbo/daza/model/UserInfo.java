package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by wumeng051 on 2017/7/17.
 * 商品bean
 */

public class UserInfo implements Serializable {
    private String head_pic_url;
    private String username;
    private String sex;
    private String phone;
    private String weixin;
    private String code;

    public UserInfo(String head_pic_url, String username, String sex, String phone, String weixin, String code) {
        this.head_pic_url = head_pic_url;
        this.username = username;
        this.sex = sex;
        this.phone = phone;
        this.weixin = weixin;
        this.code = code;
    }

    public String getHead_pic_url() {
        return head_pic_url;
    }

    public void setHead_pic_url(String head_pic_url) {
        this.head_pic_url = head_pic_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "head_pic_url='" + head_pic_url + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", weixin='" + weixin + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
