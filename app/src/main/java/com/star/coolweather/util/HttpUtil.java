package com.star.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * ********************************************************
 * 项目：CoolWeather
 * 作者：彦 on 2018/8/26.21:45
 * 邮箱：645786233@qq.com
 * 描述：基于okhttp的网络请求封装
 * 版本：v1.0
 * ********************************************************
 */
public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
