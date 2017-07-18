package com.lanbo.daza.ui;

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

import com.lanbo.daza.MyApplication;
import com.lanbo.daza.R;
import com.lanbo.daza.adapter.NewsAdapter;
import com.lanbo.daza.adapter.TravelingAdapter;
import com.lanbo.daza.model.ChannelEntity;
import com.lanbo.daza.model.FilterData;
import com.lanbo.daza.model.FunctionEntity;
import com.lanbo.daza.model.GoodsEntity;
import com.lanbo.daza.model.NewsEntity;
import com.lanbo.daza.model.OperationEntity;
import com.lanbo.daza.model.TravelingEntity;
import com.lanbo.daza.utils.ColorUtil;
import com.lanbo.daza.utils.DensityUtil;
import com.lanbo.daza.utils.ModelUtil;
import com.lanbo.daza.utils.PreferencesUtils;
import com.lanbo.daza.utils.StatusBarUtil;
import com.lanbo.daza.utils.ToastUtil;
import com.lanbo.daza.view.HeaderAdView;
import com.lanbo.daza.view.HeaderBannerView;
import com.lanbo.daza.view.HeaderBillboardView;
import com.lanbo.daza.view.HeaderChannelView;
import com.lanbo.daza.view.HeaderDividerView;
import com.lanbo.daza.view.HeaderFilterView;
import com.lanbo.daza.view.HeaderFunctionView;
import com.lanbo.daza.view.HeaderGoodsGirdView;
import com.lanbo.daza.view.HeaderGoodsView;
import com.lanbo.daza.view.HeaderOperationView;
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

