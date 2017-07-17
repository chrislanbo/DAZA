package com.monkey.framework.utils;

import com.monkey.framework.MyFramwork;
import com.monkey.framework.entity.KeyValue;
import com.monkey.framework.utils.Cookie.PersistentCookieStore;
import com.monkey.framework.view.LoadingView;
import com.monkey.framework.view.MyToast;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Description ok 请求
 *
 * @author monkey
 * @email j_monkey@sina.cn
 * @date 2016/7/18
 */
public class OkHttpUtil {

    private static final OkHttpClient client = new OkHttpClient();
    private static HashMap<String, String> headers;

    public static HashMap<String, String> getHeaders() {
        if (headers == null) {
            headers = new MyHashMap();
        }
        return headers;
    }

    //请求超时设置
    public static void init() {
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
    }

    public static Request getRequest(String url, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder();
        addHeader(builder).url(url);
        if (requestBody != null) {
            builder.post(requestBody);
        }
        return builder.build();
    }

    //一般post请求 附带文件上传
    public static void post(String url, Map<String, File> files, Map<String, String> map, final CallBackString back, final LoadingView loadingView) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        boolean flag = false;
        String keys = "";
        if (map != null && map.size() > 0) {
            ArrayList<KeyValue> arrayList = new ArrayList<>();
            for (ConcurrentHashMap.Entry<String, String> entry : map.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
                arrayList.add(new KeyValue(entry.getKey().toLowerCase(), entry.getValue()));
                flag = true;
            }
            Collections.sort(arrayList);
            for (int i = 0; i < arrayList.size() && i < 4; i++) {
                keys += arrayList.get(i).getKey();
            }
        }
        if (files != null && files.size() > 0) {
            for (ConcurrentHashMap.Entry<String, File> entry : files.entrySet()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), entry.getValue());
                builder.addFormDataPart(entry.getKey(), entry.getValue().getName(), fileBody);
                flag = true;
            }
        }
        MyLog.d("keys:" + keys);
        MyLog.d("Url:" + url);
        if (map != null) {
            MyLog.d("Body:" + map.toString());
        }
        if (files != null) {
            MyLog.d("Body:" + files.toString());
        }

        init();
        if (loadingView != null) {
            loadingView.showLoadingView();
        }

        Call call;
        if (flag) {
            call = client.newCall(getRequest(url, builder.build()));
        } else {
            call = client.newCall(getRequest(url, null));
        }

//        setCookie();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                back.send(null);
                if (loadingView != null) {
                    loadingView.hideLoadingView();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str = response.body().string();
                MyLog.d(str);
                back.send(str);
                if (loadingView != null) {
                    loadingView.hideLoadingView();
                }
            }
        });
    }


    //一般get请求
    public static void get(String url, Map<String, String> map, final CallBackString back, final LoadingView loadingView) {
        String keys = "";
        if (map != null && map.size() > 0) {
            ArrayList<KeyValue> arrayList = new ArrayList<>();
            //拼接参数
            for (ConcurrentHashMap.Entry<String, String> entry : map.entrySet()) {
                url += "&" + entry.getKey() + "=" + entry.getValue();
                arrayList.add(new KeyValue(entry.getKey().toLowerCase(), entry.getValue()));
            }
            Collections.sort(arrayList);
            for (int i = 0; i < arrayList.size() && i < 4; i++) {
                keys += arrayList.get(i).getKey();
            }
        }
        MyLog.d("Url:" + url);
        MyLog.d("keys:" + keys);
        init();
        if (loadingView != null) {
            loadingView.showLoadingView();
        }
        Call call;
        call = client.newCall(getRequest(url, null));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                back.send(null);
                MyLog.d(e.toString());
                if (loadingView != null) {
                    loadingView.hideLoadingView();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str = response.body().string();
                MyLog.d(str);
                back.send(str);
                if (loadingView != null) {
                    loadingView.hideLoadingView();
                }
            }
        });
    }


    //设置cookie持久化
    public static void setCookie() {
        client.setCookieHandler(new CookieManager(
                new PersistentCookieStore(MyFramwork.getAppContext()),
                CookiePolicy.ACCEPT_ALL));
    }

    //添加header
    public static Request.Builder addHeader(Request.Builder builder) {
        if (headers != null) {
            for (HashMap.Entry<String, String> entity :
                    headers.entrySet()) {
                builder.addHeader(entity.getKey(), entity.getValue());
            }
        }
        return builder;
    }

    public interface CallBackString {
        void send(String json);
    }
}
