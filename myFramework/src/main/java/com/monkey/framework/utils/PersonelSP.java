/*
 *SystemSP.java
 *Classes：com.bdl.sjsd.utils.SystemSP
 *wangxiaojun Create at 2016-3-25 下午12:15:47	
 */
package com.monkey.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description: 个人sp
 *
 * @author wangxiaojun
 * @version $Revision: 1.0 $
 * @email 45960894@hotmail.com
 * @date: 2016-3-25
 * @time: 下午12:15:47`
 */
public class PersonelSP {

    private static SharedPreferences prefs;

    public static void newInstance(Context context, String name) {
        prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            try {
                throw new Exception("please init PersonelSP");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prefs;
    }

    public static void setBooleanValue(String key, boolean b) {
        getInstance().edit().putBoolean(key, b).commit();
    }

    public static boolean getBooleanValue(String key) {
        return getInstance().getBoolean(key, false);
    }

    public static void setStringValue(String key, String b) {
        getInstance().edit().putString(key, b).commit();
    }

    public static String getStringValue(String key) {
        return getInstance().getString(key, "");
    }

    public static String getStringValue(String key, String def) {
        return getInstance().getString(key, def);
    }


    public static void setIntValue(String key, int b) {
        getInstance().edit().putInt(key, b).commit();
    }

    public static int getIntValue(String key) {
        return getInstance().getInt(key, -1);
    }

    /**
     * 清除保存的数据
     */
    public static void clearData(String key) {
        getInstance().edit().remove(key).commit();
    }
}
