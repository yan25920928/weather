package com.star.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * ********************************************************
 * 项目：CoolWeather
 * 作者：彦 on 2018/8/27.23:38
 * 邮箱：645786233@qq.com
 * 描述：针对和风天气返回json数据，创建对应的实例类用以数据解析。
 * 版本：v1.0
 * ********************************************************
 */
public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
