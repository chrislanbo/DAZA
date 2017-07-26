package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by wumeng051 on 2017/7/17.
 * 商品bean
 */

public class Goods implements Serializable {
    private String id;
    private String name;
    private String imgUrl;
    private String description;
    private Float price;

    public Goods(String id, String name, String imgUrl, String description, Float price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
