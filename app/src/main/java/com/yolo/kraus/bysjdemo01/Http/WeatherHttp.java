package com.yolo.kraus.bysjdemo01.Http;

import android.util.Log;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.WeatherBean;
import com.yolo.kraus.bysjdemo01.Http.mInterface.callBackBase;
import com.yolo.kraus.bysjdemo01.Http.mInterface.iWeather;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kraus on 2018/5/27.
 */

public class WeatherHttp {

    private static final String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini";

    private static String TAG = WeatherHttp.class.getSimpleName();

    public static void getWeather(String city, final callBackBase<WeatherBean> cb)
    {
        iWeather iWeather = BaseHttp.mRetrofit().create(com.yolo.kraus.bysjdemo01.Http.mInterface.iWeather.class);
        iWeather.getCityWeather(WEATHER_URL,city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherBean weatherBean) {
//                        Log.d(TAG, "onNext: "+weatherBean.getStatus());
                        cb.onSuccess(weatherBean);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: "+e.toString());
                        cb.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
