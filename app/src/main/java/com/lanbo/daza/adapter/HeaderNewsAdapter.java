package com.lanbo.daza.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanbo.daza.R;
import com.lanbo.daza.model.NewsEntity;
import com.lanbo.daza.view.GlideImageView.GlideImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻适配器
 */
public class HeaderNewsAdapter extends BaseListAdapter<NewsEntity> {

    public HeaderNewsAdapter(Context context, List<NewsEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsEntity entity = getItem(position);

        holder.tvTitle.setText(entity.getNews_des());
        holder.givImage.loadNetCircleImage(entity.getImgUrl(), R.color.font_black_6);

        if (TextUtils.isEmpty(entity.getImgUrl())) {
            holder.tvTips.setVisibility(View.INVISIBLE);
        } else {
            holder.tvTips.setVisibility(View.VISIBLE);
            holder.tvTips.setText(entity.getNews_des());
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.giv_image)
        GlideImageView givImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_tips)
        TextView tvTips;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
