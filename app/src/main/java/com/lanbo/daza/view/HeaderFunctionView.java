package com.lanbo.daza.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lanbo.daza.R;
import com.lanbo.daza.adapter.HeaderFunctionAdapter;
import com.lanbo.daza.model.FunctionEntity;
import com.lanbo.daza.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class HeaderFunctionView extends AbsHeaderView<List<FunctionEntity>> {

    @BindView(R.id.gv_channel)
    FixedGridView gvChannel;

    public HeaderFunctionView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(List<FunctionEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_channel_layout, listView, false);
        ButterKnife.bind(this, view);

        dealWithTheView(list);
        listView.addHeaderView(view);
    }

    private void dealWithTheView(final List<FunctionEntity> list) {
        if (list == null || list.size() < 2) return;
        int size = list.size();
        if (size <= 4) {
            gvChannel.setNumColumns(size);
        } else if (size == 6) {
            gvChannel.setNumColumns(3);
        } else if (size == 8) {
            gvChannel.setNumColumns(4);
        } else if (size == 12) {
            gvChannel.setNumColumns(4);
        } else {
            return;
        }

        final HeaderFunctionAdapter adapter = new HeaderFunctionAdapter(mActivity, list);
        gvChannel.setAdapter(adapter);

        gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.show(mActivity, adapter.getItem(position).getTitle());
            }
        });
    }

}
