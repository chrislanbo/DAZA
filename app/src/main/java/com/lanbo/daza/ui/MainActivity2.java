package com.lanbo.daza.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lanbo.daza.R;
import com.lanbo.daza.model.GoodsEntity;
import com.lanbo.daza.view.BottomTabView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.lanbo.daza.Constant.GLOD;
import static com.lanbo.daza.Constant.baseUrl;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = MainActivity2.class.getSimpleName();
    BottomTabView bottomTabView;

    ViewPager viewPager;

    FragmentPagerAdapter adapter;

    ArrayList<Fragment> fragments = new ArrayList<>();

    ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(6);
        bottomTabView = (BottomTabView) findViewById(R.id.bottomTabView);

        fragments = new ArrayList<>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(adapter);

        tabItemViews.add(new BottomTabView.TabItemView(this, "蜜拓蜜", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_home_black_24dp, R.drawable.ic_home_green_24dp));
        tabItemViews.add(new BottomTabView.TabItemView(this, "金币", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_account_balance_wallet_black_24dp, R.drawable.ic_account_balance_wallet_green_24dp));
        tabItemViews.add(new BottomTabView.TabItemView(this, "购物车", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_shopping_cart_black_24dp, R.drawable.ic_shopping_cart_green_24dp));
        tabItemViews.add(new BottomTabView.TabItemView(this, "我的", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_person_black_24dp, R.drawable.ic_person_green_24dp));

        bottomTabView.setTabItemViews(tabItemViews);

        bottomTabView.setUpWithViewPager(viewPager);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                getGoodsFromHtml();
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getCoinGoodsFromHtml();
//            }
//        }).start();
    }
    private List<GoodsEntity> goodsList = new ArrayList<>();

    private void getGoodsFromHtml() {

        try {
            //从一个URL加载一个Document对象
            Document doc = Jsoup.connect(baseUrl).get();
            Elements elements = doc.select("div.indexUl"); // 首页商品
//            Document doc2 = Jsoup.parse(elements.toString());
//            Elements elements2 = doc2.getElementsByTag("ul");
            Document divcontions = Jsoup.parse(elements.toString());
            Elements element = divcontions.getElementsByTag("li");
//            Log.i("element", element.toString());
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

        } catch (Exception e) {
            Log.i("mytag", e.toString());
        }
        Log.w(TAG,"商品数据准备完毕");

    }

    private void getCoinGoodsFromHtml() {
        try {
            //从一个URL加载一个Document对象
            Document doc = Jsoup.connect(baseUrl + GLOD).get();
            Elements coinE = doc.select("em"); //
            String coin = coinE.text();
            if (coin.length() == 0) {
                coin = "0";
            }

            Elements coinGoods = doc.select("div.goldShopCon"); //金币商品
            Elements element = coinGoods.select("li");
//            Elements element = doc.getElementsByTag("li");
            Log.i("coin", "" + coin);
            for (Element links : element) {
                String link_no_host = links.select("a").attr("href").trim();
                String imgUrl = links.select("img").attr("src").trim();
                if (imgUrl.length() == 0) {
                    String lazyUrl = links.select("img").attr("data-original").trim();
                    imgUrl = (lazyUrl.startsWith("http")) ? lazyUrl : baseUrl + lazyUrl;
                }
                int index = link_no_host.lastIndexOf("=");
                String goods_id = link_no_host.substring(index + 1);
                Elements title = links.select("p");
                String goods_name = title.text();
                Elements elementsPrice = links.select("span");
                String price = elementsPrice.text();
                Log.i("金币商品信息", "\n\n" + "link_no_host = " + link_no_host + "\n" + "imgUrl = " + imgUrl + "\n" + "goods_id = " + goods_id + "\n" + "goods_name = " + goods_name + "\n" + "price = " + price + "\n");
            }

        } catch (Exception e) {
            Log.i("errMsg", e.toString());
        }

        Log.w(TAG,"金币商品数据准备完毕");
    }


}