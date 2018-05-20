package com.yolo.kraus.bysjdemo01.Http.mInterface;

/**
 * Created by Kraus on 2018/4/20.
 */

public interface callBackBase<T> {
    public void onSuccess(T datas);
    public void onError(Throwable e);
}
