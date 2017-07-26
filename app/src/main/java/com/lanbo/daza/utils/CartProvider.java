package com.lanbo.daza.utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.lanbo.daza.model.Cart;
import com.lanbo.daza.model.Goods;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wumeng051 on 2017/7/17.
 * 购物车功能
 * SparseArray<ShoppingCart>存放购物车数据key-value值
 * SharedPreferences将购物车数据存入本地
 */
public class CartProvider {
    private static final String TAG = CartProvider.class.getSimpleName();
    private SparseArray<Cart> data = null;
    private static Context mContext;
    public static final String CART_JSON = "cart_json";
    public static final String GOT_CART = "got_cart"; // 从服务端获取过购物车

    private static CartProvider mInstance;

    public static CartProvider getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CartProvider(context);
        }
        return mInstance;
    }

    private CartProvider(Context context) {
        this.mContext = context;
        data = new SparseArray<>(10);
        listToSparse();
    }

    /**
     * 存储SparseArray<Cart>数据，同时更新SharedPreferences的数据到本地
     *
     * @param cart
     */
    public void put(Cart cart) {

        //intValue():long类型id强制转换成int
        Cart temp = data.get(Integer.parseInt(cart.getId()));

        if (temp != null) {
            temp.setCount(temp.getCount() + 1);
        } else {
            temp = cart;
            temp.setCount(1);
        }

        //将数据保存在SparseArray中
        data.put(Integer.parseInt(cart.getId()), temp);

        commit();
    }

    public void put(Goods goods) {
        Log.i(TAG, "put: goods");
        Cart cart = convertData(goods);

        put(cart);
    }

    /**
     * Cart 子类不能强制转换成Wares 父类，将Wares中数据添加到ShoppingCart
     *
     * @param goods
     * @return
     */
    public Cart convertData(Goods goods) {
        String id = goods.getId();
        String des = goods.getDescription();
        String name = goods.getName();
        String imgUrl = goods.getImgUrl();
        Float price = goods.getPrice();
        Cart cart = new Cart(id, name, imgUrl, des, price);
        Log.i(TAG, "convertData: goods---->shoppingCart");
        return cart;
    }

    /**
     * 保存SparseArray<Cart>里的数据到本地
     */
    public void commit() {
        Log.i(TAG, "commit: ---保存json到sp");
        List<Cart> carts = sparseToList();
        PreferencesUtils.putString(mContext, CART_JSON, JSONUtil.toJson(carts));
        PreferencesUtils.putBoolean(mContext, GOT_CART, true);
    }

    /**
     * 将保存的数据转换成List<Cart>
     *
     * @return
     */
    private List<Cart> sparseToList() {
        Log.i(TAG, "sparseToList: 数据转换成list");
        int size = data.size();
        List<Cart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(data.valueAt(i));
        }
        return list;
    }

    /**
     * 更新数据
     *
     * @param cart
     */
    public void update(Cart cart) {
        Log.i(TAG, "update数据，put新数据");
        data.put(Integer.parseInt(cart.getId()), cart);
        commit();

    }

    /**
     * 删除数据
     *
     * @param cart
     */
    public void delete(Cart cart) {
        Log.i(TAG, "delete: 删除数据指定对象");
        data.delete(Integer.parseInt(cart.getId()));
        commit();
    }

    /**
     * 删除数据
     *
     * @param carts
     */
    public void delete(List<Cart> carts) {
        Log.i(TAG, "delete: 删除所有数据");
        if (carts != null && carts.size() > 0) {
            for (Cart cart : carts) {
                delete(cart);
            }
        }
    }

    /**
     * 从本地获取数全部数据
     */
    public List<Cart> getAll() {
        Log.i(TAG, "getAll: 获取本地数据");
        return getDataFromLocal();
    }

    /**
     * 将本地数据存放在SparseArray<Cart>中
     */
    private void listToSparse() {
        Log.i(TAG, "listToSparse: 本地数据存到sa中");
        List<Cart> carts = getDataFromLocal();

        if (carts != null && carts.size() > 0) {
            for (Cart cart : carts) {
                data.put(Integer.parseInt(cart.getId()), cart);
            }
        } else {
            data.clear();
        }
    }

    /**
     * 获取本地数据,获取sp文件的json文件
     *
     * @return 对象集合
     */
    private List<Cart> getDataFromLocal() {

        String json = PreferencesUtils.getString(mContext, CART_JSON);
        Log.i(TAG, "getDataFromLocal: " + json);
        List<Cart> carts = null; // 清空集合

        // 赋值集合，返回对象集合
        if (json != null) {
            carts = JSONUtil.fromJson(json, new TypeToken<List<Cart>>() {
            }.getType());
        }
        return carts;
    }
}
