package com.lanbo.daza.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanbo.daza.R;
import com.lanbo.daza.model.NewsEntity;
import com.lanbo.daza.utils.ToastUtil;
import com.lanbo.daza.view.GlideImageView.GlideImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/20.
 * 新闻适配器
 */
public class NewsAdapter extends BaseListAdapter<NewsEntity> {

    private boolean isNoData;
    public static final int ONE_SCREEN_COUNT = 8; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定
    public static final int ONE_REQUEST_COUNT = 10; // 一次请求的个数

    public NewsAdapter(Context context) {
        super(context);
    }

    public NewsAdapter(Context context, List<NewsEntity> list) {
        super(context, list);
    }

    // 设置数据
    public void setData(List<NewsEntity> list) {
        clearAll();
        addALL(list);

        if (list.size() < ONE_SCREEN_COUNT) {
            addALL(createEmptyList(ONE_SCREEN_COUNT - list.size()));
        }

        notifyDataSetChanged();
    }

    // 创建不满一屏的空数据
    public List<NewsEntity> createEmptyList(int size) {
        List<NewsEntity> emptyList = new ArrayList<>();
        if (size <= 0) return emptyList;
        for (int i=0; i<size; i++) {
            emptyList.add(new NewsEntity());
        }
        return emptyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            RelativeLayout rootView = ButterKnife.findById(convertView, R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

        // 正常数据
        final ViewHolder holder;
        if (convertView != null && convertView instanceof LinearLayout) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        NewsEntity entity = getItem(position);

        holder.pic.loadNetImage(entity.getImgUrl(), R.color.font_black_6);
        holder.tv_title.setText(entity.getNews_title());
        holder.tv_des.setText(entity.getNews_des());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(mContext, "111");
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.giv_event_img)
        GlideImageView pic;
        @BindView(R.id.tv_event_title)
        TextView tv_title;
        @BindView(R.id.tv_event_des)
        TextView tv_des;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
