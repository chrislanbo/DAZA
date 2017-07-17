package com.lanbo.daza.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.lanbo.daza.R;

/**
 * 头布局-公告牌
 */
public class HeaderBillboardView extends AbsHeaderView<String> {

    public HeaderBillboardView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(String s, ListView listView) {
        View view = mInflate.inflate(R.layout.header_billboard_layout, listView, false);
        listView.addHeaderView(view);
    }

}
