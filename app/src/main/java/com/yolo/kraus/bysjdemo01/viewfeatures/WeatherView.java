package com.yolo.kraus.bysjdemo01.viewfeatures;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.WeatherBean;

/**
 * Created by Kraus on 2018/5/25.
 */

public interface WeatherView {

    void setWeatherData(WeatherBean data);

    void httpError(Throwable e);
}
