package com.lanbo.daza.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lanbo.daza.R;
import com.lanbo.daza.model.Cart;
import com.lanbo.daza.utils.CartProvider;

import java.util.List;

/**
 * Created by wumeng051 on 2017/7/24.
 *
 */

public class CartAdapter extends SimpleAdapter<Cart> implements BaseAdapter.OnItemClickListenner {
    private CheckBox mCheckBox;
    private TextView mTextView;

    private CartProvider cartProvider;

    public CartAdapter(Context context, List<Cart> datas) {
        super(context, datas, R.layout.item_goods_cart);
    }

    public CartAdapter(Context context, List<Cart> datas, CheckBox checkBox, TextView textView) {
        super(context, datas, R.layout.item_goods_cart);
        this.mCheckBox = checkBox;
        this.mTextView = textView;

        cartProvider = CartProvider.getInstance(context);
        setOnItemClickListenner(this);


    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void bindData(BaseViewHolder holder, Cart cart) {

    }
}
