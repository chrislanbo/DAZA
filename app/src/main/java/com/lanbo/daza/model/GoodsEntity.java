package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * 商品和兑换商品
 */
public class GoodsEntity implements Serializable {

    private String link_no_host; // 商品跳转链接
    private String imgUrl; // 商品图片链接
    private String goods_id;  // 商品id
    private String goods_name;  // 商品名称
    private String price;   // 商品价格

    public GoodsEntity() {
    }

    public GoodsEntity(String link_no_host, String imgUrl, String goods_id, String goods_name, String price) {
        this.link_no_host = link_no_host;
        this.imgUrl = imgUrl;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.price = price;
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

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "link_no_host='" + link_no_host + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}