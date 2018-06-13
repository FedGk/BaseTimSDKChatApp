package com.yolo.kraus.bysjdemo01.viewfeatures;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.WeatherBean;

import java.util.List;

/**
 * Created by Kraus on 2018/5/25.
 */

public interface WeatherView {
    
    void showProgress();

    void hideProgress();

    void showWeatherLayout();

    void setCity(String city);

    void setToday(String data);

    void setTemperature(String temperature);

    void setWind(String wind);

    void setWeather(String weather);

    void setWeatherImage(int res);

    void setWeatherData(List<WeatherBean.DataBean.ForecastBean> data);

    void showErrorToast(String msg);
}
