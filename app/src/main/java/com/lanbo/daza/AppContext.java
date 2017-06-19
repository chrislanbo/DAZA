package com.lanbo.daza;

import android.app.Application;
import android.content.Context;

/**
 * Created by wumeng051 on 2017/6/19.
 *
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
