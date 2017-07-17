package com.lanbo.daza.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbo.daza.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wumeng051 on 2017/7/6.
 * 头布局标题
 */

public class HeaderTitleView extends AbsHeaderView<String>{


    @BindView(R.id.tv_title)
    TextView tv_title;


    private String title = "";
    public HeaderTitleView(Activity activity,String title) {
        super(activity);
        this.title = title;
    }

    @Override
    protected void getView(String s, ListView listView) {
        View view = mInflate.inflate(R.layout.header_title_layout, listView, false);
        ButterKnife.bind(this, view);
        tv_title.setText(title);
        listView.addHeaderView(view);
    }
}
