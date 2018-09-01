package com.yolo.kraus.bysjdemo01.News.presenter;

import com.yolo.kraus.bysjdemo01.News.model.NewsJson;

public interface OnNewsListener {

    void OnSuccess(NewsJson newsJson);

    void OnError(Throwable e);
}
