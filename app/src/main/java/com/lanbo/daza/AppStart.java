package com.lanbo.daza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.android.volley.Response;
import com.lanbo.daza.ui.MainActivity2;
import com.lanbo.daza.utils.RequestUtil;
import com.monkey.framework.entity.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by wumeng051 on 2017/6/19.
 *
 */

public class AppStart extends AppCompatActivity implements Animation.AnimationListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//在setContextView后设置屏幕方向
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(layoutResID, 0.9f, 1f, 2000);
    }

    /**
     *
     * @param layoutResID 布局文件
     * @param fromAlpha 起始透明度
     * @param toAlpha 最终透明度
     * @param durationMillis 动画持续时间
     */
    public void setContentView(int layoutResID, float fromAlpha, float toAlpha, long durationMillis) {
        final View view = View.inflate(this, layoutResID, null);
        setContentView(view);

        AlphaAnimation anim = new AlphaAnimation(fromAlpha, toAlpha);
        anim.setDuration(durationMillis);
        anim.setAnimationListener(this);
        view.startAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        login();
    }

    private void login() {
        Log.i("login",": 跳转登录");
        Map<String, String> map = RequestUtil.getMap();
        map.put("mobile", "13965687235");
        map.put("password", "imwumeng");
        RequestUtil.login(map, new Response.Listener<BaseResponse>() {
            @Override
            public void onResponse(BaseResponse response) {
                if (response.isFlag()) {
                    try {
                        Log.i("login",": 登录成功");
                        JSONObject jsonObject = new JSONObject(response.getJson());
                        MyApplication.setToken((String)jsonObject.opt("token"));
                    } catch (JSONException e) {
                        Log.i("login",": 登录失败");
                        e.printStackTrace();
                    }
                }
            }
        }, null);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束跳转主页面
        Intent intent = new Intent(this, MainActivity2.class);
//        Intent intent = new Intent(this, com.lanbo.daza.view.gradationscroll.MainActivity.class);
        this.startActivity(intent);
        this.overridePendingTransition(0, 0);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
