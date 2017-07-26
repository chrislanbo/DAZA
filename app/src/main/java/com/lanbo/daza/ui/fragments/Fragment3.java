package com.lanbo.daza.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.lanbo.daza.utils.PreferencesUtils;
import com.lanbo.daza.utils.ToastUtil;

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
    public static final String GOT_CART_FORM_NET = "got_cart_form_net";
    public static final String GET_CART_FORM_NET = "get_cart_form_net";
    boolean gotCartFormNet = false; // 已经从服务器获过取购物车内容,并且存储到本地
    boolean getCartFormNet = true; // 从服务器获取购物车内容, true就从网络加载数据
    Map<String, String> header = MyApplication.getHeader();
    List<Cart> carts = new ArrayList<Cart>(); //购物车内容
    private CartProvider mCartProvider;
    CartAdapter mAdapter;
    String cartUrl = "http://app.mituomi.com/Mobile/Cart/ajaxCartList.html";

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtil.show(getContext(), "购物车数据成功");
//                    changeNetState();
                    showData();
                    break;
                case 0:
                    ToastUtil.show(getContext(), "购物车数据失败");
                    break;
                default:
                    break;
            }

        }
    };

    private void changeNetState() {
        getCartFormNet = false;
        gotCartFormNet = false;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "购物车fg----onCreate----");
        super.onCreate(savedInstanceState);
    }


    /**
     * 从服务端获取购物车内容
     *
     * @param header
     */
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
                int num = Integer.parseInt(goodsNum);
                String goodsPriceStr = e.select("div.price").text().split(" ")[0];
                float goodsPrice = Float.parseFloat(goodsPriceStr.substring(1));
                String check = e.select("input").attr("checked");
                boolean isChecked = check.equals("checked");
                Log.i("购物车内容", "\n goodsUrl = " + goodsUrl +
                        "\n goodsID = " + goodsID +
                        "\n goodsImgUrl = " + goodsImgUrl +
                        "\n goodsName = " + goodsName +
                        "\n goodsNum = " + goodsNum +
                        "\n goodsPrice = " + goodsPrice +
                        "\n 是否选中" + isChecked);

                mCartProvider.put(new Cart(goodsID, goodsName, goodsImgUrl, "", goodsPrice, num, isChecked));
            }

            Log.i("购物车", "加载完成");

            Message msg = new Message();
            // 消息对象可以携带数据
            msg.what = 1;
            handler.sendMessage(msg);
            getCartFormNet = PreferencesUtils.putBoolean(getContext(),GET_CART_FORM_NET,false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url返回商品id，链接有问题返回""
     *
     * @param goodsUrl 商品url
     * @return 商品id
     */
    private String getGoodsId(String goodsUrl) {
        if (goodsUrl.length() > 0) {
            int i = goodsUrl.lastIndexOf("/") + 1;
            int j = goodsUrl.indexOf(".");
            if (i < j)
                return goodsUrl.substring(i, j);
        }
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "购物车fg----onCreateView----");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_page_cart, null);
        ButterKnife.bind(this, view);
        init();
        gotCartFormNet = PreferencesUtils.getBoolean(getContext(), CartProvider.GOT_CART, false);
        getCartFormNet = PreferencesUtils.getBoolean(getContext(), GET_CART_FORM_NET, true);
        // TODO: 2017/7/26 根据条件判断是否从网络获取数据
        if (getCartFormNet) {
            Log.i(TAG, "onCreate: 需要从网络获取数据");
            Thread t = new Thread() {
                @Override
                public void run() {
                    // TODO: 2017/7/24 购物车页面逻辑
                    getCartInfo(header);

                }
            };
            t.start();
        } else {
            Log.i(TAG, "onCreate: 从本地获取数据");
            showData();
        }
        return view;
    }

    public void init() {
        mCartProvider = CartProvider.getInstance(getContext());
    }


    private void showData() {
        carts = mCartProvider.getAll();
        mAdapter = new CartAdapter(getContext(), carts);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}