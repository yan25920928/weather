package com.star.coolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * ********************************************************
 * 项目：CoolWeather
 * 作者：彦 on 2018/8/26.14:34
 * 邮箱：645786233@qq.com
 * 描述：地区实体类
 * 版本：v1.0
 * ********************************************************
 */
public class County extends LitePalSupport {

    private int id;
    private String countyName;
    private String weatherId;//对应天气的id
    private int cityId;//所属城市的Id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
