package com.lanbo.daza.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lanbo.daza.MyApplication;
import com.lanbo.daza.R;
import com.lanbo.daza.adapter.NewsAdapter;
import com.lanbo.daza.model.Address;
import com.lanbo.daza.model.FunctionEntity;
import com.lanbo.daza.model.GoodsEntity;
import com.lanbo.daza.model.NewsEntity;
import com.lanbo.daza.model.UserInfo;
import com.lanbo.daza.ui.AboutActivity;
import com.lanbo.daza.utils.ColorUtil;
import com.lanbo.daza.utils.DensityUtil;
import com.lanbo.daza.utils.JSONUtil;
import com.lanbo.daza.utils.ModelUtil;
import com.lanbo.daza.utils.PreferencesUtils;
import com.lanbo.daza.utils.StatusBarUtil;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lanbo.daza.Constant.INFO_HEAD_PIC;
import static com.lanbo.daza.Constant.addressInfoUrl;
import static com.lanbo.daza.Constant.baseNewsUrl;
import static com.lanbo.daza.Constant.baseUrl;
import static com.lanbo.daza.Constant.basewwwUrl;
import static com.lanbo.daza.Constant.infoUrl;
import static com.lanbo.daza.Constant.myOrder;
import static com.lanbo.daza.utils.Tools.fixUrl;

public class Fragment1 extends Fragment implements SmoothListView.ISmoothListViewListener, SmoothListView.OnSmoothScrollListener {


