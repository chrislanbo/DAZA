package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by wumeng051 on 2017/7/27.
 */

public class BrandsGoods implements Serializable {
    private int brandIndex;
    private String url;
    private String imgUrl;
    private String price;

    public BrandsGoods(int brandIndex, String url, String imgUrl, String price) {
        this.brandIndex = brandIndex;
        this.url = url;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public int getBrandIndex() {
        return brandIndex;
    }

    public void setBrandIndex(int brandIndex) {
        this.brandIndex = brandIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BrandsGoods{" +
                "brandIndex=" + brandIndex +
                ", url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
