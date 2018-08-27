package com.star.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * ********************************************************
 * 项目：CoolWeather
 * 作者：彦 on 2018/8/26.14:31
 * 邮箱：645786233@qq.com
 * 描述：城市的实体类
 * 版本：v1.0
 * ********************************************************
 */
public class City extends DataSupport {

    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;//记录所属省份的ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
