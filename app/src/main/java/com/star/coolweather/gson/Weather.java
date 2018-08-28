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
 * 注意：如果Json字段的名字不适合直接用Java字段命名时，使用SerializedName注解的方式
 *       来让Json字段和Java字段进行建立映射关系。
 * ********************************************************
 */
public class Weather {

    //如果Json字段与Java字段的相同时，不用使用SerializedName
    public String status;
    //Json字段与Java字段不同，需要使用SerializedName来建立映射关系
    @SerializedName("basic")
    public Basic mBasic;
    @SerializedName("aqi")
    public AQI mAQI;
    @SerializedName("now")
    public Now mNow;
    @SerializedName("suggestion")
    public Suggestion mSuggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> mForecastList;
}
