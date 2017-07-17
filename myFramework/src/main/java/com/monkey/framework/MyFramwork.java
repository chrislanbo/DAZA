package com.monkey.framework;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.monkey.framework.utils.SystemSP;
import com.monkey.framework.view.MyToast;


/**
 * 安卓简易辅助开发框架
 *
 * @author pan
 */
public class MyFramwork {
    private static Context appContext;
    public static boolean DEBUG = true;

    private MyFramwork() {
    }

    /**
     * 初始化
     *
     * @param appContext
     */
    public static void init(Context appContext) {
        MyFramwork.appContext = appContext;
        MyToast.init(appContext);
        SystemSP.newInstance(appContext);
    }

    public static Context getAppContext() {
        if (appContext == null)
            throw new IllegalStateException("MyFramwork not initialized");
        return appContext;
    }
}
