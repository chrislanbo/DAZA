package com.lanbo.daza.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.lanbo.daza.R;

/**
 * 头布局分割线
 */
public class HeaderDividerView extends AbsHeaderView<String> {

    public HeaderDividerView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(String s, ListView listView) {
        View view = mInflate.inflate(R.layout.header_divider_layout, listView, false);
        listView.addHeaderView(view);
    }

}
