package com.star.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ********************************************************
 * 项目：CoolWeather
 * 作者：彦 on 2018/8/27.23:50
 * 邮箱：645786233@qq.com
 * 描述：针对和风天气返回json数据，创建对应的实例类用以数据解析。
 * 版本：v1.0
 * ********************************************************
 */
public class Weater {
    public String status;
    public Basic mBasic;
    public AQI mAQI;
    public Now mNow;
    public Suggestion mSuggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> mForecastList;
}
