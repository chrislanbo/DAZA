package com.monkey.framework.entity;

import android.text.TextUtils;

public class BaseResponse {
    // data部分的json
    private String json;
    // 是否返回成功
    private boolean flag;
    //错误提示信息
    private String message;

    public BaseResponse() {
        super();
        this.json = null;
        this.flag = false;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        if (TextUtils.isEmpty(json)) {
            this.json = null;
        } else {
            this.json = json;
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
