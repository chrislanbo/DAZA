package com.monkey.framework.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monkey.framework.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 只需实现getViewHolder()的适配器
 *
 * @param <T>
 * @author pan
 */
public abstract class SimpleAdapter<T> extends BaseAdapter {

    protected Context context;
    protected int layoutId;
    protected List<T> mList;
    private View mNoDataView;
    private String defaultNoDataText;
    private boolean showDefaultNoDataView;
    private boolean dataInitialized;

    public SimpleAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        this.showDefaultNoDataView = true;
    }

    @Override
    public int getCount() {
        if (mList == null || mList.size() < 1) {
            if (mNoDataView == null && showDefaultNoDataView) {
                mNoDataView = getDefaultNoDataView();
            }
            if (dataInitialized && mNoDataView != null)
                return 1;
            return 0;
        }
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        if (mList == null || mList.size() < 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 传入数据集合
     *
     * @param data
     */
    public void setList(List<T> data) {
        if (data != null) {
            this.mList = data;
        }
        dataInitialized = true;
        notifyDataSetChanged();
    }

    /**
     * 传入数据集合
     *
     * @param data
     */
    public void setList(T[] data) {
        if (data != null) {
            List<T> list = new ArrayList<T>();
            for (T d : data) {
                if (d != null)
                    list.add(d);
            }
            setList(list);
        }
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<T> getList() {
        return mList;
    }

    /**
     * 当没有数据时，显示没有数据提示（显示在第一个item的位置）。
     *
     * @param noDataView
     */
    public void setNoDataView(View noDataView) {
        this.mNoDataView = noDataView;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 设置默认无数据提示文本
     *
     * @param defaultNoDataText
     */
    public void setDefaultNoDataText(String defaultNoDataText) {
        this.defaultNoDataText = defaultNoDataText;
    }

    /**
     * 显示默认无数据视图(默认true)
     *
     * @param showDefaultNoDataView
     */
    public void showDefaultNoDataView(boolean showDefaultNoDataView) {
        this.showDefaultNoDataView = showDefaultNoDataView;
    }

    /**
     * 初始化默认无数据视图
     *
     * @return
     */
    @SuppressLint("InflateParams")
    private View getDefaultNoDataView() {
        View v = LayoutInflater.from(context).inflate(R.layout.my_no_data, null);
        if (defaultNoDataText != null) {
            TextView tv = (TextView) v.findViewById(R.id.my_no_data_text);
            tv.setText(defaultNoDataText);
        }
        return v;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mList == null || mList.size() < 1) {
            return mNoDataView;
        } else if (convertView == mNoDataView) {
            convertView = null;
        }
        ViewHolder<T> holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId,
                    parent, false);
            holder = getViewHolder();
            holder.onFindView(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder<T>) convertView.getTag();
        holder.onPositionChange(position);
        if (mList != null)
            holder.onBindData(mList.get(position));
        return convertView;
    }

    /**
     * 获取视图保持器
     *
     * @return
     */
    public abstract ViewHolder<T> getViewHolder();

}
