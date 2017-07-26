package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by wumeng051 on 2017/7/18.
 * 地址
 * <p>
 * name = 拉拉
 * phone = 13612212121
 * province省 = 上海市
 * city市 = 市辖区
 * county区/县 = 黄浦区
 * detail = 上海黄埔军校
 * url = /Mobile/user/edit_address.html?id=12558
 * id = 12558
 */

public class Address implements Serializable {
    private String name; // 名字
    private String phone; // 手机号
    private String province;// 省直辖市
    private String city; // 市辖区
    private String county; // 区/县
    private String detail; // 具体地址
    private String url; // 链接
    private String id; // ID

    public Address(String name, String phone, String province, String city, String county, String detail, String url, String id) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.county = county;
        this.detail = detail;
        this.url = url;
        this.id = id;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", detail='" + detail + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
