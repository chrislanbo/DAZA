package com.lanbo.daza.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.lanbo.daza.R;

/**
 * Created by wumeng051 on 2017/7/6.
 * 头布局广告
 */

public class HeaderAdView  extends AbsHeaderView<String>{
    public HeaderAdView(Activity activity) {
        super(activity);
    }

    @Override
    protected void getView(String s, ListView listView) {
        View view = mInflate.inflate(R.layout.header_ad_layout, listView, false);
        listView.addHeaderView(view);
    }
}
