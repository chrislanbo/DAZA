package com.monkey.framework.widget;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monkey.framework.R;

/**
 * 抽象集合适配器。继承最基本的适配。 当需要重新写适配器的是可以继承此适配器。重写里面的getView方法
 *
 * @author bb
 */
public abstract class ArrayListBaseAdapter<T> extends BaseAdapter {
    // 数据源
    protected List<T> mList;
    // 上下文
    protected Context mContext;
    protected LayoutInflater mInflater;

    private View mNoDataView;
    private String defaultNoDataText;
    private boolean showDefaultNoDataView = false;
    private boolean dataInitialized;

    public ArrayListBaseAdapter(Context context, List<T> mTs) {
        this(context);
        this.mList = mTs;
    }

    public ArrayListBaseAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getmNoDataView() {
        return mNoDataView;
    }

    /**
     * 返回的总条目数
     */
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

    /**
     * 初始化默认无数据视图
     *
     * @return
     */
    @SuppressLint("InflateParams")
    private View getDefaultNoDataView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.my_no_data, null);
        if (defaultNoDataText != null) {
            TextView tv = (TextView) v.findViewById(R.id.my_no_data_text);
            tv.setText(defaultNoDataText);
        }
        return v;
    }


    /**
     * 当没有数据时，显示没有数据提示（显示在第一个item的位置）。
     *
     * @param noDataView
     */
    public void setNoDataView(View noDataView) {
        this.mNoDataView = noDataView;
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
     * 返回每个Itenm的对应得对象
     */
    @Override
    public Object getItem(int position) {

        return mList == null ? null : mList.get(position);
    }

    /**
     * 返回每个Item的id，在这里设置的是Item的position
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup arg2);

    /**
     * 返回布局
     *
     * @param c
     * @param res
     * @return
     */
    public View inflate(Context c, int res) {
        return LayoutInflater.from(c).inflate(res, null);
    }

    /**
     * 获取数据源
     *
     * @return mList 数据源
     */
    public List<T> getList() {
        return mList;
    }

    /**
     * 设置适配器的数据
     *
     * @param mList
     */
    public void setList(List<T> list) {
        this.setList(list, false);
    }

    /**
     * 设置适配器的数据并刷新适配器
     *
     * @param mList
     */
    public void setList(List<T> list, boolean refresh) {
        this.mList = list;
        dataInitialized = true;
        if (refresh)
            this.notifyDataSetChanged();
    }

    /**
     * 在原来的数据add
     *
     * @param list
     */
    public void addList(List<T> list, boolean refresh) {
        if (mList != null) {
            mList.addAll(list);
        } else {
            this.mList = list;
        }
        if (refresh)
            notifyDataSetChanged();
    }


    /**
     * 初始化
     *
     * @param mList
     */
    public void setListNull() {
        this.mList = null;
        notifyDataSetChanged();
    }

    /**
     * 设置数据源是数组的形式
     *
     * @param list
     */
    public void setList(T[] list, boolean refresh) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList, refresh);
    }

}
