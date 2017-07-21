package com.lanbo.daza.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lanbo.daza.R;
import com.lanbo.daza.utils.ToastUtil;
import com.lanbo.daza.view.VPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;

public class Fragment2 extends Fragment {

    @BindView(R.id.ll_category_pop)
    LinearLayout llCategoryPop;
    @BindView(R.id.ll_left_btn)
    LinearLayout llLeftBtn;

    List<String> categoryList = new ArrayList<>(); // 品牌列表
    int selectIndex = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_page_category, null);

        for (int i = 0; i < 8; i++) {
            categoryList.add("" + i);
        }
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.ll_category_pop)
    public void showPop(View v) {
        ToastUtil.show(getActivity(), "show" + v.getId());
        initListItem(categoryList, LEFT, categoryList.get(selectIndex));
    }

    VPopupWindow mWindow;

    private void initListItem(final List<String> list, final int flag, final String selectName) {
        //生成Listener和清空
        setPopupWindowListener();
        mWindow = null;
        //显示popupwindow
        if (flag == LEFT) {
            mWindow = new VPopupWindow(getActivity(), list, selectName, listener, llLeftBtn);
        } else if (flag == RIGHT) {
            mWindow = new VPopupWindow(getActivity(), list, selectName, listener, llLeftBtn);
        }
    }


    VPopupWindow.Listener listener;

    private void setPopupWindowListener() {
        listener = null;
        listener = new VPopupWindow.Listener() {
            @Override
            public void onPopupWindowDismissListener() {
                //消失时的操作
            }

            @Override
            public void onItemClickListener(int position) {
                //点击Item时的操作
                selectIndex = position;
            }
        };
    }

}