package com.yolo.kraus.bysjdemo01.News.presenter;

import android.util.Log;

import com.yolo.kraus.bysjdemo01.News.model.NewsJson;
import com.yolo.kraus.bysjdemo01.News.model.NewsModel;
import com.yolo.kraus.bysjdemo01.News.model.NewsModelImpl;
import com.yolo.kraus.bysjdemo01.News.view.NewsView;

public class NewsPresenterImpl implements OnNewsListener,NewsPresenter {


    private NewsView newsView;
    private NewsModel newsModel;

    private String TAG = "NewsPresenterImpl";

    public NewsPresenterImpl(NewsView view)
    {
        this.newsView = view;
        newsModel = new NewsModelImpl();
    }

    @Override
    public void OnSuccess(NewsJson newsJson) {
        Log.d(TAG, "OnSuccess: "+newsJson.getRequestId());
        newsView.setNewsData(newsJson);
    }

    @Override
    public void OnError(Throwable e) {
        Log.d(TAG, "OnError: "+e.toString());
    }

    @Override
    public void getNewsData() {
        newsModel.loadNews("Sport",this);
    }
}
