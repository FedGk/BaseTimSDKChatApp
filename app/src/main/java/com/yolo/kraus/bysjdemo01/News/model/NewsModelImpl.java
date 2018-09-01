package com.yolo.kraus.bysjdemo01.News.model;

import android.util.Log;

import com.yolo.kraus.bysjdemo01.Http.BaseHttp;
import com.yolo.kraus.bysjdemo01.News.presenter.OnNewsListener;
import com.yolo.kraus.bysjdemo01.Util.MD5Until;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsModelImpl implements NewsModel {

    private static final String NEWS_URL = "https://api.xinwen.cn/news/hot";
    private static final String[] keys = {"access_key","timestamp","signature","category","size"};
    private static  final String   ACCESS_KEY = "JeGxGJfXLFxnCkg0";
    private static  final String   SECRET_KEY = "2faed6f97f3848ebbc5af8fb0ea940cd";

    private String TAG = "NewsModelImpl";


    private Map<String,String> params = new HashMap<>();


    @Override
    public void loadNews(String cate, final OnNewsListener listener)
    {

        String time = Long.toString(new Date().getTime());

        Log.d(TAG, "loadNews: "+time);

        params.put(keys[0],ACCESS_KEY);
        params.put(keys[1],time);
        params.put(keys[2], MD5Until.getMd5(time));
        params.put(keys[3],cate);
        params.put(keys[4],"20");

        iNews iNews = BaseHttp.mRetrofit().create(com.yolo.kraus.bysjdemo01.News.model.iNews.class);



        iNews.getHotNews(NEWS_URL,params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<NewsJson>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(NewsJson newsJson) {
                    listener.OnSuccess(newsJson);
                }

                @Override
                public void onError(Throwable e) {
                    listener.OnError(e);
                }

                @Override
                public void onComplete() {

                }
            });


    }
}
