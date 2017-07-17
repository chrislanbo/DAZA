package com.lanbo.daza.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.lanbo.daza.Constant;
import com.monkey.framework.entity.BaseResponse;
import com.monkey.framework.utils.MyHashMap;
import com.monkey.framework.utils.OkHttpUtil;
import com.monkey.framework.view.LoadingView;
import com.monkey.framework.view.MyToast;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求工具类，封装了数据请求及缓存
 */
public class RequestUtil {

    //http://7xwixt.com1.z0.glb.clouddn.com/ 七牛图片
//    String md5 = "9cf054d4d3de578e5e4c9e25a3292b17";
//    public static final String API_HOST = "http://test.mituomi.com";
    public static final String API_HOST = "http://app.mituomi.com";

    public static class CallBack {
        private Response.Listener listener;

        public CallBack(Response.Listener listener) {
            this.listener = listener;
        }

        public void send(String json) {
            if (listener == null) {
                return;
            }
            final BaseResponse res = new BaseResponse();
            JSONObject jsonObject = null;
            try {
                jsonObject = JSON.parseObject(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(json) || jsonObject == null) {
                MyToast.showBottom("请求失败");
                res.setFlag(false);
            } else if (jsonObject.getIntValue("code") != Constant.OKCODE) {
                res.setMessage(jsonObject.getString("message"));
                MyToast.showBottom(jsonObject.getString("message"));
            } else {
                String js = jsonObject.getString("data");
                if (TextUtils.isEmpty(js)) {
                    js = "";
                }
                res.setJson(js);
                res.setFlag(true);
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.onResponse(res);
                }
            });

        }

    }


//    public static void showDialog(String message) {
//        final Context context = ActivityManager.getActivities().get(ActivityManager.getActivities().size() - 1);
//        showDialog(context, message);
//    }
//
//    public static void showDialog(final Context context, String message) {
//        UsuallyDialog.Show(context, message, "", "确定", new UsuallyDialog.UsuallyCallBack() {
//            @Override
//            public void cancel(Dialog dialog) {
//            }
//
//            @Override
//            public void ok(Dialog dialog) {
//                dialog.dismiss();
//            }
//        }, new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                MyApplication.quit();
//                Intent intent = new Intent();
//                intent.setClass(context, StartPagerActivity.class);
//                context.startActivity(intent);
//                ActivityManager.finishAll();
//            }
//        });
//    }

    public static Map<String, String> getMap() {
        HashMap<String, String> map = new MyHashMap();
        return map;
    }

    /**
     * 登录请求
     *
     * @param listener    监听
     * @param loadingView loading
     */
    public static void login(Map<String, String> maps, final Response.Listener<?> listener,
                             LoadingView loadingView) {
        String url = API_HOST + "/Api/Index/postlogin.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }

    /**
     * 注册
     *
     * @param listener    监听
     * @param loadingView loading
     */
    public static void register(Map<String, String> maps, final Response.Listener<?> listener,
                                LoadingView loadingView) {
        String url = API_HOST + "/Api/Index/postregist.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }

    /**
     * 忘记密码 重置
     *
     * @param listener    监听
     * @param loadingView loading
     */
    public static void forgetPsd(Map<String, String> maps, final Response.Listener<?> listener,
                                 LoadingView loadingView) {
        String url = API_HOST + "/Api/Index/postresetpsd.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }


    /**
     * 获取code
     *
     * @param listener    监听
     * @param loadingView loading
     */
    @SuppressWarnings("rawtypes")
    public static void getCode(Map<String, String> maps, final Response.Listener<?> listener,
                               LoadingView loadingView) {
        String url = API_HOST + "/Api/Index/getcode.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }


    /**
     * 获取七牛云token
     *
     * @param listener    监听
     * @param loadingView loading
     */
    @SuppressWarnings("rawtypes")
    public static void getQiNiuToken(Map<String, String> maps, final Response.Listener<?> listener,
                                     LoadingView loadingView) {
        String url = API_HOST + "/Api/Online/postqiniutoken.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }


    /**
     * 提交头像
     *
     * @param listener    监听
     * @param loadingView loading
     */
    @SuppressWarnings("rawtypes")
    public static void putHead(Map<String, String> maps, final Response.Listener<?> listener,
                               LoadingView loadingView) {
        String url = API_HOST + "/Api/Online/postheadurl.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }

    /**
     * 获取tab的url
     *
     * @param listener    监听
     * @param loadingView loading
     */
    @SuppressWarnings("rawtypes")
    public static void getTabUrl(Map<String, String> maps, final Response.Listener<?> listener,
                                 LoadingView loadingView) {
        String url = API_HOST + "/Api/Index/gettaburl.html";
        OkHttpUtil.get(url, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }

    /**
     * 支付宝支付
     *
     * @param listener    监听
     * @param loadingView loading
     */
    public static void postalipay(Map<String, String> maps, final Response.Listener<?> listener,
                                  LoadingView loadingView) {
        String url = API_HOST + "/Api/Online/postalipay.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }
    /**
     * 支付宝支付成功
     *
     * @param listener    监听
     * @param loadingView loading
     */
    public static void alipaynotify(Map<String, String> maps, final Response.Listener<?> listener,
                                  LoadingView loadingView) {
        String url = API_HOST + "/Api/Online/alipaynotify.html";
        OkHttpUtil.post(url, null, maps, new OkHttpUtil.CallBackString() {
            @Override
            public void send(String json) {
                new CallBack(listener).send(json);
            }
        }, loadingView);
    }
}
