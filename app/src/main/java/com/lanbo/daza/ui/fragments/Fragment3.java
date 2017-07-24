package com.lanbo.daza.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanbo.daza.MyApplication;
import com.lanbo.daza.R;
import com.lanbo.daza.adapter.CartAdapter;
import com.lanbo.daza.model.Cart;
import com.lanbo.daza.utils.CartProvider;

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

/**
 * 购物车页面
 */
public class Fragment3 extends Fragment {

    @BindView(R.id.rv_cart)
    RecyclerView mRecyclerView;

    private static final String TAG = Fragment3.class.getSimpleName();
    Map<String, String> header = MyApplication.getHeader();
    private CartProvider mCartProvider;
    CartAdapter mAdapter;
    String cartUrl = "http://app.mituomi.com/Mobile/Cart/ajaxCartList.html";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG,"购物车fg----onCreate----");
        super.onCreate(savedInstanceState);

        Thread t = new Thread(){
            @Override
            public void run() {
                // TODO: 2017/7/24 购物车页面逻辑
                getCartInfo(header);

            }
        };
        t.start();

    }

    private void getCartInfo(Map<String, String> header) {
        try {
            Document doc = Jsoup.connect(cartUrl).timeout(7000).header("token", header.get("token")).get();
            Elements elements = doc.select("div.shopCarCon");
            for (Element e : elements) {
                String goodsUrl = e.select("a").attr("href");
                String goodsID = getGoodsId(goodsUrl);
                String goodsImgUrl = e.select("a").select("img").attr("src");

                String goodsName = e.select("h4").text();
                String goodsNum = e.select("i").text();
                String goodsPrice = e.select("div.price").text().split(" ")[0];

                Log.i("购物车内容","\n goodsUrl = " + goodsUrl +
                        "\n goodsID = " + goodsID +
                        "\n goodsImgUrl = " + goodsImgUrl +
                        "\n goodsName = " + goodsName +
                        "\n goodsNum = " + goodsNum +
                        "\n goodsPrice = " + goodsPrice );
            }

            Log.i("购物车","加载完成");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url返回商品id，链接有问题返回""
     * @param goodsUrl 商品url
     * @return 商品id
     */
    private String getGoodsId(String goodsUrl) {
        if(goodsUrl.length()>0){
            int i = goodsUrl.lastIndexOf("/")+1;
            int j = goodsUrl.indexOf(".");
            if (i<j)
            return goodsUrl.substring(i,j);
        }
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"购物车fg----onCreateView----");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_page_cart, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        mCartProvider = CartProvider.getInstance(getContext());
        showData();
    }
    List<Cart> carts = new ArrayList<Cart>();
    private void showData() {
//        carts = mCartProvider.getAll();
        carts.add(new Cart());
        carts.add(new Cart());
        carts.add(new Cart());
        carts.add(new Cart());
        carts.add(new Cart());
        carts.add(new Cart());
        mAdapter = new CartAdapter(getContext(), carts);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}