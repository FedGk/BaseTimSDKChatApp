package com.yolo.kraus.bysjdemo01.News.model;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface iNews {
    @GET
    Observable<NewsJson> getHotNews(@Url String url, @QueryMap Map<String,String> params);

}
