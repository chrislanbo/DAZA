package com.lanbo.daza;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.lanbo.daza.model.UserInfo;
import com.monkey.framework.app.BaseApplication;
import com.monkey.framework.utils.PersonelSP;

import java.util.HashMap;
import java.util.Map;

/**
 * Description TODO
 *
 * @author monkey
 * @email j_monkey@sina.cn
 * @Created at 2017/1/23
 */
public class MyApplication extends BaseApplication {

    private static Map<String, String> header = new HashMap<>();
    private static MyApplication mInstance;
    private UserInfo userInfo;

    public static MyApplication getInstance() {
        if (mInstance == null)
            mInstance = new MyApplication();
        return mInstance;
    }

    @Override
    protected void afterInject() {
        super.afterInject();
        header.put("APPM2M", "1");
        PersonelSP.newInstance(this,"user");
    }

    public static void saveUser(String phone, String psd) {
        PersonelSP.setStringValue("phone", phone);
        PersonelSP.setStringValue("psd", psd);
    }

    public static void setToken(String token) {
        Log.i("token存储",": "+token);
        header.put("token", token);
    }

    public static Map<String, String> getHeader() {
        return header;
    }

    public static void quit() {
        PersonelSP.clearData("phone");
        PersonelSP.clearData("psd");
        header.remove("token");
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(header.get("token"));
    }

    private Intent intent;

    /**
     * 保存登录意图
     */
    public void putIntent(Intent intent) {
        this.intent = intent;
    }

    /**
     * 获取登录意图
     */
    public Intent getIntent() {
        return this.intent;
    }
}
