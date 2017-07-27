package com.lanbo.daza.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lanbo.daza.R;
import com.lanbo.daza.adapter.NewsAdapter;
import com.lanbo.daza.model.Address;
import com.lanbo.daza.model.Brands;
import com.lanbo.daza.model.FunctionEntity;
import com.lanbo.daza.model.GoodsEntity;
import com.lanbo.daza.model.NewsEntity;
import com.lanbo.daza.utils.JSONUtil;
import com.lanbo.daza.utils.ModelUtil;
import com.lanbo.daza.utils.PreferencesUtils;
import com.lanbo.daza.utils.ToastUtil;
import com.lanbo.daza.view.HeaderAdView;
import com.lanbo.daza.view.HeaderBannerView;
import com.lanbo.daza.view.HeaderBillboardView;
import com.lanbo.daza.view.HeaderDividerView;
import com.lanbo.daza.view.HeaderFunctionView;
import com.lanbo.daza.view.HeaderGoodsGirdView;
import com.lanbo.daza.view.HeaderGoodsView;
import com.lanbo.daza.view.HeaderTitleView;
import com.lanbo.daza.view.SmoothListView.SmoothListView;
import com.lanbo.daza.view.VPopupWindow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static com.lanbo.daza.Constant.BRAND_GOODS_LIST;
import static com.lanbo.daza.Constant.BRAND_LIST;
import static com.lanbo.daza.Constant.BRAND_LIST_URL;


/**
 * 蜜拓蜜商城页面
 */
public class Fragment2 extends Fragment {

    private static final String TAG = Fragment2.class.getSimpleName();
    @BindView(R.id.listView)
    SmoothListView smoothListView;
    @BindView(R.id.ll_category_pop)
    LinearLayout llCategoryPop;
    @BindView(R.id.ll_left_btn)
    LinearLayout llLeftBtn;

    private Context mContext;
    private Activity mActivity;
    List<String> categoryList = new ArrayList<>(); // 品牌列表
    int selectIndex = 0;

    // 此方法在主线程中调用，可以更新UI
    Handler handler2 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtil.show(mContext, "请求成功");
                    initData();
                    initView();
                    initListener();
                    break;
                case 0:
                    ToastUtil.show(mContext, "请求失败");
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();
        this.mContext = getActivity();
        startThread();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_page_category, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private void startThread() {
        Thread netThread = new Thread() {
            @Override
            public void run() {
                getData();
                Message msg = new Message();
                msg.what = 1;
                handler2.sendMessage(msg);
            }

        };
        netThread.start();
    }
    private List<Brands> brandList = new ArrayList<>(); // 品牌数据

    private void getData() {
        try {
            //从一个URL加载一个Document对象
            Document doc = Jsoup.connect(BRAND_LIST_URL).get();
            Elements elements = doc.select("div.brandBg"); // 首页商品
            for (Element e : elements) {
                String brand = e.select("h4").text();
                String brandDes = e.select("p").text();
                Log.i(TAG, "getData: 品牌="+brand+" 内容="+brandDes);
                brandList.add(new Brands(0,brand,brandDes));
                categoryList.add(brand);
            }

            PreferencesUtils.putString(mContext, BRAND_LIST, JSONUtil.toJson(brandList));

            Elements els = doc.select("div.brandCon");
            Document doc2 = Jsoup.parse(els.toString());
            Elements element = doc2.getElementsByTag("li");
            for (Element e : element) {
                String url = e.select("a").attr("href");
                String img = e.select("a").select("img").attr("src");
                String name = e.select("a").select("H4").text();
                String price = e.select("a").select("p").text();
                Log.i(TAG, "getData: name="+name+" 商品链接="+url+ " img=" + img + " 价格="+ price );
                goodsList.add(new GoodsEntity(url,img,"",name,price));
            }
            PreferencesUtils.putString(mContext, BRAND_GOODS_LIST, JSONUtil.toJson(goodsList));

        } catch (Exception e) {
            Log.i("mytag", e.toString());
        }
        Log.w(TAG, "商品数据准备完毕");
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
        //显示pop
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
                chooseItem(position);
            }
        };
    }

    /**
     * 选中item
     *
     * @param pos 点击相应item
     */
    private void chooseItem(int pos) {
        ToastUtil.show(getActivity(), "点击了" + pos + "位置 value=" + categoryList.get(pos));
    }

    private List<String> bannerList = new ArrayList<>(); // 广告数据
    private List<String> adsList = new ArrayList<>(); // 商城页面广告数据
    private List<String> numList = new ArrayList<>(); // 订单数据
    private List<GoodsEntity> goodsList = new ArrayList<>();
    private List<FunctionEntity> funcList = new ArrayList<>();
    private List<NewsEntity> newsList = new ArrayList<>();
    private List<Address> addressList = new ArrayList<>();

    private HeaderBannerView headerBannerView; // 广告视图
    private HeaderDividerView headerDividerView; // 分割线占位图
    private HeaderBillboardView headerBillboardView; // 分割线占位图
    private HeaderAdView headerAdView; // 广告位
    private HeaderTitleView headerTitleView; // 广告位
    private HeaderFunctionView headerFunctionView; // 广告位
    private HeaderGoodsView headerGoodsView; // 商品
    private HeaderGoodsGirdView headerGoodsGirdView; // 商品
    private NewsAdapter newsAdapter;

    private void initData() {
        // 获取广告数据
        bannerList = ModelUtil.getBannerData();
        // 获取function数据
        funcList = ModelUtil.getFunctionData2();

    }

    private void initView() {
        // 设置广告数据
        headerBannerView = new HeaderBannerView(mActivity);
        headerBannerView.fillView(bannerList, smoothListView);


        headerFunctionView = new HeaderFunctionView(mActivity);
        headerFunctionView.fillView(funcList, smoothListView);


        // 设置公告
        headerBillboardView = new HeaderBillboardView(mActivity);
        headerBillboardView.fillView("", smoothListView);

        // 广告预留位置
        headerAdView = new HeaderAdView(mActivity);
        headerAdView.fillView("", smoothListView);

        // 设置分割线
        headerDividerView = new HeaderDividerView(mActivity);
        headerDividerView.fillView("", smoothListView);

        headerTitleView = new HeaderTitleView(mActivity, "爆 / 款 / 产 / 品");
        headerTitleView.fillView("", smoothListView);

        //首页listView商品
//        headerGoodsView = new HeaderGoodsView(mActivity);
//        headerGoodsView.fillView(goodsList, smoothListView);

        //首页商品
        headerGoodsGirdView = new HeaderGoodsGirdView(mActivity);
        headerGoodsGirdView.fillView(goodsList, smoothListView);

        newsAdapter = new NewsAdapter(mContext, newsList);
        smoothListView.setAdapter(newsAdapter);
        onResume();
    }

    private void initListener() {

        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(true);
//        smoothListView.setSmoothListViewListener(this);
//        smoothListView.setOnScrollListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, " onResume 重新loop");
        if (headerBannerView != null)
            headerBannerView.enqueueBannerLoopMessage();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, " 暂停 当前fragment 不可见");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, " onStop fragment停止 移除loop");
        if (headerBannerView != null)
            headerBannerView.removeBannerLoopMessage();
    }
}