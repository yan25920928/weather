package com.star.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.star.coolweather.gson.Forecast;
import com.star.coolweather.gson.Weather;
import com.star.coolweather.util.HttpUtil;
import com.star.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView mWeatherLayout;
    private TextView mTitleCity;
    private TextView mTitleUpdateTime;
    private TextView mWeatherInfoText;
    private LinearLayout mForecastLayout;
    private TextView mAqiText;
    private TextView mPm25Text;
    private TextView mComfortText;
    private TextView mCarWashText;
    private TextView mSportText;
    private TextView mDegreeText;
    private ImageView mBingPicImg;
    private Button mNavButton;
    //需要在ChooseAreaFragment中被调用
    public DrawerLayout mDrawerLayout;
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hideStatusBar();
        setContentView(R.layout.activity_weather);
        initView();
        initData();
        setListener();
    }

    /**
     * TODO:隐藏顶部状态栏,在setContentView之前调用
     * 此方法，可提取到工具类中
     */
    private void hideStatusBar() {
        //Android5.0以上支持
        if (Build.VERSION.SDK_INT >= 21){
            //获取DecorView
            View decorView = getWindow().getDecorView();
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统UI
            decorView.setSystemUiVisibility(
                    //全屏FullScreen
                    View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //设置状态栏透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        String bingPic = prefs.getString("bing_pic",null);
        final String weatherId;
        if (weatherString != null) {
            //本地是否缓存图片
            if (bingPic != null){
                Glide.with(this).load(bingPic).into(mBingPicImg);
            }else {
                loadBingPic();
            }
            //有缓存直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.mBasic.weatherId;
            showWeatherInfo(weather);
        } else {
            //无缓存时，去服务器查询天气
            weatherId = getIntent().getStringExtra("weather_id");
            mWeatherLayout.setVisibility(View.VISIBLE);
            requestWeather(weatherId);
        }

        //下拉刷新请求
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mNavButton = findViewById(R.id.nav_button);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        //下拉刷新
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mWeatherLayout = findViewById(R.id.weather_layout);
        mTitleCity = findViewById(R.id.title_city);
        mTitleUpdateTime = findViewById(R.id.title_update_time);
        mDegreeText = findViewById(R.id.degree_text);
        mWeatherInfoText = findViewById(R.id.weather_info_text);
        mForecastLayout = findViewById(R.id.forecast_layout);
        mAqiText = findViewById(R.id.aqi_text);
        mPm25Text = findViewById(R.id.pm25_text);
        mComfortText = findViewById(R.id.comfort_text);
        mCarWashText = findViewById(R.id.car_wash_text);
        mSportText = findViewById(R.id.sport_text);
        mBingPicImg = findViewById(R.id.skin_bing_pic_img);
    }

    /**
     * 监听事件
     */
    private void setListener(){
        mNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    /**
     * 处理并展示Weather实体类中的数据
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        //校验天气数据
        if (!checkWeatherInfo(weather)) {
            Toast.makeText(WeatherActivity.this, "解析天气信息失败", Toast.LENGTH_LONG).show();
            return;
        }

        String cityName = weather.mBasic.cityName;
        //截取数据
        String updateTime = weather.mBasic.update.updateTime.split(" ")[1];
        //摄氏度
        String degree = weather.mNow.temperature + getResources().getString(R.string.weather_info_temperature_degree);
        String weatherInfo = weather.mNow.more.info;
        mTitleCity.setText(cityName);
        mDegreeText.setText(degree);
        mTitleUpdateTime.setText(updateTime);
        mWeatherInfoText.setText(weatherInfo);
        //移除子视图
        mForecastLayout.removeAllViews();

        //循环加载，显示预测信息
        for (Forecast forecast : weather.mForecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, mForecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            mForecastLayout.addView(view);
        }
        //显示指数信息
        if (weather.mAQI != null) {
            mAqiText.setText(weather.mAQI.city.aqi);
            mPm25Text.setText(weather.mAQI.city.pm25);
        }
        //显示建议信息
        String comfort = getResources().getString(R.string.suggestion_life) +
                weather.mSuggestion.comfort.info;
        String carWash = getResources().getString(R.string.suggestion_carWash_index) +
                weather.mSuggestion.carWash.info;
        String sport = getResources().getString(R.string.suggestion_sport) +
                weather.mSuggestion.sport.info;

        mComfortText.setText(comfort);
        mCarWashText.setText(carWash);
        mSportText.setText(sport);
        mWeatherLayout.setVisibility(View.VISIBLE);

    }

    /**
     * weather数据映射gson，进行非空判断
     * @param weather
     * @return
     */
    private boolean checkWeatherInfo(Weather weather) {
        return  weather.mBasic != null &&
                weather.mSuggestion != null &&
                weather.mAQI != null &&
                weather.mNow != null &&
                weather.mForecastList != null;
    }

    /**
     * 加载必应每日图片
     */
    private void loadBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                        WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(mBingPicImg);
                    }
                });
            }
        });
    }

    /**
     * 请求城市天气信息，并缓存到SharedPreferences中
     *
     * @param weatherId 天气id
     */
    public void requestWeather(final String weatherId) {
        //加载背景图片
        loadBingPic();
        //TODO:KEY记得去申请
        String weatherUrl = "http:guolin.tech/api/weather?cityid=" + weatherId + "&key=dd9d0d2964654269b9516851456f28cb";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_LONG).show();
                        //停止刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor =
                                    PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_LONG).show();
                        }

                        //停止刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
}
