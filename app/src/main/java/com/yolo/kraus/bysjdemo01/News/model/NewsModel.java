package com.yolo.kraus.bysjdemo01.News.model;

import com.yolo.kraus.bysjdemo01.News.presenter.OnNewsListener;

public interface NewsModel {
    /**
     * @param cate 新闻分类预留接口
     * @param listener 回调监听
     */
    void loadNews(String cate, OnNewsListener listener);
}

