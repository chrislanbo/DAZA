package com.lanbo.daza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

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

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束跳转主页面
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.overridePendingTransition(0, 0);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
