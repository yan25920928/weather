package com.star.coolweather.gson;

/**
 * ********************************************************
 * 项目：CoolWeather
 * 作者：彦 on 2018/8/27.23:37
 * 邮箱：645786233@qq.com
 * 描述：针对和风天气返回json数据，创建对应的实例类用以数据解析。
 * 版本：v1.0
 * ********************************************************
 */
public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
