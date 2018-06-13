package com.yolo.kraus.bysjdemo01.Logic;

import android.content.Context;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.WeatherBean;
import com.yolo.kraus.bysjdemo01.Http.WeatherHttp;
import com.yolo.kraus.bysjdemo01.Http.mInterface.callBackBase;
import com.yolo.kraus.bysjdemo01.Model.WeatherModel;
import com.yolo.kraus.bysjdemo01.Util.ToolsUtil;
import com.yolo.kraus.bysjdemo01.viewfeatures.WeatherView;

import java.util.List;

/**
 * Created by Kraus on 2018/5/25.
 */

public class WeatherLogic  {

    private WeatherView mWeatherView;
    private Context mContext;


    public void init(Context con,String city,WeatherView v)
    {
        this.mWeatherView = v;
        this.mContext = con;
        //数据加载
        loadData(city);
    }

    private void loadData(String city)
    {
        mWeatherView.showProgress();
        if(!ToolsUtil.isNetworkAvailable(mContext))
        {
            mWeatherView.showErrorToast("无网络连接");
            return;
        }
        WeatherHttp.getWeather(city, new callBackBase<WeatherBean>() {
            @Override
            public void onSuccess(WeatherBean datas) {
                List<WeatherBean.DataBean.ForecastBean> listtemp = datas.getData().getForecast();

                mWeatherView.setToday(listtemp.get(0).getDate());
                mWeatherView.setCity(datas.getData().getCity());
                mWeatherView.setTemperature(listtemp.get(0).getHigh()+"-"+listtemp.get(0).getLow());
                mWeatherView.setWeather(listtemp.get(0).getType());
                mWeatherView.setWind(listtemp.get(0).getFengxiang());
                mWeatherView.setWeatherImage(WeatherModel.getWeatherImage(listtemp.get(0).getType()));

                listtemp.remove(0);
                mWeatherView.setWeatherData(listtemp);
                mWeatherView.hideProgress();
                mWeatherView.showWeatherLayout();
            }

            @Override
            public void onError(Throwable e) {
                mWeatherView.hideProgress();
                mWeatherView.showErrorToast(e.toString());
            }
        });
    }



}
