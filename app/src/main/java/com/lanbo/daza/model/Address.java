package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by wumeng051 on 2017/7/18.
 * 地址
 *
 name = 拉拉
 phone = 13612212121
 province省 = 上海市
 city市 = 市辖区
 county区/县 = 黄浦区
 detail = 上海黄埔军校
 url = /Mobile/user/edit_address.html?id=12558
 id = 12558
 */

public class Address implements Serializable {
    private String name ;
    private String phone ;
    private String province ;
    private String county ;
    private String detail ;
    private String url ;
    private String id ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
