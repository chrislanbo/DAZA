package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by wumeng051 on 2017/7/17.
 * 购物车bean
 */

public class Cart extends Goods implements Serializable{
    private int count;
    private boolean isChecked = true;


    public Cart(String id, String name, String imgUrl, String description, Float price) {
        super(id, name, imgUrl, description, price);
    }

    public Cart(String id, String name, String imgUrl, String description, Float price, int count, boolean isChecked) {
        super(id, name, imgUrl, description, price);
        this.count = count;
        this.isChecked = isChecked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
