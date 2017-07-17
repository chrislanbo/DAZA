package com.monkey.framework.app;

import android.app.Application;

import com.monkey.framework.MyFramwork;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyFramwork.init(getApplicationContext());
        afterInject();
    }

    protected void afterInject(){

    }
}
