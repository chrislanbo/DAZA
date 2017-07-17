package com.lanbo.daza.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class FunctionEntity implements Serializable {

    private String title;
    private int image;

    public FunctionEntity(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}