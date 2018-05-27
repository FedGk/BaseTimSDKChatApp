package com.yolo.kraus.bysjdemo01.Http.mInterface;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.WeatherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Kraus on 2018/5/27.
 */

public interface iWeather {
    @GET
    Observable<WeatherBean> getCityWeather(@Url String url, @Query("city") String city);
}