import static com.lanbo.daza.Constant.INFO_CODE;
import static com.lanbo.daza.Constant.INFO_HEAD_PIC;
import static com.lanbo.daza.Constant.INFO_PHONE;
import static com.lanbo.daza.Constant.INFO_SEX;
import static com.lanbo.daza.Constant.INFO_USERNAME;
import static com.lanbo.daza.Constant.INFO_WEIXIN;
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
    private Context mContext;
    private Activity mActivity;
    private View rootView;
    private int mScreenHeight; // 屏幕高度

    private List<String> bannerList = new ArrayList<>(); // 广告数据
    private List<ChannelEntity> channelList = new ArrayList<>(); // 频道数据
    private List<OperationEntity> operationList = new ArrayList<>(); // 运营数据
    private List<TravelingEntity> travelingList = new ArrayList<>(); // ListView数据
    private List<GoodsEntity> goodsList = new ArrayList<>();; // ListView数据
    private List<FunctionEntity> funcList = new ArrayList<>();; // ListView数据
    private List<NewsEntity> newsList = new ArrayList<>();; // ListView数据

    private HeaderBannerView headerBannerView; // 广告视图
    private HeaderChannelView headerChannelView; // 频道视图
    private HeaderOperationView headerOperationView; // 运营视图
    private HeaderDividerView headerDividerView; // 分割线占位图
    private HeaderBillboardView headerBillboardView; // 分割线占位图
    private HeaderAdView headerAdView; // 广告位
    private HeaderTitleView headerTitleView; // 广告位
    private HeaderFunctionView headerFunctionView; // 广告位
    private HeaderGoodsView headerGoodsView; // 商品
    private HeaderGoodsGirdView headerGoodsGirdView; // 商品
    private HeaderFilterView headerFilterView; // 分类筛选视图
    private FilterData filterData; // 筛选数据
    private TravelingAdapter mAdapter;
    private NewsAdapter newsAdapter;

    private int titleViewHeight = 65; // 标题栏的高度

    private View itemHeaderBannerView; // 从ListView获取的广告子View
    private int bannerViewHeight = 180; // 广告视图的高度
    private int bannerViewTopMargin; // 广告视图距离顶部的距离

    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private int filterViewPosition = 4; // 筛选视图的位置
    private int filterViewTopMargin; // 筛选视图距离顶部的距离
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)

    // 此方法在主线程中调用，可以更新UI
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 1:
                    ToastUtil.show(mContext, "请求成功");
                    goodsList = (List<GoodsEntity>) msg.obj;
                    Log.i(TAG," list " + goodsList.size());
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

        Thread netThread = new Thread() {
            @Override
            public void run() {
                // 获取新闻
                getNews();

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
                    Message msg = new Message();
                    // 消息对象可以携带数据
                    msg.obj = goodsList;
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    Log.i("mytag", e.toString());
                }
                Log.w(TAG,"商品数据准备完毕");

                Map<String, String> header = MyApplication.getHeader();

                // 获取首页最新资讯
//                getTopNews();



                ArrayList orderNum = getOrderNum(header);

                for (int i = 0; i < myOrder .length; i++) {
                    PreferencesUtils.putString(mContext,myOrder[i], (String) orderNum.get(i));
                }

                getInfo(header);
                getGoodsBanner();
//                地址
                try {
                    Document doc = Jsoup.connect(addressInfoUrl).header("token",header.get("token")).get();
                    Elements elements = doc.select("div.addressCon");
                    for (Element e : elements) {
                        String name = e.select("div.name").select("span").text(); // 收货人名
                        String phone = e.select("div.tel").text(); // 收货人手机号
                        String [] address = e.select("div.address").text().split(" "); // 收货人地址
                        // TODO address非空
                        int size = address.length;
                        String a = "";
                        String b = "";
                        String c = "";
                        String d = "";
                        if(size==4){
                            a = address[0];
                            b = address[1];
                            c = address[2];
                            d = address[3];
                        }

                        String url = e.select("a.edit").attr("href"); // 收货人地址
                        int a1 = url.indexOf("=")+1;
                        String id = url.substring(a1);
                        Log.i("商品信息", "\n\n" + "name = " + name + "\n" + "phone = " + phone +
                                "\n" + "省 = " + a + "\n" + "市 = " + b + "\n" + "区/县 = " + c + "\n" + "详细地址 = " + d + "\n" + "url = " + url + "\n" + "id = " + id + "\n");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        netThread.start();

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
                Log.i("商品页轮播","\n" + "id = "+id +"\n" +  " attr = "+attr+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取订单数量，需要在提交订单后等多场景都需要刷新，集合为null或者size不等于4，默认不处理
     * @param header 头布局
     * @return  依次返回  待付款 待发货 待收货 全部订单
     */
    private ArrayList<String> getOrderNum(Map<String, String> header) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect("http://app.mituomi.com/Mobile/user/index.html").header("token",header.get("token")).get();
            String head_pic_url = doc.select("img").attr("src");
            PreferencesUtils.putString(mContext,INFO_HEAD_PIC,head_pic_url);
            Elements em = doc.select("em");
            for (Element links : em){
                String a = links.text();
                list.add(a);
            }
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
                newsImg = fixUrl(newsImg,basewwwUrl);
                String newsUrl = links.select("div").select("p").select("a").attr("href").trim();
                newsUrl = fixUrl(newsUrl,basewwwUrl);
                String newsTitle = links.select("div").select("p").select("a").text();
                Log.i("最新资讯","\n" + "newsImg = "+newsImg +"\n" +  " newsUrl="+newsUrl+"\n" + "newsTitle=" +newsTitle);
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
                newsImg = fixUrl(newsImg,basewwwUrl);
                String newsUrl = links.select("div").select("a").attr("href").trim();
                newsUrl = fixUrl(newsUrl,basewwwUrl);
                String newsTitle = links.select("div").select("h3").text();
                String newsContent = links.select("div").select("p").text();
                newsList.add(new NewsEntity(newsUrl,newsImg,newsContent,newsTitle));
                Log.i("新闻","\n" + "newsImg = "+newsImg +"\n" +  " newsUrl="+newsUrl+"\n" + "newsTitle=" +newsTitle+"\n" + "newsContent=" +newsContent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getInfo(Map<String, String> header) {
        try {
            Document doc = Jsoup.connect(infoUrl).header("token",header.get("token")).get();
            Elements infoEle = doc.select("div.personalInfo");
            Document document = Jsoup.parse(infoEle.toString());
            Elements links = document.getElementsByTag("li");
            String head_pic_url = links.select("img.now_head_pic").attr("src"); // 头像
            head_pic_url = fixUrl(head_pic_url,baseUrl);
            String[] split = head_pic_url.split(".jpg");
            head_pic_url = split[0]+".jpg";
            String username = links.select("p.name").text(); // 昵称
            String sex = links.select("p.xingbie").text(); // 昵称
            String phone = links.select("p.shouji").text(); // 昵称
            Elements aE = links.select("a");
            String code = "";
            for (Element e : aE){
                if(e.select("span").text().equals("Code码")){
                    code = e.select("p").text();
                    break;
                }
            }
            String weixin = links.select("p.weixin").text(); // 昵称
            String address = "/Mobile/user/site_admin.html";
            address = fixUrl(address,baseUrl);
            String changePwd = "/Mobile/user/reset_password.html";
            changePwd = fixUrl(changePwd,baseUrl);
            Log.i("新闻","\n" + "head_pic_url = "+head_pic_url +"\n" +  " username="+username+"\n" + "sex=" +sex+"\n" + "phone=" +phone
                    +"\n" + "weixin=" +weixin+"\n" + "address=" +address+"\n" + "changePwd=" +changePwd +"\n" + "code码=" +code );

//            PreferencesUtils.putString(mContext,INFO_HEAD_PIC,head_pic_url);
            PreferencesUtils.putString(mContext,INFO_USERNAME,username);
            PreferencesUtils.putString(mContext,INFO_SEX,sex);
            PreferencesUtils.putString(mContext,INFO_PHONE,phone);
            PreferencesUtils.putString(mContext,INFO_WEIXIN,weixin);
            PreferencesUtils.putString(mContext,INFO_CODE,code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, rootView);
        this.mActivity = getActivity();
        this.mContext = getActivity();
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
        // 获取ListView数据
        travelingList = ModelUtil.getTravelingData();


        // 获取首页商品数据
        if (goodsList!= null && goodsList.size() <= 0) {

            //TODO 无商品做相关操作
            Log.e(TAG, "商品数据获取失败或者无首页商品");
        }
    }

    private void initView() {
        // 设置广告数据
        headerBannerView = new HeaderBannerView(mActivity);
        headerBannerView.fillView(bannerList, smoothListView);


        headerFunctionView = new HeaderFunctionView(mActivity);
        headerFunctionView.fillView(funcList,smoothListView);


        // 设置公告
        headerBillboardView = new HeaderBillboardView(mActivity);
        headerBillboardView.fillView("", smoothListView);

        // 广告预留位置
        headerAdView = new HeaderAdView(mActivity);
        headerAdView.fillView("", smoothListView);

        // 设置分割线
        headerDividerView = new HeaderDividerView(mActivity);
        headerDividerView.fillView("", smoothListView);

        headerTitleView = new HeaderTitleView(mActivity,"爆 / 款 / 产 / 品");
        headerTitleView.fillView("", smoothListView);

        //首页listView商品
//        headerGoodsView = new HeaderGoodsView(mActivity);
//        headerGoodsView.fillView(goodsList, smoothListView);

        //首页商品
        headerGoodsGirdView = new HeaderGoodsGirdView(mActivity);
        headerGoodsGirdView .fillView(goodsList, smoothListView);

        headerTitleView = new HeaderTitleView(mActivity,"蜜 / 家 / 热 / 点");
        headerTitleView.fillView("", smoothListView);
        // 设置ListView数据
//        mAdapter = new TravelingAdapter(mContext, travelingList);
//        smoothListView.setAdapter(mAdapter);
        newsAdapter = new NewsAdapter(mContext,newsList);
        smoothListView.setAdapter(newsAdapter);

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;
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

    // 填充数据
    private void fillAdapter(List<TravelingEntity> list) {
        if (list == null || list.size() == 0) {
            int height = mScreenHeight - DensityUtil.dip2px(mContext, 95); // 95 = 标题栏高度 ＋ FilterView的高度
            mAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
            mAdapter.setData(list);
        }
    }

    float lastSpace = 0;

    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        Log.d(TAG, "处理标题栏颜色渐变");
        float fraction;
        if (bannerViewTopMargin > 0) {
            fraction = 1f - bannerViewTopMargin * 1f / 60;
            Log.d(TAG, "透明度 ： " + fraction);
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(bannerViewTopMargin) * 1f;
        fraction = space / (bannerViewHeight - titleViewHeight);
        if (fraction < 0f) {
            fraction = 0f;
        } else if (fraction > 1f) {
            fraction = 1f;
        } else {
            Log.d(TAG, "space =" + space);
            if (lastSpace == space && lastSpace != 0f) {
                Log.d(TAG, "出问题了-------");
            }
            lastSpace = space;
        }
        Log.d(TAG, "透明度 ： " + fraction + "统一设置为不透明");
        rlBar.setAlpha(1f);

        if (fraction >= 1f) {
            Log.d(TAG, "不透明或者吸附 ： " + 0f);
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Log.d(TAG, " fraction<1 且 没有吸附： " + (1f - fraction));
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.colorPrimary));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, " onResume 重新loop");
        if (headerBannerView!=null)
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
        if (headerBannerView!=null)
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
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isScrollIdle && bannerViewTopMargin < 0) return;
        // 获取广告头部View、自身的高度、距离顶部的高度
        if (itemHeaderBannerView == null) {
            itemHeaderBannerView = smoothListView.getChildAt(1);
        }
        if (itemHeaderBannerView != null) {
            bannerViewTopMargin = DensityUtil.px2dip(mContext, itemHeaderBannerView.getTop());
            bannerViewHeight = DensityUtil.px2dip(mContext, itemHeaderBannerView.getHeight());
            Log.d(TAG, "  111 " + bannerViewHeight + "   " + bannerViewTopMargin);
        }

        // 获取筛选View、距离顶部的高度
        if (itemHeaderFilterView == null) {
            itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
        }
        if (itemHeaderFilterView != null) {
            filterViewTopMargin = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
        }

        // 处理筛选是否吸附在顶部
        if (filterViewTopMargin <= titleViewHeight || firstVisibleItem > filterViewPosition) {
            isStickyTop = true; // 吸附在顶部
        } else {
            isStickyTop = false; // 没有吸附在顶部
        }

        if (isSmooth && isStickyTop) {
            isSmooth = false;
        }

        if (bannerViewTopMargin != 0) {
            // 处理标题栏颜色渐变
            handleTitleBarColorEvaluate();
        }
    }


}