    @BindView(R.id.listView)
    SmoothListView smoothListView;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view_title_bg)
    View viewTitleBg;
    @BindView(R.id.view_action_more_bg)
    View viewActionMoreBg;
    @BindView(R.id.fl_action_more)
    FrameLayout flActionMore;

    private static String TAG = Fragment1.class.getSimpleName();
    public static String NEWS_DATA = "news_data";
    public static String HAS_NEWS_DATA = "has_news_data";
    public static String HOME_GOODS = "home_goods";
    public static final String ORDER_NUM = "order_num";
    private Context mContext;
    private Activity mActivity;
    private View rootView;
    private int mScreenHeight; // 屏幕高度

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

    private int titleViewHeight = 65; // 标题栏的高度

    private View itemHeaderBannerView; // 从ListView获取的广告子View
    private int bannerViewHeight = 180; // 广告视图的高度
    private int bannerViewTopMargin; // 广告视图距离顶部的距离

    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private int filterViewPosition = 4; // 筛选视图的位置
    private int filterViewTopMargin; // 筛选视图距离顶部的距离
    private boolean isScrollIdle = true; // 停止滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动

    Map<String, String> header = MyApplication.getHeader();

    // 此方法在主线程中调用，可以更新UI
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtil.show(mContext, "请求成功");
                    Log.i(TAG, " list " + goodsList.size());
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

    private void startThread() {
        Thread netThread = new Thread() {
            @Override
            public void run() {
                // 获取新闻
                handleNews();
                handleHomeGoods();
                // 获取首页最新资讯
//                getTopNews();


                handleOrderNum();

                handleUserInfo();
                handleADS();
                handleAddress();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }
        };
        netThread.start();
    }

    private void handleOrderNum() {
        String json = PreferencesUtils.getString(mContext, ORDER_NUM);
        if (json != null && json.length() > 0) {
            Log.i(TAG, "run: 订单数量有数据");
            numList = null;
            numList = JSONUtil.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        } else {
            Log.i(TAG, "run: 订单数量无数据，请求网络");
            numList = getOrderNum(header);
        }

        for (int i = 0; i < myOrder.length; i++) {
            PreferencesUtils.putString(mContext, myOrder[i], numList.get(i));
        }
    }

    private void handleADS() {
        String json = PreferencesUtils.getString(mContext, ADS_DATA);
        if (json != null && json.length() > 0) {
            Log.i(TAG, "run: 商城广告，本地有数据");
            adsList = JSONUtil.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
            for (int i = 0; i < adsList.size(); i++) {
                Log.i("商城广告页", "" + adsList.get(i));
            }
        } else {
            Log.i(TAG, "run: 商城广告，本地无数据，加载网络");
            getGoodsBanner();
        }
    }

    private void handleUserInfo() {
        String json = PreferencesUtils.getString(mContext, INFO_DATA);
        if (json != null && json.length() > 0) {
            Log.i(TAG, "run: 个人信息有数据，本地加载");
            UserInfo info = JSONUtil.fromJson(json, new TypeToken<UserInfo>() {
            }.getType());
            Log.i(TAG, "handleUserInfo: 用户信息 " + info);
        } else {
            getInfo(header);
            Log.i(TAG, "run: 个人信息没有数据，请求网络");
        }
    }

    private void handleAddress() {
        String json = PreferencesUtils.getString(mContext, ADDRESS_DATA);
        if (json != null && json.length() > 0) {
            Log.i(TAG, "run: 地址有数据，本地加载");
            addressList = null;
            addressList = JSONUtil.fromJson(json, new TypeToken<List<Address>>() {
            }.getType());
        } else {
            Log.i(TAG, "run: 地址没有数据，请求网络");
            getAddress(header);
        }
    }

    public static String INFO_DATA = "info_data";
    public static String ADDRESS_DATA = "address_data";
    public static String ADS_DATA = "ads_data";

    private void handleHomeGoods() {
        String homeGoodsJson = PreferencesUtils.getString(mContext, HOME_GOODS);
        if (homeGoodsJson != null && homeGoodsJson.length() > 0) {
            Log.i(TAG, "run: 首页商品有数据，本地加载");
            goodsList = null;
            goodsList = JSONUtil.fromJson(homeGoodsJson, new TypeToken<List<GoodsEntity>>() {
            }.getType());
        } else {
            Log.i(TAG, "run: 首页商品没有数据，请求网络");
            getHomeGoods();
        }
    }

    private void handleNews() {
        String newsJson = PreferencesUtils.getString(mContext, NEWS_DATA);
        Log.i(TAG, "run: 新闻数据：" + newsJson);
        if (newsJson != null && newsJson.length() > 0) {
            Log.i(TAG, "run: 新闻有数据，本地加载");
            newsList = null;// 清空集合
            // 赋值集合，返回对象集合
            newsList = JSONUtil.fromJson(newsJson, new TypeToken<List<NewsEntity>>() {
            }.getType());
        } else {
            Log.i(TAG, "run: 新闻没有数据去请求数据");
            getNews();
        }
    }

    private void getHomeGoods() {
        try {
            //从一个URL加载一个Document对象
            Document doc = Jsoup.connect(baseUrl).get();
            Elements elements = doc.select("div.indexUl"); // 首页商品
            Document divcontions = Jsoup.parse(elements.toString());
            Elements element = divcontions.getElementsByTag("li");
            for (Element links : element) {
                String link_no_host = links.select("a").attr("href").trim();
                String imgUrl = links.select("img").attr("src").trim();
                String goods_id = links.select("input").attr("value").trim();
                Elements title = links.select("p");
                String goods_name = title.text();
                Elements elementsPrice = links.select("div.price");
                String price = elementsPrice.text();
                Log.i("商品信息", "\n\n" + "link_no_host = " + link_no_host + "\n" + "imgUrl = " + imgUrl + "\n" + "goods_id = " + goods_id + "\n" + "goods_name = " + goods_name + "\n" + "price = " + price + "\n");
                goodsList.add(new GoodsEntity(link_no_host, imgUrl, goods_id, goods_name, price));
            }
            PreferencesUtils.putString(mContext, HOME_GOODS, JSONUtil.toJson(goodsList));

        } catch (Exception e) {
            Log.i("mytag", e.toString());
        }
        Log.w(TAG, "商品数据准备完毕");
    }

    private void getAddress(Map<String, String> header) {
        try {
            Document doc = Jsoup.connect(addressInfoUrl).header("token", header.get("token")).get();
            Elements elements = doc.select("div.addressCon");
            for (Element e : elements) {
                String name = e.select("div.name").select("span").text(); // 收货人名
                String phone = e.select("div.tel").text(); // 收货人手机号
                String[] address = e.select("div.address").text().split(" "); // 收货人地址
                // TODO address非空
                int size = address.length;
                String a = "";
                String b = "";
                String c = "";
                String d = "";
                if (size == 4) {
                    a = address[0];
                    b = address[1];
                    c = address[2];
                    d = address[3];
                }

                String url = e.select("a.edit").attr("href"); // 收货人地址
                int a1 = url.indexOf("=") + 1;
                String id = url.substring(a1);
                Log.i("地址信息:", "\n\n" + "name = " + name + "\n" + "phone = " + phone +
                        "\n" + "省 = " + a + "\n" + "市 = " + b + "\n" + "区/县 = " + c + "\n" + "详细地址 = " + d + "\n" + "url = " + url + "\n" + "id = " + id + "\n");

                addressList.add(new Address(name, phone, a, b, c, d, url, id));
                PreferencesUtils.putString(mContext, ADDRESS_DATA, JSONUtil.toJson(addressList));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGoodsBanner() {
        try {
            Document doc = Jsoup.connect(baseUrl).get();
            Elements elements = doc.select("div.swiper-slide").select("a");
            for (Element e : elements) {
                String href = e.attr("href");
                int i = href.indexOf("=");
                String id = href.substring(i + 1);
                String attr = elements.select("img").attr("src");
                Log.i("商品页轮播", "\n" + "id = " + id + "\n" + " attr = " + attr + "\n");
                adsList.add(attr);
                PreferencesUtils.putString(getContext(), ADS_DATA, JSONUtil.toJson(adsList));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取订单数量，需要在提交订单后等多场景都需要刷新，集合为null或者size不等于4，默认不处理
     *
     * @param header 头布局
     * @return 依次返回  待付款 待发货 待收货 全部订单
     */
    private ArrayList<String> getOrderNum(Map<String, String> header) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect("http://app.mituomi.com/Mobile/user/index.html").header("token", header.get("token")).get();
            String head_pic_url = doc.select("img").attr("src");
            PreferencesUtils.putString(mContext, INFO_HEAD_PIC, head_pic_url);
            Elements em = doc.select("em");
            for (Element links : em) {
                String a = links.text();
                list.add(a);
            }
            PreferencesUtils.putString(mContext, ORDER_NUM, JSONUtil.toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void getTopNews() {
        try {
            Document doc = Jsoup.connect(basewwwUrl).get();
            Elements elements = doc.select("div.section-1").select("div.center").select("ul.ul-1");
            Document news = Jsoup.parse(elements.toString());
            Elements newsElements = news.getElementsByTag("li");
            for (Element links : newsElements) {
                String newsImg = links.select("span").select("img").attr("src").trim();
                newsImg = fixUrl(newsImg, basewwwUrl);
                String newsUrl = links.select("div").select("p").select("a").attr("href").trim();
                newsUrl = fixUrl(newsUrl, basewwwUrl);
                String newsTitle = links.select("div").select("p").select("a").text();
                Log.i("最新资讯", "\n" + "newsImg = " + newsImg + "\n" + " newsUrl=" + newsUrl + "\n" + "newsTitle=" + newsTitle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNews() {
        try {
            Document doc = Jsoup.connect(baseNewsUrl).get();
            Elements elements = doc.select("div.con");
            Document news = Jsoup.parse(elements.toString());
            Elements newsElements = news.getElementsByTag("li");
            for (Element links : newsElements) {
                String newsImg = links.select("span").select("img.m-img").attr("src").trim();
                newsImg = fixUrl(newsImg, basewwwUrl);
                String newsUrl = links.select("div").select("a").attr("href").trim();
                newsUrl = fixUrl(newsUrl, basewwwUrl);
                String newsTitle = links.select("div").select("h3").text();
                String newsContent = links.select("div").select("p").text();
                newsList.add(new NewsEntity(newsUrl, newsImg, newsContent, newsTitle));
                Log.i("新闻", "\n" + "newsImg = " + newsImg + "\n" + " newsUrl=" + newsUrl + "\n" + "newsTitle=" + newsTitle + "\n" + "newsContent=" + newsContent);
            }

            PreferencesUtils.putString(mContext, NEWS_DATA, JSONUtil.toJson(newsList));
            PreferencesUtils.putBoolean(mContext, HAS_NEWS_DATA, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getInfo(Map<String, String> header) {
        try {
            Document doc = Jsoup.connect(infoUrl).header("token", header.get("token")).get();
            Elements infoEle = doc.select("div.personalInfo");
            Document document = Jsoup.parse(infoEle.toString());
            Elements links = document.getElementsByTag("li");
            String head_pic_url = links.select("img.now_head_pic").attr("src"); // 头像
            head_pic_url = fixUrl(head_pic_url, baseUrl);
            String[] split = head_pic_url.split(".jpg");
            head_pic_url = split[0] + ".jpg";
            String username = links.select("p.name").text(); // 昵称
            String sex = links.select("p.xingbie").text(); // 昵称
            String phone = links.select("p.shouji").text(); // 昵称
            Elements aE = links.select("a");
            String code = "";
            for (Element e : aE) {
                if (e.select("span").text().equals("Code码")) {
                    code = e.select("p").text();
                    break;
                }
            }
            String weixin = links.select("p.weixin").text(); // 昵称
            String address = "/Mobile/user/site_admin.html";
            address = fixUrl(address, baseUrl);
            String changePwd = "/Mobile/user/reset_password.html";
            changePwd = fixUrl(changePwd, baseUrl);
            Log.i("新闻", "\n" + "head_pic_url = " + head_pic_url + "\n" + " username=" + username + "\n" + "sex=" + sex + "\n" + "phone=" + phone
                    + "\n" + "weixin=" + weixin + "\n" + "address=" + address + "\n" + "changePwd=" + changePwd + "\n" + "code码=" + code);

//            PreferencesUtils.putString(mContext,INFO_HEAD_PIC,head_pic_url);
//            PreferencesUtils.putString(mContext, INFO_USERNAME, username);
//            PreferencesUtils.putString(mContext, INFO_SEX, sex);
//            PreferencesUtils.putString(mContext, INFO_PHONE, phone);
//            PreferencesUtils.putString(mContext, INFO_WEIXIN, weixin);
//            PreferencesUtils.putString(mContext, INFO_CODE, code);

            UserInfo userInfo = new UserInfo(head_pic_url, username, sex, phone, weixin, code);
            PreferencesUtils.putString(mContext, INFO_DATA, JSONUtil.toJson(userInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, rootView);
//        startThread();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(mActivity, false);
    }


    private void initData() {
        mScreenHeight = DensityUtil.getWindowHeight(mActivity);
        // 获取广告数据
        bannerList = ModelUtil.getBannerData();
        // 获取function数据
        funcList = ModelUtil.getFunctionData();

        // 获取首页商品数据
        if (goodsList != null && goodsList.size() <= 0) {
            //TODO 无商品做相关操作
            Log.e(TAG, "商品数据获取失败或者无首页商品");
        }
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

        headerTitleView = new HeaderTitleView(mActivity, "蜜 / 家 / 热 / 点");
        headerTitleView.fillView("", smoothListView);
        // 设置ListView数据
//        mAdapter = new TravelingAdapter(mContext, travelingList);
//        smoothListView.setAdapter(mAdapter);
        newsAdapter = new NewsAdapter(mContext, newsList);
        smoothListView.setAdapter(newsAdapter);

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;
        onResume();
    }

    private void initListener() {
        // 关于
        flActionMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AboutActivity.class));
            }
        });


        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);
        smoothListView.setOnScrollListener(this);

    }

    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
//        下拉状态
        if (bannerViewTopMargin > 0) {
            fraction = 1f - bannerViewTopMargin * 1f / 60;
            Log.d(TAG, "透明度 ： " + fraction);
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(bannerViewTopMargin) * 1f; //划出距离
        fraction = space / (bannerViewHeight - titleViewHeight);
        if (fraction < 0f) {
            fraction = 0f;
        } else if (fraction >= 1f) {
            fraction = 1f;
        }
//        Log.d(TAG, "透明度 ： " + fraction + "统一设置为不透明");
//        rlBar.setAlpha(1f);

        if (fraction >= 1f) {
            Log.d(TAG, "fraction>=1  ： " + 0f);
            setBg(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Log.d(TAG, " fraction<1 ： " + (1f - fraction));
            setBg(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.colorPrimary));
        }
    }

    /**
     * 设置背景透明度，0f 为不透明 1f为全透明
     *
     * @param v 值
     */
    private void setBg(float v) {
        viewTitleBg.setAlpha(v);
        viewActionMoreBg.setAlpha(v);
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

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothListView.stopRefresh();
                smoothListView.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothListView.stopLoadMore();
            }
        }, 2000);
    }

    @Override
    public void onSmoothScrolling(View view) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
        Log.i(TAG, "onScrollStateChanged: 滑动停止" + isScrollIdle);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //停止滑动，
        if (isScrollIdle && bannerViewTopMargin < 0) {
            Log.i(TAG, "onScroll: 停止在正常位置");
            return;
        }
        // 获取广告头部View、自身的高度、距离顶部的高度
        if (itemHeaderBannerView == null) {
            itemHeaderBannerView = smoothListView.getChildAt(1);
        } else {
            bannerViewTopMargin = DensityUtil.px2dip(mContext, itemHeaderBannerView.getTop());
            bannerViewHeight = DensityUtil.px2dip(mContext, itemHeaderBannerView.getHeight());
            Log.d(TAG, "  轮播高度 " + bannerViewHeight + " 距离顶部的距离  " + bannerViewTopMargin + " 第一个可见item = " + firstVisibleItem);
        }

        // 获取筛选View、距离顶部的高度
//        if (itemHeaderFilterView == null) {
//            itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition -              firstVisibleItem);
//        } else {
//            filterViewTopMargin = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
//        }

        // 处理筛选是否吸附在顶部
//        if (filterViewTopMargin <= titleViewHeight || firstVisibleItem > filterViewPosition) {
//            isStickyTop = true; // 吸附在顶部
//        } else {
//            isStickyTop = false; // 没有吸附在顶部
//        }

//        if (isSmooth && isStickyTop) {
//            isSmooth = false;
//        }

        if (firstVisibleItem == 0) {
            Log.i(TAG, "onScroll: 修复一下标题栏-透明");
            setBg(1f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        } else if (firstVisibleItem == 1) {
            handleTitleBarColorEvaluate();
        } else {
            Log.i(TAG, "onScroll: 修复一下标题栏-完全不透明");
            setBg(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }
}