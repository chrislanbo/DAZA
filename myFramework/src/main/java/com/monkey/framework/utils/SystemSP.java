/*
 *SystemSP.java
 *Classes：com.bdl.sjsd.utils.SystemSP
 *wangxiaojun Create at 2016-3-25 下午12:15:47	
 */
package com.monkey.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description:
 * com.bdl.sjsd.utils.SystemSP
 *
 * @author wangxiaojun
 * @version $Revision: 1.0 $
 * @email 45960894@hotmail.com
 * @date: 2016-3-25
 * @time: 下午12:15:47
 */
public class SystemSP {

    private static SharedPreferences prefs;

    public static void newInstance(Context context) {
        prefs = context.getSharedPreferences("system",
                Context.MODE_PRIVATE);
    }

    public static SharedPreferences getInstance() {
        return prefs;
    }

    public static void setBooleanValue(String key, boolean b) {
        prefs.edit().putBoolean(key, b).commit();
    }

    public static boolean getBooleanValue(String key) {
        return prefs.getBoolean(key, false);
    }

    public static void setStringValue(String key, String b) {
        prefs.edit().putString(key, b).commit();
    }

    public static String getStringValue(String key) {
        return prefs.getString(key, "");
    }

    public static void setIntValue(String key, int b) {
        prefs.edit().putInt(key, b).commit();
    }

    public static int getIntValue(String key) {
        return prefs.getInt(key, -1);
    }

    /**
     * 清除保存的数据
     */
    public static void clearData(String key) {
        prefs.edit().remove(key).commit();
    }
}
