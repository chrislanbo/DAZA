package com.lanbo.daza.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lanbo.daza.R;
import com.lanbo.daza.utils.ToastUtil;
import com.lanbo.daza.view.GlideImageView.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wumeng051 on 2017/7/18.
 * 个人信息
 */

public class UserInfoActivity extends AppCompatActivity{

    @BindView(R.id.iv_head_pic)
    GlideImageView glideImageView;
    @BindView(R.id.tv_info_username)
    TextView tv_username;
    @BindView(R.id.tv_info_code)
    TextView tv_code;
    @BindView(R.id.tv_info_phone)
    TextView tv_phone;
    @BindView(R.id.tv_info_sex)
    TextView tv_sex;
    @BindView(R.id.tv_info_weixin)
    TextView tv_weixin;
//    @BindView(R.id.ll_info_pic)
//    LinearLayout ll_pic;
//    @BindView(R.id.ll_info_username)
//    LinearLayout ll_username;
//    @BindView(R.id.ll_info_code)
//    LinearLayout ll_code;
//    @BindView(R.id.ll_info_phone)
//    LinearLayout ll_phone;
//    @BindView(R.id.ll_info_sex)
//    LinearLayout ll_sex;
//    @BindView(R.id.ll_info_weixin)
//    LinearLayout ll_weixin;
//    @BindView(R.id.ll_info_address)
//    LinearLayout ll_address;
//    @BindView(R.id.ll_info_reset_pwd)
//    LinearLayout ll_reset;
//    @BindView(R.id.rl_info_unlogin)
//    RelativeLayout rl_unlogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
    }

    private void initData() {

    }

    @OnClick({R.id.ll_info_pic,R.id.ll_info_username,R.id.ll_info_code,R.id.ll_info_phone,R.id.ll_info_sex,R.id.ll_info_weixin,R.id.ll_info_address,R.id.ll_info_reset_pwd,R.id.rl_info_unlogin})
    public void onClic(View v) {
        ToastUtil.show(this,""+v.getId());
    }
    @OnClick(R.id.ll_info_pic)
    public void onClic3(View v) {
        ToastUtil.show(this,""+v.getId());
    }
}
