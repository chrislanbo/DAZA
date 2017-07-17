package com.lanbo.daza.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lanbo.daza.R;
import com.lanbo.daza.adapter.HeaderGoodsAdapter;
import com.lanbo.daza.model.GoodsEntity;
import com.lanbo.daza.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wumeng051 on 2017/7/6.
 * 商品
 */

public class HeaderGoodsGirdView extends AbsHeaderView<List<GoodsEntity>>{

    @BindView(R.id.gv_goods)
    FixedGridView lvGoods;

    public HeaderGoodsGirdView(Activity activity) {
        super(activity);
    }

    @Override
    protected void getView(List<GoodsEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_goods_gird_layout, listView, false);
        ButterKnife.bind(this, view);

        handleView(list);
        listView.addHeaderView(view);
    }

    protected void handleView(final List<GoodsEntity> list) {
        if (list == null) return;
        int size = list.size();
        final HeaderGoodsAdapter adapter = new HeaderGoodsAdapter(mActivity,list,R.layout.item_goods_grid);
        if (size % 2 != 0){
            adapter.add(new GoodsEntity());
        }
        lvGoods.setAdapter(adapter);

        lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.show(mActivity, adapter.getItem(position).getGoods_name());
                Log.i("点击","ssss");
            }
        });
    }
}
