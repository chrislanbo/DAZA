package com.monkey.framework.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.monkey.framework.R;
import com.monkey.framework.view.LoadingView;

public abstract class BaseFragmentV4 extends Fragment {

    protected LoadingView loadingView;
    protected ViewGroup mParent;
    protected Activity mActivity;

    /**
     * @see android.app.Fragment#onAttach(Activity)
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mParent == null) {
            int layoutResource = getLayoutRes();
            if (layoutResource == 0) {
                return null;
            }
            mParent = (ViewGroup) inflater.inflate(layoutResource, container,
                    false);
            mParent.setClickable(true);
            afterInject(savedInstanceState, mParent);
            initTile(mParent);
        }
        ViewGroup parent = (ViewGroup) mParent.getParent();
        if (parent != null) {
            parent.removeView(mParent);
        }
        loadData();
        return mParent;
    }

    /**
     *
     */
    protected void loadData() {

    }

    /**
     * @see Fragment#onDestroyView()
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * @see Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // HelpPopupwindow.dismiss();
    }

    protected void afterInject(Bundle savedInstanceState, View contentView) {
    }

    public abstract int getLayoutRes();

    private void initTile(View contentView) {
        View view = contentView.findViewById(R.id.img_back);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
    }

    /**
     * 获取加载中视图，如需自定义可重写此方法
     *
     * @return
     */
    public LoadingView getLoadingView() {
        return ((BaseFragmentActivity) getActivity()).getLoadingView();
    }


}
