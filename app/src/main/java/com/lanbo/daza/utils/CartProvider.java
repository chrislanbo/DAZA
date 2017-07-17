package com.lanbo.daza.utils;

import android.content.Context;
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

    /**
     * SparseArray<ShoppingCart>存放购物车数据key-value值
     * SharedPreferences将购物车数据存入本地
     */
    private SparseArray<Cart> datas = null;
    private static Context mContext;
    public static final String CART_JSON = "cart_json";

    private static CartProvider mInstance;

    public static CartProvider getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CartProvider(context);
        }
        return mInstance;
    }

    private CartProvider(Context context) {
        this.mContext = context;

        datas = new SparseArray<>(10);

        listToSparse();
    }

    //存储SparseArray<Cart>数据，同时更新SharedPreferences的数据到本地
    public void put(Cart cart) {

        //intValue():long类型id强制转换成int
        Cart temp = datas.get(cart.getId().intValue());

        if (temp != null) {
            temp.setCount(temp.getCount() + 1);
        } else {
            temp = cart;
            temp.setCount(1);
        }

        //将数据保存在SparseArray中
        datas.put(cart.getId().intValue(), temp);

        //将SparseArray<Cart>数据转换成List<Cart>数据保存在SharedPreferences中
        commit();
    }

    public void put(Goods wares) {
        Cart cart = convertData(wares);

        put(cart);
    }

    //Cart子类不能强制转换成Goods父类，将Wres中数据添加到Cart
    public Cart convertData(Goods wares) {
        Cart cart = new Cart();
        cart.setId(wares.getId());
        cart.setDescription(wares.getDescription());
        cart.setName(wares.getName());
        cart.setImgUrl(wares.getImgUrl());
        cart.setPrice(wares.getPrice());

        return cart;
    }

    //保存SparseArray<Cart>里的数据到本地
    public void commit() {
        List<Cart> carts = sparseToList();

        PreferencesUtils.putString(mContext, CART_JSON, JSONUtil.toJson(carts));
    }

    //将保存的数据转换成List<Cart>
    private List<Cart> sparseToList() {

        int size = datas.size();
        List<Cart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        return list;
    }

    //更新数据
    public void update(Cart cart) {
        datas.put(cart.getId().intValue(), cart);
        commit();

    }

    //删除数据
    public void delete(Cart cart) {

        datas.delete(cart.getId().intValue());

        commit();
    }

    //删除数据
    public void delete(List<Cart> carts) {
        if (carts != null && carts.size() > 0) {
            for (Cart cart : carts) {
                delete(cart);
            }
        }
    }

    //从本地获取数据
    public List<Cart> getAll() {

        return getDataFromLocal();
    }

    //将本地数据存放在SparseArray<Cart>中
    private void listToSparse() {
        List<Cart> carts = getDataFromLocal();

        if (carts != null && carts.size() > 0) {
            for (Cart cart : carts) {
                datas.put(cart.getId().intValue(), cart);
            }
        } else {
            datas.clear();
        }
    }

    ///获取本地数据
    private List<Cart> getDataFromLocal() {

        String json = PreferencesUtils.getString(mContext, CART_JSON);

        List<Cart> carts = null;

        if (json != null) {
            carts = JSONUtil.fromJson(json, new TypeToken<List<Cart>>() {
            }.getType());
        }
        return carts;
    }
}
