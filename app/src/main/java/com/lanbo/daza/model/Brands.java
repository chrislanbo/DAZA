package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by wumeng051 on 2017/7/27.
 */

public class Brands implements Serializable {
    private int brandIndex;
    private String brand;
    private String brandDes;

    public Brands(int brandIndex, String brand, String brandDes) {
        this.brandIndex = brandIndex;
        this.brand = brand;
        this.brandDes = brandDes;
    }

    public int getBrandIndex() {
        return brandIndex;
    }

    public void setBrandIndex(int brandIndex) {
        this.brandIndex = brandIndex;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandDes() {
        return brandDes;
    }

    public void setBrandDes(String brandDes) {
        this.brandDes = brandDes;
    }

    @Override
    public String toString() {
        return "Brands{" +
                "brandIndex=" + brandIndex +
                ", brand='" + brand + '\'' +
                ", brandDes='" + brandDes + '\'' +
                '}';
    }
}
