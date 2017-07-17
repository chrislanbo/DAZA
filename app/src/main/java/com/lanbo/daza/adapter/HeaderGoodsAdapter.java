package com.lanbo.daza.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanbo.daza.R;
import com.lanbo.daza.model.GoodsEntity;
import com.lanbo.daza.view.GlideImageView.GlideImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品适配器 , 添加构造函数，传递布局id
 */
public class HeaderGoodsAdapter extends BaseListAdapter<GoodsEntity> {

    private int resourceID = R.layout.item_goods;
    private List<GoodsEntity> list;
    private boolean isGrid = false;

    public HeaderGoodsAdapter(Context context, List<GoodsEntity> list) {
        super(context, list);
        isGrid = false;
    }

    public HeaderGoodsAdapter(Context context, List<GoodsEntity> list, int resourceID) {
        super(context, list);
        this.resourceID = resourceID;
        this.list = list;
        isGrid = true;
    }

    @Override
    public void add(GoodsEntity item) {
        if(list!=null && list.size() % 2 != 0 ){
            Log.i("ADAPTER","当前为奇数");
            super.add(item);
        }else {
            Log.i("ADAPTER","当前为偶数");
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(resourceID, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GoodsEntity entity = getItem(position);

        holder.givImage.loadNetImage(entity.getImgUrl(), R.color.font_black_6);
        holder.tvPrice.setText(entity.getPrice());
        holder.tvLinkUrl.setText(entity.getLink_no_host());
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG" , "添加" + position+
                        "到购物车");
            }
        });

        if (TextUtils.isEmpty(entity.getGoods_name())) {
            holder.tvTitle.setText("暂无商品名称");
            if(isGrid)
            holder.linearLayout.setVisibility(View.INVISIBLE);
        } else {
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(entity.getGoods_name());
        }

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.giv_img)
        GlideImageView givImage;
        @BindView(R.id.tv_goods_name)
        TextView tvTitle;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_link_url)
        TextView tvLinkUrl;
        @BindView(R.id.iv_add_cart)
        ImageView ivAdd;
        @BindView(R.id.ll_goods_data)
        LinearLayout linearLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
