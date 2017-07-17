package com.lanbo.daza.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanbo.daza.R;
import com.lanbo.daza.model.FunctionEntity;
import com.lanbo.daza.view.GlideImageView.GlideImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/20.
 * 三行 模块icon
 */
public class HeaderFunctionAdapter extends BaseListAdapter<FunctionEntity> {

//    private static int[] images = {R.drawable.icon_01,R.drawable.icon_02,R.drawable.icon_03,R.drawable.icon_04,
//            R.drawable.icon_05,R.drawable.icon_06,R.drawable.icon_07,R.drawable.icon_08,R.drawable.icon_09,
//            R.drawable.icon_10,R.drawable.icon_11,R.drawable.icon_12,};
//    private static String[] texts = {"蜜拓蜜商城","金币商城","产品中心","在线开卡","代理风采","金牌讲师","公司简介","团队管理","门店查询","防伪扫描",
//            "我的家族","客服中心"};
    public HeaderFunctionAdapter(Context context, List<FunctionEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_home_girdview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FunctionEntity entity = getItem(position);
        holder.tvTitle.setText(entity.getTitle());
        holder.givImage.setImageResource(entity.getImage());

//        holder.tvTitle.setText(texts[position]);
//        holder.givImage.setImageResource(images[position]);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.giv_gridView_item)
        GlideImageView givImage;
        @BindView(R.id.tv_gridView_item)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
