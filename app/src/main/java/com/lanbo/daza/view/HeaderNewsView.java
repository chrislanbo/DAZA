package com.lanbo.daza.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.lanbo.daza.R;
import com.lanbo.daza.model.NewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wumeng051 on 2017/7/6.
 * 新闻
 */

public class HeaderNewsView extends AbsHeaderView<List<NewsEntity>>{

    @BindView(R.id.lv_news)
    FixedListView lvNews;

    public HeaderNewsView(Activity activity) {
        super(activity);
    }

    @Override
    protected void getView(List<NewsEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_news_layout, listView, false);
        ButterKnife.bind(this, view);

        handleView(list);
        listView.addHeaderView(view);
    }

    private void handleView(final List<NewsEntity> list) {
        if (list == null) return;
//        int size = list.size();

//        final HeaderGoodsAdapter adapter = new HeaderGoodsAdapter(mActivity,list);
//        lvNews.setAdapter(adapter);

    }
}
