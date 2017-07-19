package com.lanbo.daza.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.lanbo.daza.MyApplication;
import com.lanbo.daza.ui.LoginActivity;

/**
 * Created by wumeng051 on 2017/6/20.
 * 基础类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseViewInterface{
    private boolean _isVisible;
    private ProgressDialog _waitDialog;
    protected LayoutInflater mInflater;
    protected ActionBar mActionBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initToolbar();
        init();
    }

    private void initToolbar() {
    }

    public abstract View getToolbar();

    public abstract int getLayoutId();

    public abstract void init();

    public abstract void setToolbar();

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * 跳转
     * @param intent 目标意图
     * @param isNeedLogin 是否需要登录态
     */
    public void startActivity(Intent intent, boolean isNeedLogin) {

        if (isNeedLogin) {
            if(MyApplication.isLogin()){
                super.startActivity(intent);
            } else {
                MyApplication.getInstance().putIntent(intent); //存起来后面登录完成后跳转
                Intent i = new Intent(this, LoginActivity.class);
                super.startActivity(i);
            }
        } else {
            super.startActivity(intent);
        }
    }
}
