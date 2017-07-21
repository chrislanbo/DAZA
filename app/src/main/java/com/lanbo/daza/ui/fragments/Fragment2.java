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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment2 extends Fragment {

    @BindView(R.id.ll_category_pop)
    LinearLayout llCategoryPop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_page_category, null);

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.ll_category_pop)
    public void showPop(View v){
        ToastUtil.show(getActivity(),"show"+v.getId());
    }

//    RankingPopupWindow mWindow;
//    private void initListItem(final List<String> list, final int flag, final String selectName){
//        //生成Listener和清空
//        setPopupWindowListener();
//        mWindow = null;
//        //显示popupwindow
//        if(flag == LEFT){
//            mWindow = new RankingPopupWindow(this,list,selectName,listener,mLeftWeightLayout);
//        }else if(flag == RIGHT){
//            mWindow = new RankingPopupWindow(this,list,selectName,listener,mRightWeightLayout);
//        }
//    }



//    RankingPopupWindow.Listener listener;
//    private void setPopupWindowListener(){
//        listener = null;
//        listener = new RankingPopupWindow.Listener() {
//            @Override
//            public void onPopupWindowDismissListener() {
//                //消失时的操作
//            }
//
//            @Override
//            public void onItemClickListener(int position) {
//                //点击Item时的操作
//            }
//        }
    }