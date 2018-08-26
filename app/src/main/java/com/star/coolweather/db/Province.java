package com.star.coolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * ********************************************************
 * 项目：CoolWeather
 * 作者：彦 on 2018/8/26.14:20
 * 邮箱：645786233@qq.com
 * 描述：省份的实体类
 * 版本：v1.0
 * ********************************************************
 */
public class Province extends LitePalSupport {

    private int id;
    private String provinceName;//省份名称
    private int provinceCode;//省份代码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
