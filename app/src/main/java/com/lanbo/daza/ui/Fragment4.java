package com.lanbo.daza.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanbo.daza.Constant;
import com.lanbo.daza.MyApplication;
import com.lanbo.daza.R;
import com.lanbo.daza.utils.PreferencesUtils;
import com.lanbo.daza.view.GlideImageView.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment4 extends Fragment {

    private static final String TAG = Fragment4.class.getSimpleName();


    @BindView(R.id.iv_icon_head)
    GlideImageView civ;
    @BindView(R.id.ll_login_info)
    LinearLayout ll_login_info;
    @BindView(R.id.tv_no_login)
    TextView tv_no_login;
    @BindView(R.id.tv_setting)
    TextView tv_setting;
    @BindView(R.id.user_info_notice_fans)
    TextView tv_num_all_order;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_code)
    TextView tv_code;

    private Context mContext;
    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView"+MyApplication.isLogin());
        this.mActivity = getActivity();
        this.mContext = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_page_mine, null);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        String pic_head = PreferencesUtils.getString(mContext, Constant.INFO_HEAD_PIC);
        String username = PreferencesUtils.getString(mContext, Constant.INFO_USERNAME);
        String code = PreferencesUtils.getString(mContext, Constant.INFO_CODE);
        String all_order = PreferencesUtils.getString(mContext, Constant.myOrder[3]);
        Log.i(TAG,""+pic_head);
        civ.loadNetCircleImage(pic_head,R.color.font_black_6);
        tv_no_login.setVisibility(View.GONE);
        ll_login_info.setVisibility(View.VISIBLE);
        tv_code.setText(code);
        tv_username.setText(username);
        tv_num_all_order.setVisibility(View.VISIBLE);
        tv_num_all_order.setText(all_order);

    }

}