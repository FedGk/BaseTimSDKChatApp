package com.yolo.kraus.bysjdemo01.Http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kraus on 2018/4/20.
 */

public class BaseHttp {
    public static String getAppServerUrl() {
        return APP_SERVER_URL;
    }

    private static final String APP_SERVER_URL = "http://119.28.176.152/";

    public static Retrofit mRetrofit()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APP_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return  retrofit;
    }
}
