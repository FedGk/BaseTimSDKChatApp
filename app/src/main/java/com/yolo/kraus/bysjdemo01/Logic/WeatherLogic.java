package com.yolo.kraus.bysjdemo01.Logic;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.WeatherBean;
import com.yolo.kraus.bysjdemo01.Http.WeatherHttp;
import com.yolo.kraus.bysjdemo01.Http.mInterface.callBackBase;
import com.yolo.kraus.bysjdemo01.viewfeatures.WeatherView;

/**
 * Created by Kraus on 2018/5/25.
 */

public class WeatherLogic  {

    public void setView(WeatherView view) {
        this.view = view;
    }

    private WeatherView view;

    public void init(WeatherView v,String city)
    {
        this.view = v;
        setData(city);
    }

    private void setData(String city)
    {
        WeatherHttp.getWeather(city, new callBackBase<WeatherBean>() {
            @Override
            public void onSuccess(WeatherBean datas) {
                view.setWeatherData(datas);
            }

            @Override
            public void onError(Throwable e) {
                view.httpError(e);
            }
        });
    }



}
